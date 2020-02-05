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
grant create session to tyydbadmin;            //����zhangsan�û�����session��Ȩ�ޣ�����½Ȩ��
grant unlimited tablespace to tyydbadmin;      //����zhangsan�û�ʹ�ñ�ռ��Ȩ��
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

--ͼ��ֱ���Ķ����
create table TBL_broadcast
(
  id               int                not null,           --ֱ�����������
  siteid           int                not null,           --ֱ����վ��ID
  title            VARCHAR2(200),                         --ֱ���ı���
  brief            varchar2(3000),                        --ֱ����������
  picurl           varchar2(250),                         --ֱ��ͼƬURL
  createdate       date,                                  --ֱ�����ݴ�������
  createuser       varchar2(200),                         --ֱ���Ĵ�����
  primary key (id)
);


--ͼ��ֱ�������ݱ�
create table TBL_broadcastcontent
(
  id               int                not null,           --ֱ�����ݱ�����
  bcid             int                not null,           --ֱ�����������
  siteid           int                not null,           --ֱ����վ��ID
  title            VARCHAR2(200),                         --ֱ���ı���
  brief            varchar2(3000),                        --ֱ����������
  contenturl       varchar2(250),                         --ֱ��ͼƬ�ļ�URL���߶�ý���ļ�URL
  contenttype      smallint,                              --0��ʾͼƬ   1��ʾ��ý���ļ�
  createdate       date,                                  --ֱ�����ݴ�������
  createuser       varchar2(200),                         --ֱ���Ĵ�����
  primary key (id)
);

create table TBL_SEARCHWORD
(
  SITEID     NUMBER,
  IP         VARCHAR2(20),
  KEYWORD    VARCHAR2(200),
  CREATEDATE DATE
);

##ũ��Ժ���ͱ�
drop table cs_room_info;
create table cs_room_info(
	id                       int               not null,                     --��ʶ��,ũ��Ժ����ID
	siteid                   int               not null,	                 --ũ��Ժע���վ��ID
	room_name	         varchar2(100)     not null,                     --��������ƻ��߱��
	catlog_code              char(8)           default '00000000',           --������룬��ʱ����
	room_type 	         smallint 	   default 2,                    --1--���˷� 2--��׼˫�˷�  3--���˷�   4--���˷�  5--���˷�  6--�𿻷�
        room_num                 int               default 1,                    --�����ͷ�������
	room_way                 smallint,                                       --���䳯��1-����2-����3-�ϡ�4-����5-���ϡ�6-������7-���ϡ�8-����
	room_size                int,                                            --�������   
	init_price               number(10,2),                                   --��ʼ�۸�
        bed_num                  int               default 1,                    --�����ڴ�λ����
        bed_type                 int,                                            --���ͣ�1--���˴���1.2�׿�2--���˴���0.9�׿�3--���˴���1.05�׿� 4--˫�˴���1.35�׿� 5--˫�˴���1.5�׿� 6--˫�˴���1.8�׿�7--�𿻣�5�׿�
        floor                    varchar(20),                                    --¥�㣬1�㡢2�㡢2�㣨�е��ݣ���3�㡢3�㣨�е��ݣ���4�㡢4�㣨�е��ݣ���5�㡢5�㣨�е��ݣ�6�㡢6�㣨�е��ݣ�
	toilet                   smallint          default 1,                    --����������
        tv                       varchar2(100),                                  --������ʩ����
        airconditioner           varchar2(100),                                  --�յ���ʩ����
	bathroom                 varchar2(100),                                  --ϴԡ��ʩ����
	bedclothes               varchar2(200),                                  --������Ʒ����
        amenities                varchar2(500),                                  --������ʩ�����ӻ���������ԡ���������硢����Ĵ桢��Ȫˮ������䡢24Сʱ��ˮ���������硢���ͣ��λ���յ�����ԡ����ͨ�������ɽӴ���ͥ/���ӡ���ˮ������ˮԡ�ס���ѽ��͡��ʺϾٰ����������ë����ѽ�̤������Ь��������Ʊ�������һ������ϴ��Ʒ���������������Ȱ�
	specialservice           varchar2(50),                                   --��ͷ��񡢽��ͷ���
	delflag                  smallint         default 0,                     --��Ϣ��ɾ����ʾ��0--ʹ��  1--ɾ��
	primary key (id)
);

##����ͼƬ��
drop table cs_room_pic��
create table cs_room_pic(
	id                       number            not null,                    --��ʶ��
	room_id                  int               not null,                    --ũ��Ժ�Ļ���ID
	pic_url		 	 varchar2(100)     not null,                    --ͼƬ���ʵ�url
	description		 varchar2(500),                                 --ͼƬ��������Ϣ
	title			 varchar2(100),                                 --ͼƬ����
	primary key (id)
);

##ʱ��۸��
drop table cs_date_price;
create table cs_date_price(
	id                       int               not null,                    --��ʶ��
	room_id			 int               not null,                    --ũ��Ժ����ID
	room_price		 number(9,2)       not null,                    --���ͼ۸�     
	roomspecialprice         number(9,2)       not null,                    --�����ؼ�
	roomlongtimeprice        number(9,2)       not null,                    --���ͳ���۸�
	bed_price		 number(9,2)       not null,                    --������ÿ�����ļ۸�
	bedspecialprice          number(9,2)       not null,                    --������ÿ�����ؼ�
	thedate		         date,                                          --�۸�����
	primary key (id)
);

##������־
drop table cs_operationlog;
create table cs_operationlog(
       id                        number            not null,                    --��ʶ��
       cata_date                 date,                                          --��������
       opid                      int               not null,                    --������ʶ
       description               varchar2(500),                                 --��������
       editor                    varchar2(100)     not null,                    --�����û�
       primary key (id)
);

##ũ����Ԥ����
drop table cs_reserve;
create table cs_reserve(
       id                        number            not null,                    --��ʶ��
       siteid                    int               not null,                    --����ID
       usedate                   date,                                          --ʹ������
       numember                  smallint,                                      --����
       name     		 varchar2(30),		                        --����
       mphone                    varchar2(12),                                  --�ֻ�����
       lxrname     		 varchar2(30),		                        --��ϵ������
       lxrmphone                 varchar2(12),                                  --��ϵ���ֻ�����
       lxrphone                  varchar2(12),                                  --��ϵ�˹̶��绰
       email                     varchar2(50),                                  --�����ʼ�
       remarks                   varchar2(500),                                 --��ע
       createdate                date,                                          --����ʱ��
       ruzhu                     smallint,                                      --�Ƿ���ס  ��-0  ��-1
       rzdate                    date,                                          --��ס����
       tfdate                    date,                                          --�˷�����
       rznumember                smallint,                                      --��ס����
       rzroom                    smallint,                                      --����
       rzprice		         number(9,2),                                   --����
       primary key (id)
);

--��Ʒ�������
create table tbl_wzclass
(
       id                number                not null,                  --����ID
       customerid        number                not null,                  --���ϱ����ӵ����     0-��ʾϵͳ��������ʷ���
       name              varchar2(200)         not null,                  --������߷�������
       brief             varchar2(500),                                   --���ʷ�������
       unit              varchar2(10),                                    --�������ʵļ�����λ
       keyword           varchar2(20),                                    --�������ʵĹؼ���
       code              varchar2(100)         not null,                  --����
       pcode             varchar2(100)         not null,                  --������
       orderid           number,                                          --����
       valid             number                default 0,                 --���ʱ����״̬     1--ʹ��   0--ͣ��
       delflag           number                default 0,                 --ɾ����ʶ��         0--ʹ��   1--ɾ��
       creator           varchar2(50),                                    --������
       createdate        date                  default sysdate,           --����ʱ��
       createcomp        number,                                          --���������ڵĹ�˾
       editor            varchar2(50),                                    --�޸���
       lastupdate        date,                                            --����޸�ʱ��
       updatecomp        number,                                          --����޸������ڹ�˾ 
       primary  key(id)
);

--���ʷ���ģ����Ϣ
create table tbl_wzclass_template
(
       id                number                not null,                  --����ID
       classid           number                not null,                  --���ʷ�������ID
       status            number                default 0,                 --�Ƿ�ͣ��    0--ʹ��   1--ͣ��
       delflag           number                default 0,                 --�Ƿ�ɾ��    0--ʹ��   1--ɾ��
       templatexml       clob,                                            --ģ�嶨���XML�ַ���
       customerid        number                not null,                  --���ϱ����ӵ����
       creator           varchar2(50),                                    --������
       createdate        date                  default sysdate,           --����ʱ��
       createcomp        number,                                          --���������ڵĹ�˾
       editor            varchar2(50),                                    --�޸���
       lastupdate        date,                                            --����޸�ʱ��
       updatecomp        number,                                          --����޸������ڹ�˾ 
       primary key(id)
);

--������ϸ��Ϣ
create table tbl_wzdetail
(
       id                number                not null,                  --����ID
       classid           number                not null,                  --���ʷ�������ID
       wzname            varchar2(200)         not null,                  --��������
       wzcode            varchar2(21),                                    --���ʱ�����ϸ
       unit              varchar2(10),                                    --���ʵ�λ
       customerid        number                not null,                  --���ϱ����ӵ����
       status            number                default 0,                 --�����ϼ�����
       delflag           number                default 0,                 --�����Ƿ�ɾ��   0--����  1--ɾ��
       creator           varchar2(50),                                    --������
       createdate        date                  default sysdate,           --����ʱ��
       createcomp        number,                                          --���������ڵĹ�˾
       editor            varchar2(50),                                    --�޸���
       lastupdate        date,                                            --����޸�ʱ��
       updatecomp        number,                                          --����޸������ڹ�˾ 
       primary key(id)
);

--������ϸ��Ϣ���Ա�
create table tbl_wzdetail_part
(
       id                number                not null,                  --����ID
       classid           number                not null,                  --���ʷ�������ID
       proptype          number                default 0,                 --����ֵ      1-����  2-
       ename             varchar2(200)         not null,                  --����������Ϣ��Ӣ������
       type              number                not null,                  --�������Ե���������
       textvalue         clob,                                            --�洢���ı�����ֵ
       stringvalue       varchar2(4000),                                  --�洢�ַ�������ֵ       
       minintvalue       number,                                          --�洢������������Сֵ�������Ǵ洢��������ֵ
       maxintvalue       number,                                          --�洢�������������ֵ
       minfloatvalue     number,                                          --�洢������������Сֵ�������Ǵ洢����������ֵ
       maxfloatvalue     number,                                          --�洢�������������ֵ
       creator           varchar2(50),                                    --������
       createdate        date                  default sysdate,           --����ʱ��
       createcomp        number,                                          --���������ڵĹ�˾
       editor            varchar2(50),                                    --�޸���
       lastupdate        date,                                            --����޸�ʱ��
       updatecomp        number,                                          --����޸������ڹ�˾ 
       primary key(id)
);


--���ʷ����������Ϣ
create table tbl_wzservicearea
(
       id                number                not null,                  --����ID
       classid           number                not null,                  --���ʷ�������ID
       wzid              number                not null,                  --���ʱ���
       areaid            number                not null,                  --���ʷ�����������
       status            number                default 0,                 --�����ϼ�����
       customerid        number                not null,                  --���ϱ����ӵ����
       delflag           number                default 0,                 --�����Ƿ�ɾ��   0--����  1--ɾ��
       creator           varchar2(50),                                    --������
       createdate        date                  default sysdate,           --����ʱ��
       createcomp        number,                                          --���������ڵĹ�˾
       editor            varchar2(50),                                    --�޸���
       lastupdate        date,                                            --����޸�ʱ��
       updatecomp        number,                                          --����޸������ڹ�˾ 
       primary key(id)
);

--����ͼƬ��
create table tbl_wzpictures
(
       id                number                not null,                  --����ID
       classid           number                not null,                  --���ʷ�������ID
       wzid              number                not null,                  --���ʱ���
       picurl            varchar2(500)         not null,                  --����ͼƬURL
       filename          varchar2(100),                                   --����ͼƬ�ļ����ƣ�ֻ����jpg,jpeg,png��ʽͼƬ
       sfilename         varchar2(100),                                   --����ͼƬ�ϴ�ʱ��ԭʼ����
       customerid        number                not null,                  --���ϱ����ӵ����
       delflag           number                default 0,                 --����ͼƬ�Ƿ�ɾ��   0--����  1--ɾ��
       creator           varchar2(50),                                    --������
       createdate        date                  default sysdate,           --����ʱ��
       createcomp        number,                                          --���������ڵĹ�˾
       editor            varchar2(50),                                    --�޸���
       lastupdate        date,                                            --����޸�ʱ��
       updatecomp        number,                                          --����޸������ڹ�˾ 
       primary key(id)
);

--���ʸ�����
create table tbl_wzattachements
(
       id                number                not null,                  --����ID
       classid           number                not null,                  --���ʷ�������ID
       wzid              number                not null,                  --���ʱ���
       attachementurl    varchar2(500)         not null,                  --���ʸ���URL
       filename          varchar2(100),                                   --���ʸ����ļ����ƣ�ֻ����PDF��DOC��DOCX��PPT��PPTX��ʽ�ļ�
       sfilename         varchar2(100),                                   --���ʸ����ϴ�ʱ��ԭʼ����
       customerid        number                not null,                  --���ϱ����ӵ����
       delflag           number                default 0,                 --���ʸ����Ƿ�ɾ��   0--����  1--ɾ��
       creator           varchar2(50),                                    --������
       createdate        date                  default sysdate,           --����ʱ��
       createcomp        number,                                          --���������ڵĹ�˾
       editor            varchar2(50),                                    --�޸���
       lastupdate        date,                                            --����޸�ʱ��
       updatecomp        number,                                          --����޸������ڹ�˾ 
       primary key(id)
);

create table tbl_sites_number
(
	ipaddress		varchar2(15)		not null,         --����IP��ַ
	sitesnum		number,                                   --����վ������
	hashcode                number,                                   --HASH���룬Ψһʶ���վ��
        wwwip                   varchar2(15),                             --�����IP��ַ
        docroot                 varchar2(500),                            --��վ��Ŀ¼
        ftpuser                 varchar2(500),                            --��վFTP�˺�
        ftppasswd               varchar2(500),                            --��վFTP����
        flag                    number,                                   --��վ�Ƿ�������1�������0ֹͣ�������
        privateip               varchar2(15)                              --˽��IP��ַ
)

insert into tbl_sites_number(ipaddress,sitesnum,hashcode) values('127.0.0.1',18,0);

create table tbl_right (
	rightid			number(3)		not null,
	rightname		varchar2(50)		not null,
	rightcat		varchar2(50),
	rightdesc		varchar2(300),
        primary key (rightid)
);

insert into tbl_right values(1,'��Ŀ����','CM','��Ŀ����');
insert into tbl_right values(2,'ģ�����','MM','ģ�����');
insert into tbl_right values(3,'���¹���','AM','���±༭');
insert into tbl_right values(4,'�ϴ��ļ�','FU','�ϴ��ļ�');
insert into tbl_right values(5,'����ļ�','FA','����ļ�');
insert into tbl_right values(6,'����ȫ������','PWA','����ȫ������');
insert into tbl_right values(7,'�ƶ�����','MA','�ƶ�����');
insert into tbl_right values(8,'������������','PPA','������������');
insert into tbl_right values(9,'��Ϣ�ᱨ','XXTB','������ϵͳ�ᱨ����');
insert into tbl_right values(50,'������','AR','������');
insert into tbl_right values(51,'д����','AW','д����');
insert into tbl_right values(52,'ɾ����','AD','ɾ����');
insert into tbl_right values(54,'վ�����','WM','վ�����');
insert into tbl_right values(55,'�ļ��༭','FE','�ļ��༭');
insert into tbl_right values(56,'�ļ�ɾ��','FD','�ļ�ɾ��');
insert into tbl_right values(57,'���԰����','LYB','���԰����');
insert into tbl_right values(58,'���ϵ���','SW','���ϵ���');
insert into tbl_right values(59,'ע���û�����','RU','ע���û�����');
insert into tbl_right values(60,'��ҵע���û�����','ERU','��ҵע���û�����');
insert into tbl_right values(61,'�����չ���','WD','�����չ���');
insert into tbl_right values(62,'ҵ���������','YWR','ҵ���������');
insert into tbl_right values(100,'������ѯ','ddcx','������ѯ');
insert into tbl_right values(101,'��������','zcyhcx','��������');
insert into tbl_right values(102,'���ж���','yhdz','���ж���');
insert into tbl_right values(103,'֧����ѯ','zfcx','֧����ѯ');
insert into tbl_right values(104,'ע���û���ѯ','zcyhcx','ע���û���ѯ');
insert into tbl_right values(105,'ע���û�����','zcyhdc','ע���û�����');
insert into tbl_right values(106,'��������λ��������λ����','dcbdjxsdwdd','��������λ��������λ����');


create table tbl_role (
	roleid			number(2)		not null,
	rolename		varchar2(30)		not null,
	rolecat		        varchar2(30),
	roledesc		varchar2(100),
        primary key (roleid)
);

insert into tbl_role values(0,'��ϢԱ','XXY','��������վ¼����Ϣ');
insert into tbl_role values(1,'�����쵼','BML','¼����Ϣ�����');
insert into tbl_role values(2,'�����쵼','ZGL','¼����Ϣ�����');
insert into tbl_role values(3,'ϵͳ����Ա','SM','����ϵͳ����Ա');

create table tbl_log (
	id			number			not null,         --����
	articleid		number			not null,         --����ID
	siteid			number     		not null,         --վ��ID
	editor			varchar2(30)		not null,         --�༭��Ա
	acttype			number(1)		not null,         --����ͣ�1���ӡ�2�޸ġ�3ɾ��
	acttime			date			not null,         --�ʱ��
	columnid		number			not null,         --��ĿID
	deptid                  number,                                   --����ID
	companyid               number,                                   --��˾ID
	maintitle		varchar2(600)	not null,                 --����
	createdate	        date,                                     --����ʱ��                 
        primary key (id)
);

create table tbl_sjs_log (
	id			number			not null,               --����
	siteid			number     		not null,               --վ��ID           
	sitename		varchar2(50)		not null,               --վ������
        columnid                number,                                         --��ĿID
        columnname              varchar2(50),                                   --��Ŀ����
	articleid		number			not null,               --����ID
	maintitle		varchar2(600)	        not null,               --�������ı���
        articleurl              varchar2(300),                                  --����URL
        operationtype           number(1),                                      --���²�������    1-����   2-�޸� 3-ɾ�� 
        editor                  varchar2(50),                                   --��������
	createdate	        date,                                           --����¼������
        updatedate              date,                                           --�����޸�����
        deletedate              date,                                           --����ɾ������
        primary key (id)
);

create table tbl_members (
        id                      number                  not null,      --����
        userid       		varchar2(30)   		not null,      --�û���
	mmuserid                varchar2(32),                          
        siteid       		number      		not null,      --վ��ID
        userpwd      		varchar2(32)   		not null,      --�û�����
	textpwd                 varchar2(50)   		not null,      --�û����Ŀ���
        nickname     		varchar2(30)		not null,      --�û���ʵ����
        email                   varchar2(50),                          --�����ʼ���ַ
        mphone                  varchar2(12),                          --�ֻ���
        phone                   varchar2(12),                          --��ϵ�绰        
	sex                     smallint                default(0),    --0��ʾ�� 1��ʾŮ
        country                 varchar2(30),                          --����
        province                varchar2(50),                          --ʡ
        city                    varchar2(50),                          --��
        zone                    varchar2(50),                          --����
        jiedao                  varchar2(50),                          --�ֵ�
        shequ                   varchar2(50),                          --����
        area                    varchar2(50),                          --��������
	postcode                varchar2(6),                           --��������
        birthdate               date,                                  --��������
        myimage                 varchar2(100),                         --ͷ��
        usertype                smallint                default(0),    --�û�����    0---�����û�   1--��ҵ�û�    2---�ο�   3--ũ��Ժ��Ӫ��        
        joincompanyid           number,                                --������ID��
        joinid                  number,                                --���������к�
        dflag                   number,                                --վ���Ƿ�ɾ��
        createarticles	        number                  default(0),    --�û�������������
        editarticles		number                  default(0),    --�û��޸���������
        deletearticles	        number                  default(0),    --�û�ɾ����������
        score                   number                  default(0),    --�û�����
        predeposit              number                  default(0),    --�û�Ԥ���
	createdate              date,                                  --�û���Ϣ����ʱ��
	lastupdate              date,                                  --�û���Ϣ�����޸�ʱ��
        company                 varchar2(200),                         --���ڹ�˾
        address                 varchar2(500),                         --��ϸͨѶ��ַ
	LATITUDE                number(15,9),                          --�û�ע��ʱ�����ľ���
	LONGITUDE               number(15,9),                          --�û�ע��ʱ������γ��
        department              varchar2(50),                          --��������
        emailaccount            varchar2(100),                         --email�ʺ�
        emailpasswd             varchar2(100),                         --email����
        departmentarticlestype  number                  default(0),    --������µķ�Χ0-���в���1-������2-ָ������
        departmentarticlesids   varchar2(500),                         --ָ��������µĲ���id     
	trypassnum              number,                                --�����¼�Դ�Ĵ���
	trypasstime             date,                                  --�ﵽ�Դ�����������ʱ��
	orgid                   number,                                --�û�������֯�ܹ�ID
	companyid               number,                                --�û�������˾ID
	deptid                  number,                               
	createrid               number,                                
        primary key (userid)
);

insert into tbl_members (id,userid,siteid,userpwd,textpwd,nickname,email,mphone,phone,sex) values(1,'admin',-1,'4346D362F67C89D7AB4E28732A1B1CE8','!1','ϵͳ����Ա','','','',0);

--ϵͳ�û���ɫ��
create table tbl_member_roles (
       id                       number                     not null,
       siteid                   number                     not null,
       userid                   varchar2(30)               not null,
       deptid                   number                     not null,      --�û���������ID
       rolename                 varchar2(20)               not null,      --��ϢԱ   ���Ÿ�����   ����   ���԰����Ա  �������԰����Ա  
       rolelevel                number,                                   --��ɫ���� 0-9
       primary key(id)
);

--��Ȩ�û���������Ա�
create table tbl_member_authorized_resouce (
       id                       number                     not null,
       siteid                   number                     not null,
       userid                   varchar2(30)               not null,
       lwid                     number,                                   --���û���������Ա�ID
       lwname                   varchar2(100),                            --���û���������Ա������
       lwrole                   varchar2(20),                             --���԰�����ɫ ��ĳ�����԰�Ĺ���Ա �������԰�Ĳ��Ź���Ա
       contenttype              int                         default(0),    --�������� 0Ϊ���԰�
       primary key(id)
);
create sequence tbl_resouce_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;
       
--ÿ���û���ÿ��վ���ÿ����Ŀ��¼�����������
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
	messagetype             number  default(0),                       --0��ʾ��Ŀ������Ϣ   1��ʾ�û�������Ϣ
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
	messagetype             number  default(0),                       --0��ʾ��Ŀ������Ϣ  1��ʾ�û�������Ϣ
	column_audit_rule    	varchar2(300)		not null,
	audittype               number default(0),                        --������˷�ʽ 0-���û����ƶ�����˹���   1-����ɫ������˹���
	createdate           	date			not null,
	lastupdated          	date			not null,
	creator              	varchar2(30)		not null,
	editor               	varchar2(30)		not null,
        primary key (id)
);

--�����롱��ϵ���ֵ���˲���·��
create table tbl_column_auditing_process (
	id                   	number             	not null,
	rulesid             	number             	not null,
	subrules_by_or          varchar2(300),                            --�ֽ���OR��˹���
	createdate           	date			not null,
	lastupdated          	date			not null,
	creator              	varchar2(30)		not null,
	editor               	varchar2(30)		not null,
        primary key (id)
);


create table tbl_article (
	id                   	number             	not null,           --����
	columnid             	number             	not null,           --����������ĿID
	siteid                  number                  not null,           --��������վ��ID     վ��ID=1
	sortid               	number,                                     --����������ţ���Ȼ���к�
	maintitle            	varchar2(600)   	not null,           --���±���
	vicetitle            	varchar2(600),                              --���¸�����
	summary              	varchar2(1000),                             --��������
	keyword              	varchar2(200),                              --���¹ؼ���
	source               	varchar2(600),                              --������Դ
	content              	long,                                       --��������
	emptycontentflag     	number(1)		not null,           --���������Ƿ�Ϊ��  �����ϴ��ļ�����Ϊ1 ��������Ϊ0
	author               	varchar2(600),                              --��������
	articleversion       	number,                                     --���°汾��
	publishtime          	date			not null,           --���·���ʱ��
	createdate           	date			not null,           --���´���ʱ��
	lastupdated          	date			not null,           --��������޸�ʱ��
	dirname              	varchar2(200)		not null,           --����·��
	filename             	varchar2(60),                               --�ļ�����
	editor               	varchar2(30)        	not null,           --�༭����
	creator                 varchar2(30)        	not null,           --��Ϣ����������
	status               	number(1)   		not null,           --����״̬      0-����   1-δ��
        topflag                 number(1)               default(0),         --�ö���־��1��ʾ�ö� 0-����˳��
	redflag                 number(1)               default(0),         --Ʈ���־��1��ʾƮ�� 0-����
	boldflag                number(1)               default(0),         --�Ӻڱ�־��1��ʾ�Ӻ� 0-����
	doclevel             	number                 	not null,           --������Ȩ��
	vicedoclevel		number,                                     --���¸�Ȩ��
	pubflag              	number(1)   		not null,           --���·�����־λ    0-�¸�         1-������
	auditflag            	number(1) 		not null,           --������˱�־λ    1-����         0-������
	subscriber           	number(1)   		not null,           --���¶���          0-��������   1-������
	lockstatus           	number(1) 		not null,           --����������־λ    0-δ����       1-����
	lockeditor           	varchar2(30),                               --����������
	auditor              	varchar2(200),                              --��������
	ispublished          	number(1) 		not null,           --�����Ƿ�������������
	relatedartid         	varchar2(40),                               --δ��
	indexflag            	number(1)		not null,           --�����Ƿ񱻼��뵽ȫ���������ݿ�
	isjoinrss		number(1)		not null,           --�����Ƿ����RSS
	clicknum		number			not null,           --���±��������
	referID			number			not null,           --δ��
        multimediatype          number(1)               default(0),         --��ý�����±�־0-���Ƕ�ý������ 1-�Ƕ�ý������	
	modelID			number			not null,           --����ʹ�õķ���ģ��
	articlepic		varchar2(600),                              --����ʹ�õ���ЧͼƬ
	changepic               number                  default(0),         --����ͼƬ��Ч����
	saleprice            	number(9,2),                                --��������ʹ��  ��Ʒ�ۼ�
	inprice              	number(9,2),                                --��������ʹ��  ��Ʒ����
	marketprice          	number(9,2),                                --��������ʹ��  ��Ʒ�г���
	vipprice          	number(9,2),	                            --��������ʹ��  ��ƷVIP��
	stocknum             	number,                                     --��������ʹ��  ��Ʒ�����
	weight               	number,                                     --��������ʹ��  ��Ʒ����
	brand                	varchar2(50),                               --��������ʹ��  ��ƷƷ��
	pic                  	varchar2(30),                               --��������ʹ��  ��ƷСͼƬ
	bigpic               	varchar2(30),                               --��������ʹ��  ��Ʒ��ͼƬ
	score                   number,                                     --��������ʹ��  ��Ʒ����
	voucher                 number(9,2),                                --��������ʹ��  ����ȯ
	urltype		        number(1)               default(0),         --δ��
	defineurl		varchar2(200),                              --�����Զ���URL
	notearticleid           int                     default(0),         --�������ƪ������������������µ����ݣ��������µ����ݿ����Ǳ�ҳ��һ����ǩ������
	wordsnum		number                  default(0),         --������������
	downfilename	        varchar2(100),                              --δ��
	beidate                 date,                                       --δ��    
        salesnum                number,                                     --��Ʒ��������
        commentnum              int                     default(0),         --�������۵�����
	fromsiteid              int                     default(0),         --��ʶץȡ��Ϣ����Դվ��
	sarticleid              varchar2(50),                               --ץȡ������Դ��վ��ID 
        md5title                varchar2(35),                               --���±����MD5��ʽժҪ
        mediafile               varchar2(500),                              --��ý���ļ�����
	processofaudit          int                     default(0),         --���������ִ�е�����˲���
	t1                      number                  default(0),         --����1
	t2                      number                  default(0),         --����2
	t3                      number                  default(0),         --����3
	t4                      number                  default(0),         --����4
	t5                      number                  default(0),         --����5
	deptid                  varchar2(50),                               --���·����Ĳ���ID
	orgid                   number,                                     --����������֯ID
	companyid               number,                                     --����������˾ID
	sign                    varchar2(32),                               --���µ�����ǩ�������������Ϊ���±���+ID������ǩ����ץȡ����Ϊ���µ�URL����ǩ��
        sourceurl               varchar2(1000),                             --���ɼ���Ϣ��URL
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

create table tbl_turnpic (                                                   --ͼƬ�ֻ���
        id                  number not null,
        articleid           number not null,
	sortid              int,                                             --ͼƬ��ʾ˳��
	mediaurl            varchar2(500),                                   --��ý���ļ�·��
        smallpic1url        varchar2(500),                                   --СͼƬ1URL
	smallpic2url        varchar2(500),                                   --СͼƬ2URL
	smallpic3url        varchar2(500),                                   --СͼƬ3URL
	latitude            number(38,15),                                   --ͼƬ�ľ���
	longitude           number(38,15),                                   --ͼƬ��γ��
        picname             varchar2(100),
        createdate          date default sysdate not null,
        notes               varchar2(2000),
	lib1                varchar2(100),                                    --��ǩ1
        lib2                varchar2(100),                                    --��ǩ2
        lib3                varchar2(100),                                    --��ǩ3
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
        keywordnum              int                     default 0,      --�ؼ��ʸ���
        flag                    smallint                default 0,      --1��ѡ������ڹؼ����б���        
        url			varchar2(200),
        primary key (id)
);

create table tbl_multimedia
(
        id                      number                      not null,        --����
        siteid                  number                      not null,        --վ��id 
        articleid               number                      default(0),      --����id
        dirname                 varchar2(100)               not null,        --��Ŀ·��
        filepath                varchar2(100)               not null,        --�ļ�·��
        oldfilename             varchar2(100)               not null,        --ԭ�ļ�����
        newfilename             varchar2(100)               not null,        --���ļ�����
        encodeflag              number      default(0),                      --�ļ�ת����־0-δת�� 1-�Ѿ�ת��
        infotype                smallint    default(0),                      --0�������ݵ�ͼƬ    1��ҵ������ϢͼƬ    2վ�������ϢͼƬ
        createdate              date default sysdate        not null,        --����ʱ��
        primary key(id)
);

create table tbl_deleted_article (
        siteid                  int                not null,                 --վ��ID
	columnid                int                not null,                 --��ĿID
        articleid               int                not null,                 --����ID
	sitename                varchar2(100),                               --վ������
	acttype                 smallint           default    0              --0��ʾ��ɾ��������   1��ʾ���޸ĵ�����        
);

create table tbl_relatedartids                                               --ҳ������ļ���
(
        ID                      number                         not null,     --����ID
        cname                   varchar2(100)                  not null,     --������������
        pageid                  number                         not null,     --����ID
        content                 long,                                        --��������
	dirname                 varchar2(255),                               --����·��
        contenttype             number    default(1),                        --1�ϴ��ļ�  2ͼƬ�ļ� 3ȫ��ͼƬ�ļ� 4��ý���ļ� 5htmlҳ��
        filename                varchar2(200),                               --�����ļ�����
        summary                 varchar2(250),                               --�����ļ�����
        editor                  varchar2(50),                                --�ϴ���
        createdate              date,                                        --������������
	lastupdate              date,                                        --
	primary key (id)        
);

create table tbl_recommend_article
(
        id                      number                      not null,        --����ID
        siteid		        number                      not null,        --վ��ID
        columnid	        number                      not null,        --��ĿID
        modelid                 number                      not null,        --ģ��ID
	markid                  number                      not null,        --���ID
	markname                varchar2(500),                               --�������
        articleid               number                      not null,        --����ID
	createdate              date,                                        --����ʱ��
	lastupdate              date,                                        --�޸�ʱ��
        primary key (id)
);

--��ץȡ�ļ�����ϢԴ��ص���Ϣ
create table tbl_article_sourceinfo
(
        id                            NUMBER                  not null,
        articleid                     NUMBER                  not null,
	maintitle            	      varchar2(600)           not null,           --���±���        
	md5title                      varchar2(35),                               --���±����MD5��ʽժҪ
	publishtime_from_source       date		      not null,           --�����ڱ�ץȡ��վ�ķ���ʱ��
	sourceid                      NUMBER,                                     --ץȡ������Դ��վ��ID 
        createdate                    date,                                       --��Ϣ��ץȡ��ʱ��
  	CONSTRAINT fk_sourcearticleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)        
);

create table tbl_attachment_class (
	columnid                number                  not null,            --��ĿID
	siteid               	number                  not null,            --վ��ID
	cname                	varchar2(50)        	not null,            --������������
	summary                	varchar2(500)        	not null,            --��������
	editor               	varchar2(30)            not null,            --�༭
	createdate           	date                    not null,            --��������
	lastupdated          	date			not null,            --�����޸�����
        primary key (columnid)
);

create table tbl_attachment_class_part (
	id                      number                  not null,            --����ID ��ĿID
	classid                 number                  not null,            --������������ID
	siteid               	number                  not null,            --վ��ID
	cname                	varchar2(50)        	not null,            --���������������
	ename                	varchar2(50)        	not null,            --�������Ӣ������
	cltype              	varchar2(10)            not null,            --��������
	editor               	varchar2(30)            not null,            --�༭
	createdate           	date                    not null,            --��������
	lastupdated          	date			not null,            --�����޸�����
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
        modelid                 number,                                    --ģ��ID
	picbrief                varchar2(500),                             --ģ��ͷͼ����
	pich                    int,                                       --ͷͼ�߶�
	picw                    int,                                       --ͷͼ���
	picurl                  varchar2(500),                             --ͷͼURL
	pichref                 varchar2(500),                             --ģ��ͷͼ������
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
	encodingflag            number,                                    --�ĵ������ʽ 1-utf-8  2-gb2312  3-gbk
	doctype                 varchar2(500),                             --�ĵ�����   HTML5  HTML4
	islocation              number          default(1),                --��Ŀ�洢λ����Ϣ
	contentshowtype		number(1),                                 --WAP��ʽ   HTML��ʽ
	isrss			number(1)       default(0) not null,
	getRssArticleTime	number(1),
	archivingrules		number(1)       default(0) not null,
	useArticleType		number(1)       default(0) not null,
	istype			number(1)	default(0) not null,
	publicflag              number(1)	default(0) not null,
	xmltype                 varchar2(3000),                           --���·���Ķ���
	userflag                number(1)       default(0),               --��Ŀ�����Ƿ�ֻ��ע���û��ɿ�  0--�����û� 1--ע���û�
	userLevel               number          default(0),               --�û�������µļ���
	shareflag               number          default(0),               --��Ŀ�Ƿ��Ǻ�������վ�������Ŀ
	articlecount            number          default(0),               --����Ŀ������������
        titlepic                varchar2(10),                             --����ͼƬ��С       80X120
	vtitlepic               varchar2(10),                             --������ͼƬ��С     80X120
	sourcepic               varchar2(10),                             --��ԴͼƬ��С       80X120
	authorpic               varchar2(10),                             --����ͼƬ��С       80X120
	contentpic              varchar2(10),                             --����ͼƬ��С       600X600
	specialpic              varchar2(10),                             --������ЧͼƬ��С   240X240
	productpic              varchar2(10),                             --��Ʒ��ͼƬ��С     480X480
	productsmallpic         varchar2(10),                             --��ƷСͼƬ��С     160X160
        ts_pic                  varchar2(10),                             --��ЧͼƬ�ߴ�       80X80
        s_pic                   varchar2(10),                             --СͼƬ�ߴ�         120X120
        ms_pic                  varchar2(10),                             --��СͼƬ�ߴ�       240X240
        m_pic                   varchar2(10),                             --����ͼƬ�ߴ�       360X360
        ml_pic                  varchar2(10),                             --�д�ͼƬ�ߴ�       480X480
        l_pic                   varchar2(10),                             --��ͼƬ�ߴ�         600X600
        tl_pic                  varchar2(10),                             --�ش�ͼƬ�ߴ�       800X800
        primary key (id)
);

insert into tbl_column (id,siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate,lastupdated,isdefineattr,hasarticlemodel,isaudited,isproduct,ispublishmore,languagetype,columndesc,xmltemplate,encodingflag,doctype) values(0,0,-1,0,'��ҳ','home','/','admin','html',sysdate,sysdate,0,0,0,0,0,0,'','',0,'');

--ץȡ��Ϣ�ּ�ؼ���
create table tbl_column_keywords (
	id                   	number             	not null,         --����������������ֶ�
        scolumnid               number                  not null,         --��ץȡ��Ϣ��ŵ���Ŀ
	tcolumnid               number                  not null,         --���Ϲؼ�����ƹ�������±��ּ��Ŀ����ĿID
	keywords                varchar2(500),                            --���õķּ�ؼ��ʣ��Զ��ŷָ�
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
	ispublished             number     default(0),             --����Ƿ񷢲�����0--δ  1--��
        articlenum              int        default(0),                        --���һ�η������µ����������������б���������
	primary key (id)
);

create table tbl_markdict (                                             --������������ֵ�
	chinesename          	varchar2(40)		not null,       --�����������
	markcode         	varchar2(10)            not null        --��Ǳ���
);

insert into tbl_markdict (chinesename,markcode) values('�����б�','1001');
insert into tbl_markdict (chinesename,markcode) values('�Ƽ������б�','1002');
insert into tbl_markdict (chinesename,markcode) values('�������б�','1003');
insert into tbl_markdict (chinesename,markcode) values('�ֵ������б�','1004');
insert into tbl_markdict (chinesename,markcode) values('��Ŀ�б�','1005');
insert into tbl_markdict (chinesename,markcode) values('����Ŀ�б�','1006');
insert into tbl_markdict (chinesename,markcode) values('�ȵ�����','1007');
insert into tbl_markdict (chinesename,markcode) values('�������','1008');
insert into tbl_markdict (chinesename,markcode) values('������','1009');
insert into tbl_markdict (chinesename,markcode) values('��ҳ���','1010');
insert into tbl_markdict (chinesename,markcode) values('����·��','1011');
insert into tbl_markdict (chinesename,markcode) values('Ӣ��·��','1012');
insert into tbl_markdict (chinesename,markcode) values('��������','1013');
insert into tbl_markdict (chinesename,markcode) values('����������','1014');
insert into tbl_markdict (chinesename,markcode) values('�����ļ�','1015');
insert into tbl_markdict (chinesename,markcode) values('������','1016');
insert into tbl_markdict (chinesename,markcode) values('��һƪ','1017');
insert into tbl_markdict (chinesename,markcode) values('��һƪ','1018');
insert into tbl_markdict (chinesename,markcode) values('��Ƶ����','1019');
insert into tbl_markdict (chinesename,markcode) values('����ʱ��','1020');
insert into tbl_markdict (chinesename,markcode) values('ͼƬ�Ϲ�','1021');
insert into tbl_markdict (chinesename,markcode) values('ͼƬ�¹�','1022');
insert into tbl_markdict (chinesename,markcode) values('ͼƬ�ҹ�','1023');
insert into tbl_markdict (chinesename,markcode) values('ͼƬ���','1024');
insert into tbl_markdict (chinesename,markcode) values('ͼƬ�õ�','1025');
insert into tbl_markdict (chinesename,markcode) values('Сͼ������ͼ','1026');
insert into tbl_markdict (chinesename,markcode) values('����ͼƬ��Ч','1027');
insert into tbl_markdict (chinesename,markcode) values('��Ϣ����','1028');
insert into tbl_markdict (chinesename,markcode) values('��������','1029');
insert into tbl_markdict (chinesename,markcode) values('��Ϣ����','1030');
insert into tbl_markdict (chinesename,markcode) values('�û�����','1031');
insert into tbl_markdict (chinesename,markcode) values('�û�ע��','1032');
insert into tbl_markdict (chinesename,markcode) values('�û���¼','1033');
insert into tbl_markdict (chinesename,markcode) values('�޸�ע��','1034');
insert into tbl_markdict (chinesename,markcode) values('���ﳵ','1035');
insert into tbl_markdict (chinesename,markcode) values('��������','1036');
insert into tbl_markdict (chinesename,markcode) values('��������','1037');
insert into tbl_markdict (chinesename,markcode) values('������ѯ','1038');
insert into tbl_markdict (chinesename,markcode) values('������ϸ��ѯ','1039');

--��Ŀģ��ͱ�ǵ���ر�
CREATE TABLE TBL_TemplateMark (
	ID                   number    NOT NULL,                   --���
	siteid               number    not null,                   --վ��id
        tid                  number    not null,                   --ģ��id
        mid                  number    not null,                   --��ģ���ϵı��id
	columnid             number    not null,                   --ģ��������ĿID
	CONSTRAINT pk_tmid PRIMARY KEY (ID),
	CONSTRAINT fk_templateMarkID FOREIGN KEY (tid) REFERENCES  tbl_template(ID) ON DELETE CASCADE
);

--�������Ŀ����ر�
CREATE TABLE TBL_MarkColumn (
	ID                   number    NOT NULL,                   --���
	siteid               number    not null,                   --վ��id
        mid                  number    not null,                   --���id
        cid                  number    not null,                   --������õ���Ŀid
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
	encodeflag              number          default(1),                       --�ĵ������ʽ 1-utf-8  2-gb2312  3-gbk
	doctype                 varchar2(500),                                    --�ĵ�����   HTML5  HTML4
	copycolumn	        number(1)       default(0) 	    not null,     --
	becopycolumn	        number(1)       default(0) 	    not null,     --
	pusharticle	        number(1)       default(0) 	    not null,     --�����ļ�����
	movearticle	        number(1)       default(0) 	    not null,     --�ƶ��ļ�����
        sitetype                number             default(0),                    --�����͹���վ�������,�����������վ��������վ�ȵ�
	sitepic                 varchar2(200),                                    --վ����ҳͼƬ
	sitevalid               number        default(0),                         --0-������վ 1-��ͨ��վ 2-������վ
	sitelogo                varchar2(200),                                    --վ��LOGO
	banner                  varchar2(200),                                    --վ����BANNER
	navigator               number             default(0),                    --վ�㵼����ʽ
	samsiteid               number             default(0),                    --����վ��ID
	scores                  number          default(0),                       --���ֺͻ��Ҷһ�����
	sharetemplatenum        number             default(0),                    --��������վ���ģ���׺�
	managesharesite         number        default(0),                         --������վ����Ϣ   0-������  1-����
        copyright               varchar2(1000),	                                  --��վ��Ȩ
        titlepic                varchar2(10),                                     --����ͼƬ��С       80X120
	vtitlepic               varchar2(10),                                     --������ͼƬ��С     80X120
	sourcepic               varchar2(10),                                     --��ԴͼƬ��С       80X120
	authorpic               varchar2(10),                                     --����ͼƬ��С       80X120
	contentpic              varchar2(10),                                     --����ͼƬ��С       600X600
	specialpic              varchar2(10),                                     --������ЧͼƬ��С   240X240
	productpic              varchar2(10),                                     --��Ʒ��ͼƬ��С     480X480
	productsmallpic         varchar2(10),                                     --��ƷСͼƬ��С     160X160
        ts_pic                  varchar2(10),                                     --��ЧͼƬ�ߴ�       80X80
        s_pic                   varchar2(10),                                     --СͼƬ�ߴ�         120X120
        ms_pic                  varchar2(10),                                     --��СͼƬ�ߴ�       240X240
        m_pic                   varchar2(10),                                     --����ͼƬ�ߴ�       360X360
        ml_pic                  varchar2(10),                                     --�д�ͼƬ�ߴ�       480X480
        l_pic                   varchar2(10),                                     --��ͼƬ�ߴ�         600X600
        tl_pic                  varchar2(10),                                     --�ش�ͼƬ�ߴ�       800X800
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
       sitegroupid              number                             not null,     --վ����ID��������վID
       siteid                   number                             not null,     --���ԱID
       sitename                 varchar2(100),                                --���Ա����
       createdate               date,                                         --��������
       primary key(id)
);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(1,36,931,'wangjian.coosite.com',sysdate);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(2,36,933,'petersong103.coosite.com',sysdate);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(3,36,934,'wangjianjilin1.coosite.com',sysdate);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(4,36,971,'bjchaohong.coosite.com',sysdate);
insert into tbl_sitegroup(id,sitegroupid,siteid,sitename,createdate) values(5,36,885,'ssdemotest.coosite.com',sysdate);


--������Ŀ֮���������µĶ�Ӧ��ϵ��������õ�����
--artfrom 0-��Ŀ���崦���õ����ã�1-����¼��ʱ���õ�����    2--��������
create table tbl_refers_article (
        id                      number                not null,
	articleid               number                not null,          --����ID
        siteid                  number                not null,          --����ԭվ��ID
	columnid       	        number 	              not null,          --Ŀ����Ŀ��ID
	scolumnid               number                not null,          --Դ��ĿID
	columnname	        varchar2(100),                           --Ŀ����Ŀ����������
	orders		        number	      default(0),
	createdate	        date,
	title		        varchar2(600),                           --��Ŀ����Ŀ�еı�������
	status                  number   	      not null,          --Ŀ����Ŀ�е�״̬
	pubflag                 number   	      not null,          --�����Ƿ񱻷���
	auditflag               number    	      not null,          --�����Ƿ���Ҫ���
	usearticletype          number,                                  --������������     0--��ַ����   1--��������
	refers_column_status    number,                                  --������Ŀ״̬
	tsiteid                 number,                                  --Ŀ��վ��ID
	artfrom                 number,                                  --Ӧ�����·�ʽ
	primary key(id)
);

--��Ŀ������ѡ��ı��������µ���Ŀ
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
	targetid   		number 			not null,       --��Ҫ���������µ�ID����Ŀ��ID
	type       		number,	          	                --1����,2��Ŀģ��,3��ҳģ��,4���
	status     		number(1),
	createdate    		date,
	publishdate   		date,
	uniqueid   		varchar2(50),                           --���Դ洢ע���Ա��ID	
	columnid		number			not null,
	title			varchar2(200),
        errcode                 number           default(0),            --��ҵ��������ʱ����������,Ĭ��0��ʾ�ɹ�����
        errmsg                  varchar2(500),                          --���ִ���ʱ�洢������Ϣ
        errnum                  number      default(0),                 --�洢������������·����Ĵ�����������·���3��
        priority                number      default(0),                 --��ҵ�����ȼ� 0-��ʾһ�����ȼ�
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
	latitude                number(38,15),                              --����
	longitude               number(38,15),                              --γ��
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

-- ��֯�ܹ���
create table tbl_organization
(
       id                      number not null,              --����ID
       siteid                  number,
       parent                  number(19),                   --��ID
       cotype                  number default 0,             --�ڵ����� 1--�ӹ�˾   2--����
       customerid              number,                       --�ͻ�ID����Ӧ��վ��ID
       orderid                 number,                       --�ڵ���ͬ���е����
       nodeid                  number(19),                   --�ڵ��Ӧ�ӹ�˾������
       status                  number default 1,             --״̬ 0 ��Ч 1 ��Ч
       name                    varchar2(255) not null,       --�ڵ�����
       ename                   varchar2(255),                --�ڵ�Ӣ������
       llevel                  number default 1 not null,    --��֯�ṹ�㼶
       orgcode                 varchar2(50),                 --��֯�ڵ����
       isleaf                  number(1) default 0,          --�Ƿ���Ҷ�ӽ�ռ��0Ϊ��Ҷ�ӽڵ㣬1ΪҶ�ӽڵ�
       lastupdate              date default sysdate,         --����޸�����
       createdate              date default sysdate,         --��������
       createuser              varchar2(50),                 --������
       updateuser              varchar2(50),                 --�޸���
       primary key(id)
);

--��˾����
create table tbl_companyclass (
	id                   	number                     not null,
	siteid               	number                     not null,
	parentid             	number      		not null,
	orderid              	number                     not null,
	cname                	varchar2(50)        	not null,
	ename                	varchar2(50)        	not null,
        summary                 varchar2(250),                                   --��˾������Ϣ����
	dirname              	varchar2(200)           not null,
	editor               	varchar2(30)            not null,
	extname              	varchar2(10)            not null,
        companynum              int    default(0),
	createdate           	date                    not null,
	lastupdated          	date			not null,
	primary key(id)
);

insert into tbl_companyclass values(0,0,-1,0,'��˾����','company','','/','admin','html',0,sysdate,sysdate);
--insert into tbl_companyclass values(1,40,0,0,'��˾����','www.bjrab.gov.cn','','/','admin','shtml',0,sysdate,sysdate);

--��˾��Ϣ��
create table tbl_companyinfo(
       ID                                        number,                            --����
       orgid                                     number,                            --��˾������֯ID
       SITEID                                    number,                            --��˾վ��ID 
       COMPANYNAME                               VARCHAR2(100),                     --��˾����
       COMPANYADDRESS                            VARCHAR2(100),                     --��˾���ڵ�ַ
       COMPANYPHONE                              VARCHAR2(50),                      --��˾��ϵ�绰
       COMPANYFAX                                VARCHAR2(50),                      --��˾��ϵ����
       COMPANYWEBSITE                            VARCHAR2(100),                     --��˾վ������
       COMPANYEMAIL                              VARCHAR2(100),                     --��˾email��ַ
       POSTCODE                                  VARCHAR2(20),                      --��˾��������
       companyclassid                            number,                            --��ҵ��������       
       CLASSIFICATION                            VARCHAR2(200),                     --��˾������������
       SUMMARY                                   VARCHAR2(1000),                    --��˾���� 
       COMPANYPIC                                VARCHAR2(200),                     --��˾ͼƬ
       PUBLISHFLAG                               number,                            --�������
       status                                    smallint default(0),               --״̬  0--���ⷢ��   1-�����ⷢ��
       COUNTRY                                   varchar2(12),                      --��˾���ڹ���
       PROVINCE                                  varchar2(12),                      --��˾����ʡ��
       CITY                                      varchar2(12),                      --��˾������
       ZONE                                      varchar2(12),                      --��˾��������
       TOWN                                      varchar2(12),                      --��˾��������
       VILLAGE                                   varchar2(12),                      --��˾���ڴ�
       COMPANYLATITUDE                           number(38,15),                     --��˾���ڵصľ���
       COMPANYLONGITUDE                          number(38,15),                     --��˾���ڵص�γ��
       MPHONE                                    varchar2(12),                      --��ϵ���ƶ��绰
       BIZPHONE                                  varchar2(12),                      --��ϵ�˵绰
       CONTACTOR                                 varchar2(12),                      --��ϵ������
       SAMSITEID                                 number,                            --����վ��ID
       COMPANYLEVEL                              number,                            --��˾����
       qq                                        varchar2(50),                      --��˾QQ
       weixin                                    varchar2(50),                      --��˾΢��
       weibo                                     varchar2(50),                      --��˾΢��
       createdate                                date,                              --��������
       updatedate                                date,                              --�޸�����
       createuser                                varchar2(50),                      --������
       COMPCODE                                  varchar2(50),                      --��˾�ڲ�����
       LEGAL                                     varchar2(500),                     --��˾����
       COMPSHORTNAME                             varchar2(200),                     --��˾������
       COMPENNAME                                varchar2(200),                     --��˾Ӣ������
       creditcode                                varchar2(100),                     --��˾ͳһ���ñ���
       relatetype                                number,                            --��˾����  1���ܹ�˾��2���ֹ�˾��3���ӹ�˾��4���عɹ�˾��5���ιɹ�˾��6����פ������7������
       ALIASFLAG                                 number,                            --��������һ����˾�ı���,1��ʾ��˾�����ƣ�0��ʾ����
       primary key(id)
);

--��ҵ��������
create table tbl_department (
       id                      number            not null,
       orgid                                     number,                            --��˾������֯ID
       companyid               number            not null,                          --������˾ID
       siteid                  number            not null,                          --����վ��ID
       cname                   varchar2(100)     not null,                          --������������
       ename                   varchar2(100),                                       --����Ӣ������
       telephone               varchar2(12),                                        --������ϵ�绰
       manager                 varchar2(50),                                        --���ž���
       vicemanager             varchar2(50),                                        --���Ÿ�����
       leader                  varchar2(50),                                        --���������쵼
       lastupdate              date default sysdate,                                --����޸�����
       createdate              date default sysdate,                                --��������
       createuser              varchar2(50),                                        --������
       EMAIL                   VARCHAR2(200),                                       --������ϵ�ʼ� 
       SHORTNAME               VARCHAR2(200),                                       --���ż��
       primary key(id)
);          

create table tbl_classofcompany (
       id                      number            not null,
       siteid                  number            not null,                          --����վ��ID
       companyid               number            not null,                          --��˾ID
       classid                 number            not null,                          --��˾��������ID
       lastupdate              date default sysdate,                                --����޸�����
       createdate              date default sysdate,                                --��������
       createuser              varchar2(50),                                        --������
       primary key(id)
);          

--������վ����
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
insert into tbl_websiteclass values(0,0,-1,0,'��ַ����','website','/','admin','html','',0,sysdate,sysdate);

--��վ������Ϣ
create table tbl_websiteinfo(
       id                                        number,                            --����
       siteid                                    number,                            --����վ��
       websitename                               varchar2(100),                  --��˾����
       websiteurl                                varchar2(200),                  --��˾���ڵ�ַ
       summary                                   varchar2(200),                  --��˾��ϵ�绰
       editor                                    varchar2(50),                   --�༭
       pubflag                                   number,                       --�������
       createdate                                date,                           --��������
       updatedate                                date,                           --�޸�����
       primary key(id)
);

--�����չ����
create table tbl_workdayinfo
(
    days                       varchar2(50) not null,
    siteid                     number     default 0 not null,
    worddayflag number         default 0 not null, --0������1�ǹ�����
    primary key(days)
); 

--LOG����ϵͳ
create table tbl_pv_detail(
        id                 number                        not null,        --����ID
	siteid             int                           not null,        --վ��ID
	urlname            varchar2(500),                                 --URL��ַ
	url_cn_name        varchar2(500),                                 --��������
	pageview           int,                                           --ҳ�������
	logdate            date,                                          --logʱ��
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
--ģ�����ǹ�ϵid
create sequence tbl_rtm_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
--�������Ŀ��ϵid          
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


--������ע���
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

--��������
create table TBL_PROGRAM_LIBRARY
(
        ID            NUMBER not null,               --������������
        P_TYPE        NUMBER,                        --��������
        POSITION      NUMBER,                        --JSPҳ���е�λ��
        L_TYPE        NUMBER,                        --��������
        EXPLAIN       VARCHAR2(100),                 --����˵��
        NOTES         VARCHAR2(500),                 --����ע��
        PROGRAM       LONG,                          --������
        primary key(id)
);
CREATE SEQUENCE tbl_program_id START WITH 1;

create table TBL_PROGRAM_POSITION
(
        ID       NUMBER not null,                    --����
        POSITION VARCHAR2(50),                        --λ��˵��
        primary key(id)
);

insert into tbl_program_position (id,position) values(1,'����ͷ');
insert into tbl_program_position (id,position) values(2,'��ʾ��');
insert into tbl_program_position (id,position) values(3,'ҳβ');

create table TBL_PROGRAM_TYPE
(
        ID     NUMBER not null,                      --����
        P_TYPE VARCHAR2(50),                         --��������˵��
        primary key(id)
);

insert into tbl_program_type (id,p_type) values(11,'��Ϣ����');
insert into tbl_program_type (id,p_type) values(12,'���ﳵ');
insert into tbl_program_type (id,p_type) values(13,'��������');
insert into tbl_program_type (id,p_type) values(14,'��������');
insert into tbl_program_type (id,p_type) values(15,'������ѯ');
insert into tbl_program_type (id,p_type) values(16,'��Ϣ����');
insert into tbl_program_type (id,p_type) values(17,'�û�����');
insert into tbl_program_type (id,p_type) values(18,'�û�ע��');
insert into tbl_program_type (id,p_type) values(19,'�û���¼');
insert into tbl_program_type (id,p_type) values(20,'������ϸ��ѯ');
insert into tbl_program_type (id,p_type) values(21,'�û�����');
insert into tbl_program_type (id,p_type) values(22,'�޸�ע��');
insert into tbl_program_type (id,p_type) values(24,'��ͼ��ע');


--������������

--��վǰ̨Ӧ�ñ�
--�û�ע���
create table tbl_userinfo(
        id                   number               not null,         --����
  	siteid               number               not null,         --վ��ID
  	memberid             varchar2(50)         not null,         --�û�ID
  	username             varchar2(30),                          --�û���������
  	password             varchar2(20),                          --�û�����
        role                 varchar2(20),                          --�û���ɫ
  	grade                varchar2(50),                          --�û�����
  	company              varchar2(500),                         --������λ
	department           varchar2(200),                         --���ڵ�λ����                 
  	unitpcode            varchar2(10),                          --��λ��������
  	unitphone            varchar2(20),                          --��λ�绰
  	linkman              varchar2(30),                          --��ϵ��
  	nation               varchar2(30),                          --����
  	degree               varchar2(30),                          --�Ļ��̶�
  	idno                 varchar2(19),                          --���֤����
  	country              varchar2(50),                          --����
  	province             number,                                --ʡ��
  	city                 varchar2(20),                          --���ڳ���
  	street               varchar2(100),                         --���ڽֵ�
  	address              varchar2(200),                         --��ϵ��ַ
  	postalcode           varchar2(10),                          --��ϵ��ַ��������
  	phone                varchar2(20),                          --סլ�绰
  	mobilephone          varchar2(50),                          --�ƶ��绰
  	fax                  varchar2(20),                          --����
  	email                varchar2(100),                         --�����ʼ�
  	homepage             varchar2(100),                         --������ҳ
  	usertype             number,                                --�û�����
  	valid                number,                                --�Ƿ���Ч
  	sex                  varchar2(10),                          --�û��Ա�
  	oicq                 varchar2(50),                          --�û�QQ����
  	birthday             date,                                  --��������
  	image                varchar2(50),                          --�û�ͷ��
  	sign                 varchar2(50),                          --�û�ǩ��
  	scores               number             default(0),         --�û���������
  	weekscore            number             default(0),         --�û����ܻ�������
  	monthscore           number             default(0),         --�û����»�������
  	meilizhi             number             default(0),         --����ֵ
  	zhuanjia             number             default(0),         --ר�ұ�ʶλ     0-��ר��    1-�ʰ�ר��
  	loginnum             number,                                --��¼����
  	theonline            varchar2(10),                          --�����û�
  	renzheng             varchar2(50),                          --
  	createdate           date,                                  --����ʱ��
  	stationtype          varchar2(8),                           --վ̨���
  	entitytype           varchar2(1),                           --����/����
  	stationaddr          varchar2(80),                          --վ̨��ַ
  	opedegree            varchar2(20),                          --����֤�ȼ�
  	opecode              varchar2(20),                          --����֤�����
  	callsign             varchar2(20),                          --����
  	memo                 varchar2(512),                         --��ע    
  	info                 varchar2(512),                         --����˵������
  	remark               varchar2(500),                         --��������
  	sundaytime           varchar2(24),                          --
  	lockflag             number default 0 not null,             --�Ƿ�����
  	primary key(id)
);

create table tbl_userinfo_extendattr (
	id                   	number             	not null,
	userid            	number             	not null,
	ename                	varchar2(50)		not null,
	cnname                  varchar2(50)            not null,
	type                 	number(1)		not null,             --1 �ַ��� 2-����  3-������ 4 �����ͣ��ַ�����ʽ���棩
	stringvalue          	varchar2(250),
	numericvalue         	number,
	floatvalue           	number(9,2),
	CONSTRAINT fk_userID FOREIGN KEY (userid) REFERENCES  TBL_USERINFO(ID) ON DELETE CASCADE,
        primary key (id)
);

--��Ȩ�û��ܹ��鿴����Ŀ
create table column_authorized(
        id                  int             	not null,
        siteid              int                 not null, 
        columnid            int                 not null,
        targetid            int                 not null,  --�û�ID�����ǽ�ɫID
        type                smallint            default 0, --0��ʾtargetidΪ��ɫid, 1��ʾtargetidΪ�û�id
        createdate          date,
        primary key(id)
);

--����
CREATE TABLE su_survey  (																--�����
	 id           number NOT NULL ,   			--����
	 surveyname   varchar2(200)   NULL ,			--��������
	 notes        varchar2(2000)   NULL ,			--��������
	 createtime   date  default sysdate not NULL ,		--����ʱ��
	 siteid       number default 0 not null,		--վ��id	
	 useflag      number default 0 not null,		--��Ч��־ 0-��Ч1-��Ч
        primary key (id) 
);

CREATE TABLE  su_answer  (					--�𰸱�
	 id     number NOT NULL ,				--����
	 userid   	number  NULL ,				--�û�id
	 sid   	number  NULL ,					--����id
	 qid   	number  NULL ,					--����id	
	 answers   varchar2(200)  NULL ,			--��
	 other    varchar2(200)  NULL,				--������ֵ
        primary key (id)
);

CREATE TABLE  su_danswer  (					--����𰸱�
	 id     number NOT NULL ,				--����
	 qid   number default 0 NOT NULL ,			--����id
	 qanswer   varchar2(200)  NULL ,			--�����
	 picurl   varchar2(100)  NULL,				--ͼƬ��ַ
        primary key (id) 
);

CREATE TABLE  su_dquestion  (					--���ⶨ���
	 id     number NOT NULL ,				--����
	 sid   number  NOT NULL ,				--����id
	 qname   varchar2(500)  NULL ,				--��������
	 qtype   number  NULL ,					--��������
	 qmust   number  NULL ,					--�Ƿ��ѡ
	 nother   number  NULL ,				--�Ƿ���Ҫ�����	
	 atype   number  NULL,					--������
        primary key (id) 
);

create table SU_DEFINEUSERINFO
(
  ID         NUMBER not null,
  SID        NUMBER default 0,                         --����ID
  USERNAME   VARCHAR2(100),                            --�ύ�����û���
  PHONE      VARCHAR2(100),                            --�ύ�����û��绰
  CREATEDATE DATE default sysdate,
  primary key(id)
);

create sequence SU_DEFINEUSERINFO_ID minvalue 1 maxvalue 999999999999999999999999999 start with 21 increment by 1 cache 20;

--�û������μӻ
CREATE TABLE tbl_join_activity
(
	id          number,                                 --����
	siteid      number,                                 --վ��ID
	activiid    number,                                 --�ID
	userid      varchar2(50),                           --��¼�û�ID
	username    varchar2(50),                           --�û���ʵ����
	email       varchar2(100),                          --�û��ĵ����ʼ�
	phone       varchar2(20),                           --�û��绰
	mphone      varchar2(20),                           --�û��ֻ�
	address     varchar2(500),                          --�û�סַ
	postcode    varchar2(10),                           --��������
	company     varchar2(200),                          --�û�������λ����
	title       varchar2(200),                          --�����
	auditflag   smallint         default 0,             --�Ƿ���׼�μ�
	createtime  date             default sysdate,       
	CONSTRAINT pk_feedback_id PRIMARY KEY (ID)                                        
);

--��б�
CREATE TABLE tbl_activitys
(
	id          number,                                 --����
	siteid      number,                                 --վ��ID
	title       varchar2(200),                          --�����
	createtime  date             default sysdate,       --��������
	CONSTRAINT pk_activitys_id PRIMARY KEY (ID)                                        
);

--�ҵĵ�ַ��
CREATE TABLE  tbl_userinfos (
	ID            number   not null,                            --����
	USERNAME      varchar2(20),                              --�û�����
	ADDRESS       varchar2(200),                             --��ַ
	EMAIL         varchar2(100),                             --�����ʼ�
	SEX           number,                                       --�Ա�
	MOBILEPHONE   varchar2(20),                              --�ƶ��绰
	POSTCODE      varchar2(10),                              --�ʱ�
	PHONE         varchar2(20),                              --�绰
	CORPORATION   varchar2(500),                             --��˾
	FAX           varchar2(20),                              --����
	MEMBERID      varchar2(50),                              --�û�id
	CREATETIME    number not null,                              --����ʱ��
	CONSTRAINT pk_userinfos_id PRIMARY KEY (ID)
);

--ÿ����ˮ�Ź����
create table tbl_lshbydate(
       cdate   date,
       lsh     varchar2(15)
);

--�û�����
CREATE TABLE TBL_LEAVEWORD
(
	id             number         not null,                    --����
  	siteid         number         not null,                    --վ��id
  	userid         varchar2(50),                               --��¼�û�ID,���û�е�¼����¼IP
	flag           number  default 0,                          --�Ƿ�����վ����ʾ��1--��ʾ 0--����ʾ
  	title          varchar2(100),                              --���Ա���
  	content        varchar2(4000),                             --��������
  	writedate      date,                                       --��������
  	company        varchar2(200),                              --��˾
  	linkman        varchar2(100),                              --��ϵ��
  	links          varchar2(500),                              --��ϵ��ʽ
  	zip            varchar2(20),                               --����
  	email          varchar2(100),                              --�����ʼ�
  	phone          varchar2(20),                               --�绰
  	formid         number,                                     --�û����Ա�ID
  	departmentid   number,                                     --��Ϣ�ַ����Ĳ���ID
	retcontent     varchar2(2000),                             --�ظ�������
  	auditor        varchar2(50),                               --�����Ҫ��ˣ������������Ϣ
  	auditflag      number default(0),                          --0��ʾ������  1��ʾ��˽�����
  	processor      varchar2(50),                               --�������û�ID
  	mphone         varchar2(20),                               --�ƶ��绰
	columnid       number         default(0),                  --��ѯ������ඨ��
  	code           varchar2(12),                               --�û���ѯ��ˮ��   AyyyymmddXXX    ����ĸA��ʾ�ֳ�����  B��ʾ������ѯ C��ʾ�����ײ�
  	sex            smallint,                                   --�������Ա�
	valid          smallint default(0),                        --�����Ƿ���Ч 0--��Ч 1--��Ч
	validreason    varchar2(512),                              --������Ч��ԭ��
	datefromdept   date,                                       --���Żظ�����
	finalflag      smallint default(0),                        --���ջظ��û���־λ�����ظ������׼�������԰岿�Ź���Ա�����԰����Ա��֮����Ϊ1
	endtouser      date,                                       
	CONSTRAINT pk_leaveword_id PRIMARY KEY (ID)
);
create index leavemsgindex_forsiteid on tbl_leaveword(siteid);

create table tbl_leaveword_to_dept
(
	id             number         not null,                    --����
  	siteid         number         not null,                    --վ��id
  	departmentid   number,                                     --��Ϣ�ַ����Ĳ���ID
  	formid         number,                                     --�û����Ա�ID
  	lwid           number,                                     --�û�����ID
  	processor      varchar2(50),                               --�ش����û�ID
	flag           number  default 0,                          --�Ƿ��Ѿ��ش�1--�ش� 0--δ�ش�  	
  	content        varchar2(4000),                             --�ش�����
  	retdate        date,                                       --�ش�����
  	createdate     date default sysdate,                       --��������û�������
	CONSTRAINT pk_leaveword_to_dept_id PRIMARY KEY (ID)
);

--�û����Է������
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
        lwnum                   int    default(0),                                --ĳ�������������
	createdate           	date                       not null,
	lastupdated          	date			   not null,
	primary key(id)
);
insert into tbl_leavewordclass values(0,0,-1,0,'���Է���','lwsite','/','admin','html','',0,sysdate,sysdate);

create table tbl_leaveword_valid_reason
(
	id             number         not null,                    --����
  	siteid         number         not null,                    --վ��id
        content        varchar2(500)  not null,
        primary key(id)
);

insert into tbl_leaveword_valid_reason(id,siteid,content) values(1,40,'ȱ����ϵ��Ϣ');
insert into tbl_leaveword_valid_reason(id,siteid,content) values(2,40,'ѯ�����ⲻ����');


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


--��������ϵͳ
--ȫ����ң���
create table EN_COUNTRY
(
  id                number               not null,
  orderid           number,
  countryname       varchar2(20)         not null,
  valid             number               default 0,                   --1--ʹ��   0--ͣ��
  code              varchar2(6)
)

--ʡ�м���λ��22��ʡ��5����������4��ֱϽ�У�2���ر���������
create table EN_PROVINCE
(
  id                number               not null,
  orderid           number,
  countryid         number               default(86),
  provname          varchar2(20)         not null,
  emsfee            number,
  valid             number               default 0,                   --1--ʹ��   0--ͣ��
  code              varchar2(6)
)

--�ؼ�������λ��17��������30�������ݡ�283���ؼ��С�3���ˣ�
create table en_city
(
  id                number               not null,
  orderid           number,
  provid            number               not null,
  cityname          varchar2(30)         not null,
  valid             number               default 0,                   --1--ʹ��   0--ͣ��
  code              varchar2(6)
);

--���ؼ�������λ��1464���ء�117�������ء�374���ؼ��С�852����Ͻ����49���졢3�������졢1��������2��������
create table en_zone
(
  id               number                 not null,
  orderid          number, 
  cityid           number                 not null,
  zonename         varchar2(30)           not null,
  valid            number                 default 0,                   --1--ʹ��   0--ͣ��
  code             varchar2(6)
);

--����������λ��14677���硢1092�������硢19522����6152���ֵ����´���11����������181����ľ��1��������ľ��
create table en_town
(
  id               number                 not null,
  orderid          number,
  zoneid           number                 not null,
  townname         varchar2(30)           not null,
  valid            number                 default 0,                   --1--ʹ��   0--ͣ��
  code             varchar2(6)
);

--�塢������������λ��80717����������ίԱ�ᡢ623669������ίԱ�ᣩ
create table en_village
(
  id               number                 not null,
  orderid          number,
  townid           number                 not null,
  villagename      varchar2(30)           not null,
  valid            number                 default 0,                   --1--ʹ��   0--ͣ��
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


CREATE TABLE tbl_orders(                                                            --������
         id                             number                    not null,         --����
	 SITEID        			number,				            --վ��id
	 orgid                          number,                                     --������������
	 companyid                      number,                                     --����������˾
	 deptid                         number,                                     --������������
	 ORDERID       			number(38)                NOT NULL ,        --������
	 initorderid                    number(38)  default(0)    NOT NULL ,        --ԭʼ������(��=ԭ���ţ��ǲ�=0)
	 USERID        			number,	     			            --�û�id
	 NAME          			varchar2(50),		                    --�ջ�������
	 SEX           			number,				            --�Ա�
	 ADDRESS       			varchar2(1000),	                            --��ַ
	 POSTCODE      			varchar2(20),		                    --�ʱ�
	 PHONE         			varchar2(50),		                    --�绰
	 TOTALFEE      			number(9,2),		                    --�����ܶ�
	 DELIVERYFEE   			number(9,2),		                    --�ʼķ���
	 PAYFEE        			number(9,2),		                    --Ӧ������
	 VALID         			number      default(0),		
	 STATUS        			number                     NOT NULL ,	    --����״̬
	 BILLINGWAY    			number,				            --֧����ʽ
	 CITY          			varchar2(50),			            --����
	 RECEIVE       			number,				
	 NOUSE         			number      default(1),	                    --�����Ƿ���Ч 1-��Ч 0-��Ч
	 PROVINCE      			varchar2(50),			            --ʡ��	 
	 zone          			varchar2(50),			            --����
	 sendway       			number,				            --���ͷ�ʽ
	 commissioncharge               number(9,2),		                    --�ۿ�
	 insurance   			number(9,2),		                    --���շ�
	 need_invoice                   number        default(0),	            --�Ƿ���Ҫ��Ʊ
	 bill_to_address                varchar2(2000),	                            --��Ʊ��ַ
	 notes                          varchar2(4000),	                            --��Ʊ��Ϣ
	 flag                           number(1)     default(0),
	 payflag                        number(1)     default(0),                   --�Ƿ��Ѿ�֧��
	 linktime                       varchar2(50),			
	 payway        			number,		                            --���ʽ
	 paydate                        date,                                       --����֧��ʱ��
	 usescores                      number        default(0),	            --�û�ʹ�û���
	 orderscores                    number        default(0),                   --���������Ļ��� 
	 usecard                        number        default(0),                   --ʹ�ù���ȯ���   ������
	 paycompno                      int,                                        --����֧�����ر��
	 jylsh                          varchar(200),                               --��֧�����ط��ص�֧�����ط��Ľ�����ˮ��
	 zfmemberid                     varchar2(30),                               --֧�����Ļ�ԱID
	 r2type                         smallint    default(0),                     --֧�����ķ������ͣ�1������ض��� 2��������Ե�
         payresult                      varchar2(20),                               --֧��������ɹ�/ʧ��/����
	 lastupdate                     date,		                            --��������޸�ʱ��
	 CREATEDATE    			date,				            --����ʱ��
	 primary key(ORDERID) 
);


CREATE TABLE TBL_ORDERS_DETAIL  ( -- ������ϸ��Ϣ��
	 id                             number              NOT NULL,               --����
	 orderid                        number(38)          NOT NULL,               --������
	 supplierid                     number              default(0)   not null,  --������id(��Ӧÿ��������siteid)
	 suppliername                   varchar2(100),                              --����������
	 productid                      number,				            --��Ʒid
	 productname                    varchar2(200),	                            --��Ʒ����
	 productcode                    varchar2(200),                              --��Ʒ����
	 ordernum                       number,				            --��Ʒ����
         unit                           varchar2(20),                               --��Ʒ������λ
	 saleprice                      number(9,2),		                    --��Ʒ�ۼ�
	 weight                         number,				            --��Ʒ����
	 type                           varchar2(100),	                            --��Ʒ���ͣ���չ���ԣ�
	 cardid                         number             default(0) NOT NULL,     --ʹ�ù����id 	 
	 userinstarttime                date,                                       --�û�����Ŀ�ʼ����
	 userinendtime                  date,                                       --�û�����Ľ�������
	 servicestarttime               date,                                       --���ݹ��������ķ�����ɹ���ʼ����
	 serviceendtime                 date,                                       --���ݹ��������ķ�����ɹ���������
	 subscribe                      smallint,                                   --����������
         subscribenum                   int,                                        --���񶩹�������������1�����Ȼ���2������
	 createdate                     date,				            --����ʱ��
	 CONSTRAINT fk_orderID FOREIGN KEY (ORDERID) REFERENCES  TBL_ORDERS(ORDERID) ON DELETE CASCADE,	 	 
	 primary key(id)
);

--�ͻ���ʽ����
create table tbl_fee(
	id		    number not null,     --����
	siteid              number default 0 not null,--վ��id
	cname               varchar(200),				 --�ͻ���ʽ����
	notes               varchar(2000),       --�ͻ���ʽ˵��
	fee									number(9,2) default 0 not null,--�˷�
	createdate          date default sysdate not null,--��������
	primary key(id)
);

--�˷ѹ���
create table tbl_feeforproduct --��Ʒ�˷�
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

create table tbl_cityfee --�����˷�
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

create table tbl_paywayfee --֧����ʽ����
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

--֧����ʽ����
create table tbl_sendway(
        id                  number                   not null,           --����
        siteid              number default 0         not null,           --վ��id
        cname               varchar(200),				 --֧����ʽ����
	notes               varchar(2000),                               --֧����ʽ˵��
	createdate          date default sysdate     not null,           --��������
	primary key(id)
);

##�ҵĳ����ͻ���ַ
create table tbl_addressinfo(
        id                        number               not null,      --����
        userid                    number               not null,      --�û�id
        name                      varchar2(200),                      --�ջ�������
        provinces                 varchar2(50),		              --ʡ��
        city                      varchar2(50),			      --����
        zone                      varchar2(50),                       --����
        mobile                    varchar2(50),                       --�ƶ��绰
        phone                     varchar2(50),                       --�̶��绰
        zip                       varchar2(50),                       --�ʱ�
        address                   varchar2(200),                      --��ϸ��ַ
        email                     varchar2(200),                      --�ջ����ʼ���ַ
        notes                     varchar2(200),                      --�ջ��˱�ע��Ϣ
	createdate                date,                               --����ʱ��
	primary key(id)
);

##�����ľ����ͻ���ַ
create table tbl_addressinfoForOrder(
        id                        number              not null,       --����
        ORDERID                   number              not null,       --������  
	userid                    number              not null,       --�û�id
        name                      varchar2(200),                      --�ջ�������
        provinces                 varchar2(50),			      --ʡ��
        city                      varchar2(50),			      --����
        zone                      varchar2(50),                       --����
        mobile                    varchar2(50),                       --�ƶ��绰
        phone                     varchar2(50),                       --�̶��绰
        zip                       varchar2(50),                       --�ʱ�
        address                   varchar2(200),                      --��ϸ��ַ
	createdate                date,                               --����ʱ��
	primary key(id)
);

--����ȯ
--ischeck �Ƿ񷢷�0-δ���ţ�1-�ѷ���
CREATE TABLE tbl_shoppingcard(
	id               number                  not NULL,
	siteid           number                  not null,
	cardnum          varchar(50),                             --����ȯ��ʶ�� 
	denomination     number,                                  --����ȯ���
	begintime        varchar(50),                             --��ʼʱ��
	endtime          varchar(50),                             --����ʱ��
	code             varchar(50),                             --������
	createtime       date,                                    --����ʱ��
	activation       number,                                  --�Ƿ�ʹ��0-δ��ʹ�ã�1-��ʹ��
	ischeck          number                  NOT NULL,        
	primary key(id) 
);

--������������
create table tbl_mai_zeng_fangan(
        id               number                    not null,
        siteid           number                    not null,
        zengway          number                       not null,      --��Ʒ�������
        price            number(2,9),                                   --���۸�����һ�������ļ۸�ﵽ�涨����ֵ������Ʒ��һ�ַ�����
        pcount           number,                                     --����������ĳ����Ʒ���򵽹涨������������Ʒ    ���ڶ��ַ�����
        productid        number,                                     --����������Ҫ�������Ʒ
        productbrand     varchar2(50),                            --����ĳ��Ʒ�Ƶ���Ʒ������Ʒ                    �������ַ����� 
        useflag          number,                                --�÷����Ƿ�ʹ��     0--��ʹ��   1--ʹ��
        majorflag        number,                                --��Ҫ������������������ͻ�����ʹ�ø÷�����1--������������ʹ�÷�����ֻ����һ��������
        createdate       date,                                    
        editor           varchar2(50)                             
);


--��Ʒ����
create table tbl_zengpin(
        productid        number                    not null,      --��ƷID
        productname      varchar2(250)             not null,      --��Ʒ����
        fanganid         number                       not null,   --���÷���ID
        createdate       date,                                    
        editor           varchar2(50)                             
);

--��������
create table tbl_groupproductsale(
        groupid          number                       not null,      --��Ʒ���ID
        productid        number                    not null,      --��ƷID
        productname      varchar2(250)             not null,      --��Ʒ����
        summary          varchar2(500),                           --��ϲ�Ʒ����
        productcount     number                  not null,      --��Ʒ����
        YhPrice          number(2,9),                                   --�Żݼ۸�
        groupbigpic      varchar2(250),                           --��ϴ�ͼ
        groupsmallpic    varchar2(250),                           --���Сͼ
        grouptexuao      varchar2(250),                           --�����Чͼ
        groupcontentpic  varchar2(250),                           --�������ͼ
        begintime        date,                                    --��ʼ����
        endtime          date,                                    --��������
        createdate       date,                                    
        editor           varchar2(50)                             
);

--֧���ӿڶ����
create table tbl_paywayinterface(
       id                number            not null,
       paywayid          number            not null,
       accountnumber     varchar2(200),
       paywaykey         varchar2(200),
       alipayaccount     varchar2(100),
       createdate        date              not null,
       primary key(id) 
); 

--��Ʊ��Ϣ��  ������
create table tbl_invoiceinfo
(
       id                 number,
       siteid             number           default 0,           --վ��id
       userid             number           default 0,           --�û�id
       orderid            number(38)       default 0,           --�û�������
       invoicetype        number           default 0,           --��Ʊ����(0-��ͨ��Ʊ1-��ֵ˰��Ʊ)
       title              number           default 0,           --��Ʊ̧ͷ������/��λ0-����1-��λ��
       companyname        varchar2(200),                        --��λ����
       content            number           default 0,           --��Ʊ����(�������id)
       identification     varchar2(200),                        --��˰��ʶ���
       registeraddress    varchar2(200),                        --ע���ַ
       phone              varchar2(50),                         --ע��绰
       bankname           varchar2(200),                        --��������
       bankaccount        varchar(100),                         --�����ʺ�
       createdate         date       default sysdate,           
       primary key(id)
);
CREATE SEQUENCE tbl_invoiceinfo_id START WITH 1 increment by 1 cache 20; 

##���嶩���ķ�Ʊ��Ϣ��
create table tbl_invoiceinfoForOrder
(
       id                 number        not null,
       siteid             number        default 0,              --վ��id
       userid             number        default 0,              --�û�id
       orderid            number        default 0,              --�û�������
       invoicetype        number        default 0,              --��Ʊ����(0-��ͨ��Ʊ1-��ֵ˰��Ʊ)
       title              number        default 0,              --��Ʊ̧ͷ������/��λ0-����1-��λ��
       companyname        varchar2(200),                        --��λ����
       content            number        default 0,              --��Ʊ����(�������id)
       identification     varchar2(200),                        --��˰��ʶ���
       registeraddress    varchar2(200),                        --ע���ַ
       phone              varchar2(50),                         --ע��绰
       bankname           varchar2(200),                        --��������
       bankaccount        varchar2(100),                        --�����ʺ�
       createdate         date,                                 --����ʱ��
       primary key(id)
);

##���嶩���ķ�Ʊ����ά����
create table tbl_invoicecontentForOrder
(
       id                 number           not null, 
       siteid             number           default 0,           --վ��id
       orderid            number           not null,            --����ID
       invoiceid          number           not null,            --��ƱID
       guige              varchar2(100),                        --���
       unit               varchar2(100),                        --��λ                      
       num                number,                               --��������
       danprice           number(10,2),                         --����
       content            varchar2(200)    not null,            --����
       createdate         date,                                 --����ʱ��
       primary key(id)
);



--�ͻ����ù���


--ǰ̨Ӧ�ó���
CREATE SEQUENCE TBL_ACTIVITY_ID INCREMENT BY 1 START WITH 1 NOCYCLE CACHE 20 NOORDER;
create sequence TBL_COMMENT_ID start with 1 increment by 1 cache 20;
create sequence TBL_SURVEY_ANSWER_ID start with 1 increment by 1 cache 20;
create sequence TBL_SURVEY_ID start with 1 increment by 1 cache 20;   
--�ҵ�ͨ��¼˳��id
CREATE SEQUENCE tbl_userinfos_id START WITH 805 increment by 1 cache 20;       
--����˳��id
CREATE SEQUENCE tbl_leaveword_id START WITH 1 increment by 1 cache 20;       
CREATE SEQUENCE su_answer_id START WITH 6117 increment by 1 cache 20;
CREATE SEQUENCE su_danswer_id START WITH 4 increment by 1 cache 20; 
CREATE SEQUENCE su_dquestion_id START WITH 1 increment by 1 cache 20;
CREATE SEQUENCE su_survey_id START WITH 1 increment by 1 cache 20;
--ǰ̨�û�ע���ID
CREATE SEQUENCE tbl_userreg_id START WITH 1 increment by 1 cache 20;         
CREATE SEQUENCE TBL_ORDERS_DETAIL_ID START WITH 1 increment by 1 cache 20;
CREATE SEQUENCE TBL_FEE_ID START WITH 1 increment by 1 cache 20;
CREATE SEQUENCE TBL_SENDWAY_ID START WITH 1 increment by 1 cache 20;
CREATE SEQUENCE TBL_ADDRESSINFO_ID START WITH 1 increment by 1 cache 20;
--���������û��Զ���ı�
CREATE SEQUENCE self_define_ID START WITH 1 increment by 1 cache 20;          
CREATE SEQUENCE TBL_SHOPPINGCARD_ID START WITH 1 increment by 1 cache 20;


--��Ϣ�ɼ�ϵͳ
create sequence spider_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;

create table SP_BASIC_ATTRIBUTES
(
        ID                      int                 not null,             --����
        siteid                  int,                                      --ʹ����վ��ID
        SITENAME                varchar2(50),                             --վ������
        STARTURL                varchar2(500),                            --��ʼ��URL
        urltype                 varchar2(10),                             --����ҳ��ķ�ʽ��������link����form
        method                  varchar2(10),                             --���ʷ���������get����post
        indexnum                int,                                      --ץȡ��ҳ��ҳ��
        URLNUMBER               int,                                      --ץȡURL������
        STATUS                  varchar2(5),                              --���ص�״̬��
        LOGINFLAG               number(1),                                --�Ƿ���Ҫ��¼
        POSTURL                 varchar2(255),                            --��¼�õ�URL
        POSTDATA                varchar2(255),                            --��¼�õ�����
        PROXYFLAG               number(1),                                --�Ƿ�ʹ��PROXY������
        KEYWORDFLAG             int,                                      --�Ƿ���ùؼ��ֹ���
        STOPFLAG                int,                                      --�Ƿ�ֹͣ����
        CNAME                   varchar2(100),
        createdate              date                not null,
        primary key(id)     
);

create table sp_proxy
(
        id                      int                 not null,
        siteid                  int,
        STARTURLID              int,                                       --SP_BASIC_ATTRIBUTES����ID
        PROXYLOGINUSER          varchar2(50),                              --PROXY�û���
        PROXYLOGINPASS          varchar2(50),                              --PROXY������
        PROXYURL                varchar2(500),                             --PROXY��ַ
        PROXYPORT               varchar2(20),                              --PROXY�˿ں�
        createdate              date                not null,
        primary key(id)   
);


CREATE TABLE sp_keyword_rules (
        Id                int                 not null,
        siteid            int,                                             --վ��ID
        starturlid        int,                                             --SP_BASIC_ATTRIBUTES����ID
        Tkeyword          varchar2(255),                                   --����ؼ���
        bkeyword          varchar2(255),                                   --���ݹؼ���
        tbrelation        int,                                             --����ؼ��������ݹؼ���֮��Ĺ�ϵ
        score             varchar2(10),                                    --�ؼ��ֻ�ȡ�Ļ���
        createdate        date                not null,                    --�ؼ��ֹ��򴴽�����
        primary key(id)   
);

create table SP_BASIC_COLUMNS
(
        ID         int      not null,
        siteid     int,                                                    --վ��ID
        BASICID    int,                                                    --SP_BASIC_ATTRIBUTES����ID
        CLASSID    int,                                                    --���ݱ������ĿID
        CREATEDATE date     not null,                                      --��������
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
	id                    int                         not null,             --����
	siteid                int,                                              --ץȡ��Ϣ��վ��ID
	starturlid            int,                                              --��ʼURL��ID
	pname                 varchar2(100),                                    --�����ֶε�����
	pvalue                varchar2(1000),                                   --�����ֶε�ֵ
	ptype                 varchar2(10),                                     --�����ֶε����ͣ�text/hidden/password
	pid                   varchar2(100),                                    --�����ֶε�IDֵ
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

--�ʰɱ�ṹ����
CREATE SEQUENCE tbl_wenba_ID START WITH 1 increment by 1 cache 20;
create table fawu_wenti_column                          --�ʰɷ��ඨ��
(
        id                      int                     not null,            --�ʰɷ�������
        siteid                  int                     not null,            --վ��ID
        parentid                int                     not null,            --������ID
        orderid                 int                     not null,            --���ID
        cname                   varchar2(100),                               --��������
        ename                   varchar2(100),                               --Ӣ������
        dirname                 varchar2(200),                               
        status                  int                     default 0,
        creater                 varchar2(20),
        createdate              date,
        primary key(id)
);
insert into fawu_wenti_column values(0,2191,-1,0,'�ʰ�����','wenba','/',0,'admin',sysdate);

create table fawu_anwser                                                     --�𰸱�      
(
        id                      int                     not null,            --������
        qid                     int                     not null,            --������������������
        anwser                  varchar2(4000),                              --��
        votenum                 int,                                         --ͶƱ����
        ipaddress               varchar2(15),                                --�ش��ߵ�IP��ַ
        userid                  int,                                         --�ش����û�ID
        username                varchar2(300),                               --�ش����û���
        fenshu                  int                     default 0,           --��ȡ����
        cankaoziliao            varchar2(4000),                              --�ο�����
        createdate              date                    default sysdate,     --����ʱ��
        PICPATH                 varchar2(255),                               --ͼƬ·��
        BETTERANS               int,                                         --�Ƿ�����Ѵ�
        ANW_STATUS              int,                                         --��״̬
        primary key(id)
);

create table fawu_vote                                 --�ʰɴ�ͶƱ��
(
        id                      int                    not null,             --����ID   
        aid                     int                    not null,             --�����ID
        userid                  int                    not null,             --ͶƱ�û�ID
        primary key(id)
);

create table fawu_wenti                                 --�����
(
        id                      int                     not null,           --����ID
        columnid                int                     not null,           --����ID
        parcolumid              int,                                        --��һ������ID
        cname                   varchar2(100),                              --������������
        question                varchar2(1000),                             --��������
        status                  int,                                        --����״̬
        voteflag                int,                                        --�Ƿ��Ǵ�ͶƱ������
        xuanshang               int                     default 0,          --���ͷ���
        answernum               int                     default 0,          --�ش���Ŀ
        source                  varchar2(50),                               --
        createdate              date,                                       --��������
        ipaddress               varchar2(15),                               --�ͻ���IP��ַ
        creater                 varchar2(50),                               --������
        province                varchar2(50),                               --������ʡ��
        city                    varchar2(50),                               --���������ڳ���
        area                    varchar2(50),                               --��������������
        picpath                 varchar2(255),                              --ͼƬ·��
        emailnotify             int                     default 0,          -- �Ƿ��ʼ�֪ͨ  1--֪ͨ  0--��֪ͨ,
        title                   varchar2(60),                               --
        email                   varchar2(40),                               --�û������ʼ�
        userid                  int,                                        --�û�ID
        username                varchar2(20),                               --�û�����
        filepath                varchar2(255),                              --�ϴ��ļ�·��
        istop                   int                     default 0,          --�Ƿ��ö�
        anw_status              int,                                        --��״̬
        dianjinum               int,                                        --�����
        user_id_huida           varchar2(50),                               --�ش���ID
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
	id		varchar(500)		not null,         --����ID
	lyhid		number,                                   --��¼�û�ID
	username	varchar(500),                             --��¼�û���
	logintime	date,                                     --����¼ʱ��
	loginnum        number,                                   --��¼����
	loginstatus     number(2),                                --��¼״̬
	lastactivetime  date,                                     --�û����ʱ��
	ipaddress	varchar(500)                              --��¼�ߵ�IP��ַ
);


create table bbs_friend(
	userid		varchar(500),
	lyhid		number,
	friend		varchar(500)
);


--��������
CREATE TABLE TBL_COMMENT ( 
	id             int                      not null,                --����
	siteid         int                      not null,                --վ��id
	userid         int                      not null,                --�û�id
	Username       varchar2(30)             not null,                --�û���¼����
	pid            int                      not null,                --��Ʒid
	commenttitle   varchar2(200)            not null,                --����
	Scores         int                      default(0),              --����
	experience     varchar2(500),                                    --ʹ���ĵ�
	Advantages     varchar2(500),                                    --�ŵ�
	Shortcomings   varchar2(500),                                    --����
	usefulnum      int                      default(0),              --��Ϊ���������õ�����
	nousenum       int                      default(0),              --��Ϊ���������õ�����
	auditflag      int       default 0,                              --0 ���ͨ��������ʾ 1-���δͨ�� ����ʾ 	
	createdate     date                     default sysdate,
	CONSTRAINT pk_commentsid PRIMARY KEY (ID)	
);

--�������ۻظ�
create table Tbl_commentanswer
(
        id              int                      not null,               --����
        parentid        int                      default      0,         --�ظ��ĸ�ID                                              
        cid             int                      not null,               --��Ʒ����id���
        auditflag       int                      default      1,         --�Ƿ�ͨ����˱�� 0-ͨ��  1-δͨ��
        username        varchar2(100)            not null,               --�ظ��˵�¼��
        answercontent   varchar2(500)            not null,               --�ظ�����
        createdate      date                     default sysdate,        --�ظ�ʱ��
	CONSTRAINT fk_commentID FOREIGN KEY (cid) REFERENCES  tbl_comment(ID) ON DELETE CASCADE,
        primary key(id)
);

--��Ʊ��Ϣ��  ������
create table en_invoice
(
        id               int                      not null,             --����
        siteid           int                      not null,             --վ��id
        userid           int                      not null,             --�û�id
        orderid          int                      not null,             --�û�������
        invoicetype      int                      default(0),           --��Ʊ����(0-��ͨ��Ʊ1-��ֵ˰��Ʊ)
        titleflag        int                      default(0),           --������/��λ0-����1-��λ��
        title            varchar2(100),                                  --��Ʊ̧ͷ
        companyname      varchar2(200),                                  --��λ����
        identification   varchar2(200),                                  --��˰��ʶ���
        registeraddress  varchar2(200),                                  --ע���ַ
        phone            varchar2(15),                                   --ע��绰
        postaddress      varchar2(200),                                  --�ʼĵ�ַ
        postname         varchar2(20),                                   --����������
        postcode         varchar2(6),                                    --��������
        bankname         varchar2(200),                                  --��������
        bankaccount      varchar2(100),                                  --�����ʺ�
        totalprice       number(18,2),                                    --��Ʊ�ܼ�ֵ
        createdate       date                 default sysdate,           --����ʱ��
        primary key(id)
);

--��Ʊ����ά����
create table en_invoicecontent
(
        id              int                      not null,             --����,ÿ����Ʊ���ID
        invoiceid       int                      not null,             --��ƱID
        siteid          int                      not null,             --վ��id
        content         varchar2(200)            not null,             --����
        price           number(18,2),                                   --��Ʒ����
        pnum            int                      default(0),           --��Ʒ����
        createdate      date                     default sysdate,      --����ʱ��
	CONSTRAINT      fk_contentid FOREIGN KEY (invoiceid) REFERENCES  en_invoice(id) ON DELETE CASCADE,
        primary key(id)
);


--�����û������
create table tbl_shetuan (
	id		number			not null,	--����
	stname		varchar2(100),				--��������
	lianxiren	varchar2(100),				--��ϵ��
	phone		varchar2(100),				--�绰	
	email		varchar2(100),				--�ʼ�
	username	varchar2(100),				--�û���
	passwd		varchar2(100),				--����
	createdate	date,					--ע��ʱ��
	primary key (id)
);
create sequence tbl_shetuan_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;

--�����һ�����Ϣ�����
create table tbl_jiben(
	id		number			not null,	--����
	meetname	varchar2(500),				--����������
	meetmax		varchar2(100),				--����������
	meetroot	varchar2(500),				--������λ��
	primary key (id)
);
create sequence tbl_jbxinxi_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;

--������Ԥ����Ϣ�ռ�(����)
create table tbl_yuding(
	id		number			not null,	--����
	ydperson	varchar2(100),				--Ԥ����
	jbxinxiid	varchar2(500),				--Ԥ��������ID
	khdate		date,					--����ʱ��
	jsdate		date,					--����ʱ��
	flag		number,					--����״̬        1  ���     2  δ���
	shperson	varchar2(100),				--������
	shdate		date,					--����ʱ��
	primary key (id)
);
create sequence tbl_ydxinxi_id minvalue 1 maxvalue 9999999999999999999999999 start with 39 increment by 1 cache 20;










---ϵͳδʹ�õı���
--�����������������ϵͳ
--���б�ű�
create table tbl_banks (
        id                      number                     not null,      --����
        bankid                  varchar2(12)            not null,      --����ID
        siteid                  number                     not null,      --վ��ID
        bankattribname          varchar2(12)            not null,      --���м��
        bankname                varchar2(40),                          --��������
        createdate              date,                                  --����ʱ��
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);

--ҵ��Ա���ݱ�
create table tbl_salesman (                                            --tbl_members
        id                      number                     not null,      --����
        salesmanid              varchar2(12)            not null,      --ҵ��ԱID
        siteid                  number                     not null,      --վ��ID
        chinesename             varchar2(12)            not null,      --ҵ��Ա����
        englishname             varchar2(12),                          --ҵ��ԱӢ������
        contactphone            varchar2(20),                          --ҵ��Ա��ϵ�绰
        mobilephone             varchar2(12),                          --ҵ��Ա�ƶ��绰
        contactaddress          varchar2(120),                         --ҵ��Ա��ϵ��ַ
        email                   varchar2(50),                          --ҵ��Ա�����ʼ���ַ
        createdate              date,                                  --����ʱ��
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);

--�ͻ���(ȡ��)
create table tbl_customer (                                            --tbl_userinfo
        id                      number                     not null,      --����
        customerid              varchar2(12)            not null,      --�ͻ�ID
        siteid                  number                     not null,      --վ��ID
        customerattribname      varchar2(50)            not null,      --�ͻ����
        customername            varchar2(100),                         --�ͻ�����
        invoiceno               varchar2(20),                          --��Ʊ���
        owner                   varchar2(12),                          --������
        rocid                   varchar2(18),                          --���֤����
        contactman1             varchar2(18),                          --��ϵ��1
        contactman2             varchar2(18),                          --��ϵ��2
        contactphone1           varchar2(20),                          --��ϵ��1�绰
        contactphone2           varchar2(20),                          --��ϵ��2�绰
        fax                     varchar2(20),                          --�������
        salesmanid              number,                                   --ҵ������
        customeraddress         varchar2(120),                         --��˾��ַ
        deliveryaddress         varchar2(120),                         --�ͻ���ַ
        invoiceaddress          varchar2(120),                         --��Ʊ��ַ
        paydays                 number,                                   --��������
        creditline              number(32,2),                          --���ö��
        creditbalance           number(32,2),                          --�������
        lastdeliverydate        date,                                  --�������
        createdate              date,                                  --����ʱ��
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);

--ע����ҵ��ע����ҵ����
create table tbl_supplier (
        id                     number                     not null,      --����
        suppcode               varchar2(12),                             --������ID
        corpname               varchar2(100)              not null,      --����������
        enname                 varchar2(100),                            --������Ӣ������
        shortname              varchar2(50),                             --�����̼��
	zhizhaocode            varchar2(50),                             --Ӫҵִ�ձ���
	zhizhaoimage           varchar2(250),                            --Ӫҵִ��ͼƬ
        orgcode                varchar2(50),                             --��֯��������
	orgimage               varchar2(250),                            --��֯��������ͼƬ
        weburl                 varchar2(20),                             --��Ӧ����ַ
        address                varchar2(120),                            --��˾��ַ
        zipcode                varchar2(120),                            --��˾��������
        capital                varchar2(120),                            --��˾ע���ʽ�
        taxnumber              number,                                   --��˾˰��
	manager                varchar2(120),                            --��˾������
	certstatus             number                   default(0),      --��˾��֤״̬    0-δ��֤    1-��֤
        type                   number,                                   --ע�ṫ˾����   1-��Ӧ��  2-�ɹ���  3-�ɹ��̡���Ӧ��     
        supptype               number,                                   --��Ӧ��������   1-�ܲ���Ӧ�� 2-���ӹ�˾��Ӧ��
        suppgrade              number,                                   --��Ӧ��������   5-5�� 4-4��  3-3�� 2-2�� 1-1��
        supplevel              number,                                   --��Ӧ�̼���1-ս�Թ�Ӧ�� 2-��Ҫ��Ӧ�� 3-��ͨ��Ӧ��
        is_used                number,                                   --��Ӧ���Ƿ�ʹ��   1-ʹ�� 0-δʹ��
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
        country                number,                                   --��Ӧ���������ұ���
        privince               number,                                   --��Ӧ������ʡ�ݱ���
        isbusiness             number,                                   --�Ƿ�����ͨ��      0-������   1-��ͨ��
        isopen                 number,                                   --��Ӧ���Ƿ�����Ӫҵ   0-����Ӫҵ   1-��ͣ 2-�ر�
        tylxbm                 number,
        presenter              number,                                   --�Ƽ���ҵ����
        presentername          varchar2(120),                            --�Ƽ���ҵ����
        updatedate             date,                                     --����޸�����
        createdate             date,                                     --��������
	creator                varchar2(50),                             --������
	modifier               varchar2(50),                             --����޸���
	primary key (id)
);

create table tbl_suppcontactor (
        id                     number                     not null,      --��ϵ������
	suppid                 number                     not null,      --��Ӧ������
        contactor              varchar2(20),                             --��ϵ������
        telephone              varchar2(18),                             --��ϵ�绰
	mphone                 varchar2(20),                             --��ϵ���ֻ�
	mphone1                varchar2(20),                             --��ϵ�˱����ֻ�
        fax                    varchar2(20),                             --�������
        email                  varchar2(20),                             --��ϵ�˵����ʼ�
	email1                 varchar2(20),                             --��ϵ�˱��õ����ʼ�
	title                  varchar2(20),                             --��ϵ��ְλ
        brief                  varchar2(1000),                           --��ϵ�˼��
	image                  varchar2(250),                            --��ϵ��ͷ��
        updatedate             date,                                     --����޸�����
        createdate             date,                                     --��������
	creator                varchar2(50),                             --������
	modifier               varchar2(50),                             --����޸���
	primary key (id)
);

create table tbl_suppbank (
        id                     number                     not null,      --��ϵ������
	suppid                 number                     not null,      --��Ӧ������
        openaccountbank        varchar2(120),                            --��˾������
        account                number,                                   --��˾�������˺�
        bankcert               varchar2(120),                            --������֤��Ϣ
        isbasic                number,                                   --�Ƿ��ǻ���������   1-��  0-����
        updatedate             date,                                     --����޸�����
        createdate             date,                                     --��������
	creator                varchar2(50),                             --������
	modifier               varchar2(50),                             --����޸���
	primary key (id)
);

create table tbl_suppclass (
        id                     number                     not null,      --��ϵ������
	suppid                 number                     not null,      --��Ӧ������
        suppclassid            number                     not null,      --��Ӧ�̷���ID
        updatedate             date,                                     --����޸�����
        createdate             date,                                     --��������
	creator                varchar2(50),                             --������
	modifier               varchar2(50),                             --����޸���
	primary key (id)
);


--��Ʒ��ȡ����
create table tbl_jxc_product (
        id                      number                     not null,      --����
        productid               varchar2(12)               not null,      --��ƷID
        siteid                  number                     not null,      --վ��ID
        productname             varchar2(120)              not null,      --��Ʒ����
        safestock               number,                                   --��ȫ����
        lastpurchasedate        date,                                  --�����������
        lastdeliverydate        date,                                  --�����������
        quantity                number         default(0),                --�����
        createdate              date,                                  --����ʱ��
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);

--������������������
create table tbl_purchasemaster (
        id                      number                     not null,      --����
        purchaseid              varchar2(12)            not null,      --��������
        siteid                  number                     not null,      --վ��ID
        purchasedate            date                    not null,      --��������
        supplierid              varchar2(12)            not null,      --�����̱��
        purchaseproperty        number(1)                default(0),    --���������� 0-����  1-�˻�
        invoiceno               varchar2(10),                          --��Ʊ���
        subtotal                number(36,2)            default(0),    --�ϼƽ��
        valueaddtax             number(36,2)            default(0),    --Ӫҵ˰
        amount                  number(36,2)            default(0),    --�ܼƽ��
        accountpayable          number(36,2)            default(0),    --Ӧ�����
        paid                    number(36,2)            default(0),    --�Ѹ��ʿ�
        limitdate               date,                                  --�����ֹ����
        createdate              date,
        updatedate              date,
        primary key (id)
);

--��������ϸ��
create table tbl_purchasedetail (
        id                      number                     not null,      --����
        purchaseid              varchar2(12)            not null,      --��������
        productid               varchar2(12)            not null,      --��ƷID
        purchasequantity        number                     default(0),    --��������
        purchaseunitprice       number(18,2),                          --��������
        purchaseamount          number(36,2)           default(0),     --�����ܼ�
        createdate              date,                                  --����ʱ��
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);

--������������������
create table tbl_deliverymaster (
        id                      number                     not null,      --����
        deliveryid              varchar2(12)            not null,      --��������
        siteid                  number                     not null,      --վ��ID
        deliverydate            date                    not null,      --��������
        customerid              varchar2(12)            not null,      --�ͻ����
        salesmanid              varchar2(12)            not null,      --ҵ��Ա���
        deliveryaddress         varchar2(120),                         --�ͻ���ַ
        invoiceno               varchar2(10),                          --��Ʊ���
        customerorderno         varchar2(15),                          --��������
        subtotal                number(36,2)            default(0),    --�ϼƽ��
        valueaddtax             number(36,2)            default(0),    --Ӫҵ˰
        amount                  number(36,2)            default(0),    --�ܼƽ��
        accountreceivable       number(36,2)            default(0),    --Ӧ�ս��
        received                number(36,2)            default(0),    --���ս��
        limitdate               date,                                  --�����ֹ����
        createdate              date,
        updatedate              date,
        primary key (id)
);

--��������ϸ��
create table tbl_deliverydetail (
        id                      number                     not null,      --����
        deliveryid              varchar2(12)            not null,      --��������
        productid               varchar2(12)            not null,      --��ƷID
        salesquantity           number                     default(0),    --��������
        salesunitprice          number(18,2),                          --��������
        salesamount             number(36,2)           default(0),     --�����ܼ�
        createdate              date,                                  --����ʱ��
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);


--���䶯����������
create table tbl_changemaster (
        id                      number                     not null,      --����
        changeid                varchar2(12)            not null,      --��������
        siteid                  number                     not null,      --վ��ID
        changedate              date,                                  --�䶯����
        description             varchar2(255),                         --�䶯˵��
        createdate              date,                                  --����ʱ��        
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);

--���䶯��ϸ
create table tbl_changedetail (
        id                      number                     not null,      --����
        changeid                varchar2(12)            not null,      --��������
        productid               varchar2(12)            not null,      --��ƷID
        changequantity          number                     default(0),    --�䶯����
        changeamount            number(36,2)           default(0),     --�䶯���
        createdate              date,                                  --����ʱ��        
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);

--�����������Ӧ���˿����
create table tbl_accountpayablemaster (
        id                      number                     not null,      --����
        paymentid               varchar2(12)            not null,      --��������
        siteid                  number                     not null,      --վ��ID
        paydate                 date                    not null,      --֧������
        supplierid              varchar2(12)            not null,      --��Ӧ��ID
        paycash                 number(36,2)            default(0),    --�ֽ�
        paycheck                number(36,2)            default(0),    --֧Ʊ
        noteno                  varchar2(10),                          --֧Ʊ����
        duedate                 date,                                  --������
        bankid                  varchar2(12)            not null,      --��Ʊ����
        accountno               varchar2(20),                          --�ʺ�
        discount                number(36,2)            default(0),    --�ۿ�
        remittance              number(36,2)            default(0),    --����
        prepaid                 number(36,2)            default(0),    --�ݸ���
        others                  number(36,2)            default(0),    --����
        payamount               number(36,2)            default(0),    --������
        totalbalance            number(36,2)            default(0),    --ʵ����
        createdate              date,     
        updatedate              date,
        primary key (id)
);

--���������ϸ��
create table tbl_accountpayabledetail (
        id                      number                     not null,      --����
        paymentid               varchar2(12)            not null,      --�����
        purchaseid              varchar2(12)            not null,      --����������
        balance                 number(36,2)            default(0),    --���γ������
        createdate              date,                                  --����ʱ��        
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);

--�տ��������Ӧ���˿����
create table tbl_accountreceivablemaster (
        id                      number                     not null,      --����
        siteid                  number                     not null,      --վ��ID
        receiveid               varchar2(12)            not null,      --�տ��
        receivedate             date,                                  --�տ�����
        customerid              varchar2(12)            not null,      --�ͻ����
        receivecash             number(36,2)            default(0),    --�ֽ�
        receivecheck            number(36,2)            default(0),    --֧Ʊ
        noteno                  varchar2(10),                          --֧Ʊ����
        duedate                 date,                                  --������
        bankid                  varchar2(12)            not null,      --��Ʊ����
        accountno               varchar2(20),                          --�ʺ�
        discount                number(36,2)            default(0),    --�ۿ�
        remittance              number(36,2)            default(0),    --����
        advance                 number(36,2)            default(0),    --���տ�
        others                  number(36,2)            default(0),    --����
        receiveamount           number(36,2)            default(0),    --�տ���
        totalbalance            number(36,2)            default(0),    --ʵ����
        createdate              date, 
        updatedate              date,
        primary key (id)
);


--�տ������ϸ��
create table tbl_accountreceivabledetail (
        id                      number                  not null,      --����
        receiveid               varchar2(12)            not null,      --�տ��
        deliveryid              varchar2(12)            not null,      --����������
        balance                 number(36,2)            default(0),    --���γ������
        createdate              date,                                  --����ʱ��        
        updatedate              date,                                  --�޸�ʱ��
        primary key (id)
);


--������ͨ���۵㿨�Ĳ�Ʒ��
create table tbl_bbn_product (
        id                      number                     not null,      --����
        username                varchar2(20)               not null,      --�û���
        passwd                  varchar2(12)               not null,      --��Ʒ����
        price                   number(18,2),                             --��Ʒ�۸�
        cardtype                int,                                      --��Ʒ��������
        flag                    smallint                   default(0),    --�Ƿ����� 0-δ���� 1-������ 2-����������������
        outdate                 date,                                     --�����۵�����
        buyer                   varchar(11),                              --�����˵��ֻ���
        createdate              date,                                     --����ʱ��
        updatedate              date,                                     --�޸�ʱ��
        primary key (id)
);

--�û�ʹ�����ù����ֻ�����
create table tbl_bbn_mphone (
        id                      number                     not null,      --����
        userid                  number                     not null,      --�û�ID tbl_userinfo��userid
        mphone                  varchar2(12)               not null,      --�ֻ�����
        createdate              date,                                     --����ʱ��
        primary key (id)
);

--�������ߵ�������ҵ�û�ע���
create table tbl_rsbt_org(
	id			number				not null,
	guid			varchar2(36)			not null,
	userid			varchar2(36)			not null,
	siteid			number				not null,
	password		varchar2(36)			not null,
	org_gode		varchar2(9) default(999999999)	not null,		--��֯��������
	org_name		varchar2(80)			not null,		--��֯��������
	org_area_code		varchar2(8)			not null,		--��������
	org_sys_code		varchar2(8)			not null,		--ϵͳ/��ҵ����
	org_type		varchar2(3)			not null,		--��λ����
	org_link_person		varchar2(40),						--��λ��ϵ��
	org_person_id		varchar2(40),						--��ϵ�����֤����
	org_sup_code		varchar2(9),						--�ϼ���֯����
	org_addr		varchar2(80),						--��֯������ַ
	org_post		varchar2(6),						--��֯�����ʱ�
	org_phone		varchar2(40),						--��ϵ�绰
	org_mob_phone		varchar2(40),						--�ֻ�����
	org_fax			varchar2(40),						--��֯��������
	org_bank		varchar2(80),						--��������
	org_account_name	varchar2(80),						--�˻�����
	org_account		varchar2(40),						--�����˺�
	org_hostility		number(1),						--��̨��λ����
	org_web_site		varchar2(80),						--��ַ
	org_mail		varchar2(80),						--��������
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

--ҵ��������Ϣ���ݿ��ļ���Ϣ��
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
     createdate                date,                                      --��������
     lastupdate                date,                                      --�޸�����
     editor                    varchar2(50),                              --���ݴ�����
     publishflag               number not null 
);

create table tbl_accessmdb
(
    userid        varchar2(50)                  not null,           --�ϴ���ID
    filename      varchar2(100)                 not null,           --�ϴ��ļ�ԭʼ����
    tflag         number(1) default(0),                              --�Ƿ�ת�뵽���ݿ�      0-û��ת��       1-�Ѿ�ת�룬ȫ���ɹ� 2-���룬������
    createdate    date,
    lastupdate    date,
    primary key(filename)
);


--���ߵ�������߱�
create table tbl_ganrao(
	id			number				not null,		--����
	susongdanwei		varchar2(50)			not null,		--���ϵ�λ
	lianxiren1		varchar2(20),						--��ϵ��1
	lianxiren2		varchar2(20),						--��ϵ��2
	phone			varchar2(20),						--��ϵ�绰
	taizhanweizhi		varchar2(200),						--̨վλ��
	shebeixinghao		varchar2(100),						--�豸�ͺ�
	gongzuofangshi		varchar2(50),						--������ʽ
	ganraoleixing		varchar2(50),						--��������
	zhizhaobianhao		varchar2(50),						--ִ�ձ��
	ganraopinlv		varchar2(50),						--����Ƶ��
	ganraoquyu		varchar2(50),						--��������
	ganraoshiduan		varchar2(50),						--���θ���ʱ�估����ʱ��
	ganraoshijian		varchar2(50),						--����Ƶ�ȼ�ÿ�γ���ʱ��
	tezhengmiaoshu		varchar2(500),						--�����ź���������
	shenshuyaoqiu		varchar2(500),						--����Ҫ��
	primary key(id)
);

--�������ߵ�̨�걨��
create table tbl_zhuduijiangji(
	djjid			number				not null,		--����
	shetaidanwei		varchar2(50),						--��̨��λ
	suoshuxitong		varchar2(50),						--����ϵͳ
	address			varchar2(100),						--��ַ
	lianxiren		varchar2(20),						--��ϵ��
	phone			varchar2(20),						--�绰
	chuanzhen		varchar2(20),						--����
	shiyongdiqu		varchar2(50),						--ʹ�õ���
	diantaishuliang		varchar2(20),						--��̨����
	diantaileibie		varchar2(50),						--��̨���	150MHz,400MHz,900MHz�����ĵ�ַ
	shetailiyou		varchar2(500),						--��̨����
	lingdaoyijian		varchar2(500),						--�ϼ����ܵ�λ�쵼���
	guanlijuyijian		varchar2(500),						--���������ߵ��������
	xitongbianhao		varchar2(100),						--ϵͳ���
	zhizhaobianhao		varchar2(100),						--ִ�ձ��
	primary key(djjid)
);

create table tbl_diantaiziliao(
	id			number				not null,		--����
	dtzlid			number				not null,
	shebeixinghao		varchar2(100),						--�豸�ͺ�
	chuchanghaoma		varchar2(100),						--��������
	beizhu			varchar2(100),						--��ע
	CONSTRAINT fk_dtzlID FOREIGN KEY (dtzlid) REFERENCES  tbl_zhuduijiangji(djjid) ON DELETE CASCADE,
	primary key(id)
);
create sequence tbl_duijiangji_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;
create sequence tbl_ziliao_id minvalue 1 maxvalue 9999999999999999999999999 start with 1 increment by 1 cache 20;


--ҵ������
create table tbl_BusinessInfo
(
    codeinfo varchar2(100),
    cname varchar2(100),
    siteid number default 0 not null,
    primary key(codeinfo)
);
--ԤԼ�����
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
--�û�ԤԼ��Ϣ��
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

insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1001','�������ߵ緢���豸��׼',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1002','��ʱ�������ߵ緢���豸-����',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1003','��ʱ�������ߵ緢���豸-������',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1004','����ʹ��Ƶ��',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1005','��������ʹ��Ƶ��',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1006','����΢��ʹ�ò���',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1007','�����̨����',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1008','�������ӵ�̨����',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1009','����Ƶ�ʶԽ���',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1010','900M�����ĵ�̨',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1011','ҵ�����ߵ�̨',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1012','150M��400M���̲���',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1013','���ݴ�����',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1014','���߽�����',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1015','����ʵЧ����',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1016','����ʵЧ����',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1017','ʵЧ��������',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1018','δ���ͺź�׼�豸ʵЧ����',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1019','����΢��ͨ��վ',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1020','��������ͨѶ��',40);
insert into tbl_BusinessInfo(codeinfo,cname,siteid) values('1021','�������ǵ���վ',40);


���ǵ��̻��鱨��ϵͳ
--�����
create table tbl_meetings
(
    id               number,              --��ѵ������ID
    meetingname      varchar2(200),       --��ѵ������
    meetingdatetime  date,                --��ѵ������ں�ʱ��
    address          varchar2(10),        --��ѵ���ַ
    createdate       date,                --�������ں�ʱ��
    editor           varchar2(50),        --������
    primary key(id)
);

create table tbl_meetting_sign
(
    id               number,              --����ID
    orderid          number,              --������           ORACLE�����к�
    meetingid        number,              --��ѵ��ID
    comapnyname      varchar2(200),       --��˾����
    invoicetitle     varchar2(200),       --��Ʊ̧ͷ
    address          varchar2(10),        --��Ʊ�ʼĵ�ַ
    postcode         varchar2(6),         --��������
    fee              number(9,2),         --��ѵ����
    payway           number(1),           --֧����ʽ     1--����   2--����֧��
    paytime          date,                --֧�����ں�ʱ��
    createdate       date,                --ע�����ں�ʱ��
    primary key(id)
);

create table tbl_meetting_sign_part
(
    id               number,              --����ID
    orderid          number,              --������
    signid           number,              --ע����ѵ������ID
    name             varchar2(50),        --��ѵ������
    depttitle        varchar2(50),        --��ѵ�����ڲ��ź�ְ��
    mobilephone      varchar2(11),        --��ѵ���ֻ�����
    fax              varchar2(15),        --��ѵ�˴������
    email            varchar2(50),        --��ѵ�˵����ʼ���ַ
    meettingtime     date,                --��ѵ��ʱ��
    meetingaddress   varchar2(200),       --��ѵ���ַ
    primary key(id)
);

�б�ͳ��ϵͳ
create table tbl_tender
(
    id               number,              --����ID
    articleid        number,              --����ID
    userid           number,              --userID
    name             varchar2(50),        --Ͷ��������
    depttitle        varchar2(100),        --Ͷ�������ڲ��ź�ְ��
    createdate       date,                --�״δ���ʱ��
    primary key(id)
);

