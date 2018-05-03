var app = angular.module('emag');
app.controller("productDetailsController", function ($scope, $location, $routeParams, $http) {
    $scope.currentProduct={};
    var productId = $routeParams.id;
    $http({
        url: "http://localhost:7377/product" + "/getProductById",
        method: "GET",
        params: {"id": productId}
    }).then(function (response) {
        $scope.currentProduct = response.data;
    }, function (error) {
        alert(error);
    });
});
