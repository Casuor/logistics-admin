SET FOREIGN_KEY_CHECKS = 0;
# sys_menu
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`        bigint(20)  NOT NULL AUTO_INCREMENT,
    `parent_id` bigint(20)   DEFAULT NULL COMMENT '父菜单id，一级菜单为0',
    `name`      varchar(64) NOT NULL COMMENT '菜单名称',
    `path`      varchar(255) DEFAULT NULL COMMENT '菜单URL',
    `premiss`   varchar(255) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如:user:list,user:create)',
    `component` varchar(255) DEFAULT NULL COMMENT '组件',
    `type`      int(5)      NOT NULL COMMENT '【类型】0:目录 1:菜单 2:按钮',
    `icon`      varchar(32)  DEFAULT NULL COMMENT '菜单图标',
    `sort`      int(11)      DEFAULT NULL COMMENT '排序',
    `created`   datetime    NOT NULL COMMENT '创建时间',
    `updated`   datetime     DEFAULT NULL COMMENT '修改时间',
    `status`    int(5)      NOT NULL COMMENT '【菜单状态】1：正常 0：禁用',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;


# 父级菜单：系统管理
INSERT INTO `sys_menu`
VALUES ('1', '0', '系统管理', '', 'sys:manage', '', '0', 'el-icon-s-operation', '1', '2021-12-07 10:00:00',
        '2021-12-07 10:00:00', '1');
# start 系统管理子菜单
INSERT INTO `sys_menu`
VALUES ('2', '1', '用户管理', '/main/userInfo', 'sys:user:list', 'system/UserInfo', '1', 'el-icon-user', '1',
        '2021-12-07 10:00:00',
        '2021-12-07 10:00:00', '1');
INSERT INTO `sys_menu`
VALUES ('3', '1', '角色管理', '/main/roleInfo', 'sys:role:list', 'system/RoleInfo', '1', 'el-icon-rank', '2',
        '2021-12-07 10:00:00',
        '2021-12-07 10:00:00', '1');
INSERT INTO `sys_menu`
VALUES ('4', '1', '菜单管理', '/main/menuInfo', 'sys:menu:list', 'system/MenuInfo', '1', 'el-icon-menu', '3',
        '2021-12-07 10:00:00',
        '2021-12-07 10:00:00', '1');
# end 系统管理子菜单

# 父级菜单：产品管理
INSERT INTO `sys_menu`
VALUES ('5', '0', '产品管理', '', 'sys:product', '', '0', 'el-icon-truck', '2', '2021-12-07 10:00:00',
        '2021-12-07 10:00:00', '1');
# start 产品管理子菜单
INSERT INTO `sys_menu`
VALUES ('6', '5', '产品列表', '/main/products', 'sys:product:list', 'product/Products', '1', 'el-icon-burger', '1',
        '2021-12-07 10:00:00', '2021-12-07 10:00:00', '1');
# end 产品管理子菜单


# 父级菜单：订单管理
INSERT INTO `sys_menu`
VALUES ('7', '0', '订单管理', '', 'sys:order', '', '0', 'el-icon-s-order', '3', '2021-12-07 10:00:00',
        '2021-12-07 10:00:00', '1');
# start 订单管理子菜单
INSERT INTO `sys_menu`
VALUES ('8', '7', '我要去下单', '/main/placeOrder', 'sys:order:order', 'logistics/PlaceOrder', '1', 'el-icon-eleme', '1',
        '2021-12-07 10:00:00', '2021-12-07 10:00:00', '1');
INSERT INTO `sys_menu`
VALUES ('9', '7', '订单列表', '/main/orders', 'sys:order:list', 'logistics/Orders', '1', 'el-icon-shopping-cart-full', '2',
        '2021-12-07 10:00:00', '2021-12-07 10:00:00', '1');
INSERT INTO `sys_menu`
VALUES ('10', '7', '订单记录', '/main/orderHistory', 'sys:order:history', 'logistics/OrderHistory', '1', 'el-icon-position',
        '3',
        '2021-12-07 10:00:00', '2021-12-07 10:00:00', '1');
# end 订单管理子菜单

# start btn
INSERT INTO `sys_menu`
VALUES ('11', '3', '新增角色', '', 'sys:role:insert', '', '2', '', '1', '2021-01-15 23:02:25', '2021-01-17 21:53:14', '1');
INSERT INTO `sys_menu`
VALUES ('12', '3', '修改角色', '', 'sys:role:update', '', '2', '', '2', '2021-01-17 21:51:14', '2021-01-17 21:53:14', '1');
INSERT INTO `sys_menu`
VALUES ('13', '3', '删除角色', '', 'sys:role:delete', '', '2', '', '3', '2021-01-17 21:51:39', '2021-01-17 21:53:14', '1');
INSERT INTO `sys_menu`
VALUES ('14', '3', '分配权限', '', 'sys:role:premiss', '', '2', '', '4', '2021-01-17 21:52:02', '2021-01-17 21:53:14', '1');


INSERT INTO `sys_menu`
VALUES ('15', '2', '添加用户', null, 'sys:user:insert', null, '2', null, '1', '2021-01-17 21:48:32', null, '1');
INSERT INTO `sys_menu`
VALUES ('16', '2', '修改用户', null, 'sys:user:update', null, '2', null, '2', '2021-01-17 21:49:03', '2021-01-17 21:53:04',
        '1');
INSERT INTO `sys_menu`
VALUES ('17', '2', '删除用户', null, 'sys:user:delete', null, '2', null, '3', '2021-01-17 21:49:21', null, '1');
INSERT INTO `sys_menu`
VALUES ('18', '2', '分配角色', null, 'sys:user:role', null, '2', null, '4', '2021-01-17 21:49:58', null, '1');
INSERT INTO `sys_menu`
VALUES ('19', '2', '重置密码', null, 'sys:user:repass', null, '2', null, '5', '2021-01-17 21:50:36', null, '1');


INSERT INTO `sys_menu`
VALUES ('20', '4', '添加菜单', null, 'sys:menu:insert', null, '2', null, '1', '2021-01-17 21:53:53', '2021-01-17 21:55:28',
        '1');
INSERT INTO `sys_menu`
VALUES ('21', '4', '修改菜单', null, 'sys:menu:update', null, '2', null, '2', '2021-01-17 21:56:12', null, '1');
INSERT INTO `sys_menu`
VALUES ('22', '4', '删除菜单', null, 'sys:menu:delete', null, '2', null, '3', '2021-01-17 21:56:36', null, '1');

INSERT INTO `sys_menu`
VALUES ('23', '6', '新增产品', '', 'sys:product:insert', null, '2', null, '1', '2021-01-15 23:02:25', '2021-01-17 21:53:14', '1');
INSERT INTO `sys_menu`
VALUES ('24', '6', '修改产品', '', 'sys:product:update', null, '2', null, '2', '2021-01-17 21:51:14', '2021-01-17 21:53:14', '1');
INSERT INTO `sys_menu`
VALUES ('25', '6', '删除产品', '', 'sys:product:delete', null, '2', null, '3', '2021-01-17 21:51:39', '2021-01-17 21:53:14', '1');
# end btn


# sys_role
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`      bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '角色id',
    `name`    varchar(64) NOT NULL COMMENT '角色姓名',
    `code`    varchar(64) NOT NULL COMMENT '【权限编码】admin、generalUser',
    `remark`  varchar(64) DEFAULT NULL COMMENT '备注',
    `created` datetime    DEFAULT NULL COMMENT '角色创建时间',
    `updated` datetime    DEFAULT NULL COMMENT '角色修改时间',
    `status`  int(5)      NOT NULL COMMENT '【角色状态】normal:1、disable:0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`) USING BTREE,
    UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8;

# start insert role
INSERT INTO `sys_role`
VALUES ('1', '普通用户', 'generalUser', '只有基本查看功能', '2021-12-07 10:00:00', '2021-12-07 10:00:00', '0');
INSERT INTO `sys_role`
VALUES ('2', '超级管理员', 'admin', '系统默认最高权限', '2021-12-07 10:00:00', '2021-12-07 10:00:00', '1');
# end insert role


# sys_user
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `username`   varchar(64)  DEFAULT NULL COMMENT '用户名',
    `password`   varchar(64)  DEFAULT NULL COMMENT '密码',
    `avatar`     varchar(255) DEFAULT NULL COMMENT '头像',
    `phone`      varchar(64)  DEFAULT NULL COMMENT '手机号',
    `created`    datetime     DEFAULT NULL COMMENT '创建时间',
    `updated`    datetime     DEFAULT NULL COMMENT '修改时间',
    `last_login` datetime     DEFAULT NULL COMMENT '最后一次登录时间',
    `status`     int(5)     NOT NULL COMMENT '用户状态',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_USERNAME` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8;

# start insert user
INSERT INTO sys_user VALUE (
                            '1', 'admin', 'admin',
                            'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
                            '13032294465',
                            '2021-12-07 10:00:00', '2021-12-07 10:00:00', '2021-12-07 10:00:00', '1'
    );
INSERT INTO sys_user VALUE (
                            '2', 'test', 'test',
                            'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
                            '13032294465',
                            '2021-12-07 10:00:00', '2021-12-07 10:00:00', '2021-12-07 10:00:00', '1'
    );
# end insert user


# sys_user_role
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL COMMENT '用户表id',
    `role_id` bigint(20) NOT NULL COMMENT '角色表id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8mb4;

# start insert user_role
INSERT INTO `sys_user_role`
VALUES ('1', '1', '2');
INSERT INTO `sys_user_role`
VALUES ('2', '2', '1');
# end insert user_role

# sys_role_menu
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) NOT NULL COMMENT '角色id',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 102
  DEFAULT CHARSET = utf8mb4;

# start insert role_menu
# 管理员
INSERT INTO `sys_role_menu`
VALUES ('101', '2', '1');
INSERT INTO `sys_role_menu`
VALUES ('102', '2', '2');
INSERT INTO `sys_role_menu`
VALUES ('103', '2', '3');
INSERT INTO `sys_role_menu`
VALUES ('104', '2', '4');
INSERT INTO `sys_role_menu`
VALUES ('105', '2', '5');
INSERT INTO `sys_role_menu`
VALUES ('106', '2', '6');
INSERT INTO `sys_role_menu`
VALUES ('107', '2', '7');
INSERT INTO `sys_role_menu`
VALUES ('108', '2', '8');
INSERT INTO `sys_role_menu`
VALUES ('109', '2', '9');
INSERT INTO `sys_role_menu`
VALUES ('110', '2', '10');
INSERT INTO `sys_role_menu`
VALUES ('111', '2', '11');
INSERT INTO `sys_role_menu`
VALUES ('112', '2', '12');
INSERT INTO `sys_role_menu`
VALUES ('113', '2', '13');
INSERT INTO `sys_role_menu`
VALUES ('114', '2', '14');
INSERT INTO `sys_role_menu`
VALUES ('115', '2', '15');
INSERT INTO `sys_role_menu`
VALUES ('116', '2', '16');
INSERT INTO `sys_role_menu`
VALUES ('117', '2', '17');
INSERT INTO `sys_role_menu`
VALUES ('118', '2', '18');
INSERT INTO `sys_role_menu`
VALUES ('119', '2', '19');
INSERT INTO `sys_role_menu`
VALUES ('120', '2', '20');
INSERT INTO `sys_role_menu`
VALUES ('121', '2', '21');
INSERT INTO `sys_role_menu`
VALUES ('122', '2', '22');
# end insert role_menu


# sys_product
DROP TABLE IF EXISTS `sys_product`;
CREATE TABLE `sys_product`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `productName`   varchar(64) DEFAULT NULL COMMENT '【产品名】',
    `productType`   varchar(64) DEFAULT NULL COMMENT '【产品类型】',
    `quality`       bigint(20) NOT NULL COMMENT '【产品规格】',
    `price`         bigint(20) NOT NULL COMMENT '【产品价格】',
    `created`       datetime   NOT NULL COMMENT '【产品上架时间】',
    `productStatus` int(5)     NOT NULL COMMENT '【产品状态】1、上架 0、下架',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `sys_product`
VALUES ('1', '柚子', '水果', '100', '100', '2021-12-07 10:00:00', '1');


# sys_order
DROP TABLE IF EXISTS `sys_order`;
CREATE TABLE `sys_order`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT,
    `virtualOrderNum`  varchar(255) NOT NULL COMMENT '【虚拟订单编号】',
    `realOrderNum`     varchar(255) DEFAULT NULL COMMENT '【真实订单编号】',
    `platform`         varchar(255) NOT NULL COMMENT '【招商平台】',
    `virtualOrderTime` datetime     NOT NULL COMMENT '【招商下单时间】',
    `realOrderTime`    datetime     DEFAULT NULL COMMENT '【真实下单时间】',
    `orderStatus`      bigint(20)   NOT NULL COMMENT '【招商下单状态】1、已处理 0、未处理',
    `orderProductId`   bigint(20)   NOT NULL COMMENT '【下单产品-关联产品id】',
    `orderQuality`     bigint(20)   NOT NULL COMMENT '【下单产品规格】',
    `orderPrice`       bigint(20)   DEFAULT NULL COMMENT '【下单产品总价】',
    `orderRemark`      varchar(255) DEFAULT NULL COMMENT '【下单备注】',
    `orderHandler`     varchar(255) DEFAULT NULL COMMENT '【订单处理人】',
    `orderFare`        bigint(20)   DEFAULT NULL COMMENT '【订单运费】',
    `logPlatform`      varchar(255) DEFAULT NULL COMMENT '【物流平台】',
    `logStatus`        varchar(255) DEFAULT NULL COMMENT '【物流状态，地址信息】',
    `senderName`       varchar(255) NOT NULL COMMENT '【发货人】',
    `senderPhone`      varchar(255) NOT NULL COMMENT '【发货人电话】',
    `receiverName`     varchar(255) NOT NULL COMMENT '【收货人】',
    `receiverPhone`    varchar(255) NOT NULL COMMENT '【收货人电话】',
    `receiverAddress`  varchar(255) NOT NULL COMMENT '【收货人地址】',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;



