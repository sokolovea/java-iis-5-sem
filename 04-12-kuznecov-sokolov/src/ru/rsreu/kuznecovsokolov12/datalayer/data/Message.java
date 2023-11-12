package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class Message extends AbstractEntity {

	private String data;
	private User author;
	private Timestamp time;
	
	public Message() {
	}
	
	public Message(int id) {
		super(id);
	}

	public Message(int id, String data, User author, Timestamp time) {
		this(id);
		this.setData(data);
		this.setAuthor(author);
		this.setTime(time);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		return this.getId() == other.getId();
	}

	@Override
	public int hashCode() {
		return this.getId();
	}

}
