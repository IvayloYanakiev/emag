var app = angular.module('emag');

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

app.service('fileUpload', ['$q', '$http', function ($q, $http) {
    var deffered = $q.defer();
    var responseData;
    this.uploadFileToUrl = function (file, uploadUrl,user) {
        var fd = new FormData();
        fd.append('picture', file);
        fd.append('id', user.id);
        fd.append('name', user.name);
        fd.append('email', user.email);
        fd.append('gender', user.gender);
        fd.append('mobilePhone', user.phone);

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

app.controller("userPersonalDataController", function ($q,$scope, $location, $routeParams, $http, sessionService,fileUpload) {
    $scope.currentFile = {};
    $scope.dataUpload = true;
    $scope.myFile = {};

    var getData = function () {
        if (sessionService.isLoggedIn()) {
            $http({
                url: "http://localhost:7377/user" + "/getUserPersonalData",
                method: "GET",
                params: {"id": sessionService.getSession()}
            }).then(function (response) {
                $scope.user = JSON.parse(response.data.object);
                if($scope.user.profile_url==null){
                    //ima snimka slagame cheker v html
                }
            });
        } else $location.url("/login");

    };
    getData();


    $scope.updateUser = function(){
        var file =  $scope.myFile;
        var uploadUrl = "http://localhost:7377/user/updateUserPersonalData";
        fileUpload.uploadFileToUrl(file, uploadUrl,$scope.user).then(function(result){
            $scope.success = true;
            $scope.value  = "Success";
            $scope.errors = fileUpload.getResponse();
            console.log($scope.errors);
            $scope.errVisibility = true;
        }, function() {
            alert('error');
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
                }

                reader.readAsDataURL(input.files[0]);
            }
        }

        $("#imgInp").change(function () {
            readURL(this);
        });
    });
});