﻿/*
Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
*/
(function(){window.CKEDITOR&&window.CKEDITOR.dom||(window.CKEDITOR||(window.CKEDITOR=function(){var a=/(^|.*[\\\/])ckeditor\.js(?:\?.*|;.*)?$/i,e={timestamp:"I8BI",version:"4.10.1 (Standard)",revision:"59246e9",rnd:Math.floor(900*Math.random())+100,_:{pending:[],basePathSrcPattern:a},status:"unloaded",basePath:function(){var c=window.CKEDITOR_BASEPATH||"";if(!c)for(var b=document.getElementsByTagName("script"),e=0;e<b.length;e++){var g=b[e].src.match(a);if(g){c=g[1];break}}-1==c.indexOf(":/")&&"//"!=
c.slice(0,2)&&(c=0===c.indexOf("/")?location.href.match(/^.*?:\/\/[^\/]*/)[0]+c:location.href.match(/^[^\?]*\/(?:)/)[0]+c);if(!c)throw'The CKEditor installation path could not be automatically detected. Please set the global variable "CKEDITOR_BASEPATH" before creating editor instances.';return c}(),getUrl:function(a){-1==a.indexOf(":/")&&0!==a.indexOf("/")&&(a=this.basePath+a);this.timestamp&&"/"!=a.charAt(a.length-1)&&!/[&?]t=/.test(a)&&(a+=(0<=a.indexOf("?")?"\x26":"?")+"t\x3d"+this.timestamp);
return a},domReady:function(){function a(){try{document.addEventListener?(document.removeEventListener("DOMContentLoaded",a,!1),c()):document.attachEvent&&"complete"===document.readyState&&(document.detachEvent("onreadystatechange",a),c())}catch(g){}}function c(){for(var a;a=b.shift();)a()}var b=[];return function(g){function c(){try{document.documentElement.doScroll("left")}catch(f){setTimeout(c,1);return}a()}b.push(g);"complete"===document.readyState&&setTimeout(a,1);if(1==b.length)if(document.addEventListener)document.addEventListener("DOMContentLoaded",
a,!1),window.addEventListener("load",a,!1);else if(document.attachEvent){document.attachEvent("onreadystatechange",a);window.attachEvent("onload",a);g=!1;try{g=!window.frameElement}catch(m){}document.documentElement.doScroll&&g&&c()}}}()},b=window.CKEDITOR_GETURL;if(b){var c=e.getUrl;e.getUrl=function(a){return b.call(e,a)||c.call(e,a)}}return e}()),CKEDITOR.event||(CKEDITOR.event=function(){},CKEDITOR.event.implementOn=function(a){var e=CKEDITOR.event.prototype,b;for(b in e)null==a[b]&&(a[b]=e[b])},
CKEDITOR.event.prototype=function(){function a(a){var d=e(this);return d[a]||(d[a]=new b(a))}var e=function(a){a=a.getPrivate&&a.getPrivate()||a._||(a._={});return a.events||(a.events={})},b=function(a){this.name=a;this.listeners=[]};b.prototype={getListenerIndex:function(a){for(var b=0,e=this.listeners;b<e.length;b++)if(e[b].fn==a)return b;return-1}};return{define:function(c,b){var e=a.call(this,c);CKEDITOR.tools.extend(e,b,!0)},on:function(c,b,e,k,g){function h(f,a,n,g){f={name:c,sender:this,editor:f,
data:a,listenerData:k,stop:n,cancel:g,removeListener:m};return!1===b.call(e,f)?!1:f.data}function m(){n.removeListener(c,b)}var f=a.call(this,c);if(0>f.getListenerIndex(b)){f=f.listeners;e||(e=this);isNaN(g)&&(g=10);var n=this;h.fn=b;h.priority=g;for(var r=f.length-1;0<=r;r--)if(f[r].priority<=g)return f.splice(r+1,0,h),{removeListener:m};f.unshift(h)}return{removeListener:m}},once:function(){var a=Array.prototype.slice.call(arguments),b=a[1];a[1]=function(a){a.removeListener();return b.apply(this,
arguments)};return this.on.apply(this,a)},capture:function(){CKEDITOR.event.useCapture=1;var a=this.on.apply(this,arguments);CKEDITOR.event.useCapture=0;return a},fire:function(){var a=0,b=function(){a=1},l=0,k=function(){l=1};return function(g,h,m){var f=e(this)[g];g=a;var n=l;a=l=0;if(f){var r=f.listeners;if(r.length)for(var r=r.slice(0),u,v=0;v<r.length;v++){if(f.errorProof)try{u=r[v].call(this,m,h,b,k)}catch(y){}else u=r[v].call(this,m,h,b,k);!1===u?l=1:"undefined"!=typeof u&&(h=u);if(a||l)break}}h=
l?!1:"undefined"==typeof h?!0:h;a=g;l=n;return h}}(),fireOnce:function(a,b,l){b=this.fire(a,b,l);delete e(this)[a];return b},removeListener:function(a,b){var l=e(this)[a];if(l){var k=l.getListenerIndex(b);0<=k&&l.listeners.splice(k,1)}},removeAllListeners:function(){var a=e(this),b;for(b in a)delete a[b]},hasListeners:function(a){return(a=e(this)[a])&&0<a.listeners.length}}}()),CKEDITOR.editor||(CKEDITOR.editor=function(){CKEDITOR._.pending.push([this,arguments]);CKEDITOR.event.call(this)},CKEDITOR.editor.prototype.fire=
function(a,e){a in{instanceReady:1,loaded:1}&&(this[a]=!0);return CKEDITOR.event.prototype.fire.call(this,a,e,this)},CKEDITOR.editor.prototype.fireOnce=function(a,e){a in{instanceReady:1,loaded:1}&&(this[a]=!0);return CKEDITOR.event.prototype.fireOnce.call(this,a,e,this)},CKEDITOR.event.implementOn(CKEDITOR.editor.prototype)),CKEDITOR.env||(CKEDITOR.env=function(){var a=navigator.userAgent.toLowerCase(),e=a.match(/edge[ \/](\d+.?\d*)/),b=-1<a.indexOf("trident/"),b=!(!e&&!b),b={ie:b,edge:!!e,webkit:!b&&
-1<a.indexOf(" applewebkit/"),air:-1<a.indexOf(" adobeair/"),mac:-1<a.indexOf("macintosh"),quirks:"BackCompat"==document.compatMode&&(!document.documentMode||10>document.documentMode),mobile:-1<a.indexOf("mobile"),iOS:/(ipad|iphone|ipod)/.test(a),isCustomDomain:function(){if(!this.ie)return!1;var a=document.domain,b=window.location.hostname;return a!=b&&a!="["+b+"]"},secure:"https:"==location.protocol};b.gecko="Gecko"==navigator.product&&!b.webkit&&!b.ie;b.webkit&&(-1<a.indexOf("chrome")?b.chrome=
!0:b.safari=!0);var c=0;b.ie&&(c=e?parseFloat(e[1]):b.quirks||!document.documentMode?parseFloat(a.match(/msie (\d+)/)[1]):document.documentMode,b.ie9Compat=9==c,b.ie8Compat=8==c,b.ie7Compat=7==c,b.ie6Compat=7>c||b.quirks);b.gecko&&(e=a.match(/rv:([\d\.]+)/))&&(e=e[1].split("."),c=1E4*e[0]+100*(e[1]||0)+1*(e[2]||0));b.air&&(c=parseFloat(a.match(/ adobeair\/(\d+)/)[1]));b.webkit&&(c=parseFloat(a.match(/ applewebkit\/(\d+)/)[1]));b.version=c;b.isCompatible=!(b.ie&&7>c)&&!(b.gecko&&4E4>c)&&!(b.webk