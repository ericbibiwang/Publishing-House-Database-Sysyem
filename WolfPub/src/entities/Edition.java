package entities;

import java.sql.Date;

public class Edition extends Book {
	private String isbn;
	/* attribute EditionNumber */
	private Integer edition;
	/* attribute PublicationDate */
	private java.sql.Date date;
	/* attribute EditionPrice */
	private Double price;
	
	public Edition(String id, String title, String type, String isbn,
			Integer edition, Date date, Double price) {
		super(id, title, type);
		this.isbn = isbn;
		this.edition = edition;
		this.date = date;
		this.price = price;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
