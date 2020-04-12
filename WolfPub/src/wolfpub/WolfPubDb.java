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
	
	public void executeQuery(String query) throws SQLException {
		try {
			/* Execute query */
			statement = conn.createStatement(); 
			rs = statement.executeQuery(query);
			
			/* For storage each row of result */
			Vector<String> result = new Vector<String>();
			
			/* Get result of query */
			ResultSetMetaData r = rs.getMetaData();
			int cols = r.getColumnCount();
			
			/* Put result into asciiTable */
			AsciiTable printTable = new AsciiTable();
			
			for(int i = 1; i<= cols; i++){
				result.add(r.getColumnName(i));
			}
			
			printTable.addRule();
			printTable.addRow(result);
			printTable.addRule();			
			
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
	
	/*
	 * create tables
	 */
//	private static void createTables() {
//		try {
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Publication( PublicationID VARCHAR(13)," + "PublicationTitile VARCHAR(30) NOT NULL, PublicationType VARCHAR(20) NOT NULL," + "PRIMARY KEY(PublicationID));");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST NonBook( PublicationID VARCHAR(13)," + "Period VARCHAR(10) NOT NULL," + "PRIMARY KEY(PublicationID)," + "FOREIGN KEY(PublicationID) REFERENCES Publication(PublicationID) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Book( PublicationID VARCHAR(13)," + "PRIMARY KEY(PublicationID)," + "FOREIGN KEY(PublicationID) REFERENCES Publication(PublicationID) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Issue( PublicationID VARCHAR(13)," + "PRIMARY KEY(PublicationID)," + "FOREIGN KEY(PublicationID) REFERENCES Publication(PublicationID) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Article( PublicationID VARCHAR(13)," + "IssueDate DATE, ArticleTitle VARCHAR(30)," + "ArticleText VARCHAR(65535) NOT NULL," + "PRIMARY KEY(PublicationID, IssueDate, ArticleTitle)," + "FOREIGN KEY(PublicationID, IssueDate) REFERENCES Issue(PublicationID, IssueDate) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Employee( SSN VARCHAR(11)," + "Name VARCHAR(20) NOT NULL, ContractPay FLOAT NOT NULL," + "PayPeriod VARCHAR(15) NOT NULL," + "PRIMARY KEY(SSN));");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Author(SSN VARCHAR(11)," + "PRIMARY KEY(SSN)," + "FOREIGN KEY(SSN) REFERENCES Employee(SSN) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Edition ( ISBN VARCHAR(13)," + "PublicationID VARCHAR(13), EditionNumber INT NOT NULL, PublicationDate DATE NOT NULL, EditionPrice FlOAT NOT NULL,"
//					 + "PRIMARY KEY(ISBN),FOREIGN KEY(PublicationID) REFERENCES Book(PublicationID) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Chapter ( ISBN VARCHAR(13),ChapterNumber INT, ChapterTitle VARCHAR(10)," + "PRIMARY KEY(ISBN, ChapterNumber),"
//					 + "FOREIGN KEY(ISBN) REFERENCES Edition(ISBN)ON UPDATE CASCADE ON DELETE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Distributor ( StreetAddr VARCHAR(20),City VARCHAR(20),State VARCHAR(2) NOT NULL,Name VARCHAR(20) NOT NULL,Type VARCHAR(20) NOT NULL"
//					 + "ContactPerson VARCHAR(20) NOT NULL, PhoneNumber VARCHAR(14) NOT NULL, Balance FLOAT NOT NULL," +"PRIMARY KEY(StreetAddr, City, Zip);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Editor ( SSN VARCHAR(11), PRIMARY KEY(SSN)," + "FOREIGN KEY(SSN) REFERENCES Employee(SSN) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST EditsEdition ( SSN VARCHAR(11), ISBN VARCHAR(10)," + "PRIMARY KEY(SSN, ISBN)," + "FOREIGN KEY(SSN) REFERENCES Editor(SSN) ON UPDATE CASCADEON,"
//					 + "FOREIGN KEY(ISBN) REFERENCES Edition(ISBN) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST EditsIssue ( SSN VARCHAR(11), PublicationID VARCHAR(13), IssueDate DATE," + "PRIMARY KEY(SSN, PublicationID, IssueDate)," + "FOREIGN KEY(SSN) REFERENCES Editor(SSN) ON UPDATE CASCADE,"
//					 + "FOREIGN KEY(PublicationID, IssueDate) REFERENCES Issue(PublicationID, IssueDate) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Management( SSN VARCHAR(11)," + "PRIMARY KEY(SSN)," + "FOREIGN KEY(SSN) REFERENCES Employee(SSN) ON UPDATE CASCADE ON DELETE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST OrderTable( OrderID VARCHAR(10), StreetAddr VARCHAR(20), City VARCHAR(20), Zip VARCHAR(10)," + "Date DATE NOT NULL, ShippingCost FLOAT NOT NULL," + "PRIMARY KEY(OrderID),"
//					 + "FOREIGN KEY(StreetAddr, City, Zip) REFERENCES Distributor(StreetAddr, City, Zip) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST OrderContainsEdition( OrderID VARCHAR(10), ISBN VARCHAR(10), NumOfCopies INT NOT NULL,Price FLOAT NOT NULL," + "PRIMARY KEY(OrderID, ISBN), FOREIGN KEY(ISBN) REFERENCES Edition(ISBN)ON UPDATE CASCADE,"
//					 + "FOREIGN KEY(OrderID) REFERENCES OrderTable(OrderID)ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST OrderContainsIssue( OrderID VARCHAR(10), PublicationID VARCHAR(13), IssueDate DATE, NumOfCopies INT NOT NULL, Price FLOAT NOT NULL," + "PRIMARY KEY(OrderID, PublicationID, IssueDate),"
//					 + "FOREIGN KEY(OrderID) REFERENCES OrderTable(OrderID)ON UPDATE CASCADE," + "FOREIGN KEY(PublicationID, IssueDate)REFERENCES Issue(PublicationID, IssueDate) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Payment( PaymentID VARCHAR(10), SSN VARCHAR(11), Amount FLOAT NOT NULL, DatePickedUp DATE, PRIMARY KEY(PaymentID), FOREIGN KEY(SSN) REFERENCES Employee(SSN) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Topic( TopicName VARCHAR(20), PRIMARY KEY(TopicName));");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST PublicationHas( TopicName VARCHAR(20), PublicationID VARCHAR(13), PRIMARY KEY(TopicName, PublicationID), FOREIGN KEY(PublicationID) REFERENCES Publication(PublicationID) ON UPDATE CASCADE,"
//					 + "FOREIGN KEY(TopicName) REFERENCES Topic(TopicName) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST Section( ISBN VARCHAR(13), ChapterNumber INT, SectionNumber INT, SectionTitle VARCHAR(30) NOT NULL, SectionText VARCHAR(50000) NOT NULL, PRIMARY KEY(ISBN, ChapterNumber, SectionNumber),"
//					 + "FOREIGN KEY(ISBN, ChapterNumber) REFERENCES Chapter(ISBN, ChapterNumber) ON UPDATE CASCADE);");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST WritesEdition( SSN VARCHAR(11), ISBN VARCHAR(10), PRIMARY KEY(SSN, ISBN)," + "FOREIGN KEY(SSN) REFERENCES Author(SSN) ON UPDATE CASCADE ON DELETE CASCADE," + "FOREIGN KEY(ISBN) REFERENCES Edition(ISBN) ON UPDATE CASCADE );");
//			statement.executeUpdate("CREATE TABLE IF NOT EXIST WriteArticle( SSN VARCHAR(11), PublicationID VARCHAR(13), IssueDate DATE, ArticleTitle VARCHAR(30)," + "PRIMARY KEY(SSN, PublicationID, IssueDate, ArticleTitle),"
//					 + "FOREIGN KEY(SSN) REFERENCES Author(SSN) ON UPDATE CASCADE," + "FOREIGN KEY(PublicationID, IssueDate, ArticleTitle) REFERENCES Article(PublicationID, IssueDate, ArticleTitle) ON UPDATE CASCADE);");
//			System.out.println("Tables have created");
//	    }catch (SQLException e) {
//	        e.printStackTrace();
//	   	}
//	}
	
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
