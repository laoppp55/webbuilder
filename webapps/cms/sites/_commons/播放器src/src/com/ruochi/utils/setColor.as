package com.ruochi.utils {
	import flash.geom.ColorTransform;
	import flash.display.DisplayObject;
	public function setColor(displayObject:DisplayObject , color:uint):void {
		var colorTransform:ColorTransform = new ColorTransform();
		colorTransform.color = color
		displayObject.transform.colorTransform = colorTransform;
	}
}