var app = angular.module('emag');

app.controller("checkoutShoppingCartController", function ($rootScope, $q, $scope, $location, $routeParams, $http, sessionService) {

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

    var getData = function () {
        $http({
            url: "http://localhost:7377/user" + "/getUserPersonalData",
            method: "GET",
            params: {"id": sessionService.getSession()}
        }).then(function (response) {
            $scope.user = response.data;
            if ($scope.user.pictureUrl != null) {
                $scope.pictureUrl = $scope.user.pictureUrl;
            } else $scope.pictureUrl = "http://127.0.0.1:8887/nopicture.jpg";
        });

    };
    getData();

    $scope.user = {email: ""};
    $scope.makeOrder = function () {

        var radioButtons = document.getElementsByName('address');
        for (var i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].checked) {
                var addressId = radioButtons[i].value;
                break;
            }
        }

        var radioButtonsForPayin = document.getElementsByName('payingOption');
        for (var i = 0; i < radioButtonsForPayin.length; i++) {
            if (radioButtonsForPayin[i].checked) {
                var paying = radioButtonsForPayin[i].value;
                break;
            }
        }

        $http({
            url: "http://localhost:7377/sendEmail" + "/informUserForOrder",
            method: "PUT",
            params: {
                "email": $scope.user.email,
                "addressId": addressId,
                "payingMethod": paying
            }
        }).then(function (response) {
            window.alert("Your order was successful");
            $scope.value = response.data;
            $location.url("/home");
        }, function (error) {
            window.alert("Your order was not successful");
        });
    }
});