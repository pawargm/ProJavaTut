package com.project.tutrating;


import java.util.LinkedList;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tutorial.model.Tutorial;
import com.project.user.model.User;

@Repository
public interface RatingRepo extends MongoRepository<Rating,String> {

	@Query("{$and:[{'userID':?0},{'tutID':?1}]}")
	public Rating getRating(int userid,int tutid);

	//public Rating findOne(org.springframework.data.mongodb.core.query.Query q, Class<Rating> class1);
}
