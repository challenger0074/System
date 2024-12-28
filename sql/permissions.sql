DROP TABLE IF EXISTS file_storage.permissions;
CREATE TABLE file_storage.permissions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  description VARCHAR(255),
  `menu_code` varchar(255) DEFAULT '' COMMENT '归属菜单,前端判断并展示菜单使用,',
  `menu_name` varchar(255) DEFAULT '' COMMENT '菜单的中文释义',
  `permission_code` varchar(255) DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
  `permission_name` varchar(255) DEFAULT '' COMMENT '本权限的中文释义',
  `required_permission` tinyint(1) DEFAULT '2' COMMENT '是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 插入权限信息表
INSERT INTO file_storage.permissions (name, description)
VALUES
    ('查看文件', '允许用户查看文件'),
    ('上传文件', '允许用户上传文件'),
    ('删除文件', '允许用户删除文件'),
    ('修改文件', '允许用户修改文件'),
    ('管理用户', '允许管理员管理其他用户'),
    ('设置角色', '允许管理员设置用户角色'),
    ('查看用户列表', '允许管理员查看所有用户'),
    ('管理权限', '允许管理员分配权限');

-- 修改权限信息表


ALTER TABLE `file_storage`.`permissions`
ADD COLUMN `menu_code` varchar(255) DEFAULT '' COMMENT '归属菜单,前端判断并展示菜单使用,',
ADD COLUMN `menu_name` varchar(255) DEFAULT '' COMMENT '菜单的中文释义',
ADD COLUMN `permission_code` varchar(255) DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
ADD COLUMN `permission_name` varchar(255) DEFAULT '' COMMENT '本权限的中文释义',
ADD COLUMN `required_permission` tinyint(1) DEFAULT '2' COMMENT '是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选';

