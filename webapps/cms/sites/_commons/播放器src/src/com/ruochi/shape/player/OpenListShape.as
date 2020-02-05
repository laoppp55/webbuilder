package com.ruochi.shape.player {
import flash.display.Shape;
	public class OpenListShape extends Shape {
		private var _color:uint;
		public function OpenListShape(color:uint = 0xffffff) {
			_color = color;
			draw()
		}
		private function draw():void {
			graphics.beginFill(_color);
			graphics.drawRect(0, 0, 10, 9);
			graphics.drawRect(1, 1, 8, 7);
			graphics.drawRect(2, 2, 6, 1);
			graphics.drawRect(2, 4, 6, 1);
			graphics.drawRect(2, 6, 6, 1);
			graphics.endFill();			
		}
	}
}