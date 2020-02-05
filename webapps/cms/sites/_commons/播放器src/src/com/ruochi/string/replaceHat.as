package com.ruochi.string{
	public function replaceHat(_str:String):String {
		var pattern:RegExp = /\^/g;
		return _str.replace(pattern, "&");
	}
}