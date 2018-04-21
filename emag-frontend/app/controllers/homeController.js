var app = angular.module('emag');

app.controller("homeController", function ($rootScope,$scope,$location,sessionService) {

    $scope.toHome = function () {
        $location.url("/login");
    };
    $scope.toUserPage = function () {
        $location.url("/userPage");
    };
});