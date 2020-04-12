package tasks;

import java.sql.SQLException;
import java.util.Vector;

import wolfpub.WolfPub;


public class Production {
	
	public static int enterBookEdition(String isbn, String pubID, String editionNum, String pubDate, String price) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			
			columns.add("isbn");
			values.add(isbn);
			columns.add("pubID");
			values.add(pubID);
			columns.add("editionNum");
			values.add(editionNum);
			columns.add("pubDate");
			values.add(pubDate);
			columns.add("price");
			values.add(price);
			
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO Publication VALUES ('isbn', 'pubID', 'editionNum', 'pubDate', 'price');");
			
			System.out.println("Try to process" + sb.toString());
			
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return 0;
	}
	
	
	
	public static int updateBookEdition(String isbn, String pubID, String editionNum, String pubDate, String price) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			columns.add("isbn");
			values.add(isbn);
			columns.add("pubID");
			values.add(pubID);
			columns.add("editionNum");
			values.add(editionNum);
			columns.add("pubDate");
			values.add(pubDate);
			columns.add("price");
			values.add(price);
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE Edition SET pubDate=").append(pubDate).append(" WHERE isbn=").append(isbn).append(";");
			
			
			System.out.println("Try to process" + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return 0;
	}
	
	
	
	public static int enterNewIssue(String pubID, String issueDate, String issueTitle, String price) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			columns.add("issueDate");
			values.add(issueDate);
			columns.add("pubID");
			values.add(pubID);
			columns.add("issueTitle");
			values.add(issueTitle);
			columns.add("price");
			values.add(price);
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO Issue VALUES ('isbn', 'pubID', 'issueTitle', 'price');");
			
			System.out.println("Try to process" + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return 0;
	}
	
	
	
	public static int updateIssue(String pubID, String issueDate, String issueTitle, String price) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			columns.add("issueDate");
			values.add(issueDate);
			columns.add("pubID");
			values.add(pubID);
			columns.add("issueTitle");
			values.add(issueTitle);
			columns.add("price");
			values.add(price);
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE Issue SET price=").append(price).append("WHERE pubID=").append(pubID).append("AND issueTitle=").append(issueTitle).append(";");
			
			System.out.println("Try to process" + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return 0;
	}
	
	public static int deleteIssue(String pubID, String issueDate, String issueTitle, String price) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			columns.add("issueDate");
			values.add(issueDate);
			columns.add("pubID");
			values.add(pubID);
			columns.add("issueTitle");
			values.add(issueTitle);
			columns.add("price");
			values.add(price);
			
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE Issue WHERE pubID=").append(pubID).append("AND issueDate=").append(issueDate).append(";");
			
			System.out.println("Try to process" + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return 0;
	}
	
	public static int getPublicationIssueByISSN(String issn) {
		
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}
	
	
	public static int getPublicationIssueByAttr(String title, String type, String publicationPeriod, String[] authors, String editors[]) {
		
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}
	
	public static int getBookByISSN(String isbn) {
	
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}


	public static int getBookByAttr(String title, int editionNum, String date, String[] authors, String editors[]) {
	
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}
	
	public static int enterRegularStaffPayment(String ssn, double amount, String payment, String date) {
		
		return 0;
	}
	
	
	
	public static int enterRegularInvitedPayment(String ssn, double amount, String isbn, String issn, String pubDate, String articleTitle) {
		
		return 0;
	}
	
	public static int trackPayment(String ssn) {
		return 0; // decide on return value; either print to stdout or return appropriate value
		
	}
	


}