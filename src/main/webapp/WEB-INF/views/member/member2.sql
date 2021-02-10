/* member2.sql */
create table member2 (
  idx   int not null auto_increment,			/* 회원 고유번호 */
  mid   varchar(20)  not null,						/* 회원 아이디(중복불허) */
  pwd   varchar(100) not null,						/* 비밀번호(암호화처리) */
  name  varchar(20)  not null,						/* 성명 */
  nickname varchar(20) not null, 					/* 별명(중복불허) */
  gender   char(2)   not null,						/* 성별 */
  birthday datetime  default now(),				/* 생일 */
  tel		varchar(15)  not null,						/* 전화번호 */
  email varchar(50)  not null,						/* 이메일(아이디 또는 비밀번호 분실시에 체크할 항목) */
  homepage varchar(50),										/* 홈페이지(블로그주소등) */
  address  varchar(60),										/* 주소(다음 우편번호 API사용) */
  job      varchar(10),										/* 직업 */
  hobby    varchar(50) default '',  			/* 취미 */
  joinday  datetime  default now(),				/* 가입일 */
  photo    varchar(100) default '1.jpg',	/* 회원 사진 */
  content  text,													/* 자기소개 */
  point		 int not null default 0,				/* 구매 포인트 */
  level		 int not null default 4,				/* 0:관리자 , 1:특별회원 , 2:우수회원 , 3:정회원 , 4:준회원 */
  userInfor char(2) default 'OK',					/* 정보공개(OK:공개 , NO:비공개) */
  userDel   char(2) default 'NO',					/* 탈퇴신청(NO:가입중 , OK:탈퇴신청중) */
  primary key(idx, mid),									/* 기본키(고유번호, 아이디) */
  unique(mid, nickname)										/* 중복불허키(아이디, 별명) */
);
desc member2;
/* drop table member2 */

insert into member2 values (default,'admin','1234','관리자','관리맨','남자','2000-01-01','010-3423-2704','cjsk1126@naver.com','blog.daum.net/cjsk1126','(12345)경기도 안성시 공도로 142','학생','등산',default,default,'관리자 입니다.',default,0,default,default);
delete from member2 where mid='admin';
update member2 set userDel='NO' where mid='kms1234';
select * from member2 order by idx desc;
