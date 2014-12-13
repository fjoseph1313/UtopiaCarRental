/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.entities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Komal
 */
public class EncryptPassword {

    public static String getEncryptedPassword(String textPassword) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptPassword.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            md.update(textPassword.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EncryptPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] digest = md.digest();

        return byteToString(digest);
    }

    public static String byteToString(byte[] input) {
        String hash = "";
        int i = 0;
        for (byte b : input) {
            i = b & 0xFF;
            String temp = Integer.toHexString(i);
            if (temp.length() < 2) {
                hash += "0";
            }
            hash += temp;
        }
        return hash;
    }

}
