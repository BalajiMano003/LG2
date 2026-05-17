package com.mapsted.config;

public class ConfigData {
	//valid credentials for login, you can change these values to test with different credentials
    public static final String VALID_EMAIL = "support356@mapsted.com";  //"mobile@mapsted.com"

    public static final String VALID_PASSWORD = "Fu0*t$Xy"; //"Passw0rd1@"; 
    
    
    //wrong credentials for negative testing, you can change these values to test with different credentials
    public static final String INVALID_EMAIL = "wrong@mapsted.com";
    public static final String INVALID_PASSWORD = "WrongPassword!";
    
    //if you want to test with different property, change the value of SEARCH_PROPERTY and run the test again
    public static final String SEARCH_PROPERTY = "Yorkdale";
    public static final String SEARCH_PsROPERTY = "Yorkdale";
}
