rem tb_permissions 新增sql语句
rem 2018-4-11 17:01:22
rem mrh

rem DROP TABLE tb_permissions;

CREATE TABLE tb_permissions ( 
	id 	BigInt not null COMMENT 'ID',
	cnName	Varchar (50)  not null COMMENT '权限名称',
	enName	Varchar (50)  null COMMENT '英文名称',
	perType 	TINYINT  not null COMMENT '资源类型',
	resourceId 	BigInt  not null COMMENT '资源ID',
	resource	Varchar (200)  not null COMMENT '类型',
	createTime TIMESTAMP  null COMMENT '资料创建时间',
	creator	Varchar (32)  null COMMENT '资料创建人',
	modifyTime TIMESTAMP  null COMMENT '资料更新时间',
	modifier	Varchar (32)  null COMMENT '资料更新人',
	deleteTime TIMESTAMP  null COMMENT '资料删除时间',
	deletor	Varchar (32)  null COMMENT '资料删除人',
	primary key (id)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


