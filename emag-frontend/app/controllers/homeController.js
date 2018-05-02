var app = angular.module('emag');

app.controller("homeController", function ($scope, $location, $routeParams, $http, shoppingCart) {

    var getProducts = function () {
        $http({
            url: "http://localhost:7377/product" + "/getAllProducts",
            method: "GET"
        }).then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };
    getProducts();

    $scope.addToCart = function (productId) {
        shoppingCart.addEntry(productId);
    };

    $scope.removeProduct = function (productId) {
        $http({
            url: "http://localhost:7377/product" + "/removeProductById",
            method: "DELETE",
            params:{"id":productId}
        }).then(function (response) {
            getProducts();
        }, function (error) {

        });
    };
    $scope.goTo= function(productId){
        $location.url("/product/"+productId);
    }
});