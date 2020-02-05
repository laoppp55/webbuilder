package com.ruochi.video {
	public class ScaleUtils {		
		import flash.display.DisplayObject;
		import flash.media.Video;
		public static const NO_SCALE:String = "noScale";
		public static const EXACT_FIT:String = "exactFit";
		public static const SHOW_ALL:String = "showAll";
		public static const NO_BORDER:String = "noBorder";
		public static function fillNoScale(video:Video, videoContainer:DisplayObject):void {
			video.width = video.videoWidth;
			video.height = video.videoHeight;
			video.x = Math.round((videoContainer.width -video.width) / 2);
			video.y = Math.round((videoContainer.height -video.height) / 2);
		}
		public static function fillExactFit(video:Video, videoContainer:DisplayObject):void {
			video.x = videoContainer.x;
			video.y = videoContainer.y;
			video.width = videoContainer.width;
			video.height = videoContainer.height;
		}
		public static function fillShowAll(video:Video, videoContainer:DisplayObject):void {
			var originW:Number = video.videoWidth;
			var originH:Number = video.videoHeight;
			if (originW/originH>videoContainer.width/videoContainer.height) {
				video.width = videoContainer.width;			
				video.height = Math.round(video.width / originW * originH);
			} else {
				video.height=videoContainer.height;
				video.width = Math.round(video.height / originH * originW);
			}
			video.x = Math.round((videoContainer.width -video.width) / 2);
			video.y = Math.round((videoContainer.height -video.height) / 2);
		}
		public static function fillNoBorder(video:Video, videoContainer:DisplayObject):void {
			var originW:Number = video.videoWidth;
			var originH:Number = video.height/video.scaleY;
			if (originW/originH>videoContainer.width/videoContainer.height) {
				video.height=videoContainer.height;
				video.width=video.height/originH*originW;
			} else {
				video.width=videoContainer.width;
				video.height=video.width/originW*originH;
			}
			video.x=(videoContainer.width-video.width)/2;
			video.y =(videoContainer.height-video.height)/2;
			video.x = Math.round((videoContainer.width -video.width) / 2);
			video.y = Math.round((videoContainer.height -video.height) / 2);
		}
	}	
}