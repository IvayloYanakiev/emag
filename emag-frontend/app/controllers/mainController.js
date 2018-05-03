var app = angular.module('emag');

app.controller("mainController", function ($scope, $location, $routeParams, $http, sessionService, $rootScope) {

    $http({
        url: "http://localhost:7377/category" + "/getAllCategories",
        method: "GET"
    }).then(function (response) {
        $scope.categories = response.data;
    }, function (error) {

    });
    $rootScope.isAuthenticated = sessionService.isLoggedIn();

    $scope.showMenu = function () {
        var dropdownContent = document.getElementById("dropdown_content");
        if (dropdownContent.style.display === "none") {
            dropdownContent.style.display = "block";
        } else {
            dropdownContent.style.display = "none";
        }
    };
    $scope.goTo= function(innerCategoryId){
            $location.url("/products/"+innerCategoryId);
    }

    $scope.MyController = function() {
        var listOfProducts = document.getElementById("filteredProducts");
        listOfProducts.style.display = "block";

        $http({
            url: "http://localhost:7377/product" + "/getAllProducts",
            method: "GET"
        }).then(function (response) {
            $scope.products = response.data;
        },function(error){

        });
    }

    $scope.goToPage = function(productId){
        var listOfFilteredProducts = document.getElementById("filteredProducts");
        listOfFilteredProducts.style.display = "none";
        $scope.search ="";

        $location.url("/product/"+productId);
    }
});