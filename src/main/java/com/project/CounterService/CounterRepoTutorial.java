package com.project.CounterService;


import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.CounterService.Counter;



@Repository
public interface CounterRepoTutorial extends MongoRepository<Counter,String>{
	
	public Counter findByIds(String ids);
	
}

