package com.ruochi.shape.player {
import flash.display.Shape;
	public class FullScreenOnShape extends Shape {
		private var _color:uint;
		public function FullScreenOnShape(color:uint = 0xffffff) {
			_color = color;
			draw()
		}
		private function draw():void {
			graphics.beginFill(_color);
			graphics.drawRect(0, 0, 10, 9);
			graphics.drawRect(1, 1, 8, 7);
			graphics.drawRect(2, 4, 4, 3);
			graphics.drawRect(6, 2, 1, 1);
			graphics.drawRect(7, 2, 1, 2);
			graphics.endFill();			
		}
	}
}