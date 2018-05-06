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

});