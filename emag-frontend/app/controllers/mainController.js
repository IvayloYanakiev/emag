var app = angular.module('emag');

app.controller("mainController", function ($scope, $location, $routeParams, $http, sessionService, $rootScope) {

    $http({
        url: "http://localhost:7377/category" + "/getAllCategories",
        method: "GET"
    }).then(function (response) {
        $scope.categories = JSON.parse(response.data.object);
    });
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
});