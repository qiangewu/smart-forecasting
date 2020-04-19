package com.touchspring.smartforecasting.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.touchspring.smartforecasting.exception.TokenExpiredException;

import java.util.Date;
import java.util.HashMap;

/**
 * @author Victor
 */
public class JWTUtils {

    private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";

    private static final String PAYLOAD = "payload";


    public static <T> String sign(T object, long maxAge) {
        try {

            HashMap<String, Object> headerClaims = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(object);
            headerClaims.put(PAYLOAD, json);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create().withIssuer("auth0")
                    .withHeader(headerClaims).withExpiresAt(new Date(System.currentTimeMillis() + maxAge))
                    .sign(algorithm);
        } catch (JWTCreationException | JsonProcessingException exception) {
            exception.printStackTrace();
        }
        return "";
    }


    public static <T> T unSign(String token, Class<T> classT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            Claim headerClaim = jwt.getHeaderClaim(PAYLOAD);
            String json = headerClaim.asString();
            return JSONObject.parseObject(json, classT);
        } catch (Exception e) {
            throw new TokenExpiredException("16003");
        }
    }


}
