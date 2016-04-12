app.controller('logFoodController', function($scope, logFoodService, $filter,$rootScope,$state) {
	console.log("In logFoodController");

	$scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    
	$scope.userWt = {
		max : 5
	};

	$scope.userCal = {
		max : 500
	};

	$scope.logFood = function(food) {
		var name = document.getElementById("foodName").value;
		food.name = name.split(":")[1].trim();
		food.calories = document.getElementById("foodCalories").value;
//		var foodObj = angular.copy(food);
//		console.log(foodObj + " " + food);
		logFoodService.logFood(food).then(function(data) {
			$rootScope.authenticated = true;
			$state.go('userDashboard');
		}, function(error) {
			console.log("error");
		});
	}

	logFoodService.getFoodLogged().then(function(foodLogged){
		 console.log("Data returned from angular service:food Logged");
		 $scope.foodLoggedList = foodLogged.data;
	},function(error){
		$scope.error = "Unable to load food logged: "+error.statusText;
		console.log(error.statusText);
	});
	
});

app.directive('modal', function () {
    return {
      template: '<div class="modal fade">' + 
          '<div class="modal-dialog" style="width: 75%;">' + 
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

app.service('logFoodService', function($http, $state) {
	console.log("in log food service");
	
	this.logFood = function(food) {
		console.log(food.name + " " + food.calories + " " + food.foodLogTime
				+ " " + food.weightConsumed + " " + food.type);
		return $http.post("/user/food/createFoodPlate", food).then(
				function(data) {
					console.log("response from backend service: " + data);
					return data;
				});
	}
	
	this.getFoodLogged =  function(){
		return $http.get("/user/food/foodLog/1").then(function(foodLogged){
			console.log("Data returned from backend service: acitivity feed"+foodLogged);
			return foodLogged;
		});
	}
});