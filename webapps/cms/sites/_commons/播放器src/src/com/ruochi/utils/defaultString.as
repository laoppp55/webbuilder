package com.ruochi.utils{
	public function defaultString(unknownValue,defaultValue):String {
		if (unknownValue==null||unknownValue==undefined) {
			return String(defaultValue);
		} else {
			return String(unknownValue);
		}
	}
}