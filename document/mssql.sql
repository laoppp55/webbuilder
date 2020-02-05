create table bbs_thread(
	threadid	int		not null,
	lyhid		int,
	forumid		int,
	answerid	int          default 0,
	threadname	varchar(500),
	threadcontent	varchar(500),
	posttime	datetime,
	author		varchar(500),
	ipaddress	varchar(500),
	answeruser	varchar(500),
	answertime	datetime,
	hitnum		int,
	answernum	int,
	gotop		int          default 0,
	typeflag	int,
	hiddenflag	int          default 0,
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
	postdate	datetime,
	lastposter	varchar(500),
	lasttopic	varchar(500),
	lastposttime	datetime,
	pic		varchar(500),
	creationdate	datetime,
	threadnum	int,
	topicnum	int,
	siteid		int,
	primary key(id)
);


create table bbs_online(
	id		varchar(500)		not null,         --主键ID
	lyhid		int,                                   --登录用户ID
	username	varchar(500),                             --登录用户名
	logintime	datetime,                                     --最后登录时间
	loginnum        int,                                   --登录次数
	loginstatus     int,                                --登录状态
	lastactivetime  datetime,                                     --用户最后活动时间
	ipaddress	varchar(500)                              --登录者的IP地址
);


create table bbs_friend(
	userid		varchar(500),
	lyhid		int,
	friend		varchar(500)
);


create table TBL_SEARCHWORD
(
  SITEID     int,
  IP         varchar(20),
  KEYWORD    varchar(200),
  CREATEDATE datetime
);

create table cs_room_info(
	id                       int      identity(1, 1)     not null,                     --标识符,农家院户型ID
	siteid                   int               not null,	                 --农家院注册的站点ID
	room_name	         varchar(100)     not null,                     --房间的名称或者编号
	catlog_code              char(8)           default '00000000',           --分类编码，暂时不用
	room_type 	         tinyint 	   default 2,                    --1--单人房 2--标准双人房  3--三人房   4--四人房  5--五人房  6--火炕房
        room_num                 int               default 1,                    --该类型房的数量
	room_way                 tinyint,                                       --房间朝向，1-东、2-西、3-南、4-北、5-东南、6-东北、7-西南、8-西北
	room_size                int,                                            --房间面积   
	init_price               decimal(10,2),                                   --起始价格
        bed_num                  int               default 1,                    --房间内床位数量
        bed_type                 int,                                            --床型，1--单人床（1.2米宽）2--单人床（0.9米宽）3--单人床（1.05米宽） 4--双人床（1.35米宽） 5--双人床（1.5米宽） 6--双人床（1.8米宽）7--火炕（5米宽）
        floor                    varchar(20),                                    --楼层，1层、2层、2层（有电梯）、3层、3层（有电梯）、4层、4层（有电梯）、5层、5层（有电梯）6层、6层（有电梯）
	toilet                   tinyint          default 1,                    --卫生间数量
        tv                       varchar(100),                                  --电视设施描述
        airconditioner           varchar(100),                                  --空调设施描述
	bathroom                 varchar(100),                                  --洗浴设施描述
	bedclothes               varchar(200),                                  --床上用品描述
        amenities                varchar(500),                                  --便利设施，电视机，独立卫浴，无线网络、行李寄存、矿泉水、电冰箱、24小时热水、有线网络、免费停车位、空调、淋浴、交通便利、可接待家庭/孩子、热水壶、热水浴缸、免费接送、适合举办活动、吹风机、毛巾、免费脚踏车、拖鞋、代订门票、茶包、一次性盥洗用品、代订包车、咖啡包
	specialservice           varchar(50),                                   --早餐服务、接送服务
	delflag                  tinyint         default 0,                     --信息被删除标示，0--使用  1--删除
	primary key (id)
);


create table tbl_code (
       id                        int      identity(1, 1)      not null,        --标识符
       typecode     		 varchar(30),		                       --第一级编码
       typename		         varchar(30),                                  --第一级编码名称
       detailcode                varchar(30),                                  --第二级编码
       detailname                varchar(30),                                  --第二级编码名称
       validity                  tinyint,                                      --编码是否有效
       attribute                 varchar(50),                                  --编码属性值
       standardword              varchar(50),                                  --标准关键字
       primary key (id)
);


create table tbl_sites_number (
        ipaddress varchar(15),
	sitesnum int,
	hashcode int,
        primary key (ipaddress)
);

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
insert into tbl_right values(60,'企业注册用户管理','ERU','企业注册用户管理');
insert into tbl_right values(61,'工作日管理','WD','工作日管理');
insert into tbl_right values(62,'业务审批结果','YWR','业务审批结果');

create table tbl_role (
	roleid			int  identity(1, 1)     not null,
	rolename		varchar(30)		not null,
	rolecat		        varchar(30),
	roledesc		varchar(100),
        primary key (roleid)
);

insert into tbl_role values('信息员','XXY','负责向网站录入信息');
insert into tbl_role values('部门领导','BML','录入信息的审核');
insert into tbl_role values('主管领导','ZGL','录入信息的审核');
insert into tbl_role values('系统管理员','SM','超级系统管理员');
insert into tbl_role values('留言版管理员','LWM','留言版管理员');
insert into tbl_role values('部门留言版管理员','DLWM','部门留言版管理员');

create table tbl_sjs_log (
	id			int	identity(1, 1)	not null,               --主键
	siteid			int     		not null,               --站点ID           
	sitename		varchar(50)		not null,               --站点名称
        columnid                int,                                            --栏目ID
        columnname              varchar(50),                                    --栏目名称
	articleid		int			not null,               --文章ID
	maintitle		varchar(600)	        not null,               --文章中文标题
        articleurl              varchar(300),                                   --文章URL
        operationtype           smallint,                                       --文章操作类型    1-增加   2-修改 3-删除 
        editor                  varchar(50),                                    --文章作者
	createdate	        datetime,                                       --文章录入日期
        updatedate              datetime,                                       --文章修改日期
        deletedate              datetime,                                       --文章删除日期
        primary key (id)
);


create table tbl_log (
	id                      int   identity(1, 1) not null,
	articleid               int	         not null,
	siteid	                int              not null,
	columnid                int              not null,
	editor                  varchar(30)      not null,
	acttype                 smallint         not null,
	acttime	                datetime         not null,
	maintitle               varchar(600)     not null,
	createdate              datetime,
        primary key (id)
);

create table tbl_members (
        id                      int   identity(1, 1) not null,
        userid                  varchar(30)   	not null,
	mmuserid                varchar(32),
        siteid                  int             not null,
        userpwd                 varchar(32)     not null,
        nickname                varchar(30)     not null,
        email                   varchar(50),
        phone                   varchar(12),                      --联系电话        
	mphone                  varchar(12),        
	sex                     tinyint              default(0),      --0表示男 1表示女
        country                 varchar(30),                          --国家
        province                varchar(50),                          --省
        city                    varchar(50),                          --市
        area                    varchar(50),                          --区县
        jiedao                  varchar(50),                          --街道
        shequ                   varchar(50),                          --社区
        postcode                varchar(6),                           --邮政编码
        birthdate               datetime,                                  --出生日期
        myimage                 varchar(100),                         --头像
        usertype                tinyint              default(0),      --用户类型    0---个人用户   1--企业用户        
        joincompanyid           int,                                --加盟商ID号
        joinid                  int,                                --加盟商序列号
        dflag                   int,                                --站点是否被删除
        createarticles	        int  default(0),                    --用户创建文章数量
        editarticles		int  default(0),                    --用户修改文章数量
        deletearticles	        int  default(0),                    --用户删除文章数量
        score                   int     default(0),
        createdate              datetime,
        company                 varchar(200),
        address                 varchar(500),
        department              varchar(50),                          --所属部门
        emailaccount            varchar(100),                         --email帐号
        emailpasswd             varchar(100),                        --email密码
        departmentarticlestype  int default(0),                     --审核文章的范围0-所有部门1-本部门2-指定部门
        departmentarticlesids   varchar(500),                         --指定审核文章的部门id  
        orgid                   int,                                --用户所属组织架构ID
	companyid               int,                                --用户所属公司ID
	deptid                  int,                                --用户所属部门ID
	primary key (userid)
);


insert into tbl_members(userid,siteid,userpwd,nickname,email,createdate) values('admin',-1,'4346D362F67C89D7AB4E28732A1B1CE8','系统管理员','p@biz.com',getdate());

--系统角色表
create table tbl_member_roles (
       id                       int identity(1, 1)      not null,
       siteid                   int                     not null,
       userid                   varchar(30)             not null,
       deptid                   int                     not null,      --用户所属部门ID       
       rolename                 varchar(20)             not null,      --信息员   部门负责人   主管
       rolelevel                tinyint,                               --角色级别 0-9
       primary key(id)
);

--授权用户管理的留言表
create table tbl_member_authorized_resouce (
       id                       int identity(1, 1)         not null,
       siteid                   int                        not null,
       userid                   varchar(30)                not null,
       lwid                     int,                                     --该用户管理的留言表ID
       lwname                   varchar(100),                             --该用户管理的留言表的名称
       lwrole                   varchar(20),                              --留言板管理角色 是某个留言板的管理员 或是留言板的部门管理员
       contenttype              int                         default(0),    --内容类型 0为留言板
       primary key(id)
);

-- 组织架构表
create table tbl_organization
(
       id                      int identity(1, 1)  not null,             --主键ID 
       parent                  int,                                      --父ID
       cotype                  int default(0),                           --节点类型 1--子公司   2--部门
       customerid              int,                                      --客户ID，对应着站点ID
       orderid                 int,                                      --节点在同层中的序号
       nodeid                  int,                                      --节点对应子公司、部门
       status                  int default(1),                           --状态 0 无效 1 有效
       name                    varchar(255) not null,                    --节点名称
       ename                   varchar(255),                             --节点英文名称
       llevel                  int default(1),                           --组织结构层级
       orgcode                 varchar(50),                              --组织节点编码
       isleaf                  int default(0),                           --是否是叶子节占，0为非叶子节点，1为叶子节点
       lastupdate              datetime default getdate(),               --最后修改日期
       createdate              datetime default getdate(),               --创建日期
       createuser              varchar(50),                              --创建人
       uodateuser              varchar(50),                              --修改人
       primary key(id)
);



--企业部门设置
create table tbl_department (
       id                      int identity(1, 1)       not null,      --主键
       parent                  int            not null,                --父ID
       companyid               int            not null,                --所属公司ID
       siteid                  int            not null,                --站点ID
       cname                   varchar(100)     not null,              --部门中文名称
       ename                   varchar(100),                           --部门英文名称
       telephone               varchar(12),                            --电话号码
       manager                 varchar(50),                            --部门经理
       vicemanager             varchar(50),                            --部门副经理
       leader                  varchar(50),                            --部门主管领导
       lastupdate              datetime default getdate(),             --最后修改日期
       createdate              datetime default getdate(),             --创建日期
       createuser              varchar(50),                            --创建人
       primary key(id)
);          



--每个用户在每个站点的每个栏目中录入的文章数量
create table tbl_user_articlebycolumn (
       id                       int   identity(1, 1)    not null,
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
	groupid                 int  identity(1, 1)  not null,
	siteid                  int                  not null,
	groupname   		varchar(20)          not null,
	groupdesc   		varchar(100),
        primary key (groupid)
);

create table tbl_article_auditing_info (
	id              	int    identity(1, 1)        not null,
	articleid       	int                          not null,
	messagetype             int                          default(0),                       --0表示栏目文章信息   1表示用户留言信息
	sign            	varchar(30)    	             not null,
	comments        	varchar(500),
	status          	smallint       	             not null,
	backto          	varchar(30),
	createdate      	datetime                     not null,
        primary key (id)
);


create table tbl_column_auditing_rules (
	id                   	int   identity(1, 1)    not null,
	columnid             	int             	not null,
	messagetype             int                     default(0),      --0表示栏目文章信息  1表示用户留言信息
	column_audit_rule    	varchar(300)		not null,
	audittype               int                     default(0),      --定义审核方式 0-按用户名称定义审核规则   1-按角色定义审核规则
	createdate           	datetime		not null,
	lastupdated          	datetime		not null,
	creator              	varchar(30)		not null,
	editor               	varchar(30)		not null,
        primary key (id)
);


--按“与”关系划分的审核操作路径
create table tbl_column_auditing_process (
	id                   	int  identity(1, 1)     not null,
	rulesid             	int             	not null,
	subrules_by_or          varchar(300),                            --分解后的OR审核规则
	createdate           	datetime		not null,
	lastupdated          	datetime		not null,
	creator              	varchar(30)		not null,
	editor               	varchar(30)		not null,
        primary key (id)
);

create table tbl_article (
	id                      int  identity(1, 1)  not null,
	columnid                int                  not null,
	siteid                  int                  not null,
	sortid                  int,
	maintitle               varchar(600)         not null,
	md5title                varchar(35),                               --文章标题的MD5格式摘要
	vicetitle               varchar(600),
	summary                 varchar(1000),
	keyword                 varchar(200),
	source                  varchar(600),
	content              	text,
	emptycontentflag        smallint            not null,
        multimediatype          smallint           default(0),             --多媒体文章标志0-不是多媒体文章 1-是多媒体文章	
	author                  varchar(600),
	articleversion       	int,
	publishtime             datetime                not null,
	createdate              datetime                not null,
	lastupdated             datetime                not null,
	dirname                 varchar(200)        not null,
	filename                varchar(60),
	editor                  varchar(30)         not null,
        creator                 varchar(30)        	not null,           --信息创建者名称
	status               	smallint            not null,
	doclevel             	int                 not null,
	vicedoclevel            int,
	pubflag                 smallint            not null,
	auditflag               smallint            not null,
	subscriber           	smallint            not null,
	lockstatus              smallint            not null,
	lockeditor              varchar(30),
	auditor                 varchar(200),
	ispublished          	smallint            not null,
	relatedartid            varchar(40),
	indexflag               smallint            not null,
	isjoinrss               smallint            not null,
	clicknum                int                 not null,
	referID                 int                 not null,
	modelID                 int                 not null,
	notearticleid           int                     default(0),         --定义与该篇文章相关联的其他文章的内容，其他文章的内容可以是本页面一个标签的内容
	saleprice               decimal(9,2),
	inprice                 decimal(9,2),
	marketprice             decimal(9,2),
	vipprice                decimal(9,2),	
	stocknum                int,
	weight                  int,
	brand                   varchar(50),
	pic                     varchar(30),
	bigpic                  varchar(30),	
	score                   int,
	voucher                 decimal(9,2),
	articlepic              varchar(600),
	urltype		        smallint            default 0,
	defineurl               varchar(200),
	wordsnum                int                 default 0,
	downfilename	        varchar(100),
	beidate                 datetime,
        salesnum                int,
        commentnum              int                 default 0,        --文章被评论的次数
	changepic               smallint            default 0,
	fromsiteid              int                 default 0,        --标识抓取信息的来源站点
	sarticleid              varchar(50),                          --抓取文章来源网站的ID 
	mediafile               varchar(500),                         --多媒体文件名称
	processofaudit          int                 default 0,        --被审核文章执行到的审核步骤
        deptid                  varchar(50),                          --文章发布的部门ID
	t1                      int                 default 0,
	t2                      int                 default 0,
	t3                      int                 default 0,
	t4                      int                 default 0,
	t5                      int                 default 0,
	deptid                  varchar(50),                               --文章发布的部门ID
        primary key (id)
);

create table tbl_article_extendattr (
	id                      int identity(1, 1)  not null,
	articleid               int                 not null,
	ename                   varchar(50)         not null,
	type                    smallint            not null,
	textvalue               text,
	stringvalue             varchar(4000),
	numericvalue            int,
	floatvalue              decimal(9,2),
	CONSTRAINT fk_articleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)
);

--被抓取文件与信息源相关的信息
create table tbl_article_sourceinfo
(
        id                            int                  not null,
        articleid                     int                  not null,
	maintitle            	      varchar(600)         not null,           --文章标题        
	md5title                      varchar(35),                             --文章标题的MD5格式摘要
	publishtime_from_source       datetime		   not null,           --文章在被抓取网站的发布时间
	sourceid                      int,                                     --抓取文章来源网站的ID 
        createdate                    datetime,                                --信息被抓取的时间
  	CONSTRAINT fk_articleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)        
);


create table tbl_deleted_article (
        siteid                  int                not null,
	columnid                int                not null,
        articleid               int                not null,
	sitename                varchar(100),
	acttype                 tinyint           default    0     --0表示被删除的文章   1表示被修改的文章        
);

create table tbl_template (
	id                      int  identity(1, 1)  not null,
	siteid                  int  default 0,
	columnid                int                  not null,
	isarticle               int                  not null,
	content              	text,
	createdate              datetime                 not null,
	lastupdated             datetime                 not null,
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

create table tbl_template_extend (
	id                      int  identity(1, 1)  not null,
	siteid                  int       default 0,
	columnid                int                  not null,
        errorurl                varchar(150),
        successurl              varchar(150),
        tablebackcolor          varchar(10),
        primary key(id)
);

create table tbl_column (
	id                      int identity(1, 1)      not null,
	siteid               	int                     not null,
	parentid             	int                     not null,
	orderid                 int                     not null,
	cname                   varchar(50)             not null,
	ename                   varchar(50)             not null,
	dirname                 varchar(200)            not null,
	editor                  varchar(30)             not null,
	extname                 varchar(10)             not null,
	columndesc              varchar(200),
	xmltemplate             text,
	isdefineattr            smallint                not null,
 	hasarticlemodel         smallint                not null,
	createdate              datetime                not null,
	lastupdated             datetime                not null,
	isaudited               smallint                not null,
	isproduct               smallint                not null,
	ispublishmore           smallint                not null,
	languagetype            smallint                not null,
	isrss                   smallint         default 0,
	getRssArticleTime       smallint,
	archivingrules          smallint         default 0,
	useArticleType          smallint         default 0,
	istype                  smallint         default 0,
	xmltype                 varchar(3000),                         --文章分类的定义
	userflag                smallint         default 0,            --栏目文章是否只有注册用户可看  0--所有用户 1--注册用户
	userLevel               int              default 0,            --用户浏览文章的级别
	encodingflag            smallint,                              --文档编码格式 1-utf-8  2-gb2312  3-gbk
	doctype                 varchar(500),                          --文档类型   HTML5  HTML4
	islocation              smallint         default(1),            --栏目存储位置信息
	contentshowtype	        smallint,                              
	publicflag              smallint         default 0   not null,
	shareflag               smallint         default 0,            --栏目是否是和其他网站共享的栏目
	articlecount            int              default 0,            --本栏目的文章总数量
	titlepic                varchar(10),                           --标题图片大小
	vtitlepic               varchar(10),                           --副标题图片大小
	sourcepic               varchar(10),                           --来源图片大小
	authorpic               varchar(10),                           --作者图片大小
	contentpic              varchar(10),                           --内容图片大小
	specialpic              varchar(10),                           --文章特效图片大小
	productpic              varchar(10),                           --商品大图片大小
	productsmallpic         varchar(10),                           --商品小图片大小
        ts_pic                  varchar(10),                           --特小图图片大小
        s_pic                   varchar(10),                           --小图图片大小
        ms_pic                  varchar(10),                           --中小图图片大小
        m_pic                   varchar(10),                           --中型图片大小 
        ml_pic                  varchar(10),                           --中大型图片大小
        l_pic                   varchar(10),                           --大型图片大小
        tl_pic                  varchar(10),                           --特大型图片大小
        primary key (id)
);


insert into tbl_column (siteid,parentid,orderid,cname,ename,dirname,editor,extname,isdefineattr,hasarticlemodel,createdate,lastupdated,isaudited,isproduct,ispublishmore,languagetype,publicflag) values(0,-1,0,'首页','home','/','admin','html',0,0,getdate(),getdate(),0,0,0,0,0);

create table tbl_mark (
	id                      int identity(1, 1)   not null,
	columnid                int                  not null,
	siteid               	int                  not null,
	content              	text,
	marktype                int                  not null,
	notes                   varchar(200),
	lockeditor              varchar(30),
	lockflag                smallint             not null,
	pubflag                 smallint             not null,
	innerhtmlflag        	smallint             not null,
        articlenum              int        default(0),                        --最近一次发布文章的数量，仅对文章列表标记有意义	
	formatfilenum           int,
	createdate              datetime                 not null,
	updatedate              datetime                 not null,
	publishtime             datetime                 not null,
	chinesename             varchar(40)          not null,
	relatedcolumnid         varchar(500),
	ispublished             smallint     default 0,             --标记是否发布过，0--未  1--发
        primary key (id)
);

--栏目模板和标记的相关表
CREATE TABLE TBL_TemplateMark (
	ID                   int  identity(1, 1)  NOT NULL,                   --编号
	siteid               int    not null,                   --站点id
        tid                  int    not null,                   --模板id
        mid                  int    not null,                   --该模板上的标记id
	CONSTRAINT pk_tmid PRIMARY KEY (ID),
	CONSTRAINT fk_templateMarkID FOREIGN KEY (tid) REFERENCES  tbl_template(ID) ON DELETE CASCADE
);

--标记与栏目的相关表
CREATE TABLE TBL_MarkColumn (
	ID                   int identity(1, 1)   NOT NULL,                   --编号
	siteid               int    not null,                   --站点id
        mid                  int    not null,                   --标记id
        cid                  int    not null,                   --标记引用的栏目id
	CONSTRAINT pk_mcid PRIMARY KEY (ID),
	CONSTRAINT fk_markColumnID FOREIGN KEY (mid) REFERENCES  tbl_mark(ID) ON DELETE CASCADE
);


create table tbl_viewfile (   	
    	id		        int identity(1, 1)    not null,
	siteid                  int                   not null,
    	type                    int                   not null,
    	content                 text                  not null,
    	chinesename             varchar(50)           not null,
    	editor                	varchar(30)           not null,
    	lockflag                smallint              not null,
    	notes                   varchar(300),
    	createdate              datetime              not null,
    	updatedate              datetime              not null,
        primary key (id)
);

create table tbl_siteinfo (
	siteid                  int    identity(1, 1) not null,
	sitename                varchar(50)           not null,
	imagesdir               smallint              not null,
        sitetype                int       default 0,                --样例和共享站点的类型,如电子商务网站，个人网站等等
	tcflag                  smallint              not null,
	wapflag                 smallint  default 0,
	pubflag                 smallint              not null,
	bindflag                smallint              not null,
	createdate              datetime                  not null,
	siteip                  varchar(15)           not null,	
	berefered               smallint              not null,     --
	cssjsdir                smallint              not null,
	copycolumn	        smallint  default 0,                --
	becopycolumn	        smallint  default 0,                --
	pusharticle	        smallint  default 0,                --发布文件数量
	movearticle	        smallint  default 0,                --移动文件数量
	config                  varchar(200),
	sitepic                 varchar(200),                       --站点首页图片
	sitevalid               smallint  default 0,                --0-样例网站 1-普通网站 2-共享网站
	sitelogo                varchar(200),                       --站点LOGO
	banner                  varchar(200),                       --站点主BANNER
	navigator               int       default 0,                --站点导航样式
	samsiteid               int       default 0,                --样例站点ID
	scores                  int       default 0,                --积分和货币兑换规则
	sharetemplatenum        int       default 0,                --共享样例站点的模板套号
	managesharesite         smallint  default 0,                --管理共享站点信息   0-不管理  1-管理
        COPYRIGHT               varchar(1000),	                    --站点版权信息
	encodeflag              smallint  default(1),               --文档编码格式 1-utf-8  2-gb2312  3-gbk
        titlepic                varchar(10),                        --标题图片大小
	vtitlepic               varchar(10),                        --副标题图片大小
	sourcepic               varchar(10),                        --来源图片大小
	authorpic               varchar(10),                        --作者图片大小
	contentpic              varchar(10),                        --内容图片大小
	specialpic              varchar(10),                        --文章特效图片大小
	productpic              varchar(10),                        --商品大图片大小
	productsmallpic         varchar(10),                        --商品小图片大小
        ts_pic                  varchar(10),                        --特小图图片大小
        s_pic                   varchar(10),                        --小图图片大小
        ms_pic                  varchar(10),                        --中小图图片大小
        m_pic                   varchar(10),                        --中型图片大小 
        ml_pic                  varchar(10),                        --中大型图片大小
        l_pic                   varchar(10),                        --大型图片大小
        tl_pic                  varchar(10),                        --特大型图片大小
        primary key (siteid)
);


create table tbl_siteipinfo(
    	id                      int  identity(1, 1)   not null,
    	siteid                  int                   not null,
    	siteip                  varchar(60)           not null,
    	sitename                varchar(50)           not null,
    	docpath                 varchar(100)          not null,
    	ftpuser                 varchar(50),
    	ftppasswd               varchar(50),
        ftptype                 int                   default(0),	    	
    	publishway              smallint              not null,
    	status                  smallint              not null,
	CONSTRAINT fk_siteipID FOREIGN KEY (siteid) REFERENCES  tbl_siteinfo(siteid) ON DELETE CASCADE,
        primary key (id)
);


create table tbl_sitegroup (
       id                       int identity(1, 1)    not null,
       sitegroupid              int                   not null,     --站点组ID，就是主站ID
       siteid                   int                   not null,     --组成员ID
       sitename                 varchar(100),                       --组成员名称
       createdate               datetime,                               --创建日期
       primary key(id)
);
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,931,'wangjian.coosite.com',getdate());
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,933,'petersong103.coosite.com',getdate());
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,934,'wangjianjilin1.coosite.com',getdate());
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,971,'bjchaohong.coosite.com',getdate());
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,885,'ssdemotest.coosite.com',getdate());

create table tbl_navigator (
       id                       int identity(1, 1)    not null,
       cname                    varchar(200)          not null,     --主导航中文名称
       content                  varchar(3500),                      --主导航HTML代码
       fcontent                 varchar(3500),                      --辅助导航HTML代码
       notes                    varchar(250),
       createdate               datetime,                               --创建日期
       primary key(id)
);


--根据栏目之间引用文章的对应关系，存放引用的文章
create table tbl_refers_article (
        id                      int  identity(1, 1)   not null,
	articleid               int                   not null,     --文章ID
        siteid                  int                   not null,     --文章原站点ID
	columnid       	        int 	              not null,     --目的栏目的ID
	scolumnid               int                   not null,     --源栏目ID
	columnname	        varchar(100),                       --目的栏目的中文名称
	orders		        smallint   default 0,
	createdate	        datetime,
	title		        varchar(600),                       --在目的栏目中的标题名称
	status                  smallint              not null,     --目的栏目中的状态
	pubflag                 smallint              not null,
	auditflag               smallint              not null,
	usearticletype          int,
	refers_column_status    int,
	tsiteid                 int,                                --目的站点ID
	artfrom                 smallint,                           --0-栏目定义处设置的引用，1-文章录入时设置的引用    
	primary key(id)
);

--栏目设置中选择的被引用文章的栏目
create table tbl_refers_column (
        id                int  identity(1, 1)         not null,
        ssiteid           int                         not null,
        scid              int                         not null,
        tsiteid           int                         not null,
        tcid              int                         not null,
        subs              smallint   default 0,
        createdate        datetime,
        primary key(id)
);


create table tbl_new_publish_queue (
	id         		int identity(1, 1)    not null,
	siteid     		int                   not null,
	columnid                int                   not null,
	targetid                int                   not null,       --需要发布的文章的ID、栏目的ID
	type                    smallint,		              --1文章,2栏目模板,3首页模板,4标记
	status                  smallint,
	createdate              datetime,
	publishdate             datetime,
	uniqueid                varchar(50),                                  --可以存储注册会员的ID
	title                   varchar(200),
        errcode                 int           default 0,              --作业发布出错时保存错误代码,默认0表示成功发布
        errmsg                  varchar(500),                        --出现错误时存储错误信息
        errnum                  smallint      default 0,              --存储发布错误后重新发布的次数，最多重新发布3次
        priority                smallint      default 0,              --作业的优先级 0-表示一般优先级
        primary key (id)
);
  
create table tbl_multimedia
(
        id                      int                         not null,           --主键
        siteid                  int                         not null,           --站点id 
        articleid               int                      default(0),            --文章id
        dirname                 varchar(100)               not null,            --栏目路径
        filepath                varchar(100)               not null,            --文件路径
        oldfilename             varchar(100)               not null,            --原文件名称
        newfilename             varchar(100)               not null,            --新文件名称
        encodeflag              int default(0),                                 --文件转换标志0-未转换 1-已经转换
        infotype                tinyint  default(0),                            --0文章内容的图片    1企业分类信息图片    2站点分类信息图片
        createdate              datetime default getdate(),                     --创建时间
        primary key(id)
);  
    
create table TBL_RELATEDARTIDS                                          --页面相关文件表
(
        ID                      int  identity(1, 1)      not null,
        cname                   varchar(100)             not null,
        pageid                  int                      not null,
        pagetype                smallint    default 0,                  --0文章页  1栏目页  2首页  3专题页
        contenttype             smallint    default 1,                  --1附件文件  2图片文件 3全景图片文件 4多媒体文件
        filename                varchar(200),
        summary                 varchar(250),
        editor                  varchar(50),                            --上传人
        createdate              datetime,                                   --上传日期
        primary key (id)        
);

create table TBL_Article_Keyword
(
        ID                      int identity(1, 1)       not null,
        siteid		        int                      not null,
        columnid	        int                      not null,
        keyword                 varchar(20),
        keywordnum              int                      default 0,      --关键词个数
        flag                    smallint                 default 0,      --1被选择出现在关键词列表中        
        url                     varchar(200),
        primary key (id)
);

CREATE TABLE tbl_type (
	id                      int identity(1, 1)       not null,												
	columnid                int                      not null,																
	parentid                int default 0,											
	referid                 int default 0,											
	cname                   varchar(100),
	createdate              datetime,
	ename                   varchar(100) ,
	flag	                smallint  default 0      not null,
        primary key(id)
);

CREATE TABLE tbl_type_article (
	id                      int identity(1, 1)       not null,										
	columnid                int                      not null,																
	articleid               int                      not null,															
	valueid                 int                      not null,																
	createdate              datetime,				
        primary key(id)
); 

create table TBL_PICTURE (
        ID                      int  identity(1, 1)        not null,
        SITEID                  int                        not null,
        COLUMNID                int                        not null,
        WIDTH                   int                        default 0,
        HEIGHT                  int                        default 0,
        PICSIZE                 int                        default 0,
        PICNAME                 varchar(200),
        IMGURL                  varchar(255)               not null,
        infotype                tinyint                    default 0,
	latitude                decimal(38,15),                              
	longitude               decimal(38,15),                              
        CREATEDATE              datetime,
        NOTES			varchar(500),
        primary key (id)        
);

create table tbl_turnpic (                                                   --图片轮换表
        id                      int  identity(1, 1)         not null,
        articleid               int                         not null,
	sortid                  int,                                             --图片显示顺序
	mediaurl                varchar(500),                                    --多媒体文件路径
	latitude                decimal(38,15),                                   --图片的经度
	longitude               decimal(38,15),                                   --图片的纬度
        picname                 varchar(100),
        createdate              datetime  default getdate() not null,
        notes                   varchar(2000),
	lib1                    varchar(100),                                    --标签1
        lib2                    varchar(100),                                    --标签2
        lib3                    varchar(100),                                    --标签3
        lib4                    varchar(100),                                    
        lib5                    varchar(100),                                    
        primary key(id)
);



--LOG分析系统
create table tbl_pv_detail(
        id                 bigint  identity(1, 1)        not null,        --主键ID
	siteid             int                           not null,        --站点ID
	urlname            varchar(500),                                  --URL地址
	url_cn_name        varchar(500),                                  --中文名称
	pageview           int,                                           --页面浏览数
	logdate            datetime,                                      --log时间
	primary key(id)
);


--工作日管理表
create table tbl_workdayinfo
(
    days                    varchar(50) not null,
    siteid                  int   default 0 not null,
    worddayflag             int   default 0 not null, --0工作日1非工作日
    primary key(days)
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
create index articleindex_parent_article on tbl_article(RELATEDARTID);    --父文章ID的索引
create index articleindex_forsiteid on tbl_article(siteid);
create index articleindex_editor on tbl_article(editor);
create index columnindex_forsiteid on tbl_column(siteid);
create index templateindex_forsiteid on tbl_template(siteid);
create index templateindex_forcolumnid on tbl_template(columnid);
create index templateindex_forreferid on tbl_template(ReferModelID);
create index order_fororderid on tbl_orders(userid);


--加盟商注册表
create table joincompany
(
  ID                int          identity(1, 1)         not null,
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
  CREATEDATE        datetime,
  BUYFLAG           varchar(100),
  primary key(ID)
);

create table tbl_companyclass (
	id                   	int    identity(1, 1)   not null,
	siteid               	int                     not null,
	parentid             	int     		not null,
	orderid              	int                     not null,
	cname                	varchar(50)        	not null,
	ename                	varchar(50)        	not null,
	dirname              	varchar(200)            not null,
	editor               	varchar(30)             not null,
	extname              	varchar(10)             not null,
	createdate           	datetime                not null,
	lastupdated          	datetime		not null,
	primary key(id)
);
insert into tbl_companyclass values(0,-1,0,'公司分类','company','/','admin','html',getdate(),getdate());

create table tbl_companyinfo(
       id                        int  identity(1, 1)         not null,     --主键
       siteid                    int,                                      --所属站点
       companyclassid            int,                                      --企业所属分类
       classification            varchar(200),                             --公司所属分类名称
       companyname               varchar(100),                             --公司名称
       companyaddress            varchar(100),                             --公司所在地址
       companyphone              varchar(50),                              --公司联系电话
       companyfax                varchar(50),                              --公司联系传真
       companywebsite            varchar(100),                             --公司站点名称
       companyemail              varchar(100),                             --公司email地址
       postcode                  varchar(20),                              --公司邮政编码
       summary                   varchar(1000),                            --公司描述 
       COMPANYLATITUDE           decimal(38,7),                            --公司所在地的经度
       COMPANYLONGITUDE          decimal(38,7),                            --公司所在地的纬度
       companypic                varchar(200),                             --公司图片
       publishflag               smallint,                                 --发布标记
       createdate                datetime,                                 --创建日期
       lastupdated               datetime,                                 --修改日期
       primary key(id)
);

--常用网站分类
create table tbl_websiteclass (
	id                   	int     identity(1, 1)  not null,
	siteid               	int                     not null,
	parentid             	int      		not null,
	orderid              	int                     not null,
	cname                	varchar(50)        	not null,
	ename                	varchar(50)        	not null,
	dirname              	varchar(200)            not null,
	editor               	varchar(30)             not null,
	extname              	varchar(10)             not null,
	createdate           	datetime                not null,
	lastupdated          	datetime		not null,
	primary key(id)
);
insert into tbl_websiteclass values(0,-1,0,'网址分类','website','/','admin','html',getdate(),getdate());

--网站分类信息
create table tbl_websiteinfo(
       id                                        int identity(1, 1),            --主键
       siteid                                    int,                           --所属站点
       websitename                               varchar(100),                  --公司名称
       websiteurl                                varchar(200),                  --公司所在地址
       summary                                   varchar(200),                  --公司联系电话
       editor                                    varchar(50),                   --编辑
       pubflag                                   smallint,                      --发布标记
       createdate                                datetime,                      --创建日期
       updatedate                                datetime,                      --修改日期
       primary key(id)
);

--程序库管理
create table tbl_program_library
(
        id            int identity(1, 1) not null,         --主键，自增长
        p_type        int,                                 --程序类型
        position      int,                                 --JSP页面中的位置
        l_type        int,                                 --语言类型
        explain       varchar(100),                        --程序说明
        notes         varchar(500),                        --程序注释
        program       text,                                --程序体
        primary key(id)
);

create table tbl_program_position
(
        id            int identity(1, 1) not null,        --编码
        position      varchar(50),                        --位置说明
        primary       key(id)
);

insert into tbl_program_position (position) values('程序头');
insert into tbl_program_position (position) values('显示体');
insert into tbl_program_position (position) values('页尾');

create table tbl_program_type
(
        id            int identity(1, 1) not null,      --编码
        p_type        varchar(50),                      --程序类型说明
        primary       key(id)
);

insert into tbl_program_type (p_type) values('信息检索');
insert into tbl_program_type (p_type) values('购物车');
insert into tbl_program_type (p_type) values('订单生成');
insert into tbl_program_type (p_type) values('订单查询');
insert into tbl_program_type (p_type) values('用户注册');
insert into tbl_program_type (p_type) values('用户评论');
insert into tbl_program_type (p_type) values('用户信息反馈');
--结束程序库管理

--网站前台应用表
--用户注册表
create table tbl_userinfo
(
        ID                   int identity(1, 1)     not null,   --主键
  	SITEID               int,                               --站点ID
  	MEMBERID             varchar(50)            not null,   --用户ID(*)
  	USERNAME             varchar(30),                       --用户真是姓名(*)
  	PASSWORD             varchar(20),                       --用户口令(*)
  	LINKMAN              varchar(30),                       --联系人(*)
  	nation               varchar(30),                       --民族
  	degree               varchar(30),                       --文化程度
  	idno                 varchar(19),                       --身份证号码
  	street               varchar(100),                      --所在街道
  	CITY                 varchar(20),                       --所在城市
  	ADDRESS              varchar(200),                      --具体地址
  	COUNTRY              varchar(50),                       --国家
  	POSTALCODE           varchar(10),                       --邮政编码
  	PHONE                varchar(20),                       --电话(*)
  	FAX                  varchar(20),                       --传真
  	EMAIL                varchar(100),                      --电子邮件(*)
  	HOMEPAGE             varchar(100),                      --个人主页
  	REMARK               varchar(500),                      --个人留言
  	USERTYPE             int,                               --用户类型(*)        0--外部普通注册用户   1--内部用户
  	VALID                int,                               --是否有效
  	SEX                  varchar(10),                       --用户性别
  	OICQ                 varchar(50),                       --用户QQ号码
  	BIRTHDAY             datetime,                          --出生日期
  	IMAGE                varchar(50),                       --用户头像
  	SIGN                 varchar(50),                       --用户签名
  	scores               int  default 0,                    --用户积分
  	LOGINNUM             int,                               --登录次数
  	GRADE                varchar(50),                       --用户级别 (*)
  	THEONLINE            varchar(10),                       --
  	RENZHENG             varchar(50),                       --
  	MOBILEPHONE          varchar(50),                       --移动电话(*)
  	PROVINCE             int,                               --省份
  	createdate           datetime,                          --创建时间
  	company              varchar(500),                      --工作单位
  	unitpcode            varchar(10),                       --单位邮政编码
  	unitphone            varchar(20),                       --单位电话
  	stationtype          varchar(8),                        --站台类别
  	entitytype           varchar(1),                        --集体/个人
  	stationaddr          varchar(80),                       --站台地址
  	opedegree            varchar(20),                       --操作证等级
  	opecode              varchar(20),                       --操作证书编码
  	callsign             varchar(20),                       --呼号
  	memo                 varchar(512),                      --备注    
  	lockflag             int default 0 not null,            --是否被锁定
  	primary key(id)
);

create table tbl_userinfo_extendattr (
	id                   	int             	not null,
	userid            	int             	not null,
	ename                	varchar(50)		not null,
	type                 	tinyint		not null,
	stringvalue          	varchar(250),
	numericvalue         	int,
	floatvalue           	decimal(9,2),
	CONSTRAINT fk_userID FOREIGN KEY (id) REFERENCES  tbl_userinfo(ID) ON DELETE CASCADE,
        primary key (id)
);

--授权用户能够查看的栏目
create table column_authorized(
        id                  int             	not null,
        siteid              int                 not null, 
        columnid            int                 not null,
        targetid            int                 not null,  --用户ID或者是角色ID
        type                tinyint             default 0, --0表示targetid为角色id, 1表示targetid为用户id
        createdate          datetime,
        primary key(id)
);

--文章评论
CREATE TABLE TBL_COMMENT ( 
	id             int   identity(1, 1)     not null,                --主键
	siteid         int                      not null,                --站点id
	userid         int                      not null,                --用户id
	Username       varchar(30)              not null,                --用户登录名称
	pid            int                      not null,                --商品id
	commenttitle   varchar(200)             not null,                --标题
	Scores         int                      default(0),              --评分
	experience     varchar(500),                                     --使用心得
	Advantages     varchar(500),                                     --优点
	Shortcomings   varchar(500),                                     --不足
	usefulnum      int                      default(0),              --认为该评论有用的人数
	nousenum       int                      default(0),              --认为该评论无用的人数
	auditflag      int       default 0,                              --0 审核通过，可显示 1-审核未通过 不显示 		
	createdate     datetime                 default getdate(),       --评论时间
	CONSTRAINT pk_commentid PRIMARY KEY (ID)	
);

--文章评论回复
create table Tbl_commentanswer
(
        id              int   identity(1, 1)     not null,               --主键
        cid             int                      not null,               --商品评论id外键
        username        varchar(100)             not null,               --回复人登录名
        answercontent   varchar(500)             not null,               --回复内容
        createdate      datetime                 default getdate(),      --回复时间
	CONSTRAINT fk_commentID FOREIGN KEY (cid) REFERENCES  tbl_comment(ID) ON DELETE CASCADE,
        primary key(id)
);

--调查
CREATE TABLE su_survey  (																--调查表
	 id           int  identity(1, 1) not null,   	   --主键
	 surveyname   varchar(200),			   --调查名称
	 notes        varchar(2000),			   --调查描述
	 createtime   datetime,		                   --创建时间
	 siteid       int default 0 not null,		   --站点id	
	 useflag      int default 0 not null,		   --有效标志 0-无效1-有效
         primary key (id) 
);

CREATE TABLE  su_answer  (				   --答案表
	 id           int  identity(1, 1) not null,	   --主键
	 userid       int,				   --用户id
	 sid   	      int,				   --调查id
	 qid   	      int,				   --问题id	
	 answers      varchar(200),			   --答案
	 other        varchar(200),			   --输入框的值
         primary      key(id)
);

CREATE TABLE  su_danswer  (			           --问题答案表
	 id           int  identity(1, 1) not null,	   --主键
	 qid          int  default 0 not null,	           --问题id
	 qanswer      varchar(200),		           --问题答案
	 picurl       varchar(100),                        --图片地址
         primary      key(id) 
);

CREATE TABLE  su_dquestion  (				   --问题定义表
	 id           int  identity(1, 1) not null,	   --主键
	 sid          int  not null,                       --调查id
	 qname        varchar(500),                        --问题名称
	 qtype        int,                                 --问题类型
	 qmust        int,                                 --是否必选
	 nother       int,                                 --是否需要输入框	
	 atype        int,                                 --答案类型
         primary      key(id) 
);

create table SU_DEFINEUSERINFO
(
        ID         int  identity(1, 1) not null,
        SID        int  default 0,                         --调查ID
        USERNAME   varchar(100),                            --提交调查用户名
        PHONE      varchar(100),                            --提交调查用户电话
        CREATEDATE datetime,
        primary key(id)
);

--我的地址薄
CREATE TABLE  tbl_userinfos (
	id            int  identity(1, 1) not null,         --主键
	username      varchar(20),                          --用户名称
	address       varchar(200),                         --地址
	email         varchar(100),                         --电子邮件
	sex           int,                                  --性别
	mobilephone   varchar(20),                          --移动电话
	postcode      varchar(10),                          --邮编
	phone         varchar(20),                          --电话
	corporation   varchar(500),                         --公司
	fax           varchar(20),                          --传真
	memberid      varchar(50),                          --用户id
	createtime    int                  not null,        --创建时间
	CONSTRAINT pk_userinfos_id PRIMARY KEY (ID)
);

--每日流水号管理表
create table tbl_lshbydate(
       cdate   datetime,
       lsh     varchar(15)
);

--用户留言
CREATE TABLE TBL_LEAVEWORD
(
	id             int  identity(1, 1)  not null,              --主键
	columnid       int                  default(0),            --咨询问题分类定义
  	siteid         int                  not null,              --站点id
  	code           varchar(12),                                --用户咨询流水号   AyyyymmddXXX    首字母A表示局长信箱  B表示在线咨询 C表示献言献策
  	sex            tinyint,                                    --留言人性别
  	userid         varchar(50),                                --登录用户ID,如果没有登录，记录IP
	flag           int                  default(0),            --是否在网站上显示，1--显示 0--不显示  	
  	title          varchar(100),                               --留言标题
  	content        varchar(4000),                              --留言内容
  	writedate      datetime,                                   --留言日期
  	company        varchar(200),                               --公司
  	linkman        varchar(100),                               --联系人
  	links          varchar(500),                               --联系方式
  	zip            varchar(20),                                --区号
  	email          varchar(100),                               --电子邮件
  	phone          varchar(20),                                --电话
  	formid         int,                                        --用户留言表单ID
  	departmentid   int,                                        --信息分发到的部门ID
	retcontent     varchar(2000),                              --回复的内容
  	auditor        varchar(50),                                --如果需要审核，保存审核人信息
  	auditflag      int                 default(0),             --0表示审核完毕  1表示审核进行中
  	mphone         varchar(20),                                --移动电话
  	processor      varchar(50),                                --处理人用户ID
	valid          tinyint             default(0),             --问题是否有效 0--有效 1--无效
	validreason    varchar(512),                               --问题无效的原因
	datefromdept   datetime,                                   --部门回复日期
	finalflag      tinyint             default(0),             --最终回复用户标志位，当回复获得批准后，由留言板部门管理员或留言板管理员将之设置为1
	endtouser      datetime,                                   --最终回复用户日期
	CONSTRAINT pk_leaveword_id PRIMARY KEY (ID)
);
create index leavemsgindex_forsiteid on tbl_leaveword(siteid);

create table tbl_leaveword_to_dept
(
	id             int  identity(1, 1) not null,               --主键
  	siteid         int                 not null,               --站点id
  	departmentid   int,                                        --信息分发到的部门ID
  	formid         int,                                        --用户留言表单ID
  	lwid           int,                                        --用户留言ID
  	processor      varchar(50),                                --回答人用户ID
	flag           int                 default 0,              --是否已经回答，1--回答 0--未回答  	
  	content        varchar(4000),                              --回答内容
  	retdate        datetime,                                   --回答日期
  	createdate     datetime            default getdate(),      --分配给该用户的日期
	CONSTRAINT pk_leaveword_to_dept_id PRIMARY KEY (ID)
);

--用户留言分类分类
create table tbl_leavewordclass (
	id             int  identity(1, 1)  not null,
	siteid         int                  not null,
	parentid       int      	    not null,
	orderid        int                  not null,
	cname          varchar(50)          not null,
	ename          varchar(50)          not null,
	dirname        varchar(200)         not null,
	editor         varchar(30)          not null,
	extname        varchar(10)          not null,
        summary        varchar(250),
        lwnum          int                  default(0),            --某分类的留言数量
	createdate     datetime             not null,
	lastupdated    datetime		    not null,
	primary key(id)
);
insert into tbl_leavewordclass values(0,-1,0,'留言分类','lwsite','/','admin','html','',0,getdate(),getdate());

create table tbl_leaveword_valid_reason
(
	id             int  identity(1, 1)  not null,                    --主键
  	siteid         int                  not null,                    --站点id
        content        varchar(500)         not null,
        primary key(id)
);

insert into tbl_leaveword_valid_reason(siteid,content) values(40,'缺少联系信息');
insert into tbl_leaveword_valid_reason(siteid,content) values(40,'询问问题不合理');


CREATE TABLE tbl_jobinfo(
	id                        int    identity(1, 1) not null,
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
	createdate                datetime                 not null,
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


--电子商务系统
--全球国家（）
create table EN_COUNTRY
(
  id                int               not null,
  orderid           int,
  countryname       varchar(20)       not null,
  valid             int               default 0,                   --1--使用   0--停用
  code              varchar(6)
)

--省市级单位（22个省，5个自治区，4个直辖市，2个特别行政区）
create table EN_PROVINCE
(
  id                int               not null,
  orderid           int,
  countryid         int               not null  default(86),
  provname          varchar(20)       not null,
  valid             int               default 0,                   --1--使用   0--停用
  code              varchar(6)
)

--地级行政单位（17个地区、30个自治州、283个地级市、3个盟）
create table en_city
(
  id                int               not null,
  orderid           int,
  provid            int               not null,
  cityname          varchar(30)       not null,
  valid             int               default 0,                   --1--使用   0--停用
  code              varchar(6)
);

--区县级行政单位（1464个县、117个自治县、374个县级市、852个市辖区、49个旗、3个自治旗、1个林区、2个特区）
create table en_zone
(
  id               int                 not null,
  orderid          int, 
  cityid           int                 not null,
  zonename         varchar(30)         not null,
  valid            int                 default 0,                   --1--使用   0--停用
  code             varchar(6)
);

--乡镇级行政单位（14677个乡、1092个民族乡、19522个镇、6152个街道办事处、11个区公所、181个苏木、1个民族苏木）
create table en_town
(
  id               int                 not null,
  orderid          int,
  zoneid           int                 not null,
  townname         varchar(30)         not null,
  valid            int                 default 0,                   --1--使用   0--停用
  code             varchar(6)
);

--村、社区级行政单位（80717个社区居民委员会、623669个村民委员会）
create table en_village
(
  id               int                 not null,
  orderid          int,
  townid           int                 not null,
  villagename      varchar(30)         not null,
  valid            int                 default 0,                   --1--使用   0--停用
  code             varchar(6)
);


create table en_receivemoney (
        id               int    identity(1, 1)          not null,
        brief            varchar(200)                   not null,
        userid           varchar(20),
        payer            varchar(20),
        jing_ban_ren     varchar(20),
        payway           int,
        nint             int,
        unit             varchar(100),
        createdate       datetime,
        orderid          bigint                         not null,
        nnumber          float,
);

create table en_gonghuo (
        id               int  identity(1, 1)             not null,
        kind             varchar(100)                    not null,
        name             varchar(50)                     not null,
        lianxiren        varchar(50),
        address          varchar(200),
        postcode         varchar(50),
        phone            varchar(50),
        email            varchar(50),
        notes            varchar(250),
        siteid           int,
);

create table en_message (
        id               int   identity(1, 1)           not null,
        siteid           int                            not null,
        send_user        varchar(50),
        receive_user     varchar(50),
        message          varchar(250),
        senddate         datetime,
        send_del         tinyint,
        receive_del      tinyint,
        flag             tinyint,
);

create table en_savemessage (
        id               int   identity(1, 1)            not null,
        siteid           int                             not null,
        message          varchar(250),
        sender           varchar(50),
        receiver         varchar(50),
        send_user        int,
        receive_user     int,
        senddate         datetime,
        createdate       datetime,
        userid           int,
);

create table en_applicationmanage (
        id               int   identity(1, 1)            not null,
        siteid           int                             not null,
        userid           int,
        orderid          bigint,
        productid        int,
        repairflag       tinyint,
        exchangeflag     tinyint,
        backflag         tinyint,
        notes            varchar(250),
        dealdate         datetime,
);

create table tbl_present (
        id               int   identity(1, 1)            not null,
        aid              int                             not null,
        pname            varchar(250),
        fullpresent      varchar(250),
        buypresent       varchar(250),
        columnids        varchar(250),
        payway           varchar(150),
        createdate       datetime
)

--保存购物车里面的信息
create table en_shoppingcar (
        orderid          bigint   identity(1, 1)            not null,
        userid           int                                not null,
        name             varchar(150),
        sex              int,
        address          varchar(250),
        postcode         char(6),
        phone            varchar(50),
        totalfee         decimal(9,2),
        deliveryfee      decimal(9,2),
        payfee           decimal(9,2),
        valid            int,
        siteid           int,
        status           int,
        billingway       int,
        city             int,
        receive          int,
        nouse            int,
        province         int,
        zone             int,
        sendway          int,
        payway           int,
        commissioncharge decimal(9,2),
        insurance        decimal(9,2),
        need_invoice     int,
        bill_to_address  varchar(250),
        notes            varchar(250),
        linktime         varchar(250),
        usescores        int,
        email            varchar(250),
        fromflag         int,
        ip               varchar(20),
        fenqi            varchar(250),                     --分期付款
        order_desc       varchar(250),
        createdate       datetime
);

create table en_shoppingcar_detail (
        id               bigint    identity(1, 1)            not null,
        orderid          bigint, 
	productid        int,
	ordernum         int,
	saleprice        decimal(9,2), 
	productname      varchar(250),
	createdate       datetime,
	type             varchar(50), 
	cardid           int,
	contents         varchar(250), 
	lptype           int
);

CREATE TABLE tbl_orders(                                                    --定单表
	 ORDERID                  bigint    identity(1, 1)     not null,    --定单号
	 initorderid              int    default 0,                         --原始定单号(拆单=原单号，非拆单=0)
	 USERID        	          int,	     			            --用户id
	 NAME          		  varchar(50),		                    --收货人姓名
	 SEX           		  int,				            --性别
	 ADDRESS       		  varchar(1000),	                    --地址
	 POSTCODE      		  varchar(20),		                    --邮编
	 PHONE         		  varchar(50),		                    --电话
	 TOTALFEE      		  decimal(9,2),		                    --定单总额
	 DELIVERYFEE   		  decimal(9,2),		                    --邮寄费用
	 PAYFEE        		  decimal(9,2),		                    --应付费用
	 CREATEDATE    		  datetime,				    --创建时间
	 VALID         		  int      default 0,		
	 SITEID        		  int,				            --站点id
	 STATUS        		  int                        not null,	    --定单状态
	 BILLINGWAY    		  int,				            --支付方式
	 CITY          		  varchar(50),			            --城市
	 RECEIVE       		  int,				
	 NOUSE         		  int      default 1,	                    --定单是否有效 1-有效 0-无效
	 PROVINCE      		  varchar(50),			            --省份
	 zone          		  varchar(50),			            --地区
	 sendway       		  int,				            --配送方式
	 payway        		  int,		                            --付款方式
	 commissioncharge         decimal(9,2),		                    --折扣
	 insurance   		  decimal(9,2),		                    --保险费
	 need_invoice             int        default 0,	                    --是否需要发票
	 bill_to_address          varchar(2000),	                    --发票地址
	 notes                    varchar(4000),	                    --发票信息
	 flag                     smallint   default 0,
	 payflag                  smallint   default 0,                     --是否已经支付
	 linktime                 varchar(50),			
	 usescores                int        default 0,	                    --用户使用积分
	 orderscores              int        default 0,                     --定单产生的积分 
	 primary key(orderid) 
);

CREATE TABLE TBL_ORDERS_DETAIL  ( -- 定单详细信息表
	 ID                       bigint  identity(1, 1)     not null,       --主键
	 ORDERID                  bigint                    not null,       --定单号
	 supplierid               int      default 0,                       --供货商id(对应每个供货商siteid)
	 suppliername             varchar(100),                             --供货商名称
	 PRODUCTID                int,				            --商品id
	 ORDERNUM                 int,				            --商品数量
	 SALEPRICE                decimal(9,2),		                    --商品售价
	 WEIGHT                   int,				            --商品重量
	 CREATEDATE               datetime,				    --创建时间
	 productname              varchar(200),	                            --商品名称
	 type                     varchar(100),	                            --商品类型（扩展属性）
	 cardid                   int  default 0, 	                    --使用购物卷id 
	 CONSTRAINT fk_orderID FOREIGN KEY (ORDERID) REFERENCES  TBL_ORDERS(ORDERID) ON DELETE CASCADE,	 	 
	 primary key(id)
);

--送货方式管理
create table tbl_fee(
	id                        int identity(1, 1) not null,              --主键
	siteid                    int default 0,                            --站点id
	cname                     varchar(200),				    --送货方式名称
	notes                     varchar(2000),                            --送货方式说明
	fee			  decimal(9,2) default 0,                   --运费
	createdate                datetime default getdate(),               --创建日期
	primary key(id)
);

create table en_defaultfee (
       id                         int,                                      --配送方式ID
       fee                        decimal(9,2) default 0                    --配送方式对应的运费
);

--运费管理
create table en_feeforproduct --商品运费
(
    id                      int  identity(1, 1)  not null,
    siteid                  int                  default 0,
    pid                     int                  default 0,
    fee                     decimal(9,2)         default 0,
    begintime               varchar(50),
    endtime                 varchar(50),
    updatetime              datetime             default getdate(),
    updateid                varchar(200),
    createdate              datetime             default getdate(),
    primary key(id) 
);

create table en_cityfee --城市运费
(
    id                      int  identity(1, 1)  not null,
    siteid                  int                  default 0,
    cityid                  int                  default 0,
    fee                     decimal(9,2)         default 0,
    begintime               varchar(50),
    endtime                 varchar(50),
    updatetime              datetime             default getdate(),
    updateid                varchar(200),
    createdate              datetime             default getdate(),
    primary key(id) 
);

create table en_paywayfee --支付方式运费
(
    id                      int  identity(1, 1)  not null,
    siteid                  int                  default 0,
    paywayid                int                  default 0,
    paywayname              varchar(100),
    paywaytype              varchar(100),    
    fee                     decimal(9,2)         default 0,
    totaltype               int                  default 0,
    totalfee                decimal(9,2)         default 0,
    begintime               varchar(50),
    endtime                 varchar(50),
    updatetime              datetime             default getdate(),
    updateid                varchar(200),
    createdate              datetime             default getdate(),
    primary key(id) 
);

create table en_scores_new (
    id                      int  identity(1, 1)  not null,
    userID                  int, 
    orderID                 bigint,
    scores                  int, 
    orderScores             int,
    orderScoresLeft         int,
    createTime              datetime,
    endTime                 datetime,
    flag                    int
)

--支付方式管理
create table tbl_sendway(
        id                        int identity(1, 1) not null,              --主键
        siteid                    int                not null,              --站点id
        cname                     varchar(200),				    --支付方式名称
	notes                     varchar(2000),                            --支付方式说明
	createdate                datetime default getdate(),               --创建日期
	primary key(id)
);

--送货地址
create table tbl_addressinfo(
        id                        int identity(1, 1)  not null,       --主键
        userid			  int                 not null,       --用户id
        name                      varchar(200),                       --收货人姓名
        provinces                 varchar(50),			      --省份
        city                      varchar(50),			      --城市
        zone                      varchar(50),                        --地区
        mobile                    varchar(50),                        --移动电话
        phone                     varchar(50),                        --固定电话
        zip                       varchar(50),                        --邮编
        address                   varchar(200),                       --详细地址
	createdate                datetime default getdate(),         --创建时间
	primary key(id)
);

--购物券
CREATE TABLE tbl_shoppingcard(
	id                        int identity(1, 1)  not null,       --主键
	siteid                    int                 not null,
	cardnum                   varchar(50),                        --购物券标识号 
	denomination              int,                                --购物券金额
	begintime                 varchar(50),                        --开始时间
	endtime                   varchar(50),                        --结束时间
	code                      varchar(50),                        --激活码
	createtime                datetime,                           --创建时间
	activation                int,                                --是否被使用0-未被使用，1-被使用
	ischeck                   int                 not null,       --是否发放0-未发放，1-已发放
	primary key(id) 
);

--买赠方案管理
create table tbl_mai_zeng_fangan(
        id               int identity(1, 1)           not null,
        siteid           int                          not null,
        zengway          int                          not null,       --赠品方案编号
        price            decimal(9,2),                                --按价格赠，一个订单的价格达到规定的数值享受赠品（一种方案）
        pcount           int,                                         --按数量赠，某个商品购买到规定数量，享受赠品    （第二种方案）
        productid        int,                                         --按数量赠需要购买的商品
        productbrand     varchar(50),                                 --购买某个品牌的商品享受赠品                    （第三种方案） 
        useflag          smallint,                                    --该方案是否使用     0--不使用   1--使用
        majorflag        smallint,                                    --主要方案，在两个方案冲突情况，使用该方案，1--主方案，所有使用方案中只能有一个主方案
        createdate       datetime,                                    --方案创建日期
        editor           varchar(50),                                 --方案创建人
        primary key(id)
);


--赠品管理
create table tbl_zengpin(
        productid        int                          not null,       --产品ID
        productname      varchar(250)                 not null,       --产品名称
        fanganid         int                          not null,       --适用方案ID
        createdate       datetime,                                    --创建日期
        editor           varchar(50)                                  --创建者
);

--捆绑销售
create table tbl_groupproductsale(
        groupid          int identity(1, 1)           not null,       --产品组合ID
        productid        int                          not null,       --产品ID
        productname      varchar(250)                 not null,       --产品名称
        summary          varchar(500),                                --组合产品描述
        productcount     smallint                     not null,       --商品数量
        YhPrice          decimal(9,2),                                --优惠价格
        groupbigpic      varchar(250),                                --组合大图
        groupsmallpic    varchar(250),                                --组合小图
        grouptexuao      varchar(250),                                --组合特效图
        groupcontentpic  varchar(250),                                --组合内容图
        begintime        datetime,                                    --开始日期
        endtime          datetime,                                    --结束日期
        createdate       datetime,                                    --创建日期
        editor           varchar(50)                                  --创建者
);

--支付接口定义表
create table tbl_paywayinterface(
       id                int                           not null,
       paywayid          int                           not null,
       accountnumber     varchar(200),
       paywaykey         varchar(200),
       alipayaccount     varchar(100),
       createdate        datetime                      not null,
       primary key(id) 
); 

--发票信息表  新增加
create table tbl_invoiceinfo
(
       id                 int           identity(1, 1),
       siteid             int           default 0,           --站点id
       userid             int           default 0,           --用户id
       orderid            int           default 0,           --用户订单号
       invoicetype        int           default 0,           --发票类型(0-普通发票1-增值税发票)
       title              int           default 0,           --发票抬头（个人/单位0-个人1-单位）
       companyname        varchar(200),                        --单位名称
       content            int           default 0,           --发票内容(外键内容id)
       identification     varchar(200),                        --纳税人识别号
       registeraddress    varchar(200),                        --注册地址
       phone              varchar(50),                         --注册电话
       bankname           varchar(200),                        --开户银行
       bankaccount        varchar(100),                         --银行帐号
       createdate         datetime       default getdate(),           --创建时间
       primary key(id)
);

--发票内容维护表
create table tbl_invoicecontent
(
       id                 int              identity(1, 1), 
       siteid             int              default 0,          --站点id
       content            varchar(200)     not null,           --内容
       createdate         datetime         default getdate(),    --创建时间
       primary key(id)
);

--送货费用管理

--问吧表结构定义
create table fawu_anwser                                --答案表      
(
        id                      int   identity(1, 1)    not null,            --答案主键
        qid                     int                     not null,            --问题主键，本表的外键
        anwser                  varchar(4000),                               --答案
        votenum                 int,                                         --投票数量
        ipaddress               varchar(15),                                 --回答者的IP地址
        userid                  int,                                         --回答者用户ID
        username                varchar(300),                                --回答者用户名
        fenshu                  int                     default 0,           --获取分数
        cankaoziliao            varchar(4000),                               --参考资料
        createdate              datetime,                                    --创建时间
        PICPATH                 varchar(255),                                --图片路径
        BETTERANS               int,                                         --是否是最佳答案
        ANW_STATUS              int,                                         --答案状态
        primary key(id)
);

create table fawu_wenti_column                          --问吧分类定义
(
        id                      int   identity(1, 1)    not null,            --问吧分类主键
        siteid                  int                     not null,            --站点ID
        parentid                int                     not null,            --父分类ID
        orderid                 int                     not null,            --序号ID
        cname                   varchar(100),                                --中文名称
        ename                   varchar(100),                                --英文名称
        dirname                 varchar(200),                               
        status                  int                     default 0,
        creater                 varchar(20),
        createdate              datetime,
        primary key(id)
);

create table fawu_vote                                 --问吧答案投票表
(
        id                      int  identity(1, 1)    not null,             --主键ID   
        aid                     int                    not null,             --问题答案ID
        userid                  int                    not null,             --投票用户ID
        primary key(id)
);

create table fawu_wenti                                 --问题表
(
        id                      int identity(1, 1)      not null,           --主键ID
        columnid                int                     not null,           --分类ID
        parcolumid              int,                                        --上一级分类ID
        cname                   varchar(100),                               --问题中文名称
        question                varchar(1000),                              --问题内容
        status                  int,                                        --问题状态
        voteflag                int,                                        --是否是带投票的问题
        xuanshang               int                     default 0,          --悬赏分数
        answernum               int                     default 0,          --回答数目
        source                  varchar(50),                                --
        createdate              datetime,                                   --创建日期
        ipaddress               varchar(15),                                --客户端IP地址
        creater                 varchar(50),                                --创建人
        province                varchar(50),                                --创建人省份
        city                    varchar(50),                                --创建人所在城市
        area                    varchar(50),                                --创建人所在区域
        picpath                 varchar(255),                               --图片路径
        emailnotify             int                     default 0,          --是否邮件通知  1--通知  0--不通知,
        title                   varchar(60),                                --
        email                   varchar(40),                                --用户电子邮件
        userid                  int,                                        --用户ID
        username                varchar(20),                                --用户名称
        filepath                varchar(255),                               --上传文件路径
        istop                   int                     default 0,          --是否置顶
        anw_status              int,                                        --答案状态
        dianjinum               int,                                        --点击数
        user_id_huida           varchar(50),                                --回答人ID
        primary key(id)
);


--电子商务进、销、存系统
--银行编号表
create table tbl_banks (
        id                      int   identity(1, 1)    not null,      --主键
        bankid                  varchar(12)             not null,      --银行ID
        siteid                  int                     not null,      --站点ID
        bankattribname          varchar(12)             not null,      --银行简称
        bankname                varchar(40),                           --银行名称
        createdate              datetime,                              --创建时间
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--业务员数据表
create table tbl_salesman (
        id                      int identity(1, 1)      not null,      --主键
        salesmanid              varchar(12)             not null,      --业务员ID
        siteid                  int                     not null,      --站点ID
        chinesename             varchar(12)             not null,      --业务员名称
        englishname             varchar(12),                           --业务员英文名称
        contactphone            varchar(20),                           --业务员联系电话
        mobilephone             varchar(12),                           --业务员移动电话
        contactaddress          varchar(120),                          --业务员联系地址
        email                   varchar(50),                           --业务员电子邮件地址
        createdate              datetime,                              --创建时间
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--客户表
create table tbl_customer (
        id                      int  identity(1, 1)     not null,      --主键
        customerid              varchar(12)             not null,      --客户ID
        siteid                  int                     not null,      --站点ID
        customerattribname      varchar(50)             not null,      --客户简称
        customername            varchar(100),                          --客户名称
        invoiceno               varchar(20),                           --发票编号
        owner                   varchar(12),                           --负责人
        rocid                   varchar(18),                           --身份证号码
        contactman1             varchar(18),                           --联系人1
        contactman2             varchar(18),                           --联系人2
        contactphone1           varchar(20),                           --联系人1电话
        contactphone2           varchar(20),                           --联系人2电话
        fax                     varchar(20),                           --传真号码
        salesmanid              int,                                   --业务负责人
        customeraddress         varchar(120),                          --公司地址
        deliveryaddress         varchar(120),                          --送货地址
        invoiceaddress          varchar(120),                          --发票地址
        paydays                 int,                                   --付款日数
        creditline              decimal(32,2),                         --信用额度
        creditbalance           decimal(32,2),                         --信用余额
        lastdeliverydate        datetime,                              --最后交易日
        createdate              datetime,                              --创建时间
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--供货商表
create table tbl_supplier (
        id                      int identity(1, 1)      not null,      --主键
        supplierid              varchar(12)             not null,      --供货商ID
        siteid                  int                     not null,      --站点ID
        supplierattribname      varchar(50)             not null,      --供货商简称
        suppliername            varchar(100),                          --供货商名称
        invoiceno               varchar(20),                           --发票编号
        owner                   varchar(12),                           --负责人
        rocid                   varchar(18),                           --身份证号码
        phone1                  varchar(18),                           --联系电话1
        phone2                  varchar(18),                           --联系电话2
        fax                     varchar(20),                           --传真号码
        contactname1            varchar(20),                           --联系人1
        contactname2            varchar(20),                           --联系人2
        companyaddress          varchar(120),                          --公司地址
        deliveryaddress         varchar(120),                          --工厂地址
        invoiceaddress          varchar(120),                          --发票地址
        lastpurchasedate        datetime,                              --最后交易日
        paydays                 int      default 0,                    --付款日数
        prepaid                 int      default 0,                    --暂付款
        createdate              datetime,                              --创建时间
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--产品表
create table tbl_jxc_product (
        id                      int  identity(1, 1)     not null,      --主键
        productid               varchar(12)             not null,      --产品ID
        siteid                  int                     not null,      --站点ID
        productname             varchar(120)            not null,      --产品名称
        safestock               int,                                   --安全存量
        lastpurchasedate        datetime,                              --最近进货日期
        lastdeliverydate        datetime,                              --最近出货日期
        quantity                int         default 0,                 --库存量
        createdate              datetime,                              --创建时间
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--进货单主表
create table tbl_purchasemaster (
        id                      int  identity(1, 1)   not null,        --主键
        purchaseid              varchar(12)           not null,        --进货单号
        siteid                  int                   not null,        --站点ID
        purchasedate            datetime              not null,        --进货日期
        supplierid              varchar(12)           not null,        --供货商编号
        purchaseproperty        smallint              default 0,       --进货单属性 0-进货  1-退货
        invoiceno               varchar(10),                           --发票编号
        subtotal                decimal(36,2)         default 0,       --合计金额
        valueaddtax             decimal(36,2)          default 0,       --营业税
        amount                  decimal(36,2)          default 0,       --总计金额
        accountpayable          decimal(36,2)          default 0,       --应付金额
        paid                    decimal(36,2)          default 0,       --已付帐款
        limitdate               datetime,                              --付款截止日期
        createdate              datetime,                              --创建时间
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--进货单明细表
create table tbl_purchasedetail (
        id                      int  identity(1, 1)    not null,       --主键
        purchaseid              varchar(12)            not null,       --进货单号
        productid               varchar(12)            not null,       --产品ID
        purchasequantity        int                  default 0,        --进货数量
        purchaseunitprice       decimal(18,2),                         --进货单价
        purchaseamount          decimal(36,2)        default 0,        --进货总价
        createdate              datetime,                              --创建时间
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--出货单主表
create table tbl_deliverymaster (
        id                      int  identity(1, 1)     not null,      --主键
        deliveryid              varchar(12)             not null,      --出货单号
        siteid                  int                     not null,      --站点ID
        deliverydate            datetime                not null,      --出货日期
        customerid              varchar(12)             not null,      --客户编号
        salesmanid              varchar(12)             not null,      --业务员编号
        deliveryaddress         varchar(120),                          --送货地址
        invoiceno               varchar(10),                           --发票编号
        customerorderno         varchar(15),                           --订单号码
        subtotal                decimal(36,2)           default 0,     --合计金额
        valueaddtax             decimal(36,2)           default 0,     --营业税
        amount                  decimal(36,2)           default 0,     --总计金额
        accountreceivable       decimal(36,2)           default 0,     --应收金额
        received                decimal(36,2)           default 0,     --已收金额
        limitdate               datetime,                              --付款截止日期
        createdate              datetime,                              --创建时间
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--出货单明细表
create table tbl_deliverydetail (
        id                      int identity(1, 1)      not null,      --主键
        deliveryid              varchar(12)             not null,      --出货单号
        productid               varchar(12)             not null,      --产品ID
        salesquantity           int                     default 0,     --出货数量
        salesunitprice          decimal(18,2),                         --出货单价
        salesamount             decimal(36,2)           default 0,     --出货总价
        createdate              datetime,                              --创建时间
        updatedate              datetime,                              --修改时间
        primary key (id)
);


--库存变动主表
create table tbl_changemaster (
        id                      int identity(1, 1)      not null,      --主键
        changeid                varchar(12)             not null,      --出货单号
        siteid                  int                     not null,      --站点ID
        changedate              datetime,                              --变动日期
        description             varchar(255),                          --变动说明
        createdate              datetime,                              --创建时间        
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--库存变动明细
create table tbl_changedetail (
        id                      int  identity(1, 1)     not null,      --主键
        changeid                varchar(12)             not null,      --出货单号
        productid               varchar(12)             not null,      --产品ID
        changequantity          int                     default 0,     --变动数量
        changeamount            decimal(36,2)           default 0,     --变动金额
        createdate              datetime,                              --创建时间        
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--付款冲帐主表
create table tbl_accountpayablemaster (
        id                      int  identity(1, 1)    not null,      --主键
        paymentid               varchar(12)            not null,      --出货单号
        siteid                  int                    not null,      --站点ID
        paydate                 datetime               not null,      --支付日期
        supplierid              varchar(12)            not null,      --供应商ID
        paycash                 decimal(36,2)          default 0,     --现金
        paycheck                decimal(36,2)          default 0,     --支票
        noteno                  varchar(10),                          --支票号码
        duedate                 datetime,                             --到期日
        bankid                  varchar(12)            not null,      --开票银行
        accountno               varchar(20),                          --帐号
        discount                decimal(36,2)          default 0,     --折扣
        remittance              decimal(36,2)          default 0,     --费用
        prepaid                 decimal(36,2)          default 0,     --暂付款
        others                  decimal(36,2)          default 0,     --其他
        payamount               decimal(36,2)          default 0,     --付款金额
        totalbalance            decimal(36,2)          default 0,     --实冲金额
        createdate              datetime,                             --创建时间        
        updatedate              datetime,                             --修改时间
        primary key (id)
);

--付款冲帐明细表
create table tbl_accountpayabledetail (
        id                      int  identity(1, 1)     not null,      --主键
        paymentid               varchar(12)             not null,      --付款单号
        purchaseid              varchar(12)             not null,      --进货单单号
        balance                 decimal(36,2)           default 0,     --本次冲销金额
        createdate              datetime,                              --创建时间        
        updatedate              datetime,                              --修改时间
        primary key (id)
);

--收款冲帐主表
create table tbl_accountreceivablemaster (
        id                      int  identity(1, 1)     not null,     --主键
        siteid                  int                     not null,     --站点ID
        receiveid               varchar(12)             not null,     --收款单号
        receivedate             datetime,                             --收款日期
        customerid              varchar(12)             not null,     --客户编号
        receivecash             decimal(36,2)           default 0,    --现金
        receivecheck            decimal(36,2)           default 0,    --支票
        noteno                  varchar(10),                          --支票号码
        duedate                 datetime,                             --到期日
        bankid                  varchar(12)             not null,     --开票银行
        accountno               varchar(20),                          --帐号
        discount                decimal(36,2)           default 0,    --折扣
        remittance              decimal(36,2)           default 0,    --费用
        advance                 decimal(36,2)           default 0,    --暂收款
        others                  decimal(36,2)           default 0,    --其他
        receiveamount           decimal(36,2)           default 0,    --收款金额
        totalbalance            decimal(36,2)           default 0,    --实冲金额
        createdate              datetime,                             --创建时间        
        updatedate              datetime,                             --修改时间
        primary key (id)
);


--收款冲帐明细表
create table tbl_accountreceivabledetail (
        id                      int  identity(1, 1)     not null,      --主键
        receiveid               varchar(12)             not null,      --收款单号
        deliveryid              varchar(12)             not null,      --出货单单号
        balance                 decimal(36,2)           default 0,     --本次冲销金额
        createdate              datetime,                              --创建时间        
        updatedate              datetime,                              --修改时间
        primary key (id)
);


--信息采集表
create table sp_basic_attributes (
	id                     int   identity(1, 1)    not null,
	sitename               varchar(50),
	starturl               varchar(255),
	urlnumber              int,
	status                 varchar(5),
	posturl                varchar(255),
	postdata               varchar(255),
	loginflag              tinyint                 not null,
	proxyflag              tinyint                 not null,
	stopflag               tinyint                 not null,
	keywordflag            tinyint,
	catchdepth             int,
        primary key(id) 
);

create table sp_basic_columns (
	id                    int    identity(1, 1)    not null,
	basicid               int                      not null,
	classid               int                      not null,                    --CMS中栏目ID
	createdate            datetime                 not null,
        primary key(id) 
);

create table sp_document_type (
	id                    int                      not null,
	starturlid            int,
	doctype               varchar(5) 
);

create table sp_global (
	id                    int    identity(1, 1)    not null,
	starttime             datetime,
	interval              int,
	proxyflag             tinyint,
	proxyname             varchar(50),
	proxyport             varchar(20),
	proxyloginuser        varchar(50),
	proxyloginpass        varchar(50),
	proxyloginflag        tinyint                  not null,
	systemrun             tinyint                  not null,
	tkeyword              varchar(250),
	bkeyword              varchar(250),
	tbrelation            smallint,
	keywordflag           smallint,
        primary key(id) 
);

create table sp_keyword_rules (
	id                    int identity(1, 1)       not null,
	starturlid            int,
	Tkeyword              varchar(250),
	bkeyword              varchar(250),
	tbrelation            smallint 
);

create table sp_match_url (
	id                    int identity(1, 1)       not null,
	starturlid            int,
	matchurl              varchar(255) 
);

create table sp_proxy (
	id                    int identity(1, 1)       not null,
	starturlid            int,
	proxyurl              varchar(500),
	proxyport             varchar(20),
	proxyloginflag        tinyint,
	proxyloginuser        varchar(50),
	proxyloginpass        varchar(50),
        primary key(id)
);

create table sp_special_code (
	id                    int identity(1, 1)       not null,
	starturlid            int,
	ST                    varchar(255),
	ET                    varchar(255),
        primary key(id)
);


create table tbl_workload (
	Id                    int  identity(1, 1)      not null,
	starturlid            int,
	url                   varchar(500),
	status                varchar(5),
	lastmodified          datetime,
	urltitle              varchar(200),
	urltype               tinyint,
	depth                 int,
	thedate               datetime,
        primary key(id) 
);



--北京无线电管理局企业用户注册表单
create table tbl_rsbt_org(
	id			int        identity(1, 1)       not null,
	guid			varchar(36)			not null,
	userid			varchar(36)			not null,
	siteid			int				not null,
	password		varchar(36)			not null,
	org_gode		varchar(9) default(999999999)	not null,		--组织机构代码
	org_name		varchar(80)			not null,		--组织机构名称
	org_area_code		varchar(8)			not null,		--地区代码
	org_sys_code		varchar(8)			not null,		--系统/行业代码
	org_type		varchar(3)			not null,		--单位类型
	org_link_person		varchar(40),						--单位联系人
	org_person_id		varchar(40),						--联系人身份证号码
	org_sup_code		varchar(9),						--上级组织机构
	org_addr		varchar(80),						--组织机构地址
	org_post		varchar(6),						--组织机构邮编
	org_phone		varchar(40),						--联系电话
	org_mob_phone		varchar(40),						--手机号码
	org_fax			varchar(40),						--组织机构传真
	org_bank		varchar(80),						--开户银行
	org_account_name	varchar(80),						--账户名称
	org_account		varchar(40),						--银行账号
	org_hostility		int,						--设台单位性质
	org_web_site		varchar(80),						--网址
	org_mail		varchar(80),						--电子邮箱
	createdate		datetime,
	primary key(id)
);

create table tbl_rsbt_org1(
	id			int				not null,
	value			int				not null,
	name			varchar(50)			not null,
	primary key(id)
);

create table tbl_rsbt_org2(
	id			int				not null,
	value			int				not null,
	name			varchar(50)			not null,
	primary key(id)
);

create table tbl_rsbt_org3(
	id			int				not null,
	value			int				not null,
	name			varchar(50)			not null,
	primary key(id)
);

--业务审批信息数据库文件信息表
create table gw_case_info (
     sn                        varchar(100) not null,
     operater                  varchar(100) not null,
     telephone                 varchar(100) not null,
     applicant                 varchar(200) not null,
     applicantID               varchar(100),
     registerNO                varchar(200),
     applicanttel              varchar(100) not null,
     harge                     decimal(9,2) default 0,
     dt_operate                varchar(100) not null,
     startingDate              varchar(100),
     licenceName               varchar(100),
     licenceCode               varchar(100),
     caseID                    varchar(100) not null,
     caseName                  varchar(100) not null,
     result                    varchar(100) not null,
     createdate                datetime,                                      --创建日期
     lastupdate                datetime,                                      --修改日期
     editor                    varchar(50),                              --内容创建人
     publishflag               int          not null 
);

create table tbl_accessmdb
(
    userid        varchar(50)                  not null,           --上传人ID
    filename      varchar(100)                 not null,           --上传文件原始名称
    tflag         tinyint default(0),                              --是否转入到数据库      0-没有转入       1-已经转入，全部成功 2-导入，有问题
    createdate    datetime,
    lastupdate    datetime,
    primary key(filename)
);


--无线电干扰申诉表
create table tbl_ganrao(
	id			int          identity(1, 1)	not null,		--主键
	susongdanwei		varchar(50)			not null,		--诉讼单位
	lianxiren1		varchar(20),						--联系人1
	lianxiren2		varchar(20),						--联系人2
	phone			varchar(20),						--联系电话
	taizhanweizhi		varchar(200),						--台站位置
	shebeixinghao		varchar(100),						--设备型号
	gongzuofangshi		varchar(50),						--工作方式
	ganraoleixing		varchar(50),						--干扰类型
	zhizhaobianhao		varchar(50),						--执照编号
	ganraopinlv		varchar(50),						--干扰频率
	ganraoquyu		varchar(50),						--干扰区域
	ganraoshiduan		varchar(50),						--初次干扰时间及发生时段
	ganraoshijian		varchar(50),						--出现频度及每次持续时间
	tezhengmiaoshu		varchar(500),						--干扰信号特征描述
	shenshuyaoqiu		varchar(500),						--申述要求
	primary key(id)
);

--设置无线电台申报表
create table tbl_zhuduijiangji(
	djjid			int identity(1, 1)		not null,		--主键
	shetaidanwei		varchar(50),						--设台单位
	suoshuxitong		varchar(50),						--所属系统
	address			varchar(100),						--地址
	lianxiren		varchar(20),						--联系人
	phone			varchar(20),						--电话
	chuanzhen		varchar(20),						--传真
	shiyongdiqu		varchar(50),						--使用地区
	diantaishuliang		varchar(20),						--电台数量
	diantaileibie		varchar(50),						--电台类别	150MHz,400MHz,900MHz无中心地址
	shetailiyou		varchar(500),						--设台理由
	lingdaoyijian		varchar(500),						--上级主管单位领导意见
	guanlijuyijian		varchar(500),						--北京市无线电管理局意见
	xitongbianhao		varchar(100),						--系统编号
	zhizhaobianhao		varchar(100),						--执照编号
	primary key(djjid)
);

create table tbl_diantaiziliao(
	id			int identity(1, 1)		not null,		--主键
	dtzlid			int				not null,
	shebeixinghao		varchar(100),						--设备型号
	chuchanghaoma		varchar(100),						--出厂号码
	beizhu			varchar(100),						--备注
	CONSTRAINT fk_dtzlID FOREIGN KEY (dtzlid) REFERENCES  tbl_zhuduijiangji(djjid) ON DELETE CASCADE,
	primary key(id)
);

--业务分类表
create table tbl_BusinessInfo
(
    codeinfo                   varchar(100),
    cname                      varchar(100),
    siteid                     int            default 0           not null,
    primary key(codeinfo)
);

--预约代码表
create table tbl_Appointment
(
    id                         int  identity(1, 1)                not null,
    codeinfo                   varchar(100)                       not null,
    codename                   varchar(100)                       not null,
    appointmentdate            varchar(30)                        not null,
    appointmentcode            varchar(6)                         not null,
    siteid                     int            default 0           not null,
    primary key(id) 
);

--用户预约信息表
create table tbl_userappointment
(
    id                        int  identity(1, 1)                 not null,
    userid                    varchar(100)                        not null,
    appointmentdate           varchar(30)                         not null,
    appointmentcode           varchar(6)                          not null,
    siteid                    int             default 0           not null,
    primary key(id)
);

insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1001','进口无线电发射设备核准',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1002','临时进口无线电发射设备-开机',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1003','临时进口无线电发射设备-不开机',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1004','申请使用频率',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1005','申请延期使用频率',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1006','申请微波使用波道',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1007','申请电台呼号',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1008','申请增加电台呼号',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1009','共用频率对讲机',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1010','900M无中心电台',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1011','业余无线电台',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1012','150M、400M超短波网',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1013','数据传输网',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1014','无线接入网',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1015','研制实效发射',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1016','生产实效发射',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1017','实效发射延期',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1018','未经型号核准设备实效发射',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1019','设置微波通信站',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1020','设置卫星通讯网',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1021','设置卫星地球站',40);



--以下为工作流表单
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



create table bbs_thread(
	threadid	int		not null,
	lyhid		int,
	forumid		int,
	answerid	int             default 0,
	threadname	varchar(500),
	threadcontent	varchar(500),
	posttime	datetime,
	author		varchar(500),
	ipaddress	varchar(500),
	answeruser	varchar(500),
	answertime	datetime,
	hitnum		int,
	answernum	int,
	gotop		int          default 0,
	typeflag	int,
	hiddenflag	int          default 0,
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
	postdate	datetime,
	lastposter	varchar(500),
	lasttopic	varchar(500),
	lastposttime	datetime,
	pic		varchar(500),
	creationdate	datetime,
	threadnum	int,
	topicnum	int,
	siteid		int,
	primary key(id)
);


create table bbs_online(
	id		varchar(500)		not null,         --主键ID
	lyhid		int,                                   --登录用户ID
	username	varchar(500),                             --登录用户名
	logintime	datetime,                                     --最后登录时间
	loginnum        int,                                   --登录次数
	loginstatus     int,                                   --登录状态
	lastactivetime  datetime,                                     --用户最后活动时间
	ipaddress	varchar(500)                              --登录者的IP地址
);

create table bbs_friend(
	userid		varchar(500),
	lyhid		int,
	friend		varchar(500)
);


--发票信息表  新增加
create table en_invoice
(
        id               int   identity(1, 1)     not null,             --主键
        siteid           int                      not null,             --站点id
        userid           int                      not null,             --用户id
        orderid          bigint                   not null,             --用户订单号
        invoicetype      int                      default(0),           --发票类型(0-普通发票1-增值税发票)
        titleflag        int                      default(0),           --（个人/单位0-个人1-单位）
        title            varchar(100),                                  --发票抬头
        companyname      varchar(200),                                  --单位名称
        identification   varchar(200),                                  --纳税人识别号
        registeraddress  varchar(200),                                  --注册地址
        phone            varchar(15),                                   --注册电话
        postaddress      varchar(200),                                  --邮寄地址
        postname         varchar(20),                                   --接收人姓名
        postcode         varchar(6),                                    --邮政编码
        bankname         varchar(200),                                  --开户银行
        bankaccount      varchar(100),                                  --银行帐号
        totalprice       money,                                         --发票总价值
        createdate       datetime                 default getdate(),    --创建时间
        primary key(id)
);

--发票内容维护表
create table en_invoicecontent
(
        id              int   identity(1, 1)     not null,             --主键,每个发票项的ID
        invoiceid       int                      not null,             --发票ID
        siteid          int                      not null,             --站点id
        [content]       varchar(200)             not null,             --内容
        price           money,                                         --商品单价
        pnum            int                      default(0),           --商品数量
        createdate      datetime                 default getdate(),    --创建时间
	CONSTRAINT      fk_contentid FOREIGN KEY (invoiceid) REFERENCES  en_invoice(id) ON DELETE CASCADE,
        primary key(id)
);
