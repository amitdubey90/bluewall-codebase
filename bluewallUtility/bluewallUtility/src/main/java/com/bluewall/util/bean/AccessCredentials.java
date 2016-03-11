package com.bluewall.util.bean;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@ToString
public class AccessCredentials {

    private int userId;
    private String accessToken;
    private String refreshToken;
    private int deviceId;
    private Timestamp expirationTime;
}
