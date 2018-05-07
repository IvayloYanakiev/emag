var app = angular.module('emag');

app.controller("searchedProductController", function ($rootScope, $scope, $location, $routeParams, $http, shoppingCart, sessionService) {

    $scope.addToCart = function (productId) {
        shoppingCart.addEntry(productId);
    };

    $rootScope.isAdmin = sessionService.isHeAdmin();

    $scope.removeProduct = function (productId) {
        $http({
            url: "http://localhost:7377/product" + "/removeProductById",
            method: "DELETE",
            params: {"id": productId}
        }).then(function (response) {
            getProducts();
        }, function (error) {

        });
    };

    $scope.goTo = function (productId) {
        $location.url("/product/" + productId);
    };

    $scope.editProduct = function (updateProduct) {
        $scope.updateProduct = jQuery.extend({}, updateProduct);
    };

    $scope.updProduct = function () {
        $scope.error = false;

        $http({
            url: "http://localhost:7377/product" + "/updateProduct",
            method: "PUT",
            params: {
                "id": $scope.updateProduct.id,
                "name": $scope.updateProduct.name,
                "categoryId": $scope.updateProduct.innerCategoryId,
                "price": $scope.updateProduct.price,
                "quantity": $scope.updateProduct.quantity,
                "description": $scope.updateProduct.description,
                "discount": $scope.updateProduct.discount
            }
        }).then(function (response) {
            $('#myModal').modal('hide');
            $location.url("/");
        }, function (error) {
            $scope.error = true;
            $scope.value = error.data;
        });
    };

    $scope.getNewProductPrice = function (priceOfProduct, discountOfProduct) {
        if(discountOfProduct === 0){
            return priceOfProduct;
        } else {
            var result = priceOfProduct - discountOfProduct / 100 * priceOfProduct;
            result = result.toFixed(2);
            return result;
        }
    }
});