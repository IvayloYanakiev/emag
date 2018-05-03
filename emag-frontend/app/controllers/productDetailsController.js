var app = angular.module('emag');
app.controller("productDetailsController", function ($scope, $location, $routeParams, $http) {

    var idProduct = $routeParams.id;

    $http({
        url: "http://localhost:7377/product" + "/getProductById",
        method: "GET",
        params:{"id":idProduct}
    }).then(function (response) {
        $scope.product = response.data;
    }, function (error) {
        alert(error);
    });

});

