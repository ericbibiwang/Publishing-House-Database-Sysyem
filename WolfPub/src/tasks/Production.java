package tasks;

import java.sql.SQLException;
import java.util.Vector;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import wolfpub.WolfPub;


public class Production {
	private static String BookTableName = "Books";
	private static String IssueTableName = "Issue";
	
	/**
	 * Command "wolfpub bookEdition new"
	 * Create a production with WolfPub
	 * 
	 * @param isbn
	 * @param pubID
	 * @param issueDate
	 * @param articleTitle
	 * @param chapterNum
	 * @param sectionNum
	 * @param topicName
	 * 
	 * @param editionNum
	 * @param pubDate
	 * @param editionPrice
	 * @param price
	 * @param issueTitle
	 * @param chapterTitle
	 * @param sectionTitle
	 * @param sectionText
	 * @return
	 */
	
	@Command(name = "newBook", description = "Enter information for a book edition")
	public static int enterBookEdition(@Option( names = {"-p", "-pubID"}, required = true, description = "Book edition publicationID") String pubID,
									   @Option( names = {"-e", "-editionNum"}, defaultValue = "0", description = "Book edition number") Double editionNum,
									   @Option( names = {"-pd", "-pubDate"}, required = true, description = "Book new edition publication date") String pubDate,
									   @Option( names = {"-ep", "editionPrice"}, defaultValue = "0", description = "Book new edition price") Double editionPrice,
									   @Parameters( paramLabel = "ISBN") String isbn) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			/* Add values for the required parameters */
			columns.add("ISBN");
			values.add(isbn);
			
			/* Append optional columns */
			if (pubID != null) {
				columns.add("PublicationID");
				values.add(pubID);
			}
			if (editionNum != null) {
				columns.add("DditionNumber");
				values.add(String.format("%.2f", editionNum));
			}
			if (pubDate != null) {
				columns.add("PublicationDate");
				values.add(pubDate);
			}
			if (editionPrice != null) {
				columns.add("Price");
				values.add(String.format("%.2f", editionPrice));
			}
			
			/* Build query */
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO" ).append(BookTableName).append(" (");
			for (String col : columns) {
				sb.append(col).append(",");
			}
			sb.deleteCharAt(sb.lastIndexOf(",")).append(") VALUES (");
			for (String val : values) {
				sb.append("'").append(val).append(");");
			}
			sb.deleteCharAt(sb.lastIndexOf(",")).append(");");
			
			System.out.println("Try to process" + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
			}
		
		return 0;
	}
	
	
	@Command(name = "update", description = "update book edition")
	public static int updateBookEdition(@Option( names = {"-p", "-pubID"}, required = true, description = "Book edition publicationID") String pubID,
			   							@Option( names = {"-e", "-editionNum"}, defaultValue = "0", description = "Book edition number") Double editionNum,
			   							@Option( names = {"-pd", "-pubDate"}, required = true, description = "Book new edition publication date") String pubDate,
			   							@Option( names = {"-ep", "editionPrice"}, defaultValue = "0", description = "Book new edition price") Double editionPrice,
			   							@Parameters( paramLabel = "ISBN") String isbn) {
		
		System.out.println("TODO: Attempt to update book edition information" + isbn + " with");
		
		if (pubID != null) {
			System.out.println("pubID: " + pubID);
		}
		if (editionNum != null) {
			System.out.println("edition number: " + editionNum);
		}
		if (pubDate != null) {
			System.out.println("Publication Date: " + pubDate);
		}
		if (editionPrice != null) {
			System.out.println("Edition price: " + editionPrice);
		}
		
		return 0;
	}
	
	
	@Command (name = "newIssue", description = "enter information for a new issue")
	public static int enterNewIssue(@Option( names = {"-it", "-issueTitle"}, required = true, description = "new issue title") String issueTitle,
									@Option( names = {"p", "price"}, defaultValue = "0", description = "new issue price") Double price,
									@Parameters( paramLabel = "PublicationID") String pubID,
									@Parameters( paramLabel = "issueDate") String issueDate) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			/* Add values for the required parameters */
			columns.add("pubID");
			values.add(pubID);
			columns.add("issueDate");
			values.add(issueDate);
			
			/* Add optional columns */
			if (issueTitle != null) {
				columns.add("issueTitle");
				values.add(issueTitle);
			}
			if (price != null) {
				columns.add("price");
				values.add(String.format("%.2f", price));
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ").append(IssueTableName).append(" (");
			for (String col : columns) {
				sb.append(col).append(",");
			}
			sb.deleteCharAt(sb.lastIndexOf(",")).append(") VALUES (");
			for (String val : values) {
				sb.append("'").append(val).append("',");
			}
			sb.deleteCharAt(sb.lastIndexOf(",")).append(");");

			System.out.println("Try to process " + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
			}
		
		return 0;
	}
	
	
	@Command( name = "update", description = "update issue")
	public static int updateIssue(@Option( names = {"-it", "-issueTitle"}, required = true, description = "new issue title") String issueTitle,
								  @Option( names = {"p", "price"}, defaultValue = "0", description = "new issue price") Double price,
								  @Parameters( paramLabel = "PublicationID") String pubID,
								  @Parameters( paramLabel = "issueDate") String issueDate) {
		
		System.out.println("TODO: Attempt to update issue " + pubID + " " + issueDate + " with");
		
		if (issueTitle != null) {
			System.out.println("issueTitle: " + issueTitle);
		}
		if (price != null) {
			System.out.println("price: " + price);
		}
		
		return 0;
	}
	
	@Command( name = "delete", description = "Remove an issue from the database")
	public static int deleteIssue(@Parameters( paramLabel = "PublicationID") String pubID,
			  					  @Parameters( paramLabel = "issueDate") String issueDate) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM ").append(IssueTableName).append(" WHERE ");
			sb.append("pubID='" + pubID + "' AND ");
			sb.append("issueDate='" + issueDate + "';");
			
			System.out.println("Try to process " + sb.toString());
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeUpdate(sb.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
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