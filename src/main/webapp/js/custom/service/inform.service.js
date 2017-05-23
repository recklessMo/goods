(function () {
    'use strict';

    angular
        .module('custom')
        .service('InformMessageService', InformMessageService);

    InformMessageService.$inject = ['$http'];
    function InformMessageService($http) {

        this.listInformMessage = listInformMessage;
        this.addInformMessage = addInformMessage;
        this.deleteInformMessage = deleteInformMessage;

        function listInformMessage(data){
            return $http({
                method: "POST",
                url : "/v1/informMessage/list",
                data: data,
                timeout: 10000
            });
        }

        function addInformMessage(data){
            return $http({
                method: "POST",
                url : "/v1/informMessage/add",
                data:data,
                timeout: 10000
            });
        }

        function deleteInformMessage(id){
            return $http({
                method: "POST",
                url : "/v1/informMessage/delete",
                data:id,
                timeout: 10000
            });
        }

    }
})
();
