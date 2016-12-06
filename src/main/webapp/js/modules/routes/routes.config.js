/**=========================================================
 * Module: config.js
 * App routes and resources configuration
 =========================================================*/


(function() {
    'use strict';

    angular
        .module('app.routes')
        .config(routesConfig);

    routesConfig.$inject = ['$stateProvider', '$locationProvider', '$urlRouterProvider', 'RouteHelpersProvider'];
    function routesConfig($stateProvider, $locationProvider, $urlRouterProvider, helper){

        // Set the following to true to enable the HTML5 Mode
        // You may have to set <base> tag in index and a routing configuration in your server
        $locationProvider.html5Mode(false);

        // defaults to dashboard
        $urlRouterProvider.otherwise('/app');

        //
        // Application Routes
        // -----------------------------------

        //下面两个state用于负责主页的内容,主要包括左边栏,上边栏等.
        $stateProvider
      .state('app', {
          url: '/app',
          templateUrl: helper.basepath('app.html'),
          resolve: helper.resolveFor('fastclick', 'modernizr', 'ui.select', 'icons', 'screenfull', 'animo', 'sparklines', 'slimscroll', 'classyloader', 'toaster', 'whirl', 'oitozero.ngSweetAlert', 'ngDialog')
      })
      .state('app.admission', {
          url: '/admission',
          title: 'admission',
          templateUrl: helper.basepath('custom/admission/admission-worktable.html')
      })
        .state('app.admission-manage', {
        url: '/admission-manage',
        title: 'admission-manage',
        templateUrl: helper.basepath('custom/admission/admission-manage.html')
      })
      .state('app.student-view-worktable', {
          url: '/student-view-worktable',
          title: '学生管理',
          templateUrl: helper.basepath('custom/student/list/student-view-worktable.html')
      })
        .state('app.student-add-worktable', {
            url: '/student-add-worktable',
            title: '学生录入',
            templateUrl: helper.basepath('custom/student/add/student-add-worktable.html')
        })
        .state('app.item', {
            url: '/item',
            title: '物资',
            templateUrl: helper.basepath('custom/stock/goods/goods-list.html')
        })
      .state('app.stock-worktable', {
         url: '/stock-worktable',
         title: '库存',
         templateUrl: helper.basepath('custom/stock/stock-worktable.html')
      })
      .state('app.student-add', {
            url: '/student-add',
            title: '学生导入',
            controller: 'StudentAddController',
            templateUrl: helper.basepath('custom/student/student-add.html'),
            resolve: helper.resolveFor('xeditable')
      })
          .state('app.account', {
              url: '/account',
              title: '帐号管理',
              templateUrl: helper.basepath('custom/admin/account/account.html')
          })
        .state('app.role', {
            url: '/role',
            title: '角色管理',
            templateUrl: helper.basepath('custom/admin/role/role-list.html')
        })
        .state('app.exam', {
            url: '/exam',
            title: '考试管理',
            templateUrl: helper.basepath('custom/performance/exam/exam.html')
        })
        .state('app.analyze-score', {
            url: '/analyze-score',
            title: '成绩分析',
            templateUrl: helper.basepath('custom/performance/score/analyze-result.html'),
            resolve: helper.resolveFor('angularFileUpload', 'filestyle')
        })
        .state('app.analyze-template', {
            url: '/analyze-template',
            title: '分析模版',
            templateUrl: helper.basepath('custom/performance/template/template-list.html'),
        })
        .state('app.edu-setting', {
            url: '/edu-setting',
            title: '教务设置',
            templateUrl: helper.basepath('custom/admin/edu-setting/edu-setting-worktable.html')
        })
        .state('app.todo', {
            url: '/todo',
            title: '待办事项',
            templateUrl: helper.basepath('custom/self/todo.html'),
            controller: 'TodoController'
        })

      //
      // Single Page Routes
      // -----------------------------------
      .state('page', {
          url: '/page',
          templateUrl: helper.basepath('page.html'),
         // resolve: helper.resolveFor('icons'),
          controller: ['$rootScope', function($rootScope) {
              $rootScope.app.layout.isBoxed = false;
          }]
      })
      .state('page.login', {
          url: '/login',
          title: 'Login',
          templateUrl: helper.basepath('login.html')
      });

    } // routesConfig

})();

