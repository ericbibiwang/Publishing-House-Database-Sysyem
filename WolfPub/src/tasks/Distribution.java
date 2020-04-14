package tasks;

import java.util.Map;
import java.util.Vector;

import de.vandermeer.asciitable.AsciiTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import wolfpub.WolfPub;
import wolfpub.WolfPubDb;

import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

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
			WolfPubDb db = WolfPub.getDb();
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
			WolfPubDb db = WolfPub.getDb();
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
			WolfPubDb db = WolfPub.getDb();
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
	public static int inputOrder(@Option( names = {"-o", "-order"}, description = "Existing order number to update") String orderID,
								 @Option( names = {"-b", "-isbn"}, split=",", description = "Include books with their counts") Map<String, Integer> books,
								 @Option( names = {"-p", "-issn"}, split=",", description = "Include periodicals with their counts. <ISSN>|<Issue Date>=<#copies>") Map<String, Integer> periodicals,
								 @Option( names = {"-d", "-date"}, description = "Date of order") Date orderDate,
								 @Option( names = {"-s", "-shipcost"}, description = "Shipping costs") Double shippingCost,
								 @Option( names = {"-addr"}, description = "Distributor's Street Address" ) String addr,
								 @Option( names = {"-city"}, description = "Distributor's City" ) String city,
								 @Option( names = {"-zip"}, description = "Distributor's Zip" ) String zip) {
		StringBuilder sb = new StringBuilder();
		int retval = 0;
		
		/* If one of the distributor's key values are non-null but another is null, drop the command */
		if ((addr != null || city != null || zip != null) && 
			(addr == null || city == null || zip == null)) {
			System.out.println("All three values for a distributor's address must be provided: -addr, -city, and -zip");
			return 1;
		}
		
		/* No action requested */
		if (books == null && periodicals == null) {
			System.out.println("Specify a book edition with -b(-isbn) or a periodical issue with -p(-issn) with format <id>=<#copies>");
			return 1;
		}
		
		/* If neither Distributor address or order ID are given, we have nothing to update or insert */
		/* Check for null address is enough to check all three address parts given first check in this method */ 
		if (orderID == null && addr == null) {
			System.out.println("Either a distributor's address (-addr, -city, -zip) or an order id(-o) must be specified.");
			return 1;
		}
		
		WolfPubDb db = WolfPub.getDb();
		
		/* If distributor address and order id are both provided, check that the order is indeed the distributor's */
		/* Check for non-null address is enough to check all three address parts given first check in this method */
		if (addr != null && orderID != null)
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
		
		sb.setLength(0);
		sb.append("SELECT EXISTS(SELECT 1 FROM OrderTable WHERE OrderID=" + orderID + ");");
		try {
			db.createStatement();
			db.executeQuery(sb.toString());
			
			ResultSet rs = db.getRs();
		
			/* If an order ID exists, update it */
			/* If an order ID does not exist, create a new order with an autoincremented ID. */
			if (rs.next() == false || orderID == null) {
				/* No matching orderID found, let WolfPubDB autoincrement the ID */
				if (addr == null) {
					System.out.println("No order with id " + orderID + " found. Provide a distributor address to create a new order.");
					return 1;
				}
				
				/* Create new Order */
				sb.setLength(0);
				sb.append("INSERT INTO OrderTable(StreetAddr,City,Zip,Date,ShippingCost) VALUES (");
				sb.append("'" + addr + "','" + city + "','" + zip + "','");
				if (orderDate != null) {
					sb.append(new SimpleDateFormat("yyyy-MM-dd").format(orderDate));
				} else {
					sb.append(new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()))).append("','");
				}
				sb.append(shippingCost == null ? "0" : shippingCost.toString()).append("');");
				
				System.out.println(sb.toString());
				//db.createStatement();
				if (0 == db.executeUpdate(sb.toString()))
				{
					System.out.println("No rows updated in order table");
					return 1;
				}
				
				/* Get new Order ID */
				//db.createStatement();
				db.executeQuery("SELECT LAST_INSERT_ID();");
				rs = db.getRs();
				if (rs.next()) {
					orderID = Integer.toString(rs.getInt(1));
				}
				else {
					System.out.println("Could not get autoincremented order ID");
					return 1;
				}
			}
			

			/* Set order date and shipping costs */
			sb.setLength(0);
			sb.append("UPDATE OrderTable SET Date = ");
			if (orderDate == null) {
				sb.append(java.sql.Date.from(Instant.now()).toString());
			} else {
				sb.append(orderDate.toString());
			}
			sb.append(", ShippingCost = ").append(shippingCost == null ? "0" : shippingCost.toString()).append(";");
			
			if (books != null) {
				/* Update Order contents */
				db.prepareStatement("INSERT INTO OrderContainsEdition (OrderID, ISBN, NumOfCopies, Price) " + 
						"SELECT ? , ? , ? , EditionPrice FROM Edition NATURAL JOIN OrderTable WHERE ISBN= ? " + 
						"ON DUPLICATE KEY UPDATE NumOfCopies = ?;");
				for (Map.Entry<String, Integer> entry : books.entrySet()) {
					db.setInt(1, Integer.valueOf(orderID));
					db.setString(2, entry.getKey());
					db.setInt(3, Integer.valueOf(entry.getValue()));
					db.setString(4, entry.getKey());
					db.setInt(5, Integer.valueOf(entry.getValue()));
					db.addBatch();
					System.out.printf("Ordering %d copies of %s\n", Integer.valueOf(entry.getValue()), entry.getKey());
				};
			}
			
			if (periodicals != null) {
				db.prepareStatement("INSERT INTO OrderContainsIssue (OrderID, PublicationID, IssueDate, NumOfCopies, Price) " +
									"SELECT ? , ? , ? , ?, Price FROM Issue NATURAL JOIN OrderTable " +
									"WHERE PublicationID= ? ON DUPLICATE KEY UPDATE NumOfCopies = ?;");
				for (Map.Entry<String, Integer> entry : periodicals.entrySet()) {
					if (!entry.getKey().contains("|")) {
						throw new Exception("Identify periodicals with id, issue date, and number of copies: 12345678|2020-01-01=5");
					}
					db.setInt(1, Integer.valueOf(orderID));
					db.setString(2, entry.getKey().split("\\|")[0]);
					db.setDate(3, Date.valueOf(entry.getKey().split("\\|")[1]));
					db.setInt(4, Integer.valueOf(entry.getValue()));
					db.setString(5, entry.getKey().split("\\|")[0]);
					db.setInt(6, Integer.valueOf(entry.getValue()));
					db.addBatch();
					System.out.printf("Ordering %d copies of %s from %s\n", Integer.valueOf(entry.getValue()), entry.getKey().split("\\|")[0],
							entry.getKey().split("\\|")[1]);
				};
			}
			db.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			retval = 1;
		} catch (Exception e) {
			e.printStackTrace();
			retval = 1;
		} finally {
			/* TODO */
		}
		
		return retval;
	}
	
	private static String billHeader = new String("WolfPub Publishing House\n" + "5555 Publishing Lane\n" + "Raleigh, NC 27602");
	
	/**
	 * Command "wolfpub distributor bill"
	 * 
	 * @param orderID
	 * @return
	 */
	@Command(name = "bill", description = "Generate a bill based on an order ID")
	public static int billOrder(@Parameters( paramLabel = "Order ID" ) String orderID) {
		/* Generate a bill for the distributor based on Order ID*/

		try {
			WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeQuery("SELECT EXISTS(SELECT 1 FROM OrderTable WHERE OrderID=" + orderID + ");");
			ResultSet rs = db.getRs();
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			Double subtotal = 0.0;
			Double total = 0.0;
			
			if (rs.next()) {
				AsciiTable bill = new AsciiTable();
				
				/* Build the bill header */
				bill.addRule();
				bill.addRow(null, null, null, null, null, billHeader);
				bill.addRow(null, null, null, null, null, "Bill for order " + orderID + " generated on " + new SimpleDateFormat().format(Date.from(Instant.now())));
				bill.addRule();
				/* Build the book section */
				db.executeQuery("SELECT PublicationTitle, EditionNumber, ISBN, NumOfCopies, o.Price "
							  + "FROM OrderContainsEdition o NATURAL JOIN Edition NATURAL JOIN Publication "
							  + "WHERE OrderID=" + orderID + ";");
				rs = db.getRs();

				if (rs.first()) {
					rs.beforeFirst();
				
					bill.addRow("Books: Title", "Edition", "ISBN", "Copies", "Unit Price", "Price");
					while (rs.next()) {
						Double price = rs.getInt("NumOfCopies") * rs.getDouble("o.Price");
						bill.addRow(rs.getString("PublicationTitle"),
								    rs.getString("EditionNumber"),
								    rs.getString("ISBN"),
								    rs.getString("NumOfCopies"),
								    nf.format(Double.valueOf(rs.getString("o.Price"))),
								    nf.format(price));
						subtotal += price;
					}
					bill.addRow("","","","","","");
					bill.addRow("","","","","", "Book subtotal: " + nf.format(subtotal));
	
					total += subtotal;
					subtotal = 0.0;
					bill.addRule();
				}
				
				/* Build the periodical section */
				db.executeQuery("SELECT PublicationTitle, IssueDate, PublicationID, NumOfCopies, o.Price " +
								"FROM OrderContainsIssue o NATURAL JOIN Issue NATURAL JOIN Publication " + 
								"WHERE OrderID=" + orderID + ";");
				rs = db.getRs();

				if (rs.first()) {
					bill.addRow("", "Non-books: Title", "Issue Date", "ISSN", "Copies", "Total Price");
					rs.beforeFirst();
				
					while (rs.next()) {
						Double price = rs.getInt("NumOfCopies") * rs.getDouble("o.Price");
						bill.addRow("", rs.getString("PublicationTitle"),
									rs.getString("IssueDate"),
									rs.getString("numOfCopies"),
									nf.format(Double.valueOf(rs.getString("o.Price"))),
									nf.format(price));
						subtotal += price;
					}
					bill.addRow("","","","","","");
					bill.addRow("","","","","", "Non-book subtotal: " + nf.format(subtotal));
					
					total += subtotal;
				}
				/* Print the total */
				bill.addRule();
				bill.addRow("","","","","", "Bill total: " + nf.format(total));
				bill.addRule();
				db.executeUpdate("UPDATE Distributor SET Balance=Balance+" + total + " WHERE(StreetAddr,City,Zip) IN " +
								 "(SELECT StreetAddr,City,Zip FROM OrderTable WHERE OrderID=" + orderID + ");");
				System.out.println(bill.render());
			} else {
				System.out.printf("No Order %s found", orderID);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
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
	public static int recordPayment(@Option( names = {"-p", "-paid"}, description = "Amount paid") Double payAmount,
									@Parameters( paramLabel = "Street Address" ) String addr,
									@Parameters( paramLabel = "City" ) String city,
									@Parameters( paramLabel = "Zip" ) String zip) {
		try {
			WolfPubDb db = WolfPub.getDb();
			db.createStatement();
			db.executeUpdate("UPDATE Distributor SET Balance=Balance-" + payAmount.toString() + 
							" WHERE StreetAddr='" + addr + "' AND City='" + city + "' AND Zip='" + zip + "';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}
}


