package com.spring.cjs2008.vo;

public class MailVo {
	private String toMail;
	private String title;
	private String content;
	
	private String mid;   // mid와 pwd는 비밀번호 분실시 새로 발생하여 메일로 보내기위해 추가선언
	private String pwd;
	
	public String getToMail() {
		return toMail;
	}
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "MailVo [toMail=" + toMail + ", title=" + title + ", content=" + content + ", mid=" + mid + ", pwd=" + pwd
				+ "]";
	}
}
