DROP TABLE IF EXISTS file_storage.role_permissions
CREATE TABLE file_storage.role_permissions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  role_id INT NOT NULL,
  `state` varchar(1) DEFAULT '1' COMMENT '是否有效 1有效     2无效',
  permission_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (role_id) REFERENCES roles(id),
  FOREIGN KEY (permission_id) REFERENCES permissions(id)
);
-- 插入角色权限关联表
INSERT INTO file_storage.role_permissions (role_id, permission_id)
VALUES
    (1, 1),  -- 普通用户 允许 查看文件
    (1, 2),  -- 普通用户 允许 上传文件
    (1, 3),  -- 普通用户 允许 删除文件
    (1, 4),  -- 普通用户 允许 修改文件
    (2, 1),  -- 超级管理员 允许 查看文件
    (2, 2),  -- 超级管理员 允许 上传文件
    (2, 3),  -- 超级管理员 允许 删除文件
    (2, 4),  -- 超级管理员 允许 修改文件
    (2, 5),  -- 超级管理员 允许 管理用户
    (2, 6),  -- 超级管理员 允许 设置角色
    (2, 7);  -- 超级管理员 允许 查看用户列表

--新增
`state` varchar(1) DEFAULT '1' COMMENT '是否有效 1有效     2无效';
ALTER TABLE `file_storage`.`role_permissions`
ADD COLUMN `state` varchar(1) DEFAULT '1' COMMENT '是否有效 1有效     2无效';