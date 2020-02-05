create table tbl_article (
	id                   	int  auto_increment     not null,           ##主键
	columnid             	int             	not null,           ##文章所属栏目ID
	siteid                  int                     not null,           ##文章所属站点ID     站点ID=1
	sortid               	int,                                        ##文章排列序号，自然序列号
	maintitle            	varchar(600)      	not null,           ##文章标题
	vicetitle            	varchar(600),                               ##文章副标题
	summary              	varchar(1000),                              ##文章描述
	keyword              	varchar(200),                               ##文章关键字
	source               	varchar(600),                               ##文章来源
	content              	text,                                       ##文章内容
	emptycontentflag     	smallint		not null,           ##文章内容是否为空  对于上传文件设置为1 其他设置为0
	author               	varchar(600),                               ##文章作者
	articleversion       	int,                                        ##文章版本号
	publishtime          	timestamp		not null,           ##文章发布时间
	createdate           	timestamp		not null,           ##文章创建时间
	lastupdated          	timestamp		not null,           ##文章最后修改时间
	dirname              	varchar(200)		not null,           ##文章路径
	filename             	varchar(60),                                ##文件名称
	editor               	varchar(30)        	not null,           ##编辑名称
	creator                 varchar(30)        	not null,           ##信息创建者名称
	status               	smallint   		not null,           ##文章状态      0-可用   1-未用
	doclevel             	int                 	not null,           ##文章主权重
	vicedoclevel		int,                                        ##文章副权重
	pubflag              	smallint   		not null,           ##文章发布标志位    0-新稿         1-发布稿
	auditflag            	smallint 		not null,           ##文章审核标志位    1-待审         0-审核完毕
	subscriber           	smallint   		not null,           ##文章订阅          0-不允许订阅   1-允许订阅
	lockstatus           	smallint 		not null,           ##文章锁定标志位    0-未锁定       1-锁定
	lockeditor           	varchar(30),                                ##文章锁定人
	auditor              	varchar(200),                               ##文章作者
	ispublished          	smallint 		not null,           ##文章是否曾经被发布过
	relatedartid         	varchar(40),                                ##未用
	indexflag            	smallint		not null,           ##文章是否被加入到全文索引数据库
	isjoinrss		smallint		not null,           ##文章是否进入RSS
	clicknum		int			not null,           ##文章被点击次数
	referID			int			not null,           ##未用
        multimediatype          smallint                default 0,          ##多媒体文章标志0-不是多媒体文章 1-是多媒体文章	
	modelID			int			not null,           ##文章使用的发布模板
	articlepic		varchar(600),                               ##文章使用的特效图片
	changepic               int                     default 0,          ##文章图片特效类型
	saleprice            	decimal(9,2),                               ##电子商务使用  商品售价
	inprice              	decimal(9,2),                               ##电子商务使用  商品进价
	marketprice          	decimal(9,2),                               ##电子商务使用  商品市场价
	vipprice          	decimal(9,2),	                            ##电子商务使用  商品VIP价
	stocknum             	int,                                        ##电子商务使用  商品库存量
	weight               	int,                                        ##电子商务使用  商品重量
	brand                	varchar(50),                                ##电子商务使用  商品品牌
	pic                  	varchar(30),                                ##电子商务使用  商品小图片
	bigpic               	varchar(30),                                ##电子商务使用  商品大图片
	score                   int,                                        ##电子商务使用  商品积分
	voucher                 decimal(9,2),                               ##电子商务使用  购物券
	urltype		        smallint                default 0,          ##未用
	defineurl		varchar(200),                               ##文章自定义URL
	notearticleid           int                     default 0,          ##定义与该篇文章相关联的其他文章的内容，其他文章的内容可以是本页面一个标签的内容
	wordsnum		int                     default 0,          ##文章文字数量
	downfilename	        varchar(100),                               ##未用
	beidate                 timestamp,                                  ##未用    
        salesnum                int,                                        ##商品销售数量
        commentnum              int                     default 0,          ##文章评论的数量
	fromsiteid              int                     default 0,          ##标识抓取信息的来源站点
	sarticleid              varchar(50),                                ##抓取文章来源网站的ID 
        md5title                varchar(35),                                ##文章标题的MD5格式摘要
        mediafile               varchar(500),                               ##多媒体文件名称
	processofaudit          int                     default 0,          ##被审核文章执行到的审核步骤
	t1                      int                     default 0,          ##分类1
	t2                      int                     default 0,          ##分类2
	t3                      int                     default 0,          ##分类3
	t4                      int                     default 0,          ##分类4
	t5                      int                     default 0,          ##分类5
	deptid                  varchar(50),                                ##文章发布的部门ID
	sign                    varchar2(32),                               --文章的数字签名，输入的文章为文章标题+ID的数字签名，抓取文章为文章的URL数字签名
        primary key (id)
);

create table TBL_ARTICLE_EXTENDATTR
(
        id                            int  auto_increment     not null,      ##文章扩展属性主键
        articleid                     int                     not null,      ##文章ID
        ename                         varchar(50)             not null,      ##扩展属性名称
        type                          smallint                not null,      ##扩展属性类型
        textvalue                     text,                                  ##存储文本值
        stringvalue                   varchar(4000),                         ##存储字符串
        numericvalue                  int,                                   ##存储整型
        floatvalue                    decimal(9,2),                          ##存储浮点型
  	CONSTRAINT fk_articleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)
);


create table tbl_members (
        id                      int	  auto_increment not null,         ##主键
        userid       		varchar(30)   		not null unique,   ##用户ID
	mmuserid                varchar(32),                          
        siteid       		int      		not null,     ##站点ID
        userpwd      		varchar(32)   		not null,     ##用户口令  MD5格式
        nickname     		varchar(30)		not null,     ##真实姓名
        email                   varchar(50),                          ##电子邮件
        mphone                  varchar(12),                          ##手机号码
        phone                   varchar(12),                          ##联系电话
        sex                     smallint                default 0,    ##0表示男 1表示女
        country                 varchar(30),                          ##国家
        province                varchar(50),                          ##省
        city                    varchar(50),                          ##市
        zone                    varchar(50),                          ##区县
        jiedao                  varchar(50),                          ##街道
        shequ                   varchar(50),                          ##社区
	area                    varchar(50),                          ##工作领域
        postcode                varchar(6),                           ##邮政编码
        birthdate               date,                                 ##出生日期
        myimage                 varchar(100),                         ##头像
        usertype                smallint                default 0,    ##用户类型    0---系统管理用户   5--网站注册用户  6--企业内部业务用户        
        joincompanyid           int,                                  ##加盟商ID号
        joinid                  int,                                  ##加盟商序列号
        dflag                   int,                                  ##站点是否被删除
        createarticles	        int                     default 0,    ##用户创建文章数量
        editarticles		int                     default 0,    ##用户修改文章数量
        deletearticles	        int                     default 0,    ##用户删除文章数量
        score                   int                     default 0,    ##用户积分
        createdate              timestamp,                            ##用户信息创建时间
	lastupdate              timestamp,                            ##用户信息最后修改时间
        company                 varchar(200),                         ##用户工作单位
        address                 varchar(500),                         ##工作单位地址
        department              varchar(50),                          ##所属部门
        emailaccount            varchar(100),                         ##email帐号
        emailpasswd             varchar(100),                         ##email密码
        departmentarticlestype  int                     default 0,    ##审核文章的范围0-所有部门1-本部门2-指定部门
        departmentarticlesids   varchar(500),                         ##指定审核文章的部门id   
	trypassnum              int,                                  ##允许登录试错的次数
	trypasstime             date,                                 ##达到试错次数后的锁定时间
	orgid                   int,                                  ##用户所属组织架构ID
	companyid               int,                                  ##用户所属公司ID
	deptid                  int,                                  ##用户所属部门ID
	CREATERID               int,                                  ##用户创建者ID
        primary key (id)
);


CREATE TABLE tbl_orders(                                                    ##定单表
         id                       bigint   auto_increment     not null,     ##主键
	 SITEID                   int,				            ##站点id
	 ORDERID                  bigint   not null unique,                 ##定单号
	 initorderid              int      default 0,                       ##原始定单号(拆单=原单号，非拆单=0)
	 USERID                   int,	     			            ##用户id
	 NAME                     varchar(50),		                    ##收货人姓名
	 SEX                      int,				            ##性别
	 ADDRESS                  varchar(1000),	                    ##地址
	 POSTCODE                 varchar(20),		                    ##邮编
	 PHONE                    varchar(50),		                    ##电话
	 TOTALFEE                 double(9,2),		                    ##定单总额
	 DELIVERYFEE              double(9,2),		                    ##邮寄费用
	 PAYFEE                   double(9,2),		                    ##应付费用
	 VALID                    int      default 0      not null,		
	 STATUS                   int                     not null,	    ##定单状态
	 BILLINGWAY               int,				            ##支付方式
	 CITY                     varchar(50),			            ##城市
	 RECEIVE                  int,				
	 NOUSE                    int      default 1      not null,	    ##定单是否有效 1-有效 0-无效
	 PROVINCE                 varchar(50),			            ##省份
	 zone                     varchar(50),			            ##地区
	 sendway                  int,				            ##配送方式
	 commissioncharge         double(9,2),		                    ##折扣
	 insurance                double(9,2),		                    ##保险费
	 need_invoice             int        default 0     not null,	    ##是否需要发票  0不需要  1增值税票  2普通票
	 bill_to_address          varchar(2000),	                    ##发票地址
	 notes                    varchar(4000),	                    ##发票信息
	 flag                     smallint   default 0     not null,
	 payflag                  smallint   default 0     not null,        ##是否已经支付   0-未支付  1-已经支付
	 payway                   int,		                            ##付款方式
	 paydate                  timestamp,		                    ##付款时间
	 linktime                 varchar(50),			
	 usescores                int        default 0,	                    ##用户使用积分
	 orderscores              int        default 0,                     ##定单产生的积分 
	 CREATEDATE               timestamp,		                    ##创建时间
	 lastupdate               timestamp,		                    ##订单最后时间
	 jylsh                    varchar(200),                             ##从支付网关返回的支付网关方的交易流水号
	 paycompno                int,                                      ##第三支付网关编号
	 zfmemberid               varchar(200),                             ##支付中心会员ID
	 r2type                   varchar(10),                              ##支付中心返回类型，1浏览器重定向 2服务器点对点
	 payresult                varchar(50),                              ##支付结果，成功/失败/其他
	 primary key(id) 
);

##定单详细信息表
CREATE TABLE tbl_orders_detail( 
	 ID                       bigint auto_increment        not null,    ##主键
	 ORDERID                  bigint                       not null,    ##定单号
	 supplierid               int      default 0        not null,       ##供货商id(对应每个供货商siteid)
	 suppliername             varchar(100),                             ##供货商名称
	 PRODUCTID                int,				            ##商品id
	 ORDERNUM                 int,				            ##商品数量
         unit                     varchar(10),                              ##商品计量单位
	 SALEPRICE                double(9,2),		                    ##商品售价
	 WEIGHT                   int,				            ##商品重量
	 CREATEDATE               timestamp,			            ##创建时间
	 productname              varchar(200),	                            ##商品名称
	 productcode              varchar(200),                             ##商品编码
	 type                     varchar(100),	                            ##商品类型（扩展属性）
	 cardid                   int      default 0        not null, 	    ##使用购物卷id 
	 userinstarttime          timestamp,                                ##用户输入的开始日期
	 userinendtime            timestamp,                                ##用户输入的结束日期
	 servicestarttime         timestamp,                                ##根据规则计算出的服务类采购起始日期
	 serviceendtime           timestamp,                                ##根据规则计算出的服务类采购结束日期
	 subscribe                smallint,                                 ##服务订阅类型
         subscribenum             int,                                      ##服务订购的期数
	 CONSTRAINT fk_orderID FOREIGN KEY (ORDERID) REFERENCES  TBL_ORDERS(ORDERID) ON DELETE CASCADE,	 	 
	 primary key(id)
);

##具体订单的发票信息表
create table tbl_invoiceinfoForOrder
(
       id                 int           auto_increment,
       siteid             int           default 0,           ##站点id
       orderid            bigint        not null,            ##订单ID
       userid             int           default 0,           ##用户id
       orderid            int           default 0,           ##用户订单号
       invoicetype        int           default 0,           ##发票类型(0-普通发票1-增值税发票)
       title              int           default 0,           ##发票抬头（个人/单位0-个人1-单位）
       companyname        varchar(200),                      ##单位名称
       content            int           default 0,           ##发票内容(外键内容id)
       identification     varchar(200),                      ##纳税人识别号
       registeraddress    varchar(200),                      ##注册地址
       phone              varchar(50),                       ##注册电话
       bankname           varchar(200),                      ##开户银行
       bankaccount        varchar(100),                      ##银行帐号
       createdate         timestamp,                         ##创建时间
       primary key(id)
);

##具体订单的发票内容维护表
create table tbl_invoicecontentForOrder
(
       id                 int  auto_increment not null, 
       siteid             int                 default 0,     ##站点id
       orderid            bigint        not null,            ##订单ID
       invoiceid          int                 not null,      ##发票ID
       guige              varchar(100),                      ##规格
       unit               varchar(100),                      ##单位                      
       num                int,                               ##购买数量
       danprice           float(10,2),                       ##单价
       content            varchar(200)        not null,      ##内容
       createdate         timestamp,                         ##创建时间
       primary key(id)
);

##订单的具体送货地址
create table tbl_addressinfoForOrder(
        id                        int auto_increment  not null,       ##主键
        ORDERID                   bigint              not null,       ##定单号  
	userid                    int                 not null,       ##用户id
        name                      varchar(200),                       ##收货人姓名
        provinces                 varchar(50),			      ##省份
        city                      varchar(50),			      ##城市
        zone                      varchar(50),                        ##地区
        mobile                    varchar(50),                        ##移动电话
        phone                     varchar(50),                        ##固定电话
        zip                       varchar(50),                        ##邮编
        address                   varchar(200),                       ##详细地址
	createdate                timestamp,                          ##创建时间
	primary key(id)
);
