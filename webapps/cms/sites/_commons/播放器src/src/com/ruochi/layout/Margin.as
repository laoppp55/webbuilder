package com.ruochi.layout {
	public class Margin {
		public static const AUTO:String = "auto";
		private var _top:Object;
		private var _bottom:Object;
		private var _left:Object;
		private var _right:Object;
		public function Margin(t:Object = null, r:Object = null, b:Object = null, l:Object = null) {
			if (String(t).indexOf(" ") > 0) {
				var array:Array = String(t).split(" ");
				top = array[0];
				right = array[1];
				bottom = array[2];
				left = array[3];
			}else {
				top = t;
				right = r;
				bottom = b;
				left = l;
			}
		}
		public function set top(t:Object):void {
			_top = checkIsAuto(String(t)) ? AUTO : t; 
		}
		public function get top():Object {
			if (_top == AUTO) {
				return AUTO;
			}else {
				return Number(_top);
			}			
		}
		public function set bottom(b:Object):void {
			_bottom = checkIsAuto(String(b)) ? AUTO : b; 
		}
		public function get bottom():Object {
			if (_bottom == AUTO) {
				return AUTO;
			}else {
				return Number(_bottom);
			}	
		}
		public function set left(l:Object):void {
			_left = checkIsAuto(String(l)) ? AUTO : l; 
		}
		public function get left():Object {
			if (_left == AUTO) {
				return AUTO;
			}else {
				return Number(_left);
			}	
		}
		public function set right(r:Object):void {
			_right = checkIsAuto(String(r)) ? AUTO : r; 
		}
		public function get right():Object {
			if (_right == AUTO) {
				return AUTO;
			}else {
				return Number(_right);
			}	
		}
		private function checkIsAuto(str:String):Boolean {
			if (str == null || str.toLowerCase() == AUTO) {
				return true;
			}else {
				return false
			}
		}
	}	
}