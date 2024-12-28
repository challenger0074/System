CREATE TABLE file_storage.upload_music (
                                       id INT  PRIMARY KEY,  -- primary key
                                       music_name VARCHAR(50) NOT NULL,        -- 音乐名
                                       upload_user VARCHAR(24) NOT NULL           -- 上传用户
);
alter table file_storage.upload_music change column id id int auto_increment;
--新增存储位置字段
alter table file_storage.upload_music add column storage_location varchar(255);
