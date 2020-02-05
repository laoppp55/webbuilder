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
	id		varchar(500)		not null,         ##����ID
	lyhid		int,                                      ##��¼�û�ID
	username	varchar(500),                             ##��¼�û���
	logintime	timestamp,                                ##����¼ʱ��
	loginnum        int,                                      ##��¼����
	loginstatus     int,                                      ##��¼״̬
	lastactivetime  timestamp,                                ##�û����ʱ��
	ipaddress	varchar(500)                              ##��¼�ߵ�IP��ַ
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

insert into tbl_right values(1,'��Ŀ����','CM','��Ŀ����');
insert into tbl_right values(2,'ģ�����','MM','ģ�����');
insert into tbl_right values(3,'���¹���','AM','���±༭');
insert into tbl_right values(4,'�ϴ��ļ�','FU','�ϴ��ļ�');
insert into tbl_right values(5,'����ļ�','FA','����ļ�');
insert into tbl_right values(6,'��������','PA','��������');
insert into tbl_right values(7,'�ƶ�����','MA','�ƶ�����');
insert into tbl_right values(50,'������','AR','������');
insert into tbl_right values(51,'д����','AW','д����');
insert into tbl_right values(52,'ɾ����','AD','ɾ����');
insert into tbl_right values(54,'վ�����','WM','վ�����');
insert into tbl_right values(55,'�ļ��༭','FE','�ļ��༭');
insert into tbl_right values(56,'�ļ�ɾ��','FD','�ļ�ɾ��');
insert into tbl_right values(57,'���԰����','LYB','���԰����');
insert into tbl_right values(58,'���ϵ���','SW','���ϵ���');
insert into tbl_right values(59,'ע���û�����','RU','ע���û�����');
insert into tbl_right values(60,'��ҵ�û�����','ERU','��ҵ�û�����');
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
	roleid                  int                     not null,
	rolename                varchar(30)             not null,
	rolecat                 varchar(30),
	roledesc                varchar(100),
        primary key (roleid)
);

insert into tbl_role values(0,'��ϢԱ','XXY','��������վ¼����Ϣ');
insert into tbl_role values(1,'�����쵼','BML','¼����Ϣ�����');
insert into tbl_role values(2,'�����쵼','ZGL','¼����Ϣ�����');
insert into tbl_role values(3,'ϵͳ����Ա','SM','����ϵͳ����Ա');
insert into tbl_role values(4,'���԰����Ա','LWM','���԰����Ա');
insert into tbl_role values(5,'�������԰����Ա','DLWM','�������԰����Ա');

create table tbl_sjs_log (
	id			int	auto_increment	not null,               ##����
	siteid			int     		not null,               ##վ��ID           
	sitename		varchar(50)		not null,               ##վ������
        columnid                int,                                            ##��ĿID
        columnname              varchar(50),                                    ##��Ŀ����
	articleid		int			not null,               ##����ID
	maintitle		varchar(600)	        not null,               ##�������ı���
        articleurl              varchar(300),                                   ##����URL
        operationtype           smallint,                                       ##���²�������    1-����   2-�޸� 3-ɾ�� 
        editor                  varchar(50),                                    ##��������
	createdate	        timestamp,                                      ##����¼������
        updatedate              timestamp,                                      ##�����޸�����
        deletedate              timestamp,                                      ##����ɾ������
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
  ID               NUMBER not null,              ##�û�ID
  USERNAME         VARCHAR2(50),                 ##�û���¼��
  PASSWD           VARCHAR2(50),                 ##�û���¼����
  FIRSTNAME        VARCHAR2(50),                 ##������
  GAVENAME         VARCHAR2(50),                 ##������
  CNAME            VARCHAR2(50),                 ##ƴ��������
  CSNAME           VARCHAR2(50),                 ##ƴ��������
  CNNAME           VARCHAR2(50),                 ##
  CNNAME1          VARCHAR2(50),                 ##
  PSTATUS          VARCHAR2(50),
  SEX              NUMBER default 0,             ##�Ա�
  CALLS            VARCHAR2(100),                ##�ƺ���������Ůʿ�����ڡ���ʿ��
  COMPANY          VARCHAR2(100),                ##���ڵ�λ����
  DUTIES           VARCHAR2(100),                ##ְ��
  ADDRESS          VARCHAR2(200),                ##��ϸ��ַ
  GUOJIA           VARCHAR2(100),                ##����
  SHENGSHI         VARCHAR2(200),                ##ʡ��
  SHIXIAN          VARCHAR2(200),                ##����
  XXADDRESS        VARCHAR2(200),                ##��ϵ����ϸ��ַ
  HOTELSID         VARCHAR2(50),                 ##���ݵǼ�ID�����֤ID������ID��
  SHUOMING         VARCHAR2(200),                ##�ر�˵��
  XUQIU            VARCHAR2(500),                ##�ر�����
  QINGZHEN         NUMBER default 0,             ##��Ҫ����ʳƷ��0-��1-��
  SUSHI            NUMBER default 0,             ##�Ƿ�Ҫ��ʳ��  0-��1-��
  QTSHUOMING       VARCHAR2(500),                ##δ��
  YAOQING          NUMBER default 0,             ##�Ƿ�����
  SPECIALSHUOMING  VARCHAR2(500),                ##�ر����˵��
  PEITONG          NUMBER default 0,             ##��ͬ������
  PTXING           VARCHAR2(100),                ##��ͬ��
  PTMING           VARCHAR2(100),                ##��ͬ��
  PTSET            NUMBER default 0,             ##��ͬ�Ա�
  PTZHENGHAO       VARCHAR2(100),                ##��֤ͬ�����룬���պŻ������֤�ŵ�
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
        id                      int	        auto_increment not null,         ##����
	uid                     int             not null,                        ##�û�ID
        ptxing                  varchar(100),                                    ##��ͬ��
        ptming                  varchar(100),                                    ##��ͬ��
        ptsex                   number          default 0,                       ##��ͬ�Ա�
        ptzhengjianhao          varchar(100),                                    ##��֤ͬ�����룬���պŻ������֤�ŵ�

)

create table tbl_members (
        id                      int	  auto_increment not null,         ##����
        userid       		varchar(30)   		not null unique,   ##�û�ID
	mmuserid                varchar(32),                          
        siteid       		int      		not null,     ##վ��ID
        userpwd      		varchar(32)   		not null,     ##�û�����  MD5��ʽ
        nickname     		varchar(30)		not null,     ##��ʵ����
        email                   varchar(50),                          ##�����ʼ�
        mphone                  varchar(12),                          ##�ֻ�����
        phone                   varchar(12),                          ##��ϵ�绰
        sex                     smallint                default 0,    ##0��ʾ�� 1��ʾŮ
        country                 varchar(30),                          ##����
        province                varchar(50),                          ##ʡ
        city                    varchar(50),                          ##��
        zone                    varchar(50),                          ##����
        jiedao                  varchar(50),                          ##�ֵ�
        shequ                   varchar(50),                          ##����
	area                    varchar(50),                          ##��������
        postcode                varchar(6),                           ##��������
        birthdate               date,                                 ##��������
        myimage                 varchar(100),                         ##ͷ��
        usertype                smallint                default 0,    ##�û�����    0---ϵͳ�����û�   5--��վע���û�  6--��ҵ�ڲ�ҵ���û�        
        joincompanyid           int,                                  ##������ID��
        joinid                  int,                                  ##���������к�
        dflag                   int,                                  ##վ���Ƿ�ɾ��
        createarticles	        int                     default 0,    ##�û�������������
        editarticles		int                     default 0,    ##�û��޸���������
        deletearticles	        int                     default 0,    ##�û�ɾ����������
        score                   int                     default 0,    ##�û�����
        company                 varchar(200),                         ##�û�������λ
        address                 varchar(500),                         ##������λ��ַ
        department              varchar(50),                          ##��������
        emailaccount            varchar(100),                         ##email�ʺ�
        emailpasswd             varchar(100),                         ##email����
        departmentarticlestype  int                     default 0,    ##������µķ�Χ0-���в���1-������2-ָ������
        departmentarticlesids   varchar(500),                         ##ָ��������µĲ���id   
	trypassnum              int,                                  ##�����¼�Դ�Ĵ���
	trypasstime             date,                                 ##�ﵽ�Դ�����������ʱ��
	orgid                   int,                                  ##�û�������֯�ܹ�ID
	companyid               int,                                  ##�û�������˾ID
	deptid                  int,                                  ##�û���������ID
	CREATERID               int,                                  ##�û�������ID
	zone                    varchar(200),
        createdate              timestamp,                            ##�û���Ϣ����ʱ��
	lastupdate              timestamp,                            ##�û���Ϣ����޸�ʱ��
        primary key (id)
);
insert into tbl_members (userid,siteid,userpwd,nickname,email,mphone,joincompanyid,joinid,dflag,createarticles,editarticles,deletearticles,score,createdate,company,address) values('admin',-1,'4346D362F67C89D7AB4E28732A1B1CE8','ϵͳ����Ա','','',0,0,0,0,0,0,0,current_date(),'','');

##ϵͳ��ɫ��
create table tbl_member_roles (
       id                       int	 auto_increment not null,
       siteid                   int                     not null,
       userid                   varchar(30)             not null,
       deptid                   int                     not null,      ##�û���������ID       
       rolename                 varchar(20)             not null,      ##��ϢԱ   ���Ÿ�����   ����
       rolelevel                smallint,                              ##��ɫ���� 0-9
       primary key(id)
);

##��Ȩ�û���������Ա�
create table tbl_member_authorized_resouce (
       id                       int auto_increment         not null,
       siteid                   int                        not null,
       userid                   varchar(30)                not null,
       lwid                     int,                                     ##���û���������Ա�ID
       lwname                   varchar(100),                            ##���û���������Ա������
       lwrole                   varchar(20),                             ##���԰�����ɫ ��ĳ�����԰�Ĺ���Ա �������԰�Ĳ��Ź���Ա
       contenttype              int                         default 0,  ##�������� 0Ϊ���԰�
       primary key(id)
);

-- ��֯�ܹ���
create table tbl_organization
(
       id                      int auto_increment not null,              ##����ID
       siteid                  int,
       parent                  int,                                      ##��ID
       cotype                  int default 0,                            ##�ڵ����� 1--�ӹ�˾   2--����
       customerid              int,                                      ##�ͻ�ID����Ӧ��վ��ID
       orderid                 int,                                      ##�ڵ���ͬ���е����
       nodeid                  int,                                      ##�ڵ��Ӧ�ӹ�˾������
       status                  int default 1,                            ##״̬ 0 ��Ч 1 ��Ч
       name                    varchar(255) not null,                    ##�ڵ�����
       ename                   varchar(255),                             ##�ڵ�Ӣ������
       llevel                  int default 1 not null,                   ##��֯�ṹ�㼶
       orgcode                 varchar(50),                              ##��֯�ڵ����
       isleaf                  smallint default 0,                       ##�Ƿ���Ҷ�ӽ�ռ��0Ϊ��Ҷ�ӽڵ㣬1ΪҶ�ӽڵ�
       lastupdate              timestamp default CURRENT_TIMESTAMP,      ##����޸�����
       createdate              timestamp default CURRENT_TIMESTAMP,      ##��������
       createuser              varchar(50),                              ##������
       updateuser              varchar(50),                              ##�޸���
       primary key(id)
);

##��˾��Ϣ��
create table tbl_companyinfo(
       ID                      int auto_increment not null,              ##����
       orgid                   int,                                      ##��˾������֯ID
       SITEID                  int,                                      ##��˾վ��ID 
       COMPANYNAME             varchar(100),                             ##��˾����
       companyenname           varchar(100),                             ##��˾Ӣ������
       opendate                date,                                     ##��˾��������
       COMPANYADDRESS          varchar(100),                             ##��˾���ڵ�ַ
       COMPANYPHONE            varchar(50),                              ##��˾��ϵ�绰
       COMPANYFAX              varchar(50),                              ##��˾��ϵ����
       COMPANYWEBSITE          varchar(100),                             ##��˾վ������
       COMPANYEMAIL            varchar(100),                             ##��˾email��ַ
       POSTCODE                varchar(20),                              ##��˾��������
       companyclassid          int,                                      ##��ҵ��������       
       CLASSIFICATION          varchar(200),                             ##��˾������������
       SUMMARY                 varchar(1000),                            ##��˾���� 
       COMPANYPIC              varchar(200),                             ##��˾ͼƬ
       PUBLISHFLAG             int,                                      ##�������
       status                  smallint default 0,                       ##״̬  0--���ⷢ��   1-�����ⷢ��
       COUNTRY                 varchar(12),                              ##��˾���ڹ���
       PROVINCE                varchar(12),                              ##��˾����ʡ��
       CITY                    varchar(12),                              ##��˾������
       ZONE                    varchar(12),                              ##��˾��������
       TOWN                    varchar(12),                              ##��˾��������
       VILLAGE                 varchar(12),                              ##��˾���ڴ�
       COMPANYLATITUDE         decimal(38,15),                           ##��˾���ڵصľ���
       COMPANYLONGITUDE        decimal(38,15),                           ##��˾���ڵص�γ��
       MPHONE                  varchar(12),                              ##��ϵ���ƶ��绰
       BIZPHONE                varchar(12),                              ##��ϵ�˵绰
       CONTACTOR               varchar(12),                              ##��ϵ������
       SAMSITEID               int,                                      ##����վ��ID
       COMPANYLEVEL            int,                                      ##��˾����
       qq                      varchar(50),                              ##��˾QQ
       weixin                  varchar(50),                              ##��˾΢��
       weibo                   varchar(50),                              ##��˾΢��
       createdate              timestamp,                                ##��������
       updatedate              timestamp,                                ##�޸�����
       createuser              varchar(50),                              ##������
       COMPCODE                varchar(50),                              ##��˾����
       LEGAL                   varchar(500),                             ##��˾����
       COMPSHORTNAME           varchar(200),                             ##��˾������
       ALIASFLAG               int,                                      ##��������һ����˾�ı���,1��ʾ��˾�����ƣ�0��ʾ����
       primary key(id)
);

##��ҵ��������
create table tbl_department (
       id                      int auto_increment not null,
       orgid                   int,                                      ##��˾������֯ID
       companyid               int                not null,              ##������˾ID
       siteid                  int                not null,              ##����վ��ID
       cname                   varchar(100)       not null,              ##������������
       ename                   varchar(100),                             ##����Ӣ������
       telephone               varchar(12),                              ##������ϵ�绰
       manager                 varchar(50),                              ##���ž���
       vicemanager             varchar(50),                              ##���Ÿ�����
       leader                  varchar(50),                              ##���������쵼
       lastupdate              timestamp,                                ##����޸�����
       createdate              timestamp,                                ##��������
       createuser              varchar(50),                              ##������
       EMAIL                   varchar(200),                             ##������ϵ�ʼ� 
       SHORTNAME               varchar(200),                             ##���ż��
       primary key(id)
);          


##ÿ���û���ÿ��վ���ÿ����Ŀ��¼�����������
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
	id                      int  auto_increment  not null,             ##����
	columnid                int                  not null,             ##��ĿID
	column_audit_rule       varchar(300)	     not null,             ##��Ŀ��˹���
	messagetype             int  default 0,                            ##0��ʾ��Ŀ������Ϣ  1��ʾ�û�������Ϣ
	audittype               int  default 0,                            ##������˷�ʽ 0-���û����ƶ�����˹���   1-����ɫ������˹���
	version                 int  default 0,                            ##��˹���汾�ţ����ֵ��ʾ��ǰ�汾
	createdate              timestamp	     not null,
	lastupdated             timestamp            not null,
	creator                 varchar(30)          not null,
	editor                  varchar(30)          not null,
        primary key (id)
);

##�����롱��ϵ���ֵ���˲���·��
create table tbl_column_auditing_process (
	id                   	int  auto_increment  not null,
	rulesid             	int                  not null,
	subrules                varchar(300),                            ##�ֽ���OR��˹���
	createdate           	timestamp,
	lastupdated          	timestamp,
	creator              	varchar(30)	     not null,
	editor               	varchar(30)	     not null,
        primary key (id)
);


create table tbl_article (
	id                   	int  auto_increment     not null,           ##����
	columnid             	int             	not null,           ##����������ĿID
	siteid                  int                     not null,           ##��������վ��ID     վ��ID=1
	sortid               	int,                                        ##����������ţ���Ȼ���к�
	maintitle            	varchar(600)      	not null,           ##���±���
	vicetitle            	varchar(600),                               ##���¸�����
	summary              	varchar(1000),                              ##��������
	keyword              	varchar(200),                               ##���¹ؼ���
	source               	varchar(600),                               ##������Դ
	content              	text,                                       ##��������
	emptycontentflag     	smallint		not null,           ##���������Ƿ�Ϊ��  �����ϴ��ļ�����Ϊ1 ��������Ϊ0
	author               	varchar(600),                               ##��������
	articleversion       	int,                                        ##���°汾��
	publishtime          	timestamp		not null,           ##���·���ʱ��
	createdate           	timestamp		not null,           ##���´���ʱ��
	lastupdated          	timestamp		not null,           ##��������޸�ʱ��
	dirname              	varchar(200)		not null,           ##����·��
	filename             	varchar(60),                                ##�ļ�����
	editor               	varchar(30)        	not null,           ##�༭����
	creator                 varchar(30)        	not null,           ##��Ϣ����������
	status               	smallint   		not null,           ##����״̬      0-����   1-δ��
	topflag                 smallint                default 0           ##�ö���־��1��ʾ�ö� 0-����˳��
	redflag                 smallint                default 0           ##Ʈ���־��1��ʾƮ�� 0-����
	boldflag                smallint                default 0           ##�Ӻڱ�־��1��ʾ�Ӻ� 0-����
	doclevel             	int                 	not null,           ##������Ȩ��
	vicedoclevel		int,                                        ##���¸�Ȩ��
	pubflag              	smallint   		not null,           ##���·�����־λ    0-�¸�         1-������
	auditflag            	smallint 		not null,           ##������˱�־λ    1-����         0-������
	subscriber           	smallint   		not null,           ##���¶���          0-��������   1-������
	lockstatus           	smallint 		not null,           ##����������־λ    0-δ����       1-����
	lockeditor           	varchar(30),                                ##����������
	auditor              	varchar(200),                               ##��������
	ispublished          	smallint 		not null,           ##�����Ƿ�������������
	relatedartid         	varchar(40),                                ##δ��
	indexflag            	smallint		not null,           ##�����Ƿ񱻼��뵽ȫ���������ݿ�
	isjoinrss		smallint		not null,           ##�����Ƿ����RSS
	clicknum		int			not null,           ##���±��������
	referID			int			not null,           ##δ��
        multimediatype          smallint                default 0,          ##��ý�����±�־0-���Ƕ�ý������ 1-�Ƕ�ý������	
	modelID			int			not null,           ##����ʹ�õķ���ģ��
	articlepic		varchar(600),                               ##����ʹ�õ���ЧͼƬ
	changepic               int                     default 0,          ##����ͼƬ��Ч����
	saleprice            	decimal(9,2),                               ##��������ʹ��  ��Ʒ�ۼ�
	inprice              	decimal(9,2),                               ##��������ʹ��  ��Ʒ����
	marketprice          	decimal(9,2),                               ##��������ʹ��  ��Ʒ�г���
	vipprice          	decimal(9,2),	                            ##��������ʹ��  ��ƷVIP��
	stocknum             	int,                                        ##��������ʹ��  ��Ʒ�����
	weight               	int,                                        ##��������ʹ��  ��Ʒ����
	brand                	varchar(50),                                ##��������ʹ��  ��ƷƷ��
	pic                  	varchar(30),                                ##��������ʹ��  ��ƷСͼƬ
	bigpic               	varchar(30),                                ##��������ʹ��  ��Ʒ��ͼƬ
	score                   int,                                        ##��������ʹ��  ��Ʒ����
	voucher                 decimal(9,2),                               ##��������ʹ��  ����ȯ
	urltype		        smallint                default 0,          ##δ��
	defineurl		varchar(200),                               ##�����Զ���URL
	notearticleid           int                     default 0,          ##�������ƪ������������������µ����ݣ��������µ����ݿ����Ǳ�ҳ��һ����ǩ������
	wordsnum		int                     default 0,          ##������������
	downfilename	        varchar(100),                               ##δ��
	beidate                 timestamp,                                  ##δ��    
        salesnum                int,                                        ##��Ʒ��������
        commentnum              int                     default 0,          ##�������۵�����
	fromsiteid              int                     default 0,          ##��ʶץȡ��Ϣ����Դվ��
	sarticleid              varchar(50),                                ##ץȡ������Դ��վ��ID 
        md5title                varchar(35),                                ##���±����MD5��ʽժҪ
        mediafile               varchar(500),                               ##��ý���ļ�����
	processofaudit          int                     default 0,          ##���������ִ�е�����˲���
	t1                      int                     default 0,          ##����1
	t2                      int                     default 0,          ##����2
	t3                      int                     default 0,          ##����3
	t4                      int                     default 0,          ##����4
	t5                      int                     default 0,          ##����5
	deptid                  varchar(50),                                ##���·����Ĳ���ID
	sign                    varchar(32),                                ##���µ�����ǩ�������������Ϊ���±���+ID������ǩ����ץȡ����Ϊ���µ�URL����ǩ��
        sourceurl               varchar(1000),                              ##���ɼ���Ϣ��URL
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
        ID                      int auto_increment          not null,        --����ID
        siteid		        int                         not null,        --վ��ID
        columnid	        int                         not null,        --��ĿID
        modelid                 int                         not null,        --ģ��ID
	markid                  int                         not null,        --���ID
	markname                varchar2(500),                               --�������
        articleid               int                         not null,        --����ID
	createdate              timestamp,                                   --����ʱ��
	lastupdate              timestamp,                                   --�޸�ʱ��
        primary key (id)
);


--��ץȡ�ļ�����ϢԴ��ص���Ϣ
create table tbl_article_sourceinfo
(
        id                            int auto_increment   not null,
        articleid                     int                  not null,
	maintitle            	      varchar(600)         not null,           ##���±���        
	md5title                      varchar(35),                             ##���±����MD5��ʽժҪ
	publishtime_from_source       datetime		   not null,           ##�����ڱ�ץȡ��վ�ķ���ʱ��
	sourceid                      int,                                     ##ץȡ������Դ��վ��ID 
        createdate                    datetime,                                ##��Ϣ��ץȡ��ʱ��
        primary key (id)        
);

create table tbl_attachment_class (
	id                   	int auto_increment         not null,            ##����ID ��ĿID
	siteid               	int                        not null,            ##վ��ID
	cname                	varchar(50)        	   not null,            ##������������
	summary                	varchar(500)        	   not null,            ##��������
	editor               	varchar(30)                not null,            ##�༭
	createdate           	timestamp                  not null,            ##��������
	lastupdated          	timestamp		   not null,            ##�����޸�����
        primary key (id)
);

create table tbl_attachment_class_part (
	id                   	int auto_increment         not null,            ##����ID ��ĿID
	classid                 int                        not null,            ##������������ID
	siteid               	int                        not null,            ##վ��ID
	cname                	varchar(50)        	   not null,            ##���������������
	ename                	varchar(50)        	   not null,            ##�������Ӣ������
	cltype              	varchar(10)                not null,            ##��������
	editor               	varchar(30)                not null,            ##�༭
	createdate           	timestamp                  not null,            ##��������
	lastupdated          	timestamp		   not null,            ##�����޸�����
	CONSTRAINT fk_articleAttachID FOREIGN KEY (classid) REFERENCES  tbl_attachment_class(ID) ON DELETE CASCADE,
	primary key (id)
);


create table tbl_relatedartids                                                  ##ҳ������ļ���
(
        ID                      int auto_increment         not null,            ##����ID
        cname                   varchar(100)               not null,            ##������������
        pageid                  int                        not null,            ##����ID
        content                 text,                                           ##��������
	dirname                 varchar(255),                                   ##����·��
        contenttype             smallint,                                       ##1�ϴ��ļ�  2ͼƬ�ļ� 3ȫ��ͼƬ�ļ� 4��ý���ļ� 5htmlҳ��
        filename                varchar(200),                                   ##�����ļ�����
        summary                 varchar(250),                                   ##�����ļ�����
        editor                  varchar(50),                                    ##�ϴ���
        createdate              timestamp,                                      ##������������
	lastupdate              timestamp,                                      ##
	primary key (id)        
);

create table tbl_turnpic (                                                      ##ͼƬ�ֻ���
        id                      int auto_increment         not null,
        articleid               int                        not null,
	sortid                  int,                                            ##ͼƬ��ʾ˳��
	mediaurl                varchar(500),                                   ##��ý���ļ�·��
        smallpic1url            varchar(500),                                   ##СͼƬ1URL
	smallpic2url            varchar(500),                                   ##СͼƬ2URL
	smallpic3url            varchar(500),                                   ##СͼƬ3URL
	latitude                decimal(38,15),                                 ##ͼƬ�ľ���
	longitude               decimal(38,15),                                 ##ͼƬ��γ��
        picname                 varchar(100),
        createdate              timestamp,
        notes                   varchar(2000),
	lib1                    varchar(100),                                   ##��ǩ1
        lib2                    varchar(100),                                   ##��ǩ2
        lib3                    varchar(100),                                   ##��ǩ3
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
        keywordnum              int                        default 0,           ##�ؼ��ʸ���
        flag                    smallint                   default 0,           ##1��ѡ������ڹؼ����б���        
        url			varchar(200),
        primary key (id)
);

create table tbl_multimedia
(
        id                      int auto_increment          not null,           ##����
        siteid                  int                         not null,           ##վ��id 
        articleid               int                         default 0,          ##����id
        dirname                 varchar(100)                not null,           ##��Ŀ·��
        filepath                varchar(100)                not null,           ##�ļ�·��
        oldfilename             varchar(100)                not null,           ##ԭ�ļ�����
        newfilename             varchar(100)                not null,           ##���ļ�����
        encodeflag              int                         default 0,          ##�ļ�ת����־0-δת�� 1-�Ѿ�ת��
        infotype                smallint                    default 0,          ##0�������ݵ�ͼƬ    1��ҵ������ϢͼƬ    2վ�������ϢͼƬ
        createdate              timestamp                   not null,           ##����ʱ��
        primary key(id)
);

create table tbl_deleted_article (
        articleid               int                not null,
        siteid                  int                not null,
	columnid                int                not null,
	sitename                varchar(100),
	acttype                 smallint           default    0,                ##0��ʾ��ɾ��������   1��ʾ���޸ĵ�����        
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
        modelid                 int,                                      ##ģ��ID
	picbrief                varchar(500),                             ##ģ��ͷͼ����
	pich                    int,                                      ##ͷͼ�߶�
	picw                    int,                                      ##ͷͼ���
	picurl                  varchar(500),                             ##ͷͼURL
	pichref                 varchar(500),                             ##ģ��ͷͼ������
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
	islocation              int      default 1,                ##��Ŀ�洢λ����Ϣ
	columndesc              varchar(200),
	xmltemplate             varchar(3000),
	encodingflag            int,                                    --�ĵ������ʽ 1-utf-8  2-gb2312  3-gbk
	doctype                 varchar(500),                             --�ĵ�����   HTML5  HTML4
	contentshowtype	        smallint,
	isrss                   smallint    default 0,
	getRssArticleTime       smallint,
	archivingrules          smallint    default 0,
	useArticleType          smallint    default 0,
	istype                  smallint    default 0,
	xmltype                 varchar(3000),                         ##���·���Ķ���
	userflag                smallint    default 0,                 ##��Ŀ�����Ƿ�ֻ��ע���û��ɿ�  0##�����û� 1##ע���û�
	userLevel               int         default 0,                 ##�û�������µļ���
	shareflag               smallint    default 0,                 ##��Ŀ�Ƿ��Ǻ�������վ�������Ŀ
	articlecount            int         default 0,                 ##����Ŀ������������
	titlepic                varchar(10),                           ##����ͼƬ��С
	vtitlepic               varchar(10),                           ##������ͼƬ��С
	sourcepic               varchar(10),                           ##��ԴͼƬ��С
	authorpic               varchar(10),                           ##����ͼƬ��С
	contentpic              varchar(10),                           ##����ͼƬ��С
	specialpic              varchar(10),                           ##������ЧͼƬ��С
	productpic              varchar(10),                           ##��Ʒ��ͼƬ��С
	productsmallpic         varchar(10),                           ##��ƷСͼƬ��С	
        ts_pic                  varchar(10),                           ##��СͼͼƬ��С
        s_pic                   varchar(10),                           ##СͼͼƬ��С
        ms_pic                  varchar(10),                           ##��СͼͼƬ��С
        m_pic                   varchar(10),                           ##����ͼƬ��С 
        ml_pic                  varchar(10),                           ##�д���ͼƬ��С
        l_pic                   varchar(10),                           ##����ͼƬ��С
        tl_pic                  varchar(10),                           ##�ش���ͼƬ��С       
        primary key (id)
);

insert into tbl_column (id,siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate,lastupdated,isdefineattr,hasarticlemodel,isaudited,isproduct,ispublishmore,languagetype,columndesc,xmltemplate,encodingflag,doctype) values(0,0,-1,0,'��ҳ','home','/','admin','html',current_date(),current_date(),0,0,0,0,0,0,'','',0,'');


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
	ispublished             smallint     default 0,             ##����Ƿ񷢲�����0##δ  1##��
        articlenum              int          default 0,            ##���һ�η������µ����������������б���������	
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

##��Ŀģ��ͱ�ǵ���ر�
CREATE TABLE tbl_templatemark (
	ID                   int  auto_increment  NOT NULL,                   ##���
	siteid               int    not null,                   ##վ��id
	columnid             int    not null,                   ##ģ��������ĿID
        tid                  int    not null,                   ##ģ��id
        mid                  int    not null,                   ##��ģ���ϵı��id
	CONSTRAINT pk_tmid PRIMARY KEY (ID),
	CONSTRAINT fk_templateMarkID FOREIGN KEY (tid) REFERENCES  tbl_template(ID) ON DELETE CASCADE
);

##�������Ŀ����ر�
CREATE TABLE tbl_markcolumn (
	ID                   int auto_increment   NOT NULL,                   ##���
	siteid               int    not null,                   ##վ��id
        mid                  int    not null,                   ##���id
        cid                  int    not null,                   ##������õ���Ŀid
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
	pusharticle	        smallint  default 0,                ##�����ļ�����
	movearticle	        smallint  default 0,                ##�ƶ��ļ�����
	config                  varchar(200),
        sitetype                int       default 0,                ##�����͹���վ�������,�����������վ��������վ�ȵ�
	sitepic                 varchar(200),                       ##վ����ҳͼƬ
	sitevalid               smallint  default 0,                ##0-������վ 1-��ͨ��վ 2-������վ
	sitelogo                varchar(200),                       ##վ��LOGO
	banner                  varchar(200),                       ##վ����BANNER
	navigator               int       default 0,                ##վ�㵼����ʽ
	samsiteid               int       default 0,                ##����վ��ID
	scores                  int       default 0,                ##���ֺͻ��Ҷһ�����
	sharetemplatenum        int       default 0,                ##��������վ���ģ���׺�
	managesharesite         smallint  default 0,                ##������վ����Ϣ   0-������  1-����
	copyright               varchar(1000),	                    ##��վ��Ȩ
	encodeflag              smallint          default 1,                       --�ĵ������ʽ 1-utf-8  2-gb2312  3-gbk
        titlepic                varchar(10),                       ##����ͼƬ��С
	vtitlepic               varchar(10),                       ##������ͼƬ��С
	sourcepic               varchar(10),                       ##��ԴͼƬ��С
	authorpic               varchar(10),                       ##����ͼƬ��С
	contentpic              varchar(10),                       ##����ͼƬ��С
	specialpic              varchar(10),                       ##������ЧͼƬ��С
	productpic              varchar(10),                       ##��Ʒ��ͼƬ��С
	productsmallpic         varchar(10),                       ##��ƷСͼƬ��С 
        ts_pic                  varchar(10),                       ##��СͼͼƬ��С
        s_pic                   varchar(10),                       ##СͼͼƬ��С
        ms_pic                  varchar(10),                       ##��СͼͼƬ��С
        m_pic                   varchar(10),                       ##����ͼƬ��С 
        ml_pic                  varchar(10),                       ##�д���ͼƬ��С
        l_pic                   varchar(10),                       ##����ͼƬ��С
        tl_pic                  varchar(10),                       ##�ش���ͼƬ��С       
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
       cname                    varchar(200)          not null,          ##��������������
       picurl                   varchar(500),                            ##������HTML����
       picname                  varchar(500),                            ##��������HTML����
       notes                    varchar(250),
       createdate               timestamp,                               ##��������
       primary key(id)
);


##������Ŀ֮���������µĶ�Ӧ��ϵ��������õ�����
create table tbl_refers_article (
        id                      int  auto_increment   not null,
	articleid               int                   not null,     ##����ID
        siteid                  int                   not null,     ##����ԭվ��ID
	columnid       	        int 	              not null,     ##Ŀ����Ŀ��ID
	scolumnid               int                   not null,     ##Դ��ĿID
	columnname	        varchar(100),                       ##Ŀ����Ŀ����������
	orders		        smallint   default 0,
	createdate	        timestamp,
	title		        varchar(600),                       ##��Ŀ����Ŀ�еı�������
	status                  smallint              not null,     ##Ŀ����Ŀ�е�״̬
	pubflag                 smallint              not null,
	auditflag               smallint              not null,
	usearticletype          int,
	refers_column_status    int,
	tsiteid                 int,                                ##Ŀ��վ��ID
	artfrom                 smallint,                           ##0-��Ŀ���崦���õ����ã�1-����¼��ʱ���õ�����    
	primary key(id)
);

##��Ŀ������ѡ��ı��������µ���Ŀ
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
	targetid                int                   not null,       ##��Ҫ���������µ�ID����Ŀ��ID
	type                    smallint,		              ##1����,2��Ŀģ��,3��ҳģ��,4���
	status                  smallint,
	createdate              timestamp,
	publishdate             timestamp,
	uniqueid                varchar(50),                                  ##���Դ洢ע���Ա��ID
	title                   varchar(200),
        errcode                 int           default 0,              ##��ҵ��������ʱ����������,Ĭ��0��ʾ�ɹ�����
        errmsg                  varchar(500),                        ##���ִ���ʱ�洢������Ϣ
        errnum                  smallint      default 0,              ##�洢������������·����Ĵ�����������·���3��
        priority                smallint      default 0,              ##��ҵ�����ȼ� 0-��ʾһ�����ȼ�
	creator                 varchar(100),                         ##��ҵ�Ĵ�����
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

##�����չ����
create table tbl_workdayinfo
(
    days                    varchar(50)   not null,
    siteid                  int      default 0 not null,
    worddayflag             int      default 0 not null, ##0������1�ǹ�����
    primary key(days)
); 

##LOG����ϵͳ
create table tbl_pv_detail(
        id                 bigint  auto_increment        not null,        ##����ID
	siteid             int                           not null,        ##վ��ID
	urlname            varchar(500),                                  ##URL��ַ
	url_cn_name        varchar(500),                                  ##��������
	pageview           int,                                           ##ҳ�������
	logdate            timestamp,                                     ##logʱ��
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
create index articleindex_parent_article on tbl_article(RELATEDARTID);    ##������ID������
create index articleindex_forsiteid on tbl_article(siteid);
create index articleindex_editor on tbl_article(editor);
create index columnindex_forsiteid on tbl_column(siteid);
create index templateindex_forsiteid on tbl_template(siteid);
create index templateindex_forcolumnid on tbl_template(columnid);
create index templateindex_forreferid on tbl_template(ReferModelID);
create index order_fororderid on tbl_orders(userid);


##������ע���
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
       summary     varchar(250),                                   ##��˾������Ϣ����       
       dirname     varchar(200),
       editor      varchar(30),
       extname     varchar(10),
       companynum  int                   default 0,       
       createdate  timestamp,
       lastupdated timestamp,
       primary key(id)
);
insert into tbl_companyclass values(0,0,-1,0,'��˾����','company','','/','admin','html',0,current_date(),current_date());

##������վ����
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
insert into tbl_websiteclass values(0,0,-1,0,'��ַ����','website','/','admin','html','',0,current_date(),current_date());

##��վ������Ϣ
create table tbl_websiteinfo(
       id                                        int auto_increment,             ##����
       siteid                                    int,                            ##����վ��
       websitename                               varchar(100),                   ##��˾����
       websiteurl                                varchar(200),                   ##��˾���ڵ�ַ
       summary                                   varchar(200),                   ##��˾��ϵ�绰
       editor                                    varchar(50),                    ##�༭
       pubflag                                   smallint,                       ##�������
       createdate                                timestamp,                           ##��������
       updatedate                                timestamp,                           ##�޸�����
       primary key(id)
);

##��������
create table tbl_program_library
(
        id            int auto_increment not null,         ##������������
        p_type        int,                                 ##��������
        position      int,                                 ##JSPҳ���е�λ��
        l_type        int,                                 ##��������
        explain1      varchar(100),                        ##����˵��
        notes         varchar(500),                        ##����ע��
        program       long,                                ##������
        primary key(id)
);

create table tbl_program_position
(
        id            int auto_increment not null,        ##����
        position      varchar(50),                        ##λ��˵��
        primary       key(id)
);

insert into tbl_program_position (id,position) values(1,'����ͷ');
insert into tbl_program_position (id,position) values(2,'��ʾ��');
insert into tbl_program_position (id,position) values(3,'ҳβ');

create table tbl_program_type
(
        id            int auto_increment not null,      ##����
        p_type        varchar(50),                      ##��������˵��
        primary       key(id)
);

insert into tbl_program_type (id,p_type) values(1,'��Ϣ����');
insert into tbl_program_type (id,p_type) values(2,'���ﳵ');
insert into tbl_program_type (id,p_type) values(3,'��������');
insert into tbl_program_type (id,p_type) values(4,'������ѯ');
insert into tbl_program_type (id,p_type) values(5,'�û�ע��');
insert into tbl_program_type (id,p_type) values(6,'�û�����');
insert into tbl_program_type (id,p_type) values(7,'�û���Ϣ����');
##������������

##��վǰ̨Ӧ�ñ�
##�û�ע���
create table tbl_userinfo
(
        ID                   int auto_increment     not null,       ##����
  	SITEID               int,                                   ##վ��ID
  	MEMBERID             varchar(50)            not null,       ##�û�ID
  	USERNAME             varchar(30),                           ##�û���������
  	PASSWORD             varchar(20),                           ##�û�����
  	LINKMAN              varchar(30),                           ##��ϵ��
  	nation               varchar(30),                           ##����
  	degree               varchar(30),                           ##�Ļ��̶�
  	idno                 varchar(19),                           ##���֤����  	
  	street               varchar(100),                         ##���ڽֵ�  	
  	CITY                 varchar(20),                           ##���ڳ���
  	ADDRESS              varchar(200),                          ##�����ַ
  	COUNTRY              varchar(50),                           ##����
  	POSTALCODE           varchar(10),                           ##��������
  	PHONE                varchar(20),                           ##�绰
  	FAX                  varchar(20),                           ##����
  	EMAIL                varchar(100),                          ##�����ʼ�
  	HOMEPAGE             varchar(100),                          ##������ҳ
  	REMARK               varchar(500),                          ##��������
  	USERTYPE             int,                                   ##�û�����
  	VALID                int,                                   ##�Ƿ���Ч
  	SEX                  varchar(10),                           ##�û��Ա�
  	OICQ                 varchar(50),                           ##�û�QQ����
  	BIRTHDAY             date,                                  ##��������
  	IMAGE                varchar(50),                           ##�û�ͷ��
  	SIGN                 varchar(50),                           ##�û�ǩ��
  	scores               int  default 0,                        ##�û�����
  	meilizhi             int  default 0,                        ##����ֵ
  	LOGINNUM             int,                                   ##��¼����
  	GRADE                varchar(50),                           ##�û����� 
  	THEONLINE            varchar(10),                           ##
  	RENZHENG             varchar(50),                           ##
  	MOBILEPHONE          varchar(50),                           ##�ƶ��绰
  	PROVINCE             int,                                   ##ʡ��
  	CREATEDATE           date,                                  ##����ʱ��
  	COMPANY              varchar(500),                          ##������λ
  	unitpcode            varchar(10),                           ##��λ��������
  	unitphone            varchar(20),                           ##��λ�绰
  	stationtype          varchar(8),                            ##վ̨���
  	entitytype           varchar(1),                            ##����/����
  	stationaddr          varchar(80),                           ##վ̨��ַ
  	opedegree            varchar(20),                           ##����֤�ȼ�
  	opecode              varchar(20),                           ##����֤�����
  	callsign             varchar(20),                           ##����
  	memo                 varchar(512),                          ##��ע      	
  	lockflag             int default 0 not null,                ##�Ƿ�����
        primary key (id)
);

##��Ȩ�û��ܹ��鿴����Ŀ
create table column_authorized(
        id                  int             	not null,
        siteid              int                 not null, 
        columnid            int                 not null,
        targetid            int                 not null,  ##�û�ID�����ǽ�ɫID
        type                smallint            default 0, ##0��ʾtargetidΪ��ɫid, 1��ʾtargetidΪ�û�id
        createdate          timestamp,
        primary key(id)
);

##��������
CREATE TABLE tbl_comment ( 
	id          int auto_increment not null,           ##����
	userid      varchar(20),                           ##��¼�û�ID,���û�е�¼����¼IP
	name        varchar(100),                          ##����������
	link        varchar(100),                          ##��������ϵ��ʽ
	content     varchar(2000),                         ##��������
	ip          varchar(20),                           ##������������IP��ַ
	about       int,                                   ##����ID
	Scores         int                      default 0 , ##����
	experience     varchar(500),                       ##ʹ���ĵ�
	Advantages     varchar(500),                       ##�ŵ�
	Shortcomings   varchar(500),                                    ##����
	usefulnum      int                      default 0,              ##��Ϊ���������õ�����
	nousenum       int                      default 0,              ##��Ϊ���������õ�����	
	flag           int                      default 0,            ##1 ���ͨ��������ʾ 0-���δͨ�� ����ʾ  2-ɾ�� 		
	createdate  timestamp,                             ##��������
	siteid      int,                                   ##վ��ID
	CONSTRAINT  pk_commentid PRIMARY KEY (id)	
);


##����
CREATE TABLE su_survey  (																##�����
	 id           int  auto_increment not null,   	   ##����
	 surveyname   varchar(200),			   ##��������
	 notes        varchar(2000),			   ##��������
	 createtime   timestamp,                           ##����ʱ��
	 siteid       int default 0 not null,		   ##վ��id	
	 useflag      int default 0 not null,		   ##��Ч��־ 0-��Ч1-��Ч
         primary key (id) 
);

CREATE TABLE  su_answer  (				   ##�𰸱�
	 id           int  auto_increment not null,	   ##����
	 userid       int,				   ##�û�id
	 sid   	      int,				   ##����id
	 qid   	      int,				   ##����id	
	 answers      varchar(200),			   ##��
	 other        varchar(200),			   ##������ֵ
         primary      key(id)
);

CREATE TABLE  su_danswer  (			           ##����𰸱�
	 id           int  auto_increment not null,	   ##����
	 qid          int  default 0 not null,	           ##����id
	 qanswer      varchar(200),		           ##�����
	 picurl       varchar(100),                        ##ͼƬ��ַ
         primary      key(id) 
);

CREATE TABLE  su_dquestion  (				   ##���ⶨ���
	 id           int  auto_increment not null,	   ##����
	 sid          int  not null,                       ##����id
	 qname        varchar(500),                        ##��������
	 qtype        int,                                 ##��������
	 qmust        int,                                 ##�Ƿ��ѡ
	 nother       int,                                 ##�Ƿ���Ҫ�����	
	 atype        int,                                 ##������
         primary      key(id) 
);

create table su_defineuserinfo
(
        ID         int  auto_increment not null,
        SID        int  default 0,                         ##����ID
        USERNAME   varchar(100),                           ##�ύ�����û���
        PHONE      varchar(100),                           ##�ύ�����û��绰
        CREATEDATE timestamp,
        primary key(id)
);

##��Ϣ����
CREATE TABLE tbl_feedback
(
	 id          int  auto_increment not null,          ##����
	 userid      varchar(50),                           ##��¼�û�ID,���û�е�¼����¼IP
	 email       varchar(100),                          ##�û��ĵ����ʼ�
	 phone       varchar(20),                           ##�û��绰
	 title       varchar(200),                          ##����
	 content     varchar(4000),                         ##��������
	 createtime  timestamp,                             ##��������
	 answer      varchar(4000),                         ##�����
	 answertime  timestamp,                             ##�ش�ʱ��
	 flag        int  default 0,                        ##�ش���Ƿ�����վ����ʾ��1##��ʾ 0##����ʾ
	 siteid      int,                                   ##վ��ID
         CONSTRAINT pk_feedback_id PRIMARY KEY (ID)                                        
);

##�ҵĵ�ַ��
CREATE TABLE  tbl_userinfos (
	id            int  auto_increment not null,         ##����
	username      varchar(20),                          ##�û�����
	address       varchar(200),                         ##��ַ
	email         varchar(100),                         ##�����ʼ�
	sex           int,                                  ##�Ա�
	mobilephone   varchar(20),                          ##�ƶ��绰
	postcode      varchar(10),                          ##�ʱ�
	phone         varchar(20),                          ##�绰
	corporation   varchar(500),                         ##��˾
	fax           varchar(20),                          ##����
	memberid      varchar(50),                          ##�û�id
	createtime    int                  not null,        ##����ʱ��
	CONSTRAINT pk_userinfos_id PRIMARY KEY (ID)
);

##ÿ����ˮ�Ź����
create table tbl_lshbydate(
       cdate   timestamp,
       lsh     varchar(15)
);

##�û�����
create table tbl_leaveword
(
	id             int  auto_increment    not null,           ##����
	columnid       int                    default 0,          ##��ѯ������ඨ��
  	siteid         int                    not null,           ##վ��id
  	code           varchar(12),                               ##�û���ѯ��ˮ��   AyyyymmddXXX    ����ĸA��ʾ�ֳ�����  B��ʾ������ѯ C��ʾ�����ײ�
  	sex            smallint,                                  ##�������Ա�
  	userid         varchar(50),                               ##��¼�û�ID,���û�е�¼����¼IP
	flag           int                    default 0,          ##�Ƿ�����վ����ʾ��1--��ʾ 0--����ʾ  	
  	title          varchar(100),                              ##���Ա���
  	content        varchar(4000),                             ##��������
  	writedate      timestamp,                                 ##��������
  	company        varchar(200),                              ##��˾
  	linkman        varchar(100),                              ##��ϵ��
  	links          varchar(500),                              ##��ϵ��ʽ
  	zip            varchar(20),                               ##����
  	email          varchar(100),                              ##�����ʼ�
  	phone          varchar(20),                               ##�绰
  	formid         int,                                       ##�û����Ա�ID
  	departmentid   int,                                       ##��Ϣ�ַ����Ĳ���ID
	retcontent     varchar(2000),                             ##�ظ�������
  	auditor        varchar(50),                               ##�����Ҫ��ˣ������������Ϣ
  	auditflag      int                    default 0,          ##0��ʾ������  1��ʾ��˽�����
  	mphone         varchar(20),                               ##�ƶ��绰
  	processor      varchar(50),                               ##�������û�ID
	valid          smallint               default 0,          ##�����Ƿ���Ч 0--��Ч 1--��Ч
	validreason    varchar(512),                              ##������Ч��ԭ��
	datefromdept   timestamp,                                 ##���Żظ�����
	finalflag      smallint               default 0,          ##���ջظ��û���־λ�����ظ������׼�������԰岿�Ź���Ա�����԰����Ա��֮����Ϊ1
	endtouser      timestamp,                                 ##���ջظ��û�����
	CONSTRAINT pk_leaveword_id PRIMARY KEY (ID)
);
create index leavemsgindex_forsiteid on tbl_leaveword(siteid);

create table tbl_leaveword_to_dept
(
	id             int  auto_increment    not null,           ##����
  	siteid         int                    not null,           ##վ��id
  	departmentid   int,                                       ##��Ϣ�ַ����Ĳ���ID
  	formid         int,                                       ##�û����Ա�ID
  	lwid           int,                                       ##�û�����ID
  	processor      varchar(50),                               ##�ش����û�ID
	flag           int                    default 0,          ##�Ƿ��Ѿ��ش�1--�ش� 0--δ�ش�  	
  	content        varchar(4000),                             ##�ش�����
  	retdate        timestamp,                                 ##�ش�����
  	createdate     timestamp,                                 ##��������û�������
	CONSTRAINT pk_leaveword_to_dept_id PRIMARY KEY (ID)
);

##�û����Է������
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
        lwnum          int                    default 0,           ##ĳ�������������
	createdate     timestamp              not null,
	lastupdated    timestamp              not null,
	primary key(id)
);
insert into tbl_leavewordclass values(0,0,-1,0,'���Է���','lwsite','/','admin','html','',0,current_date(),current_date());

create table tbl_leaveword_valid_reason
(
	id             int                    not null,            ##����
  	siteid         int                    not null,            ##վ��id
        content        varchar(500)           not null,
        primary key(id)
);

insert into tbl_leaveword_valid_reason(id,siteid,content) values(1,40,'ȱ����ϵ��Ϣ');
insert into tbl_leaveword_valid_reason(id,siteid,content) values(2,40,'ѯ�����ⲻ����');

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


##��������ϵͳ
##ȫ����ң���
create table EN_COUNTRY
(
  id                int               not null,
  orderid           int,
  countryname       varchar(20)       not null,
  valid             int               default 0,                   ##1--ʹ��   0--ͣ��
  code              varchar(6)
);

##ʡ�м���λ��22��ʡ��5����������4��ֱϽ�У�2���ر���������
create table EN_PROVINCE
(
  id                int               not null,
  orderid           int,
  countryid         int               not null  default 86,
  provname          varchar(20)       not null,
  valid             int               default 0,                   ##1--ʹ��   0--ͣ��
  code              varchar(6)
);

##�ؼ�������λ��17��������30�������ݡ�283���ؼ��С�3���ˣ�
create table en_city
(
  id                int               not null,
  orderid           int,
  provid            int               not null,
  cityname          varchar(30)       not null,
  valid             int               default 0,                   ##1--ʹ��   0--ͣ��
  code              varchar(6)
);

##���ؼ�������λ��1464���ء�117�������ء�374���ؼ��С�852����Ͻ����49���졢3�������졢1��������2��������
create table en_zone
(
  id               int                 not null,
  orderid          int, 
  cityid           int                 not null,
  zonename         varchar(30)         not null,
  valid            int                 default 0,                   ##1--ʹ��   0--ͣ��
  code             varchar(6)
);

##����������λ��14677���硢1092�������硢19522����6152���ֵ����´���11����������181����ľ��1��������ľ��
create table en_town
(
  id               int                 not null,
  orderid          int,
  zoneid           int                 not null,
  townname         varchar(30)         not null,
  valid            int                 default 0,                   ##1--ʹ��   0--ͣ��
  code             varchar(6)
);

##�塢������������λ��80717����������ίԱ�ᡢ623669������ίԱ�ᣩ
create table en_village
(
  id               int                 not null,
  orderid          int,
  townid           int                 not null,
  villagename      varchar(30)         not null,
  valid            int                 default 0,                   ##1--ʹ��   0--ͣ��
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

CREATE TABLE tbl_orders(                                                    ##������
        id                       bigint   auto_increment     not null,      ##����
	SITEID                   int,				            ##վ��id
	orgid                    int,                                       ##������������
	companyid                int,                                       ##����������˾
	deptid                   int,                                       ##������������
	ORDERID                  bigint   not null unique,                  ##������
	initorderid              int      default 0,                        ##ԭʼ������(��=ԭ���ţ��ǲ�=0)
	USERID                   int,	     			            ##�û�id
	NAME                     varchar(50),		                    ##�ջ�������
	SEX                      int,				            ##�Ա�
	ADDRESS                  varchar(1000),	                            ##��ַ
	POSTCODE                 varchar(20),		                    ##�ʱ�
	PHONE                    varchar(50),		                    ##�绰
	TOTALFEE                 double(9,2),		                    ##�����ܶ�
	DELIVERYFEE              double(9,2),		                    ##�ʼķ���
	PAYFEE                   double(9,2),		                    ##Ӧ������
	VALID                    int      default 0      not null,		
	STATUS                   int                     not null,	    ##����״̬
	BILLINGWAY               int,				            ##֧����ʽ
	CITY                     varchar(50),			            ##����
	RECEIVE                  int,				
	NOUSE                    int      default 1      not null,	    ##�����Ƿ���Ч 1-��Ч 0-��Ч
	PROVINCE                 varchar(50),			            ##ʡ��
	zone                     varchar(50),			            ##����
	sendway                  int,				            ##���ͷ�ʽ
	commissioncharge         double(9,2),		                    ##�ۿ�
	insurance                double(9,2),		                    ##���շ�
	need_invoice             int        default 0     not null,	    ##�Ƿ���Ҫ��Ʊ  0����Ҫ  1��ֵ˰Ʊ  2��ͨƱ
	bill_to_address          varchar(2000),	                            ##��Ʊ��ַ
	notes                    varchar(4000),	                            ##��Ʊ��Ϣ
	flag                     smallint   default 0     not null,
	payflag                  smallint   default 0     not null,         ##�Ƿ��Ѿ�֧��   0-δ֧��  1-�Ѿ�֧��
	payway                   int,		                            ##���ʽ
	paydate                  timestamp,		                    ##����ʱ��
	linktime                 varchar(50),			
	usescores                int        default 0,	                    ##�û�ʹ�û���
	orderscores              int        default 0,                      ##���������Ļ��� 
	jylsh                    varchar(200),                              ##��֧�����ط��ص�֧�����ط��Ľ�����ˮ��
	usecard                  int        default 0,                      ##ʹ�ù���ȯ���   ������
	paycompno                int,                                       ##����֧�����ر��
	zfmemberid               varchar(200),                              ##֧�����Ļ�ԱID
	r2type                   varchar(10),                               ##֧�����ķ������ͣ�1������ض��� 2��������Ե�
	payresult                varchar(50),                               ##֧��������ɹ�/ʧ��/����
	lastupdate               timestamp,		                    ##�������ʱ��
	CREATEDATE               timestamp,		                    ##����ʱ��
	primary key(id) 
);

##������ϸ��Ϣ��
CREATE TABLE tbl_orders_detail( 
	 id                       bigint auto_increment        not null,    ##����
	 orderid                  bigint                       not null,    ##������
	 supplierid               int      default 0        not null,       ##������id(��Ӧÿ��������siteid)
	 suppliername             varchar(100),                             ##����������
	 productid                int,				            ##��Ʒid
	 productname              varchar(200),	                            ##��Ʒ����
	 productcode              varchar(200),                             ##��Ʒ����
	 ordernum                 int,				            ##��Ʒ����
         unit                     varchar(10),                              ##��Ʒ������λ
	 saleprice                double(9,2),		                    ##��Ʒ�ۼ�
	 weight                   int,				            ##��Ʒ����
	 type                     varchar(100),	                            ##��Ʒ���ͣ���չ���ԣ�
	 cardid                   int      default 0        not null, 	    ##ʹ�ù����id 
	 userinstarttime          timestamp,                                ##�û�����Ŀ�ʼ����
	 userinendtime            timestamp,                                ##�û�����Ľ�������
	 servicestarttime         timestamp,                                ##���ݹ��������ķ�����ɹ���ʼ����
	 serviceendtime           timestamp,                                ##���ݹ��������ķ�����ɹ���������
	 subscribe                smallint,                                 ##����������
         subscribenum             int,                                      ##���񶩹�������������1�����Ȼ���2������
	 createdate               timestamp,			            ##����ʱ��
	 CONSTRAINT fk_orderID FOREIGN KEY (ORDERID) REFERENCES  TBL_ORDERS(ORDERID) ON DELETE CASCADE,	 	 
	 primary key(id)
);

##֧����Ϣ��¼
create table tbl_payinfo(
	 ID                       bigint auto_increment        not null,    ##����
	 SITEID                   int,				            ##վ��id
	 ORDERID                  bigint                       not null,    ##������
	 cname                    varchar(200),				    ##�ͻ���ʽ����
	 notes                    varchar(2000),                            ##�ͻ���ʽ˵��
	 fee			  double(9,2) default 0,                    ##�˷�
	 createdate               timestamp,                                ##��������
	 primary key(id)
);


##�ͻ���ʽ����
create table tbl_fee(
	id                        int auto_increment not null,              ##����
	siteid                    int default 0,                            ##վ��id
	cname                     varchar(200),				    ##�ͻ���ʽ����
	notes                     varchar(2000),                            ##�ͻ���ʽ˵��
	fee			  double(9,2) default 0,                    ##�˷�
	createdate                timestamp,                                ##��������
	primary key(id)
);

##֧����ʽ����
create table tbl_sendway(
        id                        int auto_increment not null,              ##����
        siteid                    int                not null,              ##վ��id
        cname                     varchar(200),				    ##֧����ʽ����
	notes                     varchar(2000),                            ##֧����ʽ˵��
	createdate                timestamp,                                ##��������
	primary key(id)
);

##�ҵĳ����ͻ���ַ
create table tbl_addressinfo(
        id                        int auto_increment  not null,       ##����
        userid                    int                 not null,       ##�û�id
        name                      varchar(200),                       ##�ջ�������
        provinces                 varchar(50),			      ##ʡ��
        city                      varchar(50),			      ##����
        zone                      varchar(50),                        ##����
        mobile                    varchar(50),                        ##�ƶ��绰
        phone                     varchar(50),                        ##�̶��绰
        zip                       varchar(50),                        ##�ʱ�
        address                   varchar(200),                       ##��ϸ��ַ
        email                     varchar(200),                       ##�ջ����ʼ���ַ
        notes                     varchar(200),                       ##�ջ��˱�ע��Ϣ
	createdate                timestamp,                          ##����ʱ��
	primary key(id)
);

##�����ľ����ͻ���ַ
create table tbl_addressinfoForOrder(
        id                        int auto_increment  not null,       ##����
        ORDERID                   bigint              not null,       ##������  
	userid                    int                 not null,       ##�û�id
        name                      varchar(200),                       ##�ջ�������
        provinces                 varchar(50),			      ##ʡ��
        city                      varchar(50),			      ##����
        zone                      varchar(50),                        ##����
        mobile                    varchar(50),                        ##�ƶ��绰
        phone                     varchar(50),                        ##�̶��绰
        zip                       varchar(50),                        ##�ʱ�
        address                   varchar(200),                       ##��ϸ��ַ
	createdate                timestamp,                          ##����ʱ��
	primary key(id)
);

##����ȯ
CREATE TABLE tbl_shoppingcard(
	id                        int auto_increment  not null,       ##����
	siteid                    int                 not null,
	cardnum                   varchar(50),                        ##����ȯ��ʶ�� 
	denomination              int,                                ##����ȯ���
	begintime                 varchar(50),                        ##��ʼʱ��
	endtime                   varchar(50),                        ##����ʱ��
	code                      varchar(50),                        ##������
	createtime                timestamp,                          ##����ʱ��
	activation                int,                                ##�Ƿ�ʹ��0-δ��ʹ�ã�1-��ʹ��
	ischeck                   int                 not null,       ##�Ƿ񷢷�0-δ���ţ�1-�ѷ���
	primary key(id) 
);

##������������
create table tbl_mai_zeng_fangan(
        id               int auto_increment           not null,
        siteid           int                          not null,
        zengway          int                          not null,       ##��Ʒ�������
        price            double(9,2),                                 ##���۸�����һ�������ļ۸�ﵽ�涨����ֵ������Ʒ��һ�ַ�����
        pcount           int,                                         ##����������ĳ����Ʒ���򵽹涨������������Ʒ    ���ڶ��ַ�����
        productid        int,                                         ##����������Ҫ�������Ʒ
        productbrand     varchar(50),                                 ##����ĳ��Ʒ�Ƶ���Ʒ������Ʒ                    �������ַ����� 
        useflag          smallint,                                    ##�÷����Ƿ�ʹ��     0##��ʹ��   1##ʹ��
        majorflag        smallint,                                    ##��Ҫ������������������ͻ�����ʹ�ø÷�����1##������������ʹ�÷�����ֻ����һ��������
        createdate       timestamp,                                   ##������������
        editor           varchar(50),                                 ##����������
        primary key(id)
);


##��Ʒ����
create table tbl_zengpin(
        productid        int                          not null,       ##��ƷID
        productname      varchar(250)                 not null,       ##��Ʒ����
        fanganid         int                          not null,       ##���÷���ID
        createdate       timestamp,                                   ##��������
        editor           varchar(50),                                 ##������
        primary key(productid)
);

##��������
create table tbl_groupproductsale(
        groupid          int                          not null,       ##��Ʒ���ID
        productid        int                          not null,       ##��ƷID
        productname      varchar(250)                 not null,       ##��Ʒ����
        summary          varchar(500),                                ##��ϲ�Ʒ����
        productcount     smallint                     not null,       ##��Ʒ����
        YhPrice          double(9,2),                                 ##�Żݼ۸�
        groupbigpic      varchar(250),                                ##��ϴ�ͼ
        groupsmallpic    varchar(250),                                ##���Сͼ
        grouptexuao      varchar(250),                                ##�����Чͼ
        groupcontentpic  varchar(250),                                ##�������ͼ
        begintime        timestamp,                                   ##��ʼ����
        endtime          timestamp,                                   ##��������
        createdate       timestamp,                                   ##��������
        editor           varchar(50),                                 ##������
        primary key(productid)
);

##֧���ӿڶ����
create table tbl_paywayinterface(
       id                int                           not null,
       paywayid          int                           not null,
       accountnumber     varchar(200),
       paywaykey         varchar(200),
       alipayaccount     varchar(100),
       createdate        timestamp                     not null,
       primary key(id) 
); 

##�ҵĳ��÷�Ʊ��Ϣ��
create table tbl_invoiceinfo
(
       id                 int           auto_increment,
       siteid             int           default 0,           ##վ��id
       userid             int           default 0,           ##�û�id
       invoicetype        int           default 0,           ##��Ʊ����(0-��ͨ��Ʊ1-��ֵ˰��Ʊ)
       title              int           default 0,           ##��Ʊ̧ͷ������/��λ0-����1-��λ��
       companyname        varchar(200),                      ##��λ����
       content            int           default 0,           ##��Ʊ����(�������id)
       identification     varchar(200),                      ##��˰��ʶ���
       registeraddress    varchar(200),                      ##ע���ַ
       phone              varchar(50),                       ##ע��绰
       bankname           varchar(200),                      ##��������
       bankaccount        varchar(100),                      ##�����ʺ�
       createdate         timestamp,                         ##����ʱ��
       primary key(id)
);

##��Ʊ����ά����
create table tbl_invoicecontent
(
       id                 int  auto_increment not null, 
       siteid             int                 default 0,     ##վ��id
       invoiceid          int                 not null,      ##��ƱID
       guige              varchar(100),                      ##���
       unit               varchar(100),                      ##��λ                      
       num                int,                               ##��������
       danprice           float(10,2),                       ##����
       content            varchar(200)        not null,      ##����
       createdate         timestamp,                         ##����ʱ��
       primary key(id)
);

##���嶩���ķ�Ʊ��Ϣ��
create table tbl_invoiceinfoForOrder
(
       id                 int           auto_increment,
       siteid             int           default 0,           ##վ��id
       orderid            bigint        not null,            ##����ID
       userid             int           default 0,           ##�û�id
       orderid            int           default 0,           ##�û�������
       invoicetype        int           default 0,           ##��Ʊ����(0-��ͨ��Ʊ1-��ֵ˰��Ʊ)
       title              int           default 0,           ##��Ʊ̧ͷ������/��λ0-����1-��λ��
       companyname        varchar(200),                      ##��λ����
       content            int           default 0,           ##��Ʊ����(�������id)
       identification     varchar(200),                      ##��˰��ʶ���
       registeraddress    varchar(200),                      ##ע���ַ
       phone              varchar(50),                       ##ע��绰
       bankname           varchar(200),                      ##��������
       bankaccount        varchar(100),                      ##�����ʺ�
       createdate         timestamp,                         ##����ʱ��
       primary key(id)
);

##���嶩���ķ�Ʊ����ά����
create table tbl_invoicecontentForOrder
(
       id                 int  auto_increment not null, 
       siteid             int                 default 0,     ##վ��id
       orderid            bigint        not null,            ##����ID
       invoiceid          int                 not null,      ##��ƱID
       guige              varchar(100),                      ##���
       unit               varchar(100),                      ##��λ                      
       num                int,                               ##��������
       danprice           float(10,2),                       ##����
       content            varchar(200)        not null,      ##����
       createdate         timestamp,                         ##����ʱ��
       primary key(id)
);


##�ͻ����ù���



##�ʰɱ�ṹ����
create table fawu_anwser                                ##�𰸱�      
(
        id                      int   auto_increment    not null,            ##������
        qid                     int                     not null,            ##������������������
        anwser                  varchar(4000),                               ##��
        votenum                 int,                                         ##ͶƱ����
        ipaddress               varchar(15),                                 ##�ش��ߵ�IP��ַ
        userid                  int,                                         ##�ش����û�ID
        username                varchar(300),                                ##�ش����û���
        fenshu                  int                     default 0,           ##��ȡ����
        cankaoziliao            varchar(4000),                               ##�ο�����
        createdate              timestamp,                                   ##����ʱ��
        PICPATH                 varchar(255),                                ##ͼƬ·��
        BETTERANS               int,                                         ##�Ƿ�����Ѵ�
        ANW_STATUS              int,                                         ##��״̬
        primary key(id)
);

create table fawu_wenti_column                          ##�ʰɷ��ඨ��
(
        id                      int   auto_increment    not null,            ##�ʰɷ�������
        siteid                  int                     not null,            ##վ��ID
        parentid                int                     not null,            ##������ID
        orderid                 int                     not null,            ##���ID
        cname                   varchar(100),                                ##��������
        ename                   varchar(100),                                ##Ӣ������
        dirname                 varchar(200),                               
        status                  int                     default 0,
        creater                 varchar(20),
        createdate              timestamp,
        primary key(id)
);

create table fawu_vote                                 ##�ʰɴ�ͶƱ��
(
        id                      int  auto_increment    not null,             ##����ID   
        aid                     int                    not null,             ##�����ID
        userid                  int                    not null,             ##ͶƱ�û�ID
        primary key(id)
);

create table fawu_wenti                                 ##�����
(
        id                      int auto_increment      not null,           ##����ID
        columnid                int                     not null,           ##����ID
        parcolumid              int,                                        ##��һ������ID
        cname                   varchar(100),                               ##������������
        question                varchar(1000),                              ##��������
        status                  int,                                        ##����״̬
        voteflag                int,                                        ##�Ƿ��Ǵ�ͶƱ������
        xuanshang               int                     default 0,          ##���ͷ���
        answernum               int                     default 0,          ##�ش���Ŀ
        source                  varchar(50),                                ##
        createdate              timestamp,                                  ##��������
        ipaddress               varchar(15),                                ##�ͻ���IP��ַ
        creater                 varchar(50),                                ##������
        province                varchar(50),                                ##������ʡ��
        city                    varchar(50),                                ##���������ڳ���
        area                    varchar(50),                                ##��������������
        picpath                 varchar(255),                               ##ͼƬ·��
        emailnotify             int                     default 0,          ##�Ƿ��ʼ�֪ͨ  1--֪ͨ  0--��֪ͨ,
        title                   varchar(60),                                ##
        email                   varchar(40),                                ##�û������ʼ�
        userid                  int,                                        ##�û�ID
        username                varchar(20),                                ##�û�����
        filepath                varchar(255),                               ##�ϴ��ļ�·��
        istop                   int                     default 0,          ##�Ƿ��ö�
        anw_status              int,                                        ##��״̬
        dianjinum               int,                                        ##�����
        user_id_huida           varchar(50),                                ##�ش���ID
        primary key(id)
);


##�����������������ϵͳ
##���б�ű�
create table tbl_banks (
        id                      int   auto_increment    not null,      ##����
        bankid                  varchar(12)             not null,      ##����ID
        siteid                  int                     not null,      ##վ��ID
        bankattribname          varchar(12)             not null,      ##���м��
        bankname                varchar(40),                           ##��������
        createdate              timestamp,                             ##����ʱ��
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##ҵ��Ա���ݱ�
create table tbl_salesman (
        id                      int auto_increment      not null,      ##����
        salesmanid              varchar(12)             not null,      ##ҵ��ԱID
        siteid                  int                     not null,      ##վ��ID
        chinesename             varchar(12)             not null,      ##ҵ��Ա����
        englishname             varchar(12),                           ##ҵ��ԱӢ������
        contactphone            varchar(20),                           ##ҵ��Ա��ϵ�绰
        mobilephone             varchar(12),                           ##ҵ��Ա�ƶ��绰
        contactaddress          varchar(120),                          ##ҵ��Ա��ϵ��ַ
        email                   varchar(50),                           ##ҵ��Ա�����ʼ���ַ
        createdate              timestamp,                             ##����ʱ��
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##�ͻ���
create table tbl_customer (
        id                      int  auto_increment     not null,      ##����
        customerid              varchar(12)             not null,      ##�ͻ�ID
        siteid                  int                     not null,      ##վ��ID
        customerattribname      varchar(50)             not null,      ##�ͻ����
        customername            varchar(100),                          ##�ͻ�����
        invoiceno               varchar(20),                           ##��Ʊ���
        owner                   varchar(12),                           ##������
        rocid                   varchar(18),                           ##���֤����
        contactman1             varchar(18),                           ##��ϵ��1
        contactman2             varchar(18),                           ##��ϵ��2
        contactphone1           varchar(20),                           ##��ϵ��1�绰
        contactphone2           varchar(20),                           ##��ϵ��2�绰
        fax                     varchar(20),                           ##�������
        salesmanid              int,                                   ##ҵ������
        customeraddress         varchar(120),                          ##��˾��ַ
        deliveryaddress         varchar(120),                          ##�ͻ���ַ
        invoiceaddress          varchar(120),                          ##��Ʊ��ַ
        paydays                 int,                                   ##��������
        creditline              double(32,2),                          ##���ö��
        creditbalance           double(32,2),                          ##�������
        lastdeliverydate        timestamp,                             ##�������
        createdate              timestamp,                             ##����ʱ��
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##�����̱�
create table tbl_supplier (
        id                      int auto_increment      not null,      ##����
        supplierid              varchar(12)             not null,      ##������ID
        siteid                  int                     not null,      ##վ��ID
        supplierattribname      varchar(50)             not null,      ##�����̼��
        suppliername            varchar(100),                          ##����������
        invoiceno               varchar(20),                           ##��Ʊ���
        owner                   varchar(12),                           ##������
        rocid                   varchar(18),                           ##���֤����
        phone1                  varchar(18),                           ##��ϵ�绰1
        phone2                  varchar(18),                           ##��ϵ�绰2
        fax                     varchar(20),                           ##�������
        contactname1            varchar(20),                           ##��ϵ��1
        contactname2            varchar(20),                           ##��ϵ��2
        companyaddress          varchar(120),                          ##��˾��ַ
        deliveryaddress         varchar(120),                          ##������ַ
        invoiceaddress          varchar(120),                          ##��Ʊ��ַ
        lastpurchasedate        timestamp,                             ##�������
        paydays                 int      default 0,                    ##��������
        prepaid                 int      default 0,                    ##�ݸ���
        createdate              timestamp,                             ##����ʱ��
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##��Ʒ��
create table tbl_jxc_product (
        id                      int  auto_increment     not null,      ##����
        productid               varchar(12)             not null,      ##��ƷID
        siteid                  int                     not null,      ##վ��ID
        productname             varchar(120)            not null,      ##��Ʒ����
        safestock               int,                                   ##��ȫ����
        lastpurchasedate        timestamp,                             ##�����������
        lastdeliverydate        timestamp,                             ##�����������
        quantity                int         default 0,                 ##�����
        createdate              timestamp,                             ##����ʱ��
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##����������
create table tbl_purchasemaster (
        id                      int  auto_increment   not null,        ##����
        purchaseid              varchar(12)           not null,        ##��������
        siteid                  int                   not null,        ##վ��ID
        purchasedate            timestamp             not null,        ##��������
        supplierid              varchar(12)           not null,        ##�����̱��
        purchaseproperty        smallint              default 0,       ##���������� 0-����  1-�˻�
        invoiceno               varchar(10),                           ##��Ʊ���
        subtotal                double(36,2)          default 0,       ##�ϼƽ��
        valueaddtax             double(36,2)          default 0,       ##Ӫҵ˰
        amount                  double(36,2)          default 0,       ##�ܼƽ��
        accountpayable          double(36,2)          default 0,       ##Ӧ�����
        paid                    double(36,2)          default 0,       ##�Ѹ��ʿ�
        limitdate               timestamp,                             ##�����ֹ����
        createdate              timestamp,                             ##����ʱ��
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##��������ϸ��
create table tbl_purchasedetail (
        id                      int  auto_increment    not null,       ##����
        purchaseid              varchar(12)            not null,       ##��������
        productid               varchar(12)            not null,       ##��ƷID
        purchasequantity        int                  default 0,        ##��������
        purchaseunitprice       double(18,2),                          ##��������
        purchaseamount          double(36,2)         default 0,        ##�����ܼ�
        createdate              timestamp,                             ##����ʱ��
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##����������
create table tbl_deliverymaster (
        id                      int  auto_increment     not null,      ##����
        deliveryid              varchar(12)             not null,      ##��������
        siteid                  int                     not null,      ##վ��ID
        deliverydate            timestamp               not null,      ##��������
        customerid              varchar(12)             not null,      ##�ͻ����
        salesmanid              varchar(12)             not null,      ##ҵ��Ա���
        deliveryaddress         varchar(120),                          ##�ͻ���ַ
        invoiceno               varchar(10),                           ##��Ʊ���
        customerorderno         varchar(15),                           ##��������
        subtotal                double(36,2)            default 0,     ##�ϼƽ��
        valueaddtax             double(36,2)            default 0,     ##Ӫҵ˰
        amount                  double(36,2)            default 0,     ##�ܼƽ��
        accountreceivable       double(36,2)            default 0,     ##Ӧ�ս��
        received                double(36,2)            default 0,     ##���ս��
        limitdate               timestamp,                             ##�����ֹ����
        createdate              timestamp,                             ##����ʱ��
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##��������ϸ��
create table tbl_deliverydetail (
        id                      int auto_increment      not null,      ##����
        deliveryid              varchar(12)             not null,      ##��������
        productid               varchar(12)             not null,      ##��ƷID
        salesquantity           int                     default 0,     ##��������
        salesunitprice          double(18,2),                          ##��������
        salesamount             double(36,2)            default 0,     ##�����ܼ�
        createdate              timestamp,                             ##����ʱ��
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);


##���䶯����
create table tbl_changemaster (
        id                      int auto_increment      not null,      ##����
        changeid                varchar(12)             not null,      ##��������
        siteid                  int                     not null,      ##վ��ID
        changedate              timestamp,                             ##�䶯����
        description             varchar(255),                          ##�䶯˵��
        createdate              timestamp,                             ##����ʱ��        
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##���䶯��ϸ
create table tbl_changedetail (
        id                      int  auto_increment     not null,      ##����
        changeid                varchar(12)             not null,      ##��������
        productid               varchar(12)             not null,      ##��ƷID
        changequantity          int                     default 0,     ##�䶯����
        changeamount            double(36,2)            default 0,     ##�䶯���
        createdate              timestamp,                             ##����ʱ��        
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##�����������
create table tbl_accountpayablemaster (
        id                      int  auto_increment    not null,      ##����
        paymentid               varchar(12)            not null,      ##��������
        siteid                  int                    not null,      ##վ��ID
        paydate                 timestamp              not null,      ##֧������
        supplierid              varchar(12)            not null,      ##��Ӧ��ID
        paycash                 double(36,2)           default 0,     ##�ֽ�
        paycheck                double(36,2)           default 0,     ##֧Ʊ
        noteno                  varchar(10),                          ##֧Ʊ����
        duedate                 timestamp,                            ##������
        bankid                  varchar(12)            not null,      ##��Ʊ����
        accountno               varchar(20),                          ##�ʺ�
        discount                double(36,2)           default 0,     ##�ۿ�
        remittance              double(36,2)           default 0,     ##����
        prepaid                 double(36,2)           default 0,     ##�ݸ���
        others                  double(36,2)           default 0,     ##����
        payamount               double(36,2)           default 0,     ##������
        totalbalance            double(36,2)           default 0,     ##ʵ����
        createdate              timestamp,                            ##����ʱ��        
        updatedate              timestamp,                            ##�޸�ʱ��
        primary key (id)
);

##���������ϸ��
create table tbl_accountpayabledetail (
        id                      int  auto_increment     not null,      ##����
        paymentid               varchar(12)             not null,      ##�����
        purchaseid              varchar(12)             not null,      ##����������
        balance                 double(36,2)            default 0,     ##���γ������
        createdate              timestamp,                             ##����ʱ��        
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##�տ��������
create table tbl_accountreceivablemaster (
        id                      int  auto_increment     not null,     ##����
        siteid                  int                     not null,     ##վ��ID
        receiveid               varchar(12)             not null,     ##�տ��
        receivedate             timestamp,                            ##�տ�����
        customerid              varchar(12)             not null,     ##�ͻ����
        receivecash             double(36,2)            default 0,    ##�ֽ�
        receivecheck            double(36,2)            default 0,    ##֧Ʊ
        noteno                  varchar(10),                          ##֧Ʊ����
        duedate                 timestamp,                            ##������
        bankid                  varchar(12)             not null,     ##��Ʊ����
        accountno               varchar(20),                          ##�ʺ�
        discount                double(36,2)            default 0,    ##�ۿ�
        remittance              double(36,2)            default 0,    ##����
        advance                 double(36,2)            default 0,    ##���տ�
        others                  double(36,2)            default 0,    ##����
        receiveamount           double(36,2)            default 0,    ##�տ���
        totalbalance            double(36,2)            default 0,    ##ʵ����
        createdate              timestamp,                            ##����ʱ��        
        updatedate              timestamp,                            ##�޸�ʱ��
        primary key (id)
);


##�տ������ϸ��
create table tbl_accountreceivabledetail (
        id                      int  auto_increment     not null,      ##����
        receiveid               varchar(12)             not null,      ##�տ��
        deliveryid              varchar(12)             not null,      ##����������
        balance                 double(36,2)            default 0,     ##���γ������
        createdate              timestamp,                             ##����ʱ��        
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##�û���
create table tbl_users (
        id                      int  auto_increment     not null,      ##����
        userid                  varchar(12)             not null,      ##�û�ID
        username                varchar(50)             not null,      ##�û�����
        passwordcod             varchar(36)             not null,      ##�û�����
        createdate              timestamp,                             ##����ʱ��        
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##�����
create table tbl_programs (
        id                      int  auto_increment        not null,   ##����
        programid               varchar(12)                not null,   ##����ID
        programname             varchar(50)                not null,   ##��������
        programaddress          varchar(250)               not null,   ##����URI
        createdate              timestamp,                             ##����ʱ��        
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);

##��Ȩ��
create table tbl_userauthority (
        id                      int  auto_increment     not null,      ##����
        userid                  varchar(12)             not null,      ##�û�ID
        programid               varchar(50)             not null,      ##����id
        running                 smallint,                              ##����ִ��Ȩ��
        createdate              timestamp,                             ##����ʱ��        
        updatedate              timestamp,                             ##�޸�ʱ��
        primary key (id)
);


##���������ߵ�����ϵͳ
create table tbl_rsbt_org(
	id			int        auto_increment       not null,
	guid			varchar(36)			not null,
	userid			varchar(36)			not null,
	siteid			int				not null,
	password		varchar(36)			not null,
	org_gode		varchar(9) default 999999999	not null,		##��֯��������
	org_name		varchar(80)			not null,		##��֯��������
	org_area_code		varchar(8)			not null,		##��������
	org_sys_code		varchar(8)			not null,		##ϵͳ/��ҵ����
	org_type		varchar(3)			not null,		##��λ����
	org_link_person		varchar(40),						##��λ��ϵ��
	org_person_id		varchar(40),						##��ϵ�����֤����
	org_sup_code		varchar(9),						##�ϼ���֯����
	org_addr		varchar(80),						##��֯������ַ
	org_post		varchar(6),						##��֯�����ʱ�
	org_phone		varchar(40),						##��ϵ�绰
	org_mob_phone		varchar(40),						##�ֻ�����
	org_fax			varchar(40),						##��֯��������
	org_bank		varchar(80),						##��������
	org_account_name	varchar(80),						##�˻�����
	org_account		varchar(40),						##�����˺�
	org_hostility		smallint,						##��̨��λ����
	org_web_site		varchar(80),						##��ַ
	org_mail		varchar(80),						##��������
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


##�������ߵ�̨�걨��
create table tbl_zhuduijiangji(
	djjid			int   auto_increment    not null,		##����
	shetaidanwei		varchar(50),				        ##��̨��λ
	suoshuxitong		varchar(50),					##����ϵͳ
	address			varchar(100),					##��ַ
	lianxiren		varchar(20),					##��ϵ��
	phone			varchar(20),					##�绰
	chuanzhen		varchar(20),					##����
	shiyongdiqu		varchar(50),					##ʹ�õ���
	diantaishuliang		varchar(20),					##��̨����
	diantaileibie		varchar(50),					##��̨���	150MHz,400MHz,900MHz�����ĵ�ַ
	shetailiyou		varchar(500),					##��̨����
	lingdaoyijian		varchar(500),					##�ϼ����ܵ�λ�쵼���
	guanlijuyijian		varchar(500),					##���������ߵ��������
	xitongbianhao		varchar(100),					##ϵͳ���
	zhizhaobianhao		varchar(100),					##ִ�ձ��
	primary key(djjid)
);

create table tbl_diantaiziliao(
	id			int   auto_increment    not null,		##����
	dtzlid			int			not null,
	shebeixinghao		varchar(100),					##�豸�ͺ�
	chuchanghaoma		varchar(100),					##��������
	beizhu			varchar(100),					##��ע
	CONSTRAINT fk_dtzlID FOREIGN KEY (dtzlid) REFERENCES  tbl_zhuduijiangji(djjid) ON DELETE CASCADE,
	primary key(id)
);

##��Ϣ�ɼ���
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


##����Ϊ��������
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

##��������
CREATE TABLE TBL_COMMENT ( 
	id             int   auto_increment     not null,                ##����
	siteid         int                      not null,                ##վ��id
	userid         int                      not null,                ##�û�id
	Username       varchar(30)              not null,                ##�û���¼����
	pid            int                      not null,                ##��Ʒid
	commenttitle   varchar(200)             not null,                ##����
	Scores         int                      default 0,               ##����
	experience     varchar(500),                                     ##ʹ���ĵ�
	Advantages     varchar(500),                                     ##�ŵ�
	Shortcomings   varchar(500),                                     ##����
	usefulnum      int                      default 0,               ##��Ϊ���������õ�����
	nousenum       int                      default 0,               ##��Ϊ���������õ�����
	auditflag      int                      default 0,               ##0 ���ͨ��������ʾ 1-���δͨ�� ����ʾ 		
	createdate     timestamp                default current_date(),  ##����ʱ��
	CONSTRAINT pk_commentid PRIMARY KEY (ID)	
);

##�������ۻظ�
create table Tbl_commentanswer
(
        id              int   auto_increment     not null,               ##����
        cid             int                      not null,               ##��Ʒ����id���
        username        varchar(100)             not null,               ##�ظ��˵�¼��
        answercontent   varchar(500)             not null,               ##�ظ�����
        createdate      timestamp                default current_date(),      ##�ظ�ʱ��
	CONSTRAINT fk_commentID FOREIGN KEY (cid) REFERENCES  tbl_comment(ID) ON DELETE CASCADE,
        primary key(id)
);

##��Ʊ��Ϣ��  ������
create table en_invoice
(
        id               int   auto_increment     not null,                  ##����
        siteid           int                      not null,                  ##վ��id
        userid           int                      not null,                  ##�û�id
        orderid          bigint                   not null,                  ##�û�������
        invoicetype      int                      default 0,                 ##��Ʊ����(0-��ͨ��Ʊ1-��ֵ˰��Ʊ)
        titleflag        int                      default 0,                 ##������/��λ0-����1-��λ��
        title            varchar(100),                                       ##��Ʊ̧ͷ
        companyname      varchar(200),                                       ##��λ����
        identification   varchar(200),                                       ##��˰��ʶ���
        registeraddress  varchar(200),                                       ##ע���ַ
        phone            varchar(15),                                        ##ע��绰
        postaddress      varchar(200),                                       ##�ʼĵ�ַ
        postname         varchar(20),                                        ##����������
        postcode         varchar(6),                                         ##��������
        bankname         varchar(200),                                       ##��������
        bankaccount      varchar(100),                                       ##�����ʺ�
        totalprice       money,                                              ##��Ʊ�ܼ�ֵ
        createdate       timestamp                default current_date(),    ##����ʱ��
        primary key(id)
);

##��Ʊ����ά����
create table en_invoicecontent
(
        id              int   auto_increment     not null,                   ##����,ÿ����Ʊ���ID
        invoiceid       int                      not null,                   ##��ƱID
        siteid          int                      not null,                   ##վ��id
        [content]       varchar(200)             not null,                   ##����
        price           money,                                               ##��Ʒ����
        pnum            int                      default 0,                  ##��Ʒ����
        createdate      timestamp                default current_date(),     ##����ʱ��
	CONSTRAINT      fk_contentid FOREIGN KEY (invoiceid) REFERENCES  en_invoice(id) ON DELETE CASCADE,
        primary key(id)
);

##��Ʒ�������
create table tbl_wzclass
(
       id                int    auto_increment not null,                  ##����ID
       customerid        int                   not null,                  ##���ϱ����ӵ����     0-��ʾϵͳ��������ʷ���
       name              varchar(200)         not null,                   ##������߷�������
       brief             varchar(500),                                    ##���ʷ�������
       unit              varchar(10),                                     ##�������ʵļ�����λ
       keyword           varchar(20),                                     ##�������ʵĹؼ���
       code              varchar(100)         not null,                   ##����
       pcode             varchar(100)         not null,                   ##������
       orderid           int,                                             ##����
       valid             int                default 0,                    ##���ʱ����״̬     1--ʹ��   0--ͣ��
       delflag           int                default 0,                    ##ɾ����ʶ��         0--ʹ��   1--ɾ��
       creator           varchar(50),                                     ##������
       createdate        timestamp          default current_date(),       ##����ʱ��
       createcomp        int,                                             ##���������ڵĹ�˾
       editor            varchar(50),                                     ##�޸���
       lastupdate        timestamp,                                       ##����޸�ʱ��
       updatecomp        int,                                             ##����޸������ڹ�˾ 
       primary  key(id)
);

##���ʷ���ģ����Ϣ
create table tbl_wzclass_template
(
       id                int    auto_increment not null,                  ##����ID
       classid           int                not null,                     ##���ʷ�������ID
       status            int                default 0,                    ##�Ƿ�ͣ��    0--ʹ��   1--ͣ��
       delflag           int                default 0,                    ##�Ƿ�ɾ��    0--ʹ��   1--ɾ��
       templatexml       text,                                            ##ģ�嶨���XML�ַ���
       customerid        int                not null,                     ##���ϱ����ӵ����
       creator           varchar(50),                                     ##������
       createdate        timestamp,                                       ##����ʱ��
       createcomp        int,                                             ##���������ڵĹ�˾
       editor            varchar(50),                                     ##�޸���
       lastupdate        timestamp,                                       ##����޸�ʱ��
       updatecomp        int,                                             ##����޸������ڹ�˾ 
       primary key(id)
);

##������ϸ��Ϣ
create table tbl_wzdetail
(
       id                int    auto_increment not null,                  ##����ID
       classid           int                   not null,                  ##���ʷ�������ID
       wzname            varchar(200)         not null,                   ##��������
       wzcode            varchar(21)          not null,                   ##���ʱ�����ϸ
       unit              varchar(10),                                     ##���ʵ�λ
       customerid        int                not null,                     ##���ϱ����ӵ����
       status            int                default 0,                    ##�����ϼ�����
       delflag           int                default 0,                    ##�����Ƿ�ɾ��   0--����  1--ɾ��
       creator           varchar(50),                                     ##������
       createdate        timestamp,                                       ##����ʱ��
       createcomp        int,                                             ##���������ڵĹ�˾
       editor            varchar(50),                                     ##�޸���
       lastupdate        timestamp,                                       ##����޸�ʱ��
       updatecomp        int,                                             ##����޸������ڹ�˾ 
       primary key(id)
);

##������ϸ��Ϣ���Ա�
create table tbl_wzdetail_part
(
       id                int    auto_increment not null,                  ##����ID
       classid           int                not null,                     ##���ʷ�������ID
       proptype          int                default 0,                    ##����ֵ      1-����  2-
       ename             varchar(200)         not null,                   ##����������Ϣ��Ӣ������
       type              int                not null,                     ##�������Ե���������
       textvalue         text,                                            ##�洢���ı�����ֵ
       stringvalue       varchar(4000),                                   ##�洢�ַ�������ֵ       
       minintvalue       int,                                             ##�洢������������Сֵ�������Ǵ洢��������ֵ
       maxintvalue       int,                                             ##�洢�������������ֵ
       minfloatvalue     int,                                             ##�洢������������Сֵ�������Ǵ洢����������ֵ
       maxfloatvalue     int,                                             ##�洢�������������ֵ
       creator           varchar(50),                                     ##������
       createdate        timestamp,                                       ##����ʱ��
       createcomp        int,                                             ##���������ڵĹ�˾
       editor            varchar(50),                                     ##�޸���
       lastupdate        timestamp,                                       ##����޸�ʱ��
       updatecomp        int,                                             ##����޸������ڹ�˾ 
       primary key(id)
);


##���ʷ����������Ϣ
create table tbl_wzservicearea
(
       id                int    auto_increment not null,                  ##����ID
       classid           int                not null,                     ##���ʷ�������ID
       wzid              int                not null,                     ##���ʱ���
       areaid            int                not null,                     ##���ʷ�����������
       status            int                default 0,                    ##�����ϼ�����
       customerid        int                not null,                     ##���ϱ����ӵ����
       delflag           int                default 0,                    ##�����Ƿ�ɾ��   0--����  1--ɾ��
       creator           varchar(50),                                     ##������
       createdate        timestamp,                                       ##����ʱ��
       createcomp        int,                                             ##���������ڵĹ�˾
       editor            varchar(50),                                     ##�޸���
       lastupdate        timestamp,                                       ##����޸�ʱ��
       updatecomp        int,                                             ##����޸������ڹ�˾ 
       primary key(id)
);

##����ͼƬ��
create table tbl_wzpictures
(
       id                int    auto_increment not null,                  ##����ID
       classid           int                not null,                     ##���ʷ�������ID
       wzid              int                not null,                     ##���ʱ���
       picurl            varchar(500)         not null,                   ##����ͼƬURL
       filename          varchar(100),                                    ##����ͼƬ�ļ����ƣ�ֻ����jpg,jpeg,png��ʽͼƬ
       sfilename         varchar(100),                                    ##����ͼƬ�ϴ�ʱ��ԭʼ����
       customerid        int                not null,                     ##���ϱ����ӵ����
       delflag           int                default 0,                    ##����ͼƬ�Ƿ�ɾ��   0--����  1--ɾ��
       creator           varchar(50),                                     ##������
       createdate        timestamp,                                       ##����ʱ��
       createcomp        int,                                             ##���������ڵĹ�˾
       editor            varchar(50),                                     ##�޸���
       lastupdate        timestamp,                                       ##����޸�ʱ��
       updatecomp        int,                                             ##����޸������ڹ�˾ 
       primary key(id)
);

##���ʸ�����
create table tbl_wzattachements
(
       id                int    auto_increment not null,                  ##����ID
       classid           int                not null,                     ##���ʷ�������ID
       wzid              int                not null,                     ##���ʱ���
       attachementurl    varchar(500)         not null,                   ##���ʸ���URL
       filename          varchar(100),                                    ##���ʸ����ļ����ƣ�ֻ����PDF��DOC��DOCX��PPT��PPTX��ʽ�ļ�
       sfilename         varchar(100),                                    ##���ʸ����ϴ�ʱ��ԭʼ����
       customerid        int                not null,                     ##���ϱ����ӵ����
       delflag           int                default 0,                    ##���ʸ����Ƿ�ɾ��   0--����  1--ɾ��
       creator           varchar(50),                                     ##������
       createdate        timestamp,                                       ##����ʱ��
       createcomp        int,                                             ##���������ڵĹ�˾
       editor            varchar(50),                                     ##�޸���
       lastupdate        timestamp,                                       ##����޸�ʱ��
       updatecomp        int,                                             ##����޸������ڹ�˾ 
       primary key(id)
);
