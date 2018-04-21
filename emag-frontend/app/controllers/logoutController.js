var app = angular.module('emag');

app.controller("logoutController", function ($rootScope,$scope,$location,sessionService) {

    var logoutUser = function () {
        sessionService.logout();
        $location.url("/login");
        location.reload();
    };
    logoutUser();

});