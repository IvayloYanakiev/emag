var app = angular.module('9gagApp');

app.controller("loginController", function ($rootScope, $scope, $location, $http, sessionService) {
    $scope.error = false;

    $scope.user = {email: "", password: ""};


    $scope.loginU = function () {

        $scope.error = false;

        $http({
            url: "http://localhost:7377/login" + "/loginCheckUser",
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
                sessionService.login();
                $location.url("/userPage/"+JSON.parse(response.data.object).id);
            }
        });

    }

});