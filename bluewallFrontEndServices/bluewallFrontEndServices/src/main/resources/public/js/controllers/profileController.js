app.controller('profileController', function($scope, profileService,$rootScope) {
	console.log("Inside ProfileController");

	 $scope.showModalProfile = false;
	  $scope.toggleModalProfile = function(){
	      $scope.showModalProfile = !$scope.showModalProfile;
	  };
	  
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

app.directive('modal2', function () {
    return {
      template: '<div class="modal fade">' + 
          '<div class="modal-dialog">' + 
            '<div class="modal-content">' + 
              '<div class="modal-header">' + 
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' + 
                '<h4 class="modal-title">{{ title }}</h4>' + 
              '</div>' + 
              '<div class="modal-body" ng-transclude></div>' + 
            '</div>' + 
          '</div>' + 
        '</div>',
      restrict: 'E',
      transclude: true,
      replace:true,
      scope:true,
      link: function postLink(scope, element, attrs) {
        scope.title = attrs.title;

        scope.$watch(attrs.visible, function(value){
          if(value == true)
            $(element).modal('show');
          else
            $(element).modal('hide');
        });

        $(element).on('shown.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = true;
          });
        });

        $(element).on('hidden.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = false;
          });
        });
      }
    };
  });