(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditInformMessageController', EditInformMessageController);
    EditInformMessageController.$inject = ['$scope', 'InformMessageService', 'DicService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function EditInformMessageController($scope, InformMessageService, DicService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.informTypeList = ['放假通知', '家长会通知', '成绩通知'];

        $scope.type = $scope.ngDialogData.type;
        $scope.informMessage = $scope.type == 'add'? {} : $scope.ngDialogData.informMessage;

        $scope.gradeList = [];
        $scope.classList = [];
        $scope.courseList = [];

        var block = blockUI.instances.get("edit-inform");

        //初始化选择器列表
        function initSelector(){
            block.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                    //添加全部，因为bootstrap引用出了问题
                    _.forEach($scope.gradeList, function(item){
                        item.classList.unshift({classId: 0, className:'全部'});
                    });
                    $scope.gradeList.unshift({gradeId: 0, gradeName:'全部', classList:[]});
                }
                block.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                block.stop();
            });

            $scope.selectGrade = function(data){
                $scope.classList = data.classList;
                $scope.informMessage.classId = 0;
            }
        }

        initSelector();

        $scope.save = function(informMessage){

            if(_.isEmpty(informMessage.text) || _.isEmpty(informMessage.name)
            || _.isEmpty(informMessage.type)){
                SweetAlert.error("请填写必填字段！");
                return;
            }

            block.start();
            InformMessageService.addInformMessage(informMessage).success(function(data){
                if(data.status == 200){
                    Notify.alert("保存成功!", {status:"success", timeout: 3000});
                    $scope.closeThisDialog('reload');
                }
                block.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                block.stop();
            })
        }

        //日期
        $scope.today = function() {
            $scope.dt = new Date();
        };

        $scope.clear = function() {
            $scope.dt = null;
        };

        $scope.open = function() {
            $scope.popup.opened = true;
        };

        $scope.popup = {
            opened: false
        };


    }
})();