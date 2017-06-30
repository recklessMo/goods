(function () {
    'use strict';

    angular
        .module('custom')
        .service('AssignmentService', AssignmentService);

    AssignmentService.$inject = ['$http'];
    function AssignmentService($http) {

        this.listAssignments = listAssignments;
        this.addAssignment = addAssignment;
        this.deleteAssignment = deleteAssignment;

        function listAssignments(data){
            return $http({
                method: "POST",
                url : "/v1/assignment/list",
                data: data,
                timeout: 10000
            });
        }

        function addAssignment(data){
            return $http({
                method: "POST",
                url : "/v1/assignment/add",
                data:data,
                timeout: 10000
            });
        }

        function deleteAssignment(id){
            return $http({
                method: "POST",
                url : "/v1/assignment/delete",
                data:id,
                timeout: 10000
            });
        }

    }
})
();
