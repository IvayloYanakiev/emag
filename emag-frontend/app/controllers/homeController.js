var app = angular.module('emag');

app.controller("homeController", function ($rootScope,$scope,$location,sessionService) {

    $scope.toHome = function () {
        sessionService.logout();
        $location.url("/login");
        location.reload();
    };
    $scope.toUserPage = function () {
        $location.url("/userPage/"+sessionService.getSession());
    };
});