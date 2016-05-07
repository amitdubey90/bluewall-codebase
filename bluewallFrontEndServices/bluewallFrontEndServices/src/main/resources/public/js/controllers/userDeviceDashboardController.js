app
		.controller(
				'userDeviceDashboardController',
				function($scope, $http, $rootScope, $state, $stateParams,
						addDeviceService) {
					console.log("In userDeviceDashboardController");

					$scope.addDeviceOption = false;
					$rootScope.authenticated = true;
					$rootScope.user = $stateParams.user;
					console.log("HERE: " + $rootScope.user);

					addDeviceService
							.checkIfUserHasDevice()
							.then(
									function(result) {
										// console.log("Back: "+result.data+"
										// "+result.data.deviceID);
										if (result.data != null
												&& (result.data.deviceID == null || result.data.deviceID == 0)) {
											// console.log("TRUE");
											$scope.addDeviceOption = true;
										} else {
											//console.log("FALSE");
											$scope.addDeviceOption = false;
											if(result.data.deviceID == 10){
												$scope.deviceName = "Fitbit";
											}else{
												$scope.deviceName = "Jawbone";
											}
											$scope.deviceConnectionStatus = "Active";
												//TODO: FETCH THESE VALUES FROM DB
											
											if(null!=result.data.deviceConnectionTime){
												$scope.deviceConnectionTimeStamp = new Date(result.data.deviceConnectionTime).toLocaleString();
												console.log($scope.deviceConnectionTimeStamp);
											}
											else{
												$scope.deviceConnectionTimeStamp = result.data.deviceConnectionTime;
												console.log($scope.deviceConnectionTimeStamp);
											}
											if(null!=$scope.deviceSynchTimeStamp){
												$scope.deviceSynchTimeStamp = new Date(result.data.expirationTime).toLocaleString();
												console.log($scope.deviceSynchTimeStamp);
											}else{
												$scope.deviceSynchTimeStamp = result.data.expirationTime;	
												console.log($scope.deviceSynchTimeStamp);
											}
											
										}

									},
									function(error) {
										console
												.log("Error in userDeviceDashboardController")

									});

				});