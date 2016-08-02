//创建数据库
create database goods  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use goods;

//创建user表 
create table user(user_id bigint(20) not null  auto_increment,user_name varchar(50) not null default '',pwd varchar(100) not null default '')ENGINE=InnoDB AUTO_INCREMENT=10000000 DEFAULT CHARSET=UTF8;

create table role(id int(4) not null, role varchar(50) not null default '', detail varchar(50) not null default '', unique key(id), unique key(role));
insert into role(id, role) values(1, "管理员"),(2, "老师"),(3, "财务"),(4, "后勤");

创建student表
create table student(id int(20) auto_increment, sid varchar(20) not null default '', name varchar(30) not null default '', other_name varchar(30) not null default '', phone varchar(20) not null default '', scn varchar(40) not null default '', gender int(4) not null default 0, birth datetime not null default '2016-01-01', home_town varchar(20) not null default '', people varchar(10) not null default '', birth_town varchar(20) not null default '', address varchar(100) not null default '', qq varchar(20) not null default '', wechat varchar(30) not null default '', primary key(id), unique key(sid))


