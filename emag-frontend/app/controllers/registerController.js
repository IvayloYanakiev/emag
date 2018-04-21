var app = angular.module('emag');

app.controller("registerController", function ($scope, $location, $http) {
    $scope.success =false;
    $scope.error =false;
    $scope.value = "";
    $scope.user = {name: "", email: "", password: "", confirmPassword: ""};
    $scope.register = function () {

        $scope.error =false;
        $scope.success = false;

        $http({
            url: "http://localhost:7377/register/" + "/createAccount",
            method: "POST",
            params: {
                "name": $scope.user.name,
                "email": $scope.user.email,
                "password": $scope.user.password,
                "confirmPassword": $scope.user.confirmPassword
            }
        }).then(function (response) {

            if(response.data.status=="BAD_REQUEST"){
                $scope.error=true;
                $scope.value = response.data.object;
            }
            else {
                $scope.success=true;
                $scope.value = response.data.object;
                $scope.user = {name: "", email: "", password: "", confirmPassword: ""};
            }
        });

    }

});