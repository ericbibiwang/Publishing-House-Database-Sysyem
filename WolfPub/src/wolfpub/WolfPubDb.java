package wolfpub;

import java.sql.*;
import java.util.Vector;

import de.vandermeer.asciitable.AsciiTable;

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
			if (rs != null) {
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
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

	/** 
	 * SELECT * on given table name
	 *
	 * @param table
	 */
	public void selectTable(String table) {
		try {
			statement = conn.createStatement(); 
			rs = statement.executeQuery("DESCRIBE " + table);
			
			Vector<String> result = new Vector<String>();

			while (rs.next()) {
				result.add(rs.getString(1));
			}

			AsciiTable printTable = new AsciiTable();
			
			printTable.addRule();
			printTable.addRow(result);
			printTable.addRule();
			
			rs = statement.executeQuery("SELECT * FROM " + table);

			int cols = rs.getMetaData().getColumnCount();
			
			while (rs.next()) {
				result.clear();
				for (int i = 1; i <= cols; i++) {
					result.add(rs.getString(i));
				}
				printTable.addRow(result);
			}

			printTable.addRule();
			System.out.print(printTable.render(cols * 20));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}
	
	/**
	 * Passthrough basic case of prepareStatement
	 * @param s statement
	 * @throws SQLException
	 */
	public void prepareStatement(String s) throws SQLException {
		statement = conn.prepareStatement(s);
	}
	
	/**
	 * Passthrough basic case of createStatement
	 * @throws SQLException
	 */
	public void createStatement() throws SQLException {
		statement = conn.createStatement();
	}
	
	/**
	 * Passthrough basic case of executeUpdate
	 * @param s statement
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(String s) throws SQLException {
		return statement.executeUpdate(s);
	}
}
