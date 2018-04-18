var app = angular.module('9gagApp', ['ngRoute']);

app.config(function ($routeProvider) {

    $routeProvider
        .when('/home', {
            templateUrl: 'views/home.html',
            controller: 'homeController'
        })
        .when('/login', {
            templateUrl: 'views/login.html',
            controller: 'loginController'
        })
        .when('/register', {
            templateUrl: 'views/register.html',
            controller: 'registerController'
        })
        .when('/userPage/:userId', {
            templateUrl: 'views/userPage.html',
            controller: 'userController'
        })
        .otherwise('/home');


});

app.factory('sessionService', function () {
    var session = {};
    session.getSession = function () {
        return localStorage.getItem("session");
    };
    session.login = function (userId) {
       localStorage.setItem("session",userId);
    };

    session.logout = function () {
        localStorage.removeItem("session");
    };

    session.isLoggedIn = function () {
        return localStorage.getItem("session") != null;
    };
    return session;
});