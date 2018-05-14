rem tb_dept_user_links 新增sql语句
rem 2018-4-11 17:00:59
rem mrh

rem DROP TABLE tb_dept_user_links;

CREATE TABLE tb_dept_user_links ( 
	id 	BigInt not null COMMENT 'ID',
	deptId 	BigInt  not null COMMENT '部门ID',
	userId 	BigInt  not null COMMENT '用户ID',
	createTime TIMESTAMP  null COMMENT '资料创建时间',
	creator	Varchar (32)  null COMMENT '资料创建人',
	primary key (id)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


