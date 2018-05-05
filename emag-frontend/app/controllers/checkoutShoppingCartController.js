var app = angular.module('emag');

app.controller("checkoutShoppingCartController", function ($scope, $location, $routeParams, $http, sessionService) {

    var allAddresses = function () {
        $http({
            url: "http://localhost:7377/address" + "/getAllAddresses",
            method: "GET",
            params: {"userId": sessionService.getSession()}
        }).then(function (response) {
            $scope.addresses = response.data;
        });

    };
    allAddresses();

});