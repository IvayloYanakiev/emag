var app = angular.module('emag');

app.controller("loginController", function ($rootScope, $scope, $location, $http, sessionService) {
    $scope.error = false;

    $scope.user = {email: "", password: ""};


    $scope.loginU = function () {

        $scope.error = false;

        $http({
            url: "http://localhost:7377/login" + "/user",
            method: "POST",
            params: {
                "email": $scope.user.email,
                "password": $scope.user.password
            }
        }).then(function (response) {

            if(response.data.status=="BAD_REQUEST") {
                $scope.error = true;
                $scope.value = response.data.object;
            }
            else {
                var userId = JSON.parse(response.data.object).id;
                sessionService.login(userId);
                $rootScope.isAuthenticated = sessionService.isLoggedIn();
                $location.url("/userPage");
            }
        });

    }

});