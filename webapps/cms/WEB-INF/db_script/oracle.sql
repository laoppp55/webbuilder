CREATE TABLE TBL_RIGHT (
	RIGHTID		varchar2(30) NOT NULL,
	RIGHTNAME	varchar2(30)  NOT NULL,
	RIGHTCAT	varchar2(30)	 NULL,
	RIGHTDESC	varchar2(100) NULL,
	PRIMARY KEY (RIGHTID) 
);

insert into tbl_right(rightid,rightname,rightcat,rightdesc) values('ColumnManage','栏目管理','ColumnManage','创建、修改和删除栏目');
insert into tbl_right(rightid,rightname,rightcat,rightdesc) values('ModelManage','模板管理','ModelManage','上传、装入、增加、修改和删除模板');
insert into tbl_right(rightid,rightname,rightcat,rightdesc) values('ArticleManage','文章管理','ArticleManage','录入、修改和删除文章');
insert into tbl_right(rightid,rightname,rightcat,rightdesc) values('FileUpload','文件上传','FileUpload','上传文件、删除上传文件');
insert into tbl_right(rightid,rightname,rightcat,rightdesc) values('FileAudit','文件审核','FileAudit','审核文件是否可以向外发布');
insert into tbl_right(rightid,rightname,rightcat,rightdesc) values('Publish','文章发布','Publish','可以向生产环境发布允许发布的文章');
insert into tbl_right(rightid,rightname,rightcat,rightdesc) values('ViewLog','查看LOG分析结果','ViewLog','有权查看LOG分析报告');
insert into tbl_right(rightid,rightname,rightcat,rightdesc) values('UserManage','管理系统用户','UserManage','为用户分配权限和可管理的资源');

CREATE TABLE TBL_ROLE (
	ROLEID		varchar2(30) NOT NULL,
	ROLENAME	varchar2(30) NOT NULL,
	ROLEDESC	varchar2(100) NULL,
	PRIMARY KEY(ROLEID)
);

CREATE TABLE TBL_ROLERIGHT (
	ROLEID		varchar2(30) NOT NULL,
	RIGHTID		varchar2(30) NOT NULL,
	PRIMARY KEY(ROLEID,RIGHTID)
);

CREATE TABLE TBL_GROUP (
	GROUPID		varchar2(30) NOT NULL,
	GROUPNAME	varchar2(30) NOT NULL,
	GROUPDESC	varchar2(100) NULL,
	PRIMARY KEY (GROUPID) 
);

CREATE TABLE TBL_GROUPROLE (
	GROUPID 	varchar2(30) NOT NULL,
	ROLEID		varchar2(30) NOT NULL,
	PRIMARY	KEY(GROUPID,ROLEID) 
);

CREATE TABLE TBL_Members (
  USERID       varchar2(30) NOT NULL, 
  USERPWD      varchar2(8) NOT NULL,
  NICKNAME     varchar2(30) null,
  Email        varchar2(30) NOT NULL,
  Phone        varchar2(30) NULL,
  MobilePhone  varchar2(30) NULL,
  URL          varchar2(50) NULL,
  FilePath     varchar2(50) NULL,
  PRIMARY KEY (USERID)  
);
insert into tbl_members values('admin','!1','超级用户','admin@admin.com','111111','1111','http://www.sohu.com','c:\work');

CREATE TABLE TBL_MEMBERGROUP (
	USERID      varchar2(30) NOT NULL,
	GROUPID     varchar2(30) NOT NULL,
	PRIMARY KEY(USERID,GROUPID)
);

create table tbl_columnuser(
	ColumnEname     varchar2(50)  Not Null , 
	ColumnChname    varchar2(50)  Not null,
	USERID          varchar2(30) NOT NULL,
	columnID 	number(6,0) not NUll,
	PRIMARY KEY(columnID,USERID)
);

Create table TBL_Column(
   ID                          number(12,0)             Not Null Primary key,   --编号,PK
   Code                        varchar2(60)             NULL,                   --栏目编码
   DirName                     varchar2(200)            Null,                   --生成的目录
   OrderID                     Smallint                 Null,                   --显示次序
   ParentID                    number(6,0) Default(0)   Not NUll,               --父编号
   defaultContentTemplateID    number(6,0),                                     --默认的栏目文章模板ID
   defaultIndexTemplateID      number(6,0),                                     --默认的栏目索引模板ID
   Cname                       varchar2(600)             Not Null,              --中文名称
   Ename                       varchar2(600)             Not Null,              --英文名称
   ActiveID                    number(1,0) default(1)   Not Null,               --有效标记   
   Createdate                  date  Null,                                      --创建时间
   LastUpdated                 date  Null,                                      --最后修改时间
   Editor                      varchar2(30) Null                                --最后修改者   
);	
insert into tbl_column values(0,'000','/',0,-1,0,0,'首页','home',1,sysdate,sysdate,'admin');

Create table TBL_Template(
   ID               number(6,0)                       Not Null Primary key,      --编号,PK
   ColumnID         number(6,0)                       Null,                      --所属栏目,FK(栏目)
   IsArticle        number(1,0)                       Default 0 not null,        --0 文章模板  1 栏目模板
   Content          long                              Null,                      --正文，模板正文
   Createdate       date                              Null,                      --创建时间   getdate
   LastUpdated      date                              Null,                      --最后修改时间
   Editor           varchar2(30)                      Null,                      --最后修改者   FK（用户）
   creator          varchar2(30),                                                --模板装入者
   lockstatus       number(1,0),                                                 --模板的锁定状态
   chname           varchar2(50),                                                --栏目中文名称
   defaultTemplate  int                                                          --栏目默认的模板
);

Create table TBL_Article
(
   ID                  number(12,0)          Not Null Primary Key,      --编号PK
   ColumnID            number(6,0)           Not Null,                  --所属栏目,FK(栏目)
   SortID              Smallint              null,                      --排序
   MainTitle           varchar2(500)         Null,                      --标题
   ViceTitle           varchar2(500)         Null,                      --标题
   Summary             varchar2(200)         Null,                      --概要介绍
   Keyword             varchar2(100)         Null,                      --关键字
   Source              varchar2(500)         Null,                      --文章来源
   Content             long                  Null,                      --文章正文
   author              varchar2(50)          Null,                      --文章作者
   PublishTime         date                  Null,                      --页面发布时间
   Createdate          date                  Null,                      --文章输入时间   getdate
   LastUpdated         date                  Null,                      --最后修改时间
   DirName             varchar2(600)          Null,                      --生成的目录
   FileName            varchar2(600)          Null,                      --生成的HTML文件名
   Editor              varchar2(30)          Not Null,                  --最后修改者   FK（用户）   
   status              number(1,0)           Default(1) Not Null,       --是否可用，0-不可用 1-可以使用
   doclevel            number(1,0)           Default(0) Not Null,       --是否重要，0-普通 1-重要
   pubflag             number(1,0)           Default(1) Not Null,       --发布标记，0-被发布 1-需要发布 2-发布失败，重新发布
   auditflag           number(1,0)           Default(0) Not Null,       --审核状态，0-审核完毕 1-在审核中 2-被退稿
   subscriber          number(1,0)           Default(1) not null        --是否发送给订阅者，1-需要发送 0-不发送 2-已经发送
);


--定义审核方式
create table tbl_column_auditing_rules (
   ID                  number(12,0)   Not Null Primary Key,      --编号PK
   columnID            number(6,0)    Not Null,                  --所属栏目,FK(栏目)
   column_audit_rule   varchar2(1000),
   createdate          date,
   lastupdated         date,
   creator             varchar2(30),
   editor              varchar2(30),
   CONSTRAINT fk_auditcolumn_id FOREIGN KEY (columnID) REFERENCES  tbl_column(ID)   
);

create table tbl_article_auditing_rules (
   ID                  number(12,0)       Not Null Primary Key,      --编号PK
   articleID           number(12,0)       Not Null,                  --文章编号fk(references(tbl_article))
   article_audit_rule  varchar2(1000),
   auditing_comments   varchar2(4000),
   auditing_sign_name  varchar2(100),
   createdate          date,
   lastupdated         date,
   creator             varchar2(30),
   editor              varchar2(30),
   CONSTRAINT fk_auditarticle_id FOREIGN KEY (articleID) REFERENCES  tbl_article(ID)   
);

--结束审核方式定义

create table TBL_PublishedArticle (
    ID                number(6,0) not null primary key,
    publishedtime     date,
    publishername     varchar2(50),
    filename          varchar2(300),
    url               varchar2(300),
    publishstatus     number(2,0),
    publishtime       date
);

create table TBL_topic (
    ID                number(6,0) not null primary key,
    author            varchar2(30),
    chname            varchar2(30),
    enname            varchar2(30),
    keyword           varchar2(50),                --关键字最多不超过5个
    description       varchar2(300),
    collist           varchar2(300),
    options           varchar2(5),
    liststyle         number(2,0),
    modelname         varchar2(50),
    createdate        date
);

create table tbl_logsummary (
    ID                     number(20,0) not null primary key,
    websitename            varchar2(100),
    pvnmuber               number(20,0),
    usersessions           number(20,0),
    averagesessionlength   varchar2(20),
    uniqueusers            number(20,0),
    createdate             date
);

create table tbl_logdetail (
    ID                     number(20,0) not null primary key,
    websiteid              number(20,0),
    urlname                varchar2(300),
    pvnmuber               number(20,0),
    usersessions           number(20,0),
    createdate             date,
    CONSTRAINT fk_websiteid FOREIGN KEY (websiteid) REFERENCES  tbl_logsummary(ID)
);

create table TBL_EnChPath (
    chinesPath             varchar2(250),
    englishpath            varchar2(250)
);

create table cms_siteinfo(
    id                    number(5,0),
    siteIP                varchar2(100),
    docrootpath           varchar2(100) not null,
    ftpuser               varchar2(100),
    ftppasswd             varchar2(100),
    domainname            varchar2(255) not null,
    publishway            number(1,0),
    primary               key(id)
);

CREATE VIEW VW_USERRIGHT AS SELECT TBL_MEMBERGROUP.USERID, TBL_RIGHT.RIGHTID,TBL_RIGHT.RIGHTCAT
FROM TBL_MEMBERGROUP ,TBL_GROUPROLE, TBL_ROLERIGHT,TBL_RIGHT 
WHERE (TBL_MEMBERGROUP.GROUPID = TBL_GROUPROLE.GROUPID) AND (TBL_GROUPROLE.ROLEID = TBL_ROLERIGHT.ROLEID) 
AND (TBL_ROLERIGHT.RIGHTID = TBL_RIGHT.RIGHTID);