app.controller('logActivityController',	function($scope, $filter, $http, logActivityService, $rootScope, $state, limitToFilter) {
	console.log("In ActivityController");
	

	$scope.logActivity = function(activity) {

		activity.type = document.getElementById('activityType').options[document
				.getElementById('activityType').selectedIndex].text;
		if (activity.type !== 'Other') {
			activity.name = activity.type;
		}
		var userActivity = {
			name : activity.name,
			duration : parseInt((activity.hours * 60))
					+ parseInt(activity.mins),
			//distance : activity.distance,
			caloriesBurnt : activity.caloriesBurnt,
			activityLogDate : activity.activityLogDate
		};
		logActivityService.logUserActivity(userActivity).then(
				function(data) {
					$rootScope.authenticated = true;
					$state.go('userDashboard');
				}, function(error) {
					console.log("error");
				});
	}

	$scope.change = function() {
		var hours = parseInt($('#hours :selected').text() * 60);
		if (isNaN(hours)) {
			hours = 0;
		}
		var minutes = parseInt($('#min :selected').text());
		if (isNaN(minutes)) {
			minutes = 0;
		}
		var met = $('#activityType :selected').val();
		if (met !== 'Other') {
			var time = hours + minutes;
			var calExp = 0.0175 * met * $rootScope.user.weight * time;
			$scope.activity.caloriesBurnt = calExp.toFixed(2);
		}

	}
	
	logActivityService.getActivityLogged().then(function(activityLogged){
		 console.log("Data returned from angular service:activity Logged");
		 $scope.activityLoggedList = activityLogged.data;
	},function(error){
		$scope.error = "Unable to load food logged: "+error.statusText;
		console.log(error.statusText);
	});
});

app.service('logActivityService', function($http, $state) {
	this.logUserActivity = function(activity) {
		return $http.post("/user/activity/createActivity", activity).then(function(data) {
					console.log("response from backend service: " + data);
					return data;
		});
	}
	
	this.getActivityLogged =  function(){
		return $http.get("/user/activity/activityLog").then(function(activityLogged){
			console.log("Data returned from backend service: acitivity logs: "+activityLogged);
			return activityLogged;
		});
	}
});