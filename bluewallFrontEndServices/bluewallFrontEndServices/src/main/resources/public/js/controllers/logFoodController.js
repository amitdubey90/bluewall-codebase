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

});

app.service('logFoodService', function($http, $state) {
	console.log("in log food service");
	this.logFood = function(food) {
		console.log(food.name + " " + food.calories + " " + food.foodLogTime
				+ " " + food.weightConsumed + " " + food.type);
		return $http.post("/user/food/createFoodPlate", food).then(
				function(data) {
					console.log("response from backend service: " + data);
					return data;
				});
	}
});