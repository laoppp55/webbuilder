/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
    config.forcePasteAsPlainText =false;
    //当从word里复制文字进来时，是否进行文字的格式化去除 plugins/pastefromword/plugin.js
    config.pasteFromWordIgnoreFontFace = true; //默认为忽略格式
    //是否使用<h1><h2>等标签修饰或者代替从word文档中粘贴过来的内容 plugins/pastefromword/plugin.js
    config.pasteFromWordKeepsStructure = false;
    //从word中粘贴内容时是否移除格式 plugins/pastefromword/plugin.js
    config.pasteFromWordRemoveStyle = false;
    //对应后台语言的类型来对输出的HTML内容进行格式化，默认为空
    config.protectedSource.push( /<"?["s"S]*?"?>/g );   // PHP Code
    config.protectedSource.push( /<%[\s\S]*?%>/g);   // ASP Code
    config.protectedSource.push( /<%[\s\S]*?%>/g);   // ASP Code
        //config.protectedSource.push( /(]+>["s|"S]*?<"/asp:[^">]+>)|(]+"/>)/gi );   // ASP.Net Code
    //当输入：shift+Enter时插入的标签
    config.shiftEnterMode = CKEDITOR.ENTER_P;  //可选：CKEDITOR.ENTER_BR或CKEDITOR.ENTER_DIV
    config.extraPlugins="model,code";

};