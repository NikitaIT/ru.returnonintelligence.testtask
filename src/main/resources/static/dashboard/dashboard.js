'use strict';

angular.module('myApp.dashboard', ['ngRoute'])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {
    templateUrl: 'dashboard/dashboard.html',
    controller: DashboardCtrl,
		resolve: DashboardCtrl.resolve
  });
}]);

function DashboardCtrl($scope, $rootScope, $http, authService, isAuthenticated) {
	$rootScope.authenticated = isAuthenticated;

	$scope.serverResponse = '';
	$scope.responseBoxClass = '';

	var setResponse = function(res, success) {
		$rootScope.authenticated = isAuthenticated;
		if (success) {
			$scope.responseBoxClass = 'alert-success';
		} else {
			$scope.responseBoxClass = 'alert-danger';
		}
		$scope.serverResponse = res;
		$scope.serverResponse.data = JSON.stringify(res.data, null, 2);
	}

	if ($rootScope.authenticated) {
		authService.getUser()
		.then(function(response) {
			$scope.user = response.data;
		});
	}

	$scope.getUserInfo = function() {
		authService.getUser()
		.then(function(response) {
			setResponse(response, true);
		})
		.catch(function(response) {
			setResponse(response, false);
		});
	}

	$scope.getAllUserInfo = function() {
		$http.get('user/all')
		.then(function(response) {
			setResponse(response, true);
		})
		.catch(function(response) {
			setResponse(response, false);
		});
	}

    // void saveUser(User user);
    // void updateUser(User user);
    // void deleteUserById(long id);
    // void deleteAllUsers();
    // public boolean isUserExist(User user);
    $scope.getByUsernameContaining = function() {
        $http.get('user?username=se')
            .then(function(response) {
                setResponse(response, true);
            })
            .catch(function(response) {
                setResponse(response, false);
            });
    }
    $scope.getByEmail = function() {
        $http.get('user?email=user@user.com')
            .then(function(response) {
                setResponse(response, true);
            })
            .catch(function(response) {
                setResponse(response, false);
            });
    }
    $scope.getAllByBirthday = function() {
        $http.get('user?birthday=1997-09-20')
            .then(function(response) {
                setResponse(response, true);
            })
            .catch(function(response) {
                setResponse(response, false);
            });
    }
    $scope.getById = function() {
        $http.get('user/1')
            .then(function(response) {
                setResponse(response, true);
            })
            .catch(function(response) {
                setResponse(response, false);
            });
    }

    $scope.getUserInfoO = function() {
        authService.getUser()
            .then(function(response) {
                setResponse(response, true);
            })
            .catch(function(response) {
                setResponse(response, false);
            });
    }

    $scope.getAllUserInfoO = function() {
        $http.get('open/user/all')
            .then(function(response) {
                setResponse(response, true);
            })
            .catch(function(response) {
                setResponse(response, false);
            });
    }

    // void saveUser(User user);
    // void updateUser(User user);
    // void deleteUserById(long id);
    // void deleteAllUsers();
    // public boolean isUserExist(User user);
    $scope.getByUsernameContainingO = function() {
        $http.get('open/user?username=se')
            .then(function(response) {
                setResponse(response, true);
            })
            .catch(function(response) {
                setResponse(response, false);
            });
    }
    $scope.getByEmailO = function() {
        $http.get('open/user?email=user@user.com')
            .then(function(response) {
                setResponse(response, true);
            })
            .catch(function(response) {
                setResponse(response, false);
            });
    }
    $scope.getAllByBirthdayO = function() {
        $http.get('open/user?birthday=1997-09-20')
            .then(function(response) {
                setResponse(response, true);
            })
            .catch(function(response) {
                setResponse(response, false);
            });
    }
}
DashboardCtrl.resolve = {
	isAuthenticated : function($q, $http) {
		var deferred = $q.defer();
		$http({method: 'GET', url: 'auth/refresh'})
		.success(function(data) {
			deferred.resolve(data.access_token !== null);
		})
		.error(function(data){
			deferred.resolve(false); // you could optionally pass error data here
		});
		return deferred.promise;
	}
};

DashboardCtrl.$inject = ['$scope', '$rootScope', '$http', 'AuthService', 'isAuthenticated'];

