create table tbl_right (
	rightid			smallint		not null,     --权限名称ID
	rightname		varchar(30)		not null,     --权限中文名
	rightcat		varchar(30),                          --权限类别代码
	rightdesc		varchar(100),                         --权限描述
        primary key (rightid)
);

create table tbl_log (
	id			int			not null,    --唯一ID
	articleid		int			not null,    --文章编号
	siteid			smallint		not null,    --站点ID
	columnid		int					not null,		--栏目ID
	editor			varchar(30)		not null,    --执行动作的编辑
	acttype			tinyint			not null,    --动作类型
	acttime			datetime		not null,    --动作时间（yyyy-mm-dd hh24:mi:ss形式）
	maintitle		varchar(600),	 --执行操作的文章标题
	createdate	datetime,			--动作操作时间（yyyy-mm-dd形式）
        primary key (id)
);

create table tbl_members (
        userid       		varchar(30)   		not null,   --用户名
        siteid       		smallint      		not null,   --站点编码
        userpwd      		varchar(32)   		not null,   --用户口令
        nickname     		varchar(30)		not null,   --用户真实姓名
        createarticles	int						not null default 0,		--用户创建文章数
        editarticles		int						not null default 0,		--用户修改文章数
        deletearticles	int						not null default 0,		--用户删除文章数
        primary key (userid)
);

create table tbl_members_rights (
        userid          	varchar(30)    		not null,   --用户ID
        columnid        	int			not null,   --栏目ID
        rightid         	smallint		not null    --权限ID
);

create table tbl_group_rights (   	
	groupid			int          		not null,   --用户组ID
        columnid        	int,                                --栏目ID
        rightid        	 	smallint                            --权限ID
);

create table tbl_group_members (
	userid      		varchar(30)    		not null,   --用户ID
	groupid     		int          		not null    --用户组ID
);

create table tbl_group (
	groupid     		int          		not null,    --用户组ID
	siteid      		smallint       		not null,    --站点ID
	groupname   		varchar(20)    		not null,    --用户组名称
	groupdesc   		varchar(100),                        --用户组描述
        primary key (groupid)
);

create table tbl_article_auditing_info (
	id              	int          		not null,    --唯一ID
	articleid       	int          		not null,    --被审核的文章ID
	sign            	varchar(30)    		not null,    --审核人
	comments        	varchar(500),                        --审核人意见
	status          	tinyint       		not null,    --状态  0--通过  1--未通过
	backto          	varchar(30),                         --退回人
	createdate      	datetime                not null,    --创建时间
        primary key (id)
);

create table tbl_column_auditing_rules (
	id                   	int             	not null,    --唯一ID
	columnid             	int             	not null,    --栏目ID
	column_audit_rule    	varchar(300)		not null,    --审核规则
	createdate           	datetime		not null,    --创建时间
	lastupdated          	datetime		not null,    --修改时间
	creator              	varchar(30)		not null,    --创建人
	editor               	varchar(30)		not null,    --编辑
        primary key (id)
);

create table tbl_article (
	id                   	int             	not null,    --唯一ID
	columnid             	int             	not null,    --栏目ID
	sortid               	int,                                 --文章序号
	maintitle            	varchar(600)   		not null,    --主标题
	vicetitle            	varchar(600),                        --副标题
	summary              	varchar(1000),                       --描述
	keyword              	varchar(200),                        --关键字
	source               	varchar(600),                        --来源
	content              	text,                                --内容
	emptycontentflag     	tinyint			not null,    --文章是否为空 0-非空  2-空
	author               	varchar(600),                        --作者
	articleversion       	int,                                 --文章版本号
	publishtime          	datetime		not null,    --发布时间
	createdate           	datetime		not null,    --创建时间
	lastupdated          	datetime		not null,    --最后修改时间
	dirname              	varchar(200)		not null,    --目录
	filename             	varchar(60),                         --文件名
	editor               	varchar(30)        	not null,    --编辑
	status               	tinyint   		not null,    --状态   1--可用  0--停用
	doclevel             	int                 	not null,    --文章权重
	vicedoclevel		int,                                 --文章次权重
	pubflag              	tinyint   		not null,    --是否发布  1--未发布需要发布  0--已发布  2-发布失败
	auditflag            	tinyint 		not null,    --是否审核  0--通过    1--在审
	subscriber           	tinyint   		not null,    --是否订阅  0--不发    1--发送
	lockstatus           	tinyint 		not null,    --是否锁定
	lockeditor           	varchar(30),                         --锁定人
	auditor              	varchar(200),                        --文章审核人
	ispublished          	tinyint 		not null,    --是否曾经被发布过 0-未被发布过 1-已被发布过
	relatedartid         	varchar(40),                         --文章的相关文章 
	indexflag            	tinyint			not null,    --文章是否被索引
	isjoinrss		tinyint			not null,    --是否加入到RSS
	clicknum		int			not null,    --点击次数（前台使用）
	referID			int			not null,    --（未用）
	modelID			int			not null,    --（未用）
	saleprice            	float,                               --销售价格（用于电子商务）
	inprice              	float,                               --进价（用于电子商务）
	marketprice          	float,                               --市场价格（用于电子商务）
	stocknum             	int,                                 --库存数量（用于电子商务） 
	weight               	int,                                 --重量（用于电子商务）
	brand                	varchar(50),                         --品牌（用于电子商务）
	pic                  	varchar(30),                         --图片（用于电子商务）
	bigpic               	varchar(30),                         --大图片（用于电子商务）
	articlepic		varchar(600),
	urltype				int	not null default 0,											 --是否使用自定义文章链接 0-否，1-是
	defineurl			varchar(200),															 --自定义的文章链接地址
	wordsnum			int not null default 0,											 --文章内容字数（用于做统计）
        primary key (id)
);

create table tbl_article_extendattr (
	id                   	int             	not null,    --唯一ID
	articleid            	int             	not null,    --文章ID
	ename                	varchar(50)		not null,    --英文名称
	type                 	tinyint			not null,    --类型
	textvalue            	text,                                --保存文本类型数据
	stringvalue          	varchar(4000),                       --保存字符串型数据
	numericvalue         	int,                                 --保存数值型数据 
	floatvalue           	float,                               --保存浮点型数据
        primary key (id),
	CONSTRAINT fk_articleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE        
);

create table tbl_template (
	id                   	int    			not null,    --唯一ID
	columnid             	int    			not null,    --栏目ID
	isarticle           	tinyint   		not null,    --模板类型
	content              	text,                                --模板内容
	createdate           	datetime		not null,    --创建日期
	lastupdated          	datetime		not null,    --最后修改日期
	editor               	varchar(30)		not null,    --编辑
	creator              	varchar(30)		not null,    --创建人
	status               	tinyint  		not null,    --状态
	relatedcolumnid      	varchar(500),                        --相关栏目ID串
	modelversion         	int,                                 --版本号
	lockstatus           	tinyint			not null,    --锁定状态
	lockeditor           	varchar(30),                         --锁定人
	chname               	varchar(50)		not null,    --模板中文名称
	defaulttemplate      	tinyint			not null,    --是否是默认模板
	templatename         	varchar(200)		not null,    --模板英文名称
	refermodelid         	int      		not null,    --是否是被引用的模板
	isIncluded		tinyint			not null,    --是否是包含文件
        primary key (id)
);

create table tbl_column (
	id                   	int                  	not null,     --唯一栏目ID
	siteid               	smallint               	not null,     --站点ID
	parentid             	int     		not null,     --父栏目ID
	orderid              	int                  	not null,     --顺序号
	cname                	varchar(50)        	not null,     --中文名称
	ename                	varchar(50)        	not null,     --英文名称
	dirname              	varchar(200)          	not null,     --目录名
	editor               	varchar(30)            	not null,     --编辑
	extname              	varchar(10)            	not null,     --文件扩展名
	createdate           	datetime                not null,     --创建日期
	lastupdated          	datetime		not null,     --最后修改日期
	isdefineattr         	tinyint  		not null,     --是否定义扩展属性
 	hasarticlemodel      	tinyint  		not null,     --是否有文章模板
	isaudited            	tinyint  		not null,     --是否需要审核
	isproduct            	tinyint    		not null,     --是否是商品栏目
	ispublishmore        	tinyint    		not null,     --是否多文章模板发布
	languagetype         	tinyint    		not null,     --语言类型
	columndesc           	varchar(200),                         --栏目描述
	xmltemplate          	varchar(4000),                        --扩展属性定义
	contentshowtype				tinyint				not null,								--页面展示方式WEB或WEB+WAP
	isrss									tinyint	default 0 not null,						--定义本栏目是否发布RSS
	getRssArticleTime			int,																--定义RSS发布的时间段
	archivingrules				tinyint default 0 not null,						--定义本栏目设定的归档规则
        primary key (id)
);

create table tbl_mark (
	id                   	int             	not null,
	columnid             	int             	not null,
	siteid               	smallint		not null,
	content              	varchar(4000)		not null,
	marktype             	tinyint			not null,
	notes                	varchar(200),
	lockeditor           	varchar(30),
	lockflag             	tinyint			not null,
	pubflag              	tinyint			not null,
	innerhtmlflag        	tinyint			not null,
	formatfilenum        	int,
	createdate           	datetime		not null,
	updatedate           	datetime		not null,
	publishtime         	datetime		not null,
	chinesename          	varchar(40)		not null,
	relatedcolumnid      	varchar(500),
        primary key (id)
);

create table tbl_viewfile (   	
    	id		      	int             	not null,
			siteid                	smallint		not null,
    	type                  	smallint		not null,
    	content               	text			not null,
    	chinesename           	varchar(50)		not null,
    	editor                	varchar(30)		not null,
    	lockflag              	tinyint			not null,
    	notes                 	varchar(300),
    	createdate            	datetime		not null,
    	updatedate            	datetime		not null,
        primary key (id)
);

create table tbl_siteipinfo(
    	id                    	int              	not null,
    	siteid                	smallint              	not null,
    	siteip                	varchar(60)     	not null,
    	sitename              	varchar(50)		not null,
    	docpath               	varchar(100)     	not null,
    	ftpuser               	varchar(50),
    	ftppasswd             	varchar(50),
    	publishway            	tinyint			not null,
    	status                	tinyint			not null,
        primary key (id)
);

create table tbl_siteinfo (
	siteid               	smallint               	not null,
	sitename             	varchar(50)    		not null,
	imagesdir            	tinyint          	not null,
	cssjsdir							tinyint						not null,
	tcflag               	tinyint			not null,
	wapflag								tinyint			default 0 not null,
	pubflag              	tinyint          	not null,
	bindflag             	tinyint          	not null,
	createdate           	datetime		not null,
	berefered		tinyint			not null,
	config			varchar(200),
        primary key (siteid)
);

create table tbl_referred_article (
	articleid         int       not null,								--被推荐的文章id
	columnid 	      	int 			not null,								--文章被推荐到的目的栏目id
	orders						int		default 0	not null,					--显示选中文章排序的序号
	createdate				datetime,													--文章推荐日期
	title							varchar(600)											--文章推荐后的标题
);

create table tbl_publish_queue (
	id            		int               	not null,
	siteid	      		smallint               	not null,
	type 	      		tinyint 		not null
);

create table tbl_new_publish_queue (
	id         		int 			not null,
	siteid     		smallint		not null,
	targetid   		int 			not null,
	type       		tinyint,		--1文章,2栏目模板,3首页模板,4标记
	status     		tinyint,
	createdate    		datetime,
	publishdate   		datetime,
	uniqueid   		varchar(100),
        primary key (id)
);

create table tbl_picture
(
  ID         int,
  SITEID     int,
  COLUMNID   int,
  WIDTH      int not null,
  HEIGHT     int not null,
  PICSIZE    int not null,
  PICNAME    varchar(200),
  IMGURL     varchar(255) not null,
  CREATEDATE datetime default getdate() not null,
  NOTES			 varchar(500)
);

create table tbl_relatedartids
(
  ID         int not null,
  cname			 varchar(100) not null,
  articleid	 int not null,
  type       int,
  joinedid     int
);

create table tbl_article_keyword
(
  ID         int not null,
  siteid		 int not null,
  columnid	 int not null,
  keyword    varchar(200),
  url				 varchar(200),
  primary key (id)
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

insert into tbl_members values('admin',-1,'4346D362F67C89D7AB4E28732A1B1CE8','系统管理员',0,0,0);
insert into tbl_column values(0,0,-1,0,'首页','home','/','admin','html',getdate(),getdate(),0,0,0,0,0,0,0,'','',0,0,0);
