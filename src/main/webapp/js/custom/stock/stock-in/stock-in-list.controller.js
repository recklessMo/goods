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
            $scope.stockInTableParams = new NgTableParams({}, {
                getData: function(params){
                    blockUI.start();
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = params.count();
                    return StockService.listInStock($scope.tableParams).then(function(result){
                        blockUI.stop();
                        var data = result.data;
                        if(data.status == 200){
                            params.total(data.totalCount);
                            return data.data;
                        }
                    }, function(){
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