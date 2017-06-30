(function () {
    'use strict';

    angular
        .module('custom')
        .service('NotifyService', ["$q", "$timeout", "$rootScope", "$http", "$location", function ($q, $timeout, $rootScope, $http, $location) {
            var service = {};
            var socket = {
                client: null,
                stomp: null
            };
            var startListener = function () {
                function noitfy(data) {
                    var message = data.body;
                    console.log("receive msg:" + message);
                    var notify = JSON.parse(message);
                    //下发给子scope
                    $rootScope.$broadcast(notify.type, notify.data);
                }

                socket.stomp.subscribe('/websocket/notify/' + $rootScope.userId, function (data) {
                    noitfy(data);
                });

                socket.stomp.subscribe('/websocket/notify/broadcast/' + $rootScope.orgId, function (data) {
                    noitfy(data);
                });
            };

            var errorCallback = function (error) {
                console.log("断线，5s后重连");
                reconnect();
            };

            var reconnect = function () {
                $timeout(function () {
                    service.initialize();
                }, 5000);
            };

            service.initialize = function () {
                socket.client = new SockJS('/websocket');
                socket.stomp = Stomp.over(socket.client);
                socket.stomp.connect({}, startListener, errorCallback);
            };
            return service;
        }]);
})();