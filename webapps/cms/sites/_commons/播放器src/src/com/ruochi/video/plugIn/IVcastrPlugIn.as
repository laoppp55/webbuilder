package com.ruochi.video.plugIn {
	import com.ruochi.video.IController;
	public interface IVcastrPlugIn {
		function init(controller:IController):void;
		function set dataXml(xml:XML):void
	}	
}