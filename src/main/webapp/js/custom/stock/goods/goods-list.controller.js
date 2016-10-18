(function(){

    angular.module("custom")
        .controller("GoodsListController", GoodsListController);

    GoodsListController.$inject = ['$scope', 'StockService', 'NgTableParams', 'blockUI', 'SweetAlert', 'ngDialog', 'Notify'];

    function GoodsListController($scope, StockService, NgTableParams, blockUI, SweetAlert, ngDialog, Notify){

        $scope.tableParams = {
            searchStr: null,
            page : 1,
            count : 10
        }


        $scope.search = function(){
            $scope.goodsTableParams = new NgTableParams($scope.tableParams, {
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


        $scope.addNewGoods = function(){
            var dialog= ngDialog.open({
                template: 'app/views/custom/stock/goods/add-goods.html',
                controller: 'AddGoodsController',
                className: 'ngdialog-theme-default custom-width-800',
                data: {type:'add'}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.goodsTableParams.reload();
            });
        }

        $scope.editNewGoods = function(row){
            var dialog= ngDialog.open({
                template: 'app/views/custom/stock/goods/add-goods.html',
                controller: 'AddGoodsController',
                className: 'ngdialog-theme-default custom-width-800',
                data: {type:"edit", data:row}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.goodsTableParams.reload();
            });
        }


        //删除用户
        $scope.delete = function(id){
            SweetAlert.swal({
                title: '确认删除?',
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: '是',
                cancelButtonText: '否',
                closeOnConfirm: true,
                closeOnCancel: true
            }, function(isConfirm){
                if (isConfirm) {
                    //这里可以进行调试,查看$scope,因为table会创建一个子scope
                    //然后子scope里面就不能用this了,因为this就指向了子scope,
                    //实际上在table的每一行里面的点击是调用了父scope的delete方法
                    blockUI.start();
                    StockService.deleteGoods(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.goodsTableParams.reload();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        SweetAlert.error("网络问题, 请稍后重试!");
                    });
                }
            });
        }

        //$scope.openStockDetail = function(data){
        //    var dialog = ngDialog.open({
        //        template: 'app/views/custom/stock/goods/goods_detail.html',
        //        controller: 'GoodsDetailController',
        //        className: 'ngdialog-theme-default max-dialog',
        //        data: {id : data}
        //    });
        //    dialog.closePromise.then(function(data){
        //        if(data.value != 'reload'){
        //            return;
        //        }
        //        $scope.goodsTableParams.reload();
        //    })
        //}

    }

})();