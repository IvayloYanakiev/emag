var app = angular.module("emag");

app.controller("searchController", function ($scope) {
    $scope.products = [];

    $scope.complete = function (string) {
        $scope.hidethis = false;
        var output = [];
        angular.forEach($scope.products, function (product) {
            if (product.toLowerCase().indexOf(string.toLowerCase()) >= 0) {
                output.push(product);
            }
        });
        $scope.filterProduct = output;
    }
    $scope.fillTextbox = function (string) {
        $scope.product = string;
        $scope.hidethis = true;
    }
});