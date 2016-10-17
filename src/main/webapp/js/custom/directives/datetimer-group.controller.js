(function () {
    'use strict';
    angular
        .module('custom')
        .directive('datepickerGroup', function () {

            var ret = {};
            ret.require = ['ngModel'];
            ret.restrict = 'E';
            ret.replace = true;
            ret.templateUrl = 'app/views/custom/directives/datetimer-group.html';
            ret.controller = DatepickerGroupController;
            var scope = {};
            ret.scope = scope;
            scope.value = "=ngModel";
            scope.showIcon = "@";
            scope.showTime = "@";

            return ret;
        })
        .controller('DatepickerGroupController', DatepickerGroupController);
    DatepickerGroupController.$inject = ['$scope', '$timeout'];
    function DatepickerGroupController($scope, $timeout) {
        if (!!$scope.value) {
            if ($scope.value.startDate) {
                $scope.value.startDate = new Date($scope.value.startDate);
            }
            if ($scope.value.endDate) {
                $scope.value.endDate = new Date($scope.value.endDate);
            }
        }

        $scope.datePicker = {
            opened: false
        };

        $scope.openDatePicker = function () {
            $scope.datePicker.opened = true;
        };

        $scope.endDatePicker = {
            opened: false
        };

        $scope.openEndDatePicker = function () {
            $scope.endDatePicker.opened = true;
        };

    }
})();


