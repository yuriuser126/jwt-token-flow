create table user_auth(
	 auth_no int not null auto_increment
	,user_id varchar(100) not null
	,auth varchar(100) not null -- (USER, ADMIN...)
	,primary key(auth_no)
);

select * from user_auth;