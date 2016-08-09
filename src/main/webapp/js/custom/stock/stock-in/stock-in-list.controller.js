(function(){

    angular.module("custom")
        .controller("StockInListController", StockInListController);

    StockInListController.$inject = ['$scope', 'StockService', 'NgTableParams', 'blockUI', 'SweetAlert', 'ngDialog'];

    function StockInListController($scope, StockService, NgTableParams, blockUI, SweetAlert, ngDialog){

        $scope.tableParams = {
            searchStr: null,
            stockType: '入库',
            page : 1,
            count : 10
        }

        $scope.search = function(){
            $scope.stockInTableParams = new NgTableParams($scope.tableParams, {
                getData: function($defer, params){
                    blockUI.start();
                    StockService.listInStock(params.parameters()).success(function(data){
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


        $scope.addNewInStock = function(){
            var dialog= ngDialog.open({
                template: 'app/views/custom/stock/stock-in/stock-in-add.html',
                controller: 'AddInStockController',
                className: 'ngdialog-theme-default max-dialog',
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.stockInTableParams.reload();
            });
        }

        $scope.openStockInDetail = function(data){
            var dialog= ngDialog.open({
                template: 'app/views/custom/stock/stock-in/stock-in-detail.html',
                controller: 'StockInDetailController',
                className: 'ngdialog-theme-default max-dialog',
                data: {data : data}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.stockInTableParams.reload();
            });
        }

    }

})();