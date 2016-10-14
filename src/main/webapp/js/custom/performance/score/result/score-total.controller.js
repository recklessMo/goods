(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScoreTotalController', ScoreTotalController);
    ScoreTotalController.$inject = ['$scope', 'ScoreService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function ScoreTotalController($scope, ScoreService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.examId = 0;
        //所有的数据,包括整体的和各班级的
        $scope.totalScoreList = [];
        //当前需要显示的部分
        $scope.scoreList = [];

        //显示标志
        $scope.flag = {};
        $scope.flag.show = 1;

        //后续需要加上排序以及过滤的一系列逻辑.
        $scope.showTables = function(){
            $scope.flag.show = 1;
            $scope.scoreTotalTableParams = new NgTableParams({}, {
                counts: [],
                getData: function ($defer, params) {
                    $defer.resolve($scope.scoreList);
                }
            });
        }


        $scope.activate = function() {
            ScoreService.loadTotalScore($scope.examId).success(function(data){
                blockUI.stop();
                if(data.status == 200){
                    $scope.scoreList = data.data;
                    $scope.showTables();
                }else{
                    SweetAlert.error("发生了错误! 请刷新页面!");
                }
            }).error(function(){
                blockUI.stop();
            });
        }


        $scope.activate();

        $scope.showCharts = function(){
            $scope.flag.show = 2;
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            $scope.fullList = $scope.scoreList.map(function(a){
                return a.full;
            });
            $scope.bestList = $scope.scoreList.map(function(a){
                return a.best;
            });
            $scope.goodList = $scope.scoreList.map(function(a){
                return a.good;
            });
            $scope.qualifyList = $scope.scoreList.map(function(a){
                return a.qualified;
            });

            //处理几个list

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '统计信息'
                },
                tooltip: {},
                legend: {
                    data:['满分', '优秀', '良好', '及格']
                },
                xAxis: {
                    type: 'category',
                    data: ['语文', '数学', '英语', '政治', '历史', '地理', '物理', '化学', '生物']
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