﻿/**
 * @license Copyright (c) CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

(function() {
    if (!supportsLocalStorage()) {
        return;
    }
   
    CKEDITOR.plugins.add("autosave", {
        lang: ['de', 'en', 'zh-cn'],
        init: function (editor) {
            // Checks If there is data available and load it
            if (localStorage.getItem('autosave' + editor.id)) {

                var autoSavedContent = localStorage.getItem('autosave' + editor.id);
                var editorLoadedContent = editor.getData();
                
                // check if the loaded editor content is the same as the autosaved content
                if (editorLoadedContent == autoSavedContent) {

                    localStorage.removeItem('autosave' + editor.id);

                    return;
                }

                if (confirm(editor.lang.autosave.loadSavedContent)) {
                    if (editor.plugins.bbcode) {
                        editor._.data = autoSavedContent;
                    } else {
                        editor.setData(autoSavedContent);
                    }
                }

                localStorage.removeItem('autosave' + editor.id);
            }

            editor.on('key', startTimer);
        }
    });

    var timeOutId = 0,
        savingActive = false;

    var startTimer = function(event) {
        if (timeOutId) {
            clearTimeout(timeOutId);
        }
        var delay = CKEDITOR.config.autosave_delay !