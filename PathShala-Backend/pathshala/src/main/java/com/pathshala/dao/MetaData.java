package com.pathshala.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetaData {

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp modifiedOn;

    private String createdBy;

    private String modifiedBy;
}
