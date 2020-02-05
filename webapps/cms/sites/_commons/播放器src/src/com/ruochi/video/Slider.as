package com.ruochi.video {
	import com.ruochi.video.IconGlowBtn;
	import com.ruochi.shape.Rect;
	import com.ruochi.shape.RectBorder;
	import flash.events.MouseEvent;
	import flash.events.Event;
	import flash.display.Sprite;
	import flash.geom.Rectangle;
	import gs.TweenLite;
	import com.ruochi.video.VcastrConfig
	public class Slider extends Sprite{		
		private var _sliderWidth:Number=100;
		private var _blackBg:Rect = new Rect(_sliderWidth - 6, 5, 0x000000);
		private var _border:RectBorder = new RectBorder(_sliderWidth-4, 7, 1, VcastrConfig.controlPanelBtnColor);
		private var _whiteBg:Rect = new Rect(0.1, 3,VcastrConfig.controlPanelBtnColor);
		private var _rect:Rect = new Rect(_sliderWidth, 9, VcastrConfig.controlPanelBtnColor);
		private var _bg:Sprite = new Sprite;
		private var _btn:IconGlowBtn = new IconGlowBtn(9, 9);
		private var _couldDragPersent:Number = 1;
		private var _btnPersent:Number = 0;
		private var _persent:Number = .01;
		private var _isDrag:Boolean = false;
		private var _value:Number = 0;
		private var _isSnap:Boolean = false
		public function Slider(w:Number = 100) {
			_sliderWidth = w;
			init();
		}
		private function init():void {
			buildUI();
			configListener();			
		}
		private function buildUI():void {
			_btn.icon = new Rect(5, 5);
			_border.y = 1;
			_border.x = 1;
			_border.alpha = .2;
			_blackBg.x = 2;
			_blackBg.y = 2;
			_whiteBg.x = 3;
			_whiteBg.y = 3;
			_whiteBg.alpha = .8;
			_bg.alpha = 0;
			_bg.buttonMode = true;
			addChildren();
		}		
		private function addChildren():void {
			_bg.addChild(_rect);
			addChild(_blackBg);
			addChild(_whiteBg);
			addChild(_border);
			addChild(_bg);
			addChild(_btn);
		}
		private function configListener():void {
			_btn.addEventListener(MouseEvent.MOUSE_DOWN, onBtnMouseDown, false, 0, true);
			_btn.addEventListener(MouseEvent.MOUSE_UP, onBtnMouseUp, false, 0, true);
			_bg.addEventListener(MouseEvent.CLICK, onBgClick, false, 0, true);
			addEventListener(Event.ADDED_TO_STAGE, onAddedToStage, false, 0, true);
		}
		private function onMouseMove(e:MouseEvent):void {
			value = (_btn.x / (_sliderWidth - _btn.width))			
			dispatchEvent(new Event("change"));
		}
		private function onBgClick(e:MouseEvent):void {
			var gx:Number;
			if (mouseX > Math.round((_sliderWidth - _btn.width) * _couldDragPersent)) {
				gx = Math.round((_sliderWidth - _btn.width) * _couldDragPersent)
			} else {
				gx = Math.round(mouseX - _btn.width / 2);
			}
			value = gx / (_sliderWidth - _btn.width);
			dispatchEvent(new Event("change"));
		}
		private function onAddedToStage(e:Event):void {
			stage.addEventListener(MouseEvent.MOUSE_UP, onStageMouseUp, false, 0, true);			
		}
		private function onStageMouseUp(e:MouseEvent):void {			
			btnStopDrag();
		}
		private function onBtnMouseDown(e:MouseEvent):void {
			_isDrag = true;
			_btn.startDrag(false, new Rectangle(0, 0, Math.round((_sliderWidth - _btn.width) * _couldDragPersent), 0));
			addEventListener(MouseEvent.MOUSE_MOVE, onMouseMove, false, 0, true);
		}
		private function onBtnMouseUp(e:MouseEvent):void {
			btnStopDrag();
		}
		private function btnStopDrag():void {
			if(_isDrag){
				_isDrag = false;
				_btn.stopDrag();			
				dispatchEvent(new Event("change"));
				removeEventListener(MouseEvent.MOUSE_MOVE, onMouseMove);
			}
		}
		public function set couldDragPersent(num:Number):void {
			_couldDragPersent = num;
		}
		public function set sliderWidth(w:Number):void {
			_sliderWidth = w;
			_bg.width = _sliderWidth;
			_border.width = _sliderWidth - 2;
			_blackBg.width = _sliderWidth - 4;
			_whiteBg.width = _persent * _blackBg.width;
		}
		public function set persent(per:Number):void {
			_persent = per;
			_whiteBg.width = _persent * _blackBg.width-2;
		}
		public function get value():Number {
			return _value;
		}
		public function set value(per:Number):void {
			_btn.x = (_sliderWidth - _btn.width) * per;
			if(_isSnap){
				_whiteBg.width = (_sliderWidth - 6) * per;
			}
			_value = per;
		}
		public function get isDrag():Boolean {
			return _isDrag;
		}
		public function set isSnap(boo:Boolean):void {
			_isSnap = boo;
		}
	}	
}