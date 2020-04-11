package tasks;

import java.util.Map;
import java.util.Vector;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import wolfpub.WolfPub;

import java.sql.*;

@Command(name = "distributor", 
		 description = "Manage distributors and their orders"
		 )
public class Distribution implements Runnable {
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
		try {
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
	
	@Command(name = "update", description = "Update a distributor's information")
	public static int updateDistributorInfo(@Option( names = {"-n", "-name"}, description = "Distributor name") 	String name, 
											@Option( names = {"-t", "-type"}, description = "Distributor type") 	String type,
											@Option( names = {"-p", "-phone"}, description = "Phone number") 		String phoneNumber,
											@Option( names = {"-c", "-contact"}, description = "Contact name") 		String contactPerson,
											@Option( names = {"-b", "-balance"}, description = "Starting balance") 	Double balance,
											@Option( names = {"-s", "-state"}, description = "State") 				String state,
											@Parameters( paramLabel = "Street Address" ) String addr,
											@Parameters( paramLabel = "City" ) String city,
											@Parameters( paramLabel = "Zip" ) String zip) {

		System.out.println("TODO: Attempt to update distributor " + addr + " " + city + " " + state + " with");
		
		if (zip != null) System.out.println("zip: " + zip);
		if (name != null) System.out.println("name: " + name);
		if (type != null) System.out.println("type: " + name);
		if (phoneNumber != null) System.out.println("phone#: " + name);
		if (contactPerson != null) System.out.println("contact: " + name);
		if (balance != null) System.out.println("balance: " + name);
		
		return 0;
	}
	
	@Command(name = "delete", description = "Remove a distributor from the database.")
	public static int deleteDistributor(@Parameters( paramLabel = "Street Address" ) String addr,
										@Parameters( paramLabel = "City" ) String city,
										@Parameters( paramLabel = "Zip" ) String zip) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM ").append(distTableName).append(" WHERE ");
			sb.append("StreetAddr='" + addr + "' AND ");
			sb.append("City='" + city + "' AND ");
			sb.append("Zip='" + zip + "';");

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
	
	
	@Command(name = "order", description = "Enter and update orders")
	public static int inputOrder(@Option( names = {"-o", "-order"}, description = "Existing order number to update") 		String orderID,
								 @Option( names = {"-b", "-isbn"}, description = "Include books with their counts") 		Map<String, Integer> books,
								 @Option( names = {"-p", "-issn"}, description = "Include periodicals with their counts") 	Map<String, Integer> periodicals,
								 @Option( names = {"-d", "-date"}, description = "Date of order") 							Date orderDate,
								 @Option( names = {"-s", "-shipcost"}, description = "Shipping costs") 						Double shippingCost,
								 @Parameters( paramLabel = "Street Address" ) String addr,
								 @Parameters( paramLabel = "City" ) String city,
								 @Parameters( paramLabel = "Zip" ) String zip) {
		/* If an order has been delivered (can we/do we need to check for this?) or paid for, do not allow changes to the order */ 
		/* If an order ID does not exist, create it. */
		
		/* If an order ID exists, update it */
		
		/* If an order ID exists and the given publicationId is already in it,
		 * then update the number of copies in OrderContains.. table with ON DUPLICATE KEY UPDATE */
		
		/* If an order ID exists and the given publicationId is already in it, AND the number of copies is 0,
		 * then remove it from the order */
		
		return 0;
	}
	
	@Command(name = "bill", description = "Generate a bill based on an order ID")
	public static int billOrder(@Parameters( paramLabel = "Order ID" ) String orderID) {
		/* Generate a bill for the distributor based on Order ID*/

		System.out.println("TODO: Generate a bill for order id " + orderID);
		return 0;
	}
	
	@Command(name = "pay", description = "Record a payment from or credit to a distributor.")
	public static int recordPayment(@Option( names = {"-b", "-balance"}, description = "Starting balance") 	Double newBalance,
									@Option( names = {"-s", "-state"}, description = "State") 				String state,
									@Parameters( paramLabel = "Street Address" ) String addr,
									@Parameters( paramLabel = "City" ) String city,
									@Parameters( paramLabel = "Zip" ) String zip) {
		
		return 0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
