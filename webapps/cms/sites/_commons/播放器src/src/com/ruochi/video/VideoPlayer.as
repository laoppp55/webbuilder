package com.ruochi.video {
	import com.ruochi.shape.Rect;
	import com.ruochi.video.ScaleUtils;
	import com.ruochi.video.VideoEvent;
	import flash.events.Event;
	import flash.media.Video;
	import flash.net.NetConnection;
	import flash.net.NetStream;
	import flash.events.NetStatusEvent;
	import flash.net.URLRequest;
	import flash.utils.Timer;
	import flash.events.TimerEvent;
	import flash.display.Sprite;
	import com.ruochi.component.SimpleAlert;
	import flash.media.SoundTransform;
	import com.ruochi.utils.deleteAll;
	public class VideoPlayer extends Sprite {
		private var _video:Video = new Video();
		private var _netStream:NetStream;
		private var _netConnetction:NetConnection = new NetConnection();
		private var _url:String;
		private var _state:String;
		private var _timer:Timer = new Timer(100, 0);
		private var _duration:Number;
		private var _bg:Rect = new Rect(100, 100, 0);
		private var _playerWidth:Number;
		private var _playerHeight:Number;
		private var _isVideoInit:Boolean = false;
		private var _metadata:Object;
		private var _soundTransform:SoundTransform = new SoundTransform();
		private var _isBuffering:Boolean = false;
		private var _client:Object = new Object;
		private var _dataXml:XML;
		private var _isAutoPlay:Boolean;
		private var _scaleMode:String;
		private var _isLoadBegin:Boolean = true;
		private static var _instance:VideoPlayer = new VideoPlayer();
		public function VideoPlayer(w:int = 320, h:int = 240) {
			if (!_instance) {
				_playerWidth = w;
				_playerHeight = h;
				init();
			}else {
				throw new Error("singleton");
			}
		}
		private function init():void {
			_video.smoothing = true;
			_video.visible = false;
            _netConnetction.connect(null);			
			_netStream = new NetStream(_netConnetction);
            _netStream.client = _client;
			_netStream.bufferTime = 4;
			_video.attachNetStream(_netStream);
			configListener();
			addChild(_bg);
			addChild(_video);
			_client.onMetaData = onMetaData;
			_client.onCuePoint = onCuePoint;
		}
		private function resetParam():void {
			_isVideoInit = false;
			_timer.stop();	
		}
		private function onMetaData(info:Object):void {			
			_duration = info.duration;
			dispatchEvent(new VideoEvent(VideoEvent.RECEIVE_METADATA, false, false, _state, playheadTime));
		}
		private function onCuePoint(info:Object):void {
			dispatchEvent(new VideoEvent(VideoEvent.CUE_POINT, false, false, _state, playheadTime));
		}
		private function configListener():void {
			_timer.addEventListener(TimerEvent.TIMER, onTimer, false, 0, true);
			_netStream.addEventListener(NetStatusEvent.NET_STATUS, onNetStreamStatus, false, 0, true);
		}
		private function onNetStreamStatus(e:NetStatusEvent):void {
			switch (e.info.code) { 
			case "NetConnection.Connect.Success":
				break;
			case "NetStream.Play.StreamNotFound":
				SimpleAlert.text = _url + "Moive not found"
				break;
			case "NetStream.Buffer.Flush":
				
			case "NetStream.Play.Start":
				break;			
			
			case "NetStream.Play.Stop":
				resetParam();
				_isAutoPlay = false;
                load();				
				dispatchEvent(new VideoEvent(VideoEvent.COMPLETE, false, false, _state, playheadTime));
				break;
			}
		}
		private function onTimer(e:TimerEvent):void {
			if(_timer.currentCount%10==0){
				if (!_isVideoInit && _video.videoWidth) {
					videoInit();
				}
				dispatchEvent(new VideoEvent(VideoEvent.PROGRESS, false, false, _state, playheadTime));
				dispatchEvent(new VideoEvent(VideoEvent.PLAYHEAD_UPDATE, false, false, _state, playheadTime));
				checkBuffer();
			}
			dispatchEvent(new VideoEvent(VideoEvent.MINI_PLAYHEAD_UPDATE, false, false, _state, playheadTime));
		}
		private function checkBuffer():void {
			if (_netStream.bufferLength < .5 && _isBuffering == false) {
				_isBuffering = true;
				dispatchEvent(new VideoEvent(VideoEvent.START_BUFFERING, false, false, _state, playheadTime));
			}else if(_netStream.bufferLength>_netStream.bufferTime-.5 && _isBuffering == true){
				_isBuffering = false;
				dispatchEvent(new VideoEvent(VideoEvent.STOP_BUFFERING, false, false, _state, playheadTime));
			}
		}
		private function videoInit():void {
			_video.visible = true;
			setSize(_playerWidth, _playerHeight);
			if (_isAutoPlay) {
				play()
			}else {
				pause();
			}			
			_isVideoInit = true;
		}
		private function setState(str:String):void {
			if (str != _state) {
				_state = str;
				dispatchEvent(new VideoEvent(VideoEvent.STATE_CHANGE, false, false, _state, playheadTime));				
			}
		}
		public function get state():String {
			return _state;
		}
		public function setSize(w:int, h:int):void {
			_playerWidth = w;
			_playerHeight = h;
			_bg.width = w;
			_bg.height = h;
			if (_scaleMode == ScaleUtils.NO_BORDER) {
				ScaleUtils.fillNoBorder(_video, _bg);
			}else if (_scaleMode == ScaleUtils.NO_SCALE) {
				ScaleUtils.fillNoScale(_video, _bg);
			}else if (_scaleMode == ScaleUtils.EXACT_FIT) {
				ScaleUtils.fillExactFit(_video, _bg);
			}else {				
				ScaleUtils.fillShowAll(_video, _bg); 
			}
		}
		public function play():void {
			if (_timer.running) {
				_netStream.resume();
				setState(VideoEvent.PLAYING);
			}else {
				load();
			}
		}
		public function load():void {
			_isLoadBegin = true;
			_netStream.play(_url)
			_timer.start();
			dispatchEvent(new VideoEvent(VideoEvent.LOADING, false, false, _state, playheadTime));	
		}
		public function pause():void {
			_netStream.pause();
			setState(VideoEvent.PAUSED);
		}
		public function stop():void {
			pause();
			seek(0);
			setState(VideoEvent.STOP);
		}
		public function close():void {
			_netStream.close();
		}
		public function playPause():void {
			if (_state != VideoEvent.PLAYING) {
				play();
			}else {
				pause();
			}
		}
		public function ff():void {
			seek(playheadTime + totalTime / 10);
		}
		public function rew():void {
			seek(playheadTime - totalTime / 10);
		}
		public function seekPersent(persent:Number):void {
			seek(totalTime * persent);
		}
		public function gotoBegin():void {
			pause();
			seek(0);
		}
		public function seek(offset:Number):void {
			_netStream.seek(offset);
		}
		public function get playheadTime():Number {
			return _netStream.time;
		}
		public function get totalTime():Number {
			return duration;
		}
		public function get bytesLoaded():Number {
			return _netStream.bytesLoaded;
		}
		public function get bytesTotal():Number {
			return _netStream.bytesTotal;
		}		
		public function set volume(value:Number):void {
			_soundTransform.volume = value;
			_netStream.soundTransform = _soundTransform;
		}
		public function get volume():Number {
			return _soundTransform.volume;
		}
		public function set duration(num:Number):void {
			_duration = num;
		}
		public function get duration():Number {
			if (_duration) {
				return _duration;
			}else {
				return bytesTotal/_video.videoWidth/_video.videoHeight * 1.8
			}
		}
		public function get playerWidth():int {
			return _playerWidth;
		}	
		public function get playerHeight():int {
			return _playerHeight;
		}
		public function set url(u:String):void {
			_url = u;
			if (_isLoadBegin) {
				load();
			}
		}
		public function set dataXml(xml:XML):void {
			_dataXml = xml;
			url = _dataXml.source[0];
			if (_dataXml.duration[0] != undefined) {
				_duration = _dataXml.duration[0]
			}
		}
		public function get dataXml():XML {
			return _dataXml;
		}
		public function set isAutoPlay(b:Boolean):void {
			_isAutoPlay = b;
		}
		public function set defaultVoluem(v:Number):void {
			_soundTransform.volume = v; trace(v,'cc')
		}
		public function set bufferTime(v:Number):void {
			_netStream.bufferTime = v;
		}
		public function set scaleMode(s:String):void {
			_scaleMode = s;
		}
		public function set isLoadBegin(b:Boolean):void {
			_isLoadBegin = b;
		}
		static public function get instance():VideoPlayer {
			return _instance;
		}
	}	
}