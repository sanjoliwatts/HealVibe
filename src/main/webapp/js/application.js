var app = angular.module('App',[]);
app.config(function() {
	
});
var healthApp = angular.module('healthApp', ["ngCookies"]);
healthApp.config(function() {
	
});

var doctorApp = angular.module('doctorApp', ["ngRoute","ngCookies"]);
	doctorApp.config([ '$routeProvider', function($routeProvider) {
		$routeProvider	
		.when('/viewAppointments', {
			templateUrl : 'viewAppointments.html',
			controller : 'viewAppointmentsController'	
		}).when('/cancelDoctorAppointments', {
			templateUrl : 'cancelDoctorAppointments.html',
			controller : 'cancelDoctorAppointmentsController'
		})
		.when('/addHealthRecords', {
			templateUrl : 'addHealthRecords.html',
			controller : 'addHealthRecordsController'
		}).otherwise({
			redirectTo : '/'
		});
	}
	]);

var pharmaApp = angular.module('pharmaApp', ["ngCookies"]);
pharmaApp.config(function() {
	
});

var paymentApp = angular.module('paymentApp',[]);
paymentApp.config(function() {
	
});

var patientApp = angular.module('patientApp',["ngRoute","ngCookies"]);
patientApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider	
	.when('/bookAppointment', {
		templateUrl : 'bookAppointment.html',
		controller : 'bookAppointmentController'	
	}).when('/cancelAppointment', {
		templateUrl : 'cancelAppointment.html',
		controller : 'cancelAppointmentController'
	}).when('/viewHealthRecord', {
		templateUrl : 'viewHealthRecords.html'
	}).when('/bloodGroup', {
		templateUrl : 'bloodConnections.html',
		controller : 'bloodConnectionsController'
	}).otherwise({
		redirectTo : '/'
	});
}
]);


var application = angular.module('Application',["ngRoute","ngCookies"]);
/*application.config(['$stateProvider',function($stateProvider){
	$stateProvider
	.state('/registerPatient', {
	    url : '/',
	    templateUrl : 'partials/registerPatient.html',
	    controller : 'patientController'
	})
}])*/
application.config([ '$routeProvider', function($routeProvider) {
	//$routeProvider.hashPrefix('');
	$routeProvider	
	.when('/registerPatient', {
		templateUrl : 'partials/registerPatient.html',
		controller: "patientController"
	}).when('/registerDoctor', {
		templateUrl : 'partials/registerDoctor.html',
		controller: "doctorController"
	}).when('/login', {
		templateUrl : 'partials/login.html',
		controller:"LoginController"
	})/*.when('/welcomeDoctor', {
		templateUrl : 'partials/welcomeDoctor.html',
		controller: "LoginController"
	}).when('/adminLogin', {
		templateUrl : 'partials/adminLogin.html',
		controller: "AdminController"
	})*/.otherwise({
		redirectTo : '/'
	});
}
]);



 URI=getURI();

