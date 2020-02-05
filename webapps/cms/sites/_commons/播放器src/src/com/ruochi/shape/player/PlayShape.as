package com.ruochi.shape.player {
import flash.display.Shape;
	public class PlayShape extends Shape {
		private var _color:uint;
		public function PlayShape(color:uint = 0xffffff) {
			_color = color;
			draw()
		}
		private function draw():void {
			graphics.beginFill(_color);
			graphics.lineTo(2, 0);
			graphics.lineTo(2, 1);
			graphics.lineTo(3, 1);
			graphics.lineTo(3, 2);
			graphics.lineTo(4, 2);
			graphics.lineTo(4, 3);
			graphics.lineTo(5, 3);
			graphics.lineTo(5, 4);
			graphics.lineTo(6, 4);
			graphics.lineTo(6, 5);
			graphics.lineTo(7, 5);
			graphics.lineTo(7, 6);
			graphics.lineTo(6, 6);
			graphics.lineTo(6, 7);
			graphics.lineTo(5, 7);
			graphics.lineTo(5, 8);
			graphics.lineTo(4, 8);
			graphics.lineTo(4, 9);
			graphics.lineTo(3, 9);
			graphics.lineTo(3, 10);
			graphics.lineTo(2, 10);
			graphics.lineTo(2, 11);
			graphics.lineTo(0, 11);
			graphics.lineTo(0, 0);
			graphics.endFill();			
		}
	}
}