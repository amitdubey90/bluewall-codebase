app.controller('registrationController', function($scope, $state, $rootScope, registrationService){
  console.log('inside registartionController');

  $scope.register = function(user,food) {
	  console.log("Inside register function contrl")
	  var foodTaste=[];
	 
	  if(document.getElementById('food1')){
		  foodTaste.push({"key":food.food1,"value":document.getElementById('food1').options[document.getElementById('food1').selectedIndex].text});
	  }
	  if(document.getElementById('food2')){
		  foodTaste.push({"key":food.food2,"value":document.getElementById('food2').options[document.getElementById('food2').selectedIndex].text});
	  }
	  if(document.getElementById('food3')){
		  foodTaste.push({"key":food.food3,"value":document.getElementById('food3').options[document.getElementById('food3').selectedIndex].text});
	  }
	  if(document.getElementById('food4')){
		  foodTaste.push({"key":food.food4,"value":document.getElementById('food4').options[document.getElementById('food4').selectedIndex].text});
	  }
	  if(document.getElementById('food5')){
		  foodTaste.push({"key":food.food5,"value":document.getElementById('food5').options[document.getElementById('food5').selectedIndex].text});
	  }
	  if(document.getElementById('food6')){
		  foodTaste.push({"key":food.food6,"value":document.getElementById('food6').options[document.getElementById('food6').selectedIndex].text});
	  }
	  if(document.getElementById('food7')){
		  foodTaste.push({"key":food.food7,"value":document.getElementById('food7').options[document.getElementById('food7').selectedIndex].text});
	  }
	  
	  console.log(foodTaste);
	  
	  registrationService.registerUser(user,foodTaste).then(function(data) {
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
		console.log("i am here");
		user.foodTasteList = foodTaste;
		return $http.post("/register/createUser", user).then(function(data) {
					console.log("response from backend service: " + JSON.stringify(data));
					return data;
				});
	}
	
});

