app.controller('editProfileController', function($scope, $state, editProfileService,$rootScope, messageService) {
	console.log("Inside editProfileService");

	$scope.editUserProfile = function(userProfile){
		console.log("Edit UserProfile: " + JSON.stringify(userProfile));
		
		editProfileService.editUserProfile(userProfile).then(function(userProfileResponse){
			 console.log("Data returned from angular service: edit user profile response");
			 if(null!=userProfileResponse.data){
				 messageService.info("Success","User Profile Updated");				
				 $state.go($state.current, {}, {reload: true});
			 }else{
				 messageService.error("Error","Could not edit user profile details");
				 $state.go($state.current, {}, {reload: true});
			 }
		},function(error){
			messageService.error("Error","Could not edit user profile details");
			$state.go($state.current, {}, {reload: true});
			console.log(error.statusText);
		});
	}
});

app.service('editProfileService', function($http) {
	this.editUserProfile = function(userProfile) {
		return $http.put('/user/updateProfile', userProfile).then(function(result) {
			return result;
		});
	}
});
