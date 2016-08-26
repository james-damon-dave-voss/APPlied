var gulp = require('gulp');
var babel = require('gulp-babel');
var sass = require('gulp-sass');
var uglify = require('gulp-uglify');
var htmlhint = require("gulp-htmlhint");
var beautify = require('gulp-beautify')
var surge = require('gulp-surge');
gulp.task('default',['html','css','js']);

gulp.task('html', function(){
  gulp.src('./index.html')
  .pipe(gulp.dest('./public'));
});


gulp.src("./public/*.html")
	.pipe(htmlhint());

gulp.task('beautify', function(){
   gulp.src('./public/app.js')
   .pipe(beautify({indentSize: 2}))
   .pipe(gulp.dest('./'));
})

gulp.task('css', function () {
    gulp.src('./styles.scss')
        .pipe(sass())
        .pipe(gulp.dest('./public/css'));
});

gulp.task('js', function () {
    gulp.src('./public/js/app.js')
        .pipe(babel({
    			presets: ['es2015']
    		}))
        .pipe(uglify({
            mangle: true,
        }))
        .pipe(gulp.dest('./public'));
});

gulp.task('deploy', [], function () {
  return surge({
    project: './public',         // Path to your static build directory
    domain: 'apply.surge.sh'  // Your domain or Surge subdomain
  })
});

gulp.task('watch', function () {
    gulp.watch('./public/scss/styles.scss', ['css']);
    gulp.watch('./public/index.html', ['html']);
    gulp.watch('./public/js/*.js', ['js']);
});
