var app = angular.module('emag');

app.controller("innerCategoryProducts", function ($scope, $location, $routeParams, $http, sessionService, $rootScope) {

    var innerCategoryId = $routeParams.id;

    $http({
        url: "http://localhost:7377/product" + "/getInnerCategoryProducts",
        method: "GET",
        params:{"id":innerCategoryId}
    }).then(function (response) {
        $scope.products = response.data;
    }, function (error) {

    });
});