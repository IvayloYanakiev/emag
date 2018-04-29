var app = angular.module('emag');

app.controller("shoppingCartController", function ($scope, $location, $routeParams, $http, sessionService, $rootScope) {

    $http({
        url: "http://localhost:7377/category" + "/getProductsInCart",
        method: "GET"
    }).then(function (response) {
        $scope.productsInCart = response.data;
    });
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
});