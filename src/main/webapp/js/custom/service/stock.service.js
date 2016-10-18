(function () {
    'use strict';

    angular
        .module('custom')
        .service('StockService', StockService);

    StockService.$inject = ['$http'];
    function StockService($http) {

        this.listGoods = listGoods;
        this.addGoods = addGoods;
        this.updateGoods = updateGoods;
        this.deleteGoods = deleteGoods;

        this.listInStock = listInStock;
        this.addInStock = addInStock;
        this.listOutStock = listOutStock;
        this.addOutStock = addOutStock;

        this.getStock = getStock;
        this.getGoodsHistory = getGoodsHistory;

        function deleteGoods(data){
            return $http({
                method: "POST",
                data: data,
                url: "/v1/stock/goods/delete",
                timeout: 5000
            });
        }

        function listGoods(data){
            return $http({
                method: "POST",
                data: data,
                url: "/v1/stock/goods/list",
                timeout: 5000
            });
        }

        function addGoods(data){
            return $http({
                method: "POST",
                data: data,
                url: "/v1/stock/goods/add",
                timeout: 5000
            })
        }

        function updateGoods(data){
            return $http({
                method: "POST",
                data: data,
                url: "/v1/stock/goods/update",
                timeout: 5000
            })
        }


        function listInStock(data){
            return $http({
                method : "POST",
                data: data,
                url: "/v1/stock/in/list",
                timeout: 5000
            })
        }

        function addInStock(data){
            return $http({
                method : "POST",
                data: data,
                url: "/v1/stock/in/add",
                timeout: 5000
            })
        }

        function listOutStock(data){
            return $http({
                method : "POST",
                data: data,
                url: "/v1/stock/out/list",
                timeout: 5000
            })
        }

        function addOutStock(data){
            return $http({
                method : "POST",
                data: data,
                url: "/v1/stock/out/add",
                timeout: 5000
            })
        }

        function getStock(id){
            return $http({
                method: 'POST',
                data: id,
                url: "/v1/stock/in/detail",
                timeout: 5000
            });
        }

        function getGoodsHistory(id){
            return $http({
                method: "POST",
                data : id,
                url : "/v1/stock/goods/history/list",
                timeout: 5000
            });
        }

    }
})
();
