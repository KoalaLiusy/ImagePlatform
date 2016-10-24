CREATE TABLE `bizInfo` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`bizId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`picId`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`appId`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务归属id' ,
PRIMARY KEY (`id`)
)
;

ALTER TABLE `bizInfo`
ADD UNIQUE INDEX `bizId_index` (`picId`) ;