package wolfpub;

import java.sql.*;

/**
 * Manages connections and queries to WolfPubDb from WolfPub.
 * 
 * @author schristo
 */
public class WolfPubDb implements AutoCloseable {
	static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/cdsuh";

	static String user = "cdsuh";
	static String passwd = "csc540601";

	Connection conn = null;
	Statement statement = null;
	ResultSet rs = null;

	/**
	 * @throws SQLException
	 */
	public WolfPubDb() throws SQLException {
		super();
		connect();
	}
	
	/**
	 * @param conn
	 * @param statement
	 * @param rs
	 * @throws SQLException 
	 */
	public WolfPubDb(Connection conn, Statement statement, ResultSet rs) throws SQLException {
		super();
		this.conn = conn;
		this.statement = statement;
		this.rs = rs;
		connect();
	}
	
	/**
	 * Connect to WolfPubDB
	 * @throws SQLException 
	 */
	void connect() throws SQLException {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * close gets called implicitly in try-with-resources blocks.
	 */
	@Override
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}


	/**
	 * Query & list all tables in the database.
	 */
	public void listTables() {
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'");
			
			int cols = rs.getMetaData().getColumnCount();
			
			while (rs.next()) {
				for (int i = 1; i <= cols; i++) {
					System.out.println(rs.getString(i));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
