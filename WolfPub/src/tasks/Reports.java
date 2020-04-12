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
	
	@Command(name = "distri_mon", description = "Generate monthly number and total price of each publication bought per distributor")
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
	
	@Command(name = "expen", description = "Generate monthly total expenses")
	public static void totalExpense(@Parameters( paramLabel = "year" ) String year,
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
			sb.append("SELECT YEAR(Date) AS 'Year', MONTH(Date) AS 'Month', SUM(Cost) AS 'Monthly Cost' FROM (SELECT Date AS Date, ShippingCost AS Cost FROM Distributor NATURAL JOIN OrderTable UNION ALL SELECT DatePickedUp AS Date, Amount AS Cost FROM Payment) AS New WHERE YEAR(Date)=").append(year).append(" AND MONTH(Date)=").append(month).append(" GROUP BY YEAR(Date), MONTH(Date);");
			
			System.out.println("Try to process " + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
	@Command(name = "distri_num", description = "Generate monthly total current number of distributors")
	public static void numOfDistributors(@Parameters( paramLabel = "year" ) String year,
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
			sb.append("SELECT YEAR(Date) as Year, MONTH(Date) AS Month, COUNT(DISTINCT StreetAddr, City, Zip) AS 'Total Monthly Distributors' FROM Distributor NATURAL JOIN OrderTable WHERE YEAR(Date)=").append(year).append(" AND MONTH(Date)=").append(month).append(" GROUP BY YEAR(Date), MONTH(Date);");
			
			System.out.println("Try to process " + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	
	@Command(name = "rev_city", description = "Generate total revenue per city since inception")
	public static void revenueSummaryCity() {
		try{			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT City, FORMAT(Sum(Price*NumOfCopies),2) AS 'Total Revenue' FROM ( SELECT OrderID, NumOfCopies, Price FROM OrderContainsEdition UNION ALL SELECT OrderID, NumOfCopies, Price From OrderContainsIssue) AS A NATURAL JOIN OrderTable NATURAL JOIN Distributor GROUP BY City;");
			
			System.out.println("Try to process " + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}
	
	@Command(name = "rev_distri", description = "Generate total revenue per distributor since inception")
	public static void revenueSummaryDistributor() {
		try{			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT Name, FORMAT(Sum(Price*NumOfCopies),2) AS 'Total Revenue' FROM ( SELECT OrderID, NumOfCopies, Price FROM OrderContainsEdition UNION ALL SELECT OrderID, NumOfCopies, Price From OrderContainsIssue) AS A NATURAL JOIN OrderTable NATURAL JOIN Distributor GROUP BY Name;");
			
			System.out.println("Try to process " + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}

	@Command(name = "rev_loc", description = "Generate total revenue per location since inception")
	public static void revenueSummaryLocation() {
		try{			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT CONCAT(StreetAddr, ', ', City, ', ', Zip) AS Location, FORMAT(Sum(Price*NumOfCopies),2) AS 'Total Revenue' FROM ( SELECT OrderID, NumOfCopies, Price FROM OrderContainsEdition UNION ALL SELECT OrderID, NumOfCopies, Price From OrderContainsIssue) AS A NATURAL JOIN OrderTable NATURAL JOIN Distributor GROUP BY StreetAddr, City, Zip;");
			
			System.out.println("Try to process " + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}
	
	@Command(name = "pay_time", description = "Generate total payments to editors and authors per time period")
	public static void paymentSummaryTime() {
		try{			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT Name, SSN, YEAR(DatePickedUp) AS Year, MONTH(DatePickedUp) AS Month, SUM(Amount) AS 'Total Amount' FROM Employee NATURAL JOIN Payment GROUP BY Name, SSN, YEAR(DatePickedUp), MONTH(DatePickedUp);");
			
			System.out.println("Try to process " + sb.toString());
			
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery(sb.toString());	
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}
	
	@Command(name = "pay_worktype", description = "Generate total payments to editors and authors per work type")
	public static void paymentSummaryWorktype() {
		try{
			wolfpub.WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			sb1.append("select SUM(ContractPay) as 'Editor Payment' from Employee natural join Editor;");
			sb2.append("select SUM(ContractPay) as 'Author Payment' from Employee natural join Author;");
			
			System.out.println("Try to process " + sb1.toString());
			db.executeQuery(sb1.toString());
			
			System.out.println("\nTry to process " + sb2.toString());
			db.executeQuery(sb2.toString());
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

	}
	
	

}
