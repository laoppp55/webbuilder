package com.ruochi.video.plugIn {
	import com.ruochi.video.IController;
	import flash.display.Sprite;
	import com.ruochi.video.VideoEvent;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.external.ExternalInterface;
	import flash.system.Security;
	public class JavaScriptPlugIn extends Sprite implements IVcastrPlugIn {
		private var _controller:IController;
		public function JavaScriptPlugIn() {
			Security.allowDomain("*");
		}
		
		public function set dataXml(xml:XML):void {
			
		}
		
		public function init(controller:IController):void {
			_controller = controller;
			configListener();
			addCallBack();
		}
		
		private function configListener():void {
			_controller.addEventListener(VideoEvent.STATE_CHANGE, onVideoEvent, false, 0, true);
			_controller.addEventListener(VideoEvent.COMPLETE, onVideoEvent, false, 0, true);
			_controller.addEventListener(VideoEvent.INIT, onVideoEvent, false, 0, true);
			_controller.addEventListener(VideoEvent.READY, onVideoEvent, false, 0, true);
			_controller.addEventListener(VideoEvent.PLAYHEAD_UPDATE, onVideoEvent, false, 0, true);
			_controller.addEventListener(VideoEvent.START_BUFFERING, onVideoEvent, false, 0, true);
			_controller.addEventListener(VideoEvent.STOP_BUFFERING, onVideoEvent, false, 0, true);
			_controller.addEventListener(VideoEvent.STOP, onVideoEvent, false, 0, true);			
		}	
		
		private function onVideoEvent(e:Event):void {
			ExternalInterface.call("vcastrEvent", e.type, _controller.state, _controller.playHeadTime, _controller.bytesLoadedPersent);
		}
		
		private function playPause():void {
			_controller.playPause();
		}
		
		private function playerPlay():void {
			_controller.play();
		}
		
		private function pause():void {
			_controller.pause();
		}
		
		private function playerStop():void {
			_controller.stop();
		}
		
		private function ff():void {
			_controller.ff();
		}
		
		private function rew():void {
			_controller.rew();
		}
		
		private function next():void {
			_controller.next();
		}
		
		private function prev():void {
			_controller.prev();
		}
		
		private function volumeTo(value:Number) {
			_controller.volumeTo(value);
		}
		
		private function playerSizeTo(w:Number, h:Number):void {
			_controller.playerSizeTo(w, h);
		}
		
		private function playerMoveTo(px:Number, py:Number):void {
			_controller.playerMoveTo(px, py);
		}
		
		private function seek(offset:Number):void {
			_controller.seek(offset);
		}
		
		private function gotoVideoAt(id:int):void {
			_controller.gotoVideoAt(id);
		}
		
		public function addCallBack():void {
			if (ExternalInterface.available) {
                try {
					ExternalInterface.addCallback("videoPlay", playerPlay);
					ExternalInterface.addCallback("videoStop", playerStop);
					ExternalInterface.addCallback("playPause", playPause);
					ExternalInterface.addCallback("pause", pause);
					ExternalInterface.addCallback("ff", ff);
					ExternalInterface.addCallback("rew", rew);
					ExternalInterface.addCallback("next", next);
					ExternalInterface.addCallback("prev", prev);
					ExternalInterface.addCallback("volumeTo", volumeTo);
					ExternalInterface.addCallback("playerSizeTo", playerSizeTo);
					ExternalInterface.addCallback("playerMoveTo", playerMoveTo);
					ExternalInterface.addCallback("seek", seek);
					ExternalInterface.addCallback("gotoVideoAt", gotoVideoAt);
				}catch (e:Error) {
					
				}
			}
		}
	}	
}