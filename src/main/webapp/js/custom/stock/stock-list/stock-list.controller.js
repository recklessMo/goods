(function(){

    angular.module("custom")
        .controller("StockListController", StockListController);

    StockListController.$inject = ['$scope', 'StockService', 'NgTableParams', 'blockUI', 'SweetAlert', 'ngDialog', 'Notify'];

    function StockListController($scope, StockService, NgTableParams, blockUI, SweetAlert, ngDialog, Notify){


        $scope.typeList = [{name : "全部", value : 0}, {name : "低存储", value : 1}];

        $scope.tableParams = {
            type: 0,
            searchStr: null,
            page : 1,
            count : 10
        }


        $scope.search = function(){
            $scope.stockTableParams = new NgTableParams($scope.tableParams, {
                getData: function($defer, params){
                    blockUI.start();
                    StockService.listGoods(params.parameters()).success(function(data){
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

        $scope.openStockDetail = function(data){
            var dialog = ngDialog.open({
                template: 'app/views/custom/stock/stock-list/stock-detail.html',
                controller: 'StockDetailController',
                className: 'ngdialog-theme-default max-dialog',
                data: {id : data}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.stockTableParams.reload();
            })
        }

    }

})();