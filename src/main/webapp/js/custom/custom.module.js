(function() {
    'use strict';

    angular
        .module('custom', [
            'angular-ladda',
            'blockUI',
            //因为需要修改这个开源组件,所以我们以lib方式引入进来,然后进行修改
            'angularBootstrapNavTree'
        ]).config(function(blockUIConfig){
        blockUIConfig.autoBlock = false;
        blockUIConfig.message = "正在加载..."
    });
})();