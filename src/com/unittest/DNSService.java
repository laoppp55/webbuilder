package com.unittest;

import com.bizwink.cms.multimedia.Multimedia;
import com.bizwink.persistence.UsersMapper;
import com.bizwink.po.Article;
import com.bizwink.po.Users;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: perter.song
 * Date: 16-3-18
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class DNSService {
    /**
     * * @param serverAddr DNS地址
     * * @param timeOut 连接超时时间
     * * @param type 查询类型
     * * @param address 查询地址
     * * @return
     * */
    public static List<String> search(String serverAddr,int timeOut,String type, String address) {
        InitialDirContext context = null;
        List<String> resultList = new ArrayList<String>();
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put("java.naming.factory.initial",                     "com.sun.jndi.dns.DnsContextFactory");
            env.put("java.naming.provider.url", "dns://" + serverAddr + "/");
            env.put("com.sun.jndi.ldap.read.timeout", String.valueOf(timeOut));
            context = new InitialDirContext(env);
            String dns_attrs[] = { type };
            Attributes attrs = context.getAttributes(address, dns_attrs);
            Attribute attr = attrs.get(type);
            if (attr != null) {
                for (int i = 0; i < attr.size(); i++) {
                    resultList.add((String) attr.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(context!=null){
                try {
                    context.close();
                } catch (NamingException e) {
                }
            }
        }
        return resultList;
    }
    /**
     *  * @param args
     *  */
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx=null;
        ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        DataSource dataSource =(DataSource)ctx.getBean("myDataSource");
        Connection conn = (Connection) dataSource.getConnection();

        Article article = new Article();
        Multimedia mult = new Multimedia();
        PreparedStatement pstmt = conn.prepareStatement("insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename," +
                "encodeflag,infotype,createdate,id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");



    }
}
