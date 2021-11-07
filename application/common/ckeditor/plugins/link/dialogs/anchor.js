/*
 Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
*/
CKEDITOR.dialog.add("anchor",function(c){function e(b,a){return b.createFakeElement(b.document.createElement("a",{attributes:a}),"cke_anchor","anchor")}return{title:c.lang.link.anchor.title,minWidth:300,minHeight:60,onOk:function(){var b=CKEDITOR.tools.trim(this.getVal