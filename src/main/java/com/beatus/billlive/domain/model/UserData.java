package com.beatus.billlive.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 * @author Abhinav Akey
 *
 */
@Entity
@Table(name="USER")
public class UserData {

	@Id
	@Column(name="uid")
	private String uid;
	private String firstName;
	private String lastName;	
	private String company;
	private String legalOrTradingName;
	private String lineOfBusiness;
	private String organizationDesc;
	private String gstRegistrationNum;
	private String website;
	private String address;
	private String country;
	private String state;	
	private String district;
	private String city;
	private String phoneNumber;
	private String email;
	private String typeOfUser;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getLegalOrTradingName() {
		return legalOrTradingName;
	}
	public void setLegalOrTradingName(String legalOrTradingName) {
		this.legalOrTradingName = legalOrTradingName;
	}
	public String getLineOfBusiness() {
		return lineOfBusiness;
	}
	public void setLineOfBusiness(String lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}
	public String getOrganizationDesc() {
		return organizationDesc;
	}
	public void setOrganizationDesc(String organizationDesc) {
		this.organizationDesc = organizationDesc;
	}
	public String getGstRegistrationNum() {
		return gstRegistrationNum;
	}
	public void setGstRegistrationNum(String gstRegistrationNum) {
		this.gstRegistrationNum = gstRegistrationNum;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}