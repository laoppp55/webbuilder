package com.ruochi.component{
	import com.ruochi.shape.RectBorder;
	import flash.display.Sprite;
	import com.ruochi.shape.StripeShape;
	import flash.events.Event;
	import com.ruochi.shape.Rect;
	public class StripeProgressBar extends Sprite {
		private var _speed:Number = .5;
		private var _color1:uint;
		private var _color2:uint;
		private var _stripeShape:StripeShape;
		private var _mask:Rect;
		private var _w:Number;
		private var _h:Number;
		private var _barWidth:Number;
		private var _barHeight:Number;
		private var _isStart:Boolean = false;
		private var _bg:Rect;
		private var _border1:RectBorder
		private var _border2:RectBorder
		public function StripeProgressBar(w:Number=80,h:Number=10,c1:uint=0xffffff,c2:uint=0) {
			visible = false;
			_barWidth = w;
			_barHeight = h;
			_color1 = c1;
			_color2 = c2;
			buildUI();			
		}
		private function buildUI():void {
			_bg = new Rect(_barWidth + _barHeight * 4, _barHeight);
			_bg.alpha = 0;
			_mask = new Rect(_barWidth, _barHeight);
			_stripeShape  = new StripeShape(_barWidth + _barHeight*2,_barHeight, _color1, _color2);
			_stripeShape.x = 0;
			_stripeShape.mask = _mask;
			_mask.x = _barHeight * 2;
			_border1 = new RectBorder(_barWidth, _barHeight, 1, _color2);			
			_border2 = new RectBorder(_barWidth - 2, _barHeight - 2, 1, _color1);
			_border1.x =_barHeight * 2;
			_border2.x = 1 + _barHeight * 2;;
			_border2.y = 1;
			addChild(_bg);
			addChild(_stripeShape);
			addChild(_mask);
			addChild(_border1);
			addChild(_border2)
		}
		private function onEnterFrame(e:Event):void{
			_stripeShape.x += _speed;
			if (_stripeShape.x >= _barHeight*2) {
				_stripeShape.x = 0;
			}
		}
		public function start():void {
			if (!_isStart) {
				visible = true;
				addEventListener(Event.ENTER_FRAME, onEnterFrame, false, 0, true);
				_isStart = true;
			}		
		}
		public function stop():void {
			visible = false;
			removeEventListener(Event.ENTER_FRAME, onEnterFrame);
			_isStart = false;
		}
		public function get barWidth():Number {
			return _barWidth;
		}
		public function get barHeight():Number {
			return _barHeight;
		}
		public function get stripeShape():StripeShape {
			return _stripeShape
		}
	}
}