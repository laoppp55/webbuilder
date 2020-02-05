package com.bizwink.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMail
{
    private MimeMessage mimeMsg;  //MIME邮件对象

    private Session session;      //邮件会话对象
    private Properties props;     //系统属性
    private boolean needAuth = true;  //smtp是否需要认证

    //smtp认证用户名和密码
    private String username = "sale@1080000.com";
    private String password = "yjcsxdl";

    //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
    private Multipart mp;

    public SendMail()
    {
        //如果没有指定邮件服务器,就从getConfig类中获取
        //setSmtpHost(getConfig.mailHost);
        //createMimeMessage();
    }

    public SendMail(String smtp)
    {
        setSmtpHost(smtp);
        createMimeMessage();
    }

    public void setSmtpHost(String hostName)
    {
        System.out.println("设置系统属性：mail.smtp.host = "+ hostName);
        if (props == null) props = System.getProperties();  //获得系统属性对象
        props.put("mail.smtp.host", hostName);               //设置SMTP主机
    }

    public boolean createMimeMessage()
    {
        try
        {
            System.out.println("准备获取邮件会话对象！");
            session = Session.getDefaultInstance(props,null);   //获得邮件会话对象
        }
        catch(Exception e)
        {
            System.err.println("获取邮件会话对象时发生错误！"+e);
            return false;
        }

        System.out.println("准备创建MIME邮件对象！");
        try
        {
            mimeMsg = new MimeMessage(session);  //创建MIME邮件对象
            mp = new MimeMultipart();

            return true;
        }
        catch(Exception e)
        {
            System.err.println("创建MIME邮件对象失败！"+e);
            return false;
        }
    }

    public void setNeedAuth(boolean need)
    {
        System.out.println("设置smtp身份认证：mail.smtp.auth = " + need);
        if(props == null)props = System.getProperties();
        if(need)
        {
            props.put("mail.smtp.auth","true");
        }
        else
        {
            props.put("mail.smtp.auth","false");
        }
    }

    public void setNamePass(String name,String pass)
    {
        username = name;
        password = pass;
    }

    public boolean setSubject(String mailSubject)
    {
        System.out.println("设置邮件主题！");
        try
        {
            mimeMsg.setSubject(mailSubject);
            return true;
        }
        catch(Exception e)
        {
            System.err.println("设置邮件主题发生错误！");
            return false;
        }
    }

    public boolean setBody(String mailBody)
    {
        try
        {
            BodyPart bp = new MimeBodyPart();
            //bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>"+mailBody,"text/html;charset=GB2312");
            bp.setContent(mailBody,"text/html;charset=GB2312");
            mp.addBodyPart(bp);

            return true;
        }
        catch(Exception e)
        {
            System.err.println("设置邮件正文时发生错误！"+e);
            return false;
        }
    }

    public boolean addFileAffix(String filename)
    {
        System.out.println("增加邮件附件："+filename);

        try
        {
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filename);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(fileds.getName());

            mp.addBodyPart(bp);

            return true;
        }
        catch(Exception e)
        {
            System.err.println("增加邮件附件："+filename+"发生错误！"+e);
            return false;
        }
    }

    public boolean setFrom(String from)
    {
        System.out.println("设置发信人！");

        try
        {
            mimeMsg.setFrom(new InternetAddress(from));             //设置发信人
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean setTo(String to)
    {
        if(to == null)return false;

        try
        {
            mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean setCopyTo(String copyto)
    {
        if(copyto == null)return false;

        try
        {
            mimeMsg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyto));
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean sendout()
    {
        try
        {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            System.out.println("正在发送邮件....");
            Session mailSession = Session.getInstance(props,null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String)props.get("mail.smtp.host"),username,password);
            transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));
            //transport.send(mimeMsg);
            System.out.println("发送邮件成功！");
            transport.close();
            return true;
        }
        catch(Exception e)
        {
            System.err.println("邮件发送失败！" + e);
            return false;
        }
    }

     public static void main(String[] args)
    {
        String mailbody = "<meta http-equiv=Content-Type content=text/html;charset=gb2312>" +
                "<div align=center><a href=http://www.bizwink.com.cn>" +
                "北京盈商动力软件有限公司</a></div>";

        //SendMail themail = new SendMail("smtp.163.net");
        SendMail themail = new SendMail("116.90.87.233");
        //themail.setNeedAuth(true);
        themail.setNeedAuth(false);

        if(themail.setSubject("标题") == false) return;
        if(themail.setBody(mailbody) == false) return;
        if(themail.setTo("petersong@bizwink.com.cn") == false) return;
        if(themail.setFrom("laoppp@yahoo.cn") == false) return;
        if(themail.addFileAffix("/data/a/index.shtml") == false) return;
        //themail.setNamePass("bet","7zhang8");

        if(themail.sendout() == false) return;
    }

 /*   public static void main(String[] args) throws ParseException{

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTime=new Date();
            //将截取到的时间字符串转化为时间格式的字符串
            Date endTime=sdf.parse("2012-05-18 00:00:00");
            //默认为毫秒，除以1000是为了转换成秒
            long interval=(endTime.getTime() - currentTime.getTime())/1000;//秒
            long day=interval/(24*3600);//天
            long hour=interval%(24*3600)/3600;//小时
            long minute=interval%3600/60;//分钟
            long second=interval%60;//秒
            System.out.println("两个时间相差："+day+"天"+hour+"小时"+minute+"分"+second+"秒");
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }*/
}