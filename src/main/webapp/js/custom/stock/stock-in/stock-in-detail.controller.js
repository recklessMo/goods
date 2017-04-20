(function(){

    angular.module("custom")
        .controller("StockInDetailController", StockInDetailController);

    StockInDetailController.$inject = ['$scope', 'StockService', 'SweetAlert', 'blockUI', 'NgTableParams'];

    function StockInDetailController($scope, StockService, SweetAlert, blockUI, NgTableParams){
        var block = blockUI.instances.get('stock-in-detail');

        $scope.obj = {};

        $scope.obj.id = $scope.ngDialogData.data;

        //暂时先写死,后期改成从后端拉取字典信息
        $scope.categoryList = [{value: "采购入库"}, {value: "归还入库"}, {value: "退货入库"}, {value: "其它入库"}];


        $scope.stockItemTableParams = new NgTableParams({}, {
            counts: [],
            getData: function(params){
                block.start();
                return StockService.getStock($scope.obj.id).then(function(result){
                    var data = result.data;
                    block.stop();
                    if(data.status == 200){
                        $scope.stock = data.data;
                        return $scope.stock.items;
                    }
                }, function(){
                    block.stop();
                    SweetAlert.error("获取详细记录失败!");
                    $scope.closeThisDialog("ok");
                });
            }
        });

    }

})();