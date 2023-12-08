package com.pathshala.service;

import com.pathshala.dao.SessionInfoEntity;
import com.pathshala.exception.NotFoundException;
import com.pathshala.repository.SessionInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SessionInfoService {
    private SessionInfoRepository sessionInfoRepository;

    @Transactional
    public SessionInfoEntity findByUserIdAndIsActive(Long userId){
        List<SessionInfoEntity> sessionInfo = sessionInfoRepository.findByUserIdAndIsActiveTrueOrderByIdDesc(userId);
        if(sessionInfo.isEmpty()){
            throw new NotFoundException("", "");
        } else if(sessionInfo.size() > 1){
            for(int i = 1 ;i < sessionInfo.size(); i++){
                sessionInfoRepository.expireTokenById(sessionInfo.get(i).getId());
            }
        }
        return sessionInfo.get(0);
    }

    public Boolean createSession(Long userId, String token, String ip) {
        SessionInfoEntity sessionInfo = SessionInfoEntity.builder().userId(userId).sessionToken(token).initiatorIp(ip).isActive(true).build();
        SessionInfoEntity savedSession = sessionInfoRepository.save(sessionInfo);
        return true;
    }

    @Transactional
    public void expireToken(){
        sessionInfoRepository.expireToken();
    }

    @Transactional
    public Boolean expireSessionForUserId(Long userId) {
        return sessionInfoRepository.expireSessionByUserId(userId) != 0;
    }
}
