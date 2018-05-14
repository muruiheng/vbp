rem tb_companys 新增sql语句
rem 2018-4-11 17:00:59
rem mrh

rem DROP TABLE tb_companys;

CREATE TABLE tb_companys ( 
	id 	BigInt not null COMMENT '公司ID',
	fullName	Varchar (100)  not null COMMENT '公司全名',
	shortName	Varchar (50)  not null COMMENT '公司名缩写',
	ucc	Varchar (50)  not null COMMENT '统一信用代码（组织机构代码）',
	enName	Varchar (50)  null COMMENT '公司英文名',
	parentId 	BigInt  null COMMENT '上级公司ID，',
	status 	TINYINT  null COMMENT '状态 0-正常 1-禁用',
	createTime TIMESTAMP  null COMMENT '资料创建时间',
	creator	Varchar (32)  null COMMENT '资料创建人',
	modifyTime TIMESTAMP  null COMMENT '资料更新时间',
	modifier	Varchar (32)  null COMMENT '资料更新人',
	deleteTime TIMESTAMP  null COMMENT '资料删除时间',
	deletor	Varchar (32)  null COMMENT '资料删除人',
	primary key (id)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


