-- 스키마 생성 및 세션 설정
CREATE SCHEMA `phonetail` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE phonetail;
SET SESSION FOREIGN_KEY_CHECKS=0;


SELECT * FROM hak WHERE model LIKE CONCAT('%Galaxy%') AND (sid = 'a' OR bid = 'a' ) ORDER BY lseq DESC;
SELECT * FROM hak WHERE model LIKE '%Galaxy%' AND (sid = 'a' OR bid = 'a') ORDER BY lseq DESC;

select*from member;
select*from product;
select*from question;
select * from wantlist;

-- 기존 테이블 삭제
-- DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS wantlist;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS chat;
DROP TABLE IF EXISTS chatlist;
DROP TABLE IF EXISTS report;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS member;

-- 테이블 생성
-- CREATE TABLE address
    -- (
--    zip_num varchar(15),
--    sido varchar(30),
--    gugun varchar(30),
--    dong varchar(30),
--    bunji varchar(20),
--    zip_code varchar(30)
          -- );

CREATE TABLE admin
(
    adminid varchar(45) NOT NULL,
    pwd varchar(45),
    name varchar(45),
    phone varchar(45),
    PRIMARY KEY (adminid)
);

CREATE TABLE member
(
    userid varchar(45) NOT NULL,
    pwd varchar(45) NOT NULL,
    name varchar(45) NOT NULL,
    phone varchar(45) NOT NULL,
    email varchar(100) NOT NULL,
    address1 varchar(100) NOT NULL,
    address2 varchar(100) NOT NULL,
    userstate char(1) DEFAULT 'Y' NOT NULL,
    indate datetime DEFAULT now() NOT NULL,
    PRIMARY KEY (userid)
);

CREATE TABLE product
(
    pseq int NOT NULL AUTO_INCREMENT,
    brand varchar(45) NOT NULL COMMENT 'company > brand로 수정',
    model varchar(45) NOT NULL COMMENT '카테고리 필터 용',
    price int NOT NULL,
    comment varchar(1000),
    image varchar(200),
    saveimagefile varchar(200) COMMENT 'no image가 있을수 있어서',
    sellstate char(1) DEFAULT 'N' COMMENT 'YorN',
    indate datetime DEFAULT now() NOT NULL,
    readcount int default 0,
    wantcount int default 0,
    userid varchar(45) NOT NULL,
    PRIMARY KEY (pseq)
);

CREATE TABLE chatlist(
                         lseq INT NOT NULL AUTO_INCREMENT,
                         sid varchar(45) NOT NULL,
                         bid varchar(45) NOT NULL,
                         pseq int NOT NULL,
                         PRIMARY KEY (lseq)
);

CREATE TABLE chat
(
    cseq int not null AUTO_INCREMENT,
    lseq int not null,
    userid varchar(45) not null,
    indate datetime not null DEFAULT now(),
    content varchar(500),
    PRIMARY KEY (cseq)
);

CREATE TABLE question
(
    qseq int NOT NULL AUTO_INCREMENT,
    title varchar(100) NOT NULL,
    content varchar(1000) NOT NULL,
    indate datetime DEFAULT now() NOT NULL,
    userid varchar(45) NOT NULL,
    readCount int default 0,
    secret boolean NOT NULL default FALSE,
    qreply varchar(1000),
    PRIMARY KEY (qseq)
);

CREATE TABLE report
(
    reseq int NOT NULL AUTO_INCREMENT,
    pseq int NOT NULL,
    userid varchar(45) NOT NULL,
    retype int NOT NULL COMMENT '--0 - 광고성 콘텐츠(거래와 관련없는 글)
   --1 - 상품정보 부정확
   --2 - 안전거래를 거부해요
   --3 - 사기가 의심돼요(외부 채널 유도)
   --4 - 전문업자 같아요
   --5 - 기타',
    recontent varchar(300) NOT NULL,
    restate char(1) DEFAULT 'N' NOT NULL COMMENT 'N:대기상태 Y:처리완료',
    -- 24.05.29 신고시 해당 날짜 추가해서 list에서 정렬기준을 삼을수 있게 필드 추가했습니다
    indate datetime DEFAULT now() NOT NULL,
    PRIMARY KEY (reseq)
);

CREATE TABLE `phonetail`.`wantlist` (
                                        `wseq` INT NOT NULL AUTO_INCREMENT,
                                        `pseq` INT NOT NULL,
                                        `userid` VARCHAR(45) NOT NULL,
                                        PRIMARY KEY (`wseq`),
                                        INDEX `pk1_idx` (`pseq` ASC) VISIBLE,
                                        INDEX `pk2_idx` (`userid` ASC) VISIBLE,
                                        CONSTRAINT `pk1`
                                            FOREIGN KEY (`pseq`)
                                                REFERENCES `phonetail`.`product` (`pseq`)
                                                ON DELETE CASCADE
                                                ON UPDATE CASCADE,
                                        CONSTRAINT `pk2`
                                            FOREIGN KEY (`userid`)
                                                REFERENCES `phonetail`.`member` (`userid`)
                                                ON DELETE CASCADE
                                                ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- 외래 키 추가
ALTER TABLE chat
    ADD CONSTRAINT fk_chat_chatlist
        FOREIGN KEY (lseq)
            REFERENCES chatlist(lseq);

ALTER TABLE question
    ADD FOREIGN KEY (userid) REFERENCES member (userid) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE report
    ADD FOREIGN KEY (userid) REFERENCES member (userid) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE report
    ADD FOREIGN KEY (pseq) REFERENCES product (pseq) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE chatlist
    ADD FOREIGN KEY (pseq) REFERENCES product (pseq) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE VIEW hak AS
SELECT cl.lseq, cl.sid, cl.bid, p.pseq, p.model, p.price
FROM chatlist cl
         JOIN product p ON cl.pseq = p.pseq;

CREATE VIEW hakk AS
SELECT
    p.model AS model,
    p.price AS price,
    p.pseq AS pseq,
    cl.lseq AS lseq,
    cl.sid AS sid,
    cl.bid AS bid,
    c.indate AS indate,
    c.content AS content
FROM product p JOIN chatlist cl ON p.pseq = cl.pseq JOIN chat c ON cl.lseq = c.lseq;


CREATE OR REPLACE VIEW wantlist_view
AS
SELECT
    p.pseq,
    p.brand,
    p.model,
    p.price,
    p.comment,
    p.image,
    p.saveimagefile,
    p.sellstate,
    p.indate,
    p.readcount,
    p.wantcount,
    p.userid AS puserid,
    w.userid AS wuserid
FROM
    product p
        JOIN
    wantlist w ON p.pseq = w.pseq;

-- 수정 전 new_product
create or replace view new_product
as
select pseq, model, price, image, saveimagefile, userid from product where sellstate='N' order by indate desc limit 3;

-- 수정된 new_product
CREATE OR REPLACE VIEW new_product AS
SELECT p.pseq, p.model, p.price, p.image, p.saveimagefile, p.userid
FROM product p
         JOIN member m ON p.userid = m.userid
WHERE p.sellstate = 'N'
  AND m.userstate NOT IN ('B', 'N')
ORDER BY p.indate DESC
LIMIT 3;

INSERT INTO admin (adminid, pwd, name, phone) VALUES
                                                  ('admin1', 'password1', '관리자1', '010-1234-5678'),
                                                  ('admin2', 'password2', '관리자2', '010-8765-4321'),
                                                  ('ad', 'ad', '관리자짱짱', '010-8765-1234');


INSERT INTO member (userid, pwd, name, phone, email, address1, address2, userstate, indate) VALUES
                                                                                                ('user1', 'pwd1', '사용자1', '010-1111-2222', 'user1@example.com', '서울특별시 강남구 삼성동', '123-45', 'Y', now()),
                                                                                                ('user2', 'pwd2', '사용자2', '010-3333-4444', 'user2@example.com', '부산광역시 해운대구 우동', '678-90', 'Y', now()),
                                                                                                ('user3', 'pwd3', '사용자3', '010-3333-4444', 'user2@example.com', '부산광역시 해운대구 우동', '678-90', 'Y', now()),
                                                                                                ('user4', 'pwd4', '사용자4', '010-3333-4444', 'user2@example.com', '부산광역시 해운대구 우동', '678-90', 'Y', now()),
                                                                                                ('user5', 'pwd5', '사용자5', '010-3333-4444', 'user2@example.com', '부산광역시 해운대구 우동', '678-90', 'Y', now()),
                                                                                                ('user6', 'pwd6', '사용자6', '010-3333-4444', 'user2@example.com', '부산광역시 해운대구 우동', '678-90', 'Y', now()),
                                                                                                ('user7', 'pwd7', '사용자7', '010-3333-4444', 'user2@example.com', '부산광역시 해운대구 우동', '678-90', 'Y', now()),
                                                                                                ('a', 'a', '테스트계정', '010-3333-4444', 'user2@example.com', '부산광역시 해운대구 우동', '678-90', 'Y', now()),
                                                                                                ('superman', 'kryptonite', 'Clark Kent', '123-456-7890', 'clark@dailyplanet.com', '123 Metropolis Lane', 'Apt 3B', 'Y', now()),
                                                                                                ('batman', 'robin', 'Bruce Wayne', '987-654-3210', 'bruce@wayneenterprises.com', '456 Wayne Manor', 'Gotham City', 'Y', now()),
                                                                                                ('spidey', 'webhead', 'Peter Parker', '555-555-5555', 'peter@bugle.com', '789 Queens Boulevard', 'Apt 10A', 'Y', now()),
                                                                                                ('wonderwoman', 'amazon', 'Diana Prince', '333-333-3333', 'diana@themyscira.com', 'Paradise Island', '', 'Y', now()),
                                                                                                ('flash', 'speedforce', 'Barry Allen', '777-777-7777', 'barry@ccpd.com', '123 Central City Blvd', 'Suite 4', 'Y', now()),
                                                                                                ('ironman', 'arcreactor', 'Tony Stark', '222-222-2222', 'tony@starkindustries.com', '10880 Malibu Point', 'Malibu, CA', 'Y', now()),
                                                                                                ('captainamerica', 'shield', 'Steve Rogers', '333-333-3333', 'steve@avengers.com', '569 Leaman Place', 'Brooklyn, NY', 'Y', now()),
                                                                                                ('thor', 'mjolnir', 'Thor Odinson', '444-444-4444', 'thor@asgard.com', 'Asgard Palace', 'Bifrost Bridge', 'Y', now()),
                                                                                                ('hulk', 'smash', 'Bruce Banner', '555-555-5555', 'bruce@smash.com', '123 Gamma Labs', 'Sakaar', 'Y', now()),
                                                                                                ('blackwidow', 'spies', 'Natasha Romanoff', '666-666-6666', 'natasha@redroom.com', '456 Red Room Street', 'Moscow, Russia', 'Y', now()),
                                                                                                ('hawkeye', 'arrows', 'Clint Barton', '777-777-7777', 'clint@bowandarrow.com', '789 Archer Avenue', 'New York, NY', 'Y', now()),
                                                                                                ('wolverine', 'adamantium', 'Logan', '888-888-8888', 'logan@xmen.com', '123 Xavier School', 'Westchester, NY', 'Y', now()),
                                                                                                ('storm', 'weather', 'Ororo Munroe', '999-999-9999', 'ororo@xmen.com', '456 Xavier School', 'Westchester, NY', 'Y', now()),
                                                                                                ('cyclops', 'beam', 'Scott Summers', '101-101-1010', 'scott@xmen.com', '789 Xavier School', 'Westchester, NY', 'Y', now()),
                                                                                                ('jeangrey', 'phoenix', 'Jean Grey', '202-202-2020', 'jean@xmen.com', '321 Xavier School', 'Westchester, NY', 'Y', now()),
                                                                                                ('beast', 'intellect', 'Henry McCoy', '303-303-3030', 'henry@xmen.com', '555 Xavier School', 'Westchester, NY', 'Y', now()),
                                                                                                ('iceman', 'ice', 'Bobby Drake', '404-404-4040', 'bobby@xmen.com', '777 Xavier School', 'Westchester, NY', 'Y', now());



INSERT INTO product (brand, model, price, comment, image, saveimagefile, sellstate, indate, userid) VALUES
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone13', 1000000, '아이폰 3년사용', 'iphone13.jpg', 'iphone13.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS21', 700000, '흠집 있음', 'GalaxyS21.jpg', 'GalaxyS21.jpg', 'Y', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ flip5', 500000, 'S급 상태', 'GalaxyZflip5.jpg', 'GalaxyZflip5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'G8 ThinQ', 300000, '물에 빠짐', 'LGG8ThinQ.jpg', 'LGG8ThinQ.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone14', 10000, '아이폰 5년사용', 'iphone14pro.jpg', 'iphone14pro.jpg', 'N', now(), 'user1'),
                                                                                                        ('Apple', 'iPhone15', 300000, '아이폰 3년사용', 'iphone15.jpg', 'iphone15.jpg', 'N', now(), 'user1'),
                                                                                                        ('Samsung', 'GalaxyS22', 500000, '흠집 있음', 'GalaxyS22.jpg', 'GalaxyS22.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyS24', 600000, '흠집 있음', 'GalaxyS24.jpg', 'GalaxyS24.jpg', 'N', now(), 'user2'),
                                                                                                        ('Samsung', 'GalaxyZ fold 5', 500000, 'S급 상태', 'GalaxyZfold5.jpg', 'GalaxyZfold5.jpg', 'N', now(), 'user1'),
                                                                                                        ('LG', 'V50', 300000, '물에 빠짐', 'LGV50SThinQ.jpg', 'LGV50SThinQ.jpg', 'N', now(), 'user1');



INSERT INTO chatlist (sid, bid, pseq) VALUES
                                          ('user1', 'a', 5),
                                          ('user2', 'a', 6),
                                          ('user2', 'ad', 6);

INSERT INTO chat (content, indate, userid, lseq) VALUES
                                                     ('언제 배송되나요?', now(), 'user2', 1),
                                                     ('왜 읽씹하시나요?', now(), 'a', 2);

INSERT INTO question (title, content, indate, userid) VALUES
                                                          ('제품 문의', '이 제품의 기능에 대해 알고 싶습니다.', now(), 'user1'),
                                                          ('배송 문의', '언제 배송되나요?', now(), 'user2'),
                                                          ('제품 문의', '이 제품의 기능에 대해 알고 싶습니다.', now(), 'user1'),
                                                          ('배송 문의', '언제 배송되나요?', now(), 'user2'),
                                                          ('환불 문의', '환불 절차가 궁금합니다.', now(), 'user1'),
                                                          ('AS 문의', '제품 AS는 어떻게 받나요?', now(), 'user2'),
                                                          ('제품 문의', '이 제품의 색상은 어떤가요?', now(), 'user1'),
                                                          ('배송 문의', '배송비는 얼마인가요?', now(), 'user2'),
                                                          ('환불 문의', '환불 기간은 얼마나 걸리나요?', now(), 'user1'),
                                                          ('AS 문의', 'AS 신청은 어디서 하나요?', now(), 'user2'),
                                                          ('제품 문의', '이 제품의 크기는 어떻게 되나요?', now(), 'user1'),
                                                          ('배송 문의', '배송 상태를 확인하고 싶습니다.', now(), 'user2'),
                                                          ('환불 문의', '환불 신청을 취소하고 싶습니다.', now(), 'user1'),
                                                          ('AS 문의', 'AS 진행 상황을 알고 싶습니다.', now(), 'user2'),
                                                          ('제품 문의', '이 제품은 재고가 있나요?', now(), 'user1'),
                                                          ('배송 문의', '해외 배송도 가능한가요?', now(), 'user2'),
                                                          ('환불 문의', '환불받을 계좌를 변경하고 싶습니다.', now(), 'user1'),
                                                          ('AS 문의', 'AS 비용은 얼마나 되나요?', now(), 'user2'),
                                                          ('제품 문의', '이 제품은 무게가 얼마인가요?', now(), 'user1'),
                                                          ('배송 문의', '배송 지연 사유가 궁금합니다.', now(), 'user2'),
                                                          ('환불 문의', '환불 상태를 확인하고 싶습니다.', now(), 'user1'),
                                                          ('AS 문의', 'AS 신청서를 분실했습니다.', now(), 'user2'),
                                                          ('제품 문의', '이 제품의 구성품은 무엇인가요?', now(), 'user1'),
                                                          ('배송 문의', '배송 날짜를 변경하고 싶습니다.', now(), 'user2'),
                                                          ('환불 문의', '환불 정책을 알고 싶습니다.', now(), 'user1'),
                                                          ('AS 문의', 'AS 신청 상태를 알고 싶습니다.', now(), 'user2'),
                                                          ('제품 문의', '이 제품은 어떤 소재로 만들어졌나요?', now(), 'user1'),
                                                          ('배송 문의', '배송 옵션을 변경하고 싶습니다.', now(), 'user2'),
                                                          ('환불 문의', '환불이 가능한지 알고 싶습니다.', now(), 'user1'),
                                                          ('AS 문의', 'AS 관련 문의입니다.', now(), 'user2'),
                                                          ('제품 문의', '이 제품의 보증 기간은 얼마인가요?', now(), 'user1'),
                                                          ('배송 문의', '배송 주소를 변경하고 싶습니다.', now(), 'user2'),
                                                          ('환불 문의', '환불 요청을 하고 싶습니다.', now(), 'user1'),
                                                          ('AS 문의', 'AS를 신청하려면 어떻게 해야 하나요?', now(), 'user2'),
                                                          ('제품 문의', '이 제품의 사용법을 알고 싶습니다.', now(), 'user1'),
                                                          ('배송 문의', '배송 중 파손이 걱정됩니다.', now(), 'user2'),
                                                          ('환불 문의', '환불 문의입니다.', now(), 'user1'),
                                                          ('AS 문의', 'AS 센터 위치가 어디인가요?', now(), 'user2'),
                                                          ('제품 문의', '이 제품의 사양이 궁금합니다.', now(), 'user1'),
                                                          ('배송 문의', '배송 시간대를 지정할 수 있나요?', now(), 'user2'),
                                                          ('환불 문의', '환불 절차를 알고 싶습니다.', now(), 'user1'),
                                                          ('AS 문의', 'AS 접수를 하고 싶습니다.', now(), 'user2');



INSERT INTO report (pseq, userid, retype, recontent, restate) VALUES
                                                                  (1, 'user1', 0, '광고성 콘텐츠입니다.', 'N'),
                                                                  (2, 'user2', 1, '상품 정보가 부정확합니다.', 'N'),
                                                                  (1, 'user1', 2, '안전거래를 거부합니다.', 'N'),
                                                                  (2, 'user2', 3, '사기가 의심됩니다.', 'N'),
                                                                  (1, 'user1', 4, '전문업자 같아요.', 'N'),
                                                                  (2, 'user2', 0, '거래와 관련없는 글입니다.', 'N'),
                                                                  (1, 'user1', 1, '정보가 부정확합니다.', 'N'),
                                                                  (2, 'user2', 2, '거래를 거부해요.', 'N'),
                                                                  (1, 'user1', 3, '사기 의심돼요.', 'N'),
                                                                  (2, 'user2', 4, '전문업자 같아요.', 'N'),
                                                                  (1, 'user1', 0, '광고성 콘텐츠입니다.', 'N'),
                                                                  (2, 'user2', 1, '상품 정보가 부정확합니다.', 'N'),
                                                                  (1, 'user1', 2, '안전거래를 거부합니다.', 'N'),
                                                                  (2, 'user2', 3, '사기가 의심됩니다.', 'N'),
                                                                  (1, 'user1', 4, '전문업자 같아요.', 'N'),
                                                                  (2, 'user2', 0, '거래와 관련없는 글입니다.', 'N'),
                                                                  (1, 'user1', 1, '정보가 부정확합니다.', 'N'),
                                                                  (2, 'user2', 2, '거래를 거부해요.', 'N'),
                                                                  (1, 'user1', 3, '사기 의심돼요.', 'N'),
                                                                  (2, 'user2', 4, '전문업자 같아요.', 'N'),
                                                                  (1, 'user1', 0, '광고성 콘텐츠입니다.', 'N'),
                                                                  (2, 'user2', 1, '상품 정보가 부정확합니다.', 'N'),
                                                                  (1, 'user1', 2, '안전거래를 거부합니다.', 'N'),
                                                                  (2, 'user2', 3, '사기가 의심됩니다.', 'N'),
                                                                  (1, 'user1', 4, '전문업자 같아요.', 'N'),
                                                                  (2, 'user2', 0, '거래와 관련없는 글입니다.', 'N'),
                                                                  (1, 'user1', 1, '정보가 부정확합니다.', 'N'),
                                                                  (2, 'user2', 2, '거래를 거부해요.', 'N'),
                                                                  (1, 'user1', 3, '사기 의심돼요.', 'N'),
                                                                  (2, 'user2', 4, '전문업자 같아요.', 'N'),
                                                                  (1, 'user1', 0, '광고성 콘텐츠입니다.', 'N'),
                                                                  (2, 'user2', 1, '상품 정보가 부정확합니다.', 'N'),
                                                                  (1, 'user1', 2, '안전거래를 거부합니다.', 'N'),
                                                                  (2, 'user2', 3, '사기가 의심됩니다.', 'N'),
                                                                  (1, 'user1', 4, '전문업자 같아요.', 'N'),
                                                                  (2, 'user2', 0, '거래와 관련없는 글입니다.', 'N'),
                                                                  (1, 'user1', 1, '정보가 부정확합니다.', 'N'),
                                                                  (2, 'user2', 2, '거래를 거부해요.', 'N'),
                                                                  (1, 'user1', 3, '사기 의심돼요.', 'N'),
                                                                  (2, 'user2', 4, '전문업자 같아요.', 'N');











