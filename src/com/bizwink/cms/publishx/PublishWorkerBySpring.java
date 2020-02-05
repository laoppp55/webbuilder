package com.bizwink.cms.publishx;

import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.cms.sitesetting.ISiteInfoManager;
import com.bizwink.cms.sitesetting.SiteInfo;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.publishQueue.IPublishQueueManager;
import com.bizwink.publishQueue.PublishQueuePeer;

import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * Created by petersong on 15-11-4.
 */
public class PublishWorkerBySpring  extends Thread{
    job job = null;
    String realpath = null;
    String username = null;

    public PublishWorkerBySpring() {

    }

    public PublishWorkerBySpring(String username, String realpath) {
        this.username = username;
        this.realpath = realpath;
    }


    public void run() {
        int count = 0;
        PublishServerManager.done.workerBegin();
        for (; ;) {
            job = PublishServerManager.getWorkload();
            if (job != null) {
                count = count + 1;
                processWorkload(count);
            } else {
                break;
            }
        }
        PublishServerManager.done.workerEnd();
    }

    private void processWorkload(int count) {
        int errcode = 0;
        IPublishManager publishManager = PublishPeer.getInstance();
        IPublishQueueManager pqManager = PublishQueuePeer.getInstance();
        ISiteInfoManager siteManager = SiteInfoPeer.getInstance();

        int columnid = job.getColumnID();
        int siteid = job.getSiteID();
        int samsiteid = 0;
        String sitename = null;
        SiteInfo siteinfo = null;
        int imgflag = 0;
        int big5flag = 0;
        try {
            siteinfo = siteManager.getSiteInfo(siteid);
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        if (siteinfo != null) {
            sitename = siteinfo.getDomainName();
            samsiteid = siteinfo.getSamsiteid();
            int sitetype = siteinfo.getSitetype();
            sitename = StringUtil.replace(sitename, ".", "_");
            imgflag = siteinfo.getImgeDir();
            big5flag = siteinfo.getTcFlag();
            realpath = StringUtil.replace(realpath, "\r\n", "");

            //根据作业的类型调用不同的发布程序
            try {
                switch (job.getType()) {
                    case 1:
                        Timestamp curtime = new Timestamp(System.currentTimeMillis());
                        if (job.getReferArticleFlag() == 0)  {
                            errcode = publishManager.CreateArticlePage(job.getTargetID(),job.getColumnID(), siteid,sitetype,samsiteid, realpath, sitename, username, imgflag, big5flag,true,0) ;
                        } else {
                            errcode = publishManager.CreateArticlePage(job.getTargetID(),job.getColumnID(), siteid,sitetype,samsiteid, realpath, sitename, username, imgflag, big5flag,false,0) ;
                        }
                        break;
                    case 3:
                    case 0:
                        errcode = publishManager.CreateColPage(job.getColumnID(), siteid,sitetype,samsiteid, realpath, sitename, username, imgflag, big5flag, job.getTargetID());
                        break;
                    case 2:
                        errcode = publishManager.createHomePage(siteid,sitetype,samsiteid, realpath, sitename, username, imgflag, big5flag, job.getTargetID());
                        break;
                }

                if(job.getType() > 10)
                    errcode = publishManager.CreateProgramPage(job.getColumnID(),siteid,sitetype,samsiteid,realpath,sitename,username,imgflag,big5flag, job.getTargetID());

                if (errcode == 0) {                                //发布内容正常结束或者被发布的内容在数据库中已经不存在
                    pqManager.removeJob(job.getID(),job.getType(),job.getTargetID());
                    System.out.println(this.getName() + "(" + count + ")发布作业===" + job.getMaintitle() + "====所属栏目（"+job.getColumnID() +"--"+job.getTargetID()+"）（成功）");
                } else {
                    if (errcode == -2) {
                        System.out.println(this.getName() + "(" + count + ")发布作业===" + job.getMaintitle() + "====所属栏目（"+job.getColumnID() +"--"+job.getTargetID()+"）（需要设置文章模板）");
                    } else if (errcode == -3){
                        System.out.println(this.getName() + "(" + count + ")发布作业===" + job.getMaintitle() + "====所属栏目（"+job.getColumnID() +"--"+job.getTargetID()+"）（栏目已经被删除）");
                    } else {
                        System.out.println(this.getName() + "(" + count + ")发布作业===" + job.getMaintitle() + "====所属栏目（"+job.getColumnID() +"--"+job.getTargetID()+"）（发布出错）");
                    }
                    pqManager.setupJobErrorMsg(job.getID(),job.getType(),errcode);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                publishManager = null;
                pqManager = null;
                siteManager = null;
            }
        }
    }
}
