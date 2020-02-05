package com.bizwink.util.pub;

/**
 * 接口用常量
 */
public interface ItfConsts {
    /** TimerTask类包路径 */
    public static String PATH_TASK = "com.edarong.butt.task.";

    /** 接口：所有 */
    public static String TYPE_ALL = "All";

    /** 接口：ERP调拨单销售发票信息上传 */
    public static String TYPE_FP = "WzerpToecHF";
    /** 接口：ERP合同已付款信息上传 */
    public static String TYPE_FK = "WzerpToecHK";
    /** 接口：ERP合同已收票信息上传 */
    public static String TYPE_SP = "WzerpToecHS";
    /** 接口：ERP合同签订信息上传 */
    public static String TYPE_HT = "WzerpToecHT";
    /** 接口：ERP合同已关闭信息上传 */
    public static String TYPE_HTGB = "WzerpToecHX";
    /** 接口：ERP内向和外向交货单上传 */
    public static String TYPE_JHD = "WzerpToecNY";

    /** 接口：ERP采购申请(PR)上传 */
    public static String TYPE_PR = "Procurement";
    /** 接口：ERP采购订单(PO)上传 */
    public static String TYPE_PO = "EcInterfacePo";
    /** 接口：ERP寄售（计划协议、基础油）上传 */
    public static String TYPE_JS = "EcInterfaceJs";
    /** 接口：ERP供应商评估上传 */
    public static String TYPE_GYSPG = "WzerpToecRE";

    /** 接口：IPMS采购申请(PR)上传 */
    public static String TYPE_PR_Imps = "ProcurementIpms";
    /** 接口：IPMS合同评分上传 */
    public static String TYPE_Grade = "ProcurementGrade";
    /** 接口：IPMS合同信息上传 */
    public static String TYPE_PurchaseOrder = "ProcurementPurchaseOrder";

    /** 接口：物料分类同步 */
    public static String TYPE_PRODCLASS = "ProdClass";
    /** 接口：物料明细同步 */
    public static String TYPE_PROD = "Prod";
    /** 接口：物料特征量同步 */
    public static String TYPE_PRODFEATURE = "ProdFeature";

    /** 接口：供应商主档案同步 */
    public static String TYPE_SUPPMAIN = "SuppMain";
    /** 接口：供应商产品目录同步 */
    public static String TYPE_SUPPPRODCLASS = "SuppProdClass";

    /** 指定时间点定时执行接口：供应商一年无交易 */
    public static String TYPE_SUPP_YNWJY = "#SuppYnwjy";
    public static String TYPE_SUPP_YNWJY_INFO = TYPE_SUPP_YNWJY.substring(1);
    /** 指定时间点定时执行接口：供应商综合评分 */
    public static String TYPE_SUPP_ZHPF = "#SuppZhpf";
    public static String TYPE_SUPP_ZHPF_INFO = TYPE_SUPP_ZHPF.substring(1);

    /** 定时任务：税率同步 */
    public static String TYPE_EXCHRATE = "ExchRate";
}
