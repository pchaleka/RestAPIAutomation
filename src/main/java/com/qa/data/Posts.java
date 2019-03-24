package com.qa.data;

public class Posts {

	String id;
	String userId;
	String title;
	String body;
	
	
	public Posts() {

	}

	public Posts(String id, String userId, String title, String body ) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.body = body;	
		
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
	
}
