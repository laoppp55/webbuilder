package com.ruochi.video {
	import com.ruochi.shape.player.FFShape;
	import com.ruochi.shape.player.FullScreenOffShape;
	import com.ruochi.shape.player.FullScreenOnShape;
	import com.ruochi.shape.player.OpenListShape;
	import com.ruochi.shape.player.PauseShape;
	import com.ruochi.shape.player.PlayShape;
	import com.ruochi.shape.player.RewShape;
	import com.ruochi.shape.player.SoundOffShape;
	import com.ruochi.shape.player.SoundOnShape;
	import com.ruochi.shape.Rect;
	import com.ruochi.text.EmbedText;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.display.StageDisplayState;
	import flash.events.FullScreenEvent;
	import flash.text.AntiAliasType;
	import com.ruochi.video.VcastrConfig;
	import com.ruochi.utils.formatTime;
	import gs.TweenLite;
	import com.ruochi.video.VideoEvent;
	import com.ruochi.video.TextItemList;
	import com.ruochi.layout.Margin;
	import com.robertpenner.easing.Linear;
	import flash.utils.Timer;
	import flash.events.TimerEvent;
	public class DefaultControlPanel extends Sprite {
		private var _panelWidth:int = 300;
		private var _panelHeight:int = 17;
		private var _playPauseBtn:IconGlowBtn = new IconGlowBtn(_panelHeight,_panelHeight);
		private var _volumnBtn:IconGlowBtn = new IconGlowBtn(_panelHeight,_panelHeight);
		private var _fullScreenBtn:IconGlowBtn = new IconGlowBtn(_panelHeight, _panelHeight);
		private var _nextBtn:IconGlowBtn = new IconGlowBtn(_panelHeight, _panelHeight);
		private var _prevBtn:IconGlowBtn = new IconGlowBtn(_panelHeight, _panelHeight);
		private var _openListBtn:IconGlowBtn = new IconGlowBtn(_panelHeight, _panelHeight);
		private var _progressSlider:Slider = new Slider();
		private var _volumnSlider:Slider = new Slider(30);
		private var _currentTimeText:EmbedText = new EmbedText();
		private var _totalTimeText:EmbedText = new EmbedText();
		private var _bg:Rect = new Rect(_panelWidth, _panelHeight, VcastrConfig.controlPanelBgColor);
		private var _gap:int = 0;
		private var _defualtVoluem:Number = .8;
		private var _textItemList:TextItemList = TextItemList.instance;
		private var _margin:Margin = new Margin(0, 5, 0, 5);
		private var _timer:Timer = new Timer(3000, 1);
		private static var _instance:DefaultControlPanel = new DefaultControlPanel();
		public function DefaultControlPanel() {
			if (!_instance) {
				init();
			}else {
				throw new Error("singleton");
			}
		}
		private function init():void {
			setChildren();
			addChindren();
			setLayout();
			configListener();
		}
		private function setChildren():void {
			_playPauseBtn.icon = new PlayShape();
			_playPauseBtn.icon = new PauseShape();
			_volumnBtn.icon = new SoundOnShape();
			_volumnBtn.icon = new SoundOffShape();
			_fullScreenBtn.icon = new FullScreenOnShape();
			_fullScreenBtn.icon = new FullScreenOffShape();
			_nextBtn.icon = new FFShape();
			_prevBtn.icon = new RewShape();
			_openListBtn.icon = new OpenListShape();
			_textItemList.init(VcastrConfig.dataXml);
			setTextField(_currentTimeText);
			setTextField(_totalTimeText);
			_bg.alpha = VcastrConfig.controlPanelAlpha;
			_volumnSlider.sliderWidth = 30;
			_volumnSlider.isSnap = true;			
			_volumnSlider.value = _defualtVoluem;
		}
		private function setTextField(textField:EmbedText):void {
			textField.font = "SG16";
			textField.text = "00:00";
			textField.size = 10;
			textField.y = 2;
			textField.antiAliasType = AntiAliasType.ADVANCED;
			textField.sharpness = 400;
		}
		private function setLayout():void {
			var isCompact:Boolean = false;
			if (VcastrConfig.isMulitVideo) {
				if (_panelWidth < VcastrConfig.multiVideoCompactBoundary) {
					isCompact = true;
				}
			}else {				
				if (_panelWidth < VcastrConfig.singleVideoCompactBoundary) {
					isCompact = true;
				}
			}
			_bg.width = _panelWidth;
			_playPauseBtn.x = _gap + _margin.left;
			if (isCompact) {
				_prevBtn.visible = false;
				_nextBtn.visible = false;
				_currentTimeText.visible = false;
				_volumnSlider.visible = false;
				_totalTimeText.visible = false;
			}else {
				_prevBtn.visible = true;
				_nextBtn.visible = true;
				_volumnSlider.visible = true;
				_currentTimeText.visible = true;
				_totalTimeText.visible = true;
				_prevBtn.x = Math.round(_playPauseBtn.width + _playPauseBtn.x + _gap);
				_nextBtn.x = Math.round(_prevBtn.width + _prevBtn.x + _gap);
			}
			if (VcastrConfig.isMulitVideo) {
				_currentTimeText.x = Math.round(_nextBtn.width + _nextBtn.x + _gap);
			}else {
				_currentTimeText.x = Math.round(_playPauseBtn.width + _playPauseBtn.x + _gap);
			}
			if (isCompact) {
				_progressSlider.x = Math.round(_playPauseBtn.x + _playPauseBtn.width );
			}else {
				_progressSlider.x = Math.round(_currentTimeText.x + _currentTimeText.width );
			}
			_openListBtn.x = Math.round(_panelWidth - _openListBtn.width - _gap - Number(_margin.right));
			if (VcastrConfig.isMulitVideo) {
				_fullScreenBtn.x = Math.round(_openListBtn.x - _fullScreenBtn.width - _gap);
			}else{
				_fullScreenBtn.x = Math.round(_panelWidth - _fullScreenBtn.width - _gap -  Number(_margin.right));
			}
			_volumnSlider.x = Math.round(_fullScreenBtn.x - _volumnSlider.width - _gap);
			
			if (isCompact) {
				_volumnBtn.x =  Math.round(_fullScreenBtn.x - _volumnBtn.width);
			}else {				
				_volumnBtn.x =  Math.round(_volumnSlider.x - _volumnBtn.width);
			}
			_totalTimeText.x = Math.round(_volumnBtn.x - _totalTimeText.width - _gap);
			if(isCompact){
				_progressSlider.sliderWidth = Math.round(_volumnBtn.x - _playPauseBtn.x -_playPauseBtn.width);
			}else {
				_progressSlider.sliderWidth = Math.round(_totalTimeText.x - _currentTimeText.x -_currentTimeText.width);
			}
			_volumnSlider.y = 4;
			_progressSlider.y = 4;
			_textItemList.x = _panelWidth;
			_textItemList.y = -VcastrConfig.textItemHeight-1;
		}
		private function addChindren():void {
			addChild(_bg);
			addChild(_playPauseBtn);
			addChild(_volumnBtn);
			addChild(_fullScreenBtn);
			addChild(_progressSlider);
			addChild(_volumnSlider);
			addChild(_currentTimeText);
			addChild(_totalTimeText);
			if (VcastrConfig.isMulitVideo) {
				addChild(_nextBtn);
				addChild(_prevBtn);
				addChild(_openListBtn);
				addChild(_textItemList);
			}
		}
		public function configListener():void {			
			_playPauseBtn.addEventListener(MouseEvent.CLICK, onPlayPauseBtnClick, false, 0, true);
			_volumnBtn.addEventListener(MouseEvent.CLICK, onVolumnBtnClick, false, 0, true);
			_fullScreenBtn.addEventListener(MouseEvent.CLICK, onFullScreenBtnClick, false, 0, true);
			_progressSlider.addEventListener(Event.CHANGE, onProgressSliderChange, false, 0, true);
			_progressSlider.addEventListener(MouseEvent.MOUSE_DOWN, onProgressSliderMouseDown, false, 0, true);
			_volumnSlider.addEventListener(Event.CHANGE, onVolumnSliderChange, false, 0, true);
			if (stage) {
				configStageListener();
			}else {
				addEventListener(Event.ADDED_TO_STAGE, onAddedToStage, false, 0, true);
			}
			if (VcastrConfig.isMulitVideo) {
				_nextBtn.addEventListener(MouseEvent.CLICK, onNextBtnClick, false, 0, true);
				_prevBtn.addEventListener(MouseEvent.CLICK, onPrevBtnClick, false, 0, true);
				_openListBtn.addEventListener(MouseEvent.CLICK, onOpenListBtn, false, 0, true);				
			}
			
			
		}
		
		private function onPrevBtnClick(e:MouseEvent):void {			
			Controller.instance.prev();
		}
		
		private function onNextBtnClick(e:MouseEvent):void {
			Controller.instance.next();
		}
		
		private function configStageListener():void {
			stage.addEventListener(FullScreenEvent.FULL_SCREEN, onStageFullScreen, false, 0, true);
			if(VcastrConfig.controlPanelMode==VcastrConfig.FLOAT){
				stage.addEventListener(Event.MOUSE_LEAVE, onStageMouseLeave, false, 0, true);
				stage.addEventListener(MouseEvent.MOUSE_MOVE, onStageMouseOver, false, 0, true);
			}
		}
		
		private function onProgressSliderMouseDown(e:MouseEvent):void {
			TweenLite.killTweensOf(_progressSlider);
		}
		
		private function onOpenListBtn(e:MouseEvent):void {
			Controller.instance.openCloseList();
		}
		
		private function onStageMouseOver(e:MouseEvent):void {
			TweenLite.to(this, .5, { autoAlpha:1 } );
		}
		
		private function onStageMouseLeave(e:Event):void {
			TweenLite.to(this, .5, { autoAlpha:0 } );
		}
		private function onStageFullScreen(e:FullScreenEvent):void {
			if (stage.displayState == StageDisplayState.NORMAL) {
				_fullScreenBtn.frame = 2;
				_timer.removeEventListener(TimerEvent.TIMER, onTimer);
				stage.removeEventListener(MouseEvent.MOUSE_MOVE, onStageMouseMove);
				TweenLite.to(this, .5, { autoAlpha:1 } );
			}else {
				_fullScreenBtn.frame = 1;
				_timer.addEventListener(TimerEvent.TIMER, onTimer, false, 0, true);				
				stage.addEventListener(MouseEvent.MOUSE_MOVE, onStageMouseMove, false, 0, true);
				_timer.reset();
				_timer.start();
			}
		}
		
		private function onStageMouseMove(e:MouseEvent):void {			
			_timer.reset();
			_timer.start();
			TweenLite.to(this, .5, { autoAlpha:1 } );
		}
		
		private function onTimer(e:TimerEvent):void {
			TweenLite.to(this, .5, { autoAlpha:0 } );
		}
		
		private function onFullScreenBtnClick(e:MouseEvent):void {
			if (stage.displayState == StageDisplayState.NORMAL) {
				stage.displayState = StageDisplayState.FULL_SCREEN;
				_fullScreenBtn.frame = 2;
			}else {
				stage.displayState = StageDisplayState.NORMAL;
				_fullScreenBtn.frame = 1;
			}
		}
		private function onAddedToStage(e:Event):void {
			configStageListener();
		}
		private function onProgressSliderChange(e:Event):void {
			TweenLite.killDelayedCallsTo(Controller.instance.seekPersent);
			TweenLite.delayedCall(.3, Controller.instance.seekPersent, [_progressSlider.value]);
		}
		private function onVolumnSliderChange(e:Event):void {
			Controller.instance.volumeTo(_volumnSlider.value);
		}
		private function onVolumnBtnClick(e:MouseEvent):void {
			Controller.instance.soundOnOff();
		}
		private function onPlayPauseBtnClick(e:MouseEvent):void {
			Controller.instance.playPause();
		}
		public function set panelWidth(w:Number):void {
			_panelWidth = w;
			setLayout();
		}
		
		static public function get instance():DefaultControlPanel {
			return _instance;
		}
		
		public function setToPausedState():void {
			_playPauseBtn.frame = 1;
		}
		public function setToStopState():void {
			_playPauseBtn.frame = 1;
		}
		public function setToPlayingState():void {
			_playPauseBtn.frame = 2;
		}
		public function setPlayHeadState(playheadTime:Number,totalTime:Number):void {
			_currentTimeText.text = formatTime(Math.round(playheadTime));
			_totalTimeText.text = formatTime(Math.round(totalTime));
			_progressSlider.value = playheadTime / totalTime;
		}
		public function setProgressState(bytesLoaded:Number,bytesTotal:Number):void {
			_progressSlider.persent = bytesLoaded / bytesTotal;
			_progressSlider.couldDragPersent = bytesLoaded / bytesTotal;
		}
		public function get volumnBtn():IconGlowBtn {
			return _volumnBtn
		}
		public function get volumnSlider():Slider {
			return _volumnSlider
		}
		public function get panelHeight():int {
			return _panelHeight
		}
		
		public function get prevBtn():IconGlowBtn {
			return _prevBtn;
		}
		
		public function get nextBtn():IconGlowBtn {
			return _nextBtn;
		}
	}	
}