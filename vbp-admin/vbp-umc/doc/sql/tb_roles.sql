rem tb_roles 新增sql语句
rem 2018-4-11 17:01:22
rem mrh

rem DROP TABLE tb_roles;

CREATE TABLE tb_roles ( 
	id 	BigInt not null COMMENT 'ID',
	compId 	BigInt  not null COMMENT '公司ID',
	cnName	Varchar (50)  not null COMMENT '中文名',
	enName	Varchar (50)  null COMMENT '英文名',
	createTime TIMESTAMP  null COMMENT '资料创建时间',
	creator	Varchar (32)  null COMMENT '资料创建人',
	modifyTime TIMESTAMP  null COMMENT '资料更新时间',
	modifier	Varchar (32)  null COMMENT '资料更新人',
	deleteTime TIMESTAMP  null COMMENT '资料删除时间',
	deletor	Varchar (32)  null COMMENT '资料删除人',
	primary key (id)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


