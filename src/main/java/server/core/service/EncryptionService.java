package server.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
@Service
public class EncryptionService {

    @Value("${core.auth.hash.salt}")
    private String salt;

    public String hash(String text) throws RuntimeException{
        System.out.println(this.salt);
        if (text == null)
            text = "";
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String textWithSalt = text.concat(this.salt);
            byte[] bytes = digest.digest(textWithSalt.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
    private static String bytesToHex(byte[] bytes){
        Formatter formatter = new Formatter();
        for (byte b: bytes){
            formatter.format("%02x", b);
        }
        return  formatter.toString();
    }
}
