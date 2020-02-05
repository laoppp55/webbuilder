package com.bizwink.move;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.publish.*;
import com.bizwink.cms.util.*;
import com.bizwink.move.*;
import com.bizwink.upload.*;
import com.bizwink.cms.sitesetting.*;

public class MoveOuterColumn extends HttpServlet
{
    public void run(Move move)
    {
        IColumnManager columnMgr = ColumnPeer.getInstance();
        IPublishManager publishMgr = PublishPeer.getInstance();
        IMoveManager moveMgr = MovePeer.getInstance();
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();

        try
        {
            //获得要移动的栏目的siteid,sitename,dirname
            int orgColumnID = move.getOrgColumnID();
            Column column = columnMgr.getColumn(orgColumnID);
            //String orgDirName = column.getDirName();
            int orgSiteID = column.getSiteID();
            String orgSiteName = siteMgr.getSiteInfo(orgSiteID).getDomainName();
            String _orgSiteName = StringUtil.replace(orgSiteName, ".", "_");
            move.setSiteName(orgSiteName);

            //获得目标栏目的siteid,sitename,dirname
            int aimColumnID = move.getAimColumnID();
            column = columnMgr.getColumn(aimColumnID);
            String aimDirName = column.getDirName();
            int aimSiteID = column.getSiteID();
            String aimSiteName = siteMgr.getSiteInfo(aimSiteID).getDomainName();
            String _aimSiteName = StringUtil.replace(aimSiteName, ".", "_");
            move.setSiteID(aimSiteID);
            move.setDirName(aimDirName.substring(0,aimDirName.length()-1));

            List articleList = moveMgr.getArticles(orgColumnID, move.getMoveType());
            move.setArticleList(articleList);

            if (move.getIsMovePic() == 1)
            {
                //移动标题、副标题、来源、作者等字段的图片
                String appPath = move.getAppPath();
                List imgList = getImageList(articleList);

                for (int j=0; j<imgList.size(); j++)
                {
                    String tempstr = (String)imgList.get(j);
                    String filename = tempstr.substring(0, tempstr.indexOf(","));
                    String dirName = tempstr.substring(tempstr.indexOf(",") + 1);

                    String orgImgPath = appPath + "sites" + File.separator + _orgSiteName +
                            StringUtil.replace(dirName, "/", File.separator) + "images" + File.separator + filename;

                    File orgImgFile = new File(orgImgPath);
                    if (orgImgFile.exists())
                    {
                        String newDirName = dirName.substring(0,dirName.length() - 1) + aimDirName;
                        String aimImgPath = appPath + "sites" + File.separator + _aimSiteName +
                                StringUtil.replace(newDirName, "/", File.separator) + "images" + File.separator;

                        File aimImgFile = new File(aimImgPath);
                        if (!aimImgFile.exists())
                        {
                            aimImgFile.mkdirs();
                        }

                        FileDeal.copy(orgImgPath, aimImgPath + filename, 0);
                        publishMgr.publish(move.getUserName(), aimImgPath + filename, aimSiteID, newDirName + "images/", 0);
                    }
                }
            }

            moveMgr.Moving(move);
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }

    private List getImageList(List articleList)
    {
        List imgList = new ArrayList();

        for (int j=0; j<articleList.size(); j++)
        {
            Article article = (Article)articleList.get(j);

            String mainTitle = article.getMainTitle();
            String viceTitle = article.getViceTitle();
            String author = article.getAuthor();
            String source = article.getSource();
            String dirName = article.getDirName();

            if (mainTitle != null && mainTitle.length() > 0)
            {
                if (mainTitle.toLowerCase().indexOf(".gif") != -1 || mainTitle.toLowerCase().indexOf(".jpg") != -1 ||
                        mainTitle.toLowerCase().indexOf(".jpeg") != -1 || mainTitle.toLowerCase().indexOf(".png") != -1)
                    imgList.add(mainTitle.trim() + "," + dirName);
            }
            if (viceTitle != null && viceTitle.length() > 0)
            {
                if (viceTitle.toLowerCase().indexOf(".gif") != -1 || viceTitle.toLowerCase().indexOf(".jpg") != -1 ||
                        viceTitle.toLowerCase().indexOf(".jpeg") != -1 || viceTitle.toLowerCase().indexOf(".png") != -1)
                    imgList.add(viceTitle.trim() + "," + dirName);
            }
            if (author != null && author.length() > 0)
            {
                if (author.toLowerCase().indexOf(".gif") > -1 || author.toLowerCase().indexOf(".jpg") > -1 ||
                        author.toLowerCase().indexOf(".jpeg") > -1 || author.toLowerCase().indexOf(".png") > -1)
                    imgList.add(author.trim() + "," + dirName);
            }
            if (source != null && source.length() > 0)
            {
                if (source.toLowerCase().indexOf(".gif") > -1 || source.toLowerCase().indexOf(".jpg") > -1 ||
                        source.toLowerCase().indexOf(".jpeg") > -1 || source.toLowerCase().indexOf(".png") > -1)
                    imgList.add(source.trim() + "," + dirName);
            }
        }

        return imgList;
    }
}


