package tasks;

import java.sql.SQLException;
import java.util.Vector;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import wolfpub.WolfPub;

import java.sql.*;


@Command(name = "report", 
		description = "Generate reports"
		)

public class Reports {
	
	@Command(name = "distrisum", description = "Generate monthly number and total price of each publication bought per distributor")
	public static void generateDistributorSummary(@Parameters( paramLabel = "year" ) String year,
			  									  @Parameters( paramLabel = "month" ) String month) {
		try{
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			/* Add values for the required parameters */
			columns.add("year");
			values.add(year);
			columns.add("month");
			values.add(month);
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT YEAR(Date) AS Year, MONTH(Date) AS Month, StreetAddr, City, Zip, PublicationID, SUM(Price*NumOfCopies) AS 'Total Price' FROM ( SELECT OrderID, NumOfCopies, ISBN AS PublicationID, Price FROM OrderContainsEdition UNION ALL SELECT OrderID, NumOfCopies, PublicationID, Price From OrderContainsIssue) AS A NATURAL JOIN OrderTable NATURAL JOIN Distributor WHERE YEAR(Date)=").append(year).append(" AND MONTH(Date)=").append(month).append(" GROUP BY YEAR(Date), MONTH(Date), StreetAddr, City, Zip, PublicationID;");
			
			System.out.println("Try to process " + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}
	
	@Command(name = "rev", description = "Generate monthly total revenue of the publishing house")
	public static void totalRevenue(@Parameters( paramLabel = "year" ) String year,
									@Parameters( paramLabel = "month" ) String month) {
		try{
			Vector<String> columns = new Vector<String>();
			Vector<String> values = new Vector<String>();
			
			/* Add values for the required parameters */
			columns.add("year");
			values.add(year);
			columns.add("month");
			values.add(month);
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT YEAR(Date) as 'Year',MONTH(Date) as 'Month', Sum(Price*NumOfCopies) AS 'Total Revenue' FROM ( SELECT OrderID, NumOfCopies, Price FROM OrderContainsEdition UNION ALL SELECT OrderID, NumOfCopies, Price From OrderContainsIssue) AS A NATURAL JOIN OrderTable NATURAL JOIN Distributor WHERE YEAR(Date)=").append(year).append(" AND MONTH(Date)=").append(month).append(" GROUP BY YEAR(Date), MONTH(Date);");
			
			System.out.println("Try to process " + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
	public static void totalExpense(String startDate, String endDate) {
		
	}
	
	public static void numOfDistributors() {
		
	}
	
	public static void revenueSummary() {
		
	}
	

	public static void paymentSummary(String ssn, String startDate, String endDate, String workType) {
		
	}
	

	
	

}
