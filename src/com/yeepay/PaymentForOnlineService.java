package com.yeepay;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.yeepay.DigestUtil;
import com.yeepay.HttpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PaymentForOnlineService {
	
	private static Log log 					= LogFactory.getLog(PaymentForOnlineService.class);
	private static String p1_MerId 			= Configuration.getInstance().getValue("p1_MerId");
	private static String queryRefundReqURL = Configuration.getInstance().getValue("queryRefundReqURL");
    private static String queryURL = Configuration.getInstance().getValue("queryURL");
	private static String keyValue 			= Configuration.getInstance().getValue("keyValue");
	private static String query_Cmd  		= "QueryOrdDetail";
	private static String buy_Cmd 	 		= "Buy";
	private static String refund_Cmd 		= "RefundOrd";
	private static String decodeCharset 	= "GBK";
	private static String EMPTY = "";

	
	
	/**
	 * ???hmac????
	 * ???????
	 * @param p0_Cmd
	 * ??????
	 * @param p1_MerId
	 * ?????????
	 * @param p2_Order
	 * ??????
	 * @param p3_Amt
	 * ???????
	 * @param p4_Cur
	 * ??????
	 * @param p5_Pid
	 * ???????
	 * @param p6_Pcat
	 * ???????
	 * @param p7_Pdesc
	 * ???????????????????
	 * @param p8_Url
	 * ??????
	 * @param p9_SAF
	 * ?????????
	 * @param pa_MP
	 * ???б???
	 * @param pd_FrpId
	 * ??????
	 * @param pr_NeedResponse
	 * ??????
	 * @param keyValue
	 * @return
	 */
	public static String getReqMd5HmacForOnlinePayment(String p0_Cmd,String p1_MerId,
			String p2_Order, String p3_Amt, String p4_Cur,String p5_Pid, String p6_Pcat,
			String p7_Pdesc,String p8_Url, String p9_SAF,String pa_MP,String pd_FrpId,
			String pr_NeedResponse,String keyValue) {
		  StringBuffer sValue = new StringBuffer();
		// ???????
		sValue.append(p0_Cmd);
		// ??????
		sValue.append(p1_MerId);
		// ?????????
		sValue.append(p2_Order);
		// ??????
		sValue.append(p3_Amt);
		// ???????
		sValue.append(p4_Cur);
		// ??????
		sValue.append(p5_Pid);
		// ???????
		sValue.append(p6_Pcat);
		// ???????
		sValue.append(p7_Pdesc);
		// ???????????????????
		sValue.append(p8_Url);
		// ??????
		sValue.append(p9_SAF);
		// ?????????
		sValue.append(pa_MP);
		// ???б???
		sValue.append(pd_FrpId);
		// ??????
		sValue.append(pr_NeedResponse);
		
		String sNewString = null;

		sNewString = DigestUtil.hmacSign(sValue.toString(), keyValue);
		return (sNewString);
	}

    /**
     * formatString(String text) : 字符串格式化方法
     */
    public static String formatString(String text) {
        return (text == null ? "" : text.trim());
    }

    /**
     * verifyCallbackHmac_safe() : 验证回调安全签名数据是否有效
     * @throws UnsupportedEncodingException
     */
    public static boolean verifyCallbackHmac_safe(String[] stringValue, String hmac_safeFromYeepay) throws UnsupportedEncodingException {

        System.out.println("##### verifyCallbackHmac_safe() #####");

        //String keyValue			= getKeyValue();

        StringBuffer sourceData	= new StringBuffer();
        for(int i = 0; i < stringValue.length; i++) {
            if(!"".equals(stringValue[i])){
                sourceData.append(stringValue[i]+"#");
            }

            System.out.println("stringValue ～～～～: " + stringValue[i]);
        }

        sourceData = sourceData.deleteCharAt(sourceData.length()-1);
        System.out.println("sourceData ～～～～: " + sourceData.toString());

        String localHmac_safe		= DigestUtil.hmacSign(sourceData.toString(), keyValue);
        System.out.println("localHmac_safe:"+localHmac_safe);
        StringBuffer hmacSource	= new StringBuffer();
        for(int i = 0; i < stringValue.length; i++) {
            hmacSource.append(stringValue[i]);
        }

        return (localHmac_safe.equals(hmac_safeFromYeepay) ? true : false);
    }

    /**
     * @param p2_Order
     * @return queryResult
     */
    /**
     * queryByOrder() : 订单查询方法
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> queryByOrder (String p2_Order) throws UnsupportedEncodingException {
        System.out.println("##### queryByOrder() #####");
        String p0_Cmd			= "QueryOrdDetail";
        //String p1_MerId			= getP1_MerId();
        //String p1_MerId			= p1_MerId;
        //String p2_Order			= params.get("p2_Order");
        //String keyValue			= getKeyValue();
        //String keyValue			= keyValue;
        String pv_Ver			= "3.0";
        String p3_ServiceType	= "0";     //值为1时表示退款查询,其他任意数值表示交易订单查询。

        String[] strArr			= {p0_Cmd, p1_MerId, p2_Order, pv_Ver, p3_ServiceType};

        String hmac				= DigestUtil.getHmac(strArr, keyValue);
        String hmac_safe	    = DigestUtil.getHmac_safe(strArr, keyValue);

        Map<String, String> queryParams	= new HashMap<String, String>();
        queryParams.put("p0_Cmd", p0_Cmd);
        queryParams.put("p1_MerId", p1_MerId);
        queryParams.put("p2_Order", p2_Order);
        queryParams.put("pv_Ver", pv_Ver);
        queryParams.put("p3_ServiceType", p3_ServiceType);
        queryParams.put("hmac_safe", hmac_safe);
        queryParams.put("hmac", hmac);

        //String queryURL			= getQueryURL();

        System.out.println("queryParams : " + queryParams);
        System.out.println("queryURL : " + queryURL);

        Map<String, String> queryResult = new HashMap<String, String>();
        String r0_Cmd					= "";
        String r1_Code              	= "";
        String r2_TrxId             	= "";
        String r3_Amt               	= "";
        String r4_Cur               	= "";
        String r5_Pid               	= "";
        String r6_Order             	= "";
        String r8_MP                	= "";
        String rw_RefundRequestID   	= "";
        String rx_CreateTime        	= "";
        String ry_FinshTime        		= "";
        String rz_RefundAmount      	= "";
        String rb_PayStatus         	= "";
        String rc_RefundCount       	= "";
        String rd_RefundAmt         	= "";
        String hmacFromYeepay			= "";
        String hmac_safeFromYeepay		= "";
        String hmacError		   		= ""; //自定义，非接口返回。
        String errorMsg					= ""; //自定义，非接口返回。

        List responseList		= null;

        try {
            responseList		= HttpUtils.URLGet(queryURL, queryParams);
            System.out.println("responseList : " + responseList);
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(responseList == null) {
            errorMsg	= "No data returned!";
        } else {
            Iterator iter	= responseList.iterator();
            while(iter.hasNext()) {
                String temp = formatString((String)iter.next());
                if(temp.equals("")) {
                    continue;
                }
                int i = temp.indexOf("=");
                int j = temp.length();
                if(i >= 0) {
                    String tempKey		= temp.substring(0, i);
                    String tempValue	= URLDecoder.decode(temp.substring(i+1, j),"GBK");
                    if("r0_Cmd".equals(tempKey)) {
                        r0_Cmd		= tempValue;
                    } else if("r1_Code".equals(tempKey)) {
                        r1_Code		= tempValue;
                    } else if("r2_TrxId".equals(tempKey)) {
                        r2_TrxId	= tempValue;
                    } else if("r3_Amt".equals(tempKey)) {
                        r3_Amt		= tempValue;
                    } else if("r4_Cur".equals(tempKey)) {
                        r4_Cur		= tempValue;
                    } else if("r5_Pid".equals(tempKey)) {
                        r5_Pid		= tempValue;
                    } else if("r6_Order".equals(tempKey)) {
                        r6_Order	= tempValue;
                    } else if("r8_MP".equals(tempKey)) {
                        r8_MP		= tempValue;
                    } else if("rw_RefundRequestID".equals(tempKey)) {
                        rw_RefundRequestID	= tempValue;
                    } else if("rx_CreateTime".equals(tempKey)) {
                        rx_CreateTime		= tempValue;
                    } else if("ry_FinshTime".equals(tempKey)) {
                        ry_FinshTime		= tempValue;
                    } else if("rz_RefundAmount".equals(tempKey)) {
                        rz_RefundAmount		= tempValue;
                    } else if("rb_PayStatus".equals(tempKey)) {
                        rb_PayStatus		= tempValue;
                    } else if("rc_RefundCount".equals(tempKey)) {
                        rc_RefundCount		= tempValue;
                    } else if("rd_RefundAmt".equals(tempKey)) {
                        rd_RefundAmt		= tempValue;
                    } else if("hmac".equals(tempKey)) {
                        hmacFromYeepay		= tempValue;
                    } else if("hmac_safe".equals(tempKey)){
                        hmac_safeFromYeepay	= tempValue;
                    }
                }
            }

            String[] stringArr	= {r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r8_MP,
                    rw_RefundRequestID, rx_CreateTime, ry_FinshTime, rz_RefundAmount, rb_PayStatus,
                    rc_RefundCount, rd_RefundAmt};
            String localHmac	= DigestUtil.getHmac(stringArr, keyValue);
            boolean ishmac_safe = verifyCallbackHmac_safe(stringArr, hmac_safeFromYeepay);

            if(!localHmac.equals(hmacFromYeepay) || !ishmac_safe) {
//				hmacError	= "Hmac 不匹配！ hmacFromYeepay : "
//							+ hmacFromYeepay
//							+ "; localHmac : "
//							+ localHmac;
                StringBuffer temp = new StringBuffer();
                for(int i = 0; i < stringArr.length; i++) {
                    temp.append(stringArr[i]);
                }
            }
        }

        queryResult.put("r0_Cmd", r0_Cmd);
        queryResult.put("r1_Code", r1_Code);
        queryResult.put("r2_TrxId", r2_TrxId);
        queryResult.put("r3_Amt", r3_Amt);
        queryResult.put("r4_Cur", r4_Cur);
        queryResult.put("r5_Pid", r5_Pid);
        queryResult.put("r6_Order", r6_Order);
        queryResult.put("r8_MP", r8_MP);
        queryResult.put("rw_RefundRequestID", rw_RefundRequestID);
        queryResult.put("rx_CreateTime", rx_CreateTime);
        queryResult.put("ry_FinshTime", ry_FinshTime);
        queryResult.put("rz_RefundAmount", rz_RefundAmount);
        queryResult.put("rb_PayStatus", rb_PayStatus);
        queryResult.put("rc_RefundCount", rc_RefundCount);
        queryResult.put("rd_RefundAmt", rd_RefundAmt);
        queryResult.put("hamcError", hmacError);
        queryResult.put("errorMsg", errorMsg);

        System.out.println("queryResult : " + queryResult);

        return(queryResult);
    }

    /*public static QueryResult queryByOrder(String p2_Order) {
        String[] strArr = new String[] {query_Cmd,p1_MerId,p2_Order,"3.0","2"};
		QueryResult qr = null;
        String hmac = DigestUtil.getHmac(strArr,keyValue);
		Map reParams = new HashMap();
		reParams.put("p0_Cmd", query_Cmd);
		reParams.put("p1_MerId", p1_MerId);
		reParams.put("p2_Order", p2_Order);
        reParams.put("pv_Ver","3.0");
        reParams.put("p3_ServiceType","2");
		reParams.put("hmac", hmac);
		List responseStr = null; 

        System.out.println("query_Cmd==" + query_Cmd);
        System.out.println("p1_MerId==" + p1_MerId);
        System.out.println("p2_Order==" + p2_Order);
        System.out.println("queryRefundReqURL==" + queryURL);

		try {
			log.debug("Begin http communications.data[" + reParams + "]");
			responseStr = HttpUtils.URLGet(queryURL, reParams);
			log.debug("End http communications.responseStr.data[" + responseStr + "]");
		} catch (Exception e) {
			e.printStackTrace();
		}

        System.out.println("resp msg size==" + responseStr.size());

		if (responseStr.size() == 0) {
			throw new RuntimeException("No response.");
		}
		qr = new QueryResult();
		for (int t = 0; t < responseStr.size(); t++) {
			String currentResult = (String) responseStr.get(t);
			if (currentResult == null || currentResult.equals("")) {
				continue;
			}
			int i = currentResult.indexOf("=");
			int j = currentResult.length();
			if (i >= 0) {
				String sKey = currentResult.substring(0, i);
				String sValue = currentResult.substring(i + 1);
				try {
					sValue = URLDecoder.decode(sValue, decodeCharset);
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e.getMessage());
				}
				if (sKey.equals("r0_Cmd")) {
					qr.setR0_Cmd(sValue);
				} else if (sKey.equals("r1_Code")) {
					qr.setR1_Code(sValue);
				} else if (sKey.equals("r2_TrxId")) {
					qr.setR2_TrxId(sValue);
				} else if (sKey.equals("r3_Amt")) {
					qr.setR3_Amt(sValue);
				} else if (sKey.equals("r4_Cur")) {
					qr.setR4_Cur(sValue);
				} else if (sKey.equals("r5_Pid")) {
					qr.setR5_Pid(sValue);
				} else if (sKey.equals("r6_Order")) {
					qr.setR6_Order(sValue);
				} else if (sKey.equals("r8_MP")) {
					qr.setR8_MP(sValue);
				} else if (sKey.equals("rb_PayStatus")) {
					qr.setRb_PayStatus(sValue);
				} else if (sKey.equals("rc_RefundCount")) {
					qr.setRc_RefundCount(sValue);
				} else if (sKey.equals("rd_RefundAmt")) {
					qr.setRd_RefundAmt(sValue);
				} else if (sKey.equals("hmac")) {
					qr.setHmac(sValue);
				}
			}
		}
		if (!qr.getR1_Code().equals("1")) {
			throw new RuntimeException("Query fail.Error code:" + qr.getR1_Code());
		}
		String newHmac = "";
		newHmac = DigestUtil.getHmac(new String[] { qr.getR0_Cmd(), qr.getR1_Code(), qr.getR2_TrxId(),
				  qr.getR3_Amt(), qr.getR4_Cur(), qr.getR5_Pid(), qr.getR6_Order(), qr.getR8_MP(),
				  qr.getRb_PayStatus(), qr.getRc_RefundCount(),qr.getRd_RefundAmt()}, keyValue);
		if (!newHmac.equals(qr.getHmac())) {
			throw new RuntimeException("Hmac error.");
		}
		return (qr);
		
	}*/
	
	public static RefundResult refundByTrxId(String pb_TrxId,String p3_Amt,String p4_Cur,String p5_Desc) {
		RefundResult rr = null;
		String hmac = DigestUtil.getHmac(new String[] {refund_Cmd,p1_MerId,pb_TrxId,p3_Amt,p4_Cur,p5_Desc},keyValue);
		Map reParams = new HashMap();
		reParams.put("p0_Cmd", refund_Cmd);
		reParams.put("p1_MerId", p1_MerId);
		reParams.put("pb_TrxId", pb_TrxId);
		reParams.put("p3_Amt", p3_Amt);
		reParams.put("p4_Cur", p4_Cur);
		reParams.put("p5_Desc", p5_Desc);
		reParams.put("hmac", hmac);
		List responseStr = null; 
		try {
			log.debug("Begin http communications.data[" + reParams + "]");
			responseStr = HttpUtils.URLGet(queryRefundReqURL, reParams);
			log.debug("End http communications.responseStr.data[" + responseStr + "]");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		if (responseStr.size() == 0) {
			throw new RuntimeException("No response.");
		}
		
		
		rr = new RefundResult();
		for (int t = 0; t < responseStr.size(); t++) {
			String currentResult = (String) responseStr.get(t);
			if (currentResult == null || currentResult.equals("")) {
				continue;
			}
			try {
				URLDecoder.decode(currentResult, decodeCharset);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getMessage());
			}
			int i = currentResult.indexOf("=");
			int j = currentResult.length();
			if (i >= 0) {
				String sKey = currentResult.substring(0, i);
				String sValue = currentResult.substring(i + 1);
				if (sKey.equals("r0_Cmd")) {
					rr.setR0_Cmd(sValue);
				} else if (sKey.equals("r1_Code")) {
					rr.setR1_Code(sValue);
				} else if (sKey.equals("r2_TrxId")) {
					rr.setR2_TrxId(sValue);
				} else if (sKey.equals("r3_Amt")) {
					rr.setR3_Amt(sValue);
				} else if (sKey.equals("r4_Cur")) {
					rr.setR4_Cur(sValue);
				} else if (sKey.equals("hmac")) {
					rr.setHmac(sValue);
				}	
			}
		}
		if (!rr.getR1_Code().equals("1")) {
			throw new RuntimeException("Query fail.Error code:" + rr.getR1_Code());
		}
		String newHmac = "";
		newHmac = DigestUtil.getHmac(new String[] { rr.getR0_Cmd(), rr.getR1_Code(), rr.getR2_TrxId(),
				  rr.getR3_Amt(), rr.getR4_Cur()}, keyValue);
		if (!newHmac.equals(rr.getHmac())) {
			throw new RuntimeException("Hmac error.");
		}
		return (rr);
	}
	
	/**
	 * ????У??hmac????
	 * 
	 * @param hmac
	 * ??????
	 * @param p1_MerId
	 * ???????
	 * @param r0_Cmd
	 * ??????
	 * @param r1_Code
	 * ???????????????
	 * @param r2_TrxId
	 * ??????
	 * @param r3_Amt
	 * ???????
	 * @param r4_Cur
	 * ??????
	 * @param r5_Pid
	 * ?????????
	 * @param r6_Order
	 * ?????????ID
	 * @param r7_Uid
	 * ?????????
	 * @param r8_MP
	 * ???????????
	 * @param r9_BType
	 * ???????????
	 * @param keyValue
	 * @return
	 */
	public static boolean verifyCallback(String hmac, String p1_MerId,
			String r0_Cmd, String r1_Code, String r2_TrxId, String r3_Amt,
			String r4_Cur, String r5_Pid, String r6_Order, String r7_Uid,
			String r8_MP, String r9_BType, String keyValue) {
		StringBuffer sValue = new StringBuffer();
		// ??????
		sValue.append(p1_MerId);
		// ???????
		sValue.append(r0_Cmd);
		// ??????
		sValue.append(r1_Code);
		// ???????????????
		sValue.append(r2_TrxId);
		// ??????
		sValue.append(r3_Amt);
		// ???????
		sValue.append(r4_Cur);
		// ??????
		sValue.append(r5_Pid);
		// ?????????
		sValue.append(r6_Order);
		// ?????????ID
		sValue.append(r7_Uid);
		// ?????????
		sValue.append(r8_MP);
		// ???????????
		sValue.append(r9_BType);
		String sNewString = null;
		sNewString = DigestUtil.hmacSign(sValue.toString(), keyValue);

		if (hmac.equals(sNewString)) {
			return (true);
		}
		return (false);
	}
	
}