package ua.polischuk.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

public class PasswordEncrypt {

    public String EncryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for(byte b: bytes){
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }

}
