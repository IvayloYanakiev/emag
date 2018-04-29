var app = angular.module('emag');

app.controller("subscribeController", function ($rootScope, $scope, $location, $http, sessionService) {

    $scope.user = {email: ""};
    $scope.subscribe = function () {

        $http({
            url: "http://localhost:7377/subscribe" + "/subscribeUser",
            method: "POST",
            params: {
                "email": $scope.user.email
            }
        }).then(function (response) {

            if (response.data.status == "BAD_REQUEST") {
                $scope.error = true;
                $scope.value = response.data.object;
            }
            else {
                var userId = JSON.parse(response.data.object).id;
                sessionService.login(userId);
                $rootScope.isAuthenticated = sessionService.isLoggedIn();
                $location.url("/");
            }
        });
    }
}