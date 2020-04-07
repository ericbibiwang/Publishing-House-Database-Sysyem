package entities;

public class Order {
	private final String tableName = new String("OrderTable");
	private String OrderID;
	private java.sql.Date Date;
	
	private Distributor orderer;

	public Order(Distributor orderer) {
		super();
		this.orderer = orderer;
	}

	public Order(String orderID, java.sql.Date date,
			Distributor orderer) {
		super();
		OrderID = orderID;
		Date = date;
		this.orderer = orderer;
	}

	public String getTableName() {
		return tableName;
	}

	public String getOrderID() {
		return OrderID;
	}

	public void setOrderID(String orderID) {
		OrderID = orderID;
	}

	public java.sql.Date getDate() {
		return Date;
	}

	public void setDate(java.sql.Date date) {
		Date = date;
	}

	public Distributor getOrderer() {
		return orderer;
	}

	public void setOrderer(Distributor orderer) {
		this.orderer = orderer;
	}
}
