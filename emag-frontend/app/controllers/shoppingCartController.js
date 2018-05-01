var app = angular.module('emag');

app.controller("shoppingCartController", function ($scope, $location, $routeParams, $http, sessionService, $rootScope,shoppingCart) {

   var entries = shoppingCart.getEntries();
    $http({
        url: "http://localhost:7377/product" + "/getProductsFromShoppingCart",
        method: "GET",
        params:{"products":entries}
    }).then(function (response) {
        $scope.products = response.data;
    }, function (error) {

    });
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
});