package com.ruochi.video.plugIn {
	import flash.text.TextField;
	import com.ruochi.sprites.RectSprite;
	import com.ruochi.video.IController;
	import flash.display.Sprite;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import com.ruochi.layout.place;
	import com.ruochi.utils.xmlToVar;
	import com.ruochi.text.EmbedText;
	import com.ruochi.video.plugIn.LogoPlugInConfig;
	import com.ruochi.video.VideoEvent;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	public class LogoPlugIn extends Sprite implements IVcastrPlugIn{
		private var _logoText:EmbedText = new EmbedText();
		private var _logoClipSprite:Sprite = new Sprite;
		private var _rect:RectSprite = new RectSprite();
		private var _logoTextWrapper:Sprite = new Sprite();
		private var _dataXml:XML;
		private var _controller:IController;
		private	var _loader:Loader = new Loader();
		public function LogoPlugIn() {
			
		}
		public function set dataXml(xml:XML):void {
			_dataXml = xml;
		}
		public function init(controller:IController):void {
			_controller = controller;			
			xmlToVar(_dataXml, LogoPlugInConfig); 
			if (LogoPlugInConfig.logoText != "") {				
				_logoText.embedFonts = true;
				_logoText.text = LogoPlugInConfig.logoText;
				_logoText.alpha = LogoPlugInConfig.logoTextAlpha;
				_logoText.size = LogoPlugInConfig.logoTextFontSize;
				_logoText.color = LogoPlugInConfig.logoTextColor;
				_logoTextWrapper.addChild(_logoText);
				addChild(_logoTextWrapper);
				if (LogoPlugInConfig.logoTextLink != "") {
					_rect.alpha = 0;
					_rect.width = _logoText.width;
					_rect.height = _logoText.height;
					_logoTextWrapper.addChild(_rect);
					_logoTextWrapper.buttonMode = true; 
					_logoTextWrapper.useHandCursor =  true;
					_logoTextWrapper.addEventListener(MouseEvent.CLICK, onLogoTextWrapperClick, false, 0, true);
				}
				setLayout();
			}
			if (LogoPlugInConfig.logoClipUrl) {
				addChild(_logoClipSprite);
				_loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onLoaderComplete, false, 0, true);
				_loader.load(new URLRequest(LogoPlugInConfig.logoClipUrl));
			}
			_controller.addEventListener(VideoEvent.LAYOUT_CHANGE, onVcastrLayoutChange, false, 0, true);
		}		
		
		private function onVcastrLayoutChange(e:Event):void {
			setLayout();
		}
		public function setLayout():void {
			place(_logoTextWrapper, LogoPlugInConfig.logoTextMargin, _controller.videoPlayer);
			place(_logoClipSprite, LogoPlugInConfig.logoClipMargin, _controller.videoPlayer);
		}
		
		private function onLoaderComplete(e:Event):void {
			_logoClipSprite.addChild(_loader);
			_logoClipSprite.alpha = LogoPlugInConfig.logoClipAlpha;			
			if (LogoPlugInConfig.logoClipLink != ""){
				_logoClipSprite.buttonMode = true;
				_logoClipSprite.addEventListener(MouseEvent.CLICK, onlogoSpriteClick, false, 0, true);
			}
			setLayout();
		}
		
		private function onlogoSpriteClick(e:MouseEvent):void {
			navigateToURL(new URLRequest(LogoPlugInConfig.logoClipLink), LogoPlugInConfig.windowOpen);
		}
		
		private function onLogoTextWrapperClick(e:MouseEvent):void {
			navigateToURL(new URLRequest(LogoPlugInConfig.logoTextLink), LogoPlugInConfig.windowOpen);
		}
	}	
}