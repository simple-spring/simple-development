-- auto-generated definition
create table t_demo
(
    id           bigint auto_increment
        primary key,
    gmt_create   datetime    null comment '创建时间',
    creator      varchar(64) null comment '创建人',
    gmt_modified datetime    null comment '修改时间',
    modifier     varchar(64) null comment '修改人',
    is_deleted   varchar(1)  null comment '是否删除',
    name         varchar(32) null,
    value        varchar(32) null
)
    comment 'demo表' charset = utf8;