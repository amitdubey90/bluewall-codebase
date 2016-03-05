package com.bluewall.feservices.dao;

import com.bluewall.feservices.util.SQLQueries;
import com.bluewall.util.bean.AccessCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Repository
public class DeviceAuthorizationDaoImpl implements DeviceAuthorizationDaoIfc {

    @Autowired
    DataSource dataSource;

    public boolean storeUserAccessToken(AccessCredentials credentials) {
        log.info("storeUserAccessToken started");

        try (PreparedStatement pst = dataSource.getConnection().prepareStatement(SQLQueries.INSERT_USER_ACCESS_TOKEN)) {
            int colId = 1;

            pst.setInt(colId++, credentials.getUserId());
            pst.setInt(colId++, credentials.getDeviceId());
            pst.setString(colId++, credentials.getAccessToken());
            pst.setString(colId++, credentials.getRefreshToken());
            pst.setDate(colId++, credentials.getExpirationTime());

            pst.execute();
            log.info("storeUserAccessToken successful");
            return true;
        } catch (SQLException e) {
            log.error("SqlException in storeUserAccessToken {}", e);
        }
        return false;
    }
}
