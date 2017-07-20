package com.beatus.billlive.validation;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.beatus.billlive.config.ParameterValidationConfigurer;
import com.beatus.billlive.domain.model.RequestParameterValidationData;
import com.beatus.billlive.utils.Constants;

@Component("validatorFactory")
public class ValidatorFactory {
	
	//private final Logger LOG = LoggerFactory.getLogger(Constants.PARAMETER_VALIDATION_LOG);
	
	@Autowired
    private ParameterValidationConfigurer parameterValidationConfigurer;
	
	@Resource(name = "inputParameterRegExValidator")
	private InputParameterRegExValidator inputParameterRegExValidator;
	
	public void validate(RequestParameterValidationData reqData) throws ParameterValidationException{
		/*if(StringUtils.hasText(reqData.getFirstName())){
			logAndValidate(reqData.getFirstName(), parameterValidationConfigurer.getNameRegEx(), Constants.FIRST_NAME);
		}
		if(StringUtils.hasText(reqData.getLastName())){
			logAndValidate(reqData.getLastName(), parameterValidationConfigurer.getNameRegEx(), Constants.LAST_NAME);
		}
		if(StringUtils.hasText(reqData.getUiType())){
			logAndValidate(reqData.getUiType(), parameterValidationConfigurer.getUiTypeRegEx(), Constants.UI_TYPE);
		}
		if(StringUtils.hasText(reqData.getLang())){
			logAndValidate(reqData.getLang(), parameterValidationConfigurer.getLangRegEx(), Constants.LANG);
		}
		if(StringUtils.hasText(reqData.getAuthLev())){
			logAndValidate(reqData.getAuthLev(), parameterValidationConfigurer.getAuthLevRegEx(), Constants.AUTH_LEVEL);
		}
		if(StringUtils.hasText(reqData.getAskAnswer())){
			logAndValidate(reqData.getAskAnswer(), parameterValidationConfigurer.getAskAnswerRegEx(), Constants.ASK_ANSWER);
		}
		if(StringUtils.hasText(reqData.getUsername())){
			if(reqData.getUsername().indexOf("@") != -1){
				logAndValidate(reqData.getUsername(), parameterValidationConfigurer.getEmailRegEx(), Constants.USERNAME);
			}else {
				logAndValidate(reqData.getUsername(), parameterValidationConfigurer.getUsernameRegEx(), Constants.USERNAME);
			}
		}
		if(StringUtils.hasText(reqData.getCountryCode())){
			logAndValidate(reqData.getCountryCode(), parameterValidationConfigurer.getCountryCodeRegEx(), Constants.COUNTRY_CODE);
		}
		if(StringUtils.hasText(reqData.getPhone())){
			logAndValidate(reqData.getPhone(), parameterValidationConfigurer.getMobilePhoneRegEx(), Constants.PHONE);
		}
		if(StringUtils.hasText(reqData.getDelivery())){
			logAndValidate(reqData.getDelivery(), parameterValidationConfigurer.getDeliveryRegEx(), Constants.DELIVERY);
		}
		if(StringUtils.hasText(reqData.getConfirmCode())){
			logAndValidate(reqData.getConfirmCode(), parameterValidationConfigurer.getConfirmCodeRegEx(), Constants.CONFIRM_CODE);
		}
		if(StringUtils.hasText(reqData.getCell())){
			logAndValidate(reqData.getCell(), parameterValidationConfigurer.getCellRegEx(), Constants.CELL);
		}
		if(StringUtils.hasText(reqData.getAltEmail())){
			logAndValidate(reqData.getAltEmail(), parameterValidationConfigurer.getEmailRegEx(), Constants.ALT_EMAIL);
		}
		if(StringUtils.hasText(reqData.getDeleteAltEmail())){
			logAndValidate(reqData.getDeleteAltEmail(), parameterValidationConfigurer.getEmailRegEx(), Constants.DELETE_ALT_EMAIL);
		}
		if(StringUtils.hasText(reqData.getAsqQuestion())){
			logAndValidate(reqData.getAsqQuestion(), parameterValidationConfigurer.getAsqQuestionRegEx(), Constants.ASQ_QUESTION);
		}
		if(StringUtils.hasText(reqData.getBirthDay())){
			logAndValidate(reqData.getBirthDay(), parameterValidationConfigurer.getDayRegEx(), Constants.BIRTH_DAY);
		}
		if(StringUtils.hasText(reqData.getBirthMonth())){
			logAndValidate(reqData.getBirthMonth(), parameterValidationConfigurer.getMonthRegEx(), Constants.BIRTH_MONTH);
		}
		if(StringUtils.hasText(reqData.getBirthYear())){
			logAndValidate(reqData.getBirthYear(), parameterValidationConfigurer.getYearRegEx(), Constants.BIRTH_YEAR);
		}
		if(StringUtils.hasText(reqData.getMobileVerificationCode())){
			logAndValidate(reqData.getMobileVerificationCode(), parameterValidationConfigurer.getMobileVerificationCodeRegEx(), Constants.MOBILE_VERIFICATION_CODE);
		}
		if(StringUtils.hasText(reqData.getDeleteCell())){
			logAndValidate(reqData.getDeleteCell(), parameterValidationConfigurer.getDeleteCellRegEx(), Constants.DELETE_CELL);
		}
		if(StringUtils.hasText(reqData.getRecoveryOption())){
			logAndValidate(reqData.getRecoveryOption(), parameterValidationConfigurer.getRecoveryOptionRegEx(), Constants.RECOVERY_OPTION);
		}
		if(StringUtils.hasText(reqData.getInputAltEmail())){
			logAndValidate(reqData.getInputAltEmail(), parameterValidationConfigurer.getEmailRegEx(), Constants.INPUT_ALT_EMAIL);
		}
		if(StringUtils.hasText(reqData.getLocale())){
			logAndValidate(reqData.getLocale(), parameterValidationConfigurer.getLocaleRegEx(), Constants.LOCALE);
		}
		if(StringUtils.hasText(reqData.getGender())){
			logAndValidate(reqData.getGender(), parameterValidationConfigurer.getGenderRegEx(), Constants.GENDER);
		}
		if(StringUtils.hasText(reqData.getPostCode())){
			logAndValidate(reqData.getPostCode(), parameterValidationConfigurer.getPostCodeRegEx(), Constants.POST_CODE);
		}
		if(StringUtils.hasText(reqData.getDayPhone())){
			logAndValidate(reqData.getDayPhone(), parameterValidationConfigurer.getPhoneRegEx(), Constants.DAY_PHONE);
		}
		if(StringUtils.hasText(reqData.getEvePhone())){
			logAndValidate(reqData.getEvePhone(), parameterValidationConfigurer.getPhoneRegEx(), Constants.EVE_PHONE);
		}
		if(StringUtils.hasText(reqData.getCaptchaCode())){
			logAndValidate(reqData.getCaptchaCode(), parameterValidationConfigurer.getCaptchaCodeRegEx(), Constants.CAPTCHA_CODE);
		}
		if(StringUtils.hasText(reqData.getCaptchaRef())){
			logAndValidate(reqData.getCaptchaRef(), parameterValidationConfigurer.getCaptchaRefRegEx(), Constants.CAPTCHA_REF);
		}
		if(StringUtils.hasText(reqData.getConfCodeSent())){
			logAndValidate(reqData.getConfCodeSent(), parameterValidationConfigurer.getConfCodeSentRegEx(), Constants.CONF_CODE_SENT);
		}
		if(StringUtils.hasText(reqData.getSiteDomain())){
			logAndValidate(reqData.getSiteDomain(), parameterValidationConfigurer.getSiteDomainRegEx(), Constants.SITE_DOMAIN);
		}
		if(StringUtils.hasText(reqData.getDisplayName())){
			logAndValidate(reqData.getDisplayName(), parameterValidationConfigurer.getDisplayNameRegEx(), Constants.DISPLAY_NAME);
		}
		if(StringUtils.hasText(reqData.getYear())){
			logAndValidate(reqData.getYear(), parameterValidationConfigurer.getYearRegEx(), Constants.YEAR);
		}
		if(StringUtils.hasText(reqData.getMonth())){
			logAndValidate(reqData.getMonth(), parameterValidationConfigurer.getMonthRegEx(), Constants.MONTH);
		}
		if(StringUtils.hasText(reqData.getDay())){
			logAndValidate(reqData.getDay(), parameterValidationConfigurer.getDayRegEx(), Constants.DAY);
		}
		if(StringUtils.hasText(reqData.getMobilePhone())){
			logAndValidate(reqData.getMobilePhone(), parameterValidationConfigurer.getMobilePhoneRegEx(), Constants.MOBILE_PHONE);
		}
		if(StringUtils.hasText(reqData.getAppDeviceName())){
			logAndValidate(reqData.getAppDeviceName(), parameterValidationConfigurer.getAppDeviceNameRegEx(), Constants.APP_DEVICE_NAME);
		}
		if(StringUtils.hasText(reqData.getCellNumToverify())){
			logAndValidate(reqData.getCellNumToverify(), parameterValidationConfigurer.getPhoneRegEx(), Constants.CELL_NUMBER_TO_VERIFY);
		}
		if(StringUtils.hasText(reqData.getTwofaPhoneType())){
			logAndValidate(reqData.getTwofaPhoneType(), parameterValidationConfigurer.getTwofaPhoneTypeRegEx(), TotpConstants.TWOFA_PHONE_TYPE);
		}
		if(StringUtils.hasText(reqData.getSiteState())){
			LOG.info("Just logging the Site State ( " + reqData.getSiteState() +" ) to check.");
		}*/
	}

	private void logAndValidate(String reqDataParameter, String regEx, String reqDataParameterType) {
		/*if(!reqDataParameterType.equals(Constants.ASK_ANSWER))
		    LOG.info("Validating the " + reqDataParameterType + " ( " + Base64.encodeBase64URLSafeString(reqDataParameter.getBytes()) +" ) with the regular expression " + regEx);
		inputParameterRegExValidator.validate(reqDataParameter, regEx, reqDataParameterType);		*/
	}
}
