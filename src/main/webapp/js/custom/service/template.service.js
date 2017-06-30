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
        this.makeDefaultTemplate = makeDefaultTemplate;

        function makeDefaultTemplate(id, type){
            return $http({
                method: "POST",
                data: $.param({id: id, type: type}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                timeout: 10000,
                url: "/v1/template/makeDefault"
            });
        }

        function addTemplate(data){
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/template/add"
            });
        }

        function deleteTemplate(data){
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/template/delete"
            });
        }

        function loadTemplates(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 10000,
                url: "/v1/template/list"
            });
        }

    }
})
();
