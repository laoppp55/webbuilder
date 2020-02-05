package com.alipay.util;

import java.util.*;


public class Payment {
	
	
	  

	public static String CreateUrl(String paygateway,String service,String sign_type,String show_url,String payment_type,String partner,String key,String body,String notify_url,String out_trade_no,String total_fee,String return_url,String seller_email,String subject)
      {   
	
	 
		  String[] Oristr ={ "service="+service, "partner=" + partner, "subject=" + subject, "body=" + body, "out_trade_no=" + out_trade_no, "total_fee=" + total_fee, "show_url=" + show_url, "payment_type=" + payment_type, "seller_email=" + seller_email,"return_url=" + return_url, "notify_url=" + notify_url };
		  				 	      
	       Arrays.sort(Oristr);
	      
	         String prestr="";
	         
	         for (int i = 0; i < Oristr.length; i++)
	            {
	                if (i==Oristr.length-1)
	                {
	                    prestr = prestr + Oristr[i] ;
	                }
	                else
	                {
	                    prestr = prestr + Oristr[i] + "&";
	                }
	                 
	            }

	         	prestr = prestr + key;

	            //生成Md5摘要；
	            String sign = com.alipay.util.Md5Encrypt.md5(prestr);
	            
	            //构造支付Url；
	            String parameter = "";
	            parameter = parameter + paygateway;
	            for (int i = 0; i < Oristr.length; i++)
	            {
	                parameter = parameter + Oristr[i] + "&";               
	            }

	            parameter = parameter + "sign=" + sign + "&sign_type=" + sign_type;

	            //返回支付Url；
	            return parameter;

    
	
      }
}
