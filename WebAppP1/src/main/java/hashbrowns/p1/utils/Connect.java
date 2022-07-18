package hashbrowns.p1.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {
	private static Connect connDB;
	static final String endpoint = "jdbc:postgresql://db-fs.cmqm9wl6ccrf.us-east-1.rds.amazonaws.com:5432/postgres",
			username = "postgres", password = "password";

	private Connect() {
	};

	public static synchronized Connect getConnectionDB() {
		if (connDB == null) {
			connDB = new Connect();
		}
		return connDB;
	}

	public Connection getConnection() {

		Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(endpoint, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

}


