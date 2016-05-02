app.controller('logFoodController', function($scope, logFoodService, $filter,$rootScope,$state) {
	console.log("In logFoodController");

	$scope.userWt = {
		max : 5
	};

	$scope.userCal = {
		max : 500
	};

	$scope.logFood = function(food) {
		var name = document.getElementById("foodName").value;
		food.name = name.split(":")[1].trim();
		food.calories = document.getElementById("foodCalories").value;
//		var foodObj = angular.copy(food);
//		console.log(foodObj + " " + food);
		logFoodService.logFood(food).then(function(data) {
			$rootScope.authenticated = true;
			$state.go('userDashboard');
		}, function(error) {
			console.log("error");
		});
	}

	logFoodService.getFoodLogged().then(function(foodLogged){
		 console.log("Data returned from angular service:food Logged");
		 $scope.foodLoggedList = foodLogged.data;
	},function(error){
		$scope.error = "Unable to load food logged: "+error.statusText;
		console.log(error.statusText);
	});
	
	$scope.postToController = function() {
	  alert("Inside Food Controller");
	}
	
});

app.service('logFoodService', function($http, $state) {
	console.log("in log food service");
	
	this.logFood = function(food) {
		console.log(food.name + " " + food.calories + " " + food.foodLogDate
				+ " " + food.weightConsumed + " " + food.type);
		return $http.post("/user/food/createFoodPlate", food).then(
				function(data) {
					console.log("response from backend service: " + data);
					return data;
				});
	}
	
	this.getFoodLogged =  function(){
		return $http.get("/user/food/foodLog").then(function(foodLogged){
			console.log("Data returned from backend service: food logs: "+foodLogged);
			return foodLogged;
		});
	}
});