package com.ruochi.utils{
	public function isNum(str:String):Boolean {
		var numberStr:String = "1234567890.x";
		for (var i:int = 0; i < str.length; i++) {
			if (numberStr.indexOf(str.charAt(i)) <0) {
				return false;
				break;
			}
		}
		return true;
	}
}