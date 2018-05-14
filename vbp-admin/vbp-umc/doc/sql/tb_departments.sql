rem tb_departments 新增sql语句
rem 2018-4-11 17:00:59
rem mrh

rem DROP TABLE tb_departments;

CREATE TABLE tb_departments ( 
	id 	BigInt not null COMMENT '部门ID',
	compId 	BigInt  not null COMMENT '公司ID',
	managerid 	BigInt  null COMMENT '部门主管',
	deptName	Varchar (50)  not null COMMENT '部门名称',
	deptNo	Varchar (50)  not null COMMENT '部门代码',
	status 	TINYINT  null COMMENT '状态',
	parentId 	BigInt  null COMMENT '上级部门ID',
	createTime TIMESTAMP  null COMMENT '资料创建时间',
	creator	Varchar (32)  null COMMENT '资料创建人',
	modifyTime TIMESTAMP  null COMMENT '资料更新时间',
	modifier	Varchar (32)  null COMMENT '资料更新人',
	deleteTime TIMESTAMP  null COMMENT '资料删除时间',
	deletor	Varchar (32)  null COMMENT '资料删除人',
	primary key (id)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


