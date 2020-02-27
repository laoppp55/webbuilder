package com.bizwink.cms;

import com.bizwink.po.Companyinfo;
import org.apache.lucene.document.*;

import java.sql.Date;

public class DBDocument {
    public DBDocument() {

    }

    //文档
    public static Document Document(Companyinfo companyinfo,String content,String zh_province,String zh_city,String zh_zone,String zh_town,String zh_village){
        Document doc = new Document();
        try {
            doc.add(new IntField("id", companyinfo.getID().intValue(), IntField.Store.YES));

            doc.add(new IntField("siteid", companyinfo.getSITEID().intValue(), IntField.Store.YES));

            doc.add(new IntField("classid", companyinfo.getSAMSITEID().intValue(), IntField.Store.YES));

            if (companyinfo.getUPDATEDATE()!=null)
                doc.add(new LongField("sortid", companyinfo.getUPDATEDATE().getTime(), IntField.Store.YES));
            else
                doc.add(new LongField("sortid", 0, IntField.Store.YES));

            if (companyinfo.getCOMPANYNAME()!=null)
                doc.add(new TextField("companyname", companyinfo.getCOMPANYNAME(), TextField.Store.YES));
            else
                doc.add(new TextField("companyname", "", TextField.Store.YES));

            if (companyinfo.getCOMPANYADDRESS()!=null)
                doc.add(new TextField("companyaddress", companyinfo.getCOMPANYADDRESS(), TextField.Store.YES));
            else
                doc.add(new TextField("companyaddress", "", TextField.Store.YES));

            if (companyinfo.getSUMMARY()!=null)
                doc.add(new TextField("summary", companyinfo.getSUMMARY(), TextField.Store.YES));
            else
                doc.add(new TextField("summary", "", TextField.Store.YES));

            if (companyinfo.getPROVINCE()!=null)
                doc.add(new StringField("province",companyinfo.getPROVINCE(),StringField.Store.YES));
            else
                doc.add(new StringField("province","",StringField.Store.YES));

            if (zh_province!=null)
                doc.add(new StringField("zhprovince",zh_province,StringField.Store.YES));
            else
                doc.add(new StringField("zhprovince","",StringField.Store.YES));

            if (companyinfo.getCITY()!=null)
                doc.add(new StringField("city",companyinfo.getCITY(),StringField.Store.YES));
            else
                doc.add(new StringField("city","",StringField.Store.YES));

            if (zh_city!=null)
                doc.add(new StringField("zhcity",zh_city,StringField.Store.YES));
            else
                doc.add(new StringField("zhcity","",StringField.Store.YES));

            if (companyinfo.getZONE()!=null)
                doc.add(new StringField("zone",companyinfo.getZONE(),StringField.Store.YES));
            else
                doc.add(new StringField("zone","",StringField.Store.YES));

            if (zh_zone!=null)
                doc.add(new StringField("zhzone",zh_zone,StringField.Store.YES));
            else
                doc.add(new StringField("zhzone","",StringField.Store.YES));

            if (companyinfo.getTOWN()!=null)
                doc.add(new StringField("town",companyinfo.getTOWN(),StringField.Store.YES));
            else
                doc.add(new StringField("town","",StringField.Store.YES));

            if (zh_town!=null)
                doc.add(new StringField("zhtown",zh_town,StringField.Store.YES));
            else
                doc.add(new StringField("zhtown","",StringField.Store.YES));

            if (companyinfo.getVILLAGE()!=null)
                doc.add(new StringField("village",companyinfo.getVILLAGE(),StringField.Store.YES));
            else
                doc.add(new StringField("village","",StringField.Store.YES));

            if (zh_village!=null)
                doc.add(new StringField("zhvillage",zh_village,StringField.Store.YES));
            else
                doc.add(new StringField("zhvillage","",StringField.Store.YES));

            if (companyinfo.getCOMPANYLATITUDE()!=null)
                doc.add(new DoubleField("latitude",companyinfo.getCOMPANYLATITUDE().doubleValue(),DoubleField.Store.YES));
            else
                doc.add(new DoubleField("latitude",0.0,DoubleField.Store.YES));

            if (companyinfo.getCOMPANYLONGITUDE()!=null)
                doc.add(new DoubleField("longtiude",companyinfo.getCOMPANYLONGITUDE().doubleValue(),DoubleField.Store.YES));
            else
                doc.add(new DoubleField("longtiude",0.0,DoubleField.Store.YES));

            if (companyinfo.getMPHONE()!=null)
                doc.add(new StringField("mphone",companyinfo.getMPHONE(),StringField.Store.YES));
            else
                doc.add(new StringField("mphone","",StringField.Store.YES));

            if (companyinfo.getCOMPANYEMAIL()!=null)
                doc.add(new StringField("email",companyinfo.getCOMPANYEMAIL(),StringField.Store.YES));
            else
                doc.add(new StringField("email","",StringField.Store.YES));

            if (companyinfo.getCOMPANYPIC()!=null)
                doc.add(new StringField("pic",companyinfo.getCOMPANYPIC(),StringField.Store.YES));
            else
                doc.add(new StringField("pic","",StringField.Store.YES));

            if (companyinfo.getCREATEDATE()!=null)
                doc.add(new StringField("createdate",companyinfo.getCREATEDATE().toString(),StringField.Store.YES));
            else
                doc.add(new StringField("createdate","",StringField.Store.YES));

            if (companyinfo.getUPDATEDATE()!=null)
                doc.add(new StringField("updatedate",companyinfo.getUPDATEDATE().toString(),StringField.Store.YES));
            else
                doc.add(new StringField("updatedate","",StringField.Store.YES));

            if (content!=null)
                doc.add(new TextField("content",content,TextField.Store.YES));
            else
                doc.add(new TextField("content",content,TextField.Store.YES));
        }catch(Exception e){
            e.printStackTrace();
        }

        return doc;
    }
}
