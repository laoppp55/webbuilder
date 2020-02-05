package com.ruochi.shape{
	import flash.display.Shape;
	import flash.geom.Rectangle;
	public class StripeShape extends Shape {
		private var _w:Number;
		private var _h:Number;
		private var _color1:uint;
		private var _color2:uint;
		public function StripeShape(w:Number = 80, h:Number = 10, c1:uint = 0xffffff, c2:uint = 0 ) {
			super()
			_w = w;
			_h = h;
			_color1 = c1;
			_color2 = c2;
			draw();
		}
		private function draw():void {
			graphics.clear();
			graphics.beginFill(_color1);
			graphics.drawRect(0, 0, _w, _h);
			graphics.endFill();
			var num:int = _w/_h/2;
			graphics.beginFill(_color2);			
			for (var i:int = 0; i < num; i++) {
				graphics.moveTo(i*2 * _h, _h);
				graphics.lineTo((i*2 + 1) * _h, 0);
				graphics.lineTo((i*2 + 2) * _h, 0);
				graphics.lineTo((i*2 + 1) * _h, _h);
				graphics.lineTo(i*2 * _h, _h);
			}
			graphics.endFill();
		}
		public function set color1(c:uint):void {
			_color1 = c;
			draw();
		}
		public function get color1():uint {
			return _color1;
		}
		public function set color2(c:uint):void {
			_color2 = c;
			draw();
		}
		public function get color2():uint {
			return _color2;
		}
	}
}