package ua.polischuk.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypt {

    public static String encryptPassword(String password)  {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {

        }
        byte[] bytes = md5.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for(byte b: bytes){
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }

}
