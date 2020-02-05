package com.bizwink.cms.publish;

import com.bizwink.cms.tree.*;
import com.bizwink.cms.modelManager.*;
import com.bizwink.cms.news.IColumnManager;
import com.bizwink.cms.news.Column;
import com.bizwink.cms.news.ColumnPeer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PublisTemplateFunc {
    private static PublisTemplateFunc singleton = new PublisTemplateFunc();

    public static synchronized PublisTemplateFunc getInstance() {
        return singleton;
    }

    public String getTemplate(int templateID) {
        String errmsg = "noerror";
        IModelManager modelMgr = ModelPeer.getInstance();

        try {
            Model model = modelMgr.getModel(templateID);
            String templateBuf = model.getContent();

            templateBuf = templateBuf.replaceAll("&lt;", "<");
            templateBuf = templateBuf.replaceAll("&gt;", ">");
            templateBuf = templateBuf.replaceAll("&amp;", "&");
            templateBuf = templateBuf.replaceAll("&apos;", "'");
            templateBuf = templateBuf.replaceAll("&quot;", "\"");
            return templateBuf;
        }
        catch (ModelException e) {
            System.out.println(e.getMessage());
        }

        return errmsg;
    }

    //获得默认模板ID
    //获得共享摸版        wangjian 2009
    public int getDefaultTemplateID(int siteID, int columnID, int type,int samsiteid) {
        int templateID = 0;
        if (columnID < 1) {
            return 0;
        }
        IModelManager modelManager = ModelPeer.getInstance();
        PublishCommFunc pubCommon = PublishCommFunc.getInstance();

        Tree modelTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteID,samsiteid);
        node[] treeNodes = modelTree.getAllNodes();
        int nodenum;
        int parentColumnID;
        int ModelNum;
        int columnid=columnID;
        try {
            //寻找当前栏目的文章模板，如果当前栏目不存在文章模板，判断其父栏目是否存在文章模板，循环查找，直到找到存在文章模板的父栏目                                 *
            switch (type) {
                case 0:                      //栏目模板
                {
                    ModelNum = modelManager.getIndexModelCount(columnID, type);
                    if (ModelNum > 0)
                        break;
                    else
                        return 0;
                }
                case 1:                      //文章模板
                {
                    while (columnID != 0) {
                        ModelNum = modelManager.getContentModelCount(columnID, type);
                        if (ModelNum > 0) break;
                        nodenum = pubCommon.findNodeInTree(treeNodes, columnID);
                        parentColumnID = treeNodes[nodenum].getLinkPointer();
                        columnID = parentColumnID;
                    }
                    break;
                }
                case 2:                      //首页模板
                {
                    //  ModelNum = modelManager.getHomeModelCount(columnID);
                    break;
                }
                case 5:                     //WAP文章模板
                {
                    while (columnID != 0) {
                        ModelNum = modelManager.getContentModelCount(columnID, type);
                        if (ModelNum > 0) break;
                        nodenum = pubCommon.findNodeInTree(treeNodes, columnID);
                        parentColumnID = treeNodes[nodenum].getLinkPointer();
                        columnID = parentColumnID;
                    }
                    break;
                }
                case 4:                         //WAP栏目模板
                {
                    ModelNum = modelManager.getIndexModelCount(columnID, type);
                    if (ModelNum > 0)
                        break;
                    else
                        return 0;
                }
            }

            //获取默认模板
            Model model = modelManager.getDefaultModel(columnID, type);

            //默认模板为空时，找最新模板      wangjian 2009
            if (model == null){
                model = modelManager.getCurrentModel(columnID, type);
                if(model==null)
                {
                    IColumnManager columnMgr= ColumnPeer.getInstance();
                    Column column=null;
                    try{
                        column=columnMgr.getRootparentColumn(samsiteid);
                    }
                    catch(Exception e){
                        System.out.println(""+e.toString());
                    }
                    columnID=column.getID();
                    model = modelManager.getCurrentModel(columnID, type);
                }
            }
            if (model != null) {
                templateID = model.getID();
            }
        }
        catch (ModelException e) {
            e.printStackTrace();
            return -1;
        }
        return templateID;
    }


    //获得默认模板ID
    public int getDefaultTemplateID(int siteID, int columnID, int type) {
        int templateID = 0;
        if (columnID < 1) {
            return 0;
        }

        IModelManager modelManager = ModelPeer.getInstance();
        PublishCommFunc pubCommon = PublishCommFunc.getInstance();

        Tree modelTree = TreeManager.getInstance().getSiteTree(siteID);
        node[] treeNodes = modelTree.getAllNodes();
        int nodenum;
        int parentColumnID;
        int ModelNum;

        try {
            //寻找当前栏目的文章模板，如果当前栏目不存在文章模板，判断其父栏目是否存在文章模板，循环查找，直到找到存在文章模板的父栏目                                 *
            switch (type) {
                case 0:                      //栏目模板
                {
                    ModelNum = modelManager.getIndexModelCount(columnID, type);
                    if (ModelNum > 0)
                        break;
                    else
                        return 0;
                }
                case 1:                      //文章模板
                {
                    while (columnID != 0) {
                        ModelNum = modelManager.getContentModelCount(columnID, type);
                        if (ModelNum > 0) break;
                        nodenum = pubCommon.findNodeInTree(treeNodes, columnID);
                        parentColumnID = treeNodes[nodenum].getLinkPointer();
                        columnID = parentColumnID;
                    }
                    break;
                }
                case 2:                      //首页模板
                {
                    //ModelNum = modelManager.getHomeModelCount(columnID);
                    break;
                }
                case 5:                     //WAP文章模板
                {
                    while (columnID != 0) {
                        ModelNum = modelManager.getContentModelCount(columnID, type);
                        if (ModelNum > 0) break;
                        nodenum = pubCommon.findNodeInTree(treeNodes, columnID);
                        parentColumnID = treeNodes[nodenum].getLinkPointer();
                        columnID = parentColumnID;
                    }
                    break;
                }
                case 4:                         //WAP栏目模板
                {
                    ModelNum = modelManager.getIndexModelCount(columnID, type);
                    if (ModelNum > 0)
                        break;
                    else
                        return 0;
                }
            }

            //获取默认模板
            Model model = modelManager.getDefaultModel(columnID, type);

            //默认模板为空时，找最新模板
            if (model == null)
                model = modelManager.getCurrentModel(columnID, type);

            if (model != null) {
                templateID = model.getID();
            }
        }
        catch (ModelException e) {
            e.printStackTrace();
            return -1;
        }
        return templateID;
    }

    public String[] TemplatePaser(String template) {
        String tempBuf = template;

        Pattern p = Pattern.compile("<input[^<>]*\"\\[TAG\\][^<>&?]*\\[/TAG\\]\"[^<>]*>",Pattern.CASE_INSENSITIVE);
        //Pattern p = Pattern.compile("<input[^>]*\\s+type=\"button\"[^>]*>",Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(tempBuf);
        String[] tbuf = p.split(tempBuf);    //存储BUTTON类型TAG分割的代码片段

        //获取BUTTON类型的TAG
        List<String> list1 = new ArrayList<String>();
        while (matcher.find()) {
            String matchStr = tempBuf.substring(matcher.start(), matcher.end());
            list1.add(matchStr);
        }

        List<TagPosition> tagPositionList = new ArrayList<TagPosition>();
        //获取BUTTON类型的标记在模板中的位置，并把标记的内容和位置保存在标记内容和位置列表中
        tempBuf = template;
        int posi = 0;           //当前标记在前一个标记后面子串中的位置
        int total_posi = 0;     //记录标记在整个字符串中的位置
        for(int ii=0;ii<list1.size();ii++) {
            String tag_content = list1.get(ii);
            posi = tempBuf.indexOf(tag_content);

            //标记在整个字符串中所处的开始位置
            total_posi = total_posi + posi;

            //记录该标记在整个字符串中的开始位置和标记的内容
            TagPosition tagPosition = new TagPosition();
            tagPosition.setTagContent(tag_content);
            tagPosition.setPosi(total_posi);
            tagPositionList.add(tagPosition);

            //标记在整个字符串中的结束位置
            total_posi = total_posi + tag_content.length();

            //获取标记后面的子字符串
            posi = posi + tag_content.length();
            tempBuf = tempBuf.substring(posi);
        }

        //获取非BUTTON类型的标记
        List<String> list2 = new ArrayList<String>();
        p = Pattern.compile("\\[TAG\\][^<>&?]*\\[/TAG\\]",Pattern.CASE_INSENSITIVE);
        for(int ii=0; ii<tbuf.length; ii++){
            tempBuf = tbuf[ii];
            matcher = p.matcher(tempBuf);
            while (matcher.find()) {
                String matchStr = tempBuf.substring(matcher.start(), matcher.end());
                list2.add(matchStr);
            }
        }

        //获取非BUTTON类型的标记在模板中的位置，并把标记的内容和位置保存在标记内容和位置列表中
        tempBuf = template;
        posi = 0;           //当前标记在前一个标记后面子串中的位置
        total_posi = 0;     //记录标记在整个字符串中的位置
        for(int ii=0;ii<list2.size();ii++) {
            String tag_content = list2.get(ii);
            posi = tempBuf.indexOf(tag_content);

            //标记在整个字符串中所处的开始位置
            total_posi = total_posi + posi;

            //记录该标记在整个字符串中的开始位置和标记的内容
            TagPosition tagPosition = new TagPosition();
            tagPosition.setTagContent(tag_content);
            tagPosition.setPosi(total_posi);
            tagPositionList.add(tagPosition);

            //标记在整个字符串中的结束位置
            total_posi = total_posi + tag_content.length();

            //获取标记后面的子字符串
            posi = posi + tag_content.length();
            tempBuf = tempBuf.substring(posi);
        }

        //对tagPositionList列表按在模板中出现的位置进行排序
        if (tagPositionList.size()>1) {
            for (int ii = 0; ii < tagPositionList.size(); ii++) {
                TagPosition tagPos = tagPositionList.get(ii);
                for (int jj = ii + 1; jj < tagPositionList.size(); jj++) {
                    TagPosition tagPos1 = tagPositionList.get(jj);
                    //如果后面数据项的posi小于前面posi，则将两个数据项互换
                    if (tagPos.getPosi() > tagPos1.getPosi()) {
                        TagPosition t_tagPos = tagPos;
                        tagPositionList.set(ii, tagPos1);
                        tagPositionList.set(jj, t_tagPos);
                        tagPos = tagPos1;
                    }
                }
            }
        }

       // for(int ii=0;ii<tagPositionList.size();ii++) {
       //     TagPosition tagPos = tagPositionList.get(ii);
       //     System.out.println(tagPos.getTagContent() + "==" + tagPos.getPosi());
        //}

        //将模板用TAG的内容分割成不同的段落，段落数为TAG的数量乘以2加1
        String[] buf = new String[tagPositionList.size()*2 + 1];
        if (tagPositionList.size() > 0) {
            int startPosi=0;
            int endPosi = 0;
            int i = 0;
            tempBuf = template;
            for (i = 0; i<tagPositionList.size(); i++) {
                TagPosition tagPos = tagPositionList.get(i);
                String tag = tagPos.getTagContent();
                startPosi = tempBuf.indexOf(tag);
                String leftBuf = tempBuf.substring(0, startPosi).trim();
                String rightBuf = tempBuf.substring(startPosi + tag.length());
                buf[i * 2] = leftBuf;
                if (tag.startsWith("<input")) {                                    //BUTTON类型的标记
                    startPosi = tag.indexOf("[TAG]");
                    endPosi = tag.indexOf("[/TAG]");
                    buf[i * 2 + 1] = tag.substring(startPosi,endPosi+"[/TAG]".length());
                } else {                                                           //非BUTTON类型的标记
                    buf[i * 2 + 1] = tag;
                }
                tempBuf = rightBuf;
            }
            buf[i * 2] = tempBuf;
        } else {                       //在模板上不存在任何标记，模板内容之家返回
            buf[tagPositionList.size()] = template;
        }

        return buf;
    }
}