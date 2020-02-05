package com.bizwink.ItfSchedule;


/**
 * 接口用常量
 * 
 * @author huasen.xu 2013-11-16 create
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

	/** 接口：物料分类同步 */
	public static String TYPE_PRODCLASS = "ProdClass";
	/** 接口：物料明细同步 */
	public static String TYPE_PROD = "Prod";
	/** 接口：物料特征量同步 */
	public static String TYPE_PRODFEATURE = "ProdFeature";

	/** 接口：内部供应商主档案同步 */
	public static String TYPE_INTSUPPMAIN = "IntSuppMain";
	/** 接口：外部供应商主档案同步 */
	public static String TYPE_EXTSUPPMAIN = "ExtSuppMain";
	/** 接口：供应商产品目录同步 */
	public static String TYPE_SUPPPRODCLASS = "SuppProdClass";

}
