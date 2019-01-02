package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	private static ConnectionFactory cf = null;
	
	private ConnectionFactory() {
		
	}
	
	public static synchronized ConnectionFactory getInstance() {
		if (cf == null) cf = new ConnectionFactory();
		return cf;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		String path = "C:/Users/dukef/Documents/my_git_repos/1810-oct22/Week 2/project0/src/main/resources/database.properties";
		
			try {
				prop.load(new FileReader(path));
				Class.forName(prop.getProperty("driver"));
				conn = DriverManager.getConnection(
						prop.getProperty("url"),
						prop.getProperty("user"),
						prop.getProperty("pwd"));
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}


		return conn;
	}
	

}
