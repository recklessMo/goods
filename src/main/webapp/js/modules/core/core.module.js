(function() {
    'use strict';

    angular
        .module('app.core', [
            //装载一下templates,这样就能使用缓存了
            'templates',
            'ngRoute',
            'ngAnimate',
            'ngStorage',
            'ngCookies',
            'pascalprecht.translate',
            'ui.bootstrap',
            'ui.router',
            'oc.lazyLoad',
            'cfp.loadingBar',
            'ngSanitize',
            'ngResource',
            'tmh.dynamicLocale',
            'ui.utils'
        ]);
})();