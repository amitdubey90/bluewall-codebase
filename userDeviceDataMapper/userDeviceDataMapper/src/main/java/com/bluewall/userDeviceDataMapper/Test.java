package com.bluewall.userDeviceDataMapper;

import com.bluewall.userDeviceDataMapper.util.MongoConnectionManager;
import com.bluewall.userDeviceDataMapper.util.MySqlConnectionManager;
import com.mongodb.MongoClient;

import java.sql.Connection;

public class Test {
    private static Connection mysqlConnection;
    private static MongoClient mongoClient;

    public static void main(String[] args) {
        try (MySqlConnectionManager sqlConnectionMgr = new MySqlConnectionManager();
             MongoConnectionManager mongoConnectionMgr = new MongoConnectionManager()) {

//            mysqlConnection = sqlConnectionMgr.getConnection();
            mongoClient = mongoConnectionMgr.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
