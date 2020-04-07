package entities;

public class Topic {
	public Topic(String name) {
		super();
		this.name = name;
	}

	/* Attribute TopicName */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
