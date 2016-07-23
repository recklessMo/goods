(function () {
    'use strict';

    angular
        .module('custom')
        .service('StudentWorktableService', StudentWorktableService);

    StudentWorktableService.$inject = ['$http'];
    function StudentWorktableService($http) {

        this.searchUsers = function(obj) {
            return $http({
                method: "POST",
                url : "/v1/student/list",
                data:obj,
                timeout: 5000
            });
        }

    }
})
();
