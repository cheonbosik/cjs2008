package com.spring.cjs2008.vo;

import lombok.Data;

public @Data class BoardVo {
  private int idx;
  private String mid;
  private String name;
  private String title;
  private String email;
  private String homepage;
  private String pwd;
  private String wdate;
  private int readnum ;
  private String hostip;
  private String content;
  
  private String oriContent;  // 변경되기전의 원본 content를 저장시켜두기위한 필드
  private int replyNum;       // 현재글의 댓글수를 담기위한 필드
  private int diffTime;				// 새글을 24시간 기준으로 체크해서 답해주기위한 필드
}
