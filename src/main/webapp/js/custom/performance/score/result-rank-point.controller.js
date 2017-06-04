(function () {
    'use strict';
    angular
        .module('custom')
        .controller('RankPointController', RankPointController);
    RankPointController.$inject = ['$scope', 'ExamService', 'ScoreService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function RankPointController($scope, ExamService, ScoreService, DicService, SweetAlert, NgTableParams, blockUI, Notify) {


        $scope.examTypeList = ['全部', '小测', '周考', '月考', '期中', '期末'];

        $scope.gradeList = [];
        $scope.classList = [];

        //初始化选择器列表
        function initSelector() {
            blockUI.start();
            DicService.loadAllGrade().success(function (data) {
                if (data.status == 200) {
                    $scope.gradeList = data.data;
                    _.forEach($scope.gradeList, function (item) {
                        item.classList.unshift({classId: 0, className: '全部'});
                    });
                }
                blockUI.stop();
            }).error(function () {
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            });

            $scope.selectGrade = function (data) {
                $scope.classList = data.classList;
                $scope.tableParams.classId = 0;
            }
        }

        initSelector();

        //控制左边栏是否显示
        $scope.showLeftWindow = true;

        $scope.showOrHideLeftWindow = function () {
            $scope.showLeftWindow = !$scope.showLeftWindow;
        }

        //搜索考试
        $scope.tableParams = {};

        $scope.searchExam = function () {
            $scope.examTableParams = new NgTableParams({}, {
                counts: [],
                getData: function (params) {
                    blockUI.start();
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = 10;
                    return ExamService.loadExams($scope.tableParams).then(function (data) {
                        blockUI.stop();
                        var result = data.data;
                        if (result.status == 200) {
                            params.total(result.totalCount);
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

        $scope.searchExam();


        /**业务逻辑**/
        $scope.obj = {};
        $scope.rankPointList = [];

        $scope.search = function (exam) {
            blockUI.start();
            $scope.obj.examId = exam.examId;
            $scope.obj.examName = exam.examName;
            ScoreService.loadRankPoint(exam.examId).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    $scope.rankPointList = data.data;
                    $scope.showChart();
                } else {
                    SweetAlert.error("发生了错误! 请刷新页面!");
                }
            }).error(function () {
                blockUI.stop();
                SweetAlert.error("网络异常! 请稍后重试！");
            });
        }

        $scope.flag = {};
        $scope.showChart = function () {
            $scope.rankPointList.forEach(function (item) {
                var legendList = item.rankPointInnerList.map(
                    function (temp, pos) {
                        return temp.className;
                    }
                );
                var dataList = item.rankPointInnerList.map(function (a) {
                    var single = {};
                    single.name = a.className;
                    var array = [];
                    a.rankPointPairList.forEach(function (pair, index) {
                        var temp = [];
                        temp.push(index);
                        temp.push(pair.rank);
                        temp.push(pair.score);
                        temp.push(pair.sid);
                        temp.push(pair.name);
                        array.push(temp);
                    });
                    single.type = "scatter";
                    single.data = array;
                    return single;
                });
                var x = "pointChart" + item.courseId;
                $scope.flag[x] = true;
                var myChart = echarts.init(document.getElementById(x));
                var option = {
                    title: {
                        text: item.courseName
                    },
                    tooltip : {
                        trigger: 'axis',
                        showDelay : 0,
                        formatter : function (params) {
                            if (params.value.length > 1) {
                                return params.seriesName + ' :<br/>'
                                    +' 名次:' +  params.value[1]
                                    +' 分数:' + params.value[2]
                                    +' 学号:' + params.value[3]
                                    +' 姓名:' + params.value[4];
                            }
                        },
                        axisPointer:{
                            show: true,
                            type : 'cross',
                            lineStyle: {
                                type : 'dashed',
                                width : 1
                            }
                        }
                    },
                    legend: {
                        data: legendList,
                        left: 'center'
                    },
                    xAxis : [
                        {
                            type : 'value',
                            scale:true,
                            axisLabel : {
                                formatter: '{value}'
                            }
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value',
                            scale:true,
                            axisLabel : {
                                formatter: '{value}名'
                            }
                        }
                    ],
                    series: dataList
                }
                myChart.setOption(option);
            });

        }
    }

})();