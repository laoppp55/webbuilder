package com.ruochi.shape{
	import flash.display.Shape;
	public class RectBorder extends Shape {
		private var _color:uint = 0xffffff;
		private var _w:Number;
		private var _h:Number;
		private var _borderWidth:Number;
		public function RectBorder(w:Number=100,h:Number=100,bw:Number=1,c:uint=0xff0000) {
			super();
			_color = c;
			_w = w;
			_h = h;
			_borderWidth = bw;
			draw()
		}
		private function draw():void {
			graphics.clear();
			graphics.beginFill(_color);
            graphics.drawRect(0, 0, _w, _h);
			graphics.drawRect(_borderWidth, _borderWidth, _w-_borderWidth*2, _h-_borderWidth*2);
            graphics.endFill();
		}
		public function set color(c:uint):void {
			_color = c;
			draw()
		}
		public function get color():uint {
			return _color;
		}
		override public function get width():Number { return super.width; }
		
		override public function set width(value:Number):void {
			_w = value
			draw();
		}
		override public function get height():Number { return super.height; }
		
		override public function set height(value:Number):void {
			_h = value;
			draw();
		}
	}
}