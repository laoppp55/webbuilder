package com.ruochi.video {
	import com.ruochi.component.SimpleAlert;
	import com.ruochi.video.Controller;
	import com.ruochi.video.VideoPlayer;
	import flash.system.Security;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.text.Font;
	
	public class Vcastr3 extends Sprite {
		private static var _instance:Vcastr3;
		[Embed(source = "../../../font/SG16.TTF", fontName = "SG16", mimeType = "application/x-font", unicodeRange = "U+0030-U+003A")]
		private var myFont:Class;
		public function Vcastr3() {
			if (!_instance) {
				_instance = this;
				if (myFont) {
					Font.registerFont(myFont);
				}
				init();
			}else {
				throw new Error("singleton");
			}
		}
		public function init():void {
			Security.allowDomain("*");			
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE
			stage.addChild(SimpleAlert.instance);
			addChild(VideoPlayer.instance);
			Controller.instance.loadConfig(loaderInfo.parameters["xml"]);
			stage.addEventListener(Event.RESIZE, onStageResize, false, 0, true);
		}
		
		private function onStageResize(e:Event):void {
			Controller.instance.setLayout();
		}
		static public function get instance():Vcastr3 {
			return _instance;
		}
	}
}