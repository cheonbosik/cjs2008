show tables;

create table pds2 (
  idx      int not null auto_increment primary key,
  mid      varchar(20)  not null,
  nickname varchar(20)  not null,								-- 별명
  fname    varchar(100) not null,								/* 업로드한 파일명 */
  rfname   varchar(100) not null,								/* 서버에 저장되는 실제 파일명 */
  title    varchar(100) not null,								/* 파일에 대한 간단 설명(제목) */
  part     varchar(20)  not null,								/* 파일 분류 */
  pwd      varchar(20),
  fdate    datetime  not null default now(),		/* 파일 업로드한 날짜 */
  fsize    int,																	/* 파일 크기 */
  downnum  int not null default 0,     					/* 다운 횟수 */
  opensw   varchar(10) not null default '공개',  /* 자료 공개 여부(공개/비공개) */
  content  text																	/* 자료파일의 상세 내역 */
);

desc pds2;
insert into pds2 values (default,'hkd1234','홍장군','1.jpg','1.jpg','연습','전체','1234','2021-02-10',0,default,default,'연습입니다.');

select * from pds2 order by idx desc;
