app.controller('logFoodController', function($scope,logFoodService,$filter) {
	console.log("In logFoodController");

	$scope.userWt= {
		max:5
	};
	
	$scope.userCal= {
			max:500
		};
	
	$scope.logFood = function(){
		console.log("logging");
		$scope.food.calories = $scope.userCal.max;
		$scope.food.weightConsumed = $scope.userWt.max;
		var result = $filter('date')($scope.food.timeConsumed, 'yyyy-MM-ddTHH:mm:ss');
		$scope.food.timeConsumed = result;
		var f=$scope.food;
		logFoodService.logFood($scope.food).then(function(data){
			console.log("return");
		},function(error){
			console.log("error");
		});
		
		
}
});




app.service('logFoodService', function($http,$state) {
console.log("in log food service");
	this.logFood = function(food){
		console.log(food.name+" "+food.calories+" "+food.timeConsumed+" "+food.weightConsumed+" "+food.type);
		 return $http.post("/user/food/createFoodPlate",food).then(function(data){
			 console.log("response from backend service: "+data);
			 return data;
		 });
		
		
		
	}
});