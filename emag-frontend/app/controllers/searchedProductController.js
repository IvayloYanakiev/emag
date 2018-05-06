var app = angular.module('emag');

app.controller("searchProductController", function ($scope, $location, $routeParams, $http) {

    var allAddresses = function () {
        $http({
            url: "http://localhost:7377/product" + "/getProductsFilteredByName",
            method: "GET",
            params: {"searchInput": "new"}
        }).then(function (response) {
            $scope.addresses = response.data;
        });

    };
    allAddresses();

});