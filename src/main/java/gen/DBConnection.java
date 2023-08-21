package gen;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public Connection getDatabse() {

		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			 conn = DriverManager.getConnection("jdbc:mysql://91.205.172.123:3306/McFunny?autoReconnect=true&useSSL=false&serverTimezone=UTC", "root",
						"gloadmin123");
			 System.out.println("mcFunny Games DB connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
