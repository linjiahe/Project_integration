/* 用户表*/
CREATE TABLE user (
  user_id      VARCHAR(100) NOT NULL PRIMARY KEY
  COMMENT '用户id',

  recommend_id VARCHAR(100) NOT NULL
  COMMENT '推荐人id',

  login_name   VARCHAR(100) NOT NULL
  COMMENT '登录名称',

  password     VARCHAR(100) NOT NULL
  COMMENT '登录密码',

  nick_name    VARCHAR(100) NULL     DEFAULT NULL
  COMMENT '昵称',

  user_icon    VARCHAR(100) NULL     DEFAULT NULL
  COMMENT '头像',

  open_id      VARCHAR(100) NULL     DEFAULT NULL
  COMMENT '微信openId',

  create_time  DATETIME(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time  DATETIME(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP
);

/* 商户用户表*/
CREATE TABLE merchant_user (
  merchant_user_id VARCHAR(100) NOT NULL PRIMARY KEY
  COMMENT '用户id',

  login_name       VARCHAR(255) NOT NULL
  COMMENT '登录名称',

  password         VARCHAR(255) NOT NULL
  COMMENT '登录密码',

  role_id          VARCHAR(255) NOT NULL
  COMMENT '角色id',

  nick_name        VARCHAR(100) NULL     DEFAULT NULL
  COMMENT '商户邮箱',

  create_time      DATETIME(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time      DATETIME(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP
);

/* 分类表*/
CREATE TABLE category (
  cat_id      VARCHAR(100) NOT NULL PRIMARY KEY
  COMMENT '店铺id',

  parent_id   VARCHAR(100) NOT NULL
  COMMENT '上级id',

  cat_name    VARCHAR(100) NOT NULL
  COMMENT '分类名称',

  cat_icon    VARCHAR(100) NOT NULL
  COMMENT '分类图标',

  level_num   SMALLINT(6)  NOT NULL
  COMMENT '分类1=一级,2=二级,3=三级',

  status      TINYINT(1)   NOT NULL  DEFAULT 1
  COMMENT '1有效，0无效',


  create_time DATETIME(0)  NOT NULL  DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME(0)  NOT NULL  DEFAULT CURRENT_TIMESTAMP
);

/* 资讯*/
CREATE TABLE information (
  id            VARCHAR(100) NOT NULL PRIMARY KEY
  COMMENT '资讯id',

  cat_id        VARCHAR(255) NOT NULL
  COMMENT '分类id',

  title         VARCHAR(255) NULL      DEFAULT NULL
  COMMENT '标题',

  content       VARCHAR(255) NULL      DEFAULT NULL
  COMMENT '内容',

  excerpt       VARCHAR(255) NULL      DEFAULT NULL
  COMMENT '摘要',

  online_status TINYINT(1)   NOT NULL  DEFAULT 1
  COMMENT '上架状态（ture上架，fales 下架）',

  collect_num   INT(10)      NOT NULL  DEFAULT 0
  COMMENT '收藏数量',

  news_img      VARCHAR(255) NULL      DEFAULT NULL
  COMMENT '资讯图片',

  create_time   DATETIME(0)  NOT NULL  DEFAULT CURRENT_TIMESTAMP,
  update_time   DATETIME(0)  NOT NULL  DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE information_detail (
  information_id VARCHAR(100)  NOT NULL
  COMMENT '商品id' PRIMARY KEY,
  package_detail VARCHAR(1000) NULL,
  detail         TEXT          NULL,
  create_time    DATETIME      NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '添加时间',
  update_time    DATETIME      NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '编辑时间'
);

CREATE TABLE bill (
  id           VARCHAR(100) NOT NULL
  COMMENT '账单id' PRIMARY KEY,

  user_id      VARCHAR(100) NOT NULL
  COMMENT '用户id',

  asset_type   VARCHAR(100) NULL
  COMMENT '资产类型',

  inorout_type VARCHAR(100) NULL
  COMMENT '收支类型',

  amount       DECIMAL(12, 2)         DEFAULT 0.00
  COMMENT '额度',

  deal_type    VARCHAR(100) NULL
  COMMENT '交易类型',

  create_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '添加时间',

  update_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '更新时间'
);


# 有关于积分钱包的表
# 每个用户id，对应唯一的钱包
# 创建会员就要生成这个表

create table wallet_account(
  account_id           VARCHAR(100) NOT NULL
  COMMENT '账号地址' PRIMARY KEY,

  user_id           VARCHAR(100) NOT NULL
  COMMENT '业务系统用户id' ,

  user_name           VARCHAR(100) NOT NULL
  COMMENT '用户名称' ,

  account_status           VARCHAR(100) NOT NULL
  COMMENT '账号状态，有效，暂时锁定，永久锁定' ,

  account_block_end_time           datetime  NULL
  COMMENT '锁定有效期' ,

  remark           VARCHAR(500)  NULL
  COMMENT '备注信息' ,

  create_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '添加时间',

  update_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '更新时间'


);


# 子账号类型表
# 对应每个账户都有一个子账号
# 默认可以有一个字账号，子账号是账号的组成和用途
# 例如，赠送，充值


create table wallet_sub_account_type(

  sub_account_type           VARCHAR(100) NOT NULL COMMENT '子账号类型',
  sub_account_type_desc         VARCHAR(200) NOT NULL COMMENT '子账号说明',
  category                      VARCHAR(200) NOT NULL comment '子账号分类',
  create_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '添加时间'

);



#子账号操作

create table wallet_sub_account(

  sub_account_id           VARCHAR(100) NOT NULL
  COMMENT '子账号地址' PRIMARY KEY,
  account_id           VARCHAR(100) NOT NULL
  COMMENT '账号地址' PRIMARY KEY,
  sub_account_type           VARCHAR(100) NOT NULL
  COMMENT '子账号类型' PRIMARY KEY,
  total_aivilable  DECIMAL(20,4) not null default 0 COMMENT '所有的余额',
  available_aivilable  DECIMAL(20,4) not null default 0 COMMENT '可用余额',
  block_aivilable  DECIMAL(20,4) not null default 0 COMMENT '冻结余额',
  sub_account_status TINYINT(1) not null DEFAULT  1 COMMENT '账号是否在用',
  create_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '添加时间',

  update_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '更新时间'


);


#交易类型表
create table wallet_translog_type(

  trans_type           VARCHAR(100) NOT NULL
  COMMENT '交易类型，必须是在类型表里面指定' PRIMARY KEY,


  trans_type_name           VARCHAR(100) NOT NULL
  COMMENT '交易类型，必须是在类型表里面指定',

  trans_config VARCHAR(100) NOT NULL COMMENT '交易规则',

  trans_director VARCHAR(100) not null default 'IN 流入，OUT流出，INNER 内部互转' COMMENT '所有的余额',

  create_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '添加时间'


);

#交易记录表
create table wallet_translog(

  trans_id           VARCHAR(100) NOT NULL
  COMMENT '日志表主键' PRIMARY KEY,

  rela_trans_id           VARCHAR(100)
  COMMENT '关联的操作id，类似于冻结解冻操作',


  trans_type           VARCHAR(100) NOT NULL
  COMMENT '交易类型，必须是在类型表里面指定' ,

  account_id           VARCHAR(100) NOT NULL
  COMMENT '账号地址' PRIMARY KEY,

  sub_account_id           VARCHAR(100) NOT NULL
  COMMENT '子账号地址' PRIMARY KEY,

  sub_account_type           VARCHAR(100) NOT NULL
  COMMENT '子账号类型' PRIMARY KEY,

  trans_title VARCHAR(100) not null default 0 COMMENT '交易标题',

  trans_amount DECIMAL(20,4) not null default 0 COMMENT '交易金额',

  trans_director VARCHAR(100) not null default null COMMENT 'IN 流入，OUT流出，INNER 内部互转',

  source_balance DECIMAL(20,4) not null default 0 COMMENT '交易前余额',

  tageet_balance DECIMAL(20,4) not null default 0 COMMENT '交易后余额',


  reverse_status  TINYINT(1)    NOT NULL default 0, COMMENT '是否冲正' ,

  display_status  TINYINT(1)    NOT NULL default 1, COMMENT '是否显示',

  is_block_trans  TINYINT(1)    not NULL default 0, COMMENT '是否需要冻结操作',

  block_end_time  DATETIME     NULL default 1, COMMENT '冻结结束时间',

  remark   VARCHAR(500) NOT NULL COMMENT '该交易备注信息 ' ,
  create_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '添加时间',

  update_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '更新时间'

);

#交易日结表
create table wallet_daily_summary(
  daily_summary_id     VARCHAR(100) NOT NULL
  COMMENT '日结表ID主键' PRIMARY KEY,
  account_id           VARCHAR(100) NOT NULL
  COMMENT '账号地址' ,
  daily_name           VARCHAR(100) NOT NULL
  COMMENT '日台账' ,

  trans_amount DECIMAL(20,4) not null default 0 COMMENT '今日交易金额',

  trans_in_amount DECIMAL(20,4) not null default 0 COMMENT '今日流入金额',

  trans_out_amount DECIMAL(20,4) not null default 0 COMMENT '今日流出金额',

  source_balance DECIMAL(20,4) not null default 0 COMMENT '初始化余额',

  tageet_balance DECIMAL(20,4) not null default 0 COMMENT '最后一笔变化情况',
  create_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '添加时间',

  update_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '更新时间'


);

create table admin(
  admin_id  VARCHAR(100) NOT NULL COMMENT 'id',
  name VARCHAR(100) NOT NULL COMMENT '用户名',
  password VARCHAR(100) NOT NULL COMMENT '密码',
  update_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '更新时间',
  create_time  DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '添加时间'
);