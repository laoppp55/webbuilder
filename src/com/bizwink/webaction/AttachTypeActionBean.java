package com.bizwink.webaction;

import com.bizwink.po.AttachType;
import com.bizwink.service.AttachTypeService;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-21
 * Time: 下午7:40
 * To change this template use File | Settings | File Templates.
 */

@SessionScope
public class AttachTypeActionBean  extends AbstractActionBean{
    @SpringBean
    private transient AttachTypeService attachTypeService;

    private String cname;
    private String summary;
    private String editor;
    private List partList = new ArrayList();
    private int rownum;
    private int siteid;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public List getPartList() {
        return partList;
    }

    public void setPartList(List partList) {
        this.partList = partList;
    }

    @DefaultHandler
    public ForwardResolution createAttacType() {
        AttachType attachType = new AttachType();
        attachType.setSummary(summary);
        attachType.setCname(cname);
        attachType.setCreatedate(new Timestamp(System.currentTimeMillis()));
        attachType.setLastupdated(new Timestamp(System.currentTimeMillis()));
        attachType.setEditor(editor);
        attachType.setSiteid(siteid);

        System.out.println("rownum=" + rownum);

        AttachType attachTypePart = null;
        List list = new ArrayList();
        for(int i=1; i<rownum+1; i++) {
            attachTypePart = new AttachType();
            String clchname = context.getRequest().getParameter("chname" + i);
            String clenname = context.getRequest().getParameter("enname" + i);
            String cltype = context.getRequest().getParameter("classtype" + i);
            attachTypePart.setCname(clchname);
            attachTypePart.setEname(clenname);
            attachTypePart.setCltype(cltype);
            attachTypePart.setCreatedate(new Timestamp(System.currentTimeMillis()));
            attachTypePart.setLastupdated(new Timestamp(System.currentTimeMillis()));
            attachTypePart.setEditor(editor);
            attachTypePart.setSiteid(siteid);
            list.add(attachTypePart);
        }

        int retcode = attachTypeService.createAttachType(attachType,list);

        return new ForwardResolution("/member/closewin.jsp?from=attach");
    }
}