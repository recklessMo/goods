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
              resolve: helper.resolveFor('angularFileUpload', 'filestyle', 'fastclick', 'modernizr', 'ui.select', 'icons', 'screenfull', 'animo', 'sparklines', 'slimscroll', 'classyloader', 'toaster', 'whirl', 'oitozero.ngSweetAlert', 'ngDialog')
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
                templateUrl: helper.basepath('custom/student/add/student-add-worktable.html'),
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
                    templateUrl: helper.basepath('custom/student/student-add.html')
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
                templateUrl: helper.basepath('custom/performance/exam/exam-list.html')
            })
            .state('app.result-list', {
                url: '/result-list',
                title: '成绩单',
                templateUrl: helper.basepath('custom/performance/score/result-list.html')
            })
            .state('app.result-trend', {
                url: '/result-trend',
                title: '成绩趋势分析',
                templateUrl: helper.basepath('custom/performance/score/result-trend.html')
            })
            .state('app.result-self', {
                url: '/result-self',
                title: '个人综合',
                templateUrl: helper.basepath('custom/performance/score/result-self.html')
            })
            .state('app.result-contrast', {
                url: '/result-contrast',
                title: '综合对比',
                templateUrl: helper.basepath('custom/performance/score/result-contrast.html')
            })
            .state('app.result-total', {
                url: '/result-total',
                title: '整体分析',
                templateUrl: helper.basepath('custom/performance/score/result-total.html')
            })
            .state('app.result-gap', {
                url: '/result-gap',
                title: '分数段分析',
                templateUrl: helper.basepath('custom/performance/score/result-gap.html')
            })
            .state('app.result-rank', {
                url: '/result-rank',
                title: '名次分析',
                templateUrl: helper.basepath('custom/performance/score/result-rank.html')
            })
            .state('app.result-rankchange', {
                url: '/result-rankchange',
                title: '进退步分析',
                templateUrl: helper.basepath('custom/performance/score/result-rankchange.html')
            })
            .state('app.result-absense', {
                url: '/result-absense',
                title: '缺考统计',
                templateUrl: helper.basepath('custom/performance/score/result-absense.html')
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
            .state('app.wechat-talk', {
                url: '/wechat-talk',
                title: '微信咨询',
                templateUrl: helper.basepath('custom/wechat/wechat-talk.html')
            })
            .state('app.wechat-notice', {
                url: '/wechat-notice',
                title: '微信通知',
                templateUrl: helper.basepath('custom/message/inform-message-list.html')
            })
            .state('app.assignment', {
                url: '/assignment',
                title: '作业布置',
                templateUrl: helper.basepath('custom/assignment/assignment-list.html')
            })
            .state('app.todo', {
                url: '/todo',
                title: '待办事项',
                templateUrl: helper.basepath('custom/self/todo.html'),
                controller: 'TodoController'
            })
            .state('app.student-graduate', {
                url: '/student-graduate',
                title: '毕业去向',
                templateUrl: helper.basepath('custom/student/other/student-graduate.html'),
            })
            .state('app.student-teacher', {
                url: '/student-teacher',
                title: '教师设置',
                templateUrl: helper.basepath('custom/teacher/class-course-teacher.html'),
            })
            .state('app.class-schedule', {
                url: '/class-schedule',
                title: '课表编排',
                templateUrl: helper.basepath('custom/teacher/class-schedule-course.html'),
            })
            .state('app.parents-wechat', {
                url: '/parents-wechat',
                title: '家长群发',
                templateUrl: helper.basepath('custom/teacher/class-schedule-course.html'),
            })
            .state('app.super-account', {
                url: '/super-account',
                title: '管理帐号',
                templateUrl: helper.basepath('custom/system/super-account-list.html'),
            })
            .state('app.org-list', {
                url: '/org-list',
                title: '管理机构',
                templateUrl: helper.basepath('custom/org/org-list.html'),
            })
            .state('app.test', {
                url: '/test',
                title: '管理机构',
                templateUrl: helper.basepath('custom/test/test.html'),
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

