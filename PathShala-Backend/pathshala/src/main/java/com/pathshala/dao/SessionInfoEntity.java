package com.pathshala.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "sessionInfo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionInfoEntity extends MetaData{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String sessionToken;
    private String initiatorIp;
    private Boolean isActive;
}
