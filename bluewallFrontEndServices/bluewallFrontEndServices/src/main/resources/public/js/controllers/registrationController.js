app.controller('registrationController', function($scope, $state, $rootScope, registrationService){
  console.log('inside registartionController');

  $scope.register = function(user) {
	  console.log("Inside register function contrl")
	  registrationService.registerUser(user).then(function(data) {
			$rootScope.authenticated = true;
			$state.go('welcome');
			alert("Registration Successful");
		}, function(error) {
			alert("Error in registering user")
		});
	}

});

app.service('registrationService', function($http, $state) {
	console.log("in registrationService");
	
	this.registerUser = function(user) {
		return $http.post("/register/createUser", user).then(function(data) {
					console.log("response from backend service: " + JSON.stringify(data));
					return data;
				});
	}
});