var app = angular.module('emag');

app.service('productUploadService', ['$q', '$http', function ($q, $http) {
    var deffered = $q.defer();
    var responseData;
    this.uploadFileToUrl = function (file, uploadUrl, product) {
        var fd = new FormData();
        fd.append('name', product.name);
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

app.controller("addProductController", function ($rootScope, $q, $scope, $location, $routeParams, $http, sessionService, productUploadService) {
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
    $scope.myFile = {};
    $scope.product = {name: "", price: "", quantity: "", description: ""};

    $scope.addProduct = function () {
        var file = $scope.myFile;
        $scope.success = false;
        $scope.error = false;
        var uploadUrl = "http://localhost:7377/product/addProduct";
        productUploadService.uploadFileToUrl(file, uploadUrl, $scope.product).then(function (result) {
            $location.url("/");
        }, function (err) {
            $scope.error = true;
            $scope.value = err.data;
        })


    };


    $(document).ready(function () {
        $(document).on('change', '.btn-file :file', function () {
            var input = $(this),
                label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
            input.trigger('fileselect', [label]);
        });

        $('.btn-file :file').on('fileselect', function (event, label) {

            var input = $(this).parents('.input-group').find(':text'),
                log = label;

            if (input.length) {
                input.val(log);
            } else {
            }

        });

        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#img-upload').attr('src', e.target.result);
                };

                reader.readAsDataURL(input.files[0]);
            }
        }

        $("#imgInp").change(function () {
            if (this.files && this.files[0]) {
                $scope.error = false;
                var file = this.files[0];
                var fileType = file.type;
                var ValidImageTypes = ["image/gif", "image/jpeg", "image/png"];
                if ($.inArray(fileType, ValidImageTypes) < 0) {
                    $scope.error = true;
                    $scope.value = "Invalid file"
                } else readURL(this);
            }
        });
    });
});