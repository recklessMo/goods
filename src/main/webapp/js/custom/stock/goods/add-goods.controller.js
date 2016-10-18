(function(){

    angular.module("custom")
        .controller("AddGoodsController", AddGoodsController);

    AddGoodsController.$inject = ['$scope', 'StockService', 'SweetAlert', 'blockUI'];

    function AddGoodsController($scope, StockService, SweetAlert, blockUI){

        $scope.obj = {};
        $scope.obj.type = ($scope.ngDialogData.type == 'add');
        $scope.goods = $scope.obj.type ? {} : angular.copy($scope.ngDialogData.data);

        $scope.typeList = ["书籍(教材)", "办公用品", "食品", "后勤物资", "药品", "其它"];
        $scope.dwList = ["本", "支", "袋", "包", "根", "桶", "盒", "箱", "瓶"];

        var block = blockUI.instances.get("add-goods");

        $scope.save = function(data){
            if(!validate(data)){
                SweetAlert.error("填写不完整!");
                return;
            }

            block.start();
            if($scope.obj.type) {
                StockService.addGoods(data).success(function (data) {
                    block.stop();
                    if (data.status == 200) {
                        SweetAlert.success("添加成功!");
                        $scope.closeThisDialog("reload");
                    }
                }).error(function () {
                    block.stop();
                    SweetAlert.error("网络出错,请稍后重试!");
                });
            }else{
                StockService.updateGoods(data).success(function (data) {
                    block.stop();
                    if (data.status == 200) {
                        SweetAlert.success("更新成功!");
                        $scope.closeThisDialog("reload");
                    }
                }).error(function () {
                    block.stop();
                    SweetAlert.error("网络出错,请稍后重试!");
                });
            }
        }


        //TODO 进行goods必填字段的校验,很重要,暂时不做.
        function validate(goods){
            return true;
        }
    }

})();