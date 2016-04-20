app.controller('loginController', function($scope, $rootScope, $http, $state, $location){
  console.log('inside loginController');
  var self = this;
  $scope.credentials = {};
  // TODO remove credentials
//  $scope.credentials.username = "rainashastri30@gmail.com";
//  $scope.credentials.password = "raina";

  var authenticate = function(credentials, callback) {
    console.log("authenticating!!!!")
    var headers = $scope.credentials ? {authorization : "Basic "
    + btoa($scope.credentials.username + ":" + $scope.credentials.password)
  } : {};

  $http.get('user', {headers : headers}).success(function(data) {
    console.log("Data "+ JSON.stringify(data));
    if (data.userID) {
      console.log("authenticating success!!!! "+ data)
      $rootScope.authenticated = true;
      $rootScope.user = data;
      console.log($rootScope.user);

      $state.go('userDashboard');
      //
    } else {
      console.log("authenticating failed!!!!")
      $rootScope.authenticated = false;
    }
    callback && callback();
  }).error(function() {
    $rootScope.authenticated = false;
    callback && callback();
  });
}

  //authenticate();
  $scope.login = function() {
    console.log('login function')
    authenticate($scope.credentials, function() {
      if ($rootScope.authenticated) {
       console.log("callback true!!!!")
        self.error = false;
      } else {
        console.log("callback false!!!!")
        self.error = true;
      }
    });
  };

  $scope.logout = function() {
    console.log('logging out')
  $http.post('logout', {}).success(function() {
    $rootScope.authenticated = false;
    $rootScope.user = null;
    $location.path("/");
  }).error(function(data) {
    $rootScope.authenticated = false;
  });
}

});