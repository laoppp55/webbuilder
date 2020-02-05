package com.ruochi.video {
	import flash.events.IEventDispatcher;
	import flash.display.DisplayObject;
	public interface IController extends IEventDispatcher {
		function get videoPlayer () : DisplayObject;
		function get dataXml () : XML;
		function setLayout () : void;
		function videoLink () : void;
		function playPause () : void;
		function play () : void;
		function pause () : void;
		function stop () : void;
		function ff () : void;
		function rew () : void;
		function gotoVideoAt (id:int) : void;
		function openCloseList () : void;
		function soundOnOff () : void;
		function volumeTo (value:Number) : void;
		function seek (offset:Number) : void;
		function seekPersent (per:Number) : void;
		function next () : void;
		function prev () : void;
		function playerSizeTo (w:int, h:int) : void;
		function playerMoveTo (px:int, py:int) : void;
		function get state():String ;
		function get bytesLoadedPersent():Number ;
		function get playHeadTime():int ;
	}
}
