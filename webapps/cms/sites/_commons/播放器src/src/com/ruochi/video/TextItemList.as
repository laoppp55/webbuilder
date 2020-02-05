package com.ruochi.video {
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import gs.TweenLite;
	import com.ruochi.video.Controller;
	import com.ruochi.video.VcastrConfig;
	public class TextItemList extends Sprite{
		private var _dataXml:XML
		private var _length:int;
		private var _isOpen:Boolean;
		private var _activeId:int = -1;
		private static var _instance:TextItemList = new TextItemList();
		public function TextItemList() {
			if (!_instance) {
				
			}else {
				throw new Error("singleton");
			}
		}		
		
		public function init(xml:XML):void {
			_dataXml = xml;
			_length = _dataXml.channel.item.length();
			var maxW:int = 0;
			var textItem:TextItem;
			var i:int;
			for (i = 0; i < _length; i++) {
				textItem = new TextItem();
				textItem.dataXml = _dataXml.channel.item[i];
				textItem.addEventListener(MouseEvent.CLICK, onTextItemClick, false, 0, true);
				addChild(textItem);
				if (textItem.width > maxW) {
					maxW = textItem.width;
				}
			}
			for (i = 0; i < _length; i++) {
				textItem = getChildAt(i) as TextItem;
				textItem.width = maxW;
			}
		}
		
		private function onTextItemClick(e:MouseEvent):void {
			Controller.instance.gotoVideoAt((e.currentTarget as TextItem).id);
			Controller.instance.openCloseList();
		}
		
		public function open():void {
			for (var i:int = 0; i < _length; i++) {
				(getChildAt(i) as TextItem).styleText.visible = true;
				TweenLite.to(getChildAt(i), .2, { y: -(_length -i-1) * (VcastrConfig.textItemHeight + 1), autoAlpha:1 } ); 
			}
			_isOpen = true;
		}
		
		public function close():void {
			for (var i:int = 0; i < _length; i++) {
				(getChildAt(i) as TextItem).styleText.visible = false;
				TweenLite.to(getChildAt(i), .2, { y: -((_length -i-1) + .5) * (VcastrConfig.textItemHeight + 1), autoAlpha:0 } ); 
			}
			_isOpen = false;
		}
		
		public function get isOpen():Boolean {
			return _isOpen;
		}
		
		public static function get instance():TextItemList {
			return _instance;
		}
		
		public function set activeId(id:int):void {
			if (_activeId > -1) {
				(getChildAt(_activeId) as TextItem).isEnable = true;
			}
			_activeId = id; 
			(getChildAt(_activeId) as TextItem).isEnable = false;
		}
	}	
}