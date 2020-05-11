application.controller("ApplicationController",
		function($scope, $http,$location,$window) {
//	console.log("hello load");
	
	$scope.load=function(){
	//	console.log("hello load1");
	$window.location.href = 'partials/payments.html';
}
	$scope.navigateToHealth= function()
	{
		$window.location.href = 'partials/healthPackages1.html';
	}
	
});


application.controller("LoginController",
		
		   function($scope, $http,$location,$window,$cookies) {
	  $scope.credentials={};
	  document.getElementById("login").click();
  	$scope.credentials.userId=null;
  	$scope.credentials.password=null;
  	$scope.credentials.message=null;
  	
    $scope.credentials.login = function() {
    	console.log("Inside credentials.login "+ URI + "Hospital/fetchdetails/" + $scope.credentials.userId + 
		"/" + $scope.credentials.password);
    	sessionStorage.setItem("loginsession", "yes");
    	$http.get(URI + "Hospital/fetchdetails/" + $scope.credentials.userId + 
    			"/" + $scope.credentials.password).then(
    			function(response) {	
    				$scope.credentials.message = response.data.message;
    				console.log($scope.credentials.message);
    				if(angular.equals($scope.credentials.message,"Admin"))
    					$window.location.href = 'partials/adminLogin.html';
    				if(angular.equals($scope.credentials.message,"Doctor"))
    					{
    					$cookies.put('did',$scope.credentials.userId);
    					$window.location.href = 'partials/welcomeDoctor.html';
    					}
    				else if(angular.equals($scope.credentials.message,"Patient")){
    					$cookies.put('pid',$scope.credentials.userId);
    					$window.location.href = 'partials/welcomePatient.html'; 
    				}
    				$scope.credentials.isLoggedIn=true;	
    			}, function(response) {	
					$scope.credentials.message = response.data.message;
		});
    };
    
});

/*application.controller("loadController",
		
		   function($scope, $http,$location,$window) {
	
});*/

patientApp.controller("patientBookingController",
		   function($scope,$http,$cookies){
	//console.log("Inside Patient booking controller");
		
		$scope.patient={};
		$scope.patient.pid=null;
		$scope.patient.message=null;
		$scope.patient.pname= null;
		$scope.patient.pid=parseInt($cookies.get('pid'));

		$scope.record={};
	
		$scope.record.pId = null;
		$scope.record.height = null;
		$scope.record.weight = null;
		$scope.record.issueList = null;
		$scope.record.allergies = null;
		$scope.record.medications = null;
		$scope.record.doctorName = null;
	//	console.log($scope.appointment.pid);
		$scope.patient.fetchName=function()
		{
			//console.log(URI + "Hospital/fetchNameOfPatient/"+$scope.patient.pid);
			$http.get(URI + "Hospital/fetchNameOfPatient/"+$scope.patient.pid).then(
					function(response) {
						$scope.patient.pname = response.data.username;
						$scope.patient.message=null;
					},function(response){
						$scope.patient.pname=null;
						$scope.patient.message= response.data.message;
			});
		};
			
			$scope.patient.fetchHealthRecord= function()
			{
			//	console.log("Fetching Health Record....");
				$http.get(URI + "Hospital/fetchHealthRecord/"+$scope.patient.pid).then(
						function(response) {
							$scope.record = response.data;
							$scope.patient.message=null;
						},function(response){
							$scope.record=null;
							$scope.patient.message = response.data.message;
				});
			};
			
			
			/*cope.patient.fetchHealthRecord= function()
			{
				$http.get(URI + "Hospital/fetchHealthRecord/"+$scope.patient.pid).then(
						function(response) {
							$scope.patient.pname = response.data.username;
							$scope.patient.message=null;
						},function(response){
							$scope.patient.pname=null;
							$scope.patient.message= response.data.message;
				});
			};*/
  		
});

patientApp.controller("bookAppointmentController",
		   function($scope,$http,$cookies){
	document.getElementById("bookAppointment").click();
	$scope.departmentList=['Dermatology','Arthroscopy','Cardio Thoracic','Gastroenterology',
	                       'Nephrology','ENT','Anaesthesiology','Diabetes And Metabolic Disorders','Cardiology',
	                       'Neurology','Dialysis','Infertility','Physiotherapy','Health Check',
	                       'Paediatric Cardiology','Urology','Vascular Surgery','Uro-Oncology','Rheumatology','Thoracic Surgery'
	                       ,'Pulmonology','Pain & Palliative Care','Ophthalmology','Orthopaedic Oncology',
	                       'Mental Health','Geriatrics','Endocrinology','Gynae-oncology','IVF',
	                       'Clinical Genetics','Hyperbaric Oxygen Therapy'];
		$scope.appointment={};
		$scope.name=null;
		$scope.appointment.pid=null;
		$scope.appointment.department=null;
		$scope.appointment.dId=null;
		$scope.appointment.dName=null;
    	$scope.appointment.app_date=null;
		$scope.appointment.message=null;
		$scope.appointment.pname= null;
		$scope.doctorsList = {'id':'', 'name':''};
		$scope.appointment.pid=parseInt($cookies.get('pid'));
		$scope.appointment.sendValue=function()
		{
		//	console.log("sending value");
			//console.log($scope.appointment.dId);
			$scope.appointment.dName=$scope.doctorsList[$scope.appointment.dId];
			//console.log($scope.appointment.dName);
		};
		
		
		$scope.appointment.bookAppointment=function(){
			$scope.appointment.message = null;
			$scope.appointment.dId=parseInt($scope.appointment.dId);
			//console.log("booking...");
			//console.log($scope.appointment.pid);
			$scope.appointment.app_date = $scope.appointment.app_date.toLocaleString()
			var data = JSON.stringify($scope.appointment);
			//console.log(data);
			$http.post(URI + "Hospital/bookAppointment", data).then(function(response) {
				//console.log("Hi");
				$scope.appointment.message = response.data.message;
			}, function(response) {	
				//console.log("Bye");
				$scope.appointment.message = response.data.message;
			});
		};
		
		console.log($scope.doctorsList);
		
		$scope.appointment.fetchDoctors=function(){
			$http.get(URI + "Hospital/fetchDoctors/"+$scope.appointment.department).then(
			function(response) {
				$scope.doctorsList = response.data;
				//console.log($scope.doctorsList);
				//$scope.appointment.dId=response.data;
				//console.log($scope.appointment.dId);
				$scope.message=null;
			}, function(response) {
				$scope.doctorsList=null;
				$scope.message= response.data.message;
	});
	}
	//alert($scope.doctorsList['id']);
});


patientApp.controller("cancelAppointmentController",
		function($scope,$http,$cookies){
	$scope.details={};
	$scope.details.reference=null;
	$scope.details.pid=null;
	$scope.details.dName=null;
	//$scope.referenceList=[];
	$scope.details.department=null;
	$scope.details.app_date=null;
	$scope.message=null;
	document.getElementById("cancelAppointment").click();
	$scope.fetchAppointmentList=function(){
		$scope.details.pid=parseInt($cookies.get('pid'));
		//console.log("fetching...");
		$http.get(URI + "Hospital/fetchAppointmentList/"+$scope.details.pid).then(
				function(response) {
				//	console.log("Hi");
					$scope.details = response.data;
					$scope.message=null;
				}, function(response) {
				//	console.log("Bye");
					$scope.details=null;
					$scope.message= response.data.message;
		});
	}
	$scope.cancelAppointment=function(i){
			$scope.message = null;
		//	console.log("cancel"+ i);
			$http.get(URI + "Hospital/cancelAppointment/"+i).then(function(response) {
				//console.log(response);
				$scope.message = response.data.message;
				$scope.fetchAppointmentList();
			}, function(response) {	
				$scope.message = response.data.message;
			});
		};
		
		$scope.fetchAppointmentDetails=function(){
		//	console.log("fetching details...");
			$http.get(URI + "Hospital/fetchAppointmentDetails/"+$scope.details.reference).then(
					function(response) {
						$scope.details = response.data;
						$scope.message=null;
					}, function(response) {
						$scope.details=null;
						$scope.message= response.data.message;
			});
		}
});

patientApp.controller("bloodConnectionsController",
		function($scope,$http,$cookies){
	document.getElementById("registerBloodGroup").click();
	
	$scope.blood={};
	$scope.blood.blood_group=null;
    $scope.blood.last_date_of_donation=null;
    $scope.blood.pid=null;
    $scope.blood.message=null;
    console.log("Hello-blood-controller");
    $scope.updateTheForm = function() {
        $scope.blood.message = null;
       // console.log("Hello");
        $scope.blood.pid=parseInt($cookies.get('pid'));
        $scope.blood.last_date_of_donation = $scope.blood.last_date_of_donation.toLocaleString()
        var data = JSON.stringify($scope.blood);
        $http.post(URI + "Hospital/updateTheForm",data).then(function(response) {
        //	console.log(response);
            $scope.blood.message = response.data.message;
        }, function(response) {
        	
            $scope.blood.message = response.data.message;
        });
    };
});


app.controller("AdminController",
        function($scope, $http) {
		$scope.doctorList=[];
		$scope.doctor={};
		$scope.doctor.name=null;
		$scope.doctor.age=null;
		$scope.doctor.experience=null;
		$scope.doctor.department=null;
		
		$scope.message=null;
		$scope.details={};
		$scope.details.name=null;
		$scope.details.did=null;
		$scope.details.message=null;
		$scope.fetchDoctorList=function(){
		//	console.log("hello");
			$http.get(URI + "Hospital/fetchDoctorList").then(
					function(response) {
						$scope.doctorList = response.data;
						$scope.message=null;
					}, function(response) {
						$scope.doctorList=null;
						$scope.message= response.data.message;
			});
		}
		$scope.confirm=function(){
			$http.post(URI + "Hospital/confirm/"+ $scope.details.did,$scope.details).then(
					function(response) {	
						$scope.details.message=response.data.message;
						$scope.fetchDoctorList();
					}, function(response) {
						
						$scope.details.message= response.data.message;
			});
		}
		
		$scope.deleteEntry=function(){
			$http.post(URI + "Hospital/delete/"+ $scope.details.did).then(
					function(response) {	
						$scope.details.message=response.data.message;
						$scope.details.did=null;
						$scope.fetchDoctorList();
					}, function(response) {
						
						$scope.details.message= response.data.message;
			});
		}
		
		$scope.fetchdoctordetails=function(){
			$http.get(URI + "Hospital/fetchDoctordetails/"+ $scope.details.did).then(
					function(response) {
						$scope.doctor = response.data;
						$scope.message=null;
					}, function(response) {
						$scope.doctor=null;
						$scope.message= response.data.message;
			});
		}
});


application.controller("patientController",
        function($scope, $http) {
	document.getElementById("registerPatient").click();
	
            $scope.signupForm = {};
            $scope.signupForm.name=null;
            $scope.signupForm.age = null;
            $scope.signupForm.emailId = null;
            $scope.signupForm.contactNo = null;
            $scope.signupForm.password = null;
            $scope.signupForm.question=null;
            $scope.signupForm.message=null;
            $scope.signupForm.cpassword=null;
            $scope.signupForm.answer=null;
            
           $scope.matchpattern=function()
            {
            	$scope.signupForm.res=null;
            	var a=$scope.signupForm.password;
            	if(!(a.match("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$")))
          	      {
            		$scope.signupForm.res1="Password Should be Minimum 8 characters at least 1 uppercase 1 number and 1 special character";
            		$scope.signupForm.password=null;
          	      }
          	  
            }
            $scope.matchpassword=function()
            {
            		if($scope.signupForm.cpassword!=$scope.signupForm.password)
            		{
            			$scope.signupForm.cpassword=null;
            			$scope.signupForm.password=null;
            			$scope.signupForm.res2="Password doesn't match";
            		}
            }
            $scope.reset= function()
            {
            	$scope.signupForm.res2=null;
            	//$scope.signupForm.res1=null;
            }
            $scope.reset1= function()
            {
            	//$scope.signupForm.res2=null;
            	$scope.signupForm.res1=null;
            }

           $scope.signupForm.submitTheForm = function() {
                $scope.signupForm.message = null;
             //   console.log("Hello");

                var data = JSON.stringify($scope.signupForm);
              //  console.log(data);

                $http.post(URI + "Hospital/signup", data).then(function(response) {
                //	console.log(response);
                    $scope.signupForm.message = response.data.message;
                }, function(response) {
                	
                    $scope.signupForm.message = response.data.message;
                });
            };
      
        });
application.controller("doctorController",
        function($scope, $http) {
	document.getElementById("registerDoctor").click();
            $scope.registerForm = {};
            $scope.registerForm.name=null;
            $scope.registerForm.age = null;
            $scope.registerForm.department=null;
            $scope.registerForm.emailId = null;
            $scope.registerForm.contactNo = null;
            $scope.registerForm.password = null;
            $scope.registerForm.question=null;
            $scope.registerForm.message=null;
            $scope.registerForm.experience=null;
            $scope.registerForm.cpassword=null;
            $scope.registerForm.answer=null;
            $scope.registerForm.status=null;
            $scope.registerForm.patientCount=null;
            $scope.departmentList=['Dermatology','Arthroscopy','Cardio Thoracic','Gastroenterology','Nephrology','ENT','Liver Transplant','Heart Transplant','Anaesthesiology','Cardiology','Neurology','Kidney Transplant','Surgical Oncology','Physiotherapy','Paediatric Surgery','Paediatric Cardiology','Urology','Vascular Surgery','Uro-Oncology','Rheumatology','Thoracic Surgery','Pulmonology','Pain & Palliative Care','Ophthalmology','Orthopaedic Oncology','Nuclear Medicine','Mental Health','Infertility','Geriatrics','Endocrinology','Gynae-oncology','IVF','Clinical Genetics','Hyperbaric Oxygen Therapy'];
           $scope.registerForm.submitTheForm = function() {
                $scope.registerForm.message = null;
             //   console.log("Hello");

                var data = JSON.stringify($scope.registerForm);
              //  console.log(data);
              
                $http.post(URI + "Hospital/register", data).then(function(response) {
                //	console.log(response);
                    $scope.registerForm.message = response.data.message;
                }, function(response) {
                	
                    $scope.registerForm.message = response.data.message;
                });
            };
            $scope.matchpattern=function()
            {
            	$scope.registerForm.res=null;
            	var a=$scope.registerForm.password;
            	if(!(a.match("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$")))
          	      {
            		$scope.registerForm.res1="Password Should be Minimum 8 characters at least 1 uppercase 1 number and 1 special character";
            		$scope.registerForm.password=null;
          	      }
          	  
            }
            $scope.matchpassword=function()
            {
            		if($scope.registerForm.cpassword!=$scope.signupForm.password)
            		{
            			$scope.registerForm.cpassword=null;
            			$scope.registerForm.password=null;
            			$scope.registerForm.res2="Password doesn't match";
            		}
            }
            $scope.reset= function()
            {
            	$scope.registerForm.res2=null;
            	//$scope.signupForm.res1=null;
            }
            $scope.reset1= function()
            {
            	//$scope.signupForm.res2=null;
            	$scope.registerForm.res1=null;
            }
      
        });
healthApp.controller("healthController", function($scope, $http,$cookies) {
	$scope.patient={};
	$scope.patient.pid=null;
	$scope.patient.message=null;
	$scope.patient.pname= null;
	$scope.patient.emailId = null;
	$scope.patient.pid=parseInt($cookies.get('pid'));

//	console.log($scope.appointment.pid);
		
			
			$scope.patient.fetchDetails=function()
			{
				$http.get(URI + "Hospital/fetchDetailsOfPatient/"+$scope.patient.pid).then(
						function(response) {
							$scope.patient.pname = response.data.name;
						//	console.log($scope.patient.pname);
							$scope.patient.emailId = response.data.emailId;
						//	console.log($scope.patient.emailId);
							$scope.patient.message=null;
						},function(response){
							$scope.patient.pname=null;
							$scope.patient.message= response.data.message;
				});
			};	
});
application.controller("bookAppointment", function() {
	
});


paymentApp.controller("paymentAppController", function($scope,$location,$window) {
//	console.log("Inside paymentAppController");
	
	
		$scope.pay=function(){
			$window.location.href='http://localhost:8765/trial/partials/payments.html';
		}
});

doctorApp.controller("viewAppointmentsController", function($scope, $http,$window,$cookies) {
	document.getElementById("viewAppointments").click();
	$scope.a={};
	$scope.a.dId=parseInt($cookies.get('did'));;
	$scope.a.message=null;
	$scope.a.app_date=null;
	
	$scope.showAppointments=function(){
		 $scope.a.app_date = $scope.a.app_date.toLocaleString();
		// console.log($scope.a.app_date);
		 var data = JSON.stringify($scope.a);
		 $http.post(URI + "Hospital/showAppointments",data).then( 
   					/*		 $http.get(URI + "Hospital/showAppointments/" + $scope.id+ 
	    			"/" + $scope.date1).then( */
   			function(response) {
   				//console.log("loading response in show appointments"+response.data);
   				$scope.appointment=response.data;
   				
   			}, function(response) {
   			//	console.log("loading response1 i show appointments");
					$scope.a.message = response.data.message;
		});
	}
});

doctorApp.controller("cancelDoctorAppointmentsController", function($scope, $http,$window,$cookies) {
	document.getElementById("cancelDoctorAppointment").click();

	$scope.details={};
	$scope.details.reference=null;
	$scope.details.did=null;
	$scope.details.pid=null;
	$scope.details.dName=null;
	$scope.referenceList=[];
	$scope.details.department=null;
	$scope.details.app_date=null;
	$scope.message=null;
	$scope.appointment=[{userId:null,name:null,emailId:null,contactNo:null,password:null,age:null,answer:null,question:null}];
	//console.log("In cancel Appointment controller1");
	
	$scope.fetchReferenceList=function(){
		$scope.details.did=parseInt($cookies.get('did'));
		//console.log("fetching...");
		$http.get(URI + "Hospital/fetchReferenceList/"+$scope.details.did).then(
				function(response) {
					$scope.referenceList = response.data;
					$scope.message=null;
				}, function(response) {
					$scope.referenceList=null;
					$scope.message= response.data.message;
		});
	}
	$scope.cancelAppointment=function(i){
			$scope.message = null;
		//	console.log("cancel"+i);
			$http.get(URI + "Hospital/cancelAppointment/"+i).then(function(response) {
				//console.log(response);
				$scope.message = response.data.message;
				$scope.fetchReferenceList();
			}, function(response) {	
				$scope.message = response.data.message;
			});
		};
		
		$scope.fetchAppointmentDetails=function(i){
			console.log("fetching details...");
			$scope.details.reference=i;
			$http.get(URI + "Hospital/fetchAppointmentDetails/"+$scope.details.reference).then(
					function(response) {
						$scope.details = response.data;
						$scope.message=null;
					}, function(response) {
						$scope.details=null;
						$scope.message= response.data.message;
			});
		}
	
});
doctorApp.controller("doctorWelcomeController", function($scope, $http,$window,$cookies) {
//	console.log("In doctor controller");
	$scope.logout = function() {
        sessionStorage.setItem("loginsession", "no");
       // alert(sessionStorage.getItem("loginsession"));
        $cookies.remove('did');
 }

	
	
	
});

doctorApp.controller("addHealthRecordsController", function($scope, $http,$window,$cookies) {
	document.getElementById("addHealthRecords").click();
	   $scope.records = {};
       $scope.records.pId=null;
       $scope.records.height = null;
       $scope.records.weight = null;
       $scope.records.issueList = null;
       $scope.records.allergies = null;
       $scope.records.medications=null;
       $scope.records.doctorName=null;
       $scope.records.message=null;
     
      $scope.records.submitRecords = function() {
           $scope.records.message = null;
           var data = JSON.stringify($scope.records);
          // console.log(data);
           $http.post(URI + "Hospital/addRecords", data).then(function(response) {
           	$scope.records=response.data;
               $scope.records.message = response.data.message;
           }, function(response) {
        		$scope.records=response.data;	
               $scope.records.message = response.data.message;
           });
       };
	
});



pharmaApp.controller("pharmaController", function($scope) {
	$scope.quantity1={};
	//console.log("Inside controller");
	$scope.input=0;
	$scope.stepUp=function()
	{
		//console.log("Inside stepUp function");
		$scope.input+=1;
	}
	
});