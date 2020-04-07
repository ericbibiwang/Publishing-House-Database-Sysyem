package entities;

public class Chapter {
	/* Attributes:
	 * 	ChapterNumber
	 * 	ChapterTitle
	 */
	Integer number;
	String title;

	private Edition belongsToEdition;

	public Chapter(Integer number, String title, Edition belongsToEdition) {
		super();
		this.number = number;
		this.title = title;
		this.belongsToEdition = belongsToEdition;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Edition getBelongsToEdition() {
		return belongsToEdition;
	}

	public void setBelongsToEdition(Edition belongsToEdition) {
		this.belongsToEdition = belongsToEdition;
	}
	
}
