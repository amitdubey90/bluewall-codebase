app.controller('logActivityController', function($scope, $filter, $http,
		logActivityService, $rootScope, $state, limitToFilter) {
	console.log("In ActivityController");

	// $scope.activitesType = function(){
	// var act = [{name:"Running", met:34},{name:"Sleeping",met:45}]
	// alert(act);
	// return act;
	// }

	$scope.activitiesType = [ {
		name : 'Running',
		met : 34
	}, {
		name : 'Sleeping',
		value : 44
	}, {
		name : 'Walking',
		value : 33
	} ];

	$scope.logActivity = function(activity) {

		
		activity.type = document.getElementById('activityType').options[document.getElementById('activityType').selectedIndex].text;
		if(activity.type !== 'Other'){
			activity.name = activity.type;
		}
		console.log(activity);
//						logActivityService.logUserActivity(activity).then(
//								function(data) {
//									$rootScope.authenticated = true;
//									$state.go('userDashboard');
//								}, function(error) {
//									console.log("error");
//								});
	}
	
	$scope.change = function() {
		console.log('from change')
		var hours = parseInt($('#hours :selected').text()*60);
		if(isNaN(hours)){
			hours=0;
		}
		var minutes = parseInt($('#min :selected').text());
		if(isNaN(minutes)){
			minutes=0;
		}
		var time =  hours+minutes;
		var met = $('#activityType :selected').val();
		var calExp = 0.0175*met*30*time;
		$scope.activity.caloriesBurnt = calExp.toFixed(2);
	}
});

app.service('logActivityService', function($http, $state) {
	this.logUserActivity = function(activity) {
		return $http.post("/user/activity/createActivity", activity).then(
				function(data) {
					console.log("response from backend service: " + data);
					return data;
				});
	}
});