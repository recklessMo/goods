(function(){

    angular.module("custom")
        .controller("StockOutListController", StockOutListController);

    StockOutListController.$inject = ['$scope', 'StockService', 'NgTableParams', 'blockUI', 'SweetAlert', 'ngDialog'];

    function StockOutListController($scope, StockService, NgTableParams, blockUI, SweetAlert, ngDialog){

        $scope.tableParams = {
            searchStr: null,
            stockType: '出库',
            page : 1,
            count : 10
        }

        $scope.search = function(){
            $scope.stockOutTableParams = new NgTableParams($scope.tableParams, {
                getData: function($defer, params){
                    blockUI.start();
                    StockService.listOutStock(params.parameters()).success(function(data){
                        if(data.status == 200){
                            params.total(data.totalCount);
                            $defer.resolve(data.data);
                            console.log(data.data);
                            blockUI.stop();
                        }
                    }).error(function(){
                        SweetAlert.error("网络问题, 请稍后重试!");
                        blockUI.stop();
                    });
                }
            })
        }

        $scope.search();


        $scope.addNewOutStock = function(){
            var dialog= ngDialog.open({
                template: 'app/views/custom/stock/stock-out/stock-out-add.html',
                controller: 'AddOutStockController',
                className: 'ngdialog-theme-default max-dialog',
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.stockOutTableParams.reload();
            });
        }

        $scope.openStockOutDetail = function(data){
            var dialog= ngDialog.open({
                template: 'app/views/custom/stock/stock-out/stock-out-detail.html',
                controller: 'StockOutDetailController',
                className: 'ngdialog-theme-default max-dialog',
                data: {data : data}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.stockOutTableParams.reload();
            });
        }

    }

})();