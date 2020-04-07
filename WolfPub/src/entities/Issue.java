package entities;

import java.sql.Date;

public class Issue extends NonBook {
	public Issue(String id, String title, String type, String period, Date date,
			String title2, Double price) {
		super(id, title, type, period);
		this.date = date;
		title = title2;
		this.price = price;
	}
	/* Attributes:
	 * 	IssueDate
	 * 	IssueTitle
	 * 	Price
	 */
	private java.sql.Date date;
	private String title;
	private Double price;
	
	public java.sql.Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
