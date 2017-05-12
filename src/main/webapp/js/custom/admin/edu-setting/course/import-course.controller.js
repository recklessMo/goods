(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ImportCourseController', ImportCourseController);
    ImportCourseController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'blockUI', 'NgTableParams', 'Notify'];

    function ImportCourseController($scope, SettingService, SweetAlert, blockUI, NgTableParams, Notify) {

        var block = blockUI.instances.get("import-course");

        $scope.tableParams = {
            page: 1,
            count: 500
        }

        $scope.courseList = [];

        activate();

        function activate() {
            $scope.allCourseTableParams = new NgTableParams($scope.tableParams, {
                counts: [],
                getData: function (params) {
                    block.start();
                    return SettingService.listStandardCourse({
                        page: params.page(),
                        count: params.count()
                    }).then(function (data) {
                        block.stop();
                        var result = data.data;
                        if (result.status == 200) {
                            params.total(result.totalCount);
                            $scope.courseList = result.data;
                            return result.data;
                        } else {
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }, function () {
                        SweetAlert.error("网络异常, 请稍后重试!");
                        block.stop();
                    });
                }
            })
        }

        $scope.changeImportStatus = function (course) {
            course.isSelected = !course.isSelected;
        }

        $scope.loading = false;

        $scope.save = function () {
            var temp = [];
            $scope.courseList.forEach(function(item, index, arr){
                if(item.isSelected){
                    temp.push(item);
                }
            });
            if(temp.length == 0){
                SweetAlert.error("未选择科目!");
                return;
            }
            SettingService.importCourse(temp).success(function (data) {
                $scope.loading = false;
                block.stop();
                if (data.status == 200) {
                    Notify.alert("导入成功!", {status:"success", timeout: 3000});
                    $scope.closeThisDialog('reload');
                } else {
                    //更新失败的情况
                    SweetAlert.error("服务器异常,请稍后重试");
                }
            }).error(function () {
                SweetAlert.error("网络问题,稍后重试!");
                $scope.loading = false;
                block.stop();
            });
        }

    }
})();