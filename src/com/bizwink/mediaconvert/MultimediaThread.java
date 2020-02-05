package com.bizwink.mediaconvert;

import com.bizwink.archiver.ArchiveWorker;
import com.bizwink.cms.multimedia.Multimedia;
import com.bizwink.cms.news.*;
import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.sitesetting.ISiteInfoManager;
import com.bizwink.cms.sitesetting.SiteInfo;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.publishQueue.IPublishQueueManager;
import com.bizwink.publishQueue.PublishQueuePeer;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-25
 * Time: 10:05:56
 * To change this template use File | Settings | File Templates.
 */
public class MultimediaThread implements Runnable {
    private Thread runner;
    private String logFileString;
    private PrintWriter log;
    private String pid;
    PoolServer cpool;

    public MultimediaThread(String logPath, PoolServer cpool) throws IOException {
        this.cpool = cpool;

        try {
            if (logPath!= null) {
                int posi = logPath.lastIndexOf(File.separator);
                if (posi>-1) logPath = logPath.substring(0,posi+1);
                this.logFileString = logPath;
                log = new PrintWriter(new FileOutputStream(logFileString + "media_server.log"), true);
            }
        }
        catch (IOException e1) {
            System.err.println("Warning:Indexer could not open \""
                    + logFileString + "\" to write log to. Make sure that your Java " +
                    "process has permission to write to the file and that the directory exists."
            );
            try {
                log = new PrintWriter(new FileOutputStream("DCB_" + System.currentTimeMillis() + ".log"), true
                );
            }
            catch (IOException e2) {
                throw new IOException("Can't open any log file");
            }
        }

        // Write the pid file (used to clean up dead/broken connection)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
        java.util.Date nowc = new java.util.Date();
        pid = formatter.format(nowc);

        BufferedWriter pidout = new BufferedWriter(new FileWriter(logFileString + "pid"));
        pidout.write(pid);
        pidout.close();

        log.println("Starting Multimedia:");
        log.println("-----------------------------------------");
        log.println(pidout.toString());

        runner = new Thread(this);
        runner.start();
    }

    public void run() {
        Connection conn = null;
        SiteInfo siteInfo = null;
        boolean doFlag = true;
        int ftype = -1;

        ISiteInfoManager siteInfoManager = SiteInfoPeer.getInstance();
        IColumnManager columnManager = ColumnPeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        IPublishManager publishMgr = PublishPeer.getInstance();
        IPublishQueueManager queueMgr = PublishQueuePeer.getInstance();

        while (doFlag) {
            int count = 0;
            int articleid = 0;
            try {
                //获得所有未转换的多媒体
                String SQL_GET_MULTIMEDIA_ALL = "select * from tbl_multimedia where encodeflag =0";
                //更新多媒体转换标志  为以转换状态
                String SQL_UPDATE_MULTIMEDIA = "update tbl_multimedia set encodeflag=1 where id=?";

                List multlist = new ArrayList();
                PreparedStatement pstmt=null;
                ResultSet rs=null;
                try {
                    conn = cpool.getConnection();
                    pstmt = conn.prepareStatement(SQL_GET_MULTIMEDIA_ALL);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        Multimedia mult = new Multimedia();
                        mult.setId(rs.getInt("id"));
                        mult.setSiteid(rs.getInt("siteid"));
                        mult.setArticleid(rs.getInt("articleid"));
                        mult.setDirname(rs.getString("dirname"));
                        mult.setFilepath(rs.getString("filepath"));
                        mult.setOldfilename(rs.getString("oldfilename"));
                        mult.setNewfilename(rs.getString("newfilename").trim());
                        mult.setEncodeflag(rs.getInt("encodeflag"));
                        mult.setInfotype(rs.getInt("infotype"));
                        multlist.add(mult);
                    }
                    rs.close();
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    doFlag = false;
                } finally {
                    if (conn != null) {
                        try {
                            cpool.freeConnection(conn);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    for (int i = 0; i < multlist.size(); i++) {
                        Multimedia mult = (Multimedia) multlist.get(i);
                        //获取在栏目定义中设置的视频文件宽度和长度数据，获取视频文件缩略图的宽度和长度设置
                        articleid = mult.getArticleid();
                        Article article = articleMgr.getArticle(articleid);
                        Column column = null;
                        String mediasize = null;
                        String mediapicsize = null;
                        int columnid = article.getColumnID();
                        while ((mediasize==null || mediapicsize == null) && columnid>0) {
                            column = columnManager.getColumn(columnid);
                            mediasize = column.getMediasize();
                            mediapicsize = column.getMediapicsize();
                            columnid = column.getParentID();
                        }

                        //如果在当前栏目和它的以上各级栏目都没有视频文件的长度和宽度设置，从站点设置中尝试获取视频文件的宽度和长度数据
                        //如果在站点设置和本栏目以及本栏目以上各级栏目都没有视频文件的宽度和长度设置，则采用默认的宽度和长度设置
                        if (mediasize==null || mediapicsize == null) {
                            siteInfo = siteInfoManager.getSiteInfo(mult.getSiteid());
                            mediasize = siteInfo.getMediasize();
                            mediapicsize = siteInfo.getMediapicsize();
                        }

                        if (mediasize==null) mediasize = "800x600";
                        if (mediapicsize == null) mediapicsize = "400x300";

                        String cmd = "";
                        String fileOut = mult.getFilepath();
                        fileOut = StringUtil.replace(fileOut,"/", File.separator);
                        String fileName = mult.getNewfilename().trim();
                        String Path = fileOut.substring(0, fileOut.lastIndexOf("sites") + 5) + java.io.File.separator + "_commons";
                        String oldfilename = mult.getOldfilename().trim();
                        String type = oldfilename.substring(oldfilename.lastIndexOf(".") + 1, oldfilename.length()).toLowerCase();

                        if (type.equals("avi") || type.equals("vob") || type.equals("mpg") || type.equals("wmv") || type.equals("3gp") || type.equals("mov") || type.equals("mp4") ||
                                type.equals("asf") || type.equals("asx") || type.equals("mpeg") || type.equals("mpe")) {
                            ftype = 0;
                        } else if (type.equals("flv")) {
                            ftype = 3;
                        } else if (type.equals("wmv9") || type.equals("rm") || type.equals("rmvb")) {
                            ftype = 1;
                        }

                        log.println("ftype=" + ftype + "==" + fileName + "==" + oldfilename);

                        if (ftype!=3) {
                            String newName = fileName.substring(0, fileName.lastIndexOf(".") + 1) + type;
                            String inPath = fileOut + newName; //完整文件路径
                            String outPath = fileOut + fileName;

                            File filePath = new File(inPath);
                            if(filePath.exists() && filePath.length() > 0) {     //如果用户上传的文件在目的目录存在则执行文件转换
                                if (ftype == 0) {
                                    mediasize = mediasize.replace("X","x");
                                    if (cpool.getOStype().equalsIgnoreCase("win2000"))
                                        //cmd = Path + java.io.File.separator + "ffmpeg.exe -i " + inPath + " -y -s 640x480 -deinterlace -b 256 -ab 128 -ar 22050 -ac 2 -qscale 6 -c:v libx264 -crf 24 " + outPath; //命令字串
                                        cmd = Path + java.io.File.separator + "ffmpeg.exe -i " + inPath + " -y -s " + mediasize + " -deinterlace -b 256 -ab 128 -ar 22050 -ac 2 -qscale 6 -crf 24 " + outPath; //命令字串
                                    else if (cpool.getOStype().equalsIgnoreCase("unix"))
                                        //cmd = "ffmpeg -i " + inPath + " -y -s 640x480 -deinterlace -b 256 -ab 128 -ar 22050 -ac 2 -qscale 6 -c:v libx264 -crf 24 " + outPath; //命令字串
                                        cmd = "ffmpeg -i " + inPath + " -y -s " + mediasize + " -deinterlace -b 256 -ab 128 -ar 22050 -ac 2 -qscale 6 -crf 24 " + outPath; //命令字串
                                } else if (ftype == 1) {
                                    mediasize = mediasize.replace("X",":");
                                    if (cpool.getOStype().equalsIgnoreCase("win2000"))
                                        cmd = Path + java.io.File.separator + "mencoder.exe " + inPath + " -o " + outPath + " -lavfopts i_certify_that_my_video_stream_does_not_use_b_frames -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=1000:mbd=2:mv0:trell:v4mv:cbp:last_pred=3:dia=4:cmp=3:vb_strategy=1 -vf scale=" + mediasize + ",expand=" + mediasize + ":::1,crop=" + mediasize + ":0:0 -ofps 30 -srate 22050"; //命令字串
                                    else if (cpool.getOStype().equalsIgnoreCase("unix"))
                                        cmd = "mencoder " + inPath + " -o " + outPath + " -lavfopts i_certify_that_my_video_stream_does_not_use_b_frames -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=1000:mbd=2:mv0:trell:v4mv:cbp:last_pred=3:dia=4:cmp=3:vb_strategy=1 -vf scale=" + mediasize + ",expand=" + mediasize + ":::1,crop=" + mediasize + ":0:0 -ofps 30 -srate 22050"; //命令字串
                                }
                                log.println("cmd=" + cmd);

                                try {
                                    //如果目录路径不存在则创建
                                    File file = new File(fileOut);
                                    if (!file.exists()) {
                                        file.mkdirs();
                                    }
                                    Process p = Runtime.getRuntime().exec(cmd);
                                    //获取进程的标准输入流
                                    final InputStream is1 = p.getInputStream();
                                    //获取进程的错误流
                                    final InputStream is2 = p.getErrorStream();
                                    //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
                                    new Thread() {
                                        public void run() {
                                            BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                                            try {
                                                String line1 = null;
                                                while ((line1 = br1.readLine()) != null) {
                                                    if (line1 != null) {
                                                    }
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } finally {
                                                try {
                                                    is1.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }.start();

                                    new Thread() {
                                        public void run() {
                                            BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                                            try {
                                                String line2 = null;
                                                while ((line2 = br2.readLine()) != null) {
                                                    if (line2 != null) {
                                                    }
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } finally {
                                                try {
                                                    is2.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }.start();
                                    p.waitFor();//java程序等待执行过程完毕
                                    p.destroy();

                                    filePath = new File(outPath);
                                    //如果文件存在,并且长度不为0,则表示转换成功.
                                    boolean success = filePath.exists() && filePath.length() > 0;
                                    if (success) {
                                        //创建缩略图,修改文章的状态位，如果文章需要审核，修改为待审状态，否则修改为新稿状态
                                        //-i %2 -y -f image2 -ss 5 -t 0.001 -s 250x170 %3 2
                                        if (cpool.getOStype().equalsIgnoreCase("win2000"))
                                            cmd = Path + java.io.File.separator + "ffmpeg.exe -i " + outPath + " -y -f image2 -ss 5 -t 0.001 -s " + mediapicsize + " " + fileOut + java.io.File.separator + fileName.substring(0, fileName.lastIndexOf(".") + 1) + "jpg";
                                        else if (cpool.getOStype().equalsIgnoreCase("unix"))
                                            cmd = "ffmpeg -i " + outPath + " -y -f image2 -ss 5 -t 0.001 -s " + mediapicsize + " " + fileOut + java.io.File.separator + fileName.substring(0, fileName.lastIndexOf(".") + 1) + "jpg";

                                        Process p1 = Runtime.getRuntime().exec(cmd);

                                        //获取进程的错误流
                                        InputStream is = p1.getErrorStream();
                                        InputStreamReader inputStreamReader = new InputStreamReader(is);
                                        BufferedReader inputBufferedReader = new BufferedReader(inputStreamReader);
                                        String line = null;
                                        StringBuilder stringBuilder = new StringBuilder();
                                        while ((line = inputBufferedReader.readLine()) != null) {
                                            stringBuilder.append(line);
                                        }
                                        inputBufferedReader.close();
                                        inputStreamReader.close();
                                        is.close();

                                        p1.waitFor();
                                        p1.destroy();

                                        //修改tbl_multimedia表中多媒体文件已经被成功转换的状态标识位
                                        try {
                                            conn = cpool.getConnection();
                                            conn.setAutoCommit(false);
                                            pstmt = conn.prepareStatement(SQL_UPDATE_MULTIMEDIA);
                                            pstmt.setInt(1, mult.getId());
                                            pstmt.executeUpdate();
                                            pstmt.close();
                                            conn.commit();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            doFlag = false;
                                        } finally {
                                            if (conn != null) {
                                                try {
                                                    cpool.freeConnection(conn);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        //修改tbl_article表中文章状态标识位从带转换状态为新稿状态或者待审核状态
                                        articleMgr.updateStatus(articleid, 1);

                                        //将mp4文件、FLV文件和缩略图文件发送到WWW服务器
                                        fileOut = mult.getFilepath();
                                        fileName = mult.getNewfilename();
                                        String mp4MediaFileName = fileOut + fileName.substring(0, fileName.indexOf(".") + 1) + "mp4";
                                        String mediaFileName = fileName.substring(0, fileName.lastIndexOf(".") + 1) + "flv";
                                        String imgFileName = fileName.substring(0, fileName.lastIndexOf(".") + 1) + "jpg";
                                        fileOut = StringUtil.replace(fileOut, "/", File.separator);
                                        mediaFileName = fileOut + mediaFileName;
                                        imgFileName = fileOut + imgFileName;
                                        String fileDir = mult.getDirname();
                                        fileDir = StringUtil.replace(fileDir, "/", File.separator);

                                        //将多媒体文件和截取的图片文件发布的目的服务器
                                        if (article != null) {
                                            int errcode1 = publishMgr.publish(article.getEditor(), mediaFileName, article.getSiteID(), fileDir + "images" + File.separator, 0);
                                            int errcode2 = publishMgr.publish(article.getEditor(), mp4MediaFileName, article.getSiteID(), fileDir + "images" + File.separator, 0);
                                            int errcode3 = publishMgr.publish(article.getEditor(), imgFileName, article.getSiteID(), fileDir + "images" + File.separator, 0);

                                            //在作业队列中加入需要发布的作业
                                            queueMgr.insertJobs(article);
                                        }
                                        //统计转换文件的数量
                                        count++;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {                        //用户直接上传FLV格式的视频文件
                            log.println("用户直接上传了FLV文件：" + fileOut);
                            String inPath = fileOut + fileName;
                            String outPath = fileOut + fileName.substring(0, fileName.lastIndexOf(".") + 1) + "mp4";
                            //生成MP4格式的文件
                            if (cpool.getOStype().equalsIgnoreCase("win2000"))
                                cmd = Path + java.io.File.separator + "ffmpeg.exe -i " + inPath + " -y -s " + mediasize + " -deinterlace -b 256 -ab 128 -ar 22050 -ac 2 -qscale 6 -crf 24 " + outPath; //命令字串
                            else if (cpool.getOStype().equalsIgnoreCase("unix"))
                                cmd = "ffmpeg -i " + inPath + " -y -s " + mediasize + " -deinterlace -b 256 -ab 128 -ar 22050 -ac 2 -qscale 6 -crf 24 " + outPath; //命令字串

                            log.println(cmd);

                            //如果目录路径不存在则创建
                            File file = new File(fileOut);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            Process p = Runtime.getRuntime().exec(cmd);
                            //获取进程的标准输入流
                            final InputStream is1 = p.getInputStream();
                            //获取进程的错误流
                            final InputStream is2 = p.getErrorStream();
                            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
                            new Thread() {
                                public void run() {
                                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                                    try {
                                        String line1 = null;
                                        while ((line1 = br1.readLine()) != null) {
                                            if (line1 != null) {
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            is1.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();

                            new Thread() {
                                public void run() {
                                    BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                                    try {
                                        String line2 = null;
                                        while ((line2 = br2.readLine()) != null) {
                                            if (line2 != null) {
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            is2.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                            p.waitFor();//java程序等待执行过程完毕
                            p.destroy();

                            //创建缩略图,修改文章的状态位，如果文章需要审核，修改为待审状态，否则修改为新稿状态
                            //-i %2 -y -f image2 -ss 5 -t 0.001 -s 250x170 %3 2
                            if (cpool.getOStype().equalsIgnoreCase("win2000"))
                                cmd = Path + java.io.File.separator + "ffmpeg.exe -i " + inPath + " -y -f image2 -ss 5 -t 0.001 -s " + mediapicsize + " " + fileOut + java.io.File.separator + fileName.substring(0, fileName.lastIndexOf(".") + 1) + "jpg";
                            else if (cpool.getOStype().equalsIgnoreCase("unix"))
                                cmd = "ffmpeg -i " + inPath + " -y -f image2 -ss 5 -t 0.001 -s " + mediapicsize + " " + fileOut + java.io.File.separator + fileName.substring(0, fileName.lastIndexOf(".") + 1) + "jpg";

                            Process p1 = Runtime.getRuntime().exec(cmd);

                            //获取进程的错误流
                            InputStream is = p1.getErrorStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(is);
                            BufferedReader inputBufferedReader = new BufferedReader(inputStreamReader);
                            String line = null;
                            StringBuilder stringBuilder = new StringBuilder();
                            while ((line = inputBufferedReader.readLine()) != null) {
                                stringBuilder.append(line);
                            }
                            inputBufferedReader.close();
                            inputStreamReader.close();
                            is.close();

                            p1.waitFor();
                            p1.destroy();

                            //修改tbl_multimedia表中多媒体文件已经被成功转换的状态标识位
                            try {
                                conn = cpool.getConnection();
                                conn.setAutoCommit(false);
                                pstmt = conn.prepareStatement(SQL_UPDATE_MULTIMEDIA);
                                pstmt.setInt(1, mult.getId());
                                pstmt.executeUpdate();
                                pstmt.close();
                                conn.commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                                doFlag = false;
                            } finally {
                                if (conn != null) {
                                    try {
                                        cpool.freeConnection(conn);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            //修改tbl_article表中文章状态标识位从带转换状态为新稿状态或者待审核状态
                            articleMgr.updateStatus(articleid, 1);

                            //将mp4文件、FLV文件和缩略图文件发送到WWW服务器
                            fileOut = mult.getFilepath();
                            fileName = mult.getNewfilename();
                            String mp4MediaFileName = fileOut + fileName.substring(0, fileName.indexOf(".") + 1) + "mp4";
                            String mediaFileName = fileName.substring(0, fileName.lastIndexOf(".") + 1) + "flv";
                            String imgFileName = fileName.substring(0, fileName.lastIndexOf(".") + 1) + "jpg";
                            fileOut = StringUtil.replace(fileOut, "/", File.separator);
                            mediaFileName = fileOut + mediaFileName;
                            imgFileName = fileOut + imgFileName;
                            String fileDir = mult.getDirname();
                            fileDir = StringUtil.replace(fileDir, "/", File.separator);

                            //将多媒体文件和截取的图片文件发布的目的服务器
                            if (article != null) {
                                int errcode1 = publishMgr.publish(article.getEditor(), mediaFileName, article.getSiteID(), fileDir + "images" + File.separator, 0);
                                int errcode2 = publishMgr.publish(article.getEditor(), mp4MediaFileName, article.getSiteID(), fileDir + "images" + File.separator, 0);
                                int errcode3 = publishMgr.publish(article.getEditor(), imgFileName, article.getSiteID(), fileDir + "images" + File.separator, 0);

                                //在作业队列中加入需要发布的作业
                                queueMgr.insertJobs(article);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //进程停止1分钟，然后再次扫描看是否有新的文件需要转换
            try {
                System.out.println("完成" + count + "视频文件转换,进程睡眠5分钟，等待待处理的新的视频文件");
                Thread.sleep(5*1000 * 60);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
