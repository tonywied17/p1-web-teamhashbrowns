package hashbrowns.p1.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {
	
	static Logger logger = Logger.getLogger();
	private static Connect con;
	private Properties props;
	
	private Connect() {
		props = new Properties();		
		InputStream propsFile = Connect.class.getClassLoader()
				.getResourceAsStream("database.properties");
		try {
			props.load(propsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized Connect getConnect() {
		if(con == null) {
			con = new Connect();
			logger.log("Connection Object Created", LoggingLevel.INFO);
		}
		return con;
	}
	public Connection getConnection() throws SQLException{
		Connection con = null;
		String dbUrl = props.getProperty("url");
		String dbUser = props.getProperty("usr");
		String dbPass = props.getProperty("psw");
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
		} catch (SQLException | ClassNotFoundException e) {
			logger.log("Database Connection Failed", LoggingLevel.FATAL);
			e.printStackTrace();
		}
		return con;
	}
}
