CREATE TABLE `picInfo` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`picId`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`picName`  varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`picType`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' ,
`picPath`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`expiryDate`  date NULL DEFAULT NULL ,
`reservedValue1`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`reservedValue2`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`reservedValue3`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
;

ALTER TABLE `picInfo`
ADD UNIQUE INDEX `picId_index` (`picId`) ;

//--ALTER TABLE `picInfo` ADD CONSTRAINT `for_picId` FOREIGN KEY (`picId`) REFERENCES `bizInfo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
