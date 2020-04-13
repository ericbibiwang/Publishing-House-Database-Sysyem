package tasks;

import java.sql.SQLException;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import wolfpub.WolfPubDb;

@Command(name = "publish", 
description = "Manage publications"
)
public class EditingPublishing {
	
	// helper methods to enclose string variable in double quotes
	
	private static String enclose(String arg) {
		return "'"+arg+"'";
	}
	
	private static void enclose(String[] arr) {
		
		for(int i=0; i<arr.length; i++) {
			
			arr[i] = "'"+arr[i]+"'";
		}
	}
	
	// checks if the user entered a valid topic or not
	
	private static boolean checkTopic(String topic) {
		
		
		
		String[] validTopics = new String[] {"General", "Sports", "Technology", "Business", "Politics", "Fiction"};
		
		for(String name: validTopics) {
			
			if(name.equals(topic)) {
				
				return true;
			}
			
			
			
		}
		
		return false;
		
	}
	
	@Command(name = "enterBook", description = "Enter a new book")
	public static int enterNewBook(
			
			/*
			 * This task requires following operations:
			 * 
			 * 1. Insert the publication value in Publication table
			 * 2. Insert the publication value in the Book table
			 * 
			 */
			@Option( names = {"-t", "-title"},required=true, description = "Title of the book") String title,
			@Option( names = {"-i", "-isbn"},required=true, description = "ISBN of the first edition of the book") String isbn,
			@Option( names = {"-a", "-topic"},required=true, description = "Topics associated with the book", split=",") String[] topics) {
		
		
		// ensure that valid topics were entered
		
		for (String topic : topics) {
			
			if(! checkTopic(topic)) {
				
				System.out.println("One of the topics that you entered is not valid. Please enter a valid topic.");
				
				return -1;
				
			}
		}
		
		// enclose all the string variables in single
		title = EditingPublishing.enclose(title);
		isbn = EditingPublishing.enclose(isbn);
		EditingPublishing.enclose(topics);
		
		
				
		String type = "'Book'";
		int result;
		String template = "INSERT INTO %s VALUES(%s);";
		
		String publicationQuery = String.format(template, "Publication",String.join(",",isbn, title, type));
		String bookQuery = String.format(template,"Books", isbn);
		String topicQuery;
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			// add publication
			result = db.executeUpdate(publicationQuery);
			System.out.println("Number of rows affected in the Publication Table: "+result);
			// add book
			result = db.executeUpdate(bookQuery);
			System.out.println("Number of rows affected in the Book Table: "+result);
			
			result = 0;
			
			for (String topic: topics) {
				
				 topicQuery = String.format(template, "PublicationHas", String.join(",",topic,isbn));
				 
				 result += db.executeUpdate(topicQuery);
				 
			}
			
			System.out.println("Number of rows affected in the PublicationHas table: "+result);
			db.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return 0;
	}
	
	@Command(name = "enterNonBook", description = "Enter a new journal or magazine")
	public static int enterNewJournalMagazine(
			@Option( names = {"-t", "-title"},required=true, description = "Title of the journal or Magazine") String title, 
			@Option( names = {"-i", "-issn"},required=true, description = "The ISSN number of the journal or magazine") String issn, 
			@Option( names = {"-p", "-period"},required=true, description = "The frequency at which the Journal or the Magazine is published") String period,
			@Option( names = {"-a", "-topics"},required=true, description = "All the topics addressed by the journal or magazine", split=",") String[] topics) {
		
		// ensure that valid topics were entered
		
				for (String topic : topics) {
					
					if(! checkTopic(topic)) {
						
						System.out.println("One of the topics that you entered is not valid. Please enter a valid topic.");
						
						return -1;
						
					}
				}
				
		
		title = EditingPublishing.enclose(title);
		issn = EditingPublishing.enclose(issn);
		period = EditingPublishing.enclose(period);
		EditingPublishing.enclose(topics);
		
		
		/*
		 *  add to Publication table
		 *  add to the NonBook table
		 *  add to the PublicationHas table to connect the magazine with a topic
		 *  
		 *  
		 */
		
		//and then add to NonBook table
		
		String type = "'NonBook'";
		int result;
		String template = "INSERT INTO %s VALUES(%s);";
		
		String publicationQuery = String.format(template, "Publication",String.join(",",issn, title, type));
		String nonBookQuery = String.format(template,"NonBook", String.join(",",issn,period));
		String topicQuery;
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			// add publication
			result = db.executeUpdate(publicationQuery);
			System.out.println("Number of rows affected in the Publication Table: "+result);
			// add book
			result = db.executeUpdate(nonBookQuery);
			System.out.println("Number of rows affected in the NonBook Table: "+result);
			
			result = 0;
			
			for (String topic: topics) {
				
				 topicQuery = String.format(template, "PublicationHas", String.join(",",topic,issn));
				 result += db.executeUpdate(topicQuery);
				 
			}
			
			System.out.println("Number of rows affected in the PublicationHas table: "+result);
			db.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	
	@Command(name = "updateBook", description = "Update information about an existing book")
	public static int updateBook(
			@Option( names = {"-t", "-title"},required=true, description = "Title of the book") String title,
			@Option( names = {"-i", "-isbn"},required=true, description = "ISBN of the first edition of the book") String isbn,
			@Option( names = {"-a", "-topic"},required=true, description = "Topics associated with the book", split=",") String[] topics) {
		
		// ensure that valid topics were entered
		
				for (String topic : topics) {
					
					if(! checkTopic(topic)) {
						
						System.out.println("One of the topics that you entered is not valid. Please enter a valid topic.");
						
						return -1;
						
					}
				}
				
				// enclose all the string variables in single
				title = EditingPublishing.enclose(title);
				isbn = EditingPublishing.enclose(isbn);
				EditingPublishing.enclose(topics);
				
				/*
				 * Update the title of book associated with isbn to the new title
				 * Delete all the topics associate with the book, then insert the new topics
				 */
						
				
				int result;
				
				String template = "INSERT INTO %s VALUES(%s);";
				String updateTitle = "UPDATE Publication SET PublicationTitle="+title+"WHERE PublicationID="+isbn;
				String deleteTopics = "DELETE FROM PublicationHas WHERE PublicationID="+isbn;
				
				
				
				String topicQuery;
				
				try {
					WolfPubDb db = new WolfPubDb();
					db.createStatement();
					// update title
					result = db.executeUpdate(updateTitle);
					System.out.println("Number of rows updated in the Publication Table: "+result);
					// add book
					result = db.executeUpdate(deleteTopics);
					System.out.println("Number of old topics removed: "+result);
					
					result = 0;
					
					for (String topic: topics) {
						
						 topicQuery = String.format(template, "PublicationHas", String.join(",",topic,isbn));
						 
						 result += db.executeUpdate(topicQuery);
						 
					}
					
					System.out.println("Number of new topics added: "+result);
					db.close();
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
		
		return 0;
	}
	
	
	@Command(name = "updateNonBook", description = "Update information about an existing journal or magazine")
	public static int updateJournalMagazine(
			@Option( names = {"-t", "-title"},required=true, description = "Title of the journal or Magazine") String title, 
			@Option( names = {"-i", "-issn"},required=true, description = "The ISSN number of the journal or magazine") String issn, 
			@Option( names = {"-p", "-period"},required=true, description = "The frequency at which the Journal or the Magazine is published") String period,
			@Option( names = {"-a", "-topics"},required=true, description = "All the topics addressed by the journal or magazine", split=",") String[] topics) {
		
		
		
		// ensure that valid topics were entered
		
		for (String topic : topics) {
			
			if(! checkTopic(topic)) {
				
				System.out.println("One of the topics that you entered is not valid. Please enter a valid topic.");
				
				return -1;
				
			}
		}
		

		title = EditingPublishing.enclose(title);
		issn = EditingPublishing.enclose(issn);
		period = EditingPublishing.enclose(period);
		EditingPublishing.enclose(topics);
		
		
		/*
		 *  Update title in the Publication table
		 *  Update period in the NonBook table
		 *  Remove all the old topics associated with the publication
		 *  add the new topics 
		 *  
		 */
		
		//and then add to NonBook table
		
		
		int result;
		String template = "INSERT INTO %s VALUES(%s);";
		
		String updateTitle = "UPDATE Publication SET PublicationTitle="+title+" WHERE PublicationID="+issn;
		String updatePeriod = "UPDATE NonBook SET Period="+period+" WHERE PublicationID="+issn;
		String deleteTopics = "DELETE FROM PublicationHas WHERE PublicationID="+issn;
		String topicQuery;
		WolfPubDb db = null;
		try {
			db = new WolfPubDb();
			db.autoCommitOff();
			db.createStatement();
			// 
			result = db.executeUpdate(updateTitle);
			System.out.println("Number of rows affected in the Publication Table: "+result);
			// 
			result = db.executeUpdate(updatePeriod);
			System.out.println("Number of rows affected in the NonBook Table: "+result);
			
			// delete topics
			result = db.executeUpdate(deleteTopics);
			System.out.println("Number of old topics deleted: "+result);
			
			result = 0;
			
			for (String topic: topics) {
				
				 topicQuery = String.format(template, "PublicationHas", String.join(",",topic,issn));
				 result += db.executeUpdate(topicQuery);
				 
			}
			db.commit();
			System.out.println("Number of new topics added: "+result);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(db != null) {
				db.rollback();
				System.out.println("Transaction failed. Rolling back the transaction");
			}
			
			
			e.printStackTrace();
		}
		finally {
			if(db != null) {
				db.autoCommitOn();
			}
			
		}
		
		return 0;
	}
	
	
	@Command(name = "assignEditorBook", description = "Assign editors to a Book Edition")
	public static int assignEditor(
			@Option( names = {"-s", "-ssn"},required=true, description = "The Social Security Number of the editor")String ssn, 
			@Option( names = {"-i", "-isbn"},required=true, description = "The ISBN number of the book edition")String isbn) {
		
		ssn = EditingPublishing.enclose(ssn);
		isbn = EditingPublishing.enclose(isbn);
		
		String template = "INSERT INTO EditsEdition VALUES(%s)";
		String query = String.format(template, String.join(",",ssn,isbn));
		int result;
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			result = db.executeUpdate(query);
			System.out.println("Number of rows changes in the EditsEdition Table: "+result);
			db.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	@Command(name = "assignEditorNonBook", description = "Assign editors to a Book Edition")
	public static int assignEditorNonBook(
			@Option( names = {"-s", "-ssn"},required=true, description = "The Social Security Number of the editor")String ssn, 
			@Option( names = {"-i", "-isbn"},required=true, description = "The ISSN number of the journal or magazine")String issn,
			@Option( names = {"-d", "-date"},required=true, description = "The publication date of an isssue")String date) {
		
		ssn = EditingPublishing.enclose(ssn);
		issn = EditingPublishing.enclose(issn);
		date = EditingPublishing.enclose(date);
		
		
		String template = "INSERT INTO EditsEdition VALUES(%s)";
		String query = String.format(template, String.join(",",ssn,issn,date));
		int result;
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			result = db.executeUpdate(query);
			System.out.println("Number of rows changes in the EditsEdition Table: "+result);
			db.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	
	@Command(name = "addArticle", description = "Add an article to a magazine or a journal issue")
	public static int addArticle(
			@Option( names = {"-t", "-title"},required=true, description = "The title of the article") String title, 
			@Option( names = {"-w", "-text"},required=true, description = "The text of the article") String text, 
			@Option( names = {"-i", "-issn"},required=true, description = "The ISSN date of the publication") String issn, 
			@Option( names = {"-d", "-date"},required=true, description = "The publication date of the issue") String publicationDate, 
			@Option( names = {"-a", "-ssn", "-author", "-authors"},required=true, description = "The Social Security Numbers of the authors of the article", split=",") String[] authors) {
		
		// make sure all the entered topics are valid
		
		
		
		
		title = EditingPublishing.enclose(title);
		text = EditingPublishing.enclose(text);
		issn = EditingPublishing.enclose(issn);
		publicationDate = EditingPublishing.enclose(publicationDate);
		EditingPublishing.enclose(authors);
		
		// add an entry in the article and then for each author add an entry to the WritesArticle table
		
		String template = "INSERT INTO %s VALUES(%s);";
		String articleQuery = String.format(template, "Article",String.join(",",issn,publicationDate,title,text));
		String relationshipQuery;
		int result;
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			result = db.executeUpdate(articleQuery);
			System.out.println("Number of rows added to the Article Table: "+result);
			
			result = 0;
			
			for(String ssn: authors) {
				
				relationshipQuery = String.format(template, "WritesArticle",String.join(",",ssn,issn,publicationDate,title));
				result += db.executeUpdate(relationshipQuery);
				
			}
			
			System.out.println("Number of rows deleted from the WritesArticle Table: "+result);
			db.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		return 0;
		
	}
	
	@Command(name = "deleteArticle", description = "Delete article from a journal or a magazine issue")
	public static int deleteArticle(
			@Option( names = {"-t", "-title"},required=true, description = "The title of the article") String title, 
			@Option( names = {"-i", "-issn"},required=true, description = "The ISSN of the publication") String issn, 
			@Option( names = {"-d", "-date"},required=true, description = "The publication date of the article") String publicationDate) {
		
		
		title = EditingPublishing.enclose(title);
		issn = EditingPublishing.enclose(issn);
		publicationDate = EditingPublishing.enclose(publicationDate);
		
		String template = "DELETE FROM %s WHERE PublicationID=%s AND IssueDate=%s AND ArticleTitle=%s";
		String deleteRelationship = String.format(template,"WritesArticle",issn,publicationDate,title);
		String deleteArticle = String.format(template,"Article",issn,publicationDate,title);
		int result;
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			result = db.executeUpdate(deleteRelationship);
			System.out.println("Number of rows deleted in the WritesArticle Table: "+result);
			result = db.executeUpdate(deleteArticle);
			System.out.println("Number of rows deleted in the Article Table: "+result);
			db.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Command(name = "addChapter", description = "Add chapter to a book edition")
	public static int addChapter(
			@Option( names = {"-i", "-isbn"},required=true, description = "The ISBN of the book edition") String isbn, 
			@Option( names = {"-t", "-title"},required=true, description = "The title of the chapter") String title, 
			@Option( names = {"-c", "-chapter"},required=true, description = "The number of the chapter") int chapterNumber) {
		
		isbn = EditingPublishing.enclose(isbn);
		title = EditingPublishing.enclose(title);
		
		String template = "INSERT INTO %s VALUES(%s);";
		String query = String.format(template, "Chapter",String.join(",",isbn,Integer.toString(chapterNumber),title));
		int result;
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			result = db.executeUpdate(query);
			System.out.println("Number of rows changes in the Chapter Table: "+result);
			db.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return 0;
	}

	@Command(name = "deleteChapter", description = "Delete chapter from a book edition")
	public static int deleteChapter(
			@Option( names = {"-i", "-isbn"},required=true, description = "The ISBN of the book edition") String isbn, 
			@Option( names = {"-c", "-chapter"},required=true, description = "The number of the chapter") int chapterNumber) {
		
		
		isbn = EditingPublishing.enclose(isbn);
		
		String query = "DELETE FROM Chapter WHERE ISBN="+isbn+" AND ChapterNumber="+chapterNumber;
		int result;
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			result = db.executeUpdate(query);
			System.out.println("Number of rows changes in the Chapter Table: "+result);
			db.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Command(name = "addSection", description = "Add a section to a chapter of a book edition")
	public static int addSection(
			@Option( names = {"-i", "-isbn"},required=true, description = "The ISBN number of the edition") String isbn, 
			@Option( names = {"-t", "-title"},required=true, description = "The title of the section") String title, 
			@Option( names = {"-w", "-text"},required=true, description = "The text in the section") String sectionText, 
			@Option( names = {"-s", "-snumber"},required=true, description = "The section number") int sectionNumber, 
			@Option( names = {"-c", "-cnumber"},required=true, description = "The chapter number") int chapterNumber) {
		
		isbn = EditingPublishing.enclose(isbn);
		title = EditingPublishing.enclose(title);
		sectionText = EditingPublishing.enclose(sectionText);
		
		String template = "INSERT INTO %s VALUES(%s);";
		String query = String.format(template, "Sections",String.join(",",isbn,Integer.toString(chapterNumber),Integer.toString(sectionNumber),title,sectionText));
		int result;
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			result = db.executeUpdate(query);
			System.out.println("Number of rows changes in the Sections Table: "+result);
			db.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	@Command(name = "deleteSection", description = "Delete a section from a chapter of a book edition")
	public static int deleteSection(
			@Option( names = {"-i", "-isbn"},required=true, description = "The ISBN number of the edition")String isbn, 
			@Option( names = {"-s", "-snumber"},required=true, description = "The section number") int sectionNumber, 
			@Option( names = {"-c", "-cnumber"},required=true, description = "The chapter number") int chapterNumber) {
		
		isbn = EditingPublishing.enclose(isbn);
		
		String query = "DELETE FROM Sections WHERE ISBN="+isbn+" AND ChapterNumber="+chapterNumber+" AND SectionNumber="+sectionNumber;
		int result;
		
		try {
			WolfPubDb db = new WolfPubDb();
			db.createStatement();
			result = db.executeUpdate(query);
			System.out.println("Number of rows changes in the Section Table: "+result);
			db.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	

	
	

}
