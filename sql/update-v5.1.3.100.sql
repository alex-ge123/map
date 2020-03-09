USE `virsical_map`;

alter table svg add direction smallint(1) NOT NULL DEFAULT 0 COMMENT '文字方向';
