package com.project.CounterService;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
//(collection = "counters")
public class Counter {
	
	@Id
	private String ids;
	private int seq  = 0;
	
	public int getSeq() {
		
		return this.seq;
	}

	public void setSeq(int num) {
		this.seq = num;
	}
	
	public String getIds() {
		return this.ids;
	}
	public void setIds(String id) {
		this.ids = id;
	}
	
}
