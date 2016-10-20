(function () {
    'use strict';
    angular
        .module('custom')
        .directive('sisDatepicker', function () {

            var ret = {};
            ret.require = ['ngModel'];
            ret.restrict = 'E';
            ret.replace = true;
            ret.templateUrl = 'app/views/custom/directives/sis-datetimer.html';
            ret.controller = SisDatepickerController;
            var scope = {};
            ret.scope = scope;
            scope.value = "=ngModel";
            scope.showIcon = "@";
            scope.showTime = "@";

            return ret;
        })
        .controller('SisDatepickerController', SisDatepickerController);
    SisDatepickerController.$inject = ['$scope', '$timeout'];
    function SisDatepickerController($scope, $timeout) {
        if ($scope.value) {
            $scope.value = new Date($scope.value);
        }

        $scope.datePicker = {
            opened: false
        };

        $scope.openDatePicker = function () {
            $scope.datePicker.opened = true;
        };
    }

    //对日期补0
    //1988-3-2 => 1988-03-02
    angular.module('custom').directive('autoFillZeroInDate', function () {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                element.on('blur', function () {
                    var self = this;
                    var val = _.trim(self.value);
                    if (val.length < 1) return;
                    //如果检测到月份或日期忘记补零
                    if (!/^\d{4}-\d-\d$/.test(val) || !/^\d{4}-\d-\d\d$/.test(val) || !/^\d{4}-\d\d-\d$/.test(val)) {
                        self.value = val.replace(/-(\d)\b/g, '-0$1');
                        element.trigger('change');
                    }
                })
            }
        }
    });


})();


