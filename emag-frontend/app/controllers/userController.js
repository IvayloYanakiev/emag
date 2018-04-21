var app = angular.module('emag');

app.controller("userController", function ($scope, $location, $routeParams, $http,sessionService,$rootScope) {

    var userId = $routeParams.userId;

    function a() {
        $http({
            url: "http://localhost:7377/login" + "/getUserPageById",
            method: "GET",
            params: {
                "id": userId
            }
        }).then(function (response) {
            $scope.user = response.data.object.user;
        });
    }
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
    a();


});