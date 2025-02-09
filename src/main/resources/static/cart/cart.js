angular.module('market-front').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.loadCart = function () {
        $http.get(contextPath + '/cart')
            .then(function (response) {
                $scope.currentCart = response.data;
            });
    }

    $scope.addToCart = function (productId) {
        $http.get(contextPath + '/cart/add/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.removeFromCart = function (productId) {
        $http.delete(contextPath + '/cart/removeProduct/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.clearCart = function () {
        $http.get(contextPath + '/cart/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }


    $scope.disabledCreateOrder = function (){
        alert("Для оформления заказа необходимо войти в учётную запись")
    }

    $scope.createOrder = function () {
        console.log($scope.orderDetails);
        $http.post(contextPath + '/orders', $scope.orderDetails)
            .then(function (response) {
                alert("Заказ: " + response.data + " оформлен!");
                $scope.loadCart();
                $scope.orderDetails = null;

            });
    }

    $scope.loadCart();
});
