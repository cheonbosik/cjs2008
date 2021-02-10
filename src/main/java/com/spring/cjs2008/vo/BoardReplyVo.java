package com.spring.cjs2008.vo;

import lombok.Data;

//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString
public @Data class BoardReplyVo {
	private int idx;
	private int boardIdx;
	private String mid;
	private String nickname;
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	//private Date wdate;
	private String wdate;
	private String hostip;
	private String content;
	private int level;
	private int levelOrder;
	
}
