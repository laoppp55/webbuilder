package com.bizwink.cms.business.Order;


import com.bizwink.cms.util.StringUtil;
import com.wxpay.WXPayService;
import com.yeepay.PaymentForOnlineService;
import jxl.CellFormat;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExportOrderToExcel {

    public ExportOrderToExcel(){

    }

    public static String ExportOrders(List<Order> list,String path) throws IOException, WriteException {
        //创建excel文件
        //String path = request.getRealPath("");
        IOrderManager orderMgr = orderPeer.getInstance();
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFm.format(date);
        String xlsname = "order"+time+".xls";
        String filepath = path+"xls" + File.separator + "order";
        File dir = new File(filepath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File f = new File(filepath,xlsname);
        //在当前目录下建立文件
        if (f.exists()) {
            f.delete();
        } else {
            f.createNewFile();
        }
        String absolutePath = f.getAbsolutePath();
        System.out.println(absolutePath);
        //创建一个可写入的excel文件对象
        WritableWorkbook workbook = Workbook.createWorkbook(f);
        //使用第一张工作表，将其命名为“member”
        // WritableSheet sheet = workbook.createSheet("dd", 0);
        int totle = list.size();//获取List集合的size
        int mus = 60000;//每个工作表格最多存储2条数据（注：excel表格一个工作表可以存储65536条）
        int avg = totle / mus;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00");
        DecimalFormat df2 = new DecimalFormat();
        df2.applyPattern("0");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatDateAndTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String phone = "";
        String province = "";
        String city = "";
        String Orderflag="";
        String PayWay = "";
        String sendWay="";
        String orderStatus="新订单";
        try {
            for (int i = 0; i < avg + 1; i++) {
                WritableSheet sheet = workbook.createSheet("列表"+(i + 1), i); //创建一个可写入的工作表
                WritableCellFormat wcfF = new WritableCellFormat(NumberFormats.INTEGER);
                CellView cv0 = new CellView();
                cv0.setSize(20);
                sheet.setColumnView(0,200);
                cv0.setSize(40);
                sheet.setColumnView(1,200);

                WritableCellFormat wcfF1 = new WritableCellFormat();
                CellView cv1 = new CellView();
                cv1.setSize(20);
                sheet.setColumnView(2, 200);
                sheet.setColumnView(3,200);
                cv1.setSize(60);
                sheet.setColumnView(4,200);

                //表头
                Label label0 = new Label(0, 0, "订单号");
                sheet.addCell(label0);
                Label label1 = new Label(1, 0, "用户ID");
                sheet.addCell(label1);
                Label label2 = new Label(2, 0, "订单类型");
                sheet.addCell(label2);
                Label label3 = new Label(3, 0, "收件人姓名");
                sheet.addCell(label3);
                Label label4 = new Label(4, 0, "所在省市");
                sheet.addCell(label4);
                Label label5 = new Label(5, 0, "收件人地址");
                sheet.addCell(label5);
                Label label6 = new Label(6, 0, "邮编");
                sheet.addCell(label6);
                Label label7 = new Label(7, 0, "收件人电话");
                sheet.addCell(label7);
                Label label8 = new Label(8, 0, "付款方式");
                sheet.addCell(label8);
                Label label9 = new Label(9, 0, "取货方式");
                sheet.addCell(label9);
                Label label10 = new Label(10, 0, "订单状态");
                sheet.addCell(label10);
                Label label11 = new Label(11, 0, "创建时间");
                sheet.addCell(label11);
                Label label12 = new Label(12, 0, "报纸名称");
                sheet.addCell(label12);
                Label label13 = new Label(13, 0, "订阅份数");
                sheet.addCell(label13);
                Label label14 = new Label(14, 0, "订单金额");
                sheet.addCell(label14);
                Label label15 = new Label(15, 0, "下单时间");
                sheet.addCell(label15);
                Label label16 = new Label(16, 0, "订阅方式");
                sheet.addCell(label16);
                Label label17 = new Label(17, 0, "起始日期");
                sheet.addCell(label17);
                Label label18 = new Label(18, 0, "截止日期");
                sheet.addCell(label18);
                Label label19 = new Label(19, 0, "收报人姓名(单位)");
                sheet.addCell(label19);
                Label label20 = new Label(20, 0, "座机");
                sheet.addCell(label20);
                Label label21 = new Label(21, 0, "手机");
                sheet.addCell(label21);
                Label label22 = new Label(22, 0, "地址");
                sheet.addCell(label22);
                Label label23 = new Label(23, 0, "邮编");
                sheet.addCell(label23);
                Label label24 = new Label(24, 0, "备注");
                sheet.addCell(label24);
                Label label25 = new Label(25, 0, "发票");
                sheet.addCell(label25);
                Label label26 = new Label(26, 0, "发票类型");
                sheet.addCell(label26);
                Label label27 = new Label(27, 0, "发票单位");
                sheet.addCell(label27);
                Label label28 = new Label(28, 0, "税号");
                sheet.addCell(label28);
                Label label29 = new Label(29, 0, "地址");
                sheet.addCell(label29);
                Label label30 = new Label(30, 0, "联系电话");
                sheet.addCell(label30);
                Label label31 = new Label(31, 0, "开户银行");
                sheet.addCell(label31);
                Label label32 = new Label(32, 0, "银行账号");
                sheet.addCell(label32);
                Label label33 = new Label(33, 0, "发票内容");
                sheet.addCell(label33);
                Label label34 = new Label(34, 0, "电子邮件");
                sheet.addCell(label34);



                int num = i * mus;
                int index = 0;
                for (int m = num; m < list.size(); m++) {
                    if (index == mus) {//判断index == mus的时候跳出当前for循环
                        break;
                    }
                    Order order=(Order)list.get(m);
                    Orderflag="普通订单";
                    if(order.getOrderFlag() !=0) Orderflag = "竞标订单";
                    province = order.getProvince()==null?"--": StringUtil.gb2iso4View(order.getProvince());
                    city = order.getCity()==null?"--":StringUtil.gb2iso4View(order.getCity());
                    if ((order.getPhone() != null) && (!"".equals(order.getPhone()))) {
                        phone = order.getPhone();
                        phone = StringUtil.gb2iso4View(phone);
                    }
                    if(order.getPayWay() == 0){
                        PayWay="微信支付";
                    }else if(order.getPayWay() == 1){
                        PayWay="支付宝";
                    }else if(order.getPayWay() == 2){
                        PayWay="银行支付";
                    }else{
                        PayWay="其他";
                    }
                    Fee fee = new Fee();
                    fee = orderMgr.getAFeeInfo(order.getSendWay());
                    if (fee == null){
                        sendWay="同城送货";
                    }else{
                        if(fee.getCname() == null){

                        }else{
                            sendWay=StringUtil.gb2iso4View(fee.getCname());
                        }
                    }

                    if (order.getNouse() == 0) {
                        orderStatus="客户取消";
                    }else{
                        if (order.getStatus() == 1){
                            orderStatus="处理中";
                        }
                        if (order.getStatus() == 2){
                            orderStatus="发货";
                        }
                        if (order.getStatus() == 3){
                            orderStatus="退货";
                        }
                        if (order.getStatus() == 4){
                            orderStatus="完成";
                        }
                        if (order.getStatus() == 5){
                            orderStatus="拒收";
                        }
                        if (order.getStatus() == 6){
                            orderStatus="缺货";
                        }
                        if (order.getStatus() == 7){
                            orderStatus="等待客户付款";
                        }
                        if (order.getStatus() == 8){
                            orderStatus="已付款";
                        }if (order.getStatus() == 9){
                            orderStatus="用户超时未付款";
                        }
                    }

                    AddressInfo addressInfo = orderMgr.getAAddresInfoForOrder(order.getOrderid());
                    Invoice invoice = orderMgr.getInvoiceInfoForOrder(order.getOrderid());
                    String Nees_invoice="是";
                    if(order.getNees_invoice() == 0){
                        Nees_invoice = "否";
                    }
                    String Neestype="未知发票类型";
                    if(order.getNees_invoice()==1){
                        Neestype ="电子专项发票";
                    }
                    if(order.getNees_invoice()==1){
                        Neestype ="电子普通发票";
                    }

                    Label orderID = new Label(0, index+1, String.valueOf(order.getOrderid()));
                    sheet.addCell(orderID);
                    Label userid = new Label(1, index+1, order.getName() == null ? "" : StringUtil.gb2iso4View(order.getName()));
                    sheet.addCell(userid);
                    Label orderflag = new Label(2, index+1, Orderflag);
                    sheet.addCell(orderflag);
                    Label username = new Label(3, index+1, order.getName() == null ? "" : StringUtil.gb2iso4View(order.getName()));
                    sheet.addCell(username);
                    Label provincecity = new Label(4, index+1, province+""+city);
                    sheet.addCell(provincecity);
                    Label Address = new Label(5, index+1, order.getAddress() == null ? "" : StringUtil.gb2iso4View(order.getAddress()));
                    sheet.addCell(Address);
                    Label Postcode = new Label(6, index+1, order.getPostcode() == null ? "" : StringUtil.gb2iso4View(order.getPostcode()));
                    sheet.addCell(Postcode);
                    Label phones = new Label(7, index+1, phone);
                    sheet.addCell(phones);
                    Label PayWays = new Label(8, index+1, PayWay);
                    sheet.addCell(PayWays);
                    Label sendWays = new Label(9, index+1, sendWay);
                    sheet.addCell(sendWays);
                    Label orderStatuss = new Label(10, index+1, orderStatus);
                    sheet.addCell(orderStatuss);
                    Label CreateDate = new Label(11, index+1, order.getCreateDate().toString().substring(0,19));
                    sheet.addCell(CreateDate);
                    Label Productname = new Label(12, index+1, order.getProductname());
                    sheet.addCell(Productname);
                    Label bznum = new Label(13, index+1, String.valueOf(order.getOrderNum()));
                    sheet.addCell(bznum);
                    Label payFee = new Label(14, index+1, String.valueOf(order.getPayfee()));
                    sheet.addCell(payFee);
                    Label createdate = new Label(15, index+1, formatDateAndTime.format(order.getCreateDate()));
                    sheet.addCell(createdate);
                    Label subscribe_type = null;
                    if (order.getSubscribe() == 1)
                        subscribe_type = new Label(16, index+1, "年订");
                    else if (order.getSubscribe() == 2)
                        subscribe_type = new Label(16, index+1, "半年订");
                    else if (order.getSubscribe() == 3)
                        subscribe_type = new Label(16, index+1, "季度订");
                    else if (order.getSubscribe() == 4)
                        subscribe_type = new Label(16, index+1, "月订");
                    else
                        subscribe_type = new Label(16, index+1, "");
                    sheet.addCell(subscribe_type);
                    Label beginDate = new Label(17,index+1,format.format(order.getServicestarttime()));
                    sheet.addCell(beginDate);
                    Label endDate = new Label(18,index+1,format.format(order.getServiceendtime()));
                    sheet.addCell(endDate);
                    Label addressInfoname = new Label(19, index+1, (addressInfo!=null)?addressInfo.getName():"");
                    sheet.addCell(addressInfoname);
                    Label addressInfophone = new Label(20, index+1, (addressInfo!=null)?(addressInfo.getPhone()==null)?"":addressInfo.getPhone():"");
                    sheet.addCell(addressInfophone);
                    Label addressInfoMobile = new Label(21, index+1, (addressInfo!=null)?(addressInfo.getMobile()==null)?"":addressInfo.getMobile():"");
                    sheet.addCell(addressInfoMobile);
                    Label addressInfoAddress = new Label(22, index+1, (addressInfo!=null)?addressInfo.getAddress():"");
                    sheet.addCell(addressInfoAddress);
                    Label addressInfozip = new Label(23, index+1, (addressInfo!=null)?addressInfo.getZip():"");
                    sheet.addCell(addressInfozip);
                    Label addressInfonote = new Label(24, index+1, (addressInfo!=null)?(addressInfo.getNotes()==null)?"":addressInfo.getNotes():"");
                    sheet.addCell(addressInfonote);
                    Label Nees_invoices = new Label(25, index+1, Nees_invoice);
                    sheet.addCell(Nees_invoices);
                    Label Neestypes = new Label(26, index+1, Neestype);
                    sheet.addCell(Neestypes);
                    Label invoiceCompanyname = new Label(27, index+1,(invoice!=null)?(invoice.getCompanyname()==null?"":StringUtil.gb2iso4View(invoice.getCompanyname())):"");
                    sheet.addCell(invoiceCompanyname);
                    Label Identification = new Label(28, index+1, (invoice!=null)?invoice.getIdentification():"");
                    sheet.addCell(Identification);
                    Label Registeraddress = new Label(29, index+1, (invoice!=null)?((invoice.getRegisteraddress()==null)?"":invoice.getRegisteraddress()):"");
                    sheet.addCell(Registeraddress);
                    Label invoicePhone = new Label(30, index+1, (invoice!=null)?((invoice.getPhone()==null)?"":invoice.getPhone()):"");
                    sheet.addCell(invoicePhone);
                    Label invoiceBankname = new Label(31, index+1, (invoice!=null)?(invoice.getBankname()):"");
                    sheet.addCell(invoiceBankname);
                    Label invoiceBankaccount = new Label(32, index+1, (invoice!=null)?(invoice.getBankaccount()):"");
                    sheet.addCell(invoiceBankaccount);
                    Label invoiceContentinfo = new Label(33, index+1, (invoice!=null)?(invoice.getContentinfo()):"");
                    sheet.addCell(invoiceContentinfo);
                    Label invoiceEmail = new Label(34, index+1, (invoice!=null)?((invoice.getEmail()==null)?"":invoice.getEmail()):"");
                    sheet.addCell(invoiceEmail);
                    index++;
                }
            }

            //关闭对象，释放资源
            workbook.write();
            workbook.close();
        }catch (Exception e) {
            workbook.close();
            e.printStackTrace();
        }
        return "/xls/order/"+xlsname;
    }

    public static String ExportOrdersForFangzheng(List<Order> list,String path) throws IOException, WriteException {
        //创建excel文件
        //String path = request.getRealPath("");
        IOrderManager orderMgr = orderPeer.getInstance();
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFm.format(date);
        String xlsname = "order"+time+".xls";
        String filepath = path+"xls" + File.separator + "order";
        File dir = new File(filepath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File f = new File(filepath,xlsname);
        //在当前目录下建立文件
        if (f.exists()) {
            f.delete();
        } else {
            f.createNewFile();
        }
        String absolutePath = f.getAbsolutePath();
        System.out.println(absolutePath);
        //创建一个可写入的excel文件对象
        WritableWorkbook workbook = Workbook.createWorkbook(f);
        //使用第一张工作表，将其命名为“member”
        // WritableSheet sheet = workbook.createSheet("dd", 0);
        int totle = list.size();//获取List集合的size
        int mus = 60000;//每个工作表格最多存储2条数据（注：excel表格一个工作表可以存储65536条）
        int avg = totle / mus;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00");
        DecimalFormat df2 = new DecimalFormat();
        df2.applyPattern("0");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        try {
            for (int i = 0; i < avg + 1; i++) {
                WritableSheet sheet = workbook.createSheet("列表"+(i + 1), i); //创建一个可写入的工作表
                //表头
                Label label0 = new Label(0, 0, "行号");
                sheet.addCell(label0);
                Label label1 = new Label(1, 0, "订单号");
                sheet.addCell(label1);
                Label label2 = new Label(2, 0, "订单类型");
                sheet.addCell(label2);
                Label label3 = new Label(3, 0, "收订员");
                sheet.addCell(label3);
                Label label4 = new Label(4, 0, "订户");
                sheet.addCell(label4);
                Label label5 = new Label(5, 0, "地址");
                sheet.addCell(label5);
                Label label6 = new Label(6, 0, "路名（等于地址）");
                sheet.addCell(label6);
                Label label7 = new Label(7, 0, "门牌");
                sheet.addCell(label7);
                Label label8 = new Label(8, 0, "住宅");
                sheet.addCell(label8);
                Label label9 = new Label(9, 0, "手机");
                sheet.addCell(label9);
                Label label10 = new Label(10, 0, "座机");
                sheet.addCell(label10);
                Label label11 = new Label(11, 0, "邮编");
                sheet.addCell(label11);
                Label label12 = new Label(12, 0, "订阅类型");
                sheet.addCell(label12);
                Label label13 = new Label(13, 0, "报刊");
                sheet.addCell(label13);
                Label label14 = new Label(14, 0, "起始日期");
                sheet.addCell(label14);
                Label label15 = new Label(15, 0, "截止日期");
                sheet.addCell(label15);
                Label label16 = new Label(16, 0, "份数");
                sheet.addCell(label16);
                Label label17 = new Label(17, 0, "折扣类型");
                sheet.addCell(label17);
                Label label18 = new Label(18, 0, "金额");
                sheet.addCell(label18);
                Label label19 = new Label(19, 0, "投递站");
                sheet.addCell(label19);
                Label label20 = new Label(20, 0, "投递段");
                sheet.addCell(label20);
                Label label21 = new Label(21, 0, "投递方式");
                sheet.addCell(label21);
                Label label22 = new Label(22, 0, "投递类型");
                sheet.addCell(label22);
                Label label23 = new Label(23, 0, "备注");
                sheet.addCell(label23);
                Label label24 = new Label(24, 0, "联系人");
                sheet.addCell(label24);
                Label label25 = new Label(25, 0, "订户类型");
                sheet.addCell(label25);
                Label label26 = new Label(26, 0, "单位类型");
                sheet.addCell(label26);

                int num = i * mus;
                int index = 0;
                for (int m = num; m < list.size(); m++) {
                    if (index == mus) {//判断index == mus的时候跳出当前for循环
                        break;
                    }
                    Order order=(Order)list.get(m);
                    AddressInfo addressInfo = orderMgr.getAAddresInfoForOrder(order.getOrderid());

                    Label rownum = new Label(0, index+1, String.valueOf(m));
                    sheet.addCell(rownum);
                    Label orderID = new Label(1, index+1, String.valueOf(order.getOrderid()));
                    sheet.addCell(orderID);
                    Label ordertype = new Label(2, index+1, "");
                    sheet.addCell(ordertype);
                    Label shou_ding_yuan = new Label(3, index+1, "");
                    sheet.addCell(shou_ding_yuan);
                    Label username = new Label(4, index+1, addressInfo.getName() == null ? "" : StringUtil.gb2iso4View(addressInfo.getName()));
                    sheet.addCell(username);
                    Label Address = new Label(5, index+1, addressInfo.getAddress() == null ? "" : StringUtil.gb2iso4View(addressInfo.getAddress()));
                    sheet.addCell(Address);
                    Label roadname = new Label(6, index+1, "");
                    sheet.addCell(roadname);
                    Label  doorplate  = new Label(7, index+1, "");
                    sheet.addCell(doorplate);
                    Label homeaddress = new Label(8, index+1, "");
                    sheet.addCell(homeaddress);
                    Label mphone = new Label(9, index+1, addressInfo.getMobile()==null?"":addressInfo.getMobile());
                    sheet.addCell(mphone);
                    Label thephone = new Label(10, index+1, addressInfo.getPhone()==null?"":addressInfo.getPhone());
                    sheet.addCell(thephone);
                    Label postcode = new Label(11, index+1, addressInfo.getZip()==null?"":addressInfo.getZip());
                    sheet.addCell(postcode);
                    Label subscribe_type = null;
                    if (order.getSubscribe() == 1)
                        subscribe_type = new Label(12, index+1, "年订");
                    else if (order.getSubscribe() == 2)
                        subscribe_type = new Label(12, index+1, "半年订");
                    else if (order.getSubscribe() == 3)
                        subscribe_type = new Label(12, index+1, "季度订");
                    else if (order.getSubscribe() == 4)
                        subscribe_type = new Label(12, index+1, "月订");
                    else
                        subscribe_type = new Label(12, index+1, "");
                    sheet.addCell(subscribe_type);
                    Label Productname = new Label(13, index+1, order.getProductname());
                    sheet.addCell(Productname);
                    Label beginDate = new Label(14,index+1,format.format(order.getServicestarttime()));
                    sheet.addCell(beginDate);
                    Label endDate = new Label(15,index+1,format.format(order.getServiceendtime()));
                    sheet.addCell(endDate);
                    Label bznum = new Label(16, index+1, String.valueOf(order.getOrderNum()));
                    sheet.addCell(bznum);
                    Label discount_type = new Label(17, index+1, "");
                    sheet.addCell(discount_type);
                    Label totalFee = new Label(18, index+1, String.valueOf(order.getTotalfee()));
                    sheet.addCell(totalFee);
                    Label tou_di_zhan = new Label(19, index+1, "");
                    sheet.addCell(tou_di_zhan);
                    Label tou_di_duan = new Label(20, index+1, "");
                    sheet.addCell(tou_di_duan);
                    Label tou_di_fang_shi = new Label(21, index+1, "");
                    sheet.addCell(tou_di_fang_shi);
                    Label tou_di_type = new Label(22, index+1, "");
                    sheet.addCell(tou_di_type);
                    Label beizhu = new Label(23, index+1, "");
                    sheet.addCell(beizhu);
                    Label contactor = new Label(24, index+1, addressInfo.getName()==null?"":addressInfo.getName());
                    sheet.addCell(contactor);
                    Label ding_hu_type = new Label(25, index+1, "");
                    sheet.addCell(ding_hu_type);
                    Label unit_type = new Label(26, index+1, "");
                    sheet.addCell(unit_type);
                    index++;
                }
            }

            //关闭对象，释放资源
            workbook.write();
            workbook.close();
        }catch (Exception e) {
            workbook.close();
            e.printStackTrace();
        }
        return "/xls/order/"+xlsname;
    }

    public static String ExportOrdersbyquery(List<Order> list,String path) throws IOException, WriteException {

        //创建excel文件
        //String path = request.getRealPath("");
        IOrderManager orderMgr = orderPeer.getInstance();
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFm.format(date);
        String xlsname = "querypay"+time+".xls";
        String filepath = path+File.separator + "xls" + File.separator + "order";
        File dir = new File(filepath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File f = new File(filepath,xlsname);
        //在当前目录下建立文件
        if (f.exists()) {
            f.delete();
        } else {
            f.createNewFile();
        }
        String absolutePath = f.getAbsolutePath();
        System.out.println(absolutePath);
        //创建一个可写入的excel文件对象
        WritableWorkbook workbook = Workbook.createWorkbook(f);
        //使用第一张工作表，将其命名为“member”
        // WritableSheet sheet = workbook.createSheet("dd", 0);
        int totle = list.size();//获取List集合的size
        int mus = 60000;//每个工作表格最多存储2条数据（注：excel表格一个工作表可以存储65536条）
        int avg = totle / mus;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00");
        DecimalFormat df2 = new DecimalFormat();
        df2.applyPattern("0");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatDateAndTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String phone = "";
        String province = "";
        String city = "";
        String Orderflag="";
        String PayWay = "";
        String sendWay="";
        String orderStatus="新订单";
        try {
            for (int i = 0; i < avg + 1; i++) {
                WritableSheet sheet = workbook.createSheet("列表"+(i + 1), i); //创建一个可写入的工作表
                //表头
                Label label0 = new Label(0, 0, "订单号");
                sheet.addCell(label0);
                Label label1 = new Label(1, 0, "用户ID");
                sheet.addCell(label1);
                Label label2 = new Label(2, 0, "报纸名称");
                sheet.addCell(label2);
                Label label3 = new Label(3, 0, "金额");
                sheet.addCell(label3);
                Label label4 = new Label(4, 0, "订阅方式");
                sheet.addCell(label4);
                Label label5 = new Label(5, 0, "开始服务时间");
                sheet.addCell(label5);
                Label label6 = new Label(6, 0, "结束服务时间");
                sheet.addCell(label6);
                Label label7 = new Label(7, 0, "下单时间");
                sheet.addCell(label7);
                Label label8 = new Label(8, 0, "支付状态");
                sheet.addCell(label8);
                Label label9 = new Label(9, 0, "支付时间");
                sheet.addCell(label9);
                Label label10 = new Label(10, 0, "支付交易流水号");
                sheet.addCell(label10);
                Label label11 = new Label(11, 0, "支付方式");
                sheet.addCell(label11);
                Label label12 = new Label(12, 0, "支付时间");
                sheet.addCell(label12);
                Label label13 = new Label(13, 0, "支付状态");
                sheet.addCell(label13);
                Label label14 = new Label(14, 0, "支付金额");
                sheet.addCell(label14);

                int num = i * mus;
                int index = 0;
                for (int m = num; m < list.size(); m++) {
                    if (index == mus) {//判断index == mus的时候跳出当前for循环
                        break;
                    }
                    Order order=(Order)list.get(m);
                    Orderflag="普通订单";
                    if(order.getOrderFlag() !=0) Orderflag = "竞标订单";
                    province = order.getProvince()==null?"--": StringUtil.gb2iso4View(order.getProvince());
                    city = order.getCity()==null?"--":StringUtil.gb2iso4View(order.getCity());
                    if ((order.getPhone() != null) && (!"".equals(order.getPhone()))) {
                        phone = order.getPhone();
                        phone = StringUtil.gb2iso4View(phone);
                    }
                    if(order.getPayWay() == 0){
                        PayWay="货到付款";
                    }else if(order.getPayWay() == 1){
                        PayWay="银行支付";
                    }else if(order.getPayWay() == 2){
                        PayWay="支付宝";
                    }else{
                        PayWay="微信";
                    }
                    Fee fee = new Fee();
                    fee = orderMgr.getAFeeInfo(order.getSendWay());
                    if (fee == null){
                        sendWay="同城送货";
                    }else{
                        if(fee.getCname() == null){

                        }else{
                            sendWay=StringUtil.gb2iso4View(fee.getCname());
                        }
                    }

                    if (order.getNouse() == 0) {
                        orderStatus="客户取消";
                    }else{
                        if (order.getStatus() == 1){
                            orderStatus="处理中";
                        }
                        if (order.getStatus() == 2){
                            orderStatus="发货";
                        }
                        if (order.getStatus() == 3){
                            orderStatus="退货";
                        }
                        if (order.getStatus() == 4){
                            orderStatus="完成";
                        }
                        if (order.getStatus() == 5){
                            orderStatus="拒收";
                        }
                        if (order.getStatus() == 6){
                            orderStatus="缺货";
                        }
                        if (order.getStatus() == 7){
                            orderStatus="等待客户付款";
                        }
                        if (order.getStatus() == 8){
                            orderStatus="已付款";
                        }if (order.getStatus() == 9){
                            orderStatus="用户超时未付款";
                        }
                    }
                    int subscribe = order.getSubscribe();
                    int subscribenum = order.getSubscribenum();
                    Timestamp servicestartdate = null;
                    Timestamp serviceenddate = null;
                    if (order.getServicestarttime()!=null)
                        servicestartdate = new Timestamp(order.getServicestarttime().getTime());
                    if (order.getServiceendtime()!=null)
                        serviceenddate = new Timestamp(order.getServiceendtime().getTime());
                    String dyfs="";
                    if(subscribe==1) {
                        dyfs = "年订";
                        //out.println("<br />您的订阅价格：" + orderAndOrderdetail.getPAYFEE());
                    } else if (subscribe==2) {
                        dyfs = "半年订";
                        //out.println("<br />您的订阅价格：" + orderAndOrderdetail.getPAYFEE());
                    } else if (subscribe==3) {
                        dyfs = "季订：您订阅了" + subscribenum + "个季度";
                        //out.println("<br />您的订阅价格：" + orderAndOrderdetail.getPAYFEE());
                    } else if (subscribe==4) {
                        dyfs = "月订：您订阅了" + subscribenum + "个月";
                        //out.println("<br />您的订阅价格：" + orderAndOrderdetail.getPAYFEE());
                    }

                    Timestamp paydates = null;
                    if(order.getPaydate() != null)
                        paydates = new Timestamp(order.getPaydate().getTime());
                    String r2TrxId = ""; //交易流水号
                    String paytime = "";//支付时间
                    String payresult="未支付"; //支付结果状态
                    String payrebackfee = "";//支付金额
                    Map<String, String> qr = null;
                    if(order.getPayWay() == 0){

                    }else if(order.getPayWay() == 1){ //银行支付
                        System.out.println("银行支付");
                        System.out.println(String.valueOf(order.getOrderid()));
                        qr = PaymentForOnlineService.queryByOrder(String.valueOf(order.getOrderid()));	            // 调用后台外理查询方法
                        System.out.println("yyyyyy");
                        if(qr !=null ){
                            if (qr.get("rb_PayStatus").equals("SUCCESS")) {
                                r2TrxId = qr.get("r2_TrxId");
                                paytime = qr.get("rx_CreateTime");
                                payresult = qr.get("rb_PayStatus");
                                payrebackfee = qr.get("r3_Amt");
                            }
                        }
                        System.out.println("银行支付查询结果为空");
                    }else if(order.getPayWay()==2){  //支付宝支付
                    }else{//微信支付
                        System.out.println("微信支付");
                        WXPayService wxPayService = WXPayService.getInstance();
                        qr = wxPayService.tradeQuery(String.valueOf(order.getOrderid()));
                        if(qr !=null ) {
                            if (qr.get("result_code").equals("SUCCESS")) {
                                r2TrxId = qr.get("transaction_id");
                                paytime = qr.get("time_end");
                                if (qr.get("trade_state").equals("SUCCESS")) {
                                    payresult = "支付成功";
                                }
                                if (qr.get("trade_state").equals("REFUND")) {
                                    payresult = "转入退款";
                                }
                                if (qr.get("trade_state").equals("NOTPAY")) {
                                    payresult = "未支付";
                                }
                                if (qr.get("trade_state").equals("CLOSED")) {
                                    payresult = "已关闭";
                                }
                                if (qr.get("trade_state").equals("REVOKED")) {
                                    payresult = "已撤销（刷卡支付）";
                                }
                                if (qr.get("trade_state").equals("USERPAYING")) {
                                    payresult = "用户支付中";
                                }
                                if (qr.get("trade_state").equals("PAYERROR")) {
                                    payresult = "支付失败(其他原因，如银行返回失败)";
                                }

                                payrebackfee = qr.get("total_fee");
                            }
                            System.out.println("微信支付查询完毕");
                        }
                        System.out.println("微信支付查询结果为空");
                    }
                    System.out.println("uuuuuuuuuuuuuuuuuu");
                    Label orderID = new Label(0, index+1, String.valueOf(order.getOrderid()));
                    sheet.addCell(orderID);
                    Label userid = new Label(1, index+1, order.getName() == null ? "" : StringUtil.gb2iso4View(order.getName()));
                    sheet.addCell(userid);
                    Label Productname = new Label(2, index+1, order.getProductname());
                    sheet.addCell(Productname);
                    Label payFee = new Label(3, index+1, String.valueOf(order.getPayfee()));
                    sheet.addCell(payFee);
                    Label dyfss = new Label(4, index+1, dyfs);
                    sheet.addCell(dyfss);
                    Label servicestartdates = new Label(5, index+1, format.format(servicestartdate));
                    sheet.addCell(servicestartdates);
                    Label serviceenddates = new Label(6, index+1, format.format(serviceenddate));
                    sheet.addCell(serviceenddates);
                    Label createdate = new Label(7, index+1, formatDateAndTime.format(order.getCreateDate()));
                    sheet.addCell(createdate);
                    Label orderStatuss = new Label(8, index+1, orderStatus);
                    sheet.addCell(orderStatuss);
                    Label paydate = new Label(9, index+1, formatDateAndTime.format(paydates));
                    sheet.addCell(paydate);
                    Label r2TrxIds = new Label(10, index+1, r2TrxId);
                    sheet.addCell(r2TrxIds);
                    Label PayWays = new Label(11, index+1, PayWay);
                    sheet.addCell(PayWays);
                    Label paytimes = new Label(12, index+1, paytime);
                    sheet.addCell(paytimes);
                    Label payresults = new Label(13, index+1, payresult);
                    sheet.addCell(payresults);
                    Label payrebackfees = new Label(14, index+1, payrebackfee);
                    sheet.addCell(payrebackfees);

                    index++;
                }
            }

            //关闭对象，释放资源
            workbook.write();
            workbook.close();
        }catch (Exception e) {
            workbook.close();
            e.printStackTrace();
        }
        return "/xls/order/"+xlsname;
    }
}
