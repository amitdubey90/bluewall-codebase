 app.controller('userDashboardController', function($scope,userDashboardService) {
	console.log("In userDashboardController");
	
	 var changeFunct = function(start, end, label) {
		 var d = new Date(start);
	 	 var day = ("0" + d.getDate()).slice(-2);
		 var month = ("0" + (d.getMonth() + 1)).slice(-2);
		 var today = d.getFullYear()+"-"+(month)+"-"+(day);
		    
    	userDashboardService.populateUserCalorieInfo(today).then(function(calorieInfo){
	   		 console.log("Data returned from angular service:calorie info " + calorieInfo);
	   		 console.log(calorieInfo.data);
	   		 var res = calorieInfo.data.split(",");
	   	     $("#calorieBurnt").text(res[0]);
	   	     $("#calorieConsumed").text(res[1]);
	   	     myFunction(10);
	   		 countNumber();
		   	},function(error){
		   		$scope.error = "Unable to load calorieInfo feed: "+error.statusText;
		   		console.log(error.statusText);
		   	});
	    };
	    
	    var initDatepicker = function() {
		    $('input[name="birthdate"]').daterangepicker({
		        singleDatePicker: true,
		        showDropdowns: true
		    },
		    changeFunct
		    );
		};
	 
	    var now = new Date();
	 
	    var day = ("0" + now.getDate()).slice(-2);
	    var month = ("0" + (now.getMonth() + 1)).slice(-2);
	
	    var today = now.getFullYear()+"-"+(month)+"-"+(day);
	
	    $('#datePicker').val(today);
	    initDatepicker();
	
		changeFunct(new Date().getTime(),0,0);
		
	    userDashboardService.populateUserActivityFeed().then(function(activityFeed){
			 console.log("Data returned from angular service:activity feed");
			 $scope.activitiesList = activityFeed.data;
		},function(error){
			$scope.error = "Unable to load activity feed: "+error.statusText;
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
	
		this.populateUserCalorieInfo = function(date){
			return $http.get("/calorieDetails/1/"+date).then(function(calorieInfo){
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