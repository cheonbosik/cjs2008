package com.spring.cjs2008.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2008.dao.StudyDao;

@Service
public class StudyServiceImp implements StudyService {
	@Autowired
	StudyDao studyDao;

	@Override
	public void getCalendar() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 1.오늘 날짜를 저장
		Calendar calToday = Calendar.getInstance();
		int toYear = calToday.get(Calendar.YEAR);
		int toMonth = calToday.get(Calendar.MONTH);
		int toDay = calToday.get(Calendar.DATE);
		
		// 2.화면에 보여줄 해당년월 셋팅을 위한 부분
		Calendar calView = Calendar.getInstance();//화면에 표시할 날짜
		int yy = request.getParameter("yy") == null ? calView.get(Calendar.YEAR) : Integer.parseInt(request.getParameter("yy"));
		int mm = request.getParameter("mm") == null ? calView.get(Calendar.MONTH) : Integer.parseInt(request.getParameter("mm"));
		
		if( mm < 0 ) { // 1월에서 전월 버튼을 클릭시에 실행
			yy--;
			mm=11;
		}
		if( mm > 11) { // 12월에서 다음월 버튼을 클릭시에 실행함
			yy++;
			mm=0;
		}

		calView.set(yy,mm,1);//해당 년, 월의 첫날을 셋팅 
		
		int startWeek = calView.get(Calendar.DAY_OF_WEEK);//해당 년/월의 1일에 해당하는 요일값을 가져온다
		int lastDay = calView.getActualMaximum(Calendar.DAY_OF_MONTH);//해당월의 마지막일을 구한다
		
		// 3.화면에 보여줄 년월기준 전년도 다음년도를 위한 부분
		int preYear = yy; //전년도
		int preMonth = mm - 1; // 전월
		int nextYear = yy; //다음년도
		int nextMonth = mm + 1; //다음월
		
		if(preMonth == -1) { //1월에서 전월 버튼을 클릭시에 실행함
			preYear--;
			preMonth = 11;
		}
		
		if(nextMonth == 12) { //12월에서 다음월 버튼을 클릭시에 실행함
			nextYear++;
			nextMonth = 0;
		}
		Calendar calPre = Calendar.getInstance();//이전달력
		calPre.set(preYear, preMonth,1);//이전달력셋팅
		int preLastDay = calPre.getActualMaximum(Calendar.DAY_OF_MONTH);//이전달력의 마지막일을 구한다
		
		Calendar calNext = Calendar.getInstance();//다음달력
		calNext.set(nextYear, nextMonth,1);//다음달력셋팅
		int nextStartWeek = calNext.get(Calendar.DAY_OF_WEEK);//다음달력의 1일에 해당하는 요일값을 가져온다
		
		// 4.화면에 표시할 해당사용자의 해당월의 일자별 입금총액과 지출총액 처리
//		int[] ipkumArr = new int[lastDay];//해당월의 마지막날값으로 입금 배열생성
//		int[] jichulArr = new int[lastDay];//해당월의 마지막날값으로 지출 배열생성
//		for(int i=0; i<lastDay;i++) {
//			String ymd = yy + "-" + (mm+1) + "-" + (i+1);
//			//System.out.println(ymd);
//			ipkumArr[i] = dao.userMoneyIpkumList(smid,ymd,"입금");
//			jichulArr[i] = dao.userMoneyjichulList(smid,ymd,"지출");
//			//System.out.println(i + "입금 : " + ipkumArr[i] + "/ 지출 : " + jichulArr[i]);
//		}
		
		// 5. 해당사용자에 대한 해당월별 지출에대한 현금,신용카드,체크카드 사용내역
		// 해당 sql쿼리 처리시 dataformat 비교형식에 맞춰주기 위해 아래와 같이 처리 > 현재 1월~9월은 2020-1 이런식으로 출력되므로 2020-01 이렇게 바꿔주어야한다 
		String ym = "";
		int tmpMM = (mm+1);
		if(tmpMM >= 1 && tmpMM <=9) {
			ym = yy + "-" + "0" + (mm+1);
		}
		else {
			ym = yy + "-" + (mm+1);
		}
//		List<Money_Vo> jichulKubunVos = dao.jichulKubunList(smid,ym,"지출");
		
		/*======================= setAttribute ===========================*/
		//오늘기준 달력
		request.setAttribute("toYear", toYear);		
		request.setAttribute("toMonth", toMonth);		
		request.setAttribute("toDay", toDay);		
		
		//화면에 보여줄 해당 달력
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("startWeek", startWeek);
		request.setAttribute("lastDay", lastDay);
		
		//화면에 보여줄 해당 달력 기준 전년도,전년월,다음년도,다음월 달력
		request.setAttribute("preYear", preYear);
		request.setAttribute("preMonth", preMonth);
		request.setAttribute("preLastDay", preLastDay);
		request.setAttribute("nextYear", nextYear);
		request.setAttribute("nextMonth", nextMonth);
		request.setAttribute("nextStartWeek", nextStartWeek);
		
		//해당사용자의 입금액,지출액 등등 금액관련 부분
//		request.setAttribute("ipkumArr", ipkumArr);
//		request.setAttribute("jichulArr", jichulArr);
//		request.setAttribute("jichulKubunVos", jichulKubunVos);
	}

	@Override
	public int fileUpload(MultipartFile mFile, String file) {
		int res = 0;
		try {
			// 파일업로드 처리
			String oFileName = mFile.getOriginalFilename();
			//String preFileName = oFileName.substring(0,oFileName.lastIndexOf("."));  // 순수 파일명 추출
			//String oFileExt = oFileName.substring(oFileName.lastIndexOf(".")+1);     // 확장지 추출
			
			UUID uid = UUID.randomUUID(); // UUID객체를 통한 고유번호를 생성
			
			String saveFileName = uid + "_" + oFileName;
			
			writeFile(mFile, saveFileName);
			
			// 파일이 업로드 성공시에 DB에 정보를 저장한다.
			// studyDao.fileUpload(mFile, file)
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	private void writeFile(MultipartFile mFile, String saveFileName) throws IOException {
		byte[] data = mFile.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/member/");
		
		FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
		fos.write(data);
		
		fos.close();
	}
}
