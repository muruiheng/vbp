rem tb_role_permissions_link 新增sql语句
rem 2018-4-11 16:54:56
rem mrh

rem DROP TABLE tb_role_permissions_link;

CREATE TABLE tb_role_permissions_link ( 
	id 	BigInt not null COMMENT 'ID',
	permissionId 	BigInt  not null COMMENT '权限名称',
	roleId 	BigInt  null COMMENT '英文名称',
	createTime TIMESTAMP  null COMMENT '资料创建时间',
	creator	Varchar (32)  null COMMENT '资料创建人',
	primary key (id)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


