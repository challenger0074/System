DROP TABLE IF EXISTS file_storage.mainmenu;
CREATE TABLE file_storage.mainmenu (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- Auto-incrementing primary key
    title VARCHAR(50) NOT NULL,        -- Title cannot be NULL, with a maximum length of 255 characters
    path VARCHAR(24) NOT NULL           -- Path cannot be NULL, with a maximum length of 255 characters
);
-- 添加数据到 mainmenu 表
INSERT INTO file_storage.mainmenu (id,title, path) VALUES (1,'首页', '/home');
INSERT INTO file_storage.mainmenu (id,title, path) VALUES (2,'关于我们', '/about');
INSERT INTO file_storage.mainmenu (id,title, path) VALUES (3,'服务', '/services');
INSERT INTO file_storage.mainmenu (id,title, path) VALUES (4,'联系我们', '/contact');
INSERT INTO file_storage.mainmenu (id,title, path) VALUES (5,'博客', '/blog');
INSERT INTO file_storage.mainmenu (id,title, path) VALUES (100,'权限管理', '/admin');
INSERT INTO file_storage.mainmenu (id,title, path) VALUES (200,'音乐平台', '/music');
-- 添加图标名称字段
alter table file_storage.mainmenu add column icon varchar(50) default '';
