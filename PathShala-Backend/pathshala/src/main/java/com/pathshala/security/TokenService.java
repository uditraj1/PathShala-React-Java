package com.pathshala.security;

import com.pathshala.config.PropertyConfig;
import com.pathshala.dao.SessionInfoEntity;
import com.pathshala.service.SessionInfoService;
import com.pathshala.util.AESUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class TokenService {

    private PropertyConfig config;
    private SessionInfoService sessionInfoService;

    public String createToken(Long userId, String userRole) {
        long now = new Date().getTime();
        String plainToken = now + "#" + userId + "#" + userRole + "#" + now;
        return AESUtility.doEncrypt(plainToken, config.getPropertyByName("secretKey"));
    }

    public Boolean validateToken(Long userId, String token, String userRole) {
        SessionInfoEntity sessionInfo = sessionInfoService.findByUserIdAndIsActive(userId);
        if (sessionInfo.getSessionToken().equals(token)){
            String decryptedToken = AESUtility.doDecrypt(token, config.getPropertyByName("secretKey"));
            String[] tokens = decryptedToken.split("#");
            return userRole.equalsIgnoreCase(tokens[2]);
        }
        return false;
    }

    public void expireToken(){
        sessionInfoService.expireToken();
    }


}
