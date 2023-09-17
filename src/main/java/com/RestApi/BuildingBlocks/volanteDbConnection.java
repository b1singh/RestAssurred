package com.RestApi.BuildingBlocks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class volanteDbConnection {
	private static Connection conn=null;
	static {
	String cnn_string="URL";
	try {
		conn=DriverManager.getConnection(cnn_string);
	}catch (SQLException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
	public static Connection createDbConnection() {
		return conn;
	}

}
