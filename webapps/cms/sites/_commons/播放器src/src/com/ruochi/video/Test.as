package com.ruochi.video {
	import flash.display.Sprite;
	import com.ruochi.video.IconGlowBtn
	import com.ruochi.shape.player.*;
	public class Test extends Sprite{
		
		public function Test() {
			var _playPauseBtn:IconGlowBtn = new IconGlowBtn()
			_playPauseBtn.icon = new PlayShape();
			addChild(_playPauseBtn);
		}
		
	}
	
}