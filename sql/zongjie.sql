DROP TABLE IF EXISTS file_storage.role;

CREATE TABLE file_storage.role (
    role_id VARCHAR(15)   COMMENT '角色id',
    username VARCHAR(15)  COMMENT '角色名'
) COMMENT='角色表';
INSERT INTO file_storage.role (role_id, username)
VALUES
    ('r001', '普通用户'),
    ('r002', '超级管理员');
    DROP TABLE IF EXISTS file_storage.system_power;

CREATE TABLE file_storage.system_power (
    power_id VARCHAR(15)  COMMENT '权限id',
    permission VARCHAR(15)  COMMENT '权限id',
    `desc` VARCHAR(15)  COMMENT '权限id'
) COMMENT='系统权限表';
INSERT INTO file_storage.system_power (power_id, permission, `desc`)
VALUES
    ('1001', '查看文件', '用户可以查看文件'),
    ('1002', '上传文件', '用户可以上传文件'),
    ('1003', '删除文件', '用户可以删除文件'),
    ('1004', '管理用户', '管理员可以管理用户'),
    ('1005', '管理角色', '管理员可以管理角色');
    DROP TABLE IF EXISTS file_storage.role_power;

CREATE TABLE file_storage.role_power (
    role_id VARCHAR(15)   COMMENT '角色id',
    power_id VARCHAR(15)  COMMENT '权限id'
) COMMENT='角色权限表';
INSERT INTO file_storage.role_power (role_id, power_id)
VALUES
    ('r001', '1001'),  -- 普通用户可以查看文件
    ('r001', '1002'),  -- 普通用户可以上传文件
    ('r002', '1001'),  -- 超级管理员可以查看文件
    ('r002', '1002'),  -- 超级管理员可以上传文件
    ('r002', '1003'),  -- 超级管理员可以删除文件
    ('r002', '1004'),  -- 超级管理员可以管理用户
    ('r002', '1005');  -- 超级管理员可以管理角色
    DROP TABLE IF EXISTS file_storage.user_role;

CREATE TABLE file_storage.user_role (
    user_id VARCHAR(15)  COMMENT '用户id',
    role_id VARCHAR(15)   COMMENT '角色id'    
) COMMENT='用户角色表';
INSERT INTO file_storage.user_role (user_id, role_id)
VALUES
    ('john_doe', 'r001'),
    ('jane_smith', 'r001'),
    ('alice_jones', 'r001'),
    ('bob_brown', 'r001'),
    ('charlie_davis', 'r001'),
    ('david_miller', 'r001'),
    ('emily_wilson', 'r001'),
    ('frank_moore', 'r001'),
    ('grace_taylor', 'r001'),
    ('hannah_anderson', 'r001'),
    ('joshua_white', 'r001'),
    ('laura_hall', 'r001'),
    ('michael_wright', 'r001'),
    ('nina_scott', 'r001'),
    ('olivia_clark', 'r001'),
    ('peter_harris', 'r001'),
    ('quinn_adams', 'r001'),
    ('ryan_evans', 'r001'),
    ('samantha_lewis', 'r001'),
    ('tyler_thompson', 'r001'),
    ('uma_baker', 'r001'),
    ('victor_king', 'r001'),
    ('willow_james', 'r001'),
    ('xander_lee', 'r001'),
    ('yasmine_lopez', 'r001'),
    ('zachary_jameson', 'r001'),
    ('amanda_martin', 'r001'),
    ('brandon_sanders', 'r001'),
    ('cynthia_garcia', 'r001'),
    ('daniel_ortiz', 'r001'),
    ('admin_user', '2');  -- 超级管理员