package tasks;

import java.sql.SQLException;
import java.util.Vector;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import wolfpub.WolfPub;

@Command( name = "production", description = "Production processes")
public class Production {
	private static String EditionTableName = "Edition";
	private static String IssueTableName = "Issue";
	private static String ArticleTableName = "Article";
	private static String ChapterTableName = "Chapter";
	private static String TopicTableName = "Topic";
	private static String AuthortoArticleTableName = "WritesArticle";
	
	/**
	 * Command "wolfpub bookEdition new"
	 * Create a production with WolfPub
	 * 
	 * @param isbn
	 * @param PublicationID
	 * @param issueDate
	 * @param articleTitle
	 * @param chapterNumber
	 * @param sectionNumber
	 * @param topicName
	 * 
	 * @param editionNumber
	 * @param publicationDate
	 * @param editionPrice
	 * @param price
	 * @param issueTitle
	 * @param chapterTitle
	 * @param sectionTitle
	 * @param sectionText
	 * @return
	 */
	
	/* Tested. To enter a new edition of a book, need to enter a new book, a new publication first (foreign key constraints) */
	@Command(name = "enterEdition", description = "Enter information for a new book edition")
	public static int enterBookEdition(@Option( names = {"-p", "-PublicationID"}, required = true, description = "Book edition publicationID") String PublicationID,
									   @Option( names = {"-e", "-editionNumber"}, defaultValue = "0", description = "Book edition number") Double editionNumber,
									   @Option( names = {"-pd", "-publicationDate"}, required = true, description = "Book new edition publication date") String publicationDate,
									   @Option( names = {"-ep", "-editionPrice"}, defaultValue = "0", description = "Book new edition price") Double editionPrice,
									   @Parameters( paramLabel = "ISBN") String isbn) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			/* Add values for the required parameters */
			columns.add("isbn");
			values.add(isbn);
			
			/* Append optional columns */
			if (PublicationID != null) {
				columns.add("PublicationID");
				values.add(PublicationID);
			}
			if (editionNumber != null) {
				columns.add("editionNumber");
				values.add(String.format("%.2f", editionNumber));
			}
			if (publicationDate != null) {
				columns.add("publicationDate");
				values.add(publicationDate);
			}
			if (editionPrice != null) {
				columns.add("editionPrice");
				values.add(String.format("%.2f", editionPrice));
			}
			
			/* Build query */
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ").append(EditionTableName).append(" (");
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
	
	
	@Command(name = "updateEdition", description = "update book edition")
	public static int updateBookEdition(@Option( names = {"-p", "-PublicationID"}, required = true, description = "Book edition publicationID") String PublicationID,
			   							@Option( names = {"-e", "-editionNumber"}, defaultValue = "0", description = "Book edition number") Double editionNumber,
			   							@Option( names = {"-pd", "-publicationDate"}, required = true, description = "Book new edition publication date") String publicationDate,
			   							@Option( names = {"-ep", "editionPrice"}, defaultValue = "0", description = "Book new edition price") Double editionPrice,
			   							@Parameters( paramLabel = "ISBN") String isbn) {
		
		System.out.println("TODO: Attempt to update book edition information" + isbn + " with");
		
		if (PublicationID != null) {
			System.out.println("PublicationID: " + PublicationID);
		}
		if (editionNumber != null) {
			System.out.println("editionNumber: " + editionNumber);
		}
		if (publicationDate != null) {
			System.out.println("publicationDate: " + publicationDate);
		}
		if (editionPrice != null) {
			System.out.println("editionPrice: " + editionPrice);
		}
		
		return 0;
	}
	
	@Command( name = "deleteEdition", description = "Remove an book edition from the database")
	public static int deleteEdition(@Parameters( paramLabel = "PublicationID") String PublicationID,
			  					    @Parameters( paramLabel = "ISBN") String isbn) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM ").append(EditionTableName).append(" WHERE ");
			sb.append("PublicationID='" + PublicationID + "' AND ");
			sb.append("ISBN='" + isbn + "';");
			
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
	
	
	@Command (name = "enterIssue", description = "enter information for a new issue")
	public static int enterNewIssue(@Option( names = {"-it", "-issueTitle"}, required = true, description = "new issue title") String issueTitle,
									@Option( names = {"p", "Price"}, defaultValue = "0", description = "new issue price") Double Price,
									@Parameters( paramLabel = "PublicationID") String PublicationID,
									@Parameters( paramLabel = "issueDate") String issueDate) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			/* Add values for the required parameters */
			columns.add("PublicationID");
			values.add(PublicationID);
			columns.add("issueDate");
			values.add(issueDate);
			
			/* Add optional columns */
			if (issueTitle != null) {
				columns.add("issueTitle");
				values.add(issueTitle);
			}
			if (price != null) {
				columns.add("Price");
				values.add(String.format("%.2f", Price));
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
	
	
	@Command( name = "updateIssue", description = "update issue")
	public static int updateIssue(@Option( names = {"-it", "-issueTitle"}, required = true, description = "new issue title") String issueTitle,
								  @Option( names = {"p", "Price"}, defaultValue = "0", description = "new issue price") Double Price,
								  @Parameters( paramLabel = "PublicationID") String PublicationID,
								  @Parameters( paramLabel = "issueDate") String issueDate) {
		
		System.out.println("TODO: Attempt to update issue " + PublicationID + " " + issueDate + " with");
		
		if (issueTitle != null) {
			System.out.println("issueTitle: " + issueTitle);
		}
		if (price != null) {
			System.out.println("price: " + price);
		}
		
		return 0;
	}
	
	@Command( name = "deleteIssue", description = "Remove an issue from the database")
	public static int deleteIssue(@Parameters( paramLabel = "PublicationID") String PublicationID,
			  					  @Parameters( paramLabel = "issueDate") String issueDate) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM ").append(IssueTableName).append(" WHERE ");
			sb.append("PublicationID='" + PublicationID + "' AND ");
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
	
	
	@Command(name = "enterArticle", description = "enter a article or enter article text into database")
	public static int enterArticle( @Option( names = {"-a", "-articleText"}, required = true, description = "Article text")	String ArticleText,
									@Paramters( paramLabel = "PublicationID")	String PublicationID,
									@Paramters( paramLabel = "Issue Date")		String IssueDate,
									@Paramters( paramLabel = "Article Title")	String ArticleTitle) {
		Vector<String> columns = new Vector<String>();
		Vector<String> values = new Vector<String>();
		
		/* Add values for the required parameters */
		columns.add("PublicationID");
		values.add(PublicationID);
		columns.add("IssueDate");
		values.add(IssueDate);
		columns.add("ArticleTitle");
		values.add(ArticleTitle);
		
		/* Append optional columns */
		if (ArticleText != null) {
			columns.add("ArticleText");
			values.add(ArticleText);
		}
		/* Build the update string */
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(articleTableName).append(" (");
		for (String col : columns) {
			sb.append(col).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(") VALUES (");
		for (String val : values) {
			sb.append("'").append(val).append("',");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(");");

		System.out.println("Try to process " + sb.toString());
		
		try {
			WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeUpdate(sb.toString());	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}
	
	@Command( name = "updateArticle", desciption = "update article or article text")
	public static int updateArticle( @Option( names = {"-a", "-articleText"}, required = true, description = "Article text")	String ArticleText,
									 @Paramters( paramLabel = "PublicationID")	String PublicationID,
									 @Paramters( paramLabel = "Issue Date")		String IssueDate,
									 @Paramters( paramLabel = "Article Title")	String ArticleTitle) {
		
		System.out.println("TODO: Attempt to update article " + PublicationID + " " + IssueDate + ArticleTitle + " with");
		
		if (ArticleText != null) {
			System.out.println("ArticleText: " + ArticleText);
		}
		
		return 0;
	}
	
	
	@Command( name = "enterChapter", description = "enter a chapter into database")
	public static int enterChapter(@Option( names = {"-c", "-chapterTitle"}, required = true, description = "Chapter Title")	String chapterTitle,
								   @Paramters( paramLabel = "ISBN")			 String isbn,
								   @Paramters( paramLabel = "ChapterNumber") String ChapterNumber){
		
		Vector<String> columns = new Vector<String>();
		Vector<String> values = new Vector<String>();
		
		/* Add values for the required parameters */
		columns.add("ISBN");
		values.add(isbn);
		columns.add("ChapterNumber");
		values.add(ChapterNumber);
		
		/* Append optional columns */
		if (chapterTitle != null) {
			columns.add("chapterTitle");
			values.add(chapterTitle);
		}
		/* Build the update string */
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(ChapterTableName).append(" (");
		for (String col : columns) {
			sb.append(col).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(") VALUES (");
		for (String val : values) {
			sb.append("'").append(val).append("',");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(");");

		System.out.println("Try to process " + sb.toString());
		
		try {
			WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeUpdate(sb.toString());	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}
	
	@Command( name = "updateChapter", description = "update chapter")
	public static int updateChapter(@Option( names = {"-c", "-chapterTitle"}, required = true, description = "Chapter Title")	String chapterTitle,
									@Paramters( paramLabel = "ISBN")		  String isbn,
									@Paramters( paramLabel = "ChapterNumber") String ChapterNumber) {
		
		System.out.println("TODO: Attempt to update chapter " + isbn + " " + ChapterNumber + " with");
		
		if (chapterTitle != null) {
			System.out.println("chapterTitle: " + chapterTitle);
		}
		
		return 0;
		
	}
	
	@Command( name = "enterTopic", description = "enter topic into publications")
	public static int enterTopic(@Parameters( paramLabel = "TopicName")		String TopicName,
											   @Parameters( paramLabel = "PublicationID")	String PublicationID) {
		Vector<String> columns = new Vector<String>();
		Vector<String> values = new Vector<String>();
		
		/* Add values for the required parameters */
		columns.add("TopicName");
		values.add(TopicName);
		columns.add("PublicationID");
		values.add(PublicationID);
		
		/* Build the update string */
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(TopicTableName).append(" (");
		for (String col : columns) {
			sb.append(col).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(") VALUES (");
		for (String val : values) {
			sb.append("'").append(val).append("',");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(");");

		System.out.println("Try to process " + sb.toString());
		
		try {
			WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeUpdate(sb.toString());	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}
	
	@Command( name = "updateTopic", description = "update publication topic")
	public static int updateTopic(@Parameters( paramLabel = "TopicName")		String TopicName,
								  @Parameters( paramLabel = "PublicationID")	String PublicationID) {
		
		System.out.println("TODO: Attempt to update topic " + TopicName + " " + PublicationID);
		
		return 0;
		
	}
	
	@Command( name = "enterAuthorAOrDatetoArticle", description = "enter author or date to article with table WritesArticle")
	public static int enterAuthortoArticle(@Parameters( paramLabel = "AuthorSSN")		String AuthorSSN,
										   @Parameters( paramLabel = "PublicationID")	String PublicationID,
										   @Parameters( paramLabel = "IssueDate")		String IssueDate,
										   @Parameters( paramLabel = "ArticleTitle")	String ArticleTitle) {
		Vector<String> columns = new Vector<String>();
		Vector<String> values = new Vector<String>();
		
		/* Add values for the required parameters */
		columns.add("AuthorSSN");
		values.add(AuthorSSN);
		columns.add("PublicationID");
		values.add(PublicationID);
		columns.add("IssueDate");
		values.add(IssueDate);
		columns.add("ArticleTitle");
		values.add(ArticleTitle);
		
		/* Build the update string */
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(AuthortoArticleTableName).append(" (");
		for (String col : columns) {
			sb.append(col).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(") VALUES (");
		for (String val : values) {
			sb.append("'").append(val).append("',");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(");");

		System.out.println("Try to process " + sb.toString());
		
		try {
			WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeUpdate(sb.toString());	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}
	
	@Command( name = "updateAuthorOrDatetoArticle", description = "update author or date information to article with table WritesArticle"))
	public static int updateAuthortoArticle(@Parameters( paramLabel = "AuthorSSN")		String AuthorSSN,
										    @Parameters( paramLabel = "PublicationID")	String PublicationID,
										    @Parameters( paramLabel = "IssueDate")		String IssueDate,
										    @Parameters( paramLabel = "ArticleTitle")	String ArticleTitle) {
		
		System.out.println("TODO: Attempt to update author with article " + AuthorSSN + " " + PublicationID + " " + IssueDate + " " + ArticleTitle);
		
		return 0;
	}
	
	
	public static int getPublicationIssueByISSN(String issn) {
			
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}

	public static int getPublicationIssueByAttr(String title, String type, String publicationPeriod, String[] authors, String editors[]) {
		
		return 0; // decide on the appropriate return type; either return a tuple or print to stdout
	}
	
	public static int getBookByISBN(String isbn) {
	
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