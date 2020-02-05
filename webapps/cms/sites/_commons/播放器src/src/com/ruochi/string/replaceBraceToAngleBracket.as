package com.ruochi.string{
	public function replaceBraceToAngleBracket(str:String):String {
		var pattern:RegExp = /{/g;
		var returnStr:String = str.replace(pattern, "<");
		pattern = /}/g;
		return returnStr.replace(pattern, ">");
	}
}