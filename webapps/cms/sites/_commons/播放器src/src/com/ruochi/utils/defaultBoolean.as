package com.ruochi.utils{
	public function defaultBoolean(unknownValue,defaultValue):Boolean {
		if (unknownValue==null||unknownValue==undefined) {
			if (String(defaultValue) == "0" || String(defaultValue) == "false") {
				return false;				
			}else {
				return true;
			}
		} else {
			if (String(unknownValue)== "0" || String(unknownValue) == "false") {
				return false;				
			}else {
				return true;
			}
		}
	}
}