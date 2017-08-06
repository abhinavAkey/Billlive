package com.beatus.billlive.domain.model;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementations
 * @author Abhinav Akey
 *
 */

public class CompanyUsers {
	
	private String companyId;
	private String Uid;
	private String postId;
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getUid() {
		return Uid;
	}
	public void setUid(String uid) {
		Uid = uid;
	}

	

}