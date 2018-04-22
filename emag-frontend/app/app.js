var app = angular.module('emag', ['ngRoute']);

app.config(function ($routeProvider) {

    $routeProvider
        .when('/logout', {
            templateUrl: 'views/logout.html',
            controller: 'logoutController'
        })
        .when('/login', {
            templateUrl: 'views/login.html',
            controller: 'loginController'
        })
        .when('/register', {
            templateUrl: 'views/register.html',
            controller: 'registerController'
        })
        .when('/userPage', {
            templateUrl: 'views/userPage.html',
            controller: 'userController'
        })
        .otherwise('/login');


});

app.factory('sessionService', function () {

    var session = {};
    session.getSession = function () {
        return localStorage.getItem("session");
    };
    session.login = function (userId) {
        localStorage.setItem("session", userId);
    };

    session.logout = function () {
        localStorage.removeItem("session");
    };

    session.isLoggedIn = function () {
        return localStorage.getItem("session") != null;
    };

    return session;
});