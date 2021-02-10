/* notify.sql */
create table notify (
  idx     int not null auto_increment primary key,
  name    varchar(20)  not null,
  title   varchar(100) not null,
  content text not null,
  startDate datetime default now(),
  endDate   datetime default now(),
  popupSw   char(1)  default 'N'
);

desc notify;

insert into notify (name,title,content) values ('관리자','구정맞이 특가 이벤트 행사','2021년 구정을 맞이하여 특별가격 행사를 진행합니다.');
insert into notify values (default,'관리자','구정맞이 특가 이벤트 행사','2021년 구정을 맞이하여 특별가격 행사를 진행합니다.',default,default,default);

select * from notify order by idx desc;
