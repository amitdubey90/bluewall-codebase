 app.controller('userDashboardController', function($scope,userDashboardService, $rootScope) {
	console.log("In userDashboardController");
	
	 var changeFunct = function(start, end, label) {
		 var d = new Date(start);
	 	 var day = ("0" + d.getDate()).slice(-2);
		 var month = ("0" + (d.getMonth() + 1)).slice(-2);
		 var today = d.getFullYear()+"-"+(month)+"-"+(day);
		    
    	userDashboardService.populateUserCalorieInfo(today).then(function(calorieInfo){
	   		 console.log("Data returned from angular service:calorie info " + calorieInfo);
	   		 var res = calorieInfo.data.split(",");
	   	     $("#calorieBurnt").text(res[0]);
	   	     $("#calorieConsumed").text(res[1]);
	   	     $("#netCalorie").text(res[2]);
	   	     $("#dailyCalorie").text(res[4]);
	   	     myFunction(res[3]);
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

			var protein = (nutrientInfo.data.proteinInCalories/nutrientInfo.data.dailyCalories)*100;
			var carbs =  (nutrientInfo.data.carbInCalories/nutrientInfo.data.dailyCalories)*100;
			var fats =  (nutrientInfo.data.fatInCalories/nutrientInfo.data.dailyCalories)*100;
			 var chart = new CanvasJS.Chart("nutrientChart",
						{
							
							animationEnabled: true,
							legend:{
								verticalAlign: "bottom",
								horizontalAlign: "center"
							},
							width: 600,
							height:300,
							data: [
							{       
								type: "pie",
								showInLegend: true,
								toolTipContent: "{legendText}: <strong>{y}%</strong>",
								indexLabel: "{label} {y}%",
								dataPoints: [
									{  y: protein, legendText: "Proteins", exploded: true, label: "Proteins",color:"#E21A48"},
									{  y: carbs, legendText: "Carbohydrates", label: "Carbohydrates",color:"#1711B5" },
									{  y: fats, legendText: "Fats", label: "Fats",color:"#05A702" },
									
								]
						}
						]
						});
						chart.render();
		},function(error){
			$scope.error = "Unable to load nutrientInfo feed: "+error.statusText;
			console.log(error.statusText);
		});
		 
		 
		 userDashboardService.getFoodRecommendations().then(function(data){
			 $scope.recommendationList = data.data;
			 console.log("Recomendation fetched successfully");
		 },function(error){
				$scope.error = "Unable to load recommendations: "+error.statusText;
				console.log(error.statusText);
			});
		 
		 $scope.userRating = function(foodId, index) {
				
				for (i = 0; i < document.getElementsByName('rating'+index).length; i++) {
					if(document.getElementsByName('rating'+index)[i].checked == true){
						var nameValue = document.getElementsByName('rating'+index)[i].getAttribute("name");
					}
					else{
						continue;
					}
					
				    if (nameValue == 'rating'+index) {
				      var userRating = document.getElementsByName('rating'+index)[i].value;
				      userDashboardService.postUserRating(foodId, userRating).then(function(data) {
						  return data;
					  }, function(error) {
						  console.log("error");
					  });
				      break;
				    }
			  }
		}

});

 app.service('userDashboardService', function($http) {
		
		this.populateUserActivityFeed =  function(){
			return $http.get("/user/activity/recentActivityLog").then(function(activityFeed){
				console.log("Data returned from backend service: acitivity feed"+activityFeed);
				return activityFeed;
			});
		}
	
		this.populateUserCalorieInfo = function(date){
			return $http.get("/calorieDetails/"+date).then(function(calorieInfo){
				console.log("Data returned from backend service: calorie info: "+calorieInfo);
				return calorieInfo;
			});
		}
	
		this.populateUserNutrientInfo= function(){
			
			return $http.get("/user/dailyNutritionPlan").then(function(nutrientInfo){
				console.log("Data returned from backend service: nutrient info: "+nutrientInfo);
				return nutrientInfo;
			});
		}
		
		this.getFoodRecommendations = function(){
			
			return $http.get("/recommendation/get/5").then(function(food){
				console.log("Data returned from backend servie: recommendations service: "+ JSON.stringify(food));
				return food;
			});
		}
	
		this.postUserRating = function(foodId, foodRating) {
			console.log("User Rating: "+ foodId +" " + foodRating);
			return $http.post("/recommendation/rateFood/"+foodId+"/"+foodRating).then(function(data) {
				return data;
			});
		}
	}
);