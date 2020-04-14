package tasks;

import java.sql.SQLException;
import java.util.Vector;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import wolfpub.WolfPub;
import wolfpub.WolfPubDb;

/**
 * @author Tianqiang Cao
 *
 */

@Command( name = "production", description = "Production processes")
public class Production {
	private static String EditionTableName = "Edition";
	private static String IssueTableName = "Issue";
	private static String ArticleTableName = "Article";
	private static String ChapterTableName = "Chapter";
	private static String PublicationTopicTableName = "PublicationHas";
	private static String AuthortoArticleTableName = "WritesArticle";
	private static String StaffPaymentTableName = "Employee";
	
	
	/* description: enter a book edition with foreign key constraints
	 * Using MySQLWorkbench
	 * INSERT INTO `cdsuh`.`Publication` (`PublicationID`, `PublicationTitle`, `PublicationType`) VALUES ('2', 'test', 'Book');
	 * INSERT INTO `cdsuh`.`Book` (`PublicationID`) VALUES ('2');
	 * INSERT INTO `cdsuh`.`Edition` (`ISBN`, `PublicationID`, `EditionNumber`, `PublicationDate`) VALUES ('123123', '2', '2', '2020-01-01');
	 *  
	 * Check MySQLWorkbench results after run configuration as follow: production enterEdition 123123 -p 2 -e 2 -pd 2020-01-01 -ep 30
	 * Test passed.
	 */
	
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
			db.executeQueryAndPrintResults(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
			}
		
		return 0;
	}
	
	/* description: update edition with edition number, publication date, edition price, keep publicationID and ISBN
	 * Check MySQLWorkbench results after run configuration as follow: production updateEdition 123123 -p 2 -e 3 -pd 2020-03-31 -ep 40 
	 * Test passed.
	 */
	@Command(name = "updateEdition", description = "update book edition")
	public static int updateBookEdition(@Option( names = {"-p", "-PublicationID"}, required = true, description = "Book edition publicationID") String PublicationID,
			   							@Option( names = {"-e", "-editionNumber"}, defaultValue = "0", description = "Book edition number") Double editionNumber,
			   							@Option( names = {"-pd", "-publicationDate"}, required = true, description = "Book new edition publication date") String publicationDate,
			   							@Option( names = {"-ep", "editionPrice"}, defaultValue = "0", description = "Book new edition price") Double editionPrice,
			   							@Parameters( paramLabel = "ISBN") String isbn) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE ").append(EditionTableName).append(" SET ");
			if (editionNumber != null) {
				sb.append("EditionNumber=" + editionNumber + ",");
			}
			if (publicationDate!= null) {
				sb.append("PublicationDate=" + publicationDate + ",");
			}
			if (editionPrice != null) {
				sb.append("editionPrice=" + editionPrice + ",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append(" WHERE PublicationID = '" + PublicationID + "' AND ISBN='" + isbn + "';");
			
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
	
	/* description: delete edition with edition publicationID(foreign key) and ISBN
	 * Check MySQLWorkbench results after run configuration as follow: production deleteEdition 2 123123
	 * Test passed.
	 */
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
	
	/* description: enter an issue
	 * INSERT INTO `cdsuh`.`Publication` (`PublicationID`, `PublicationTitle`) VALUES ('3', 'test');
	 * INSERT INTO `cdsuh`.`NonBook` (`PublicationID`, `Period`) VALUES ('3', 'Weekly');
	 * Check MySQLWorkbench results after run configuration as follow: production enterIssue 3 2019-01-01 -it test
	 * Test passed.
	 */
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
			db.executeQueryAndPrintResults(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
			}
		
		return 0;
	}
	
	/* description: update issue with issue title, price, keep publicationID and issueDate
	 * Check MySQLWorkbench results after run configuration as follow: production updateIssue 3 2019-01-01 -it updateIssue -p 20 
	 * Test passed.
	 */
	@Command( name = "updateIssue", description = "update issue")
	public static int updateIssue(@Option( names = {"-it", "-issueTitle"}, required = true, description = "new issue title") String issueTitle,
								  @Option( names = {"-p", "Price"}, defaultValue = "0", description = "new issue price") Double Price,
								  @Parameters( paramLabel = "PublicationID") String PublicationID,
								  @Parameters( paramLabel = "issueDate") 	 String issueDate) {
		
		System.out.println("TODO: Attempt to update issue " + PublicationID + " " + issueDate + " with");
		
		if (issueTitle != null) {
			System.out.println("issueTitle: " + issueTitle);
		}
		if (Price != null) {
			System.out.println("price: " + Price);
		}
		
		return 0;
	}
	
	/* description: delete issue with issueDate, publicationID(foreign key)
	 * Check MySQLWorkbench results after run configuration as follow: production deleteIssue 3 2019-01-01
	 * Test passed.
	 */
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
	
	/* description: enter an article
	 * INSERT INTO `cdsuh`.`Publication` (`PublicationID`, `PublicationTitle`) VALUES ('5', 'test5');
	 * INSERT INTO `cdsuh`.`NonBook` (`PublicationID`) VALUES ('5');
	 * INSERT INTO `cdsuh`.`Issue` (`PublicationID`, `IssueDate`, `IssueTitle`, `Price`) VALUES ('5', '2020-05-01', 'test5', '5');
	 * Check MySQLWorkbench results after run configuration as follow: production enterArticle 5 2020-05-01 test5 -a text5
	 * Test passed.
	 */
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
	/* 
	 * Test passed.
	 */
	@Command( name = "updateArticle", description = "update article or article text")
	public static int updateArticle( @Option( names = {"-a", "-articleText"}, required = true, description = "Article text")	String ArticleText,
									 @Parameters( paramLabel = "PublicationID")	String PublicationID,
									 @Parameters( paramLabel = "Issue Date")		String IssueDate,
									 @Parameters( paramLabel = "Article Title")	String ArticleTitle) {
		String query = "UPDATE Article SET ArticleText='"+ArticleText+"' WHERE PublicationID='"+PublicationID+"' AND IssueDate"
				+ "='"+IssueDate+"' AND ArticleTitle='"+ArticleTitle+"'";
		
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			db.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/* description: enter an chapter to an exist book which publicationID is 1234567890
	 * Check MySQLWorkbench results after run configuration as follow: production enterChapter 1234567890 5 -c title
	 * Test passed.
	 */
	
	// here we assume isbn is the publicationID
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
	
	/* description: update chapter
	 * Check MySQLWorkbench results after run configuration as follow: production updateChapter 1234567890 5 -c update
	 * Test passed.
	 */
	@Command( name = "updateChapter", description = "update chapter")
	public static int updateChapter(@Option( names = {"-c", "-chapterTitle"}, required = true, description = "Chapter Title")	String chapterTitle,
									@Parameters( paramLabel = "ISBN")		   String isbn,
									@Parameters( paramLabel = "ChapterNumber") String ChapterNumber) {
		
		String query = "UPDATE Chapter SET ChapterTitle='"+chapterTitle+"' WHERE ISBN='"+isbn+"' AND ChapterNumber"
				+ "="+ChapterNumber;
		
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			db.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/* description: enter topic to publication
	 * INSERT INTO `cdsuh`.`Publication` (`PublicationID`) VALUES ('testTopic');
	 * use an exist topic "politics"
	 * Check MySQLWorkbench results after run configuration as follow: production enterTopic politics testTopic
	 * Test passed.
	 */
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
		sb.append("INSERT INTO ").append(PublicationTopicTableName).append(" (");
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
	
	/* description: update publication's topic (in publicationHas table)
	 * Check MySQLWorkbench results after run configuration as follow: production updateTopic Fiction testTopic
	 * Test passed.
	 */
	@Command( name = "updateTopic", description = "update publication topic")
	public static int updateTopic(@Parameters( paramLabel = "TopicName")		String TopicName,
								  @Parameters( paramLabel = "PublicationID")	String PublicationID) {
		
		String query = "UPDATE PublicationHas SET TopicName='"+TopicName+"' WHERE PublicationID='"+PublicationID+"'";
		
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			db.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			db.executeQueryAndPrintResults(sb.toString());	
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
			db.executeQueryAndPrintResults(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
	@Command( name = "getArticleByAttr", description = "get article by attributes")
	public static void getArticleByAttr(@Option( names = "-author", description = "name of author", paramLabel = "AuthorSSN" ) String AuthorSSN,
										@Option( names = "-issn", description = "ISSN of publication", paramLabel = "PublicationID" ) String PublicationID,
										@Option( names = "-issued", description = "Date article was published", paramLabel = "IssueDate" ) String IssueDate,
										@Option( names = "-article_title", description = "title of article", paramLabel = "ArticleTitle" ) String ArticleTitle) {
		if (AuthorSSN == null && PublicationID == null && IssueDate == null&& ArticleTitle == null) {
			System.err.println("Search by -author, -issn, -issued, or -article_title");
		}
		try {
			boolean andit = false;
			/* Add query*/
			StringBuilder sb = new StringBuilder();
			// TODO: many cases
			sb.append("SELECT * FROM Article WHERE ");
			if (PublicationID != null) {
				sb.append("PublicationID='" + PublicationID + "'");
				andit = true;
			}
			if (AuthorSSN != null) {
				if(andit) sb.append(" AND ");
				sb.append("AuthorSSN='" + AuthorSSN + "'");
				andit=true;
			}
			if (IssueDate != null) {
				if(andit) sb.append(" AND ");
				sb.append("IssueDate='" + IssueDate + "'");
				andit=true;
			}
			if (ArticleTitle != null) {
				if(andit) sb.append(" AND ");
				sb.append("ArticleTitle='" + ArticleTitle + "'");
			}
			sb.append(";");
			
			System.out.println("Try to process " + sb.toString());
			
			/* Execute query*/
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQueryAndPrintResults(sb.toString());	
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
			db.executeQueryAndPrintResults(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}

	@Command ( name = "getBookByAttr", description = "get book by attributes")
	public static void getBookByAttr(@Option( names = "-publicationDate", description = "Book Publication Date", paramLabel = "PublicationDate")	String PublicationDate,
									 @Option( names = "-publicationType", description = "Publication Type", paramLabel = "publicationType")			String PublicationType,
									 @Option( names = "-editionNumber", description = "Edition Number", paramLabel = "EditionNumber")				Double EditionNumber,
									 @Option( names = "-PublicationTitle", description = "Publication Title", paramLabel = "PublicationTitle")		String PublicationTitle) {
		if (PublicationDate == null && PublicationType == null && EditionNumber == null && PublicationTitle == null) {
			System.err.println("Search by -publicationDate, -publicationType, -editionNumber, or -PublicationTitle");
		}
		try {
			boolean andit = false;
			/* Add query*/
			StringBuilder sb = new StringBuilder();
			// TODO: many cases
			sb.append("SELECT * FROM Book NATURAL JOIN Edition NATURAL JOIN Publication WHERE ");
			if (PublicationDate != null) {
				sb.append("PublicationDate='" + PublicationDate + "'");
				andit = true;
			}
			if (PublicationType != null) {
				if(andit) sb.append(" AND ");
				sb.append("PublicationType='" + PublicationType + "'");
				andit=true;
			}
			if (EditionNumber != null) {
				if(andit) sb.append(" AND ");
				sb.append("EditionNumber='" + EditionNumber + "'");
				andit=true;
			}
			if (PublicationTitle != null) {
				if(andit) sb.append(" AND ");
				sb.append("PublicationTitle='" + PublicationTitle + "'");
			}
			sb.append(";");
			
			System.out.println("Try to process " + sb.toString());
			
			/* Execute query*/
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQueryAndPrintResults(sb.toString());	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Command( name = "enterpayment", description = "enter payment of staff or invited employee")
	// difference between staff and invited are contractPay and PayPeriod.
	// add contractPay is 0 then (s)he is an invited employee.
	public static int enterRegularStaffPayment(@Option( names = {"-n", "-name"}, description = "Employee name")			String Name,
											   @Option( names = {"-c", "-contractPay"}, description = "Contract Pay")	Float Contractpay,
											   @Option( names = {"-p", "-payPeriod"}, description = "Pay Period")		String PayPeriod,
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
			db.executeQueryAndPrintResults(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
			}
		
		return 0;
	}
	
	@Command( name = "track", description = "keep track of when each payment was claimed by its PaymentID")
	// Since in schema we have PaymentID associate with payment, we can use SSN and paymentID to track payment with related SSN
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
			db.executeQueryAndPrintResults(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
}
