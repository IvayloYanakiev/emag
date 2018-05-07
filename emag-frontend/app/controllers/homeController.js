var app = angular.module('emag');

app.controller("homeController", function ($rootScope,$scope, $location, $routeParams, $http, shoppingCart,sessionService) {

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

    $rootScope.isAdmin = sessionService.isHeAdmin();
    $scope.orderByPriceAscending = function () {
        $http({
            url: "http://localhost:7377/product" + "/orderProductsByPrice",
            method: "GET",
            params: {"orderIn":"asc"}
    }).
        then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };

    $scope.orderByPriceDescending = function () {
        $http({
            url: "http://localhost:7377/product" + "/orderProductsByPrice",
            method: "GET",
            params: {"orderIn":"desc"}
        }).
        then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };
    $scope.orderByDiscountAscending = function () {
        $http({
            url: "http://localhost:7377/product" + "/orderProductsByDiscount",
            method: "GET",
            params: {"orderIn": "asc"}
        }).then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };

    $scope.orderByDiscountDescending = function () {
        $http({
            url: "http://localhost:7377/product" + "/orderProductsByDiscount",
            method: "GET",
            params: {"orderIn": "desc"}
        }).then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };
    $scope.orderByNameAscending = function () {
        $http({
            url: "http://localhost:7377/product" + "/orderProductsByName",
            method: "GET",
            params: {"orderIn": "asc"}
        }).then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };

    $scope.orderByNameDescending = function () {
        $http({
            url: "http://localhost:7377/product" + "/orderProductsByName",
            method: "GET",
            params: {"orderIn": "desc"}
        }).then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };
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
                "id":  $scope.updateProduct.id,
                "name":  $scope.updateProduct.name,
                "categoryId":  $scope.updateProduct.innerCategoryId,
                "price":  $scope.updateProduct.price,
                "quantity":  $scope.updateProduct.quantity,
                "description":  $scope.updateProduct.description,
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

    $scope.orderByPriceDescending = function () {
        $http({
            url: "http://localhost:7377/product" + "/orderProductsBy",
            method: "GET",
            params: {"by":"desc"}
        }).
        then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };


});