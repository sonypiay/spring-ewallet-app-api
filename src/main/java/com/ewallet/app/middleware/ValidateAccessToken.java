package com.ewallet.app.middleware;

import com.ewallet.app.exceptions.UnauthorizedException;
import com.ewallet.app.models.entities.PersonalTokens;
import com.ewallet.app.models.repositories.PersonalTokensRepository;
import com.ewallet.app.utils.Base64EncodeDecode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class ValidateAccessToken implements HandlerInterceptor {

    @Autowired
    private PersonalTokensRepository personalTokensRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String getAuthorization = request.getHeader("Authorization");

        if( getAuthorization == null || getAuthorization.isBlank() ) {
            throw new UnauthorizedException("You are not logged in");
        }

        String parsingAuthorization = getAuthorization.replace("Basic ", "");
        String[] decodeToken = Base64EncodeDecode.decode(parsingAuthorization).split(":");
        String customerId = decodeToken[0];
        String accessToken = decodeToken[1];

        PersonalTokens personalTokens = personalTokensRepository.findByAccessTokenAndCustomerId(accessToken, customerId)
                .orElseThrow(() -> new UnauthorizedException("You are not logged in"));

        if( personalTokens.getExpiredAt() < System.currentTimeMillis() ) {
            throw new UnauthorizedException("Token has been expired");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
