create table guest2 (
  idx   int not null auto_increment primary key,  /* 고유번호 */
  name  varchar(20) not null,                     /* 방문자 이름 */
  email varchar(60),                                  /* 이메일 주소 */
  homepage varchar(60),                           /* 홈페이지(블로그)주소 */
  vdate datetime default now(),                   /* 방문일자 */
  hostip varchar(50) not null,                      /* 방문자 IP */
  content text not null                               /* 방문소감 */
);

drop table guest;
select * from guest2;

insert into guest2 values(default,'관리자','admin@naver.com','www.admin.com',default,'218.203.236.79','방명록 서비스 개시');
insert into guest2 values(default,'관리자','admin@naver.com','www.admin.com',default,'218.203.236.79','방명록 서비스 개시');

desc guest2;