package com.ruochi.text{
	import flash.text.TextField;
	import flash.text.TextFormat;
	import flash.text.AntiAliasType;
	import flash.text.TextFieldAutoSize;
	import flash.text.GridFitType;
	public class EmbedText extends TextField {
		private var _tf:TextFormat = new TextFormat();
		private var _color:uint = 0xffffff;
		private var _font:String = "Calibri";
		private var _size:Number = 12;
		private var _align:String = "left";
		public function EmbedText() {
			super();
			selectable = false;
			height = 1;
			wordWrap = false;
			autoSize = TextFieldAutoSize.LEFT			
			antiAliasType = AntiAliasType.ADVANCED;
			embedFonts = true;
			init();
		}
		public function init():void {
			_tf.font = _font;
			_tf.color = _color;
			_tf.size = _size;
			_tf.align = _align;
			defaultTextFormat = _tf;
			setTextFormat(_tf);			
		}
		public function set color(_c:uint):void {
			_color = _c;
			init();
		}
		public function get color():uint {
			return _color;
		}
		public function set align(_str:String):void {
			_align = _str;
			init();
		}
		public function set size(_n:Number):void {
			_size = _n;
			init();
		}
		public function set font(_str:String):void {
			_font = _str;
			init();
		}
		public function get tf():TextFormat {
			return _tf;
		}
	}
}