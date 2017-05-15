(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScoreContrastController', ScoreContrastController);
    ScoreContrastController.$inject = ['$scope', 'ExamService', 'ScoreService', 'StudentService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function ScoreContrastController($scope, ExamService, ScoreService, StudentService, DicService, SweetAlert, NgTableParams, blockUI, Notify) {

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

        $scope.studentChooseList = [];

        $scope.addStudent = function (data) {
            if (!_.find($scope.studentChooseList, data)) {
                $scope.studentChooseList.push(data);
                $scope.startAnalyse(1);
            }
        }

        $scope.deleteStudent = function (row) {
            $scope.studentChooseList = _.without($scope.studentChooseList, row);
        }

        $scope.flag = {show: 1};

        $scope.choose = function (data) {
            $scope.choosedStudent = data;
            $scope.startAnalyse();
        }

        $scope.examTypeList = ['小测', '周考', '月考', '模考', '期中', '期末'];
        $scope.analyseParams = {examTypes: ['小测', '周考', '月考', '模考', '期中', '期末']};

        $scope.labelList = [];
        $scope.scoreList = [];

        $scope.startAnalyse = function (type) {
            $scope.flag.show = type;
            $scope.analyseParams.sidList = _.map($scope.studentChooseList, function(e){
                return e.sid;
            });
            $scope.analyseParams.showType = type;
            if (_.isUndefined($scope.analyseParams.sidList) || $scope.analyseParams.sidList.length == 0) {
                SweetAlert.error("请选择要查询成绩的学生!");
                return;
            }
            if ($scope.analyseParams.examTypes.length == 0) {
                SweetAlert.error("请选择需要分析的考试类型!");
                return;
            }
            blockUI.start();
            ScoreService.loadScoreContrast($scope.analyseParams).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    if (_.isUndefined(data.data.dataList) || data.data.dataList.length == 0) {
                        Notify.alert("未找到考试数据!", {status: "success", timeout: 3000});
                        $scope.data = {}
                    } else {
                        if(type == 1) {
                            $scope.labelList = data.data.labelList;
                            $scope.scoreList = data.data.dataList;
                            $scope.showTables();
                        }else if(type == 2){
                            $scope.result = data.data;
                            $scope.showCharts(type);
                        }
                    }
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
            var option = {
                title: {
                    text: '综合对比',
                    x: 'left'
                },
                toolbox: {
                    show: true,
                    feature: {
                        saveAsImage: {show: true}
                    }
                },
                tooltip: {},
                legend: {
                    data: $scope.result.nameList
                },
                radar: {
                    // shape: 'circle',
                    indicator: $scope.result.typeList
                },
                series: [{
                    name: '预算 vs 开销（Budget vs spending）',
                    type: 'radar',
                    itemStyle: {normal: {areaStyle: {type: 'default'}}},
                    data : $scope.result.dataList
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }

    }

})();