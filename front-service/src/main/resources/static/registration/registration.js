angular.module('market-front').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1';

    $scope.regUser = function () {
        console.log($scope.newUser);
        $http.post(contextPath + '/reg', $scope.newUser)
            .then(function (response) {
                alert("Пользователь: " + response.data.username + " зарегистрирован!");
            });
    }

});
