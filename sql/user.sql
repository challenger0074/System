DROP TABLE IF EXISTS file_storage.user;

CREATE TABLE file_storage.user (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户唯一标识符',
    username VARCHAR(25) NULL UNIQUE COMMENT '用户登录名',
    `nickname` varchar(255) DEFAULT NULL COMMENT '昵称'
    `password` VARCHAR(25) NULL COMMENT '用户密码',
    email VARCHAR(50) COMMENT '用户电子邮件地址',
    `role` VARCHAR(12) COMMENT '用户角色',
    `enabled` TINYINT COMMENT 'security状态',
    `state` TINYINT COMMENT '账户状态',
    `userId` varchar(100) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '账户创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) COMMENT='用户信息表';

INSERT INTO file_storage.user (username, password, email, role, enabled, state)
VALUES
    ('john_doe', 'password123', 'john.doe@example.com', '普通用户', 1, 1),
    ('jane_smith', 'password456', 'jane.smith@example.com', '普通用户', 1, 0),
    ('alice_jones', 'password789', 'alice.jones@example.com', '普通用户', 1, 1),
    ('bob_brown', 'password321', 'bob.brown@example.com', '普通用户', 0, 0),
    ('charlie_davis', 'password654', 'charlie.davis@example.com', '普通用户', 1, 1),
    ('david_miller', 'password987', 'david.miller@example.com', '普通用户', 0, 0),
    ('emily_wilson', 'password234', 'emily.wilson@example.com', '普通用户', 1, 1),
    ('frank_moore', 'password567', 'frank.moore@example.com', '普通用户', 0, 0),
    ('grace_taylor', 'password890', 'grace.taylor@example.com', '普通用户', 1, 1),
    ('hannah_anderson', 'password135', 'hannah.anderson@example.com', '普通用户', 0, 0),
    ('joshua_white', 'password246', 'joshua.white@example.com', '普通用户', 1, 1),
    ('laura_hall', 'password753', 'laura.hall@example.com', '普通用户', 0, 0),
    ('michael_wright', 'password159', 'michael.wright@example.com', '普通用户', 1, 1),
    ('nina_scott', 'password864', 'nina.scott@example.com', '普通用户', 0, 0),
    ('olivia_clark', 'password147', 'olivia.clark@example.com', '普通用户', 1, 1),
    ('peter_harris', 'password258', 'peter.harris@example.com', '普通用户', 0, 0),
    ('quinn_adams', 'password369', 'quinn.adams@example.com', '普通用户', 1, 1),
    ('ryan_evans', 'password951', 'ryan.evans@example.com', '普通用户', 0, 0),
    ('samantha_lewis', 'password753', 'samantha.lewis@example.com', '普通用户', 1, 1),
    ('tyler_thompson', 'password357', 'tyler.thompson@example.com', '普通用户', 0, 0),
    ('uma_baker', 'password468', 'uma.baker@example.com', '普通用户', 1, 1),
    ('victor_king', 'password579', 'victor.king@example.com', '普通用户', 0, 0),
    ('willow_james', 'password682', 'willow.james@example.com', '普通用户', 1, 1),
    ('xander_lee', 'password794', 'xander.lee@example.com', '普通用户', 0, 0),
    ('yasmine_lopez', 'password805', 'yasmine.lopez@example.com', '普通用户', 1, 1),
    ('zachary_jameson', 'password916', 'zachary.jameson@example.com', '普通用户', 0, 0),
    ('amanda_martin', 'password027', 'amanda.martin@example.com', '普通用户', 1, 1),
    ('brandon_sanders', 'password138', 'brandon.sanders@example.com', '普通用户', 0, 0),
    ('cynthia_garcia', 'password249', 'cynthia.garcia@example.com', '普通用户', 1, 1),
    ('daniel_ortiz', 'password360', 'daniel.ortiz@example.com', '普通用户', 0, 0),
    ('admin_user', 'admin_password', 'admin@example.com', '超级管理员', 1, 1);
    --更新nick'name
UPDATE file_storage.user
SET nickname = CASE
    WHEN username = 'john_doe' THEN 'John'
    WHEN username = 'jane_smith' THEN 'Jane'
    WHEN username = 'alice_jones' THEN 'Alice'
    WHEN username = 'bob_brown' THEN 'Bob'
    WHEN username = 'charlie_davis' THEN 'Charlie'
    WHEN username = 'david_miller' THEN 'David'
    WHEN username = 'emily_wilson' THEN 'Emily'
    WHEN username = 'frank_moore' THEN 'Frank'
    WHEN username = 'grace_taylor' THEN 'Grace'
    WHEN username = 'hannah_anderson' THEN 'Hannah'
    WHEN username = 'joshua_white' THEN 'Joshua'
    WHEN username = 'laura_hall' THEN 'Laura'
    WHEN username = 'michael_wright' THEN 'Michael'
    WHEN username = 'nina_scott' THEN 'Nina'
    WHEN username = 'olivia_clark' THEN 'Olivia'
    WHEN username = 'peter_harris' THEN 'Peter'
    WHEN username = 'quinn_adams' THEN 'Quinn'
    WHEN username = 'ryan_evans' THEN 'Ryan'
    WHEN username = 'samantha_lewis' THEN 'Samantha'
    WHEN username = 'tyler_thompson' THEN 'Tyler'
    WHEN username = 'uma_baker' THEN 'Uma'
    WHEN username = 'victor_king' THEN 'Victor'
    WHEN username = 'willow_james' THEN 'Willow'
    WHEN username = 'xander_lee' THEN 'Xander'
    WHEN username = 'yasmine_lopez' THEN 'Yasmine'
    WHEN username = 'zachary_jameson' THEN 'Zachary'
    WHEN username = 'amanda_martin' THEN 'Amanda'
    WHEN username = 'brandon_sanders' THEN 'Brandon'
    WHEN username = 'cynthia_garcia' THEN 'Cynthia'
    WHEN username = 'daniel_ortiz' THEN 'Daniel'
    WHEN username = 'admin_user' THEN 'Admin'
    ELSE nickname
END;
ALTER TABLE file_storage.user MODIFY COLUMN `nickname` varchar(255) DEFAULT 'nickname' COMMENT '昵称';

-- 更新userId
-- 用户名后续修改为唯一
