package entities;

public class Article {
	private String title;
	private String text;

	private Issue parentIssue;

	public Article(String title, String text, Issue parentIssue) {
		super();
		this.title = title;
		this.text = text;
		this.parentIssue = parentIssue;
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

	public Issue getParentIssue() {
		return parentIssue;
	}

	public void setParentIssue(Issue parentIssue) {
		this.parentIssue = parentIssue;
	}
	
}
