rem tb_users 新增sql语句
rem 2018-4-11 17:00:59
rem mrh

rem DROP TABLE tb_users;

CREATE TABLE tb_users ( 
	id 	BigInt not null COMMENT 'ID',
	userName	Varchar (50)  not null COMMENT '用户名',
	password	Varchar (100)  not null COMMENT '密码',
	cnName	Varchar (50)  not null COMMENT '真是中文姓名',
	cnNamePy	Varchar (50)  not null COMMENT '真是姓名拼音',
	enName	Varchar (50)  not null COMMENT '英文名',
	userType	Varchar (50)  not null COMMENT '用户类型',
	email	Varchar (50)  not null COMMENT '邮箱',
	telephone	Varchar (50)  not null COMMENT '移动电话（手机）',
	cerNo	Varchar (50)  not null COMMENT '身份证编号',
	compId 	BigInt  not null COMMENT '公司ID',
	status 	TINYINT  null COMMENT '状态 0-正常 1-禁用',
	createTime TIMESTAMP  null COMMENT '资料创建时间',
	creator	Varchar (32)  null COMMENT '资料创建人',
	modifyTime TIMESTAMP  null COMMENT '资料创建时间',
	modifier	Varchar (32)  null COMMENT '资料创建人',
	deleteTime TIMESTAMP  null COMMENT '资料删除时间',
	deletor	Varchar (32)  null COMMENT '资料删除人',
	primary key (id)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


