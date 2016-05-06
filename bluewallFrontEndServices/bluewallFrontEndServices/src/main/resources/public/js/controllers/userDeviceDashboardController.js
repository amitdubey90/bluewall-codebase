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
											// console.log("FALSE");
											$scope.addDeviceOption = false;
											if (result.data.deviceID == 10) {
												$scope.deviceName = "Fitbit";
											} else {
												$scope.deviceName = "Jawbone";
											}
											$scope.deviceConnectionStatus = "Active";
											// TODO: FETCH THESE VALUES FROM DB
											var currentdate = new Date();
											$scope.deviceConnectionTimeStamp = currentdate
													.getDate()
													+ "/"
													+ (currentdate.getMonth() + 1)
													+ "/"
													+ currentdate.getFullYear()
													+ " @ "
													+ currentdate.getHours()
													+ ":"
													+ currentdate.getMinutes()
													+ ":"
													+ currentdate.getSeconds();
											$scope.deviceSynchTimeStamp = currentdate
													.getDate()
													+ "/"
													+ (currentdate.getMonth() + 1)
													+ "/"
													+ currentdate.getFullYear()
													+ " @ "
													+ currentdate.getHours()
													+ ":"
													+ currentdate.getMinutes()
													+ ":"
													+ currentdate.getSeconds();
										}

									},
									function(error) {
										console
												.log("Error in userDeviceDashboardController")

									});

				});