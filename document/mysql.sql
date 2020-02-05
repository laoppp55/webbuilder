create table bbs_thread(
	threadid	int		not null,
	lyhid		int,
	forumid		int,
	answerid	int             default 0,
	threadname	varchar(500),
	threadcontent	varchar(500),
	posttime	timestamp,
	author		varchar(500),
	ipaddress	varchar(500),
	answeruser	varchar(500),
	answertime	timestamp,
	hitnum		int,
	answernum	int,
	gotop		int             default 0,
	typeflag	int,
	hiddenflag	int             default 0,
	lockflag	int,
	primary key(threadid) 
);

create table bbs_column(
	id		int		not null,
	lyhid		int,
	bbsname		varchar(500),
	bbsdesc		varchar(500),
	manager		varchar(500),
	managerpass	varchar(500),
	postman		varchar(500),
	postdate	timestamp,
	lastposter	varchar(500),
	lasttopic	varchar(500),
	lastposttime	timestamp,
	pic		varchar(500),
	creationdate	timestamp,
	threadnum	int,
	topicnum	int,
	siteid		int,
	primary key(id)
);


create table bbs_online(
	id		varchar(500)		not null,         ##主键ID
	lyhid		int,                                      ##登录用户ID
	username	varchar(500),                             ##登录用户名
	logintime	timestamp,                                ##最后登录时间
	loginnum        int,                                      ##登录次数
	loginstatus     int,                                      ##登录状态
	lastactivetime  timestamp,                                ##用户最后活动时间
	ipaddress	varchar(500)                              ##登录者的IP地址
);


create table bbs_friend(
	userid		varchar(500),
	lyhid		int,
	friend		varchar(500)
);

create table tbl_sites_number
(
	ipaddress		varchar(15)		not null,
	sitesnum		int,
	hashcode                int,
        wwwip                   varchar(15),
        docroot                 varchar(500),
        ftpuser                 varchar(500),
        ftppasswd               varchar(500),
        flag                    int,
        privateip               varchar(15)
)

insert into tbl_sites_number(ipaddress,sitesnum,hashcode) values('127.0.0.1',18,0);

create table tbl_right (
	rightid	               int               not null,
	rightname              varchar(30)	 not null,
	rightcat               varchar(30),
	rightdesc              varchar(100),
        primary key (rightid)
);

insert into tbl_right values(1,'栏目管理','CM','栏目管理');
insert into tbl_right values(2,'模板管理','MM','模板管理');
insert into tbl_right values(3,'文章管理','AM','文章编辑');
insert into tbl_right values(4,'上传文件','FU','上传文件');
insert into tbl_right values(5,'审核文件','FA','审核文件');
insert into tbl_right values(6,'发布文章','PA','发布文章');
insert into tbl_right values(7,'移动文章','MA','移动文章');
insert into tbl_right values(50,'读文章','AR','读文章');
insert into tbl_right values(51,'写文章','AW','写文章');
insert into tbl_right values(52,'删文章','AD','删文章');
insert into tbl_right values(54,'站点管理','WM','站点管理');
insert into tbl_right values(55,'文件编辑','FE','文件编辑');
insert into tbl_right values(56,'文件删除','FD','文件删除');
insert into tbl_right values(57,'留言板管理','LYB','留言板管理');
insert into tbl_right values(58,'网上调查','SW','网上调查');
insert into tbl_right values(59,'注册用户管理','RU','注册用户管理');
insert into tbl_right values(60,'企业用户管理','ERU','企业用户管理');
insert into tbl_right values(61,'工作日管理','WD','工作日管理');
insert into tbl_right values(62,'业务审批结果','YWR','业务审批结果');
insert into tbl_right values(100,'订单查询','ddcx','订单查询');
insert into tbl_right values(101,'订单导出','zcyhcx','订单导出');
insert into tbl_right values(102,'银行对账','yhdz','银行对账');
insert into tbl_right values(103,'支付查询','zfcx','支付查询');
insert into tbl_right values(104,'注册用户查询','zcyhcx','注册用户查询');
insert into tbl_right values(105,'注册用户导出','zcyhdc','注册用户导出');
insert into tbl_right values(106,'导出本单位及下属单位订单','dcbdjxsdwdd','导出本单位及下属单位订单');


create table tbl_role (
	roleid                  int                     not null,
	rolename                varchar(30)             not null,
	rolecat                 varchar(30),
	roledesc                varchar(100),
        primary key (roleid)
);

insert into tbl_role values(0,'信息员','XXY','负责向网站录入信息');
insert into tbl_role values(1,'部门领导','BML','录入信息的审核');
insert into tbl_role values(2,'主管领导','ZGL','录入信息的审核');
insert into tbl_role values(3,'系统管理员','SM','超级系统管理员');
insert into tbl_role values(4,'留言版管理员','LWM','留言版管理员');
insert into tbl_role values(5,'部门留言版管理员','DLWM','部门留言版管理员');

create table tbl_sjs_log (
	id			int	auto_increment	not null,               ##主键
	siteid			int     		not null,               ##站点ID           
	sitename		varchar(50)		not null,               ##站点名称
        columnid                int,                                            ##栏目ID
        columnname              varchar(50),                                    ##栏目名称
	articleid		int			not null,               ##文章ID
	maintitle		varchar(600)	        not null,               ##文章中文标题
        articleurl              varchar(300),                                   ##文章URL
        operationtype           smallint,                                       ##文章操作类型    1-增加   2-修改 3-删除 
        editor                  varchar(50),                                    ##文章作者
	createdate	        timestamp,                                      ##文章录入日期
        updatedate              timestamp,                                      ##文章修改日期
        deletedate              timestamp,                                      ##文章删除日期
        primary key (id)
);


create table tbl_log (
	id                      int	 auto_increment not null,
	articleid               int	         not null,
	siteid	                int              not null,
	columnid                int              not null,
	editor                  varchar(30)      not null,
	acttype                 smallint         not null,
	acttime	                timestamp             not null,
	maintitle               varchar(600)     not null,
	createdate              timestamp,
        primary key (id)
);

create table TBL_USERINFO
(
  ID               NUMBER not null,              ##用户ID
  USERNAME         VARCHAR2(50),                 ##用户登录名
  PASSWD           VARCHAR2(50),                 ##用户登录口令
  FIRSTNAME        VARCHAR2(50),                 ##中文姓
  GAVENAME         VARCHAR2(50),                 ##中文名
  CNAME            VARCHAR2(50),                 ##拼音中文姓
  CSNAME           VARCHAR2(50),                 ##拼音中文名
  CNNAME           VARCHAR2(50),                 ##
  CNNAME1          VARCHAR2(50),                 ##
  PSTATUS          VARCHAR2(50),
  SEX              NUMBER default 0,             ##性别
  CALLS            VARCHAR2(100),                ##称呼，先生、女士、教授、博士等
  COMPANY          VARCHAR2(100),                ##所在单位名称
  DUTIES           VARCHAR2(100),                ##职务
  ADDRESS          VARCHAR2(200),                ##详细地址
  GUOJIA           VARCHAR2(100),                ##国家
  SHENGSHI         VARCHAR2(200),                ##省市
  SHIXIAN          VARCHAR2(200),                ##市县
  XXADDRESS        VARCHAR2(200),                ##联系人详细地址
  HOTELSID         VARCHAR2(50),                 ##宾馆登记ID，身份证ID、护照ID等
  SHUOMING         VARCHAR2(200),                ##特别说明
  XUQIU            VARCHAR2(500),                ##特别需求
  QINGZHEN         NUMBER default 0,             ##需要清真食品，0-否、1-是
  SUSHI            NUMBER default 0,             ##是否要素食，  0-否、1-是
  QTSHUOMING       VARCHAR2(500),                ##未用
  YAOQING          NUMBER default 0,             ##是否邀请
  SPECIALSHUOMING  VARCHAR2(500),                ##特别情况说明
  PEITONG          NUMBER default 0,             ##陪同人数，
  PTXING           VARCHAR2(100),                ##陪同姓
  PTMING           VARCHAR2(100),                ##陪同名
  PTSET            NUMBER default 0,             ##陪同性别
  PTZHENGHAO       VARCHAR2(100),                ##陪同证件号码，护照号或者身份证号等
  PTXING1          VARCHAR2(100),
  PTMING1          VARCHAR2(100),
  PTSET1           NUMBER default 0,
  PTZHENGHAO1      VARCHAR2(100),
  PTXING2          VARCHAR2(100),
  PTMING2          VARCHAR2(100),
  PTSET2           NUMBER default 0,
  PTZHENGHAO2      VARCHAR2(100),
  PTSHUOMING       VARCHAR2(500),
  YNEMAIL          VARCHAR2(50),
  PTTITLE          VARCHAR2(50),
  PTGUANXI         VARCHAR2(50),
  PTGUOJI          VARCHAR2(100),
  PTDATE           VARCHAR2(50),
  PTBIRTH          VARCHAR2(50),
  PTBIRTHZONE      VARCHAR2(50),
  PTHOTEL          VARCHAR2(50),
  PTTITLE1         VARCHAR2(50),
  PTGUANXI1        VARCHAR2(50),
  PTGUOJI1         VARCHAR2(100),
  PTDATE1          VARCHAR2(50),
  PTBIRTH1         VARCHAR2(50),
  PTBIRTHZONE1     VARCHAR2(50),
  PTHOTEL1         VARCHAR2(50),
  PTTITLE2         VARCHAR2(50),
  PTGUANXI2        VARCHAR2(50),
  PTGUOJI2         VARCHAR2(100),
  PTDATE2          VARCHAR2(50),
  PTBIRTH2         VARCHAR2(50),
  PTBIRTHZONE2     VARCHAR2(50),
  PTHOTEL2         VARCHAR2(50),
  FEIYONG1         VARCHAR2(100),
  FEIYONG2         VARCHAR2(100),
  FEIYONG3         VARCHAR2(100),
  FEIYONG4         VARCHAR2(100),
  FEIYONG5         VARCHAR2(100),
  FEIYONG6         VARCHAR2(100),
  ZFEIYONG         VARCHAR2(100),
  ACTIVE0          NUMBER default 0,
  ACTIVE0PERSON    NUMBER default 0,
  ACTIVE3          NUMBER default 0,
  ACTIVE3PERSON    NUMBER default 0,
  LINE3            NUMBER default 0,
  LINE3PERSON      NUMBER default 0,
  LINE4            NUMBER default 0,
  LINE4PERSON      NUMBER default 0,
  LINE5            NUMBER default 0,
  LINE5PERSON      NUMBER default 0,
  LINE6            NUMBER default 0,
  LINE6PERSON      NUMBER default 0,
  PTREQUEST        VARCHAR2(500),
  INVOICINGREQUEST VARCHAR2(500),
  ZONGREQUEST      VARCHAR2(500),
  POSTCODE         VARCHAR2(50),
  LINKNAME         VARCHAR2(100),
  PHONE            VARCHAR2(50),
  MOBILE           VARCHAR2(50),
  FAX              VARCHAR2(50),
  EMAIL            VARCHAR2(100),
  NATIONALITY      VARCHAR2(50),
  CARDTYPE         NUMBER default 0,
  CARDNUM          VARCHAR2(50),
  CARDDATE         VARCHAR2(50),
  BIRTHDAY         VARCHAR2(50),
  BIRTHZONE        VARCHAR2(100),
  ACCOMMODATION    NUMBER default 0,
  INDATE           VARCHAR2(50),
  LEFTDATE         VARCHAR2(50),
  HOTELS           VARCHAR2(100),
  SHARINGNAME      VARCHAR2(200),
  BEDTYPE          NUMBER default 0,
  ARRIVEDATE       VARCHAR2(50),
  AFLIGTNUM        VARCHAR2(100),
  ADATE            VARCHAR2(100),
  LLEFTDATE        VARCHAR2(50),
  LFIGHTNUM        VARCHAR2(100),
  LDATE            VARCHAR2(50),
  SPECIALREQUEST   VARCHAR2(500),
  ACTIVE1          NUMBER default 0,
  ACTIVE1PERSON    NUMBER default 0,
  ACTIVE2          NUMBER default 0,
  ACTIVE2PERSON    NUMBER default 0,
  LINE1            NUMBER default 0,
  LINE1PERSON      NUMBER default 0,
  LINE2            NUMBER default 0,
  LINE2PERSON      NUMBER default 0,
  TOTALFEE         NUMBER(2,9) default 0,
  PAYWAY           NUMBER default 0,
  INVOICENEEDED    NUMBER default 0,
  INVOICINGADDRESS VARCHAR2(500),
  FLAG             NUMBER default 0,
  LANGUAGETYPE     VARCHAR2(50),
  UPDATETIME       DATE default sysdate,
  AUDITTIME        DATE,
  CREATEDATE       DATE default sysdate,
  JNFEIYONG        VARCHAR2(100),
  ACTIVE4          NUMBER default 0,
  LINE             NUMBER default 0,
  ACTIVE4PERSON    NUMBER default 0,
  XINCITY          VARCHAR2(100),
  XINCITY1         VARCHAR2(100),
  XINCITY2         VARCHAR2(100),
  XINCITY3         VARCHAR2(100),
  TYPE             NUMBER default 0,
  YHFLAG           NUMBER default 0
)

create table tbl_ assistant (
        id                      int	        auto_increment not null,         ##主键
	uid                     int             not null,                        ##用户ID
        ptxing                  varchar(100),                                    ##陪同姓
        ptming                  varchar(100),                                    ##陪同名
        ptsex                   number          default 0,                       ##陪同性别
        ptzhengjianhao          varchar(100),                                    ##陪同证件号码，护照号或者身份证号等

)

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
	zone                    varchar(200),
        createdate              timestamp,                            ##用户信息创建时间
	lastupdate              timestamp,                            ##用户信息最后修改时间
        primary key (id)
);
insert into tbl_members (userid,siteid,userpwd,nickname,email,mphone,joincompanyid,joinid,dflag,createarticles,editarticles,deletearticles,score,createdate,company,address) values('admin',-1,'4346D362F67C89D7AB4E28732A1B1CE8','系统管理员','','',0,0,0,0,0,0,0,current_date(),'','');

##系统角色表
create table tbl_member_roles (
       id                       int	 auto_increment not null,
       siteid                   int                     not null,
       userid                   varchar(30)             not null,
       deptid                   int                     not null,      ##用户所属部门ID       
       rolename                 varchar(20)             not null,      ##信息员   部门负责人   主管
       rolelevel                smallint,                              ##角色级别 0-9
       primary key(id)
);

##授权用户管理的留言表
create table tbl_member_authorized_resouce (
       id                       int auto_increment         not null,
       siteid                   int                        not null,
       userid                   varchar(30)                not null,
       lwid                     int,                                     ##该用户管理的留言表ID
       lwname                   varchar(100),                            ##该用户管理的留言表的名称
       lwrole                   varchar(20),                             ##留言板管理角色 是某个留言板的管理员 或是留言板的部门管理员
       contenttype              int                         default 0,  ##内容类型 0为留言板
       primary key(id)
);

-- 组织架构表
create table tbl_organization
(
       id                      int auto_increment not null,              ##主键ID
       siteid                  int,
       parent                  int,                                      ##父ID
       cotype                  int default 0,                            ##节点类型 1--子公司   2--部门
       customerid              int,                                      ##客户ID，对应着站点ID
       orderid                 int,                                      ##节点在同层中的序号
       nodeid                  int,                                      ##节点对应子公司、部门
       status                  int default 1,                            ##状态 0 无效 1 有效
       name                    varchar(255) not null,                    ##节点名称
       ename                   varchar(255),                             ##节点英文名称
       llevel                  int default 1 not null,                   ##组织结构层级
       orgcode                 varchar(50),                              ##组织节点编码
       isleaf                  smallint default 0,                       ##是否是叶子节占，0为非叶子节点，1为叶子节点
       lastupdate              timestamp default CURRENT_TIMESTAMP,      ##最后修改日期
       createdate              timestamp default CURRENT_TIMESTAMP,      ##创建日期
       createuser              varchar(50),                              ##创建人
       updateuser              varchar(50),                              ##修改人
       primary key(id)
);

##公司信息表
create table tbl_companyinfo(
       ID                      int auto_increment not null,              ##主键
       orgid                   int,                                      ##公司所属组织ID
       SITEID                  int,                                      ##公司站点ID 
       COMPANYNAME             varchar(100),                             ##公司名称
       companyenname           varchar(100),                             ##公司英文名称
       opendate                date,                                     ##公司成立日期
       COMPANYADDRESS          varchar(100),                             ##公司所在地址
       COMPANYPHONE            varchar(50),                              ##公司联系电话
       COMPANYFAX              varchar(50),                              ##公司联系传真
       COMPANYWEBSITE          varchar(100),                             ##公司站点名称
       COMPANYEMAIL            varchar(100),                             ##公司email地址
       POSTCODE                varchar(20),                              ##公司邮政编码
       companyclassid          int,                                      ##企业所属分类       
       CLASSIFICATION          varchar(200),                             ##公司所属分类名称
       SUMMARY                 varchar(1000),                            ##公司描述 
       COMPANYPIC              varchar(200),                             ##公司图片
       PUBLISHFLAG             int,                                      ##发布标记
       status                  smallint default 0,                       ##状态  0--对外发布   1-不对外发布
       COUNTRY                 varchar(12),                              ##公司所在国家
       PROVINCE                varchar(12),                              ##公司所在省份
       CITY                    varchar(12),                              ##公司所在市
       ZONE                    varchar(12),                              ##公司所在区县
       TOWN                    varchar(12),                              ##公司所在乡镇
       VILLAGE                 varchar(12),                              ##公司所在村
       COMPANYLATITUDE         decimal(38,15),                           ##公司所在地的经度
       COMPANYLONGITUDE        decimal(38,15),                           ##公司所在地的纬度
       MPHONE                  varchar(12),                              ##联系人移动电话
       BIZPHONE                varchar(12),                              ##联系人电话
       CONTACTOR               varchar(12),                              ##联系人姓名
       SAMSITEID               int,                                      ##样例站点ID
       COMPANYLEVEL            int,                                      ##公司级别
       qq                      varchar(50),                              ##公司QQ
       weixin                  varchar(50),                              ##公司微信
       weibo                   varchar(50),                              ##公司微博
       createdate              timestamp,                                ##创建日期
       updatedate              timestamp,                                ##修改日期
       createuser              varchar(50),                              ##创建人
       COMPCODE                varchar(50),                              ##公司编码
       LEGAL                   varchar(500),                             ##公司法人
       COMPSHORTNAME           varchar(200),                             ##公司短名称
       ALIASFLAG               int,                                      ##该名称是一个公司的别名,1表示公司主名称，0表示别名
       primary key(id)
);

##企业部门设置
create table tbl_department (
       id                      int auto_increment not null,
       orgid                   int,                                      ##公司所属组织ID
       companyid               int                not null,              ##所属公司ID
       siteid                  int                not null,              ##所属站点ID
       cname                   varchar(100)       not null,              ##部门中文名称
       ename                   varchar(100),                             ##部门英文名称
       telephone               varchar(12),                              ##部门联系电话
       manager                 varchar(50),                              ##部门经理
       vicemanager             varchar(50),                              ##部门副经理
       leader                  varchar(50),                              ##部门主管领导
       lastupdate              timestamp,                                ##最后修改日期
       createdate              timestamp,                                ##创建日期
       createuser              varchar(50),                              ##创建人
       EMAIL                   varchar(200),                             ##部门联系邮件 
       SHORTNAME               varchar(200),                             ##部门简称
       primary key(id)
);          


##每个用户在每个站点的每个栏目中录入的文章数量
create table tbl_user_articlebycolumn (
       id                       int   auto_increment    not null,
       columnid                 int                     not null,
       userid                   varchar(30)             not null,
       siteid                   int                     not null,   
       articlecount             int    default 0 ,
       primary key(id)
);
       
create table tbl_members_rights (
        userid                  varchar(30)    	not null,
        columnid                int             not null,
        rightid                 int             not null
);

create table tbl_group_rights (   	
	groupid	                int             not null,
        columnid                int,
        rightid                 int
);

create table tbl_group_members (
	userid                  varchar(30)    	not null,
	groupid                 int             not null
);

create table tbl_group (
	groupid                 int  auto_increment  not null,
	siteid                  int                  not null,
	groupname   		varchar(20)          not null,
	groupdesc   		varchar(100),
        primary key (groupid)
);

create table tbl_article_auditing_info (
	id                      int   auto_increment not null,
	articleid               int          	     not null,
	sign                    varchar(30)    	     not null,
	comments                varchar(500),
	messagetype             int default 0,
	status                  int       	     not null,
	backto                  varchar(30),
	createdate              timestamp                 not null,
        primary key (id)
);

create table tbl_column_auditing_rules (
	id                      int  auto_increment  not null,             ##主键
	columnid                int                  not null,             ##栏目ID
	column_audit_rule       varchar(300)	     not null,             ##栏目审核规则
	messagetype             int  default 0,                            ##0表示栏目文章信息  1表示用户留言信息
	audittype               int  default 0,                            ##定义审核方式 0-按用户名称定义审核规则   1-按角色定义审核规则
	version                 int  default 0,                            ##审核规则版本号，最大值表示当前版本
	createdate              timestamp	     not null,
	lastupdated             timestamp            not null,
	creator                 varchar(30)          not null,
	editor                  varchar(30)          not null,
        primary key (id)
);

##按“与”关系划分的审核操作路径
create table tbl_column_auditing_process (
	id                   	int  auto_increment  not null,
	rulesid             	int                  not null,
	subrules                varchar(300),                            ##分解后的OR审核规则
	createdate           	timestamp,
	lastupdated          	timestamp,
	creator              	varchar(30)	     not null,
	editor               	varchar(30)	     not null,
        primary key (id)
);


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
	topflag                 smallint                default 0           ##置顶标志，1表示置顶 0-正常顺序
	redflag                 smallint                default 0           ##飘红标志，1表示飘红 0-正常
	boldflag                smallint                default 0           ##加黑标志，1表示加黑 0-正常
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
	sign                    varchar(32),                                ##文章的数字签名，输入的文章为文章标题+ID的数字签名，抓取文章为文章的URL数字签名
        sourceurl               varchar(1000),                              ##被采集信息的URL
	primary key (id)
);

create table TBL_ARTICLE_EXTENDATTR
(
        id                            int  auto_increment     not null,
        articleid                     int                     not null,
        ename                         varchar(50)             not null,
        type                          smallint                not null,
        textvalue                     text,
        stringvalue                   varchar(4000),
        numericvalue                  int,
        floatvalue                    decimal(9,2),
  	CONSTRAINT fk_articleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)
);

create table tbl_recommend_article
(
        ID                      int auto_increment          not null,        --主键ID
        siteid		        int                         not null,        --站点ID
        columnid	        int                         not null,        --栏目ID
        modelid                 int                         not null,        --模板ID
	markid                  int                         not null,        --标记ID
	markname                varchar2(500),                               --标记名称
        articleid               int                         not null,        --文章ID
	createdate              timestamp,                                   --创建时间
	lastupdate              timestamp,                                   --修改时间
        primary key (id)
);


--被抓取文件与信息源相关的信息
create table tbl_article_sourceinfo
(
        id                            int auto_increment   not null,
        articleid                     int                  not null,
	maintitle            	      varchar(600)         not null,           ##文章标题        
	md5title                      varchar(35),                             ##文章标题的MD5格式摘要
	publishtime_from_source       datetime		   not null,           ##文章在被抓取网站的发布时间
	sourceid                      int,                                     ##抓取文章来源网站的ID 
        createdate                    datetime,                                ##信息被抓取的时间
        primary key (id)        
);

create table tbl_attachment_class (
	id                   	int auto_increment         not null,            ##主键ID 栏目ID
	siteid               	int                        not null,            ##站点ID
	cname                	varchar(50)        	   not null,            ##分类中文名称
	summary                	varchar(500)        	   not null,            ##分类描述
	editor               	varchar(30)                not null,            ##编辑
	createdate           	timestamp                  not null,            ##创建日期
	lastupdated          	timestamp		   not null,            ##最终修改日期
        primary key (id)
);

create table tbl_attachment_class_part (
	id                   	int auto_increment         not null,            ##主键ID 栏目ID
	classid                 int                        not null,            ##附件分类主表ID
	siteid               	int                        not null,            ##站点ID
	cname                	varchar(50)        	   not null,            ##附件类别中文名称
	ename                	varchar(50)        	   not null,            ##附件类别英文名称
	cltype              	varchar(10)                not null,            ##附件类型
	editor               	varchar(30)                not null,            ##编辑
	createdate           	timestamp                  not null,            ##创建日期
	lastupdated          	timestamp		   not null,            ##最终修改日期
	CONSTRAINT fk_articleAttachID FOREIGN KEY (classid) REFERENCES  tbl_attachment_class(ID) ON DELETE CASCADE,
	primary key (id)
);


create table tbl_relatedartids                                                  ##页面相关文件表
(
        ID                      int auto_increment         not null,            ##主键ID
        cname                   varchar(100)               not null,            ##附件中文名称
        pageid                  int                        not null,            ##文章ID
        content                 text,                                           ##附件内容
	dirname                 varchar(255),                                   ##附件路径
        contenttype             smallint,                                       ##1上传文件  2图片文件 3全景图片文件 4多媒体文件 5html页面
        filename                varchar(200),                                   ##附件文件名称
        summary                 varchar(250),                                   ##附件文件描述
        editor                  varchar(50),                                    ##上传人
        createdate              timestamp,                                      ##附件创建日期
	lastupdate              timestamp,                                      ##
	primary key (id)        
);

create table tbl_turnpic (                                                      ##图片轮换表
        id                      int auto_increment         not null,
        articleid               int                        not null,
	sortid                  int,                                            ##图片显示顺序
	mediaurl                varchar(500),                                   ##多媒体文件路径
        smallpic1url            varchar(500),                                   ##小图片1URL
	smallpic2url            varchar(500),                                   ##小图片2URL
	smallpic3url            varchar(500),                                   ##小图片3URL
	latitude                decimal(38,15),                                 ##图片的经度
	longitude               decimal(38,15),                                 ##图片的纬度
        picname                 varchar(100),
        createdate              timestamp,
        notes                   varchar(2000),
	lib1                    varchar(100),                                   ##标签1
        lib2                    varchar(100),                                   ##标签2
        lib3                    varchar(100),                                   ##标签3
        lib4                    varchar(100),                                    
        lib5                    varchar(100),                                    
        primary key(id)
);

create table TBL_Article_Keyword
(
        ID                      int auto_increment         not null,
        siteid		        int                        not null,
        columnid	        int                        not null,
        keyword                 varchar(20),
        keywordnum              int                        default 0,           ##关键词个数
        flag                    smallint                   default 0,           ##1被选择出现在关键词列表中        
        url			varchar(200),
        primary key (id)
);

create table tbl_multimedia
(
        id                      int auto_increment          not null,           ##主键
        siteid                  int                         not null,           ##站点id 
        articleid               int                         default 0,          ##文章id
        dirname                 varchar(100)                not null,           ##栏目路径
        filepath                varchar(100)                not null,           ##文件路径
        oldfilename             varchar(100)                not null,           ##原文件名称
        newfilename             varchar(100)                not null,           ##新文件名称
        encodeflag              int                         default 0,          ##文件转换标志0-未转换 1-已经转换
        infotype                smallint                    default 0,          ##0文章内容的图片    1企业分类信息图片    2站点分类信息图片
        createdate              timestamp                   not null,           ##创建时间
        primary key(id)
);

create table tbl_deleted_article (
        articleid               int                not null,
        siteid                  int                not null,
	columnid                int                not null,
	sitename                varchar(100),
	acttype                 smallint           default    0,                ##0表示被删除的文章   1表示被修改的文章        
        primary key(articleid)
);

create table tbl_template (
	id                      int  auto_increment  not null,
	siteid                  int  default 0,
	columnid                int                  not null,
	isarticle               int                  not null,
	content              	long,
	createdate              timestamp                 not null,
	lastupdated             timestamp                 not null,
	editor                  varchar(30)          not null,
	creator                 varchar(30)          not null,
	status               	smallint             not null,
	relatedcolumnid         varchar(1000),
	modelversion            int,
	lockstatus              smallint             not null,
	lockeditor              varchar(30),
	chname                  varchar(50)          not null,
	defaulttemplate         smallint             not null,
	templatename            varchar(200)         not null,
	refermodelid            int                  not null,
	isIncluded              smallint             not null,
	tempnum                 int    default 0,
        primary key (id)
);

create table tbl_template_headpics (
	id                   	int    		not null,
	siteid                  int             default 0,
	columnid             	int    		not null,
        modelid                 int,                                      ##模板ID
	picbrief                varchar(500),                             ##模板头图描述
	pich                    int,                                      ##头图高度
	picw                    int,                                      ##头图宽度
	picurl                  varchar(500),                             ##头图URL
	pichref                 varchar(500),                             ##模板头图的链接
	CONSTRAINT fk_modelpicid FOREIGN KEY (modelid) REFERENCES  tbl_template(id) ON DELETE CASCADE,
        primary key (id)
)

create table tbl_column (
	id                      int auto_increment      not null,
	siteid               	int                     not null,
	parentid             	int                     not null,
	orderid                 int                     not null,
	cname                   varchar(50)             not null,
	ename                   varchar(50)             not null,
	dirname                 varchar(200)            not null,
	editor                  varchar(30)             not null,
	extname                 varchar(10)             not null,
	createdate              timestamp                    not null,
	lastupdated             timestamp                    not null,
	isdefineattr            smallint                not null,
 	hasarticlemodel         smallint                not null,
	isaudited               smallint                not null,
	isproduct               smallint                not null,
	ispublishmore           smallint                not null,
	languagetype            smallint                not null,
	publicflag              smallint    default 0   not null,
	islocation              int      default 1,                ##栏目存储位置信息
	columndesc              varchar(200),
	xmltemplate             varchar(3000),
	encodingflag            int,                                    --文档编码格式 1-utf-8  2-gb2312  3-gbk
	doctype                 varchar(500),                             --文档类型   HTML5  HTML4
	contentshowtype	        smallint,
	isrss                   smallint    default 0,
	getRssArticleTime       smallint,
	archivingrules          smallint    default 0,
	useArticleType          smallint    default 0,
	istype                  smallint    default 0,
	xmltype                 varchar(3000),                         ##文章分类的定义
	userflag                smallint    default 0,                 ##栏目文章是否只有注册用户可看  0##所有用户 1##注册用户
	userLevel               int         default 0,                 ##用户浏览文章的级别
	shareflag               smallint    default 0,                 ##栏目是否是和其他网站共享的栏目
	articlecount            int         default 0,                 ##本栏目的文章总数量
	titlepic                varchar(10),                           ##标题图片大小
	vtitlepic               varchar(10),                           ##副标题图片大小
	sourcepic               varchar(10),                           ##来源图片大小
	authorpic               varchar(10),                           ##作者图片大小
	contentpic              varchar(10),                           ##内容图片大小
	specialpic              varchar(10),                           ##文章特效图片大小
	productpic              varchar(10),                           ##商品大图片大小
	productsmallpic         varchar(10),                           ##商品小图片大小	
        ts_pic                  varchar(10),                           ##特小图图片大小
        s_pic                   varchar(10),                           ##小图图片大小
        ms_pic                  varchar(10),                           ##中小图图片大小
        m_pic                   varchar(10),                           ##中型图片大小 
        ml_pic                  varchar(10),                           ##中大型图片大小
        l_pic                   varchar(10),                           ##大型图片大小
        tl_pic                  varchar(10),                           ##特大型图片大小       
        primary key (id)
);

insert into tbl_column (id,siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate,lastupdated,isdefineattr,hasarticlemodel,isaudited,isproduct,ispublishmore,languagetype,columndesc,xmltemplate,encodingflag,doctype) values(0,0,-1,0,'首页','home','/','admin','html',current_date(),current_date(),0,0,0,0,0,0,'','',0,'');


create table tbl_mark (
	id                      int auto_increment   not null,
	columnid                int                  not null,
	siteid               	int                  not null,
	content              	text,
	marktype                int                  not null,
	notes                   varchar(200),
	lockeditor              varchar(30),
	lockflag                smallint             not null,
	pubflag                 smallint             not null,
	innerhtmlflag        	smallint             not null,
	formatfilenum           int,
	createdate              timestamp                 not null,
	updatedate              timestamp                 not null,
	publishtime             timestamp                 not null,
	chinesename             varchar(40)          not null,
	relatedcolumnid         varchar(500),
	ispublished             smallint     default 0,             ##标记是否发布过，0##未  1##发
        articlenum              int          default 0,            ##最近一次发布文章的数量，仅对文章列表标记有意义	
        primary key (id)
);

create table tbl_markdict (                                             --标记中文名称字典
	chinesename          	varchar2(40)		not null,       --标记中文名称
	markcode         	varchar2(10)            not null        --标记编码
);

insert into tbl_markdict (chinesename,markcode) values('文章列表','1001');
insert into tbl_markdict (chinesename,markcode) values('推荐文章列表','1002');
insert into tbl_markdict (chinesename,markcode) values('子文章列表','1003');
insert into tbl_markdict (chinesename,markcode) values('兄弟文章列表','1004');
insert into tbl_markdict (chinesename,markcode) values('栏目列表','1005');
insert into tbl_markdict (chinesename,markcode) values('子栏目列表','1006');
insert into tbl_markdict (chinesename,markcode) values('热点文章','1007');
insert into tbl_markdict (chinesename,markcode) values('相关文章','1008');
insert into tbl_markdict (chinesename,markcode) values('导航条','1009');
insert into tbl_markdict (chinesename,markcode) values('分页标记','1010');
insert into tbl_markdict (chinesename,markcode) values('中文路径','1011');
insert into tbl_markdict (chinesename,markcode) values('英文路径','1012');
insert into tbl_markdict (chinesename,markcode) values('文章条数','1013');
insert into tbl_markdict (chinesename,markcode) values('子文章条数','1014');
insert into tbl_markdict (chinesename,markcode) values('包含文件','1015');
insert into tbl_markdict (chinesename,markcode) values('最近浏览','1016');
insert into tbl_markdict (chinesename,markcode) values('上一篇','1017');
insert into tbl_markdict (chinesename,markcode) values('下一篇','1018');
insert into tbl_markdict (chinesename,markcode) values('视频播放','1019');
insert into tbl_markdict (chinesename,markcode) values('发布时间','1020');
insert into tbl_markdict (chinesename,markcode) values('图片上滚','1021');
insert into tbl_markdict (chinesename,markcode) values('图片下滚','1022');
insert into tbl_markdict (chinesename,markcode) values('图片右滚','1023');
insert into tbl_markdict (chinesename,markcode) values('图片左滚','1024');
insert into tbl_markdict (chinesename,markcode) values('图片幻灯','1025');
insert into tbl_markdict (chinesename,markcode) values('小图引导大图','1026');
insert into tbl_markdict (chinesename,markcode) values('文章图片特效','1027');
insert into tbl_markdict (chinesename,markcode) values('信息检索','1028');
insert into tbl_markdict (chinesename,markcode) values('文章评论','1029');
insert into tbl_markdict (chinesename,markcode) values('信息反馈','1030');
insert into tbl_markdict (chinesename,markcode) values('用户留言','1031');
insert into tbl_markdict (chinesename,markcode) values('用户注册','1032');
insert into tbl_markdict (chinesename,markcode) values('用户登录','1033');
insert into tbl_markdict (chinesename,markcode) values('修改注册','1034');
insert into tbl_markdict (chinesename,markcode) values('购物车','1035');
insert into tbl_markdict (chinesename,markcode) values('订单生成','1036');
insert into tbl_markdict (chinesename,markcode) values('订单回显','1037');
insert into tbl_markdict (chinesename,markcode) values('订单查询','1038');
insert into tbl_markdict (chinesename,markcode) values('订单明细查询','1039');

##栏目模板和标记的相关表
CREATE TABLE tbl_templatemark (
	ID                   int  auto_increment  NOT NULL,                   ##编号
	siteid               int    not null,                   ##站点id
	columnid             int    not null,                   ##模板所在栏目ID
        tid                  int    not null,                   ##模板id
        mid                  int    not null,                   ##该模板上的标记id
	CONSTRAINT pk_tmid PRIMARY KEY (ID),
	CONSTRAINT fk_templateMarkID FOREIGN KEY (tid) REFERENCES  tbl_template(ID) ON DELETE CASCADE
);

##标记与栏目的相关表
CREATE TABLE tbl_markcolumn (
	ID                   int auto_increment   NOT NULL,                   ##编号
	siteid               int    not null,                   ##站点id
        mid                  int    not null,                   ##标记id
        cid                  int    not null,                   ##标记引用的栏目id
	CONSTRAINT pk_mcid PRIMARY KEY (ID),
	CONSTRAINT fk_markColumnID FOREIGN KEY (mid) REFERENCES  tbl_mark(ID) ON DELETE CASCADE
);


create table tbl_viewfile (   	
    	id		        int auto_increment    not null,
	siteid                  int                   not null,
    	type                    int                   not null,
    	content                 long                  not null,
    	chinesename             varchar(50)           not null,
    	editor                	varchar(30)           not null,
    	lockflag                smallint              not null,
    	notes                   varchar(300),
    	createdate              timestamp                  not null,
    	updatedate              timestamp                  not null,
        primary key (id)
);

create table tbl_siteinfo (
	siteid                  int    auto_increment not null,
    	siteip                  varchar(15)           not null,	
	sitename                varchar(50)           not null,
	imagesdir               smallint              not null,
	cssjsdir                smallint              not null,
	tcflag                  smallint              not null,
	wapflag                 smallint  default 0,
	pubflag                 smallint              not null,
	bindflag                smallint              not null,
	createdate              timestamp                  not null,
	berefered               smallint              not null,     ##
	copycolumn	        smallint  default 0,                ##
	becopycolumn	        smallint  default 0,                ##
	pusharticle	        smallint  default 0,                ##发布文件数量
	movearticle	        smallint  default 0,                ##移动文件数量
	config                  varchar(200),
        sitetype                int       default 0,                ##样例和共享站点的类型,如电子商务网站，个人网站等等
	sitepic                 varchar(200),                       ##站点首页图片
	sitevalid               smallint  default 0,                ##0-样例网站 1-普通网站 2-共享网站
	sitelogo                varchar(200),                       ##站点LOGO
	banner                  varchar(200),                       ##站点主BANNER
	navigator               int       default 0,                ##站点导航样式
	samsiteid               int       default 0,                ##样例站点ID
	scores                  int       default 0,                ##积分和货币兑换规则
	sharetemplatenum        int       default 0,                ##共享样例站点的模板套号
	managesharesite         smallint  default 0,                ##管理共享站点信息   0-不管理  1-管理
	copyright               varchar(1000),	                    ##网站版权
	encodeflag              smallint          default 1,                       --文档编码格式 1-utf-8  2-gb2312  3-gbk
        titlepic                varchar(10),                       ##标题图片大小
	vtitlepic               varchar(10),                       ##副标题图片大小
	sourcepic               varchar(10),                       ##来源图片大小
	authorpic               varchar(10),                       ##作者图片大小
	contentpic              varchar(10),                       ##内容图片大小
	specialpic              varchar(10),                       ##文章特效图片大小
	productpic              varchar(10),                       ##商品大图片大小
	productsmallpic         varchar(10),                       ##商品小图片大小 
        ts_pic                  varchar(10),                       ##特小图图片大小
        s_pic                   varchar(10),                       ##小图图片大小
        ms_pic                  varchar(10),                       ##中小图图片大小
        m_pic                   varchar(10),                       ##中型图片大小 
        ml_pic                  varchar(10),                       ##中大型图片大小
        l_pic                   varchar(10),                       ##大型图片大小
        tl_pic                  varchar(10),                       ##特大型图片大小       
        primary key (siteid)
);

create table tbl_siteipinfo(
    	id                      int  auto_increment   not null,
    	siteid                  int                   not null,
    	siteip                  varchar(60)           not null,
    	sitename                varchar(50)           not null,
    	docpath                 varchar(100)          not null,
    	ftpuser                 varchar(50),
    	ftppasswd               varchar(50),
        ftptype                 int                   default 0,	    	
    	publishway              smallint              not null,
    	status                  smallint              not null,
	CONSTRAINT fk_siteipID FOREIGN KEY (siteid) REFERENCES  tbl_siteinfo(siteid) ON DELETE CASCADE,
        primary key (id)
);

create table tbl_headpic (
       id                       int auto_increment    not null,
       cname                    varchar(200)          not null,          ##主导航中文名称
       picurl                   varchar(500),                            ##主导航HTML代码
       picname                  varchar(500),                            ##辅助导航HTML代码
       notes                    varchar(250),
       createdate               timestamp,                               ##创建日期
       primary key(id)
);


##根据栏目之间引用文章的对应关系，存放引用的文章
create table tbl_refers_article (
        id                      int  auto_increment   not null,
	articleid               int                   not null,     ##文章ID
        siteid                  int                   not null,     ##文章原站点ID
	columnid       	        int 	              not null,     ##目的栏目的ID
	scolumnid               int                   not null,     ##源栏目ID
	columnname	        varchar(100),                       ##目的栏目的中文名称
	orders		        smallint   default 0,
	createdate	        timestamp,
	title		        varchar(600),                       ##在目的栏目中的标题名称
	status                  smallint              not null,     ##目的栏目中的状态
	pubflag                 smallint              not null,
	auditflag               smallint              not null,
	usearticletype          int,
	refers_column_status    int,
	tsiteid                 int,                                ##目的站点ID
	artfrom                 smallint,                           ##0-栏目定义处设置的引用，1-文章录入时设置的引用    
	primary key(id)
);

##栏目设置中选择的被引用文章的栏目
create table tbl_refers_column (
        id                int  auto_increment         not null,
        ssiteid           int                         not null,
        scid              int                         not null,
        tsiteid           int                         not null,
        tcid              int                         not null,
        subs              smallint   default 0,
        createdate        timestamp,
        primary key(id)
);


create table tbl_new_publish_queue (
	id         		int auto_increment    not null,
	siteid     		int                   not null,
	columnid                int                   not null,
	targetid                int                   not null,       ##需要发布的文章的ID、栏目的ID
	type                    smallint,		              ##1文章,2栏目模板,3首页模板,4标记
	status                  smallint,
	createdate              timestamp,
	publishdate             timestamp,
	uniqueid                varchar(50),                                  ##可以存储注册会员的ID
	title                   varchar(200),
        errcode                 int           default 0,              ##作业发布出错时保存错误代码,默认0表示成功发布
        errmsg                  varchar(500),                        ##出现错误时存储错误信息
        errnum                  smallint      default 0,              ##存储发布错误后重新发布的次数，最多重新发布3次
        priority                smallint      default 0,              ##作业的优先级 0-表示一般优先级
	creator                 varchar(100),                         ##作业的创建者
        primary key (id)
);

create table tbl_picture
(
        ID                      int  auto_increment      not null,
        SITEID                  int,
        COLUMNID                int,
        WIDTH                   int                      not null,
        HEIGHT                  int                      not null,
        PICSIZE                 int                      not null,
        PICNAME                 varchar(200),
        IMGURL                  varchar(255)             not null,
        CREATEDATE              timestamp,
        NOTES                   varchar(500),
        primary key (id)        
);


CREATE TABLE tbl_type (
	id                      int auto_increment       not null,												
	columnid                int                      not null,																
	parentid                int default 0,											
	referid                 int default 0,											
	cname                   varchar(100),
	createdate              timestamp,
	ename                   varchar(100) ,
	flag	                smallint  default 0      not null,
        primary key(id)
);

CREATE TABLE tbl_type_article (
	id                      int auto_increment       not null,										
	columnid                int                      not null,																
	articleid               int                      not null,															
	valueid                 int                      not null,																
	createdate              timestamp,				
        primary key(id)
); 

##工作日管理表
create table tbl_workdayinfo
(
    days                    varchar(50)   not null,
    siteid                  int      default 0 not null,
    worddayflag             int      default 0 not null, ##0工作日1非工作日
    primary key(days)
); 

##LOG分析系统
create table tbl_pv_detail(
        id                 bigint  auto_increment        not null,        ##主键ID
	siteid             int                           not null,        ##站点ID
	urlname            varchar(500),                                  ##URL地址
	url_cn_name        varchar(500),                                  ##中文名称
	pageview           int,                                           ##页面浏览数
	logdate            timestamp,                                     ##log时间
	primary key(id)
);

create index articleindex on tbl_article(id,columnid,status,doclevel,emptycontentflag,pubflag,auditflag);
create index articleindex_forauditor on tbl_article(auditor);
create index articleindex_forcolumn on tbl_article(columnid);
create index articleindex_forstatus on tbl_article(status);
create index articleindex_fordoclevel on tbl_article(doclevel);
create index articleindex_foremptyflag on tbl_article(emptycontentflag);
create index articleindex_forpubflag on tbl_article(pubflag);
create index articleindex_forauditflag on tbl_article(auditflag);
create index articleindex_forindexflag on tbl_article(indexflag);
create index articleindex_parent_article on tbl_article(RELATEDARTID);    ##父文章ID的索引
create index articleindex_forsiteid on tbl_article(siteid);
create index articleindex_editor on tbl_article(editor);
create index columnindex_forsiteid on tbl_column(siteid);
create index templateindex_forsiteid on tbl_template(siteid);
create index templateindex_forcolumnid on tbl_template(columnid);
create index templateindex_forreferid on tbl_template(ReferModelID);
create index order_fororderid on tbl_orders(userid);


##加盟商注册表
create table joincompany
(
       ID                int          auto_increment         not null,
       JOINID            varchar(200)                        not null,
       JOINNAME          varchar(300),
       ADDRESS           varchar(600),
       EMAIL             varchar(200),
       PHONE             varchar(200),
       COMPANY           varchar(400),
       PASSWORD          varchar(300),
       ANSWER            varchar(300),
       QUESTION          varchar(300),
       ZHIZHAONUMBER     varchar(200),
       FAX               varchar(100),
       LIANXIPEOPLE      varchar(300),
       CREATEDATE        timestamp,
       BUYFLAG           varchar(100),
       primary key(ID)
);

create table tbl_companyclass( 
       id          int auto_increment     not null,
       siteid      int                    not null, 
       parentid    int,
       orderid     int,
       cname       varchar(50),
       ename       varchar(50),
       summary     varchar(250),                                   ##公司分类信息描述       
       dirname     varchar(200),
       editor      varchar(30),
       extname     varchar(10),
       companynum  int                   default 0,       
       createdate  timestamp,
       lastupdated timestamp,
       primary key(id)
);
insert into tbl_companyclass values(0,0,-1,0,'公司分类','company','','/','admin','html',0,current_date(),current_date());

##常用网站分类
create table tbl_websiteclass (
	id                   	int   auto_increment    not null,
	siteid               	int                     not null,
	parentid             	int                     not null,
	orderid              	int                     not null,
	cname                	varchar(50)             not null,
	ename                	varchar(50)             not null,
	dirname              	varchar(200)            not null,
	editor               	varchar(30)             not null,
	extname              	varchar(10)             not null,
        summary                 varchar(250),
        websitenum              int                     default 0,	
	createdate           	timestamp                    not null,
	lastupdated          	timestamp                    not null,
	primary key(id)
);
insert into tbl_websiteclass values(0,0,-1,0,'网址分类','website','/','admin','html','',0,current_date(),current_date());

##网站分类信息
create table tbl_websiteinfo(
       id                                        int auto_increment,             ##主键
       siteid                                    int,                            ##所属站点
       websitename                               varchar(100),                   ##公司名称
       websiteurl                                varchar(200),                   ##公司所在地址
       summary                                   varchar(200),                   ##公司联系电话
       editor                                    varchar(50),                    ##编辑
       pubflag                                   smallint,                       ##发布标记
       createdate                                timestamp,                           ##创建日期
       updatedate                                timestamp,                           ##修改日期
       primary key(id)
);

##程序库管理
create table tbl_program_library
(
        id            int auto_increment not null,         ##主键，自增长
        p_type        int,                                 ##程序类型
        position      int,                                 ##JSP页面中的位置
        l_type        int,                                 ##语言类型
        explain1      varchar(100),                        ##程序说明
        notes         varchar(500),                        ##程序注释
        program       long,                                ##程序体
        primary key(id)
);

create table tbl_program_position
(
        id            int auto_increment not null,        ##编码
        position      varchar(50),                        ##位置说明
        primary       key(id)
);

insert into tbl_program_position (id,position) values(1,'程序头');
insert into tbl_program_position (id,position) values(2,'显示体');
insert into tbl_program_position (id,position) values(3,'页尾');

create table tbl_program_type
(
        id            int auto_increment not null,      ##编码
        p_type        varchar(50),                      ##程序类型说明
        primary       key(id)
);

insert into tbl_program_type (id,p_type) values(1,'信息检索');
insert into tbl_program_type (id,p_type) values(2,'购物车');
insert into tbl_program_type (id,p_type) values(3,'订单生成');
insert into tbl_program_type (id,p_type) values(4,'订单查询');
insert into tbl_program_type (id,p_type) values(5,'用户注册');
insert into tbl_program_type (id,p_type) values(6,'用户评论');
insert into tbl_program_type (id,p_type) values(7,'用户信息反馈');
##结束程序库管理

##网站前台应用表
##用户注册表
create table tbl_userinfo
(
        ID                   int auto_increment     not null,       ##主键
  	SITEID               int,                                   ##站点ID
  	MEMBERID             varchar(50)            not null,       ##用户ID
  	USERNAME             varchar(30),                           ##用户真是姓名
  	PASSWORD             varchar(20),                           ##用户口令
  	LINKMAN              varchar(30),                           ##联系人
  	nation               varchar(30),                           ##民族
  	degree               varchar(30),                           ##文化程度
  	idno                 varchar(19),                           ##身份证号码  	
  	street               varchar(100),                         ##所在街道  	
  	CITY                 varchar(20),                           ##所在城市
  	ADDRESS              varchar(200),                          ##具体地址
  	COUNTRY              varchar(50),                           ##国家
  	POSTALCODE           varchar(10),                           ##邮政编码
  	PHONE                varchar(20),                           ##电话
  	FAX                  varchar(20),                           ##传真
  	EMAIL                varchar(100),                          ##电子邮件
  	HOMEPAGE             varchar(100),                          ##个人主页
  	REMARK               varchar(500),                          ##个人留言
  	USERTYPE             int,                                   ##用户类型
  	VALID                int,                                   ##是否有效
  	SEX                  varchar(10),                           ##用户性别
  	OICQ                 varchar(50),                           ##用户QQ号码
  	BIRTHDAY             date,                                  ##出生日期
  	IMAGE                varchar(50),                           ##用户头像
  	SIGN                 varchar(50),                           ##用户签名
  	scores               int  default 0,                        ##用户积分
  	meilizhi             int  default 0,                        ##魅力值
  	LOGINNUM             int,                                   ##登录次数
  	GRADE                varchar(50),                           ##用户级别 
  	THEONLINE            varchar(10),                           ##
  	RENZHENG             varchar(50),                           ##
  	MOBILEPHONE          varchar(50),                           ##移动电话
  	PROVINCE             int,                                   ##省份
  	CREATEDATE           date,                                  ##创建时间
  	COMPANY              varchar(500),                          ##工作单位
  	unitpcode            varchar(10),                           ##单位邮政编码
  	unitphone            varchar(20),                           ##单位电话
  	stationtype          varchar(8),                            ##站台类别
  	entitytype           varchar(1),                            ##集体/个人
  	stationaddr          varchar(80),                           ##站台地址
  	opedegree            varchar(20),                           ##操作证等级
  	opecode              varchar(20),                           ##操作证书编码
  	callsign             varchar(20),                           ##呼号
  	memo                 varchar(512),                          ##备注      	
  	lockflag             int default 0 not null,                ##是否被锁定
        primary key (id)
);

##授权用户能够查看的栏目
create table column_authorized(
        id                  int             	not null,
        siteid              int                 not null, 
        columnid            int                 not null,
        targetid            int                 not null,  ##用户ID或者是角色ID
        type                smallint            default 0, ##0表示targetid为角色id, 1表示targetid为用户id
        createdate          timestamp,
        primary key(id)
);

##文章评论
CREATE TABLE tbl_comment ( 
	id          int auto_increment not null,           ##主键
	userid      varchar(20),                           ##登录用户ID,如果没有登录，记录IP
	name        varchar(100),                          ##评论人姓名
	link        varchar(100),                          ##评论人联系方式
	content     varchar(2000),                         ##评论内容
	ip          varchar(20),                           ##评论人上网的IP地址
	about       int,                                   ##文章ID
	Scores         int                      default 0 , ##评分
	experience     varchar(500),                       ##使用心得
	Advantages     varchar(500),                       ##优点
	Shortcomings   varchar(500),                                    ##不足
	usefulnum      int                      default 0,              ##认为该评论有用的人数
	nousenum       int                      default 0,              ##认为该评论无用的人数	
	flag           int                      default 0,            ##1 审核通过，可显示 0-审核未通过 不显示  2-删除 		
	createdate  timestamp,                             ##创建日期
	siteid      int,                                   ##站点ID
	CONSTRAINT  pk_commentid PRIMARY KEY (id)	
);


##调查
CREATE TABLE su_survey  (																##调查表
	 id           int  auto_increment not null,   	   ##主键
	 surveyname   varchar(200),			   ##调查名称
	 notes        varchar(2000),			   ##调查描述
	 createtime   timestamp,                           ##创建时间
	 siteid       int default 0 not null,		   ##站点id	
	 useflag      int default 0 not null,		   ##有效标志 0-无效1-有效
         primary key (id) 
);

CREATE TABLE  su_answer  (				   ##答案表
	 id           int  auto_increment not null,	   ##主键
	 userid       int,				   ##用户id
	 sid   	      int,				   ##调查id
	 qid   	      int,				   ##问题id	
	 answers      varchar(200),			   ##答案
	 other        varchar(200),			   ##输入框的值
         primary      key(id)
);

CREATE TABLE  su_danswer  (			           ##问题答案表
	 id           int  auto_increment not null,	   ##主键
	 qid          int  default 0 not null,	           ##问题id
	 qanswer      varchar(200),		           ##问题答案
	 picurl       varchar(100),                        ##图片地址
         primary      key(id) 
);

CREATE TABLE  su_dquestion  (				   ##问题定义表
	 id           int  auto_increment not null,	   ##主键
	 sid          int  not null,                       ##调查id
	 qname        varchar(500),                        ##问题名称
	 qtype        int,                                 ##问题类型
	 qmust        int,                                 ##是否必选
	 nother       int,                                 ##是否需要输入框	
	 atype        int,                                 ##答案类型
         primary      key(id) 
);

create table su_defineuserinfo
(
        ID         int  auto_increment not null,
        SID        int  default 0,                         ##调查ID
        USERNAME   varchar(100),                           ##提交调查用户名
        PHONE      varchar(100),                           ##提交调查用户电话
        CREATEDATE timestamp,
        primary key(id)
);

##信息反馈
CREATE TABLE tbl_feedback
(
	 id          int  auto_increment not null,          ##主键
	 userid      varchar(50),                           ##登录用户ID,如果没有登录，记录IP
	 email       varchar(100),                          ##用户的电子邮件
	 phone       varchar(20),                           ##用户电话
	 title       varchar(200),                          ##标题
	 content     varchar(4000),                         ##问题内容
	 createtime  timestamp,                             ##创建日期
	 answer      varchar(4000),                         ##问题答案
	 answertime  timestamp,                             ##回答时间
	 flag        int  default 0,                        ##回答后是否在网站上显示，1##显示 0##不显示
	 siteid      int,                                   ##站点ID
         CONSTRAINT pk_feedback_id PRIMARY KEY (ID)                                        
);

##我的地址薄
CREATE TABLE  tbl_userinfos (
	id            int  auto_increment not null,         ##主键
	username      varchar(20),                          ##用户名称
	address       varchar(200),                         ##地址
	email         varchar(100),                         ##电子邮件
	sex           int,                                  ##性别
	mobilephone   varchar(20),                          ##移动电话
	postcode      varchar(10),                          ##邮编
	phone         varchar(20),                          ##电话
	corporation   varchar(500),                         ##公司
	fax           varchar(20),                          ##传真
	memberid      varchar(50),                          ##用户id
	createtime    int                  not null,        ##创建时间
	CONSTRAINT pk_userinfos_id PRIMARY KEY (ID)
);

##每日流水号管理表
create table tbl_lshbydate(
       cdate   timestamp,
       lsh     varchar(15)
);

##用户留言
create table tbl_leaveword
(
	id             int  auto_increment    not null,           ##主键
	columnid       int                    default 0,          ##咨询问题分类定义
  	siteid         int                    not null,           ##站点id
  	code           varchar(12),                               ##用户咨询流水号   AyyyymmddXXX    首字母A表示局长信箱  B表示在线咨询 C表示献言献策
  	sex            smallint,                                  ##留言人性别
  	userid         varchar(50),                               ##登录用户ID,如果没有登录，记录IP
	flag           int                    default 0,          ##是否在网站上显示，1--显示 0--不显示  	
  	title          varchar(100),                              ##留言标题
  	content        varchar(4000),                             ##留言内容
  	writedate      timestamp,                                 ##留言日期
  	company        varchar(200),                              ##公司
  	linkman        varchar(100),                              ##联系人
  	links          varchar(500),                              ##联系方式
  	zip            varchar(20),                               ##区号
  	email          varchar(100),                              ##电子邮件
  	phone          varchar(20),                               ##电话
  	formid         int,                                       ##用户留言表单ID
  	departmentid   int,                                       ##信息分发到的部门ID
	retcontent     varchar(2000),                             ##回复的内容
  	auditor        varchar(50),                               ##如果需要审核，保存审核人信息
  	auditflag      int                    default 0,          ##0表示审核完毕  1表示审核进行中
  	mphone         varchar(20),                               ##移动电话
  	processor      varchar(50),                               ##处理人用户ID
	valid          smallint               default 0,          ##问题是否有效 0--有效 1--无效
	validreason    varchar(512),                              ##问题无效的原因
	datefromdept   timestamp,                                 ##部门回复日期
	finalflag      smallint               default 0,          ##最终回复用户标志位，当回复获得批准后，由留言板部门管理员或留言板管理员将之设置为1
	endtouser      timestamp,                                 ##最终回复用户日期
	CONSTRAINT pk_leaveword_id PRIMARY KEY (ID)
);
create index leavemsgindex_forsiteid on tbl_leaveword(siteid);

create table tbl_leaveword_to_dept
(
	id             int  auto_increment    not null,           ##主键
  	siteid         int                    not null,           ##站点id
  	departmentid   int,                                       ##信息分发到的部门ID
  	formid         int,                                       ##用户留言表单ID
  	lwid           int,                                       ##用户留言ID
  	processor      varchar(50),                               ##回答人用户ID
	flag           int                    default 0,          ##是否已经回答，1--回答 0--未回答  	
  	content        varchar(4000),                             ##回答内容
  	retdate        timestamp,                                 ##回答日期
  	createdate     timestamp,                                 ##分配给该用户的日期
	CONSTRAINT pk_leaveword_to_dept_id PRIMARY KEY (ID)
);

##用户留言分类分类
create table tbl_leavewordclass (
	id             int  auto_increment    not null,
	siteid         int                    not null,
	parentid       int                    not null,
	orderid        int                    not null,
	cname          varchar(50)            not null,
	ename          varchar(50)            not null,
	dirname        varchar(200)           not null,
	editor         varchar(30)            not null,
	extname        varchar(10)            not null,
        summary        varchar(250),
        lwnum          int                    default 0,           ##某分类的留言数量
	createdate     timestamp              not null,
	lastupdated    timestamp              not null,
	primary key(id)
);
insert into tbl_leavewordclass values(0,0,-1,0,'留言分类','lwsite','/','admin','html','',0,current_date(),current_date());

create table tbl_leaveword_valid_reason
(
	id             int                    not null,            ##主键
  	siteid         int                    not null,            ##站点id
        content        varchar(500)           not null,
        primary key(id)
);

insert into tbl_leaveword_valid_reason(id,siteid,content) values(1,40,'缺少联系信息');
insert into tbl_leaveword_valid_reason(id,siteid,content) values(2,40,'询问问题不合理');

CREATE TABLE tbl_jobinfo(
	id                        int   auto_increment  not null,
	companyid                 varchar(50)       not null,
	postid                    varchar(50)       not null,
	firstname                 varchar(40)       not null,
	sendname                  varchar(40)       not null,
	gender                    varchar(2)        not null,
	nationality               varchar(15)       not null,
	age                       varchar(10),
	marrage                   smallint,
	birthday                  varchar(10),
	birthplace                varchar(100),
	photopath                 varchar(100),
	photooldname              varchar(100),
	accontplace               varchar(100),
	homeaddress               varchar(200),
	liveaddress               varchar(200),
	educationalcategorie      varchar(200),
	education                 varchar(20),
	majorsubjects1            varchar(50),
	majorsubjects2            varchar(50),
	professional              varchar(40),
	certificate               varchar(50),
	cbgntime                  varchar(50),
	cendtime                  varchar(50),
	graduateschool            varchar(80)         not null,
	documentstype             varchar(4)          not null,
	documentsnum1             varchar(50),
	titlebgntime              varchar(20),
	titlename                 varchar(50),
	grade                     varchar(50),
	workbgntime1              varchar(20),
	workendtime1              varchar(20),
	employer1                 varchar(60),
	post1                     varchar(200),
	city1                     varchar(25),
	mobile                    varchar(20),
	phone                     varchar(20),
	email                     varchar(30),
	createdate                timestamp            not null,
	major1                    varchar(50),
	major2                    varchar(50),
	documentsnum2             varchar(50),
	documentsnum3             varchar(50),
	documentsnum4             varchar(50),
	workbgntime2              varchar(20),
	workendtime2              varchar(20),
	employer2                 varchar(60),
	post2                     varchar(200),
	city2                     varchar(25),
	workbgntime3              varchar(20),
	workendtime3              varchar(20),
	employer3                 varchar(60),
	post3                     varchar(200),
	city3                     varchar(25),
	subTitleName              varchar(50),
	primary key(id) 
);


##电子商务系统
##全球国家（）
create table EN_COUNTRY
(
  id                int               not null,
  orderid           int,
  countryname       varchar(20)       not null,
  valid             int               default 0,                   ##1--使用   0--停用
  code              varchar(6)
);

##省市级单位（22个省，5个自治区，4个直辖市，2个特别行政区）
create table EN_PROVINCE
(
  id                int               not null,
  orderid           int,
  countryid         int               not null  default 86,
  provname          varchar(20)       not null,
  valid             int               default 0,                   ##1--使用   0--停用
  code              varchar(6)
);

##地级行政单位（17个地区、30个自治州、283个地级市、3个盟）
create table en_city
(
  id                int               not null,
  orderid           int,
  provid            int               not null,
  cityname          varchar(30)       not null,
  valid             int               default 0,                   ##1--使用   0--停用
  code              varchar(6)
);

##区县级行政单位（1464个县、117个自治县、374个县级市、852个市辖区、49个旗、3个自治旗、1个林区、2个特区）
create table en_zone
(
  id               int                 not null,
  orderid          int, 
  cityid           int                 not null,
  zonename         varchar(30)         not null,
  valid            int                 default 0,                   ##1--使用   0--停用
  code             varchar(6)
);

##乡镇级行政单位（14677个乡、1092个民族乡、19522个镇、6152个街道办事处、11个区公所、181个苏木、1个民族苏木）
create table en_town
(
  id               int                 not null,
  orderid          int,
  zoneid           int                 not null,
  townname         varchar(30)         not null,
  valid            int                 default 0,                   ##1--使用   0--停用
  code             varchar(6)
);

##村、社区级行政单位（80717个社区居民委员会、623669个村民委员会）
create table en_village
(
  id               int                 not null,
  orderid          int,
  townid           int                 not null,
  villagename      varchar(30)         not null,
  valid            int                 default 0,                   ##1--使用   0--停用
  code             varchar(6)
);

create table en_receivemoney (
        id               int    auto_increment          not null,
        brief            varchar(200)                   not null,
        userid           varchar(20),
        payer            varchar(20),
        jing_ban_ren     varchar(20),
        payway           int,
        nint             int,
        unit             varchar(100),
        createdate       timestamp,
        orderid          bigint                         not null,
        nnumber          float,
        primary key(id)
);

create table en_gonghuo (
        id               int  auto_increment             not null,
        kind             varchar(100)                    not null,
        name             varchar(50)                     not null,
        lianxiren        varchar(50),
        address          varchar(200),
        postcode         varchar(50),
        phone            varchar(50),
        email            varchar(50),
        notes            varchar(250),
        siteid           int,
        primary key(id)
);

create table en_message (
        id               int   auto_increment           not null,
        siteid           int                            not null,
        send_user        varchar(50),
        receive_user     varchar(50),
        message          varchar(250),
        senddate         timestamp,
        send_del         smallint,
        receive_del      smallint,
        flag             smallint,
        primary key(id)
);

create table en_savemessage (
        id               int   auto_increment            not null,
        siteid           int                             not null,
        message          varchar(250),
        sender           varchar(50),
        receiver         varchar(50),
        send_user        int,
        receive_user     int,
        senddate         timestamp,
        createdate       timestamp,
        userid           int,
        primary key(id)
);

create table en_applicationmanage (
        id               int   auto_increment            not null,
        siteid           int                             not null,
        userid           int,
        orderid          bigint,
        productid        int,
        repairflag       smallint,
        exchangeflag     smallint,
        backflag         smallint,
        notes            varchar(250),
        dealdate         timestamp,
        primary key(id)
);

CREATE TABLE tbl_orders(                                                    ##定单表
        id                       bigint   auto_increment     not null,      ##主键
	SITEID                   int,				            ##站点id
	orgid                    int,                                       ##订单所属部门
	companyid                int,                                       ##订单所属公司
	deptid                   int,                                       ##订单所属部门
	ORDERID                  bigint   not null unique,                  ##定单号
	initorderid              int      default 0,                        ##原始定单号(拆单=原单号，非拆单=0)
	USERID                   int,	     			            ##用户id
	NAME                     varchar(50),		                    ##收货人姓名
	SEX                      int,				            ##性别
	ADDRESS                  varchar(1000),	                            ##地址
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
	bill_to_address          varchar(2000),	                            ##发票地址
	notes                    varchar(4000),	                            ##发票信息
	flag                     smallint   default 0     not null,
	payflag                  smallint   default 0     not null,         ##是否已经支付   0-未支付  1-已经支付
	payway                   int,		                            ##付款方式
	paydate                  timestamp,		                    ##付款时间
	linktime                 varchar(50),			
	usescores                int        default 0,	                    ##用户使用积分
	orderscores              int        default 0,                      ##定单产生的积分 
	jylsh                    varchar(200),                              ##从支付网关返回的支付网关方的交易流水号
	usecard                  int        default 0,                      ##使用购物券面额   新增加
	paycompno                int,                                       ##第三支付网关编号
	zfmemberid               varchar(200),                              ##支付中心会员ID
	r2type                   varchar(10),                               ##支付中心返回类型，1浏览器重定向 2服务器点对点
	payresult                varchar(50),                               ##支付结果，成功/失败/其他
	lastupdate               timestamp,		                    ##订单最后时间
	CREATEDATE               timestamp,		                    ##创建时间
	primary key(id) 
);

##定单详细信息表
CREATE TABLE tbl_orders_detail( 
	 id                       bigint auto_increment        not null,    ##主键
	 orderid                  bigint                       not null,    ##定单号
	 supplierid               int      default 0        not null,       ##供货商id(对应每个供货商siteid)
	 suppliername             varchar(100),                             ##供货商名称
	 productid                int,				            ##商品id
	 productname              varchar(200),	                            ##商品名称
	 productcode              varchar(200),                             ##商品编码
	 ordernum                 int,				            ##商品数量
         unit                     varchar(10),                              ##商品计量单位
	 saleprice                double(9,2),		                    ##商品售价
	 weight                   int,				            ##商品重量
	 type                     varchar(100),	                            ##商品类型（扩展属性）
	 cardid                   int      default 0        not null, 	    ##使用购物卷id 
	 userinstarttime          timestamp,                                ##用户输入的开始日期
	 userinendtime            timestamp,                                ##用户输入的结束日期
	 servicestarttime         timestamp,                                ##根据规则计算出的服务类采购起始日期
	 serviceendtime           timestamp,                                ##根据规则计算出的服务类采购结束日期
	 subscribe                smallint,                                 ##服务订阅类型
         subscribenum             int,                                      ##服务订购的期数，例如1个季度或者2个季度
	 createdate               timestamp,			            ##创建时间
	 CONSTRAINT fk_orderID FOREIGN KEY (ORDERID) REFERENCES  TBL_ORDERS(ORDERID) ON DELETE CASCADE,	 	 
	 primary key(id)
);

##支付信息记录
create table tbl_payinfo(
	 ID                       bigint auto_increment        not null,    ##主键
	 SITEID                   int,				            ##站点id
	 ORDERID                  bigint                       not null,    ##定单号
	 cname                    varchar(200),				    ##送货方式名称
	 notes                    varchar(2000),                            ##送货方式说明
	 fee			  double(9,2) default 0,                    ##运费
	 createdate               timestamp,                                ##创建日期
	 primary key(id)
);


##送货方式管理
create table tbl_fee(
	id                        int auto_increment not null,              ##主键
	siteid                    int default 0,                            ##站点id
	cname                     varchar(200),				    ##送货方式名称
	notes                     varchar(2000),                            ##送货方式说明
	fee			  double(9,2) default 0,                    ##运费
	createdate                timestamp,                                ##创建日期
	primary key(id)
);

##支付方式管理
create table tbl_sendway(
        id                        int auto_increment not null,              ##主键
        siteid                    int                not null,              ##站点id
        cname                     varchar(200),				    ##支付方式名称
	notes                     varchar(2000),                            ##支付方式说明
	createdate                timestamp,                                ##创建日期
	primary key(id)
);

##我的常用送货地址
create table tbl_addressinfo(
        id                        int auto_increment  not null,       ##主键
        userid                    int                 not null,       ##用户id
        name                      varchar(200),                       ##收货人姓名
        provinces                 varchar(50),			      ##省份
        city                      varchar(50),			      ##城市
        zone                      varchar(50),                        ##地区
        mobile                    varchar(50),                        ##移动电话
        phone                     varchar(50),                        ##固定电话
        zip                       varchar(50),                        ##邮编
        address                   varchar(200),                       ##详细地址
        email                     varchar(200),                       ##收货人邮件地址
        notes                     varchar(200),                       ##收货人备注信息
	createdate                timestamp,                          ##创建时间
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

##购物券
CREATE TABLE tbl_shoppingcard(
	id                        int auto_increment  not null,       ##主键
	siteid                    int                 not null,
	cardnum                   varchar(50),                        ##购物券标识号 
	denomination              int,                                ##购物券金额
	begintime                 varchar(50),                        ##开始时间
	endtime                   varchar(50),                        ##结束时间
	code                      varchar(50),                        ##激活码
	createtime                timestamp,                          ##创建时间
	activation                int,                                ##是否被使用0-未被使用，1-被使用
	ischeck                   int                 not null,       ##是否发放0-未发放，1-已发放
	primary key(id) 
);

##买赠方案管理
create table tbl_mai_zeng_fangan(
        id               int auto_increment           not null,
        siteid           int                          not null,
        zengway          int                          not null,       ##赠品方案编号
        price            double(9,2),                                 ##按价格赠，一个订单的价格达到规定的数值享受赠品（一种方案）
        pcount           int,                                         ##按数量赠，某个商品购买到规定数量，享受赠品    （第二种方案）
        productid        int,                                         ##按数量赠需要购买的商品
        productbrand     varchar(50),                                 ##购买某个品牌的商品享受赠品                    （第三种方案） 
        useflag          smallint,                                    ##该方案是否使用     0##不使用   1##使用
        majorflag        smallint,                                    ##主要方案，在两个方案冲突情况，使用该方案，1##主方案，所有使用方案中只能有一个主方案
        createdate       timestamp,                                   ##方案创建日期
        editor           varchar(50),                                 ##方案创建人
        primary key(id)
);


##赠品管理
create table tbl_zengpin(
        productid        int                          not null,       ##产品ID
        productname      varchar(250)                 not null,       ##产品名称
        fanganid         int                          not null,       ##适用方案ID
        createdate       timestamp,                                   ##创建日期
        editor           varchar(50),                                 ##创建者
        primary key(productid)
);

##捆绑销售
create table tbl_groupproductsale(
        groupid          int                          not null,       ##产品组合ID
        productid        int                          not null,       ##产品ID
        productname      varchar(250)                 not null,       ##产品名称
        summary          varchar(500),                                ##组合产品描述
        productcount     smallint                     not null,       ##商品数量
        YhPrice          double(9,2),                                 ##优惠价格
        groupbigpic      varchar(250),                                ##组合大图
        groupsmallpic    varchar(250),                                ##组合小图
        grouptexuao      varchar(250),                                ##组合特效图
        groupcontentpic  varchar(250),                                ##组合内容图
        begintime        timestamp,                                   ##开始日期
        endtime          timestamp,                                   ##结束日期
        createdate       timestamp,                                   ##创建日期
        editor           varchar(50),                                 ##创建者
        primary key(productid)
);

##支付接口定义表
create table tbl_paywayinterface(
       id                int                           not null,
       paywayid          int                           not null,
       accountnumber     varchar(200),
       paywaykey         varchar(200),
       alipayaccount     varchar(100),
       createdate        timestamp                     not null,
       primary key(id) 
); 

##我的常用发票信息表
create table tbl_invoiceinfo
(
       id                 int           auto_increment,
       siteid             int           default 0,           ##站点id
       userid             int           default 0,           ##用户id
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

##发票内容维护表
create table tbl_invoicecontent
(
       id                 int  auto_increment not null, 
       siteid             int                 default 0,     ##站点id
       invoiceid          int                 not null,      ##发票ID
       guige              varchar(100),                      ##规格
       unit               varchar(100),                      ##单位                      
       num                int,                               ##购买数量
       danprice           float(10,2),                       ##单价
       content            varchar(200)        not null,      ##内容
       createdate         timestamp,                         ##创建时间
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


##送货费用管理



##问吧表结构定义
create table fawu_anwser                                ##答案表      
(
        id                      int   auto_increment    not null,            ##答案主键
        qid                     int                     not null,            ##问题主键，本表的外键
        anwser                  varchar(4000),                               ##答案
        votenum                 int,                                         ##投票数量
        ipaddress               varchar(15),                                 ##回答者的IP地址
        userid                  int,                                         ##回答者用户ID
        username                varchar(300),                                ##回答者用户名
        fenshu                  int                     default 0,           ##获取分数
        cankaoziliao            varchar(4000),                               ##参考资料
        createdate              timestamp,                                   ##创建时间
        PICPATH                 varchar(255),                                ##图片路径
        BETTERANS               int,                                         ##是否是最佳答案
        ANW_STATUS              int,                                         ##答案状态
        primary key(id)
);

create table fawu_wenti_column                          ##问吧分类定义
(
        id                      int   auto_increment    not null,            ##问吧分类主键
        siteid                  int                     not null,            ##站点ID
        parentid                int                     not null,            ##父分类ID
        orderid                 int                     not null,            ##序号ID
        cname                   varchar(100),                                ##中文名称
        ename                   varchar(100),                                ##英文名称
        dirname                 varchar(200),                               
        status                  int                     default 0,
        creater                 varchar(20),
        createdate              timestamp,
        primary key(id)
);

create table fawu_vote                                 ##问吧答案投票表
(
        id                      int  auto_increment    not null,             ##主键ID   
        aid                     int                    not null,             ##问题答案ID
        userid                  int                    not null,             ##投票用户ID
        primary key(id)
);

create table fawu_wenti                                 ##问题表
(
        id                      int auto_increment      not null,           ##主键ID
        columnid                int                     not null,           ##分类ID
        parcolumid              int,                                        ##上一级分类ID
        cname                   varchar(100),                               ##问题中文名称
        question                varchar(1000),                              ##问题内容
        status                  int,                                        ##问题状态
        voteflag                int,                                        ##是否是带投票的问题
        xuanshang               int                     default 0,          ##悬赏分数
        answernum               int                     default 0,          ##回答数目
        source                  varchar(50),                                ##
        createdate              timestamp,                                  ##创建日期
        ipaddress               varchar(15),                                ##客户端IP地址
        creater                 varchar(50),                                ##创建人
        province                varchar(50),                                ##创建人省份
        city                    varchar(50),                                ##创建人所在城市
        area                    varchar(50),                                ##创建人所在区域
        picpath                 varchar(255),                               ##图片路径
        emailnotify             int                     default 0,          ##是否邮件通知  1--通知  0--不通知,
        title                   varchar(60),                                ##
        email                   varchar(40),                                ##用户电子邮件
        userid                  int,                                        ##用户ID
        username                varchar(20),                                ##用户名称
        filepath                varchar(255),                               ##上传文件路径
        istop                   int                     default 0,          ##是否置顶
        anw_status              int,                                        ##答案状态
        dianjinum               int,                                        ##点击数
        user_id_huida           varchar(50),                                ##回答人ID
        primary key(id)
);


##电子商务进、销、存系统
##银行编号表
create table tbl_banks (
        id                      int   auto_increment    not null,      ##主键
        bankid                  varchar(12)             not null,      ##银行ID
        siteid                  int                     not null,      ##站点ID
        bankattribname          varchar(12)             not null,      ##银行简称
        bankname                varchar(40),                           ##银行名称
        createdate              timestamp,                             ##创建时间
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##业务员数据表
create table tbl_salesman (
        id                      int auto_increment      not null,      ##主键
        salesmanid              varchar(12)             not null,      ##业务员ID
        siteid                  int                     not null,      ##站点ID
        chinesename             varchar(12)             not null,      ##业务员名称
        englishname             varchar(12),                           ##业务员英文名称
        contactphone            varchar(20),                           ##业务员联系电话
        mobilephone             varchar(12),                           ##业务员移动电话
        contactaddress          varchar(120),                          ##业务员联系地址
        email                   varchar(50),                           ##业务员电子邮件地址
        createdate              timestamp,                             ##创建时间
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##客户表
create table tbl_customer (
        id                      int  auto_increment     not null,      ##主键
        customerid              varchar(12)             not null,      ##客户ID
        siteid                  int                     not null,      ##站点ID
        customerattribname      varchar(50)             not null,      ##客户简称
        customername            varchar(100),                          ##客户名称
        invoiceno               varchar(20),                           ##发票编号
        owner                   varchar(12),                           ##负责人
        rocid                   varchar(18),                           ##身份证号码
        contactman1             varchar(18),                           ##联系人1
        contactman2             varchar(18),                           ##联系人2
        contactphone1           varchar(20),                           ##联系人1电话
        contactphone2           varchar(20),                           ##联系人2电话
        fax                     varchar(20),                           ##传真号码
        salesmanid              int,                                   ##业务负责人
        customeraddress         varchar(120),                          ##公司地址
        deliveryaddress         varchar(120),                          ##送货地址
        invoiceaddress          varchar(120),                          ##发票地址
        paydays                 int,                                   ##付款日数
        creditline              double(32,2),                          ##信用额度
        creditbalance           double(32,2),                          ##信用余额
        lastdeliverydate        timestamp,                             ##最后交易日
        createdate              timestamp,                             ##创建时间
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##供货商表
create table tbl_supplier (
        id                      int auto_increment      not null,      ##主键
        supplierid              varchar(12)             not null,      ##供货商ID
        siteid                  int                     not null,      ##站点ID
        supplierattribname      varchar(50)             not null,      ##供货商简称
        suppliername            varchar(100),                          ##供货商名称
        invoiceno               varchar(20),                           ##发票编号
        owner                   varchar(12),                           ##负责人
        rocid                   varchar(18),                           ##身份证号码
        phone1                  varchar(18),                           ##联系电话1
        phone2                  varchar(18),                           ##联系电话2
        fax                     varchar(20),                           ##传真号码
        contactname1            varchar(20),                           ##联系人1
        contactname2            varchar(20),                           ##联系人2
        companyaddress          varchar(120),                          ##公司地址
        deliveryaddress         varchar(120),                          ##工厂地址
        invoiceaddress          varchar(120),                          ##发票地址
        lastpurchasedate        timestamp,                             ##最后交易日
        paydays                 int      default 0,                    ##付款日数
        prepaid                 int      default 0,                    ##暂付款
        createdate              timestamp,                             ##创建时间
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##产品表
create table tbl_jxc_product (
        id                      int  auto_increment     not null,      ##主键
        productid               varchar(12)             not null,      ##产品ID
        siteid                  int                     not null,      ##站点ID
        productname             varchar(120)            not null,      ##产品名称
        safestock               int,                                   ##安全存量
        lastpurchasedate        timestamp,                             ##最近进货日期
        lastdeliverydate        timestamp,                             ##最近出货日期
        quantity                int         default 0,                 ##库存量
        createdate              timestamp,                             ##创建时间
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##进货单主表
create table tbl_purchasemaster (
        id                      int  auto_increment   not null,        ##主键
        purchaseid              varchar(12)           not null,        ##进货单号
        siteid                  int                   not null,        ##站点ID
        purchasedate            timestamp             not null,        ##进货日期
        supplierid              varchar(12)           not null,        ##供货商编号
        purchaseproperty        smallint              default 0,       ##进货单属性 0-进货  1-退货
        invoiceno               varchar(10),                           ##发票编号
        subtotal                double(36,2)          default 0,       ##合计金额
        valueaddtax             double(36,2)          default 0,       ##营业税
        amount                  double(36,2)          default 0,       ##总计金额
        accountpayable          double(36,2)          default 0,       ##应付金额
        paid                    double(36,2)          default 0,       ##已付帐款
        limitdate               timestamp,                             ##付款截止日期
        createdate              timestamp,                             ##创建时间
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##进货单明细表
create table tbl_purchasedetail (
        id                      int  auto_increment    not null,       ##主键
        purchaseid              varchar(12)            not null,       ##进货单号
        productid               varchar(12)            not null,       ##产品ID
        purchasequantity        int                  default 0,        ##进货数量
        purchaseunitprice       double(18,2),                          ##进货单价
        purchaseamount          double(36,2)         default 0,        ##进货总价
        createdate              timestamp,                             ##创建时间
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##出货单主表
create table tbl_deliverymaster (
        id                      int  auto_increment     not null,      ##主键
        deliveryid              varchar(12)             not null,      ##出货单号
        siteid                  int                     not null,      ##站点ID
        deliverydate            timestamp               not null,      ##出货日期
        customerid              varchar(12)             not null,      ##客户编号
        salesmanid              varchar(12)             not null,      ##业务员编号
        deliveryaddress         varchar(120),                          ##送货地址
        invoiceno               varchar(10),                           ##发票编号
        customerorderno         varchar(15),                           ##订单号码
        subtotal                double(36,2)            default 0,     ##合计金额
        valueaddtax             double(36,2)            default 0,     ##营业税
        amount                  double(36,2)            default 0,     ##总计金额
        accountreceivable       double(36,2)            default 0,     ##应收金额
        received                double(36,2)            default 0,     ##已收金额
        limitdate               timestamp,                             ##付款截止日期
        createdate              timestamp,                             ##创建时间
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##出货单明细表
create table tbl_deliverydetail (
        id                      int auto_increment      not null,      ##主键
        deliveryid              varchar(12)             not null,      ##出货单号
        productid               varchar(12)             not null,      ##产品ID
        salesquantity           int                     default 0,     ##出货数量
        salesunitprice          double(18,2),                          ##出货单价
        salesamount             double(36,2)            default 0,     ##出货总价
        createdate              timestamp,                             ##创建时间
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);


##库存变动主表
create table tbl_changemaster (
        id                      int auto_increment      not null,      ##主键
        changeid                varchar(12)             not null,      ##出货单号
        siteid                  int                     not null,      ##站点ID
        changedate              timestamp,                             ##变动日期
        description             varchar(255),                          ##变动说明
        createdate              timestamp,                             ##创建时间        
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##库存变动明细
create table tbl_changedetail (
        id                      int  auto_increment     not null,      ##主键
        changeid                varchar(12)             not null,      ##出货单号
        productid               varchar(12)             not null,      ##产品ID
        changequantity          int                     default 0,     ##变动数量
        changeamount            double(36,2)            default 0,     ##变动金额
        createdate              timestamp,                             ##创建时间        
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##付款冲帐主表
create table tbl_accountpayablemaster (
        id                      int  auto_increment    not null,      ##主键
        paymentid               varchar(12)            not null,      ##出货单号
        siteid                  int                    not null,      ##站点ID
        paydate                 timestamp              not null,      ##支付日期
        supplierid              varchar(12)            not null,      ##供应商ID
        paycash                 double(36,2)           default 0,     ##现金
        paycheck                double(36,2)           default 0,     ##支票
        noteno                  varchar(10),                          ##支票号码
        duedate                 timestamp,                            ##到期日
        bankid                  varchar(12)            not null,      ##开票银行
        accountno               varchar(20),                          ##帐号
        discount                double(36,2)           default 0,     ##折扣
        remittance              double(36,2)           default 0,     ##费用
        prepaid                 double(36,2)           default 0,     ##暂付款
        others                  double(36,2)           default 0,     ##其他
        payamount               double(36,2)           default 0,     ##付款金额
        totalbalance            double(36,2)           default 0,     ##实冲金额
        createdate              timestamp,                            ##创建时间        
        updatedate              timestamp,                            ##修改时间
        primary key (id)
);

##付款冲帐明细表
create table tbl_accountpayabledetail (
        id                      int  auto_increment     not null,      ##主键
        paymentid               varchar(12)             not null,      ##付款单号
        purchaseid              varchar(12)             not null,      ##进货单单号
        balance                 double(36,2)            default 0,     ##本次冲销金额
        createdate              timestamp,                             ##创建时间        
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##收款冲帐主表
create table tbl_accountreceivablemaster (
        id                      int  auto_increment     not null,     ##主键
        siteid                  int                     not null,     ##站点ID
        receiveid               varchar(12)             not null,     ##收款单号
        receivedate             timestamp,                            ##收款日期
        customerid              varchar(12)             not null,     ##客户编号
        receivecash             double(36,2)            default 0,    ##现金
        receivecheck            double(36,2)            default 0,    ##支票
        noteno                  varchar(10),                          ##支票号码
        duedate                 timestamp,                            ##到期日
        bankid                  varchar(12)             not null,     ##开票银行
        accountno               varchar(20),                          ##帐号
        discount                double(36,2)            default 0,    ##折扣
        remittance              double(36,2)            default 0,    ##费用
        advance                 double(36,2)            default 0,    ##暂收款
        others                  double(36,2)            default 0,    ##其他
        receiveamount           double(36,2)            default 0,    ##收款金额
        totalbalance            double(36,2)            default 0,    ##实冲金额
        createdate              timestamp,                            ##创建时间        
        updatedate              timestamp,                            ##修改时间
        primary key (id)
);


##收款冲帐明细表
create table tbl_accountreceivabledetail (
        id                      int  auto_increment     not null,      ##主键
        receiveid               varchar(12)             not null,      ##收款单号
        deliveryid              varchar(12)             not null,      ##出货单单号
        balance                 double(36,2)            default 0,     ##本次冲销金额
        createdate              timestamp,                             ##创建时间        
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##用户表
create table tbl_users (
        id                      int  auto_increment     not null,      ##主键
        userid                  varchar(12)             not null,      ##用户ID
        username                varchar(50)             not null,      ##用户名称
        passwordcod             varchar(36)             not null,      ##用户密码
        createdate              timestamp,                             ##创建时间        
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##程序表
create table tbl_programs (
        id                      int  auto_increment        not null,   ##主键
        programid               varchar(12)                not null,   ##程序ID
        programname             varchar(50)                not null,   ##程序名称
        programaddress          varchar(250)               not null,   ##程序URI
        createdate              timestamp,                             ##创建时间        
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);

##授权表
create table tbl_userauthority (
        id                      int  auto_increment     not null,      ##主键
        userid                  varchar(12)             not null,      ##用户ID
        programid               varchar(50)             not null,      ##程序id
        running                 smallint,                              ##程序执行权限
        createdate              timestamp,                             ##创建时间        
        updatedate              timestamp,                             ##修改时间
        primary key (id)
);


##北京市无线电管理局系统
create table tbl_rsbt_org(
	id			int        auto_increment       not null,
	guid			varchar(36)			not null,
	userid			varchar(36)			not null,
	siteid			int				not null,
	password		varchar(36)			not null,
	org_gode		varchar(9) default 999999999	not null,		##组织机构代码
	org_name		varchar(80)			not null,		##组织机构名称
	org_area_code		varchar(8)			not null,		##地区代码
	org_sys_code		varchar(8)			not null,		##系统/行业代码
	org_type		varchar(3)			not null,		##单位类型
	org_link_person		varchar(40),						##单位联系人
	org_person_id		varchar(40),						##联系人身份证号码
	org_sup_code		varchar(9),						##上级组织机构
	org_addr		varchar(80),						##组织机构地址
	org_post		varchar(6),						##组织机构邮编
	org_phone		varchar(40),						##联系电话
	org_mob_phone		varchar(40),						##手机号码
	org_fax			varchar(40),						##组织机构传真
	org_bank		varchar(80),						##开户银行
	org_account_name	varchar(80),						##账户名称
	org_account		varchar(40),						##银行账号
	org_hostility		smallint,						##设台单位性质
	org_web_site		varchar(80),						##网址
	org_mail		varchar(80),						##电子邮箱
	createdate		timestamp,
	primary key(id)
);

create table tbl_rsbt_org1(
	id			int        auto_increment       not null,
	value			int				not null,
	name			varchar(50)			not null,
	primary key(id)
);

create table tbl_rsbt_org2(
	id			int        auto_increment       not null,
	value			int				not null,
	name			varchar(50)			not null,
	primary key(id)
);

create table tbl_rsbt_org3(
	id			int        auto_increment       not null,
	value			int				not null,
	name			varchar(50)			not null,
	primary key(id)
);

create table tbl_Appointment
(
    id                          int   auto_increment    not null,
    codeinfo                    varchar(100)            not null,
    codename                    varchar(100)            not null,
    appointmentdate             varchar(30)             not null,
    appointmentcode             varchar(6)              not null,
    siteid                      int    default 0        not null,
    primary key(id) 
);

create table tbl_userappointment
(
    id                          int   auto_increment    not null,
    userid                      varchar(100)            not null,
    appointmentdate             varchar(30)             not null,
    appointmentcode             varchar(6)              not null,
    siteid                      int    default 0        not null,
    primary key(id)
);


##设置无线电台申报表
create table tbl_zhuduijiangji(
	djjid			int   auto_increment    not null,		##主键
	shetaidanwei		varchar(50),				        ##设台单位
	suoshuxitong		varchar(50),					##所属系统
	address			varchar(100),					##地址
	lianxiren		varchar(20),					##联系人
	phone			varchar(20),					##电话
	chuanzhen		varchar(20),					##传真
	shiyongdiqu		varchar(50),					##使用地区
	diantaishuliang		varchar(20),					##电台数量
	diantaileibie		varchar(50),					##电台类别	150MHz,400MHz,900MHz无中心地址
	shetailiyou		varchar(500),					##设台理由
	lingdaoyijian		varchar(500),					##上级主管单位领导意见
	guanlijuyijian		varchar(500),					##北京市无线电管理局意见
	xitongbianhao		varchar(100),					##系统编号
	zhizhaobianhao		varchar(100),					##执照编号
	primary key(djjid)
);

create table tbl_diantaiziliao(
	id			int   auto_increment    not null,		##主键
	dtzlid			int			not null,
	shebeixinghao		varchar(100),					##设备型号
	chuchanghaoma		varchar(100),					##出厂号码
	beizhu			varchar(100),					##备注
	CONSTRAINT fk_dtzlID FOREIGN KEY (dtzlid) REFERENCES  tbl_zhuduijiangji(djjid) ON DELETE CASCADE,
	primary key(id)
);

##信息采集表
create table sp_basic_attributes (
	id                     int   auto_increment    not null,
	sitename               varchar(50),
	starturl               varchar(255),
	urlnumber              int,
	status                 varchar(5),
	posturl                varchar(255),
	postdata               varchar(255),
	loginflag              smallint                 not null,
	proxyflag              smallint                 not null,
	stopflag               smallint                 not null,
	keywordflag            smallint,
	catchdepth             int,
        primary key(id) 
);

create table sp_document_type (
	id                    int                      not null,
	starturlid            int,
	doctype               varchar(5) 
);

create table sp_global (
	id                    int    auto_increment    not null,
	starttime             timestamp,
	inter_val              int,
	proxyflag             smallint,
	proxyname             varchar(50),
	proxyport             varchar(20),
	proxyloginuser        varchar(50),
	proxyloginpass        varchar(50),
	proxyloginflag        smallint                  not null,
	systemrun             smallint                  not null,
	tkeyword              varchar(250),
	bkeyword              varchar(250),
	tbrelation            smallint,
	keywordflag           smallint,
        primary key(id) 
);

create table sp_keyword_rules (
	id                    int auto_increment       not null,
	starturlid            int,
	Tkeyword              varchar(250),
	bkeyword              varchar(250),
	tbrelation            smallint,
        primary key(id) 
);

create table sp_match_url (
	id                    int auto_increment       not null,
	starturlid            int,
	matchurl              varchar(255),
	primary key(id) 
);

create table sp_proxy (
	id                    int auto_increment       not null,
	starturlid            int,
	proxyurl              varchar(500),
	proxyport             varchar(20),
	proxyloginflag        smallint,
	proxyloginuser        varchar(50),
	proxyloginpass        varchar(50),
        primary key(id)
);

create table sp_special_code (
	id                    int auto_increment       not null,
	starturlid            int,
	ST                    varchar(255),
	ET                    varchar(255),
        primary key(id)
);

create table tbl_workload (
	Id                    int  auto_increment      not null,
	siteid                int,
	starturlid            int,
	url                   varchar(500),
	status                varchar(5),
	lastmodified          timestamp,
	urltitle              varchar(200),
	urltype               smallint,
	depth                 int,
	thedate               timestamp,
        primary key(id) 
);


##以下为工作流表单
create table OS_PROPERTYENTRY
(
	GLOBAL_KEY varchar(255),
	ITEM_KEY varchar(255),
	ITEM_TYPE smallint,
	STRING_VALUE varchar(255),
	DATE_VALUE date,
	DATA_VALUE blob,
	FLOAT_VALUE float,
	NUMBER_VALUE numeric,
	primary key (GLOBAL_KEY, ITEM_KEY)
);

create table OS_USER
(
    USERNAME varchar(20),
    PASSWORDHASH varchar(2024),
    primary key (USERNAME)
);

create table OS_GROUP
(
    GROUPNAME varchar(20),
    primary key (GROUPNAME)
);

create table OS_MEMBERSHIP
(
    USERNAME varchar(20),
    GROUPNAME varchar(20),
    primary key (USERNAME, GROUPNAME),
    foreign key (USERNAME) references OS_USER(USERNAME),
    foreign key (GROUPNAME) references OS_GROUP(GROUPNAME)
);

create table OS_WFENTRY
(
    ID number,
    NAME varchar(20),
    STATE integer,
    primary key (ID)
);

create table OS_CURRENTSTEP
(
    ID number,
    ENTRY_ID number,
    STEP_ID integer,
    ACTION_ID integer,
    OWNER varchar(20),
    START_DATE date,
    FINISH_DATE date,
    DUE_DATE date,
    STATUS varchar(20),
    CALLER varchar(20),
    primary key (ID),
    foreign key (ENTRY_ID) references OS_WFENTRY(ID),
    foreign key (OWNER) references OS_USER(USERNAME),
    foreign key (CALLER) references OS_USER(USERNAME)
);

create table OS_HISTORYSTEP
(
    ID number,
    ENTRY_ID number,
    STEP_ID integer,
    ACTION_ID integer,
    OWNER varchar(20),
    START_DATE date,
    FINISH_DATE date,
    DUE_DATE date,
    STATUS varchar(20),
    CALLER varchar(20),
    primary key (ID),
    foreign key (ENTRY_ID) references OS_WFENTRY(ID),
    foreign key (OWNER) references OS_USER(USERNAME),
    foreign key (CALLER) references OS_USER(USERNAME)
);

create table OS_CURRENTSTEP_PREV
(
    ID number,
    PREVIOUS_ID number,
    primary key (ID, PREVIOUS_ID),
    foreign key (ID) references OS_CURRENTSTEP(ID),
    foreign key (PREVIOUS_ID) references OS_HISTORYSTEP(ID)
);

create table OS_HISTORYSTEP_PREV
(
    ID number,
    PREVIOUS_ID number,
    primary key (ID, PREVIOUS_ID),
    foreign key (ID) references OS_HISTORYSTEP(ID),
    foreign key (PREVIOUS_ID) references OS_HISTORYSTEP(ID)
);

create sequence seq_os_wfentry minvalue 10 increment by 10;
create sequence seq_os_currentsteps;

##文章评论
CREATE TABLE TBL_COMMENT ( 
	id             int   auto_increment     not null,                ##主键
	siteid         int                      not null,                ##站点id
	userid         int                      not null,                ##用户id
	Username       varchar(30)              not null,                ##用户登录名称
	pid            int                      not null,                ##商品id
	commenttitle   varchar(200)             not null,                ##标题
	Scores         int                      default 0,               ##评分
	experience     varchar(500),                                     ##使用心得
	Advantages     varchar(500),                                     ##优点
	Shortcomings   varchar(500),                                     ##不足
	usefulnum      int                      default 0,               ##认为该评论有用的人数
	nousenum       int                      default 0,               ##认为该评论无用的人数
	auditflag      int                      default 0,               ##0 审核通过，可显示 1-审核未通过 不显示 		
	createdate     timestamp                default current_date(),  ##评论时间
	CONSTRAINT pk_commentid PRIMARY KEY (ID)	
);

##文章评论回复
create table Tbl_commentanswer
(
        id              int   auto_increment     not null,               ##主键
        cid             int                      not null,               ##商品评论id外键
        username        varchar(100)             not null,               ##回复人登录名
        answercontent   varchar(500)             not null,               ##回复内容
        createdate      timestamp                default current_date(),      ##回复时间
	CONSTRAINT fk_commentID FOREIGN KEY (cid) REFERENCES  tbl_comment(ID) ON DELETE CASCADE,
        primary key(id)
);

##发票信息表  新增加
create table en_invoice
(
        id               int   auto_increment     not null,                  ##主键
        siteid           int                      not null,                  ##站点id
        userid           int                      not null,                  ##用户id
        orderid          bigint                   not null,                  ##用户订单号
        invoicetype      int                      default 0,                 ##发票类型(0-普通发票1-增值税发票)
        titleflag        int                      default 0,                 ##（个人/单位0-个人1-单位）
        title            varchar(100),                                       ##发票抬头
        companyname      varchar(200),                                       ##单位名称
        identification   varchar(200),                                       ##纳税人识别号
        registeraddress  varchar(200),                                       ##注册地址
        phone            varchar(15),                                        ##注册电话
        postaddress      varchar(200),                                       ##邮寄地址
        postname         varchar(20),                                        ##接收人姓名
        postcode         varchar(6),                                         ##邮政编码
        bankname         varchar(200),                                       ##开户银行
        bankaccount      varchar(100),                                       ##银行帐号
        totalprice       money,                                              ##发票总价值
        createdate       timestamp                default current_date(),    ##创建时间
        primary key(id)
);

##发票内容维护表
create table en_invoicecontent
(
        id              int   auto_increment     not null,                   ##主键,每个发票项的ID
        invoiceid       int                      not null,                   ##发票ID
        siteid          int                      not null,                   ##站点id
        [content]       varchar(200)             not null,                   ##内容
        price           money,                                               ##商品单价
        pnum            int                      default 0,                  ##商品数量
        createdate      timestamp                default current_date(),     ##创建时间
	CONSTRAINT      fk_contentid FOREIGN KEY (invoiceid) REFERENCES  en_invoice(id) ON DELETE CASCADE,
        primary key(id)
);

##商品分类编码
create table tbl_wzclass
(
       id                int    auto_increment not null,                  ##主键ID
       customerid        int                   not null,                  ##物料编码的拥有者     0-表示系统定义的物资分类
       name              varchar(200)         not null,                   ##编码或者分类名称
       brief             varchar(500),                                    ##物资分类描述
       unit              varchar(10),                                     ##本类物资的计量单位
       keyword           varchar(20),                                     ##本类物资的关键字
       code              varchar(100)         not null,                   ##编码
       pcode             varchar(100)         not null,                   ##父编码
       orderid           int,                                             ##排序
       valid             int                default 0,                    ##物资编码的状态     1--使用   0--停用
       delflag           int                default 0,                    ##删除标识，         0--使用   1--删除
       creator           varchar(50),                                     ##创建人
       createdate        timestamp          default current_date(),       ##创建时间
       createcomp        int,                                             ##创建者所在的公司
       editor            varchar(50),                                     ##修改人
       lastupdate        timestamp,                                       ##最后修改时间
       updatecomp        int,                                             ##最后修改人所在公司 
       primary  key(id)
);

##物资分类模板信息
create table tbl_wzclass_template
(
       id                int    auto_increment not null,                  ##主键ID
       classid           int                not null,                     ##物资分类主键ID
       status            int                default 0,                    ##是否停用    0--使用   1--停用
       delflag           int                default 0,                    ##是否删除    0--使用   1--删除
       templatexml       text,                                            ##模板定义的XML字符串
       customerid        int                not null,                     ##物料编码的拥有者
       creator           varchar(50),                                     ##创建人
       createdate        timestamp,                                       ##创建时间
       createcomp        int,                                             ##创建者所在的公司
       editor            varchar(50),                                     ##修改人
       lastupdate        timestamp,                                       ##最后修改时间
       updatecomp        int,                                             ##最后修改人所在公司 
       primary key(id)
);

##物资明细信息
create table tbl_wzdetail
(
       id                int    auto_increment not null,                  ##主键ID
       classid           int                   not null,                  ##物资分类主键ID
       wzname            varchar(200)         not null,                   ##物资名称
       wzcode            varchar(21)          not null,                   ##物资编码明细
       unit              varchar(10),                                     ##物资单位
       customerid        int                not null,                     ##物料编码的拥有者
       status            int                default 0,                    ##物资上架销售
       delflag           int                default 0,                    ##物资是否被删除   0--正常  1--删除
       creator           varchar(50),                                     ##创建人
       createdate        timestamp,                                       ##创建时间
       createcomp        int,                                             ##创建者所在的公司
       editor            varchar(50),                                     ##修改人
       lastupdate        timestamp,                                       ##最后修改时间
       updatecomp        int,                                             ##最后修改人所在公司 
       primary key(id)
);

##物资明细信息属性表
create table tbl_wzdetail_part
(
       id                int    auto_increment not null,                  ##主键ID
       classid           int                not null,                     ##物资分类主键ID
       proptype          int                default 0,                    ##特征值      1-材质  2-
       ename             varchar(200)         not null,                   ##物资属性信息的英文名称
       type              int                not null,                     ##物资属性的数据类型
       textvalue         text,                                            ##存储大文本属性值
       stringvalue       varchar(4000),                                   ##存储字符串属性值       
       minintvalue       int,                                             ##存储整数型区间最小值，或者是存储整数类型值
       maxintvalue       int,                                             ##存储整数型区间最大值
       minfloatvalue     int,                                             ##存储浮点型区间最小值，或者是存储浮点数类型值
       maxfloatvalue     int,                                             ##存储浮点型区间最大值
       creator           varchar(50),                                     ##创建人
       createdate        timestamp,                                       ##创建时间
       createcomp        int,                                             ##创建者所在的公司
       editor            varchar(50),                                     ##修改人
       lastupdate        timestamp,                                       ##最后修改时间
       updatecomp        int,                                             ##最后修改人所在公司 
       primary key(id)
);


##物资服务的区域信息
create table tbl_wzservicearea
(
       id                int    auto_increment not null,                  ##主键ID
       classid           int                not null,                     ##物资分类主键ID
       wzid              int                not null,                     ##物资编码
       areaid            int                not null,                     ##物资服务的区域编码
       status            int                default 0,                    ##物资上架销售
       customerid        int                not null,                     ##物料编码的拥有者
       delflag           int                default 0,                    ##物资是否被删除   0--正常  1--删除
       creator           varchar(50),                                     ##创建人
       createdate        timestamp,                                       ##创建时间
       createcomp        int,                                             ##创建者所在的公司
       editor            varchar(50),                                     ##修改人
       lastupdate        timestamp,                                       ##最后修改时间
       updatecomp        int,                                             ##最后修改人所在公司 
       primary key(id)
);

##物资图片表
create table tbl_wzpictures
(
       id                int    auto_increment not null,                  ##主键ID
       classid           int                not null,                     ##物资分类主键ID
       wzid              int                not null,                     ##物资编码
       picurl            varchar(500)         not null,                   ##物资图片URL
       filename          varchar(100),                                    ##物资图片文件名称，只接受jpg,jpeg,png格式图片
       sfilename         varchar(100),                                    ##物资图片上传时的原始名称
       customerid        int                not null,                     ##物料编码的拥有者
       delflag           int                default 0,                    ##物资图片是否被删除   0--正常  1--删除
       creator           varchar(50),                                     ##创建人
       createdate        timestamp,                                       ##创建时间
       createcomp        int,                                             ##创建者所在的公司
       editor            varchar(50),                                     ##修改人
       lastupdate        timestamp,                                       ##最后修改时间
       updatecomp        int,                                             ##最后修改人所在公司 
       primary key(id)
);

##物资附件表
create table tbl_wzattachements
(
       id                int    auto_increment not null,                  ##主键ID
       classid           int                not null,                     ##物资分类主键ID
       wzid              int                not null,                     ##物资编码
       attachementurl    varchar(500)         not null,                   ##物资附件URL
       filename          varchar(100),                                    ##物资附件文件名称，只接受PDF、DOC、DOCX、PPT、PPTX格式文件
       sfilename         varchar(100),                                    ##物资附件上传时的原始名称
       customerid        int                not null,                     ##物料编码的拥有者
       delflag           int                default 0,                    ##物资附件是否被删除   0--正常  1--删除
       creator           varchar(50),                                     ##创建人
       createdate        timestamp,                                       ##创建时间
       createcomp        int,                                             ##创建者所在的公司
       editor            varchar(50),                                     ##修改人
       lastupdate        timestamp,                                       ##最后修改时间
       updatecomp        int,                                             ##最后修改人所在公司 
       primary key(id)
);
