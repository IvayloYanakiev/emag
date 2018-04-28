var app = angular.module('emag');

app.controller("shoppingCartController", function ($scope, $location, $routeParams, $http, sessionService, $rootScope) {

    $http({
        url: "http://localhost:7377/category" + "/getProductsInCart",
        method: "GET"
    }).then(function (response) {
        $scope.productsInCart = JSON.parse(response.data.object);
    });
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
});