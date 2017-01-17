(function () {
    'use strict';
    angular.module('custom')
            .filter('sexFilter', function () {
                return function (sex) {
                    if (sex == 0) {
                        return '男';
                    }
                    if (sex == 1) {
                        return '女';
                    }
                    return '未知';
                };
            })
            .filter('yearFilter', function(){
                return function(status){
                    if (status == 0){
                        return "-";
                    }
                    if (status == 1){
                        return "当前学年";
                    }
                    return '-';
                }
            })
})();
