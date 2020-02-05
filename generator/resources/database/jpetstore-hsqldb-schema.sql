http://blog.csdn.net/chenhaiyang_ok/article/details/7965510   主从数据库读写分离
http://blog.csdn.net/liu_liu213/article/details/7268318
http://blog.csdn.net/wjssbp/article/details/6347448
http://mybatis.github.io/mybatis-3/zh/configuration.html


-- drop index productCat;
-- drop index productName;
-- drop index itemProd;

-- drop table lineitem;
-- drop table orderstatus;
-- drop table orders;
-- drop table bannerdata;
-- drop table profile;
-- drop table signon;
-- drop table inventory;
-- drop table item;
-- drop table product;
-- drop table account;
-- drop table category;
-- drop table supplier;
-- drop table sequence;

create table tbl_column (
	id                   	number                  not null,
	siteid               	number                     not null,
	parentid             	number     		not null,
	orderid              	number                  not null,
	cname                	varchar2(50)        	not null,
	ename                	varchar2(50)        	not null,
	editor               	varchar2(30)            not null,
	createdate           	date                    not null,
	lastupdated          	date			not null,
	primary key(id)
);

insert into tbl_column values(0,0,-1,0,'北京潮虹伟业科技有限公司','chwy','admin',sysdate,sysdate);


create table tbl_company (
	id                   	number                  not null,
	comname                	varchar2(100)        	not null,
	comshortname            varchar2(50),
	ename                	varchar2(50)        	not null,
	editor               	varchar2(30)            not null,
	createdate           	date                    not null,
	lastupdated          	date			not null,
	primary key(id)
);

create table supplier (
    suppid         int not null,
    name           varchar2(80) null,
    status         varchar2(2) not null,
    addr1          varchar2(80) null,
    addr2          varchar2(80) null,
    city           varchar2(80) null,
    state          varchar2(80) null,
    zip            varchar2(5) null,
    phone          varchar2(80) null,
    constraint pk_supplier primary key (suppid)
);

create table signon (
    username       varchar2(25) not null,
    password       varchar2(25)  not null,
    constraint pk_signon primary key (username)
);

create table account (
    userid          varchar2(80) not null,
    email           varchar2(80) not null,
    firstname       varchar2(80) not null,
    lastname        varchar2(80) not null,
    status          varchar2(2)  null,
    addr1           varchar2(80) not null,
    addr2           varchar2(40) null,
    city            varchar2(80) not  null,
    state           varchar2(80) not null,
    zip             varchar2(20) not null,
    country         varchar2(20) not null,
    phone           varchar2(80) not null,
    constraint pk_account primary key (userid)
);

create table profile (
    userid           varchar2(80) not null,
    langpref         varchar2(80) not null,
    favcategory      varchar2(30),
    mylistopt        int,
    banneropt        int,
    constraint pk_profile primary key (userid)
);

create table bannerdata (
    favcategory      varchar2(80) not null,
    bannername       varchar2(255)  null,
    constraint pk_bannerdata primary key (favcategory)
);

create table orders (
      orderid         int not null,
      userid          varchar2(80) not null,
      orderdate       date not null,
      shipaddr1       varchar2(80) not null,
      shipaddr2       varchar2(80) null,
      shipcity        varchar2(80) not null,
      shipstate       varchar2(80) not null,
      shipzip         varchar2(20) not null,
      shipcountry     varchar2(20) not null,
      billaddr1       varchar2(80) not null,
      billaddr2       varchar2(80)  null,
      billcity        varchar2(80) not null,
      billstate       varchar2(80) not null,
      billzip         varchar2(20) not null,
      billcountry     varchar2(20) not null,
      courier         varchar2(80) not null,
      totalprice      number(10,2) not null,
      billtofirstname varchar2(80) not null,
      billtolastname  varchar2(80) not null,
      shiptofirstname varchar2(80) not null,
      shiptolastname  varchar2(80) not null,
      creditcard      varchar2(80) not null,
      exprdate        varchar2(7) not null,
      cardtype        varchar2(80) not null,
      locale          varchar2(80) not null,
      constraint pk_orders primary key (orderid)
);

create table orderstatus (
      orderid         int not null,
      linenum         int not null,
      timestamp       date not null,
      status          varchar2(2) not null,
      constraint pk_orderstatus primary key (orderid, linenum)
);

create table lineitem (
      orderid         int not null,
      linenum         int not null,
      itemid          varchar2(10) not null,
      quantity        int not null,
      unitprice       number(10,2) not null,
      constraint pk_lineitem primary key (orderid, linenum)
);

create table category (
	catid         varchar2(10) not null,
	name          varchar2(80) null,
	descn         varchar2(255) null,
	constraint pk_category primary key (catid)
);

create table product (
    productid         varchar2(10) not null,
    category          varchar2(10) not null,
    name              varchar2(80) null,
    descn             varchar2(255) null,
    constraint pk_product primary key (productid),
        constraint fk_product_1 foreign key (category)
        references category (catid)
);

create index productCat on product (category);
create index productName on product (name);

create table item (
    itemid            varchar2(10) not null,
    productid         varchar2(10) not null,
    listprice         number(10,2) null,
    unitcost          number(10,2) null,
    supplier          int null,
    status            varchar2(2) null,
    attr1             varchar2(80) null,
    attr2             varchar2(80) null,
    attr3             varchar2(80) null,
    attr4             varchar2(80) null,
    attr5             varchar2(80) null,
    constraint pk_item primary key (itemid),
        constraint fk_item_1 foreign key (productid)
        references product (productid),
        constraint fk_item_2 foreign key (supplier)
        references supplier (suppid)
);

create index itemProd on item (productid);

create table inventory (
    itemid            varchar2(10) not null,
    qty               int not null,
    constraint pk_inventory primary key (itemid)
);

CREATE TABLE sequence
(
    name               varchar2(30)  not null,
    nextid             int          not null,
    constraint pk_sequence primary key (name)
);
