package com.ruochi.layout {
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.display.Stage;
	public function place(displayObject:DisplayObject, margin:Margin = null, containerObject:DisplayObject = null) :void {
		if (!margin) {
			margin = new Margin();
		}
		if (!containerObject) {
			containerObject = displayObject.parent;
		}
		var containerWidth:Number;
		var containerHight:Number;
		
		if (containerObject is Stage) {
			containerWidth = (containerObject as Stage).stageWidth;
			containerHight = (containerObject as Stage).stageHeight;
		}else if (containerObject.parent is Stage) {
			containerWidth = (containerObject.parent as Stage).stageWidth;
			containerHight = (containerObject.parent as Stage).stageHeight;
		}else if (containerObject is Sprite) {
			containerWidth = containerObject.width;
			containerHight = containerObject.height;
		}	
		if (margin.left != Margin.AUTO) {
			displayObject.x = Number(margin.left);
		}else if (margin.right != Margin.AUTO) {
			displayObject.x = containerWidth - displayObject.width - Number(margin.right);
		}else {
			displayObject.x = (containerWidth - displayObject.width) / 2;
		}
		if (margin.top != Margin.AUTO) {
			displayObject.y = Number(margin.top);
		}else if (margin.bottom != Margin.AUTO) {
			displayObject.y = containerHight - displayObject.height - Number(margin.bottom);
		}else {
			displayObject.y = (containerHight - displayObject.height) / 2;
		}
	}		
}
