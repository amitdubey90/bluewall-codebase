//app
//		.controller(
//				'logActivityController',
//				function($scope, logActivityService, $filter,$http,limitToFilter) {
//					console.log("In ActivityController");
//
//				
//
//					// for populating typeahead
//					
//					
//					$scope.foods = function(){
//						var op = [];
////						return logActivityService.getFoodList('/user/food/getFoodItems').then(function(response){
////							op=response;
////							//return response;
//						$http({
//					        method: 'GET',
//					        url: '/user/food/getFoodItems'
//					    }).error(function ($data) {
//					        console.log("failed to fetch typeahead data");
//					    }).success(function ($data) {            
//					        $data.objects.forEach(function (person)
//					        {
//					            output.push(person.foodName);
//					        });
//					        console.log(op);        
//					    });
//					    return op;
//					}
//						
//						
//						
//						
//						
//						
//					});
//// $scope.foods = function(){
//// return http.get('/user/food/getFoodItems').then(function(response){
//// return limitToFilter(response.data, 15);
//// });
////						
//// };
//					
//					
//
//					// ON submitting food log
//				});			
//
//app.service('logActivityService', function($http, $state) {
//	console.log("in Activity service");
//	
//	this.getFoodList = function(url) {
//		console.log("Get food list");
//		return $http.get(url).then(function(response) {
//			
//			return response.data;
//		});
//	}
//});
//
