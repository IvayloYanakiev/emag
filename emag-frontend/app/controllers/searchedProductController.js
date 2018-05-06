var app = angular.module('emag');

app.controller("searchedProductController", function ($rootScope, $scope, $location, $routeParams, $http, shoppingCart, sessionService) {

    var input = document.getElementById("searchBox").value;
    var filteredProducts = function () {

        if (input === "") {
            $scope.hasProducts = false;
            $scope.informUser = true;
        } else {
            $scope.hasProducts = true;
            $scope.informUser = false;


        $http({
            url: "http://localhost:7377/product" + "/getProductsFilteredByName",
            method: "GET",
            params: {"searchInput": input}
        }).then(function (response) {
            document.getElementById("searchBox").value = "";
            $scope.filteredProducts = response.data;
            var result = $scope.filteredProducts;

            if (typeof result != "undefined" && result != null && result.length != null && result.length > 0) {
                $scope.hasProducts = true;
                $scope.informUser = false;
            } else {
                $scope.hasProducts = false;
                $scope.informUser = true;
            }
        });
        }
    };
    filteredProducts();

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

});