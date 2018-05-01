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
        var x = document.getElementById("dropdown_content");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    };
    $scope.goTo= function(innerCategoryId){

    }

});