ALTER TABLE CANDIDATE
    MODIFY RSM_ID INT DEFAULT NULL UNIQUE;

ALTER TABLE CANDIDATE
    DROP INDEX CN_EMAIL,
    DROP INDEX CN_PHONE;