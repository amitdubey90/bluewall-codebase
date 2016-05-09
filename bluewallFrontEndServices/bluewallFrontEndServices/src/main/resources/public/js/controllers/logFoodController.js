app.controller('logFoodController', function($scope, logFoodService, $filter,$rootScope,$state,messageService) {
	console.log("In logFoodController");

	$scope.logFood = function(food) {
		var name = document.getElementById("foodName").value;
		food.name = name.split(":")[1].trim();
		food.calories = document.getElementById("foodCalories").value;
		food.foodLogDate = new Date(moment(food.foodLogDate).format());
		logFoodService.logFood(food).then(function(data) {
			console.log(data);
			if(data.data){
				messageService.info("Success","Food Logged");
				$state.go($state.current, {}, {reload: true});
			}else{
				messageService.error("Error","Could not log food");
				$state.go($state.current, {}, {reload: true});
			}
		}, function(error) {
			messageService.error("Error","Could not log food");
			$state.go($state.current, {}, {reload: true});
		});
	}

	logFoodService.getFoodLogged().then(function(foodLogged){
		 console.log("Data returned from angular service:food Logged");
		 if(null!=foodLogged.data){
			 $scope.foodLoggedList = foodLogged.data;
		 }else{
			 messageService.error("Error","Could not fetch food logs");
			 $state.go($state.current, {}, {reload: true});
		 }
	},function(error){
		messageService.error("Error","Could not fetch food logs");
		$state.go($state.current, {}, {reload: true});
		console.log(error.statusText);
	});
	
});

app.service('logFoodService', function($http, $state) {
	console.log("in log food service");
	
	this.logFood = function(food) {
		console.log(food.name + " " + food.calories + " " + food.foodLogDate
				+ " " + food.weightConsumed + " " + food.type);
		return $http.post("/user/food/createFoodPlate", food).then(
				function(data) {
					console.log("response from backend service: create food log: " + data);
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