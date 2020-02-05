--采购申请任务分配表
create table tbl_joballocator(
	id                       int               not null,                    --主键ID
	customer_id	         int               not null,                    --客户ID
	company_id		 int               not null,                    --设置该规则的公司ID     
	serviced_companyid       int               not null,                    --被服务的公司ID
	buy_companyid            int               not null,                    --执行采购服务的公司ID
	wuzicode		 varchar2(100),                                 --物资编码
	wuziname                 varchar2(100),                                 --物资或物资分类名称
	createdate               timestamp,                                     --创建时间
	creator		         varchar2(100),                                 --创建人
	updatedate               timestamp,                                     --最后修改时间 
	updater                  varchar2(100),                                 --最后修改人
	remark                   varchar2(1000),                                --注释
	primary key (id)
);

--在任务分配表中定义的物资必须符合某些条件才执行集中采购
create table tbl_joballocator_rules(
	id                       int               not null,                    --主键ID
	allocatorid	         int               not null,                    --任务分配表主键ID
	rulename		 varchar2(100)     not null,                    --规则名称，对应采购申请的数据表字段名
	rulevalue                varchar2(1000)    not null,                    --规则字符串，多个规则按照"与"、"或"的关系进行组合，例如 进口采购 AND 概算金额>50万
	createdate               timestamp,                                     --创建时间
	creator		         varchar2(100),                                 --创建人
	updatedate               timestamp,                                     --最后修改时间 
	updater                  varchar2(100),                                 --最后修改人
	remark                   varchar2(1000),                                --注释
	primary key (id)
);

--在任务分配设置表中设置的集中采购物资也允许企业自采或者在某些条件下自采
create table tbl_permit_selfbuy(
	id                       int               not null,                    --主键ID
	customer_id	         int               not null,                    --客户ID
	company_id		 int               not null,                    --设置该规则的公司ID     
	wuzicode		 varchar2(100),                                 --物资编码
	wuziname                 varchar2(100),                                 --物资名称或物资分类名称
	createdate               timestamp,                                     --创建时间
	creator		         varchar2(100),                                 --创建人
	updatedate               timestamp,                                     --最后修改时间 
	updater                  varchar2(100),                                 --最后修改人
	remark                   varchar2(1000),                                --注释
	primary key (id)
);

--允许企业进行自采的集采物资的条件设置
create table tbl_permit_selfbuy_rules(
	id                       int               not null,                    --主键ID
	permit_id	         int               not null,                    --客户ID
	rulename		 varchar2(100)     not null,                    --规则名称，对应采购申请的数据表字段名
	rulevalue                varchar2(1000)    not null,                    --规则字符串，多个规则按照"与"、"或"的关系进行组合，例如概算金额<50万
	createdate               timestamp,                                     --创建时间
	creator		         varchar2(100),                                 --创建人
	updatedate               timestamp,                                     --最后修改时间 
	updater                  varchar2(100),                                 --最后修改人
	remark                   varchar2(1000),                                --注释
	primary key (id)
);

create table tbl_dict
(
  id           NUMBER(19) not null,                  --主键
  typecode     VARCHAR2(255 CHAR),                   --字典项代码
  typename     VARCHAR2(255 CHAR),                   --字典项名称
  detailcode   VARCHAR2(255 CHAR),                   --明细字典编码
  detailname   VARCHAR2(255 CHAR),                   --明细字典名称
  validaty     NUMBER(1),                            --是否有效  1--有效  0-无效
  primary key (id)
)

  
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(1, '数据状态',101,1,'保存',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(2, '数据状态', 101,    2,'修改',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(3, '数据状态', 101,    3,'删除',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(4, '数据状态', 101,    4, '停用',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(5, '数据状态', 101,    5, '提报',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(6, '数据状态', 101,    6, '审批',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(7, '数据状态', 101,    7, '审批通过',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(8, '数据状态', 101,    8, '审批未通过',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(9, '采购方式', 102,    1, '询比价',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(10, '采购方式', 102,    2, '竞价',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(11, '采购方式', 102,    3,  '谈判',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(12, '采购方式', 102,    4,   '招标',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(13,'采购方式', 102,    5,   '超市',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(14,'采购类型', 103,    1,   '集中采购',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(15,'采购类型', 103,    2,   '企业自采',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(16, '需求来源',104,   1,  '项目',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(17,'需求来源',104,   2,  '生产',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(18,'需求来源',104,   3,  '维修',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(19,'需求来源',104,   4,  '科研',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(20,'数据来源',105,   1,  '手工录入',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(21,'数据来源',105,   2,  '需求计划提报',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(22,'数据来源',105,   3,  '文件导入',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(23,'数据来源',105,   4,  'ERP系统',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(24,'采购订单进度',106,   1,'订单维护',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(25,'采购订单进度',106,   2,'采购方案',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(26,'采购订单进度',106,   3,'询价书',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(27,'采购订单进度',106,   4,'报价书',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(28,'采购订单进度',106,   5,'采购结果方案',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(29,'采购订单进度',106,   6,'合同维护',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(30,'采购订单进度',106,   7,'合同评价',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(31,'采购订单进度',106,   8,'合同核销',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(32,'寻源类型',107,   1,'框架协议寻源',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(33,'寻源类型',107,   2,'直接采购寻源',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(34,'币种',108,   1,'人民币',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(35,'时区',109,   2,'西2区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(36,'时区',109,   3,'西1区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(37,'时区',109,   4,'0时区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(38,'时区',109,   5,'东1区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(39,'时区',109,   6,'东2区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(40,'时区',109,   7,'东3区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(41,'时区',109,   8,'东4区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(42,'时区',109,   9,'东5区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(43,'时区',109,   10,'东6区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(44,'时区',109,   11,'东7区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(45,'时区',109,   12,'东8区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(46,'时区',109,   13,'东9区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(47,'时区',109,   14,'东10区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(48,'时区',109,   15,'东11区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(49,'时区',109,   16,'东12区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(50,'时区',109,   17,'西11区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(51,'时区',109,   18,'西10区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(52,'时区',109,   19,'西9区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(53,'时区',109,   20,'西8区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(54,'时区',109,   21,'西7区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(55,'时区',109,   22,'西6区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(56,'时区',109,   23,'西5区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(57,'时区',109,   24,'西4区',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(58, '物资所属专业',110,1,'化工原料',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(59, '物资所属专业',110,2,'材料',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(60, '物资所属专业',110,3,'设备',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(61,'委托方式',111,1,'委托企业自采',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(62,'委托方式',111,2,'委托外单位',1);



--订单管理模块
--采购订单表
--在一个业务环节里面的处理称为状态，例如提报、保存、审批通过、审批未通过
--在不同业务环节里面的处理称为进度，例如需求订单、询源方案、询价书、报价，询源结果
create table tbl_prodorder
(
  ID                 NUMBER(19) not null,            --主键ID
  ORDERCODE          VARCHAR2(40),                   --订单编号
  OLDCODE            VARCHAR2(40),                   --原编码                       从其他系统传的采购申请编号
  PLANNO             VARCHAR2(40),                   --采购申请号                   记录外部系统过来的业务单据号
  USERID             NUMBER(19) not null,            --会员ID
  OPERANAME          VARCHAR2(20),                   --业务员真实姓名
  company            NUMBER(19) not null,            --采购申请编制公司ID
  SUBTIME            timestamp,                      --提交时间
  PLANTIME           timestamp default sysdate,      --采购申请提报时间
  SEQTIME            timestamp,                      --排序时间                     用于排序                
  STATUS             NUMBER(19),                     --状态ID                       提报   未审批 （状态值标出来） 
  STOCKTYPE          NUMBER(19),                     --采购方式ID                   询比价  竞价  联合谈判  招标  超市      
  PROGRESS           NUMBER(19) default 0,           --需求订单业务进度ID           订单  寻源方案  询价书 报价书 采购结果方案 合同 合同评价  合同核销         
  ATTRTYPE           NUMBER(19),                     --采购类型ID                   1-集中采购   2-企业自行采购                     
  REQUIRESOURCE      NUMBER(19) default 0,           --需求来源                     项目 生产 维修 科研
  DATASOURCE         NUMBER(19) default 0,           --数据来源                     1-手工录入 2-需求计划  3-文件导入 4-ERP
  TIMEZONE           NUMBER(19) default 0,           --时区                         取编码定义的时区值
  PRODSPECIALTY      NUMBER(19) default 0,           --需求物资专业ID               取编码定义的值
  FRMTYPE            NUMBER default 0,               --寻源类型                     1框架协议寻源、0非框架协议寻源；根据选择的需求订单自动确定
  PROJECTID          NUMBER,                         --对应询源方案ID
  ENQUIREBILLID      NUMBER,                         --对应询价书ID
  WTTYPE             NUMBER(19),                     --委托和国际采购ID               
  CREATEDEPT         NUMBER(19) default 0,           --编制部门ID
  createcomp         NUMBER(19) default 0,           --编制采购申请的公司ID
  CREATEDATE         timestamp,                      --编制时间
  creator            NUMBER(19) default 0,           --编制人
  UPDATETIME         timestamp,                      --最近修改时间
  updatecomp         NUMBER(19) default 0,           --最近修改采购申请的公司ID
  UPDATEDEPT         NUMBER(19) default 0,           --最近修改部门ID
  UPDATEUSER         NUMBER(19) default 0,           --最近修改用户ID
  WTWAY              NUMBER(19) default 0,           --委托方式                      1-委托直属机构 2-委托企业自采  3-委托外单位 0-不委托 
  WTCODE             VARCHAR2(20),                   --委托单编号ID
  WTCOMP             NUMBER(19) default 0,           --委托单位ID
  ORDER_REMARK       VARCHAR2(200),                  --需求订单备注(新增)
  primary key(id)
);
--把附表中所有物资都相同的数据项全部放到主表中

--采购产品类订单项目表
create table tbl_proddetails
(
  ID                 NUMBER(19) not null,            --主键ID
  mian_id            NUMBER(19),                     --数据主表ID,采购申请主表对应采购申请ID，采购方案主表对应采购方案ID，询价书主表对应询价书ID
  main_code          VARCHAR2(40),                   --数据主表编号,采购申请主表对应采购申请编码，采购方案主表对应采购方案编码，询价书主表对应询价书编码
  WZCLASS            NUMBER(19),                     --产品类ID
  WZCLASSCODE        VARCHAR2(16),                   --所属产品类代码
  buycomp            NUMBER(19) not null,            --执行该物资采购公司ID
  contractcomp       NUMBER(19) not null,            --执行该物资合同签订公司ID，一般同执行该物资采购公司ID
  ISPROCESSCONTROL   NUMBER(1) default 0,            --是否过程控制                 0-否  1-是
  ISIMPORT           NUMBER(1) default 0,            --是否进口                     0-否   1-是
  WZNAME             VARCHAR2(200),                  --物资名称
  WZDESC             VARCHAR2(1000),                 --物资描述
  WZNUM              NUMBER,                         --物资采购数量
  WZUNIT             VARCHAR2(50),                   --物资计量单位
  ESTIMATECURRENCY   NUMBER(19) default 0,           --概算币种                     取编码表定义的值     
  ESTIMATEAMOUNT     NUMBER(14,2) default 0,         --概算金额
  CURRENCYRATE       NUMBER(10,4) default 0,         --概算币本位币汇率             取订单编制时当天的汇率
  BASECURRENCY       NUMBER(19) default 0,           --本位币                       取本位币的定义值
  BASECURRENCYAMOUNT NUMBER(14,2) default 0,         --本位币概算金额    
  RMBRATE            NUMBER(10,4) default 0,         --概算币人民币汇率
  RMBAMOUNT          NUMBER(14,2) default 0,         --人民币概算金额
  deliveryaddr       VARCHAR2(1000),                 --交货地址  
  BeginDeliverydate  timestamp,                      --开始交货期
  ENDDeliveryDATE    timestamp,                      --截止交货期            
  VALIDITY           NUMBER(1) default 1,            --是否有效 1-有效  0-无效
  unit_remark        VARCHAR2(20),                   --使用计量单位(新增)
  quality            NUMBER,                         --质量分类(新增)             1-A类 2-B类  3-C类
  primary key(id)
);

--采购订单内容库---订单明细表
create table tbl_ordercont
(
  ID                 NUMBER(19) not null,            --主键
  ORDERCODE          VARCHAR2(40),                   --订单编号
  ITEMCODE           NUMBER(8),                      --订单项目编号
  ITEMVAL            VARCHAR2(100),                  --订单项目值
  primary key(id)
);

--询源方案
create table tbl_project
(
  ID                 NUMBER not null,                --主键
  PROJCODE           VARCHAR2(40)  not null,         --寻源方案编号 编号=公司编码+采购属性+录入方式(1：网上 2：网下 )+年月+流水号+类型
  USERID             NUMBER(19) not null,            --会员ID           
  PROJTOPIC          VARCHAR2(100),                  --寻源方案名称
  OPERANAME          VARCHAR2(20),                   --业务员真实姓名
  SUBTIME            timestamp default sysdate,      --方案提交时间
  STATUS             NUMBER,                         --状态代码ID            见数据字典表t_dict
  TYPE               NUMBER default 0,               --类型                  1-新增  2-修改  3-撤销
  STOCKTYPE          NUMBER,                         --采购方式ID            询比价  竞价  联合谈判  招标  超市            
  ATTRTYPE           NUMBER(19) default 0,           --采购类型ID            集中采购   企业自采
  FRMTYPE            NUMBER default 0,               --寻源类型              1-框架协议寻源、0-非框架协议寻源；根据选择的需求订单自动确定
  STOCKLEVEL         NUMBER(19) default 0,           --集采级别      物装部集中采购或者SEG集中采购
  suggestion         VARCHAR2(2000),                 --计划员采购意见
  ENQUIREBILLID      VARCHAR2(40),                   --对应询价书(招标公告 采购单)编号ID
  ATTACHMENT         number(1),                      --有无附件               1-有  0-无              
  buycomp            NUMBER(19) not null,            --执行采购的公司ID
  PROCESS            NUMBER,                         --进度                                     
  PROJECTRANGE       NUMBER default 0,               --供应商范围    1-定向  2-公开
  TRANSORG           NUMBER(19),                     --运输组织，             1-供应商组织 2-询价单位组织 3-収货单位组织 4-使用单位组织
  ESTIMATECURRENCY   NUMBER(19),                     --概算币种（从用户本位币配置获取）  
  ESTIMATEAMOUNT     NUMBER(14,2) default 0,         --概算金额 （根据用户本位币计算需求订单的概算金额，可调整）                               
  RMBAMOUNT          NUMBER(14,2) default 0,         --人民币概算金额  （需求订单人民币概算总和）
  quoteCURRENCY      NUMBER,                         --报价币种ID      
  quotetype          NUMBER,                         --价格类型ID         出厂价  离岸价
  quoteremark        VARCHAR2(2000),                 --报价要求     
  otherfee           NUMBER,                         --运杂费及其他费用计费方式ID     （新增）
  BISSAX             NUMBER(1),                      --是否含税        0-不   1-含税   改为C_IFTAX
  taxrate            VARCHAR2(40),                   --税率      
  TIMEZONE           NUMBER(19) default 0,           --时区ID
  CREATETIME         timestamp,                      --编制时间：系统时间，前台显示
  createcomp         NUMBER(19) default 0,           --编制采购申请的公司ID
  CREATEDEPT         NUMBER,                         --编制部门ID：用户部门，前台显示
  creator            NUMBER(19) default 0,           --编制人
  UPDATETIME         timestamp,                      --最近修改时间：系统时间，前台显示
  UPDATECOMP         NUMBER(19),                     --最近修改单位ID：用户单位，前台显示
  UPDATEDEPT         NUMBER(19),                     --最近修改部门ID：用户部门，前台显示
  UPDATEUSER         NUMBER(19),                     --最近修改用户ID：用户登录名，不显示
  remark             VARCHAR2(2000),                 --备注
  PAYWAY             NUMBER,                         --货款支付方式
  PAYPROCESS         varchar2(100),                  --支付进度
  STANDARDWORD       NUMBER,                         --贸易术语      (看标准化网站) 
  STARTPORT          NUMBER,                         --起运港ID      (数据4.0中已经有)
  ENDPORT            NUMBER,                         --到货港ID      (数据4.0中已经有)
  AUTHORIZE          NUMBER(1) default 0,            --是否委托      只是进口的可以从这里进行委托
  MOBILE             VARCHAR2(19),                   --手机
  TEL                VARCHAR2(19),                   --电话
  FAX                VARCHAR2(19),                   --传真
  EMAIL              VARCHAR2(19),                   --电子邮件
  ZIP                VARCHAR2(19),                   --邮政编码
  ADDR               VARCHAR2(200),                  --详细地址
  CONTACTOR          VARCHAR2(40),                   --联系人
  IMPORTFLAG         NUMBER,                         --进口标识
  frameworkcode      VARCHAR2(40),                   --框架协议编号 
  buyer              VARCHAR2(40),                   --采购员          （新增，没有指定为空，审批领导指定后回写）
  CONSTRAINT         wyprojectcode  UNIQUE(PROJCODE),--PROJCODE为唯一约束字段
  primary key(id)
);


create table tbl_project_inqu(
  ID                 number not null,                --主键ID
  projectid          number not null,                --采购计划ID
  projectcode        VARCHAR2(40)  not null,         --采购计划编号
  start_time         timestamp ,                     --计划询价发布时间（需要审批的审批通过/无需审批的，到计划发布询价时间自动发布询价书）
  close_time         timestamp ,                     --计划报价截止时间
  inqu_clause        varchar2(4000),                 --询价要求条款
  other_demand       varchar2(4000) ,                --报价要求说明
  part_whether       number(1) default 1,            --是否允许清单部分报价（1、是；0、否）
  quantity_whether   number(1) default 1,            --是否允许单项调整数量报价（1、是；0、否）
  xjmultiquoteflag   NUMBER default 0,               --询价是否允许多伦报价   0-不允许   1-允许多伦报价
  tech_whether       number(1) default 0,            --是否技术评审                （1、是；0、否：技术评审时，供应商报价必须上传技术附件）
  xjTECHENQUIRE      NUMBER,                         --技术评审环节的位置           1-定价定价和技术评审同时进行  2-先进行技术评审，技术评审通过的供应商才可以报价。
  xjTECHENQUSTART    timestamp,                      --技术询价开始日期
  xjTECHENQUEND      timestamp,                      --技术询价结束日期
  ensure_mode        number(1) default 1,            --决标方式（1、采购专家决标；2、采购项目组决标）
  Review_mode        number(1) default 1,            --项目组决标方式（1、投票；2、打分）
  createtime         timestamp default sysdate,      --创建时间
  updatetime         timestamp,                      --最近维护时间
  username           number(8),                      --操作人姓名
  userid             number not null,                --操作用户名
  remark             varchar2(2000),                 --维护说明
  primary key(id)
);
alter table tbl_project_inqu add unique(projectcode); 

create table tbl_project_auction(
  ID                 number not null,                --主键ID
  projectid          number not null,                --采购计划ID
  projectcode        VARCHAR2(40)  not null,         --采购计划编号
  start_time         timestamp ,                     --计划竞价开始时间（需要审批的审批通过/无需审批的，到计划发布询价时间自动发布询价书）
  close_time         timestamp ,                     --计划竞价截止时间
  jjQUOTETYPE        NUMBER default 0,               --是否预报价             1-是   0-否 
  jjbasepriceflag    NUMBER default 0,               --是否设置竞价基准价     1-是   0-否
  createtime         timestamp default sysdate,      --创建时间
  updatetime         timestamp,                      --最近维护时间
  username           number(8),                      --操作人姓名
  userid             number not null,                --操作用户名
  remark             varchar2(2000),                 --维护说明
  primary key(id)
);
alter table tbl_project_auction add unique(projectcode); 

--寻源方案对应的采购物资表（按照物料编码排序）
create table tbl_wzinproject
(
  ID                   NUMBER not null,              --主键
  PROJECTID            NUMBER,                       --寻源方案ID
  PROJCODE             VARCHAR2(100),                --寻源方案编号
  ORDERITEM            NUMBER(19),                   --订单ID
  ORDERCODE            VARCHAR2(40),                 --订单编号
  proddetailid         NUMBER,                       --订单物资明细表的物资ID
  WZCLASS              NUMBER(19),                   --产品类ID
  WZCLASSCODE          VARCHAR2(16),                 --所属产品类代码
  CREATETIME           timestamp,                    --操作时间
  primary key(id)
);

--寻源方案与供应商关系表
create table tbl_projectsupp
(
  ID                   NUMBER not null,              --主键
  PROJCODE             VARCHAR2(40),                 --寻源编号
  PROJECTID            NUMBER default 0 not null,    --寻源id
  SUPPID               NUMBER default 0 not null,    --供应商表主键ID
  SUPPCODE             NUMBER default 0 not null,    --供应商编码
  SUPPCODENAME         VARCHAR2(200),                --供应商名字
  QUOTETYPE            NUMBER,                       --是否报价 0--没有报价 1--有报价（新增）
  COMPTYPE             NUMBER,                       --是否参与竞价 0--不参与 1--参与（新增）
  CREATETIME           timestamp,                    --操作时间
  primary key(id)
);

--7、采购计划附件
create table tbl_projectfile(
  ID                   NUMBER not null,              --主键
  PROJCODE             VARCHAR2(40),                 --寻源编号
  PROJECTID            NUMBER default 0 not null,    --寻源id
  attachedfile         varchar2(40) ,                --采购计划附件GUID
  suppvisible          NUMBER default 0 not null,    --附件是否对供应商可见    0-不对供应商可见   1-对供应商可见
  createtime           timestamp default sysdate,    --维护时间
  updatetime           timestamp,                    --最近维护时间
  username             number(8),                    --操作人编号
  userid               varchar2(40),                 --操作用户名
  remark               varchar2(2000)                --维护说明
);
alter table tbl_projectfile  add unique(PROJCODE,attachedfile);


--寻源过程之询比价   
--询价书表
-- Create table
create table T_ENQUIREBILL
(
  c_id               NUMBER not null,
  c_enqucode         VARCHAR2(40),
  c_enqutopic        VARCHAR2(200),
  c_subtime          DATE,
  c_sendtime         DATE,
  c_operaname        VARCHAR2(20),
  c_addr             VARCHAR2(100),
  c_zip              VARCHAR2(10),
  c_tel              VARCHAR2(100),
  c_fax              VARCHAR2(100),
  c_email            VARCHAR2(100),
  c_status           NUMBER,
  c_taxcode          NUMBER(19),
  c_attrtype         NUMBER(19) default 0,
  c_stocktype        NUMBER,
  c_projcode         VARCHAR2(40),
  c_type             NUMBER(1) default 0,
  c_project          NUMBER(19),
  c_mode             NUMBER,
  c_process          NUMBER,
  c_prequtotype      NUMBER(1) default 0,
  c_quotecurrency    NUMBER,
  c_pricetype        NUMBER,
  c_chargetype       NUMBER,
  c_iftax            NUMBER(1) default 0,
  c_quotedemand      VARCHAR2(2000),
  c_mobile           VARCHAR2(19),
  c_starttime        DATE,
  c_endtime          DATE,
  c_posttime         DATE,
  c_pay              NUMBER,
  c_payprocess       VARCHAR2(200),
  c_stocklevel       NUMBER(19) default 0,
  c_projectrange     NUMBER default 0,
  c_transorg         NUMBER(19),
  c_remark           VARCHAR2(2000),
  c_createdate       DATE,
  c_createuser       NUMBER(19),
  c_createdept       NUMBER(19),
  c_createorg        NUMBER(19),
  c_createcomp       NUMBER(19),
  c_lastupdate       DATE,
  c_lastupuser       NUMBER(19),
  c_lastupdept       NUMBER(19),
  c_lastuporg        NUMBER(19),
  c_lastupcomp       NUMBER(19),
  c_timezone         NUMBER(19) default 0,
  c_fileflag         NUMBER default 0,
  c_importflag       NUMBER default 0,
  c_standardword     VARCHAR2(40),
  c_startport        NUMBER,
  c_endport          NUMBER,
  c_payment          NUMBER(19),
  c_payterm          NUMBER(19),
  c_customer         NUMBER default 10,
  c_transportmode    NUMBER,
  username           VARCHAR2(100),
  c_pricevalidtime   DATE,
  c_contractsigntype NUMBER(1)
)
tablespace DATASPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 4M
    next 8K
    minextents 1
    maxextents unlimited
  )
nologging;
-- Add comments to the table 
comment on table T_ENQUIREBILL
  is '询价书';
-- Add comments to the columns 
comment on column T_ENQUIREBILL.c_id
  is '主键';
comment on column T_ENQUIREBILL.c_enqucode
  is '询价书编号';
comment on column T_ENQUIREBILL.c_enqutopic
  is '标题';
comment on column T_ENQUIREBILL.c_subtime
  is '提交时间';
comment on column T_ENQUIREBILL.c_sendtime
  is '发送时间                   发给供应商的时间';
comment on column T_ENQUIREBILL.c_operaname
  is '询价书联系人，默认是询价书编制人';
comment on column T_ENQUIREBILL.c_addr
  is '地址';
comment on column T_ENQUIREBILL.c_zip
  is '邮编';
comment on column T_ENQUIREBILL.c_tel
  is '电话';
comment on column T_ENQUIREBILL.c_fax
  is '传真';
comment on column T_ENQUIREBILL.c_email
  is '电子邮件';
comment on column T_ENQUIREBILL.c_status
  is '状态             发出、揭示等操作状态';
comment on column T_ENQUIREBILL.c_taxcode
  is '税率';
comment on column T_ENQUIREBILL.c_attrtype
  is '采购类型ID  对应T_code';
comment on column T_ENQUIREBILL.c_stocktype
  is '采购方式ID';
comment on column T_ENQUIREBILL.c_projcode
  is '寻源方案编号';
comment on column T_ENQUIREBILL.c_type
  is '单据是否有效     0-无效  1-有效';
comment on column T_ENQUIREBILL.c_project
  is '寻源方案id';
comment on column T_ENQUIREBILL.c_mode
  is '定商方式         （t-code来的值）';
comment on column T_ENQUIREBILL.c_process
  is '采购进度 ';
comment on column T_ENQUIREBILL.c_prequtotype
  is '是否需要预报价  0 否 1是';
comment on column T_ENQUIREBILL.c_quotecurrency
  is '报价币种         （从寻源方案带入，可调整）';
comment on column T_ENQUIREBILL.c_pricetype
  is '价格类型         （从寻源方案带入，可调整）';
comment on column T_ENQUIREBILL.c_chargetype
  is '运杂费及其他费用计费方式ID     （从寻源方案带入，可调整）  ';
comment on column T_ENQUIREBILL.c_iftax
  is '是否含税        0-不   1-含税 ';
comment on column T_ENQUIREBILL.c_quotedemand
  is '报价要求       （从寻源方案带入，可调整）';
comment on column T_ENQUIREBILL.c_mobile
  is '手机号';
comment on column T_ENQUIREBILL.c_starttime
  is '询价开始时间';
comment on column T_ENQUIREBILL.c_endtime
  is '询价截止时间  ';
comment on column T_ENQUIREBILL.c_posttime
  is '询价揭示时间';
comment on column T_ENQUIREBILL.c_pay
  is '货款支付方式   （从寻源方案带入，可调整）';
comment on column T_ENQUIREBILL.c_payprocess
  is '支付进度       （从寻源方案带入，可调整）';
comment on column T_ENQUIREBILL.c_stocklevel
  is '集采级别       （从寻源方案带入）';
comment on column T_ENQUIREBILL.c_projectrange
  is '寻源范围       （从寻源方案带入）';
comment on column T_ENQUIREBILL.c_transorg
  is '运输组织，1供应商组织/2询价单位组织/3収货单位组织/4使用单位组织     （从寻源方案带入）';
comment on column T_ENQUIREBILL.c_remark
  is '备注 ';
comment on column T_ENQUIREBILL.c_createdate
  is '编制时间';
comment on column T_ENQUIREBILL.c_createuser
  is '编制用户(对应T_User)';
comment on column T_ENQUIREBILL.c_createdept
  is '编制部门(对应T_Department)';
comment on column T_ENQUIREBILL.c_createorg
  is '编制组织架构(对应T_Organization)';
comment on column T_ENQUIREBILL.c_createcomp
  is '编制单位(对应T_Company) （新增）';
comment on column T_ENQUIREBILL.c_lastupdate
  is '最后修改时间';
comment on column T_ENQUIREBILL.c_lastupuser
  is '最后修改用户(对应T_User)    ';
comment on column T_ENQUIREBILL.c_lastupdept
  is '最后修改部门(对应T_Department)';
comment on column T_ENQUIREBILL.c_lastuporg
  is '最后修改组织架构(对应T_Organization) ';
comment on column T_ENQUIREBILL.c_lastupcomp
  is '最后修改单位(对应T_Company)';
comment on column T_ENQUIREBILL.c_timezone
  is '时区ID';
comment on column T_ENQUIREBILL.c_fileflag
  is '有无附件标识   0 无  1有';
comment on column T_ENQUIREBILL.c_importflag
  is '进口标识        0-否   1-是  ';
comment on column T_ENQUIREBILL.c_standardword
  is '贸易术语       （从寻源方案带入，可调整）';
comment on column T_ENQUIREBILL.c_startport
  is '起运港ID';
comment on column T_ENQUIREBILL.c_endport
  is '到货港ID';
comment on column T_ENQUIREBILL.c_payment
  is '结算方式T_code';
comment on column T_ENQUIREBILL.c_payterm
  is '结算条件';
comment on column T_ENQUIREBILL.c_customer
  is '采购主体';
comment on column T_ENQUIREBILL.c_transportmode
  is '运输方式的codeId';
comment on column T_ENQUIREBILL.c_pricevalidtime
  is '报价有效时间';
comment on column T_ENQUIREBILL.c_contractsigntype
  is '合同签订：0统谈统签、1统谈分签';
-- Create/Recreate indexes 



--询价书操作日志表（规范到统一的日志管理系统中）   
-- Create table
create table T_ENQUIREWORK
(
  c_id         NUMBER not null,
  c_enqucode   NUMBER(19),
  c_status     NUMBER(19),
  c_type       NUMBER(1),
  c_user       NUMBER(19),
  c_updatetime DATE,
  c_endtimeorg DATE,
  c_endtimenew DATE,
  c_remark     VARCHAR2(1000)
)
tablespace DATASPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 256K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column T_ENQUIREWORK.c_id
  is '主键';
comment on column T_ENQUIREWORK.c_enqucode
  is '询价书编号';
comment on column T_ENQUIREWORK.c_status
  is '状态';
comment on column T_ENQUIREWORK.c_type
  is '类型1--新单 2--修改 3--撤消 5--取消询价6--延时报价';
comment on column T_ENQUIREWORK.c_user
  is '用户';
comment on column T_ENQUIREWORK.c_updatetime
  is '更新时间';
comment on column T_ENQUIREWORK.c_endtimeorg
  is '原报价截止时间';
comment on column T_ENQUIREWORK.c_endtimenew
  is '新报价截止时间';
comment on column T_ENQUIREWORK.c_remark
  is '延时报价说明';


--采购报价书表
create table T_QUOTEBILL
(
  C_ID            NUMBER(19) not null,                 --主键ID      
  C_STRQUOTECODE  VARCHAR2(40),                        --报价书编号
  C_STRQUOTETOPIC VARCHAR2(60),                        --报价书标题       (来源于询价书，不允许供应商修改，保持4.0风格)
  C_STROPERANAME  VARCHAR2(20),                        --业务员姓名（从登录用户带入，可调整）
  C_STRADDR       VARCHAR2(100),                       --地址（从登录用户带入，可调整）
  C_STRZIP        VARCHAR2(10),                        --邮编（从登录用户带入，可调整）
  C_STRTEL        VARCHAR2(100),                       --电话（从登录用户带入，可调整）
  C_STRFAX        VARCHAR2(100),                       --传真（从登录用户带入，可调整）
  C_STREMAIL      VARCHAR2(100),                       --电子邮箱（从登录用户带入，可调整）
  C_STRMEASUNIT   VARCHAR2(20),                        --计量单位（不用）
  C_QUOTECURRENCY NUMBER,                              --报价币种 （带入询价书要求报价币种，新增）
  C_INTAMOUNTSUM  NUMBER(17,4) default 0,              --报价币种总金额
  C_BASECURRENCY       NUMBER(19) default 0,           --供应商本位币ID  （新增）
  C_CURRENCYRATE       NUMBER(10,4) default 0,         --供应商本位币与报价币种汇率 （新增）
  C_BASECURRENCYAMOUNT NUMBER(14,2) default 0,         --供应商本位币总金额（新增）
  C_INTAMOUNTQTY  NUMBER(17,4) default 0,              --数量合计     （不用）
  C_CREATEDATE    DATE,                                --编制时间：系统时间（新增）
  C_DASUBTIME     DATE,                                --报价提交时间
  C_DAOPENTIME    DATE,                                --报价打开时间 （不用）
  C_DAENDTIME     DATE,                                --报价有效期
  C_STREXPLAIN    VARCHAR2(400),                       --报价说明      
  C_STRTECHACCES  VARCHAR2(100),                       --是否有附件    改为有无附件标识 与寻源方案保持一致
  C_INTAFFIRM     NUMBER(1) default 0,                 --确认单标志 0--未确认 1--已确认 3--重新编制确认单  （不用）
  C_STRBUSIACCES  VARCHAR2(100),                       --商务附件名    （不用，调整为统一的附件记录）
  C_INTEXAM       NUMBER(1) default 0,                 --采购审批标志 0--未审批 1--已审批   （不用）
  C_INTSHOW       NUMBER(1) default 0,                 --显示标志 0--否 1--是   （不用）
  C_INTATTRTYPE   NUMBER(1) default 0,                 --属性   描述改为采购类型
  C_INTSTOCKTYPE  NUMBER(19),                          --采购类型ID    描述改为采购方式
  C_STRCLASSCODE  NUMBER(19) default 0,                --所属产品类代码   （不用）
  C_INTSTATUS     NUMBER(19),                          --报价书状态代码    改为C_STATUS
  C_SUPPLIER      VARCHAR2(40),                        --供应商  （不用）
  C_STRPROJCODE   NUMBER(19),                          --对应寻源方案编号
  C_ORG           NUMBER(19),                          --所属公司ID   （不用）
  C_ENQUCODE      NUMBER(19),                          --询价书ID
  C_QSOLUTION     VARCHAR2(10),                        --报价结果   （不用）
  C_SOLTIME       DATE,                                --报价结果发送时间   （不用）
  C_QUOTETYPE     NUMBER(1),                           --是否报价 0--没有报价 1--有报价  （不用）
  C_SCHEDULE      VARCHAR2(40),                        --采购进度   (字段名改为C_PROCESS) 
  C_ENQUTOPIC     VARCHAR2(40),                        --询价书名称   （不用）
  C_USER          VARCHAR2(20) not null,               --会员登录名ID  
  C_CREATEORG     NUMBER(19),                          --编制单位ID：供应商  (新增)
  C_CREATEDATE    DATE,                                --编制时间：系统时间   (新增)
  C_DEPT          NUMBER,                              --编制部门ID：供应商部门  (新增)
  C_UPDATETIME    DATE,                                --最近修改时间：系统时间  (新增)
  C_UPDATEORG     NUMBER(19),                          --最近修改单位ID：供应商单位  (新增)
  C_UPDATEDEPT    NUMBER(19),                          --最近修改部门ID：供应商部门  (新增)
  C_UPDATEUSER    NUMBER(19),                          --最近修改用户ID：供应商登录名  (新增)
  C_TIMEZONE      NUMBER(19) default 0,                --时区ID  
  primary key(c_id)  
);

--采购报价书明细表（按照物料编码排序）（增加报价可修改字段配置）
create table T_QUOTEBILL_PART
(
  C_ID             NUMBER(19) not null,                --主键ID
  C_INTPARTCODE    NUMBER(16),                         --序号   （不用）
  C_STRQUOTECODE   NUMBER(19),                         --报价书ID
  C_STRORDERCODE   VARCHAR2(20),                       --需求订单编号
  C_DASTARTCONTIME DATE,                               --起始交货期
  C_DAENDCONTIME   DATE,                               --结束交货期
  C_STRCONADDR     VARCHAR2(100),                      --交货地点
  C_STRCONSIGNEE   VARCHAR2(100),                      --收货单位
  C_STRPRODCODE    VARCHAR2(20),                       --产品代码
  C_STRPRODNAME    VARCHAR2(100),                      --产品名称
  C_STRMEASUNIT    VARCHAR2(20),                       --计量单位
  C_INTQTY         NUMBER(12,4),                       --数量
  C_QUOTECURRENCY  NUMBER,                             --报价币种 （带入报价书报价币种，新增）
  C_               number,                             --净价（新增，不含税，不含任何相关费用）
  C_PRICETYPE      NUMBER,                             --价格类型   （新增，从询价书带入）
  C_INTPRICE       NUMBER(12,4),                       --单价（按询价要求的价格类型报的价格）
  C_INTAMOUNT      NUMBER(15,4),                       --总价
  c_TAXCODE        VARCHAR2(6)                         --税码ID（与用户确认）
  C_               NUMBER,                             --运杂费及其他费用计费方式ID）（从寻源方案带入）  
  C_INTFREE        NUMBER(12,4),                       --运杂费
  C_INTOTHER       NUMBER(12,4),                       --其他费用
  C_INTALL         NUMBER(15,4),                       --总计
  primary key(c_id)  
);
