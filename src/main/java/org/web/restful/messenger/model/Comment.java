package org.web.restful.messenger.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {

	private long		id;
	private String	content;
	private String	author;
	private Date		created;

	public Comment() {

	}

	public Comment(long id, String content, String author, Date created) {
		this.id = id;
		this.content = content;
		this.author = author;
		this.created = created;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Comment: id=");
		sb.append(this.getId());
		sb.append(", content=");
		sb.append(this.getContent());
		sb.append(", author=");
		sb.append(this.getAuthor());
		sb.append(", created=");
		sb.append(this.getCreated());

		return sb.toString();
	}
}
