package com.ruochi.video{
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.display.MovieClip;
	import flash.events.MouseEvent;
	import flash.events.Event;
	import flash.events.FocusEvent;
	import com.robertpenner.easing.*
	import gs.TweenFilterLite;
	import gs.TweenLite;
	import com.ruochi.shape.Rect;
	import com.ruochi.layout.setCenter;
	import com.ruochi.video.VcastrConfig;
	import com.ruochi.utils.setColor;
	public class IconGlowBtn extends Sprite {
		private var _enable:Boolean = true;
		private var _frame:int = 1;
		private var _clickArea:Rect = new Rect(15,15,0xff0000);
		private var _iconWrapper:Sprite = new Sprite();
		private var _btnWidth:Number;
		private var _btnHeight:Number;
		public function IconGlowBtn(w:Number = 15,h:Number = 15) {
			_btnWidth = w;
			_btnHeight = h;
			init();
		}
		private function onOverEffect(e:Event):void  {	
			TweenFilterLite.to(_iconWrapper, 1, { glowFilter: { color:VcastrConfig.controlPanelBtnGlowColor, alpha:1, blurX:4, blurY:4, strength:3 }, ease:Elastic.easeOut,  overwrite:true } );
		}
		private function onOutEffect(e:Event):void  {		
			TweenFilterLite.to(_iconWrapper, .8, { glowFilter: { color:VcastrConfig.controlPanelBtnGlowColor, alpha:0, blurX:2, blurY:2 , strength:3}, overwrite:true } );
		}
		private function onClickEffect(e:MouseEvent) :void {
			if (_enable != false) {
				TweenFilterLite.to(this, .1, { glowFilter: { color:VcastrConfig.controlPanelBtnGlowColor, alpha:1, blurX:3, blurY:3 , strength:3 }, overwrite:false } );
				TweenFilterLite.to(this, .3, { glowFilter: { color:VcastrConfig.controlPanelBtnGlowColor, alpha:0, blurX:2, blurY:2 , strength:3 }, delay:.1, overwrite:false } );
			}
		}
		private function init():void  {
			buildUI();
		}
		private function buildUI():void  {			
			buttonMode = true;
			_clickArea.width = _btnWidth;
			_clickArea.height = _btnHeight;			
			_clickArea.alpha = 0;
			addChildren();
			configListener();		
		}
		private function addChildren():void {			
			addChild(_iconWrapper);			
			addChild(_clickArea);		
		}
		private function configListener():void  {
			addEventListener(MouseEvent.MOUSE_OVER, onOverEffect, false, 0, true);
			addEventListener(FocusEvent.FOCUS_IN, onOverEffect, false, 0, true);
			addEventListener(FocusEvent.FOCUS_OUT, onOutEffect, false, 0, true);
			addEventListener(MouseEvent.MOUSE_OUT, onOutEffect, false, 0, true);
			addEventListener(MouseEvent.MOUSE_DOWN, onClickEffect, false, 0, true);
		}
		public function set enable(en:Boolean):void  {
			if (en) {				
				TweenLite.to(this,.5,{alpha:1, overwrite:false});
			} else {
				TweenLite.to(this,.5,{alpha:.2, overwrite:false});
			}
			_enable = en;
			mouseEnabled = _enable;
			mouseChildren = _enable;
		}
		public function get enable():Boolean {
			return _enable;
		}
		public function set icon(icon:Shape):void {
			setCenter(icon, _clickArea)
			setColor(icon, VcastrConfig.controlPanelBtnColor);
			if (_iconWrapper.numChildren > 0) {
				icon.visible = false;
			}
			_iconWrapper.addChild(icon);
		}
		public function set frame(num:int):void {
			_iconWrapper.getChildAt(_frame-1).visible = false;
			_frame = num;
			_iconWrapper.getChildAt(_frame-1).visible = true;
		}
	}
}