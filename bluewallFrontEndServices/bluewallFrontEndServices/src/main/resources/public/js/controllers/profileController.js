app.controller('profileController', function($scope, profileService,$rootScope) {
	console.log("Inside ProfileController");

	profileService.getUserProfile().then(function(userProfile){
		 console.log("Data returned from angular service: user profile");
		 if(null!=userProfile.data){
			 console.log(JSON.stringify("user profile: "+userProfile.data));
			 $scope.userProfile = userProfile.data;
		 }else{
			 messageService.error("Error","Could not fetch user profile details");
			 $state.go($state.current, {}, {reload: true});
		 }
	},function(error){
		messageService.error("Error","Could not fetch user profile details");
		$state.go($state.current, {}, {reload: true});
		console.log(error.statusText);
	});

});

app.service('profileService', function($http) {
	this.getUserProfile = function() {
		return $http.get('/user/viewprofile').then(function(result) {
			return result;
		});
	}
});
