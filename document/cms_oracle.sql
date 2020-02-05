create table tbl_sites_number (
	ipaddress		varchar2(15)		not null,
	sitesnum		number,
	hashcode                number,
        primary key (ipaddress)
);

insert into tbl_sites_number(ipaddress,sitesnum,hashcode) values('127.0.0.1',18,0);

create table tbl_right (
	rightid			number(2)		not null,
	rightname		varchar2(30)		not null,
	rightcat		varchar2(30),
	rightdesc		varchar2(100),
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
	roleid			number(2)		not null,
	rolename		varchar2(30)		not null,
	rolecat		varchar2(30),
	roledesc		varchar2(100),
        primary key (roleid)
);

insert into tbl_role values(0,'信息员','XXY','负责向网站录入信息');
insert into tbl_role values(1,'部门领导','BML','录入信息的审核');
insert into tbl_role values(2,'主管领导','ZGL','录入信息的审核');
insert into tbl_role values(3,'系统管理员','SM','超级系统管理员');
insert into tbl_role values(4,'留言版管理员','LWM','留言版管理员');
insert into tbl_role values(5,'部门留言版管理员','DLWM','部门留言版管理员');

create table tbl_log (
	id			number			not null,
	articleid		number			not null,
	siteid			number     		not null,
	editor			varchar2(30)		not null,
	acttype			number(1)		not null,
	acttime			date			not null,
	columnid		number					not null,
	maintitle		varchar2(600)	not null,
	createdate	date,
        primary key (id)
);

create table tbl_members (
        userid       		varchar2(30)   		not null,
        siteid       		number      		not null,
        userpwd      		varchar2(32)   		not null,
        nickname     		varchar2(30)		not null,
        email                   varchar2(50),
        mphone                  varchar2(12),
        phone                   varchar2(12),                          --联系电话        
	sex                     smallint              default(0),      --0表示男 1表示女
        country                 varchar2(30),                          --国家
        province                varchar2(50),                          --省
        city                    varchar2(50),                          --市
        area                    varchar2(50),                          --区县
        jiedao                  varchar2(50),                          --街道
        shequ                   varchar2(50),                          --社区
        postcode                varchar2(6),                           --邮政编码
        birthdate               date,                                  --出生日期
        myimage                 varchar2(100),                         --头像
        usertype                smallint              default(0),      --用户类型    0---个人用户   1--企业用户        
        joincompanyid           number,                                --加盟商ID号
        joinid                  number,                                --加盟商序列号
        dflag                   number,                                --站点是否被删除
        createarticles	        number  default(0),                    --用户创建文章数量
        editarticles		number  default(0),                    --用户修改文章数量
        deletearticles	        number  default(0),                    --用户删除文章数量
        score                   number     default(0),
        createdate              date,
        company                 varchar2(200),
        address                 varchar2(500),
        department              varchar2(50),                          --所属部门
        emailaccount            varchar2(100),                         --email帐号
        emailpasswd             varchar2(100),                         --email密码
        departmentarticlestype  number default(0),                     --审核文章的范围0-所有部门1-本部门2-指定部门
        departmentarticlesids   varchar2(500),                         --指定审核文章的部门id        
        primary key (userid)
);

insert into tbl_members (userid,siteid,userpwd,nickname,email,mphone,phone,sex) values('admin',-1,'4346D362F67C89D7AB4E28732A1B1CE8','系统管理员','','','',0);

--系统角色表
create table tbl_member_roles (
       id                       number                     not null,
       siteid                   number                     not null,
       userid                   varchar2(30)               not null,
       deptid                   number                     not null,      --用户所属部门ID
       rolename                 varchar2(20)               not null,      --信息员   部门负责人   主管   留言版管理员  部门留言版管理员  
       rolelevel                number,                                   --角色级别 0-9
       primary key(id)
);

--授权用户管理的留言表
create table tbl_member_authorized_resouce (
       id                       number                     not null,
       siteid                   number                     not null,
       userid                   varchar2(30)               not null,
       lwid                     number,                                   --该用户管理的留言表ID
       lwname                   varchar2(100),                            --该用户管理的留言表的名称
       lwrole                   varchar2(20),                             --留言板管理角色 是某个留言板的管理员 或是留言板的部门管理员
       contenttype              int                         default(0),    --内容类型 0为留言板
       primary key(id)
);
create sequence tbl_resouce_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;

--企业部门设置
create table tbl_department (
       id                      number                      not null,
       siteid                  number                      not null,
       cname                   varchar2(100)            not null,
       ename                   varchar2(100),
       telephone               varchar2(12),
       manager                 varchar2(50),                            --部门经理
       vicemanager             varchar2(50),                            --部门副经理
       leader                  varchar2(50),                            --部门主管领导
       primary key(id)
);          
       
--每个用户在每个站点的每个栏目中录入的文章数量
create table tbl_user_articlebycolumn (
       id                       number                     not null,
       columnid                 number                     not null,
       userid                   varchar2(30)            not null,
       siteid                   number                     not null,   
       articlecount             number    default(0),
       primary key(id)
);
       
create table tbl_members_rights (
        userid          	varchar2(30)    	not null,
        columnid        	number			not null,
        rightid         	number(2)		not null
);

create table tbl_group_rights (   	
	groupid			number          	not null,
        columnid        	number,
        rightid        	 	number(2)
);

create table tbl_group_members (
	userid      		varchar2(30)    	not null,
	groupid     		number          	not null
);

create table tbl_group (
	groupid     		number          	not null,
	siteid      		number             	not null,
	groupname   		varchar2(20)    	not null,
	groupdesc   		varchar2(100),
        primary key (groupid)
);

create table tbl_article_auditing_info (
	id              	number          	not null,
	articleid       	number          	not null,
	messagetype             number  default(0),                       --0表示栏目文章信息   1表示用户留言信息
	sign            	varchar2(30)    	not null,
	comments        	varchar2(500),
	status          	number(1)       	not null,
	backto          	varchar2(30),
	createdate      	date                    not null,
        primary key (id)
);

create table tbl_column_auditing_rules (
	id                   	number             	not null,
	columnid             	number             	not null,
	messagetype             number  default(0),                       --0表示栏目文章信息  1表示用户留言信息
	column_audit_rule    	varchar2(300)		not null,
	audittype               number default(0),                        --定义审核方式 0-按用户名称定义审核规则   1-按角色定义审核规则
	createdate           	date			not null,
	lastupdated          	date			not null,
	creator              	varchar2(30)		not null,
	editor               	varchar2(30)		not null,
        primary key (id)
);

create table tbl_article (
	id                   	number             	not null,           --主键
	columnid             	number             	not null,           --文章所属栏目ID
	siteid                  number                  not null,           --文章所属站点ID     站点ID=1
	sortid               	number,                                     --文章排列序号，自然序列号
	maintitle            	varchar2(600)   	not null,           --文章标题
	vicetitle            	varchar2(600),                              --文章副标题
	summary              	varchar2(1000),                             --文章描述
	keyword              	varchar2(200),                              --文章关键字
	source               	varchar2(600),                              --文章来源
	content              	long,                                       --文章内容
	emptycontentflag     	number(1)		not null,           --文章内容是否为空  对于上传文件设置为1 其他设置为0
	author               	varchar2(600),                              --文章作者
	articleversion       	number,                                     --文章版本号
	publishtime          	date			not null,           --文章发布时间
	createdate           	date			not null,           --文章创建时间
	lastupdated          	date			not null,           --文章最后修改时间
	dirname              	varchar2(200)		not null,           --文章路径
	filename             	varchar2(60),                               --文件名称
	editor               	varchar2(30)        	not null,           --编辑名称
	status               	number(1)   		not null,           --文章状态      0-可用   1-未用
	doclevel             	number                 	not null,           --文章主权重
	vicedoclevel		number,                                     --文章副权重
	pubflag              	number(1)   		not null,           --文章发布标志位    0-新稿         1-发布稿
	auditflag            	number(1) 		not null,           --文章审核标志位    1-待审         0-审核完毕
	subscriber           	number(1)   		not null,           --文章订阅          0-不允许订阅   1-允许订阅
	lockstatus           	number(1) 		not null,           --文章锁定标志位    0-未锁定       1-锁定
	lockeditor           	varchar2(30),                               --文章锁定人
	auditor              	varchar2(200),                              --文章作者
	ispublished          	number(1) 		not null,           --文章是否曾经被发布过
	relatedartid         	varchar2(40),                               --未用
	indexflag            	number(1)		not null,           --文章是否被加入到全文索引数据库
	isjoinrss		number(1)		not null,           --文章是否进入RSS
	clicknum		number			not null,           --文章被点击次数
	referID			number			not null,           --未用
        multimediatype          number(1)               default(0),         --多媒体文章标志0-不是多媒体文章 1-是多媒体文章	
	modelID			number			not null,           --文章使用的发布模板
	articlepic		varchar2(600),                              --文章使用的特效图片
	changepic               number                  default(0),         --文章图片特效类型
	saleprice            	number(9,2),                                --电子商务使用  商品售价
	inprice              	number(9,2),                                --电子商务使用  商品进价
	marketprice          	number(9,2),                                --电子商务使用  商品市场价
	vipprice          	number(9,2),	                            --电子商务使用  商品VIP价
	stocknum             	number,                                     --电子商务使用  商品库存量
	weight               	number,                                     --电子商务使用  商品重量
	brand                	varchar2(50),                               --电子商务使用  商品品牌
	pic                  	varchar2(30),                               --电子商务使用  商品小图片
	bigpic               	varchar2(30),                               --电子商务使用  商品大图片
	score                   number,                                     --电子商务使用  商品积分
	voucher                 number(9,2),                                --电子商务使用  购物券
	urltype		        number(1)               default(0),         --未用
	defineurl		varchar2(200),                              --文章自定义URL
	notearticleid           int                     default(0),         --定义与该篇文章相关联的其他文章的内容，其他文章的内容可以是本页面一个标签的内容
	wordsnum		number                  default(0),         --文章文字数量
	downfilename	        varchar2(100),                              --未用
	beidate                 date,                                       --未用    
        salesnum                number,                                     --商品销售数量
        commentnum              int                     default(0),         --文章评论的数量
	fromsiteid              int                     default(0),         --标识抓取信息的来源站点
	sarticleid              varchar2(50),                               --抓取文章来源网站的ID 
	t1                      number                  default(0),         --未用
	t2                      number                  default(0),         --未用
	t3                      number                  default(0),         --未用
	t4                      number                  default(0),         --未用
	t5                      number                  default(0),         --未用
        primary key (id)
);

create table tbl_article_extendattr (
	id                   	number             	not null,
	articleid            	number             	not null,
	ename                	varchar2(50)		not null,
	type                 	number(1)		not null,
	textvalue            	long,
	stringvalue          	varchar2(4000),
	numericvalue         	number,
	floatvalue           	number(9,2),
	CONSTRAINT fk_articleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)
);

create table tbl_article_attachment_class (
	id                   	number                  not null,            --主键ID 栏目ID
	parentid             	number     		not null,            --父栏目ID
	siteid               	number                  not null,            --站点ID
	columnid                number                  not null,            --栏目ID
	orderid              	number                  not null,            --序号ID
	cname                	varchar2(50)        	not null,            --分类中文名称
	ename                	varchar2(50)        	not null,            --分类英文名称
	dirname              	varchar2(200)           not null,            --路径信息
	editor               	varchar2(30)            not null,            --编辑
	extname              	varchar2(10)            not null,            --扩展名
	createdate           	date                    not null,            --创建日期
	lastupdated          	date			not null,            
        primary key (id)
);

create table tbl_relatedartids                                               --页面相关文件表
(
        ID                      number                         not null,     --主键ID
	subcolumnid             number                         not null,     --附件所属分类ID
        cname                   varchar2(100)                  not null,     --附件中文名称
        pageid                  number                         not null,     --文章ID
        content                 long,                                        --附件内容
	dirname                 varchar2(255),                               --附件路径
        contenttype             number    default(1),                        --1上传文件  2图片文件 3全景图片文件 4多媒体文件 5html页面
        filename                varchar2(200),                               --附件文件名称
        summary                 varchar2(250),                               --附件文件描述
        editor                  varchar2(50),                                --上传人
        createdate              date,                                        --附件创建日期
	lastupdate              date,                                        --
	primary key (id)        
);

create table tbl_deleted_article (
        siteid                  int                not null,                 --站点ID
	columnid                int                not null,                 --栏目ID
        articleid               int                not null,                 --文章ID
	sitename                varchar2(100),                               --站点名称
	acttype                 smallint           default    0              --0表示被删除的文章   1表示被修改的文章        
);

create table tbl_multimedia
(
        id                      number                      not null,        --主键
        siteid                  number                      not null,        --站点id 
        articleid               number                      default(0),      --文章id
        dirname                 varchar2(100)               not null,        --栏目路径
        filepath                varchar2(100)               not null,        --文件路径
        oldfilename             varchar2(100)               not null,        --原文件名称
        newfilename             varchar2(100)               not null,        --新文件名称
        encodeflag              number      default(0),                      --文件转换标志0-未转换 1-已经转换
        infotype                smallint    default(0),                      --0文章内容的图片    1企业分类信息图片    2站点分类信息图片
        createdate              date default sysdate        not null,        --创建时间
        primary key(id)
);


create table tbl_template (
	id                   	number    		not null,
	siteid                  number       default(0),
	columnid             	number    		not null,
	isarticle           	number(3)   		not null,
	content              	long,
	createdate           	date			not null,
	lastupdated          	date			not null,
	editor               	varchar2(30)		not null,
	creator              	varchar2(30)		not null,
	status               	number(1)  		not null,
	relatedcolumnid      	varchar2(1000),
	modelversion         	number,
	lockstatus           	number(1)		not null,
	lockeditor           	varchar2(30),
	chname               	varchar2(50)		not null,
	defaulttemplate      	number(1)		not null,
	templatename         	varchar2(200)		not null,
	refermodelid         	number      		not null,
	isIncluded		number(1)		not null,
	tempnum                 number    default(0),
        primary key (id)
);

create table tbl_template_extend (
	id                   	number    		not null,
	siteid                  number            default(0),
	columnid             	number    		not null,
        errorurl                varchar2(150),
        successurl              varchar2(150),
        tablebackcolor          varchar2(10)
);

create table tbl_column (
	id                   	number                  not null,
	siteid               	number                     not null,
	parentid             	number     		not null,
	orderid              	number                  not null,
	cname                	varchar2(50)        	not null,
	ename                	varchar2(50)        	not null,
	dirname              	varchar2(200)           not null,
	editor               	varchar2(30)            not null,
	extname              	varchar2(10)            not null,
	createdate           	date                    not null,
	lastupdated          	date			not null,
	isdefineattr         	number(1)  		not null,
 	hasarticlemodel      	number(1)  		not null,
	isaudited            	number(1)  		not null,
	isproduct            	number(1)    		not null,
	ispublishmore        	number(1)    		not null,
	languagetype         	number(1)    		not null,
	columndesc           	varchar2(200),
	xmltemplate          	varchar2(3000),
	contentshowtype		number(1),
	isrss			number(1)	default(0) not null,
	getRssArticleTime	number(1),
	archivingrules		number(1)       default(0) not null,
	useArticleType		number(1)       default(0) not null,
	istype			number(1)	default(0) not null,
	publicflag              number(1)	default(0) not null,
	xmltype                 varchar2(3000),                        --文章分类的定义
	userflag                number(1)       default(0),            --栏目文章是否只有注册用户可看  0--所有用户 1--注册用户
	userLevel               number          default(0),            --用户浏览文章的级别
	shareflag               number        default(0),            --栏目是否是和其他网站共享的栏目
	articlecount            number             default(0),            --本栏目的文章总数量
	titlepic                varchar2(10),                          --标题图片大小
	vtitlepic               varchar2(10),                          --副标题图片大小
	sourcepic               varchar2(10),                          --来源图片大小
	authorpic               varchar2(10),                          --作者图片大小
	contentpic              varchar2(10),                          --内容图片大小
	specialpic              varchar2(10),                          --文章特效图片大小
	productpic              varchar2(10),                          --商品大图片大小
	productsmallpic         varchar2(10),                          --商品小图片大小
        ts_pic                  varchar2(10),
        s_pic                   varchar2(10),
        ms_pic                  varchar2(10),
        m_pic                   varchar2(10),
        ml_pic                  varchar2(10),
        l_pic                   varchar2(10),
        tl_pic                  varchar2(10),
        primary key (id)
);

insert into tbl_column values(0,0,-1,0,'首页','home','/','admin','html',sysdate,sysdate,0,0,0,0,0,0,'','',0,0,0,0,0,0,0,'',0,0,0,0,'','','','','','','','','','','','','','','');


create table tbl_mark (
	id                   	number             	not null,
	columnid             	number             	not null,
	siteid               	number      		not null,
	content              	long,
	marktype             	number(3)		not null,
	notes                	varchar2(200),
	lockeditor           	varchar2(30),
	lockflag             	number(1)		not null,
	pubflag              	number(1)		not null,
	innerhtmlflag        	number(1)		not null,
	formatfilenum        	number,
	createdate           	date			not null,
	updatedate           	date			not null,
	publishtime         	date			not null,
	chinesename          	varchar2(40)		not null,
	relatedcolumnid      	varchar2(500),
	ispublished             number     default(0),             --标记是否发布过，0--未  1--发
        articlenum              int        default(0),                        --最近一次发布文章的数量，仅对文章列表标记有意义
	primary key (id)
);


--栏目模板和标记的相关表
CREATE TABLE TBL_TemplateMark (
	ID                   number    NOT NULL,                   --编号
	siteid               number    not null,                   --站点id
        tid                  number    not null,                   --模板id
        mid                  number    not null,                   --该模板上的标记id
	CONSTRAINT pk_tmid PRIMARY KEY (ID),
	CONSTRAINT fk_templateMarkID FOREIGN KEY (tid) REFERENCES  tbl_template(ID) ON DELETE CASCADE
);

--标记与栏目的相关表
CREATE TABLE TBL_MarkColumn (
	ID                   number    NOT NULL,                   --编号
	siteid               number    not null,                   --站点id
        mid                  number    not null,                   --标记id
        cid                  number    not null,                   --标记引用的栏目id
	CONSTRAINT pk_mcid PRIMARY KEY (ID),
	CONSTRAINT fk_markColumnID FOREIGN KEY (mid) REFERENCES  tbl_mark(ID) ON DELETE CASCADE
);

create table tbl_viewfile (   	
    	id		      	number             	not null,
	siteid                	number     		not null,
    	type                  	number(2)		not null,
    	content               	long			not null,
    	chinesename           	varchar2(50)		not null,
    	editor                	varchar2(30)		not null,
    	lockflag              	number(1)		not null,
    	notes                 	varchar2(300),
    	createdate            	date			not null,
    	updatedate            	date			not null,
        primary key (id)
);


create table tbl_siteinfo (
	siteid               	number                                 not null,
	sitename             	varchar2(50)    	            not null,
	imagesdir            	number(1)          	            not null,
	tcflag               	number(1)		            not null,
	pubflag              	number(1)               	    not null,
	bindflag             	number(1)               	    not null,
	createdate           	date			            not null,
	berefered		number(1)	        	    not null,     --
	config			varchar2(200),
	siteip                	varchar2(15) default('127.0.0.1')   not null,	
	cssjsdir		number(1)		            not null,
	wapflag			number(1)       default(0)          not null,
	copycolumn	        number(1)       default(0) 	    not null,     --
	becopycolumn	        number(1)       default(0) 	    not null,     --
	pusharticle	        number(1)       default(0) 	    not null,     --发布文件数量
	movearticle	        number(1)       default(0) 	    not null,     --移动文件数量
        sitetype                number             default(0),                       --样例和共享站点的类型,如电子商务网站，个人网站等等
	sitepic                 varchar2(200),                                    --站点首页图片
	sitevalid               number        default(0),                       --0-样例网站 1-普通网站 2-共享网站
	sitelogo                varchar2(200),                                    --站点LOGO
	banner                  varchar2(200),                                    --站点主BANNER
	navigator               number             default(0),                       --站点导航样式
	samsiteid               number             default(0),                       --样例站点ID
	scores                  number          default(0),                       --积分和货币兑换规则
	sharetemplatenum        number             default(0),                       --共享样例站点的模板套号
	managesharesite         number        default(0),                       --管理共享站点信息   0-不管理  1-管理
        copyright               varchar2(1000),	                                  --网站版权
        titlepic                varchar2(10),                                     --标题图片大小
	vtitlepic               varchar2(10),                                     --副标题图片大小
	sourcepic               varchar2(10),                                     --来源图片大小
	authorpic               varchar2(10),                                     --作者图片大小
	contentpic              varchar2(10),                                     --内容图片大小
	specialpic              varchar2(10),                                     --文章特效图片大小
	productpic              varchar2(10),                                     --商品大图片大小
	productsmallpic         varchar2(10),                                     --商品小图片大小
        ts_pic                  varchar2(10),
        s_pic                   varchar2(10),
        ms_pic                  varchar2(10),
        m_pic                   varchar2(10),
        ml_pic                  varchar2(10),
        l_pic                   varchar2(10),
        tl_pic                  varchar2(10),
        primary key (siteid)
);


create table tbl_siteipinfo(
    	id                    	number              	not null,
    	siteid                	number              	not null,
    	siteip                	varchar2(60)     	not null,
    	sitename              	varchar2(50)		not null,
    	docpath               	varchar2(100)     	not null,
    	ftpuser               	varchar2(50),
    	ftppasswd             	varchar2(50),
        ftptype                 int                     default(0),	
    	publishway            	number(1)		not null,
    	status                	number(1)		not null,
	CONSTRAINT fk_siteipID FOREIGN KEY (siteid) REFERENCES  tbl_siteinfo(siteid) ON DELETE CASCADE,
        primary key (id)
);

create table tbl_sitegroup (
       id                       number                             not null,
       sitegroupid              number                             not null,     --站点组ID，就是主站ID
       siteid                   number                             not null,     --组成员ID
       sitename                 varchar2(100),                                --组成员名称
       createdate               date,                                         --创建日期
       primary key(id)
);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(1,36,931,'wangjian.coosite.com',sysdate);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(2,36,933,'petersong103.coosite.com',sysdate);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(3,36,934,'wangjianjilin1.coosite.com',sysdate);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(4,36,971,'bjchaohong.coosite.com',sysdate);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(5,36,885,'ssdemotest.coosite.com',sysdate);

create table tbl_navigator (
       id                       number                             not null,
       cname                    varchar2(200)                   not null,     --主导航中文名称
       content                  varchar2(3500),                               --主导航HTML代码
       fcontent                 varchar2(3500),                               --辅助导航HTML代码
       notes                    varchar2(250),
       createdate               date,                                         --创建日期
       primary key(id)
);


--根据栏目之间引用文章的对应关系，存放引用的文章
--artfrom 0-栏目定义处设置的引用，1-文章录入时设置的引用    2--推送文章
create table tbl_refers_article (
        id                      number                not null,
	articleid               number                not null,          --文章ID
        siteid                  number                not null,          --文章原站点ID
	columnid       	        number 	              not null,          --目的栏目的ID
	scolumnid               number                not null,          --源栏目ID
	columnname	        varchar2(100),                           --目的栏目的中文名称
	orders		        number	      default(0),
	createdate	        date,
	title		        varchar2(600),                           --在目的栏目中的标题名称
	status                  number   	      not null,          --目的栏目中的状态
	pubflag                 number   	      not null,          --文章是否被发布
	auditflag               number    	      not null,          --文章是否需要审核
	usearticletype          number,                                  --文章引用类型     0--地址引用   1--内容引用
	refers_column_status    number,                                  --引用栏目状态
	tsiteid                 number,                                  --目的站点ID
	artfrom                 number,                                  --应用文章方式
	primary key(id)
);

--栏目设置中选择的被引用文章的栏目
create table tbl_refers_column (
        id                number                not null,
        ssiteid           number                not null,
        scid              number                not null,
        tsiteid           number                not null,
        tcid              number                not null,
        subs              number              default(0),
        createdate	  date,
        primary key(id)
);

create table tbl_new_publish_queue (
	id         		number 			not null,
	siteid     		number      		not null,
	targetid   		number 			not null,       --需要发布的文章的ID、栏目的ID
	type       		number,	          	                --1文章,2栏目模板,3首页模板,4标记
	status     		number(1),
	createdate    		date,
	publishdate   		date,
	uniqueid   		varchar2(50),                           --可以存储注册会员的ID	
	columnid		number			not null,
	title			varchar2(200),
        errcode                 number           default(0),            --作业发布出错时保存错误代码,默认0表示成功发布
        errmsg                  varchar2(500),                          --出现错误时存储错误信息
        errnum                  number      default(0),                 --存储发布错误后重新发布的次数，最多重新发布3次
        priority                number      default(0),                 --作业的优先级 0-表示一般优先级
        primary key (id)
);

create table TBL_PICTURE 
(
        ID                      NUMBER,
        SITEID                  NUMBER,
        COLUMNID                NUMBER,
        WIDTH                   NUMBER                      not null,
        HEIGHT                  NUMBER                      not null,
        PICSIZE                 NUMBER                      not null,
        PICNAME                 VARCHAR2(200),
        IMGURL                  VARCHAR2(255)               not null,
        infotype                smallint                    default       0,
	latitude                number(38,15),                              --经度
	longitude               number(38,15),                              --纬度
        CREATEDATE              DATE                        default       sysdate   not null,
        NOTES			varchar2(500),
        primary key (id)        
);

create table tbl_turnpic (                                                   --图片轮换表
        id                  number not null,
        articleid           number not null,
	sortid              int,                                             --图片显示顺序
	mediaurl            varchar2(500),                                    --多媒体文件路径
	latitude            number(38,15),                                   --图片的经度
	longitude           number(38,15),                                   --图片的纬度
        picname             varchar2(100),
        createdate          date default sysdate not null,
        notes               varchar2(2000),
	lib1                varchar2(100),                                    --标签1
        lib2                varchar2(100),                                    --标签2
        lib3                varchar2(100),                                    --标签3
        lib4                varchar2(100),                                    
        lib5                varchar2(100),                                    
        primary key(id)
);
      
create table TBL_Article_Keyword
(
        ID                      number                      not null,
        siteid		        number                      not null,
        columnid	        number                      not null,
        keyword                 varchar2(20),
        keywordnum              int                     default 0,      --关键词个数
        flag                    smallint                default 0,      --1被选择出现在关键词列表中        
        url			varchar2(200),
        primary key (id)
);

CREATE TABLE tbl_type (
	id                      number                      NOT NULL,												
	columnid                number                      NOT NULL,																
	parentid                number default(0)           NOT NULL,											
	referid                 number default(0)           NOT NULL,											
	cname                   varchar2(100),
	createdate              date   default sysdate      NOT NULL,
	ename                   varchar2(100) ,
	flag	                number(1) default(0)        not null,
        primary key(id)
);

CREATE TABLE tbl_type_article (
	id                      number                      NOT NULL,										
	columnid                number                      NOT NULL,																
	articleid               number                      NOT NULL,															
	valueid                 number                      NOT NULL,																
	createdate              date default sysdate        NOT NULL,				
        primary key(id)
); 

create table tbl_companyclass (
	id                   	number                     not null,
	siteid               	number                     not null,
	parentid             	number      		not null,
	orderid              	number                     not null,
	cname                	varchar2(50)        	not null,
	ename                	varchar2(50)        	not null,
        summary                 varchar2(250),                                   --公司分类信息描述
	dirname              	varchar2(200)           not null,
	editor               	varchar2(30)            not null,
	extname              	varchar2(10)            not null,
        companynum              int    default(0),
	createdate           	date                    not null,
	lastupdated          	date			not null,
	primary key(id)
);

insert into tbl_companyclass values(0,0,-1,0,'公司分类','company','','/','admin','html',0,sysdate,sysdate);
--insert into tbl_companyclass values(1,40,0,0,'公司分类','www.bjrab.gov.cn','','/','admin','shtml',0,sysdate,sysdate);


create table tbl_companyinfo(
       ID                                        number,                            --主键
       SITEID                                    number,                            --所属站点
       COMPANYNAME                               VARCHAR2(100),                     --公司名称
       COMPANYADDRESS                            VARCHAR2(100),                     --公司所在地址
       COMPANYPHONE                              VARCHAR2(50),                      --公司联系电话
       COMPANYFAX                                VARCHAR2(50),                      --公司联系传真
       COMPANYWEBSITE                            VARCHAR2(100),                     --公司站点名称
       COMPANYEMAIL                              VARCHAR2(100),                     --公司email地址
       POSTCODE                                  VARCHAR2(20),                      --公司邮政编码
       companyclassid                            number,                            --企业所属分类       
       CLASSIFICATION                            VARCHAR2(200),                     --公司所属分类名称
       SUMMARY                                   VARCHAR2(1000),                    --公司描述 
       COMPANYLATITUDE                           number(38,15),                     --公司所在地的经度
       COMPANYLONGITUDE                          number(38,15),                     --公司所在地的纬度
       COMPANYPIC                                VARCHAR2(200),                     --公司图片
       PUBLISHFLAG                               number,                            --发布标记
       status                                    smallint default(0),               --状态  0--对外发布   1-不对外发布
       createdate                                date,                              --创建日期
       updatedate                                date,                              --修改日期
       primary key(id)
);

--常用网站分类
create table tbl_websiteclass (
	id                   	number                     not null,
	siteid               	number                     not null,
	parentid             	number      		not null,
	orderid              	number                     not null,
	cname                	varchar2(50)        	not null,
	ename                	varchar2(50)        	not null,
	dirname              	varchar2(200)           not null,
	editor               	varchar2(30)            not null,
	extname              	varchar2(10)            not null,
        summary                 varchar2(250),
        websitenum              int    default(0),
	createdate           	date                    not null,
	lastupdated          	date			not null,
	primary key(id)
);
insert into tbl_websiteclass values(0,0,-1,0,'网址分类','website','/','admin','html','',0,sysdate,sysdate);

--网站分类信息
create table tbl_websiteinfo(
       id                                        number,                            --主键
       siteid                                    number,                            --所属站点
       websitename                               varchar2(100),                  --公司名称
       websiteurl                                varchar2(200),                  --公司所在地址
       summary                                   varchar2(200),                  --公司联系电话
       editor                                    varchar2(50),                   --编辑
       pubflag                                   number,                       --发布标记
       createdate                                date,                           --创建日期
       updatedate                                date,                           --修改日期
       primary key(id)
);

--工作日管理表
create table tbl_workdayinfo
(
    days                       varchar2(50) not null,
    siteid                     number     default 0 not null,
    worddayflag number         default 0 not null, --0工作日1非工作日
    primary key(days)
); 

--LOG分析系统
create table tbl_pv_detail(
        id                 number                        not null,        --主键ID
	siteid             int                           not null,        --站点ID
	urlname            varchar2(500),                                 --URL地址
	url_cn_name        varchar2(500),                                 --中文名称
	pageview           int,                                           --页面浏览数
	logdate            date,                                          --log时间
	primary key(id)
);

create sequence tbl_site_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;
create sequence tbl_template_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_mark_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_column_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_article_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_article_extendattr_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_new_publish_queue_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_group_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_article_attechment_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_log_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_article_keyword_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_image_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_pic_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_type_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_type_article_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_viewfile_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_refers_article_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_refers_column_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_turnpic_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_userrole_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence TBL_MEMBER_ROLES_ID minvalue 1 maxvalue 999999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_multimedia_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;     
--模板与标记关系id
create sequence tbl_rtm_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
--标记与栏目关系id          
create sequence tbl_rmc_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence joincompany_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence companyinfo_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence department_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence navigator_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence message_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence pv_detail_id minvalue 1 maxvalue 9999999999999999999999999 start with 487039 increment by 1 cache 20;


create index articleindex on tbl_article(id,columnid,status,doclevel,emptycontentflag,pubflag,auditflag);
create index articleindex_forauditor on tbl_article(auditor);
create index articleindex_forcolumn on tbl_article(columnid);
create index articleindex_forstatus on tbl_article(status);
create index articleindex_fordoclevel on tbl_article(doclevel);
create index articleindex_foremptyflag on tbl_article(emptycontentflag);
create index articleindex_forpubflag on tbl_article(pubflag);
create index articleindex_forauditflag on tbl_article(auditflag);
create index articleindex_forindexflag on tbl_article(indexflag);
create index articleindex_parent_article on tbl_article(RELATEDARTID);
create index articleindex_forsiteid on tbl_article(siteid);
create index articleindex_editor on tbl_article(editor);
create index columnindex_forsiteid on tbl_column(siteid);
create index templateindex_forsiteid on tbl_template(siteid);
create index templateindex_forcolumnid on tbl_template(columnid);
create index templateindex_forreferid on tbl_template(ReferModelID);


--加盟商注册表
create table joincompany
(
  ID                NUMBER            not null,
  JOINID            VARCHAR2(200)     not null,
  JOINNAME          VARCHAR2(300),
  ADDRESS           VARCHAR2(600),
  EMAIL             VARCHAR2(200),
  PHONE             VARCHAR2(200),
  COMPANY           VARCHAR2(400),
  PASSWORD          VARCHAR2(300),
  ANSWER            VARCHAR2(300),
  QUESTION          VARCHAR2(300),
  ZHIZHAONUMBER     VARCHAR2(200),
  FAX               VARCHAR2(100),
  LIANXIPEOPLE      VARCHAR2(300),
  CREATEDATE        DATE default sysdate,
  BUYFLAG           VARCHAR2(100)
);

--程序库管理
create table TBL_PROGRAM_LIBRARY
(
        ID            NUMBER not null,               --主键，自增长
        P_TYPE        NUMBER,                        --程序类型
        POSITION      NUMBER,                        --JSP页面中的位置
        L_TYPE        NUMBER,                        --语言类型
        EXPLAIN       VARCHAR2(100),                 --程序说明
        NOTES         VARCHAR2(500),                 --程序注释
        PROGRAM       LONG,                          --程序体
        primary key(id)
);
CREATE SEQUENCE tbl_program_id START WITH 1;

create table TBL_PROGRAM_POSITION
(
        ID       NUMBER not null,                    --编码
        POSITION VARCHAR2(50),                        --位置说明
        primary key(id)
);

insert into tbl_program_position (id,position) values(1,'程序头');
insert into tbl_program_position (id,position) values(2,'显示体');
insert into tbl_program_position (id,position) values(3,'页尾');

create table TBL_PROGRAM_TYPE
(
        ID     NUMBER not null,                      --编码
        P_TYPE VARCHAR2(50),                         --程序类型说明
        primary key(id)
);

insert into tbl_program_type (id,p_type) values(11,'信息检索');
insert into tbl_program_type (id,p_type) values(12,'购物车');
insert into tbl_program_type (id,p_type) values(13,'订单生成');
insert into tbl_program_type (id,p_type) values(14,'订单回显');
insert into tbl_program_type (id,p_type) values(15,'订单查询');
insert into tbl_program_type (id,p_type) values(16,'信息反馈');
insert into tbl_program_type (id,p_type) values(17,'用户评论');
insert into tbl_program_type (id,p_type) values(18,'用户注册');
insert into tbl_program_type (id,p_type) values(19,'用户登录');
insert into tbl_program_type (id,p_type) values(20,'订单明细查询');
insert into tbl_program_type (id,p_type) values(21,'用户留言');
insert into tbl_program_type (id,p_type) values(22,'修改注册');
insert into tbl_program_type (id,p_type) values(24,'地图标注');


--结束程序库管理

--网站前台应用表
--用户注册表
create table tbl_userinfo
(
        id                   number               not null,         --主键
  	siteid               number               not null,         --站点ID
  	memberid             varchar2(50)         not null,         --用户ID
  	username             varchar2(30),                          --用户真是姓名
  	password             varchar2(20),                          --用户口令
        role                 varchar2(20),                          --用户角色
  	grade                varchar2(50),                          --用户级别
  	company              varchar2(500),                         --工作单位
	department           varchar2(200),                         --所在单位部门                 
  	unitpcode            varchar2(10),                          --单位邮政编码
  	unitphone            varchar2(20),                          --单位电话
  	linkman              varchar2(30),                          --联系人
  	nation               varchar2(30),                          --民族
  	degree               varchar2(30),                          --文化程度
  	idno                 varchar2(19),                          --身份证号码
  	country              varchar2(50),                          --国家
  	province             number,                                --省份
  	city                 varchar2(20),                          --所在城市
  	street               varchar2(100),                         --所在街道
  	address              varchar2(200),                         --联系地址
  	postalcode           varchar2(10),                          --联系地址邮政编码
  	phone                varchar2(20),                          --住宅电话
  	mobilephone          varchar2(50),                          --移动电话
  	fax                  varchar2(20),                          --传真
  	email                varchar2(100),                         --电子邮件
  	homepage             varchar2(100),                         --个人主页
  	usertype             number,                                --用户类型
  	valid                number,                                --是否有效
  	sex                  varchar2(10),                          --用户性别
  	oicq                 varchar2(50),                          --用户QQ号码
  	birthday             date,                                  --出生日期
  	image                varchar2(50),                          --用户头像
  	sign                 varchar2(50),                          --用户签名
  	scores               number             default(0),         --用户积分总数
  	weekscore            number             default(0),         --用户本周积分总数
  	monthscore           number             default(0),         --用户本月积分总数
  	meilizhi             number             default(0),         --魅力值
  	zhuanjia             number             default(0),         --专家标识位     0-非专家    1-问吧专家
  	loginnum             number,                                --登录次数
  	theonline            varchar2(10),                          --在线用户
  	renzheng             varchar2(50),                          --
  	createdate           date,                                  --创建时间
  	stationtype          varchar2(8),                           --站台类别
  	entitytype           varchar2(1),                           --集体/个人
  	stationaddr          varchar2(80),                          --站台地址
  	opedegree            varchar2(20),                          --操作证等级
  	opecode              varchar2(20),                          --操作证书编码
  	callsign             varchar2(20),                          --呼号
  	memo                 varchar2(512),                         --备注    
  	info                 varchar2(512),                         --其他说明事项
  	remark               varchar2(500),                         --个人留言
  	sundaytime           varchar2(24),                          --
  	lockflag             number default 0 not null,             --是否被锁定
  	primary key(id)
);

create table tbl_userinfo_extendattr (
	id                   	number             	not null,
	userid            	number             	not null,
	ename                	varchar2(50)		not null,
	cnname                  varchar2(50)            not null,
	type                 	number(1)		not null,             --1 字符型 2-整型  3-浮点型 4 日期型（字符串形式保存）
	stringvalue          	varchar2(250),
	numericvalue         	number,
	floatvalue           	number(9,2),
	CONSTRAINT fk_userID FOREIGN KEY (userid) REFERENCES  TBL_USERINFO(ID) ON DELETE CASCADE,
        primary key (id)
);

--授权用户能够查看的栏目
create table column_authorized(
        id                  int             	not null,
        siteid              int                 not null, 
        columnid            int                 not null,
        targetid            int                 not null,  --用户ID或者是角色ID
        type                smallint            default 0, --0表示targetid为角色id, 1表示targetid为用户id
        createdate          date,
        primary key(id)
);

--文章评论
CREATE TABLE TBL_COMMENT ( 
	ID          NUMBER,                                 --主键
	userid      varchar2(20),                           --登录用户ID,如果没有登录，记录IP
	NAME        VARCHAR2(100),                          --评论人姓名
	LINK        VARCHAR2(100),                          --评论人联系方式
	CONTENT     VARCHAR2(2000),                         --评论内容
	IP          VARCHAR2(20),                           --评论人上网的IP地址
	ABOUT       NUMBER,                                 --文章ID
	auditflag   int       default 0,                    --0 审核通过，可显示 1-审核未通过 不显示 
	CREATEDATE  DATE,                                   --创建日期
	SITEID      NUMBER,                                 --站点ID
	CONSTRAINT pk_commentid PRIMARY KEY (ID)	
);

--调查
CREATE TABLE su_survey  (																--调查表
	 id           number NOT NULL ,   			--主键
	 surveyname   varchar2(200)   NULL ,			--调查名称
	 notes        varchar2(2000)   NULL ,			--调查描述
	 createtime   date  default sysdate not NULL ,		--创建时间
	 siteid       number default 0 not null,		--站点id	
	 useflag      number default 0 not null,		--有效标志 0-无效1-有效
        primary key (id) 
);

CREATE TABLE  su_answer  (					--答案表
	 id     number NOT NULL ,				--主键
	 userid   	number  NULL ,				--用户id
	 sid   	number  NULL ,					--调查id
	 qid   	number  NULL ,					--问题id	
	 answers   varchar2(200)  NULL ,			--答案
	 other    varchar2(200)  NULL,				--输入框的值
        primary key (id)
);
CREATE TABLE  su_danswer  (					--问题答案表
	 id     number NOT NULL ,				--主键
	 qid   number default 0 NOT NULL ,			--问题id
	 qanswer   varchar2(200)  NULL ,			--问题答案
	 picurl   varchar2(100)  NULL,				--图片地址
        primary key (id) 
);
CREATE TABLE  su_dquestion  (					--问题定义表
	 id     number NOT NULL ,				--主键
	 sid   number  NOT NULL ,				--调查id
	 qname   varchar2(500)  NULL ,				--问题名称
	 qtype   number  NULL ,					--问题类型
	 qmust   number  NULL ,					--是否必选
	 nother   number  NULL ,				--是否需要输入框	
	 atype   number  NULL,					--答案类型
        primary key (id) 
);

create table SU_DEFINEUSERINFO
(
  ID         NUMBER not null,
  SID        NUMBER default 0,                         --调查ID
  USERNAME   VARCHAR2(100),                            --提交调查用户名
  PHONE      VARCHAR2(100),                            --提交调查用户电话
  CREATEDATE DATE default sysdate,
  primary key(id)
);

create sequence SU_DEFINEUSERINFO_ID minvalue 1 maxvalue 999999999999999999999999999 start with 21 increment by 1 cache 20;

--用户报名参加活动
CREATE TABLE tbl_join_activity
(
	id          number,                                 --主键
	siteid      number,                                 --站点ID
	activiid    number,                                 --活动ID
	userid      varchar2(50),                           --登录用户ID
	username    varchar2(50),                           --用户真实姓名
	email       varchar2(100),                          --用户的电子邮件
	phone       varchar2(20),                           --用户电话
	mphone      varchar2(20),                           --用户手机
	address     varchar2(500),                          --用户住址
	postcode    varchar2(10),                           --邮政编码
	company     varchar2(200),                          --用户工作单位名称
	title       varchar2(200),                          --活动标题
	auditflag   smallint         default 0,             --是否批准参加
	createtime  date             default sysdate,       
	CONSTRAINT pk_feedback_id PRIMARY KEY (ID)                                        
);

--活动列表
CREATE TABLE tbl_activitys
(
	id          number,                                 --主键
	siteid      number,                                 --站点ID
	title       varchar2(200),                          --活动标题
	createtime  date             default sysdate,       --创建日期
	CONSTRAINT pk_activitys_id PRIMARY KEY (ID)                                        
);

--我的地址薄
CREATE TABLE  tbl_userinfos (
	ID            number   not null,                            --主键
	USERNAME      varchar2(20),                              --用户名称
	ADDRESS       varchar2(200),                             --地址
	EMAIL         varchar2(100),                             --电子邮件
	SEX           number,                                       --性别
	MOBILEPHONE   varchar2(20),                              --移动电话
	POSTCODE      varchar2(10),                              --邮编
	PHONE         varchar2(20),                              --电话
	CORPORATION   varchar2(500),                             --公司
	FAX           varchar2(20),                              --传真
	MEMBERID      varchar2(50),                              --用户id
	CREATETIME    number not null,                              --创建时间
	CONSTRAINT pk_userinfos_id PRIMARY KEY (ID)
);

--每日流水号管理表
create table tbl_lshbydate(
       cdate   date,
       lsh     varchar2(15)
);

--用户留言
CREATE TABLE TBL_LEAVEWORD
(
	id             number         not null,                    --主键
  	siteid         number         not null,                    --站点id
  	userid         varchar2(50),                               --登录用户ID,如果没有登录，记录IP
	flag           number  default 0,                          --是否在网站上显示，1--显示 0--不显示
  	title          varchar2(100),                              --留言标题
  	content        varchar2(4000),                             --留言内容
  	writedate      date,                                       --留言日期
  	company        varchar2(200),                              --公司
  	linkman        varchar2(100),                              --联系人
  	links          varchar2(500),                              --联系方式
  	zip            varchar2(20),                               --区号
  	email          varchar2(100),                              --电子邮件
  	phone          varchar2(20),                               --电话
  	formid         number,                                     --用户留言表单ID
  	departmentid   number,                                     --信息分发到的部门ID
	retcontent     varchar2(2000),                             --回复的内容
  	auditor        varchar2(50),                               --如果需要审核，保存审核人信息
  	auditflag      number default(0),                          --0表示审核完毕  1表示审核进行中
  	processor      varchar2(50),                               --处理人用户ID
  	mphone         varchar2(20),                               --移动电话
	columnid       number         default(0),                  --咨询问题分类定义
  	code           varchar2(12),                               --用户咨询流水号   AyyyymmddXXX    首字母A表示局长信箱  B表示在线咨询 C表示献言献策
  	sex            smallint,                                   --留言人性别
	valid          smallint default(0),                        --问题是否有效 0--有效 1--无效
	validreason    varchar2(512),                              --问题无效的原因
	datefromdept   date,                                       --部门回复日期
	finalflag      smallint default(0),                        --最终回复用户标志位，当回复获得批准后，由留言板部门管理员或留言板管理员将之设置为1
	endtouser      date,                                       
	CONSTRAINT pk_leaveword_id PRIMARY KEY (ID)
);
create index leavemsgindex_forsiteid on tbl_leaveword(siteid);

create table tbl_leaveword_to_dept
(
	id             number         not null,                    --主键
  	siteid         number         not null,                    --站点id
  	departmentid   number,                                     --信息分发到的部门ID
  	formid         number,                                     --用户留言表单ID
  	lwid           number,                                     --用户留言ID
  	processor      varchar2(50),                               --回答人用户ID
	flag           number  default 0,                          --是否已经回答，1--回答 0--未回答  	
  	content        varchar2(4000),                             --回答内容
  	retdate        date,                                       --回答日期
  	createdate     date default sysdate,                       --分配给该用户的日期
	CONSTRAINT pk_leaveword_to_dept_id PRIMARY KEY (ID)
);

--用户留言分类分类
create table tbl_leavewordclass (
	id                   	number                     not null,
	siteid               	number                     not null,
	parentid             	number      		   not null,
	orderid              	number                     not null,
	cname                	varchar2(50)        	   not null,
	ename                	varchar2(50)        	   not null,
	dirname              	varchar2(200)              not null,
	editor               	varchar2(30)               not null,
	extname              	varchar2(10)               not null,
        summary                 varchar2(250),
        lwnum                   int    default(0),                                --某分类的留言数量
	createdate           	date                       not null,
	lastupdated          	date			   not null,
	primary key(id)
);
insert into tbl_leavewordclass values(0,0,-1,0,'留言分类','lwsite','/','admin','html','',0,sysdate,sysdate);

create table tbl_leaveword_valid_reason
(
	id             number         not null,                    --主键
  	siteid         number         not null,                    --站点id
        content        varchar2(500)  not null,
        primary key(id)
);

insert into tbl_leaveword_valid_reason(id,siteid,content) values(1,40,'缺少联系信息');
insert into tbl_leaveword_valid_reason(id,siteid,content) values(2,40,'询问问题不合理');


CREATE TABLE tbl_jobinfo(
	id                        number                NOT NULL,
	companyid                 varchar2(50)       NOT NULL,
	postid                    varchar2(50)       NOT NULL,
	firstname                 varchar2(40)       NOT NULL,
	sendname                  varchar2(40)       NOT NULL,
	gender                    varchar2(2)        NOT NULL,
	nationality               varchar2(15)       NOT NULL,
	age                       varchar2(10),
	marrage                   number,
	birthday                  varchar2(10),
	birthplace                varchar2(100),
	photopath                 varchar2(100),
	photooldname              varchar2(100),
	accontplace               varchar2(100),
	homeaddress               varchar2(200),
	liveaddress               varchar2(200),
	educationalcategorie      varchar2(200),
	education                 varchar2(20),
	majorsubjects1            varchar2(50),
	majorsubjects2            varchar2(50),
	professional              varchar2(40),
	certificate               varchar2(50),
	cbgntime                  varchar2(50),
	cendtime                  varchar2(50),
	graduateschool            varchar2(80)         NOT NULL,
	documentstype             varchar2(4)          NOT NULL,
	documentsnum1             varchar2(50),
	titlebgntime              varchar2(20),
	titlename                 varchar2(50),
	grade                     varchar2(50),
	workbgntime1              varchar2(20),
	workendtime1              varchar2(20),
	employer1                 varchar2(60),
	post1                     varchar2(200),
	city1                     varchar2(25),
	mobile                    varchar2(20),
	phone                     varchar2(20),
	email                     varchar2(30),
	createdate                date                  NOT NULL,
	major1                    varchar2(50),
	major2                    varchar2(50),
	documentsnum2             varchar2(50),
	documentsnum3             varchar2(50),
	documentsnum4             varchar2(50),
	workbgntime2              varchar2(20),
	workendtime2              varchar2(20),
	employer2                 varchar2(60),
	post2                     varchar2(200),
	city2                     varchar2(25),
	workbgntime3              varchar2(20),
	workendtime3              varchar2(20),
	employer3                 varchar2(60),
	post3                     varchar2(200),
	city3                     varchar2(25),
	subTitleName              varchar2(50),
	primary key(id) 
);


--电子商务系统
create table en_province (
        id               number              not null,
        provname         varchar2(100)     not null,
        orderid          number,
        emsfee           number
);

create table en_city (
        id               number              not null,
        provid           number              not null,
        cityname         varchar2(100)     not null,
        orderid          number
);

create table en_zone (
        id               number              not null,
        cityid           number              not null,
        zonename         varchar2(100)     not null,
        orderid          number
);

create table en_receivemoney (
        id               number              not null,
        brief            varchar2(200)    not null,
        userid           varchar2(20),
        payer            varchar2(20),
        jing_ban_ren     varchar2(20),
        payway           number,
        nint             number,
        unit             varchar2(100),
        createdate       date,
        orderid          number(38)       not null,
        nnumber          float,
        primary key(id)
);

create table en_expfee (
        id               number              not null,
        cityid           number              not null,
        zonename         varchar2(100)     not null,
        orderid          number
);

create table en_expcorp (
        id               number              not null,
        cityid           number              not null,
        zonename         varchar2(100)     not null,
        orderid          number
);

create table en_postfee (
        id               number              not null,
        cityid           number              not null,
        zonename         varchar2(100)     not null,
        orderid          number
);

create table en_gonghuo (
        id               number               not null,
        kind             varchar2(100)     not null,
        name             varchar2(50)      not null,
        lianxiren        varchar2(50),
        address          varchar2(200),
        postcode         varchar2(50),
        phone            varchar2(50),
        email            varchar2(50),
        notes            varchar2(250),
        siteid           number,
        primary key(id)
);

create table en_message (
        id               number               not null,
        siteid           number               not null,
        send_user        varchar2(50),
        receive_user     varchar2(50),
        message          varchar2(250),
        senddate         date,
        send_del         number,
        receive_del      number,
        flag             number,
        primary key(id)
);

create table en_savemessage (
        id               number               not null,
        siteid           number               not null,
        message          varchar2(250),
        sender           varchar2(50),
        receiver         varchar2(50),
        send_user        number,
        receive_user     number,
        senddate         date,
        createdate       date,
        userid           number,
        primary key(id)
);

create table en_applicationmanage (
        id               number               not null,
        siteid           number               not null,
        userid           number,
        orderid          number(38),
        productid        number,
        repairflag       number,
        exchangeflag     number,
        backflag         number,
        notes            varchar2(250),
        dealdate         date,
        primary key(id)
);

CREATE TABLE TBL_ORDERS  ( --定单表
	 SITEID        			number,				            --站点id
	 ORDERID       			number(38)                NOT NULL ,        --定单号
	 initorderid                    number(38)  default(0)    NOT NULL ,        --原始定单号(拆单=原单号，非拆单=0)
	 USERID        			number,	     			            --用户id
	 NAME          			varchar2(50),		                    --收货人姓名
	 SEX           			number,				            --性别
	 ADDRESS       			varchar2(1000),	                            --地址
	 POSTCODE      			varchar2(20),		                    --邮编
	 PHONE         			varchar2(50),		                    --电话
	 TOTALFEE      			number(9,2),		                    --定单总额
	 DELIVERYFEE   			number(9,2),		                    --邮寄费用
	 PAYFEE        			number(9,2),		                    --应付费用
	 r2TrxId                        varchar2(80),                               --支付中心返回流水号
	 zfmemberid                     varchar2(30),                               --支付中心会员ID
	 r2type                         smallint    default(0),                     --支付中心返回类型，1浏览器重定向 2服务器点对点
         payresult                      varchar2(20),                               --支付结果，成功/失败/其他
	 VALID         			number      default(0),		
	 STATUS        			number                     NOT NULL ,	    --定单状态
	 BILLINGWAY    			number,				            --支付方式
	 CITY          			varchar2(50),			            --城市
	 RECEIVE       			number,				
	 NOUSE         			number      default(1),	                    --定单是否有效 1-有效 0-无效
	 PROVINCE      			varchar2(50),			            --省份
	 zone          			varchar2(50),			            --地区
	 sendway       			number,				            --配送方式
	 payway        			number,		                            --付款方式
	 commissioncharge               number(9,2),		                    --折扣
	 insurance   			number(9,2),		                    --保险费
	 need_invoice                   number        default(0),	            --是否需要发票
	 bill_to_address                varchar2(2000),	                            --发票地址
	 notes                          varchar2(4000),	                            --发票信息
	 flag                           number(1)     default(0),
	 payflag                        number(1)     default(0),                   --是否已经支付
	 linktime                       varchar2(50),			
	 usescores                      number        default(0),	            --用户使用积分
	 orderscores                    number        default(0),                   --定单产生的积分 
	 usecard                        number        default(0),                   --使用购物券面额   新增加
	 paydate                        date,                                       --订单支付时间
	 CREATEDATE    			date,				            --创建时间
	 primary key(orderid) 
);

CREATE TABLE TBL_ORDERS_DETAIL  ( -- 定单详细信息表
	 ID                             number              NOT NULL,               --主键
	 ORDERID                        number(38)          NOT NULL,               --定单号
	 supplierid                     number              default(0)   not null,  --供货商id(对应每个供货商siteid)
	 suppliername                   varchar2(100),                              --供货商名称
	 PRODUCTID                      number,				            --商品id
	 ORDERNUM                       number,				            --商品数量
	 SALEPRICE                      number(9,2),		                    --商品售价
	 WEIGHT                         number,				            --商品重量
	 CREATEDATE                     date,				            --创建时间
	 productname                    varchar2(200),	                            --商品名称
	 type                           varchar2(100),	                            --商品类型（扩展属性）
	 cardid                         number             default(0) NOT NULL,     --使用购物卷id 
	 CONSTRAINT fk_orderID FOREIGN KEY (ORDERID) REFERENCES  TBL_ORDERS(ORDERID) ON DELETE CASCADE,	 	 
	 primary key(id)
);

--送货方式管理
create table tbl_fee(
	id		    number not null,     --主键
	siteid              number default 0 not null,--站点id
	cname               varchar(200),				 --送货方式名称
	notes               varchar(2000),       --送货方式说明
	fee									number(9,2) default 0 not null,--运费
	createdate          date default sysdate not null,--创建日期
	primary key(id)
);

--运费管理
create table tbl_feeforproduct --商品运费
(
    id                      number                  not null,
    siteid                  number                  default 0,
    pid                     number                  default 0,
    fee                     number(9,2)             default 0,
    begintime               varchar2(50),
    endtime                 varchar2(50),
    updatetime              date                    default sysdate,
    updateid                varchar2(200),
    createdate              date                    default sysdate,
    primary key(id) 
);
CREATE SEQUENCE tbl_feeforproduct_id START WITH 1 increment by 1 cache 20; 

create table tbl_cityfee --城市运费
(
    id                      number                  not null,
    siteid                  number                  default 0,
    cityid                  number                  default 0,
    fee                     number(9,2)             default 0,
    begintime               varchar2(50),
    endtime                 varchar2(50),
    updatetime              date                    default sysdate,
    updateid                varchar2(200),
    createdate              date                    default sysdate,
    primary key(id) 
);
CREATE SEQUENCE tbl_cityfee_id START WITH 1 increment by 1 cache 20; 

create table tbl_paywayfee --支付方式运费
(
    id                      number                  not null,
    siteid                  number                  default 0,
    paywayid                number                  default 0,
    paywayname              varchar2(100),
    paywaytype              varchar2(100),    
    fee                     number(9,2)             default 0,
    totaltype               number                  default 0,
    totalfee                number(9,2)             default 0,
    begintime               varchar2(50),
    endtime                 varchar2(50),
    updatetime              date                    default sysdate,
    updateid                varchar(200),
    createdate              date                    default sysdate,
    primary key(id) 
);
CREATE SEQUENCE tbl_paywayfee_id START WITH 1 increment by 1 cache 20; 

--支付方式管理
create table tbl_sendway(
        id                  number                   not null,           --主键
        siteid              number default 0         not null,           --站点id
        cname               varchar(200),				 --支付方式名称
	notes               varchar(2000),                               --支付方式说明
	createdate          date default sysdate     not null,           --创建日期
	primary key(id)
);

--送货地址
create table tbl_addressinfo(
        id		    number                   not null,       --主键
        userid		    number                   not null,       --用户id
        name                varchar(200),          --收货人姓名
        provinces	    varchar(50),					 --省份
        city		    varchar(50),						--城市
        zone                varchar(50),            --地区
        mobile              varchar(50),            --移动电话
        phone               varchar(50),            --固定电话
        zip		    varchar(50),            --邮编
        address             varchar(200),           --详细地址
	createdate          date     default sysdate not null,--创建时间
	primary key(id)
);

--购物券
--ischeck 是否发放0-未发放，1-已发放
CREATE TABLE tbl_shoppingcard(
	id               number                  not NULL,
	siteid           number                  not null,
	cardnum          varchar(50),                             --购物券标识号 
	denomination     number,                                  --购物券金额
	begintime        varchar(50),                             --开始时间
	endtime          varchar(50),                             --结束时间
	code             varchar(50),                             --激活码
	createtime       date,                                    --创建时间
	activation       number,                                  --是否被使用0-未被使用，1-被使用
	ischeck          number                  NOT NULL,        
	primary key(id) 
);

--买赠方案管理
create table tbl_mai_zeng_fangan(
        id               number                    not null,
        siteid           number                    not null,
        zengway          number                       not null,      --赠品方案编号
        price            number(2,9),                                   --按价格赠，一个订单的价格达到规定的数值享受赠品（一种方案）
        pcount           number,                                     --按数量赠，某个商品购买到规定数量，享受赠品    （第二种方案）
        productid        number,                                     --按数量赠需要购买的商品
        productbrand     varchar2(50),                            --购买某个品牌的商品享受赠品                    （第三种方案） 
        useflag          number,                                --该方案是否使用     0--不使用   1--使用
        majorflag        number,                                --主要方案，在两个方案冲突情况，使用该方案，1--主方案，所有使用方案中只能有一个主方案
        createdate       date,                                    
        editor           varchar2(50)                             
);


--赠品管理
create table tbl_zengpin(
        productid        number                    not null,      --产品ID
        productname      varchar2(250)             not null,      --产品名称
        fanganid         number                       not null,   --适用方案ID
        createdate       date,                                    
        editor           varchar2(50)                             
);

--捆绑销售
create table tbl_groupproductsale(
        groupid          number                       not null,      --产品组合ID
        productid        number                    not null,      --产品ID
        productname      varchar2(250)             not null,      --产品名称
        summary          varchar2(500),                           --组合产品描述
        productcount     number                  not null,      --商品数量
        YhPrice          number(2,9),                                   --优惠价格
        groupbigpic      varchar2(250),                           --组合大图
        groupsmallpic    varchar2(250),                           --组合小图
        grouptexuao      varchar2(250),                           --组合特效图
        groupcontentpic  varchar2(250),                           --组合内容图
        begintime        date,                                    --开始日期
        endtime          date,                                    --结束日期
        createdate       date,                                    
        editor           varchar2(50)                             
);

--支付接口定义表
create table tbl_paywayinterface(
       id                number            not null,
       paywayid          number            not null,
       accountnumber     varchar2(200),
       paywaykey         varchar2(200),
       alipayaccount     varchar2(100),
       createdate        date              not null,
       primary key(id) 
); 

--发票信息表  新增加
create table tbl_invoiceinfo
(
       id                 number,
       siteid             number           default 0,           --站点id
       userid             number           default 0,           --用户id
       orderid            number(38)       default 0,           --用户订单号
       invoicetype        number           default 0,           --发票类型(0-普通发票1-增值税发票)
       title              number           default 0,           --发票抬头（个人/单位0-个人1-单位）
       companyname        varchar2(200),                        --单位名称
       content            number           default 0,           --发票内容(外键内容id)
       identification     varchar2(200),                        --纳税人识别号
       registeraddress    varchar2(200),                        --注册地址
       phone              varchar2(50),                         --注册电话
       bankname           varchar2(200),                        --开户银行
       bankaccount        varchar(100),                         --银行帐号
       createdate         date       default sysdate,           
       primary key(id)
);
CREATE SEQUENCE tbl_invoiceinfo_id START WITH 1 increment by 1 cache 20; 

--发票内容维护表
create table tbl_invoicecontent
(
       id                 number,
       siteid             number            default 0,          --站点id
       content            varchar2(200)     not null,           --内容
       createdate         date              default sysdate,    --创建时间
       primary key(id)
);
CREATE SEQUENCE tbl_invoicecontent_id START WITH 1 increment by 1 cache 20; 
--送货费用管理


--前台应用程序
CREATE SEQUENCE TBL_ACTIVITY_ID INCREMENT BY 1 START WITH 1 NOCYCLE CACHE 20 NOORDER;
create sequence TBL_COMMENT_ID start with 1 increment by 1 cache 20;
create sequence TBL_SURVEY_ANSWER_ID start with 1 increment by 1 cache 20;
create sequence TBL_SURVEY_ID start with 1 increment by 1 cache 20;   
--我的通信录顺序id
CREATE SEQUENCE tbl_userinfos_id START WITH 805 increment by 1 cache 20;       
--留言顺序id
CREATE SEQUENCE tbl_leaveword_id START WITH 1 increment by 1 cache 20;       
CREATE SEQUENCE su_answer_id START WITH 6117 increment by 1 cache 20;
CREATE SEQUENCE su_danswer_id START WITH 4 increment by 1 cache 20; 
CREATE SEQUENCE su_dquestion_id START WITH 1 increment by 1 cache 20;
CREATE SEQUENCE su_survey_id START WITH 1 increment by 1 cache 20;
--前台用户注册的ID
CREATE SEQUENCE tbl_userreg_id START WITH 1 increment by 1 cache 20;         
CREATE SEQUENCE TBL_ORDERS_DETAIL_ID START WITH 1 increment by 1 cache 20;
CREATE SEQUENCE TBL_FEE_ID START WITH 1 increment by 1 cache 20;
CREATE SEQUENCE TBL_SENDWAY_ID START WITH 1 increment by 1 cache 20;
CREATE SEQUENCE TBL_ADDRESSINFO_ID START WITH 1 increment by 1 cache 20;
--用于所有用户自定义的表单
CREATE SEQUENCE self_define_ID START WITH 1 increment by 1 cache 20;          
CREATE SEQUENCE TBL_SHOPPINGCARD_ID START WITH 1 increment by 1 cache 20;


--信息采集系统
create sequence spider_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;

create table SP_BASIC_ATTRIBUTES
(
  ID                      int                 not null,             --主键
  siteid                  int,                                      --使用者站点ID
  SITENAME                varchar2(50),                             --站点名称
  STARTURL                varchar2(500),                            --开始的URL
  URLNUMBER               int,                                      --抓取URL的数量
  STATUS                  varchar2(5),                              --返回的状态码
  LOGINFLAG               number(1),                                --是否需要登录
  POSTURL                 varchar2(255),                            --登录用的URL
  POSTDATA                varchar2(255),                            --登录用的数据
  PROXYFLAG               number(1),                                --是否使用PROXY服务器
  KEYWORDFLAG             int,                                      --是否采用关键字过滤
  STOPFLAG                int,                                      --是否停止运行
  CNAME                   varchar2(100),
  createdate              date                not null,
  primary key(id)     
);

create table sp_proxy
(
  id                      int                 not null,
  siteid                  int,
  STARTURLID              int,                                       --SP_BASIC_ATTRIBUTES主键ID
  PROXYLOGINUSER          varchar2(50),                              --PROXY用户名
  PROXYLOGINPASS          varchar2(50),                              --PROXY口令字
  PROXYURL                varchar2(500),                             --PROXY地址
  PROXYPORT               varchar2(20),                              --PROXY端口号
  createdate              date                not null,
  primary key(id)   
);


CREATE TABLE sp_keyword_rules (
  Id                int                 not null,
  siteid            int,
  starturlid        int,                                             --SP_BASIC_ATTRIBUTES主键ID
  Tkeyword          varchar2(255),
  bkeyword          varchar2(255),
  tbrelation        int,
  score             varchar2(10),
  createdate        date                not null,
  primary key(id)   
);

create table SP_BASIC_COLUMNS
(
  ID         int      not null,
  siteid     int,
  BASICID    int,                                                    --SP_BASIC_ATTRIBUTES主键ID
  CLASSID    int,                                                    --内容保存的栏目ID
  CREATEDATE date     not null,
  primary key(id)
);

create table sp_global (
	id                    int                       not null,
	siteid                int,
	starttime             date,
	interval              int,
	proxyflag             number(1),
	proxyname             varchar2(50),
	proxyport             varchar2(20),
	proxyloginuser        varchar2(50),
	proxyloginpass        varchar2(50),
	proxyloginflag        number(1)                  not null,
	systemrun             number(1)                  not null,
	tkeyword              varchar2(250),
	bkeyword              varchar2(250),
	tbrelation            number(1),
	keywordflag           number(1),
        createdate            date                       not null,
        primary key(id)   
);

create table sp_document_type (
	id                    number                      not null,
	starturlid            number,
	doctype               varchar2(5) 
);

create table sp_match_url (
	id                    int                         not null,
	siteid                int,
	starturlid            int,
	matchurl              varchar2(255) 
);

create table sp_special_code (
	id                    int                          not null,
	siteid                int,
	starturlid            int,
	ST                    varchar2(255),
	ET                    varchar2(255),
        primary key(id)
);

create table tbl_workload (
	Id                    int                          not null,
	siteid                int,
	starturlid            int,
	url                   varchar2(500),
	status                varchar2(5),
	lastmodified          date,
	urltitle              varchar2(200),
	urltype               number(1),
	depth                 int,
	thedate               date,
        primary key(id) 
);

--问吧表结构定义
CREATE SEQUENCE tbl_wenba_ID START WITH 1 increment by 1 cache 20;
create table fawu_wenti_column                          --问吧分类定义
(
        id                      int                     not null,            --问吧分类主键
        siteid                  int                     not null,            --站点ID
        parentid                int                     not null,            --父分类ID
        orderid                 int                     not null,            --序号ID
        cname                   varchar2(100),                               --中文名称
        ename                   varchar2(100),                               --英文名称
        dirname                 varchar2(200),                               
        status                  int                     default 0,
        creater                 varchar2(20),
        createdate              date,
        primary key(id)
);
insert into fawu_wenti_column values(0,2191,-1,0,'问吧文类','wenba','/',0,'admin',sysdate);

create table fawu_anwser                                                     --答案表      
(
        id                      int                     not null,            --答案主键
        qid                     int                     not null,            --问题主键，本表的外键
        anwser                  varchar2(4000),                              --答案
        votenum                 int,                                         --投票数量
        ipaddress               varchar2(15),                                --回答者的IP地址
        userid                  int,                                         --回答者用户ID
        username                varchar2(300),                               --回答者用户名
        fenshu                  int                     default 0,           --获取分数
        cankaoziliao            varchar2(4000),                              --参考资料
        createdate              date                    default sysdate,     --创建时间
        PICPATH                 varchar2(255),                               --图片路径
        BETTERANS               int,                                         --是否是最佳答案
        ANW_STATUS              int,                                         --答案状态
        primary key(id)
);

create table fawu_vote                                 --问吧答案投票表
(
        id                      int                    not null,             --主键ID   
        aid                     int                    not null,             --问题答案ID
        userid                  int                    not null,             --投票用户ID
        primary key(id)
);

create table fawu_wenti                                 --问题表
(
        id                      int                     not null,           --主键ID
        columnid                int                     not null,           --分类ID
        parcolumid              int,                                        --上一级分类ID
        cname                   varchar2(100),                              --问题中文名称
        question                varchar2(1000),                             --问题内容
        status                  int,                                        --问题状态
        voteflag                int,                                        --是否是带投票的问题
        xuanshang               int                     default 0,          --悬赏分数
        answernum               int                     default 0,          --回答数目
        source                  varchar2(50),                               --
        createdate              date,                                       --创建日期
        ipaddress               varchar2(15),                               --客户端IP地址
        creater                 varchar2(50),                               --创建人
        province                varchar2(50),                               --创建人省份
        city                    varchar2(50),                               --创建人所在城市
        area                    varchar2(50),                               --创建人所在区域
        picpath                 varchar2(255),                              --图片路径
        emailnotify             int                     default 0,          -- 是否邮件通知  1--通知  0--不通知,
        title                   varchar2(60),                               --
        email                   varchar2(40),                               --用户电子邮件
        userid                  int,                                        --用户ID
        username                varchar2(20),                               --用户名称
        filepath                varchar2(255),                              --上传文件路径
        istop                   int                     default 0,          --是否置顶
        anw_status              int,                                        --答案状态
        dianjinum               int,                                        --点击数
        user_id_huida           varchar2(50),                               --回答人ID
        primary key(id)
);

create table bbs_thread(
	threadid	number		not null,
	lyhid		number,
	forumid		number,
	answerid	number          default 0,
	threadname	varchar(500),
	threadcontent	varchar(500),
	posttime	date,
	author		varchar(500),
	ipaddress	varchar(500),
	answeruser	varchar(500),
	answertime	date,
	hitnum		number,
	answernum	number,
	gotop		number          default 0,
	typeflag	number,
	hiddenflag	number          default 0,
	lockflag	number,
	primary key(threadid) 
);



create table bbs_column(
	id		number		not null,
	lyhid		number,
	bbsname		varchar(500),
	bbsdesc		varchar(500),
	manager		varchar(500),
	managerpass	varchar(500),
	postman		varchar(500),
	postdate	date,
	lastposter	varchar(500),
	lasttopic	varchar(500),
	lastposttime	date,
	pic		varchar(500),
	creationdate	date,
	threadnum	number,
	topicnum	number,
	siteid		number,
	primary key(id)
);


create table bbs_online(
	id		varchar(500)		not null,
	lyhid		number,
	username	varchar(500),
	logintime	date,
	ipaddress	varchar(500)
);


create table bbs_friend(
	userid		varchar(500),
	lyhid		number,
	friend		varchar(500)
);


--文章评论
CREATE TABLE TBL_COMMENT ( 
	id             int                      not null,                --主键
	siteid         int                      not null,                --站点id
	userid         int                      not null,                --用户id
	Username       varchar2(30)             not null,                --用户登录名称
	pid            int                      not null,                --商品id
	commenttitle   varchar2(200)            not null,                --标题
	Scores         int                      default(0),              --评分
	experience     varchar2(500),                                    --使用心得
	Advantages     varchar2(500),                                    --优点
	Shortcomings   varchar2(500),                                    --不足
	usefulnum      int                      default(0),              --认为该评论有用的人数
	nousenum       int                      default(0),              --认为该评论无用的人数
	auditflag      int       default 0,                              --0 审核通过，可显示 1-审核未通过 不显示 	
	createdate     date                     default sysdate,
	CONSTRAINT pk_commentid PRIMARY KEY (ID)	
);

--文章评论回复
create table Tbl_commentanswer
(
        id              int                      not null,               --主键
        parentid        int                      default      0,         --回复的父ID                                              
        cid             int                      not null,               --商品评论id外键
        auditflag       int                      default      1,         --是否通过审核标记 0-通过  1-未通过
        username        varchar2(100)            not null,               --回复人登录名
        answercontent   varchar2(500)            not null,               --回复内容
        createdate      date                     default sysdate,        --回复时间
	CONSTRAINT fk_commentID FOREIGN KEY (cid) REFERENCES  tbl_comment(ID) ON DELETE CASCADE,
        primary key(id)
);

--发票信息表  新增加
create table en_invoice
(
        id               int                      not null,             --主键
        siteid           int                      not null,             --站点id
        userid           int                      not null,             --用户id
        orderid          int                      not null,             --用户订单号
        invoicetype      int                      default(0),           --发票类型(0-普通发票1-增值税发票)
        titleflag        int                      default(0),           --（个人/单位0-个人1-单位）
        title            varchar2(100),                                  --发票抬头
        companyname      varchar2(200),                                  --单位名称
        identification   varchar2(200),                                  --纳税人识别号
        registeraddress  varchar2(200),                                  --注册地址
        phone            varchar2(15),                                   --注册电话
        postaddress      varchar2(200),                                  --邮寄地址
        postname         varchar2(20),                                   --接收人姓名
        postcode         varchar2(6),                                    --邮政编码
        bankname         varchar2(200),                                  --开户银行
        bankaccount      varchar2(100),                                  --银行帐号
        totalprice       number(18,2),                                    --发票总价值
        createdate       date                 default sysdate,           --创建时间
        primary key(id)
);

--发票内容维护表
create table en_invoicecontent
(
        id              int                      not null,             --主键,每个发票项的ID
        invoiceid       int                      not null,             --发票ID
        siteid          int                      not null,             --站点id
        content         varchar2(200)            not null,             --内容
        price           number(18,2),                                   --商品单价
        pnum            int                      default(0),           --商品数量
        createdate      date                     default sysdate,      --创建时间
	CONSTRAINT      fk_contentid FOREIGN KEY (invoiceid) REFERENCES  en_invoice(id) ON DELETE CASCADE,
        primary key(id)
);


--社团用户管理表
create table tbl_shetuan (
	id		number			not null,	--主键
	stname		varchar2(100),				--社团名称
	lianxiren	varchar2(100),				--联系人
	phone		varchar2(100),				--电话	
	email		varchar2(100),				--邮件
	username	varchar2(100),				--用户名
	passwd		varchar2(100),				--口令
	createdate	date,					--注册时间
	primary key (id)
);
create sequence tbl_shetuan_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;

--会议室基本信息管理表
create table tbl_jiben(
	id		number			not null,	--主键
	meetname	varchar2(500),				--会议室名称
	meetmax		varchar2(100),				--会议室容量
	meetroot	varchar2(500),				--会议室位置
	primary key (id)
);
create sequence tbl_jbxinxi_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;

--会议室预定信息收集(管理)
create table tbl_yuding(
	id		number			not null,	--主键
	ydperson	varchar2(100),				--预定人
	jbxinxiid	varchar2(500),				--预定会议室ID
	khdate		date,					--开会时间
	jsdate		date,					--结束时间
	flag		number,					--审批状态        1  审核     2  未审核
	shperson	varchar2(100),				--审批人
	shdate		date,					--审批时间
	primary key (id)
);
create sequence tbl_ydxinxi_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;





---系统未使用的表定义
--电子商务进、销、存系统
--银行编号表
create table tbl_banks (
        id                      number                     not null,      --主键
        bankid                  varchar2(12)            not null,      --银行ID
        siteid                  number                     not null,      --站点ID
        bankattribname          varchar2(12)            not null,      --银行简称
        bankname                varchar2(40),                          --银行名称
        createdate              date,                                  --创建时间
        updatedate              date,                                  --修改时间
        primary key (id)
);

--业务员数据表
create table tbl_salesman (                                            --tbl_members
        id                      number                     not null,      --主键
        salesmanid              varchar2(12)            not null,      --业务员ID
        siteid                  number                     not null,      --站点ID
        chinesename             varchar2(12)            not null,      --业务员名称
        englishname             varchar2(12),                          --业务员英文名称
        contactphone            varchar2(20),                          --业务员联系电话
        mobilephone             varchar2(12),                          --业务员移动电话
        contactaddress          varchar2(120),                         --业务员联系地址
        email                   varchar2(50),                          --业务员电子邮件地址
        createdate              date,                                  --创建时间
        updatedate              date,                                  --修改时间
        primary key (id)
);

--客户表(取消)
create table tbl_customer (                                            --tbl_userinfo
        id                      number                     not null,      --主键
        customerid              varchar2(12)            not null,      --客户ID
        siteid                  number                     not null,      --站点ID
        customerattribname      varchar2(50)            not null,      --客户简称
        customername            varchar2(100),                         --客户名称
        invoiceno               varchar2(20),                          --发票编号
        owner                   varchar2(12),                          --负责人
        rocid                   varchar2(18),                          --身份证号码
        contactman1             varchar2(18),                          --联系人1
        contactman2             varchar2(18),                          --联系人2
        contactphone1           varchar2(20),                          --联系人1电话
        contactphone2           varchar2(20),                          --联系人2电话
        fax                     varchar2(20),                          --传真号码
        salesmanid              number,                                   --业务负责人
        customeraddress         varchar2(120),                         --公司地址
        deliveryaddress         varchar2(120),                         --送货地址
        invoiceaddress          varchar2(120),                         --发票地址
        paydays                 number,                                   --付款日数
        creditline              number(32,2),                          --信用额度
        creditbalance           number(32,2),                          --信用余额
        lastdeliverydate        date,                                  --最后交易日
        createdate              date,                                  --创建时间
        updatedate              date,                                  --修改时间
        primary key (id)
);

--供货商表（供货商管理）
create table tbl_supplier (
        id                      number                     not null,      --主键
        supplierid              varchar2(12)            not null,      --供货商ID
        siteid                  number                     not null,      --站点ID
        supplierattribname      varchar2(50)            not null,      --供货商简称
        suppliername            varchar2(100),                         --供货商名称
        invoiceno               varchar2(20),                          --发票编号
        owner                   varchar2(12),                          --负责人
        rocid                   varchar2(18),                          --身份证号码
        phone1                  varchar2(18),                          --联系电话1
        phone2                  varchar2(18),                          --联系电话2
        fax                     varchar2(20),                          --传真号码
        contactname1            varchar2(20),                          --联系人1
        contactname2            varchar2(20),                          --联系人2
        companyaddress          varchar2(120),                         --公司地址
        deliveryaddress         varchar2(120),                         --工厂地址
        invoiceaddress          varchar2(120),                         --发票地址
        lastpurchasedate        date,                                  --最后交易日
        paydays                 number      default(0),                   --付款日数
        prepaid                 number      default(0),                   --暂付款
        createdate              date,                                  --创建时间
        updatedate              date,                                  --修改时间
        primary key (id)
);

--产品表（取消）
create table tbl_jxc_product (
        id                      number                     not null,      --主键
        productid               varchar2(12)               not null,      --产品ID
        siteid                  number                     not null,      --站点ID
        productname             varchar2(120)              not null,      --产品名称
        safestock               number,                                   --安全存量
        lastpurchasedate        date,                                  --最近进货日期
        lastdeliverydate        date,                                  --最近出货日期
        quantity                number         default(0),                --库存量
        createdate              date,                                  --创建时间
        updatedate              date,                                  --修改时间
        primary key (id)
);

--进货单主表（进货管理）
create table tbl_purchasemaster (
        id                      number                     not null,      --主键
        purchaseid              varchar2(12)            not null,      --进货单号
        siteid                  number                     not null,      --站点ID
        purchasedate            date                    not null,      --进货日期
        supplierid              varchar2(12)            not null,      --供货商编号
        purchaseproperty        number(1)                default(0),    --进货单属性 0-进货  1-退货
        invoiceno               varchar2(10),                          --发票编号
        subtotal                number(36,2)            default(0),    --合计金额
        valueaddtax             number(36,2)            default(0),    --营业税
        amount                  number(36,2)            default(0),    --总计金额
        accountpayable          number(36,2)            default(0),    --应付金额
        paid                    number(36,2)            default(0),    --已付帐款
        limitdate               date,                                  --付款截止日期
        createdate              date,
        updatedate              date,
        primary key (id)
);

--进货单明细表
create table tbl_purchasedetail (
        id                      number                     not null,      --主键
        purchaseid              varchar2(12)            not null,      --进货单号
        productid               varchar2(12)            not null,      --产品ID
        purchasequantity        number                     default(0),    --进货数量
        purchaseunitprice       number(18,2),                          --进货单价
        purchaseamount          number(36,2)           default(0),     --进货总价
        createdate              date,                                  --创建时间
        updatedate              date,                                  --修改时间
        primary key (id)
);

--出货单主表（出货管理）
create table tbl_deliverymaster (
        id                      number                     not null,      --主键
        deliveryid              varchar2(12)            not null,      --出货单号
        siteid                  number                     not null,      --站点ID
        deliverydate            date                    not null,      --出货日期
        customerid              varchar2(12)            not null,      --客户编号
        salesmanid              varchar2(12)            not null,      --业务员编号
        deliveryaddress         varchar2(120),                         --送货地址
        invoiceno               varchar2(10),                          --发票编号
        customerorderno         varchar2(15),                          --订单号码
        subtotal                number(36,2)            default(0),    --合计金额
        valueaddtax             number(36,2)            default(0),    --营业税
        amount                  number(36,2)            default(0),    --总计金额
        accountreceivable       number(36,2)            default(0),    --应收金额
        received                number(36,2)            default(0),    --已收金额
        limitdate               date,                                  --付款截止日期
        createdate              date,
        updatedate              date,
        primary key (id)
);

--出货单明细表
create table tbl_deliverydetail (
        id                      number                     not null,      --主键
        deliveryid              varchar2(12)            not null,      --出货单号
        productid               varchar2(12)            not null,      --产品ID
        salesquantity           number                     default(0),    --出货数量
        salesunitprice          number(18,2),                          --出货单价
        salesamount             number(36,2)           default(0),     --出货总价
        createdate              date,                                  --创建时间
        updatedate              date,                                  --修改时间
        primary key (id)
);


--库存变动主表（库存管理）
create table tbl_changemaster (
        id                      number                     not null,      --主键
        changeid                varchar2(12)            not null,      --出货单号
        siteid                  number                     not null,      --站点ID
        changedate              date,                                  --变动日期
        description             varchar2(255),                         --变动说明
        createdate              date,                                  --创建时间        
        updatedate              date,                                  --修改时间
        primary key (id)
);

--库存变动明细
create table tbl_changedetail (
        id                      number                     not null,      --主键
        changeid                varchar2(12)            not null,      --出货单号
        productid               varchar2(12)            not null,      --产品ID
        changequantity          number                     default(0),    --变动数量
        changeamount            number(36,2)           default(0),     --变动金额
        createdate              date,                                  --创建时间        
        updatedate              date,                                  --修改时间
        primary key (id)
);

--付款冲帐主表（应付账款管理）
create table tbl_accountpayablemaster (
        id                      number                     not null,      --主键
        paymentid               varchar2(12)            not null,      --出货单号
        siteid                  number                     not null,      --站点ID
        paydate                 date                    not null,      --支付日期
        supplierid              varchar2(12)            not null,      --供应商ID
        paycash                 number(36,2)            default(0),    --现金
        paycheck                number(36,2)            default(0),    --支票
        noteno                  varchar2(10),                          --支票号码
        duedate                 date,                                  --到期日
        bankid                  varchar2(12)            not null,      --开票银行
        accountno               varchar2(20),                          --帐号
        discount                number(36,2)            default(0),    --折扣
        remittance              number(36,2)            default(0),    --费用
        prepaid                 number(36,2)            default(0),    --暂付款
        others                  number(36,2)            default(0),    --其他
        payamount               number(36,2)            default(0),    --付款金额
        totalbalance            number(36,2)            default(0),    --实冲金额
        createdate              date,     
        updatedate              date,
        primary key (id)
);

--付款冲帐明细表
create table tbl_accountpayabledetail (
        id                      number                     not null,      --主键
        paymentid               varchar2(12)            not null,      --付款单号
        purchaseid              varchar2(12)            not null,      --进货单单号
        balance                 number(36,2)            default(0),    --本次冲销金额
        createdate              date,                                  --创建时间        
        updatedate              date,                                  --修改时间
        primary key (id)
);

--收款冲帐主表（应收账款管理）
create table tbl_accountreceivablemaster (
        id                      number                     not null,      --主键
        siteid                  number                     not null,      --站点ID
        receiveid               varchar2(12)            not null,      --收款单号
        receivedate             date,                                  --收款日期
        customerid              varchar2(12)            not null,      --客户编号
        receivecash             number(36,2)            default(0),    --现金
        receivecheck            number(36,2)            default(0),    --支票
        noteno                  varchar2(10),                          --支票号码
        duedate                 date,                                  --到期日
        bankid                  varchar2(12)            not null,      --开票银行
        accountno               varchar2(20),                          --帐号
        discount                number(36,2)            default(0),    --折扣
        remittance              number(36,2)            default(0),    --费用
        advance                 number(36,2)            default(0),    --暂收款
        others                  number(36,2)            default(0),    --其他
        receiveamount           number(36,2)            default(0),    --收款金额
        totalbalance            number(36,2)            default(0),    --实冲金额
        createdate              date, 
        updatedate              date,
        primary key (id)
);


--收款冲帐明细表
create table tbl_accountreceivabledetail (
        id                      number                  not null,      --主键
        receiveid               varchar2(12)            not null,      --收款单号
        deliveryid              varchar2(12)            not null,      --出货单单号
        balance                 number(36,2)            default(0),    --本次冲销金额
        createdate              date,                                  --创建时间        
        updatedate              date,                                  --修改时间
        primary key (id)
);