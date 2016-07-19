(function () {
    'use strict';
    angular
        .module('custom')
        .controller('AdmissionController', AdmissionController);
    AdmissionController.$inject = ['$filter', '$scope', 'AccountService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function AdmissionController($filter, $scope, AccountService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.tableParams = {page : 1, count : 10, sorting: {name: 'asc'}};

        $scope.data = [{name:'1', phone:4567, city:'广东'}, {name:'2', phone:124567, city:'湖北'}];

        $scope.activate = function() {
            $scope.admissionTableParams = new NgTableParams($scope.tableParams, {
                getData: function ($defer, params) {
                    blockUI.start();

                    var x = $filter('orderBy')($scope.data, params.orderBy());
                    params.total($scope.data.length);
                    $defer.resolve(x);
                    //AccountService.loadUsers(params.parameters()).success(function (data) {
                    //    if (data.status == 200) {
                    //        params.total(data.totalCount);
                    //        console.log(data);
                    //        $defer.resolve(data.data);
                    //        blockUI.stop();
                    //    }
                    //}).error(function () {
                    //    SweetAlert.error("网络问题,请稍后重试!");
                        blockUI.stop();
                    //});
                }
            });
        }

        $scope.activate();

        //删除用户
        $scope.delete = function(id){
        }


        //添加用户
        $scope.add = function(){
            $scope.data.push({name:"98", phone:3456, city:"xfda"});
            $scope.admissionTableParams.reload();
        }



    }
})();