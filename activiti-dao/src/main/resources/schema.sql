DROP DATABASE IF EXISTS activiti;

CREATE DATABASE activiti
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE activiti;

-- ----------------------------
--  Table structure for tb_user
-- ----------------------------
DROP TABLE
IF EXISTS tb_user;

CREATE TABLE tb_user
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  username     VARCHAR(32)                           NOT NULL
  COMMENT '用户名',
  realname     VARCHAR(32)                           NOT NULL
  COMMENT '真实姓名',
  password     VARCHAR(64)                           NOT NULL
  COMMENT '密码',
  salt         VARCHAR(64)                           NOT NULL
  COMMENT '密码盐',
  is_deleted   TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '用户表';
CREATE UNIQUE INDEX username_UNIQUE
  ON tb_user (username);

-- ----------------------------
--  Table structure for tb_role
-- ----------------------------
DROP TABLE
IF EXISTS tb_role;

CREATE TABLE tb_role
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '角色代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '角色名称',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '角色表';
CREATE UNIQUE INDEX code_UNIQUE
  ON tb_role (code);

-- ----------------------------
--  Table structure for tb_menu
-- ----------------------------
DROP TABLE
IF EXISTS tb_menu;

CREATE TABLE tb_menu
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '菜单代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '菜单名称',
  pcode        VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '父菜单代码',
  url          VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单地址',
  sort         INT(11)                               NOT NULL                DEFAULT 0
  COMMENT '菜单排序(从0开始)',
  icon         VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单图标的样式',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '菜单表';
CREATE INDEX sort_ix
  ON tb_menu (sort);
CREATE UNIQUE INDEX code_UNIQUE
  ON tb_menu (code);

-- ----------------------------
--  Table structure for tb_user_role
-- ----------------------------
DROP TABLE
IF EXISTS tb_user_role;

CREATE TABLE tb_user_role
(
  username  VARCHAR(32) NOT NULL
  COMMENT '用户名',
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  PRIMARY KEY (username, role_code)
)
  COMMENT '用户角色表';

-- ----------------------------
--  Table structure for rtb_ole_menu
-- ----------------------------
DROP TABLE
IF EXISTS tb_role_menu;

CREATE TABLE tb_role_menu
(
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  menu_code VARCHAR(32) NOT NULL
  COMMENT '菜单代码',
  PRIMARY KEY (role_code, menu_code)
)
  COMMENT '角色菜单表';

#====================初始数据====================#

-- ----------------------------
--  data for tb_user
-- ----------------------------
INSERT INTO tb_user
(username, realname, password, salt)
VALUES
  # 密码全是：111111
  ('admin', '超级管理员', '25500f5b85a66895e0b99117a12cd51b6d07eb13', '93fab0ba521763fc'),
  ('guest', '普通用户', '25500f5b85a66895e0b99117a12cd51b6d07eb13', '93fab0ba521763fc');

-- ----------------------------
--  data for tb_role
-- ----------------------------
INSERT INTO tb_role
(code, name)
VALUES
  ('ROLE_ADMIN', '超级管理员'),
  ('ROLE_GUEST', '普通用户');

-- ----------------------------
--  data for tb_menu
-- ----------------------------
INSERT INTO tb_menu
(code, name, pcode, url, sort, icon)
VALUES
  ('DASHBOARD', '工作台', '', 'index', 0, 'menu-icon fa fa-dashboard'),

  ('SYSTEM', '系统', 'DASHBOARD', 'system', 1, 'menu-icon fa fa-cogs'),
  ('SYSTEM_USER', '用户管理', 'SYSTEM', 'system/user', 0, ''),
  ('SYSTEM_ROLE', '角色管理', 'SYSTEM', 'system/role', 1, ''),
  ('SYSTEM_MENU', '菜单管理', 'SYSTEM', 'system/menu', 2, ''),
  ('SYSTEM_CACHE', '缓存管理', 'SYSTEM', 'system/cache', 5, ''),
  ('SYSTEM_LOGIN', '登录日志', 'SYSTEM', 'system/login', 6, ''),
  ('SYSTEM_SQL', '执行脚本', 'SYSTEM', 'system/sql', 7, ''),

  ('MANAGE', '管理', 'DASHBOARD', 'manage', 2, 'menu-icon fa fa-desktop'),
  ('MANAGE_ACTIVITI', '流程管理', 'MANAGE', 'manage/activiti', 1, ''),

  ('USER', '我的', 'DASHBOARD', 'user', 3, 'menu-icon fa fa-user'),
  ('USER_INFO', '基本信息', 'USER', 'user/info', 0, '');

-- ----------------------------
--  data for tb_user_role
-- ----------------------------
INSERT INTO tb_user_role
VALUES
  ('admin', 'ROLE_ADMIN'),
  ('guest', 'ROLE_GUEST');

-- ----------------------------
--  data for tb_role_menu
-- ----------------------------
INSERT INTO tb_role_menu SELECT
                           'ROLE_ADMIN',
                           code
                         FROM tb_menu;

INSERT INTO tb_role_menu SELECT
                           'ROLE_GUEST',
                           code
                         FROM tb_menu
                         WHERE code LIKE 'USER%' OR code LIKE 'MANAGE%' OR code = 'DASHBOARD';
