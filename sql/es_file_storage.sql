
DROP TABLE IF EXISTS file_storage.es_file_storage;
CREATE TABLE file_storage.es_file_storage (
	id varchar(32) NULL COMMENT  '主键',
	upload_record_number varchar(32) NULL COMMENT  '文件上传的记录号,l即文件上传记录表的id',
	upload_batch_number varchar(32) NULL COMMENT  '文件上传的批次号，方便某一业务的同批次上传',
	file_name varchar(100) NULL COMMENT  '文件名',
	service_type varchar(2) NULL COMMENT  '业务类型',
	file_size varchar(50) NULL COMMENT  '文件大小',
	create_by varchar(32) NULL COMMENT  '创建人',
	create_by_name varchar(64) NULL COMMENT '创建人名称',
	create_date timestamp NULL COMMENT  '创建时间',
	update_by varchar(32) NULL COMMENT  '修改人',
	update_by_name varchar(64) NULL COMMENT  '修改人名称',
	update_date timestamp NULL COMMENT  '修改时间',
	del_flag varchar(2) NULL COMMENT  '是否删除：0-已删除 1-未删除',
	real_name varchar(100) NULL COMMENT  '文件在ftp的实际存储名称',
	platform varchar(2) NULL COMMENT  '平台：'
)COMMENT='文件实际存储表';



