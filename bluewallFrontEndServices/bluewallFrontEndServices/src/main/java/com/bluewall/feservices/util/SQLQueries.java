package com.bluewall.feservices.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bluewall.feservices.dao.SqlDBConnections;

public class SQLQueries {
		
	public boolean checkValidUser(String username, String password){
		Connection conn = null;
		SqlDBConnections sqlConn;
		try {
			sqlConn = new SqlDBConnections("jdbc:mysql://user-db-instance.cqqcnirpnrkg.us-west-1.rds.amazonaws.com:3306","userDatabase","root","q1w2e3r4t5y6");
			conn = sqlConn.returnSQLConnection();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "select * from LoginDetail where emailID = '"+username+"' and password = '"+password+"'";
		System.out.println("My sql : "+sql);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				System.out.println("Success");
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try 
			{ 
				if (rs != null) 
					rs.close(); 
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace(); 
			}
			try 
			{ 
				if (stmt != null) 
					stmt.close(); 
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace(); 
			}
			try 
			{ 
				if (conn != null) 
					conn.close(); 
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace(); 
			}
		}
		System.out.println("Fail");
		return false;
	}

    public static String INSERT_USER_ACCESS_TOKEN = "INSERT INTO UserConnectedDevice (userID, deviceId, accessToken, refreshToken, creationTime) values (?, ?, ?, ?, ?)";

}
