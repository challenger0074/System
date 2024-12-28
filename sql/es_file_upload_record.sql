

DROP TABLE IF EXISTS file_storage.es_file_upload_record ;

CREATE TABLE file_storage.es_file_upload_record (
	id varchar(32) NOT NULL COMMENT   '主键',
	file_name varchar(100) NULL COMMENT   '文件名称',
	upload_status varchar(2) NULL COMMENT   '上传状态：0-失败 1-成功',
	deal_status varchar(2) NULL COMMENT   '文件处理状态：1未处理 2-处理中 3-已处理 极少数类型的文件上传会用到',
	file_type varchar(2) NULL COMMENT   '文件类型，对应的业务类型枚举需要确定',
	create_by varchar(32) NULL COMMENT   '创建人',
	create_by_name varchar(64) NULL COMMENT   '创建人名称',
	create_date timestamp NULL COMMENT   '创建时间',
	update_by varchar(32) NULL COMMENT   '修改人',
	update_by_name varchar(64) NULL COMMENT   '修改人名称',
	update_date timestamp NULL COMMENT   '修改时间',
	del_flag varchar(2) NULL COMMENT   '是否删除：0-已删除 1-未删除',
	company_code varchar(64) NULL COMMENT   '企业编码，部分上传文件需要使用',
	business_type varchar(2) NULL COMMENT   '业务类型',
	CONSTRAINT es_file_upload_record_pk PRIMARY KEY (id)
)COMMENT '上传记录表';




