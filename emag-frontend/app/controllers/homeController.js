var app = angular.module('emag');

app.controller("homeController", function ($rootScope, $scope, $location, $routeParams, $http, shoppingCart, sessionService) {

    $scope.hasProducts = true;
    $scope.informUser = false;
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
            params: {"orderIn": "asc"}
        }).then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };

    $scope.orderByPriceDescending = function () {
        $http({
            url: "http://localhost:7377/product" + "/orderProductsByPrice",
            method: "GET",
            params: {"orderIn": "desc"}
        }).then(function (response) {
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
            alert(error.data);
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
            alert(error.data);
        });
    };
    $scope.removeProduct = function (productId) {
        $http({
            url: "http://localhost:7377/admin" + "/removeProductById",
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
            url: "http://localhost:7377/admin" + "/updateProduct",
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


    var slider = document.getElementById("myRange");
    var output = document.getElementById("demo");
    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    }

    $scope.filterProducts = function () {

        var newArray = $scope.products.filter(function (el) {
            return el.price <= slider.value;
        });
        $scope.products = newArray;
    }


    $scope.getNewProductPrice = function (price, discount) {
        if (discount === 0) {
            return price;
        } else {
            var result = price - discount / 100 * price;
            result = result.toFixed(2);
            return result;
        }
    }
})
;