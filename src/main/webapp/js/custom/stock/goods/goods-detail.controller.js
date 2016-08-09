(function(){

    angular.module("custom")
        .controller("GoodsDetailController", GoodsDetailController);

    GoodsDetailController.$inject = ['$scope', 'StockService', 'SweetAlert', 'blockUI', 'NgTableParams'];

    function GoodsDetailController($scope, StockService, SweetAlert, blockUI, NgTableParams){


        $scope.id = $scope.ngDialogData.id;

        $scope.init = function(){
            $scope.goodsHistoryTableParams = new NgTableParams({}, {
                counts: [],
                getData: function($defer, params){
                    StockService.getGoodsHistory($scope.id).success(function(data){
                        if(data.status == 200){
                            $defer.resolve(data.data);
                        }
                    }).error(function(){
                        SweetAlert.error("加载失败!");
                        $scope.closeThisDialog("ok");
                    });
                }
            });
        }

        $scope.init();



    }

})();