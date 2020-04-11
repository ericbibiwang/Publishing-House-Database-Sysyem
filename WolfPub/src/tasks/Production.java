package tasks;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class Production {
	
	public static int enterBookEdition(String isbn, int editionNum, String pubDate) {
		
		
		return 0;
	}
	
	
	public static int updateBookEdition(String isbn, int editionNum, String pubDate) {
		
		
		return 0;
	}
	
	
	
	public static int enterNewIssue(String isbn, String issueDate, String title) {
		
		
		return 0;
	}
	
	public static int updateIssue(String isbn, String issueDate, String title) {
		
		
		return 0;
	}
	
	public static int deleteIssue(String issn) {
		
		
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
