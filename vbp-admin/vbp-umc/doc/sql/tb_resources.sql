rem tb_resources 新增sql语句
rem 2018-4-11 17:01:22
rem mrh

rem DROP TABLE tb_resources;

CREATE TABLE tb_resources ( 
	id 	BigInt not null COMMENT 'ID',
	cnName	Varchar (50)  not null COMMENT '菜单名',
	enName	Varchar (50)  null COMMENT '英文名',
	mCode	Varchar (32)  not null COMMENT '菜单编码',
	parentId 	BigInt  null COMMENT '上级菜单',
	resType 	TINYINT  not null COMMENT '资源类型',
	src	Varchar (100)  not null COMMENT '菜单地址',
	icon	Varchar (50)  null COMMENT '菜单图标',
	state 	Integer  null COMMENT '状态',
	createTime TIMESTAMP  null COMMENT '资料创建时间',
	creator	Varchar (32)  null COMMENT '资料创建人',
	modifyTime TIMESTAMP  null COMMENT '资料更新时间',
	modifier	Varchar (32)  null COMMENT '资料更新人',
	deleteTime TIMESTAMP  null COMMENT '资料删除时间',
	deletor	Varchar (32)  null COMMENT '资料删除人',
	primary key (id)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


