package entities;

import java.util.HashSet;

public class Publication {
	/* Attributes:
	 *  PublicationID
	 *  PublicationTitle
	 *  PublicationType
	 */
	protected String id;
	protected String title;
	protected String type;
	
	/* Table name PublicationHas
	 * CDS: Not sure if needed
	private HashSet<Topic> hasTopic; */

	public Publication(String id, String title, String type) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
