(function(){

    angular.module("custom")
        .controller("StockDetailController", StockDetailController);

    StockDetailController.$inject = ['$scope', 'StockService', 'SweetAlert', 'blockUI', 'NgTableParams'];

    function StockDetailController($scope, StockService, SweetAlert, blockUI, NgTableParams){


        $scope.id = $scope.ngDialogData.id;

        $scope.init = function(){
            $scope.goodsHistoryTableParams = new NgTableParams({}, {
                counts: [],
                getData: function($defer, params){
                    blockUI.start();
                    StockService.getGoodsHistory($scope.id).success(function(data){
                        blockUI.stop();
                        if(data.status == 200){
                            $defer.resolve(data.data);
                        }
                    }).error(function(){
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