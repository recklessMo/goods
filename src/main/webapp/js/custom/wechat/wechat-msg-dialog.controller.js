(function () {
    'use strict';
    angular
        .module('custom')
        .controller('WechatMsgDialogController', WechatMsgDialogController);
    WechatMsgDialogController.$inject = ['$scope', 'WechatService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function WechatMsgDialogController($scope, WechatService,DicService, SweetAlert, NgTableParams, blockUI, Notify) {


        //入口
        $scope.$on("chooseStudent", function (event, data, shouldGetMsg) {
            $scope.openId = data;
            $scope.fetchMessages(1);
        });


        $scope.message = {
            messageJson: {content: "", type: "text"},
            messageList: []
        };

        $scope.bindStatus = true;
        $scope.itemsPerPage = 20;
        $scope.msgTextarea = {};

        var tempDate = new Date();
        $scope.todayTime = new Date(tempDate.getFullYear()+'/'+(tempDate.getMonth()+1)+'/'+tempDate.getDate()).getTime(); //当天零点的时间


        //点击查看图片或者视频
        $scope.showMedia = function (msg) {
            var url = "";
            if (msg.messageJson.msgType === 'image') {
                url = "/wechat/getMedia?isVideo=0&mediaId=" + msg.messageJson.mediaId;
            }
            else if (msg.messageJson.msgType === 'shortvideo') {
                url = "/wechat/getMedia?isVideo=1&mediaId=" + msg.messageJson.mediaId;
            }

            var dialog = ngDialog.open({
                template: '/views/wechat/wechat-media.html',
                controller: 'WechatMediaController',
                className: 'ngdialog-theme-default large-dialog show-media-box',
                resolve: {
                    msgType: function () { return msg.messageJson.msgType; },
                    url: function () { return url; }
                }
            });
        };



        //拉取消息列表
        $scope.fetchMessages = function (bigCurrentPage) {
            var params = {
                page: bigCurrentPage || 1,
                count: $scope.itemsPerPage ,
                sid: _.get($scope, "sid", -1)
            };
            WechatService.getMessages(params).success(function (data) {
                if (!Array.isArray(data.data)) {
                    return;
                }
                data.data.sort(function (a, b) {
                    return a.created - b.created;
                });
                $scope.bigTotalItems = _.get(data, "page.totalCount");
                $scope.message.messageList = data.data;
                $timeout(function () {
                    $scope.scrollToBottom();
                }, 500);
            }).error(function () {
                Notify.alert('网络受限,请稍后重试...', {status: 'danger', pos: 'top-right', timeout: 1000});
            });
        };

        //发送消息
        $scope.sendMsg = function () {
            if ($scope.msgTextarea.content === "") {
                return;
            }
            $scope.message.patientId = $scope.patientId;
            $scope.message.messageJson.content = $scope.msgTextarea.content;
            $scope.msgTextarea.content = "";
            // 医生发送消息
            WechatService.sendMsg($scope.message).success(function (data) {
                // 将医生发送的消息显示
                $scope.fetchMessages();
                $timeout(function(){
                    $scope.scrollToBottom();
                },500)
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