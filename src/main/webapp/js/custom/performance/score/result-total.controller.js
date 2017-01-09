(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultTotalController', ResultTotalController);
    ResultTotalController.$inject = ['$scope', 'ScoreService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultTotalController($scope, ScoreService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

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
            //判断模板是否选择, 以及考试是否选择
            if(angular.isUndefined($scope.selectedExam)){
                SweetAlert.error("请选择考试!");
                return;
            }

            if(angular.isUndefined($scope.template)){
                SweetAlert.error("请选择模板!")
                return;
            }

            //both are ok , so we proceed .首先获取数据, 只加载一遍.
            blockUI.start();
            ScoreService.loadScoreTotalResult($scope.examId, $scope.classId).success(function (data) {
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



        //内部分析结果
        $scope.resultList = [];
        //显示表格
        $scope.showTables = function(){
            $scope.flag.show = 1;
            $scope.scoreTotalTableParams = new NgTableParams({}, {
                counts: [],
                dataset: $scope.resultList
            });
        }

        //显示图形
        $scope.showCharts = function(){
            $scope.flag.show = 2;
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            $scope.fullList = $scope.resultList.map(function(a){
                return a.full;
            });
            $scope.bestList = $scope.resultList.map(function(a){
                return a.best;
            });
            $scope.goodList = $scope.resultList.map(function(a){
                return a.good;
            });
            $scope.qualifyList = $scope.resultList.map(function(a){
                return a.qualified;
            });

            //处理几个list
            //指定图表的配置项和数据
            var option = {
                title: {
                    text: '整体信息'
                },
                toolbox: {
                    show : true,
                    feature : {
                        saveAsImage : {show: true}
                    }
                },
                tooltip: {},
                legend: {
                    data:['满分', '优秀', '良好', '及格']
                },
                xAxis: {
                    type: 'category',
                    data: $scope.courseList
                },
                yAxis: {data:'人数'},
                series: [
                    {
                        name: '满分',
                        type: 'bar',
                        data: $scope.fullList
                    },
                    {
                        name: '优秀',
                        type: 'bar',
                        data: $scope.bestList
                    },
                    {
                        name: '良好',
                        type: 'bar',
                        data: $scope.goodList
                    },
                    {
                        name: '及格',
                        type: 'bar',
                        data: $scope.qualifyList
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    }

})();