(function(){

    angular.module("custom")
        .controller("StockDetailController", StockDetailController);

    StockDetailController.$inject = ['$scope', 'StockService', 'SweetAlert', 'blockUI', 'NgTableParams'];

    function StockDetailController($scope, StockService, SweetAlert, blockUI, NgTableParams){


        $scope.id = $scope.ngDialogData.id;

        $scope.init = function(){
            $scope.goodsHistoryTableParams = new NgTableParams({}, {
                counts: [],
                getData: function(params){
                    blockUI.start();
                    return StockService.getGoodsHistory($scope.id).then(function(result){
                        var data = result.data;
                        blockUI.stop();
                        if(data.status == 200){
                            params.total(data.totalCount);
                            return data.data;
                        }
                    }, function(){
                        blockUI.stop();
                        SweetAlert.error("加载失败!");
                        $scope.closeThisDialog("ok");
                    });
                }
            });
        }

        $scope.init();



    }

})();