create tablespace sales
datafile 'C:\oracle\oradata\orcl11g\sales.dbf' size 200M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user salesdbadmin identified by qazwsxokm default tablespace sales;
grant resource,connect,dba to salesdbadmin;


create tablespace egwdb
datafile 'C:\oracle\oradata\orcl11g\egwdb.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user egwdbadmin identified by qazwsxokm default tablespace egwdb;
grant resource,connect,dba to egwdbadmin;


create tablespace zmhddb
datafile 'C:\oracle11\oradata\orcl11g\zmhddb.dbf' size 5000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user zmhddbadmin identified by qazwsxokm default tablespace zmhddb;
grant resource,connect,dba to zmhddb;


create tablespace bucgdx
datafile 'C:\oracle\oradata\orcl11g\bucgdx.dbf' size 300M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user bucgdxdbadmin identified by qazwsxokm default tablespace bucgdx;
grant resource,connect,dba to bucgdxdbadmin;


create tablespace bucg
datafile 'C:\oracle\oradata\orcl11g\bucg.dbf' size 300M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user bucgdbadmin identified by qazwsxokm default tablespace bucg;
grant resource,connect,dba to bucgdbadmin;


create tablespace bjsjsdb
datafile 'C:\oracle\oradata\orcl11g\bjsjsdb.dbf' size 2000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user bjsjsdbadmin identified by qazwsxokm default tablespace bjsjsdb;
grant resource,connect,dba to bjsjsdbadmin;


create tablespace db221
datafile 'E:\app\Administrator\oradata\orcl\db221.dbf' size 5000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user db221admin identified by qazwsxokm default tablespace db221;
grant resource,connect,dba to db221admin;


create tablespace buyorderdb
datafile '/usr/local/oracle/oradata/orcl10g/buyorderdb.dbf' size 200M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user orderdbadmin identified by qazwsxokm default tablespace buyorderdb;
grant resource,connect,dba to orderdbadmin;



create tablespace tianyiyun
datafile 'D:\APP\ADMINISTRATOR\ORADATA\ORCL\tianyiyun.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user tyydbadmin identified by qazwsxokm default tablespace tianyiyun;
grant resource,connect,dba to tyydbadmin;
grant create session to tyydbadmin;            //授予zhangsan用户创建session的权限，即登陆权限
grant unlimited tablespace to tyydbadmin;      //授予zhangsan用户使用表空间的权限
grant dba to tyydbadmin;
grant IMP_FULL_DATABASE to tyydbadmin;
grant EXP_FULL_DATABASE to tyydbadmin;



create tablespace qbpricedb
datafile '/usr/local/oracle/oradata/orcl10g/qbpricedb.dbf' size 200M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user qbpricedbadmin identified by qazwsxokm default tablespace qbpricedb;
grant resource,connect,dba to qbpricedbadmin;



create tablespace buyresultdb
datafile '/usr/local/oracle/oradata/orcl10g/buyresultdb.dbf' size 200M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user resultdbadmin identified by qazwsxokm default tablespace buyresultdb;
grant resource,connect,dba to resultdbadmin;



create tablespace contractdb
datafile '/usr/local/oracle/oradata/orcl10g/contractdb.dbf' size 200M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user contractdbadmin identified by qazwsxokm default tablespace contractdb;
grant resource,connect,dba to contractdbadmin;

create tablespace sinoec
datafile 'C:\oracle11\oradata\orcl11g\sinoec.dbf' size 100M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user sinodbadmin identified by qazwsxokm default tablespace sinoec;
grant resource,connect,dba to sinodbadmin;


create tablespace bidding
datafile '/data/oradata1/bidding.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user biddbadmin identified by qazwsxokm default tablespace bidding;
grant resource,connect,dba to biddbadmin;

create tablespace cbcsd
datafile 'C:\oracle11\oradata\orcl11g\cbcsd.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user cbcsd164dbadmin identified by qazwsxokm default tablespace cbcsd;
grant resource,connect,dba to cbcsd164dbadmin;


create tablespace coosite
datafile 'C:\oracle\oradata\orcl11g\coosite.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user coositedb identified by coositedbpass2009 default tablespace coosite;
grant resource,connect,dba to coositedb;

create tablespace gugulxdb
datafile 'C:\oracle11\oradata\orcl11g\gugulxdb.dbf' size 3000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user gugulxdbadmin identified by qazwsxokm default tablespace gugulxdb;
grant resource,connect,dba to gugulxdbadmin;


create tablespace nsbd
datafile 'C:\oracle11\oradata\oracle11g\nsbd.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user nsbddbadmin identified by qazwsxokm default tablespace nsbd;
grant resource,connect,dba to nsbddbadmin;

create tablespace nsbd0904
datafile 'C:\oracle11\oradata\orcl11g\nsbd0904.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user nsbd0904dbadmin identified by qazwsxokm default tablespace nsbd0904;
grant resource,connect,dba to nsbd0904dbadmin;


create tablespace supplier
datafile 'C:\oracle11\oradata\orcl11g\supplier.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user suppdbadmin identified by qazwsxokm default tablespace supplier;
grant resource,connect,dba to suppdbadmin;

create tablespace products
datafile '/data/oradata1/proddb.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user proddbadmin identified by qazwsxokm default tablespace products;
grant resource,connect,dba to proddbadmin;

DROP MATERIALIZED VIEW MV_SUPP_ZHPF;
alter database datafile 'D:\app\oradata\wzec\system01.dbf' resize 30000m;

create tablespace scmdb
datafile 'D:\app\oradata\wzec\scmdb.dbf' size 5000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create tablespace DATASPACE
datafile 'C:\oracle11\oradata\oracle11g\dataspace.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user scmdbadmin identified by qazwsxokm default tablespace scmdb;
grant resource,connect,dba to scmdbadmin;

create user interxtpt identified by qazwsxokm default tablespace scmdb;
grant resource,connect,dba to interxtpt;

create user interbw identified by qazwsxokm default tablespace scmdb;
grant resource,connect,dba to interbw;

create user interface identified by qazwsxokm default tablespace scmdb;
grant resource,connect,dba to interface;

create user intermdm identified by qazwsxokm default tablespace scmdb;
grant resource,connect,dba to intermdm;

create user interprocess identified by qazwsxokm default tablespace scmdb;
grant resource,connect,dba to interprocess;

create user intersupp identified by qazwsxokm default tablespace scmdb;
grant resource,connect,dba to intersupp;

create user interstocked identified by qazwsxokm default tablespace scmdb;
grant resource,connect,dba to interstocked;

create user ecbidding identified by qazwsxokm default tablespace scmdb;
grant resource,connect,dba to ecbidding;


create tablespace ebidding
datafile 'D:\app\oradata\wzec\ebidding.dbf' size 1000M
default storage (initial 500K 
Next 500K
maxextents unlimited
pctincrease 10);

create user ebiddbadmin identified by qazwsxokm default tablespace ebidding;
grant resource,connect,dba to ebiddbadmin;

--图文直播的定义表
create table TBL_broadcast
(
  id               int                not null,           --直播定义表主键
  siteid           int                not null,           --直播的站点ID
  title            VARCHAR2(200),                         --直播的标题
  brief            varchar2(3000),                        --直播内容描述
  picurl           varchar2(250),                         --直播图片URL
  createdate       date,                                  --直播内容创建日期
  createuser       varchar2(200),                         --直播的创建者
  primary key (id)
);


--图文直播的内容表
create table TBL_broadcastcontent
(
  id               int                not null,           --直播内容表主键
  bcid             int                not null,           --直播定义的主键
  siteid           int                not null,           --直播的站点ID
  title            VARCHAR2(200),                         --直播的标题
  brief            varchar2(3000),                        --直播内容描述
  contenturl       varchar2(250),                         --直播图片文件URL或者多媒体文件URL
  contenttype      smallint,                              --0表示图片   1表示多媒体文件
  createdate       date,                                  --直播内容创建日期
  createuser       varchar2(200),                         --直播的创建者
  primary key (id)
);

create table TBL_SEARCHWORD
(
  SITEID     NUMBER,
  IP         VARCHAR2(20),
  KEYWORD    VARCHAR2(200),
  CREATEDATE DATE
);

##农家院房型表
drop table cs_room_info;
create table cs_room_info(
	id                       int               not null,                     --标识符,农家院户型ID
	siteid                   int               not null,	                 --农家院注册的站点ID
	room_name	         varchar2(100)     not null,                     --房间的名称或者编号
	catlog_code              char(8)           default '00000000',           --分类编码，暂时不用
	room_type 	         smallint 	   default 2,                    --1--单人房 2--标准双人房  3--三人房   4--四人房  5--五人房  6--火炕房
        room_num                 int               default 1,                    --该类型房的数量
	room_way                 smallint,                                       --房间朝向，1-东、2-西、3-南、4-北、5-东南、6-东北、7-西南、8-西北
	room_size                int,                                            --房间面积   
	init_price               number(10,2),                                   --起始价格
        bed_num                  int               default 1,                    --房间内床位数量
        bed_type                 int,                                            --床型，1--单人床（1.2米宽）2--单人床（0.9米宽）3--单人床（1.05米宽） 4--双人床（1.35米宽） 5--双人床（1.5米宽） 6--双人床（1.8米宽）7--火炕（5米宽）
        floor                    varchar(20),                                    --楼层，1层、2层、2层（有电梯）、3层、3层（有电梯）、4层、4层（有电梯）、5层、5层（有电梯）6层、6层（有电梯）
	toilet                   smallint          default 1,                    --卫生间数量
        tv                       varchar2(100),                                  --电视设施描述
        airconditioner           varchar2(100),                                  --空调设施描述
	bathroom                 varchar2(100),                                  --洗浴设施描述
	bedclothes               varchar2(200),                                  --床上用品描述
        amenities                varchar2(500),                                  --便利设施，电视机，独立卫浴，无线网络、行李寄存、矿泉水、电冰箱、24小时热水、有线网络、免费停车位、空调、淋浴、交通便利、可接待家庭/孩子、热水壶、热水浴缸、免费接送、适合举办活动、吹风机、毛巾、免费脚踏车、拖鞋、代订门票、茶包、一次性盥洗用品、代订包车、咖啡包
	specialservice           varchar2(50),                                   --早餐服务、接送服务
	delflag                  smallint         default 0,                     --信息被删除标示，0--使用  1--删除
	primary key (id)
);

##户型图片表
drop table cs_room_pic；
create table cs_room_pic(
	id                       number            not null,                    --标识符
	room_id                  int               not null,                    --农家院的户型ID
	pic_url		 	 varchar2(100)     not null,                    --图片访问的url
	description		 varchar2(500),                                 --图片的描述信息
	title			 varchar2(100),                                 --图片标题
	primary key (id)
);

##时间价格表
drop table cs_date_price;
create table cs_date_price(
	id                       int               not null,                    --标识符
	room_id			 int               not null,                    --农家院户型ID
	room_price		 number(9,2)       not null,                    --户型价格     
	roomspecialprice         number(9,2)       not null,                    --户型特价
	roomlongtimeprice        number(9,2)       not null,                    --户型长租价格
	bed_price		 number(9,2)       not null,                    --户型内每个床的价格
	bedspecialprice          number(9,2)       not null,                    --户型内每个床特价
	thedate		         date,                                          --价格日期
	primary key (id)
);

##操作日志
drop table cs_operationlog;
create table cs_operationlog(
       id                        number            not null,                    --标识符
       cata_date                 date,                                          --操作日期
       opid                      int               not null,                    --操作标识
       description               varchar2(500),                                 --操作描述
       editor                    varchar2(100)     not null,                    --操作用户
       primary key (id)
);

##农家乐预定表
drop table cs_reserve;
create table cs_reserve(
       id                        number            not null,                    --标识符
       siteid                    int               not null,                    --文章ID
       usedate                   date,                                          --使用日期
       numember                  smallint,                                      --份数
       name     		 varchar2(30),		                        --姓名
       mphone                    varchar2(12),                                  --手机号码
       lxrname     		 varchar2(30),		                        --联系人姓名
       lxrmphone                 varchar2(12),                                  --联系人手机号码
       lxrphone                  varchar2(12),                                  --联系人固定电话
       email                     varchar2(50),                                  --电子邮件
       remarks                   varchar2(500),                                 --备注
       createdate                date,                                          --创建时间
       ruzhu                     smallint,                                      --是否入住  否-0  是-1
       rzdate                    date,                                          --入住日期
       tfdate                    date,                                          --退房日期
       rznumember                smallint,                                      --入住人数
       rzroom                    smallint,                                      --房型
       rzprice		         number(9,2),                                   --费用
       primary key (id)
);

--商品分类编码
create table tbl_wzclass
(
       id                number                not null,                  --主键ID
       customerid        number                not null,                  --物料编码的拥有者     0-表示系统定义的物资分类
       name              varchar2(200)         not null,                  --编码或者分类名称
       brief             varchar2(500),                                   --物资分类描述
       unit              varchar2(10),                                    --本类物资的计量单位
       keyword           varchar2(20),                                    --本类物资的关键字
       code              varchar2(100)         not null,                  --编码
       pcode             varchar2(100)         not null,                  --父编码
       orderid           number,                                          --排序
       valid             number                default 0,                 --物资编码的状态     1--使用   0--停用
       delflag           number                default 0,                 --删除标识，         0--使用   1--删除
       creator           varchar2(50),                                    --创建人
       createdate        date                  default sysdate,           --创建时间
       createcomp        number,                                          --创建者所在的公司
       editor            varchar2(50),                                    --修改人
       lastupdate        date,                                            --最后修改时间
       updatecomp        number,                                          --最后修改人所在公司 
       primary  key(id)
);

--物资分类模板信息
create table tbl_wzclass_template
(
       id                number                not null,                  --主键ID
       classid           number                not null,                  --物资分类主键ID
       status            number                default 0,                 --是否停用    0--使用   1--停用
       delflag           number                default 0,                 --是否删除    0--使用   1--删除
       templatexml       clob,                                            --模板定义的XML字符串
       customerid        number                not null,                  --物料编码的拥有者
       creator           varchar2(50),                                    --创建人
       createdate        date                  default sysdate,           --创建时间
       createcomp        number,                                          --创建者所在的公司
       editor            varchar2(50),                                    --修改人
       lastupdate        date,                                            --最后修改时间
       updatecomp        number,                                          --最后修改人所在公司 
       primary key(id)
);

--物资明细信息
create table tbl_wzdetail
(
       id                number                not null,                  --主键ID
       classid           number                not null,                  --物资分类主键ID
       wzname            varchar2(200)         not null,                  --物资名称
       wzcode            varchar2(21),                                    --物资编码明细
       unit              varchar2(10),                                    --物资单位
       customerid        number                not null,                  --物料编码的拥有者
       status            number                default 0,                 --物资上架销售
       delflag           number                default 0,                 --物资是否被删除   0--正常  1--删除
       creator           varchar2(50),                                    --创建人
       createdate        date                  default sysdate,           --创建时间
       createcomp        number,                                          --创建者所在的公司
       editor            varchar2(50),                                    --修改人
       lastupdate        date,                                            --最后修改时间
       updatecomp        number,                                          --最后修改人所在公司 
       primary key(id)
);

--物资明细信息属性表
create table tbl_wzdetail_part
(
       id                number                not null,                  --主键ID
       classid           number                not null,                  --物资分类主键ID
       proptype          number                default 0,                 --特征值      1-材质  2-
       ename             varchar2(200)         not null,                  --物资属性信息的英文名称
       type              number                not null,                  --物资属性的数据类型
       textvalue         clob,                                            --存储大文本属性值
       stringvalue       varchar2(4000),                                  --存储字符串属性值       
       minintvalue       number,                                          --存储整数型区间最小值，或者是存储整数类型值
       maxintvalue       number,                                          --存储整数型区间最大值
       minfloatvalue     number,                                          --存储浮点型区间最小值，或者是存储浮点数类型值
       maxfloatvalue     number,                                          --存储浮点型区间最大值
       creator           varchar2(50),                                    --创建人
       createdate        date                  default sysdate,           --创建时间
       createcomp        number,                                          --创建者所在的公司
       editor            varchar2(50),                                    --修改人
       lastupdate        date,                                            --最后修改时间
       updatecomp        number,                                          --最后修改人所在公司 
       primary key(id)
);


--物资服务的区域信息
create table tbl_wzservicearea
(
       id                number                not null,                  --主键ID
       classid           number                not null,                  --物资分类主键ID
       wzid              number                not null,                  --物资编码
       areaid            number                not null,                  --物资服务的区域编码
       status            number                default 0,                 --物资上架销售
       customerid        number                not null,                  --物料编码的拥有者
       delflag           number                default 0,                 --物资是否被删除   0--正常  1--删除
       creator           varchar2(50),                                    --创建人
       createdate        date                  default sysdate,           --创建时间
       createcomp        number,                                          --创建者所在的公司
       editor            varchar2(50),                                    --修改人
       lastupdate        date,                                            --最后修改时间
       updatecomp        number,                                          --最后修改人所在公司 
       primary key(id)
);

--物资图片表
create table tbl_wzpictures
(
       id                number                not null,                  --主键ID
       classid           number                not null,                  --物资分类主键ID
       wzid              number                not null,                  --物资编码
       picurl            varchar2(500)         not null,                  --物资图片URL
       filename          varchar2(100),                                   --物资图片文件名称，只接受jpg,jpeg,png格式图片
       sfilename         varchar2(100),                                   --物资图片上传时的原始名称
       customerid        number                not null,                  --物料编码的拥有者
       delflag           number                default 0,                 --物资图片是否被删除   0--正常  1--删除
       creator           varchar2(50),                                    --创建人
       createdate        date                  default sysdate,           --创建时间
       createcomp        number,                                          --创建者所在的公司
       editor            varchar2(50),                                    --修改人
       lastupdate        date,                                            --最后修改时间
       updatecomp        number,                                          --最后修改人所在公司 
       primary key(id)
);

--物资附件表
create table tbl_wzattachements
(
       id                number                not null,                  --主键ID
       classid           number                not null,                  --物资分类主键ID
       wzid              number                not null,                  --物资编码
       attachementurl    varchar2(500)         not null,                  --物资附件URL
       filename          varchar2(100),                                   --物资附件文件名称，只接受PDF、DOC、DOCX、PPT、PPTX格式文件
       sfilename         varchar2(100),                                   --物资附件上传时的原始名称
       customerid        number                not null,                  --物料编码的拥有者
       delflag           number                default 0,                 --物资附件是否被删除   0--正常  1--删除
       creator           varchar2(50),                                    --创建人
       createdate        date                  default sysdate,           --创建时间
       createcomp        number,                                          --创建者所在的公司
       editor            varchar2(50),                                    --修改人
       lastupdate        date,                                            --最后修改时间
       updatecomp        number,                                          --最后修改人所在公司 
       primary key(id)
);

create table tbl_sites_number
(
	ipaddress		varchar2(15)		not null,         --内网IP地址
	sitesnum		number,                                   --配置站点数量
	hashcode                number,                                   --HASH编码，唯一识别该站点
        wwwip                   varchar2(15),                             --对外的IP地址
        docroot                 varchar2(500),                            --网站根目录
        ftpuser                 varchar2(500),                            --网站FTP账号
        ftppasswd               varchar2(500),                            --网站FTP口令
        flag                    number,                                   --网站是否对外服务，1对外服务，0停止对外服务
        privateip               varchar2(15)                              --私网IP地址
)

insert into tbl_sites_number(ipaddress,sitesnum,hashcode) values('127.0.0.1',18,0);

create table tbl_right (
	rightid			number(3)		not null,
	rightname		varchar2(50)		not null,
	rightcat		varchar2(50),
	rightdesc		varchar2(300),
        primary key (rightid)
);

insert into tbl_right values(1,'栏目管理','CM','栏目管理');
insert into tbl_right values(2,'模板管理','MM','模板管理');
insert into tbl_right values(3,'文章管理','AM','文章编辑');
insert into tbl_right values(4,'上传文件','FU','上传文件');
insert into tbl_right values(5,'审核文件','FA','审核文件');
insert into tbl_right values(6,'发布全部文章','PWA','发布全部文章');
insert into tbl_right values(7,'移动文章','MA','移动文章');
insert into tbl_right values(8,'发布个人文章','PPA','发布个人文章');
insert into tbl_right values(9,'信息提报','XXTB','负责向系统提报文章');
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
insert into tbl_right values(100,'订单查询','ddcx','订单查询');
insert into tbl_right values(101,'订单导出','zcyhcx','订单导出');
insert into tbl_right values(102,'银行对账','yhdz','银行对账');
insert into tbl_right values(103,'支付查询','zfcx','支付查询');
insert into tbl_right values(104,'注册用户查询','zcyhcx','注册用户查询');
insert into tbl_right values(105,'注册用户导出','zcyhdc','注册用户导出');
insert into tbl_right values(106,'导出本单位及下属单位订单','dcbdjxsdwdd','导出本单位及下属单位订单');


create table tbl_role (
	roleid			number(2)		not null,
	rolename		varchar2(30)		not null,
	rolecat		        varchar2(30),
	roledesc		varchar2(100),
        primary key (roleid)
);

insert into tbl_role values(0,'信息员','XXY','负责向网站录入信息');
insert into tbl_role values(1,'部门领导','BML','录入信息的审核');
insert into tbl_role values(2,'主管领导','ZGL','录入信息的审核');
insert into tbl_role values(3,'系统管理员','SM','超级系统管理员');

create table tbl_log (
	id			number			not null,         --主键
	articleid		number			not null,         --文章ID
	siteid			number     		not null,         --站点ID
	editor			varchar2(30)		not null,         --编辑人员
	acttype			number(1)		not null,         --活动类型，1增加、2修改、3删除
	acttime			date			not null,         --活动时间
	columnid		number			not null,         --栏目ID
	deptid                  number,                                   --部门ID
	companyid               number,                                   --公司ID
	maintitle		varchar2(600)	not null,                 --标题
	createdate	        date,                                     --创建时间                 
        primary key (id)
);

create table tbl_sjs_log (
	id			number			not null,               --主键
	siteid			number     		not null,               --站点ID           
	sitename		varchar2(50)		not null,               --站点名称
        columnid                number,                                         --栏目ID
        columnname              varchar2(50),                                   --栏目名称
	articleid		number			not null,               --文章ID
	maintitle		varchar2(600)	        not null,               --文章中文标题
        articleurl              varchar2(300),                                  --文章URL
        operationtype           number(1),                                      --文章操作类型    1-增加   2-修改 3-删除 
        editor                  varchar2(50),                                   --文章作者
	createdate	        date,                                           --文章录入日期
        updatedate              date,                                           --文章修改日期
        deletedate              date,                                           --文章删除日期
        primary key (id)
);

create table tbl_members (
        id                      number                  not null,      --主键
        userid       		varchar2(30)   		not null,      --用户名
	mmuserid                varchar2(32),                          
        siteid       		number      		not null,      --站点ID
        userpwd      		varchar2(32)   		not null,      --用户口令
	textpwd                 varchar2(50)   		not null,      --用户明文口令
        nickname     		varchar2(30)		not null,      --用户真实姓名
        email                   varchar2(50),                          --电子邮件地址
        mphone                  varchar2(12),                          --手机号
        phone                   varchar2(12),                          --联系电话        
	sex                     smallint                default(0),    --0表示男 1表示女
        country                 varchar2(30),                          --国家
        province                varchar2(50),                          --省
        city                    varchar2(50),                          --市
        zone                    varchar2(50),                          --区县
        jiedao                  varchar2(50),                          --街道
        shequ                   varchar2(50),                          --社区
        area                    varchar2(50),                          --工作领域
	postcode                varchar2(6),                           --邮政编码
        birthdate               date,                                  --出生日期
        myimage                 varchar2(100),                         --头像
        usertype                smallint                default(0),    --用户类型    0---个人用户   1--企业用户    2---游客   3--农家院经营者        
        joincompanyid           number,                                --加盟商ID号
        joinid                  number,                                --加盟商序列号
        dflag                   number,                                --站点是否被删除
        createarticles	        number                  default(0),    --用户创建文章数量
        editarticles		number                  default(0),    --用户修改文章数量
        deletearticles	        number                  default(0),    --用户删除文章数量
        score                   number                  default(0),    --用户积分
        predeposit              number                  default(0),    --用户预存款
	createdate              date,                                  --用户信息创建时间
	lastupdate              date,                                  --用户信息最终修改时间
        company                 varchar2(200),                         --所在公司
        address                 varchar2(500),                         --详细通讯地址
	LATITUDE                number(15,9),                          --用户注册时所处的经度
	LONGITUDE               number(15,9),                          --用户注册时所处的纬度
        department              varchar2(50),                          --所属部门
        emailaccount            varchar2(100),                         --email帐号
        emailpasswd             varchar2(100),                         --email密码
        departmentarticlestype  number                  default(0),    --审核文章的范围0-所有部门1-本部门2-指定部门
        departmentarticlesids   varchar2(500),                         --指定审核文章的部门id     
	trypassnum              number,                                --允许登录试错的次数
	trypasstime             date,                                  --达到试错次数后的锁定时间
	orgid                   number,                                --用户所属组织架构ID
	companyid               number,                                --用户所属公司ID
	deptid                  number,                               
	createrid               number,                                
        primary key (userid)
);

insert into tbl_members (id,userid,siteid,userpwd,textpwd,nickname,email,mphone,phone,sex) values(1,'admin',-1,'4346D362F67C89D7AB4E28732A1B1CE8','!1','系统管理员','','','',0);

--系统用户角色表
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

create table tbl_group (
	groupid     		number          	not null,
	siteid      		number             	not null,
	groupname   		varchar2(20)    	not null,
	groupdesc   		varchar2(100),
        primary key (groupid)
);

create table tbl_group_members (
	userid      		varchar2(30)    	not null,
	groupid     		number          	not null
);

create table tbl_group_rights (   	
	groupid			number          	not null,
        columnid        	number,
        rightid        	 	number(2)
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

--按“与”关系划分的审核操作路径
create table tbl_column_auditing_process (
	id                   	number             	not null,
	rulesid             	number             	not null,
	subrules_by_or          varchar2(300),                            --分解后的OR审核规则
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
	creator                 varchar2(30)        	not null,           --信息创建者名称
	status               	number(1)   		not null,           --文章状态      0-可用   1-未用
        topflag                 number(1)               default(0),         --置顶标志，1表示置顶 0-正常顺序
	redflag                 number(1)               default(0),         --飘红标志，1表示飘红 0-正常
	boldflag                number(1)               default(0),         --加黑标志，1表示加黑 0-正常
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
        md5title                varchar2(35),                               --文章标题的MD5格式摘要
        mediafile               varchar2(500),                              --多媒体文件名称
	processofaudit          int                     default(0),         --被审核文章执行到的审核步骤
	t1                      number                  default(0),         --分类1
	t2                      number                  default(0),         --分类2
	t3                      number                  default(0),         --分类3
	t4                      number                  default(0),         --分类4
	t5                      number                  default(0),         --分类5
	deptid                  varchar2(50),                               --文章发布的部门ID
	orgid                   number,                                     --文章所属组织ID
	companyid               number,                                     --文章所属公司ID
	sign                    varchar2(32),                               --文章的数字签名，输入的文章为文章标题+ID的数字签名，抓取文章为文章的URL数字签名
        sourceurl               varchar2(1000),                             --被采集信息的URL
	primary key (id)
);

create table TBL_ARTICLE_EXTENDATTR
(
        id                            NUMBER                  not null,
        articleid                     NUMBER                  not null,
        ename                         VARCHAR2(50)            not null,
        type                          NUMBER(1)               not null,
        textvalue                     LONG,
        stringvalue                   VARCHAR2(4000),
        numericvalue                  NUMBER,
        floatvalue                    NUMBER(9,2),
  	CONSTRAINT fk_articleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)
);

create table tbl_turnpic (                                                   --图片轮换表
        id                  number not null,
        articleid           number not null,
	sortid              int,                                             --图片显示顺序
	mediaurl            varchar2(500),                                   --多媒体文件路径
        smallpic1url        varchar2(500),                                   --小图片1URL
	smallpic2url        varchar2(500),                                   --小图片2URL
	smallpic3url        varchar2(500),                                   --小图片3URL
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

create table tbl_deleted_article (
        siteid                  int                not null,                 --站点ID
	columnid                int                not null,                 --栏目ID
        articleid               int                not null,                 --文章ID
	sitename                varchar2(100),                               --站点名称
	acttype                 smallint           default    0              --0表示被删除的文章   1表示被修改的文章        
);

create table tbl_relatedartids                                               --页面相关文件表
(
        ID                      number                         not null,     --主键ID
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

create table tbl_recommend_article
(
        id                      number                      not null,        --主键ID
        siteid		        number                      not null,        --站点ID
        columnid	        number                      not null,        --栏目ID
        modelid                 number                      not null,        --模板ID
	markid                  number                      not null,        --标记ID
	markname                varchar2(500),                               --标记名称
        articleid               number                      not null,        --文章ID
	createdate              date,                                        --创建时间
	lastupdate              date,                                        --修改时间
        primary key (id)
);

--被抓取文件与信息源相关的信息
create table tbl_article_sourceinfo
(
        id                            NUMBER                  not null,
        articleid                     NUMBER                  not null,
	maintitle            	      varchar2(600)           not null,           --文章标题        
	md5title                      varchar2(35),                               --文章标题的MD5格式摘要
	publishtime_from_source       date		      not null,           --文章在被抓取网站的发布时间
	sourceid                      NUMBER,                                     --抓取文章来源网站的ID 
        createdate                    date,                                       --信息被抓取的时间
  	CONSTRAINT fk_sourcearticleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)        
);

create table tbl_attachment_class (
	columnid                number                  not null,            --栏目ID
	siteid               	number                  not null,            --站点ID
	cname                	varchar2(50)        	not null,            --分类中文名称
	summary                	varchar2(500)        	not null,            --分类描述
	editor               	varchar2(30)            not null,            --编辑
	createdate           	date                    not null,            --创建日期
	lastupdated          	date			not null,            --最终修改日期
        primary key (columnid)
);

create table tbl_attachment_class_part (
	id                      number                  not null,            --主键ID 栏目ID
	classid                 number                  not null,            --附件分类主表ID
	siteid               	number                  not null,            --站点ID
	cname                	varchar2(50)        	not null,            --附件类别中文名称
	ename                	varchar2(50)        	not null,            --附件类别英文名称
	cltype              	varchar2(10)            not null,            --附件类型
	editor               	varchar2(30)            not null,            --编辑
	createdate           	date                    not null,            --创建日期
	lastupdated          	date			not null,            --最终修改日期
	CONSTRAINT fk_articleAttachID FOREIGN KEY (classid) REFERENCES  tbl_attachment_class(columnid) ON DELETE CASCADE,
	primary key (id)
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

create table tbl_template_headpics (
	id                   	number    		not null,
	siteid                  number       default(0),
	columnid             	number    		not null,
        modelid                 number,                                    --模板ID
	picbrief                varchar2(500),                             --模板头图描述
	pich                    int,                                       --头图高度
	picw                    int,                                       --头图宽度
	picurl                  varchar2(500),                             --头图URL
	pichref                 varchar2(500),                             --模板头图的链接
	CONSTRAINT fk_modelpicid FOREIGN KEY (modelid) REFERENCES  tbl_template(id) ON DELETE CASCADE,
        primary key (id)
)

create table tbl_column (
	id                   	number                      not null,
	siteid               	number                      not null,
	parentid             	number          	    not null,
	orderid              	number                      not null,
	cname                	varchar2(50)        	    not null,
	ename                	varchar2(50)        	    not null,
	dirname              	varchar2(200)               not null,
	editor               	varchar2(30)                not null,
	extname              	varchar2(10)                not null,
	createdate           	date                        not null,
	lastupdated          	date		            not null,
	isdefineattr         	number(1)  		    not null,
 	hasarticlemodel      	number(1)  		    not null,
	isaudited            	number(1)  		    not null,
	isproduct            	number(1)    		    not null,
	ispublishmore        	number(1)    		    not null,
	languagetype         	number(1)    		    not null,
	columndesc           	varchar2(200),
	xmltemplate          	varchar2(3000),
	encodingflag            number,                                    --文档编码格式 1-utf-8  2-gb2312  3-gbk
	doctype                 varchar2(500),                             --文档类型   HTML5  HTML4
	islocation              number          default(1),                --栏目存储位置信息
	contentshowtype		number(1),                                 --WAP格式   HTML格式
	isrss			number(1)       default(0) not null,
	getRssArticleTime	number(1),
	archivingrules		number(1)       default(0) not null,
	useArticleType		number(1)       default(0) not null,
	istype			number(1)	default(0) not null,
	publicflag              number(1)	default(0) not null,
	xmltype                 varchar2(3000),                           --文章分类的定义
	userflag                number(1)       default(0),               --栏目文章是否只有注册用户可看  0--所有用户 1--注册用户
	userLevel               number          default(0),               --用户浏览文章的级别
	shareflag               number          default(0),               --栏目是否是和其他网站共享的栏目
	articlecount            number          default(0),               --本栏目的文章总数量
        titlepic                varchar2(10),                             --标题图片大小       80X120
	vtitlepic               varchar2(10),                             --副标题图片大小     80X120
	sourcepic               varchar2(10),                             --来源图片大小       80X120
	authorpic               varchar2(10),                             --作者图片大小       80X120
	contentpic              varchar2(10),                             --内容图片大小       600X600
	specialpic              varchar2(10),                             --文章特效图片大小   240X240
	productpic              varchar2(10),                             --商品大图片大小     480X480
	productsmallpic         varchar2(10),                             --商品小图片大小     160X160
        ts_pic                  varchar2(10),                             --特效图片尺寸       80X80
        s_pic                   varchar2(10),                             --小图片尺寸         120X120
        ms_pic                  varchar2(10),                             --中小图片尺寸       240X240
        m_pic                   varchar2(10),                             --中型图片尺寸       360X360
        ml_pic                  varchar2(10),                             --中大图片尺寸       480X480
        l_pic                   varchar2(10),                             --大图片尺寸         600X600
        tl_pic                  varchar2(10),                             --特大图片尺寸       800X800
        primary key (id)
);

insert into tbl_column (id,siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate,lastupdated,isdefineattr,hasarticlemodel,isaudited,isproduct,ispublishmore,languagetype,columndesc,xmltemplate,encodingflag,doctype) values(0,0,-1,0,'首页','home','/','admin','html',sysdate,sysdate,0,0,0,0,0,0,'','',0,'');

--抓取信息分拣关键词
create table tbl_column_keywords (
	id                   	number             	not null,         --表的主键，自增长字段
        scolumnid               number                  not null,         --被抓取信息存放的栏目
	tcolumnid               number                  not null,         --符合关键词设计规则的文章被分拣的目标栏目ID
	keywords                varchar2(500),                            --设置的分拣关键词，以逗号分隔
	primary key(id)
)


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

--栏目模板和标记的相关表
CREATE TABLE TBL_TemplateMark (
	ID                   number    NOT NULL,                   --编号
	siteid               number    not null,                   --站点id
        tid                  number    not null,                   --模板id
        mid                  number    not null,                   --该模板上的标记id
	columnid             number    not null,                   --模板所在栏目ID
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
	cssjsdir		number(1)		            not null,
	tcflag               	number(1)		            not null,
	pubflag              	number(1)               	    not null,
	bindflag             	number(1)               	    not null,
	createdate           	date			            not null,
	berefered		number(1)	        	    not null,     --
	config			varchar2(200),
	siteip                	varchar2(15) default('127.0.0.1')   not null,	
	wapflag			number(1)       default(0)          not null,
	encodeflag              number          default(1),                       --文档编码格式 1-utf-8  2-gb2312  3-gbk
	doctype                 varchar2(500),                                    --文档类型   HTML5  HTML4
	copycolumn	        number(1)       default(0) 	    not null,     --
	becopycolumn	        number(1)       default(0) 	    not null,     --
	pusharticle	        number(1)       default(0) 	    not null,     --发布文件数量
	movearticle	        number(1)       default(0) 	    not null,     --移动文件数量
        sitetype                number             default(0),                    --样例和共享站点的类型,如电子商务网站，个人网站等等
	sitepic                 varchar2(200),                                    --站点首页图片
	sitevalid               number        default(0),                         --0-样例网站 1-普通网站 2-共享网站
	sitelogo                varchar2(200),                                    --站点LOGO
	banner                  varchar2(200),                                    --站点主BANNER
	navigator               number             default(0),                    --站点导航样式
	samsiteid               number             default(0),                    --样例站点ID
	scores                  number          default(0),                       --积分和货币兑换规则
	sharetemplatenum        number             default(0),                    --共享样例站点的模板套号
	managesharesite         number        default(0),                         --管理共享站点信息   0-不管理  1-管理
        copyright               varchar2(1000),	                                  --网站版权
        titlepic                varchar2(10),                                     --标题图片大小       80X120
	vtitlepic               varchar2(10),                                     --副标题图片大小     80X120
	sourcepic               varchar2(10),                                     --来源图片大小       80X120
	authorpic               varchar2(10),                                     --作者图片大小       80X120
	contentpic              varchar2(10),                                     --内容图片大小       600X600
	specialpic              varchar2(10),                                     --文章特效图片大小   240X240
	productpic              varchar2(10),                                     --商品大图片大小     480X480
	productsmallpic         varchar2(10),                                     --商品小图片大小     160X160
        ts_pic                  varchar2(10),                                     --特效图片尺寸       80X80
        s_pic                   varchar2(10),                                     --小图片尺寸         120X120
        ms_pic                  varchar2(10),                                     --中小图片尺寸       240X240
        m_pic                   varchar2(10),                                     --中型图片尺寸       360X360
        ml_pic                  varchar2(10),                                     --中大图片尺寸       480X480
        l_pic                   varchar2(10),                                     --大图片尺寸         600X600
        tl_pic                  varchar2(10),                                     --特大图片尺寸       800X800
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
        creator                 varchar2(100),
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

-- 组织架构表
create table tbl_organization
(
       id                      number not null,              --主键ID
       siteid                  number,
       parent                  number(19),                   --父ID
       cotype                  number default 0,             --节点类型 1--子公司   2--部门
       customerid              number,                       --客户ID，对应着站点ID
       orderid                 number,                       --节点在同层中的序号
       nodeid                  number(19),                   --节点对应子公司、部门
       status                  number default 1,             --状态 0 无效 1 有效
       name                    varchar2(255) not null,       --节点名称
       ename                   varchar2(255),                --节点英文名称
       llevel                  number default 1 not null,    --组织结构层级
       orgcode                 varchar2(50),                 --组织节点编码
       isleaf                  number(1) default 0,          --是否是叶子节占，0为非叶子节点，1为叶子节点
       lastupdate              date default sysdate,         --最后修改日期
       createdate              date default sysdate,         --创建日期
       createuser              varchar2(50),                 --创建人
       updateuser              varchar2(50),                 --修改人
       primary key(id)
);

--公司类型
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

--公司信息表
create table tbl_companyinfo(
       ID                                        number,                            --主键
       orgid                                     number,                            --公司所属组织ID
       SITEID                                    number,                            --公司站点ID 
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
       COMPANYPIC                                VARCHAR2(200),                     --公司图片
       PUBLISHFLAG                               number,                            --发布标记
       status                                    smallint default(0),               --状态  0--对外发布   1-不对外发布
       COUNTRY                                   varchar2(12),                      --公司所在国家
       PROVINCE                                  varchar2(12),                      --公司所在省份
       CITY                                      varchar2(12),                      --公司所在市
       ZONE                                      varchar2(12),                      --公司所在区县
       TOWN                                      varchar2(12),                      --公司所在乡镇
       VILLAGE                                   varchar2(12),                      --公司所在村
       COMPANYLATITUDE                           number(38,15),                     --公司所在地的经度
       COMPANYLONGITUDE                          number(38,15),                     --公司所在地的纬度
       MPHONE                                    varchar2(12),                      --联系人移动电话
       BIZPHONE                                  varchar2(12),                      --联系人电话
       CONTACTOR                                 varchar2(12),                      --联系人姓名
       SAMSITEID                                 number,                            --样例站点ID
       COMPANYLEVEL                              number,                            --公司级别
       qq                                        varchar2(50),                      --公司QQ
       weixin                                    varchar2(50),                      --公司微信
       weibo                                     varchar2(50),                      --公司微博
       createdate                                date,                              --创建日期
       updatedate                                date,                              --修改日期
       createuser                                varchar2(50),                      --创建人
       COMPCODE                                  varchar2(50),                      --公司内部编码
       LEGAL                                     varchar2(500),                     --公司法人
       COMPSHORTNAME                             varchar2(200),                     --公司短名称
       COMPENNAME                                varchar2(200),                     --公司英文名称
       creditcode                                varchar2(100),                     --公司统一信用编码
       relatetype                                number,                            --公司属性  1：总公司，2：分公司，3：子公司，4：控股公司，5：参股公司，6：派驻机构，7：其它
       ALIASFLAG                                 number,                            --该名称是一个公司的别名,1表示公司主名称，0表示别名
       primary key(id)
);

--企业部门设置
create table tbl_department (
       id                      number            not null,
       orgid                                     number,                            --公司所属组织ID
       companyid               number            not null,                          --所属公司ID
       siteid                  number            not null,                          --所属站点ID
       cname                   varchar2(100)     not null,                          --部门中文名称
       ename                   varchar2(100),                                       --部门英文名称
       telephone               varchar2(12),                                        --部门联系电话
       manager                 varchar2(50),                                        --部门经理
       vicemanager             varchar2(50),                                        --部门副经理
       leader                  varchar2(50),                                        --部门主管领导
       lastupdate              date default sysdate,                                --最后修改日期
       createdate              date default sysdate,                                --创建日期
       createuser              varchar2(50),                                        --创建人
       EMAIL                   VARCHAR2(200),                                       --部门联系邮件 
       SHORTNAME               VARCHAR2(200),                                       --部门简称
       primary key(id)
);          

create table tbl_classofcompany (
       id                      number            not null,
       siteid                  number            not null,                          --所属站点ID
       companyid               number            not null,                          --公司ID
       classid                 number            not null,                          --公司所属分类ID
       lastupdate              date default sysdate,                                --最后修改日期
       createdate              date default sysdate,                                --创建日期
       createuser              varchar2(50),                                        --创建人
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
create sequence tbl_siteip_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;
create sequence tbl_template_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_mark_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_column_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_article_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_article_extendattr_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_article_attach_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
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
create sequence uip_queue_id minvalue 1 maxvalue 9999999999999999999999999 start with 487039 increment by 1 cache 20;


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
create index articleindex_sign on tbl_article(sign);
create index articleextendindex_articleid on TBL_ARTICLE_EXTENDATTR(articleid) online;
create index columnindex_forsiteid on tbl_column(siteid);
create index templateindex_forsiteid on tbl_template(siteid);
create index templateindex_forcolumnid on tbl_template(columnid);
create index templateindex_forreferid on tbl_template(ReferModelID);
create index referarticleidindex on tbl_refers_article(articleid);
create index refercolumnidindex on tbl_refers_article(columnid);


--加盟商注册表
create table joincompany(
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
create table tbl_userinfo(
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
--全球国家（）
create table EN_COUNTRY
(
  id                number               not null,
  orderid           number,
  countryname       varchar2(20)         not null,
  valid             number               default 0,                   --1--使用   0--停用
  code              varchar2(6)
)

--省市级单位（22个省，5个自治区，4个直辖市，2个特别行政区）
create table EN_PROVINCE
(
  id                number               not null,
  orderid           number,
  countryid         number               default(86),
  provname          varchar2(20)         not null,
  emsfee            number,
  valid             number               default 0,                   --1--使用   0--停用
  code              varchar2(6)
)

--地级行政单位（17个地区、30个自治州、283个地级市、3个盟）
create table en_city
(
  id                number               not null,
  orderid           number,
  provid            number               not null,
  cityname          varchar2(30)         not null,
  valid             number               default 0,                   --1--使用   0--停用
  code              varchar2(6)
);

--区县级行政单位（1464个县、117个自治县、374个县级市、852个市辖区、49个旗、3个自治旗、1个林区、2个特区）
create table en_zone
(
  id               number                 not null,
  orderid          number, 
  cityid           number                 not null,
  zonename         varchar2(30)           not null,
  valid            number                 default 0,                   --1--使用   0--停用
  code             varchar2(6)
);

--乡镇级行政单位（14677个乡、1092个民族乡、19522个镇、6152个街道办事处、11个区公所、181个苏木、1个民族苏木）
create table en_town
(
  id               number                 not null,
  orderid          number,
  zoneid           number                 not null,
  townname         varchar2(30)           not null,
  valid            number                 default 0,                   --1--使用   0--停用
  code             varchar2(6)
);

--村、社区级行政单位（80717个社区居民委员会、623669个村民委员会）
create table en_village
(
  id               number                 not null,
  orderid          number,
  townid           number                 not null,
  villagename      varchar2(30)           not null,
  valid            number                 default 0,                   --1--使用   0--停用
  code             varchar2(6)
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


CREATE TABLE tbl_orders(                                                            --定单表
         id                             number                    not null,         --主键
	 SITEID        			number,				            --站点id
	 orgid                          number,                                     --订单所属部门
	 companyid                      number,                                     --订单所属公司
	 deptid                         number,                                     --订单所属部门
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
	 VALID         			number      default(0),		
	 STATUS        			number                     NOT NULL ,	    --定单状态
	 BILLINGWAY    			number,				            --支付方式
	 CITY          			varchar2(50),			            --城市
	 RECEIVE       			number,				
	 NOUSE         			number      default(1),	                    --定单是否有效 1-有效 0-无效
	 PROVINCE      			varchar2(50),			            --省份	 
	 zone          			varchar2(50),			            --地区
	 sendway       			number,				            --配送方式
	 commissioncharge               number(9,2),		                    --折扣
	 insurance   			number(9,2),		                    --保险费
	 need_invoice                   number        default(0),	            --是否需要发票
	 bill_to_address                varchar2(2000),	                            --发票地址
	 notes                          varchar2(4000),	                            --发票信息
	 flag                           number(1)     default(0),
	 payflag                        number(1)     default(0),                   --是否已经支付
	 linktime                       varchar2(50),			
	 payway        			number,		                            --付款方式
	 paydate                        date,                                       --订单支付时间
	 usescores                      number        default(0),	            --用户使用积分
	 orderscores                    number        default(0),                   --定单产生的积分 
	 usecard                        number        default(0),                   --使用购物券面额   新增加
	 paycompno                      int,                                        --第三支付网关编号
	 jylsh                          varchar(200),                               --从支付网关返回的支付网关方的交易流水号
	 zfmemberid                     varchar2(30),                               --支付中心会员ID
	 r2type                         smallint    default(0),                     --支付中心返回类型，1浏览器重定向 2服务器点对点
         payresult                      varchar2(20),                               --支付结果，成功/失败/其他
	 lastupdate                     date,		                            --订单最后修改时间
	 CREATEDATE    			date,				            --创建时间
	 primary key(ORDERID) 
);


CREATE TABLE TBL_ORDERS_DETAIL  ( -- 定单详细信息表
	 id                             number              NOT NULL,               --主键
	 orderid                        number(38)          NOT NULL,               --定单号
	 supplierid                     number              default(0)   not null,  --供货商id(对应每个供货商siteid)
	 suppliername                   varchar2(100),                              --供货商名称
	 productid                      number,				            --商品id
	 productname                    varchar2(200),	                            --商品名称
	 productcode                    varchar2(200),                              --商品编码
	 ordernum                       number,				            --商品数量
         unit                           varchar2(20),                               --商品计量单位
	 saleprice                      number(9,2),		                    --商品售价
	 weight                         number,				            --商品重量
	 type                           varchar2(100),	                            --商品类型（扩展属性）
	 cardid                         number             default(0) NOT NULL,     --使用购物卷id 	 
	 userinstarttime                date,                                       --用户输入的开始日期
	 userinendtime                  date,                                       --用户输入的结束日期
	 servicestarttime               date,                                       --根据规则计算出的服务类采购起始日期
	 serviceendtime                 date,                                       --根据规则计算出的服务类采购结束日期
	 subscribe                      smallint,                                   --服务订阅类型
         subscribenum                   int,                                        --服务订购的期数，例如1个季度或者2个季度
	 createdate                     date,				            --创建时间
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

create table tbl_paywayfee --支付方式费用
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

##我的常用送货地址
create table tbl_addressinfo(
        id                        number               not null,      --主键
        userid                    number               not null,      --用户id
        name                      varchar2(200),                      --收货人姓名
        provinces                 varchar2(50),		              --省份
        city                      varchar2(50),			      --城市
        zone                      varchar2(50),                       --地区
        mobile                    varchar2(50),                       --移动电话
        phone                     varchar2(50),                       --固定电话
        zip                       varchar2(50),                       --邮编
        address                   varchar2(200),                      --详细地址
        email                     varchar2(200),                      --收货人邮件地址
        notes                     varchar2(200),                      --收货人备注信息
	createdate                date,                               --创建时间
	primary key(id)
);

##订单的具体送货地址
create table tbl_addressinfoForOrder(
        id                        number              not null,       --主键
        ORDERID                   number              not null,       --定单号  
	userid                    number              not null,       --用户id
        name                      varchar2(200),                      --收货人姓名
        provinces                 varchar2(50),			      --省份
        city                      varchar2(50),			      --城市
        zone                      varchar2(50),                       --地区
        mobile                    varchar2(50),                       --移动电话
        phone                     varchar2(50),                       --固定电话
        zip                       varchar2(50),                       --邮编
        address                   varchar2(200),                      --详细地址
	createdate                date,                               --创建时间
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

##具体订单的发票信息表
create table tbl_invoiceinfoForOrder
(
       id                 number        not null,
       siteid             number        default 0,              --站点id
       userid             number        default 0,              --用户id
       orderid            number        default 0,              --用户订单号
       invoicetype        number        default 0,              --发票类型(0-普通发票1-增值税发票)
       title              number        default 0,              --发票抬头（个人/单位0-个人1-单位）
       companyname        varchar2(200),                        --单位名称
       content            number        default 0,              --发票内容(外键内容id)
       identification     varchar2(200),                        --纳税人识别号
       registeraddress    varchar2(200),                        --注册地址
       phone              varchar2(50),                         --注册电话
       bankname           varchar2(200),                        --开户银行
       bankaccount        varchar2(100),                        --银行帐号
       createdate         date,                                 --创建时间
       primary key(id)
);

##具体订单的发票内容维护表
create table tbl_invoicecontentForOrder
(
       id                 number           not null, 
       siteid             number           default 0,           --站点id
       orderid            number           not null,            --订单ID
       invoiceid          number           not null,            --发票ID
       guige              varchar2(100),                        --规格
       unit               varchar2(100),                        --单位                      
       num                number,                               --购买数量
       danprice           number(10,2),                         --单价
       content            varchar2(200)    not null,            --内容
       createdate         date,                                 --创建时间
       primary key(id)
);



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
        urltype                 varchar2(10),                             --访问页面的方式，可以是link或者form
        method                  varchar2(10),                             --访问方法，包括get或者post
        indexnum                int,                                      --抓取分页的页数
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
        siteid            int,                                             --站点ID
        starturlid        int,                                             --SP_BASIC_ATTRIBUTES主键ID
        Tkeyword          varchar2(255),                                   --标题关键字
        bkeyword          varchar2(255),                                   --内容关键字
        tbrelation        int,                                             --标题关键字与内容关键字之间的关系
        score             varchar2(10),                                    --关键字获取的积分
        createdate        date                not null,                    --关键字规则创建日期
        primary key(id)   
);

create table SP_BASIC_COLUMNS
(
        ID         int      not null,
        siteid     int,                                                    --站点ID
        BASICID    int,                                                    --SP_BASIC_ATTRIBUTES主键ID
        CLASSID    int,                                                    --内容保存的栏目ID
        CREATEDATE date     not null,                                      --创建日期
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
	matchurl              varchar2(255),
	primary key(id)
);

create table sp_form_param (
	id                    int                         not null,             --主键
	siteid                int,                                              --抓取信息的站点ID
	starturlid            int,                                              --起始URL的ID
	pname                 varchar2(100),                                    --输入字段的名称
	pvalue                varchar2(1000),                                   --输入字段的值
	ptype                 varchar2(10),                                     --输入字段的类型，text/hidden/password
	pid                   varchar2(100),                                    --输入字段的ID值
	primary key(id)
);


create table sp_special_code (
	id                    int                          not null,
	siteid                int,
	starturlid            int,
	ST                    varchar2(255),
	ET                    varchar2(255),
	contenttype           int,
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
	id		varchar(500)		not null,         --主键ID
	lyhid		number,                                   --登录用户ID
	username	varchar(500),                             --登录用户名
	logintime	date,                                     --最后登录时间
	loginnum        number,                                   --登录次数
	loginstatus     number(2),                                --登录状态
	lastactivetime  date,                                     --用户最后活动时间
	ipaddress	varchar(500)                              --登录者的IP地址
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
	CONSTRAINT pk_commentsid PRIMARY KEY (ID)	
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

--注册企业表（注册企业管理）
create table tbl_supplier (
        id                     number                     not null,      --主键
        suppcode               varchar2(12),                             --供货商ID
        corpname               varchar2(100)              not null,      --供货商名称
        enname                 varchar2(100),                            --供货商英文名称
        shortname              varchar2(50),                             --供货商简称
	zhizhaocode            varchar2(50),                             --营业执照编码
	zhizhaoimage           varchar2(250),                            --营业执照图片
        orgcode                varchar2(50),                             --组织机构代码
	orgimage               varchar2(250),                            --组织机构代码图片
        weburl                 varchar2(20),                             --供应商网址
        address                varchar2(120),                            --公司地址
        zipcode                varchar2(120),                            --公司邮政编码
        capital                varchar2(120),                            --公司注册资金
        taxnumber              number,                                   --公司税号
	manager                varchar2(120),                            --公司负责人
	certstatus             number                   default(0),      --公司认证状态    0-未认证    1-认证
        type                   number,                                   --注册公司类型   1-供应商  2-采购商  3-采购商、供应商     
        supptype               number,                                   --供应商子类型   1-总部供应商 2-分子公司供应商
        suppgrade              number,                                   --供应商评级，   5-5星 4-4星  3-3星 2-2星 1-1星
        supplevel              number,                                   --供应商级别，1-战略供应商 2-重要供应商 3-普通供应商
        is_used                number,                                   --供应商是否被使用   1-使用 0-未使用
        szbm                   number,
        sdbz                   number,
        zltype                 number,
        zyflbm                 number,
        zlibz                  number,
        wmbz                   number,
        nmbz                   number,
        nwbz                   number,
        nbdwbz                 number,
        cxsy                   number,
        country                number,                                   --供应商所属国家编码
        privince               number,                                   --供应商所属省份编码
        isbusiness             number,                                   --是否是流通商      0-生产商   1-流通商
        isopen                 number,                                   --供应商是否正常营业   0-正常营业   1-暂停 2-关闭
        tylxbm                 number,
        presenter              number,                                   --推荐企业编码
        presentername          varchar2(120),                            --推荐企业名称
        updatedate             date,                                     --最后修改日期
        createdate             date,                                     --创建日期
	creator                varchar2(50),                             --创建人
	modifier               varchar2(50),                             --最后修改人
	primary key (id)
);

create table tbl_suppcontactor (
        id                     number                     not null,      --联系人主键
	suppid                 number                     not null,      --供应商主键
        contactor              varchar2(20),                             --联系人姓名
        telephone              varchar2(18),                             --联系电话
	mphone                 varchar2(20),                             --联系人手机
	mphone1                varchar2(20),                             --联系人备用手机
        fax                    varchar2(20),                             --传真号码
        email                  varchar2(20),                             --联系人电子邮件
	email1                 varchar2(20),                             --联系人备用电子邮件
	title                  varchar2(20),                             --联系人职位
        brief                  varchar2(1000),                           --联系人简介
	image                  varchar2(250),                            --联系人头像
        updatedate             date,                                     --最后修改日期
        createdate             date,                                     --创建日期
	creator                varchar2(50),                             --创建人
	modifier               varchar2(50),                             --最后修改人
	primary key (id)
);

create table tbl_suppbank (
        id                     number                     not null,      --联系人主键
	suppid                 number                     not null,      --供应商主键
        openaccountbank        varchar2(120),                            --公司开户行
        account                number,                                   --公司开户行账号
        bankcert               varchar2(120),                            --银行认证信息
        isbasic                number,                                   --是否是基本开户行   1-是  0-不是
        updatedate             date,                                     --最后修改日期
        createdate             date,                                     --创建日期
	creator                varchar2(50),                             --创建人
	modifier               varchar2(50),                             --最后修改人
	primary key (id)
);

create table tbl_suppclass (
        id                     number                     not null,      --联系人主键
	suppid                 number                     not null,      --供应商主键
        suppclassid            number                     not null,      --供应商分类ID
        updatedate             date,                                     --最后修改日期
        createdate             date,                                     --创建日期
	creator                varchar2(50),                             --创建人
	modifier               varchar2(50),                             --最后修改人
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


--北京网通销售点卡的产品表
create table tbl_bbn_product (
        id                      number                     not null,      --主键
        username                varchar2(20)               not null,      --用户名
        passwd                  varchar2(12)               not null,      --产品名称
        price                   number(18,2),                             --产品价格
        cardtype                int,                                      --产品卡的类型
        flag                    smallint                   default(0),    --是否被销售 0-未销售 1-已销售 2-被锁定，不能销售
        outdate                 date,                                     --被销售的日期
        buyer                   varchar(11),                              --购买人的手机号
        createdate              date,                                     --创建时间
        updatedate              date,                                     --修改时间
        primary key (id)
);

--用户使曾经用过的手机号码
create table tbl_bbn_mphone (
        id                      number                     not null,      --主键
        userid                  number                     not null,      --用户ID tbl_userinfo的userid
        mphone                  varchar2(12)               not null,      --手机号码
        createdate              date,                                     --创建时间
        primary key (id)
);

--北京无线电管理局企业用户注册表单
create table tbl_rsbt_org(
	id			number				not null,
	guid			varchar2(36)			not null,
	userid			varchar2(36)			not null,
	siteid			number				not null,
	password		varchar2(36)			not null,
	org_gode		varchar2(9) default(999999999)	not null,		--组织机构代码
	org_name		varchar2(80)			not null,		--组织机构名称
	org_area_code		varchar2(8)			not null,		--地区代码
	org_sys_code		varchar2(8)			not null,		--系统/行业代码
	org_type		varchar2(3)			not null,		--单位类型
	org_link_person		varchar2(40),						--单位联系人
	org_person_id		varchar2(40),						--联系人身份证号码
	org_sup_code		varchar2(9),						--上级组织机构
	org_addr		varchar2(80),						--组织机构地址
	org_post		varchar2(6),						--组织机构邮编
	org_phone		varchar2(40),						--联系电话
	org_mob_phone		varchar2(40),						--手机号码
	org_fax			varchar2(40),						--组织机构传真
	org_bank		varchar2(80),						--开户银行
	org_account_name	varchar2(80),						--账户名称
	org_account		varchar2(40),						--银行账号
	org_hostility		number(1),						--设台单位性质
	org_web_site		varchar2(80),						--网址
	org_mail		varchar2(80),						--电子邮箱
	createdate		date,
	primary key(id)
);

create table tbl_rsbt_org1(
	id			number				not null,
	value			number				not null,
	name			varchar2(50)			not null,
	primary key(id)
);

create table tbl_rsbt_org2(
	id			number				not null,
	value			number				not null,
	name			varchar2(50)			not null,
	primary key(id)
);

create table tbl_rsbt_org3(
	id			number				not null,
	value			number				not null,
	name			varchar2(50)			not null,
	primary key(id)
);

--业务审批信息数据库文件信息表
create table gw_case_info (
     sn                        varchar2(100) not null,
     operater                  varchar2(100) not null,
     telephone                 varchar2(100) not null,
     applicant                 varchar2(200) not null,
     applicantID               varchar2(100),
     registerNO                varchar2(200),
     applicanttel              varchar2(100) not null,
     harge                     number(9,2) default 0,
     dt_operate                varchar2(100) not null,
     startingDate              varchar2(100),
     licenceName               varchar2(100),
     licenceCode               varchar2(100),
     caseID                    varchar2(100) not null,
     caseName                  varchar2(100) not null,
     result                    varchar2(100) not null,
     createdate                date,                                      --创建日期
     lastupdate                date,                                      --修改日期
     editor                    varchar2(50),                              --内容创建人
     publishflag               number not null 
);

create table tbl_accessmdb
(
    userid        varchar2(50)                  not null,           --上传人ID
    filename      varchar2(100)                 not null,           --上传文件原始名称
    tflag         number(1) default(0),                              --是否转入到数据库      0-没有转入       1-已经转入，全部成功 2-导入，有问题
    createdate    date,
    lastupdate    date,
    primary key(filename)
);


--无线电干扰申诉表
create table tbl_ganrao(
	id			number				not null,		--主键
	susongdanwei		varchar2(50)			not null,		--诉讼单位
	lianxiren1		varchar2(20),						--联系人1
	lianxiren2		varchar2(20),						--联系人2
	phone			varchar2(20),						--联系电话
	taizhanweizhi		varchar2(200),						--台站位置
	shebeixinghao		varchar2(100),						--设备型号
	gongzuofangshi		varchar2(50),						--工作方式
	ganraoleixing		varchar2(50),						--干扰类型
	zhizhaobianhao		varchar2(50),						--执照编号
	ganraopinlv		varchar2(50),						--干扰频率
	ganraoquyu		varchar2(50),						--干扰区域
	ganraoshiduan		varchar2(50),						--初次干扰时间及发生时段
	ganraoshijian		varchar2(50),						--出现频度及每次持续时间
	tezhengmiaoshu		varchar2(500),						--干扰信号特征描述
	shenshuyaoqiu		varchar2(500),						--申述要求
	primary key(id)
);

--设置无线电台申报表
create table tbl_zhuduijiangji(
	djjid			number				not null,		--主键
	shetaidanwei		varchar2(50),						--设台单位
	suoshuxitong		varchar2(50),						--所属系统
	address			varchar2(100),						--地址
	lianxiren		varchar2(20),						--联系人
	phone			varchar2(20),						--电话
	chuanzhen		varchar2(20),						--传真
	shiyongdiqu		varchar2(50),						--使用地区
	diantaishuliang		varchar2(20),						--电台数量
	diantaileibie		varchar2(50),						--电台类别	150MHz,400MHz,900MHz无中心地址
	shetailiyou		varchar2(500),						--设台理由
	lingdaoyijian		varchar2(500),						--上级主管单位领导意见
	guanlijuyijian		varchar2(500),						--北京市无线电管理局意见
	xitongbianhao		varchar2(100),						--系统编号
	zhizhaobianhao		varchar2(100),						--执照编号
	primary key(djjid)
);

create table tbl_diantaiziliao(
	id			number				not null,		--主键
	dtzlid			number				not null,
	shebeixinghao		varchar2(100),						--设备型号
	chuchanghaoma		varchar2(100),						--出厂号码
	beizhu			varchar2(100),						--备注
	CONSTRAINT fk_dtzlID FOREIGN KEY (dtzlid) REFERENCES  tbl_zhuduijiangji(djjid) ON DELETE CASCADE,
	primary key(id)
);
create sequence tbl_duijiangji_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_ziliao_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;


--业务分类表
create table tbl_BusinessInfo
(
    codeinfo varchar2(100),
    cname varchar2(100),
    siteid number default 0 not null,
    primary key(codeinfo)
);
--预约代码表
create table tbl_Appointment
(
    id number not null,
    codeinfo varchar2(100) not null,
    codename varchar2(100) not null,
    appointmentdate varchar2(30) not null,
    appointmentcode varchar2(6) not null,
    siteid number default 0 not null,
    primary key(id) 
);
create sequence tbl_Appointment_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
--用户预约信息表
create table tbl_userappointment
(
    id number not null,
    userid varchar2(100) not null,
    appointmentdate varchar2(30) not null,
    appointmentcode varchar2(6) not null,
    siteid number default 0 not null,
    primary key(id)
);
create sequence tbl_userappointment_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;

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


长城电商会议报名系统
--会议表
create table tbl_meetings
(
    id               number,              --培训会主键ID
    meetingname      varchar2(200),       --培训会名称
    meetingdatetime  date,                --培训会的日期和时间
    address          varchar2(10),        --培训会地址
    createdate       date,                --创建日期和时间
    editor           varchar2(50),        --创建人
    primary key(id)
);

create table tbl_meetting_sign
(
    id               number,              --主键ID
    orderid          number,              --订单号           ORACLE的序列号
    meetingid        number,              --培训会ID
    comapnyname      varchar2(200),       --公司名称
    invoicetitle     varchar2(200),       --发票抬头
    address          varchar2(10),        --发票邮寄地址
    postcode         varchar2(6),         --邮政编码
    fee              number(9,2),         --培训费用
    payway           number(1),           --支付方式     1--银行   2--网络支付
    paytime          date,                --支付日期和时间
    createdate       date,                --注册日期和时间
    primary key(id)
);

create table tbl_meetting_sign_part
(
    id               number,              --主键ID
    orderid          number,              --订单号
    signid           number,              --注册培训会主表ID
    name             varchar2(50),        --培训人姓名
    depttitle        varchar2(50),        --培训人所在部门和职务
    mobilephone      varchar2(11),        --培训人手机号码
    fax              varchar2(15),        --培训人传真号码
    email            varchar2(50),        --培训人电子邮件地址
    meettingtime     date,                --培训会时间
    meetingaddress   varchar2(200),       --培训会地址
    primary key(id)
);

招标统计系统
create table tbl_tender
(
    id               number,              --主键ID
    articleid        number,              --文章ID
    userid           number,              --userID
    name             varchar2(50),        --投标人名称
    depttitle        varchar2(100),        --投标人所在部门和职务
    createdate       date,                --首次创建时间
    primary key(id)
);

