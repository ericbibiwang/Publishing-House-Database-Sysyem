package tasks;

import java.sql.SQLException;
import java.util.Vector;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import wolfpub.WolfPub;
import wolfpub.WolfPubDb;

@Command( name = "production", description = "Production processes")
public class Production {
	private static String EditionTableName = "Edition";
	private static String IssueTableName = "Issue";
	private static String ArticleTableName = "Article";
	private static String ChapterTableName = "Chapter";
	private static String TopicTableName = "Topic";
	private static String AuthortoArticleTableName = "WritesArticle";
	private static String StaffPaymentTableName = "Employee";
	
	
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
			if (Price != null) {
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
		if (Price != null) {
			System.out.println("price: " + Price);
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
									@Parameters( paramLabel = "PublicationID")	String PublicationID,
									@Parameters( paramLabel = "Issue Date")		String IssueDate,
									@Parameters( paramLabel = "Article Title")	String ArticleTitle) {
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
		sb.append("INSERT INTO ").append(ArticleTableName).append(" (");
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
	
	@Command( name = "updateArticle", description = "update article or article text")
	public static int updateArticle( @Option( names = {"-a", "-articleText"}, required = true, description = "Article text")	String ArticleText,
									 @Parameters( paramLabel = "PublicationID")	String PublicationID,
									 @Parameters( paramLabel = "Issue Date")		String IssueDate,
									 @Parameters( paramLabel = "Article Title")	String ArticleTitle) {
		
		System.out.println("TODO: Attempt to update article " + PublicationID + " " + IssueDate + ArticleTitle + " with");
		
		if (ArticleText != null) {
			System.out.println("ArticleText: " + ArticleText);
		}
		
		return 0;
	}
	
	
	@Command( name = "enterChapter", description = "enter a chapter into database")
	public static int enterChapter(@Option( names = {"-c", "-chapterTitle"}, required = true, description = "Chapter Title")	String chapterTitle,
								   @Parameters( paramLabel = "ISBN")			 String isbn,
								   @Parameters( paramLabel = "ChapterNumber") String ChapterNumber){
		
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
									@Parameters( paramLabel = "ISBN")		  String isbn,
									@Parameters( paramLabel = "ChapterNumber") String ChapterNumber) {
		
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
	
	@Command( name = "updateAuthorOrDatetoArticle", description = "update author or date information to article with table WritesArticle")
	public static int updateAuthortoArticle(@Parameters( paramLabel = "AuthorSSN")		String AuthorSSN,
										    @Parameters( paramLabel = "PublicationID")	String PublicationID,
										    @Parameters( paramLabel = "IssueDate")		String IssueDate,
										    @Parameters( paramLabel = "ArticleTitle")	String ArticleTitle) {
		
		System.out.println("TODO: Attempt to update author with article " + AuthorSSN + " " + PublicationID + " " + IssueDate + " " + ArticleTitle);
		
		return 0;
	}
	
	@Command( name = "getIssueByPubID", description = "find issue with publicationID")
	public static void getPublicationIssueByPubID(@Parameters( paramLabel = "PublicationID" )	String PublicationID,
												  @Parameters( paramLabel = "IssueDate" )		String IssueDate) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			/* Add values for the required parameters */
			columns.add("PublicationID");
			values.add(PublicationID);
			columns.add("IssueDate");
			values.add(IssueDate);
			
			/* Add query*/
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT IssueTitle, Price FROM Issue WHERE PublicationID=").append(PublicationID).append(" ;");
			
			System.out.println("Try to process " + sb.toString());
			
			/* Execute query*/
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
	

	@Command( name = "getIssueByDate", description = "find issue with IssueDate")
	public static void getPublicationIssueByDate(@Parameters( paramLabel = "PublicationID" )	String PublicationID,
												 @Parameters( paramLabel = "IssueDate" )		String IssueDate) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			/* Add values for the required parameters */
			columns.add("PublicationID");
			values.add(PublicationID);
			columns.add("IssueDate");
			values.add(IssueDate);
			
			/* Add query*/
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT IssueTitle, Price FROM Issue WHERE IssueDate=").append(IssueDate).append(" ;");
			
			System.out.println("Try to process " + sb.toString());
			
			/* Execute query*/
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
	@Command( name = "getArticleByAttr", description = "get article by attributes")
	public static void getArticleByAttr(@Parameters( paramLabel = "AuthorSSN" )		String AuthorSSN,
										@Parameters( paramLabel = "PublicationID" )	String PublicationID,
										@Parameters( paramLabel = "IssueDate" )		String IssueDate,
										@Parameters( paramLabel = "ArticleTitle" )	String ArticleTitle) {
		try {
			/* Add query*/
			StringBuilder sb = new StringBuilder();
			// TODO: many cases
			sb.append("SELECT AuthorSSN, PublicationID, IssueDate, ArticleTitle FROM Article WHERE PublicationID=").append(PublicationID).append(" AND IssueDate=").append(IssueDate).append(" AND ArticleTitle=").append(ArticleTitle).append(" ;");
			
			System.out.println("Try to process " + sb.toString());
			
			/* Execute query*/
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	
	@Command( name = "getBookByISBN", description = " get book by ISBN")
	// Assume ISBN is the publicationID
	public static void getBookByISBN(@Parameters( paramLabel = "PublicationID" )	String PublicationID,
									 @Option( names = {"-p", "-PublicationType"}, required = true, description = "Publication Type")	String PublicationType) {
		try {
			/* Add query*/
			StringBuilder sb = new StringBuilder();
			
			sb.append("SELECT PublicationTitle, PublicationID FROM Publication NATURAL JOIN Book WHERE PublicationID=").append(PublicationID).append(" AND PublicationType=").append(PublicationType).append(" ;");
			
			System.out.println("Try to process " + sb.toString());
			
			/* Execute query*/
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}

	@Command ( name = "getBookByAttr", description = "get book by attributes")
	public static void getBookByAttr(@Parameters( paramLabel = "PublicationID" )	String PublicationID,
									 @Option( names = {"-pd", "-publicationDate"}, description = "Book Publication Date")	String PublicationDate,
									 @Option( names = {"-t", "-publicationType"}, description = "Publication Type")			String PublicationType,
									 @Option( names = {"-e", "-editionNumber"}, description = "Edition Number")				Double EditionNumber,
									 @Option( names = {"-pt", "-PublicationTitle"}, description = "Publication Title")		String PublicationTitle) {
		try {
			/* Add query*/
			StringBuilder sb = new StringBuilder();
			/*
			 * by publicationTitle, publicationType, editionNumber to get related publicationID
			 */
			sb.append("SELECT PublicationID FROM Book NATURAL JOIN Publication NATURAL JOIN Edition WHERE PublicationTitle=").append(PublicationTitle).append(" AND PublicationType=").append(PublicationType).append(" AND EditionNumber=").append(EditionNumber).append(" ;");
			
			System.out.println("Try to process " + sb.toString());
			
			/* Execute query*/
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}

	@Command( name = "enterpayment", description = "enter payment of staff or invited employee")
	// difference between staff and invited are contractPay and PayPeriod.
	public static int enterRegularStaffPayment(@Option( names = {"-n", "-name"}, description = "Employee name")			String Name,
											   @Option( names = {"-c", "-contractPay"}, description = "Contract Pay")	Float Contractpay,
											   @Option( names = {"p", "-payPeriod"}, description = "Pay Period")		String PayPeriod,
											   @Parameters( paramLabel = "SSN")		String SSN) {
		try {
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			/* Add values for the required parameters */
			columns.add("SSN");
			values.add(SSN);
			
			/* Append optional columns */
			if (Name != null) {
				columns.add("Name");
				values.add(Name);
			}
			if (Contractpay != null) {
				columns.add("Contractpay");
				values.add(String.format("%.2f", Contractpay));
			}
			if (PayPeriod != null) {
				columns.add("PayPeriod");
				values.add(PayPeriod);
			}
			
			/* Build query */
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ").append(StaffPaymentTableName).append(" (");
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
	
	@Command( name = "track", description = "keep track of when each payment was claimed by its address")
	// Since in schema we have PaymentID associate with payment, we can use PaymentID to track payment with related SSN
	public static void trackPayment(@Option( names = {"-a", "-amount"}, description = "Payment Amount")			Float Amount,
									@Option( names = {"-d", "-datePickedUp"}, description = "Date picked Up")	String  dataPcikedUp,
									@Parameters( paramLabel = "SSN")		String SSN,
									@Parameters( paramLabel = "PaymentID")	String PaymentID) {
		try {
			/* Add query*/
			StringBuilder sb = new StringBuilder();
			
			sb.append("SELECT PaymentID, DatePickedUp FROM Payment WHERE SSN=").append(SSN).append(" ;");
			
			System.out.println("Try to process " + sb.toString());
			
			/* Execute query*/
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
	
}