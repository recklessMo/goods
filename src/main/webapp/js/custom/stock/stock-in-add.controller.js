(function(){

    angular.module("custom")
        .controller("AddInStockController", AddInStockController);

    AddInStockController.$inject = ['$scope', 'StockService', 'SweetAlert', 'blockUI'];

    function AddInStockController($scope, StockService, SweetAlert, blockUI){

        var block = blockUI.instances.get("add-in-stock");

        $scope.save = function(data){
            if(!validate(data)){
                SweetAlert.error("填写不完整!");
                return;
            }

            block.start();
            StockService.addInStock(data).success(function(data){
                block.stop();
                if(data.status == 200){
                    SweetAlert.success("添加成功!");
                    $scope.closeThisDialog("reload");
                }
            }).error(function(){
                block.stop();
                SweetAlert.error("网络出错,请稍后重试!");
            });
        }


        //TODO 进行goods必填字段的校验,很重要,暂时不做.
        function validate(goods){
            return true;
        }
    }

})();