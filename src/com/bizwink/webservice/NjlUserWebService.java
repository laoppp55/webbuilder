package com.bizwink.webservice;

import com.bizwink.cms.modelManager.*;
import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishException;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.po.CmsTemplate;
import com.bizwink.po.Siteinfo;
import com.bizwink.po.SitesNumber;
import com.bizwink.po.Users;
import com.bizwink.service.NjlUserService;
import com.google.gson.Gson;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by petersong on 15-12-27.
 */
public class NjlUserWebService {
    static ApplicationContext applicationContext = null;

    public NjlUserWebService() {
        applicationContext = ContextLoaderListener.getCurrentWebApplicationContext();
    }

    public List<String> registerNjlUser(List njluser,int TemplateNum,String contactor,int samsiteid) {
        List mm = new ArrayList();
        String str =null;
        Siteinfo siteinfo = null;
        //Siteinfo samsiteinfo = null;
        //int rootColumnID = 0;
        //Users user = null;

        if (applicationContext != null) {
            NjlUserService njlUserService = applicationContext.getBean(NjlUserService.class);
            siteinfo = njlUserService.RegisterNjlUserInfo(njluser,TemplateNum,contactor,samsiteid);
            //user = (Users)njluser.get(0);

            //获得共享网站的信息
            //samsiteinfo = njlUserService.getSiteinfo(BigDecimal.valueOf(samsiteid));

            //获取共享网站的根栏目ID
            //rootColumnID =njlUserService.getRootColumnIdBySiteid(BigDecimal.valueOf(samsiteid));
            Gson gson = new Gson();
            str = gson.toJson(siteinfo);
        } else {
            System.out.println("applicationContext get failed" );
        }

        //如果注册用户成功，拷贝相应的文件到WWW服务器
        if (siteinfo != null) {
/*
            IModelManager modelManager = ModelPeer.getInstance();
            try {
                Model model = modelManager.getModel(samsiteid,rootColumnID,TemplateNum);
                if (model!=null) {
                    String path = this.getClass().getResource("").getPath();
                    int posi = path.indexOf("WEB-INF");
                    path = path.substring(0,posi);

                    //源文件路径
                    String srcPath = path  + "sites/" + samsiteinfo.getSITENAME().replace(".","_");
                    //目标文件路径
                    String objPath = path  + "sites/" + siteinfo.getSITENAME().replace(".","_");

                    System.out.println("srcPath===" + srcPath);
                    System.out.println("objPath===" + objPath);


                    String srcfile =null;
                    String objfile = null;

                    FileInputStream source = null;
                    FileOutputStream destination = null;

                    String buf = model.getContent();
                    Pattern p = Pattern.compile("(\"[^\",]*\\.(gif|jpg|jpeg|png|swf|js|css)\")|('[^',]*\\.(gif|jpg|jpeg|png|swf|css)')|\\([^)]*\\.(gif|jpg|jpeg|png|bmp)\\)",Pattern.CASE_INSENSITIVE);
                    java.util.regex.Matcher matcher = p.matcher(buf);
                    String matchStr=null;
                    String filename = null;
                    List<String> csslist = new ArrayList<String>();
                    List<String> imglist = new ArrayList<String>();
                    while (matcher.find()) {
                        matchStr = buf.substring(matcher.start() + 1, matcher.end() - 1);
                        System.out.println("matchStr===" + matchStr);
                        posi = matchStr.indexOf("/webbuilder/sites/" + samsiteinfo.getSITENAME().replace(".","_"));

                        if (posi > -1)
                            filename = matchStr.substring(posi + ("/webbuilder/sites/" + samsiteinfo.getSITENAME().replace(".","_")).length());
                        else
                            filename = matchStr;

                        int tposi = filename.lastIndexOf("/");
                        String dirName = filename.substring(0,tposi+1).replace("/",File.separator);

                        srcfile =  srcPath  + filename;
                        objfile = objPath + filename;

                        System.out.println("srcfile===" + srcfile);
                        System.out.println("objfile===" + objfile);


                        //CSS文件放入临时列表中，后面处理CSS文件中的图片文件
                        if (srcfile.toLowerCase().endsWith(".css")) csslist.add(srcfile);
                        if (srcfile.toLowerCase().endsWith(".jpg") || srcfile.toLowerCase().endsWith(".jpeg") || srcfile.toLowerCase().endsWith(".png") || srcfile.toLowerCase().endsWith(".bmp")) imglist.add(srcfile);

                        //创建目标文件的路径
                        posi = objfile.lastIndexOf("/");
                        if (posi > -1) {
                            File objfilepath = new File(objfile.substring(0,posi));
                            if (!objfilepath.exists()) objfilepath.mkdirs();
                        }

                        //源文件存在，则将源文件拷贝到创建站点的目录之下
                        File srcf = new File(srcfile);
                        if (srcf.exists()) {
                            //将CMS中共享网站的图片、CSS和JS文件写到生层的目标站点的目录
                            source = new FileInputStream(srcfile);
                            destination = new FileOutputStream(objfile);
                            byte[] buffer = new byte[1024];
                            int bytes_read;
                            while (true) {
                                bytes_read = source.read(buffer);
                                if (bytes_read == -1) break;
                                destination.write(buffer, 0, bytes_read);
                            }
                            source.close();
                            destination.close();

                            //发布文件到所有的WEB服务器
                            IPublishManager publishMgr = PublishPeer.getInstance();
                            int errcode = publishMgr.publish(user.getUSERID(), srcfile, siteinfo.getSITEID().intValue(), dirName, 0);
                        }
                    }

                    //获取所有CSS文件的背景图片，这些图片是在发布模板文件时未曾发布过多图片文件
                    List<String> cssImgeFileList = new ArrayList<String>();
                    for (int jj=0; jj<csslist.size(); jj++) {
                        String cssfile = (String)csslist.get(jj);
                        File file = new File(cssfile);
                        StringBuffer tbuf  = new StringBuffer();
                        if (file.exists()) {
                            BufferedReader reader = null;
                            reader = new BufferedReader(new FileReader(file));
                            String tempString = null;
                            while ((tempString = reader.readLine()) != null) {
                                tbuf.append(tempString);
                            }
                            reader.close();

                            p = Pattern.compile("\\([^)]*\\.(gif|jpg|jpeg|png|bmp)\\)",Pattern.CASE_INSENSITIVE);
                            matcher = p.matcher(tbuf.toString());
                            while (matcher.find()) {
                                matchStr = tbuf.toString().substring(matcher.start() + 1, matcher.end() - 1);

                                //判断图片文件是否已经被发不过
                                boolean ImageIspublished = false;
                                for (int ii=0; ii<imglist.size(); ii++) {
                                    String imgfile = (String)imglist.get(ii);
                                    if (imgfile.endsWith(matchStr)) {
                                        ImageIspublished = true;
                                        break;
                                    }
                                }

                                //判断图片文件在待发布队列中是否已经存在了
                                boolean ImageIsExisting = false;
                                for (int ii=0; ii<cssImgeFileList.size(); ii++) {
                                    String imgfile = (String)cssImgeFileList.get(ii);
                                    if (imgfile.endsWith(matchStr)) {
                                        ImageIsExisting = true;
                                        break;
                                    }
                                }

                                if (ImageIspublished == false && ImageIsExisting == false) {
                                    cssImgeFileList.add(matchStr);
                                }
                            }
                        }
                    }

                    //发布CSS文件中的所有图片文件
                    for(int jj=0; jj<cssImgeFileList.size(); jj++) {
                        String imgfile = (String)cssImgeFileList.get(jj);
                        posi = imgfile.lastIndexOf("/");
                        String dirName = imgfile.substring(0,posi+1);

                        srcfile = srcPath  + imgfile;
                        objfile = objPath + imgfile;

                        File srcf = new File(srcfile);
                        if (srcf.exists()) {
                            //将CMS中共享网站的图片、CSS和JS文件写到生层的目标站点的目录
                            source = new FileInputStream(srcfile);
                            destination = new FileOutputStream(objfile);
                            byte[] buffer = new byte[1024];
                            int bytes_read;
                            while (true) {
                                bytes_read = source.read(buffer);
                                if (bytes_read == -1) break;
                                destination.write(buffer, 0, bytes_read);
                            }
                            source.close();
                            destination.close();

                            //发布文件到所有的WEB服务器
                            IPublishManager publishMgr = PublishPeer.getInstance();
                            int errcode = publishMgr.publish(user.getUSERID(), srcfile, siteinfo.getSITEID().intValue(), dirName, 0);
                        }
                    }
                } else {
                    System.out.println("model is null");
                }
            } catch (ModelException exp) {
                exp.printStackTrace();
            } catch (FileNotFoundException fexp) {
                fexp.printStackTrace();
            } catch (IOException ioexp) {
                ioexp.printStackTrace();
            } catch (PublishException pexp) {
              pexp.printStackTrace();
            }
*/
            mm.add(str);
        } else {
            System.out.println("siteinfo is null");
        }

        return mm;
    }

    public List<CmsTemplate> getTemplates(int siteid) {
        List mm = null;
        if (applicationContext != null) {
            NjlUserService njlUserService = applicationContext.getBean(NjlUserService.class);
            mm = new ArrayList();
            mm = njlUserService.getTemplates(BigDecimal.valueOf(siteid));
        } else {
            System.out.println("applicationContext get failed" );
        }

        return mm;
    }

    public boolean checkUser(String username) {
        Users user = null;
        if (applicationContext != null) {
            NjlUserService njlUserService = applicationContext.getBean(NjlUserService.class);
            user = njlUserService.selectByPrimaryKey(username);
        } else {
            System.out.println("applicationContext get failed" );
        }

        if (user != null)
            return true;
        else
            return false;
    }

    public boolean checkEmail(String email) {
        Users user = null;
        if (applicationContext != null) {
            NjlUserService njlUserService = applicationContext.getBean(NjlUserService.class);
            user = njlUserService.selectByEmail(email);
        } else {
            System.out.println("applicationContext get failed" );
        }

        if (user != null)
            return true;
        else
            return false;
    }
}
