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
	id		varchar(500)		not null,         --����ID
	lyhid		int,                                   --��¼�û�ID
	username	varchar(500),                             --��¼�û���
	logintime	datetime,                                     --����¼ʱ��
	loginnum        int,                                   --��¼����
	loginstatus     int,                                --��¼״̬
	lastactivetime  datetime,                                     --�û����ʱ��
	ipaddress	varchar(500)                              --��¼�ߵ�IP��ַ
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
	id                       int      identity(1, 1)     not null,                     --��ʶ��,ũ��Ժ����ID
	siteid                   int               not null,	                 --ũ��Ժע���վ��ID
	room_name	         varchar(100)     not null,                     --��������ƻ��߱��
	catlog_code              char(8)           default '00000000',           --������룬��ʱ����
	room_type 	         tinyint 	   default 2,                    --1--���˷� 2--��׼˫�˷�  3--���˷�   4--���˷�  5--���˷�  6--�𿻷�
        room_num                 int               default 1,                    --�����ͷ�������
	room_way                 tinyint,                                       --���䳯��1-����2-����3-�ϡ�4-����5-���ϡ�6-������7-���ϡ�8-����
	room_size                int,                                            --�������   
	init_price               decimal(10,2),                                   --��ʼ�۸�
        bed_num                  int               default 1,                    --�����ڴ�λ����
        bed_type                 int,                                            --���ͣ�1--���˴���1.2�׿�2--���˴���0.9�׿�3--���˴���1.05�׿� 4--˫�˴���1.35�׿� 5--˫�˴���1.5�׿� 6--˫�˴���1.8�׿�7--�𿻣�5�׿�
        floor                    varchar(20),                                    --¥�㣬1�㡢2�㡢2�㣨�е��ݣ���3�㡢3�㣨�е��ݣ���4�㡢4�㣨�е��ݣ���5�㡢5�㣨�е��ݣ�6�㡢6�㣨�е��ݣ�
	toilet                   tinyint          default 1,                    --����������
        tv                       varchar(100),                                  --������ʩ����
        airconditioner           varchar(100),                                  --�յ���ʩ����
	bathroom                 varchar(100),                                  --ϴԡ��ʩ����
	bedclothes               varchar(200),                                  --������Ʒ����
        amenities                varchar(500),                                  --������ʩ�����ӻ���������ԡ���������硢����Ĵ桢��Ȫˮ������䡢24Сʱ��ˮ���������硢���ͣ��λ���յ�����ԡ����ͨ�������ɽӴ���ͥ/���ӡ���ˮ������ˮԡ�ס���ѽ��͡��ʺϾٰ����������ë����ѽ�̤������Ь��������Ʊ�������һ������ϴ��Ʒ���������������Ȱ�
	specialservice           varchar(50),                                   --��ͷ��񡢽��ͷ���
	delflag                  tinyint         default 0,                     --��Ϣ��ɾ����ʾ��0--ʹ��  1--ɾ��
	primary key (id)
);


create table tbl_code (
       id                        int      identity(1, 1)      not null,        --��ʶ��
       typecode     		 varchar(30),		                       --��һ������
       typename		         varchar(30),                                  --��һ����������
       detailcode                varchar(30),                                  --�ڶ�������
       detailname                varchar(30),                                  --�ڶ�����������
       validity                  tinyint,                                      --�����Ƿ���Ч
       attribute                 varchar(50),                                  --��������ֵ
       standardword              varchar(50),                                  --��׼�ؼ���
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
insert into tbl_right values(60,'��ҵע���û�����','ERU','��ҵע���û�����');
insert into tbl_right values(61,'�����չ���','WD','�����չ���');
insert into tbl_right values(62,'ҵ���������','YWR','ҵ���������');

create table tbl_role (
	roleid			int  identity(1, 1)     not null,
	rolename		varchar(30)		not null,
	rolecat		        varchar(30),
	roledesc		varchar(100),
        primary key (roleid)
);

insert into tbl_role values('��ϢԱ','XXY','��������վ¼����Ϣ');
insert into tbl_role values('�����쵼','BML','¼����Ϣ�����');
insert into tbl_role values('�����쵼','ZGL','¼����Ϣ�����');
insert into tbl_role values('ϵͳ����Ա','SM','����ϵͳ����Ա');
insert into tbl_role values('���԰����Ա','LWM','���԰����Ա');
insert into tbl_role values('�������԰����Ա','DLWM','�������԰����Ա');

create table tbl_sjs_log (
	id			int	identity(1, 1)	not null,               --����
	siteid			int     		not null,               --վ��ID           
	sitename		varchar(50)		not null,               --վ������
        columnid                int,                                            --��ĿID
        columnname              varchar(50),                                    --��Ŀ����
	articleid		int			not null,               --����ID
	maintitle		varchar(600)	        not null,               --�������ı���
        articleurl              varchar(300),                                   --����URL
        operationtype           smallint,                                       --���²�������    1-����   2-�޸� 3-ɾ�� 
        editor                  varchar(50),                                    --��������
	createdate	        datetime,                                       --����¼������
        updatedate              datetime,                                       --�����޸�����
        deletedate              datetime,                                       --����ɾ������
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
        phone                   varchar(12),                      --��ϵ�绰        
	mphone                  varchar(12),        
	sex                     tinyint              default(0),      --0��ʾ�� 1��ʾŮ
        country                 varchar(30),                          --����
        province                varchar(50),                          --ʡ
        city                    varchar(50),                          --��
        area                    varchar(50),                          --����
        jiedao                  varchar(50),                          --�ֵ�
        shequ                   varchar(50),                          --����
        postcode                varchar(6),                           --��������
        birthdate               datetime,                                  --��������
        myimage                 varchar(100),                         --ͷ��
        usertype                tinyint              default(0),      --�û�����    0---�����û�   1--��ҵ�û�        
        joincompanyid           int,                                --������ID��
        joinid                  int,                                --���������к�
        dflag                   int,                                --վ���Ƿ�ɾ��
        createarticles	        int  default(0),                    --�û�������������
        editarticles		int  default(0),                    --�û��޸���������
        deletearticles	        int  default(0),                    --�û�ɾ����������
        score                   int     default(0),
        createdate              datetime,
        company                 varchar(200),
        address                 varchar(500),
        department              varchar(50),                          --��������
        emailaccount            varchar(100),                         --email�ʺ�
        emailpasswd             varchar(100),                        --email����
        departmentarticlestype  int default(0),                     --������µķ�Χ0-���в���1-������2-ָ������
        departmentarticlesids   varchar(500),                         --ָ��������µĲ���id  
        orgid                   int,                                --�û�������֯�ܹ�ID
	companyid               int,                                --�û�������˾ID
	deptid                  int,                                --�û���������ID
	primary key (userid)
);


insert into tbl_members(userid,siteid,userpwd,nickname,email,createdate) values('admin',-1,'4346D362F67C89D7AB4E28732A1B1CE8','ϵͳ����Ա','p@biz.com',getdate());

--ϵͳ��ɫ��
create table tbl_member_roles (
       id                       int identity(1, 1)      not null,
       siteid                   int                     not null,
       userid                   varchar(30)             not null,
       deptid                   int                     not null,      --�û���������ID       
       rolename                 varchar(20)             not null,      --��ϢԱ   ���Ÿ�����   ����
       rolelevel                tinyint,                               --��ɫ���� 0-9
       primary key(id)
);

--��Ȩ�û���������Ա�
create table tbl_member_authorized_resouce (
       id                       int identity(1, 1)         not null,
       siteid                   int                        not null,
       userid                   varchar(30)                not null,
       lwid                     int,                                     --���û���������Ա�ID
       lwname                   varchar(100),                             --���û���������Ա������
       lwrole                   varchar(20),                              --���԰�����ɫ ��ĳ�����԰�Ĺ���Ա �������԰�Ĳ��Ź���Ա
       contenttype              int                         default(0),    --�������� 0Ϊ���԰�
       primary key(id)
);

-- ��֯�ܹ���
create table tbl_organization
(
       id                      int identity(1, 1)  not null,             --����ID 
       parent                  int,                                      --��ID
       cotype                  int default(0),                           --�ڵ����� 1--�ӹ�˾   2--����
       customerid              int,                                      --�ͻ�ID����Ӧ��վ��ID
       orderid                 int,                                      --�ڵ���ͬ���е����
       nodeid                  int,                                      --�ڵ��Ӧ�ӹ�˾������
       status                  int default(1),                           --״̬ 0 ��Ч 1 ��Ч
       name                    varchar(255) not null,                    --�ڵ�����
       ename                   varchar(255),                             --�ڵ�Ӣ������
       llevel                  int default(1),                           --��֯�ṹ�㼶
       orgcode                 varchar(50),                              --��֯�ڵ����
       isleaf                  int default(0),                           --�Ƿ���Ҷ�ӽ�ռ��0Ϊ��Ҷ�ӽڵ㣬1ΪҶ�ӽڵ�
       lastupdate              datetime default getdate(),               --����޸�����
       createdate              datetime default getdate(),               --��������
       createuser              varchar(50),                              --������
       uodateuser              varchar(50),                              --�޸���
       primary key(id)
);



--��ҵ��������
create table tbl_department (
       id                      int identity(1, 1)       not null,      --����
       parent                  int            not null,                --��ID
       companyid               int            not null,                --������˾ID
       siteid                  int            not null,                --վ��ID
       cname                   varchar(100)     not null,              --������������
       ename                   varchar(100),                           --����Ӣ������
       telephone               varchar(12),                            --�绰����
       manager                 varchar(50),                            --���ž���
       vicemanager             varchar(50),                            --���Ÿ�����
       leader                  varchar(50),                            --���������쵼
       lastupdate              datetime default getdate(),             --����޸�����
       createdate              datetime default getdate(),             --��������
       createuser              varchar(50),                            --������
       primary key(id)
);          



--ÿ���û���ÿ��վ���ÿ����Ŀ��¼�����������
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
	messagetype             int                          default(0),                       --0��ʾ��Ŀ������Ϣ   1��ʾ�û�������Ϣ
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
	messagetype             int                     default(0),      --0��ʾ��Ŀ������Ϣ  1��ʾ�û�������Ϣ
	column_audit_rule    	varchar(300)		not null,
	audittype               int                     default(0),      --������˷�ʽ 0-���û����ƶ�����˹���   1-����ɫ������˹���
	createdate           	datetime		not null,
	lastupdated          	datetime		not null,
	creator              	varchar(30)		not null,
	editor               	varchar(30)		not null,
        primary key (id)
);


--�����롱��ϵ���ֵ���˲���·��
create table tbl_column_auditing_process (
	id                   	int  identity(1, 1)     not null,
	rulesid             	int             	not null,
	subrules_by_or          varchar(300),                            --�ֽ���OR��˹���
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
	md5title                varchar(35),                               --���±����MD5��ʽժҪ
	vicetitle               varchar(600),
	summary                 varchar(1000),
	keyword                 varchar(200),
	source                  varchar(600),
	content              	text,
	emptycontentflag        smallint            not null,
        multimediatype          smallint           default(0),             --��ý�����±�־0-���Ƕ�ý������ 1-�Ƕ�ý������	
	author                  varchar(600),
	articleversion       	int,
	publishtime             datetime                not null,
	createdate              datetime                not null,
	lastupdated             datetime                not null,
	dirname                 varchar(200)        not null,
	filename                varchar(60),
	editor                  varchar(30)         not null,
        creator                 varchar(30)        	not null,           --��Ϣ����������
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
	notearticleid           int                     default(0),         --�������ƪ������������������µ����ݣ��������µ����ݿ����Ǳ�ҳ��һ����ǩ������
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
        commentnum              int                 default 0,        --���±����۵Ĵ���
	changepic               smallint            default 0,
	fromsiteid              int                 default 0,        --��ʶץȡ��Ϣ����Դվ��
	sarticleid              varchar(50),                          --ץȡ������Դ��վ��ID 
	mediafile               varchar(500),                         --��ý���ļ�����
	processofaudit          int                 default 0,        --���������ִ�е�����˲���
        deptid                  varchar(50),                          --���·����Ĳ���ID
	t1                      int                 default 0,
	t2                      int                 default 0,
	t3                      int                 default 0,
	t4                      int                 default 0,
	t5                      int                 default 0,
	deptid                  varchar(50),                               --���·����Ĳ���ID
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

--��ץȡ�ļ�����ϢԴ��ص���Ϣ
create table tbl_article_sourceinfo
(
        id                            int                  not null,
        articleid                     int                  not null,
	maintitle            	      varchar(600)         not null,           --���±���        
	md5title                      varchar(35),                             --���±����MD5��ʽժҪ
	publishtime_from_source       datetime		   not null,           --�����ڱ�ץȡ��վ�ķ���ʱ��
	sourceid                      int,                                     --ץȡ������Դ��վ��ID 
        createdate                    datetime,                                --��Ϣ��ץȡ��ʱ��
  	CONSTRAINT fk_articleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)        
);


create table tbl_deleted_article (
        siteid                  int                not null,
	columnid                int                not null,
        articleid               int                not null,
	sitename                varchar(100),
	acttype                 tinyint           default    0     --0��ʾ��ɾ��������   1��ʾ���޸ĵ�����        
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
	xmltype                 varchar(3000),                         --���·���Ķ���
	userflag                smallint         default 0,            --��Ŀ�����Ƿ�ֻ��ע���û��ɿ�  0--�����û� 1--ע���û�
	userLevel               int              default 0,            --�û�������µļ���
	encodingflag            smallint,                              --�ĵ������ʽ 1-utf-8  2-gb2312  3-gbk
	doctype                 varchar(500),                          --�ĵ�����   HTML5  HTML4
	islocation              smallint         default(1),            --��Ŀ�洢λ����Ϣ
	contentshowtype	        smallint,                              
	publicflag              smallint         default 0   not null,
	shareflag               smallint         default 0,            --��Ŀ�Ƿ��Ǻ�������վ�������Ŀ
	articlecount            int              default 0,            --����Ŀ������������
	titlepic                varchar(10),                           --����ͼƬ��С
	vtitlepic               varchar(10),                           --������ͼƬ��С
	sourcepic               varchar(10),                           --��ԴͼƬ��С
	authorpic               varchar(10),                           --����ͼƬ��С
	contentpic              varchar(10),                           --����ͼƬ��С
	specialpic              varchar(10),                           --������ЧͼƬ��С
	productpic              varchar(10),                           --��Ʒ��ͼƬ��С
	productsmallpic         varchar(10),                           --��ƷСͼƬ��С
        ts_pic                  varchar(10),                           --��СͼͼƬ��С
        s_pic                   varchar(10),                           --СͼͼƬ��С
        ms_pic                  varchar(10),                           --��СͼͼƬ��С
        m_pic                   varchar(10),                           --����ͼƬ��С 
        ml_pic                  varchar(10),                           --�д���ͼƬ��С
        l_pic                   varchar(10),                           --����ͼƬ��С
        tl_pic                  varchar(10),                           --�ش���ͼƬ��С
        primary key (id)
);


insert into tbl_column (siteid,parentid,orderid,cname,ename,dirname,editor,extname,isdefineattr,hasarticlemodel,createdate,lastupdated,isaudited,isproduct,ispublishmore,languagetype,publicflag) values(0,-1,0,'��ҳ','home','/','admin','html',0,0,getdate(),getdate(),0,0,0,0,0);

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
        articlenum              int        default(0),                        --���һ�η������µ����������������б���������	
	formatfilenum           int,
	createdate              datetime                 not null,
	updatedate              datetime                 not null,
	publishtime             datetime                 not null,
	chinesename             varchar(40)          not null,
	relatedcolumnid         varchar(500),
	ispublished             smallint     default 0,             --����Ƿ񷢲�����0--δ  1--��
        primary key (id)
);

--��Ŀģ��ͱ�ǵ���ر�
CREATE TABLE TBL_TemplateMark (
	ID                   int  identity(1, 1)  NOT NULL,                   --���
	siteid               int    not null,                   --վ��id
        tid                  int    not null,                   --ģ��id
        mid                  int    not null,                   --��ģ���ϵı��id
	CONSTRAINT pk_tmid PRIMARY KEY (ID),
	CONSTRAINT fk_templateMarkID FOREIGN KEY (tid) REFERENCES  tbl_template(ID) ON DELETE CASCADE
);

--�������Ŀ����ر�
CREATE TABLE TBL_MarkColumn (
	ID                   int identity(1, 1)   NOT NULL,                   --���
	siteid               int    not null,                   --վ��id
        mid                  int    not null,                   --���id
        cid                  int    not null,                   --������õ���Ŀid
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
        sitetype                int       default 0,                --�����͹���վ�������,�����������վ��������վ�ȵ�
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
	pusharticle	        smallint  default 0,                --�����ļ�����
	movearticle	        smallint  default 0,                --�ƶ��ļ�����
	config                  varchar(200),
	sitepic                 varchar(200),                       --վ����ҳͼƬ
	sitevalid               smallint  default 0,                --0-������վ 1-��ͨ��վ 2-������վ
	sitelogo                varchar(200),                       --վ��LOGO
	banner                  varchar(200),                       --վ����BANNER
	navigator               int       default 0,                --վ�㵼����ʽ
	samsiteid               int       default 0,                --����վ��ID
	scores                  int       default 0,                --���ֺͻ��Ҷһ�����
	sharetemplatenum        int       default 0,                --��������վ���ģ���׺�
	managesharesite         smallint  default 0,                --������վ����Ϣ   0-������  1-����
        COPYRIGHT               varchar(1000),	                    --վ���Ȩ��Ϣ
	encodeflag              smallint  default(1),               --�ĵ������ʽ 1-utf-8  2-gb2312  3-gbk
        titlepic                varchar(10),                        --����ͼƬ��С
	vtitlepic               varchar(10),                        --������ͼƬ��С
	sourcepic               varchar(10),                        --��ԴͼƬ��С
	authorpic               varchar(10),                        --����ͼƬ��С
	contentpic              varchar(10),                        --����ͼƬ��С
	specialpic              varchar(10),                        --������ЧͼƬ��С
	productpic              varchar(10),                        --��Ʒ��ͼƬ��С
	productsmallpic         varchar(10),                        --��ƷСͼƬ��С
        ts_pic                  varchar(10),                        --��СͼͼƬ��С
        s_pic                   varchar(10),                        --СͼͼƬ��С
        ms_pic                  varchar(10),                        --��СͼͼƬ��С
        m_pic                   varchar(10),                        --����ͼƬ��С 
        ml_pic                  varchar(10),                        --�д���ͼƬ��С
        l_pic                   varchar(10),                        --����ͼƬ��С
        tl_pic                  varchar(10),                        --�ش���ͼƬ��С
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
       sitegroupid              int                   not null,     --վ����ID��������վID
       siteid                   int                   not null,     --���ԱID
       sitename                 varchar(100),                       --���Ա����
       createdate               datetime,                               --��������
       primary key(id)
);
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,931,'wangjian.coosite.com',getdate());
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,933,'petersong103.coosite.com',getdate());
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,934,'wangjianjilin1.coosite.com',getdate());
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,971,'bjchaohong.coosite.com',getdate());
insert into tbl_sitegroup(sitegroupid,siteid,sitename,createdate) values(36,885,'ssdemotest.coosite.com',getdate());

create table tbl_navigator (
       id                       int identity(1, 1)    not null,
       cname                    varchar(200)          not null,     --��������������
       content                  varchar(3500),                      --������HTML����
       fcontent                 varchar(3500),                      --��������HTML����
       notes                    varchar(250),
       createdate               datetime,                               --��������
       primary key(id)
);


--������Ŀ֮���������µĶ�Ӧ��ϵ��������õ�����
create table tbl_refers_article (
        id                      int  identity(1, 1)   not null,
	articleid               int                   not null,     --����ID
        siteid                  int                   not null,     --����ԭվ��ID
	columnid       	        int 	              not null,     --Ŀ����Ŀ��ID
	scolumnid               int                   not null,     --Դ��ĿID
	columnname	        varchar(100),                       --Ŀ����Ŀ����������
	orders		        smallint   default 0,
	createdate	        datetime,
	title		        varchar(600),                       --��Ŀ����Ŀ�еı�������
	status                  smallint              not null,     --Ŀ����Ŀ�е�״̬
	pubflag                 smallint              not null,
	auditflag               smallint              not null,
	usearticletype          int,
	refers_column_status    int,
	tsiteid                 int,                                --Ŀ��վ��ID
	artfrom                 smallint,                           --0-��Ŀ���崦���õ����ã�1-����¼��ʱ���õ�����    
	primary key(id)
);

--��Ŀ������ѡ��ı��������µ���Ŀ
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
	targetid                int                   not null,       --��Ҫ���������µ�ID����Ŀ��ID
	type                    smallint,		              --1����,2��Ŀģ��,3��ҳģ��,4���
	status                  smallint,
	createdate              datetime,
	publishdate             datetime,
	uniqueid                varchar(50),                                  --���Դ洢ע���Ա��ID
	title                   varchar(200),
        errcode                 int           default 0,              --��ҵ��������ʱ����������,Ĭ��0��ʾ�ɹ�����
        errmsg                  varchar(500),                        --���ִ���ʱ�洢������Ϣ
        errnum                  smallint      default 0,              --�洢������������·����Ĵ�����������·���3��
        priority                smallint      default 0,              --��ҵ�����ȼ� 0-��ʾһ�����ȼ�
        primary key (id)
);
  
create table tbl_multimedia
(
        id                      int                         not null,           --����
        siteid                  int                         not null,           --վ��id 
        articleid               int                      default(0),            --����id
        dirname                 varchar(100)               not null,            --��Ŀ·��
        filepath                varchar(100)               not null,            --�ļ�·��
        oldfilename             varchar(100)               not null,            --ԭ�ļ�����
        newfilename             varchar(100)               not null,            --���ļ�����
        encodeflag              int default(0),                                 --�ļ�ת����־0-δת�� 1-�Ѿ�ת��
        infotype                tinyint  default(0),                            --0�������ݵ�ͼƬ    1��ҵ������ϢͼƬ    2վ�������ϢͼƬ
        createdate              datetime default getdate(),                     --����ʱ��
        primary key(id)
);  
    
create table TBL_RELATEDARTIDS                                          --ҳ������ļ���
(
        ID                      int  identity(1, 1)      not null,
        cname                   varchar(100)             not null,
        pageid                  int                      not null,
        pagetype                smallint    default 0,                  --0����ҳ  1��Ŀҳ  2��ҳ  3ר��ҳ
        contenttype             smallint    default 1,                  --1�����ļ�  2ͼƬ�ļ� 3ȫ��ͼƬ�ļ� 4��ý���ļ�
        filename                varchar(200),
        summary                 varchar(250),
        editor                  varchar(50),                            --�ϴ���
        createdate              datetime,                                   --�ϴ�����
        primary key (id)        
);

create table TBL_Article_Keyword
(
        ID                      int identity(1, 1)       not null,
        siteid		        int                      not null,
        columnid	        int                      not null,
        keyword                 varchar(20),
        keywordnum              int                      default 0,      --�ؼ��ʸ���
        flag                    smallint                 default 0,      --1��ѡ������ڹؼ����б���        
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

create table tbl_turnpic (                                                   --ͼƬ�ֻ���
        id                      int  identity(1, 1)         not null,
        articleid               int                         not null,
	sortid                  int,                                             --ͼƬ��ʾ˳��
	mediaurl                varchar(500),                                    --��ý���ļ�·��
	latitude                decimal(38,15),                                   --ͼƬ�ľ���
	longitude               decimal(38,15),                                   --ͼƬ��γ��
        picname                 varchar(100),
        createdate              datetime  default getdate() not null,
        notes                   varchar(2000),
	lib1                    varchar(100),                                    --��ǩ1
        lib2                    varchar(100),                                    --��ǩ2
        lib3                    varchar(100),                                    --��ǩ3
        lib4                    varchar(100),                                    
        lib5                    varchar(100),                                    
        primary key(id)
);



--LOG����ϵͳ
create table tbl_pv_detail(
        id                 bigint  identity(1, 1)        not null,        --����ID
	siteid             int                           not null,        --վ��ID
	urlname            varchar(500),                                  --URL��ַ
	url_cn_name        varchar(500),                                  --��������
	pageview           int,                                           --ҳ�������
	logdate            datetime,                                      --logʱ��
	primary key(id)
);


--�����չ����
create table tbl_workdayinfo
(
    days                    varchar(50) not null,
    siteid                  int   default 0 not null,
    worddayflag             int   default 0 not null, --0������1�ǹ�����
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
create index articleindex_parent_article on tbl_article(RELATEDARTID);    --������ID������
create index articleindex_forsiteid on tbl_article(siteid);
create index articleindex_editor on tbl_article(editor);
create index columnindex_forsiteid on tbl_column(siteid);
create index templateindex_forsiteid on tbl_template(siteid);
create index templateindex_forcolumnid on tbl_template(columnid);
create index templateindex_forreferid on tbl_template(ReferModelID);
create index order_fororderid on tbl_orders(userid);


--������ע���
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
insert into tbl_companyclass values(0,-1,0,'��˾����','company','/','admin','html',getdate(),getdate());

create table tbl_companyinfo(
       id                        int  identity(1, 1)         not null,     --����
       siteid                    int,                                      --����վ��
       companyclassid            int,                                      --��ҵ��������
       classification            varchar(200),                             --��˾������������
       companyname               varchar(100),                             --��˾����
       companyaddress            varchar(100),                             --��˾���ڵ�ַ
       companyphone              varchar(50),                              --��˾��ϵ�绰
       companyfax                varchar(50),                              --��˾��ϵ����
       companywebsite            varchar(100),                             --��˾վ������
       companyemail              varchar(100),                             --��˾email��ַ
       postcode                  varchar(20),                              --��˾��������
       summary                   varchar(1000),                            --��˾���� 
       COMPANYLATITUDE           decimal(38,7),                            --��˾���ڵصľ���
       COMPANYLONGITUDE          decimal(38,7),                            --��˾���ڵص�γ��
       companypic                varchar(200),                             --��˾ͼƬ
       publishflag               smallint,                                 --�������
       createdate                datetime,                                 --��������
       lastupdated               datetime,                                 --�޸�����
       primary key(id)
);

--������վ����
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
insert into tbl_websiteclass values(0,-1,0,'��ַ����','website','/','admin','html',getdate(),getdate());

--��վ������Ϣ
create table tbl_websiteinfo(
       id                                        int identity(1, 1),            --����
       siteid                                    int,                           --����վ��
       websitename                               varchar(100),                  --��˾����
       websiteurl                                varchar(200),                  --��˾���ڵ�ַ
       summary                                   varchar(200),                  --��˾��ϵ�绰
       editor                                    varchar(50),                   --�༭
       pubflag                                   smallint,                      --�������
       createdate                                datetime,                      --��������
       updatedate                                datetime,                      --�޸�����
       primary key(id)
);

--��������
create table tbl_program_library
(
        id            int identity(1, 1) not null,         --������������
        p_type        int,                                 --��������
        position      int,                                 --JSPҳ���е�λ��
        l_type        int,                                 --��������
        explain       varchar(100),                        --����˵��
        notes         varchar(500),                        --����ע��
        program       text,                                --������
        primary key(id)
);

create table tbl_program_position
(
        id            int identity(1, 1) not null,        --����
        position      varchar(50),                        --λ��˵��
        primary       key(id)
);

insert into tbl_program_position (position) values('����ͷ');
insert into tbl_program_position (position) values('��ʾ��');
insert into tbl_program_position (position) values('ҳβ');

create table tbl_program_type
(
        id            int identity(1, 1) not null,      --����
        p_type        varchar(50),                      --��������˵��
        primary       key(id)
);

insert into tbl_program_type (p_type) values('��Ϣ����');
insert into tbl_program_type (p_type) values('���ﳵ');
insert into tbl_program_type (p_type) values('��������');
insert into tbl_program_type (p_type) values('������ѯ');
insert into tbl_program_type (p_type) values('�û�ע��');
insert into tbl_program_type (p_type) values('�û�����');
insert into tbl_program_type (p_type) values('�û���Ϣ����');
--������������

--��վǰ̨Ӧ�ñ�
--�û�ע���
create table tbl_userinfo
(
        ID                   int identity(1, 1)     not null,   --����
  	SITEID               int,                               --վ��ID
  	MEMBERID             varchar(50)            not null,   --�û�ID(*)
  	USERNAME             varchar(30),                       --�û���������(*)
  	PASSWORD             varchar(20),                       --�û�����(*)
  	LINKMAN              varchar(30),                       --��ϵ��(*)
  	nation               varchar(30),                       --����
  	degree               varchar(30),                       --�Ļ��̶�
  	idno                 varchar(19),                       --���֤����
  	street               varchar(100),                      --���ڽֵ�
  	CITY                 varchar(20),                       --���ڳ���
  	ADDRESS              varchar(200),                      --�����ַ
  	COUNTRY              varchar(50),                       --����
  	POSTALCODE           varchar(10),                       --��������
  	PHONE                varchar(20),                       --�绰(*)
  	FAX                  varchar(20),                       --����
  	EMAIL                varchar(100),                      --�����ʼ�(*)
  	HOMEPAGE             varchar(100),                      --������ҳ
  	REMARK               varchar(500),                      --��������
  	USERTYPE             int,                               --�û�����(*)        0--�ⲿ��ͨע���û�   1--�ڲ��û�
  	VALID                int,                               --�Ƿ���Ч
  	SEX                  varchar(10),                       --�û��Ա�
  	OICQ                 varchar(50),                       --�û�QQ����
  	BIRTHDAY             datetime,                          --��������
  	IMAGE                varchar(50),                       --�û�ͷ��
  	SIGN                 varchar(50),                       --�û�ǩ��
  	scores               int  default 0,                    --�û�����
  	LOGINNUM             int,                               --��¼����
  	GRADE                varchar(50),                       --�û����� (*)
  	THEONLINE            varchar(10),                       --
  	RENZHENG             varchar(50),                       --
  	MOBILEPHONE          varchar(50),                       --�ƶ��绰(*)
  	PROVINCE             int,                               --ʡ��
  	createdate           datetime,                          --����ʱ��
  	company              varchar(500),                      --������λ
  	unitpcode            varchar(10),                       --��λ��������
  	unitphone            varchar(20),                       --��λ�绰
  	stationtype          varchar(8),                        --վ̨���
  	entitytype           varchar(1),                        --����/����
  	stationaddr          varchar(80),                       --վ̨��ַ
  	opedegree            varchar(20),                       --����֤�ȼ�
  	opecode              varchar(20),                       --����֤�����
  	callsign             varchar(20),                       --����
  	memo                 varchar(512),                      --��ע    
  	lockflag             int default 0 not null,            --�Ƿ�����
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

--��Ȩ�û��ܹ��鿴����Ŀ
create table column_authorized(
        id                  int             	not null,
        siteid              int                 not null, 
        columnid            int                 not null,
        targetid            int                 not null,  --�û�ID�����ǽ�ɫID
        type                tinyint             default 0, --0��ʾtargetidΪ��ɫid, 1��ʾtargetidΪ�û�id
        createdate          datetime,
        primary key(id)
);

--��������
CREATE TABLE TBL_COMMENT ( 
	id             int   identity(1, 1)     not null,                --����
	siteid         int                      not null,                --վ��id
	userid         int                      not null,                --�û�id
	Username       varchar(30)              not null,                --�û���¼����
	pid            int                      not null,                --��Ʒid
	commenttitle   varchar(200)             not null,                --����
	Scores         int                      default(0),              --����
	experience     varchar(500),                                     --ʹ���ĵ�
	Advantages     varchar(500),                                     --�ŵ�
	Shortcomings   varchar(500),                                     --����
	usefulnum      int                      default(0),              --��Ϊ���������õ�����
	nousenum       int                      default(0),              --��Ϊ���������õ�����
	auditflag      int       default 0,                              --0 ���ͨ��������ʾ 1-���δͨ�� ����ʾ 		
	createdate     datetime                 default getdate(),       --����ʱ��
	CONSTRAINT pk_commentid PRIMARY KEY (ID)	
);

--�������ۻظ�
create table Tbl_commentanswer
(
        id              int   identity(1, 1)     not null,               --����
        cid             int                      not null,               --��Ʒ����id���
        username        varchar(100)             not null,               --�ظ��˵�¼��
        answercontent   varchar(500)             not null,               --�ظ�����
        createdate      datetime                 default getdate(),      --�ظ�ʱ��
	CONSTRAINT fk_commentID FOREIGN KEY (cid) REFERENCES  tbl_comment(ID) ON DELETE CASCADE,
        primary key(id)
);

--����
CREATE TABLE su_survey  (																--�����
	 id           int  identity(1, 1) not null,   	   --����
	 surveyname   varchar(200),			   --��������
	 notes        varchar(2000),			   --��������
	 createtime   datetime,		                   --����ʱ��
	 siteid       int default 0 not null,		   --վ��id	
	 useflag      int default 0 not null,		   --��Ч��־ 0-��Ч1-��Ч
         primary key (id) 
);

CREATE TABLE  su_answer  (				   --�𰸱�
	 id           int  identity(1, 1) not null,	   --����
	 userid       int,				   --�û�id
	 sid   	      int,				   --����id
	 qid   	      int,				   --����id	
	 answers      varchar(200),			   --��
	 other        varchar(200),			   --������ֵ
         primary      key(id)
);

CREATE TABLE  su_danswer  (			           --����𰸱�
	 id           int  identity(1, 1) not null,	   --����
	 qid          int  default 0 not null,	           --����id
	 qanswer      varchar(200),		           --�����
	 picurl       varchar(100),                        --ͼƬ��ַ
         primary      key(id) 
);

CREATE TABLE  su_dquestion  (				   --���ⶨ���
	 id           int  identity(1, 1) not null,	   --����
	 sid          int  not null,                       --����id
	 qname        varchar(500),                        --��������
	 qtype        int,                                 --��������
	 qmust        int,                                 --�Ƿ��ѡ
	 nother       int,                                 --�Ƿ���Ҫ�����	
	 atype        int,                                 --������
         primary      key(id) 
);

create table SU_DEFINEUSERINFO
(
        ID         int  identity(1, 1) not null,
        SID        int  default 0,                         --����ID
        USERNAME   varchar(100),                            --�ύ�����û���
        PHONE      varchar(100),                            --�ύ�����û��绰
        CREATEDATE datetime,
        primary key(id)
);

--�ҵĵ�ַ��
CREATE TABLE  tbl_userinfos (
	id            int  identity(1, 1) not null,         --����
	username      varchar(20),                          --�û�����
	address       varchar(200),                         --��ַ
	email         varchar(100),                         --�����ʼ�
	sex           int,                                  --�Ա�
	mobilephone   varchar(20),                          --�ƶ��绰
	postcode      varchar(10),                          --�ʱ�
	phone         varchar(20),                          --�绰
	corporation   varchar(500),                         --��˾
	fax           varchar(20),                          --����
	memberid      varchar(50),                          --�û�id
	createtime    int                  not null,        --����ʱ��
	CONSTRAINT pk_userinfos_id PRIMARY KEY (ID)
);

--ÿ����ˮ�Ź����
create table tbl_lshbydate(
       cdate   datetime,
       lsh     varchar(15)
);

--�û�����
CREATE TABLE TBL_LEAVEWORD
(
	id             int  identity(1, 1)  not null,              --����
	columnid       int                  default(0),            --��ѯ������ඨ��
  	siteid         int                  not null,              --վ��id
  	code           varchar(12),                                --�û���ѯ��ˮ��   AyyyymmddXXX    ����ĸA��ʾ�ֳ�����  B��ʾ������ѯ C��ʾ�����ײ�
  	sex            tinyint,                                    --�������Ա�
  	userid         varchar(50),                                --��¼�û�ID,���û�е�¼����¼IP
	flag           int                  default(0),            --�Ƿ�����վ����ʾ��1--��ʾ 0--����ʾ  	
  	title          varchar(100),                               --���Ա���
  	content        varchar(4000),                              --��������
  	writedate      datetime,                                   --��������
  	company        varchar(200),                               --��˾
  	linkman        varchar(100),                               --��ϵ��
  	links          varchar(500),                               --��ϵ��ʽ
  	zip            varchar(20),                                --����
  	email          varchar(100),                               --�����ʼ�
  	phone          varchar(20),                                --�绰
  	formid         int,                                        --�û����Ա�ID
  	departmentid   int,                                        --��Ϣ�ַ����Ĳ���ID
	retcontent     varchar(2000),                              --�ظ�������
  	auditor        varchar(50),                                --�����Ҫ��ˣ������������Ϣ
  	auditflag      int                 default(0),             --0��ʾ������  1��ʾ��˽�����
  	mphone         varchar(20),                                --�ƶ��绰
  	processor      varchar(50),                                --�������û�ID
	valid          tinyint             default(0),             --�����Ƿ���Ч 0--��Ч 1--��Ч
	validreason    varchar(512),                               --������Ч��ԭ��
	datefromdept   datetime,                                   --���Żظ�����
	finalflag      tinyint             default(0),             --���ջظ��û���־λ�����ظ������׼�������԰岿�Ź���Ա�����԰����Ա��֮����Ϊ1
	endtouser      datetime,                                   --���ջظ��û�����
	CONSTRAINT pk_leaveword_id PRIMARY KEY (ID)
);
create index leavemsgindex_forsiteid on tbl_leaveword(siteid);

create table tbl_leaveword_to_dept
(
	id             int  identity(1, 1) not null,               --����
  	siteid         int                 not null,               --վ��id
  	departmentid   int,                                        --��Ϣ�ַ����Ĳ���ID
  	formid         int,                                        --�û����Ա�ID
  	lwid           int,                                        --�û�����ID
  	processor      varchar(50),                                --�ش����û�ID
	flag           int                 default 0,              --�Ƿ��Ѿ��ش�1--�ش� 0--δ�ش�  	
  	content        varchar(4000),                              --�ش�����
  	retdate        datetime,                                   --�ش�����
  	createdate     datetime            default getdate(),      --��������û�������
	CONSTRAINT pk_leaveword_to_dept_id PRIMARY KEY (ID)
);

--�û����Է������
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
        lwnum          int                  default(0),            --ĳ�������������
	createdate     datetime             not null,
	lastupdated    datetime		    not null,
	primary key(id)
);
insert into tbl_leavewordclass values(0,-1,0,'���Է���','lwsite','/','admin','html','',0,getdate(),getdate());

create table tbl_leaveword_valid_reason
(
	id             int  identity(1, 1)  not null,                    --����
  	siteid         int                  not null,                    --վ��id
        content        varchar(500)         not null,
        primary key(id)
);

insert into tbl_leaveword_valid_reason(siteid,content) values(40,'ȱ����ϵ��Ϣ');
insert into tbl_leaveword_valid_reason(siteid,content) values(40,'ѯ�����ⲻ����');


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


--��������ϵͳ
--ȫ����ң���
create table EN_COUNTRY
(
  id                int               not null,
  orderid           int,
  countryname       varchar(20)       not null,
  valid             int               default 0,                   --1--ʹ��   0--ͣ��
  code              varchar(6)
)

--ʡ�м���λ��22��ʡ��5����������4��ֱϽ�У�2���ر���������
create table EN_PROVINCE
(
  id                int               not null,
  orderid           int,
  countryid         int               not null  default(86),
  provname          varchar(20)       not null,
  valid             int               default 0,                   --1--ʹ��   0--ͣ��
  code              varchar(6)
)

--�ؼ�������λ��17��������30�������ݡ�283���ؼ��С�3���ˣ�
create table en_city
(
  id                int               not null,
  orderid           int,
  provid            int               not null,
  cityname          varchar(30)       not null,
  valid             int               default 0,                   --1--ʹ��   0--ͣ��
  code              varchar(6)
);

--���ؼ�������λ��1464���ء�117�������ء�374���ؼ��С�852����Ͻ����49���졢3�������졢1��������2��������
create table en_zone
(
  id               int                 not null,
  orderid          int, 
  cityid           int                 not null,
  zonename         varchar(30)         not null,
  valid            int                 default 0,                   --1--ʹ��   0--ͣ��
  code             varchar(6)
);

--����������λ��14677���硢1092�������硢19522����6152���ֵ����´���11����������181����ľ��1��������ľ��
create table en_town
(
  id               int                 not null,
  orderid          int,
  zoneid           int                 not null,
  townname         varchar(30)         not null,
  valid            int                 default 0,                   --1--ʹ��   0--ͣ��
  code             varchar(6)
);

--�塢������������λ��80717����������ίԱ�ᡢ623669������ίԱ�ᣩ
create table en_village
(
  id               int                 not null,
  orderid          int,
  townid           int                 not null,
  villagename      varchar(30)         not null,
  valid            int                 default 0,                   --1--ʹ��   0--ͣ��
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

--���湺�ﳵ�������Ϣ
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
        fenqi            varchar(250),                     --���ڸ���
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

CREATE TABLE tbl_orders(                                                    --������
	 ORDERID                  bigint    identity(1, 1)     not null,    --������
	 initorderid              int    default 0,                         --ԭʼ������(��=ԭ���ţ��ǲ�=0)
	 USERID        	          int,	     			            --�û�id
	 NAME          		  varchar(50),		                    --�ջ�������
	 SEX           		  int,				            --�Ա�
	 ADDRESS       		  varchar(1000),	                    --��ַ
	 POSTCODE      		  varchar(20),		                    --�ʱ�
	 PHONE         		  varchar(50),		                    --�绰
	 TOTALFEE      		  decimal(9,2),		                    --�����ܶ�
	 DELIVERYFEE   		  decimal(9,2),		                    --�ʼķ���
	 PAYFEE        		  decimal(9,2),		                    --Ӧ������
	 CREATEDATE    		  datetime,				    --����ʱ��
	 VALID         		  int      default 0,		
	 SITEID        		  int,				            --վ��id
	 STATUS        		  int                        not null,	    --����״̬
	 BILLINGWAY    		  int,				            --֧����ʽ
	 CITY          		  varchar(50),			            --����
	 RECEIVE       		  int,				
	 NOUSE         		  int      default 1,	                    --�����Ƿ���Ч 1-��Ч 0-��Ч
	 PROVINCE      		  varchar(50),			            --ʡ��
	 zone          		  varchar(50),			            --����
	 sendway       		  int,				            --���ͷ�ʽ
	 payway        		  int,		                            --���ʽ
	 commissioncharge         decimal(9,2),		                    --�ۿ�
	 insurance   		  decimal(9,2),		                    --���շ�
	 need_invoice             int        default 0,	                    --�Ƿ���Ҫ��Ʊ
	 bill_to_address          varchar(2000),	                    --��Ʊ��ַ
	 notes                    varchar(4000),	                    --��Ʊ��Ϣ
	 flag                     smallint   default 0,
	 payflag                  smallint   default 0,                     --�Ƿ��Ѿ�֧��
	 linktime                 varchar(50),			
	 usescores                int        default 0,	                    --�û�ʹ�û���
	 orderscores              int        default 0,                     --���������Ļ��� 
	 primary key(orderid) 
);

CREATE TABLE TBL_ORDERS_DETAIL  ( -- ������ϸ��Ϣ��
	 ID                       bigint  identity(1, 1)     not null,       --����
	 ORDERID                  bigint                    not null,       --������
	 supplierid               int      default 0,                       --������id(��Ӧÿ��������siteid)
	 suppliername             varchar(100),                             --����������
	 PRODUCTID                int,				            --��Ʒid
	 ORDERNUM                 int,				            --��Ʒ����
	 SALEPRICE                decimal(9,2),		                    --��Ʒ�ۼ�
	 WEIGHT                   int,				            --��Ʒ����
	 CREATEDATE               datetime,				    --����ʱ��
	 productname              varchar(200),	                            --��Ʒ����
	 type                     varchar(100),	                            --��Ʒ���ͣ���չ���ԣ�
	 cardid                   int  default 0, 	                    --ʹ�ù����id 
	 CONSTRAINT fk_orderID FOREIGN KEY (ORDERID) REFERENCES  TBL_ORDERS(ORDERID) ON DELETE CASCADE,	 	 
	 primary key(id)
);

--�ͻ���ʽ����
create table tbl_fee(
	id                        int identity(1, 1) not null,              --����
	siteid                    int default 0,                            --վ��id
	cname                     varchar(200),				    --�ͻ���ʽ����
	notes                     varchar(2000),                            --�ͻ���ʽ˵��
	fee			  decimal(9,2) default 0,                   --�˷�
	createdate                datetime default getdate(),               --��������
	primary key(id)
);

create table en_defaultfee (
       id                         int,                                      --���ͷ�ʽID
       fee                        decimal(9,2) default 0                    --���ͷ�ʽ��Ӧ���˷�
);

--�˷ѹ���
create table en_feeforproduct --��Ʒ�˷�
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

create table en_cityfee --�����˷�
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

create table en_paywayfee --֧����ʽ�˷�
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

--֧����ʽ����
create table tbl_sendway(
        id                        int identity(1, 1) not null,              --����
        siteid                    int                not null,              --վ��id
        cname                     varchar(200),				    --֧����ʽ����
	notes                     varchar(2000),                            --֧����ʽ˵��
	createdate                datetime default getdate(),               --��������
	primary key(id)
);

--�ͻ���ַ
create table tbl_addressinfo(
        id                        int identity(1, 1)  not null,       --����
        userid			  int                 not null,       --�û�id
        name                      varchar(200),                       --�ջ�������
        provinces                 varchar(50),			      --ʡ��
        city                      varchar(50),			      --����
        zone                      varchar(50),                        --����
        mobile                    varchar(50),                        --�ƶ��绰
        phone                     varchar(50),                        --�̶��绰
        zip                       varchar(50),                        --�ʱ�
        address                   varchar(200),                       --��ϸ��ַ
	createdate                datetime default getdate(),         --����ʱ��
	primary key(id)
);

--����ȯ
CREATE TABLE tbl_shoppingcard(
	id                        int identity(1, 1)  not null,       --����
	siteid                    int                 not null,
	cardnum                   varchar(50),                        --����ȯ��ʶ�� 
	denomination              int,                                --����ȯ���
	begintime                 varchar(50),                        --��ʼʱ��
	endtime                   varchar(50),                        --����ʱ��
	code                      varchar(50),                        --������
	createtime                datetime,                           --����ʱ��
	activation                int,                                --�Ƿ�ʹ��0-δ��ʹ�ã�1-��ʹ��
	ischeck                   int                 not null,       --�Ƿ񷢷�0-δ���ţ�1-�ѷ���
	primary key(id) 
);

--������������
create table tbl_mai_zeng_fangan(
        id               int identity(1, 1)           not null,
        siteid           int                          not null,
        zengway          int                          not null,       --��Ʒ�������
        price            decimal(9,2),                                --���۸�����һ�������ļ۸�ﵽ�涨����ֵ������Ʒ��һ�ַ�����
        pcount           int,                                         --����������ĳ����Ʒ���򵽹涨������������Ʒ    ���ڶ��ַ�����
        productid        int,                                         --����������Ҫ�������Ʒ
        productbrand     varchar(50),                                 --����ĳ��Ʒ�Ƶ���Ʒ������Ʒ                    �������ַ����� 
        useflag          smallint,                                    --�÷����Ƿ�ʹ��     0--��ʹ��   1--ʹ��
        majorflag        smallint,                                    --��Ҫ������������������ͻ�����ʹ�ø÷�����1--������������ʹ�÷�����ֻ����һ��������
        createdate       datetime,                                    --������������
        editor           varchar(50),                                 --����������
        primary key(id)
);


--��Ʒ����
create table tbl_zengpin(
        productid        int                          not null,       --��ƷID
        productname      varchar(250)                 not null,       --��Ʒ����
        fanganid         int                          not null,       --���÷���ID
        createdate       datetime,                                    --��������
        editor           varchar(50)                                  --������
);

--��������
create table tbl_groupproductsale(
        groupid          int identity(1, 1)           not null,       --��Ʒ���ID
        productid        int                          not null,       --��ƷID
        productname      varchar(250)                 not null,       --��Ʒ����
        summary          varchar(500),                                --��ϲ�Ʒ����
        productcount     smallint                     not null,       --��Ʒ����
        YhPrice          decimal(9,2),                                --�Żݼ۸�
        groupbigpic      varchar(250),                                --��ϴ�ͼ
        groupsmallpic    varchar(250),                                --���Сͼ
        grouptexuao      varchar(250),                                --�����Чͼ
        groupcontentpic  varchar(250),                                --�������ͼ
        begintime        datetime,                                    --��ʼ����
        endtime          datetime,                                    --��������
        createdate       datetime,                                    --��������
        editor           varchar(50)                                  --������
);

--֧���ӿڶ����
create table tbl_paywayinterface(
       id                int                           not null,
       paywayid          int                           not null,
       accountnumber     varchar(200),
       paywaykey         varchar(200),
       alipayaccount     varchar(100),
       createdate        datetime                      not null,
       primary key(id) 
); 

--��Ʊ��Ϣ��  ������
create table tbl_invoiceinfo
(
       id                 int           identity(1, 1),
       siteid             int           default 0,           --վ��id
       userid             int           default 0,           --�û�id
       orderid            int           default 0,           --�û�������
       invoicetype        int           default 0,           --��Ʊ����(0-��ͨ��Ʊ1-��ֵ˰��Ʊ)
       title              int           default 0,           --��Ʊ̧ͷ������/��λ0-����1-��λ��
       companyname        varchar(200),                        --��λ����
       content            int           default 0,           --��Ʊ����(�������id)
       identification     varchar(200),                        --��˰��ʶ���
       registeraddress    varchar(200),                        --ע���ַ
       phone              varchar(50),                         --ע��绰
       bankname           varchar(200),                        --��������
       bankaccount        varchar(100),                         --�����ʺ�
       createdate         datetime       default getdate(),           --����ʱ��
       primary key(id)
);

--��Ʊ����ά����
create table tbl_invoicecontent
(
       id                 int              identity(1, 1), 
       siteid             int              default 0,          --վ��id
       content            varchar(200)     not null,           --����
       createdate         datetime         default getdate(),    --����ʱ��
       primary key(id)
);

--�ͻ����ù���

--�ʰɱ�ṹ����
create table fawu_anwser                                --�𰸱�      
(
        id                      int   identity(1, 1)    not null,            --������
        qid                     int                     not null,            --������������������
        anwser                  varchar(4000),                               --��
        votenum                 int,                                         --ͶƱ����
        ipaddress               varchar(15),                                 --�ش��ߵ�IP��ַ
        userid                  int,                                         --�ش����û�ID
        username                varchar(300),                                --�ش����û���
        fenshu                  int                     default 0,           --��ȡ����
        cankaoziliao            varchar(4000),                               --�ο�����
        createdate              datetime,                                    --����ʱ��
        PICPATH                 varchar(255),                                --ͼƬ·��
        BETTERANS               int,                                         --�Ƿ�����Ѵ�
        ANW_STATUS              int,                                         --��״̬
        primary key(id)
);

create table fawu_wenti_column                          --�ʰɷ��ඨ��
(
        id                      int   identity(1, 1)    not null,            --�ʰɷ�������
        siteid                  int                     not null,            --վ��ID
        parentid                int                     not null,            --������ID
        orderid                 int                     not null,            --���ID
        cname                   varchar(100),                                --��������
        ename                   varchar(100),                                --Ӣ������
        dirname                 varchar(200),                               
        status                  int                     default 0,
        creater                 varchar(20),
        createdate              datetime,
        primary key(id)
);

create table fawu_vote                                 --�ʰɴ�ͶƱ��
(
        id                      int  identity(1, 1)    not null,             --����ID   
        aid                     int                    not null,             --�����ID
        userid                  int                    not null,             --ͶƱ�û�ID
        primary key(id)
);

create table fawu_wenti                                 --�����
(
        id                      int identity(1, 1)      not null,           --����ID
        columnid                int                     not null,           --����ID
        parcolumid              int,                                        --��һ������ID
        cname                   varchar(100),                               --������������
        question                varchar(1000),                              --��������
        status                  int,                                        --����״̬
        voteflag                int,                                        --�Ƿ��Ǵ�ͶƱ������
        xuanshang               int                     default 0,          --���ͷ���
        answernum               int                     default 0,          --�ش���Ŀ
        source                  varchar(50),                                --
        createdate              datetime,                                   --��������
        ipaddress               varchar(15),                                --�ͻ���IP��ַ
        creater                 varchar(50),                                --������
        province                varchar(50),                                --������ʡ��
        city                    varchar(50),                                --���������ڳ���
        area                    varchar(50),                                --��������������
        picpath                 varchar(255),                               --ͼƬ·��
        emailnotify             int                     default 0,          --�Ƿ��ʼ�֪ͨ  1--֪ͨ  0--��֪ͨ,
        title                   varchar(60),                                --
        email                   varchar(40),                                --�û������ʼ�
        userid                  int,                                        --�û�ID
        username                varchar(20),                                --�û�����
        filepath                varchar(255),                               --�ϴ��ļ�·��
        istop                   int                     default 0,          --�Ƿ��ö�
        anw_status              int,                                        --��״̬
        dianjinum               int,                                        --�����
        user_id_huida           varchar(50),                                --�ش���ID
        primary key(id)
);


--�����������������ϵͳ
--���б�ű�
create table tbl_banks (
        id                      int   identity(1, 1)    not null,      --����
        bankid                  varchar(12)             not null,      --����ID
        siteid                  int                     not null,      --վ��ID
        bankattribname          varchar(12)             not null,      --���м��
        bankname                varchar(40),                           --��������
        createdate              datetime,                              --����ʱ��
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--ҵ��Ա���ݱ�
create table tbl_salesman (
        id                      int identity(1, 1)      not null,      --����
        salesmanid              varchar(12)             not null,      --ҵ��ԱID
        siteid                  int                     not null,      --վ��ID
        chinesename             varchar(12)             not null,      --ҵ��Ա����
        englishname             varchar(12),                           --ҵ��ԱӢ������
        contactphone            varchar(20),                           --ҵ��Ա��ϵ�绰
        mobilephone             varchar(12),                           --ҵ��Ա�ƶ��绰
        contactaddress          varchar(120),                          --ҵ��Ա��ϵ��ַ
        email                   varchar(50),                           --ҵ��Ա�����ʼ���ַ
        createdate              datetime,                              --����ʱ��
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--�ͻ���
create table tbl_customer (
        id                      int  identity(1, 1)     not null,      --����
        customerid              varchar(12)             not null,      --�ͻ�ID
        siteid                  int                     not null,      --վ��ID
        customerattribname      varchar(50)             not null,      --�ͻ����
        customername            varchar(100),                          --�ͻ�����
        invoiceno               varchar(20),                           --��Ʊ���
        owner                   varchar(12),                           --������
        rocid                   varchar(18),                           --���֤����
        contactman1             varchar(18),                           --��ϵ��1
        contactman2             varchar(18),                           --��ϵ��2
        contactphone1           varchar(20),                           --��ϵ��1�绰
        contactphone2           varchar(20),                           --��ϵ��2�绰
        fax                     varchar(20),                           --�������
        salesmanid              int,                                   --ҵ������
        customeraddress         varchar(120),                          --��˾��ַ
        deliveryaddress         varchar(120),                          --�ͻ���ַ
        invoiceaddress          varchar(120),                          --��Ʊ��ַ
        paydays                 int,                                   --��������
        creditline              decimal(32,2),                         --���ö��
        creditbalance           decimal(32,2),                         --�������
        lastdeliverydate        datetime,                              --�������
        createdate              datetime,                              --����ʱ��
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--�����̱�
create table tbl_supplier (
        id                      int identity(1, 1)      not null,      --����
        supplierid              varchar(12)             not null,      --������ID
        siteid                  int                     not null,      --վ��ID
        supplierattribname      varchar(50)             not null,      --�����̼��
        suppliername            varchar(100),                          --����������
        invoiceno               varchar(20),                           --��Ʊ���
        owner                   varchar(12),                           --������
        rocid                   varchar(18),                           --���֤����
        phone1                  varchar(18),                           --��ϵ�绰1
        phone2                  varchar(18),                           --��ϵ�绰2
        fax                     varchar(20),                           --�������
        contactname1            varchar(20),                           --��ϵ��1
        contactname2            varchar(20),                           --��ϵ��2
        companyaddress          varchar(120),                          --��˾��ַ
        deliveryaddress         varchar(120),                          --������ַ
        invoiceaddress          varchar(120),                          --��Ʊ��ַ
        lastpurchasedate        datetime,                              --�������
        paydays                 int      default 0,                    --��������
        prepaid                 int      default 0,                    --�ݸ���
        createdate              datetime,                              --����ʱ��
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--��Ʒ��
create table tbl_jxc_product (
        id                      int  identity(1, 1)     not null,      --����
        productid               varchar(12)             not null,      --��ƷID
        siteid                  int                     not null,      --վ��ID
        productname             varchar(120)            not null,      --��Ʒ����
        safestock               int,                                   --��ȫ����
        lastpurchasedate        datetime,                              --�����������
        lastdeliverydate        datetime,                              --�����������
        quantity                int         default 0,                 --�����
        createdate              datetime,                              --����ʱ��
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--����������
create table tbl_purchasemaster (
        id                      int  identity(1, 1)   not null,        --����
        purchaseid              varchar(12)           not null,        --��������
        siteid                  int                   not null,        --վ��ID
        purchasedate            datetime              not null,        --��������
        supplierid              varchar(12)           not null,        --�����̱��
        purchaseproperty        smallint              default 0,       --���������� 0-����  1-�˻�
        invoiceno               varchar(10),                           --��Ʊ���
        subtotal                decimal(36,2)         default 0,       --�ϼƽ��
        valueaddtax             decimal(36,2)          default 0,       --Ӫҵ˰
        amount                  decimal(36,2)          default 0,       --�ܼƽ��
        accountpayable          decimal(36,2)          default 0,       --Ӧ�����
        paid                    decimal(36,2)          default 0,       --�Ѹ��ʿ�
        limitdate               datetime,                              --�����ֹ����
        createdate              datetime,                              --����ʱ��
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--��������ϸ��
create table tbl_purchasedetail (
        id                      int  identity(1, 1)    not null,       --����
        purchaseid              varchar(12)            not null,       --��������
        productid               varchar(12)            not null,       --��ƷID
        purchasequantity        int                  default 0,        --��������
        purchaseunitprice       decimal(18,2),                         --��������
        purchaseamount          decimal(36,2)        default 0,        --�����ܼ�
        createdate              datetime,                              --����ʱ��
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--����������
create table tbl_deliverymaster (
        id                      int  identity(1, 1)     not null,      --����
        deliveryid              varchar(12)             not null,      --��������
        siteid                  int                     not null,      --վ��ID
        deliverydate            datetime                not null,      --��������
        customerid              varchar(12)             not null,      --�ͻ����
        salesmanid              varchar(12)             not null,      --ҵ��Ա���
        deliveryaddress         varchar(120),                          --�ͻ���ַ
        invoiceno               varchar(10),                           --��Ʊ���
        customerorderno         varchar(15),                           --��������
        subtotal                decimal(36,2)           default 0,     --�ϼƽ��
        valueaddtax             decimal(36,2)           default 0,     --Ӫҵ˰
        amount                  decimal(36,2)           default 0,     --�ܼƽ��
        accountreceivable       decimal(36,2)           default 0,     --Ӧ�ս��
        received                decimal(36,2)           default 0,     --���ս��
        limitdate               datetime,                              --�����ֹ����
        createdate              datetime,                              --����ʱ��
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--��������ϸ��
create table tbl_deliverydetail (
        id                      int identity(1, 1)      not null,      --����
        deliveryid              varchar(12)             not null,      --��������
        productid               varchar(12)             not null,      --��ƷID
        salesquantity           int                     default 0,     --��������
        salesunitprice          decimal(18,2),                         --��������
        salesamount             decimal(36,2)           default 0,     --�����ܼ�
        createdate              datetime,                              --����ʱ��
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);


--���䶯����
create table tbl_changemaster (
        id                      int identity(1, 1)      not null,      --����
        changeid                varchar(12)             not null,      --��������
        siteid                  int                     not null,      --վ��ID
        changedate              datetime,                              --�䶯����
        description             varchar(255),                          --�䶯˵��
        createdate              datetime,                              --����ʱ��        
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--���䶯��ϸ
create table tbl_changedetail (
        id                      int  identity(1, 1)     not null,      --����
        changeid                varchar(12)             not null,      --��������
        productid               varchar(12)             not null,      --��ƷID
        changequantity          int                     default 0,     --�䶯����
        changeamount            decimal(36,2)           default 0,     --�䶯���
        createdate              datetime,                              --����ʱ��        
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--�����������
create table tbl_accountpayablemaster (
        id                      int  identity(1, 1)    not null,      --����
        paymentid               varchar(12)            not null,      --��������
        siteid                  int                    not null,      --վ��ID
        paydate                 datetime               not null,      --֧������
        supplierid              varchar(12)            not null,      --��Ӧ��ID
        paycash                 decimal(36,2)          default 0,     --�ֽ�
        paycheck                decimal(36,2)          default 0,     --֧Ʊ
        noteno                  varchar(10),                          --֧Ʊ����
        duedate                 datetime,                             --������
        bankid                  varchar(12)            not null,      --��Ʊ����
        accountno               varchar(20),                          --�ʺ�
        discount                decimal(36,2)          default 0,     --�ۿ�
        remittance              decimal(36,2)          default 0,     --����
        prepaid                 decimal(36,2)          default 0,     --�ݸ���
        others                  decimal(36,2)          default 0,     --����
        payamount               decimal(36,2)          default 0,     --������
        totalbalance            decimal(36,2)          default 0,     --ʵ����
        createdate              datetime,                             --����ʱ��        
        updatedate              datetime,                             --�޸�ʱ��
        primary key (id)
);

--���������ϸ��
create table tbl_accountpayabledetail (
        id                      int  identity(1, 1)     not null,      --����
        paymentid               varchar(12)             not null,      --�����
        purchaseid              varchar(12)             not null,      --����������
        balance                 decimal(36,2)           default 0,     --���γ������
        createdate              datetime,                              --����ʱ��        
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);

--�տ��������
create table tbl_accountreceivablemaster (
        id                      int  identity(1, 1)     not null,     --����
        siteid                  int                     not null,     --վ��ID
        receiveid               varchar(12)             not null,     --�տ��
        receivedate             datetime,                             --�տ�����
        customerid              varchar(12)             not null,     --�ͻ����
        receivecash             decimal(36,2)           default 0,    --�ֽ�
        receivecheck            decimal(36,2)           default 0,    --֧Ʊ
        noteno                  varchar(10),                          --֧Ʊ����
        duedate                 datetime,                             --������
        bankid                  varchar(12)             not null,     --��Ʊ����
        accountno               varchar(20),                          --�ʺ�
        discount                decimal(36,2)           default 0,    --�ۿ�
        remittance              decimal(36,2)           default 0,    --����
        advance                 decimal(36,2)           default 0,    --���տ�
        others                  decimal(36,2)           default 0,    --����
        receiveamount           decimal(36,2)           default 0,    --�տ���
        totalbalance            decimal(36,2)           default 0,    --ʵ����
        createdate              datetime,                             --����ʱ��        
        updatedate              datetime,                             --�޸�ʱ��
        primary key (id)
);


--�տ������ϸ��
create table tbl_accountreceivabledetail (
        id                      int  identity(1, 1)     not null,      --����
        receiveid               varchar(12)             not null,      --�տ��
        deliveryid              varchar(12)             not null,      --����������
        balance                 decimal(36,2)           default 0,     --���γ������
        createdate              datetime,                              --����ʱ��        
        updatedate              datetime,                              --�޸�ʱ��
        primary key (id)
);


--��Ϣ�ɼ���
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
	classid               int                      not null,                    --CMS����ĿID
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



--�������ߵ�������ҵ�û�ע���
create table tbl_rsbt_org(
	id			int        identity(1, 1)       not null,
	guid			varchar(36)			not null,
	userid			varchar(36)			not null,
	siteid			int				not null,
	password		varchar(36)			not null,
	org_gode		varchar(9) default(999999999)	not null,		--��֯��������
	org_name		varchar(80)			not null,		--��֯��������
	org_area_code		varchar(8)			not null,		--��������
	org_sys_code		varchar(8)			not null,		--ϵͳ/��ҵ����
	org_type		varchar(3)			not null,		--��λ����
	org_link_person		varchar(40),						--��λ��ϵ��
	org_person_id		varchar(40),						--��ϵ�����֤����
	org_sup_code		varchar(9),						--�ϼ���֯����
	org_addr		varchar(80),						--��֯������ַ
	org_post		varchar(6),						--��֯�����ʱ�
	org_phone		varchar(40),						--��ϵ�绰
	org_mob_phone		varchar(40),						--�ֻ�����
	org_fax			varchar(40),						--��֯��������
	org_bank		varchar(80),						--��������
	org_account_name	varchar(80),						--�˻�����
	org_account		varchar(40),						--�����˺�
	org_hostility		int,						--��̨��λ����
	org_web_site		varchar(80),						--��ַ
	org_mail		varchar(80),						--��������
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

--ҵ��������Ϣ���ݿ��ļ���Ϣ��
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
     createdate                datetime,                                      --��������
     lastupdate                datetime,                                      --�޸�����
     editor                    varchar(50),                              --���ݴ�����
     publishflag               int          not null 
);

create table tbl_accessmdb
(
    userid        varchar(50)                  not null,           --�ϴ���ID
    filename      varchar(100)                 not null,           --�ϴ��ļ�ԭʼ����
    tflag         tinyint default(0),                              --�Ƿ�ת�뵽���ݿ�      0-û��ת��       1-�Ѿ�ת�룬ȫ���ɹ� 2-���룬������
    createdate    datetime,
    lastupdate    datetime,
    primary key(filename)
);


--���ߵ�������߱�
create table tbl_ganrao(
	id			int          identity(1, 1)	not null,		--����
	susongdanwei		varchar(50)			not null,		--���ϵ�λ
	lianxiren1		varchar(20),						--��ϵ��1
	lianxiren2		varchar(20),						--��ϵ��2
	phone			varchar(20),						--��ϵ�绰
	taizhanweizhi		varchar(200),						--̨վλ��
	shebeixinghao		varchar(100),						--�豸�ͺ�
	gongzuofangshi		varchar(50),						--������ʽ
	ganraoleixing		varchar(50),						--��������
	zhizhaobianhao		varchar(50),						--ִ�ձ��
	ganraopinlv		varchar(50),						--����Ƶ��
	ganraoquyu		varchar(50),						--��������
	ganraoshiduan		varchar(50),						--���θ���ʱ�估����ʱ��
	ganraoshijian		varchar(50),						--����Ƶ�ȼ�ÿ�γ���ʱ��
	tezhengmiaoshu		varchar(500),						--�����ź���������
	shenshuyaoqiu		varchar(500),						--����Ҫ��
	primary key(id)
);

--�������ߵ�̨�걨��
create table tbl_zhuduijiangji(
	djjid			int identity(1, 1)		not null,		--����
	shetaidanwei		varchar(50),						--��̨��λ
	suoshuxitong		varchar(50),						--����ϵͳ
	address			varchar(100),						--��ַ
	lianxiren		varchar(20),						--��ϵ��
	phone			varchar(20),						--�绰
	chuanzhen		varchar(20),						--����
	shiyongdiqu		varchar(50),						--ʹ�õ���
	diantaishuliang		varchar(20),						--��̨����
	diantaileibie		varchar(50),						--��̨���	150MHz,400MHz,900MHz�����ĵ�ַ
	shetailiyou		varchar(500),						--��̨����
	lingdaoyijian		varchar(500),						--�ϼ����ܵ�λ�쵼���
	guanlijuyijian		varchar(500),						--���������ߵ��������
	xitongbianhao		varchar(100),						--ϵͳ���
	zhizhaobianhao		varchar(100),						--ִ�ձ��
	primary key(djjid)
);

create table tbl_diantaiziliao(
	id			int identity(1, 1)		not null,		--����
	dtzlid			int				not null,
	shebeixinghao		varchar(100),						--�豸�ͺ�
	chuchanghaoma		varchar(100),						--��������
	beizhu			varchar(100),						--��ע
	CONSTRAINT fk_dtzlID FOREIGN KEY (dtzlid) REFERENCES  tbl_zhuduijiangji(djjid) ON DELETE CASCADE,
	primary key(id)
);

--ҵ������
create table tbl_BusinessInfo
(
    codeinfo                   varchar(100),
    cname                      varchar(100),
    siteid                     int            default 0           not null,
    primary key(codeinfo)
);

--ԤԼ�����
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

--�û�ԤԼ��Ϣ��
create table tbl_userappointment
(
    id                        int  identity(1, 1)                 not null,
    userid                    varchar(100)                        not null,
    appointmentdate           varchar(30)                         not null,
    appointmentcode           varchar(6)                          not null,
    siteid                    int             default 0           not null,
    primary key(id)
);

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



--����Ϊ��������
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
	id		varchar(500)		not null,         --����ID
	lyhid		int,                                   --��¼�û�ID
	username	varchar(500),                             --��¼�û���
	logintime	datetime,                                     --����¼ʱ��
	loginnum        int,                                   --��¼����
	loginstatus     int,                                   --��¼״̬
	lastactivetime  datetime,                                     --�û����ʱ��
	ipaddress	varchar(500)                              --��¼�ߵ�IP��ַ
);

create table bbs_friend(
	userid		varchar(500),
	lyhid		int,
	friend		varchar(500)
);


--��Ʊ��Ϣ��  ������
create table en_invoice
(
        id               int   identity(1, 1)     not null,             --����
        siteid           int                      not null,             --վ��id
        userid           int                      not null,             --�û�id
        orderid          bigint                   not null,             --�û�������
        invoicetype      int                      default(0),           --��Ʊ����(0-��ͨ��Ʊ1-��ֵ˰��Ʊ)
        titleflag        int                      default(0),           --������/��λ0-����1-��λ��
        title            varchar(100),                                  --��Ʊ̧ͷ
        companyname      varchar(200),                                  --��λ����
        identification   varchar(200),                                  --��˰��ʶ���
        registeraddress  varchar(200),                                  --ע���ַ
        phone            varchar(15),                                   --ע��绰
        postaddress      varchar(200),                                  --�ʼĵ�ַ
        postname         varchar(20),                                   --����������
        postcode         varchar(6),                                    --��������
        bankname         varchar(200),                                  --��������
        bankaccount      varchar(100),                                  --�����ʺ�
        totalprice       money,                                         --��Ʊ�ܼ�ֵ
        createdate       datetime                 default getdate(),    --����ʱ��
        primary key(id)
);

--��Ʊ����ά����
create table en_invoicecontent
(
        id              int   identity(1, 1)     not null,             --����,ÿ����Ʊ���ID
        invoiceid       int                      not null,             --��ƱID
        siteid          int                      not null,             --վ��id
        [content]       varchar(200)             not null,             --����
        price           money,                                         --��Ʒ����
        pnum            int                      default(0),           --��Ʒ����
        createdate      datetime                 default getdate(),    --����ʱ��
	CONSTRAINT      fk_contentid FOREIGN KEY (invoiceid) REFERENCES  en_invoice(id) ON DELETE CASCADE,
        primary key(id)
);
