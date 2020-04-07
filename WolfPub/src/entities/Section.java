package entities;

public class Section {

	public Section(Chapter belongsToChapter) {
		super();
		this.belongsToChapter = belongsToChapter;
		/* TODO Auto-generate section number based on highest section number in given chapter */
	}

	public Section(Integer number, Chapter belongsToChapter) {
		super();
		this.number = number;
		this.belongsToChapter = belongsToChapter;
	}
	
	public Section(String title, Chapter belongsToChapter) {
		super();
		this.title = title;
		this.belongsToChapter = belongsToChapter;
		/* TODO Auto-generate section number based on highest section number in given chapter */
	}

	public Section(Integer number, String title, String text,
			Chapter belongsToChapter) {
		super();
		this.number = number;
		this.title = title;
		this.text = text;
		this.belongsToChapter = belongsToChapter;
	}

	/* Attributes SectionNumber, SectionTitle, SectionText */
	private Integer number;
	private String title;
	private String text;

	private Chapter belongsToChapter;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Chapter getBelongsToChapter() {
		return belongsToChapter;
	}

	public void setBelongsToChapter(Chapter belongsToChapter) {
		this.belongsToChapter = belongsToChapter;
	}
}
