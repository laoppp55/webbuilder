/**
 * @title  : Applet文件上传控件的JS类.
 * @author : 马必强
 * @modify : 2008-1-10
 */

/**
 * MUpload可以设置的变量有：
 * name          控件的名称（授权的时候用到)，可以附加公司的相关信息
 * limitTypes    限制的文件类型，使用对象进行表示，如果不指定或指定为空则默认是全部。
 *               格式为：{'文件类型描述' : '文件后缀列表','文件类型描述' : '文件后缀列表'}
 *               对于每一种文件类型，多个文件后缀使用逗号进行分隔！不指定则选择所有文件
 * limitSize     限制每个文件的大小，-1不限制，单位为KB
 * uploadType    文件上传方式 目前只有http-form
 * appWidth      applet的宽度
 * appHeight     applet的高度
 * jarPath       applet的jar包的存放路径，相对当前页面而言
 * onComplete    文件上传成功后的回调方法名称，函数的参数依次为：本地的文件路径、文件的大小(单位字节)、
 *               上传到服务器后的文件路径
 * onFailed      文件上传失败的回调方法名称，函数的参数依次为：本地的文件路径、文件的大小(单位字节)、失败信息
 * onSelect      成功选择文件后的回调方法，函数的参数依次为：本地的文件路径、文件的大小(单位字节)
 * httpURL       http方式上传时接收数据的URL地址
 *
 * 备注：
 * 1.对于使用table.MUploadClient.class而言，最佳的大小为：宽-480 高-220
 * 2.对于使用simple.MUploadClient.class而言，最佳的大小为：宽-400 高-150
 * 3.对于文件上传成功后的返回结果应该为:true=上传后的文件路径，失败则应该返回非true=开头的字符串
 *
 *
 */
function MUpload() {};
MUpload.version = '1.0';
MUpload.prototype = {
	_isIE : ( /msie/i.test(navigator.userAgent) && !/opera/i.test(navigator.userAgent) ), //浏览器类型
	options : {
			    name : 'MUpload文件上传控件',
				limitTypes : false,
				limitSize : -1,
				uploadType : 'http-form',
				appWidth : 480,
				appHeight : 220,
				jarPath : '.',
				onComplete : null,
				onFailed : null,
				onSelect : null,
				httpURL : null
			  },
	innerOptions : {
						appName : 'mupload',
						jarName : 'mupload.jar',
						mainClass : 'cn.mjc.mupload.table.MUploadClient.class'
					},
	
	/**
	 * 初始化方法，运行主方法
	 */
	run : function(/*object*/options) {
		if (options) this._setOptions(options);
		this.innerOptions.appName += Math.random();
		this._setObjectHtml();
	},
	
	/**
	 * 获取当前文件上传的状态.返回的对象格式为：{status : WAITING_FOR_SELECT_FILE, message : '等待选择文件'}
	 * 系统预设的状态有：
	 * WAITING_FOR_SELECT_FILE      等待用户选择文件
	 * WAITING_FOR_UPLOAD           等待用户上传文件（用户已选择文件）
	 * FILE_UPLOADING               文件上传中
	 * FILE_SIZE_TOO_LONG           文件大小超出系统最大限制（1.5G）
	 * FILE_SIZE_OVER_LIMIT         文件大小超出指定大小限制（即options中的limitSize，单位KB）
	 * FILE_UPLOAD_COMPLETE         文件上传成功
	 * FILE_UPLOAD_FAILED           文件上传失败（有失败原因）
	 */
	status : function() {
		var stat = document.getElementById(this.innerOptions.appName + (this._isIE ? '_ie' : '_nf')).getStatus();
		return eval('(' + stat + ')');
	},
	
	/**
	 * 设置参数
	 */
	_setOptions : function(/*object*/ options) {
		for (var prop in this.options) {
			if (options[prop]) this.options[prop] = options[prop];
		}
	},
	
	/**
	 * 在页面上写入引用applet的html代码.
	 */
	_setObjectHtml : function() {
		var param = this._getParameter();
		var html = '<OBJECT CLASSID="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" '
			+ 'CODEBASE="http://java.sun.com/update/1.5.0/jinstall-1_6-windows-i586.cab#Version=5,0,0,3" '
			+ 'WIDTH="' + this.options.appWidth + '" HEIGHT="' + this.options.appHeight + '" NAME="'
			+ this.innerOptions.appName + '_ie" ID="' + this.innerOptions.appName + '_ie">'
			+ '<PARAM NAME="CODEBASE" VALUE="' + this.options.jarPath + '">'
			+ '<PARAM NAME="ARCHIVE" VALUE="' + this.innerOptions.jarName + '">'
			+ '<PARAM NAME="CODE" VALUE="' + this.innerOptions.mainClass + '">'
			+ '<PARAM NAME="NAME" VALUE="' + this.options.name + '">'
			+ '<PARAM NAME="TYPE" VALUE="application/x-java-applet;version=1.6">'
			+ '<PARAM NAME="SCRIPTABLE" VALUE="true">' + param.ie
			+ '<EMBED TYPE="application/x-java-applet;version=1.6" ID="' + this.innerOptions.appName + '_nf" '
			+ 'CODE="' + this.innerOptions.mainClass + '" JAVA_CODEBASE="' + this.options.jarPath + '" '
			+ 'ARCHIVE="' + this.innerOptions.jarName + '" NAME="' + this.options.name + '" '
			+ 'WIDTH="' + this.options.appWidth + '" HEIGHT="' + this.options.appHeight + '" ' + param.nf
			+ 'MAYSCRIPT="true" pluginspage="http://java.sun.com/products/plugin/index.html#download">'
			+ '<NOEMBED></NOEMBED></EMBED></OBJECT>';
                alert(html);
		document.write(html);
	},
	

    /**
    <OBJECT CLASSID="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" CODEBASE="http://java.sun.com/update/1.5.0/jinstall-1_5-windows-i586.cab#Version=5,0,0,3" WIDTH="480" HEIGHT="220" NAME="MUpload文件上传控件" ID="mupload_ie">
			<PARAM NAME="CODEBASE" VALUE=".">
			<PARAM NAME="ARCHIVE" VALUE="mjc-mupload.jar">
			<PARAM NAME="CODE" VALUE="cn.mjc.mupload.table.MUploadClient.class">
			<PARAM NAME="NAME" VALUE="' + this.options.name + '">'
			<PARAM NAME="TYPE" VALUE="application/x-java-applet;version=1.5">'
			<PARAM NAME="SCRIPTABLE" VALUE="true">' + param.ie
			<EMBED TYPE="application/x-java-applet;version=1.5" ID="' + this.innerOptions.appName + '_nf" '
			CODE="' + this.innerOptions.mainClass + '" JAVA_CODEBASE="' + this.options.jarPath + '" '
			ARCHIVE="' + this.innerOptions.jarName + '" NAME="' + this.options.name + '" '
			WIDTH="' + this.options.appWidth + '" HEIGHT="' + this.options.appHeight + '" ' + param.nf
			MAYSCRIPT="true" pluginspage="http://java.sun.com/products/plugin/index.html#download">'
			<NOEMBED></NOEMBED></EMBED></OBJECT>';
     */

	/**
	 * 获取参数字符串的表示.
	 */
	_getParameter : function() {
		var object_str = '', embed_str = '';
		if (typeof(this.options.limitTypes) == 'object') {
			var propStr = '';
			for (var prop in this.options.limitTypes) {
				propStr += prop + '=' + this.options.limitTypes[prop] + '&';
			}
			object_str += '<PARAM NAME="limitTypes" VALUE="' + propStr + '">';
			embed_str += ' limitTypes="' + propStr + '" ';
		}
		if (!isNaN(this.options.limitSize) && this.options.limitSize > 0) {
			object_str += '<PARAM NAME="limitSize" VALUE="' + this.options.limitSize + '">';
			embed_str += ' limitSize="' + this.options.limitSize + '" ';
		}
		if (this.options.onComplete && typeof(this.options.onComplete) == 'string') {
			object_str += '<PARAM NAME="onComplete" VALUE="' + this.options.onComplete + '">';
			embed_str += ' onComplete="' + this.options.onComplete + '" ';
		}
		if (this.options.onFailed && typeof(this.options.onFailed) == 'string') {
			object_str += '<PARAM NAME="onFailed" VALUE="' + this.options.onFailed + '">';
			embed_str += ' onFailed="' + this.options.onFailed + '" ';
		}
		if (this.options.onSelect && typeof(this.options.onSelect) == 'string') {
			object_str += '<PARAM NAME="onSelect" VALUE="' + this.options.onSelect + '">';
			embed_str += ' onSelect="' + this.options.onSelect + '" ';
		}
		if (this.options.uploadType == 'http-form') {
			object_str += '<PARAM NAME="httpURL" VALUE="' + this.options.httpURL + '">';
			embed_str += ' httpURL="' + this.options.httpURL + '" ';
		}
		object_str += '<PARAM NAME="uploadType" VALUE="' + this.options.uploadType + '">';
		embed_str += ' uploadType="' + this.options.uploadType + '" ';
		return {"ie" : object_str, "nf" : embed_str};
	}
};