package hashbrowns.p1.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {
	
	private static Connect connUtil;
	private Properties props;

	
	private Connect() {
		// this constructor can be blank if you're not using
		// a properties file for your connection info
		props = new Properties();
		
		InputStream propsFile = Connect.class.getClassLoader()
				.getResourceAsStream("database.properties");
		try {
			props.load(propsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized Connect getConnectionUtil() {
		if (connUtil == null) {
			connUtil = new Connect();
		}
		return connUtil;
	}
	
	
	public Connection getConnectionDB() {

		Connection conn = null;
		
		// using properties file
		String dbUrl = props.getProperty("url");
		String dbUser = props.getProperty("usr");
		String dbPass = props.getProperty("psw");
		
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(
					// jdbc:postgresql://pet-app.cziwys5p2mwa.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=pet_app0
					dbUrl,
					dbUser,
					dbPass);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	

}


