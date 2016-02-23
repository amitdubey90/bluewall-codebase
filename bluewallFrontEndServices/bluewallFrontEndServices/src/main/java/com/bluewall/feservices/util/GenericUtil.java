package com.bluewall.feservices.util;

public class GenericUtil {

	public static java.sql.Date javaToSQLDate(java.util.Date date){
		return new java.sql.Date(date.getTime());
	}
}
