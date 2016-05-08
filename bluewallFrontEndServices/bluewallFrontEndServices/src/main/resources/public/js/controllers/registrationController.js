app.controller('registrationController', function($scope, $state, $rootScope, registrationService){
  console.log('inside registartionController');
  var obj = {}
  $scope.ratings = function(foodId, userRating){
	  if(foodId in obj){
		  obj[foodId] = userRating;
	  }
	  else{
		  obj[foodId] = userRating;
	  }
	  $rootScope.foodRatings = obj; 
  }
  
  $scope.register = function(user,food) {
	  console.log("Inside register function contrl")
	  registrationService.registerUser(user,$rootScope.foodRatings).then(function(data) {
			$state.go('welcome');
			alert("Registration Successful");
		}, function(error) {
			alert("Error in registering user")
		});
	}

});

app.service('registrationService', function($http, $state) {
	console.log("in registrationService");
	
	this.registerUser = function(user,foodTaste) {
		user.userRating = JSON.stringify(foodTaste);
		return $http.post("/register/createUser", user).then(function(data) {
					console.log("response from backend service: " + JSON.stringify(data));
					return data;
				});
	}
	
});

