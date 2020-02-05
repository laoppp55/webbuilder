package com.ruochi.video {
	import com.ruochi.string.replaceHat;
	import com.ruochi.string.replaceBraceToAngleBracket;
	import com.ruochi.component.SimpleAlert;
	import com.ruochi.video.VcastrConfig;
	import com.ruochi.video.plugIn.IVcastrPlugIn;
	import com.ruochi.video.VideoPlayer;
	import com.ruochi.video.Vcastr3;
	import com.ruochi.utils.xmlToVar;
	import com.ruochi.utils.about;
	import flash.display.DisplayObject;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.display.Loader;
	import flash.display.LoaderInfo;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.EventDispatcher;
	import flash.net.navigateToURL;
	public class Controller extends EventDispatcher implements IController {
		private var _unLoadPlugInsNum:int;
		private var _activeVideoId:int = 0;
		private static var _instance:Controller = new Controller();
				
		private function configUrlLoaderComplete(e:Event):void {
			VcastrConfig.dataXml = new XML((e.target as URLLoader).data);
			loadPlugIn()
		}
		
		private function loadPlugIn():void {
			var length:int = VcastrConfig.dataXml.plugIns.*.length();
			_unLoadPlugInsNum = length;
			for (var i:int = 0; i < length; i++) { 
				var plugInLoader:Loader = new Loader();
				plugInLoader.name = String(i);
				plugInLoader.contentLoaderInfo.addEventListener(Event.COMPLETE, onPlugInLoaderComplete, false, 0, true);
				plugInLoader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, onPlugInLoaderIOError, false, 0, true);
				plugInLoader.load(new URLRequest(VcastrConfig.dataXml.plugIns.*[i].url[0]));
			}
			startUp();
		}
		
		private function onPlugInLoaderIOError(e:IOErrorEvent):void {
			SimpleAlert.text = (e.target as LoaderInfo).loaderURL + " not found";
		}
		
		private function onPlugInLoaderComplete(e:Event):void {
			Vcastr3.instance.addChild(e.target.loader.content);
			var plugIn:IVcastrPlugIn = (e.target as LoaderInfo).loader.content as IVcastrPlugIn
			plugIn.dataXml = VcastrConfig.dataXml.plugIns.*[int(e.target.loader.name)];
			plugIn.init(this);
			_unLoadPlugInsNum--;
			startUp();
		}
		
		private function startUp():void {
			if (_unLoadPlugInsNum == 0 && VcastrConfig.dataXml) {
				if (VcastrConfig.dataXml.channel.item.length() > 1) {
					VcastrConfig.isMulitVideo = true;
				}
				xmlToVar(VcastrConfig.dataXml.config[0], VcastrConfig);
				Vcastr3.instance.addChild(CenterBtn.instance);
				Vcastr3.instance.setChildIndex(CenterBtn.instance,1);
				configListener();
				if (VcastrConfig.controlPanelMode!=VcastrConfig.NONE) {
					Vcastr3.instance.addChild(DefaultControlPanel.instance);
					//Vcastr3.instance.setChildIndex(CenterBtn.instance,2);
				}
				if (VcastrConfig.isShowAbout) {
					about(Vcastr3.instance, "About Vcastr 3.0", "http://code.google.com/p/vcastr/");
				}
				VideoPlayer.instance.scaleMode = VcastrConfig.scaleMode;
				VideoPlayer.instance.isLoadBegin = VcastrConfig.isLoadBegin;
				VideoPlayer.instance.bufferTime = VcastrConfig.bufferTime;
				volumeTo(VcastrConfig.defautVolume);
				//VideoPlayer.instance.defaultVoluem = VcastrConfig.defautVolume;
				VideoPlayer.instance.isAutoPlay = VcastrConfig.isAutoPlay;
				gotoVideoAt(_activeVideoId);
				dispatchEvent(new VideoEvent(VideoEvent.INIT, false, false, VideoPlayer.instance.state,  VideoPlayer.instance.playheadTime));
				setLayout()
			}						
		}
		
		private function configListener():void {
			VideoPlayer.instance.addEventListener(VideoEvent.STATE_CHANGE, onVideoPlayerStateChange, false, 0, true);
			VideoPlayer.instance.addEventListener(VideoEvent.COMPLETE, onVideoPlayerComplete, false, 0, true);
			VideoPlayer.instance.addEventListener(VideoEvent.READY, onVideoPlayerReady, false, 0, true);
			VideoPlayer.instance.addEventListener(VideoEvent.PLAYHEAD_UPDATE, onVideoPlayerPlayHeadUpdate, false, 0, true);
			VideoPlayer.instance.addEventListener(VideoEvent.PROGRESS, onVideoPlayerProgress, false, 0, true);
			VideoPlayer.instance.addEventListener(VideoEvent.START_BUFFERING, onVideoPlayerStartBuffering, false, 0 , true);
			VideoPlayer.instance.addEventListener(VideoEvent.STOP_BUFFERING, onVideoPlayerStopBuffering, false, 0, true);
			VideoPlayer.instance.addEventListener(VideoEvent.STOP, onVideoPlayerStop, false, 0, true);
			VideoPlayer.instance.addEventListener(VideoEvent.LOADING, onVideoPlayerLoading, false, 0, true);
			VideoPlayer.instance.addEventListener(VideoEvent.MINI_PLAYHEAD_UPDATE, onMiniVideoPlayerPlayHeadUpdate, false, 0, true);
		}
		
		private function onMiniVideoPlayerPlayHeadUpdate(e:VideoEvent):void {			
			DefaultControlPanel.instance.setPlayHeadState(VideoPlayer.instance.playheadTime, VideoPlayer.instance.totalTime);
		}
		
		private function onVideoPlayerPlaying(e:VideoEvent):void {
			DefaultControlPanel.instance.setToPlayingState();
			dispatchEvent(e);
		}
		
		private function onVideoPlayerPaused(e:VideoEvent):void {			
			DefaultControlPanel.instance.setToPausedState();
			dispatchEvent(e);
		}
		
		
		private function onVideoPlayerLoading(e:VideoEvent):void {
			dispatchEvent(e);
		}
		
		private function onVideoPlayerStop(e:VideoEvent):void {
			DefaultControlPanel.instance.setToStopState();
			dispatchEvent(e);
		}
		
		private function onVideoPlayerStopBuffering(e:VideoEvent):void {
			CenterBtn.instance.stopBuffering();
			dispatchEvent(e);
		}
		
		private function onVideoPlayerStartBuffering(e:VideoEvent):void {
			CenterBtn.instance.startBuffering();
			dispatchEvent(e);
		}
		
		private function onVideoPlayerProgress(e:VideoEvent):void {
			if(VideoPlayer.instance.bytesLoaded>0){
				DefaultControlPanel.instance.setProgressState(VideoPlayer.instance.bytesLoaded, VideoPlayer.instance.bytesTotal);
				dispatchEvent(e);
			}
		}
		
		private function onVideoPlayerPlayHeadUpdate(e:VideoEvent):void {
			dispatchEvent(e);
		}
		
		private function onVideoPlayerStateChange(e:VideoEvent):void {
			CenterBtn.instance.onVideoPlayerStateChange(e);
			if (VideoPlayer.instance.state == VideoEvent.PLAYING) {
				DefaultControlPanel.instance.setToPlayingState();
			}else {
				DefaultControlPanel.instance.setToPausedState();
			}
			dispatchEvent(new VideoEvent(e.state, false, false, e.state, e.playheadTime));
		}
		
		private function onVideoPlayerComplete(e:VideoEvent):void {
			if (_activeVideoId == VcastrConfig.dataXml.channel.item.source.length() - 1) {
				if (VcastrConfig.isRepeat) {
					VideoPlayer.instance.isAutoPlay = true;
					gotoVideoAt(0);
				}
			}else {
				if(VcastrConfig.isMulitVideo){
					next();
				}else {
					VideoPlayer.instance.gotoBegin();
				}
				dispatchEvent(e);
			}
			
		}
		
		private function onVideoPlayerReady(e:VideoEvent):void {
			dispatchEvent(e);
		}		

		public function loadConfig(str:Object):void {
			if (str) {
				VcastrConfig.xml = replaceBraceToAngleBracket(String(str));
				VcastrConfig.xml = replaceHat(String(VcastrConfig.xml));
			}
			if (VcastrConfig.xml.indexOf("<") > -1) {
				VcastrConfig.dataXml = new XML(VcastrConfig.xml); 
				if (VcastrConfig.dataXml.channel.item.source.length()>0) {
					loadPlugIn();
				}else {
					SimpleAlert.text = "no movie source available"
				}
			}else {
				var configUrlLoader:URLLoader = new URLLoader();
				configUrlLoader.addEventListener(Event.COMPLETE, configUrlLoaderComplete, false, 0, true);
				configUrlLoader.load(new URLRequest(VcastrConfig.xml))
			}
		}		
		
		public function setLayout():void {
			var videoWidth:int;
			var videoHeight:int;
			if (VcastrConfig.videoWidth > 0 && VcastrConfig.videoHeight > 0) {
				videoWidth = VcastrConfig.videoWidth;
				videoHeight = VcastrConfig.videoHeight;
			}else {
				videoWidth = Vcastr3.instance.stage.stageWidth;
				videoHeight = Vcastr3.instance.stage.stageHeight;
			}
			playerSizeTo(videoWidth, videoHeight);
			playerMoveTo(VcastrConfig.videoX, VcastrConfig.videoY);
			CenterBtn.instance.setLayout(videoWidth, videoHeight);			
			if (VcastrConfig.controlPanelMode != VcastrConfig.BOTTOM) {
				if (videoWidth > 630) {
					DefaultControlPanel.instance.panelWidth = 600;
					DefaultControlPanel.instance.x = Math.round((videoWidth - 600) / 2);
				}else {
					DefaultControlPanel.instance.panelWidth = videoWidth - 30;
					DefaultControlPanel.instance.x = 15;
				}				
				DefaultControlPanel.instance.y = videoHeight - 30;
			}else {				
				playerSizeTo(videoWidth, videoHeight - DefaultControlPanel.instance.panelHeight);
				DefaultControlPanel.instance.panelWidth = videoWidth;
				DefaultControlPanel.instance.y =  Math.round(videoHeight - DefaultControlPanel.instance.panelHeight);
			}			
		}
		
		public function videoLink():void {
			if (VideoPlayer.instance.dataXml.link[0] != undefined) {
				navigateToURL(new URLRequest(VideoPlayer.instance.dataXml.link[0]),"_blank");
			}
		}
		
		public function playPause():void {
			VideoPlayer.instance.playPause();
		}
		
		public function play():void {
			VideoPlayer.instance.play();
		}
		
		public function pause():void {
			VideoPlayer.instance.pause();
		}
		
		public function stop():void {
			VideoPlayer.instance.stop();
		}
		
		public function ff():void {
			VideoPlayer.instance.ff();
		}
		
		public function rew():void {
			VideoPlayer.instance.rew();
		}
		
		public function gotoVideoAt(id:int):void {
			_activeVideoId = id;
			VideoPlayer.instance.dataXml = VcastrConfig.dataXml.channel.item[_activeVideoId];
			TextItemList.instance.activeId = _activeVideoId;
			if (VcastrConfig.isMulitVideo) {
				if (_activeVideoId == 0) {
					DefaultControlPanel.instance.prevBtn.enable = false;
				}else {
					DefaultControlPanel.instance.prevBtn.enable = true;
				}
				if (_activeVideoId == VcastrConfig.dataXml.channel.item.source.length() - 1) {
					DefaultControlPanel.instance.nextBtn.enable = false;
				}else {
					DefaultControlPanel.instance.nextBtn.enable = true;
				}
			}
		}
		
		public function openCloseList():void {
			if (TextItemList.instance.isOpen) {
				TextItemList.instance.close();
			} else {
				TextItemList.instance.open();
			}
		}
		
		public function soundOnOff():void {
			if (VideoPlayer.instance.volume > 0.05) {
				VideoPlayer.instance.volume = 0;
				DefaultControlPanel.instance.volumnBtn.frame = 2;
			}else {
				VideoPlayer.instance.volume = .8;				
				DefaultControlPanel.instance.volumnBtn.frame = 1;
			}
			DefaultControlPanel.instance.volumnSlider.value = VideoPlayer.instance.volume;
		}
		
		public function volumeTo(value:Number):void {			
			if (value < 0.05) {
				value = 0;
				DefaultControlPanel.instance.volumnBtn.frame = 2;
			} else {
				DefaultControlPanel.instance.volumnBtn.frame = 1;
			}			
			VideoPlayer.instance.volume = value;
			DefaultControlPanel.instance.volumnSlider.value = value;
		}
		
		public function seek(offset:Number):void {
			VideoPlayer.instance.seek(offset);
		}
		
		public function seekPersent(per:Number):void {
			VideoPlayer.instance.seekPersent(per);
		}
		
		public function next():void {
			if (_activeVideoId < VcastrConfig.dataXml.channel.item.length() - 1) {
				VideoPlayer.instance.isAutoPlay = true;
				gotoVideoAt(_activeVideoId+1)
			}
		}
		
		public function prev():void {
			if (_activeVideoId > 0) {
				VideoPlayer.instance.isAutoPlay = true;
				gotoVideoAt(_activeVideoId-1)
			}
		}
		
		public function playerSizeTo(w:int,h:int):void {
			VideoPlayer.instance.setSize(w, h);
			CenterBtn.instance.setLayout(w, h);
			dispatchEvent(new VideoEvent(VideoEvent.LAYOUT_CHANGE, false, false, VideoPlayer.instance.state, VideoPlayer.instance.playheadTime));
		}
		
		public function playerMoveTo(px:int,py:int):void {
			VideoPlayer.instance.x = px;
			VideoPlayer.instance.y = py;			
			dispatchEvent(new VideoEvent(VideoEvent.LAYOUT_CHANGE, false, false, VideoPlayer.instance.state, VideoPlayer.instance.playheadTime));
		}
		
		public function get videoPlayer():DisplayObject {
			return VideoPlayer.instance as DisplayObject;
		}
		
		public function get dataXml():XML {
			return VcastrConfig.dataXml;
		}
		
		public function get state():String {
			return VideoPlayer.instance.state;
		}		
		
		public function get bytesLoadedPersent():Number {
			return VideoPlayer.instance.bytesLoaded / VideoPlayer.instance.bytesTotal;
		}
		
		
		public function get playHeadTime():int {
			return VideoPlayer.instance.playheadTime;
		}
		
		static public function get instance():Controller {
			return _instance;
		}
	}	
}