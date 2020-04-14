CREATE TABLE test01(
	tno int,
    title varchar(50)
);

CREATE TABLE MEMBERS(
	mno integer not null comment '회원일련번호',
    email varchar(40) not null comment '이메일',
    pwd varchar(100) not null comment '암호',
    mname varchar(50) not null comment '이름',
    cre_date datetime not null comment '가입일',
    mod_date datetime not null comment '마지막암호변경일'
)
COMMENT '회원기본정보';

ALTER TABLE members ADD CONSTRAINT pk_members 
PRIMARY KEY (mno);

CREATE UNIQUE INDEX uix_members ON members
(email ASC);

ALTER TABLE members
	MODIFY COLUMN mno INTEGER NOT NULL AUTO_INCREMENT
    COMMENT '회원일련번호';
    
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s1@test.com','1111','아이유',NOW(),NOW());
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s2@test.com','1111','트와이스사나',NOW(),NOW());
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s3@test.com','1111','블랙핑크제니',NOW(),NOW());
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s4@test.com','1111','차은우',NOW(),NOW());
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s5@test.com','1111','박보검',NOW(),NOW());

COMMIT;

SELECT * FROM members;