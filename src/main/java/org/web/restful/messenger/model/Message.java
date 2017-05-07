package org.web.restful.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Message {

	private long		id;
	private String	message;
	private Date		created;
	private String	author;
	private Map<Long, Comment>	comments	= new HashMap<>();
	private List<Link>					links			= new ArrayList<>();

	public Message() {

	}

	public Message(long id, String message, String author, Date created) {
		this.id = id;
		this.message = message;
		this.author = author;
		this.created = created;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@XmlTransient
	public Map<Long, Comment> getComments() {
		return comments;
	}

	public void setComments(Map<Long, Comment> comments) {
		this.comments = comments;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(String uri, String rel) {
		Link link = new Link(uri, rel);
		this.links.add(link);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Message: id=");
		sb.append(this.getId());
		sb.append(", message=");
		sb.append(this.getMessage());
		sb.append(", author=");
		sb.append(this.getAuthor());
		sb.append(", created=");
		sb.append(this.getCreated());
		sb.append(", comments=");
		sb.append(this.getComments().values());
		sb.append(", links=");
		sb.append(this.getLinks());

		return sb.toString();
	}

}
