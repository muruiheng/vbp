rem tb_companys 新增sql语句
rem 2018-4-11 16:07:50
rem mrh

rem DROP TABLE tb_sequences;

CREATE TABLE tb_sequences ( 
	sysCode		Varchar (100) not null COMMENT '系统代码',
	sequence	BigInt not null COMMENT 'ID增长序列',
	step	int default 10 not null COMMENT '增长步骤',
	createTime TIMESTAMP not null COMMENT '资料创建时间',
	modifyTime TIMESTAMP not null COMMENT '资料更新时间',
	primary key (sysCode)
)ENGINE=InnoDB charset utf8 collate utf8_general_ci;


