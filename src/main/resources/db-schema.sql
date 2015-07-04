drop table payment;
drop table parcelOrderItem;
drop table parcelOrder;
drop table userTable;
drop table coldStoreItem;
drop table parcel;
drop table fishType;
create table fishType
(
   id int not null generated always as identity,
   name varchar(255) not null,
   description varchar(255),
   creationDate date not null,
   image blob(2147483647),
   primary key (id)
);
create table parcel
(
   id int not null generated always as identity,
   name varchar(255) not null,
   description varchar(255),
   shipName varchar(255) not null,
   deliveryPrice decimal(19,2) not null,
   status int not null,
   arrivalDate date not null,
   creationDate date not null,
   primary key (id)
);
create table coldStoreItem
(
   id int not null generated always as identity,
   parcelId int constraint parcel_fk references parcel,
   fishTypeId int constraint fishType_fk references fishType not null,
   weightPurchased decimal(19,2) not null,
   weightArrived decimal(19,2) not null,
   weightLeft decimal(19,2) not null,
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
   name varchar(255) not null,
   surname varchar(255) not null,
   email varchar(255) unique not null,
   password varchar(255) not null,
   type int not null,
   status int not null,
   quota decimal(19,2),
   creationDate date not null,
   primary key (id)
);
create table parcelOrder
(
   id int not null generated always as identity,
   userId int constraint customer_fk references userTable,
   sumToPay decimal(19,2) not null,
   sumPayed decimal(19,2) not null,
   shipmentDate date,
   bankAccount varchar(255),
   description varchar(255),
   status int not null,
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

