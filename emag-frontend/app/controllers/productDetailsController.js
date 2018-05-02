var app = angular.module('emag');

app.controller("productDetailsController", function ($scope, $location, $routeParams, $http) {

    var productId = $routeParams.id;

    $http({
        url: "http://localhost:7377/product" + "/getProductById",
        method: "GET",
        params:{"id":productId}
    }).then(function (response) {
        $scope.product = response.data;
    }, function (error) {

    });
});