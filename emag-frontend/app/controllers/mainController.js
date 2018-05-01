var app = angular.module('emag');

app.controller("mainController", function ($scope, $location, $routeParams, $http, sessionService, $rootScope) {

    $http({
        url: "http://localhost:7377/category" + "/getAllCategories",
        method: "GET"
    }).then(function (response) {
        $scope.categories = response.data;
    },function(error){

    });
    $rootScope.isAuthenticated = sessionService.isLoggedIn();

    $scope.showMenu = function() {
        var x = document.getElementById("dropdown_content");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }

    var getProducts = function () {
        $http({
            url: "http://localhost:7377/product" + "/showProductsByCategoryId",
            method: "GET",
            params: {"id": $scope.inner.id }
        }).then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    }


    // var getAddresses = function () {
    //     if (sessionService.isLoggedIn()) {
    //         $http({
    //             url: "http://localhost:7377/address" + "/getAllAddresses",
    //             method: "GET",
    //             params: {"userId": sessionService.getSession()}
    //         }).then(function (response) {
    //             $scope.addresses = response.data;
    //         });
    //     } else $location.url("/login");
    //
    // };
    // getAddresses();
});