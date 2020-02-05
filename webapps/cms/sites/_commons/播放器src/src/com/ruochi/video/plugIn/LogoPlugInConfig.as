package com.ruochi.video.plugIn {
	import com.ruochi.layout.Margin;
	public class LogoPlugInConfig {
		public static var logoText:String = "";
		public static var logoTextAlpha:Number = .75;
		public static var logoTextFontSize:int = 24;
		public static var logoTextColor:uint = 0xffffff;
		public static var logoTextLink:String = "";
		public static var logoTextMargin:Margin = new Margin(10, null, null, 10);
		public static var logoClipUrl:String;
		public static var logoClipAlpha:Number = 1;
		public static var logoClipMargin:Margin = new Margin(10, 10, null, null);
		public static var url:String;		
		public static var logoClipLink:String = "";
		public static var windowOpen:String = "_blank";
		public static function set textMargin(s:String) {
			logoTextMargin = new Margin(s);
		}
		public static function set clipMargin(s:String) {
			logoClipMargin = new Margin(s);
		}
	}	
}
