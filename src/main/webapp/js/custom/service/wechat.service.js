(function () {
    'use strict';

    angular
        .module('custom')
        .service('WechatService', WechatService);

    WechatService.$inject = ['$http'];
    function WechatService($http) {

        this.loadWechatRecentUser = loadWechatRecentUser;
        this.loadWechatAllUser = loadWechatAllUser;

        this.loadWechatMsg = loadWechatMsg;
        this.sendWechatMsg = sendWechatMsg;

        function loadWechatRecentUser(data){
            return $http({
                method: "POST",
                data: data,
                url: "/v1/wechat/user/recent/list",
                timeout: 5000
            });
        }

        function loadWechatAllUser(data){
            return $http({
                method: "POST",
                data: data,
                url: "/v1/wechat/user/all/list",
                timeout: 5000
            });
        }

        function loadWechatMsg(data){
            return $http({
                method: "POST",
                data: data,
                url: "/v1/wechat/message/list",
                timeout: 5000
            });
        }

        function sendWechatMsg(data){
            return $http({
                method: "POST",
                data: data,
                url: "/v1/wechat/message/add",
                timeout: 5000
            });
        }


    }
})
();
