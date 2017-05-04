(function () {
    'use strict';
    angular
        .module('custom')
        .controller('WechatMsgDialogController', WechatMsgDialogController);
    WechatMsgDialogController.$inject = ['$scope', 'WechatService', 'DicService', 'SweetAlert', 'NgTableParams','$timeout', 'blockUI', 'Notify'];

    function WechatMsgDialogController($scope, WechatService,DicService, SweetAlert, NgTableParams,$timeout, blockUI, Notify) {

        //入口
        $scope.$on("chooseStudent", function (event, data) {
            console.log(data);
            if(data.type == 'bind') {
                $scope.openId = data.openId;
                $scope.fetchMessages(1);
            }
        });

        $scope.$on("RECEIVE_WECHAT_MSG", function (event, data) {
            console.log(data);
            if($scope.openId == data.data){
                $scope.fetchMessages(1);
            }
        });

        $scope.message = {
            messageJson: {content: "", type: "text"},
            messageList: []
        };

        $scope.bindStatus = true;

        var tempDate = new Date();

        //点击查看图片或者视频
        //$scope.showMedia = function (msg) {
        //    var url = "";
        //    if (msg.messageJson.msgType === 'image') {
        //        url = "/wechat/getMedia?isVideo=0&mediaId=" + msg.messageJson.mediaId;
        //    }
        //    else if (msg.messageJson.msgType === 'shortvideo') {
        //        url = "/wechat/getMedia?isVideo=1&mediaId=" + msg.messageJson.mediaId;
        //    }
        //
        //    var dialog = ngDialog.open({
        //        template: '/views/wechat/wechat-media.html',
        //        controller: 'WechatMediaController',
        //        className: 'ngdialog-theme-default large-dialog show-media-box',
        //        resolve: {
        //            msgType: function () { return msg.messageJson.msgType; },
        //            url: function () { return url; }
        //        }
        //    });
        //};

        //拉取消息列表
        $scope.itemsPerPage = 20;
        $scope.fetchMessages = function (currentPage) {
            var params = {
                page: currentPage || 1,
                count: $scope.itemsPerPage,
                openId: _.get($scope, "openId", "")
            };
            blockUI.start();
            WechatService.loadWechatMsg(params).success(function (data) {
                blockUI.stop();
                if(data.status == 200) {
                    $scope.totalItems = data.totalCount;
                    data.data.sort(function (a, b) {
                        return a.created - b.created;
                    });
                    $scope.message.messageList = data.data;
                    $timeout(function () {
                        $scope.scrollToBottom();
                    }, 200);
                }else{
                    SweetAlert.error("服务器异常!");
                }
            }).error(function () {
                blockUI.stop();
                Notify.alert('网络受限,请稍后重试...', {status: 'danger', pos: 'top-right', timeout: 1000});
            });
        };

        //发送消息
        $scope.msgTextarea = {};
        $scope.sendMessage = function () {
            if ($scope.msgTextarea.content === "") {
                return;
            }
            $scope.message.openId = $scope.openId;
            $scope.message.message = $scope.msgTextarea.content;
            $scope.msgTextarea = {};
            // 医生发送消息
            blockUI.start();
            WechatService.sendWechatMsg($scope.message).success(function (data) {
                // 将医生发送的消息显示
                $scope.fetchMessages(1);
                $timeout(function(){
                    $scope.scrollToBottom();
                },500)
                blockUI.stop();
            });
        };


        //滚动到最新消息
        $scope.scrollToBottom = function () {
            var scrollElement = document.getElementById("wechatMessageList");
            if (scrollElement) {
                scrollElement.scrollTop = scrollElement.scrollHeight;
            }
        };

    }
})();