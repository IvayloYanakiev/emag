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
    this.uploadFileToUrl = function (file, uploadUrl,userId) {
        var fd = new FormData();
        fd.append('id', userId);
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

app.controller("userPersonalDataController", function ($rootScope,$q,$scope, $location, $routeParams, $http, sessionService,fileUpload) {
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
    $scope.currentFile = {};
    $scope.dataUpload = true;
    $scope.myFile = {};
    $scope.pictureUrl = "";
    $scope.address = "";
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
    $scope.putGender = function(gender){
        if(gender==1){
            $scope.user.gender = "Male";
        }
        else  $scope.user.gender = "Female";

    };

    var getData = function () {
        if (sessionService.isLoggedIn()) {
            $http({
                url: "http://localhost:7377/user" + "/getUserPersonalData",
                method: "GET",
                params: {"id": sessionService.getSession()}
            }).then(function (response) {
                $scope.user = JSON.parse(response.data.object);
                if($scope.user.pictureUrl!=null){
                    $scope.pictureUrl=$scope.user.pictureUrl;
                }else $scope.pictureUrl = "http://127.0.0.1:8887/nopicture.jpg";
            });
        } else $location.url("/login");

    };
    getData();


    $scope.updateAddress = function(){
        $http({
            url: "http://localhost:7377/address" + "/updateAddress",
            method: "PUT",
            params: {
                "addressId": $scope.currentAddress.id,
                "receiverName": $scope.currentAddress.receiverName,
                "receiverPhone": $scope.currentAddress.receiverPhone,
                "cityId": $scope.currentAddress.city.id,
                "street": $scope.currentAddress.street,
                "floor": $scope.currentAddress.floor
            }
        }).then(function (response) {
            $('#myModal4').modal('hide');
            getAddresses();
            $scope.currentAddress = "";
        });
    };

    $scope.getAddress= function(addressId){
        $http({
            url: "http://localhost:7377/address" + "/getAddress",
            method: "GET",
            params: {
                "addressId": addressId
            }
        }).then(function (response) {
            $scope.currentAddress = JSON.parse(response.data.object);
        });
    };



    $scope.deleteAddress= function(addressId){
        $http({
            url: "http://localhost:7377/address" + "/deleteAddress",
            method: "DELETE",
            params: {
                "addressId": addressId
            }
        }).then(function (response) {
            getAddresses();
        });
    };

    var getAddresses = function () {
        if (sessionService.isLoggedIn()) {
            $http({
                url: "http://localhost:7377/address" + "/getAllAddresses",
                method: "GET",
                params: {"userId": sessionService.getSession()}
            }).then(function (response) {
                $scope.addresses = JSON.parse(response.data.object);

            });
        } else $location.url("/login");

    };
    getAddresses();

    $scope.updateUserProfilePicture = function(){
        var file =  $scope.myFile;
        var uploadUrl = "http://localhost:7377/user/updateUserProfilePicture";
        fileUpload.uploadFileToUrl(file, uploadUrl,$scope.user.id).then(function(result){
            var url = JSON.parse(result.data.object);
            $scope.pictureUrl = url;
            $('#myModal').modal('hide');
        }, function() {
            alert('error');
        })

    };

  $scope.updateUserPersonalData = function () {
      $http({
          url: "http://localhost:7377/user" + "/updateUserPersonalData",
          method: "PUT",
          params: {
              "id": $scope.user.id,
              "name": $scope.user.name,
              "email": $scope.user.email,
              "gender": $scope.user.gender,
              "phone": $scope.user.phone

          }
      }).then(function (response) {
          $('#myModal2').modal('hide');
      });

  };

  $scope.resetPersonalData = function(){
      $('#myModal2').modal('hide');
      getData();
  };

    $scope.resetAddresses = function(){
        $('#myModal3').modal('hide');
        getAddresses();
        $scope.address = "";
    };


    $scope.addAddress = function(){
        $http({
            url: "http://localhost:7377/address" + "/addAddress",
            method: "POST",
            params: {
                "userId": sessionService.getSession(),
                "receiverName": $scope.address.receiverName,
                "receiverPhone": $scope.address.receiverPhone,
                "cityId": $scope.address.cityId,
                "street": $scope.address.street,
                "floor": $scope.address.floor
            }
        }).then(function (response) {
            $('#myModal3').modal('hide');
            getAddresses();
            $scope.address = "";
        });
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