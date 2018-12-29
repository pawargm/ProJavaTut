package com.project.tutrating;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.LinkedList;

import com.project.tutorial.model.Tutorial;
import com.project.tutorial.repository.TutorialRepository;
import com.project.tutrating.Rating;
import com.project.tutrating.RatingRepo;

@RestController
public class RatingService {

	@Autowired
	private RatingRepo ratingrepo;
	
	@RequestMapping(value="/rating/rate",method=RequestMethod.POST)
	public HttpStatus rate(@RequestBody Rating rate,HttpSession session) {
		
		try {
			int userid = Integer.parseInt(session.getAttribute("usrsession").toString());
			
			rate.setUserID(userid);
			Rating tmp = ratingrepo.getRating(userid, rate.getTutID());
			ratingrepo.save(rate);
		
			if(tmp != null) {
				return HttpStatus.BAD_REQUEST;
			}
			else {
			ratingrepo.save(rate);
			}
		}
		catch(Exception e) {
			return HttpStatus.EXPECTATION_FAILED;
		}
		
		return HttpStatus.OK;
	}
	
	
	
}
