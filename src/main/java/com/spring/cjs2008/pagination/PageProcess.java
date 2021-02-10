package com.spring.cjs2008.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cjs2008.dao.BoardDao;
import com.spring.cjs2008.dao.GuestDao;
import com.spring.cjs2008.dao.PdsDao;

@Service
public class PageProcess {
	@Autowired
	BoardDao boardDao;
	
	@Autowired
	GuestDao guestDao;
	
	@Autowired
	PdsDao pdsDao;
	
	public PageVo pagination(int pag, int pageSize, String partFlag, String partValue) {
		int blockSize = 3;
		int totRecCnt = 0;
		
		PageVo pageVo = new PageVo();
		
		if(partFlag.equals("board")) {
		  totRecCnt = boardDao.totBoardRecCnt();
		}
		else if(partFlag.equals("guest")) {
			totRecCnt = guestDao.totRecCnt();
	  }
		else if(partFlag.equals("pds")) {
			totRecCnt = pdsDao.totRecCnt(partValue);
	  }
		
		int totPage = (totRecCnt % pageSize) == 0 ? totRecCnt/pageSize : (int)(totRecCnt/pageSize) + 1;  //총 페이지수 구하기
		int startNo = (pag - 1) * pageSize;  // 한페이지에 보여줄 시작 인덱스번호를 구한다.
		int curScrNo = totRecCnt - startNo;  // 해당페이지의 시작번호 구하기.
		
		pageVo.setPag(pag);
		pageVo.setPageSize(pageSize);
		pageVo.setBlockSize(blockSize);
		pageVo.setTotRecCnt(totRecCnt);
		pageVo.setTotPage(totPage);
		pageVo.setStartNo(startNo);
		pageVo.setCurScrNo(curScrNo);
		
		return pageVo;
	}
}
