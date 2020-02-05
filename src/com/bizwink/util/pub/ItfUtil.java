package com.bizwink.util.pub;

//import com.edarong.common.Constants;
//import com.edarong.persistence.hibernate.JDBCHelper;

/**
 * 接口工具类
 */
public class ItfUtil {

    /**
     * 是否发送数据到ERP
     *
     * @param type 1: 询比价 2：协议等只有寻源方案的 3：招标 4：竞价
     * @param billId type=1或4为询价书ID，2或3时为寻源方案ID
     * @param prComp 需求公司
     * @return boolean
     * @author huasen.xu 2013-12-10 create
     */
    public static boolean doSendDataToErp(int type, long billId, long prComp) {
/*
        if(type == Constants.ECTOERP_TYPE_ENQUIRE) { //询比价
            //TODO 待实现
            return false;
        } else if(type == Constants.ECTOERP_TYPE_BID) { //招标
            String sql = "select t.flag_down from t_interface_ec_erp t where t.code_ec="+ prComp
                    +" and not exists(select 1 from t_company c where c.c_id="+ prComp
                    +" and c.c_comptype in("+ Constants.COMPTYPE_ZSJGJN +","+ Constants.COMPTYPE_HQ
                    + Constants.COMPTYPE_CBZX +"))";
            java.math.BigDecimal val = (java.math.BigDecimal) JDBCHelper.queryReturnFristValue(sql);
            //是否启动下载接口(1 是 0 否)
            int flag_down = 0;
            if(val != null)
                flag_down = val.intValue();
            if(flag_down == 0)
                return false;
            //是否存在没有采购申请号的
            sql = "select 1 from t_stockorder o where o.c_project="+ billId
                    + " and o.c_planno is null and rownum=1";
            val = (java.math.BigDecimal) JDBCHelper.queryReturnFristValue(sql);
            if(val == null)
                return true;
            else
                return false;
        } else if(type == Constants.ECTOERP_TYPE_PRJONLY) { //协议等只有寻源方案的
            //TODO 待实现
            return false;
        } else if(type == Constants.ECTOERP_TYPE_COMPETE) { //竞价
            //TODO 待实现
            return false;
        } else  { //未知
            return false;
        }
*/
        return false;
    }

}
