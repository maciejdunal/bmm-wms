package com.wsei.useless;

import com.wsei.useless.TokenRequest;
import com.wsei.useless.TokenResponse;
import com.wsei.useless.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/tokens")
    public TokenResponse createToken(@RequestBody TokenRequest tokenRequest) {
        return new TokenResponse(tokenService.generateToken(tokenRequest));
    }
}