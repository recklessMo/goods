(function(){

    angular.module("custom")
        .controller("StockWorktableController", StockWorktableController);

    StockWorktableController.$inject = ['$scope', 'StockService'];

    function StockWorktableController($scope, StockService){

    }

})();