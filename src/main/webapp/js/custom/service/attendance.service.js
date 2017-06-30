(function () {
    'use strict';

    angular
        .module('custom')
        .service('AttendanceService', AttendanceService);

    AttendanceService.$inject = ['$http'];
    function AttendanceService($http) {

        this.getAttendanceList = getAttendanceList;
        this.addAttendance = addAttendance;
        this.updateAttendance = updateAttendance;
        this.deleteAttendance = deleteAttendance;

        function getAttendanceList(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/attendance/list"
            });
        }

        function addAttendance(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/attendance/add"
            });
        }

        function updateAttendance(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/attendance/update"
            });
        }

        function deleteAttendance(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/attendance/delete"
            });
        }

    }
})
();
