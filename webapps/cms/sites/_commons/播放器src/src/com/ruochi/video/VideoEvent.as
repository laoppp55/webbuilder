package com.ruochi.video {
	import flash.events.Event;
	public class VideoEvent extends Event {
		public static const START_BUFFERING:String = "startBuffering";
		public static const STOP_BUFFERING:String = "stopBuffering";
		public static const CLOSE:String = "close";
		public static const COMPLETE:String = "complete";
		public static const PAUSED:String = "paused";
		public static const PLAYING:String = "playing";
		public static const READY:String = "ready";
		public static const STOP:String = "stop";
		public static const STATE_CHANGE:String = "stateChange";
		public static const PROGRESS:String = "progress";
		public static const PLAYHEAD_UPDATE:String = "playheadUpdate";
		public static const LOADING:String = "loading";
		public static const INIT:String = "init";
		public static const LAYOUT_CHANGE:String = "layoutChange";
		public static const RECEIVE_METADATA:String = "receivedMetadata";
		public static const CUE_POINT:String = "cuePoint";
		public static const MINI_PLAYHEAD_UPDATE:String = "miniPlayheadUpdate";
		private var _state:String;
		private var _playheadTime:Number;
		public function VideoEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false, state:String=null, playheadTime:Number=NaN) {
			super(type, bubbles, cancelable);
			_state = state;
			_playheadTime = playheadTime;
		}
		public function get state():String {
			return _state;
		}
		public function set state(s:String):void {
			_state = s;
		}
		public function get playheadTime():Number {
			return _playheadTime;
		}
		public function set playheadTime(t:Number):void {
			_playheadTime = t;
		}
	} 
}
