package com.beatus.billlive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("parameterValidationConfigurer")
public class ParameterValidationConfigurer {
	
	/**
     * Regular Expressions for the request data ------------------------------------------------------
     *//*
    @Value("${parameter.validation.name.regEx}")                        private String nameRegEx;
    @Value("${parameter.validation.uiType.regEx}")                      private String uiTypeRegEx;
    @Value("${parameter.validation.lang.regEx}")                        private String langRegEx;
    @Value("${parameter.validation.authLev.regEx}")                     private String authLevRegEx;
    @Value("${parameter.validation.asqAnswer.regEx}")                   private String askAnswerRegEx;
    @Value("${parameter.validation.username.regEx}")                    private String usernameRegEx;
    @Value("${parameter.validation.countryCode.regEx}")                 private String countryCodeRegEx;
    @Value("${parameter.validation.phone.regEx}")                       private String phoneRegEx;
    @Value("${parameter.validation.delivery.regEx}")                    private String deliveryRegEx;
    @Value("${parameter.validation.confirmCode.regEx}")                 private String confirmCodeRegEx;
    @Value("${parameter.validation.cell.regEx}")                        private String cellRegEx;
    @Value("${parameter.validation.email.regEx}")                       private String emailRegEx;
    @Value("${parameter.validation.asqQuestion.regEx}")                 private String asqQuestionRegEx;
    @Value("${parameter.validation.day.regEx}")                         private String dayRegEx;
    @Value("${parameter.validation.month.regEx}")                       private String monthRegEx;
    @Value("${parameter.validation.year.regEx}")                        private String yearRegEx;
    @Value("${parameter.validation.mobileVerificationCode.regEx}")      private String mobileVerificationCodeRegEx;
    @Value("${parameter.validation.deleteCell.regEx}")                  private String deleteCellRegEx;
    @Value("${parameter.validation.recoveryOption.regEx}")              private String recoveryOptionRegEx;
    @Value("${parameter.validation.locale.regEx}")                      private String localeRegEx;
    @Value("${parameter.validation.gender.regEx}")                      private String genderRegEx;
    @Value("${parameter.validation.postCode.regEx}")                    private String postCodeRegEx;
    @Value("${parameter.validation.captchaCode.regEx}")                 private String captchaCodeRegEx;
    @Value("${parameter.validation.captchaRef.regEx}")                  private String captchaRefRegEx;
    @Value("${parameter.validation.confCodeSent.regEx}")                private String confCodeSentRegEx;
    @Value("${parameter.validation.siteDomain.regEx}")                  private String siteDomainRegEx;
    @Value("${parameter.validation.displayName.regEx}")                 private String displayNameRegEx;
    @Value("${parameter.validation.mobile.phone.regEx}")                private String mobilePhoneRegEx;
    @Value("${parameter.validation.appDeviceName.regEx}")               private String appDeviceNameRegEx;
    @Value("${parameter.validation.twofaPhoneType.regEx}")              private String twofaPhoneTypeRegEx;

    public String getNameRegEx() {
		return nameRegEx;
	}
	public void setNameRegEx(String nameRegEx) {
		this.nameRegEx = nameRegEx;
	}
	public String getUiTypeRegEx() {
		return uiTypeRegEx;
	}
	public void setUiTypeRegEx(String uiTypeRegEx) {
		this.uiTypeRegEx = uiTypeRegEx;
	}
	public String getLangRegEx() {
		return langRegEx;
	}
	public void setLangRegEx(String langRegEx) {
		this.langRegEx = langRegEx;
	}
	public String getAuthLevRegEx() {
		return authLevRegEx;
	}
	public void setAuthLevRegEx(String authLevRegEx) {
		this.authLevRegEx = authLevRegEx;
	}
	public String getAskAnswerRegEx() {
		return askAnswerRegEx;
	}
	public void setAskAnswerRegEx(String askAnswerRegEx) {
		this.askAnswerRegEx = askAnswerRegEx;
	}
	public String getUsernameRegEx() {
		return usernameRegEx;
	}
	public void setUsernameRegEx(String usernameRegEx) {
		this.usernameRegEx = usernameRegEx;
	}
	public String getCountryCodeRegEx() {
		return countryCodeRegEx;
	}
	public void setCountryCodeRegEx(String countryCodeRegEx) {
		this.countryCodeRegEx = countryCodeRegEx;
	}
	public String getPhoneRegEx() {
		return phoneRegEx;
	}
	public void setPhoneRegEx(String phoneRegEx) {
		this.phoneRegEx = phoneRegEx;
	}
	public String getDeliveryRegEx() {
		return deliveryRegEx;
	}
	public void setDeliveryRegEx(String deliveryRegEx) {
		this.deliveryRegEx = deliveryRegEx;
	}
	public String getConfirmCodeRegEx() {
		return confirmCodeRegEx;
	}
	public void setConfirmCodeRegEx(String confirmCodeRegEx) {
		this.confirmCodeRegEx = confirmCodeRegEx;
	}
	public String getCellRegEx() {
		return cellRegEx;
	}
	public void setCellRegEx(String cellRegEx) {
		this.cellRegEx = cellRegEx;
	}
	public String getEmailRegEx() {
		return emailRegEx;
	}
	public void setEmailRegEx(String emailRegEx) {
		this.emailRegEx = emailRegEx;
	}
	public String getAsqQuestionRegEx() {
		return asqQuestionRegEx;
	}
	public void setAsqQuestionRegEx(String asqQuestionRegEx) {
		this.asqQuestionRegEx = asqQuestionRegEx;
	}
	public String getDayRegEx() {
		return dayRegEx;
	}
	public void setDayRegEx(String dayRegEx) {
		this.dayRegEx = dayRegEx;
	}
	public String getMonthRegEx() {
		return monthRegEx;
	}
	public void setMonthRegEx(String monthRegEx) {
		this.monthRegEx = monthRegEx;
	}
	public String getYearRegEx() {
		return yearRegEx;
	}
	public void setYearRegEx(String yearRegEx) {
		this.yearRegEx = yearRegEx;
	}
	public String getMobileVerificationCodeRegEx() {
		return mobileVerificationCodeRegEx;
	}
	public void setMobileVerificationCodeRegEx(String mobileVerificationCodeRegEx) {
		this.mobileVerificationCodeRegEx = mobileVerificationCodeRegEx;
	}
	public String getDeleteCellRegEx() {
		return deleteCellRegEx;
	}
	public void setDeleteCellRegEx(String deleteCellRegEx) {
		this.deleteCellRegEx = deleteCellRegEx;
	}
	public String getRecoveryOptionRegEx() {
		return recoveryOptionRegEx;
	}
	public void setRecoveryOptionRegEx(String recoveryOptionRegEx) {
		this.recoveryOptionRegEx = recoveryOptionRegEx;
	}
	public String getLocaleRegEx() {
		return localeRegEx;
	}
	public void setLocaleRegEx(String localeRegEx) {
		this.localeRegEx = localeRegEx;
	}
	public String getGenderRegEx() {
		return genderRegEx;
	}
	public void setGenderRegEx(String genderRegEx) {
		this.genderRegEx = genderRegEx;
	}
	public String getPostCodeRegEx() {
		return postCodeRegEx;
	}
	public void setPostCodeRegEx(String postCodeRegEx) {
		this.postCodeRegEx = postCodeRegEx;
	}
	public String getCaptchaCodeRegEx() {
		return captchaCodeRegEx;
	}
	public void setCaptchaCodeRegEx(String captchaCodeRegEx) {
		this.captchaCodeRegEx = captchaCodeRegEx;
	}
	public String getCaptchaRefRegEx() {
		return captchaRefRegEx;
	}
	public void setCaptchaRefRegEx(String captchaRefRegEx) {
		this.captchaRefRegEx = captchaRefRegEx;
	}
	public String getConfCodeSentRegEx() {
		return confCodeSentRegEx;
	}
	public void setConfCodeSentRegEx(String confCodeSentRegEx) {
		this.confCodeSentRegEx = confCodeSentRegEx;
	}
	public String getSiteDomainRegEx() {
		return siteDomainRegEx;
	}
	public void setSiteDomainRegEx(String siteDomainRegEx) {
		this.siteDomainRegEx = siteDomainRegEx;
	}
	public String getDisplayNameRegEx() {
		return displayNameRegEx;
	}
	public void setDisplayNameRegEx(String displayNameRegEx) {
		this.displayNameRegEx = displayNameRegEx;
	}
	public String getMobilePhoneRegEx() {
		return mobilePhoneRegEx;
	}
	public void setMobilePhoneRegEx(String mobilePhoneRegEx) {
		this.mobilePhoneRegEx = mobilePhoneRegEx;
	}
	public String getAppDeviceNameRegEx() {
		return appDeviceNameRegEx;
	}
	public void setAppDeviceNameRegEx(String appDeviceNameRegEx) {
		this.appDeviceNameRegEx = appDeviceNameRegEx;
	}
	public String getTwofaPhoneTypeRegEx() {
		return twofaPhoneTypeRegEx;
	}
	public void setTwofaPhoneTypeRegEx(String twofaPhoneTypeRegEx) {
		this.twofaPhoneTypeRegEx = twofaPhoneTypeRegEx;
	}*/
}
