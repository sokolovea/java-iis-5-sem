package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class Message extends AbstractEntity {

	private int id;
	private String data;
	private int author;
	private Timestamp time;
	
	public Message() {
	}

	public Message(int id, String data, int author, Timestamp time) {
		this.setId(id);
		this.setData(data);
		this.setAuthor(author);
		this.setTime(time);
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public int getAuthor() {
		return author;
	}


	public void setAuthor(int author) {
		this.author = author;
	}


	public Timestamp getTime() {
		return time;
	}


	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	

}
