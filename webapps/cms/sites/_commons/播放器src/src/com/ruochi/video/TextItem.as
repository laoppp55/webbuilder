package com.ruochi.video {
	import flash.events.MouseEvent;
	import flash.display.Sprite;
	import com.ruochi.text.StyleText;
	import com.ruochi.shape.Rect;
	import com.ruochi.video.VcastrConfig;
	public class TextItem extends Sprite {
		private var _isEnable:Boolean = true;
		private var _styleText:StyleText = new StyleText();
		private var _dataXml:XML;
		private var _bg:Rect = new Rect(100,VcastrConfig.textItemHeight,VcastrConfig.controlPanelBgColor);
		public function TextItem() {
			init();
		}
		private function init():void {
			setChildren();
			addChildren();
			configeListener();
		}
		
		private function configeListener():void{
			addEventListener(MouseEvent.MOUSE_OVER, onMouseOver, false, 0, true);
			addEventListener(MouseEvent.MOUSE_OUT, onMouseOut, false, 0, true);
		}
		
		private function onMouseOut(e:MouseEvent):void {
			if (_isEnable) {				
				_bg.alpha = VcastrConfig.controlPanelAlpha;
			}else {
				_bg.alpha = .2;
			}
			//}
		}
		
		private function onMouseOver(e:MouseEvent):void {
			_bg.alpha = .8
		}
		
		private function addChildren():void{
			addChild(_bg);
			addChild(_styleText);			
		}
		
		private function setChildren():void {			
			buttonMode = true;
			visible = false;
			alpha = 0;
			_bg.alpha = VcastrConfig.controlPanelAlpha;
			_styleText.mouseEnabled = false;
		}
		
		override public function set width(value:Number):void { 
			_bg.width = value;
			_bg.x = - _bg.width;
			_styleText.x =  - _styleText.width - 5;
		}
		
		public function get styleText():StyleText {
			return _styleText
		}
		
		public function set isEnable(b:Boolean):void {
			_isEnable = b;
			if(_isEnable){
				_bg.alpha = VcastrConfig.controlPanelAlpha;
			}else {
				_bg.alpha = .2;
			}
			mouseEnabled = _isEnable;
		}
		
		public function get id():int {
			return _dataXml.childIndex();
		}
		
		public function set dataXml(xml:XML):void {
			_dataXml = xml;
			if(_dataXml.title[0]!=undefined){
				_styleText.text = _dataXml.title[0];
			}else {
				_styleText.text = _dataXml.source[0];
			}
			_bg.width = _styleText.width +10;
		}
	}	
}
