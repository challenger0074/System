DROP TABLE IF EXISTS file_storage.es_binary_info;

CREATE TABLE file_storage.es_binary_info (
    id VARCHAR(32) NOT NULL COMMENT '主键', 
    binary_type VARCHAR(128) NULL COMMENT '二进制类型，图片，文件', 
    binary_value BLOB NOT NULL COMMENT '二进制值', 
    file_name VARCHAR(128) NULL COMMENT '文件名', 
    create_by VARCHAR(32) NULL COMMENT '创建人', 
    create_by_name VARCHAR(64) NULL COMMENT '创建人名称', 
    create_date TIMESTAMP NULL COMMENT '创建时间', 
    update_by VARCHAR(32) NULL COMMENT '修改人', 
    update_by_name VARCHAR(64) NULL COMMENT '修改人名称', 
    update_date TIMESTAMP NULL COMMENT '修改时间', 
    del_flag VARCHAR(2) NULL COMMENT '是否删除：0-已删除 1-未删除',
    PRIMARY KEY (id)
);
