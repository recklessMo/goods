(function() {
    'use strict';

    angular
        .module('custom', [
            'angular-ladda',
            'blockUI',
            'ngTable',
            'angucomplete-alt',
        ]).config(function(blockUIConfig){
        blockUIConfig.autoBlock = false;
        blockUIConfig.message = "正在加载..."
    });
})();