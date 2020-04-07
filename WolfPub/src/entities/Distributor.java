package entities;

public class Distributor {
	/* Attributes:
	 * 	StreetAddr
	 * 	City
	 * 	Zip
	 * 	State
	 * 	Name
	 * 	Type
	 * 	ContactPerson
	 * 	PhoneNumber
	 * 	Balance
	 */
	private String streetAddr;
	private String city;
	private String zip;
	private String state;
	private String name;
	private String type;
	private String contact;
	private String number;
	private Double balance;
	
	public Distributor(String streetAddr, String city, String zip, String state,
			String name, String type, String contact, String number,
			Double balance) {
		super();
		this.streetAddr = streetAddr;
		this.city = city;
		this.zip = zip;
		this.state = state;
		this.name = name;
		this.type = type;
		this.contact = contact;
		this.number = number;
		this.balance = balance;
	}

	public String getStreetAddr() {
		return streetAddr;
	}

	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
