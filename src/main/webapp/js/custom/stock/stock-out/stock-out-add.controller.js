(function(){

    angular.module("custom")
        .controller("AddOutStockController", AddOutStockController);

    AddOutStockController.$inject = ['$scope', 'StockService', 'SweetAlert', 'blockUI', 'NgTableParams'];

    function AddOutStockController($scope, StockService, SweetAlert, blockUI, NgTableParams){
        var block = blockUI.instances.get('stock-out-add');

        $scope.stock = {
            stockType: "出库",
            created: new Date()
        };

        //暂时先写死,后期改成从后端拉取字典信息
        $scope.categoryList = [{value: "借用出库"}, {value: "损坏出库"}, {value: "其它出库"}];
        //定义table对象数据
        $scope.stockItems = [];

        $scope.addRow = function(){
            $scope.stockItems.push({});
        }

        $scope.deleteItem = function(index){
            $scope.stockItems.splice(index, 1);
        }

        $scope.selectCategory = function(){
            if($scope.stock.category != "借用出库"){
                $scope.stock.clientName = "";
            }
        }

        $scope.stockItemTableParams = new NgTableParams({}, {
            counts: [],
            getData: function($defer, params){
                $defer.resolve($scope.stockItems);
            }
        });

        //用于搜索物资
        $scope.titles = [];
        $scope.search = function(data){
            var tableParameters = {searchStr : data, page : 1, count: 20};
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
            row.dw = $item.dw;
        }


        $scope.validate = function(data){
            return true;
        }

        $scope.addStockOut = function(){
            if(!$scope.validate($scope.stock)){
                return;
            }
            $scope.stock.items = $scope.stockItems;
            block.start()
            StockService.addOutStock($scope.stock).success(function(data){
                if(data.status == 200) {
                    block.stop();
                    SweetAlert.success("添加成功");
                    $scope.closeThisDialog("reload");
                }
            }).error(function(){
                block.stop();
                SweetAlert.error("添加失败");
            });
        }

    }

})();