package com.ruochi.utils{
	public function formatTime(n:Number):String {
		var mm:Number;
		var ss:Number;
		var mmStr:String;
		var ssStr:String;
		n = Math.floor(n);
		mm = Math.floor(n/60);
		ss = n%60;
		if (mm<10) {
			mmStr = String("0"+mm);
		} else {
			mmStr = String(mm);
		}
		if (ss<10) {
			ssStr = String("0"+ss);
		} else {
			ssStr = String(ss);
		}
		return mmStr + ":" + ssStr;

	}
}