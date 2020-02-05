/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	config.fullPage = true;//允许包含html标签
	
	config.language = 'zh-cn';
	
	config.allowedContent = true;
	
	config.height = 700;
	
	config.resize_maxHeight = 3000;
    //改变大小的最大宽度
    config.resize_maxWidth =3000;

    //改变大小的最小高度
    config.resize_minHeight =250;

    //改变大小的最小宽度
    config.resize_minWidth =750;
    
	config.entities = true;
	
	config.startupOutlineBlocks = true;
    
	config.removeDialogTabs='image:advanced;link:advanced';
	
    //config.filebrowsermyAddImageUploadUrl='/webbuilder/upload/uploadImage.jsp';

    //config.filebrowserUploadUrl = '/webbuilder/CkeditorPicUploader?column=' + createForm.column.value;
	
    config.filebrowserUploadUrl = '/webbuilder/CkeditorPicUploader?column=' + createForm.column.value;

	config.filebrowserImageUploadUrl='/webbuilder/CkeditorPicUploader?column=' + createForm.column.value;

    config.filebrowserFlashUploadUrl = '/webbuilder/CkeditorPicUploader?type=Flashs&column=' + createForm.column.value;
	
	config.filebrowserImageBrowseUrl='/webbuilder/upload/browse.jsp';

	config.filebrowserWindowWidth='800';

	config.filebrowserWindowHeight='600';
	
	config.image_previewText=' '; //预览区域显示内容
    
	config.font_names="宋体/SimSun;新宋体/NSimSun;仿宋_GB2312/FangSong_GB2312;楷体_GB2312/KaiTi_GB2312;黑体/SimHei;微软雅黑/Microsoft YaHei;幼圆/YouYuan;华文彩云/STCaiyun;华文行楷/STXingkai;方正舒体/FZShuTi;方正姚体/FZYaoti;"+ config.font_names;
	
	config.skin = 'kama';
    config.toolbar = 'Article';

	config.toolbar_Article =[
       { name: 'document', items : [ 'Source','-','Save','NewPage','DocProps','Preview','Print','-','Templates' ] },
       { name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
       { name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
       //{ name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
       { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
       { name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv',
       '-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
		'/',
       { name: 'links', items : [ 'HelloWorld','Link','Unlink','Anchor'] },
       //{ name: 'insert', items : [ 'myAddImage','Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },
       { name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },
       { name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
       { name: 'colors', items : [ 'TextColor','BGColor' ] },
       //{ name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About','code','InsertAttachment','uploadvideo','FormatHtml','WordToHtml','HisImages' ] }
	   { name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','InsertAttachment','uploadvideo','FormatHtml','WordToHtml','HisImages','About' ] }
    ];

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

    //config.extraPlugins='code,HelloWorld,InsertAttachment,uploadvideo,FormatHtml,WordToHtml,HisImages';
	config.extraPlugins='HisImages,HelloWorld,InsertAttachment,uploadvideo,FormatHtml,WordToHtml';
};