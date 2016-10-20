(function(){

    angular.module("custom")
        .controller("StockOutDetailController", StockOutDetailController);

    StockOutDetailController.$inject = ['$scope', 'StockService', 'SweetAlert', 'blockUI', 'NgTableParams'];

    function StockOutDetailController($scope, StockService, SweetAlert, blockUI, NgTableParams){
        var block = blockUI.instances.get('stock-out-detail');

        $scope.stock = $scope.ngDialogData.data;

        //暂时先写死,后期改成从后端拉取字典信息
        $scope.categoryList = [{value: "采购入库"}, {value: "归还入库"}, {value: "退货入库"}, {value: "其它入库"}];

        //定义table对象数据
        $scope.stockItems = [];

        $scope.addRow = function(){
            $scope.stockItems.push({});
        }

        $scope.deleteItem = function(index){
            $scope.stockItems.splice(index, 1);
        }

        $scope.stockItemTableParams = new NgTableParams({}, {
            counts: [],
            getData: function($defer, params){
                block.start();
                StockService.getStock($scope.stock.id).success(function(data){
                    if(data.status == 200){
                        $scope.stockItems = data.data.items;
                        $defer.resolve($scope.stockItems);
                        block.stop();
                    }
                }).error(function(){
                    block.stop();
                    SweetAlert.error("获取详细记录失败!");
                    $scope.closeThisDialog("ok");
                });
            }
        });

        //用于搜索物资
        $scope.titles = [];
        $scope.search = function(data){
            var tableParameters = {searchStr : data, page : 1, count: 40};
            StockService.listGoods(tableParameters).success(function(data){
                if(data.status == 200){
                    $scope.titles = data.data;
                    blockUI.stop();
                }
            }).error(function(){
                SweetAlert.error("网络问题, 请稍后重试!");
                blockUI.stop();
            });
        }


        $scope.itemSelected = function(row, $item){
            row.gg = $item.gg;
            row.cjmc = $item.cjmc;
        }


        $scope.validate = function(data){
            return true;
        }

        $scope.addStockIn = function(){
            if(!$scope.validate($scope.stock)){
                return;
            }
            $scope.stock.items = $scope.stockItems;
            block.start()
            StockService.addInStock($scope.stock).success(function(data){
                block.stop();
                SweetAlert.success("成功");
                $scope.closeThisDialog("reload");
            }).error(function(){
                block.stop();
                SweetAlert.error("失败");
            });
        }

    }

})();