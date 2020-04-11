package tasks;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class EditingPublishing {
	
	
	public static int enterNewBook(String title, String ssn, String isbn) {
		
		return 0;
	}
	
	
	public static int enterNewJournalMagazine(String title, String issn, String[] topics) {
		
		return 0;
	}
	
	
	public static int updateBook(String title, String isbn, String ssn) {
		
		return 0;
	}
	
	
	public static int updateJournalMagazine(String title, String issn, String topics) {
		
		return 0;
	}
	
	
	public static int assignEditors(String ssn, String id) {
		
		return 0;
	}
	
	public static int addArticles(String title, String text, String issn, String publicationDate, String authorsSSN, String topics) {
		
		return 0;
		
	}
	
	
	public static int deleteArticles(String title, String issn, String publicationDate) {
		
		return 0;
	}
	
	public static int addChapter(String isbn, String title, int chapterNumber) {
		return 0;
	}

	public static int deleteChapter(String isbn, int chapterNumber) {
		
		return 0;
	}
	
	public static int addSection(String isbn, String title, String sectionText, int sectionNumber, int chapterNumber) {
		
		return 0;
	}

	
	public static int deleteSection(String isbn, int sectionNumber, int chapterNumber) {
		
		return 0;
	}
	
	

	
	

}
