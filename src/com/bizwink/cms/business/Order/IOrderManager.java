package com.bizwink.cms.business.Order;

import java.util.*;

import com.bizwink.cms.security.User;
import com.bizwink.webapps.register.Uregister;

import java.sql.*;


public interface IOrderManager
{
    String ToChinese();

    void updteOldOrderPayStatus();

    void orderPeer(String NumberMoney);

    List getOrderList(int startindex,int range,int status,String sqlstr) throws OrderException;

    List getOrderList(int startindex, int range, int siteid,int status);

    int getTotalOrderNumByStatus(int siteid,int status);

    int getTotalSubscribeNum(int siteid,int status);    //获取所有订单中报纸订阅份数的综合，相当于获取所有订单中某种商品的卖出总和

    List getOrderListByUsers(int startindex, int range, int siteid,int status,List<User> users);

    int getOrderNumByStatusAndUsers(int siteid,int status,List<User> users);

    int getSubscribeNumByStatusAndUsers(int siteid,int status,List<User> users);

    List searchOrderList(int startindex, int range, int siteid,int status,String whereClause,String startday,String endday,int orgtype,int com_orgid,int dept_orgid,int uid,int source) throws OrderException;

    int searchOrderNum(int siteid,int status,String whereClause,String startday,String endday,int orgtype,int com_orgid,int dept_orgid,int uid,int source)  throws OrderException;

    int searchSubscribeOrderNum(int siteid,int status,String whereClause,String startday,String endday,int orgtype,int comid,int deptid,int uid,int source);

    //range为0时为查询全部，sqlstr为自定义查询为空时为默认查询
    public List getSupplierNumByOrder(long orderid) throws OrderException;

    public Order getAOrder(long orderid) throws OrderException;

    public void updateStatus(long orderid,int status,String jingbanren) throws OrderException;

    public int updateOrderinfoByZhifuResult(Order order) throws OrderException;

    //会修改对应收款与库存记录
    public String getPhone(long orderid) throws OrderException;

    public String getPayWay(int payway) throws OrderException;

    public String getCountry(int country) throws OrderException;// may not use anymore,but 'country' is still exist

    public String getProvince(int province) throws OrderException;

    public String getCity(int cid) throws OrderException;

    public String getZone(int zid) throws OrderException;

    public String getSendWay(int sendway) throws OrderException;

    public int getOrderStatus(long orderid) throws OrderException;

    public List getDetailList(long orderid) throws OrderException;

    public List getDetail(long orderid) throws OrderException;

    //public Hashtable getProduct(int productid) throws OrderException;

    public String getNotes(int orderid) throws OrderException;

    public int getStatus(long orderid) throws OrderException;

    //public void addStockNum(int orderid,int flag)throws OrderException;
    //flag为1是加库存，flag为0是减库存
    public int getUserOrderNums(int userid) throws OrderException;

    public boolean checkAOrder(long orderid) throws OrderException;

    public void addReceiveMoney(long orderid, String jingbanren) throws SQLException;

    public String getPayWay(int payway, int payflag) throws OrderException;

    public String getSendwayname(int sendway) throws OrderException;

    public List getNouse(int startindex, int range, String loginuserid)  throws OrderException;

    public List getNouse(int startindex, int range,int siteid) throws OrderException;

    public int createFee(Fee fee);

    public Fee getAFeeInfo(int id);

    public int deleteAFeeInfo(int id);

    public List getAllFeeInfo(int siteid);

    public int updateAFeeInfo(Fee fee);

    public int createSendWayInfo(SendWay send);

    public SendWay getASendWayInfo(int id);

    public int deleteASendWayInfo(int id);

    public List getAllSendWayInfo(int siteid);

    public int updateASendWayInfo(SendWay sendway);

    public int createAddressInfo(AddressInfo address);

    public AddressInfo getAAddresInfo(int id);

    public AddressInfo getAAddresInfoForOrder(long orderid);

    public int updateAddressinfo(AddressInfo address);

    public int deleteAddressInfo(int id);

    public List getAllAddressInfo(int userid);

    public int createOrderInfo(Order order,List order_detail,Invoice invoice);

    public List getUserOrderList(int userid);

    public int createScoresRule(int siteid, int scores);

    public boolean checkOrderID(long orderID) throws OrderException;

    public int getScoresRuleForSite(int siteid);

    public int updateUserScores(int userid, int scores);

    public List getCardForOutLine(int start, int range, String sql) throws OrderException;

    public int getCardNum(String sql) throws OrderException;

    public int getIsCheckCardNum(int siteid) throws OrderException;

    public int getActivationCardNum(int siteid) throws OrderException;

    public void createCard(Card card) throws OrderException;

    public void autoCreateCard(int siteid, String begintime, String endtime, int denominations, int number) throws OrderException;

    public int deledAllCards(int siteid) throws OrderException;

    public int deleteACard(int id) throws OrderException;

    public Card getACardInfo(int id);

    public int checkCard(int siteid, String cardnum, String code,int voucher);

    public int updateCardUsed(int id);

    public int updateCardIscheck(int id,int ischeck);

    int deleteOrder(int siteid,long orderid) throws OrderException;

    int delOrder(int siteid, long orderid) throws OrderException;

    int createNewOrderBySupplier(int supplierid,Order order) throws OrderException;

    String getProductInfoForBBN(long orderid,int selid)  throws OrderException;

    //获取某种类型的用户名和密码的ID
    int getProductIDInfoForBBN(String mphone,int cardtype)  throws OrderException;

    //设置被选择的用户名口令对应的记录的flag为零，其他用户可以继续选择该商品
    int updateProductFlagForBBN(int selid);

    int setProductSucessSaleoutStatusForBBN(long orderid,String username,String password,int cardtype)  throws OrderException;

    int addInterfaceParam(InterfaceParam iparam);

    InterfaceParam getInterfaceParam(int paywayid);

    int updateInterfaceParam(InterfaceParam iParam);

    List getUserOrderList(int userid,int range,int startrow);

    int getUserOrderNum(int userid);

    void cancelOrder(long orderid) throws OrderException;

    int createInvoiceConent(Invoice invoice);

    List getInvoiceContentList(int siteid);

    int updateInvoicContnet(Invoice invoice);

    int deleteInvoiceConenteById(int id);

    Invoice getInvoiceConenteById(int id);

    Invoice getInvoiceInfoForUser(int userid);

    Invoice getInvoiceInfoForOrder(long orderid);

    Card getACardInfo(String cardnum);

    int updateOrderPayflag(long orderid,int payflag);

    int getCityId(String cityname) throws OrderException;

    float getShoppingFee(int sendway,int payway,int cityid,String productids,String nowdata,float totalfee,int siteid);

    //报社
    int createOrderList(Order order,List list,Uregister ure);

    List<Order> getOrderListToExcel(String whereClause,String startday,String endday,int siteid,int comid,int deptid,int source,int orgtype);
}
