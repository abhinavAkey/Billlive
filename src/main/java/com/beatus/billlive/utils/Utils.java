package com.beatus.billlive.utils;

import java.security.SecureRandom;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {
	
	public static String generateRandomKey(final int numberOfCharacters) {
    	String	randomNumber = RandomStringUtils.random(numberOfCharacters, 0, 0, false, true, null, new SecureRandom());
		return randomNumber;
	}

}
