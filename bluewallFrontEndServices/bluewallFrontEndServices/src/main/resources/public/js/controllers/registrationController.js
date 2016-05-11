app.controller('registrationController', function($scope, $state, $rootScope, messageService, registrationService){
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
		  if(null!=data.data){
				 messageService.info("Registration Successful");				
				 $state.go($state.current, {}, {reload: true});
			 }else{
				 messageService.error("Error","Could not register user");
				 $state.go($state.current, {}, {reload: true});
			 }
		}, function(error) {
			messageService.error("Error","Could not register user");
			$state.go($state.current, {}, {reload: true});
			console.log(error.statusText);
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

