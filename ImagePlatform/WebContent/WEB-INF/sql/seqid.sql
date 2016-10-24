CREATE TABLE `seqId` (
`PICID`  bigint NOT NULL ,
`DATETIME`  date NULL 
);
INSERT INTO seqId(PICID,DATETIME) VALUES (0,CURDATE());

DELIMITER //
CREATE PROCEDURE `getPicStep`()
BEGIN
DECLARE t_error INTEGER DEFAULT 0;
DECLARE isOutDate DATE;
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error =1;
START TRANSACTION;
	UPDATE seqId SET seqId.PICID = seqId.PICID;
	SET isOutDate:= (SELECT seqId.DATETIME FROM seqId);
	
		IF (isOutDate != CURDATE()) THEN
		UPDATE seqId SET seqId.PICID = 0,seqId.DATETIME = NOW();
		SET @picId = 0;
	ELSE
		UPDATE seqId SET seqId.PICID = seqId.PICID+10000;
		SET @picId:= (SELECT seqId.PICID  from seqId);
	END IF;

	IF t_error = 1 THEN
			ROLLBACK;
		ELSE
		COMMIT;
		END IF;

	SELECT @picId as picId,t_error;
END //
DELIMITER; 
