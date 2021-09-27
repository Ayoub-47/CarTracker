package com.example.car.HashUtility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class ShaHash {

    public static String sha256(String input) throws NoSuchAlgorithmException{


        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());

        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for(byte   b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }
}
