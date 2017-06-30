/**
 * Created by yyq on 17/6/30.
 */
(function () {
    'use strict';

    angular
        .module('custom')
        .service('QuestionsetService', QuestionsetService);

    QuestionsetService.$inject = ['$http'];
    function QuestionsetService($http) {

        this.listQuestion = listStudent;

        this.addQuestion = addStudent;
        //this.searchQuestionByName = searchQuestionByName;
        //this.getSingleStudentInfo = getSingleStudentInfo;
        //this.saveSingleStudentInfo = saveSingleStudentInfo;
        //this.loadScoreListBySid = loadScoreListBySid;

        function listStudent(data) {
            return $http({
                method: "POST",
                url: "/v1/questionset/list",
                data: data,
                timeout: 10000
            });
        }

        function addStudent(data) {
            return $http({
                method: "POST",
                url: "/v1/questionset/add",
                data: data,
                timeout: 10000
            });
        }
    }
})
();
