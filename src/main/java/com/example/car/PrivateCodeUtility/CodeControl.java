package com.example.car.PrivateCodeUtility;



import com.example.car.ConstantsUtility.Constants;

import java.util.ArrayList;
import java.util.Arrays;

public class CodeControl {
    // verifying if the secret code setting by user contains at least two numbers
    private static boolean contain2Nums(String nums, String privateCode){
        short cpt = 0;
        for (int i = 0; i < privateCode.length(); i++) {
            if (nums.contains("" + privateCode.charAt(i)))
                cpt++;
        }
        return (cpt >= 2);
    }


    // verifying if the secret code setting by user contains at least two special characters
    private static boolean contain2SpecialCharacters(String spChar, String privateCode){
        short cpt = 0;
        for (int i = 0; i < privateCode.length(); i++) {
            if (spChar.contains("" + privateCode.charAt(i)))
                cpt++;
        }
        return (cpt >= 2);
    }


    // verifying if the secret code setting by user contains at least two Uppercase characters
    private static boolean contains2Uppercase(String uppers, String privateCode){
        short cpt = 0;
        for (int i = 0; i < privateCode.length(); i++) {
            if (uppers.contains("" + privateCode.charAt(i)))
                cpt++;
        }
        return (cpt >= 2);
    }


    // precising the recommended status that private code setting by user still doesn't verify it
    public static ArrayList<String> controlPrivateCode(String privateCode){


        ArrayList<String> errorMessages = new ArrayList<String>();

        if (privateCode.length() < 8)
            errorMessages.add(com.example.car.ConstantsUtility.Constants.messages[0]);
        if (!contain2Nums(com.example.car.ConstantsUtility.Constants.DIGIT, privateCode))
            errorMessages.add(com.example.car.ConstantsUtility.Constants.messages[1]);
        if (!contain2SpecialCharacters(com.example.car.ConstantsUtility.Constants.SPECIAL_CHAR, privateCode))
            errorMessages.add(com.example.car.ConstantsUtility.Constants.messages[2]);
        if (!contains2Uppercase(com.example.car.ConstantsUtility.Constants.ALPHA_UPPER_CASE, privateCode))
            errorMessages.add(com.example.car.ConstantsUtility.Constants.messages[3]);

        return errorMessages;
    }


    public static String displayPrivateCodeErrors(String privateCode){
        ArrayList<String> errs = controlPrivateCode(privateCode);
        String allErrors = "";
        for (String string : errs) {
            allErrors+=string +"\n";
        }
        return allErrors;
    }

    //generating a random and powerful private code
    public static String  randPrivateCode(){


        String[] strings = { com.example.car.ConstantsUtility.Constants.ALPHA_UPPER_CASE,
                com.example.car.ConstantsUtility.Constants.ALPHA_LOWER_CASE,
                com.example.car.ConstantsUtility.Constants.DIGIT,
                com.example.car.ConstantsUtility.Constants.SPECIAL_CHAR};

        char[] prvtCd = new char[8];

        ArrayList<Integer> positions = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                int index = (int) Math.floor(strings[i].length() * Math.random());
                int pos = (int) (positions.size() * Math.random());
                prvtCd[positions.get(pos)] = strings[i].charAt(index);
                positions.remove(pos);
            }
        }
        return String.valueOf(prvtCd);
    }
}

