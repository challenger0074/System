DROP TABLE IF EXISTS file_storage.roles;
CREATE TABLE file_storage.roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  description VARCHAR(255),
  `state` varchar(1) DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 插入角色信息表
INSERT INTO file_storage.roles (name, description)
VALUES
    ('普通用户', '普通用户角色，具有基本权限'),
    ('超级管理员', '拥有所有权限的管理员角色'),
    ('管理员', '拥有管理权限的角色');

-- 插入字段
ALTER TABLE file_storage.roles
ADD COLUMN state VARCHAR(1) DEFAULT '1' COMMENT '是否有效  1有效  2无效';
-- 修改字段名
ALTER TABLE file_storage.roles
CHANGE COLUMN `name` `role_name` VARCHAR(50) NOT NULL UNIQUE;

