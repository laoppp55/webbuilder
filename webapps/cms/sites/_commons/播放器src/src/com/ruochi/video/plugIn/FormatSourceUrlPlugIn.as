package com.ruochi.video.plugIn {
	import flash.display.Sprite
	import com.ruochi.video.IController;
	public class FormatSourceUrlPlugIn extends Sprite implements IVcastrPlugIn {
		private var _controller:IController;
		public function FormatSourceUrlPlugIn() {
				
		}
		
		public function set dataXml(xml:XML):void {
			
		}
		
		public function init(controller:IController):void {
			_controller = controller;
			var length:int = _controller.dataXml.channel[0].item.length();
			for (var i:int = 0; i < length; i++) {
				_controller.dataXml.channel[0].item[i].source[0] = _controller.dataXml.channel[0].item[i].source[0];
				/*
				 * 可以只在xml中写文件名，在这里加入路径，这样可以起到文件地址的保密
				_controller.dataXml.channel[0].item[i].source[0] = "http://www.abc.com/video/" + _controller.dataXml.channel[0].item[i].source[0];
				 * 
				*/
			}
		}
	}
}