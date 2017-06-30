(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultTotalController', ResultTotalController);
    ResultTotalController.$inject = ['$scope', 'ScoreService', 'DicService', 'ExamService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultTotalController($scope, ScoreService, DicService, ExamService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.examTypeList = ['全部', '小测', '周考', '月考', '期中', '期末'];

        $scope.gradeList = [];
        $scope.classList = [];

        //初始化选择器列表
        function initSelector(){
            blockUI.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                    _.forEach($scope.gradeList, function(item){
                        item.classList.unshift({classId: 0, className:'全部'});
                    });
                }
                blockUI.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            });

            $scope.selectGrade = function(data){
                $scope.classList = data.classList;
                $scope.tableParams.classId = 0;
            }
        }

        initSelector();

        //data
        $scope.courseList = [];
        $scope.scoreList = [];

        //控制左边栏是否显示
        $scope.showLeftWindow = true;

        $scope.showOrHideLeftWindow = function () {
            $scope.showLeftWindow = !$scope.showLeftWindow;
        }

        //搜索考试
        $scope.tableParams = {};

        $scope.searchExam = function(){
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

        //控制右上角的班级列表, 文科班,理科班,单独班级,全年级等可以一起进行分析
        //控制左边栏参数填写
        //加载默认模板
        $scope.openTemplateDialog = function (type) {
            var dialog = ngDialog.open({
                template: 'app/views/custom/performance/template/template-list.html',
                controller: 'TemplateListController',
                className: 'ngdialog-theme-default max-dialog',
                data: {type: type}
            });
            dialog.closePromise.then(function (data) {
                if (!data.value.status) {
                    return;
                }
                $scope.template = data.value.value;
            });
        }

        /*******************************上面部分是公用的代码,主要负责考试选择,模板选择,年级选择************************************************************/

        //show 代表显示类型, type代表分析类型
        $scope.flag = {show: 1, type: 1};

        $scope.use = function(row){
            $scope.selectedExam = row;
        }

        //开始分析
        $scope.startAnalyse = function () {

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
            ScoreService.loadScoreTotalResult($scope.selectedExam.examId, $scope.flag.type, $scope.template.id).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    $scope.resultList = data.data;
                    $scope.show();
                } else {
                    SweetAlert.error("加载成绩列表发生了错误! 请刷新页面!");
                }
            }).error(function () {
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }


        $scope.show = function () {
            //强制默认显示表格
            $scope.showTables();
        }


        $scope.showClass = function () {
            $scope.flag.type = 1;
            $scope.startAnalyse();
        }

        $scope.showCourse = function () {
            $scope.flag.type = 2;
            $scope.startAnalyse();
        }

        //内部分析结果
        $scope.resultList = [];
        //显示表格
        $scope.showTables = function () {
            $scope.flag.show = 1;
        }

        //显示图形
        $scope.showColCharts = function () {
            $scope.flag.show = 2;
            // 基于准备好的dom，初始化echarts实例
            $scope.resultList.forEach(function (item, index) {
                if ($scope.flag.type == 1) {
                    var myChart = echarts.init(document.getElementById("colChart" + item.classId));
                    var list = $scope.resultList[index].courseTotalList;
                    var courseList = _.uniq(list.map(function (a) {
                        return a.name;
                    }));
                    //处理几个list
                    var fullList = list.map(function (a) {
                        return a.full;
                    });
                    var bestList = list.map(function (a) {
                        return a.best;
                    });
                    var goodList = list.map(function (a) {
                        return a.good;
                    });
                    var qualifyList = list.map(function (a) {
                        return a.qualified;
                    });
                    //指定图表的配置项和数据
                    var option = {
                        title: {
                            text: item.className
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
                            data: ['满分', '优秀', '良好', '及格']
                        },
                        xAxis: {
                            type: 'category',
                            data: courseList
                        },
                        yAxis: {},
                        series: [
                            {
                                name: '满分',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: fullList
                            },
                            {
                                name: '优秀',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: bestList
                            },
                            {
                                name: '良好',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: goodList
                            },
                            {
                                name: '及格',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: qualifyList
                            }
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                } else {
                    var myChart = echarts.init(document.getElementById("colChart" + item.courseName));
                    var list = $scope.resultList[index].classTotalList;
                    var classList = _.uniq(list.map(function (a) {
                        return a.name;
                    }));
                    //处理几个list
                    var fullList = list.map(function (a) {
                        return a.full;
                    });
                    var bestList = list.map(function (a) {
                        return a.best;
                    });
                    var goodList = list.map(function (a) {
                        return a.good;
                    });
                    var qualifyList = list.map(function (a) {
                        return a.qualified;
                    });
                    //指定图表的配置项和数据
                    var option = {
                        title: {
                            text: item.courseName
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
                            data: ['满分', '优秀', '良好', '及格']
                        },
                        xAxis: {
                            type: 'category',
                            data: classList
                        },
                        yAxis: {},
                        series: [
                            {
                                name: '满分',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: fullList
                            },
                            {
                                name: '优秀',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: bestList
                            },
                            {
                                name: '良好',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: goodList
                            },
                            {
                                name: '及格',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: qualifyList
                            }
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });
        }

        //显示百分比图
        $scope.showPieCharts = function (type) {
            $scope.flag.show = type;
            // 基于准备好的dom，初始化echarts实例
            $scope.resultList.forEach(function (item, index) {
                var myChart = echarts.init(document.getElementById("pieChart" + item.courseName));
                var list = $scope.resultList[index].classTotalList;
                var classList = _.uniq(list.map(function (a) {
                    return a.name;
                }));
                //处理几个list
                var dataList = [];
                var name = "";
                var subname = "";
                if(type == 3) {
                    dataList = list.map(function (a) {
                        return {name: a.name, value : a.full};
                    });
                    name = "满分饼图";
                    subname = "满分人数";
                }else if(type == 4){
                    dataList = list.map(function (a) {
                        return {name: a.name, value : a.best};
                    });
                    name = "优秀饼图";
                    subname = "优秀人数";
                }else if(type == 5){
                    dataList = list.map(function (a) {
                        return {name: a.name, value : a.good};
                    });
                    name = "良好饼图";
                    subname = "良好人数";
                }else if(type == 6){
                    dataList = list.map(function (a) {
                        return {name: a.name, value : a.qualified};
                    });
                    name = "及格饼图";
                    subname = "及格人数";
                }
                //指定图表的配置项和数据
                var option = {
                    title: {
                        text: item.courseName,
                        x: 'center',
                        subtext: subname
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            saveAsImage: {show: true}
                        }
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: classList
                    },
                    series: [
                        {
                            name: name,
                            type: 'pie',
                            radius : '75%',
                            center: ['50%', '60%'],
                            data:dataList,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            });
        }

    }

})();