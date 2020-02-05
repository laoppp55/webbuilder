package com.ruochi.shape.player {
import flash.display.Shape;
	public class FullScreenOffShape extends Shape {
		private var _color:uint;
		public function FullScreenOffShape(color:uint = 0xffffff) {
			_color = color;
			draw()
		}
		private function draw():void {
			graphics.beginFill(_color);
			graphics.drawRect(0, 0, 10, 9);
			graphics.drawRect(1, 1, 8, 7);
			graphics.drawRect(4, 2, 4, 3);
			graphics.drawRect(2, 5, 1, 2);
			graphics.drawRect(3, 6, 1, 1);
			graphics.endFill();			
		}
	}
}