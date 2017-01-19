(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultGapController', ResultGapController);
    ResultGapController.$inject = ['$scope', 'ScoreService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultGapController($scope, ScoreService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        //data
        $scope.courseList = [];
        $scope.scoreList = [];

        //控制左边栏是否显示
        $scope.showLeftWindow = true;

        $scope.showOrHideLeftWindow = function(){
            $scope.showLeftWindow = !$scope.showLeftWindow;
        }

        //控制左边栏考试选择
        $scope.remoteUrlRequestFn = function(str){
            return {str: str};
        }

        //控制右上角的班级列表, 文科班,理科班,单独班级,全年级等可以一起进行分析
        //$scope.classList = [];



        //控制左边栏参数填写

        //加载默认模板
        $scope.openTemplateDialog = function(type){
            var dialog= ngDialog.open({
                template: 'app/views/custom/performance/template/template-list.html',
                controller: 'TemplateListController',
                className: 'ngdialog-theme-default max-dialog',
                data : {type:type}
            });
            dialog.closePromise.then(function(data){
                if(!data.value.status){
                    return;
                }
                $scope.template = data.value.value;
            });
        }


        //开始分析
        $scope.flag = {show : 1};
        $scope.examId = 0;
        $scope.classId = 0;
        $scope.startAnalyse = function() {
            //todo 测试的时候先不管这些
            //判断模板是否选择, 以及考试是否选择
            //if(angular.isUndefined($scope.selectedExam)){
            //    SweetAlert.error("请选择考试!");
            //    return;
            //}
            //
            //if(angular.isUndefined($scope.template)){
            //    SweetAlert.error("请选择模板!")
            //    return;
            //}

            //both are ok , so we proceed .首先获取数据, 只加载一遍.
            blockUI.start();
            ScoreService.loadScoreGapResult($scope.examId, $scope.classId).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    $scope.resultList = data.data;
                    $scope.show();
                } else {
                    SweetAlert.error("加载成绩列表发生了错误! 请刷新页面!");
                }
            }).error(function () {
                blockUI.stop();
            });
        }


        $scope.show = function(){
            if($scope.flag.show == 1){
                $scope.showTables();
            }else{
                $scope.showCharts();
            }
        }


        /*******************************上面部分是公用的代码,主要负责考试选择,模板选择,年级选择************************************************************/
        //显示表格
        $scope.headList = [];
        $scope.tableList = [];
        $scope.showTables = function(){
            $scope.resultList.forEach(
                function(item) {
                    $scope.headList.push(item.gapList);
                    $scope.tableList.push(item.gapCount);
                }
            );
            $scope.flag.show = 1;
        }

        //显示图形
        $scope.showCharts = function(){
            $scope.flag.show = 2;
            // 基于准备好的dom，初始化echarts实例
            $scope.resultList.forEach(function(item) {
                var myChart = echarts.init(document.getElementById('chart' + item.name));
                var gapList = item.gapList.map(function(e){
                    return e.start+"-"+ e.end;
                });
                var gapInnerList = item.gapInnerList;
                var series = [];
                var legend = gapInnerList.map(function(e){
                    series.push({name:e.cname, type:"bar", data:e.countList});
                    return e.cname;
                });
                //处理几个list
                //指定图表的配置项和数据
                var option = {
                    title: {
                        text: item.name
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            saveAsImage: {show: true}
                        }
                    },
                    tooltip: { trigger: 'axis'},
                    legend: {
                        data: legend
                    },
                    xAxis: {
                        type: 'category',
                        data: gapList
                    },
                    yAxis: {data: '人数', type:'value'},
                    series: series
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            });
        }
    }

})();