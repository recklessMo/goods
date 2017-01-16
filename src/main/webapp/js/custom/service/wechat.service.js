(function () {
    'use strict';

    angular
        .module('custom')
        .service('WechatService', WechatService);

    WechatService.$inject = ['$http'];
    function WechatService($http) {

        this.loadWechatRecentUser = loadWechatRecentUser;
        this.loadWechatAllUser = loadWechatAllUser;

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


    }
})
();
