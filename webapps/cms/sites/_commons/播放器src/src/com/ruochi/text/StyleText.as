package com.ruochi.text{
	import flash.text.TextField;
	import flash.text.TextFormat;
	import flash.text.AntiAliasType;
	import flash.text.TextFieldAutoSize;
	public class StyleText extends TextField {
		private var _textFormat:TextFormat = new TextFormat();
		private var _color:uint = 0xffffff;
		private var _font:String = "微软雅黑";
		private var _size:Number = 12;
		private var _align:String = "left";
		public function StyleText() {
			super();
			selectable = false;
			height = 1;
			wordWrap = false;
			autoSize = TextFieldAutoSize.LEFT;	
			antiAliasType = AntiAliasType.ADVANCED;
			draw();
		}
		private function draw():void {
			_textFormat.font = _font;
			_textFormat.color = _color;
			_textFormat.size = _size;
			_textFormat.align = _align;			
			defaultTextFormat = _textFormat;
			setTextFormat(_textFormat);			
		}
		public function set color(_c:uint):void {
			_color = _c;
			draw();
		}
		public function set align(_str:String):void {
			autoSize = _str;
			_align = _str;
			draw();
		}
		public function set size(_n:Number):void{
			_size = _n;
			draw();
		}
		public function set font(_str:String):void {
			_font = _str;
			draw();
		}
		public function set textFormat(tf:TextFormat):void {
			_textFormat = tf;
			draw()
		}
		public function get textFormat():TextFormat {
			return _textFormat;
		}
		public function set bold(b:Boolean):void {
			_textFormat.bold = b;
			draw();
		}
	}
}