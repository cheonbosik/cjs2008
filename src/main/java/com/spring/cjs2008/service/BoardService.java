package com.spring.cjs2008.service;

import java.util.List;

import com.spring.cjs2008.vo.BoardReplyVo;
import com.spring.cjs2008.vo.BoardVo;

public interface BoardService {

	public void setBoardInput(BoardVo vo);

	public List<BoardVo> getBoardList(int startNo, int pageSize);

	public BoardVo getBoardContent(int idx);

	public void imgCheck(String content, String uploadPath, int position);

	public int totBoardRecCnt();

	public BoardVo passwordCheck(int idx, String pwd);

	public void imgBackup(String content, String uploadPath);

	public void imgDelete(String uploadPath, String oriContent);

	public void setBoardUpdate(BoardVo vo);

	public void setBoardDelete(int idx);

	public String maxLevelOrder(int boardIdx);

	public void setReplyInsert(BoardReplyVo cVo);

	public List<BoardReplyVo> getBoardReply(int boardIdx);

	public void setReplyDelete(int idx);

	public void levelOrderPlusUpdate(BoardReplyVo cVo);

	public void bContReplyInsert(BoardReplyVo cVo);

}
