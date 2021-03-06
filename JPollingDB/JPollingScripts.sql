
--CREATE DATABASE JPOLLING_DB;
--GO
--USE JPOLLING_DB;




CREATE TABLE USERS(
	USER_ID BIGINT PRIMARY KEY auto_increment,
	PASSWORD VARCHAR(20) NOT NULL,
	MAIL_ID VARCHAR(50),
	USER_NAME VARCHAR(50)
	USER_TYPE VARCHAR(10)
);


CREATE TABLE QUESTIONS(
	Q_ID INT PRIMARY KEY auto_increment,
	QUESTION TEXT,
	START_DATE DATETIME,
	END_DATE DATETIME
);

CREATE TABLE ANSWERS(
	ANS_ID INT PRIMARY KEY auto_increment,
	Q_ID INT FOREIGN KEY REFERENCES QUESTIONS(Q_ID),
	ANSWER TEXT
);

CREATE TABLE QA_MAPPING(
	MAP_ID INT PRIMARY KEY auto_increment,
	USER_ID BIGINT FOREIGN KEY REFERENCES USERS(USER_ID),
	Q_ID INT FOREIGN KEY REFERENCES QUESTIONS(Q_ID),
	ANS_ID INT FOREIGN KEY REFERENCES ANSWERS(ANS_ID) NULL
);

CREATE TABLE qa(
	id INT PRIMARY KEY auto_increment,
	q_id INT,
	ans_id INT
);

alter table USERS add Unique_Id varchar(100) NOT NULL;

alter table USERS ADD UNIQUE(Unique_Id);
alter table USERS add verified tinyint default 0;
alter table USERS add address1 varchar(200);
alter table USERS add address2 varchar(200);
alter table USERS add city varchar(200);
alter table USERS add state varchar(200);
alter table USERS add polling_area varchar(300) NOT NULL DEFAULT 'Salem';

-- Clear
DELETE FROM QA_MAPPING
DELETE FROM ANSWERS
DELETE FROM QUESTIONS
DELETE FROM USERS



-- DROP 

DROP TABLE QA_MAPPING
DROP TABLE ANSWERS
DROP TABLE QUESTIONS
DROP TABLE USERS

