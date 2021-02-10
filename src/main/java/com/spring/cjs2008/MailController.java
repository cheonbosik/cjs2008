package com.spring.cjs2008;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.cjs2008.vo.MailVo;

@Controller
@RequestMapping("/mail")
public class MailController {
	String msgFlag = "";

	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping(value="/mailForm", method=RequestMethod.GET)
	public String mailFormGet() {
		
		return "mail/mailForm";
	}
	
	// 일반 메일발송시의 설정
	@RequestMapping(value="/mailForm", method=RequestMethod.POST)
	public String mailFormPost(MailVo vo) {
		//String fromMail = "cbsk1126@gmail.com";
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			// 메일보관함에 저장
			//messageHelper.setFrom(fromMail);
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			
			// 메세지 내용과 함께 사진을 전송한다.
			content = content.replace("\n", "<br/>");
			content += "<br><hr><h3>CJ GREEN입니다.<h3><hr><br>";
			content += "<p><img src=\"cid:dog3.jpg\" width='250px'></p><hr>";
			content += "<p>오늘도 행복한 시간들 되세요~~~~</p>";
			content += "<p><a href='http://218.236.203.76:9090/cjs2008'>CJ Green</a></p>";
			messageHelper.setText(content, true);
			FileSystemResource file = new FileSystemResource(new File("D:/images/dog3.jpg"));
			messageHelper.addInline("dog3.jpg", file);
			
			// 메일과 함께 첨부파일 전송하기
			FileSystemResource mfile = new FileSystemResource(new File("D:/images/1.jpg"));
			messageHelper.addAttachment("1.jpg", mfile);
			
			mailSender.send(message);  // 실제 메일 전송
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		msgFlag = "mailSendOk";
		return "redirect:/msg/" + msgFlag;
	}
	
	/*
	// 비밀번호 분실시에 임시비밀번호를 반송하기 위한 메일설정
	@RequestMapping(value="/pwdConfirmMailForm/{toMail}/{content}/", method=RequestMethod.GET)
	public String pwdConfirmMailFormGet(@PathVariable String toMail, @PathVariable String content) {
		String fromMail = "cbsk1126@gmail.com";
		String title = "비밀번호 확인 메일입니다.";
		String pwd = content;
		content = "그린나라에서 발송한 메일입니다.\n아래 임시 비밀번호를 보내오니 사이트에 접속하셔서 비밀번호를 변경하세요\n";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			// 메일보관함에 저장
			messageHelper.setFrom(fromMail);
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			//messageHelper.setText(content);
			
			// 메세지 내용과 함께 사진을 전송한다.
			content = content.replace("\n", "<br/>");
			content += "<font color=red>"+pwd+"</font><br>";
			content += "<br><hr><h3>CJ GREEN입니다.<h3><hr><br>";
			content += "<p><img src=\"cid:cj.jpg\" width='500px'></p><hr>";
			content += "<p>오늘도 행복한 시간들 되세요~~~~</p>";
			content += "<p><a href='http://218.236.203.76:9090/cjs2008'>CJ Green</a></p>";
			messageHelper.setText(content, true);
			FileSystemResource file = new FileSystemResource(new File("D:/images/cj.jpg"));
			messageHelper.addInline("cj.jpg", file);
			
			// 메일과 함께 첨부파일 전송하기
//			FileSystemResource mfile = new FileSystemResource(new File("D:/images/1.jpg"));
//			messageHelper.addAttachment("1.jpg", mfile);
			
			mailSender.send(message);  // 실제 메일 전송
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		msgFlag = "pwdConfirmOk";
		return "redirect:/msg/" + msgFlag;
	}
	*/
	
	// RedirectAttributes를 이용한 vo에 담은 전송방법
	@RequestMapping(value="/pwdConfirmMailForm", method=RequestMethod.GET)
	public String pwdConfirmMailFormGet(MailVo mailVo) {
		String fromMail = "cbsk1126@gmail.com";
		String title = "비밀번호 확인 메일입니다.";
		String pwd = mailVo.getPwd();
		String content = "그린나라에서 발송한 메일입니다.\n아래 임시 비밀번호를 보내오니 사이트에 접속하셔서 비밀번호를 변경하세요\n";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			// 메일보관함에 저장
			messageHelper.setFrom(fromMail);
			messageHelper.setTo(mailVo.getToMail());
			messageHelper.setSubject(title);
			//messageHelper.setText(content);
			
			// 메세지 내용과 함께 사진을 전송한다.
			content = content.replace("\n", "<br/><br/>");
			content += "임시 비밀번호 => <font color=red>"+pwd+"</font><br>";
			content += "<br><hr><h3>CJ GREEN입니다.<h3><hr><br>";
			content += "<p><img src=\"cid:cj.jpg\" width='500px'></p><hr>";
			content += "<p>오늘도 행복한 시간들 되세요~~~~</p>";
			content += "<p>방문하기 : <a href='http://218.236.203.76:9090/cjs2008'>CJ Green</a></p>";
			messageHelper.setText(content, true);
			FileSystemResource file = new FileSystemResource(new File("D:/images/cj.jpg"));
			messageHelper.addInline("cj.jpg", file);
			
			// 메일과 함께 첨부파일 전송하기
//			FileSystemResource mfile = new FileSystemResource(new File("D:/images/1.jpg"));
//			messageHelper.addAttachment("1.jpg", mfile);
			
			mailSender.send(message);  // 실제 메일 전송
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		msgFlag = "pwdConfirmOk";
		return "redirect:/msg/" + msgFlag;
	}
}
