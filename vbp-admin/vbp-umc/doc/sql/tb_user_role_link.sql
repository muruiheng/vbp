rem tb_user_role_link 新增sql语句
rem 2018-4-11 17:01:22
rem mrh

rem DROP TABLE tb_user_role_link;

CREATE TABLE tb_user_role_link ( 
	id 	BigInt not null COMMENT 'ID',
	userid 	BigInt  not null COMMENT '用户',
	roleid 	BigInt  not null COMMENT '角色',
	createTime TIMESTAMP  null COMMENT '资料创建时间',
	creator	Varchar (32)  null COMMENT '资料创建人',
	primary key (id)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


