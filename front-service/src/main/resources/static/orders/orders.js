angular.module('market-front').controller('ordersController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1';

    $scope.loadOrders = function () {
        $http.get(contextPath + '/orders')
            .then(function (response) {
                $scope.myOrders = response.data;
            });
    }

    $scope.loadOrders();

});