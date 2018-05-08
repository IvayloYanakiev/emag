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

    $rootScope.isAdmin = sessionService.isHeAdmin();
    $scope.showMenu = function () {
        var dropdownContent = document.getElementById("dropdown_content");
        if (dropdownContent.style.display === "none") {
            dropdownContent.style.display = "block";
        } else {
            dropdownContent.style.display = "none";
        }
    };
    $scope.goTo = function (innerCategoryId) {
        $location.url("/products/" + innerCategoryId);
        var dropdownContent = document.getElementById("dropdown_content");
        dropdownContent.style.display = "none";
    }

    $scope.searchProduct = function search() {
        var input = document.getElementById("searchBox").value;
        $location.url("/searchedProduct");

            if (input === "") {
                $scope.hasProducts = false;
                $scope.informUser = true;
            } else {
                $scope.hasProducts = true;
                $scope.informUser = false;

                $http({
                    url: "http://localhost:7377/product" + "/getProductsFilteredByName",
                    method: "GET",
                    params: {"searchInput": input}
                }).then(function (response) {
                    document.getElementById("searchBox").value = "";
                    $scope.products = response.data;
                    var result = $scope.products;

                    if (typeof result != "undefined" && result != null && result.length != null && result.length > 0) {
                        $scope.hasProducts = true;
                        $scope.informUser = false;
                    } else {
                        $scope.hasProducts = false;
                        $scope.informUser = true;
                    }
                });
            }
    }
    $('#searchBox').keydown(function(e){
        if(e.keyCode === 13){
            $scope.searchProduct();
        }
    });
});