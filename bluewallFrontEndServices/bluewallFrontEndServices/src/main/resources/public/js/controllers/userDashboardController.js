app.controller('userDashboardController', function($scope,userDashboardService) {
	console.log("In userDashboardController");
	
	 userDashboardService.populateUserActivityFeed().then(function(activityFeed){
		 console.log("Data returned from angular service:activity feed");
		 $scope.activitiesList = activityFeed.data;
	},function(error){
		$scope.error = "Unable to load activity feed: "+error.statusText;
		console.log(error.statusText);
	});
	
	 $(function() {
		    $('input[name="birthdate"]').daterangepicker({
		        singleDatePicker: true,
		        showDropdowns: true
		    },
		    function(start, end, label) {
		    	userDashboardService.populateUserCalorieInfo(start.format('YYYY-MM-DD')).then(function(calorieInfo){
		   		 console.log("Data returned from angular service:calorie info " + calorieInfo);
		   		 console.log(calorieInfo.data);
		   		 var res = calorieInfo.data.split(",");
		   		console.log(res[0] + " " + res[1]);
		   		$scope.caloriesBurnt = res[0];
		   		$scope.caloriesConsumed = res[1];
		   		$scope.calorieInfo = calorieInfo.data;
		   		 myFunction(10);
		   		
			   	},function(error){
			   		$scope.error = "Unable to load calorieInfo feed: "+error.statusText;
			   		console.log(error.statusText);
			   	});
		    });
		});
	 
	/* userDashboardService.populateUserCalorieInfo().then(function(calorieInfo){
		 console.log("Data returned from angular service:calorie info ");
		 console.log(calorieInfo.data);
		 $scope.calorieDetails = {};
		 $scope.calorieDetails.sumCalorieBurnt = 10;
		 myFunction(10);
		
	},function(error){
		$scope.error = "Unable to load calorieInfo feed: "+error.statusText;
		console.log(error.statusText);
	});*/
	 
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
	
		this.populateUserCalorieInfo = function(date){
			alert("Date: " + date);
			return $http.get("/calorieDetails/13/"+date).then(function(calorieInfo){
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