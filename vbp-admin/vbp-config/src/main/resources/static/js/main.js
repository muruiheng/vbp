'use strict';

/* Controllers */

angular.module('app')
  .controller('AppCtrl', ['$scope', '$translate', '$localStorage', '$window', 
    function(              $scope,   $translate,   $localStorage,   $window ) {
      // add 'ie' classes to html
      var isIE = !!navigator.userAgent.match(/MSIE/i);
      isIE && angular.element($window.document.body).addClass('ie');
      isSmartDevice( $window ) && angular.element($window.document.body).addClass('smart');

      // config
      $scope.app = {
        name: '统一配置系统',
        version: '0.0.1',
        color: {
          primary: '#7266ba',
          info:    '#23b7e5',
          success: '#27c24c',
          warning: '#fad733',
          danger:  '#f05050',
          light:   '#e8eff0',
          dark:    '#3a3f51',
          black:   '#1c2b36'
        },
        settings: {
          themeID: 1,
          navbarHeaderColor: 'bg-black',
          navbarCollapseColor: 'bg-white-only',
          asideColor: 'bg-black',
          headerFixed: true,
          asideFixed: false,
          asideFolded: false,
          asideDock: false,
          container: false
        }
      }

      // save settings to local storage
      if ( angular.isDefined($localStorage.settings) ) {
        $scope.app.settings = $localStorage.settings;
      } else {
        $localStorage.settings = $scope.app.settings;
      }
      $scope.$watch('app.settings', function(){
        if( $scope.app.settings.asideDock  &&  $scope.app.settings.asideFixed ){
          // aside dock and fixed must set the header fixed.
          $scope.app.settings.headerFixed = true;
        }
        // save to local storage
        $localStorage.settings = $scope.app.settings;
      }, true);

      // angular translate
      $scope.lang = { isopen: false };
      $scope.langs = {zh_CN:'中国', en:'English'};
      $scope.selectLang = $scope.langs[$translate.proposedLanguage()] || "English";
      $scope.setLang = function(langKey, $event) {
        // set the current lang
        $scope.selectLang = $scope.langs[langKey];
        // You can change the language during runtime
        $translate.use(langKey);
        $scope.lang.isopen = !$scope.lang.isopen;
      };

      function isSmartDevice( $window )
      {
          // Adapted from http://www.detectmobilebrowsers.com
          var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
          // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
          return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
      }

      
      /**
       * 处理post请求
       * 
       * @param $http
       *            angularjs 的http
       * @param url
       *            后台url
       * @param data
       *            post后台的的json数据
       */
      $scope.post = function($http, url, data, success, failed) {
      	$http.post(url, data).success(function(response) {
      		if (response.relogin) {
      			window.location.href=response.values.loginUrl;
      		} else if (response.success) {
      			success(response.values, response.message);
      		} else {
      			failed(response.message);
      		}

      	});
      }

      /**
       * 处理get请求
       * 
       * @param $http
       * @param url
       * @param data
       * @param success
       * @param failed
       */
      $scope.get = function($http, url, data, success, failed) {
      	$http.get(url, data).success(function(response) {
      		if (response.relogin) {
      			window.location.href=response.values.loginUrl;
      		} else if (response.success) {
      			success(response.values, response.message);
      		} else {
      			failed(response.message);
      		}

      	});
      }

      var base64encodechars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
      var base64decodechars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1,
      		63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1,
      		0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
      		20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31,
      		32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
      		50, 51, -1, -1, -1, -1, -1);

      /**
       * 加密 
       * @param str 需要加密的字符串
       * @returns {String}
       */
      $scope.base64encode = function (str) {
      	var out, i, len;
      	var c1, c2, c3;
      	len = str.length;
      	i = 0;
      	out = "";
      	while (i < len) {
      		c1 = str.charCodeAt(i++) & 0xff;
      		if (i == len) {
      			out += base64encodechars.charAt(c1 >> 2);
      			out += base64encodechars.charAt((c1 & 0x3) << 4);
      			out += "==";
      			break;
      		}
      		c2 = str.charCodeAt(i++);
      		if (i == len) {
      			out += base64encodechars.charAt(c1 >> 2);
      			out += base64encodechars.charAt(((c1 & 0x3) << 4)
      					| ((c2 & 0xf0) >> 4));
      			out += base64encodechars.charAt((c2 & 0xf) << 2);
      			out += "=";
      			break;
      		}
      		c3 = str.charCodeAt(i++);
      		out += base64encodechars.charAt(c1 >> 2);
      		out += base64encodechars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xf0) >> 4));
      		out += base64encodechars.charAt(((c2 & 0xf) << 2) | ((c3 & 0xc0) >> 6));
      		out += base64encodechars.charAt(c3 & 0x3f);
      	}
      	return out;
      }

      /**
       * 解密
       * @param str 需要解密的字符串
       * @returns {String}
       */
      $scope.base64decode = function (str) {
      	var c1, c2, c3, c4;
      	var i, len, out;
      	len = str.length;
      	i = 0;
      	out = "";
      	while (i < len) {

      		do {
      			c1 = base64decodechars[str.charCodeAt(i++) & 0xff];
      		} while (i < len && c1 == -1);
      		if (c1 == -1)
      			break;

      		do {
      			c2 = base64decodechars[str.charCodeAt(i++) & 0xff];
      		} while (i < len && c2 == -1);
      		if (c2 == -1)
      			break;

      		out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));

      		do {
      			c3 = str.charCodeAt(i++) & 0xff;
      			if (c3 == 61)
      				return out;
      			c3 = base64decodechars[c3];
      		} while (i < len && c3 == -1);
      		if (c3 == -1)
      			break;

      		out += String.fromCharCode(((c2 & 0xf) << 4) | ((c3 & 0x3c) >> 2));

      		do {
      			c4 = str.charCodeAt(i++) & 0xff;
      			if (c4 == 61)
      				return out;
      			c4 = base64decodechars[c4];
      		} while (i < len && c4 == -1);
      		if (c4 == -1)
      			break;
      		out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
      	}
      	return out;
      }

  }]);