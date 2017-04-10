(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultSelfController', ResultSelfController);
    ResultSelfController.$inject = ['$scope', 'ScoreService', 'DicService', 'StudentService', 'ExamService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultSelfController($scope, ScoreService, DicService, StudentService, ExamService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

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

        $scope.searchExam = function () {
            $scope.examTableParams = new NgTableParams({}, {
                counts: [],
                getData: function (params) {
                    blockUI.start();
                    return ExamService.loadExams({
                        searchStr: $scope.tableParams.examName,
                        page: params.page(),
                        count: 10
                    }).then(function (data) {
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

        $scope.use = function (item) {
            $scope.selectedExam = item;
        }

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

        //show 代表显示类型, type代表分析维度
        $scope.flag = {show: 0};
        //开始分析
        $scope.startAnalyse = function () {
            //判断模板是否选择, 以及考试是否选择
            if (angular.isUndefined($scope.selectedExam)) {
                SweetAlert.error("请选择考试!");
                return;
            }

            $scope.template = {id: 2};

            //if(angular.isUndefined($scope.template)){
            //    SweetAlert.error("请选择模板!")
            //    return;
            //}

            //both are ok , so we proceed .首先获取数据, 只加载一遍.
            $scope.show()
        }


        /*******************************上面部分是公用的代码,主要负责考试选择,模板选择,年级选择************************************************************/
        $scope.obj = {};

        $scope.studentTableParamsSetting = {
            page: 1,
            count: 12,
            examId: 0,
            searchStr: ''
        };

        $scope.show = function () {
            blockUI.start();
            $scope.flag.show = 1;
            $scope.studentTableParamsSetting.examId = $scope.selectedExam.examId;
            $scope.studentTableParamsSetting.searchStr = '';
            StudentService.searchStudentByExam($scope.studentTableParamsSetting).success(function (data) {
                var result = data;
                blockUI.stop();
                if (data.status == 200) {
                    $scope.studentList = result.data;
                    $scope.obj.totalCount = result.totalCount;
                    $scope.studentTableParams = new NgTableParams({}, {counts:[], dataset: $scope.studentList});
                } else {
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function () {
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }


        $scope.studentChooseList = [];

        $scope.addStudent = function (data) {
            if (!_.find($scope.studentChooseList, data)) {
                $scope.studentChooseList.push(data);
            }
        }

        $scope.deleteStudent = function (row) {
            $scope.studentChooseList = _.without($scope.studentChooseList, row);
        }

        //开始进行分析
        $scope.beginToAnalyse = function(){
            if($scope.studentChooseList.length == 0){
                SweetAlert.error("请至少选择一位学生进行分析");
                return;
            }

            blockUI.start();
            var sidList = _.map($scope.studentChooseList, function(item){
                return item.sid;
            });
            var params = {examId: $scope.selectedExam.examId, templateId:7, sidList: sidList};
            ScoreService.loadScoreSelf(params).success(function(data){
                blockUI.stop();
                if(data.status == 200){
                    $scope.resultObj = data.data;
                    $scope.showChart();
                }else{
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function(){
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }

        $scope.showChart = function(){
            var myChart = echarts.init(document.getElementById('chart'));
            var option = {
                title: {
                    text: '个人综合情况',
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
                    data: ['预算分配（Allocated Budget）', '实际开销（Actual Spending）']
                },
                radar: {
                    // shape: 'circle',
                    indicator: [
                        { name: '销售（sales）', max: 6500.7},
                        { name: '管理（Administration）', max: 16000},
                        { name: '信息技术（Information Techology）', max: 30000},
                        { name: '客服（Customer Support）', max: 38000},
                        { name: '研发（Development）', max: 52000},
                        { name: '市场（Marketing）', max: 25000}
                    ]
                },
                series: [{
                    name: '预算 vs 开销（Budget vs spending）',
                    type: 'radar',
                    // areaStyle: {normal: {}},
                    data : [
                        {
                            value : [4300, 10000, 28000, 35000, 50000, 19000],
                            name : '预算分配（Allocated Budget）'
                        },
                        {
                            value : [6500.1, 14000, 28000, 31000, 42000, 21000],
                            name : '实际开销（Actual Spending）'
                        }
                    ]
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }


    }

})();