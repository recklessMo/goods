(function () {
    'use strict';

    angular
        .module('custom', [
            'angular-ladda',
            'blockUI',
            'ngTable',
            'angucomplete-alt'
        ])
        .config(
            function (blockUIConfig) {
                blockUIConfig.autoBlock = false;
                blockUIConfig.message = "正在加载..."
            })
        .run(['NotifyService', '$rootScope',
            function (NotifyService, $rootScope) {
                if(window.__current != 0){
                    $rootScope.userId = window.__current;
                    $rootScope.orgId = window.__orgId;
                    $rootScope.orgName = window.__orgName;
                    NotifyService.initialize();
                }
            }]
        );
})();