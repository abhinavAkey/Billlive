package com.beatus.billlive.domain.model;

/**
s * @author Abhinav Akey
 *
 */

public class UserData {

	private String uid;
	private String email;
	private String isAdmin;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}