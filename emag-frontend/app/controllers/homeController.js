var app = angular.module('emag');

app.controller("homeController", function ($scope, $location, $routeParams, $http, shoppingCart) {

    $http({
        url: "http://localhost:7377/product" + "/getAllProducts",
        method: "GET"
    }).then(function (response) {
        $scope.products = response.data;
    }, function (error) {

    });

    $scope.addToCart = function(productId){
        shoppingCart.addEntry(productId);
    }
});