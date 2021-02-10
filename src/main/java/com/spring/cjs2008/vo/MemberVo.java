package com.spring.cjs2008.vo;

public class MemberVo {
	private int idx;
	private String mid;
	private String pwd;
	private String name;
	private String nickname;
	private String gender;
	private String birthday;
	private String tel;
	private String email;
	private String homepage;
	private String address;
	private String job;
	private String hobby;
	private String joinday;
	private String photo;
	private String content;
	private int point;
	private int level;
	private String userInfor;
	private String userDel;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getJoinday() {
		return joinday;
	}
	public void setJoinday(String joinday) {
		this.joinday = joinday;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getUserInfor() {
		return userInfor;
	}
	public void setUserInfor(String userInfor) {
		this.userInfor = userInfor;
	}
	public String getUserDel() {
		return userDel;
	}
	public void setUserDel(String userDel) {
		this.userDel = userDel;
	}
	@Override
	public String toString() {
		return "MemberVo [idx=" + idx + ", mid=" + mid + ", pwd=" + pwd + ", name=" + name + ", nickname=" + nickname
				+ ", gender=" + gender + ", birthday=" + birthday + ", tel=" + tel + ", email=" + email + ", homepage="
				+ homepage + ", address=" + address + ", job=" + job + ", hobby=" + hobby + ", joinday=" + joinday + ", photo="
				+ photo + ", content=" + content + ", point=" + point + ", level=" + level + ", userInfor=" + userInfor
				+ ", userDel=" + userDel + "]";
	}
}
