package com.project.tutorial.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.CounterService.CounterRepoTutorial;
import com.project.CounterService.CounterRepository;
import com.project.CounterService.CounterServiceId;
import com.project.tutorial.model.Tutorial;
import com.project.tutorial.repository.TutorialRepository;
import com.project.user.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TutorialService {

	@Autowired
	private TutorialRepository tutorialRepository;
	@Autowired
	private CounterServiceId cntservice;
	//To add tutorial with rating 
	public HttpStatus  create(Tutorial tut) {
		
		System.out.println("tut: "+tut);
		int num = cntservice.getNextSequence("tut");
		tut.setId(num);
		tut.setTechName(tut.getTechName().toLowerCase());
		tut.setTutUrl(tut.getTutUrl().toLowerCase());;
		try {
		tutorialRepository.save(tut);
		}
		catch(Exception e) {
			
			cntservice.decrimentId("tut");
			
			return HttpStatus.EXPECTATION_FAILED;
		}
		return HttpStatus.ACCEPTED;
	}
	
	//To get all tutorials for given tags
	public List<Tutorial> getTutOnTag(LinkedList<String> lstobj){
		
		return tutorialRepository.findByTags(lstobj);
		
	}
	
	//To get all tutorials for given tag
	public List<Tutorial> getTutOnTag(String lstobj){
		
		return tutorialRepository.findByTags(lstobj);
	}

	//To get all tutorials for given collections
	public List<Tutorial> getAllTutorials(){
		return tutorialRepository.findAll();
	
	}
	//To get Tut of TutURL
	public Tutorial getTutorialByUrl(String tuturl) {
		System.out.println(tuturl);
		return tutorialRepository.findByTutUrl(tuturl);
	}
	
	
	
}
