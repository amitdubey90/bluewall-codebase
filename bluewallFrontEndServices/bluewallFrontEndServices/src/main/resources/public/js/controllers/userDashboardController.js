app.controller('userDashboardController', function($scope,userDashboardService) {
	console.log("In userDashboardController");
	
	 userDashboardService.populateUserActivityFeed().then(function(activityFeed){
		 console.log("Data returned from angular service:activity feed");
		 $scope.activitiesList = activityFeed.data;
	},function(error){
		$scope.error = "Unable to load activity feed: "+error.statusText;
		console.log(error.statusText);
	});
	 
	 userDashboardService.populateUserCalorieInfo($scope.currentData).then(function(calorieInfo){
		 console.log("Data returned from angular service:calorie info ");
		 console.log(calorieInfo.data);
		 $scope.calorieDetails = {};
		 $scope.calorieDetails.sumCalorieBurnt = 10;
		 myFunction(10);
		
	},function(error){
		$scope.error = "Unable to load calorieInfo feed: "+error.statusText;
		console.log(error.statusText);
	});
	 
	 userDashboardService.populateUserNutrientInfo().then(function(nutrientInfo){
		 console.log("Data returned from angular service:nutrient info ");
		 $scope.nutrientDetails = nutrientInfo.data;
	},function(error){
		$scope.error = "Unable to load nutrientInfo feed: "+error.statusText;
		console.log(error.statusText);
	});
});

app.service('userDashboardService', function($http) {
	
		this.populateUserActivityFeed =  function(){
			return $http.get("/user/activity/getRecentActivity").then(function(activityFeed){
				console.log("Data returned from backend service: acitivity feed"+activityFeed);
				return activityFeed;
			});
		}
	
		this.populateUserCalorieInfo = function(){
			//var num = $('#currentDate').text();
			return $http.get("/calorieDetails/1").then(function(calorieInfo){
				console.log("Data returned from backend service: calorie info: "+calorieInfo);
				return calorieInfo;
			});
		}
	
		this.populateUserNutrientInfo= function(){
			
			return $http.get("").then(function(nutrientInfo){
				console.log("Data returned from backend service: nutrient info: "+nutrientInfo);
				return nutrientInfo;
			});
		}
	
	}
);