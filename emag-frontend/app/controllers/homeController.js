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
    };

    $scope.editProduct= function(updateProduct){
        $scope.updateProduct = jQuery.extend({}, updateProduct);
    };

    $scope.updProduct = function(){
        $scope.error = false;

        $http({
            url: "http://localhost:7377/product" + "/updateProduct",
            method: "PUT",
            params: {
                "id":  $scope.updateProduct.id,
                "name":  $scope.updateProduct.name,
                "categoryId":  $scope.updateProduct.innerCategoryId,
                "price":  $scope.updateProduct.price,
                "quantity":  $scope.updateProduct.quantity,
                "description":  $scope.updateProduct.description
            }
        }).then(function (response) {
            $location.url("/");
        }, function (error) {
            $scope.error = true;
            $scope.value = error.data;
        });
    }
});