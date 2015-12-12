package com.ncwcandroid.ui.bean;
/**
 * 存放三方登陆时用户属性的对象
 * @author Administrator
 *
 */
public class UserInfo {
	private String userIcon;
	private String userName;
	private Gender userGender;
	private String userNote;
	

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Gender getUserGender() {
		return userGender;
	}

	public void setUserGender(Gender userGender) {
		this.userGender = userGender;
	}

	public String getUserNote() {
		return userNote;
	}

	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}

	public static enum Gender {BOY, GIRL}

}