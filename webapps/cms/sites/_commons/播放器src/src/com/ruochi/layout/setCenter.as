package com.ruochi.layout{
	import flash.display.DisplayObject;
	import flash.display.Stage;
	public function setCenter(displayObject:DisplayObject,displayObjectContainer:DisplayObject=null):void {
		if (displayObjectContainer != null) {
			if(displayObjectContainer is Stage){
				displayObject.x = Math.round(((displayObjectContainer as Stage).stageWidth -displayObject.width) / 2);
				displayObject.y = Math.round(((displayObjectContainer as Stage).stageHeight -displayObject.height) / 2);
			} else {
				displayObject.x = Math.round((displayObjectContainer.width -displayObject.width) / 2);
				displayObject.y = Math.round((displayObjectContainer.height -displayObject.height) / 2);
			}
		} else {
			displayObject.x = Math.round(-displayObject.width / 2);
			displayObject.y = Math.round(-displayObject.height / 2);
		}
	}
}
