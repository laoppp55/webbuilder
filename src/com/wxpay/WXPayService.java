package com.wxpay;

import com.bizwink.util.MD5Util;
import com.bizwink.util.MapXMLUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Administrator on 18-9-23.
 */
public class WXPayService {
    private static Logger logger = LoggerFactory.getLogger(WXPayService.class);
    private WXPay wxpay;
    private WXPayServiceConfig config;
    private static WXPayService INSTANCE;

    private WXPayService() throws Exception {
        config = WXPayServiceConfig.getInstance();
        wxpay = new WXPay(config);
    }

    public static WXPayService getInstance() throws Exception {
        if (INSTANCE == null) {
            synchronized (WXPayServiceConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayService();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 微信下單接口
     *
     * @param out_trade_no
     * @param body
     * @param money
     * @param applyNo
     * @return
     */
    public String doUnifiedOrder(String out_trade_no, String body, Double money, String applyNo) {
        String code_url = null;
        String certFile = "/usr/local/cert/apiclient_cert.p12";
        try {
            WXPayServiceConfig config = new WXPayServiceConfig(certFile);
            WXPay wxpay = new WXPay(config,config.getNotifUrl(),true,false);

            String amt = String.valueOf(money * 100);
            HashMap<String, String> data = new HashMap<String, String>();
            //data.put("appid",config.getAppID());
            //data.put("mch_id",config.getMchID());
            data.put("body", body);
            data.put("out_trade_no", out_trade_no);
            data.put("device_info", "WEB");
            data.put("fee_type", "CNY");
            data.put("total_fee", amt.substring(0, amt.lastIndexOf(".")));
            data.put("spbill_create_ip", config.getSpbillCreateIp());
            data.put("notify_url", config.getNotifUrl());
            data.put("trade_type", config.getTradeType());
            data.put("product_id", body);
            data.put("attach","订阅报纸");
            data.put("nonce_str",WXPayUtil.generateNonceStr());
            //String sign = WXPayUtil.generateSignature(data, config.getKey(), WXPayConstants.SignType.MD5);
            //data.put("sign",sign);
            // data.put("time_expire", "20170112104120");

            //System.out.println("money==" + String.valueOf(money * 100));
            //System.out.println("xml==" + WXPayUtil.mapToXml(data));

            Map<String, String> r = wxpay.unifiedOrder(data);
            code_url = r.get("code_url");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code_url;
    }

    /**
     * 订单交易查询
     *
     * @param out_trade_no
     * 商户订单号
     */
    public Map<String, String> tradeQuery(String out_trade_no) {
        Map<String, String> map = null;
        try {
            WXPayServiceConfig config = new WXPayServiceConfig();
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", out_trade_no);

            map = new HashMap<String, String>();
            map = wxpay.orderQuery(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map == null) {
            map = new HashMap<String, String>();
        }

        return map;
    }

    /**
     * 关闭订单
     *
     * @param out_trade_no
     * 商户订单号
     */
    public Map<String, String> close(String out_trade_no) {
        Map<String, String> map = null;
        try {
            WXPayServiceConfig config = new WXPayServiceConfig();
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", out_trade_no);

            map = new HashMap<String, String>();
            map = wxpay.closeOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map == null) {
            map = new HashMap<String, String>();
        }
        return map;
    }


    /**
     * 退款 已测试
     */
    public void doRefund(String out_trade_no, String total_fee) {
        try {
            WXPayServiceConfig config = new WXPayServiceConfig();
            WXPay wxpay = new WXPay(config);
            //logger.info("退款时的订单号为：" + out_trade_no + "退款时的金额为:" + total_fee);
            String amt = String.valueOf(Double.parseDouble(total_fee) * 100);
            //logger.info("修正后的金额为：" + amt);
            //logger.info("最终的金额为：" + amt.substring(0, amt.lastIndexOf(".")));
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", out_trade_no);
            data.put("out_refund_no", out_trade_no);
            data.put("total_fee", amt.substring(0, amt.lastIndexOf(".")));
            data.put("refund_fee", amt.substring(0, amt.lastIndexOf(".")));
            data.put("refund_fee_type", "CNY");
            data.put("op_user_id", config.getMchID());

            Map<String, String> r = wxpay.refund(data);
            //logger.info("退款操作返回的参数为" + r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 申请退款
     *
     * @param out_trade_no
     *            商户订单号，与微信订单号二选一设置，若两者都传，则以微信订单号为准
     * @param transaction_id
     *            微信订单号，与商户订单号二选一设置，若两者都传，则以微信订单号为准
     * @param out_refund_no
     *            商户退款单号
     * @param total_fee
     *            订单总金额，单位为分，只能为整数
     * @param refund_fee
     *            退款总金额，单位为分，只能为整数
     * @param refund_desc
     *            退款原因
     */
    public Map<String, String> refund(String out_trade_no, String transaction_id, String out_refund_no,
                                      String total_fee, String refund_fee, String refund_desc) {
        Map<String, String> map = null;
        try {
            WXPayServiceConfig config = new WXPayServiceConfig();
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            if (transaction_id != null && !"".equals(transaction_id)) {
                data.put("transaction_id", transaction_id);
            } else {
                data.put("out_trade_no", out_trade_no);
            }
            data.put("out_refund_no", out_refund_no);
            data.put("total_fee", total_fee);
            data.put("refund_fee", refund_fee);
            data.put("refund_desc", refund_desc);
            data.put("notify_url", config.getRefundNotifyUrl());

            map = new HashMap<String, String>();
            map = wxpay.refund(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map == null) {
            map = new HashMap<String, String>();
        }
        return map;
    }

    /**
     * 退款查询
     *
     * @param out_trade_no
     * 商户订单号
     */
    public Map<String, String> refundQuery(String out_trade_no) {
        Map<String, String> map = null;
        try {
            WXPayServiceConfig config = new WXPayServiceConfig();
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", out_trade_no);

            map = new HashMap<String, String>();
            map = wxpay.refundQuery(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map == null) {
            map = new HashMap<String, String>();
        }
        return map;
    }

    /**
     * 微信验签接口
     *
     * @param:out_trade_no
     * @param:body
     * @param:money
     * @param:applyNo
     * @return
     * @throws DocumentException
     */
    public boolean checkSign(String  strXML) throws DocumentException {
        WXPayServiceConfig config = null;
        SortedMap<String, String> smap = null;
        try {
            config = new WXPayServiceConfig();
            smap = new TreeMap<String, String>();
            Document doc = DocumentHelper.parseText(strXML);
            Element root = doc.getRootElement();
            for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
                Element e = (Element) iterator.next();
                smap.put(e.getName(), e.getText());
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        if (smap!=null && config!=null)
            return isWechatSign(smap,config.getKey());
        else
            return false;
    }


    private boolean isWechatSign(SortedMap<String, String> smap,String apiKey) {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> es = smap.entrySet();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry =  it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + apiKey);
        /** 验证的签名 */
        String sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
        /** 微信端返回的合法签名 */
        String validSign = ((String) smap.get("sign")).toUpperCase();
        return validSign.equals(sign);
    }

    public static void main(String[] args) throws Exception {
        WXPayServiceConfig config = new WXPayServiceConfig();
        WXPay wxpay = new WXPay(config);

        //8180920221548
        System.out.println("查询微信订单信息");
        WXPayService wxPayService = WXPayService.getInstance();
        Map<String, String> qr = wxPayService.tradeQuery("8180920221548");
        System.out.println(qr.get("out_trade_no"));
        System.out.println(qr.get("body"));
        System.out.println(qr.get("fee_type"));
        System.out.println(qr.get("product_id"));
        System.out.println(qr.get("return_code"));
        System.out.println(qr.get("result_code"));
        System.out.println(qr.get("transaction_id"));
        System.out.println(qr.get("cash_fee"));
        System.out.println(qr.get("total_fee"));
        System.out.println(qr.get("attach"));
        System.out.println(qr.get("time_end"));
        System.out.println(qr.get("trade_state_desc"));


        /*Map<String, String> data = new HashMap<String, String>();
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", "2016090910595900000012");
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
