var app = angular.module('emag');

app.controller("shoppingCartController", function ($scope, $location, $routeParams, $http, sessionService, $rootScope,shoppingCart) {


    var getShoppingCart = function(){
        var entries = shoppingCart.getEntries();
        var isNotEmpty = shoppingCart.isNotEmpty();
        if(isNotEmpty){
            $http({
                url: "http://localhost:7377/product" + "/getProductsFromShoppingCart",
                method: "GET",
                params:{"products":entries}
            }).then(function (response) {
                $scope.products = response.data;
            }, function (error) {

            });
        }
        else $scope.products=[];
    };

    getShoppingCart();
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
    $scope.addToCart = function(productId){
        shoppingCart.addEntry(productId);
        getShoppingCart();
    };
    $scope.removeFromCart = function(productId){
        shoppingCart.removeEntry(productId);

        getShoppingCart();
    }


});