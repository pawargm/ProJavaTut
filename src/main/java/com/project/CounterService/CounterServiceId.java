package com.project.CounterService;

import static org.springframework.data.mongodb.core.query.Query.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.*;
import  com.project.CounterService.CounterRepository;;

@Service
public class CounterServiceId 
{

	@Autowired
	private CounterRepository cntrepo;

	@Autowired private MongoOperations mongo;
	
	public int getNextSequence(String collectionName)  {
		
		//JSONObject jsonObj = new JSONObject("{_id:tut}");
		String tmp = "'"+collectionName+"'";
		BasicQuery query1 = new BasicQuery("{_id:"+tmp+"}");
		if(mongo.exists(query1, "counter")) {
			System.out.println("True");
			Counter cnt = cntrepo.findByIds(collectionName);
			cnt.setSeq(cnt.getSeq()+1);
			cntrepo.save(cnt);
			return cnt.getSeq();
		}
		else {
			Counter cnt = new Counter();
			cnt.setIds(collectionName);
			cnt.setSeq(1);
			cntrepo.save(cnt);
			System.out.println("False");
			return cnt.getSeq();
		}
		//System.out.println(cntrepo.count());
		
	}
	
	public void decrimentId(String usrname) {
		
		Counter cnt = cntrepo.findByIds(usrname);
		cnt.setSeq(cnt.getSeq()-1);
		cntrepo.save(cnt);
	}
	
}
