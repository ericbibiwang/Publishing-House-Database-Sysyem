package tasks;

import java.sql.SQLException;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
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
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	
	@Command(name = "updateBook", description = "Update information about an existing book")
	public static int updateBook(
			String title, 
			String isbn, 
			String ssn) {
		
		return 0;
	}
	
	
	@Command(name = "updateNonBook", description = "Update information about an existing journal or magazine")
	public static int updateJournalMagazine(
			String title, 
			String issn, 
			String topics) {
		
		return 0;
	}
	
	
	@Command(name = "assignEditor", description = "Assign editors to a publication")
	public static int assignEditors(
			String ssn, 
			String id) {
		
		return 0;
	}
	
	@Command(name = "addArticle", description = "Add an article to a magazine or a journal issue")
	public static int addArticle(
			String title, 
			String text, 
			String issn, 
			String publicationDate, 
			String authorsSSN, 
			String topics) {
		
		return 0;
		
	}
	
	@Command(name = "deleteArticle", description = "Delete article from a journal or a magazine issue")
	public static int deleteArticle(
			String title, 
			String issn, 
			String publicationDate) {
		
		return 0;
	}
	
	@Command(name = "addChapter", description = "Add chapter to a book edition")
	public static int addChapter(
			String isbn, 
			String title, 
			int chapterNumber) {
		
		
		return 0;
	}

	@Command(name = "deleteChapter", description = "Delete chapter from a book edition")
	public static int deleteChapter(
			String isbn, 
			int chapterNumber) {
		
		return 0;
	}
	
	@Command(name = "addSection", description = "Add a section to a chapter of a book edition")
	public static int addSection(
			String isbn, 
			String title, 
			String sectionText, 
			int sectionNumber, 
			int chapterNumber) {
		
		return 0;
	}

	@Command(name = "deleteSection", description = "Delete a section from a chapter of a book edition")
	public static int deleteSection(String isbn, int sectionNumber, int chapterNumber) {
		
		return 0;
	}
	
	

	
	

}
