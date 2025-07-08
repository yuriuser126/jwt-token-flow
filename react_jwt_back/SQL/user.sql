create table user(
	 no int not null auto_increment
	,user_id varchar(100) not null
	,user_pw varchar(200) not null
	,name varchar(100) not null
	,email varchar(200) default null
	,enabled int default 1
	,primary key(no)
);

select * from user;