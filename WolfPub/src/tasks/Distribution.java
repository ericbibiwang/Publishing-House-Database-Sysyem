package tasks;

import java.util.Map;
import java.util.Vector;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ArgGroup;
import wolfpub.WolfPub;

import java.sql.*;

@Command(name = "distributor", 
		 description = "Manage distributors and their orders"
		 )
public class Distribution {
	private static String distTableName = "Distributor";
	
	/**
	 * Command "wolfpub distributor new"
	 * Create a new distributor with WolfPubDB
	 * 
	 * @param name
	 * @param type
	 * @param phoneNumber
	 * @param contactPerson
	 * @param balance
	 * @param zip
	 * @param addr
	 * @param city
	 * @param state
	 * @return
	 */
	@Command(name = "new", description = "Enter information for a new distributor")
	public static int enterNewDistributor(@Option( names = {"-n", "-name"}, required = true, description = "Distributor name") 		String name, 
										  @Option( names = {"-t", "-type"}, required = true, description = "Distributor type") 		String type,
										  @Option( names = {"-p", "-phone"}, required = true, description = "Phone number") 		String phoneNumber,
										  @Option( names = {"-c", "-contact"}, required = true, description = "Contact name") 		String contactPerson,
										  @Option( names = {"-b", "-balance"}, defaultValue = "0", description = "Starting balance")Double balance,
										  @Option( names = {"-s", "-state"}, required = true, description = "State") 				String state,
										  @Parameters( paramLabel = "Street Address" ) String addr,
										  @Parameters( paramLabel = "City" ) String city,
										  @Parameters( paramLabel = "Zip" ) String zip) {
		Vector<String> columns = new Vector<String>();
		Vector<String> values = new Vector<String>();
		
		/* Add values for the required parameters */
		columns.add("StreetAddr");
		values.add(addr);
		columns.add("City");
		values.add(city);
		columns.add("State");
		values.add(state);
		
		/* Append optional columns */
		if (zip != null) {
			columns.add("Zip");
			values.add(zip);
		}
		if (name != null) {
			columns.add("Name");
			values.add(name);
		}
		if (type != null) {
			columns.add("Type");
			values.add(type);
		}
		if (phoneNumber != null) {
			columns.add("PhoneNumber");
			values.add(phoneNumber);
		}
		if (contactPerson != null) {
			columns.add("ContactPerson");
			values.add(contactPerson);
		}
		if (balance != null) {
			columns.add("Balance");
			values.add(String.format("%.2f", balance));
		}

		/* Build the update string */
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(distTableName).append(" (");
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
	
	/**
	 * Command "wolfpub distributor update"
	 * 
	 * @param name
	 * @param type
	 * @param phoneNumber
	 * @param contactPerson
	 * @param balance
	 * @param state
	 * @param newAddr
	 * @param newCity
	 * @param newZip
	 * @param addr
	 * @param city
	 * @param zip
	 * @return
	 */
	@Command(name = "update", description = "Update a distributor's information")
	public static int updateDistributorInfo(@Option( names = {"-n", "-name"}, description = "Distributor name") 		String name, 
											@Option( names = {"-t", "-type"}, description = "Distributor type") 		String type,
											@Option( names = {"-p", "-phone"}, description = "Phone number") 			String phoneNumber,
											@Option( names = {"-c", "-contact"}, description = "Contact name") 			String contactPerson,
											@Option( names = {"-b", "-balance"}, description = "Starting balance") 		Double balance,
											@Option( names = {"-s", "-state"}, description = "State") 					String state,
											@Option( names = {"-new_street_addr"}, description = "New street address") 	String newAddr,
											@Option( names = {"-new_city"}, description = "New city") 					String newCity,
											@Option( names = {"-new_zip"}, description = "New zip") 					String newZip,
											@Parameters( paramLabel = "Street Address" ) String addr,
											@Parameters( paramLabel = "City" ) String city,
											@Parameters( paramLabel = "Zip" ) String zip) {
		Vector<String> columns = new Vector<String>();
		Vector<String> values = new Vector<String>();
		
		if (name == null && type == null && phoneNumber == null && contactPerson == null && balance == null && state == null &&
				newAddr == null && newCity == null && newZip == null) {
			System.out.println("Nothing to update");
			return 0;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(distTableName).append(" SET ");
		
		if (name != null) {
			sb.append(" Name=").append(String.format("'%s'", name)).append(",");
		}
		if (type != null) {
			sb.append(" Type=").append(String.format("'%s'", type)).append(",");
		}
		if (phoneNumber != null) {
			sb.append(" PhoneNumber=").append(String.format("'%s'", phoneNumber)).append(",");
		}
		if (contactPerson != null) {
			sb.append(" ContactPerson=").append(String.format("'%s'", contactPerson)).append(",");
		}
		if (balance != null) {
			sb.append(" Balance=").append(String.format("'%s'", balance)).append(",");
		}
		if (state != null) {
			sb.append(" State=").append(String.format("'%s'", state)).append(",");
		}
		if (newAddr != null) {
			sb.append(" StreetAddr=").append(String.format("'%s'", newAddr)).append(",");
		}
		if (newCity != null) {
			sb.append(" City=").append(String.format("'%s'", newCity)).append(",");
		}
		if (newZip != null) {
			sb.append(" Zip=").append(String.format("'%s'", newZip)).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("WHERE StreetAddr='" + addr + "' AND City='" + city + "' AND Zip='" + zip + "';");
		
		System.out.println("Try to process " + sb.toString());
		
		try {
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
	
	/**
	 * Command "wolfpub distributor delete"
	 * 
	 * @param addr
	 * @param city
	 * @param zip
	 * @return
	 */
	@Command(name = "delete", description = "Remove a distributor from the database.")
	public static int deleteDistributor(@Parameters( paramLabel = "Street Address" ) String addr,
										@Parameters( paramLabel = "City" ) String city,
										@Parameters( paramLabel = "Zip" ) String zip) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ").append(distTableName).append(" WHERE ");
		sb.append("StreetAddr='" + addr + "' AND ");
		sb.append("City='" + city + "' AND ");
		sb.append("Zip='" + zip + "';");

		System.out.println("Try to process " + sb.toString());
		try {
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
	
	/**
	 * Command "wolfpub distributor order"
	 * @param orderID
	 * @param books
	 * @param periodicals
	 * @param orderDate
	 * @param shippingCost
	 * @param addr
	 * @param city
	 * @param zip
	 * @return
	 */
	@Command(name = "order", description = "Enter and update orders")
	public static int inputOrder(@Option( names = {"-o", "-order"}, description = "Existing order number to update") 		String orderID,
								 @Option( names = {"-b", "-isbn"}, description = "Include books with their counts") 		Map<String, Integer> books,
								 @Option( names = {"-p", "-issn"}, description = "Include periodicals with their counts") 	Map<String, Integer> periodicals,
								 @Option( names = {"-d", "-date"}, description = "Date of order") 							Date orderDate,
								 @Option( names = {"-s", "-shipcost"}, description = "Shipping costs") 						Double shippingCost,
								 @Option( names = {"-addr"}, description = "Distributor's Street Address" ) 				String addr,
								 @Option( names = {"-city"}, description = "Distributor's City" ) 							String city,
								 @Option( names = {"-zip"}, description = "Distributor's Zip" ) 							String zip) {
		StringBuilder sb = new StringBuilder();
		/* If one of the distributor's key values are non-null but another is null, drop the command */
		if ((addr != null || city != null || zip != null) && 
			(addr == null || city == null || zip == null)) {
			System.out.println("All three values for a distributor's address must be provided: -addr, -city, and -zip");
			return 1;
		}
		
		if (books == null && periodicals == null) {
			System.out.println("Specify a book edition with -b(-isbn) or a periodical issue with -p(-issn) with format <id>=<#copies>");
			return 1;
		}
		
		wolfpub.WolfPubDb db = WolfPub.getDb();
		
		/* If distributor address and order id are both provided, check that the order is indeed the distributor's */
		if (addr != null && city != null && zip != null && orderID != null)
		{
			sb.append("SELECT * FROM OrderTable WHERE OrderID='" + orderID + "' AND StreetAddr='" + addr + 
					"' AND City='" + city + "' AND Zip='" + zip + "';");
			try {
				db.createStatement();
				if (0 == db.executeUpdate(sb.toString())) {
					System.out.printf("No order %s found for given distributor", orderID);
					return 1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 1;
			}
		}
		
		/* TODO If an order has been delivered (can we/do we need to check for this?) or paid for, do not allow changes to the order */ 
		
		sb.setLength(0);
		sb.append("SELECT EXISTS(SELECT 1 FROM OrderTable WHERE OrderID=" + orderID);
		try {
			db.createStatement();
			if (0 == db.executeQuery(sb.toString())) {
				System.out.printf("No order %s found for given distributor", orderID);
				return 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		/* If an order ID exists, update it */
		
		/* If an order ID does not exist, create a new autoincremented value. */
		
		/* If an order ID exists and the given publicationId is already in it,
		 * then update the number of copies in OrderContains.. table with ON DUPLICATE KEY UPDATE */
		
		/* If an order ID exists and the given publicationId is already in it, AND the number of copies is 0,
		 * then remove it from the order */
		
		return 0;
	}
	
	/**
	 * Command "wolfpub distributor bill"
	 * 
	 * @param orderID
	 * @return
	 */
	@Command(name = "bill", description = "Generate a bill based on an order ID")
	public static int billOrder(@Parameters( paramLabel = "Order ID" ) String orderID) {
		/* Generate a bill for the distributor based on Order ID*/

		System.out.println("TODO: Generate a bill for order id " + orderID);
		return 0;
	}
	
	/**
	 * Command "wolfpub distributor pay"
	 * 
	 * @param newBalance
	 * @param state
	 * @param addr
	 * @param city
	 * @param zip
	 * @return
	 */
	@Command(name = "pay", description = "Record a payment from or credit to a distributor.")
	public static int recordPayment(@Option( names = {"-b", "-balance"}, description = "Starting balance") 	Double newBalance,
									@Option( names = {"-s", "-state"}, description = "State") 				String state,
									@Parameters( paramLabel = "Street Address" ) String addr,
									@Parameters( paramLabel = "City" ) String city,
									@Parameters( paramLabel = "Zip" ) String zip) {
		
		return 0;
	}
}

