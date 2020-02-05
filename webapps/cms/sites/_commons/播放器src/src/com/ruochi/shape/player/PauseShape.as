package com.ruochi.shape.player {
import flash.display.Shape;
	public class PauseShape extends Shape {
		private var _color:uint;
		public function PauseShape(color:uint = 0xffffff) {
			_color = color;
			draw()
		}
		private function draw():void {
			graphics.beginFill(_color);
			graphics.drawRect(0, 0, 3, 11);
			graphics.drawRect(6, 0, 3, 11);
			graphics.endFill();			
		}
	}
}