package entities;

public class Employee {
	/* Attributes
	 * 	SSN
	 * 	Name
	 * 	ContractPay
	 * 	PayPeriod
	 */
	protected String ssn;
	protected String name;
	protected Double pay;
	protected String period;
	
	public Employee(String ssn, String name, Double pay, String period) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.pay = pay;
		this.period = period;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
}
