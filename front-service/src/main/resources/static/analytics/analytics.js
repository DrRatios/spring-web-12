angular.module('market-front').controller('analyticsController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/analytics/api/v1';


    $scope.loadOrderAnalytics = function () {
        $http.get(contextPath + '/analytics/order')
            .then(function (response) {
                $scope.orderAnalytics = response.data;
            });
    }
    $scope.updateOrderAnalyticsList = function () {
        $http.get(contextPath + '/analytics/order/update')
            .then(function (response) {
                $scope.orderAnalytics = response.data;
                $scope.loadOrderAnalytics();
            });
    }

    $scope.loadCartAnalytics = function () {
        $http.get(contextPath + '/analytics/cart')
            .then(function (response) {
                $scope.cartAnalytics = response.data;
            });
    }
    $scope.updateCartAnalyticsList = function () {
        $http.get(contextPath + '/analytics/cart/update')
            .then(function (response) {
                $scope.cartAnalytics = response.data;
                $scope.loadCartAnalytics();
            });
    }

    $scope.loadOrderAnalytics();
    $scope.loadCartAnalytics();
});