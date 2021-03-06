(function () {
    'use strict';
    angular
        .module('custom')
        .controller('UploadStudentFileController', UploadStudentFileController);
    UploadStudentFileController.$inject = ['FileUploader', 'SweetAlert', 'Notify'];

    function UploadStudentFileController(FileUploader, SweetAlert, Notify) {

        var vm = this;

        activate();

        ////////////////
        function activate() {
            var uploader = vm.uploader = new FileUploader({
                //上传的地址
                url: '/common/file/student/uploadExcel'
            });

            // FILTERS
            uploader.filters.push({
                name: 'customFilter',
                fn: function(/*item, options*/) {
                    return this.queue.length < 10;
                }
            });

            // CALLBACKS
            uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
                console.info('onWhenAddingFileFailed', item, filter, options);
            };
            uploader.onAfterAddingFile = function(fileItem) {
                console.info('onAfterAddingFile', fileItem);
            };
            uploader.onAfterAddingAll = function(addedFileItems) {
                console.info('onAfterAddingAll', addedFileItems);
            };
            uploader.onBeforeUploadItem = function(item) {
                console.info('onBeforeUploadItem', item);
            };
            uploader.onProgressItem = function(fileItem, progress) {
                console.info('onProgressItem', fileItem, progress);
            };
            uploader.onProgressAll = function(progress) {
                console.info('onProgressAll', progress);
            };
            uploader.onSuccessItem = function(fileItem, response, status, headers) {
                console.info('onSuccessItem', fileItem, response, status, headers);
                if(response.status != 200){
                    fileItem.remove();
                    Notify.alert(response.data, {status: 'danger', pos: 'top-center', timeout: 3000});
                }
            };
            uploader.onErrorItem = function(fileItem, response, status, headers) {
                console.info('onErrorItem', fileItem, response, status, headers);
            };
            uploader.onCancelItem = function(fileItem, response, status, headers) {
                console.info('onCancelItem', fileItem, response, status, headers);
            };
            uploader.onCompleteItem = function(fileItem, response, status, headers) {
                console.info('onCompleteItem', fileItem, response, status, headers);
            };
            uploader.onCompleteAll = function() {
                console.info('onCompleteAll');
            };

            console.info('uploader', uploader);
        }

    }

})();