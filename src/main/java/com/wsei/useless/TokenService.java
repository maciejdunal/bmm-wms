package com.wsei.useless;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


@Service
public class TokenService {

    @SneakyThrows
    public String generateToken(TokenRequest tokenRequest){
        JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("active", "true")
                .build();

        Payload payload = new Payload(claims.toJSONObject());

        JWEObject jweObject = new JWEObject(header, payload);

        String secret = "841D8A6C80CBA4FCAD32D5367C18C53B";
        byte[] secretKey = secret.getBytes();

        jweObject.encrypt(new DirectEncrypter(secretKey));

        return jweObject.serialize();
    }

}
