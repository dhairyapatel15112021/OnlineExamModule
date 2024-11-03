package com.example.service.jwt;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class jwtService {

    private String keys;
    public jwtService(){
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretkey = keyGen.generateKey();
            this.keys = Base64.getEncoder().encodeToString(secretkey.getEncoded());
        }
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public String generateToken(String email){
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 60))
                .and()
                .signWith(getKey())
                .compact();
    }

    public SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(keys);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
