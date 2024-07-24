package com.mathias.royalbankingapplication.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account";

    public static final String ACCOUNT_CREATION_SUCCESS_CODE = "002";
    public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Account has been created Successfully";

    public static final String ACCOUNT_NUMBER_NON_EXISTS_CODE = "003";
    public static final String ACCOUNT_NUMBER_NON_EXISTS_MESSAGE = "Provided account number doesn't exist";

    public static final String ACCOUNT_NUMBER_FOUND_CODE = "004";
    public static final String ACCOUNT_NUMBER_FOUND_MESSAGE = "Account number found";

    public static final String ACCOUNT_CREDITED_CODE = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Your account has been credited Successfully";

    public static final String ACCOUNT_INSUFFICIENT_CODE = "006";
    public static final String ACCOUNT_INSUFFICIENT_MESSAGE = " Insufficient Funds!!";

    public static final String ACCOUNT_DEBITED_CODE = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Your account has been debited Successfully";



    public static final String generateAccountNumber(){
        //**
        // This algorithm will assume that your account number will be total of 10 digits,
        // since we basically have 10 digits account number in Nigeria


        //1. Get your current year
        Year currentYear = Year.now();

        //2. get 6 random digits
        int min = 100000;
        int max = 999999;

        //generate a random number between min and max
        int randomNumber = (int)Math.floor(Math.random() * (max - min +1) + min);

        //convert current year and random six number to string and then concatenate them
        String year = String.valueOf(currentYear);
        String randomNum = String.valueOf(randomNumber);


        //append both the year and the random number
        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(randomNum).toString();
    }
}
