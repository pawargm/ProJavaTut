package com.project.CallDjano;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CallDjango {
	
	RestTemplate resttemp = new RestTemplate();
	
	@RequestMapping(value="/callother",method=RequestMethod.GET)
	public String getData() {
		
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(header);
		
		System.out.println("==================================================");
		
		System.out.println(resttemp.exchange("http://127.0.0.1:8000/test",HttpMethod.GET,entity,String.class).getBody());
		
		return resttemp.exchange("http://127.0.0.1:8000/test",HttpMethod.GET,entity,String.class).getBody();
		
	}

	@RequestMapping(value="/callwitharg",method=RequestMethod.GET)
	public String getDataByArg(@RequestParam String arg) {
		
		System.out.println(arg);
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(header);
		
		String URL = "http://127.0.0.1:8000/testarg"+"?"+"arg="+arg;
		
		System.out.println("==================================================");
		System.out.println(resttemp.exchange(URL,HttpMethod.GET,entity,String.class).getBody());
		
		return resttemp.exchange(URL,HttpMethod.GET,entity,String.class).getBody();
		
	}
	
	public Runnable getNewRestThread(final String URL) {
		
	     Runnable r = new Runnable() {
	         public void run() {
	        	 
	     		HttpHeaders header = new HttpHeaders();
	    		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    		HttpEntity<String> entity = new HttpEntity<String>(header);
	    		
					//String URL2 = "http://127.0.0.1:8000/trainandprediction?arg="+str;	
	    		try {
					String res2 = resttemp.exchange(URL,HttpMethod.GET,entity,String.class).getBody();
					System.out.println("Final Result"+res2);
	    		}
	    		catch(Exception e) {
	    			System.out.println("Failed Recommandation Calling Thread"+URL);
	    			System.out.println(e.toString());
	    		}
	         }
	     };

	     return r;
		
	}
	
	
	
	@RequestMapping(value="/recomdationstart", method=RequestMethod.GET)
	public HttpStatus initiateRecomadation(HttpSession session) {
		
		System.out.println("Reached at initiateRecomadation");
		int userid = 1;//Integer.parseInt(session.getAttribute("usrsession").toString());
		
		//Check for stale data
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(header);
		
		String URL = "http://127.0.0.1:8000/staledata?arg="+userid;//session.getAttribute("usrsession").toString();		
		
		String res = resttemp.exchange(URL,HttpMethod.GET,entity,String.class).getBody();
		System.out.println("Successfully find Stale or Not Stale data and Result is as below:");
		System.out.println(res);
		System.out.println("==============================================");
		
		if(res.equals("justpolling")) {
			System.out.println("In JustPolling");
			return HttpStatus.OK;
		}
		else {
			
			if(res.equals("predictiononly")) {
		
				System.out.println("Calling PredictionOnly.......................");
				
				/*String URL1 = "http://127.0.0.1:8000/preditiononly?arg="+session.getAttribute("usrsession").toString();		
				String res1 = resttemp.exchange(URL1,HttpMethod.GET,entity,String.class).getBody();*/
				//final String str = session.getAttribute("usrsession").toString();
				final String URL2 = "http://127.0.0.1:8000/preditiononly?arg="+"1";
				
				Runnable r = getNewRestThread(URL2);
			    new Thread(r).start();
			    System.out.println("Thread Calling Ended");
				return HttpStatus.OK;
				
			}
			if(res.equals("startall")) {
				/*System.out.println("Result1234");
				String URL2 = "http://127.0.0.1:8000/trainandprediction?arg="+session.getAttribute("usrsession").toString();		
				
				String res2 = resttemp.exchange(URL2,HttpMethod.GET,entity,String.class).getBody();
				System.out.println("Final Result"+res2);
				return "startpolling";
				*/
				System.out.println("Calling StartAll..........................");
				//final String str = session.getAttribute("usrsession").toString();
				final String URL2 = "http://127.0.0.1:8000/trainandprediction?arg="+"1";
				/*final String str = session.getAttribute("usrsession").toString();
			     Runnable r = new Runnable() {
			         public void run() {
			        	 
			     		HttpHeaders header = new HttpHeaders();
			    		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			    		HttpEntity<String> entity = new HttpEntity<String>(header);
			    		
							String URL2 = "http://127.0.0.1:8000/trainandprediction?arg="+str;							
							String res2 = resttemp.exchange(URL2,HttpMethod.GET,entity,String.class).getBody();
							System.out.println("Final Result"+res2);
			         }
			     };
					*/
				Runnable r = getNewRestThread(URL2);
			    new Thread(r).start();
			    System.out.println("Thread Calling Ended");
				return HttpStatus.OK;
			}
			else {
				return HttpStatus.OK;
			}
			
		}
		
		
		
	}
	
	
	

}
