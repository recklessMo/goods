(function() {
    'use strict';

    angular
        .module('app.sidebar')
        .service('SidebarLoader', SidebarLoader);

    SidebarLoader.$inject = ['$http'];
    function SidebarLoader($http) {
        this.getMenu = getMenu;

        ////////////////

        function getMenu(onReady, onError) {
          var menuURL  = 'v1/system/menu';
          var menuURLTest = 'server/sidebar-menu.json';
          onError = onError || function() { alert('Failure loading menu'); };
          $http
            .get(menuURL)
            .success(onReady)
            .error(onError);
        }
    }
})();