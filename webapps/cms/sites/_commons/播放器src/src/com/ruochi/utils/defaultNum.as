package com.ruochi.utils{
	public function defaultNum(unknownValue,defaultValue):Number {
		if (unknownValue==null||unknownValue==undefined) {
			return Number(defaultValue);
		} else {
			return Number(unknownValue);
		}
	}
}