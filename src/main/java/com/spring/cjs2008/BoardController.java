package com.spring.cjs2008;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.cjs2008.pagination.PageProcess;
import com.spring.cjs2008.pagination.PageVo;
import com.spring.cjs2008.service.BoardService;
import com.spring.cjs2008.vo.BoardReplyVo;
import com.spring.cjs2008.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	String msgFlag = "";
	
	@Autowired
	BoardService boardService;
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value="/bList", method=RequestMethod.GET)
	public String bListGet(HttpServletRequest request, Model model) {
		int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag"));
		int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize"));
		
		//PageProcess pageProcess = new PageProcess();
		
		PageVo pageVo = pageProcess.pagination(pag, pageSize, "board", "");
		
		List<BoardVo> vos = boardService.getBoardList(pageVo.getStartNo(),pageSize);
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		return "board/bList";
	}
//	@RequestMapping(value="/bList", method=RequestMethod.GET)
//	public String bListGet(HttpServletRequest request, Model model) {
//		// 페이징처리 준비작업
//		int totRecCnt = boardService.totBoardRecCnt();  // 게시글의 총 건수 구해오기
//		int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag"));
//		int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize"));
//		int blockSize = 3;  // 한블록의 크기 3개로 설정
//		int totPage = (totRecCnt % pageSize) == 0 ? totRecCnt/pageSize : (int)(totRecCnt/pageSize) + 1;  //총 페이지수 구하기
//		int startNo = (pag - 1) * pageSize;  // 한페이지에 보여줄 시작 인덱스번호를 구한다.
//		int curScrNo = totRecCnt - startNo;  // 해당페이지의 시작번호 구하기.
//		// 페이징 처리 준비완료
//		
//		List<BoardVo> vos = boardService.getBoardList(startNo, pageSize);
//		model.addAttribute("vos", vos);
//		
//		model.addAttribute("pag", pag);
//		model.addAttribute("pageSize", pageSize);
//		model.addAttribute("blockSize", blockSize);
//		model.addAttribute("totPage", totPage);
//		model.addAttribute("curScrNo", curScrNo);
//		
//		return "board/bList";
//	}
	
	@RequestMapping(value="/bInput", method=RequestMethod.GET)
	public String bInputGet() {
		return "board/bInput";
	}
	
	@RequestMapping(value="/bInput", method=RequestMethod.POST)
	//public String bInputPost(BoardVo vo, HttpServletRequest request) {
	public String bInputPost(BoardVo vo) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		@SuppressWarnings("deprecation")
		String uploadPath = request.getRealPath("/resources/ckeditor/images/");  // ckeditor를 통해서 저장되는 그림파일의 경로
		
		vo.setContent(vo.getContent().replace("/resources/ckeditor/images/", "/resources/ckeditor/images/src/")); // 실제로 서버에 저장되는 경로
		
		// 44는 이미지 파일이 시작하는 첫 위치 : src="/cjs2008/resources/ckeditor/images/src/그림파일.jsp"
		boardService.imgCheck(vo.getContent(), uploadPath, 44); // 이미지를 발췌해서 'src'폴더로 복사처리
		
		boardService.setBoardInput(vo); // 잘 정비된 vo를 DB에 저장한다.
		
		msgFlag = "bInputOk";
		return "redirect:/msg/" + msgFlag;
	}
	
	@RequestMapping(value="/bContent", method=RequestMethod.GET)
	public String bContentGet(HttpServletRequest request, Model model) {
		int idx = Integer.parseInt(request.getParameter("idx"));
		int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag"));
		int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize"));
		
		// 원본글 내역 가져오기
		BoardVo vo = boardService.getBoardContent(idx);  //원본글의 idx
		
		//댓글 내용 가져오기
		List<BoardReplyVo> cVos = boardService.getBoardReply(idx);
		
		//PageVo pageVo = pageProcess.pagination(pag, pageSize, "board");
		//model.addAttribute("pageVo", pageVo);
		
		model.addAttribute("vo", vo);
		model.addAttribute("cVos", cVos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		return "board/bContent";
	}
	
	@RequestMapping(value="/boardUpd", method=RequestMethod.GET)
	public String boardUpdGet(HttpServletRequest request, Model model, HttpSession session) {
		int idx = Integer.parseInt(request.getParameter("idx"));
		int pag = Integer.parseInt(request.getParameter("pag"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String pwd = request.getParameter("pwd");
		
		BoardVo vo = boardService.passwordCheck(idx, pwd);  // 비밀번호 체크
		
		if(vo == null) {  // 비밀번호가 틀렸으면 메세지 출력후 bContent.jsp 로 보낸다.
			msgFlag = "boardUpdNo$idx="+idx+"&pag="+pag+"&pageSize="+pageSize;
			return "redirect:/msg/" + msgFlag;
		}
		// 비밀번호가 맞으면 아래 작업 수행...
		// 1.수정작업처리전에 기존 그림파일이 있는 src폴더의 파일을 images폴더로 복사(backup)시켜 놓는다.
		@SuppressWarnings("deprecation")
		String uploadPath = request.getRealPath("/resources/ckeditor/images/");
		boardService.imgBackup(vo.getContent(), uploadPath);
		
		PageVo pageVo = pageProcess.pagination(pag, pageSize, "board", "");
		model.addAttribute("pageVo", pageVo);
		
		model.addAttribute("vo", vo);
		
		return "board/bUpdate";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/boardUpd", method=RequestMethod.POST)
	public String boardUpdPost(HttpServletRequest request, BoardVo vo, Model model) {
		// 기존의 내용을 변경시켰고, content안에 'src='태그속성이 있다면 그림파일이 변경처리되어 있다고 가정하여, images방의 그림파일을 src폴더로 복사처리해야 한다.
		if(!vo.getOriContent().equals(vo.getContent()) && vo.getContent().indexOf("src=\"/") != -1) {
			// 앞에서(수정처리를 위해 boardUpdGet메소드 수행후) 이미지의 위치가 'src'폴더에서 images로 변경된다.
			// 따라서 DB에 저장된 실제 그림의 경로도 변경시켜줘야 한다.
			vo.setContent(vo.getContent().replace("/resources/ckeditor/images/src/","/resources/ckeditor/images/"));
			
			// 현재 서버에 저장(위치:'/src/')되어 있는 이미지를 삭제처리한다.
			@SuppressWarnings("deprecation")
			String uploadPath = request.getRealPath("/resources/ckeditor/images/src/");
			boardService.imgDelete(vo.getOriContent(), uploadPath);  // 이미지 삭제처리
			
			// 새롭게 업로드를 위한 이미지를 'src'폴더에 다시 재등록한다.
			uploadPath = request.getRealPath("/resources/ckeditor/images/");
			// 40는 이미지 파일이 시작하는 첫 위치 : src="/cjs2008/resources/ckeditor/images/그림파일.jsp"
			boardService.imgCheck(vo.getContent(), uploadPath, 40);  // content필드안의 그림파일들을 모두 images폴더에서 src폴더로 복사한다.
			  
			// 다시 이미지의 경로를 원래대로 바꿔준다. 즉 'images'폴더에서 'images/src'폴더로 변경한다.
			vo.setContent(vo.getContent().replace("/resources/ckeditor/images/","/resources/ckeditor/images/src/"));
			}
			// 잘 정비된 vo자료를 DB에 update시켜준다.
			boardService.setBoardUpdate(vo);
			
			int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag"));
			int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize"));
			msgFlag = "boardUpdateOk$idx="+vo.getIdx()+"&pag="+pag+"&pageSize="+pageSize;
			
			return "redirect:/msg/" + msgFlag;
			}
			@RequestMapping(value="/boardDel",method=RequestMethod.GET)
			public String boardDelGet (HttpServletRequest request,int idx,String pwd,int pag,int pageSize) {
			BoardVo vo = boardService.passwordCheck(idx, pwd);
			if(vo == null) {
				msgFlag = "boardDelNo$idx="+idx+"&pag="+pag+"&pageSize="+pageSize;
				return "redirect:/msg/" + msgFlag;
			}
			//실제 서버에 저장된 그림파일을 삭제처리
			@SuppressWarnings("deprecation")
			String uploadPath = request.getRealPath("/resources/ckeditor/images/src/");
			boardService.imgDelete(vo.getContent(), uploadPath);
			
			//DB에서 실제 게시글을 삭제처리
			boardService.setBoardDelete(idx);
			
			msgFlag = "bDeleteOk$pag="+pag+"&pageSize="+pageSize;
			
			return "redirect:/msg/"+msgFlag;
		}
			
		//댓글(댓글의 level은 0)
		@ResponseBody
		@RequestMapping(value="/bReplyInsert",method=RequestMethod.GET)
		//public int bReplyInsertGet(int boardIdx, String nickname, String hostip, String content) {
		public int bReplyInsertGet(BoardReplyVo cVo) {
			//현재 본문글에 해당하는 댓글의 levelOrder값을 구해온다(댓글 없으면 levelOrder는 0이고 댓글이 있으면 levelOrder의 최대값보다 1씩 커야한다)
			String strLevelOrder = boardService.maxLevelOrder(cVo.getBoardIdx());  //levelOrder가 없을경우 null이 발생 int는 오류
			int levelOrder = 0;  //첫번째 댓글
			if(strLevelOrder != null) levelOrder = Integer.parseInt(strLevelOrder) + 1; //levelOrder 번호부여
			cVo.setLevelOrder(levelOrder);
			
			boardService.setReplyInsert(cVo);
			return 1;
		}
		
  @ResponseBody
  @RequestMapping(value="/bReplyDelete", method=RequestMethod.GET)
  public int bReplyDeleteGet(int idx) {
  	boardService.setReplyDelete(idx);
  	
  	return 1;
  }
  
  //  부모댓글의 답변글(대댓글)입력처리
  @ResponseBody
  @RequestMapping(value="/bContReplyInsert", method=RequestMethod.GET)
  public int bContReplyInsertGet(BoardReplyVo cVo) {
  	boardService.levelOrderPlusUpdate(cVo);   // 부모댓글보다 큰 모든 댓글의 levelOrder값를 부모댓글의 levelOrder값보다 +1큰값으로 지정(update)
    cVo.setLevel(cVo.getLevel()+1);           // 자신의 level은 부모level보다 +1한다.  	
  	cVo.setLevelOrder(cVo.getLevelOrder()+1); // 자신의 levelOrder은 부모levelOrder보다 +1한다.
  	
  	boardService.bContReplyInsert(cVo);       // 모든 설정이 끝난 cVo에 담긴 내용을 대댓글 테이블에 저장한다. 
  	
  	return 1;
  }
}
