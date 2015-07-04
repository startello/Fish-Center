drop table payment;
drop table parcelOrderItem;
drop table parcelOrder;
drop table bankAccount;
drop table customer;
drop table coldStoreItem;
drop table parcel;
drop table fishType;
drop table staffMember;
create table fishType
(
   id int not null generated always as identity,
   name varchar(60) not null,
   description varchar(300),
   creationDate date not null,
   primary key (id)
);
create table parcel
(
   id int not null generated always as identity,
   name varchar(60) not null,
   description varchar(300),
   shipName varchar(60),
   deliveryPrice decimal(19,2) not null,
   status smallint not null,
   arrivalDate date,
   creationDate date not null,
   primary key (id)
);
create table coldStoreItem
(
   id int not null generated always as identity,
   parcelId int constraint parcel_fk references parcel,
   fishTypeId int constraint fishType_fk references fishType not null,
   weightPurchased decimal(19,2) not null constraint wp_chck check (weightPurchased >= 0),
   weightArrived decimal(19,2) not null constraint wa_chck check (weightArrived >= 0),
   weightLeft decimal(19,2) not null constraint wl_chck check (weightLeft >= 0),
   purchasePrice decimal(19,2) not null,
   sellingPrice decimal(19,2) not null,
   storagePrice decimal(19,2) not null,
   status int not null,
   arrivalDate date,
   writeOffDate date,
   creationDate date not null,
   primary key (id)
);
create table userTable
(
   id int not null generated always as identity,
   name varchar(60) not null,
   surname varchar(60) not null,
   email varchar(60) unique not null,
   password varchar(60) not null,
   position smallint not null,
   status smallint not null,
   creationDate date not null,
   primary key (id)
);
create table bankAccount
(
   id int not null generated always as identity,
   userId int constraint bankAccountCustomer_fk references userTable,
   number varchar(60) not null,
   description varchar(60),
   status smallint not null,
   creationDate date not null,
   primary key (id)
);

create table parcelOrder
(
   id int not null generated always as identity,
   userId int constraint customer_fk references userTable,
   bankAccountId int constraint bankAccount_fk references bankAccount,
   sumToPay decimal(19,2) not null,
   sumPayed decimal(19,2) not null,
   shipmentDate date,
   description varchar(60),
   status smallint not null,
   creationDate date not null,
   primary key (id)
);
create table parcelOrderItem
(
   id int not null generated always as identity,
   parcelOrderId int constraint parcelOrder_fk references parcelOrder,
   coldStoreItemId int constraint coldStoreItem_fk references coldStoreItem,
   weight decimal(19,2) not null,
   price decimal(19,2) not null,
   creationDate date not null,
   primary key (id)
);
create table payment
(
	id int not null generated always as identity,
	parcelOrderId int constraint paymentParcelOrder_fk references parcelOrder,
	sumPayed decimal(19,2) not null,
	creationDate date not null,
	primary key (id)
);

