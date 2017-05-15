(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScoreTrendController', ScoreTrendController);
    ScoreTrendController.$inject = ['$scope', 'ExamService', 'ScoreService', 'StudentService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function ScoreTrendController($scope, ExamService, ScoreService, StudentService, DicService, SweetAlert, NgTableParams, blockUI, Notify) {

        //控制左边栏是否显示
        $scope.showLeftWindow = true;
        $scope.showOrHideLeftWindow = function () {
            $scope.showLeftWindow = !$scope.showLeftWindow;
        }
        $scope.tableParams = {};
        $scope.obj = {};
        $scope.searchStudent = function () {
            $scope.studentTableParams = new NgTableParams({}, {
                counts: [],
                getData: function (params) {
                    blockUI.start();
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = params.count();
                    return StudentService.listStudent($scope.tableParams).then(function (data) {
                        var result = data.data;
                        blockUI.stop();
                        if (result.status == 200) {
                            params.total(result.totalCount);
                            $scope.obj.totalCount = result.totalCount;
                            return result.data;
                        } else {
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }, function () {
                        SweetAlert.error("网络问题,请稍后重试!");
                        blockUI.stop();
                    });
                }
            });
        }

        $scope.searchStudent();


        /*************************************业务逻辑****************************************/
        $scope.flag = {show: 1};

        $scope.choosedStudent = {};
        $scope.choose = function (data) {
            $scope.choosedStudent = data;
            $scope.startAnalyseTable();
        }

        $scope.examTypeList = ['小测', '周考', '月考', '模考', '期中', '期末'];

        $scope.analyseParams = {examTypes: ['小测', '周考', '月考', '模考', '期中', '期末'], showType: '表格'};


        $scope.labelList = [];
        $scope.scoreList = [];

        $scope.startAnalyseTable = function () {
            $scope.flag.show = 1;
            $scope.analyseParams.sid = $scope.choosedStudent.sid;
            $scope.analyseParams.showType = 1;
            if (_.isUndefined($scope.analyseParams.sid)) {
                SweetAlert.error("请选择要查询成绩的学生!");
                return;
            }
            if ($scope.analyseParams.examTypes.length == 0) {
                SweetAlert.error("请选择需要分析的考试类型!");
                return;
            }
            blockUI.start();
            ScoreService.loadScoreTrend($scope.analyseParams).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    if (_.isUndefined(data.data.dataList) || data.data.dataList.length == 0) {
                        Notify.alert("未找到考试数据!", {status: "success", timeout: 3000});
                        $scope.labelList = [];
                        $scope.scoreList = [];
                    } else {
                        $scope.labelList = data.data.labelList;
                        $scope.scoreList = data.data.dataList;
                    }
                    $scope.showTables();
                } else {
                    SweetAlert.error("加载成绩列表发生了错误! 请刷新页面!");
                }
            }).error(function () {
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }

        $scope.startAnalyseChart = function (type) {
            $scope.flag.show = type;
            $scope.analyseParams.sid = $scope.choosedStudent.sid;
            $scope.analyseParams.showType = type;
            if (_.isUndefined($scope.analyseParams.sid)) {
                SweetAlert.error("请选择要查询成绩的学生!");
                return;
            }
            if ($scope.analyseParams.examTypes.length == 0) {
                SweetAlert.error("请选择需要分析的考试类型!");
                return;
            }
            blockUI.start();
            ScoreService.loadScoreTrend($scope.analyseParams).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    if (_.isUndefined(data.data.dataList) || data.data.dataList.length == 0) {
                        Notify.alert("未找到考试数据!", {status: "success", timeout: 3000});
                        $scope.data = {}
                    } else {
                        $scope.result = data.data;
                    }
                    $scope.showCharts(type);
                } else {
                    SweetAlert.error("加载成绩列表发生了错误! 请刷新页面!");
                }
            }).error(function () {
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }

        $scope.showTables = function () {
            $scope.scoreListTableParams = new NgTableParams({page: 1, count: 10},
                {
                    counts: [],
                    dataset: $scope.scoreList
                }
            );
        }

        $scope.showCharts = function (type) {
            var myChart = echarts.init(document.getElementById("chart" + type));
            var series = [];
            $scope.result.dataList.map(function(singleList, index){
                series.push({name:$scope.result.typeList[index], type:"line", data:singleList,label:{
                    normal: {
                        show: true,
                        position: 'top'
                    }
                }});
            });
            //指定图表的配置项和数据
            var option = {
                title: {
                    text: "折线趋势图"
                },
                toolbox: {
                    show: true,
                    feature: {
                        saveAsImage: {show: true},
                        magicType : {show: true, type: ['line', 'bar']}
                    }
                },
                tooltip: {},
                legend: {
                    data: $scope.result.typeList
                },
                xAxis: {
                    type: 'category',
                    data: $scope.result.xList
                },
                yAxis: {
                    inverse: type != 2
                },
                series: series
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }

    }

})();