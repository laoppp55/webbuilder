--�ɹ�������������
create table tbl_joballocator(
	id                       int               not null,                    --����ID
	customer_id	         int               not null,                    --�ͻ�ID
	company_id		 int               not null,                    --���øù���Ĺ�˾ID     
	serviced_companyid       int               not null,                    --������Ĺ�˾ID
	buy_companyid            int               not null,                    --ִ�вɹ�����Ĺ�˾ID
	wuzicode		 varchar2(100),                                 --���ʱ���
	wuziname                 varchar2(100),                                 --���ʻ����ʷ�������
	createdate               timestamp,                                     --����ʱ��
	creator		         varchar2(100),                                 --������
	updatedate               timestamp,                                     --����޸�ʱ�� 
	updater                  varchar2(100),                                 --����޸���
	remark                   varchar2(1000),                                --ע��
	primary key (id)
);

--�����������ж�������ʱ������ĳЩ������ִ�м��вɹ�
create table tbl_joballocator_rules(
	id                       int               not null,                    --����ID
	allocatorid	         int               not null,                    --������������ID
	rulename		 varchar2(100)     not null,                    --�������ƣ���Ӧ�ɹ���������ݱ��ֶ���
	rulevalue                varchar2(1000)    not null,                    --�����ַ��������������"��"��"��"�Ĺ�ϵ������ϣ����� ���ڲɹ� AND ������>50��
	createdate               timestamp,                                     --����ʱ��
	creator		         varchar2(100),                                 --������
	updatedate               timestamp,                                     --����޸�ʱ�� 
	updater                  varchar2(100),                                 --����޸���
	remark                   varchar2(1000),                                --ע��
	primary key (id)
);

--������������ñ������õļ��вɹ�����Ҳ������ҵ�Բɻ�����ĳЩ�������Բ�
create table tbl_permit_selfbuy(
	id                       int               not null,                    --����ID
	customer_id	         int               not null,                    --�ͻ�ID
	company_id		 int               not null,                    --���øù���Ĺ�˾ID     
	wuzicode		 varchar2(100),                                 --���ʱ���
	wuziname                 varchar2(100),                                 --�������ƻ����ʷ�������
	createdate               timestamp,                                     --����ʱ��
	creator		         varchar2(100),                                 --������
	updatedate               timestamp,                                     --����޸�ʱ�� 
	updater                  varchar2(100),                                 --����޸���
	remark                   varchar2(1000),                                --ע��
	primary key (id)
);

--������ҵ�����Բɵļ������ʵ���������
create table tbl_permit_selfbuy_rules(
	id                       int               not null,                    --����ID
	permit_id	         int               not null,                    --�ͻ�ID
	rulename		 varchar2(100)     not null,                    --�������ƣ���Ӧ�ɹ���������ݱ��ֶ���
	rulevalue                varchar2(1000)    not null,                    --�����ַ��������������"��"��"��"�Ĺ�ϵ������ϣ����������<50��
	createdate               timestamp,                                     --����ʱ��
	creator		         varchar2(100),                                 --������
	updatedate               timestamp,                                     --����޸�ʱ�� 
	updater                  varchar2(100),                                 --����޸���
	remark                   varchar2(1000),                                --ע��
	primary key (id)
);

create table tbl_dict
(
  id           NUMBER(19) not null,                  --����
  typecode     VARCHAR2(255 CHAR),                   --�ֵ������
  typename     VARCHAR2(255 CHAR),                   --�ֵ�������
  detailcode   VARCHAR2(255 CHAR),                   --��ϸ�ֵ����
  detailname   VARCHAR2(255 CHAR),                   --��ϸ�ֵ�����
  validaty     NUMBER(1),                            --�Ƿ���Ч  1--��Ч  0-��Ч
  primary key (id)
)

  
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(1, '����״̬',101,1,'����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(2, '����״̬', 101,    2,'�޸�',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(3, '����״̬', 101,    3,'ɾ��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(4, '����״̬', 101,    4, 'ͣ��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(5, '����״̬', 101,    5, '�ᱨ',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(6, '����״̬', 101,    6, '����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(7, '����״̬', 101,    7, '����ͨ��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(8, '����״̬', 101,    8, '����δͨ��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(9, '�ɹ���ʽ', 102,    1, 'ѯ�ȼ�',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(10, '�ɹ���ʽ', 102,    2, '����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(11, '�ɹ���ʽ', 102,    3,  '̸��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(12, '�ɹ���ʽ', 102,    4,   '�б�',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(13,'�ɹ���ʽ', 102,    5,   '����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(14,'�ɹ�����', 103,    1,   '���вɹ�',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(15,'�ɹ�����', 103,    2,   '��ҵ�Բ�',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(16, '������Դ',104,   1,  '��Ŀ',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(17,'������Դ',104,   2,  '����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(18,'������Դ',104,   3,  'ά��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(19,'������Դ',104,   4,  '����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(20,'������Դ',105,   1,  '�ֹ�¼��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(21,'������Դ',105,   2,  '����ƻ��ᱨ',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(22,'������Դ',105,   3,  '�ļ�����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(23,'������Դ',105,   4,  'ERPϵͳ',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(24,'�ɹ���������',106,   1,'����ά��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(25,'�ɹ���������',106,   2,'�ɹ�����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(26,'�ɹ���������',106,   3,'ѯ����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(27,'�ɹ���������',106,   4,'������',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(28,'�ɹ���������',106,   5,'�ɹ��������',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(29,'�ɹ���������',106,   6,'��ͬά��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(30,'�ɹ���������',106,   7,'��ͬ����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(31,'�ɹ���������',106,   8,'��ͬ����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(32,'ѰԴ����',107,   1,'���Э��ѰԴ',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(33,'ѰԴ����',107,   2,'ֱ�Ӳɹ�ѰԴ',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(34,'����',108,   1,'�����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(35,'ʱ��',109,   2,'��2��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(36,'ʱ��',109,   3,'��1��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(37,'ʱ��',109,   4,'0ʱ��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(38,'ʱ��',109,   5,'��1��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(39,'ʱ��',109,   6,'��2��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(40,'ʱ��',109,   7,'��3��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(41,'ʱ��',109,   8,'��4��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(42,'ʱ��',109,   9,'��5��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(43,'ʱ��',109,   10,'��6��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(44,'ʱ��',109,   11,'��7��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(45,'ʱ��',109,   12,'��8��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(46,'ʱ��',109,   13,'��9��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(47,'ʱ��',109,   14,'��10��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(48,'ʱ��',109,   15,'��11��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(49,'ʱ��',109,   16,'��12��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(50,'ʱ��',109,   17,'��11��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(51,'ʱ��',109,   18,'��10��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(52,'ʱ��',109,   19,'��9��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(53,'ʱ��',109,   20,'��8��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(54,'ʱ��',109,   21,'��7��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(55,'ʱ��',109,   22,'��6��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(56,'ʱ��',109,   23,'��5��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(57,'ʱ��',109,   24,'��4��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(58, '��������רҵ',110,1,'����ԭ��',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(59, '��������רҵ',110,2,'����',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(60, '��������רҵ',110,3,'�豸',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(61,'ί�з�ʽ',111,1,'ί����ҵ�Բ�',1);
insert into tbl_dict(id,typecode,typename,detailcode,detailname,validaty) values(62,'ί�з�ʽ',111,2,'ί���ⵥλ',1);



--��������ģ��
--�ɹ�������
--��һ��ҵ�񻷽�����Ĵ����Ϊ״̬�������ᱨ�����桢����ͨ��������δͨ��
--�ڲ�ͬҵ�񻷽�����Ĵ����Ϊ���ȣ��������󶩵���ѯԴ������ѯ���顢���ۣ�ѯԴ���
create table tbl_prodorder
(
  ID                 NUMBER(19) not null,            --����ID
  ORDERCODE          VARCHAR2(40),                   --�������
  OLDCODE            VARCHAR2(40),                   --ԭ����                       ������ϵͳ���Ĳɹ�������
  PLANNO             VARCHAR2(40),                   --�ɹ������                   ��¼�ⲿϵͳ������ҵ�񵥾ݺ�
  USERID             NUMBER(19) not null,            --��ԱID
  OPERANAME          VARCHAR2(20),                   --ҵ��Ա��ʵ����
  company            NUMBER(19) not null,            --�ɹ�������ƹ�˾ID
  SUBTIME            timestamp,                      --�ύʱ��
  PLANTIME           timestamp default sysdate,      --�ɹ������ᱨʱ��
  SEQTIME            timestamp,                      --����ʱ��                     ��������                
  STATUS             NUMBER(19),                     --״̬ID                       �ᱨ   δ���� ��״ֵ̬������� 
  STOCKTYPE          NUMBER(19),                     --�ɹ���ʽID                   ѯ�ȼ�  ����  ����̸��  �б�  ����      
  PROGRESS           NUMBER(19) default 0,           --���󶩵�ҵ�����ID           ����  ѰԴ����  ѯ���� ������ �ɹ�������� ��ͬ ��ͬ����  ��ͬ����         
  ATTRTYPE           NUMBER(19),                     --�ɹ�����ID                   1-���вɹ�   2-��ҵ���вɹ�                     
  REQUIRESOURCE      NUMBER(19) default 0,           --������Դ                     ��Ŀ ���� ά�� ����
  DATASOURCE         NUMBER(19) default 0,           --������Դ                     1-�ֹ�¼�� 2-����ƻ�  3-�ļ����� 4-ERP
  TIMEZONE           NUMBER(19) default 0,           --ʱ��                         ȡ���붨���ʱ��ֵ
  PRODSPECIALTY      NUMBER(19) default 0,           --��������רҵID               ȡ���붨���ֵ
  FRMTYPE            NUMBER default 0,               --ѰԴ����                     1���Э��ѰԴ��0�ǿ��Э��ѰԴ������ѡ������󶩵��Զ�ȷ��
  PROJECTID          NUMBER,                         --��ӦѯԴ����ID
  ENQUIREBILLID      NUMBER,                         --��Ӧѯ����ID
  WTTYPE             NUMBER(19),                     --ί�к͹��ʲɹ�ID               
  CREATEDEPT         NUMBER(19) default 0,           --���Ʋ���ID
  createcomp         NUMBER(19) default 0,           --���Ʋɹ�����Ĺ�˾ID
  CREATEDATE         timestamp,                      --����ʱ��
  creator            NUMBER(19) default 0,           --������
  UPDATETIME         timestamp,                      --����޸�ʱ��
  updatecomp         NUMBER(19) default 0,           --����޸Ĳɹ�����Ĺ�˾ID
  UPDATEDEPT         NUMBER(19) default 0,           --����޸Ĳ���ID
  UPDATEUSER         NUMBER(19) default 0,           --����޸��û�ID
  WTWAY              NUMBER(19) default 0,           --ί�з�ʽ                      1-ί��ֱ������ 2-ί����ҵ�Բ�  3-ί���ⵥλ 0-��ί�� 
  WTCODE             VARCHAR2(20),                   --ί�е����ID
  WTCOMP             NUMBER(19) default 0,           --ί�е�λID
  ORDER_REMARK       VARCHAR2(200),                  --���󶩵���ע(����)
  primary key(id)
);
--�Ѹ������������ʶ���ͬ��������ȫ���ŵ�������

--�ɹ���Ʒ�ඩ����Ŀ��
create table tbl_proddetails
(
  ID                 NUMBER(19) not null,            --����ID
  mian_id            NUMBER(19),                     --��������ID,�ɹ����������Ӧ�ɹ�����ID���ɹ����������Ӧ�ɹ�����ID��ѯ���������Ӧѯ����ID
  main_code          VARCHAR2(40),                   --����������,�ɹ����������Ӧ�ɹ�������룬�ɹ����������Ӧ�ɹ��������룬ѯ���������Ӧѯ�������
  WZCLASS            NUMBER(19),                     --��Ʒ��ID
  WZCLASSCODE        VARCHAR2(16),                   --������Ʒ�����
  buycomp            NUMBER(19) not null,            --ִ�и����ʲɹ���˾ID
  contractcomp       NUMBER(19) not null,            --ִ�и����ʺ�ͬǩ����˾ID��һ��ִͬ�и����ʲɹ���˾ID
  ISPROCESSCONTROL   NUMBER(1) default 0,            --�Ƿ���̿���                 0-��  1-��
  ISIMPORT           NUMBER(1) default 0,            --�Ƿ����                     0-��   1-��
  WZNAME             VARCHAR2(200),                  --��������
  WZDESC             VARCHAR2(1000),                 --��������
  WZNUM              NUMBER,                         --���ʲɹ�����
  WZUNIT             VARCHAR2(50),                   --���ʼ�����λ
  ESTIMATECURRENCY   NUMBER(19) default 0,           --�������                     ȡ��������ֵ     
  ESTIMATEAMOUNT     NUMBER(14,2) default 0,         --������
  CURRENCYRATE       NUMBER(10,4) default 0,         --����ұ�λ�һ���             ȡ��������ʱ����Ļ���
  BASECURRENCY       NUMBER(19) default 0,           --��λ��                       ȡ��λ�ҵĶ���ֵ
  BASECURRENCYAMOUNT NUMBER(14,2) default 0,         --��λ�Ҹ�����    
  RMBRATE            NUMBER(10,4) default 0,         --���������һ���
  RMBAMOUNT          NUMBER(14,2) default 0,         --����Ҹ�����
  deliveryaddr       VARCHAR2(1000),                 --������ַ  
  BeginDeliverydate  timestamp,                      --��ʼ������
  ENDDeliveryDATE    timestamp,                      --��ֹ������            
  VALIDITY           NUMBER(1) default 1,            --�Ƿ���Ч 1-��Ч  0-��Ч
  unit_remark        VARCHAR2(20),                   --ʹ�ü�����λ(����)
  quality            NUMBER,                         --��������(����)             1-A�� 2-B��  3-C��
  primary key(id)
);

--�ɹ��������ݿ�---������ϸ��
create table tbl_ordercont
(
  ID                 NUMBER(19) not null,            --����
  ORDERCODE          VARCHAR2(40),                   --�������
  ITEMCODE           NUMBER(8),                      --������Ŀ���
  ITEMVAL            VARCHAR2(100),                  --������Ŀֵ
  primary key(id)
);

--ѯԴ����
create table tbl_project
(
  ID                 NUMBER not null,                --����
  PROJCODE           VARCHAR2(40)  not null,         --ѰԴ������� ���=��˾����+�ɹ�����+¼�뷽ʽ(1������ 2������ )+����+��ˮ��+����
  USERID             NUMBER(19) not null,            --��ԱID           
  PROJTOPIC          VARCHAR2(100),                  --ѰԴ��������
  OPERANAME          VARCHAR2(20),                   --ҵ��Ա��ʵ����
  SUBTIME            timestamp default sysdate,      --�����ύʱ��
  STATUS             NUMBER,                         --״̬����ID            �������ֵ��t_dict
  TYPE               NUMBER default 0,               --����                  1-����  2-�޸�  3-����
  STOCKTYPE          NUMBER,                         --�ɹ���ʽID            ѯ�ȼ�  ����  ����̸��  �б�  ����            
  ATTRTYPE           NUMBER(19) default 0,           --�ɹ�����ID            ���вɹ�   ��ҵ�Բ�
  FRMTYPE            NUMBER default 0,               --ѰԴ����              1-���Э��ѰԴ��0-�ǿ��Э��ѰԴ������ѡ������󶩵��Զ�ȷ��
  STOCKLEVEL         NUMBER(19) default 0,           --���ɼ���      ��װ�����вɹ�����SEG���вɹ�
  suggestion         VARCHAR2(2000),                 --�ƻ�Ա�ɹ����
  ENQUIREBILLID      VARCHAR2(40),                   --��Ӧѯ����(�б깫�� �ɹ���)���ID
  ATTACHMENT         number(1),                      --���޸���               1-��  0-��              
  buycomp            NUMBER(19) not null,            --ִ�вɹ��Ĺ�˾ID
  PROCESS            NUMBER,                         --����                                     
  PROJECTRANGE       NUMBER default 0,               --��Ӧ�̷�Χ    1-����  2-����
  TRANSORG           NUMBER(19),                     --������֯��             1-��Ӧ����֯ 2-ѯ�۵�λ��֯ 3-������λ��֯ 4-ʹ�õ�λ��֯
  ESTIMATECURRENCY   NUMBER(19),                     --������֣����û���λ�����û�ȡ��  
  ESTIMATEAMOUNT     NUMBER(14,2) default 0,         --������ �������û���λ�Ҽ������󶩵��ĸ�����ɵ�����                               
  RMBAMOUNT          NUMBER(14,2) default 0,         --����Ҹ�����  �����󶩵�����Ҹ����ܺͣ�
  quoteCURRENCY      NUMBER,                         --���۱���ID      
  quotetype          NUMBER,                         --�۸�����ID         ������  �밶��
  quoteremark        VARCHAR2(2000),                 --����Ҫ��     
  otherfee           NUMBER,                         --���ӷѼ��������üƷѷ�ʽID     ��������
  BISSAX             NUMBER(1),                      --�Ƿ�˰        0-��   1-��˰   ��ΪC_IFTAX
  taxrate            VARCHAR2(40),                   --˰��      
  TIMEZONE           NUMBER(19) default 0,           --ʱ��ID
  CREATETIME         timestamp,                      --����ʱ�䣺ϵͳʱ�䣬ǰ̨��ʾ
  createcomp         NUMBER(19) default 0,           --���Ʋɹ�����Ĺ�˾ID
  CREATEDEPT         NUMBER,                         --���Ʋ���ID���û����ţ�ǰ̨��ʾ
  creator            NUMBER(19) default 0,           --������
  UPDATETIME         timestamp,                      --����޸�ʱ�䣺ϵͳʱ�䣬ǰ̨��ʾ
  UPDATECOMP         NUMBER(19),                     --����޸ĵ�λID���û���λ��ǰ̨��ʾ
  UPDATEDEPT         NUMBER(19),                     --����޸Ĳ���ID���û����ţ�ǰ̨��ʾ
  UPDATEUSER         NUMBER(19),                     --����޸��û�ID���û���¼��������ʾ
  remark             VARCHAR2(2000),                 --��ע
  PAYWAY             NUMBER,                         --����֧����ʽ
  PAYPROCESS         varchar2(100),                  --֧������
  STANDARDWORD       NUMBER,                         --ó������      (����׼����վ) 
  STARTPORT          NUMBER,                         --���˸�ID      (����4.0���Ѿ���)
  ENDPORT            NUMBER,                         --������ID      (����4.0���Ѿ���)
  AUTHORIZE          NUMBER(1) default 0,            --�Ƿ�ί��      ֻ�ǽ��ڵĿ��Դ��������ί��
  MOBILE             VARCHAR2(19),                   --�ֻ�
  TEL                VARCHAR2(19),                   --�绰
  FAX                VARCHAR2(19),                   --����
  EMAIL              VARCHAR2(19),                   --�����ʼ�
  ZIP                VARCHAR2(19),                   --��������
  ADDR               VARCHAR2(200),                  --��ϸ��ַ
  CONTACTOR          VARCHAR2(40),                   --��ϵ��
  IMPORTFLAG         NUMBER,                         --���ڱ�ʶ
  frameworkcode      VARCHAR2(40),                   --���Э���� 
  buyer              VARCHAR2(40),                   --�ɹ�Ա          ��������û��ָ��Ϊ�գ������쵼ָ�����д��
  CONSTRAINT         wyprojectcode  UNIQUE(PROJCODE),--PROJCODEΪΨһԼ���ֶ�
  primary key(id)
);


create table tbl_project_inqu(
  ID                 number not null,                --����ID
  projectid          number not null,                --�ɹ��ƻ�ID
  projectcode        VARCHAR2(40)  not null,         --�ɹ��ƻ����
  start_time         timestamp ,                     --�ƻ�ѯ�۷���ʱ�䣨��Ҫ����������ͨ��/���������ģ����ƻ�����ѯ��ʱ���Զ�����ѯ���飩
  close_time         timestamp ,                     --�ƻ����۽�ֹʱ��
  inqu_clause        varchar2(4000),                 --ѯ��Ҫ������
  other_demand       varchar2(4000) ,                --����Ҫ��˵��
  part_whether       number(1) default 1,            --�Ƿ������嵥���ֱ��ۣ�1���ǣ�0����
  quantity_whether   number(1) default 1,            --�Ƿ�����������������ۣ�1���ǣ�0����
  xjmultiquoteflag   NUMBER default 0,               --ѯ���Ƿ�������ױ���   0-������   1-������ױ���
  tech_whether       number(1) default 0,            --�Ƿ�������                ��1���ǣ�0���񣺼�������ʱ����Ӧ�̱��۱����ϴ�����������
  xjTECHENQUIRE      NUMBER,                         --�������󻷽ڵ�λ��           1-���۶��ۺͼ�������ͬʱ����  2-�Ƚ��м������󣬼�������ͨ���Ĺ�Ӧ�̲ſ��Ա��ۡ�
  xjTECHENQUSTART    timestamp,                      --����ѯ�ۿ�ʼ����
  xjTECHENQUEND      timestamp,                      --����ѯ�۽�������
  ensure_mode        number(1) default 1,            --���귽ʽ��1���ɹ�ר�Ҿ��ꣻ2���ɹ���Ŀ����꣩
  Review_mode        number(1) default 1,            --��Ŀ����귽ʽ��1��ͶƱ��2����֣�
  createtime         timestamp default sysdate,      --����ʱ��
  updatetime         timestamp,                      --���ά��ʱ��
  username           number(8),                      --����������
  userid             number not null,                --�����û���
  remark             varchar2(2000),                 --ά��˵��
  primary key(id)
);
alter table tbl_project_inqu add unique(projectcode); 

create table tbl_project_auction(
  ID                 number not null,                --����ID
  projectid          number not null,                --�ɹ��ƻ�ID
  projectcode        VARCHAR2(40)  not null,         --�ɹ��ƻ����
  start_time         timestamp ,                     --�ƻ����ۿ�ʼʱ�䣨��Ҫ����������ͨ��/���������ģ����ƻ�����ѯ��ʱ���Զ�����ѯ���飩
  close_time         timestamp ,                     --�ƻ����۽�ֹʱ��
  jjQUOTETYPE        NUMBER default 0,               --�Ƿ�Ԥ����             1-��   0-�� 
  jjbasepriceflag    NUMBER default 0,               --�Ƿ����þ��ۻ�׼��     1-��   0-��
  createtime         timestamp default sysdate,      --����ʱ��
  updatetime         timestamp,                      --���ά��ʱ��
  username           number(8),                      --����������
  userid             number not null,                --�����û���
  remark             varchar2(2000),                 --ά��˵��
  primary key(id)
);
alter table tbl_project_auction add unique(projectcode); 

--ѰԴ������Ӧ�Ĳɹ����ʱ��������ϱ�������
create table tbl_wzinproject
(
  ID                   NUMBER not null,              --����
  PROJECTID            NUMBER,                       --ѰԴ����ID
  PROJCODE             VARCHAR2(100),                --ѰԴ�������
  ORDERITEM            NUMBER(19),                   --����ID
  ORDERCODE            VARCHAR2(40),                 --�������
  proddetailid         NUMBER,                       --����������ϸ�������ID
  WZCLASS              NUMBER(19),                   --��Ʒ��ID
  WZCLASSCODE          VARCHAR2(16),                 --������Ʒ�����
  CREATETIME           timestamp,                    --����ʱ��
  primary key(id)
);

--ѰԴ�����빩Ӧ�̹�ϵ��
create table tbl_projectsupp
(
  ID                   NUMBER not null,              --����
  PROJCODE             VARCHAR2(40),                 --ѰԴ���
  PROJECTID            NUMBER default 0 not null,    --ѰԴid
  SUPPID               NUMBER default 0 not null,    --��Ӧ�̱�����ID
  SUPPCODE             NUMBER default 0 not null,    --��Ӧ�̱���
  SUPPCODENAME         VARCHAR2(200),                --��Ӧ������
  QUOTETYPE            NUMBER,                       --�Ƿ񱨼� 0--û�б��� 1--�б��ۣ�������
  COMPTYPE             NUMBER,                       --�Ƿ���뾺�� 0--������ 1--���루������
  CREATETIME           timestamp,                    --����ʱ��
  primary key(id)
);

--7���ɹ��ƻ�����
create table tbl_projectfile(
  ID                   NUMBER not null,              --����
  PROJCODE             VARCHAR2(40),                 --ѰԴ���
  PROJECTID            NUMBER default 0 not null,    --ѰԴid
  attachedfile         varchar2(40) ,                --�ɹ��ƻ�����GUID
  suppvisible          NUMBER default 0 not null,    --�����Ƿ�Թ�Ӧ�̿ɼ�    0-���Թ�Ӧ�̿ɼ�   1-�Թ�Ӧ�̿ɼ�
  createtime           timestamp default sysdate,    --ά��ʱ��
  updatetime           timestamp,                    --���ά��ʱ��
  username             number(8),                    --�����˱��
  userid               varchar2(40),                 --�����û���
  remark               varchar2(2000)                --ά��˵��
);
alter table tbl_projectfile  add unique(PROJCODE,attachedfile);


--ѰԴ����֮ѯ�ȼ�   
--ѯ�����
-- Create table
create table T_ENQUIREBILL
(
  c_id               NUMBER not null,
  c_enqucode         VARCHAR2(40),
  c_enqutopic        VARCHAR2(200),
  c_subtime          DATE,
  c_sendtime         DATE,
  c_operaname        VARCHAR2(20),
  c_addr             VARCHAR2(100),
  c_zip              VARCHAR2(10),
  c_tel              VARCHAR2(100),
  c_fax              VARCHAR2(100),
  c_email            VARCHAR2(100),
  c_status           NUMBER,
  c_taxcode          NUMBER(19),
  c_attrtype         NUMBER(19) default 0,
  c_stocktype        NUMBER,
  c_projcode         VARCHAR2(40),
  c_type             NUMBER(1) default 0,
  c_project          NUMBER(19),
  c_mode             NUMBER,
  c_process          NUMBER,
  c_prequtotype      NUMBER(1) default 0,
  c_quotecurrency    NUMBER,
  c_pricetype        NUMBER,
  c_chargetype       NUMBER,
  c_iftax            NUMBER(1) default 0,
  c_quotedemand      VARCHAR2(2000),
  c_mobile           VARCHAR2(19),
  c_starttime        DATE,
  c_endtime          DATE,
  c_posttime         DATE,
  c_pay              NUMBER,
  c_payprocess       VARCHAR2(200),
  c_stocklevel       NUMBER(19) default 0,
  c_projectrange     NUMBER default 0,
  c_transorg         NUMBER(19),
  c_remark           VARCHAR2(2000),
  c_createdate       DATE,
  c_createuser       NUMBER(19),
  c_createdept       NUMBER(19),
  c_createorg        NUMBER(19),
  c_createcomp       NUMBER(19),
  c_lastupdate       DATE,
  c_lastupuser       NUMBER(19),
  c_lastupdept       NUMBER(19),
  c_lastuporg        NUMBER(19),
  c_lastupcomp       NUMBER(19),
  c_timezone         NUMBER(19) default 0,
  c_fileflag         NUMBER default 0,
  c_importflag       NUMBER default 0,
  c_standardword     VARCHAR2(40),
  c_startport        NUMBER,
  c_endport          NUMBER,
  c_payment          NUMBER(19),
  c_payterm          NUMBER(19),
  c_customer         NUMBER default 10,
  c_transportmode    NUMBER,
  username           VARCHAR2(100),
  c_pricevalidtime   DATE,
  c_contractsigntype NUMBER(1)
)
tablespace DATASPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 4M
    next 8K
    minextents 1
    maxextents unlimited
  )
nologging;
-- Add comments to the table 
comment on table T_ENQUIREBILL
  is 'ѯ����';
-- Add comments to the columns 
comment on column T_ENQUIREBILL.c_id
  is '����';
comment on column T_ENQUIREBILL.c_enqucode
  is 'ѯ������';
comment on column T_ENQUIREBILL.c_enqutopic
  is '����';
comment on column T_ENQUIREBILL.c_subtime
  is '�ύʱ��';
comment on column T_ENQUIREBILL.c_sendtime
  is '����ʱ��                   ������Ӧ�̵�ʱ��';
comment on column T_ENQUIREBILL.c_operaname
  is 'ѯ������ϵ�ˣ�Ĭ����ѯ���������';
comment on column T_ENQUIREBILL.c_addr
  is '��ַ';
comment on column T_ENQUIREBILL.c_zip
  is '�ʱ�';
comment on column T_ENQUIREBILL.c_tel
  is '�绰';
comment on column T_ENQUIREBILL.c_fax
  is '����';
comment on column T_ENQUIREBILL.c_email
  is '�����ʼ�';
comment on column T_ENQUIREBILL.c_status
  is '״̬             ��������ʾ�Ȳ���״̬';
comment on column T_ENQUIREBILL.c_taxcode
  is '˰��';
comment on column T_ENQUIREBILL.c_attrtype
  is '�ɹ�����ID  ��ӦT_code';
comment on column T_ENQUIREBILL.c_stocktype
  is '�ɹ���ʽID';
comment on column T_ENQUIREBILL.c_projcode
  is 'ѰԴ�������';
comment on column T_ENQUIREBILL.c_type
  is '�����Ƿ���Ч     0-��Ч  1-��Ч';
comment on column T_ENQUIREBILL.c_project
  is 'ѰԴ����id';
comment on column T_ENQUIREBILL.c_mode
  is '���̷�ʽ         ��t-code����ֵ��';
comment on column T_ENQUIREBILL.c_process
  is '�ɹ����� ';
comment on column T_ENQUIREBILL.c_prequtotype
  is '�Ƿ���ҪԤ����  0 �� 1��';
comment on column T_ENQUIREBILL.c_quotecurrency
  is '���۱���         ����ѰԴ�������룬�ɵ�����';
comment on column T_ENQUIREBILL.c_pricetype
  is '�۸�����         ����ѰԴ�������룬�ɵ�����';
comment on column T_ENQUIREBILL.c_chargetype
  is '���ӷѼ��������üƷѷ�ʽID     ����ѰԴ�������룬�ɵ�����  ';
comment on column T_ENQUIREBILL.c_iftax
  is '�Ƿ�˰        0-��   1-��˰ ';
comment on column T_ENQUIREBILL.c_quotedemand
  is '����Ҫ��       ����ѰԴ�������룬�ɵ�����';
comment on column T_ENQUIREBILL.c_mobile
  is '�ֻ���';
comment on column T_ENQUIREBILL.c_starttime
  is 'ѯ�ۿ�ʼʱ��';
comment on column T_ENQUIREBILL.c_endtime
  is 'ѯ�۽�ֹʱ��  ';
comment on column T_ENQUIREBILL.c_posttime
  is 'ѯ�۽�ʾʱ��';
comment on column T_ENQUIREBILL.c_pay
  is '����֧����ʽ   ����ѰԴ�������룬�ɵ�����';
comment on column T_ENQUIREBILL.c_payprocess
  is '֧������       ����ѰԴ�������룬�ɵ�����';
comment on column T_ENQUIREBILL.c_stocklevel
  is '���ɼ���       ����ѰԴ�������룩';
comment on column T_ENQUIREBILL.c_projectrange
  is 'ѰԴ��Χ       ����ѰԴ�������룩';
comment on column T_ENQUIREBILL.c_transorg
  is '������֯��1��Ӧ����֯/2ѯ�۵�λ��֯/3������λ��֯/4ʹ�õ�λ��֯     ����ѰԴ�������룩';
comment on column T_ENQUIREBILL.c_remark
  is '��ע ';
comment on column T_ENQUIREBILL.c_createdate
  is '����ʱ��';
comment on column T_ENQUIREBILL.c_createuser
  is '�����û�(��ӦT_User)';
comment on column T_ENQUIREBILL.c_createdept
  is '���Ʋ���(��ӦT_Department)';
comment on column T_ENQUIREBILL.c_createorg
  is '������֯�ܹ�(��ӦT_Organization)';
comment on column T_ENQUIREBILL.c_createcomp
  is '���Ƶ�λ(��ӦT_Company) ��������';
comment on column T_ENQUIREBILL.c_lastupdate
  is '����޸�ʱ��';
comment on column T_ENQUIREBILL.c_lastupuser
  is '����޸��û�(��ӦT_User)    ';
comment on column T_ENQUIREBILL.c_lastupdept
  is '����޸Ĳ���(��ӦT_Department)';
comment on column T_ENQUIREBILL.c_lastuporg
  is '����޸���֯�ܹ�(��ӦT_Organization) ';
comment on column T_ENQUIREBILL.c_lastupcomp
  is '����޸ĵ�λ(��ӦT_Company)';
comment on column T_ENQUIREBILL.c_timezone
  is 'ʱ��ID';
comment on column T_ENQUIREBILL.c_fileflag
  is '���޸�����ʶ   0 ��  1��';
comment on column T_ENQUIREBILL.c_importflag
  is '���ڱ�ʶ        0-��   1-��  ';
comment on column T_ENQUIREBILL.c_standardword
  is 'ó������       ����ѰԴ�������룬�ɵ�����';
comment on column T_ENQUIREBILL.c_startport
  is '���˸�ID';
comment on column T_ENQUIREBILL.c_endport
  is '������ID';
comment on column T_ENQUIREBILL.c_payment
  is '���㷽ʽT_code';
comment on column T_ENQUIREBILL.c_payterm
  is '��������';
comment on column T_ENQUIREBILL.c_customer
  is '�ɹ�����';
comment on column T_ENQUIREBILL.c_transportmode
  is '���䷽ʽ��codeId';
comment on column T_ENQUIREBILL.c_pricevalidtime
  is '������Чʱ��';
comment on column T_ENQUIREBILL.c_contractsigntype
  is '��ͬǩ����0ͳ̸ͳǩ��1ͳ̸��ǩ';
-- Create/Recreate indexes 



--ѯ���������־���淶��ͳһ����־����ϵͳ�У�   
-- Create table
create table T_ENQUIREWORK
(
  c_id         NUMBER not null,
  c_enqucode   NUMBER(19),
  c_status     NUMBER(19),
  c_type       NUMBER(1),
  c_user       NUMBER(19),
  c_updatetime DATE,
  c_endtimeorg DATE,
  c_endtimenew DATE,
  c_remark     VARCHAR2(1000)
)
tablespace DATASPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 256K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column T_ENQUIREWORK.c_id
  is '����';
comment on column T_ENQUIREWORK.c_enqucode
  is 'ѯ������';
comment on column T_ENQUIREWORK.c_status
  is '״̬';
comment on column T_ENQUIREWORK.c_type
  is '����1--�µ� 2--�޸� 3--���� 5--ȡ��ѯ��6--��ʱ����';
comment on column T_ENQUIREWORK.c_user
  is '�û�';
comment on column T_ENQUIREWORK.c_updatetime
  is '����ʱ��';
comment on column T_ENQUIREWORK.c_endtimeorg
  is 'ԭ���۽�ֹʱ��';
comment on column T_ENQUIREWORK.c_endtimenew
  is '�±��۽�ֹʱ��';
comment on column T_ENQUIREWORK.c_remark
  is '��ʱ����˵��';


--�ɹ��������
create table T_QUOTEBILL
(
  C_ID            NUMBER(19) not null,                 --����ID      
  C_STRQUOTECODE  VARCHAR2(40),                        --��������
  C_STRQUOTETOPIC VARCHAR2(60),                        --���������       (��Դ��ѯ���飬������Ӧ���޸ģ�����4.0���)
  C_STROPERANAME  VARCHAR2(20),                        --ҵ��Ա�������ӵ�¼�û����룬�ɵ�����
  C_STRADDR       VARCHAR2(100),                       --��ַ���ӵ�¼�û����룬�ɵ�����
  C_STRZIP        VARCHAR2(10),                        --�ʱࣨ�ӵ�¼�û����룬�ɵ�����
  C_STRTEL        VARCHAR2(100),                       --�绰���ӵ�¼�û����룬�ɵ�����
  C_STRFAX        VARCHAR2(100),                       --���棨�ӵ�¼�û����룬�ɵ�����
  C_STREMAIL      VARCHAR2(100),                       --�������䣨�ӵ�¼�û����룬�ɵ�����
  C_STRMEASUNIT   VARCHAR2(20),                        --������λ�����ã�
  C_QUOTECURRENCY NUMBER,                              --���۱��� ������ѯ����Ҫ�󱨼۱��֣�������
  C_INTAMOUNTSUM  NUMBER(17,4) default 0,              --���۱����ܽ��
  C_BASECURRENCY       NUMBER(19) default 0,           --��Ӧ�̱�λ��ID  ��������
  C_CURRENCYRATE       NUMBER(10,4) default 0,         --��Ӧ�̱�λ���뱨�۱��ֻ��� ��������
  C_BASECURRENCYAMOUNT NUMBER(14,2) default 0,         --��Ӧ�̱�λ���ܽ�������
  C_INTAMOUNTQTY  NUMBER(17,4) default 0,              --�����ϼ�     �����ã�
  C_CREATEDATE    DATE,                                --����ʱ�䣺ϵͳʱ�䣨������
  C_DASUBTIME     DATE,                                --�����ύʱ��
  C_DAOPENTIME    DATE,                                --���۴�ʱ�� �����ã�
  C_DAENDTIME     DATE,                                --������Ч��
  C_STREXPLAIN    VARCHAR2(400),                       --����˵��      
  C_STRTECHACCES  VARCHAR2(100),                       --�Ƿ��и���    ��Ϊ���޸�����ʶ ��ѰԴ��������һ��
  C_INTAFFIRM     NUMBER(1) default 0,                 --ȷ�ϵ���־ 0--δȷ�� 1--��ȷ�� 3--���±���ȷ�ϵ�  �����ã�
  C_STRBUSIACCES  VARCHAR2(100),                       --���񸽼���    �����ã�����Ϊͳһ�ĸ�����¼��
  C_INTEXAM       NUMBER(1) default 0,                 --�ɹ�������־ 0--δ���� 1--������   �����ã�
  C_INTSHOW       NUMBER(1) default 0,                 --��ʾ��־ 0--�� 1--��   �����ã�
  C_INTATTRTYPE   NUMBER(1) default 0,                 --����   ������Ϊ�ɹ�����
  C_INTSTOCKTYPE  NUMBER(19),                          --�ɹ�����ID    ������Ϊ�ɹ���ʽ
  C_STRCLASSCODE  NUMBER(19) default 0,                --������Ʒ�����   �����ã�
  C_INTSTATUS     NUMBER(19),                          --������״̬����    ��ΪC_STATUS
  C_SUPPLIER      VARCHAR2(40),                        --��Ӧ��  �����ã�
  C_STRPROJCODE   NUMBER(19),                          --��ӦѰԴ�������
  C_ORG           NUMBER(19),                          --������˾ID   �����ã�
  C_ENQUCODE      NUMBER(19),                          --ѯ����ID
  C_QSOLUTION     VARCHAR2(10),                        --���۽��   �����ã�
  C_SOLTIME       DATE,                                --���۽������ʱ��   �����ã�
  C_QUOTETYPE     NUMBER(1),                           --�Ƿ񱨼� 0--û�б��� 1--�б���  �����ã�
  C_SCHEDULE      VARCHAR2(40),                        --�ɹ�����   (�ֶ�����ΪC_PROCESS) 
  C_ENQUTOPIC     VARCHAR2(40),                        --ѯ��������   �����ã�
  C_USER          VARCHAR2(20) not null,               --��Ա��¼��ID  
  C_CREATEORG     NUMBER(19),                          --���Ƶ�λID����Ӧ��  (����)
  C_CREATEDATE    DATE,                                --����ʱ�䣺ϵͳʱ��   (����)
  C_DEPT          NUMBER,                              --���Ʋ���ID����Ӧ�̲���  (����)
  C_UPDATETIME    DATE,                                --����޸�ʱ�䣺ϵͳʱ��  (����)
  C_UPDATEORG     NUMBER(19),                          --����޸ĵ�λID����Ӧ�̵�λ  (����)
  C_UPDATEDEPT    NUMBER(19),                          --����޸Ĳ���ID����Ӧ�̲���  (����)
  C_UPDATEUSER    NUMBER(19),                          --����޸��û�ID����Ӧ�̵�¼��  (����)
  C_TIMEZONE      NUMBER(19) default 0,                --ʱ��ID  
  primary key(c_id)  
);

--�ɹ���������ϸ���������ϱ������򣩣����ӱ��ۿ��޸��ֶ����ã�
create table T_QUOTEBILL_PART
(
  C_ID             NUMBER(19) not null,                --����ID
  C_INTPARTCODE    NUMBER(16),                         --���   �����ã�
  C_STRQUOTECODE   NUMBER(19),                         --������ID
  C_STRORDERCODE   VARCHAR2(20),                       --���󶩵����
  C_DASTARTCONTIME DATE,                               --��ʼ������
  C_DAENDCONTIME   DATE,                               --����������
  C_STRCONADDR     VARCHAR2(100),                      --�����ص�
  C_STRCONSIGNEE   VARCHAR2(100),                      --�ջ���λ
  C_STRPRODCODE    VARCHAR2(20),                       --��Ʒ����
  C_STRPRODNAME    VARCHAR2(100),                      --��Ʒ����
  C_STRMEASUNIT    VARCHAR2(20),                       --������λ
  C_INTQTY         NUMBER(12,4),                       --����
  C_QUOTECURRENCY  NUMBER,                             --���۱��� �����뱨���鱨�۱��֣�������
  C_               number,                             --���ۣ�����������˰�������κ���ط��ã�
  C_PRICETYPE      NUMBER,                             --�۸�����   ����������ѯ������룩
  C_INTPRICE       NUMBER(12,4),                       --���ۣ���ѯ��Ҫ��ļ۸����ͱ��ļ۸�
  C_INTAMOUNT      NUMBER(15,4),                       --�ܼ�
  c_TAXCODE        VARCHAR2(6)                         --˰��ID�����û�ȷ�ϣ�
  C_               NUMBER,                             --���ӷѼ��������üƷѷ�ʽID������ѰԴ�������룩  
  C_INTFREE        NUMBER(12,4),                       --���ӷ�
  C_INTOTHER       NUMBER(12,4),                       --��������
  C_INTALL         NUMBER(15,4),                       --�ܼ�
  primary key(c_id)  
);
