DROP TABLE IF EXISTS file_storage.user_roles;
CREATE TABLE file_storage.user_roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);
-- 插入用户角色关联表
INSERT INTO file_storage.user_roles (user_id, role_id)
VALUES
    (1, 1),   -- john_doe 属于 普通用户
    (2, 1),   -- jane_smith 属于 普通用户
    (3, 1),   -- alice_jones 属于 普通用户
    (4, 1),   -- bob_brown 属于 普通用户
    (5, 1),   -- charlie_davis 属于 普通用户
    (6, 1),   -- david_miller 属于 普通用户
    (7, 1),   -- emily_wilson 属于 普通用户
    (8, 1),   -- frank_moore 属于 普通用户
    (9, 1),   -- grace_taylor 属于 普通用户
    (10, 1),  -- hannah_anderson 属于 普通用户
    (11, 1),  -- joshua_white 属于 普通用户
    (12, 1),  -- laura_hall 属于 普通用户
    (13, 1),  -- michael_wright 属于 普通用户
    (14, 1),  -- nina_scott 属于 普通用户
    (15, 1),  -- olivia_clark 属于 普通用户
    (16, 1),  -- peter_harris 属于 普通用户
    (17, 1),  -- quinn_adams 属于 普通用户
    (18, 1),  -- ryan_evans 属于 普通用户
    (19, 1),  -- samantha_lewis 属于 普通用户
    (20, 1),  -- tyler_thompson 属于 普通用户
    (21, 1),  -- uma_baker 属于 普通用户
    (22, 1),  -- victor_king 属于 普通用户
    (23, 1),  -- willow_james 属于 普通用户
    (24, 1),  -- xander_lee 属于 普通用户
    (25, 1),  -- yasmine_lopez 属于 普通用户
    (26, 1),  -- zachary_jameson 属于 普通用户
    (27, 1),  -- amanda_martin 属于 普通用户
    (28, 2);  -- admin_user 属于 超级管理员
