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
	roleid			number(2)		not null,
	rolename		varchar2(30)		not null,
	rolecat		varchar2(30),
	roledesc		varchar2(100),
        primary key (roleid)
);

insert into tbl_role values(0,'��ϢԱ','XXY','��������վ¼����Ϣ');
insert into tbl_role values(1,'�����쵼','BML','¼����Ϣ�����');
insert into tbl_role values(2,'�����쵼','ZGL','¼����Ϣ�����');
insert into tbl_role values(3,'ϵͳ����Ա','SM','����ϵͳ����Ա');
insert into tbl_role values(4,'���԰����Ա','LWM','���԰����Ա');
insert into tbl_role values(5,'�������԰����Ա','DLWM','�������԰����Ա');

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
        phone                   varchar2(12),                          --��ϵ�绰        
	sex                     smallint              default(0),      --0��ʾ�� 1��ʾŮ
        country                 varchar2(30),                          --����
        province                varchar2(50),                          --ʡ
        city                    varchar2(50),                          --��
        area                    varchar2(50),                          --����
        jiedao                  varchar2(50),                          --�ֵ�
        shequ                   varchar2(50),                          --����
        postcode                varchar2(6),                           --��������
        birthdate               date,                                  --��������
        myimage                 varchar2(100),                         --ͷ��
        usertype                smallint              default(0),      --�û�����    0---�����û�   1--��ҵ�û�        
        joincompanyid           number,                                --������ID��
        joinid                  number,                                --���������к�
        dflag                   number,                                --վ���Ƿ�ɾ��
        createarticles	        number  default(0),                    --�û�������������
        editarticles		number  default(0),                    --�û��޸���������
        deletearticles	        number  default(0),                    --�û�ɾ����������
        score                   number     default(0),
        createdate              date,
        company                 varchar2(200),
        address                 varchar2(500),
        department              varchar2(50),                          --��������
        emailaccount            varchar2(100),                         --email�ʺ�
        emailpasswd             varchar2(100),                         --email����
        departmentarticlestype  number default(0),                     --������µķ�Χ0-���в���1-������2-ָ������
        departmentarticlesids   varchar2(500),                         --ָ��������µĲ���id        
        primary key (userid)
);

insert into tbl_members (userid,siteid,userpwd,nickname,email,mphone,phone,sex) values('admin',-1,'4346D362F67C89D7AB4E28732A1B1CE8','ϵͳ����Ա','','','',0);

--ϵͳ��ɫ��
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

--��ҵ��������
create table tbl_department (
       id                      number                      not null,
       siteid                  number                      not null,
       cname                   varchar2(100)            not null,
       ename                   varchar2(100),
       telephone               varchar2(12),
       manager                 varchar2(50),                            --���ž���
       vicemanager             varchar2(50),                            --���Ÿ�����
       leader                  varchar2(50),                            --���������쵼
       primary key(id)
);          
       
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
	status               	number(1)   		not null,           --����״̬      0-����   1-δ��
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
	t1                      number                  default(0),         --δ��
	t2                      number                  default(0),         --δ��
	t3                      number                  default(0),         --δ��
	t4                      number                  default(0),         --δ��
	t5                      number                  default(0),         --δ��
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
	id                   	number                  not null,            --����ID ��ĿID
	parentid             	number     		not null,            --����ĿID
	siteid               	number                  not null,            --վ��ID
	columnid                number                  not null,            --��ĿID
	orderid              	number                  not null,            --���ID
	cname                	varchar2(50)        	not null,            --������������
	ename                	varchar2(50)        	not null,            --����Ӣ������
	dirname              	varchar2(200)           not null,            --·����Ϣ
	editor               	varchar2(30)            not null,            --�༭
	extname              	varchar2(10)            not null,            --��չ��
	createdate           	date                    not null,            --��������
	lastupdated          	date			not null,            
        primary key (id)
);

create table tbl_relatedartids                                               --ҳ������ļ���
(
        ID                      number                         not null,     --����ID
	subcolumnid             number                         not null,     --������������ID
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

create table tbl_deleted_article (
        siteid                  int                not null,                 --վ��ID
	columnid                int                not null,                 --��ĿID
        articleid               int                not null,                 --����ID
	sitename                varchar2(100),                               --վ������
	acttype                 smallint           default    0              --0��ʾ��ɾ��������   1��ʾ���޸ĵ�����        
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
	xmltype                 varchar2(3000),                        --���·���Ķ���
	userflag                number(1)       default(0),            --��Ŀ�����Ƿ�ֻ��ע���û��ɿ�  0--�����û� 1--ע���û�
	userLevel               number          default(0),            --�û�������µļ���
	shareflag               number        default(0),            --��Ŀ�Ƿ��Ǻ�������վ�������Ŀ
	articlecount            number             default(0),            --����Ŀ������������
	titlepic                varchar2(10),                          --����ͼƬ��С
	vtitlepic               varchar2(10),                          --������ͼƬ��С
	sourcepic               varchar2(10),                          --��ԴͼƬ��С
	authorpic               varchar2(10),                          --����ͼƬ��С
	contentpic              varchar2(10),                          --����ͼƬ��С
	specialpic              varchar2(10),                          --������ЧͼƬ��С
	productpic              varchar2(10),                          --��Ʒ��ͼƬ��С
	productsmallpic         varchar2(10),                          --��ƷСͼƬ��С
        ts_pic                  varchar2(10),
        s_pic                   varchar2(10),
        ms_pic                  varchar2(10),
        m_pic                   varchar2(10),
        ml_pic                  varchar2(10),
        l_pic                   varchar2(10),
        tl_pic                  varchar2(10),
        primary key (id)
);

insert into tbl_column values(0,0,-1,0,'��ҳ','home','/','admin','html',sysdate,sysdate,0,0,0,0,0,0,'','',0,0,0,0,0,0,0,'',0,0,0,0,'','','','','','','','','','','','','','','');


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


--��Ŀģ��ͱ�ǵ���ر�
CREATE TABLE TBL_TemplateMark (
	ID                   number    NOT NULL,                   --���
	siteid               number    not null,                   --վ��id
        tid                  number    not null,                   --ģ��id
        mid                  number    not null,                   --��ģ���ϵı��id
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
	pusharticle	        number(1)       default(0) 	    not null,     --�����ļ�����
	movearticle	        number(1)       default(0) 	    not null,     --�ƶ��ļ�����
        sitetype                number             default(0),                       --�����͹���վ�������,�����������վ��������վ�ȵ�
	sitepic                 varchar2(200),                                    --վ����ҳͼƬ
	sitevalid               number        default(0),                       --0-������վ 1-��ͨ��վ 2-������վ
	sitelogo                varchar2(200),                                    --վ��LOGO
	banner                  varchar2(200),                                    --վ����BANNER
	navigator               number             default(0),                       --վ�㵼����ʽ
	samsiteid               number             default(0),                       --����վ��ID
	scores                  number          default(0),                       --���ֺͻ��Ҷһ�����
	sharetemplatenum        number             default(0),                       --��������վ���ģ���׺�
	managesharesite         number        default(0),                       --������վ����Ϣ   0-������  1-����
        copyright               varchar2(1000),	                                  --��վ��Ȩ
        titlepic                varchar2(10),                                     --����ͼƬ��С
	vtitlepic               varchar2(10),                                     --������ͼƬ��С
	sourcepic               varchar2(10),                                     --��ԴͼƬ��С
	authorpic               varchar2(10),                                     --����ͼƬ��С
	contentpic              varchar2(10),                                     --����ͼƬ��С
	specialpic              varchar2(10),                                     --������ЧͼƬ��С
	productpic              varchar2(10),                                     --��Ʒ��ͼƬ��С
	productsmallpic         varchar2(10),                                     --��ƷСͼƬ��С
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

create table tbl_navigator (
       id                       number                             not null,
       cname                    varchar2(200)                   not null,     --��������������
       content                  varchar2(3500),                               --������HTML����
       fcontent                 varchar2(3500),                               --��������HTML����
       notes                    varchar2(250),
       createdate               date,                                         --��������
       primary key(id)
);


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

create table tbl_turnpic (                                                   --ͼƬ�ֻ���
        id                  number not null,
        articleid           number not null,
	sortid              int,                                             --ͼƬ��ʾ˳��
	mediaurl            varchar2(500),                                    --��ý���ļ�·��
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


create table tbl_companyinfo(
       ID                                        number,                            --����
       SITEID                                    number,                            --����վ��
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
       COMPANYLATITUDE                           number(38,15),                     --��˾���ڵصľ���
       COMPANYLONGITUDE                          number(38,15),                     --��˾���ڵص�γ��
       COMPANYPIC                                VARCHAR2(200),                     --��˾ͼƬ
       PUBLISHFLAG                               number,                            --�������
       status                                    smallint default(0),               --״̬  0--���ⷢ��   1-�����ⷢ��
       createdate                                date,                              --��������
       updatedate                                date,                              --�޸�����
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


--������ע���
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
create table tbl_userinfo
(
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

--��������
CREATE TABLE TBL_COMMENT ( 
	ID          NUMBER,                                 --����
	userid      varchar2(20),                           --��¼�û�ID,���û�е�¼����¼IP
	NAME        VARCHAR2(100),                          --����������
	LINK        VARCHAR2(100),                          --��������ϵ��ʽ
	CONTENT     VARCHAR2(2000),                         --��������
	IP          VARCHAR2(20),                           --������������IP��ַ
	ABOUT       NUMBER,                                 --����ID
	auditflag   int       default 0,                    --0 ���ͨ��������ʾ 1-���δͨ�� ����ʾ 
	CREATEDATE  DATE,                                   --��������
	SITEID      NUMBER,                                 --վ��ID
	CONSTRAINT pk_commentid PRIMARY KEY (ID)	
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

CREATE TABLE TBL_ORDERS  ( --������
	 SITEID        			number,				            --վ��id
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
	 r2TrxId                        varchar2(80),                               --֧�����ķ�����ˮ��
	 zfmemberid                     varchar2(30),                               --֧�����Ļ�ԱID
	 r2type                         smallint    default(0),                     --֧�����ķ������ͣ�1������ض��� 2��������Ե�
         payresult                      varchar2(20),                               --֧��������ɹ�/ʧ��/����
	 VALID         			number      default(0),		
	 STATUS        			number                     NOT NULL ,	    --����״̬
	 BILLINGWAY    			number,				            --֧����ʽ
	 CITY          			varchar2(50),			            --����
	 RECEIVE       			number,				
	 NOUSE         			number      default(1),	                    --�����Ƿ���Ч 1-��Ч 0-��Ч
	 PROVINCE      			varchar2(50),			            --ʡ��
	 zone          			varchar2(50),			            --����
	 sendway       			number,				            --���ͷ�ʽ
	 payway        			number,		                            --���ʽ
	 commissioncharge               number(9,2),		                    --�ۿ�
	 insurance   			number(9,2),		                    --���շ�
	 need_invoice                   number        default(0),	            --�Ƿ���Ҫ��Ʊ
	 bill_to_address                varchar2(2000),	                            --��Ʊ��ַ
	 notes                          varchar2(4000),	                            --��Ʊ��Ϣ
	 flag                           number(1)     default(0),
	 payflag                        number(1)     default(0),                   --�Ƿ��Ѿ�֧��
	 linktime                       varchar2(50),			
	 usescores                      number        default(0),	            --�û�ʹ�û���
	 orderscores                    number        default(0),                   --���������Ļ��� 
	 usecard                        number        default(0),                   --ʹ�ù���ȯ���   ������
	 paydate                        date,                                       --����֧��ʱ��
	 CREATEDATE    			date,				            --����ʱ��
	 primary key(orderid) 
);

CREATE TABLE TBL_ORDERS_DETAIL  ( -- ������ϸ��Ϣ��
	 ID                             number              NOT NULL,               --����
	 ORDERID                        number(38)          NOT NULL,               --������
	 supplierid                     number              default(0)   not null,  --������id(��Ӧÿ��������siteid)
	 suppliername                   varchar2(100),                              --����������
	 PRODUCTID                      number,				            --��Ʒid
	 ORDERNUM                       number,				            --��Ʒ����
	 SALEPRICE                      number(9,2),		                    --��Ʒ�ۼ�
	 WEIGHT                         number,				            --��Ʒ����
	 CREATEDATE                     date,				            --����ʱ��
	 productname                    varchar2(200),	                            --��Ʒ����
	 type                           varchar2(100),	                            --��Ʒ���ͣ���չ���ԣ�
	 cardid                         number             default(0) NOT NULL,     --ʹ�ù����id 
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

create table tbl_paywayfee --֧����ʽ�˷�
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

--�ͻ���ַ
create table tbl_addressinfo(
        id		    number                   not null,       --����
        userid		    number                   not null,       --�û�id
        name                varchar(200),          --�ջ�������
        provinces	    varchar(50),					 --ʡ��
        city		    varchar(50),						--����
        zone                varchar(50),            --����
        mobile              varchar(50),            --�ƶ��绰
        phone               varchar(50),            --�̶��绰
        zip		    varchar(50),            --�ʱ�
        address             varchar(200),           --��ϸ��ַ
	createdate          date     default sysdate not null,--����ʱ��
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

--��Ʊ����ά����
create table tbl_invoicecontent
(
       id                 number,
       siteid             number            default 0,          --վ��id
       content            varchar2(200)     not null,           --����
       createdate         date              default sysdate,    --����ʱ��
       primary key(id)
);
CREATE SEQUENCE tbl_invoicecontent_id START WITH 1 increment by 1 cache 20; 
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
  siteid            int,
  starturlid        int,                                             --SP_BASIC_ATTRIBUTES����ID
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
  BASICID    int,                                                    --SP_BASIC_ATTRIBUTES����ID
  CLASSID    int,                                                    --���ݱ������ĿID
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
	CONSTRAINT pk_commentid PRIMARY KEY (ID)	
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

--�����̱������̹���
create table tbl_supplier (
        id                      number                     not null,      --����
        supplierid              varchar2(12)            not null,      --������ID
        siteid                  number                     not null,      --վ��ID
        supplierattribname      varchar2(50)            not null,      --�����̼��
        suppliername            varchar2(100),                         --����������
        invoiceno               varchar2(20),                          --��Ʊ���
        owner                   varchar2(12),                          --������
        rocid                   varchar2(18),                          --���֤����
        phone1                  varchar2(18),                          --��ϵ�绰1
        phone2                  varchar2(18),                          --��ϵ�绰2
        fax                     varchar2(20),                          --�������
        contactname1            varchar2(20),                          --��ϵ��1
        contactname2            varchar2(20),                          --��ϵ��2
        companyaddress          varchar2(120),                         --��˾��ַ
        deliveryaddress         varchar2(120),                         --������ַ
        invoiceaddress          varchar2(120),                         --��Ʊ��ַ
        lastpurchasedate        date,                                  --�������
        paydays                 number      default(0),                   --��������
        prepaid                 number      default(0),                   --�ݸ���
        createdate              date,                                  --����ʱ��
        updatedate              date,                                  --�޸�ʱ��
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