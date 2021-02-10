/* board2.sql */
create table board2 (
  idx   int not null auto_increment,
  mid   varchar(20) not null,
  name  varchar(20) not null,
  title varchar(100) not null,
  email varchar(60),
  homepage varchar(60),
  pwd   varchar(20) not null,
  wdate datetime default now(),
  readnum int default 0,
  hostip  varchar(50) not null,
  content text not null,
  primary key(idx)
);

drop table board2;
select * from board2;

/* 대댓글 달기 */
create table boardReply2 (
  idx      int not null auto_increment,
  boardIdx int not null,
  mid      varchar(20) not null,  /* 삭제시에 mid와 같을경우는 삭제처리 */
  nickname varchar(20) not null,
  wdate    datetime    not null default now(),
  hostip   varchar(50) not null,
  content  text not null,
  level    int not null default 0,
  levelOrder int not null default 0,
  primary key(idx),
  foreign key(boardIdx) references board2(idx)
);
desc boardCont2;
/* drop table boardCont2; */
/* delete from boardCont2; */
select * from boardReply2 order by idx desc;
