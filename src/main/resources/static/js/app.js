var app = angular.module('myApp', ['ngRoute']);

//Application Configure to give single page facility
app.config(function($routeProvider) {
  $routeProvider

  .when('/home', {
    templateUrl : '/home',
    controller  : 'HomeController'
  })

  .when('/blog', {
    templateUrl : '/blog',
    controller  : 'BlogController'
  })

  .when('/about', {
    template : '<h2>in about</h2>',
    //controller  : 'AboutController'
  })

  .when('/login', {
	  
	  templateUrl : '/login',
	  controller : 'LoginForm'
	  
  })
  
  .when('/singup', {
	  
	  templateUrl : '/singup1',
	  controller : 'SingupForm'
	  
  })
  
  .when('/tutorial1', {
	  
	  templateUrl : '/tutorial',
	  controller : 'TutorialRecom'
	  
  })
  
  .when('/afterlogin1', {
	  templateUrl : '/afterlogin',
  })
  
  .when('/testform', {
	  templateUrl : '/testingform',
	  controller : 'FormTest'
	  		  
  })
  
  .when('/tutdetail',{
	  templateUrl : '/detailform',
	  controller:'RatingController'
  })
  
  .otherwise({redirectTo: '/'});
});

//To display when click on Home
app.controller('HomeController', function($scope) {
	  $scope.message = 'Hello from HomeController';
	});
//To display content when click on blog
app.controller('BlogController', function($scope) {
	  $scope.message = 'Hello from BlogController';
	});
//To display content when click on About 
app.controller('AboutController', function($scope) {
	  $scope.message = 'Hello from AboutController';
	});
//To display content for login form	
app.controller('LoginForm', function($scope){

	});
//to display contet for singup form	
app.controller('SingupForm', function($scope){
	
});

//controller for rating form

app.controller('RatingController', function($scope,$rootScope,$location,$interval,$http){

	var senddata = {tutUrl:$rootScope.tuturl}
	//$scope.msg = "Reached at other controller";
	
	$scope.Rateit = function(rate){
		$scope.msg = "Reached at form test";
		$scope.rating_data = parseInt(rate,10);
		var star = parseInt(rate,10);
		var tutid = parseInt($rootScope.id,10);//$scope.rating_tut;
		var senddata = {"tutID":tutid,"rating":star}
		 $http(
					{
						method  : 'POST',
						url     : 'http://localhost:8080/rating/rate',
						data	: senddata,
						headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
					} 
				 ).then
			      (
			    		  function (response){
			    			  if(response.data == "OK"){
			    				  $scope.send_data = "Successfully Rated Data!";
			    			  }
			    			  else{
			    				  document.getElementById("tbox").disabled = true;
			    				  $scope.double_rating = "You Already give rating and can't give it now!";
			    			  }
			    		  	},
			    		  	function (reason){
			    		  	
							}	
		      );
		
		
		
		
	}
	
	
	//Tut list
	 $http(
				{
					method  : 'POST',
					url     : 'http://localhost:8080/tut/tutbyurl',
					data	: senddata,
					headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
				} 
			 ).then
		      (
		    		  function (response){
	    		  	  
		    			  $scope.title = response.data.techName;
		    			  $scope.author = response.data.userName;
		    			  $scope.tags = response.data.tags;
		    			  console.log("Over");
		    		  	},
		    		  	function (reason){
		    		  		$location.path('/login');
						}	
	      );

	
	
	
});



//Form Test
app.controller('FormTest', function($rootScope,$scope,$http,$location,$interval,$timeout){
	
	
	
	//Form Function
	var sessionid;
	var stoptime;
	$scope.CheckData = function(tutid,tuturl){
		$scope.msg = "Reached at form test";
		
		var value = document.getElementById('tuturl').innerHTML;
		//var value2 = document.getElementValue('tuturl');
		$rootScope.id = tutid
		$rootScope.tuturl = tuturl
		console.log(parseInt(tutid,10));
		console.log(tuturl);
		$location.path("/tutdetail");
	}
	//set ID
	$http(
			{
				method  : 'GET',
				url     : 'http://localhost:8080/dummy',
				headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
			} 
		 ).then
	      (
	    		  function (response){
    		  	    	
	    			  //$scope.res = response.data;
	    			  //$scope.res = "you reached herer!";
	    			  $scope.session_data = response.data;
	    			  $rootScope.userid = response.data;
	    			  sessionid = response.data;
	    			  console.log("Session set");
	    			  console.log(response.data);
	    		  	},
	    		  	function (reason){
	    		  		//$location.path('/login');
					}	
      );
	
	
	
	
	//Tut list
	 $http(
				{
					method  : 'GET',
					url     : 'http://localhost:8080/tut/all',
					headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
				} 
			 ).then
		      (
		    		  function (response){
	    		  	    	
		    			  //$scope.res = response.data;
		    			  //$scope.res = "you reached herer!";
		    			  $scope.res = response
		    			  $scope.names = response.data
		    			  console.log("Over");
		    		  	},
		    		  	function (reason){
		    		  		//$location.path('/login');
						}	
	      );

	 //to get flow
	 $http(
				{
					method  : 'GET',
					url     : 'http://localhost:8080/recomdationstart',
					headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
				} 
			 ).then
		      (
		    		  function (response){
	    		  		  console.log("Observer!");
		    			  console.log(response.data);
		    			  stoptime = $interval(function(){poll();},2000);

		    		  	},
		    		  	function (reason){
		    		  		//$location.path('/login');
		    		  		console.log("failed Stale");
						}	
	      );
	 
	//Polling function
	var poll = function() {
		
		console.log('poll function');
		
		
		 $http(
					{
						method  : 'GET',
						url     : 'http://127.0.0.1:8000/testarg?arg=1',//+$rootScope.userid,
						headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
					} 
				 ).then
			      (
			    		  function (response){
		    		  	    	
			    			  //$scope.res = response.data;
			    			  //$scope.res = "you reached herer!";
			    			
			    			  if(response.data.data != "not_present"){
			    				  console.log("Stopping request")
			    				  $interval.cancel(stoptime);
			    				  $scope.responsedata = response.data.data;
			    			  }
			    			  
			    			  console.log(response.data);
			    		  	},
			    		  	function (reason){
			    		  		//$location.path('/login');
							}	
		      );
		
		
		/*if(flag == 10){
			$interval.cancel(stoptime);
			console.log(flag);
		}
		flag++;*/
	};


	
})



//TUT
app.controller('TutorialRecom', function($scope,$http,$location,$timeout,$interval){

	
	$scope.message = "Hello From TUT! HA HA";
	
	var flag = 0;
	
	
	
	$scope.rating = function(){
	
		$scope.datafront = $scope.tuturl;
		$scope.datafront = "You Reached";
		}
	//$scope.rating();
	 $http(
				{
					method  : 'GET',
					url     : 'http://localhost:8080/tut/all',
					headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
				} 
			 ).then
		      (
		    		  function (response){
	    		  	    	
		    			  //$scope.res = response.data;
		    			  //$scope.res = "you reached herer!";
		    			  $scope.res = response
		    			  $scope.names = response.data
		    			  console.log("Over");
		    		  	},
		    		  	function (reason){
		    		  		$location.path('/login');
						}	
	      );

		var poll = function() {
				
				console.log('poll function');
				
				
				 $http(
							{
								method  : 'GET',
								url     : 'http://127.0.0.1:8000/test',
								headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
							} 
						 ).then
					      (
					    		  function (response){
				    		  	    	
					    			  //$scope.res = response.data;
					    			  //$scope.res = "you reached herer!";
					    			
					    			  if(response.data.test == "success"){
					    				  console.log("Stopping request")
					    				  $interval.cancel(stoptime);
					    				  $scope.responsedata = response.data.test;
					    			  }
					    			  
					    			  console.log(response.data.test);
					    		  	},
					    		  	function (reason){
					    		  		$location.path('/login');
									}	
				      );
				
				
				/*if(flag == 10){
					$interval.cancel(stoptime);
					console.log(flag);
				}
				flag++;*/
		};
		
		var stoptime = $interval(function(){poll();},2000);
		//$timeout(poll,4000);
		 
		


		
		
		
});



//Controller to check user session
app.controller('CheckSession', function($scope,$http,$location){
	
	$scope.linkAddress = function(){
	 $http(
				
				{
					method  : 'GET',
					url     : 'http://localhost:8080/islogin',
					headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
				} 
			 ).then
		      (
	    		  	    function (response){
	    		  	    	
						if(response.data == "OK"){
							$location.path('/tutorial1');
							
						}
						else{
							$location.path('/login');
						}
	    		  	},

					function (reason){
	    		  		$location.path('/login');
					}	
	      );
	}
});

//Controller for hadling make rest call for create user, login and checking email and username
app.controller("MyController",function($http,$scope,$rootScope,$httpParamSerializerJQLike,$location,$route){
	
	$scope.createUser = function(){
		
		var objdata = {firstName:$scope.fname,lastName:$scope.lname,email:$scope.email,userName:$scope.uname,password:$scope.password};

		$http(
				{
					method  : 'POST',
					url     : 'http://localhost:8080/create',
					data    : objdata,
					headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
				} 
			 ).then
			      (
 							function (response){
								var data = response;
								//$scope.data = data	
								if(response.data == "OK"){
									//$location.path('/login');
									$scope.data = "You are registered, please login";
									//myService.set("You are registered, please login");
									//$location.path('/login');
								}
							},

							function (reason){
								$scope.errordata = reason.data;
							}	
					)
		}
	
	
		$scope.checkEmail = function(){

			var email_lower = $scope.email;
			var lower = email_lower.toLowerCase();
			var objdata = {email:lower};

			$http(
					{
						method  : 'GET',
						url     : 'http://localhost:8080/checkemail?',
						params    : objdata,
						headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
					} 
				 ).then
				      (
				    		  	    function (response){
									$scope.data = response.data
									//$scope.data = data	
									if($scope.data == "FOUND")
									{
										$scope.message = "email is already exit, please use other mail id";
										alert($scope.message)
									}
									if($scope.data == "NOT_FOUND"){
										$scope.message = "email is not present";
									}
								},

								function (reason){
									$scope.errordata = reason.data;
								}	
				      )
	}
		
		
		$scope.checkUserName = function(){
			
			var objdata = {username:$scope.uname};
			$http(
					{
						method  : 'GET',
						url     : 'http://localhost:8080/checkusername?',
						params    : objdata,
						headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
					} 
				 ).then
				      (
				    		  	    function (response){
									$scope.data = response.data	
									if($scope.data == "FOUND")
									{
										$scope.message = "username is already exit, please use different username! :)";
										alert($scope.message);
									}
									if($scope.data.data == "NOT_FOUND"){
										$scope.message = "usrname is not present";
									}
									
								},

								function (reason){
									$scope.errordata = "In Exception"//reason.data;
								}	
				      )
	}
	
	$scope.authenticateUser = function(){
		var objdata = {userName:$scope.uname,password:$scope.password};
		$http(
				{
					method  : 'POST',
					url     : 'http://localhost:8080/login1',
					data    : objdata,
					headers : { 'Content-Type': 'application/json' }  // set the headers so angular passing info as form data (not request payload)
				} 
			 ).then
			      (
 					function (response){

 						if(response.data == "OK")
							{		
 									//$route.reload();
									$location.path('/singup');
									//$location.path('http://localhost:8080/singlepage#!/singup');
							}
						},
					function (reason){
								$scope.errordata = reason.data;
					}	
					);
					
	
					
					
			}
		

		
});






