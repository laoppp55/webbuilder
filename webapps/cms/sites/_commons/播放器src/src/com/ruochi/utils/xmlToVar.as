package com.ruochi.utils{
	public function  xmlToVar(xml:XML, ob:Object):void {
		if(xml){
			var xmlList:XMLList = xml.*;
			for (var i:int = 0; i < xmlList.length(); i++) {
				if (xmlList[i] == "true") {
					ob[xmlList[i].name()] = true
				}else if(xmlList[i] == "false") {
					ob[xmlList[i].name()] = false
				}else {
					ob[xmlList[i].name()] = xmlList[i];
				}			
			}
		}
	}
}