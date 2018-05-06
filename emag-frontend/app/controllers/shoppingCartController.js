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
                if(products.length ===0){
                    var element = document.getElementById("checkoutBtn");
                    element.style.display = "none";
                }
            }, function (error) {

            });
        }
        else $scope.products=[];
    };
    $scope.goTo = function (productId) {

        $location.url("/product/" + productId);
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
    };

    $scope.getTotal = function() {
        var total = 0;
        if($scope.products.length === 0){
            return 0;
        }
        for(var i = 0; i < $scope.products.length; i++){
            var product = $scope.products[i];
            if(product.discount===0) total += (product.price * product.quantity);
            else  total= (product.price - product.discount/100*product.price)*product.quantity;
        }
        return total;
    };

    $scope.getSubTotal = function (price, quantity,discount) {

        if(discount===0) return price*quantity;
        else return (price - discount/100*price)*quantity;
    }

});