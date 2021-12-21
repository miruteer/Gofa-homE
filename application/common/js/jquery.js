/*
 * jQuery 1.2.1 - New Wave Javascript
 *
 * Copyright (c) 2007 John Resig (jquery.com)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * $Date: 2007/09/27 04:47:19 $
 * $Rev: 3353 $
 */
(function(){if(typeof jQuery!="undefined")var _jQuery=jQuery;var jQuery=window.jQuery=function(selector,context){return this instanceof jQuery?this.init(selector,context):new jQuery(selector,context);};if(typeof $!="undefined")var _$=$;window.$=jQuery;var quickExpr=/^[^<]*(<(.|\s)+>)[^>]*$|^#(\w+)$/;jQuery.fn=jQuery.prototype={init:function(selector,context){selector=selector||document;if(typeof selector=="string"){var m=quickExpr.exec(selector);if(m&&(m[1]||!context)){if(m[1])selector=jQuery.clean([m[1]],context);else{var tmp=document.getElementById(m[3]);if(tmp)if(tmp.id!=m[3])return jQuery().find(selector);else{this[0]=tmp;this.length=1;return this;}else
selector=[];}}else
return new jQuery(context).find(selector);}else if(jQuery.isFunction(selector))return new jQuery(document)[jQuery.fn.ready?"ready":"load"](selector);return this.setArray(selector.constructor==Array&&selector||(selector.jquery||selector.le