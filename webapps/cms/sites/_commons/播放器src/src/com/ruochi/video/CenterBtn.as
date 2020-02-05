package com.ruochi.video {
	import com.ruochi.shape.player.PauseShape;
	import com.ruochi.shape.player.PlayShape;
	import com.ruochi.shape.Rect;
	import com.ruochi.layout.setCenter;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.display.StageDisplayState;
	import flash.events.TimerEvent;
	import com.ruochi.video.IconGlowBtn;
	import gs.TweenLite;
	import com.ruochi.video.VideoEvent;
	import com.ruochi.component.StripeProgressBar;
	import flash.utils.Timer;
	public class CenterBtn extends Sprite {
		private var _playPauseBtn:IconGlowBtn = new IconGlowBtn();
		private var _rect:Rect = new Rect(100, 100, 0);
		private var _bg:Sprite = new Sprite();
		private var _isBgDoubleClick:Boolean = false;
		private static var _instance:CenterBtn = new CenterBtn();
		private var _progressBar:StripeProgressBar = new StripeProgressBar(60, 10);
		private var _timer:Timer = new Timer(300, 1);
		public function CenterBtn() {
			if (!_instance) {
				init();
			}else {
				throw new Error("singleton");
			}
		}
		private function init():void {
			setChildren();
			addChildren();
			configListener();
		}
		
		private function addChildren():void{
					
			addChild(_bg)
			addChild(_progressBar);
			addChild(_playPauseBtn);
			_bg.addChild(_rect);
		}
		
		private function setChildren():void {
			buttonMode = true;
			_playPauseBtn.icon = new PlayShape();
			_playPauseBtn.icon = new PauseShape();
			_playPauseBtn.scaleX = _playPauseBtn.scaleY = 2;
			_bg.alpha = 0;
			_progressBar.stripeShape.alpha = .5;
			_bg.doubleClickEnabled = true;
		}
		public function setLayout(w:int,h:int):void {
			_bg.width = w;
			_bg.height = h;
			setCenter(_playPauseBtn, _bg);
			setCenter(_progressBar, _bg);
			_progressBar.y = _playPauseBtn.y + 40;
		}
		private function configListener():void {
			_bg.addEventListener(MouseEvent.CLICK, onBgClick, false, 0, true);
			_bg.addEventListener(MouseEvent.DOUBLE_CLICK, onBgDoubleClick, false, 0, true);
			_playPauseBtn.addEventListener(MouseEvent.CLICK, onPlayPauseBtnClick, false, 0, true);
			_timer.addEventListener(TimerEvent.TIMER, onTimer, false, 0, true);
		}
		
		private function onTimer(e:TimerEvent):void {
			if (!_isBgDoubleClick) {
				Controller.instance.playPause();
			}
			_isBgDoubleClick = false;
		}
		
		public function onVideoPlayerStateChange(e:VideoEvent):void {
			if (e.state == VideoEvent.PLAYING) {
				turnOff()
			}else {
				turnOn();
			}
		}
		
		public function stopBuffering():void {
			_progressBar.stop();
		}
		
		public function startBuffering():void {
			_progressBar.start();
		}
		private function onBgClick(e:MouseEvent):void {
			_timer.reset();
			_timer.start();
		}
		private function delayBgClick():void {
			
		}
		private function onBgDoubleClick(e:MouseEvent):void {			
			_isBgDoubleClick = true;
			if (stage.displayState == StageDisplayState.NORMAL) {
				stage.displayState = StageDisplayState.FULL_SCREEN;
			}else {
				stage.displayState = StageDisplayState.NORMAL;
			}
		}
		private function onPlayPauseBtnClick(e:MouseEvent):void {
			Controller.instance.playPause();
			Controller.instance.videoLink();
		}
		
		private function turnOn():void {
			_playPauseBtn.frame = 1;
			_playPauseBtn.visible = true;
		}
		private function turnOff():void {
			_progressBar.stop();
			_playPauseBtn.frame = 2;
			_playPauseBtn.visible = false;
		}
		public static function get instance():CenterBtn {
			return _instance;
		}
	}	
}