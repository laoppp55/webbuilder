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
	sign                    varchar2(32),                               --���µ�����ǩ�������������Ϊ���±���+ID������ǩ����ץȡ����Ϊ���µ�URL����ǩ��
        primary key (id)
);

create table TBL_ARTICLE_EXTENDATTR
(
        id                            int  auto_increment     not null,      ##������չ��������
        articleid                     int                     not null,      ##����ID
        ename                         varchar(50)             not null,      ##��չ��������
        type                          smallint                not null,      ##��չ��������
        textvalue                     text,                                  ##�洢�ı�ֵ
        stringvalue                   varchar(4000),                         ##�洢�ַ���
        numericvalue                  int,                                   ##�洢����
        floatvalue                    decimal(9,2),                          ##�洢������
  	CONSTRAINT fk_articleID FOREIGN KEY (articleid) REFERENCES  tbl_article(ID) ON DELETE CASCADE,
        primary key (id)
);


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
        createdate              timestamp,                            ##�û���Ϣ����ʱ��
	lastupdate              timestamp,                            ##�û���Ϣ����޸�ʱ��
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
        primary key (id)
);


CREATE TABLE tbl_orders(                                                    ##������
         id                       bigint   auto_increment     not null,     ##����
	 SITEID                   int,				            ##վ��id
	 ORDERID                  bigint   not null unique,                 ##������
	 initorderid              int      default 0,                       ##ԭʼ������(��=ԭ���ţ��ǲ�=0)
	 USERID                   int,	     			            ##�û�id
	 NAME                     varchar(50),		                    ##�ջ�������
	 SEX                      int,				            ##�Ա�
	 ADDRESS                  varchar(1000),	                    ##��ַ
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
	 bill_to_address          varchar(2000),	                    ##��Ʊ��ַ
	 notes                    varchar(4000),	                    ##��Ʊ��Ϣ
	 flag                     smallint   default 0     not null,
	 payflag                  smallint   default 0     not null,        ##�Ƿ��Ѿ�֧��   0-δ֧��  1-�Ѿ�֧��
	 payway                   int,		                            ##���ʽ
	 paydate                  timestamp,		                    ##����ʱ��
	 linktime                 varchar(50),			
	 usescores                int        default 0,	                    ##�û�ʹ�û���
	 orderscores              int        default 0,                     ##���������Ļ��� 
	 CREATEDATE               timestamp,		                    ##����ʱ��
	 lastupdate               timestamp,		                    ##�������ʱ��
	 jylsh                    varchar(200),                             ##��֧�����ط��ص�֧�����ط��Ľ�����ˮ��
	 paycompno                int,                                      ##����֧�����ر��
	 zfmemberid               varchar(200),                             ##֧�����Ļ�ԱID
	 r2type                   varchar(10),                              ##֧�����ķ������ͣ�1������ض��� 2��������Ե�
	 payresult                varchar(50),                              ##֧��������ɹ�/ʧ��/����
	 primary key(id) 
);

##������ϸ��Ϣ��
CREATE TABLE tbl_orders_detail( 
	 ID                       bigint auto_increment        not null,    ##����
	 ORDERID                  bigint                       not null,    ##������
	 supplierid               int      default 0        not null,       ##������id(��Ӧÿ��������siteid)
	 suppliername             varchar(100),                             ##����������
	 PRODUCTID                int,				            ##��Ʒid
	 ORDERNUM                 int,				            ##��Ʒ����
         unit                     varchar(10),                              ##��Ʒ������λ
	 SALEPRICE                double(9,2),		                    ##��Ʒ�ۼ�
	 WEIGHT                   int,				            ##��Ʒ����
	 CREATEDATE               timestamp,			            ##����ʱ��
	 productname              varchar(200),	                            ##��Ʒ����
	 productcode              varchar(200),                             ##��Ʒ����
	 type                     varchar(100),	                            ##��Ʒ���ͣ���չ���ԣ�
	 cardid                   int      default 0        not null, 	    ##ʹ�ù����id 
	 userinstarttime          timestamp,                                ##�û�����Ŀ�ʼ����
	 userinendtime            timestamp,                                ##�û�����Ľ�������
	 servicestarttime         timestamp,                                ##���ݹ��������ķ�����ɹ���ʼ����
	 serviceendtime           timestamp,                                ##���ݹ��������ķ�����ɹ���������
	 subscribe                smallint,                                 ##����������
         subscribenum             int,                                      ##���񶩹�������
	 CONSTRAINT fk_orderID FOREIGN KEY (ORDERID) REFERENCES  TBL_ORDERS(ORDERID) ON DELETE CASCADE,	 	 
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
