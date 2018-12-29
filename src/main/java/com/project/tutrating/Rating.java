package com.project.tutrating;


import java.util.LinkedList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

@Document
public class Rating {

	
	private int userID;
	private int tutID;
	private int rating;
	
	public int getUserID() {
		return this.userID;
	}
	
	public void setUserID(int userid) {
		this.userID = userid;
	}
	
	public int getTutID() {
		return this.tutID;
	}
	
	public void setTutID(int tutid) {
		this.tutID = tutid;
	}
	public int getRating() {
		return this.rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
}
