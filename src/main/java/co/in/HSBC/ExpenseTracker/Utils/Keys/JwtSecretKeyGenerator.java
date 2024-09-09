package co.in.HSBC.ExpenseTracker.Utils.Keys;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtSecretKeyGenerator {
    public static void main(String[] args) {
//        Generating a new key
        byte[] secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String base64Key = java.util.Base64.getEncoder().encodeToString(secretKey);
        System.out.println("Generated JWT secret key (Base64) " +base64Key);
    }
}
