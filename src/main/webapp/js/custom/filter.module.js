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
        .filter('courseTypeFilter', function(){
            return function(type){
                if (type == 1){
                    return "主科";
                }else if(type == 2){
                    return "文科";
                }else{
                    return "理科";
                }
            }
        })
        .filter('scheduleFilter', function(){
            return function(time){
                var hour = Math.floor(time / 60);
                var minute = time % 60;
                var hourStr = "" + hour;
                var minuteStr = "" + minute;
                if(hour < 10){
                    hourStr = "0" + hour;
                }
                if(minute < 10){
                    minuteStr = "0" + minute;
                }
                return hourStr + ":" + minuteStr;
            }
        })
        .filter('examTypeFilter', function(){
            return function(type){
                if(type == 0){

                }
            }
        })
        .filter('templateTypeFilter', function(){
            return function(type){
                if(type == 1){
                    return "整体分析模板";
                }else if(type == 2){
                    return "分数段分析模板";
                }else{
                    return "名次段分析模板";
                }
            }
        })
})();
