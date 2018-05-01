var app = angular.module('emag', ['ngRoute']);

app.config(function ($routeProvider) {

    $routeProvider
        .when('/logout', {
            templateUrl: 'views/logout.html',
            controller: 'logoutController'
        })
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
        .when('/userPersonalData', {
            templateUrl: 'views/userPersonalData.html',
            controller: 'userPersonalDataController',
            css:'css/userPersonalData.css'
        })
        .when('/shoppingCart', {
            templateUrl: 'views/shoppingCart.html',
            controller: 'shoppingCartController'
        })
        .when('/addProduct', {
            templateUrl: 'views/addProduct.html',
            controller: 'addProductController',
            css:'css/addProduct.css'
        })
        .otherwise('/home');


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

app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

app.service('productUploadService', ['$q', '$http', function ($q, $http) {
    var deffered = $q.defer();
    var responseData;
    this.uploadFileToUrl = function (file, uploadUrl, product) {
        var fd = new FormData();
        fd.append('name', product.name);
        fd.append('categoryId', product.category);
        fd.append('price', product.price);
        fd.append('quantity', product.quantity);
        fd.append('description', product.description);
        fd.append('picture', file);
        return $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
            .success(function (response) {

                responseData = response;
                deffered.resolve(response);
                return deffered.promise;
            })
            .error(function (error) {
                deffered.reject(error);
                return deffered.promise;
            });

    };

    this.getResponse = function () {
        return responseData;
    }

}]);

app.service('addProfilePictureService', ['$q', '$http', function ($q, $http) {
    var deffered = $q.defer();
    var responseData;
    this.uploadFileToUrl = function (file, uploadUrl, id) {
        var fd = new FormData();
        fd.append('id', id);
        fd.append('picture', file);
        return $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
            .success(function (response) {

                responseData = response;
                deffered.resolve(response);
                return deffered.promise;
            })
            .error(function (error) {
                deffered.reject(error);
                return deffered.promise;
            });

    };

    this.getResponse = function () {
        return responseData;
    }

}]);
