package com.spring.cjs2008.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cjs2008.dao.BoardDao;
import com.spring.cjs2008.vo.BoardReplyVo;
import com.spring.cjs2008.vo.BoardVo;

@Service
public class BoardServiceImp implements BoardService {
	@Autowired
	BoardDao boardDao;

	@Override
	public void setBoardInput(BoardVo vo) {
		//System.out.println("vo: " + vo);
		boardDao.setBoardInput(vo);
	}

	@Override
	public List<BoardVo> getBoardList(int startNo, int pageSize) {
		return boardDao.getBoardList(startNo, pageSize);
	}

	@Override
	public BoardVo getBoardContent(int idx) {
		return boardDao.getBoardContent(idx);
	}

	@Override
	public void imgCheck(String content, String uploadPath, int position) {
		if(content.indexOf("src=\"/") == -1) return; // content안에 그림파일이 없으면 작업을 수행하지 않는다.
		//            0         1         2         3         4         5         6
		//            01234567890123456789012345678901234567890123456789012345678901234567890
		//<img alt="" src="/cjs2008/resources/ckeditor/images/src/210201125255+0900_m13.jpg" style="height:400px; width:600px" />
		
		//int position = 44;
		boolean sw = true;
		
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));  // 순수한 그림파일만 가져온다.
			
			String oriFilePath = uploadPath + imgFile;  // 원본파일이 있는 경로명과 파일명
			String copyFilePath = uploadPath + "src/" + imgFile;  // 복사될파일이 있는 경로명과 파일명
			
			fileCopyCheck(oriFilePath, copyFilePath);  // images폴더에서 images/src폴도로 파일 복사작업처리
			
			// nextImg변수안에 또다른 'src="/'문구가 있는지를 검색하여, 있다면 다시 앞의 작업을 반복수행한다.
			if(nextImg.indexOf("src=\"/") == -1) {
				sw = false;
			}
			else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
			}
		}
	}

	private void fileCopyCheck(String oriFilePath, String copyFilePath) {
		File oriFile = new File(oriFilePath);
		File copyFile = new File(copyFilePath);
		
		try {
			FileInputStream fis = new FileInputStream(oriFile);
			FileOutputStream fos = new FileOutputStream(copyFile);
			
			byte[] buffer = new byte[1024];
			int readCnt = 0;
			while((readCnt=fis.read(buffer)) !=  -1) {
				fos.write(buffer, 0, readCnt);
			}
			fos.flush();
			fis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int totBoardRecCnt() {
		return boardDao.totBoardRecCnt();
	}

	@Override
	public BoardVo passwordCheck(int idx, String pwd) {
		return boardDao.passwordCheck(idx, pwd);
	}

	@Override
	public void imgBackup(String content, String uploadPath) {
		if(content.indexOf("src=\"/") == -1) return; // content안에 그림파일이 없으면 작업을 수행하지 않는다.
		//            0         1         2         3         4         5         6
		//            01234567890123456789012345678901234567890123456789012345678901234567890
		//<img alt="" src="/cjs2008/resources/ckeditor/images/src/210201125255+0900_m13.jpg" style="height:400px; width:600px" />
		
		int position = 44;
		boolean sw = true;
		
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));  // 순수한 그림파일만 가져온다.
			
			String oriFilePath = uploadPath + "src/" + imgFile;  // 원본파일이 있는 경로명과 파일명
			String copyFilePath = uploadPath + imgFile;  // 복사될파일이 있는 경로명과 파일명
			
			fileCopyCheck(oriFilePath, copyFilePath);  // images폴더에서 images/src폴도로 파일 복사작업처리
			
			// nextImg변수안에 또다른 'src="/'문구가 있는지를 검색하여, 있다면 다시 앞의 작업을 반복수행한다.
			if(nextImg.indexOf("src=\"/") == -1) {
				sw = false;
			}
			else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
			}
		}
	}

	@Override
	public void imgDelete(String uploadPath, String oriContent) {
		if(oriContent.indexOf("src=\"/") == -1) return; // content안에 그림파일이 없으면 작업을 수행하지 않는다.
		//            0         1         2         3         4         5         6
		//            01234567890123456789012345678901234567890123456789012345678901234567890
		//<img alt="" src="/cjs2008/resources/ckeditor/images/src/210201125255+0900_m13.jpg" style="height:400px; width:600px" />
		
		int position = 44;
		boolean sw = true;
		
		String nextImg = oriContent.substring(oriContent.indexOf("src=\"/")+position);
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));  // 순수한 그림파일만 가져온다.
			
			String delFilePath = uploadPath + imgFile;  // 원본파일이 있는 경로명과 파일명
			
			fileDeleteCheck(delFilePath);  // 실제로 파일을 지워주는 메소드호출
			
			// nextImg변수안에 또다른 'src="/'문구가 있는지를 검색하여, 있다면 다시 앞의 작업을 반복수행한다.
			if(nextImg.indexOf("src=\"/") == -1) {
				sw = false;
			}
			else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
			}
		}
	}
	// 게시물의 그림을 실제로 지워주는 메소드
	private void fileDeleteCheck(String delFilePath) {    
		File delFile = new File(delFilePath);
		if(delFile.exists()) delFile.delete();
	}

	@Override
	public void setBoardUpdate(BoardVo vo) {
		boardDao.setBoardUpdate(vo);
	}

	@Override
	public void setBoardDelete(int idx) {
		boardDao.setBoardDelete(idx);
	}

	@Override
	public String maxLevelOrder(int boardIdx) {
		return boardDao.maxLevelOrder(boardIdx);
	}

	@Override
	public void setReplyInsert(BoardReplyVo cVo) {
		boardDao.setReplyInsert(cVo);
	}

	@Override
	public List<BoardReplyVo> getBoardReply(int boardIdx) {
		return boardDao.getBoardReply(boardIdx);
	}

	@Override
	public void setReplyDelete(int idx) {
		boardDao.setReplyDelete(idx);
	}

	@Override
	public void levelOrderPlusUpdate(BoardReplyVo cVo) {
		boardDao.levelOrderPlusUpdate(cVo);
	}

	@Override
	public void bContReplyInsert(BoardReplyVo cVo) {
		boardDao.bContReplyInsert(cVo);
	}

}
