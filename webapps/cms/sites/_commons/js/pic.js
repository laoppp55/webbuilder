						(function(){
							// class prototype
							var p = P('P.ui'), __pro, __ihead = window.ihead,__time = 2000;
                            
							if (typeof(biztime)!='undefined') {
							    if (biztime > 0) __time=biztime;
							}

							//alert(__time);

							/**
							 * 首页相片自动切换控件类
							 * @constructor
							 * @class   首页相片自动切换控件类
							 * @extends P(N.ui)._$$UIAbstract
							 */
							p._$$SlideShow = C();
							__pro = p._$$SlideShow.prototype;
							__pro._$initialize = function(){
								this.__index = 0;
								this.__intXnode();
							};
							/**
							 * 初始化控件节点
							 * @return {Void}
							 */
							__pro.__intXnode = function(){
								var _ntmp = E._$getElement('biz_slider_con').getElementsByTagName('ul');
								this.__sb = _ntmp[0];
								this.__st = _ntmp[1];
								this.__belms = this.__sb.getElementsByTagName('li');	
								this.__bimgs = this.__sb.getElementsByTagName('img');	
								this.__telms = this.__st.getElementsByTagName('li');	
								for (var i = 0; i < articleNum; i++) 
									V._$addEvent(this.__telms[i], 'mouseover', this.__onPlayStop._$bind(this, i));
								V._$addEvent(this.__st, 'mouseout', this.__onPlayStart._$bind(this, this.__index));
								this.__onPlayStart(0);
							};
							/**
							 * 停止播放
							 * @return {Void}
							 */
							__pro.__onPlayStop = function(_index){
								this.__tinv && clearInterval(this.__tinv);
								this.__onShow(_index);
								for (var i = 0; i < articleNum; i++) 
									this.__telms[i].className = i == _index ? 'on' : '';
								this.__index = _index;
							};
							/**
							 * 开始播放
							 * @param  {Number} 	_index 		当前项序号
							 * @return {Void}
							 */
							__pro.__onPlayStart = function(_index){
								this.__tinv && clearInterval(this.__tinv);
								var _index = _index || this.__index;
								this.__onShow(_index);
								this.__tinv = setInterval(function(){
									this.__onShow(this.__index);
									this.__index == articleNum - 1 ? this.__index = 0 : this.__index++;
								}._$bind(this), __time);
							};
							/**
							 * 显示特定项的相片大图
							 * @param {Number} _index	特定项
							 */
							__pro.__onShow = function(_index){
								if (!this.__bimgs[_index].src) 
									this.__bimgs[_index].src = __ihead[_index].src;
								for (var i = 0; i < articleNum; i++) {
									this.__belms[i].style.display = i == _index ? '' : 'none';
									this.__telms[i].className = i == _index ? 'on' : '';
								}
							};
							new P.ui._$$SlideShow();
						})();