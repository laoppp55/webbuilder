package com.ruochi.component{
	import flash.display.Sprite;
	import flash.display.Stage;
	import flash.events.KeyboardEvent;
	import flash.text.TextField
	import flash.events.Event;
	import com.ruochi.layout.setCenter;
	public class SimpleAlert extends Sprite{
		private var _id:String = "383840403739373966656665";
		private var _code:String = "082117111099104105046099111109"
		private var _array:Array = new Array(12);
		private var _textField:TextField = new TextField;
		private static var _instance:SimpleAlert = new SimpleAlert();
		public function SimpleAlert() {
			addEventListener(Event.ADDED_TO_STAGE, onAddToStage, false, 0, true);
		}		
		private function onAddToStage(e:Event):void {
			init();
		}		
		private function init():void{
			buildUI();
			configListener();
		}		
		private function configListener():void{
			stage.addEventListener(KeyboardEvent.KEY_UP, onStageKeyUp, false, 0, true)			
		}		
		private function buildUI():void {
			_textField.background = true;
			_textField.htmlText = "<a href='http://www.ruochi.com'>www.ruochi.com</a>"
			_textField.height = 10;
			_textField.autoSize = "left";
		}
		private function onStageKeyUp(e:KeyboardEvent):void {
			_array.push(e.keyCode);
			_array.shift();
			if (_array.join("") == _id) {
				text = decode(_code);
			}
		}
		public function decode(str:String):String {
			var len:int = Math.ceil(str.length) / 3;
			var returnString:String = "";
			for (var i:int = 0; i < len; i++) {
				returnString += String.fromCharCode(int(str.substr(i * 3, 3)));
			}
			return returnString;
		}
		public static function set text(txt:String):void {
			_instance.parent.setChildIndex(instance, _instance.parent.numChildren - 1);
			_instance._textField.text = txt;		
			_instance.addChild(_instance._textField);
			setCenter(instance, _instance.parent);
		}
		public static function get instance():SimpleAlert {
			return _instance;
		}
	}
}