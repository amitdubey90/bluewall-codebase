app.controller('addDeviceController', function($scope, addDeviceService,$rootScope,$state,messageService) {
	console.log("In addDeviceController");

	addDeviceService.checkIfUserHasDevice().then(
			function(result) {
				//console.log("Back: "+result.data+" "+result.data.deviceID);
				if (result.data != null && (result.data.deviceID == null
						|| result.data.deviceID == 0)) {
					//console.log("TRUE");
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
					
					if(null!=result.data.deviceConnectionTime){
						$scope.deviceConnectionTimeStamp = new Date(result.data.deviceConnectionTime).toLocaleString();
						console.log(result.data.deviceConnectionTime);
						console.log($scope.deviceConnectionTimeStamp);
					}
					else{
						$scope.deviceConnectionTimeStamp = result.data.deviceConnectionTime;
						console.log($scope.deviceConnectionTimeStamp);
					}
					if(null!=result.data.expirationTime){
						$scope.deviceSynchTimeStamp = new Date(result.data.expirationTime).toLocaleString();
						console.log(result.data.expirationTime);
						console.log($scope.deviceSynchTimeStamp);
					}else{
						$scope.deviceSynchTimeStamp = result.data.expirationTime;	
						console.log($scope.deviceSynchTimeStamp);
					}
					messageService.info("Success","Device connected successfully");
					//$state.go($state.current, {}, {reload: true});
				}
				
			}, function(error) {
				messageService.error("Error","Could connect to device");
				//$state.go($state.current, {}, {reload: true});
			});

});

app.service('addDeviceService', function($http) {
	this.checkIfUserHasDevice = function() {
		return $http.get('/user/device/checkIfUserHasDevice').then(
				function(result) {
//					console.log("response from checkIfUserHasDevice: "
//							+ result.data);
					return result;
				});
	}
});
