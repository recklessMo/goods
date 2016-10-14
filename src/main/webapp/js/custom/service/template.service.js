(function () {
    'use strict';

    angular
        .module('custom')
        .service('TemplateService', TemplateService);

    TemplateService.$inject = ['$http'];
    function TemplateService($http) {

        this.addTemplate = addTemplate;
        this.deleteTemplate = deleteTemplate;
        this.loadTemplates = loadTemplates;

        function addTemplate(data){
            return $http({
                method: "POST",
                data: data,
                timeout: 5000,
                url: "/v1/template/add"
            });
        }

        function deleteTemplate(data){
            return $http({
                method: "POST",
                data: data,
                timeout: 5000,
                url: "/v1/template/delete"
            });
        }

        function loadTemplates(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 5000,
                url: "/v1/template/list"
            });
        }

    }
})
();
