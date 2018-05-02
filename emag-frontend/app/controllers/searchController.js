var app = angular.module('emag');

app.controller("searchController", function ($scope, $location, $routeParams, $http) {

    $http({
        url: "http://localhost:7377/product" + "/getAllProducts",
        method: "GET"
    }).then(function (response) {
        $scope.products = response.data;

        $scope.complete = function (string) {
            $scope.hidethis = false;
            var output = [];
            angular.forEach($scope.products, function (product) {
                if (product.toLowerCase().indexOf(string.toLowerCase()) >= 0) {
                    output.push(product);
                }
            });
            $scope.filterProducts = output;
        }
        $scope.fillTextbox = function (string) {
            $scope.product = string;
            $scope.hidethis = true;
        }
    }, function (error) {

    });
});