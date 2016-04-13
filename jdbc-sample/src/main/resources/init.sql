DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id`       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(64)  NOT NULL,
  `gender`   VARCHAR(8)   NOT NULL,
  `birthday` DATE         NULL,
  `email`    VARCHAR(128) NULL,
  `password` VARCHAR(128) NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户';