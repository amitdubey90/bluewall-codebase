app
		.controller(
				'userDeviceDashboardController',
				function($scope, $http, $rootScope, $state, $stateParams,
						addDeviceService, messageService) {
					console.log("In userDeviceDashboardController");
					$scope.addDeviceOption = false;
					$rootScope.authenticated = true;
					if (null != $stateParams.user) {
						$rootScope.user = $stateParams.user;
						console.log("HERE: " + $rootScope.user);

						addDeviceService
								.checkIfUserHasDevice()
								.then(
										function(result) {

											if (result.data != null
													&& (result.data.deviceID == null || result.data.deviceID == 0)) {
												$scope.addDeviceOption = true;
											} else {
												$scope.addDeviceOption = false;
												if (result.data.deviceID == 10) {
													$scope.deviceName = "Fitbit";
												} else {
													$scope.deviceName = "Jawbone";
												}
												$scope.deviceConnectionStatus = "Active";

												if (null != result.data.deviceConnectionTime) {
													$scope.deviceConnectionTimeStamp = new Date(
															result.data.deviceConnectionTime)
															.toLocaleString();
													console
															.log($scope.deviceConnectionTimeStamp);
												} else {
													$scope.deviceConnectionTimeStamp = result.data.deviceConnectionTime;
													console
															.log($scope.deviceConnectionTimeStamp);
												}
												if (null != $scope.deviceSynchTimeStamp) {
													$scope.deviceSynchTimeStamp = new Date(
															result.data.expirationTime)
															.toLocaleString();
													console
															.log($scope.deviceSynchTimeStamp);
												} else {
													$scope.deviceSynchTimeStamp = result.data.expirationTime;
													console
															.log($scope.deviceSynchTimeStamp);
												}
												messageService
														.info("Success",
																"Device connected successfully");
//												$state.go('addFitnessDevice', {
//													reload : true
//												});

											}

										},
										function(error) {
											messageService.error("Error",
													"Could connect to device");
//											$state.go('addFitnessDevice', {}, {
//												reload : true
//											});

										});
					} else {
						messageService
								.error("Error", "Could connect to device");
//						$state.go('addFitnessDevice', {}, {
//							reload : true
//						});

					}

				});