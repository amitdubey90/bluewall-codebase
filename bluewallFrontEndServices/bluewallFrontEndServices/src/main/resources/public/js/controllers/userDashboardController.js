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
			 var userActivityFeed = [];
			 userActivityFeed.push(activityFeed.data[0]);
			 userActivityFeed.push(activityFeed.data[1]);
			 userActivityFeed.push(activityFeed.data[2]);
			 userActivityFeed.push(activityFeed.data[3]);
			 userActivityFeed.push(activityFeed.data[4]);
			 userActivityFeed.push(activityFeed.data[5]);
			 $scope.activitiesList = userActivityFeed;
			 console.log("1: "+activityFeed.data[0]);
			 console.log("2: "+activityFeed.data[1]);
			 console.log("3: "+activityFeed.data[2]);
			 console.log("4: "+activityFeed.data[3]);
			 console.log("5: "+activityFeed.data[4]);
			 console.log("5: "+activityFeed.data[5]);
			 
		},function(error){
			$scope.error = "Unable to load activity feed: "+error.statusText;
			console.log(error.statusText);
		});
		 
		 userDashboardService.populateUserNutrientInfo().then(function(nutrientInfo){
			 console.log("Data returned from angular service:nutrient info ");
			 $scope.nutrientDetails = nutrientInfo.data;

			protein = (nutrientInfo.data.proteintConsumed/nutrientInfo.data.proteinInGms)*100;
			carbs =  (nutrientInfo.data.carbsConsumed/nutrientInfo.data.carbInGms)*100;
			fat =  (nutrientInfo.data.fatConsumed/nutrientInfo.data.fatInGms)*100;

			protein = protein > 100 ? 100: Math.round(protein);
			carbs = carbs > 100 ? 100: Math.round(carbs);
			fat = fat > 100 ? 100: Math.round(fat);
			var chart;
			var chartingOptions = {

			    chart: {
			        type: 'solidgauge',
			        marginTop: 50
			    },

			    title: {
			        text: '',
			        style: {
			            fontSize: '24px'
			        }
			    },

			    tooltip: {
			        borderWidth: 0,
			        backgroundColor: 'none',
			        shadow: false,
			        style: {
			            fontSize: '16px'
			        },
			        pointFormat: '{series.name}<br><span style="font-size:2em; color:{point.color}; font-weight: bold">{point.y}%</span>',
			        positioner: function (labelWidth, labelHeight, point) {
			            var tooltipX, tooltipY;
			            
			            tooltipX = point.plotX + chart.plotLeft - 40;
			            tooltipY = point.plotY + chart.plotTop - 175;
			            
			            return {
			                x: tooltipX,
			                y: tooltipY
			            };
			        }
			    },

			    pane: {
			        startAngle: 0,
			        endAngle: 360,
			        background: [{ // Track for Protein
			            outerRadius: '112%',
			            innerRadius: '88%',
			            backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0.3).get(),
			            borderWidth: 0
			        }, { // Track for Carbs
			            outerRadius: '87%',
			            innerRadius: '63%',
			            backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[1]).setOpacity(0.3).get(),
			            borderWidth: 0
			        }, { // Track for Fat
			            outerRadius: '62%',
			            innerRadius: '38%',
			            backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[2]).setOpacity(0.3).get(),
			            borderWidth: 0
			        }]
			    },

			    yAxis: {
			        min: 0,
			        max: 100,
			        lineWidth: 0,
			        tickPositions: []
			    },

			    plotOptions: {
			        solidgauge: {
			            borderWidth: '30px',
			            dataLabels: {
			                enabled: false
			            },
			            linecap: 'round',
			            stickyTracking: false
			        }
			    },

			    series: [{
			        name: 'Proteins',
			        borderColor: Highcharts.getOptions().colors[0],
			        data: [{
			            color: Highcharts.getOptions().colors[0],
			            radius: '100%',
			            innerRadius: '100%',
			            y: protein
			        }]
			    }, {
			        name: 'Carbs',
			        borderColor: Highcharts.getOptions().colors[1],
			        data: [{
			            color: Highcharts.getOptions().colors[1],
			            radius: '75%',
			            innerRadius: '75%',
			            y: carbs
			        }]
			    }, {
			        name: 'Fats',
			        borderColor: Highcharts.getOptions().colors[2],
			        data: [{
			            color: Highcharts.getOptions().colors[2],
			            radius: '50%',
			            innerRadius: '50%',
			            y: fat
			        }]
			    }]
			};
 			chart = $('#nutrientChart').highcharts(chartingOptions).highcharts();
		},function(error){
			$scope.error = "Unable to load nutrientInfo feed: "+error.statusText;
			console.log(error.statusText);
		});
		 
		 userDashboardService.getFoodRecommendations().then(function(data){
			// $scope.recommendationList = data.data;
			 $scope.myInterval = 6000000;
			 $scope.recommendationList = [];
			 if(data.data){
				 for(i=0;i<data.data.length;i++){
					 var food= {};
					 food.foodId = data.data[i].foodId;
					 food.foodName = data.data[i].foodName;
					 food.foodCalorie = data.data[i].foodCalorie;
					 food.image = data.data[i].imageUrl;
					 $scope.recommendationList.push(food);
				 }
			 }
			 console.log($scope.recommendationList);
			 console.log("RECOMMENDED: "+JSON.stringify(data.data));
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
			
			return $http.get("/recommendation/get/10").then(function(food){
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