package com.ruochi.utils{
	import flash.display.DisplayObjectContainer;
	public function deleteAll(displayObjectContainer:DisplayObjectContainer):void {
		while (displayObjectContainer.numChildren) {
			displayObjectContainer.removeChildAt(0);
		}
	}
}