package com.beatus.billlive.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component("inputParameterRegExValidator")
public class InputParameterRegExValidator {
	
	/*@Autowired
    private Configurer configurer;
	*/
	//private final Logger LOG = LoggerFactory.getLogger(Constants.PARAMETER_VALIDATION_LOG);
	private Pattern pattern;

	public void validate(String inputParameterValue, String inputPattern, String inputParameterType) {
	/*
        if(StringUtils.isNotBlank(inputParameterValue)){
	        pattern = Pattern.compile(inputPattern, Pattern.UNICODE_CHARACTER_CLASS);
	        Matcher matcher = pattern.matcher(inputParameterValue);
			if(!matcher.matches()){
				if(inputParameterType.equals(Constants.ASK_ANSWER)){
					StringBuilder askNotMatchedCharacters = new StringBuilder();
					for (int i = 0; i < inputParameterValue.length(); i++) {
						String askCharacter = Character.valueOf(inputParameterValue.charAt(i)).toString();
				        Matcher m = pattern.matcher(askCharacter);
				        if (!m.matches()) {
				        	askNotMatchedCharacters.append(askCharacter);
				        }
					 }
					LOG.error("{} with value {} doesnt match pattern {} : ", inputParameterType, Base64.encodeBase64URLSafeString(askNotMatchedCharacters.toString().getBytes()), inputPattern);
				} else {
				    LOG.error("{} with value {} doesnt match pattern {} : ", inputParameterType, Base64.encodeBase64URLSafeString(inputParameterValue.getBytes()), inputPattern);
				}
				if(!configurer.isParameterValidationTrailRun())
					throw new ParameterValidationException(inputParameterType +"\"" + inputParameterValue + "\"" +  " is not matching the standards, it has some redundant data ");
			}
        }*/
	}

}
