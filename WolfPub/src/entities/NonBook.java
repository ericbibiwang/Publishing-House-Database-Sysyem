package entities;

public class NonBook extends Publication {
	public NonBook(String id, String title, String type, String period) {
		super(id, title, type);
		this.period = period;
	}

	protected String period;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
}
