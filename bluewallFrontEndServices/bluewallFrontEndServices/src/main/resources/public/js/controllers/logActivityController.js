app.controller('logActivityController',	function($scope, $filter, $http, logActivityService, $rootScope, $state, limitToFilter,messageService) {
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
			caloriesBurnt : activity.caloriesBurnt,
			activityLogDate : new Date(moment(activity.activityLogDate).format())
		};
		logActivityService.logUserActivity(userActivity).then(function(data) {
				console.log(data);
				if(data.data){
					messageService.info("Success","Activity Logged");
					$state.go($state.current, {}, {reload: true});
				}else{
					messageService.error("Error","Could not log activity");
					$state.go($state.current, {}, {reload: true});
				}
		}, function(error) {
					messageService.error("Error","Could not log activity");
					$state.go($state.current, {}, {reload: true});
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
		 if(null!=activityLogged.data){
			 $scope.activityLoggedList = activityLogged.data;
			 console.log("ACTIVITY HERE: "+JSON.stringify(activityLogged.data));
		 }else{
			 messageService.error("Error","Could not fetch activity logs");
			 $state.go($state.current, {}, {reload: true});
		 }
	},function(error){
		messageService.error("Error","Could not fetch activity logs");
		$state.go($state.current, {}, {reload: true});
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