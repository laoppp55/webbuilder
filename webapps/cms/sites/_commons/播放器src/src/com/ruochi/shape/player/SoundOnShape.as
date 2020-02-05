package com.ruochi.shape.player {
import flash.display.Shape;
	public class SoundOnShape extends Shape {
		private var _color:uint;
		public function SoundOnShape(color:uint = 0xffffff) {
			_color = color;
			draw()
		}
		private function draw():void {
			graphics.beginFill(_color);
			graphics.lineTo(0, 3);
			graphics.lineTo(2, 3);
			graphics.lineTo(2, 2);
			graphics.lineTo(3, 2);
			graphics.lineTo(3, 1);
			graphics.lineTo(4, 1);
			graphics.lineTo(4, 0);
			graphics.lineTo(5, 0);
			graphics.lineTo(5, 9);
			graphics.lineTo(4, 9);
			graphics.lineTo(4, 8);
			graphics.lineTo(3, 8);
			graphics.lineTo(3, 7);
			graphics.lineTo(2, 7);
			graphics.lineTo(2, 6);
			graphics.lineTo(0, 6);
			graphics.lineTo(0, 3);
			graphics.drawRect(7, 3, 1, 3);
			graphics.drawRect(8, 1, 1, 1);
			graphics.drawRect(9, 2, 1, 5);
			graphics.drawRect(8, 7, 1, 1);
			graphics.endFill();			
		}
	}
}