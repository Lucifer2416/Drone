package manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MDB {
	private static Connection connection = null;
	
	public static void connect() throws SQLException {
		PropertyValues properties = new PropertyValues();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String mysqlURL="jdbc:mysql://"+properties.getIp()+":"+properties.getPort()+"/"+properties.getDbnm()+"?serverTimezone=UTC";
			System.out.println(mysqlURL);
			connection = DriverManager.getConnection(mysqlURL, properties.getUnm(), properties.getPwd());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet execQuery(String query) {
		PreparedStatement ps = getPS(query);
		ResultSet rs = null;
		try {
			if(ps != null) {
				ps.execute();
				rs = ps.getResultSet();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rs;
	}
	
	public static PreparedStatement getPS(String query) {
		PreparedStatement ps = null;
		try { 
			if(connection == null || connection.isClosed())
				connect();
			ps = connection.prepareStatement(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}
	
	public static PreparedStatement getPS(String query, int id) {
		PreparedStatement ps = null;
		try { 
			if(connection == null || connection.isClosed())
				connect();
			if (id == 1)
				ps = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}
	
	public static PreparedStatement getPS(String query, String column) {
		return getPS(query, new String[]{column});
	}
	
	public static PreparedStatement getPS(String query, String[] columns) {
		PreparedStatement ps = null;
		try { 
			if(connection == null || connection.isClosed())
				connect();
				ps = connection.prepareStatement(query,columns);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}
	
	public static void disconnect() {
		try {
			if(connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			connection = null;
		}
	}
}
