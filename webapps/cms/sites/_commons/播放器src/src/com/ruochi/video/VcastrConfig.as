package com.ruochi.video {	
	public dynamic class VcastrConfig {	
		public static const FLOAT:String = "float";
		public static const NORMAL:String = "normal";
		public static const BOTTOM:String = "bottom";
		public static const NONE:String = "none";
		public static var bufferTime:Number = 4;
		public static var videoWidth:int = -1;
		public static var videoHeight:int = -1;
		public static var videoX:int = 0;
		public static var videoY:int = 0;
		public static var controlPanelAlpha:Number = .75;
		public static var controlPanelBgColor:uint = 0xff6600;
		public static var controlPanelBtnColor:uint = 0xffffff;
		public static var controlPanelBtnGlowColor:uint = 0xffff00;		
		public static var controlPanelMode:String = FLOAT;
		public static var dataXml:XML;
		public static var defautVolume:Number = .8;
		public static var isAutoPlay:Boolean = true;
		public static var isLoadBegin:Boolean = true;
		public static var isShowAbout:Boolean = true;
		public static var scaleMode:String = ScaleUtils.SHOW_ALL;
		public static var xml:String = "vcastr.xml";
		public static var isMulitVideo:Boolean = false;
		public static var textItemHeight:Number = 24;
		public static var multiVideoCompactBoundary:int = 240;
		public static var singleVideoCompactBoundary:int = 180;
		public static var isRepeat:Boolean = false;
	}	
}