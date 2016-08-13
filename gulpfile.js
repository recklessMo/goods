var args        = require('yargs').argv,
    path        = require('path'),
    flip        = require('css-flip'),
    through     = require('through2'),
    gulp        = require('gulp'),
    $           = require('gulp-load-plugins')(),
    gulpsync    = $.sync(gulp),
    rev = require('gulp-rev'),
    revCollector = require('gulp-rev-collector'),
    PluginError = $.util.PluginError,
    del         = require('del');
    connect = require('gulp-connect');

//是否是生产环境，不同环境对文件操作不同，reversion操作不同
var isProduction = false;
var useSourceMaps = false;

//是否将view打包成js一次性发送给前台，而不是每次都去进行加载，这样可以节省时间
var useCache = true;

// ignore everything that begins with underscore
var hidden_files = '**/_*.*';
var ignored_files = '!'+hidden_files;

//定义一些常见的目录，这些目录包含了我们的源码，包括js，css，img，view等
var basePath = "src/main/webapp/";
var paths = {
  app:     basePath+'app/',
  styles:  basePath+'less/',
  scripts: basePath+ 'js/',
  img:	   basePath+ 'img/',
  pages:   basePath+ 'views/'
};

//前端依赖定义
var vendor = {
  // 将前端所需要用到的一些全局的库加入base
  base: {
    source: require('./vendor.base.json'),
    dest: basePath+'/app/js',
    name: 'base.js'
  },
  // 还有一些库在用到的时候在加载，通过懒加载机制进行加载
  app: {
    source: require('./vendor.json'),
    dest: basePath+'vendor'
  }
};


// 具体的源码文件
var source = {
  scripts: [paths.scripts + 'app.module.js',
            paths.scripts + 'modules/**/*.module.js',
            paths.scripts + 'modules/**/*.js',
            paths.scripts + 'custom/**/*.module.js',
            paths.scripts + 'custom/**/*.js'
  ],
  styles: {
    app:    [ paths.styles + '*.*'],
    themes: [ paths.styles + 'themes/*'],
    watch:  [ paths.styles + '**/*', '!'+paths.styles+'themes/*']
  },
  pages:paths.pages+'**',
  img:paths.img+'*.*'
};

//定义操作完毕后的文件存放目录
//基本都是存放到app目录里面
var build = {
  scripts: paths.app + 'js',
  styles:  paths.app + 'css',
  images:  paths.app + 'img',
  pages:   paths.app + 'pages',
  templates: {
	//index.html
    index: '../',
    //pages
	views: paths.app,
	//page.js
    cache: paths.app + 'js/' + 'templates.js',
  }
};

//下面定义的是一些工具的选项，具体不管
var vendorUglifyOpts = {
  mangle: {
    except: ['$super'] // rickshaw requires this
  }
};

//---------------
// TASKS， 定义所有的task，注意task的依赖关系
//---------------

// 生成app.js
gulp.task('scripts:app', function() {
    log('Building scripts app.js..');
    // Minify and copy all JavaScript (except vendor scripts)
    return gulp.src(source.scripts)
        .pipe($.jsvalidate())
        .on('error', handleError)
        .pipe( $.if( useSourceMaps, $.sourcemaps.init() ))
        .pipe($.concat( 'app.js' ))
        .pipe($.ngAnnotate())
        .on('error', handleError)
        .pipe( $.if( useSourceMaps, $.sourcemaps.write() ))
        .pipe($.if(isProduction,rev()))
        .pipe(gulp.dest(build.scripts))
        .pipe($.if(isProduction,rev.manifest()))
        .pipe($.if(isProduction,gulp.dest(build.scripts)))
        ;
});


//复制和连接依赖
gulp.task('vendor', gulpsync.sync(['vendor:base', 'vendor:app']) );

//将程序启动需要用到的一些依赖拷贝到base.js
gulp.task('vendor:base', function() {
    log('Copying base vendor assets..');
    return gulp.src(vendor.base.source)
        .pipe($.expectFile(vendor.base.source))
        .pipe($.if( isProduction, $.uglify() ))
        .pipe($.concat(vendor.base.name))
        .pipe($.if(isProduction,rev()))
        .pipe(gulp.dest(vendor.base.dest))
        .pipe($.if(isProduction,rev.manifest({path: "vendor-base-manifest.json"})))
        .pipe($.if(isProduction,gulp.dest(vendor.base.dest)))
        ;
});

//将程序的一些依赖放到vendor目录下，当需要的时候进行加载即可，具体配置在constants.js模块里
gulp.task('vendor:app', function() {
  log('Copying vendor assets..');

  var jsFilter = $.filter('**/*.js');
  var cssFilter = $.filter('**/*.css');
  return gulp.src(vendor.app.source, {base: 'bower_components'})
      .pipe($.expectFile(vendor.app.source))
      .pipe(jsFilter)
      .pipe($.if( isProduction, $.uglify( vendorUglifyOpts ) ))
      .pipe(jsFilter.restore())
      .pipe(cssFilter)
      .pipe($.if( isProduction, $.minifyCss() ))
      .pipe(cssFilter.restore())
      .pipe( gulp.dest(vendor.app.dest) );
});

//处理less，生成css
gulp.task('styles:app', function() {
    log('Building application styles..');
    return gulp.src(source.styles.app)
        .pipe( $.if( useSourceMaps, $.sourcemaps.init() ))
        .pipe( $.less() )
        .on('error', handleError)
        .pipe( $.if( isProduction, $.minifyCss() ))
        .pipe( $.if( useSourceMaps, $.sourcemaps.write() ))
        .pipe($.if(isProduction,$.rev()))
        .pipe(gulp.dest(build.styles))
        .pipe($.if(isProduction,rev.manifest()))
        .pipe($.if(isProduction,gulp.dest(build.styles)))
        ;
});

//简单的拷贝一下图片到build目录下
gulp.task('styles:img', function () {
    gulp.src(paths.img+"/**")
        .pipe(gulp.dest(build.images));
});

//将pages放到templateCache.js里面
gulp.task('styles:pages', function () {
    gulp.src(source.pages)
        .pipe($.sourcemaps.init())
        .pipe($.angularTemplatecache({standalone: true, root: 'app/views/'}))
        .pipe($.rename('templateCache.js'))
        .pipe($.sourcemaps.write('.'))
        .pipe($.if(isProduction,$.rev()))
        .pipe(gulp.dest(build.pages))
        .pipe($.if(isProduction,rev.manifest()))
        .pipe($.if(isProduction,gulp.dest(build.pages)));
});


//LESS相关
gulp.task('styles:themes', function() {
    log('Building application theme styles..');
    return gulp.src(source.styles.themes)
		.pipe($.less())
        .on('error', handleError)
        .pipe(gulp.dest(build.styles));
});

//---------------
// 加入监听机制，这样当我们的js和page文件有更新的时候可以直接刷新即可
//---------------
gulp.task('watch', function() {
  log('Starting watch and LiveReload..');

  $.livereload.listen();

  gulp.watch(source.scripts,         ['scripts:app']);
  gulp.watch(source.pages,         ['styles:pages']);
  gulp.watch(source.img,         ['styles:img']);
  gulp.watch(source.styles.watch,    ['styles:app']);
  gulp.watch(source.styles.themes,   ['styles:themes']);

  // a delay before triggering browser reload to ensure everything is compiled
  var livereloadDelay = 1500;
  // list of source file to watch for live reload
  var watchSource = [].concat(
      source.scripts,
      source.styles.watch,
      source.styles.themes
    );

  gulp
    .watch(watchSource)
    .on('change', function(event) {
      setTimeout(function() {
        $.livereload.changed( event.path );
      }, livereloadDelay);
    });

});

gulp.task('clean', function(done) {
    var delconfig = [].concat(
                        build.styles,
                        build.pages,
						build.scripts,
                        build.templates.index + 'index.html',
                        build.templates.views + 'views',
                        vendor.app.dest
                      );

    log('Cleaning: ' + $.util.colors.blue(delconfig));
    // force: clean files outside current directory
    del(delconfig, {force: true}, done);
});

//---------------
// MAIN TASKS
//---------------

// build for production (minify)
gulp.task('build', gulpsync.sync([
          'clean',
          'prod',
          'vendor',
          'assets',
        ]));

gulp.task('prod', function() { 
  log('Starting production build...');
  isProduction = true; 
});

// build with sourcemaps (no minify)
gulp.task('sourcemaps', ['usesources', 'default']);
gulp.task('usesources', function(){ useSourceMaps = true; });

gulp.task('webserver', function() {
    connect.server({
        port: 9090
    });
});

// default (no minify)
gulp.task('default', gulpsync.sync([
          'clean',
          'vendor',
          'assets',
          'watch',
          'webserver'
        ]), function(){

  log('************');
  log('* All Done * You can start editing your code, LiveReload will update your browser after any change..');
  log('************');

});

gulp.task('assets',[
          'scripts:app',
          'styles:app',
          'styles:img',
          'styles:pages',
          'styles:themes'
        ]);

//错误处理
function handleError(err) {
  log(err.toString());
  this.emit('end');
}

//console打印  
function log(msg) {
  $.util.log( $.util.colors.blue( msg ) );  
}
