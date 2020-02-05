(function(){
var p=P('np.c'),
__ud=UD;
p._$UD=__ud;
p._$ISLOGIN=!!__ud.isLogin;
p._$ISEDIT=!!__ud.editable;
p._$GROUP=U._$getGValue('GROUP');
})();
(function(){
J._$registXDomain('blog.163.com');
})();
P('EJ');
(function(){
var __pool={},
__timeout=60000;
var __clear=function(_sn){
var _req=__pool[_sn];
if(!_req)return;
delete __pool[_sn];
var _rpc=_req.rpc;
delete _req.rpc;
_req.timer=window.clearTimeout(_req.timer);
V._$clearEvent(_rpc);
E._$removeElement(_rpc);
};
var __onLoad=function(_sn){
if(!__pool[_sn])return;
var _load=__pool[_sn].onload;
__clear(_sn);_load();
};
var __onError=function(_sn,_message){
var _error=__pool[_sn].onerror;
__clear(_sn);_error(_message||'脚本加载出错！');
};
EJ._$loadScript=function(_url,_onload,_onerror,_charset){
var _sn=_url;
if(!__pool[_sn]){
var _script=document.cloneElement('script');
if(!!_charset)
_script.charset=_charset;
__pool[_sn]={
rpc:_script,
onload:_onload,
onerror:_onerror?_onerror:_onload,
timer:window.setTimeout(__onError._$bind(window,_sn,'请求超时！'),__timeout)
};
V._$addEvent(_script,'load',__onLoad._$bind(window,_sn));
V._$addEvent(_script,'error',__onError._$bind(window,_sn,'无法加载指定的脚本文件！'));
_script.src=_url;
document.head.appendChild(_script);
}
return __pool[_sn].rpc;
};
})();
(function(){
var c=P('U.cls');
c._$augment=function(_des,_src,_flag){
if(!c._$isClass(_des)||!c._$isClass(_src))
return;
var _key,_p,_pro=_src.prototype;
for(_key in _pro){
_p=_pro[_key];
if(U.fun._$isFunction(_p))
_des.prototype[_key]=_p;
}
_flag&&U.obj._$extend(_des,_src,U.fun._$isFunction);
};
c._$isClass=function(_obj){
return U.fun._$isFunction(_obj);
};
})();
(function(){
var o=P('U.obj');
o.__pool={};
o._$isObject=function(_obj){
return(_obj&&(typeof _obj==='object'||U.fun._$isFunction(_obj)))||false;
};
o._$extend=function(_des,_src,_factor){
_des=_des||O;
if(_src)
for(var _p in _src)
if(!_factor||_factor&&_factor(_src[_p]))
_des[_p]=_src[_p];
return _des;
};
o._$clone=function(_obj,_flag){
var _f=function(){};
_f.prototype=_obj;
return new _f();
};
o._$toArray=function(_obj){
if(U.arr._$isArray(_obj))
return _obj;
var _arr=[];
if(o._$isObject(_obj)){
var _p,_v;
for(_p in _obj){
_v=_obj[_p];
!U.fun._$isFunction(_v)&&_arr.push(_v);
}
}
return _arr;
};
o._$toArray2=function(_obj){
if(U.arr._$isArray(_obj)){
return _obj;
}
if(o._$isObject(_obj)){
var _array=[];
for(var i=0,_size=_obj.length;i<_size;i++){
_array.push(_obj[i]);
}
return _array;
}
};
o._$delete=function(_obj,_pro){
if(!_obj||!_pro)
return;
try{
_obj[_pro]=undefined;
delete _obj[_pro];
}
catch(e){
_obj[_pro]=undefined;
}
};
o._$toHash=function(_obj){
var _str=U._$serialize(_obj);
_str=_str.replace(/:/g,'=').replace(/,/g,'&').replace(/\'/g,'');
if(_str)
_str=_str.replace(/^{/,'#').replace(/}$/,'');
return _str;
};
o._$isUndefined=function(_obj){
return typeof _obj==='undefined';
};
o._$setData=function(_key,_data){
this.__pool[_key]=_data;
};
o._$getData=function(_key){
return this.__pool[_key];
};
})();
(function(){
var a=P('U.arr');
a._$isArray=function(_obj){
return Object.prototype.toString.call(_obj)==='[object Array]';
};
a._$forEach=function(_arr,_fn){
if(!a._$isArray(_arr)||!U.fun._$isFunction(_fn))
return;
if(_arr.forEach)
_arr.forEach(_fn);
else
for(var i=0,_tmp;_tmp=_arr[i];i++)
_fn(_tmp,i);
};
a._$filter=function(_arr,_factor){
var _res=[];
if(!a._$isArray(_arr)||!U.fun._$isFunction(_factor))
return _res;
if(_arr.filter)
_res=_arr.filter(_factor);
else
for(var i=0,_tmp;_tmp=_arr[i];i++)
_factor(_tmp)&&_res.push(_tmp);
return _res;
};
a._$indexOf=function(_arr,_o,_flag){
if(U.arr._$isArray(_arr)){
if(_arr.indexOf&&(_flag||!U.fun._$isFunction(_o)))
return _arr.indexOf(_o);
else{
for(var i=0,_l=_arr.length,_a;i<_l;i++){
_a=_arr[i];
if(!_flag&&U.fun._$isFunction(_o)&&_o(_a))
return i;
else
if(_a==_o)
return i;
}
return-1;
}
}
};
a._$toObject=function(_arr,_attr,_cb){
var _obj={};
if(a._$isArray(_arr)&&_attr){
for(var i=0,_l=_arr.length,_tmp;i<_l;i++){
_tmp=_arr[i];
if(a._$isArray(_tmp))
_obj=U.obj._$extend(_obj,a._$toObject(_tmp,_attr));
else
if(U.obj._$isObject(_tmp)){
_cb&&_cb(_tmp,i);
_obj[_tmp[_attr]]=_tmp;
}
}
}
return _obj;
};
a._$every=function(_arr,_fun){
if(a._$isArray(_arr)&&U.fun._$isFunction(_fun)){
for(var i=0,_l=_arr.length;i<_l;i++){
if(!_fun(_arr[i]))
return false;
}
}
return true;
};
a._$erase=function(_arr,_item){
for(var i=_arr.length;i--;_arr[i]===_item&&_arr.splice(i,1))
;
return _arr;
};
a._$unique=function(_source,_fun){
var _len=_source.length,_result=_source.slice(0),i,_tmp;
if(!U.fun._$isFunction(_fun)){
_fun=function(itm0,itm1){
return itm0===itm1;
};
}
while(--_len>0){
_tmp=_result[_len];
i=_len;
while(i--){
if(_fun(_tmp,_result[i])){
_result.splice(_len,1);
break;
}
}
}
return _result;
};
})();
(function(){
var f=P('U.fun');
f._$isFunction=function(_obj){
return Object.prototype.toString.call(_obj)==='[object Function]';
};
f._$getPassportUserImage=function(_name,_type){
return'http://os.blog.163.com/common/ava.s?passport='+_name+'&b='+(_type||0);
};
})();
(function(){
var s=P('U.str');
s._$isString=function(_obj){
return typeof _obj==='string';
};
s._$isUrl=function(_str){
if(!s._$isString(_str))
return false;
return U.reg._$getRegex('url').test(_str);
};
s._$getLength=function(_str){
_str=_str||'';
for(var i=0,_len=0,_l=_str.length;i<_l;i++)
_len+=_str.charCodeAt(i)>255?2:1;
return _len;
};
s._$truncate=function(_str,_length,_flag){
_str=_str||'';
if(_length==undefined)
return _str;
for(var i=0,_len=0,_l=_str.length;i<_l;i++){
_len+=_str.charCodeAt(i)>255?2:1;
if(_len>_length)
break;
}
var _ret=_str.slice(0,i);
return i<_str.length&&!_flag?_ret+'...':_ret;
};
s._$trim=function(_str){
_str=_str||'';
if(_str.trim)
return _str.trim();
return _str.replace(U.reg._$getRegex('REG_TRIM_SPACE'),'');
};
s._$trimsc=function(_str){
return this._$trim(_str).replace(U.reg._$getRegex('REG_TRIM_SEMICOLON'),'');
};
s._$toHash=function(_hashStr){
if(!s._$isString(_hashStr))
return null;
_hashStr=s._$trim(_hashStr).replace(/^[?#]?/,'').replace(/=/g,':').replace(/&/g,',');
return U._$deserialize('{'+_hashStr+'}');
};
s._$include=function(_str,_pattern){
return _str.indexOf(_pattern)>-1;
};
s._$camelize=function(_str){
return _str.replace(/-([a-z])/ig,function(_all,_letter){return _letter.toUpperCase();});
};
s._$fixString=function(_elm,_str,_maxwidth,_suffix,_lines){
_elm=E._$getElement(_elm);
if(!_elm||!U._$trim(_str))
return;
var k=s._$calString(_elm,_str,_maxwidth,_suffix);
if(k!==-1)
_elm.innerText=_str.substring(0,k||0)+_suffix;
else
_elm.innerText=_str;
};
s._$calString=function(_elm,_str,_maxwidth,_suffix){
var _tmpArray=new Array(257);
_elm.innerText='中中中中中中中中中中';
_tmpArray[256]=_elm.offsetWidth/10;
for(var i=32;i<256;++i){
_elm.innerText='中'+String.fromCharCode(i)+'中';
_tmpArray[i]=_elm.offsetWidth-2*_tmpArray[256];
}
var k,_width=0,_suffixWidth;
_elm.innerText=_suffix||'';
_suffixWidth=_elm.offsetWidth;
for(var i=0,l=_str.length;i<l;++i){
var _code=_str.charCodeAt(i),
_charWidth=(_code>255?_tmpArray[256]:_tmpArray[_code]||0);
if(k===undefined&&
_charWidth+_width+_suffixWidth>_maxwidth)
k=i;
_width+=_charWidth;
if(_width>_maxwidth)
break;
}
return _width>_maxwidth?k||0:-1;
};
})();
(function(){
var d=P('U.dom');
d._$getElement=function(_obj){
return(_obj&&_obj._$getBody&&_obj._$getBody())||E._$getElement(_obj);
};
d._$emptyElement=function(_elm){
_elm=E._$getElement(_elm);
var _nd;
while(_nd=_elm&&_elm.firstChild)
E._$removeElement(_nd);
};
d._$getValueOfRadio=function(_rds){
_rds=_rds.length?_rds:[_rds];
for(var i=0,_rd;_rd=_rds[i];i++)
if(_rd&&_rd.checked)
return _rd.value;
};
d._$getValueOfText=function(_txt,_hint){
_hint=_hint||'';
var _v=_txt&&_txt.value;
_v=_v||'';
return U._$trim(_v)==_hint?'':_v;
};
d._$isText=function(_elm){
return _elm&&(_elm.tagName.toLowerCase()=='textarea'||(_elm.type&&_elm.type.toLowerCase()=='text'||_elm.type.toLowerCase()=='password'));
};
d._$addTextHint=function(_txt,_hint){
var _arg0=_txt;
if(U.arr._$isArray(_arg0)){
for(var i=0,_a;_a=_arg0[i];i++)
d._$addTextHint(_a,_hint);
}
else
if(d._$isText(_txt)&&_hint){
_txt.value=_hint;
V._$addEvent(_txt,'focus',d.__onFocusText._$bind(d,_txt,_hint));
V._$addEvent(_txt,'blur',d.__onBlurText._$bind(d,_txt,_hint));
}
};
d.__onFocusText=function(_txt,_hint){
if(!_txt||!_hint)
return;
if(_txt.value==_hint)
_txt.value='';
};
d.__onBlurText=function(_txt,_hint){
if(!_txt||!_hint)
return;
if(_txt.value=='')
_txt.value=_hint;
};
d._$addTextChange=function(_txt,_handler){
if(d._$isText(_txt)&&U.fun._$isFunction(_handler)){
V._$addEvent(_txt,B._$ISIE?'propertychange':'input',_handler);
if(B._$ISFF){
V._$addEvent(_txt,'paste',_handler);
V._$addEvent(_txt,'cut',_handler);
}
}
};
d._$textFocus=function(_txt){
if(d._$isText(_txt)){
_txt.focus();
if(B._$ISIE){
var _range=_txt.createTextRange();
_range.collapse(false);
_range.select();
}
}
};
d._$isAncestor=function(_p,_c){
var _p=E._$getElement(_p),_c=E._$getElement(_c),_result=false;
if((_p&&_c)&&(_p.nodeType&&_c.nodeType))
_result=_p.contains&&_p!==_c?_p.contains(_c):!!(_p.compareDocumentPosition(_c)&16);
return _result;
};
d._$getRelatedTarget=function(_event){
var _t=_event.relatedTarget;
if(!_t){
if(_event.type=="mouseout")
_t=_event.toElement;
else
if(_event.type=="mouseover")
_t=_event.fromElement;
}
return d.__resolveTextNode(_t);
};
d.__resolveTextNode=function(_elm){
try{
if(_elm&&3==_elm.nodeType)
return _elm.parentNode;
}
catch(e){}
return _elm;
};
d._$scrollTo=function(_elm){
var _body=document.documentElement||document.body,
_filter=_elm==_body;
scrollTo(E._$offsetX(_elm,_filter)||0,E._$offsetY(_elm,_filter)||0);
};
d._$animateScrollTo=function(_elm){
var _filter=true;
_x=E._$offsetX(_elm||_filter)||0,
_y=E._$offsetY(_elm||_filter)||0,
_top=U.dom._$scrollTop();
this.__sitv=setInterval(function(){
if(_top>=_y){
_top-=20;
scrollTo(0,_top);
}else
this.__sitv&&clearInterval(this.__sitv);
}._$bind(this),10);
};
d._$initAnchor=function(_elm,_opt){
_elm=E._$getElement(_elm),_opt=_opt||O;
if(_elm){
var _tagName=_elm.tagName.toLowerCase();
if(_elm.getAttribute('needlogin')=='true')
V._$addEvent(_elm,'click',d.__onClickNeedLogin._$bind(d,_opt));
else{
var _as=_elm.getElementsByTagName('a');
for(var i=0,_a;_a=_as[i];i++)
if(_a.getAttribute('needlogin')=='true')
V._$addEvent(_a,'click',d.__onClickNeedLogin._$bind(d,_opt));
}
}
};
d.__onClickNeedLogin=function(_opt,_event){
V._$stopDefault(_event);
var _anchor=V._$getElement(_event),
_href=_anchor.getAttribute('href',2),
_opt=_opt||O,
_onBeforeReload=_opt.onbeforereload;
P.ui._$$QLogin._$getInstance({
classname:'lay-login'
})._$reset({
onsuccess:function(_un){
if(U.str._$isUrl(_href)){
_href=_href.replace(location.r,location.r+'/'+_un);
if(_href!=location.href){
location.href=_href;
return;
}
}
_onBeforeReload&&_onBeforeReload(_un);
setTimeout(function(){location.reload();},500);
}
})._$show()._$focus();
};
d._$setMaxLength=function(_txt){
if(!d._$isText(_txt))
return;
if(_txt.onpropertychange===undefined)
V._$addEvent(_txt,'input',d.__checkLength._$bind(d,_txt));
else
_txt.onpropertychange=d.__checkLength._$bind(d,_txt);
};
d.__checkLength=function(_txt){
var _maxLength=parseInt(_txt.getAttribute('maxlength'));
if(!_maxLength)
return;
if(_txt.value.length>_maxLength)
_txt.value=_txt.value.slice(0,_maxLength);
};
d._$hoverElement=function(_elm,_class){
if(!B._$ISOLDIE)return;
_elm=E._$getElement(_elm);
if(!_elm)return;
_class=_class||'js-hover';
V._$addEvent(_elm,'mouseenter',E._$addClassName._$bind(E,_elm,_class));
V._$addEvent(_elm,'mouseleave',E._$delClassName._$bind(E,_elm,_class));
};
d._$clientWidth=function(){
return document.documentElement.clientWidth||document.body.clientWidth;
};
d._$clientHeight=function(){
return document.documentElement.clientHeight||document.body.clientHeight;
};
d._$scrollLeft=function(){
return document.documentElement.scrollLeft||document.body.scrollLeft;
};
d._$scrollTop=function(){
return document.documentElement.scrollTop||document.body.scrollTop;
};
d._$getStyle=function(_elm,_name){
_elm=E._$getElement(_elm);
if(!_elm)
return;
if(!!document.defaultView){
var _style=document.defaultView.getComputedStyle(_elm,null);
return _name in _style?_style[_name]:_style.getPropertyValue(_name);
}
else{
var _style=_elm.currentStyle;
if(_name=='opacity'){
if(/alpha\(opacity=(.*)\)/i.test(_style.filter)){
var _opacity=parseFloat(RegExp.$1);
return _opacity?_opacity/100:0;
}
return 1;
};
_name=='float'&&
(_name='styleFloat');
var _ret=_style[_name]||_style[U.str._$camelize(_name)];
if(!/^\-?\d+(px)?$/i.test(_ret)&&/^\-?\d/.test(_ret)){
var _style=_elm.style,_left=_style.left,_rsLeft=_elm.runtimeStyle.left;
_elm.runtimeStyle.left=_elm.currentStyle.left;
_style.left=_ret||0;
_ret=_style.pixelLeft+'px';
_style.left=_left;
_elm.runtimeStyle.left=_rsLeft;
}
return _ret;
}
};
d._$setStyle=function(_elm,_styles){
_elm=E._$getElement(_elm);
if(!_elm)
return;
var _style=_elm.style,match;
if(U.str._$isString(_styles)){
_elm.style.cssText+=';'+_styles;
return U.str._$include(_styles,'opacity')?d._$setOpacity(_elm,_styles.match(/opacity:\s*(\d?\.?\d*)/)[1]):_elm;
}
for(var _prop in _styles)
if(_prop=='opacity')
d._$setOpacity(_styles[_prop]);
else
_style[(_prop=='float'||_prop=='cssFloat')?(U.obj._$isUndefined(_style.styleFloat)?'cssFloat':'styleFloat'):_prop]=_styles[_prop];
return _elm;
};
d._$setOpacity=function(_elm,_value){
_elm=E._$getElement(_elm);
_elm.style.opacity=(_value==1||_value==='')?'':(_value<0.00001)?0:_value;
return _elm;
};
d._$setText=function(_elms,_value){
if(U.arr._$isArray(_elms)){
for(var i=0,_a;_a=_elms[i];i++)
d._$setText(_a,_value);
}
else
if(U.str._$isString(_value))
_elms.innerText=_value;
};
d._$toggle=function(_elm,_class){
E._$hasClassName(_elm,_class)?E._$delClassName(_elm,_class):E._$addClassName(_elm,_class);
};
d._$toggleBtn=function(_elm,_disabled,_class){
_elm.disabled=_disabled;
_disabled?E._$addClassName(_elm,_class):E._$delClassName(_elm,_class);
};
d._$enableBtn=function(_elm,_class){
_elm.disabled=false;
E._$delClassName(_elm,_class);
};
d._$disableBtn=function(_elm,_class){
_elm.disabled=true;
E._$addClassName(_elm,_class);
};
d._$onImgError=function(_event,_default){
var _element=V._$getElement(_event);
if(!_element||_element.tagName!='IMG'||
!_default||_element.src==_default)
return;
_element.src=_default;
};
d._$setRankData=function(_elm,_data,_flag){
_elm=E._$getElement(_elm);
if(!_elm)return;
this.__rankData=
this.__rankData||[
{"grade":11,"name":"一级拍客"},
{"grade":12,"name":"二级拍客"},
{"grade":13,"name":"三级拍客"},
{"grade":14,"name":"四级拍客"},
{"grade":15,"name":"五级拍客"},
{"grade":20,"name":"一星拍客"},
{"grade":40,"name":"二星拍客"},
{"grade":60,"name":"三星拍客"}]
var _grade=!isNaN(_data.grade)?_data.grade:_data.shareGrade;
if(_data.rank){
if(_flag){
if(_grade>=20)
_elm.innerText=_data.rank;
}
else
_elm.innerText=_data.rank;
}
else
for(var i=0,l=this.__rankData.length;i<l;i++){
if(this.__rankData[i].grade==_grade){
if(_flag){
if(_grade>=20)
_elm.innerText=this.__rankData[i].name;
}
else
_elm.innerText=this.__rankData[i].name;
break;
}
}
_grade=_grade/20;
if(_grade>=1){
E._$addClassName(_elm,'star iblock');
_elm.title=(_grade==1?'一':(_grade==2?'二':'三'))+'星高级拍客';
_elm.style.width=14*_grade+'px';
}
};
d._$show=function(_arg0){
if(U.arr._$isArray(_arg0)){
for(var i=0,_a;_a=_arg0[i];i++)
d._$show(_a);
}
else{
_arg0=E._$getElement(_arg0);
_arg0.style.display='';
}
};
d._$hide=function(_arg0){
if(U.arr._$isArray(_arg0)){
for(var i=0,_a;_a=_arg0[i];i++)
d._$hide(_a);
}
else{
_arg0=E._$getElement(_arg0);
_arg0.style.display='none';
}
};
d._$insertHTML=function(_elm,_xhtml,_flag){
_elm=E._$getElement(_elm);
if(!_elm||!_xhtml)
return;
var _temp=document.cloneElement('div'),_frag=document.createDocumentFragment();
_temp.innerHTML=_xhtml;
(function(){
if(_temp.firstChild){
_frag.appendChild(_temp.firstChild);
if(_flag)
setTimeout(arguments.callee,0);
else
arguments.callee();
}
else
_elm.appendChild(_frag);
})();
};
d._$getAttribute=function(_elm,_attr){
var _result;
if(d._$hasAttribute(_elm,_attr)){
_result=_elm.getAttribute(_attr);
_elm.removeAttribute(_attr);
}
return _result;
};
d._$hasAttribute=function(_elm,_attr){
return B._$ISIE&&B._$VERSION<=8?(_attr in _elm):_elm.hasAttribute(_attr);
};
})();
(function(){
var r=P('U.reg');
r._$getRegex=function(_type){
this.__regs=this.__regs||{};
if(U.str._$isString(_type)){
if(!this.__regs[_type]){
switch(_type){
case'email':
this.__regs[_type]=/\w[-.\w]*@[-a-z0-9]+(\.[-a-z0-9]+)*\.[a-z]+/;
break;
case'url':
this.__regs[_type]=/(ftp|https?):\/\/[^\/:](?::\d+)?(\/.*)?/;
break;
case'REG_TRIM_SPACE':
this.__regs[_type]=/(?:^\s+)|(?:\s+$)/g;
break;
case'REG_TRIM_SEMICOLON':
this.__regs[_type]=/(?:^\;+)|(?:\;+$)/g;
break;
case'REG_URL_COMPLETE':
this.__regs[_type]=/^(.*?)\//;
break;
}
}
return this.__regs[_type];
}
};
})();
(function(){
var e=P('U.evt');
e._$fireEvent=function(_elm,_type){
if(U.obj._$isObject(_elm)&&U.str._$isString(_type)){
if(B._$ISIE)
_elm.fireEvent(typeof _elm[_type]!=='undefined'?_type:'onpropertychange');
if(document.createEvent){
var _evt=document.createEvent('Events');
_evt.initEvent(_type,true,true);
_elm.dispatchEvent(_evt);
}
}
};
})();
(function(){
var f=P('U.fls');
f._$hackHashFlash=function(){
if(!B._$ISIE)
return;
var _title='';
setInterval(function(){
_title=document.title;
if(_title==title)
return;
else
if(_title.indexOf('#')!=-1)
document.title=title;
},1000);
};
})();
(function(){
var u=P('U.utl');
u.__type=['','163.com','126.com','popo.163.com','188.com','vip.163.com','yeah.net','game.163.com'];
u._$getAutoLogin=function(){
var _info=U._$getCookie('NEPHOTO_LOGIN');
if(!_info)
return null;
_info=_info.split('|');
if(_info.length<3||_info[1]=='null'||_info[2]=='null')
return null;
_info[0]=_info[0]==1?2:_info[0]==2?1:_info[0];
return[_info[1].replace(/@126$/,'').replace(/@188$/,'').replace(/@yeah$/,'').replace(/.vip$/,'')+'@'+(u.__type[parseInt(_info[0])+1]||'163.com'),_info[2]];
};
})();
(function(){
var s=P('U.sys');
s.__param={};
s.__init=function(){
if(window.name!='_nephoto'){
var _param=U._$deserialize(window.name);
if(!!_param)
this.__param[_param.op]=_param;
}
window.name='_nephoto';
var _node=E._$getElement('photo-163-com-template');
if(!_node)
return;
var _ntmp=_node.getElementsByTagName('textarea');
if(!!_ntmp&&_ntmp.length>0)
for(var i=0,l=_ntmp.length,_type,_item;i<l;i++){
_item=_ntmp[i];
if(!_item.id)
continue;
_type=U._$trim(_item.name.toLowerCase());
if(_type=='jst'){
E._$addHtmlTemplate(_item);
continue;
}
if(_type=='txt'){
U.obj._$setData(_item.id,_item.value||'');
continue;
}
if(_type=='ntp'){
E._$addNodeTemplate(_item.value||'',_item.id);
continue;
}
}
E._$removeElement(_node);
};
s._$getXParam=function(_key){
return this.__param[_key]||null;
};
s._$setXParam=function(_key,_value){
this.__param[_key]=_value;
};
s._$getAllXParam=function(){
return this.__param||null;
};
s._$refresh=function(_url,_data){
if(!_url)
return;
if(!!_data&&!!_data.op)
window.name=U._$serialize(_data);
location.href=_url;
};
s.__init();
document.lbody=document.body.appendChild(E._$parseElement('<div class="g-lbody fixed" style="display:none;"></div>'));
})();
(function(){
var m=P('P.ut'),
__pro
m._$$EEvent=C();
__pro=m._$$EEvent.prototype;
__pro._$initialize=function(){
this.__events={};
};
__pro._$addEvent=function(_type,_handler){
if(U.str._$isString(_type)){
this.__events[_type]=this.__events[_type]||[];
var _id=U._$randNumberString();
this.__events[_id]=_handler;
this.__events[_type].push(_handler);
}
return _id;
};
__pro._$delEvent=function(_type,_id){
var _handler=this.__events[_id];
if(_handler){
var _event=this.__events[_type];
if(U.arr._$isArray(_event)){
var _index=U.arr._$indexOf(_event,_handler,true);
if(_index!=-1){
_event.splice(_index,1);
if(!_event.length)
delete this.__events[_type];
}
}
delete this.__events[_id];
}
};
__pro._$dispatchEvent=function(){
var _type=Array.prototype.shift.apply(arguments),_event=this.__events[_type];
if(U.arr._$isArray(_event)){
for(var i=0,_e;_e=_event[i];i++)
_e.apply(window,arguments);
}
};
})();
(function(){
var p=P('P.ut');
p._$$Callback=C();
p._$$Callback._$addCB=function(_fun){
this.__cbs=this.__cbs||[];
this.__cbs.push(_fun);
};
p._$$Callback._$fireCB=function(){
var _args=arguments;
U.arr._$forEach(this.__cbs,function(_cb){
try{
_cb.apply(window,_args);
}
catch(e){}
});
this.__cbs&&delete this.__cbs;
};
})();
(function(){
var p=P('P.ut');
p._$$Single=C();
p._$$Single._$getInstance=function(_opt){
this.__instance=this.__instance||new this(_opt);
return this.__instance;
};
})();

(function(){
var p=P('P.ut');
p._$$Reuse=C();
p._$$Reuse._$allocate=function(_datas,_cnode,_opt,_callback){
var _arr=[];
if(U.arr._$isArray(_datas)){
var that=this;
U.arr._$forEach(_datas,function(_data,_index){
var _reuse=that._$getInstance(_opt);
_reuse._$appendTo&&_reuse._$appendTo(_cnode);
_reuse._$resetOptions&&_reuse._$resetOptions(_opt,_index);
_reuse._$reset&&_reuse._$reset(_data,_opt);
_callback&&_callback(_reuse,_index);
_arr.push(_reuse);
});
}
return _arr;
};
p._$$Reuse._$getInstance=function(_param){
var _reuse=(this.__pool&&this.__pool.length&&this.__pool.shift())||new this(_param);
return _reuse;
};
p._$$Reuse._$recycle=function(_reuse){
if(_reuse instanceof this){
_reuse._$destroy&&_reuse._$destroy();
E._$removeElementByEC(_reuse._$getBody&&_reuse._$getBody());
this.__pool=this.__pool||[];
this.__pool.push(_reuse);
}else if(U.arr._$isArray(_reuse))
for(var i=0,_r;_r=_reuse.pop();this._$recycle(_r));
};
p._$$Reuse._$clear=function(){
this.__pool=[];
};
})();

(function(){
var p=P('P.ut'),
__pro;
p._$$ECache=C();
__pro=p._$$ECache._$extend(P(N.ut)._$$Cache);
__pro.__getListDataInCache=function(_key,_offset,_limit){
if(_key==undefined||_offset==undefined||_limit==undefined)
return null;
var _list=this.__getDataInCache(_key);
if(U.arr._$isArray(_list)){
var _arr=_list.slice(_offset,_offset+_limit);
for(var i=0,_l=_arr.length;i<_l;i++)
if(_arr[i]==undefined)
return null;
}
return _arr;
};
__pro.__setListDataInCache=function(_key,_offset,_limit,_list){
if(_key==undefined||_offset==undefined||_limit==undefined||!U.arr._$isArray(_list))return;
var _data=this.__getDataInCache(_key);
if(U.arr._$isArray(_data)){
U.arr._$forEach(_list,function(_d,_index){
_data.splice(_offset+_index,1,_d);
});
this.__setDataInCache(_key,_data);
}
};
__pro.__setPicSetDataInCache=function(_key,_obj){
if(_key==undefined||_obj==undefined)return;
var _data=this.__getDataInCache(_key);
if(_data==undefined)
this.__setDataInCache(_key,_obj);
};
__pro.__getPicSetDataInCache=function(_key){
if(_key==undefined)return;
return this.__getDataInCache(_key);
};
})();

(function(){
var p=P('P.ut'),__proSTaber;
p._$$STaber=C();
__proSTaber=p._$$STaber.prototype;
__proSTaber._$initialize=function(_list,_options){
_options=_options||O;
this.__selected=_options.selected||'selected';
this.__onGetIndex=_options.ongetindex;
this._$setList(_list||[]);
};
__proSTaber._$setList=function(_list){
this.__list=_list||this.__list;
this.__map=undefined;
var _index;
for(var i=0,_item;_item=this.__list[i];i++){
if(_item._$getID){
this.__map=this.__map||
{};
this.__map[_item._$getID()]=_item;
}
E._$delClassName(U.dom._$getElement(_item),this.__selected);
}
_index=this.__index;
delete this.__index;
this._$setIndex(_index);
};
__proSTaber.__getList=function(){
return this.__list;
};
__proSTaber._$setIndex=function(_index,_callback){
if(!this.__list||this.__list.length<=0||_index==undefined||
this.__index==_index)
return;
var _data=this.__map||this.__list;
E._$delClassName(U.dom._$getElement(_data[this.__index]),this.__selected);
this.__index=_index;
var _elm=U.dom._$getElement(_data[this.__index]);
E._$addClassName(_elm,this.__selected);
_callback&&_callback(_elm);
};
})();
(function(){
var p=P('P.ut'),__proVTaber;
p._$$VTaber=C();
__proVTaber=p._$$VTaber._$extend(p._$$STaber);
__proVTaber._$initialize=function(_elm,_options){
var _list=E._$getChildElements(E._$getElement(_elm));
this._$super(_list,_options);
};
__proVTaber._$setIndex=function(_index){
this.constructor._$supro._$setIndex.call(this,_index);
_index=_index||0;
var _list=this.__getList();
if(!_list||!_list.length)
return;
for(var i=0,_elm;_elm=_list[i];i++){
_elm.style.display=_index==i?'':'none';
}
};
})();

(function(){
var p=P('np.w'),
__targets=[],
__pro;
p._$$ImageLazyLoad=C();
__pro=p._$$ImageLazyLoad._$extend(P(N.ut)._$$Singleton,true);
__pro.__initialize=function(){
this.__bindEvent();
};
__pro.__bindEvent=function(){
var _function=this.__beginLoad._$bind(this);
this._$addEvent('appear',_function);
V._$addEvent(window,'scroll',this.__delayLoad._$bind(this,_function));
V._$addEvent(window,'resize',this.__delayResize._$bind(this,_function));
};
__pro.__clearEvent=function(){
V._$delEvent(window,'scroll',this.__delayLoad._$bind(this));
V._$delEvent(window,'resize',this.__delayResize._$bind(this));
};
__pro._$resetOption=function(_options){
_options=_options||O;
this.__delay=_options.delay||0;
this.__threshold=_options.threshold||0;
this.__failureLimit=_options.failurelimit||0;
this.__container=_options.container||window;
var _tmp=_options.targets||this.__container.getElementsByTagName('img');
this.__imgs=this.__imgs&&this.__imgs.concat(U.obj._$toArray2(_tmp))||_tmp;
this.__placeholder=_options.placeholder||{};
this.__attribute=_options.attribute||'data-lazyload-src';
this.__effect=_options.effect||'';
this._$batEvent({
onbeforedataload:_options.onbeforedataload||F,
onafterdataload:_options.onafterdataload||F
});
this.__setPlaceHolder();
this.__width=0;
this.__height=0;
this._$dispatchEvent('appear');
};
__pro.__setPlaceHolder=function(){
for(var i=0,j=this.__imgs.length,_img,_tmp=[];i<j;i++){
_img=this.__imgs[i];
_img.loaded=false;
if(this.__hasAttribute(_img)){
if(!this.__placeholder['disabled']&&_img.setup!='done'){
_img.src=this.__placeholder['src']||location.snf;
}
_tmp.push(_img);
_img.setup='done';
}
}
this.__imgs=_tmp;
};
__pro.__delayLoad=function(_function){
clearTimeout(this.__timer);
if(this.__pause||this.__isFinish())
return;
var _that=this;
if(this.__lock){
this.__timer=setTimeout(function(){_that.__delayLoad(_function);},this.__delay);
}
else{
this.__lock=true;
_function();
setTimeout(function(){_that.__lock=false;},this.__delay);
}
};
__pro.__beginLoad=function(){
for(var i=0,j=this.__imgs.length,_img;i<j;i++){
_img=this.__imgs[i];
if(this.__isInViewport(_img)){
this.__onLoadData(_img);
_img.loaded=true;
}
}
this.__imgs=U.arr._$filter(this.__imgs,function(_img){return!_img.loaded;});
};
__pro.__isInViewport=function(_element){
return!this.__aboveTheTop(_element)&&!this.__belowTheBottom(_element);
};
__pro.__aboveTheTop=function(_element){
var _top=this.__getClient().top;
return _top>=this.__offset(_element).top+this.__offset(_element).height+this.__threshold;
};
__pro.__belowTheBottom=function(_element){
var _bottom=this.__getClient().bottom;
return _bottom<=this.__offset(_element).top+this.__offset(_element).height-this.__threshold;
};
__pro.__offset=function(_element){
var _left=0,_top=0,_width=_element.offsetWidth,_height=_element.offsetHeight;
while(_element.offsetParent){
_left+=_element.offsetLeft;
_top+=_element.offsetTop;
_element=_element.offsetParent;
}
return{
top:_top,
left:_left,
width:_width,
height:_height
};
};
__pro.__getClient=function(){
var _top=U.dom._$scrollTop(),
_height=U.dom._$clientHeight(),
_bottom=_top+_height,
_left=U.dom._$scrollLeft(),
_width=U.dom._$clientWidth();
return{
top:_top,left:_left,width:_width,height:_height,bottom:_bottom
};
};
__pro.__onLoadData=function(_img){
if(this.__hasAttribute(_img)){
this._$dispatchEvent('onbeforedataload',_img);
if(this.__effect&&this.__effect=='show'){
var _image=new Image();
_image.onload=function(){_img.src=_image.src;}
_image.src=_img.getAttribute(this.__attribute);
}
else{
_img.src=_img.getAttribute(this.__attribute);
}
_img.removeAttribute(this.__attribute);
this._$dispatchEvent('onafterdataload',_img);
}
};
__pro.__hasAttribute=function(_img){
return B._$ISIE&&B._$VERSION<=8?(this.__attribute in _img):_img.hasAttribute(this.__attribute);
};
__pro.__delayResize=function(_function){
var _width=this.__getClient().width,
_height=this.__getClient().height;
if(_width!=this.__width||_height!=this.__height){
this.__width=_width;
this.__height=_height;
this.__delayLoad(_function);
}
};
__pro.__isFinish=function(){
if(!this.__imgs||!this.__imgs.length){
this.__destroy();
return true;
}
else
return false;
};
__pro.__destroy=function(_load){
clearTimeout(this.__timer);
if(_load&&this.__imgs){
U.arr._$forEach(this.__imgs,function(_img){
this.__onLoadData(_img);
}._$bind(this));
this.__imgs=null;
}
this.__clearEvent();
};
__pro._$reset=function(){
this.__destroy();
this.__pause=false;
this.__imgs=this.__container.getElementsByTagName('img');
this.__bindEvent();
this.__setPlaceHolder();
this._$dispatchEvent('appear');
};
__pro._$pauseLoad=function(){
this.__pause=true;
};
})();
(function(){
var p=P('P.ut'),
__ud=UD,
__proCache;
var __arraySortFunc=function(_filed,_flag,_data0,_data1){
var _result=0;
if(_data0[_filed]!=_data1[_filed])
_result=_data0[_filed]<_data1[_filed]?-1:1;
return _result*_flag;
};
var __arrayLocalSortFunc=function(_filed,_flag,_data0,_data1){
var _result=0;
if(_data0[_filed].localeCompare(_data1[_filed])!=0)
_result=_data0[_filed].localeCompare(_data1[_filed]);
return _result*_flag;
};
p._$$APCache=C();
__proCache=p._$$APCache._$extend(P(N.ut)._$$Cache);
U.cls._$augment(p._$$APCache,P.ut._$$Single,true);
__proCache.__completeURL=function(_data){
if(_data.s==undefined)
return _data;
var _tmp0=U.reg._$getRegex('REG_URL_COMPLETE'),_tmp1='http://img$1.'+(_data.s==3?'ph.126.net/':'bimg.126.net/');
if(_data.curl)
_data.curl=_data.curl.replace(_tmp0,_tmp1);
if(_data.murl)
_data.murl=_data.murl.replace(_tmp0,_tmp1);
if(_data.ourl)
_data.ourl=_data.ourl.replace(_tmp0,_tmp1);
if(_data.qurl)
_data.qurl=_data.qurl.replace(_tmp0,_tmp1);
if(_data.surl)
_data.surl=_data.surl.replace(_tmp0,_tmp1);
if(_data.turl)
_data.turl=_data.turl.replace(_tmp0,_tmp1);
if(_data.lurl)
_data.lurl=_data.lurl.replace(_tmp0,_tmp1);
if(_data.cvsurl)
_data.cvsurl=_data.cvsurl.replace(_tmp0,_tmp1);
if(_data.cvlurl)
_data.cvlurl=_data.cvlurl.replace(_tmp0,_tmp1);
if(_data.av)
_data.av=_data.av.replace(_tmp0,_tmp1);
U.obj._$delete(_data,'s');
return _data;
};
__proCache.__genHashFromList=function(_data){
delete _data.hash;
_data.hash={};
if(!_data.list||!_data.list.length)
return;
for(var i=0,l=_data.list.length,_item;i<l;_item=_data.list[i],_item.index=i,_data.hash[_item.id]=this.__completeURL(_item),i++)
;
};
__proCache.__getSortType=function(_type){
switch(_type){
case 2:
case 3:
return 1;
case 4:
case 5:
return 2;
case 6:
case 7:
return 3;
case 8:
return 4;
default:
return 0;
}
};
__proCache.__sortAlbumList=function(){
var _data=this._$getAlbumListInCache();
if(!_data||!_data.list)
return;
var _filed,_flag,_type=_data.sort;
switch(_type){
case 8:
this.__sortDataBySeqResetFlag(_data);
this.__sortDataBySeq(_data,_data.seq);
return;case 2:
case 3:
_filed='count';
_flag=2.5;
break;
case 4:
case 5:
_filed='name';
_flag=4.5;
break;
case 6:
case 7:
_filed='ut';
_flag=6.5;
break;
default:
_filed='t';
_flag=0.5;
break;
}
_flag=2*(_flag-_type);
_data.list.sort(function(_data0,_data1){
if(_filed=='name')
return __arrayLocalSortFunc(_filed,_flag,_data0,_data1);
return __arraySortFunc(_filed,_flag,_data0,_data1);
});
this.__genHashFromList(_data);
};
__proCache.__sortDataBySeqResetFlag=function(_data){
for(var i=0,l=_data.list.length,_item;i<l;i++){
_item=_data.list[i];
if(!!_item)
_item.sflag=false;
}
};
__proCache.__sortDataBySeq=function(_data,_seq){
var _arr=_seq.split(';');
this.__genHashFromList(_data);
for(var i=0,j=0,l=_arr.length,_item,_tmp;i<=l;i++){
_item=_data.hash[_arr[i]];
if(!_item||!!_item.sflag)
continue;
_tmp=_data.list[j];
if(!!_tmp){
_tmp.index=_item.index;
_data.list[_item.index]=_tmp;
}
_item.sflag=true;
_item.index=j;
_data.list[j++]=_item;
}
};
__proCache._$genAlbumListString=function(_aid){
var _data=this._$getAlbumListInCache();
if(_data)
return this.__genAlbumListString(_data.list,_aid);
else
return[];
};
__proCache.__genAlbumListString=function(_data,_aid){
var __tem_arr=[],__selAlbumNum;
if(_data&&_data.length&&_data.length>0)
for(var i=0;i<_data.length;i++){
__tem_arr.push(this.__genAlbumString(_data[i]));
if(_data[i].id==_aid)
__selAlbumNum=i;
}
return[__tem_arr.join('&!&'),__selAlbumNum];
};
__proCache.__genAlbumString=function(_album){
var __tmp_arr=[];
__tmp_arr.push(_album.id);
__tmp_arr.push(_album.count);
__tmp_arr.push('['+_album.name+']');
__tmp_arr.push(_album.au);
return __tmp_arr.join('&!&')+'&!&#';
};
__proCache._$getAlbumListInCache=function(){
return this.__getDataInCache('album_list_'+__ud.hostId);
};
__proCache._$getAlbumList=function(_cb){
var _data=this._$getAlbumListInCache();
if(_data!=undefined){
_cb(_data);
return;
}
this.__getAlbumList._$addCB(_cb);
EJ._$loadScript(__ud.albumUrl,this.__getAlbumList._$bind(this));
};
__proCache.__getAlbumList=function(){
var _ud=__ud,_uid=_ud.hostId;
var _key='g_a$'+_uid+'d',_d=window[_key];
U.obj._$delete(window,_key);
if(_d){
var _data={
list:_d
};
this.__setDataInCache('album_list_'+_uid,_data);
_data.sort=_ud.albumSort;
_key='g_a$'+_uid+'s';
_data.seq=U.str._$trimsc(window[_key]||'');
U.obj._$delete(window,_key);
if(!_ud.editable)
this.__delPrivateAlbum();
this.__sortAlbumList();
}
this.__getAlbumList._$fireCB(this.__getDataInCache('album_list_'+_uid)||null);
};
U.obj._$extend(__proCache.__getAlbumList,P.ut._$$Callback);
__proCache._$getAlbumByIdInCache=function(_id){
var _data=this._$getAlbumListInCache();
return _data?(_data.hash[_id]):null;
};
__proCache._$getAlbumById=function(_id,_cb){
this.__getAlbumById._$addCB(_cb);
this._$getAlbumList(this.__getAlbumById._$bind(this,_id));
};
__proCache.__getAlbumById=function(_id,_data){
this.__getAlbumById._$fireCB(_id,_data?(_data.hash[_id]):null);
};
U.obj._$extend(__proCache.__getAlbumById,P.ut._$$Callback);
__proCache._$getAlbumListByUserId=function(){
var _data=this._$getAlbumListInCache();
if(_data!=undefined){
this._$dispatchEvent('onalbumlistByUserload',_data);
return;
}
J._$loadDataByDWR(location.pdwr,'AlbumBean','getAlbumListByUserId',__ud.hostId,this.__getAlbumListByUserId._$bind(this));
};
__proCache._$refreshPhotoListInCache=function(_aid){
var _album=this._$getAlbumByIdInCache(_aid);
this._$getAlbumByIdWithDWR(_aid,_album);
};
__proCache._$getAlbumByIdWithDWR=function(_aid,_album){
J._$loadDataByDWR(location.pdwr,'AlbumBean','getAlbumData',_aid,'','',U._$randNumberString(8),false,this.__getAlbumByIdWithDWR._$bind(this,_album));
};
__proCache.__getAlbumByIdWithDWR=function(_album,_url){
if(_album){
_album.purl=_url;
this.__delDataInCache('photo_list_'+__ud.hostId+'_'+_album.id);
}
this.__delDataInCache('album_list_'+__ud.hostId);
this._$getAlbumListByUserIdWithDWR();
};
__proCache._$getAlbumListByUserIdWithDWR=function(){
J._$loadDataByDWR(location.pdwr,'AlbumBean','getAlbumListByUserId',__ud.hostId,function(_data){
if(_data){
var _data={list:_data};
this.__setDataInCache('album_list_'+__ud.hostId,_data);
_data.sort=__ud.albumSort;
this.__sortAlbumList();
}
this._$dispatchEvent('onalbumlistbyuseridwithdwrget',_data);
}._$bind(this));
};
__proCache.__getAlbumListByUserId=function(_data){
if(!_data){
this._$dispatchEvent('onalbumlistByUserload',null);
return;
}
var _data={
list:_data
};
this._$dispatchEvent('onalbumlistByUserload',_data);
};
__proCache.__onquepwdGet=function(_data){
if(!_data){
this._$dispatchEvent('onquepwdget',null);
return;
}
this._$dispatchEvent('onquepwdget',_data);
};
__proCache.__onGetQuestion=function(_id,_data){
if(!_data){
this._$dispatchEvent('onGetQuestion',null);
return;
}
this._$dispatchEvent('onGetQuestion',_id,_data);
};
__proCache._$getAlbumQues=function(_id){
J._$loadDataByDWR(location.pdwr,'AlbumBean','getAlbumQuestion',_id,this.__onGetQuestion._$bind(this,_id));
};
__proCache._$getAlbumQuepwd=function(_id){
J._$loadDataByDWR(location.pdwr,'AlbumBean','getAlbumQuepwd',_id,this.__onquepwdGet._$bind(this));
};
__proCache._$getAlbumSortType=function(){
var _data=this._$getAlbumListInCache();
return this.__getSortType(_data.sort);
};
__proCache.__delPrivateAlbum=function(){
var _data=this._$getAlbumListInCache();
if(!_data||!_data.list||!_data.list.length)
return;
for(var _list=_data.list,_seq=';'+_data.seq,i=_list.length-1;i>=0;i--){
if(_list[i].au==2){
if(_data.sort==8)
_seq=_seq.replace(';'+_list[i].id+';',';');
_list.splice(i,1);
}
}
_data.seq=_seq.slice(1);
if(_data.list.length==0)
this._$all_album_is_private=true;
};
__proCache._$hasBlogFriAlbumInCache=function(){
return this.__getDataInCache('hasblogfrialbum');
};
__proCache._$hasBlogFriAlbum=function(_cb){
var _data=this._$hasBlogFriAlbumInCache();
if(_data!=undefined){
_cb(_data);
return;
}
this.__hasBlogFriAlbum._$addCB(_cb);
this._$getAlbumList(this.__hasBlogFriAlbum._$bind(this));
};
__proCache.__hasBlogFriAlbum=function(_data){
var _flag=false;
if(_data&&_data.list&&_data.list.length&&_data.list.length>0){
for(var i=0,_alist=_data.list,_ab;_ab=_alist[i];i++)
if(_ab.au&&_ab.au==4){
_flag=true;
break;
}
}
this.__hasBlogFriAlbum._$fireCB(_flag);
};
U.obj._$extend(__proCache.__hasBlogFriAlbum,P.ut._$$Callback);
__proCache._$createAlbum=function(_album){
J._$postDataByDWR(location.pdwr,'AlbumBean','createWithFolderId',_album.name,_album.desc,_album.au,_album.password||'',_album.question||'',_album.folderId||0,this.__createAlbum._$bind(this));
};
__proCache.__createAlbum=function(_album){
if(_album&&_album.errorType!=2)
var _list=this.__createAlbumInCache(_album);
this._$dispatchEvent('onalbumcreate',_album,_list);
};
__proCache.__createAlbumInCache=function(_album){
var _data=this._$getAlbumListInCache();
if(!_data)
return null;
if(!!_data.seq)
_data.seq=_album.id+";"+_data.seq;
_data.list.unshift(_album);
this.__sortAlbumList();
return _data.list;
};
__proCache._$updateAlbum=function(_album){
J._$postDataByDWR(location.pdwr,'AlbumBean','updateMetaWithFolderId',_album.id,_album.name,_album.desc,_album.au,_album.password||'',_album.question||'',_album.folderId||0,this.__updateAlbum._$bind(this));
};
__proCache.__updateAlbum=function(_album){
if(_album&&_album.errorType!=2){
var _type=this._$getAlbumSortType(),_noresort=_type!=2,_list=this.__updateAlbumInCache(_album,_noresort);
}
this._$dispatchEvent('onalbumupdate',_album);
};
__proCache.__updateAlbumInCache=function(_album,_noresort){
var _data=this._$getAlbumListInCache(),_data0=_data.hash[_album.id];
if(!_noresort){
_data.list[_data0.index]=_album;
this.__sortAlbumList();
return _data.list;
}
_album.index=_data0.index;
_album=this.__completeURL(_album);
_data.hash[_album.id]=_album;
_data.list[_data0.index]=_album;
return _album;
};
__proCache._$updateAlbumName=function(_id,_name){
J._$postDataByDWR(location.pdwr,'AlbumBean','updateName',_id,_name,this.__updateAlbumName._$bind(this));
};
__proCache.__updateAlbumName=function(_album){
if(_album&&_album.errorType!=2)
this.__updateAlbumInCache(_album,true);
this._$dispatchEvent('onalbumnameupdate',_album);
};
__proCache._$updateAlbumCover=function(_aid,_pid){
P.ui._$$Posting._$getInstance()._$reset({msg:'封面设置中...'})._$show();
J._$postDataByDWR(location.pdwr,'AlbumBean','updateCover',_aid,_pid,this.__updateAlbumCover._$bind(this,_pid));
};
__proCache.__updateAlbumCover=function(_id,_album){
P.ui._$$Posting._$getInstance()._$hide();
if(!_album){
this._$dispatchEvent('onalbumcoverupdate',_id,false);
return;
}
this.__updateAlbumInCache(_album,true);
this._$dispatchEvent('onalbumcoverupdate',_id,true);
};
__proCache._$updateAlbumDesc=function(_aid,_desc){
J._$postDataByDWR(location.pdwr,'AlbumBean','updateDesc',_aid,_desc,this.__updateAlbumDesc._$bind(this));
};
__proCache.__updateAlbumDesc=function(_album){
if(_album&&_album.errorType!=2)
this.__updateAlbumInCache(_album,true);
this._$dispatchEvent('onalbumdescupdate',_album);
};
__proCache._$deleteAlbum=function(_ids){
J._$postDataByDWR(location.pdwr,'AlbumBean','deleteAlbums',_ids,this.__deleteAlbum._$bind(this,_ids));
};
__proCache.__deleteAlbum=function(_ids,_suc){
if(!_suc){
this._$dispatchEvent('onalbumdelete',_ids,_suc);
return;
}
this.__deleteAlbumInCache(_ids);
this._$dispatchEvent('onalbumdelete',_ids,_suc);
};
__proCache.__deleteAlbumInCache=function(_ids){
var _data=this._$getAlbumListInCache();
for(var i=0,_id;_id=_ids[i];i++){
_data.list.splice(_data.hash[_id].index,1);
this.__genHashFromList(_data);
}
return _data.list;
};
__proCache._$changeAlbumSort=function(_type,_seq){
var _data=this._$getAlbumListInCache();
if((_type!=8&&_type==_data.sort)||
(_type==8&&_seq==_data.seq))
return;
J._$postDataByDWR(location.pdwr,'UserSpaceBean','updateSeq',_type,_seq||'',this.__changeAlbumSort._$bind(this,_type,_seq||''));
};
__proCache.__changeAlbumSort=function(_type,_seq,_suc){
if(!_suc){
this._$dispatchEvent('onalbumsortchange',false);
return;
}
var _data=this._$getAlbumListInCache();
_data.sort=_type;
_data.seq=_seq;
this.__sortAlbumList();
this._$dispatchEvent('onalbumsortchange',true,_type);
};
__proCache._$checkAlbumPassword=function(_id,_password,_key){
var _data=this._$getAlbumByIdInCache(_id);
if(_data.purl){
this.__checkAlbumPassword(_id,_data.purl);
return;
}
var _tmp=_data.dmt;
_data.dmt=U._$randNumberString(8);
J._$loadDataByDWR(location.pdwr,'AlbumBean','getAlbumData',_id,_password||'',_key||'',_data.dmt,false,this.__checkAlbumPassword._$bind(this,_id));
};
__proCache.__checkAlbumPassword=function(_id,_url){
var _data=this._$getAlbumByIdInCache(_id);
_data.purl=_url;
this._$dispatchEvent('onalbumpasswordcheck',_id,_url?true:false);
};
__proCache._$checkAlbumBlogFriends=function(_id){
var _data=this._$getAlbumByIdInCache(_id);
if(_data.purl){
this.__checkAlbumBlogFriends(_id,_data.purl);
return;
}
var _tmp=_data.dmt;
_data.dmt=U._$randNumberString(8);
J._$loadDataByDWR(location.pdwr,'AlbumBean','getAlbumData',_id,'','',_data.dmt,false,this.__checkAlbumBlogFriends._$bind(this,_id));
};
__proCache.__checkAlbumBlogFriends=function(_id,_url){
var _data=this._$getAlbumByIdInCache(_id);
_data.purl=_url;
this._$dispatchEvent('onalbumblogfriendcheck',_id,_url?true:false);
};
__proCache._$getAlbumDataInSession=function(_id){
J._$loadDataByDWR(location.pdwr,'AlbumBean','getAlbumData',_id,'','fromblog',U._$randNumberString(8),false,this.__getAlbumDataInSession._$bind(this,_id));
};
__proCache.__getAlbumDataInSession=function(_id,_url){
if(!_id||!_url){
this._$dispatchEvent('ongetalbumdatainsession',_id,null);
return;
}
if(!U.arr._$isArray(window.auAids))
window.auAids=[];
var _index=U.arr._$indexOf(window.auAids,function(_aid){return _aid==_id});
if(_index==-1||_index==undefined)
window.auAids.push(_id);
var _url='http://'+_url,_type=0;
J._$loadScript(_url,this.__getPhotoListByUrl._$bind(this,_id,_url,_type));
};
__proCache._$changePhotoSort=function(_id,_type,_seq){
var _data=this._$getPhotoListInCache(_id);
if((_type!=8&&_type==_data.sort)||(_type==8&&_seq==_data.seq))
return;
J._$postDataByDWR(location.pdwr,'AlbumBean','updateSeq',_id,_type,_seq||'',this.__changePhotoSort._$bind(this,_id,_type,_seq||''));
};
__proCache.__changePhotoSort=function(_id,_type,_seq,_suc){
if(!_suc){
this._$dispatchEvent('onphotosortchange',null);
return;
}
var _data=this._$getPhotoListInCache(_id);
_data.sort=_type;
_data.seq=_seq;
this.__sortPhotoList(_id);
this._$dispatchEvent('onphotosortchange',_data.list,_type);
};
__proCache.__sortPhotoList=function(_id){
var _data=this._$getPhotoListInCache(_id);
if(!_data||!_data.list)
return;
var _filed,_flag,_type=_data.sort;
switch(_type){
case 8:
this.__sortDataBySeqResetFlag(_data);
this.__sortDataBySeq(_data,_data.seq);
return;
case 4:
case 5:
_filed='desc';
_flag=4.5;
break;
default:
_filed='t';
_flag=0.5;
break;
}
_flag=2*(_flag-_type);
_data.list.sort(function(_data0,_data1){
if(_filed=='desc')
return __arrayLocalSortFunc(_filed,_flag,_data0,_data1);
return __arraySortFunc(_filed,_flag,_data0,_data1);
});
this.__genHashFromList(_data);
};
__proCache._$getPhotoList=function(_id){
var _data=this._$getPhotoListInCache(_id);
if(!!_data){
this._$dispatchEvent('onphotolistload',_id,_data);
return;
}
_data=this._$getAlbumByIdInCache(_id);
if(!_data){
this._$dispatchEvent('onphotolistload',_id,null);
return;
}
if(_data.purl){
this.__getPhotoList_0(_id,_data.purl);
return;
}
this.__tmp_callback=this._$getEvent('onalbumpasswordcheck');
this._$addEvent('onalbumpasswordcheck',this.__getPhotoList_0._$bind(this));
this._$checkAlbumPassword(_id,'','');
};
__proCache.__getPhotoList_0=function(_id,_suc){
var _data=this._$getAlbumByIdInCache(_id);
var _url=_suc?_data.purl:'';
this._$addEvent('onalbumpasswordcheck',this.__tmp_callback);
delete this.__tmp_callback;
if(!_url){
this._$dispatchEvent('onphotolistload',_id,null);
return;
}
_url='http://'+_url;
J._$loadScript(_url,this.__getPhotoList_1._$bind(this,_id));
};
__proCache.__getPhotoList_1=function(_id){
var _key='g_p$'+_id+'d',_d=window[_key];
if(!_d){
var _album=this._$getAlbumByIdInCache(_id);
_album.purl='';
if(!!_album.try404Flag)
this._$dispatchEvent('onphotolistload',_id,null);
else{
_album.try404Flag=true;
this._$getPhotoList(_id);
}
return;
}
var _data={
list:_d
};
var _ud=__ud;
this.__setDataInCache('photo_list_'+_ud.hostId+'_'+_id,_data);
_data.sort=this._$getAlbumByIdInCache(_id).st;
_key='g_p$'+_id+'s';
_data.seq=U.str._$trimsc(window[_key]||'');
this.__sortPhotoList(_id);
this._$dispatchEvent('onphotolistload',_id,_data);
};
__proCache._$getPhotoListByUrl=function(_id,_url,_type){
_url='http://'+_url;
J._$loadScript(_url,this.__getPhotoListByUrl._$bind(this,_id,_url,_type));
};
__proCache.__getPhotoListByUrl=function(_id,_url,_type){
var _key=_type==1?'g_pic$'+_id:'g_p$'+_id+'d',_d=window[_key];
U.obj._$delete(window,_key);
if(!_d){
if(!!this.__getPhotoListByUrl.try404Flag)
this._$dispatchEvent('onphotolistload',_id,null);
else{
this.__getPhotoListByUrl.try404Flag=true;
this._$getPhotoList(_id,_url);
}
return;
}
var _data={
list:_d
};
var _ud=__ud;
this.__setDataInCache('photo_list_'+_ud.hostId+'_'+_id,_data);
this.__genHashFromList(_data);
if(_type==0){
_data.sort=this._$getAlbumByIdInCache(_id)&&this._$getAlbumByIdInCache(_id).st||_ud.albumSort;
_key='g_p$'+_id+'s';
_data.seq=U.str._$trimsc(window[_key]||'');
this.__sortPhotoList(_id);
}
this._$dispatchEvent('onphotolistload',_id,_data);
};
__proCache._$getPictureSetUrl=function(_sid){
J._$postDataByDWR(location.sdwr,'PictureSetBean','getPictureSetData',_sid,this.__getPictureSetUrl._$bind(this,_sid,1));
};
__proCache.__getPictureSetUrl=function(_id,_type,_url){
_url='http://'+_url;
J._$loadScript(_url,this.__getPhotoListByUrl._$bind(this,_id,_url,_type));
};
__proCache._$getPhotoExif=function(_pid,_cb){
var _data=this.__getDataInCache('photo_exif_'+'_'+_pid);
if(_data!=undefined){
_cb&&_cb(_pid,_data);
return;
}
this.__getPhotoExif._$addCB(_cb);
J._$postDataByDWRWithSync(location.pdwr,'PhotoBean','getPhotoExif',_pid,this.__getPhotoExif._$bind(this,_pid));
};
__proCache.__getPhotoExif=function(_pid,_exif){
_exif=_exif||'';
this.__setDataInCache('photo_exif_'+'_'+_pid,_exif);
this.__getPhotoExif._$fireCB(_pid,_exif);
};
U.obj._$extend(__proCache.__getPhotoExif,P.ut._$$Callback);
__proCache._$getPhotoListInCache=function(_id){
return this.__getDataInCache('photo_list_'+__ud.hostId+'_'+_id);
};
__proCache.__updatePhotoInCache=function(_id,_photo,_noresort){
var _data=this._$getPhotoListInCache(_id),_data0=_data.hash[_photo.id];
if(!_noresort){
_data.list[_data0.index]=_photo;
this.__sortPhotoList(_id);
return _data.list;
}
_photo.index=_data0.index;
_photo=this.__completeURL(_photo);
_data.hash[_photo.id]=_photo;
_data.list[_data0.index]=_photo;
return _photo;
};
__proCache._$getPhotoByIdInCache=function(_aid,_pid){
var _data=this._$getPhotoListInCache(_aid);
return _data?(_data.hash[_pid]||null):null;
};
__proCache._$getPhotoById=function(_aid,_pid){
var _data=this._$getPhotoByIdInCache(_aid,_pid);
if(_data!=undefined){
this._$dispatchEvent('onphotoload',null);
return;
}
J._$loadDataByDWR(location.pdwr,'PhotoBean','getPhotoById',_pid,this._$dispatchEvent._$bind(this,'onphotoload',_pid));
};
__proCache._$updatePhotoDesc=function(_pid,_aid,_desc){
J._$postDataByDWR(location.pdwr,'PhotoBean','updateDesc',_pid,_aid,_desc||'',this.__updatePhotoDesc._$bind(this,_aid));
};
__proCache.__updatePhotoDesc=function(_aid,_photo){
if(_photo&&_photo.errorType!=2)
this.__updatePhotoInCache(_aid,_photo,true);
this._$dispatchEvent('onphotodescupdate',_photo);
};
__proCache._$getUploadSetupedFlag=function(){
J._$loadDataByDWR(location.pdwr,'UploadSessionBean','getUploadSetupedFlag','',this.__getUploadSetupedFlag._$bind(this));
};
__proCache.__getUploadSetupedFlag=function(_ret){
if(_ret&&_ret==1){
this._$dispatchEvent('onuploadtoolwebsetup',_ret);
}
else{
this._$dispatchEvent('onuploadtoolwebsetup');
}
};
__proCache._$addDelOriginFile=function(_aids){
J._$postDataByDWR(location.pdwr,'AlbumBean','addDelOriginFile',_aids,this.__addDelOriginFile._$bind(this));
};
__proCache.__addDelOriginFile=function(_suc){
if(!_suc){
this._$dispatchEvent('onalbumcompress',null);
return;
}
this._$dispatchEvent('onalbumcompress',_suc);
};
__proCache._$getPlanDetail=function(){
J._$loadDataByDWR(location.pdwr,'AlbumBean','getPlanDetail',__ud.hostId,this.__getPlanDetail._$bind(this));
};
__proCache.__getPlanDetail=function(_data){
if(!_data){
this._$dispatchEvent('ongetplandetail',null);
return;
}
var _data={
list:_data
};
this._$dispatchEvent('ongetplandetail',_data);
};
__proCache._$pointExchange=function(_no){
J._$postDataByDWR(location.pdwr,'AlbumBean','pointExchange',_no,false,this.__pointExchange._$bind(this));
};
__proCache.__pointExchange=function(_suc){
this._$dispatchEvent('onpointexchanged',_suc);
};
__proCache._$deletePhotos=function(_aid,_pids,_coverID){
J._$postDataByDWR(location.pdwr,'PhotoBean','deleteNew',_pids,_aid,_coverID,this.__deletePhotos._$bind(this,_aid,_pids))
};
__proCache.__deletePhotos=function(_aid,_pids,_album){
if(!_album){
this._$dispatchEvent('onphotodelete',_pids,false);
return;
}
var _data=this._$getPhotoListInCache(_aid),_list=_data.list;
for(var i=_pids.length;i>0;i--)
_list.splice(_data.hash[_pids[i-1]].index,1);
this.__genHashFromList(_data);
var _nosort=this._$getAlbumSortType()!=1;
var _nalbum=this.__updateAlbumInCache(_album,_nosort);
this._$dispatchEvent('onphotodelete',_pids,true);
};
__proCache._$movePhotos=function(_pids,_sid,_did,_coverID){
J._$postDataByDWR(location.pdwr,'AlbumBean','movePhotosNew',_pids,_sid,_did,_coverID,this.__movePhotos._$bind(this,_pids,_sid,_did))
};
__proCache.__movePhotos=function(_pids,_sid,_did,_arr){
if(!_arr||!_arr.length){
this._$dispatchEvent('onphotomove',null);
return;
}
var _data0=this._$getPhotoListInCache(_sid),
_data1=this._$getPhotoListInCache(_did)||[],
_list0=_data0.list;
_data1.list=_data1.list||[];
for(var i=_pids.length,_obj;i>0;i--){
_obj=_list0.splice(_data0.hash[_pids[i-1]].index,1)[0];
_data1&&_data1.list.push(_obj);
}
this.__genHashFromList(_data0);
_data1&&this.__sortPhotoList(_did);
_data0=this._$getAlbumListInCache();
_data0.list[_data0.hash[_sid].index]=_arr[0];
_data0.list[_data0.hash[_did].index]=_arr[1];
this.__sortAlbumList();
this._$dispatchEvent('onphotomove',_arr);
};
__proCache._$updateAlbumType=function(_id,_type,_password,_question){
J._$postDataByDWR(location.pdwr,'AlbumBean','updateType',_id,_type,_password||'',_question||'',this.__updateAlbumType._$bind(this));
};
__proCache.__updateAlbumType=function(_album){
if(!_album){
alert('修改权限失败，请稍后再试!');
return;
}
this.__updateAlbumInCache(_album,true);
this._$dispatchEvent('onupdatealbumtype',_album,true);
setTimeout(function(){alert('设置成功！');},10);
};
__proCache._$rotateImage=function(_aid,_pid,_angle){
P.ui._$$Posting._$getInstance()._$reset({msg:'相片旋转中...'})._$show();
J._$postDataByDWR(location.pdwr,'PhotoBean','rotateImage',_pid,_angle,this.__rotateImage._$bind(this,_aid));
};
__proCache.__rotateImage=function(_aid,_photo){
P.ui._$$Posting._$getInstance()._$hide();
var _flag=false;
if(U.obj._$isObject(_photo)){
this.__updatePhotoInCache(_aid,_photo);
_flag=true;
}
this._$dispatchEvent('onrotateimage',this.__completeURL(_photo));
};
__proCache._$setUploadSetupFlag=function(){
J._$postDataByDWR(location.pdwr,'UploadSessionBean','setUploadSetupedFlag',false);
};
__proCache._$clearUploadSetupFlag=function(){
J._$postDataByDWR(location.pdwr,'UploadSessionBean','clearUploadSetupedFlag',false);
};
__proCache._$getUploadSetupFlag=function(){
J._$postDataByDWR(location.pdwr,'UploadSessionBean','getUploadSetupedFlag',false,this.__getUploadSetupedFlag._$bind(this));
};
__proCache.__getUploadSetupedFlag=function(_flag){
this._$dispatchEvent('onGetUploadSetupedFlag',_flag);
};
__proCache._$onPageSettingUpdate=function(_itype,_stype){
J._$postDataByDWR(location.pdwr,'ProfileBean','updatePageSetting',_itype,_stype,this._$dispatchEvent._$bind(this,'onpagesettingupdate',_itype,_stype));
};
__proCache._$getLovePhotoExif=function(_uid,_pid,_cb){
var _data=this.__getDataInCache('photo_exif_'+'_'+_pid);
if(_data!=undefined){
_cb&&_cb(_pid,_data);
return;
}
this.__getPhotoExif._$addCB(_cb);
J._$postDataByDWR(location.pdwr,'PhotoBean','getPhotoExifByIdANDUserId',_pid,_uid,this.__getPhotoExif._$bind(this,_pid));
};
__proCache._$applyRecover=function(_uid){
J._$postDataByDWR(location.pdwr,'UserApplyBean','get30DaysRecovery',_uid,this._$dispatchEvent._$bind(this,'onalbumrecovery'));
};
__proCache._$getAlbumFolders=function(){
var _cache=this.__getDataInCache('folders');
if(_cache)
this._$dispatchEvent('onalbumfoldersget',_cache);
else
J._$postDataByDWRWithSync(location.pdwr,'AlbumFolderBean','getAlbumFolders',function(_data){
if(!_data);
else
this.__setDataInCache('folders',_data);
this._$dispatchEvent('onalbumfoldersget',_data);
}._$bind(this));
};
__proCache._$getAlbumFolderItemById=function(_aid){
J._$postDataByDWRWithSync(location.pdwr,'AlbumFolderBean','getAlbumFolderItemById',_aid,__ud.hostId,this._$dispatchEvent._$bind(this,'onalbumfolderitemget'));
};
__proCache._$addAlbumFolder=function(_name){
J._$postDataByDWRWithSync(location.pdwr,'AlbumFolderBean','addAlbumFolder',_name,function(_folder){
if(_folder){
if(_folder.errorType==2){
alert('添加失败，分类名或描述中含有不恰当的词汇。');
return;
}
}
var _folders=this.__getDataInCache('folders');
_folders[_folders.length]=_folder;
this._$dispatchEvent('onalbumfolderadd',_folder);
}._$bind(this));
};
__proCache._$getAlbumItemsByFolderId=function(_folderId){
J._$postDataByDWRWithSync(location.pdwr,'AlbumFolderBean','getAlbumFolderItemsByFolderId',_folderId,1000,0,function(_list){
this._$dispatchEvent('onalbumfolderitemsget',_list);
}._$bind(this));
};
__proCache._$delAlbumFoldersInCache=function(){
this.__delDataInCache('folders');
};
__proCache._$getRecycleInfo=function(){
J._$postDataByDWRWithSync(location.pdwr,'UserApplyBean','getRecycleInfo',__ud.visitName,false,function(_totalCount){
this._$dispatchEvent('onRecycleInfoGet',_totalCount||0);
}._$bind(this));
};
__proCache._$getTotalRecoverPhotoCounts=function(){
J._$postDataByDWRWithSync(location.pdwr,'UserApplyBean','get30DaysRecovery',__ud.visitId,false,function(_totalCount){
this._$dispatchEvent('onphotobackupcountget',_totalCount||0);
}._$bind(this));
};
__proCache._$getCanRecoverPhotos=function(_pageNo){
J._$postDataByDWR(location.pdwr,'UserApplyBean','getPhotoBackupsByUserName',__ud.visitId,_pageNo,function(_list){
this._$dispatchEvent('onphotobackupsget',_list||[]);
}._$bind(this));
};
__proCache._$recoverPhotos=function(_photoIds){
J._$postDataByDWRWithSync(location.pdwr,'UserApplyBean','recoverPhotos',_photoIds,__ud.visitName,function(_flag){
this._$dispatchEvent('onphotobackupsrecover',_flag||[]);
}._$bind(this));
};
__proCache._$clearPhotos=function(_photoIds){
J._$postDataByDWRWithSync(location.pdwr,'UserApplyBean','updatePhotoBackupsByPids',__ud.visitId,_photoIds,function(_flag){
this._$dispatchEvent('onphotobackupsclear',_flag||[]);
}._$bind(this));
};
})();

(function(){
var p=P('P.ut'),
__maxCount=10000,
__pro,
__reg0=/^1#/,__reg1=/^中国#/,__reg2=/^(.*?)\//,__extraRecDir,__ud=window.UD;
var __preTreatRegions=function(_data){
if(!U.obj._$isObject(_data))
return;
var _obj;
for(var _p in _data){
_obj=_data[_p];
_obj.ids=_obj.ids.replace(__reg0,'');
_obj.nms=_obj.nms.replace(__reg1,'');
__preTreatRegions(_obj.son);
}
};
var __count=0;
var __createIndex=function(_data,_map){
if(!U.obj._$isObject(_data)||!U.obj._$isObject(_map))
return;
var _obj;
for(var _p in _data){
_obj=_data[_p];
_map[_obj.id]=_obj;
__createIndex(_obj.son,_map);
}
__count++;
};
var __completeURL=function(_data){
if(_data.s==undefined)
return _data;
var _tmp0=U.reg._$getRegex('REG_URL_COMPLETE'),_tmp1='http://img$1.'+(_data.s==3?'ph.126.net/':'bimg.126.net/');
if(_data.lurl)
_data.lurl=_data.lurl.replace(_tmp0,_tmp1);
if(_data.murl)
_data.murl=_data.murl.replace(_tmp0,_tmp1);
if(_data.surl)
_data.surl=_data.surl.replace(_tmp0,_tmp1);
if(_data.cvsurl)
_data.cvsurl=_data.cvsurl.replace(_tmp0,_tmp1);
if(_data.cvlurl)
_data.cvlurl=_data.cvlurl.replace(_tmp0,_tmp1);
if(_data.cv240url)
_data.cv240url=_data.cv240url.replace(_tmp0,_tmp1);
if(_data.cvmurl)
_data.cvmurl=_data.cvmurl.replace(_tmp0,_tmp1);
U.obj._$delete(_data,'s');
return _data;
};
p._$$ShareCache=C();
__pro=p._$$ShareCache._$extend(P.ut._$$ECache);
P.ut._$$ShareCache._$filterHide=function(_obj,_flag){
if(!U.obj._$isObject(_obj))return null;
if(_flag)
return U.obj._$extend({},_obj,function(_data){return _data.s!=0;});
else
return U.obj._$extend({},_obj,function(_data){return _data.id!='37'&&_data.s!=0;});
};
__pro._$getShareDir=function(_cb){
var _data=this.__getDataInCache('share_dir');
if(_data!=undefined){
_cb(_data);
return;
}
this.__getShareDir._$addCB(_cb);
EJ._$loadScript('http://photo.163.com/share/info/share_dir.json',this.__getShareDir._$bind(this));
};
__pro.__getShareDir=function(){
var _key='g_share_dir',_d=window[_key];
U.obj._$delete(window,_key);
if(_d){
var _map={};
__createIndex(_d,_map);
this.__setDataInCache('share_dir',_d);
this.__setDataInCache('share_dir_map',_map);
}
this.__getShareDir._$fireCB(this.__getDataInCache('share_dir')||null);
};
U.obj._$extend(__pro.__getShareDir,P.ut._$$Callback);
__pro._$getDirByIdInCache=function(_id){
var _map=this.__getDataInCache('share_dir_map');
return _map?_map[_id]:null;
};
__pro._$getDirById=function(_id,_cb){
this.__getDirById._$addCB(_cb);
this._$getShareDir(this.__getDirById._$bind(this,_id));
};
__pro.__getDirById=function(_id,_shareDir){
var _map=this.__getDataInCache('share_dir_map');
this.__getDirById._$fireCB(_id,U.obj._$isObject(_shareDir)?_map[_id]:null);
};
U.obj._$extend(__pro.__getDirById,P.ut._$$Callback);
__pro._$getDirHelps=function(_cb){
var _data=this.__getDataInCache('share_dir_help');
if(_data!=undefined){
_cb(_data);
return;
}
this.__getDirHelps._$addCB(_cb);
EJ._$loadScript('http://photo.163.com/share/info/share_dir_help.json',this.__getDirHelps._$bind(this));
};
__pro.__getDirHelps=function(){
var _key='g_share_dir_help',_d=window[_key];
U.obj._$delete(window,_key);
_d&&this.__setDataInCache('share_dir_help',_d);
this.__getDirHelps._$fireCB(this.__getDataInCache('share_dir_help')||null);
};
U.obj._$extend(__pro.__getDirHelps,P.ut._$$Callback);
__pro._$getDirHelpByIdInCache=function(_id){
var _map=this.__getDataInCache('share_dir_help');
return _map?_map[_id]:null;
};
__pro._$getDirCamerists=function(_cb){
var _data=this.__getDataInCache('share_dir_biz_user');
if(_data!=undefined){
_cb(_data);
return;
}
this.__getDirCamerists._$addCB(_cb);
EJ._$loadScript('http://photo.163.com/share/info/share_dir_biz_user.json',this.__getDirCamerists._$bind(this));
};
__pro.__getDirCamerists=function(){
var _key='g_share_dir_biz_user',_d=window[_key];
U.obj._$delete(window,_key);
_d&&this.__setDataInCache('share_dir_biz_user',_d);
this.__getDirCamerists._$fireCB(this.__getDataInCache('share_dir_biz_user')||null);
};
U.obj._$extend(__pro.__getDirCamerists,P.ut._$$Callback);
__pro._$getDirCameristById=function(_id,_cb){
this.__getDirCameristById._$addCB(_cb);
this._$getDirCamerists(this.__getDirCameristById._$bind(this,_id));
};
__pro.__getDirCameristById=function(_id,_camerists){
this.__getDirCameristById._$fireCB(_id,U.obj._$isObject(_camerists)?_camerists[_id]:null);
};
U.obj._$extend(__pro.__getDirCameristById,P.ut._$$Callback);
__pro._$getCityCamerists=function(_cb){
var _data=this.__getDataInCache('share_city_biz_user');
if(_data!=undefined){
_cb(_data);
return;
}
this.__getCityCamerists._$addCB(_cb);
EJ._$loadScript('http://photo.163.com/share/info/share_city_biz_user.json',this.__getCityCamerists._$bind(this));
};
__pro.__getCityCamerists=function(){
var _key='g_share_city_biz_user',_d=window[_key];
U.obj._$delete(window,_key);
_d&&this.__setDataInCache('share_city_biz_user',_d);
this.__getCityCamerists._$fireCB(this.__getDataInCache('share_city_biz_user')||null);
};
U.obj._$extend(__pro.__getCityCamerists,P.ut._$$Callback);
__pro._$getCityCameristById=function(_id,_cb){
this.__getCityCameristById._$addCB(_cb);
this._$getCityCamerists(this.__getCityCameristById._$bind(this,_id));
};
__pro.__getCityCameristById=function(_id,_camerists){
this.__getCityCameristById._$fireCB(_id,U.obj._$isObject(_camerists)?_camerists[_id]:null);
};
U.obj._$extend(__pro.__getCityCameristById,P.ut._$$Callback);
__pro._$getRegionsInCache=function(){
return this.__getDataInCache('regions');
};
__pro._$getRegions=function(_cb){
var _data=this._$getRegionsInCache();
if(_data!=undefined){
_cb(_data);
return;
}
this.__getRegions._$addCB(_cb);
EJ._$loadScript('http://photo.163.com/share/info/region.json',this.__getRegions._$bind(this));
};
__pro.__getRegions=function(){
var _key='g_region',_d=window[_key];
U.obj._$delete(window,_key);
if(_d){
__preTreatRegions(_d);
var _map={};
__createIndex(_d,_map);
this.__setDataInCache('regions',_d);
this.__setDataInCache('regions_map',_map);
}
this.__getRegions._$fireCB(this.__getDataInCache('regions')||null);
};
U.obj._$extend(__pro.__getRegions,P.ut._$$Callback);
__pro._$getRegionById=function(_id,_cb){
this.__getRegionById._$addCB(_cb);
this._$getRegions(this.__getRegionById._$bind(this,_id));
};
__pro.__getRegionById=function(_id,_region){
var _map=this.__getDataInCache('regions_map');
this.__getRegionById._$fireCB(_id,U.obj._$isObject(_region)?_map[_id]:null);
};
U.obj._$extend(__pro.__getRegionById,P.ut._$$Callback);
__pro._$getCameraInCache=function(){
return this.__getDataInCache('camera');
};
__pro._$getCamera=function(_cb){
var _data=this._$getCameraInCache();
if(_data!=undefined){
_cb(_data);
return;
}
this.__getCamera._$addCB(_cb);
EJ._$loadScript('http://photo.163.com/share/info/camera.json',this.__getCamera._$bind(this));
};
__pro.__getCamera=function(){
var _key='g_camera',_d=window[_key];
U.obj._$delete(window,_key);
if(_d){
var _map={};
__createIndex(_d,_map);
this.__setDataInCache('camera',_d);
this.__setDataInCache('camera_map',_map);
}
this.__getCamera._$fireCB(this.__getDataInCache('camera')||null);
};
U.obj._$extend(__pro.__getCamera,P.ut._$$Callback);
__pro._$getCameraById=function(_id,_cb){
this.__getCameraById._$addCB(_cb);
this._$getCamera(this.__getCameraById._$bind(this,_id));
};
__pro.__getCameraById=function(_id,_camera){
var _map=this.__getDataInCache('camera_map');
this.__getCameraById._$fireCB(_id,U.obj._$isObject(_camera)?_map[_id]:null);
};
U.obj._$extend(__pro.__getCameraById,P.ut._$$Callback);
__pro._$getLensInCache=function(){
return this.__getDataInCache('lens');
};
__pro._$getLens=function(_cb){
var _data=this._$getLensInCache();
if(_data!=undefined){
_cb(_data);
return;
}
this.__getLens._$addCB(_cb);
EJ._$loadScript('http://photo.163.com/share/info/lens.json',this.__getLens._$bind(this));
};
__pro.__getLens=function(){
var _key='g_lens',_d=window[_key];
U.obj._$delete(window,_key);
if(_d){
var _map={};
__createIndex(_d,_map);
this.__setDataInCache('lens',_d);
this.__setDataInCache('lens_map',_map);
}
this.__getLens._$fireCB(this.__getDataInCache('lens')||null);
};
U.obj._$extend(__pro.__getLens,P.ut._$$Callback);
__pro._$getLensById=function(_id,_cb){
this.__getLensById._$addCB(_cb);
this._$getLens(this.__getLensById._$bind(this,_id));
};
__pro.__getLensById=function(_id,_lens){
var _map=this.__getDataInCache('lens_map');
this.__getLensById._$fireCB(_id,U.obj._$isObject(_lens)?_map[_id]:null);
};
U.obj._$extend(__pro.__getLensById,P.ut._$$Callback);
__pro._$getPictureSetCountByCity=function(_pid,_tid,_cid,_cb){
var _count=this.__getDataInCache('ps_city_count_'+_pid+'_'+_tid+'_'+_cid);
if(_count!=undefined){
_cb(_pid,_tid,_cid,_count);
return;
}
this.__getPictureSetCountByCity._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'PictureSetBean','getPictureSetCountByCity',_pid,_tid,_cid,this.__getPictureSetCountByCity._$bind(this,_pid,_tid,_cid));
};
__pro.__getPictureSetCountByCity=function(_pid,_tid,_cid,_count){
if(_count){
_count=Math.min(_count,__maxCount);
this.__setDataInCache('ps_city_count_'+_pid+'_'+_tid+'_'+_cid,_count);
this.__setDataInCache('ps_city_list_'+_pid+'_'+_tid+'_'+_cid,new Array(_count));
}
this.__getPictureSetCountByCity._$fireCB(_pid,_tid,_cid,_count);
};
U.obj._$extend(__pro.__getPictureSetCountByCity,P.ut._$$Callback);
__pro._$getPictureSetCountByDirId=function(_id,_cb){
var _count=this.__getDataInCache('ps_dir_count_'+_id);
if(_count!=undefined){
_cb(_id,_count);
return;
}
this.__getPictureSetCountByDirId._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'PictureSetBean','getPictureSetCountByDirId',_id,this.__getPictureSetCountByDirId._$bind(this,_id));
};
__pro.__getPictureSetCountByDirId=function(_id,_count){
if(_count){
_count=Math.min(_count,__maxCount);
this.__setDataInCache('ps_dir_count_'+_id,_count);
this.__setDataInCache('ps_dir_list_'+_id,new Array(_count));
}
this.__getPictureSetCountByDirId._$fireCB(_id,_count);
};
U.obj._$extend(__pro.__getPictureSetCountByDirId,P.ut._$$Callback);
__pro._$getPictureSetListByDirId=function(_id,_offset,_limit,_cb){
var _list=this.__getListDataInCache('ps_dir_list_'+_id,_offset,_limit);
if(_list!=undefined){
_cb&&_cb(_id,_list);
return;
}
this.__getPictureSetListByDirId._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'PictureSetBean','getPictureSetListByDirId',_id,_offset,_limit,this.__getPictureSetListByDirId._$bind(this,_id,_offset,_limit));
};
__pro.__getPictureSetListByDirId=function(_id,_offset,_limit,_list){
if(_list){
U.arr._$forEach(_list,function(_ps,_index){
__completeURL(_ps);
});
this.__setListDataInCache('ps_dir_list_'+_id,_offset,_limit,_list);
}
this.__getPictureSetListByDirId._$fireCB(_id,_list);
};
U.obj._$extend(__pro.__getPictureSetListByDirId,P.ut._$$Callback);
__pro._$getPictureSetNewCountByDirId=function(_id,_cb){
var _count=this.__getDataInCache('ps_dir_new_count_'+_id);
if(_count!=undefined){
_cb(_id,_count);
return;
}
this.__getPictureSetNewCountByDirId._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'PictureSetBean','getPictureSetNewCountByDirId',_id,this.__getPictureSetNewCountByDirId._$bind(this,_id));
};
__pro.__getPictureSetNewCountByDirId=function(_id,_count){
E._$hideHint();
if(_count){
_count=Math.min(_count,__maxCount);
this.__setDataInCache('ps_dir_new_count_'+_id,_count);
this.__setDataInCache('ps_dir_new_list_'+_id,new Array(_count));
}
this.__getPictureSetNewCountByDirId._$fireCB(_id,_count);
};
U.obj._$extend(__pro.__getPictureSetNewCountByDirId,P.ut._$$Callback);
__pro._$getPictureSetNewListByDirId=function(_id,_offset,_limit,_cb){
var _list=this.__getListDataInCache('ps_dir_new_list_'+_id,_offset,_limit);
if(_list!=undefined){
_cb&&_cb(_id,_list);
return;
}
this.__getPictureSetNewListByDirId._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'PictureSetBean','getPictureSetNewListByDirId',_id,_offset,_limit,this.__getPictureSetNewListByDirId._$bind(this,_id,_offset,_limit));
};
__pro.__getPictureSetNewListByDirId=function(_id,_offset,_limit,_list){
E._$hideHint();
if(_list){
U.arr._$forEach(_list,function(_ps,_index){
__completeURL(_ps);
});
this.__setListDataInCache('ps_dir_new_list_'+_id,_offset,_limit,_list);
}
this.__getPictureSetNewListByDirId._$fireCB(_id,_list);
};
U.obj._$extend(__pro.__getPictureSetNewListByDirId,P.ut._$$Callback);
__pro._$getPictureSetRecommendCountByDirId=function(_id,_cb){
var _count=this.__getDataInCache('ps_dir_recom_count_'+_id);
if(_count!=undefined){
_cb(_id,_count);
return;
}
this.__getPictureSetRecommendCountByDirId._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'PictureSetBean','getPictureSetRecommendCountByDirId',_id,this.__getPictureSetRecommendCountByDirId._$bind(this,_id));
};
__pro.__getPictureSetRecommendCountByDirId=function(_id,_count){
E._$hideHint();
if(_count){
_count=Math.min(_count,__maxCount);
this.__setDataInCache('ps_dir_recom_count_'+_id,_count);
this.__setDataInCache('ps_dir_recom_list_'+_id,new Array(_count));
}
this.__getPictureSetRecommendCountByDirId._$fireCB(_id,_count);
};
U.obj._$extend(__pro.__getPictureSetRecommendCountByDirId,P.ut._$$Callback);
__pro._$getPictureSetRecommendListByDirId=function(_id,_offset,_limit,_cb){
var _list=this.__getListDataInCache('ps_dir_recom_list_'+_id,_offset,_limit);
if(_list!=undefined){
_cb&&_cb(_id,_list);
return;
}
this.__getPictureSetRecommendListByDirId._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'PictureSetBean','getPictureSetRecommendListByDirId',_id,_offset,_limit,this.__getPictureSetRecommendListByDirId._$bind(this,_id,_offset,_limit));
};
__pro.__getPictureSetRecommendListByDirId=function(_id,_offset,_limit,_list){
E._$hideHint();
if(_list){
U.arr._$forEach(_list,function(_ps,_index){
__completeURL(_ps);
});
this.__setListDataInCache('ps_dir_recom_list_'+_id,_offset,_limit,_list);
}
this.__getPictureSetRecommendListByDirId._$fireCB(_id,_list);
};
U.obj._$extend(__pro.__getPictureSetRecommendListByDirId,P.ut._$$Callback);
__pro._$getPictureSetListByCity=function(_pid,_tid,_cid,_offset,_limit,_cb){
var _list=this.__getListDataInCache('ps_city_list_'+_pid+'_'+_tid+'_'+_cid,_offset,_limit);
if(_list!=undefined){
_cb&&_cb(_pid,_tid,_cid,_list);
return;
}
this.__getPictureSetListByCity._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'PictureSetBean','getPictureSetListByCity',_pid,_tid,_cid,_offset,_limit,this.__getPictureSetListByCity._$bind(this,_pid,_tid,_cid,_offset,_limit));
};
__pro.__getPictureSetListByCity=function(_pid,_tid,_cid,_offset,_limit,_list){
U.arr._$forEach(_list,function(_ps,_index){
__completeURL(_ps);
});
this.__setListDataInCache('ps_city_list_'+_pid+'_'+_tid+'_'+_cid,_offset,_limit,_list);
this.__getPictureSetListByCity._$fireCB(_pid,_tid,_cid,_list);
};
U.obj._$extend(__pro.__getPictureSetListByCity,P.ut._$$Callback);
__pro._$createPicSet=function(_data){
J._$postDataByDWR(location.sdwr,'PictureSetBean','create',
_data.name,_data.desc||'',_data.pids,_data.descs,_data.cvid,_data.littleCoverDocId,_data.provinceCode,_data.citeCode||0,_data.cameraBrand||0,_data.cameraType||0,_data.cameraLens||0,_data.dirNamePath,_data.idPath,_data.dirType,_data.customName||'',_data.ext||'',_data.from||'',U.obj._$getData('diycameras'),_data.send2Weibo||false,
this._$dispatchEvent._$bind(this,'oncreatepicset'));
};
__pro._$updateMeta=function(_data){
J._$postDataByDWR(location.sdwr,'PictureSetBean','updateMeta',
_data.sid,_data.name,_data.desc||'',_data.pids,_data.descs,_data.cvid,_data.littleCoverDocId||0,_data.provinceCode,_data.citeCode||0,_data.cameraBrand||0,_data.cameraType||0,_data.cameraLens||0,_data.dirNamePath,_data.idPath,_data.dirType,_data.ext||'',U.obj._$getData('diycameras'),
this._$dispatchEvent._$bind(this,'onupdatemeta'));
};
__pro._$deletePicSet=function(_id,_flag,_type,_cb){
this.__deletePicSet._$addCB(_cb);
J._$postDataByDWR(location.sdwr,'PictureSetBean','updateDeleteFlag',_id,_flag,this.__deletePicSet._$bind(this,_id,_type));
};
__pro.__deletePicSet=function(_id,_type,_suc){
if(_suc){
var _data=this.__getDataInCache('share_picture_set');
if(U.arr._$isArray(_data)){
for(var i=0,_set;_set=_data[i];i++)
if(_set.id===_id){
_data.splice(i,1);
break;
}
this.__setDataInCache('share_picture_set',_data);
}
this.__delDataInCache('picturesetcate'+__ud.hostId);
}
this.__deletePicSet._$fireCB(_suc);
};
U.obj._$extend(__pro.__deletePicSet,P.ut._$$Callback);
__pro._$getPictureSet=function(_cb){
var _data=this.__getDataInCache('share_picture_set');
if(_data!=undefined||!__ud.pictureSetUrl){
_cb(_data);
return;
}
this.__getPictureSet._$addCB(_cb);
EJ._$loadScript(__ud.pictureSetUrl,this.__getPictureSet._$bind(this));
};
__pro.__getPictureSet=function(){
var _key='g_set$'+__ud.hostId,_d=window[_key];
U.obj._$delete(window,_key);
U.arr._$forEach(_d,function(_ps){
__completeURL(_ps);
});
if(U.arr._$isArray(_d)){
_d.sort(function(_x,_y){return _y.ctime-_x.ctime;});
this.__setDataInCache('share_picture_set',_d);
}
this.__getPictureSet._$fireCB(this.__getDataInCache('share_picture_set')||null);
};
U.obj._$extend(__pro.__getPictureSet,P.ut._$$Callback);
__pro._$getPictureSetByID=function(_id,_cb){
this.__getPictureSetByID._$addCB(_cb);
J._$postDataByDWR(location.sdwr,'PictureSetBean','getPictureSetByPicSetId',_id,this.__getPictureSetByID._$bind(this,_id));
};
__pro.__getPictureSetByID=function(_id,_data){
this.__getPictureSetByID._$fireCB(_id,__completeURL(_data));
};
U.obj._$extend(__pro.__getPictureSetByID,P.ut._$$Callback);
__pro._$getPraiseCount=function(_uid,_cb){
var _data=this.__getDataInCache('picture_set_praise_'+_uid);
if(_data!=undefined){
_cb(_data);
return;
}
this.__getPraiseCount._$addCB(_cb);
J._$postDataByDWR(location.sdwr,'PicSetInteractionBean','getPraiseCount',_uid,this.__getPraiseCount._$bind(this,_uid));
};
__pro.__getPraiseCount=function(_uid,_num){
if(_num!=undefined)
this.__setDataInCache('picture_set_praise_'+_uid,_num);
this.__getPraiseCount._$fireCB(_num||0);
};
U.obj._$extend(__pro.__getPraiseCount,P.ut._$$Callback);
__pro._$getPersonalLikeViewCount=function(_id,_ids,_uid,_cb){
this.__getLikeViewCount._$addCB(_cb);
J._$postDataByDWR(location.sdwr,'PicSetInteractionBean','getPersonalLikeViewCount',_ids,_uid,this.__getLikeViewCount._$bind(this,_id,_ids));
};
__pro._$getLikeViewCount=function(_id,_ids,_uids,_cb){
this.__getLikeViewCount._$addCB(_cb);
J._$postDataByDWR(location.sdwr,'PicSetInteractionBean','getLikeViewCount',_ids,_uids,this.__getLikeViewCount._$bind(this,_id,_ids));
};
__pro.__getLikeViewCount=function(_id,_ids,_lvs){
if(_lvs&&!_id){
var _ps=this.__getDataInCache('share_picture_set');
if(U.arr._$isArray(_ps))
U.arr._$forEach(_lvs,function(_lv,_index){
var _obj=_ps[_index],_lv=_lv||
{};
_obj.vcnt=_lv.vcnt;
_obj.lcnt=_lv.lcnt;
_obj.ccnt=_lv.ccnt;
_obj.build=true;
});
}
this.__getLikeViewCount._$fireCB(_ids,_lvs);
};
U.obj._$extend(__pro.__getLikeViewCount,P.ut._$$Callback);
__pro._$getPictureSetByPicSetIdStr=function(_ids,_id){
var _data=this.__getDataInCache('picture_set_photo_'+_id);
if(_data!=undefined){
this._$dispatchEvent('onpicturesetload',_id,_data);
return;
}
J._$postDataByDWR(location.sdwr,'PictureSetBean','getPictureSetByPicSetIdStr',_ids,this.__getPictureSetByPicSetIdStr._$bind(this,_id));
};
__pro.__getPictureSetByPicSetIdStr=function(_id,_ps){
_ps&&this.__setDataInCache('picture_set_photo_'+_id,_ps);
this._$dispatchEvent('onpicturesetload',_id,_ps||null);
};
__pro._$getPictureSets75PictureUrl=function(_offset,_limit,_cb){
this.__getPictureSets75PictureUrl._$addCB(_cb);
J._$postDataByDWR(location.sdwr,'PictureSetBean','getPictureSets75PictureUrl',_offset,_limit,this.__getPictureSets75PictureUrl._$bind(this));
};
__pro.__getPictureSets75PictureUrl=function(_data){
if(!_data){
this.__getPictureSets75PictureUrl._$fireCB(null);
return;
}
U.arr._$forEach(_data,function(_ps){
__completeURL(_ps);
});
if(U.arr._$isArray(_data)){
_data.sort(function(_x,_y){
return _y.ctime-_x.ctime;
});
}
this.__getPictureSets75PictureUrl._$fireCB(_data);
};
U.obj._$extend(__pro.__getPictureSets75PictureUrl,P.ut._$$Callback);
__pro._$getPictureSetsWithCover=function(_offset,_limit,_isFor240,_cb){
this.__getPictureSetsWithCover._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'PictureSetBean','getPictureSetsWithCover',_offset,_limit,_isFor240,this.__getPictureSetsWithCover._$bind(this));
};
__pro.__getPictureSetsWithCover=function(_data){
if(!_data){
this.__getPictureSetsWithCover._$fireCB(null);
return;
}
U.arr._$forEach(_data,function(_ps){
__completeURL(_ps);
});
this.__getPictureSetsWithCover._$fireCB(_data);
};
U.obj._$extend(__pro.__getPictureSetsWithCover,P.ut._$$Callback);
__pro._$getPictureSetCate=function(){
var _data=this.__getDataInCache('picturesetcate'+__ud.hostId);
if(_data)
this._$dispatchEvent('onpicturesetcateload',_data);
else
J._$postDataByDWR(location.sdwr,'PictureSetBean','getPictureSetCate',this.__getPictureSetCate._$bind(this));
};
__pro.__getPictureSetCate=function(_data){
if(!_data){
this._$dispatchEvent('onpicturesetcateload',null);
return;
}
this.__setDataInCache('picturesetcate'+__ud.hostId,_data);
this._$dispatchEvent('onpicturesetcateload',_data);
};
__pro._$getLovePhotoFolderList=function(_uid){
var _data=this.__getLovePhotoFolderListInCache(_uid);
if(_data!=undefined){
this._$dispatchEvent('onlovephotofolderlistget',_data);
return;
}
J._$postDataByDWR(location.sdwr,'LovePhotoFolderBean','getLovePhotoFolderListByUserIdOrderByCreateTimeDESC',_uid,0,1000,this.__getLovePhotoFolderList._$bind(this,_uid));
};
__pro.__getLovePhotoFolderList=function(_uid,_data){
if(!_data){
this._$dispatchEvent('onlovephotofolderlistget',null);
return;
}
if(_data!=undefined){
var _filed='lastFavTime';
_data.sort(function(_data0,_data1){
return __arraySortFunc(_filed,-1,_data0,_data1);
});
}
this.__addLovePhotoFolderListInCache(_uid,_data);
this._$dispatchEvent('onlovephotofolderlistget',_data);
};
__pro._$getLovePhotoFolderListForIndex=function(_uid){
var _data=this.__getLovePhotoFolderListInCache(_uid);
if(_data!=undefined){
this._$dispatchEvent('onlovephotofolderlistget',_data);
return;
}
J._$postDataByDWR(location.sdwr,'LovePhotoFolderBean','getLovePhotoFolderListByUserIdOrderByCreateTimeDESC',_uid,0,1000,this.__getLovePhotoFolderListForIndex._$bind(this,_uid));
};
__pro.__getLovePhotoFolderListForIndex=function(_uid,_data){
if(!_data){
this._$dispatchEvent('onlovephotofolderlistget',null);
return;
}
var favsort=this.__getDataInCache('love_photo_folder_list_favsort'+_uid);
if(favsort==undefined)
favsort=__ud.favSort;
this.__addLovePhotoFolderListInCache(_uid,_data);
_data=this.__sortLovePhotoFolderList(favsort);
this._$dispatchEvent('onlovephotofolderlistget',_data);
};
__pro.__addLovePhotoFolderListInCache=function(_uid,_data){
this.__setDataInCache('love_photo_folder_list'+_uid,_data);
};
__pro.__getLovePhotoFolderListInCache=function(_uid){
return this.__getDataInCache('love_photo_folder_list'+_uid);
};
__pro._$addLovePhotoFolder=function(_uid,_title){
J._$postDataByDWR(location.sdwr,'LovePhotoFolderBean','addLovePhotoFolder',_title,this.__addLovePhotoFolder._$bind(this,_uid));
};
__pro.__addLovePhotoFolder=function(_uid,_folder){
if(_folder){
if(_folder.errorType==-1){
alert('最多只能创建1000个收藏夹。');
return;
}
else
if(_folder.errorType==2)
alert('创建失败。收藏夹名或描述中含有不恰当的词汇。');
else{
var _data=this.__getLovePhotoFolderListInCache(_uid);
_data.unshift(_folder);
}
}
this._$dispatchEvent('onlovephotofolderadd',_data||null,_folder||null);
};
__pro.__addLovePhotoFolderInCache=function(_uid,_folder){
var _data=this.__getLovePhotoFolderListInCache(_uid);
_data.unshift(_folder);
var _sortType=this.__getDataInCache('love_photo_folder_list_favsort'+_uid);
if(_sortType==undefined)
_sortType=__ud.favSort;
_data=this.__sortLovePhotoFolderList(_sortType);
return _data;
};
__pro._$updateLovePhotoFolder=function(_uid,_id,_title){
J._$postDataByDWR(location.sdwr,'LovePhotoFolderBean','updateLovePhotoFolder',_id,_title,this.__updateLovePhotoFolder._$bind(this,_uid,_id,_title));
};
__pro.__updateLovePhotoFolder=function(_uid,_id,_title,_folder){
if(_folder&&_folder.errorType!=2)
this.__updateLovePhotoFolderInCache(_uid,_id,_title);
this._$dispatchEvent('onupdatelovephotofolder',_folder);
};
__pro.__updateLovePhotoFolderInCache=function(_uid,_id,_title){
var _data=this.__getLovePhotoFolderListInCache(_uid)||'',_folder;
if(!_data)
return null;
if(U.arr._$isArray(_data)){
for(var i=0,_folder;_folder=_data[i];i++)
if(_folder.id===_id){
_data[i].title=_title;
_folder=_data[i];
break;
}
this.__setDataInCache('love_photo_folder_list'+_uid,_data);
}
return _folder;
};
__pro._$deleteLovePhotoFolder=function(_id){
J._$postDataByDWR(location.sdwr,'LovePhotoFolderBean','deleteLovePhotoFolder',_id,this.__deleteLovePhotoFolder._$bind(this,_id));
};
__pro.__deleteLovePhotoFolder=function(_id,_suc){
if(_suc)
this.__deleteLovePhotoFolderInCache(_id);
this._$dispatchEvent('onlovephotofolderdelete',_suc||false);
};
__pro.__deleteLovePhotoFolderInCache=function(_id){
var _data=this.__getLovePhotoFolderListInCache(__ud.hostId);
if(U.arr._$isArray(_data)){
for(var i=0,_folder;_folder=_data[i];i++)
if(_folder.id===_id){
_data.splice(i,1);
break;
}
this.__setDataInCache('love_photo_folder_list'+__ud.hostId,_data);
}
};
__pro._$addLovePhotoUserItem=function(_folderId,_photoId,_setId,_picId,_authorId,_cmt,_send2NeWeibo,_event){
_authorId=_authorId==0?__ud.hostId:_authorId;
var _data=this.__getLovePhotoFolderListInCache(__ud.visitId);
if(_data!=undefined){
if(U.arr._$isArray(_data)){
for(var i=0,_item;_item=_data[i];i++)
if(_item.id==_folderId){
_data.splice(i,1);
_data.unshift(_item);
break;
}
}
this.__setDataInCache('love_photo_folder_list'+__ud.visitId,_data);
}
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','addLovePhotoUserItem',_folderId,_photoId,_setId,_picId,_authorId,_cmt||'',_event||false,_send2NeWeibo||false,false,this.__addLovePhotoUserItem._$bind(this));
};
__pro.__addLovePhotoUserItem=function(_photo){
if(!_photo){
alert('收藏相片失败，请稍后再试!');
return;
}
switch(_photo.errorType){
case-1:
alert('一个收藏夹最多存放1000张相片。请选择其他收藏夹或创建新收藏夹。');
break;
case 1:
alert('相片被作者设置访问权限，不能被收藏。');
break;
case 2:
alert('评论失败，评论中含有不恰当的词汇。');
break;
}
this._$dispatchEvent('onlovephotouseritemadd',_photo);
};
__pro._$sortLovePhotoFolderList=function(_uid,_type){
var _data=this.__getLovePhotoFolderListInCache(_uid);
J._$postDataByDWR(location.sdwr,'LovePhotoFolderBean','updateFavSeq',_type,this.__changeLovePhotoFolderSort._$bind(this,_type));
};
__pro.__changeLovePhotoFolderSort=function(_type,_suc){
if(!_suc){
this._$dispatchEvent('onlovephotofoldersortchange',false);
return;
}
var _data=this.__sortLovePhotoFolderList(_type);
if(_data!=undefined)
this.__setDataInCache('love_photo_folder_list'+__ud.hostId,_data);
this.__setDataInCache('love_photo_folder_list_favsort'+__ud.hostId,_type);
this._$dispatchEvent('onlovephotofoldersortchange',true,_type);
};
__pro.__sortLovePhotoFolderList=function(_type){
var _data=this.__getLovePhotoFolderListInCache(__ud.hostId);
if(!_data)
return;
var _filed,_flag;
switch(_type){
case 0:
case 1:
_filed='createTime';
_flag=0.5;
break;
case 2:
case 3:
_filed='lastFavTime';
_flag=2.5;
break;
case 4:
case 5:
_filed='title';
_flag=4.5;
break;
default:
_filed='createTime';
_flag=0.5;
break;
}
_flag=2*(_flag-_type);
_data.sort(function(_data0,_data1){
if(_filed=='title')
return __arrayLocalSortFunc(_filed,_flag,_data0,_data1);
return __arraySortFunc(_filed,_flag,_data0,_data1);
});
return _data;
};
var __arrayLocalSortFunc=function(_filed,_flag,_data0,_data1){
var _result=0;
if(_data0[_filed].localeCompare(_data1[_filed])!=0)
_result=_data0[_filed].localeCompare(_data1[_filed]);
return _result*_flag;
};
var __arraySortFunc=function(_filed,_flag,_data0,_data1){
var _result=0;
if(_filed=='lastFavTime'){
if(_data0[_filed]==0)
_data0[_filed]=_data0['updateTime'];
if(_data1[_filed]==0)
_data1[_filed]=_data0['updateTime'];
}
if(_data0[_filed]!=_data1[_filed])
_result=_data0[_filed]<_data1[_filed]?-1:1;
return _result*_flag;
};
__pro._$getLovePhotoUserItemList=function(_uid,_fid,_offset,_limit){
if(_offset==0&&_limit==1000){
var _data=this.__getDataInCache('love_photo_list'+_fid);
if(_data!=undefined){
this._$dispatchEvent('onlovephotouseritemlistget',_data||[]);
return;
}
}
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','getLovePhotoUserItemListByFolderId',_fid,_offset,_limit,this.__getLovePhotoUserItemList._$bind(this,_fid));
};
__pro.__getLovePhotoUserItemList=function(_fid,_data){
var _tmp1=[],_tmp2=[];
if(_data){
U.arr._$forEach(_data,function(_d){
_d.s=_d.imgStorageType;
_d.lurl=_d.imageUrl;
_d.murl=_d.imageLUrl;
if(_d.lurl=='noauth'){
_d.lurl=location.na130;
_d.murl=location.na250;
}
else
if(_d.lurl=='deled'){
_d.lurl=location.del130;
_d.murl=location.del250;
}
else
__completeURL(_d);
if(__ud.visitId!=""){
if(''+_d.hostid==__ud.visitId){
_tmp1.push(_d);
}
else
_tmp2.push(_d);
}
});
_tmp2=__ud.visitId!=""?_tmp1.concat(_tmp2):_data;
this.__setDataInCache('love_photo_list'+_fid,_tmp2);
}
this._$dispatchEvent('onlovephotouseritemlistget',_tmp2||[]);
};
__pro._$getLovePhotoFolder=function(_fid,_uid){
J._$postDataByDWR(location.sdwr,'LovePhotoFolderBean','getLovePhotoFolderById',_fid,_uid,this._$dispatchEvent._$bind(this,'onlovephotofolderget'));
};
__pro._$getTop5UserOfMaxLovedPhoto=function(_uid,_fid){
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','getTop5UserOfMaxLovedPhoto',_uid,_fid,this._$dispatchEvent._$bind(this,'ontop5userofmaxlovedphotoget'));
};
__pro._$getLovePhotoUserItem=function(_pid,_uid){
var _data=this.__getDataInCache('love_photo_'+_uid+'_'+_pid);
if(_data!=undefined){
this._$dispatchEvent('onlovephotoitemget',_data);
return;
}
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','getLovePhotoUserItemByPidAndUserId',_pid,_uid,this.__getLovePhotoItem._$bind(this,_uid,_pid));
};
__pro.__getLovePhotoItem=function(_uid,_pid,_data){
if(_data)
this.__setDataInCache('love_photo_'+_uid+'_'+_pid,_data);
this._$dispatchEvent('onlovephotoitemget',_data);
};
__pro._$favriteLovePhotoUserItemList=function(_ids,_fid,_event){
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','addLovePhotoUserItemList',_ids,_fid,'',_event||false,false,this.__favriteLovePhotoUserItemList._$bind(this,_ids.split(',').length));
};
__pro.__favriteLovePhotoUserItemList=function(_ocount,_fcount){
if(_fcount==-1){
alert('一个收藏夹最多存放1000张相片。请选择其他收藏夹或创建新收藏夹。');
return;
}
var _delta=_ocount-_fcount;
!!_delta?alert('收藏本页成功！其中'+_delta+'张相片已在你的收藏夹中，不能被重复收藏。'):alert('收藏本页成功！');
this._$dispatchEvent('onfavritelovephotouseritemlistadd');
};
__pro._$addLovePhotoFolderFavorite=function(_fid,_uid,_content){
J._$postDataByDWR(location.sdwr,'LovePhotoFolderFavoriteBean','addLovePhotoFolderFavorite',_fid,_uid,_content,this._$dispatchEvent._$bind(this,'onlovephotofolderfavoriteadd'));
};
__pro._$getLovePhotoFolderFavriteCount=function(_fid){
J._$postDataByDWR(location.sdwr,'LovePhotoFolderFavoriteBean','getLovePhotoFolderFavriteCountByLovePhotoFolderId',_fid,this._$dispatchEvent._$bind(this,'onlovephotofolderfavritecountget'));
};
__pro._$checkLovePhotoFolderFavorite=function(_fid){
J._$postDataByDWR(location.sdwr,'LovePhotoFolderFavoriteBean','isLovePhotoFolderFavoriteByUserIdAndFolderId',_fid,this._$dispatchEvent._$bind(this,'onlovephotofolderfavoritecheck'));
};
__pro._$isLovePhotoUserItem=function(_pid,_uid){
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','isLovePhotoUserItem',_pid,_uid,this._$dispatchEvent._$bind(this,'onislovephotouseritemget'));
};
__pro._$getLovePhotoUserItemTopUser20ByPhotoId=function(_pid){
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','getLovePhotoUserItemTopUser20ByPhotoId',_pid,this._$dispatchEvent._$bind(this,'onlovephotouseritemtopuserget'));
};
__pro._$getPictureSetBySetIdAndUId=function(_ids,_id,_uid){
var _data=this.__getDataInCache('picture_set_photo_'+_id);
if(_data!=undefined){
this._$dispatchEvent('onpicturesetload',_id,_data);
return;
}
J._$postDataByDWR(location.sdwr,'PictureSetBean','getPictureSetBySetIdAndUId',_ids,_uid,this.__getPictureSetBySidAndUid._$bind(this,_id));
};
__pro.__getPictureSetBySidAndUid=function(_id,_ps){
_ps&&this.__setDataInCache('picture_set_photo_'+_id,_ps);
if(_ps)
__completeURL(_ps);
this._$dispatchEvent('onpicturesetload',_id,_ps||null);
};
__pro._$getLovePhotoHostItemListBySetId=function(_sid,_uid,_offset,_limit,_cb){
this.__getLovePhotoHostItemListBySetId._$addCB(_cb);
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','getLovePhotoHostItemListBySetId',_sid,_uid,_offset,_limit,this.__getLovePhotoHostItemListBySetId._$bind(this,_sid,_offset,_limit));
};
__pro.__getLovePhotoHostItemListBySetId=function(_sid,_offset,_limit,_list){
this.__getLovePhotoHostItemListBySetId._$fireCB(_list);
};
U.obj._$extend(__pro.__getLovePhotoHostItemListBySetId,P.ut._$$Callback);
__pro._$getLovePhotoHostItemListByPhotoId=function(_pid,_uid,_offset,_limit,_cb){
var _list=this.__getListDataInCache('lp_list_'+_pid,_offset,_limit);
if(_list!=undefined){
_cb&&_cb(_list);
return;
}
this.__getLovePhotoHostItemListByPhotoId._$addCB(_cb);
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','getLovePhotoHostItemListByPhotoId',_pid,_uid,_offset,_limit,this.__getLovePhotoHostItemListByPhotoId._$bind(this,_pid,_offset,_limit));
};
__pro.__getLovePhotoHostItemListByPhotoId=function(_pid,_offset,_limit,_list){
if(_list)
this.__setListDataInCache('lp_list_'+_pid,_offset,_limit,_list);
this.__getLovePhotoHostItemListByPhotoId._$fireCB(_list);
};
U.obj._$extend(__pro.__getLovePhotoHostItemListByPhotoId,P.ut._$$Callback);
__pro._$getLovePhotoHostItemCountByPhotoId=function(_id,_uid){
J._$loadDataByDWRWithSync(location.sdwr,'LovePhotoItemBean','getLovePhotoHostItemCountByPhotoId',_id,_uid,this._$dispatchEvent._$bind(this,'onlovephotohostitemcountbyphotoidget'));
};
__pro._$getLovePhotoHostItemCountBySetId=function(_id,_uid){
J._$loadDataByDWRWithSync(location.sdwr,'LovePhotoItemBean','getLovePhotoHostItemCountBySetId',_id,_uid,this._$dispatchEvent._$bind(this,'onlovephotohostitemcountbysetidget'));
};
__pro._$deleteLovePhotoUserItem=function(_fid,_id,_uid){
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','deleteLovePhotoUserItem',_fid,_id,_uid,this.__deleteLovePhotoUserItem._$bind(this,_fid,_id));
};
__pro.__deleteLovePhotoUserItem=function(_fid,_id,_suc){
var _data=this.__getLovePhotoFolderListInCache(__ud.hostId);
if(_data!=undefined){
if(U.arr._$isArray(_data)){
for(var i=0,_folder;_folder=_data[i];i++){
if(_folder.id==_fid){
_data[i].count=_data[i].count-1;
break;
}
}
this.__setDataInCache('love_photo_folder_list'+__ud.hostId,_data);
}
}
var _data2=this.__getDataInCache('love_photo_list'+_fid);
if(_data2!=undefined){
if(U.arr._$isArray(_data2)){
for(var i=0,_item;_item=_data2[i];i++)
if(_item.photoId===_id){
_data2.splice(i,1);
break;
}
this.__setDataInCache('love_photo_list'+_fid,_data2);
}
}
this._$dispatchEvent('onlovephotouseritemdelete',_suc);
};
__pro._$movePhoto=function(_uid,_pid,_sid,_did){
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','moveLovePhoto',_uid,_pid,_sid,_did,this.__movePhoto._$bind(this,_sid,_did,_pid));
};
__pro.__movePhoto=function(_sid,_did,_pid,_suc){
var _data=this.__getLovePhotoFolderListInCache(__ud.hostId);
var _j=0;
if(U.arr._$isArray(_data)){
for(var i=0;i<_data.length;i++){
if(_data[i].id==_sid){
_data[i].count=_data[i].count-1;
_j=_j+1;
}
if(_data[i].id==_did){
_data[i].count=_data[i].count+1;
_j=_j+1;
}
if(_j>=2)
break;
}
this.__setDataInCache('love_photo_folder_list'+__ud.hostId,_data);
}
var _data2=this.__getDataInCache('love_photo_list'+_sid);
if(U.arr._$isArray(_data2)){
for(var i=0,_item;_item=_data2[i];i++)
if(_item.photoId===_pid){
_data2.splice(i,1);
break;
}
this.__setDataInCache('love_photo_list'+_sid,_data2);
}
this._$dispatchEvent('onphotomove',_suc);
};
__pro._$moveLovePhotos=function(_pids,_hids,_sid,_did){
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','batchmoveLovePhotos',_pids,_hids,_sid,_did,this.__moveLovePhotos._$bind(this,_sid,_did));
};
__pro.__moveLovePhotos=function(_sid,_did,_count){
var _data=this.__getLovePhotoFolderListInCache(__ud.hostId);
var _j=0;
if(U.arr._$isArray(_data)){
for(var i=0;i<_data.length;i++){
if(_data[i].id===_sid){
_data[i].count=_data[i].count-_count;
_j=_j+1;
}
if(_data[i].id==_did){
_data[i].count=_data[i].count+_count;
_j=_j+1;
}
if(_j>=2)
break;
}
this.__setDataInCache('love_photo_folder_list'+__ud.hostId,_data);
}
this._$dispatchEvent('onlovephotobatchmoves',_count);
};
__pro._$getPhotoByPhotoId=function(_pid,_uid){
J._$loadDataByDWR(location.sdwr,'LovePhotoItemBean','getPhotoByPhotoId',_pid,_uid,this._$dispatchEvent._$bind(this,'onphotoget'));
};
__pro._$getPhotoBySetId=function(_sid,_uid){
J._$loadDataByDWR(location.sdwr,'LovePhotoItemBean','getPhotoBySetId',_sid,_uid,this._$dispatchEvent._$bind(this,'onphotoget'));
};
__pro._$getPicSetLovePhotoBySetId=function(_sid){
J._$loadDataByDWR(location.sdwr,'LovePhotoItemBean','getPicSetLovePhotoBySetId',_sid,__ud.hostId,this._$dispatchEvent._$bind(this,'onpicsetlovephotoget'));
};
__pro._$getAuthorPraiseCount=function(_uid,_cb){
this.__getAuthorPraiseCount._$addCB(_cb);
J._$postDataByDWR(location.sdwr,'PicSetInteractionBean','getPraiseCount',_uid,this.__getAuthorPraiseCount._$bind(this,_uid));
};
__pro.__getAuthorPraiseCount=function(_uid,_num){
this.__getAuthorPraiseCount._$fireCB(_num||0);
};
U.obj._$extend(__pro.__getAuthorPraiseCount,P.ut._$$Callback);
__pro._$clickPraise=function(_hostid,_visitid){
J._$postDataByDWR(location.sdwr,'PicSetInteractionBean','clickPraise',_hostid,_visitid,this.__clickPraise._$bind(this,_hostid));
};
__pro.__clickPraise=function(_hostid,_num){
if(_num!=null)
this.__setDataInCache('picture_set_praise_'+_hostid,_num);
this._$dispatchEvent('onpraiseclick',_num);
};
__pro._$getLovePhotoUserItemCount=function(){
J._$loadDataByDWR(location.sdwr,'LovePhotoItemBean','getLovePhotoUserItemCountByUserId',__ud.hostId,this._$dispatchEvent._$bind(this,'onlovephotouseritemcountget'));
};
__pro._$deleteLovePhotos=function(_fid,_pids,_hids){
J._$postDataByDWR(location.sdwr,'LovePhotoItemBean','deleteLovePhotos',_fid,_pids,_hids,this.__deleteLovePhotos._$bind(this,_fid));
};
__pro.__deleteLovePhotos=function(_fid,_count){
var _data=this.__getLovePhotoFolderListInCache(__ud.hostId);
if(_data!=undefined){
if(U.arr._$isArray(_data)){
for(var i=0;i<_data.length;i++){
if(_data[i].id==_fid){
_data[i].count=_data[i].count-_count;
break;
}
}
this.__setDataInCache('love_photo_folder_list'+__ud.hostId,_data);
}
}
this._$dispatchEvent('onlovephotouseritembatchdelete',_count);
};
__pro._$getHuoDongPictureSetByPicSetIdStr=function(_ids){
J._$loadDataByDWR(location.hdwr,'HuoDongBean','getLastHuoDongByPicSetIds',_ids,this._$dispatchEvent._$bind(this,'onhuodongpicsetget'));
};
})();
(function(){
var p=P('P.ut'),
__proCache,
__ud=window.UD;
p._$$TrackCache=C();
__proCache=p._$$TrackCache._$extend(P.ut._$$ECache);
__proCache.__addUserFollowInCache=function(_follow){
if(!U.obj._$isObject(_follow))return;
var _pid=_follow.parentID,_cid=_follow.ChildID;
var _count=this.__getDataInCache('following_count_'+_pid),_list;
if(_count>0){
this.__setDataInCache('following_count_'+_pid,_count+1);
_list=this.__getDataInCache('following_list_'+_pid);
if(U.arr._$isArray(_list)){
_list.unshift(_follow);
this.__setDataInCache('following_list_'+_pid,_list);
}
}
_count=this.__getDataInCache('followed_count_'+_cid);
if(_count>0){
this.__setDataInCache('followed_count_'+_cid,_count+1);
_list=this.__getDataInCache('followed_list_'+_cid);
if(U.arr._$isArray(_list)){
_list.unshift(_follow);
this.__setDataInCache('followed_list_'+_cid,_list);
}
}
};
__proCache.__clearUserFollowInCache=function(){
this.__delDataInCache('following_count_'+__ud.hostId);
this.__delDataInCache('following_list_'+__ud.hostId);
}
__proCache.__deleteUserFollowInCache=function(_follow){
if(!U.obj._$isObject(_follow))return;
var _pid=_follow.parentID,_cid=_follow.ChildID;
var _count=this.__getDataInCache('following_count_'+_pid),_list;
if(_count>0){
this.__setDataInCache('following_count_'+_pid,_count-1);
_list=this.__getDataInCache('following_list_'+_pid);
if(U.arr._$isArray(_list)){
for(var i=0,_f;_f=_list[i];i++){
if(_f.id==_follow.id){
_list.splice(i,1);
break;
}
}
this.__setDataInCache('following_list_'+_pid,_list);
}
}
_count=this.__getDataInCache('followed_count_'+_cid);
if(_count>0){
this.__setDataInCache('followed_count_'+_cid,_count-1);
_list=this.__getDataInCache('followed_list_'+_cid);
if(U.arr._$isArray(_list)){
for(var i=0,_f;_f=_list[i];i++){
if(_f.id==_follow.id){
_list.splice(i,1);
break;
}
}
this.__setDataInCache('followed_list_'+_cid,_list);
}
}
};
__proCache._$addUserFollowing=function(_id){
J._$postDataByDWR(location.sdwr,'UserFollowerBean','addUserFollowing',_id,this.__addUserFollowing._$bind(this));
};
__proCache.__addUserFollowing=function(_follow){
_follow&&this.__addUserFollowInCache(_follow);
this._$dispatchEvent('onadduserfollowing',_follow?true:false);
};
__proCache._$deleteUserFollowing=function(_userId){
J._$postDataByDWR(location.sdwr,'UserFollowerBean','deleteUserFollowing',_userId,this.__deleteUserFollowing._$bind(this));
};
__proCache.__deleteUserFollowing=function(_follow){
_follow&&this.__deleteUserFollowInCache(_follow);
this._$dispatchEvent('ondeleteuserfollowing',_follow?true:false);
};
__proCache._$followUserForPhoto=function(_vc){
J._$postDataByDWR(location.bdwrnew,'UserFollowBeanNew','followUserForPhoto',_vc,false,this.__followUserForPhoto._$bind(this));
};
__proCache.__followUserForPhoto=function(_follow){
if(_follow=="valCode")UD.showCaptcha=true;
this._$dispatchEvent('onadduserfollowing',_follow);
};
__proCache._$cancelFollowUserForPhoto=function(_username){
if(_username==undefined||_username==""){
J._$postDataByDWR(location.bdwrnew,'UserFollowBeanNew','cancelFollowUserForPhoto',this.__cancelFollowUserForPhoto._$bind(this));
}else{
var dwrurl=location.bar+"/"+_username+"/dwr";
J._$postDataByDWR(dwrurl,'UserFollowBeanNew','cancelFollowUserForPhoto',this.__cancelFollowUserForPhoto._$bind(this));
}
};
__proCache.__cancelFollowUserForPhoto=function(_follow){
if(_follow){
this.__clearUserFollowInCache();
}
this._$dispatchEvent('ondeleteuserfollowing',_follow?true:false);
};
__proCache._$getUserFollowingCountByUserId=function(_id,_cb){
var _count=this.__getDataInCache('following_count_'+_id);
if(_count!=undefined){
_cb(_id,_count);
return;
}
this.__getUserFollowingCountByUserId._$addCB(_cb);
J._$loadDataByDWRWithSync(location.sdwr,'UserFollowerBean','getUserFollowingCountByUserId',_id,this.__getUserFollowingCountByUserId._$bind(this,_id));
};
__proCache.__getUserFollowingCountByUserId=function(_id,_count){
if(_count){
this.__setDataInCache('following_count_'+_id,_count);
this.__setDataInCache('following_list_'+_id,new Array(Number(_count)));
}
this.__getUserFollowingCountByUserId._$fireCB(_id,_count||0);
};
U.obj._$extend(__proCache.__getUserFollowingCountByUserId,P.ut._$$Callback);
__proCache._$getUserFollowingList=function(_id,_limit,_offset,_countcb,_listcb){
this.__getUserFollowingCountByUserId._$addCB(_countcb);
this.__getUserFollowingList._$addCB(_listcb);
var _needCount=false,_count=this.__getDataInCache('following_count_'+_id);
if(_count==undefined){
_needCount=true;
}
this._$getUserFollowingCountByUserId(_id,F);
var _list=this.__getListDataInCache('following_list_'+_id,_offset,_limit);
if(_list!=undefined&&_list.length>0){
_listcb(_id,_list);
return;
}
J._$loadDataByDWR(location.sdwr,'UserFollowerBean','getUserFollowingList',_id,_limit,_offset,this.__getUserFollowingList._$bind(this,_id,_offset,_limit,_needCount));
};
__proCache.__getUserFollowingList=function(_id,_offset,_limit,_needCount,_list){
if(_needCount){
var _count=_list.length;
this.__getUserFollowingCountByUserId(_id,_count);
}
this.__setListDataInCache('following_list_'+_id,_offset,_limit,_list);
this.__getUserFollowingList._$fireCB(_id,_list);
};
U.obj._$extend(__proCache.__getUserFollowingList,P.ut._$$Callback);
__proCache._$getUserFollowedCountByUserId=function(_id,_cb){
var _count=this.__getDataInCache('followed_count_'+_id);
if(_count!=undefined){
_cb(_id,_count);
return;
}
this.__getUserFollowedCountByUserId._$addCB(_cb);
J._$loadDataByDWRWithSync(location.sdwr,'UserFollowerBean','getUserFollowedCountByUserId',_id,this.__getUserFollowedCountByUserId._$bind(this,_id));
};
__proCache.__getUserFollowedCountByUserId=function(_id,_count){
if(_count){
this.__setDataInCache('followed_count_'+_id,_count);
this.__setDataInCache('followed_list_'+_id,new Array(Number(_count)));
}
this.__getUserFollowedCountByUserId._$fireCB(_id,_count);
};
U.obj._$extend(__proCache.__getUserFollowedCountByUserId,P.ut._$$Callback);
__proCache._$getUserFollowedList=function(_id,_limit,_offset,_coutcb,_listcb){
this.__getUserFollowedCountByUserId._$addCB(_coutcb);
this.__getUserFollowedList._$addCB(_listcb);
var _needCount=false,_count=this.__getDataInCache('followed_count_'+_id);
if(_count==undefined){
_needCount=true;
}
this._$getUserFollowedCountByUserId(_id,F);
var _list=this.__getListDataInCache('followed_list_'+_id,_offset,_limit);
if(_list!=undefined&&_list.length>0){
_listcb(_id,_list);
return;
}
J._$loadDataByDWR(location.sdwr,'UserFollowerBean','getUserFollowedList',_id,_limit,_offset,this.__getUserFollowedList._$bind(this,_id,_offset,_limit,_needCount));
};
__proCache.__getUserFollowedList=function(_id,_offset,_limit,_needCount,_list){
if(_needCount){
var _count=_list.length;
this.__getUserFollowedCountByUserId(_id,_count);
}
this.__setListDataInCache('followed_list_'+_id,_offset,_limit,_list);
this.__getUserFollowedList._$fireCB(_id,_list);
};
U.obj._$extend(__proCache.__getUserFollowedList,P.ut._$$Callback);
__proCache._$getRecommendUser=function(_cb){
var _data=this.__getDataInCache('recommend_user');
if(_data!=undefined){
_cb(_data);
return;
}
this.__getRecommendUser._$addCB(_cb);
J._$loadDataByDWR(location.sdwr,'UserFollowerBean','getRecommendUser',this.__getRecommendUser._$bind(this));
};
__proCache.__getRecommendUser=function(_data){
if(_data)
this.__setDataInCache('recommend_user',_data);
this.__getRecommendUser._$fireCB(_data);
};
U.obj._$extend(__proCache.__getRecommendUser,P.ut._$$Callback);
__proCache._$getFollowEventCount=function(_id,_cb){
var _count=this.__getDataInCache('follow_event_count_'+_id);
if(_count!=undefined){
_cb(_id,_count);
return;
}
this.__getFollowEventCount._$addCB(_cb);
J._$loadDataByDWR(location.pdwr,'UserFollowerBean','getFollowEventCount',_id,this.__getFollowEventCount._$bind(this,_id));
};
__proCache.__getFollowEventCount=function(_id,_count){
if(_count){
this.__setDataInCache('follow_event_count_'+_id,_count);
this.__setDataInCache('follow_event_list_'+_id,new Array(_count));
}
this.__getFollowEventCount._$fireCB(_id,_count);
};
U.obj._$extend(__proCache.__getFollowEventCount,P.ut._$$Callback);
__proCache._$getFollowEventList=function(_id,_offset,_limit,_cb){
var _list=this.__getListDataInCache('follow_event_list_'+_id,_offset,_limit);
if(_list!=undefined){
_cb(_id,_list);
return;
}
this.__getFollowEventList._$addCB(_cb);
J._$loadDataByDWR(location.pdwr,'UserFollowerBean','getFollowEventList',_id,_offset,_limit,this.__getFollowEventList._$bind(this,_id,_offset,_limit));
};
__proCache.__getFollowEventList=function(_id,_offset,_limit,_list){
this.__setListDataInCache('follow_event_list_'+_id,_offset,_limit,_list);
this.__getFollowEventList._$fireCB(_id,_list);
};
U.obj._$extend(__proCache.__getFollowEventList,P.ut._$$Callback);
__proCache._$getUserRecvMsgCount=function(){
var _data=this.__getDataInCache('user_recv_count_'+__ud.hostId);
if(_data!=undefined){
this._$dispatchEvent('getuserrecvmsgcount',_data);
return;
}
J._$postDataByDWR(location.mdwr,'MessageBean','getUserRecvMsgCount',this.__getUserRecvMsgCount._$bind(this));
};
__proCache.__getUserRecvMsgCount=function(_count){
if(!_count){
this._$dispatchEvent('getuserrecvmsgcount',0);
return;
}
this.__setDataInCache('user_recv_count_'+__ud.hostId,_count);
this._$dispatchEvent('getuserrecvmsgcount',_count);
};
__proCache._$getUserSentMsgCount=function(){
var _data=this.__getDataInCache('user_sent_count_'+__ud.hostId);
if(_data!=undefined){
if(_data==this.__data){
this._$dispatchEvent('getusersentmsgcount',_data,false);
return;
}
}
J._$postDataByDWR(location.mdwr,'MessageBean','getUserSentMsgCount',this.__getUserSentMsgCount._$bind(this));
};
__proCache.__getUserSentMsgCount=function(_count){
if(!_count){
if(_count==0){
this.__data=0;
this.__setDataInCache('user_sent_count_'+__ud.hostId,0);
}
this._$dispatchEvent('getusersentmsgcount',0,false);
return;
}
this.__data=_count;
this.__setDataInCache('user_sent_count_'+__ud.hostId,_count);
this._$dispatchEvent('getusersentmsgcount',_count,true);
};
__proCache._$getMsgHistoryCount=function(_name){
var _data=this.__getDataInCache('msg_his_count_'+_name);
if(_data!=undefined){
this._$dispatchEvent('getmsghistorycount',_name,_data);
return;
}
J._$postDataByDWR(location.mdwr,'AlbumMsgBean','getMsgHistoryCountByAlbum',_name,this.__getMsgHistoryCount._$bind(this,_name));
};
__proCache.__getMsgHistoryCount=function(_name,_count){
if(!_count){
this._$dispatchEvent('getmsghistorycount',_name,0);
return;
}
this.__setDataInCache('msg_his_count_'+_name,_count);
this._$dispatchEvent('getmsghistorycount',_name,_count);
};
__proCache._$setLatestMSGCount=function(_count){
J._$postDataByDWR(location.pdwr,'IndexSessionBean','setLatestMSGCount',false,_count);
};
__proCache._$getLatestMSGCount=function(){
J._$postDataByDWR(location.pdwr,'IndexSessionBean','getLatestMSGCount',false,this.__getLatestMSGCount._$bind(this));
};
__proCache.__getLatestMSGCount=function(_count){
this._$dispatchEvent('onGetLatestMSGCount',_count);
};
__proCache._$getNewMsgCount=function(_id,_name,_cb){
var _data=this.__getDataInCache('new_msg_count_'+Math.abs(_id));
if(_data!=undefined){
_cb(_data);
return;
}
this.__getNewMsgCount._$addCB(_cb);
J._$postDataByDWR(location.mdwr,'PollingBean','pollingNewMsgByAlbum',_name,this.__getNewMsgCount._$bind(this,Math.abs(_id)));
};
__proCache.__getNewMsgCount=function(_id,_data){
if(_data)
this.__setDataInCache('new_msg_count_'+_id,_data);
this.__getNewMsgCount._$fireCB(_data);
};
U.obj._$extend(__proCache.__getNewMsgCount,P.ut._$$Callback);
__proCache.__setNewMsgCount=function(_key0,_key1,_value){
var _data=this.__getDataInCache(_key0);
_data[_key1]=_value;
};
})();

(function(){
var s=P('P.s'),
__pro;
s._$$Module=C();
U.cls._$augment(s._$$Module,P.ut._$$Single,true);
__pro=s._$$Module.prototype;
__pro._$initialize=function(_options){
_options=_options||
{};
this.id=_options.id;
this.__children__=[];
this.__event=new ntes.util._$$Event();
this.__body=this.__getXNode();
this.__initialize(_options);
};
__pro.__initialize=F;
__pro._$getBody=function(){
return this.__body;
};
__pro._$getID=function(){
return this.id;
};
__pro._$reset=function(){
var _event=U.str._$toHash(location.hash);
this.__reset(_event);
if(!this.__build){
var _c;
for(var _p in this.register){
_c=this.register[_p];
!U.fun._$isFunction(_c)&&this._$appendChild(this._$getChild(_c.id),_c.pnode);
}
delete this.register;
this.__build=true;
}
for(var i=0,_m;_m=this.__children__[i];i++)
_m._$reset();
};
__pro.__reset=F;
__pro.__getXNode=function(){
var _node=document.cloneElement('div');
_node.innerHTML='<span>test</span>';
return _node;
};
__pro._$registerChild=function(_cfg){
if(U.arr._$isArray(_cfg)){
for(var i=0,_c;_c=_cfg[i];i++)
this._$registerChild(_c);
}
else
if(U.obj._$isObject(_cfg)){
this.register=this.register||{};
_cfg.id=_cfg.id||U._$randNumberString();
this.register[_cfg.id]=_cfg;
}
};
__pro._$getChild=function(_id){
if(_id==undefined)return null;
var _arr=U.arr._$filter(this.__children__,function(_c){
return _c.id==_id;
});
var _mdl=_arr[0];
if(!_mdl){
var _c=this.register&&this.register[_id];
if(!_c)return null;
_mdl=_c.cls._$getInstance({id:_id});
}
return _mdl;
};
__pro._$setParent=function(_parent){
this.parent=_parent;
};
__pro._$getParent=function(){
return this.parent;
};
__pro._$appendChild=function(_child,_pnode){
if(U.obj._$isObject(_child)){
_child._$setParent(this);
this.__children__.push(_child);
}
_pnode=E._$getElement(_pnode);
var _body=_child._$getBody();
if(_pnode&&_body&&_pnode!=_body.parentNode)
_pnode.appendChild(_child._$getBody());
_pnode&&(_pnode.style.display='');
return this;
};
__pro._$addEvent=function(_type,_handler){
this.__event._$addEvent(_type,_handler);
};
__pro._$dispatchEvent=function(){
this.__event._$dispatchEvent.apply(this.__event,arguments);
var _parent=this._$getParent();
_parent&&_parent._$dispatchEvent.apply(_parent,arguments);
};
__pro._$show=function(){
this.__body.style.display='';
};
__pro._$hide=function(){
this.__body.style.display='none';
};
})();

(function(){
var s=P('P.s'),
__pro,
__supro;
s._$$CnModule=C();
__pro=s._$$CnModule._$extend(s._$$Module,true);
__supro=s._$$Module.prototype;
__pro._$reset=function(){
var _event=U.str._$toHash(location.hash);
var _cid=this.__getCID(_event);
if(_cid==undefined)return;
var _child=this._$getChild(_cid);
if(_cid!=this.__getCurCID()){
this._$removeChild(this._$getChild(this.__getCurCID()));
this._$appendChild(_child);
}
_child._$show();
_child._$reset();
this.__curCID=_cid;
};
__pro._$appendChild=function(_child){
__supro._$appendChild.call(this,_child,this._$getBody());
};
__pro.__getCID=F;
__pro.__getCurCID=function(){
return this.__curCID;
};
__pro._$removeChild=function(_child,_hook){
if(_child&&(_child.parent==this)){
_child._$getBody().style.display='none';
delete _child.parent;
var _index=U.arr._$indexOf(this.__children__,_child);
if(_index!=-1)
this.__children__.splice(_index,1);
}
return this;
};
})();

(function(){
var p=P('np.w'),
__proModule;
p._$$Module=C();
__proModule=p._$$Module._$extend(P(N.ut)._$$Event);
__proModule._$initialize=function(_node){
this._$super();
this.__body=E._$getElement(_node);
var _ntmp=E._$getElementsByClassName(this.__body,'np-jsc');
this.__case=_ntmp[0];
var _node=E._$getChildElements(this.__case,'np-init');
this.__initFrame(_node&&_node[0]||null);
};
__proModule._$getId=function(){
return this.__body.id;
};
__proModule._$getBody=function(){
return this.__body;
};
__proModule._$getContainer=function(){
return this.__case;
};
__proModule._$setContent=function(_content){
this.__case.innerHTML=_content||'&nbsp;';
};
__proModule.__initFrame=function(_node){
var _html,_script;
if(!!_node){
var _ntmp=_node.getElementsByTagName('textarea')||[];
for(var i=_ntmp.length-1,_type,_item;i>=0;i--){
_item=_ntmp[i];
_type=U._$trim(_item.name.toLowerCase());
if(_type=='js'){
_script=_item.value||'';
continue;
}
if(_type=='html'){
_html=_item.value||'';
continue;
}
if(!_item.id)
continue;
if(_type=='jst'){
E._$addHtmlTemplate(_item);
continue;
}
if(_type=='txt'){
U.obj._$setData(_item.id,_item.value||'');
continue;
}
if(_type=='ntp'){
E._$addNodeTemplate(_item.value||'',_item.id);
continue;
}
}
E._$removeElement(_node);
}
this.__execScript(_script);
if(!!_html)
this._$setContent(_html);
};
__proModule.__execScript=function(_source){
_source=U._$trim(_source||'');
if(!!_source){
try{
new Function(_source).call(this);
}
catch(e){}
}
};
})();

(function(){
var p=P('P.ui');
p._$$UIBase=C();
var __pro=p._$$UIBase.prototype;
__pro._$initialize=function(_param){
this.__body=this.__getXNode();
this.__initialize(_param);
};
__pro.__initialize=F;
__pro.__getXNode=F;
__pro._$getBody=function(){
return this.__body;
};
__pro._$show=function(){
this.__body&&(this.__body.style.display='');
this.__visible=true;
return this;
};
__pro._$hide=function(){
this.__body&&(this.__body.style.display='none');
this.__visible=false;
return this;
};
__pro._$visible=function(){
return this.__visible;
};
__pro._$toggle=function(){
this.__visible?this._$hide():this._$show();
};
__pro._$appendTo=function(_parent,_before){
_parent=E._$getElement(_parent);
if(_parent)
!_before?_parent.appendChild(this.__body):_parent.insertAdjacentElement('afterBegin',this.__body);
this.__pnode=_parent;
return this;
};
})();

(function(){
var p=P('P.ui'),
__xhtml='<div class="w-cxt-input"><input name="username" type="text" class="txt js-txt fc1 name-hint bdc6 js-init" autocomplete="off" value="如 name@example.com"/><div style="display: none;" class="w-cxt-input-layer js-layer js-con-im bdc6 bgc99"></div></div>',
__reg=/(\w[-.\w]*)@([-a-z0-9]+)(\.[-a-z0-9]+)*\.[a-z]+/,
__posts=['@163.com','@126.com','@yeah.net','@vip.163.com','@vip.126.com','@popo.163.com','@188.com','@qq.com','@yahoo.com.cn','@sina.com'];
var __getList=function(_value){
var _list=[];
if(_value){
var _arr=/([^@]*)(.*)/.exec(_value);
var _pre=_arr[1],_post=_arr[2];
U.arr._$forEach(__posts,function(_pt){
if(_pt.indexOf(_post)!=-1){
_list.push(_pre+_pt);
}
});
if(!_list.length){
_list.push(_value);
}
}
return _list;
};
var __getUserName=function(_email){
if(!_email)return;
return _email.replace(__reg,function(){
var _arr=[].slice.call(arguments);
__account=_arr[1]
var _type=_arr[2];
var _dom=_arr[3];
switch(_type){
case'163':
return __account;
break;
case'popo':
case'vip':
if(_dom=='.163')
return __account+'.'+_type;
else if(_dom=='.126')
return __account+'@vip.126.com';
break;
case'188':
case'126':
case'yeah':
return __account+'@'+_type;
default:
return _email;
}
});
};
p._$$UNInput=C();
var __pro=p._$$UNInput._$extend(p._$$UIBase);
__pro.__initialize=function(_param){
_param=_param||
{};
this._$appendTo(_param.parent);
E._$addClassName(this.__body,_param.classname);
this.__onSuccess=_param.onsuccess;
this.__onFocus=_param.onfocus;
this.__onBlur=_param.onblur;
V._$addEvent(this.__txt,B._$ISIE?'propertychange':'input',this.__onChange._$bind(this));
V._$addEvent(document,'click',this.__onClick._$bind(this));
V._$addEvent(this.__txt,'click',function(_event){
V._$stopBubble(_event);
});
V._$addEvent(this.__txt,'keydown',this.__onKeyDown._$bind(this));
V._$addEvent(this.__txt,'keypress',this.__onKeyPress._$bind(this));
V._$addEvent(this.__txt,'focus',this.__onFocusText._$bind(this));
V._$addEvent(this.__txt,'blur',this.__onBlurText._$bind(this));
};
__pro.__resetList=function(_list){
if(!_list||!_list.length)
_list=[];
this.__list=_list;
for(var i=0,_cs=this.__context.childNodes,len=_cs.length;i<len;i++)
this.__context.removeChild(this.__context.firstChild);
var _frag=document.createDocumentFragment(),_tpl=document.createElement('div'),_div;
_div=_tpl.cloneNode(false);
_div.className='hint';
_div.innerText='请选择或继续输入...';
_div.onclick=this.__select._$bind(this,this.__context.__curIndex);
_frag.appendChild(_div);
for(var i=0,l=_list.length;i<l;i++){
_div=_tpl.cloneNode(false);
_div.innerText=_list[i];
if(i==0){
E._$addClassName(_div,'js-cur');
this.__context.__curIndex=i;
}
_div.onmouseover=this.__onMouseOver._$bind(this,i);
_frag.appendChild(_div);
}
this.__count=0;
this.__context.appendChild(_frag);
};
__pro.__onMouseOver=function(_index){
if(!this.__count++)
return;
E._$delClassName(this.__context.getElementsByTagName('div')[this.__context.__curIndex+1],'js-cur');
this.__context.__curIndex=_index;
E._$addClassName(this.__context.getElementsByTagName('div')[this.__context.__curIndex+1],'js-cur');
};
__pro.__onClick=function(){
if(this.__context.__curIndex!=undefined&&this.__context.style.display!='none')
this.__select(this.__context.__curIndex);
};
__pro.__onFocusText=function(){
if(this.__txt.value=='如 name@example.com'){
this.__txt.value='';
E._$delClassName(this.__txt,'js-init');
E._$delClassName(this.__txt,'name-hint');
}
E._$addClassName(this.__txt,'js-focus');
this.__onFocus&&this.__onFocus();
};
__pro.__onBlurText=function(){
if(this.__txt.value==''){
E._$addClassName(this.__txt,'js-init');
this.__txt.value='如 name@example.com';
E._$addClassName(this.__txt,'name-hint');
}
E._$delClassName(this.__txt,'js-focus');
this.__onBlur&&this.__onBlur();
};
__pro.__select=function(_index){
if(_index==undefined||this.__context.style.display=='none')
return;
this.__txt.value=this.__list[_index];
this.__context.style.display='none';
this.__onSuccess&&this.__onSuccess();
};
__pro.__onChange=function(){
this.__onChange.__oldValue=this.__onChange.__oldValue||'如 name@example.com',_value=this.__txt.value;
if(_value==this.__onChange.__oldValue)return;
this.__onChange.__oldValue=_value;
if(_value==''){
this.__context.style.display='none';
return;
}
this.__onListChange(__getList(this.__txt.value));
};
__pro.__onKeyDown=function(_event){
switch(_event.keyCode){
case 9:
this.__select(this.__context.__curIndex);
V._$stop(_event);
break;
case 38:
if(this.__context.__curIndex>0){
E._$delClassName(this.__context.getElementsByTagName('div')[this.__context.__curIndex+1],'js-cur');
this.__context.__curIndex--;
E._$addClassName(this.__context.getElementsByTagName('div')[this.__context.__curIndex+1],'js-cur');
}
break;
case 40:
if(!this.__list||!this.__list.length)return;
if(this.__context.__curIndex<this.__list.length-1){
E._$delClassName(this.__context.getElementsByTagName('div')[this.__context.__curIndex+1],'js-cur');
this.__context.__curIndex++;
E._$addClassName(this.__context.getElementsByTagName('div')[this.__context.__curIndex+1],'js-cur');
}
break;
}
};
__pro.__onKeyPress=function(_event){
switch(_event.keyCode){
case 13:
this.__select(this.__context.__curIndex);
V._$stop(_event);
break;
}
};
__pro.__onListChange=function(_list){
if(!_list||!_list.length)
return;
this.__resetList(_list);
this.__context.style.display='';
};
__pro._$getAccount=function(){
return __getUserName(this.__txt.value);
};
__pro._$getValue=function(){
return this.__txt.value;
};
__pro._$setValue=function(_value){
this.__txt.value=_value||'';
this.__select(this.__context.__curIndex);
};
__pro._$focus=function(){
};
__pro._$addTextChange=function(_fun){
U.dom._$addTextChange(this,__txt,_fun);
};
__pro.__getXNode=function(){
var _nd=E._$parseElement(__xhtml);
this.__txt=E._$getElementsByClassName(_nd,'js-txt')[0];
this.__context=E._$getElementsByClassName(_nd,'js-layer')[0];
return _nd;
};
})();

(function(){
var p=P('P.ui'),
__pro,
__supro,
__xhtml='<span class="w-posting iblock"><img src="http://r.ph.126.net/photo/image/acting.gif"/><span class="fc2 fs0"></span></span>';
p._$$Posting=C();
__pro=p._$$Posting._$extend(p._$$UIBase);
__supro=p._$$UIBase.prototype;
U.cls._$augment(p._$$Posting,P.ut._$$Single,true);
__pro._$reset=function(_opt){
_opt=_opt||{};
this.__emsg.innerText=_opt.msg||'';
this.__nocover=_opt.nocover;
var _pnode=_opt.pnode;
this.__body.style.left=_pnode?'':document.documentElement.scrollLeft+(document.body.offsetWidth-100)/2+'px';
this.__body.style.top=_pnode?'':document.documentElement.scrollTop+180+'px';
_pnode=_pnode||document.body;
this._$appendTo(_pnode);
return this;
};
__pro._$show=function(){
!this.__nocover&&E._$showCover();
__supro._$show.call(this);
return this;
};
__pro._$hide=function(){
__supro._$hide.call(this);
!this.__nocover&&E._$hideCover();
return this;
};
__pro.__getXNode=function(){
var _nd=E._$parseElement(__xhtml);
this.__emsg=_nd.getElementsByTagName('span')[0];
return _nd;
};
})();
(function(){
var p=P('P.ui'),
__pro,
__xhtml='<div class="p-lay bgc4">\
       <div class="lay-wrap bdc6 bgc99">\
      <div class="ttl"><span class="close js-close icn0 icn0-10">&nbsp;</span><div class="fc1 js-cttl"></div></div>\
      <div class="content js-cnt"></div>\
       </div>\
      </div>';
p._$$WBase=C();
__pro=p._$$WBase._$extend(p._$$UIBase);
U.cls._$augment(p._$$WBase,P.ut._$$Single,true);
__pro._$initialize=function(_param){
_param=_param||
{};
this.__body=this.__getXNode();
E._$addClassName(this.__body,_param.classname);
V._$addEvent(this.__eclose,'click',this.__onClickClose._$bind(this));
var _nd=this.__getContent();
this.__ecnt.appendChild(_nd);
this.__content=_nd;
this._$show();
this.__initialize(_param);
};
__pro.__initialize=F;
__pro.__onClickClose=function(){
this._$hide();
this.__onCancel&&this.__onCancel();
};
__pro._$show=function(){
if(this.__body){
E._$showCover();
this._$appendTo(document.lbody);
document.lbody.style.display='';
this.__body.style.display=''
}
return this;
};
__pro._$hide=function(){
if(this.__body){
E._$hideCover();
document.lbody.removeChild(this.__body);
document.lbody.style.display='none';
this.__body.style.display='none';
}
return this;
};
__pro._$setTitle=function(_title){
if(U.str._$isString(_title))
this.__ecttl.innerHTML=_title;
};
__pro.__getContent=F;
__pro.__getXNode=function(){
var _nd=E._$parseElement(__xhtml);
this.__eclose=E._$getElementsByClassName(_nd,'js-close')[0];
this.__ecttl=E._$getElementsByClassName(_nd,'js-cttl')[0];
this.__ecnt=E._$getElementsByClassName(_nd,'js-cnt')[0];
return _nd;
};
})();

(function(){
var p=P('P.ui'),
__pro,
__id='p_lay_login_cb'+U._$randNumberString(),
__account,
__xhtml='<form onsubmit="return false;">\
     <table>\
      <tr><th class="fc5">帐　号</th><td class="js-uname-cnt"></td></tr>\
      <tr><th class="fc5">密　码</th><td><input type="password" class="txt bdc6 fc5" name="password"/></td></tr>\
      <tr><th> </th><td><input type="checkbox" id="'+__id+'" class="autoc hand" name="autologin"/><label for="'+__id+'" class="autol fc2 js-autologin-text hand">自动登录</label><a class="forget" href="http://reg.163.com/RecoverPassword.shtml" target="_blank">忘记密码？</a></td></tr>\
      <tr class="btns"><th> </th><td><input class="login btn btn3 fc5" name="login" type="button" value="登录"/><a class="reg" href="http://reg.163.com/reg/reg.jsp?product=photo&url=http://photo.163.com/&loginurl=http://photo.163.com/" target="_blank">注册网易通行证>></a></td></tr>\
     </table>\
     <div class="login-tiparea js-tiparea" style="display:none;">\
      <div class="login-tiparea-top icn0 icn0-65"></div>\
      <span id="autologin_close" class="login-tiparea-close icn0 icn0-64 js-tiparea-close">&nbsp;</span>\
      <p class="fc01">为了你的帐号安全，请不要在网吧或公用电脑上使用此功能！ </p>\
     </div>\
    </form>';
p._$$QLogin=C();
__pro=p._$$QLogin._$extend(p._$$WBase,true);
__pro.__initialize=function(_param){
this.__disable(true);
this._$setTitle("登录网易通行证");
this.__name=new P.ui._$$UNInput({
parent:this.__ename,
className:'w-cxt-input',
onsuccess:this.__cbSuccess._$bind(this)
});
this.__addFormChangeEvent();
V._$addEvent(this.__btnLogin,'click',this.__onClickLogin._$bind(this));
V._$addEvent(this.__epwd,'focus',function(){
this.__epwd.select();
}._$bind(this));
V._$addEvent(this.__epwd,'keypress',this.__onKeyPress._$bind(this));
V._$addEvent(this.__etipareaclose,'click',this.__closeLoginTipArea._$bind(this));
V._$addEvent(this.__eautologintext,'mouseover',this.__showLoginTipArea._$bind(this));
V._$addEvent(this.__eautologintext,'mouseout',this.__closeLoginTipArea._$bind(this));
V._$addEvent(this.__eautoLogin,'mouseover',this.__showLoginTipArea._$bind(this));
V._$addEvent(this.__eautoLogin,'mouseout',this.__closeLoginTipArea._$bind(this));
};
__pro._$reset=function(_opt){
_opt=_opt||{};
var _onSuccess=_opt.onsuccess;
if(_onSuccess)
this.__onSuccess=_onSuccess;
this.__form.reset();
var _al=U.utl._$getAutoLogin();
window.setTimeout(function(){this.__eautoLogin.checked=!!_al;}._$bind(this),0);
if(_al){
this.__name._$setValue(_al[0]);
this.__epwd.value=this.__autoPwd=_al[1];
this.__disable(false);
}
return this;
};
__pro.__showLoginTipArea=function(){
this.__etiparea.style.display='block';
};
__pro.__closeLoginTipArea=function(){
this.__etiparea.style.display='none';
};
__pro._$focus=function(){
this.__name&&this.__name._$focus();
};
__pro.__cbSuccess=function(){
U.dom._$textFocus(this.__epwd);
};
__pro.__addFormChangeEvent=function(){
var _inputs=this.__form.getElementsByTagName('input');
for(var i=0,_it,_type;_it=_inputs[i];i++)
U.dom._$addTextChange(_it,U.evt._$fireEvent._$bind(window,this.__form,'formchange'));
V._$addEvent(this.__form,B._$ISIE?'propertychange':'formchange',this.__onFormChange._$bind(this));
};
__pro.__onFormChange=function(){
this.__disable(!this.__suffice());
};
__pro.__suffice=function(){
return this.__name._$getValue()&&this.__epwd.value;
};
__pro.__disable=function(_flag){
_flag=!!_flag;
this.__btnLogin.disabled=_flag;
_flag?E._$addClassName(this.__btnLogin,'btn3-disabled'):E._$delClassName(this.__btnLogin,'btn3-disabled');
};
__pro.__getContent=function(){
var _form=E._$parseElement(__xhtml);
this.__ename=E._$getElementsByClassName(_form,'js-uname-cnt')[0];
this.__eautologintext=E._$getElementsByClassName(_form,'js-autologin-text')[0];
this.__etiparea=E._$getElementsByClassName(_form,'js-tiparea')[0];
this.__etipareaclose=E._$getElementsByClassName(_form,'js-tiparea-close')[0];
this.__epwd=_form['password'];
this.__eautoLogin=_form['autologin'];
this.__btnLogin=_form['login'];
this.__form=_form;
return _form;
};
__pro.__onClickLogin=function(){
this.__disable(true);
var _account=this.__name._$getAccount(),_pwd=this.__epwd.value;
if(!this.__autoPwd||this.__autoPwd!=_pwd)
_pwd=/@126$/.test(_account)||/@yeah$/.test(_account)||/@188$/.test(_account)?_pwd:U._$md5(_pwd);
J._$postDataByDWR(location.pdwr,'IndexBean','login',_account,_pwd,this.__eautoLogin.checked,false,false,this.__cbLogin._$bind(this,_account,_pwd));
};
__pro.__onKeyPress=function(_event){
if(_event.keyCode==13&&!this.__btnLogin.disabled)
this.__onClickLogin(_event);
};
__pro.__cbLogin=function(_account,_pwd,_uname){
if(_uname){
this.__onSuccess&&this.__onSuccess(_uname);
}
else{
window.location.href='https://reg.163.com/logins.jsp?type=1&product=blog&username='+U._$getFullName(_account)+'&password='+_pwd+'&url='+window.location.href;
}
};
})();

(function(){
var p=P('np.l'),
__proCard,
__proWindow;
var __initialize=function(_parent,_options){
_options=_options||O;
_options['class']=_options['class']||'npw-win zbwin';
this._$super(_parent||document.body,_options);
};
p._$$Window=C();
__proWindow=p._$$Window._$extend(P(N.ui)._$$WindowWrapper,true);
__proWindow._$initialize=__initialize;
p._$$Card=C();
__proCard=p._$$Card._$extend(P(N.ui)._$$CardWrapper,true);
__proCard._$initialize=__initialize;
})();

(function(){
var p=P('np.w'),
__proModule,
__proCache,
__uispace='ui-'+U._$randNumberString(),
__xhtml='\
    <div>\
     <div class="main bds2 bdc3">\
      <b class="a-img bds0 bdc2 bdwa"><img class="0 t ava"></b>\
      <div class="alert">\
       <p class="y tt fw1 fc2">是否将<span class="0 z"></span>加为关注？</p>\
       <p class="n tt fw1 fc2">是否取消对<span class="1 z"></span>的关注？</p>\
       <div>\
        <input type="button" value="确定" class="1 t btn btn3 fc5"/>\
        <input type="button" value="取消" class="2 t btn btn3 fc5"/>\
       </div>\
      </div>\
     </div>\
     <div class="des bgc7 fc1">\
      <p class="y">关注后你就能在新鲜事接受他(她)的最新动态</p>\
      <p class="n">取消关注后新鲜事不会有他(她)的最新动态</p>\
     </div>\
    </div>';
P(N.ui)._$pushStyle('\
  #<uispace>{width:300px;}\
  #<uispace> .y,#<uispace> .n{display:none;}\
  #<uispace> .follow .y,#<uispace> .unfollow .n{display:block;}\
  #<uispace> .zcnt{padding:0;}\
  #<uispace> .main{padding:15px 15px 0;border-width:0 0 1px;}\
  #<uispace> .a-img {position:absolute;padding-bottom:3px;padding-left:3px;padding-right:3px;display:block;padding-top:3px;}\
  #<uispace> .ava{width:60px;height:60px;}\
  #<uispace> .alert{text-align:center;margin-left:75px;padding-bottom:15px;zoom:1;}\
  #<uispace> .alert input{margin-right:10px;}\
  #<uispace> .alert .tt{line-height:22px;padding:10px 0;}\
  #<uispace> .des{padding-bottom:10px;line-height:1.75;padding-left:10px;padding-right:10px;padding-top:10px;}\
  ',__uispace);
p._$$FollowModule=C();
__proModule=p._$$FollowModule._$extend(np.l._$$Window,true);
__proModule._$initialize=function(_parent,_options){
_options=_options||O;
this._$super(_parent,_options);
};
__proModule.__getSpace=function(){
return __uispace;
};
__proModule.__getXhtml=function(){
return __xhtml;
};
__proModule.__intXnode=function(){
var _ntmp=E._$getElementsByClassName(this.__body,'t');
this.__eimg=_ntmp[0];
this.__esubmit=_ntmp[1];
this.__ecancel=_ntmp[2];
this.__enames=E._$getElementsByClassName(this.__body,'z');
this.__cache=new p._$$FollowCache({follow:this.__cbFollow._$bind(this),unfollow:this.__cbUnfollow._$bind(this)});
V._$addEvent(this.__ecancel,'click',this._$hide._$bind(this));
V._$addEvent(this.__eimg,'error',U.dom._$onImgError._$bind2(U.dom,location.f60));
V._$addEvent(this.__esubmit,'click',this.__followAction._$bind(this));
};
__proModule._$resetOption=function(_options){
p._$$FollowModule._$supro._$resetOption.call(this,_options);
this._$addEvent('onfollow',_options.onfollow||F);
this._$addEvent('onunfollow',_options.onunfollow||F);
this.__layer._$setTitle(_options.title);
this.__follow=_options.follow||false;
this.__profile=_options.profile;
this.__eimg.src=U.fun._$getPassportUserImage(this.__profile.name);
var _tmp=this.__profile.nickname||this.__profile.name;
U.dom._$setText(this.__enames,U.str._$truncate(_tmp,16));
this.__eimg.alt=this.__eimg.title=_tmp;
this.__esubmit.focus();
this.__body.className=this.__follow?'follow':'unfollow';
};
__proModule.__followAction=function(){
if(this.__follow)
this.__cache._$follow(this.__profile.id);
else
this.__cache._$unfollow(this.__profile.id);
this._$hide();
};
__proModule.__cbFollow=function(_result){
if(_result&&_result.childID)
_result=true;
this._$dispatchEvent('onfollow',_result);
};
__proModule.__cbUnfollow=function(_result){
if(_result&&_result.childID)
_result=true;
this._$dispatchEvent('onunfollow',_result);
};
p._$$FollowCache=C();
__proCache=p._$$FollowCache._$extend(P(N.ut)._$$Cache);
__proCache._$follow=function(_userId){
J._$postDataByDWR(location.sdwr,'UserFollowerBean','addUserFollowing',_userId,this._$dispatchEvent._$bind(this,'follow'));
};
__proCache._$unfollow=function(_userId){
J._$postDataByDWR(location.sdwr,'UserFollowerBean','deleteUserFollowing',_userId,this._$dispatchEvent._$bind(this,'unfollow'));
};
})();

(function(){
var p=P('P.ui'),
__pro,
__tkey=E._$addNodeTemplate(E._$parseElement('<input type="button"/>'));
p._$$Button=C();
__pro=p._$$Button._$extend(P(N.ut)._$$Item,true);
__pro._$initialize=function(){
this._$super(__tkey);
V._$addEvent(this.__body,'click',this.__onClick._$bind(this));
};
__pro._$destroy=function(){
if(this.__data.c)
E._$delClassName(this.__body,this.__data.c);
p._$$Button._$supro._$destroy.call(this);
};
__pro._$reset=function(_options){
_options=_options||O;
this._$addEvent('onclick',_options.callback||F);
};
__pro._$setData=function(_data){
this.__data=_data||O;
this.__body.value=this.__data.t;
if(this.__data.c)
E._$addClassName(this.__body,this.__data.c);
};
__pro._$getData=function(){
return this.__data;
};
__pro.__onClick=function(){
this._$dispatchEvent('onclick',this.__data);
};
})();
(function(){
var p=P('P.ui'),
__pro,
__xhtml='<div class="lay-ct"><div></div><div class="lay-btn"></div></div>';
p._$$MessageBox=C();
__pro=p._$$MessageBox._$extend(P(N.ui)._$$WindowWrapper,true);
__pro._$initialize=function(_parent,_options){
this.__bopt={callback:this.__onButtonClick._$bind(this)};
this._$super(_parent,_options);
};
__pro.__getXhtml=function(){
return __xhtml;
};
__pro.__intXnode=function(){
var _ntmp=E._$getChildElements(this.__body);
_ntmp=E._$getChildElements(_ntmp[0]);
this.__cntCase=_ntmp[0];
this.__btnCase=_ntmp[1];
};
__pro._$resetOption=function(_options){
_options=_options||O;
p._$$MessageBox._$supro._$resetOption.call(this,_options);
this.__callback=_options.callback||F;
this.__layer._$setTitle(_options.title);
this._$setMessage(_options.message);
this._$setButtons(_options.buttons);
};
__pro._$destroy=function(){
if(this.__buttons)
this.__buttons=p._$$Button._$recycle(this.__buttons);
p._$$MessageBox_$supro._$destroy.call(this);
};
__pro.__onButtonClick=function(_data){
var _result;
if(!!_data.fn)
_result=_data.fn.call(_data,_data.v);
else
_result=this.__callback(_data.v);
if(_result==-1)
return;
this._$hide();
};
__pro._$setMessage=function(_cnt){
_cnt=_cnt||'';
this.__cntCase.innerHTML='';
typeof(_cnt)=='string'?this.__cntCase.innerHTML=_cnt:this.__cntCase.appendChild(_cnt);
};
__pro._$setButtons=function(_btns){
this.__buttons=p._$$Button._$recycle(this.__buttons);
this.__buttons=p._$$Button._$allocate(_btns,this.__btnCase,this.__bopt);
};
E._$alert=function(_title,_msg,_fn,_btnText){
P('P.ui')._$$MessageBox._$show({
parent:document.body,
nohack:false,
iframe:true,
'class':'win0',
title:_title||'',
message:_msg||'',
buttons:[{t:_btnText||'确定',c:'btn btn3 fc5',fn:_fn||F}]
});
};
E._$confirm=function(_title,_msg,_fnok,_fncc,_btnOK,_btnCC){
P('P.ui')._$$MessageBox._$show({
parent:document.body,
nohack:false,
iframe:true,
'class':'win0',
title:_title||'',
message:_msg||'',
buttons:[{t:_btnOK||'确定',c:'btn btn3 fc5',fn:_fnok||F},
{t:_btnCC||'取消',c:'btn btn3 fc5',fn:_fncc||F}]
});
};
E._$close=function(){
P('P.ui')._$$MessageBox._$hide();
};
})();
(function(){
var p=P('P.ui'),
__pro,
__supro,
__xhtml='<div class="w-hint"><div class="w-hint-head"><span class="icn0 icn0-49"> </span></div><div class="w-hint-body bdwa bds0 bdc23 bgc7 fc2"><b>：)</b> <div class="msg js-msg"></div></div></div></div>';
p._$$NHint=C();
__pro=p._$$NHint._$extend(p._$$UIBase);
__supro=p._$$UIBase.prototype;
U.cls._$augment(p._$$NHint,P.ut._$$Single,true);
__pro._$reset=function(_opt){
_opt=_opt||
{};
this.__body.style.display='none';
this._$appendTo(_opt.pnode);
return this;
};
__pro._$show=function(_msg){
this.__emsg.innerHTML=_msg||'';
__supro._$show.call(this);
return this;
};
__pro._$hide=function(){
__supro._$hide.call(this);
this.__emsg.innerHTML='';
return this;
};
__pro.__getXNode=function(){
var _nd=E._$parseElement(__xhtml);
this.__emsg=E._$getElementsByClassName(_nd,'js-msg')[0];
return _nd;
};
})();
(function(){
var p=P('P.ui'),
__pro,
__xhtml='<div class="w-select"><div class="w-select-face js-face bdc6"></div><div class="w-select-layer js-layer bdc6 bgc99" style="display:none;"></div></div>';
p._$$Select=C();
__pro=p._$$Select._$extend(p._$$UIBase);
__pro.__initialize=function(_param){
V._$addEvent(this.__face,'click',this.__toggleLayer._$bind(this));
V._$addEvent(document,'click',this.__hideLayer._$bind(this));
this._$appendTo(_param.parent);
E._$addClassName(this.__body,_param.classname);
this.__onChange=_param.onchange;
this.__setList(_param.list);
this.__select(this.__list[_param.index||0]);
};
__pro.__setList=function(_list){
if(!U.arr._$isArray(_list)){
this.__list=[];
return;
}
if(this.__list==_list)return;
this.__itms&&p._$$SelectItem._$recycle(this.__itms);
this.__itms=p._$$SelectItem._$allocate(_list,this.__layer,{
onselect:this.__select._$bind(this)
});
this.__list=_list;
};
__pro.__select=function(_data){
if(this.__curData==_data)
return;
this.__face.innerText=_data;
this.__curData=_data;
this.__hideLayer();
this.__onChange&&this.__onChange(_data);
};
__pro.__hideLayer=function(_event){
this.__layer.style.display='none';
};
__pro.__showLayer=function(){
this.__layer.style.display='';
};
__pro.__toggleLayer=function(_event){
this.__layer.style.display=this.__layer.style.display=='none'?'':'none';
V._$stopBubble(_event);
};
__pro.__getXNode=function(){
var _nd=E._$parseElement(__xhtml);
this.__face=E._$getElementsByClassName(_nd,'js-face')[0];
this.__layer=E._$getElementsByClassName(_nd,'js-layer')[0];
return _nd;
};
})();
(function(){
var p=P('P.ui'),__xhtml='<a href="javascript:void(0);" hidefocus="true" class="h-fc99 h-bgc3 noul"></a>';
p._$$SelectItem=C();
var __proItem=p._$$SelectItem._$extend(P.ui._$$UIBase);
U.cls._$augment(p._$$SelectItem,P.ut._$$Reuse,true);
__proItem.__initialize=function(_param){
_param=_param||{};
this.__onSelect=_param.onselect;
V._$addEvent(this.__body,'click',this.__onClickSelect._$bind(this));
};
__proItem._$reset=function(_data){
this.__data=_data;
this.__body.innerText=this.__data;
};
__proItem.__onClickSelect=function(){
this.__onSelect(this.__data);
};
__proItem.__getXNode=function(){
return E._$parseElement(__xhtml);
};
})();

(function(){
var p=P('np.m'),
__proSearchModule;
p._$$TopSearchModule=C();
__proSearchModule=p._$$TopSearchModule._$extend(P(N.ut)._$$Singleton,true);
__proSearchModule.__initialize=function(){
this.__intXnode();
};
__proSearchModule.__intXnode=function(){
var _tmp=E._$getElementsByClassName(E._$getElement('np_top_search'),'t');
if(!_tmp)return;
this.__eform=_tmp[0];
this.__etypeCon=_tmp[1];
this.__ecurTypeTxt=_tmp[2];
this.__etypeLayer=_tmp[3];
this.__etypeContent=_tmp[4];
this.__etypeAuthor=_tmp[5];
this.__einput=_tmp[6];
this.__ecurType=_tmp[7];
this.__ebutton=_tmp[8];
U.dom._$addTextHint(this.__einput,'西藏 布达拉宫');
V._$addEvent(document.body,'click',this.__toggleTypeLayer._$bind(this));
V._$addEvent(this.__ecurTypeTxt,'click',this.__toggleTypeLayer._$bind(this));
V._$addEvent(this.__etypeContent,'click',this.__setType._$bind(this,0));
V._$addEvent(this.__etypeAuthor,'click',this.__setType._$bind(this,1));
V._$addEvent(this.__eform,'submit',this.__search._$bind(this));
V._$addEvent(this.__ebutton,'click',this.__search._$bind(this));
};
__proSearchModule.__toggleTypeLayer=function(_event){
if(V._$getElement(_event)==this.__ecurTypeTxt){
V._$stopBubble(_event);
this.__etypeLayer.style.display=(this.__etypeLayer.style.display!='block')?'block':'none';
this.__etypeCon.style.overflow=(this.__etypeCon.style.overflow!='hidden')?'visible':'hidden';
}
else{
this.__etypeLayer.style.display='none';
this.__etypeLayer.style.overflow='hidden';
}
};
__proSearchModule.__setType=function(_type){
this.__ecurType.value=_type;
this.__ecurTypeTxt.innerText=['内容','作者'][_type];
};
__proSearchModule.__search=function(){
var _txt=U._$trim(this.__einput.value);
if(_txt)
location=location.r+'/pp/search?q='+escape(_txt)+"&st="+this.__ecurType.value+'#m=0&page=1';
else
location=location.r+'/pp/search';
};
new p._$$TopSearchModule();
})();
(function(){
var p=P('np.m'),
__proCache;
p._$$TopCache=C();
__proCache=p._$$TopCache._$extend(P(N.ut)._$$Cache);
__proCache._$checkFolStatusFromBlog=function(){
J._$loadDataByDWR(location.bdwrnew,'UserFollowBeanNew','isFollowedForPhoto',this._$dispatchEvent._$bind(this,'onfolstatuscheck'));
};
__proCache._$checkFolStatusFromPhoto=function(_parentId,_childId){
J._$loadDataByDWR(location.sdwr,'UserFollowerBean','isUserFollowed',_parentId,_childId,this._$dispatchEvent._$bind(this,'onfolstatuscheck'));
};
__proCache._$follow=function(_userId){
J._$loadDataByDWR(location.sdwr,'UserFollowerBean','addUserFollowing',_userId,this._$dispatchEvent._$bind(this,'onfollow'));
};
__proCache._$logout=function(){
J._$postDataByDWR(location.br+'/dwr','UserBean','clearSession',false,false);
J._$postDataByDWR(location.pdwr,'IndexBean','logout',false,false,function(_suc){
if(_suc){
J._$postDataByDWR(location.sdwr,'IndexBean','logout',false,false,function(_suc){
if(_suc){
onbeforeunload=null;
if(window.logoutTarget)
location=window.logoutTarget;
else
location.reload();
}
else
alert('退出失败, 请稍候再试！');
});
}
else
alert('退出失败, 请稍候再试！');
});
};
__proCache._$checkMessage=function(_name){
J._$loadDataByTAG(location.sdwr,'ShareMessageBean','getShareMessageCount',this._$dispatchEvent._$bind(this,'onmessagecheck'));
};
})();

(function(){
var p=P('np.m'),
__proModule;
p._$$TopLoginModule=C();
__proModule=p._$$TopLoginModule._$extend(P(N.ut)._$$Singleton,true);
__proModule.__initialize=function(){
this.__intXnode();
this.__intCache();
};
__proModule.__intXnode=function(){
var _ntmp,_enav=E._$getElement('np_top_nav');
if(!_enav)return;
_ntmp=E._$getElementsByClassName(_enav,'j');
for(var i=0,l=_ntmp.length;i<l;i++)
U.dom._$hoverElement(_ntmp[i]);
if(np.c._$ISLOGIN){
V._$addEvent('np_top_logout','click',this.__logout._$bind(this));
_ntmp=E._$getElementsByClassName(_enav,'b');
for(var i=0,l=_ntmp.length;i<l;i++)
V._$addEvent(_ntmp[i],'click',this.__hidePopupLayer._$bind(this));
}
else{
U.dom._$initAnchor('np_top_login');
_ntmp=E._$getElementsByClassName(_enav,'a');
for(var i=0,l=_ntmp.length;i<l;i++)
V._$addEvent(_ntmp[i],'click',this.__loginBeforeRedirect._$bind(this,_ntmp[i].href));
}
};
__proModule.__intCache=function(){
this.__cache=new p._$$TopCache();
};
__proModule.__loginBeforeRedirect=function(_link,_event){
V._$stop(_event);
if(!U.str._$isUrl(_link))
return;
P.ui._$$QLogin._$getInstance({
classname:'lay-login'
})._$reset({
onsuccess:function(_name){
_link=_link.replace(location.r,location.r+'/'+_name);
if(_link!=location.href)
location=_link;
else
location.reload();
}
})._$show()._$focus();
};
__proModule.__logout=function(){
this.__cache._$logout();
};
__proModule.__hidePopupLayer=function(_event){
var _target=V._$getElement(_event);
_target.blur();
while(_target.tagName.toLowerCase()!='div')
_target=_target.parentNode;
if(_target){
_target.style.display='none';
setTimeout(function(){_target.removeAttribute('style');},0);
}
};
new p._$$TopLoginModule();
var __dhOther=E._$getElement('J-more');
if(__dhOther){
V._$addEvent(__dhOther,'mouseover',function(e){
E._$addClassName(__dhOther,'j-hover');
});
V._$addEvent(__dhOther,'mouseout',function(e){
E._$delClassName(__dhOther,'j-hover');
});
}
})();
(function(){
var p=P('np.m'),
__proBanner,
__suproBanner,
__uispace='ui-'+U._$randNumberString();
p._$$TopHomeBanner=C();
__proBanner=p._$$TopHomeBanner._$extend(np.l._$$Window,true);
__suproBanner=np.l._$$Window.prototype;
P(N.ui)._$pushStyle('\
     #<uispace>{width:704px;}\
     #<uispace> .zcnt{}\
     #<uispace> .zcnt .ht0{line-height:30px;}\
     #<uispace> .zcnt .ht1{padding-top:70px;}\
     #<uispace> .zcnt .ht3{padding-top:70px;background:url("http://r.ph.126.net/photo/image/default/loading2.gif") no-repeat 265px bottom;}\
     #<uispace> .zcnt .ht2{font-size:20px;margin-top:10px;}\
     #<uispace> .zcnt .h-t{height:20px;line-height:20px;position:relative;width:238px;}\
     #<uispace> .zcnt .h-b{position:relative;width:61px;height:25px;line-height:25px;}\
     #<uispace> .zcnt .file{position:absolute;left:20px;width:305px;height:25px;filter:alpha(opacity=0);opacity:0;}\
     #<uispace> .zcnt .submit{}\
     #<uispace> .zcnt .hint{text-align:center;height:200px;margin-top:10px;}\
     #<uispace> .zcnt .hint img{width:660px;height:200px;margin:0 auto;}\
     #<uispace> .zcnt .act{text-align:center;padding-top:30px;}\
     #<uispace> .zcnt .act input{margin-right:20px;}\
     #<uispace> .w-hint .w-hint-body{width:400px;}\
     ',__uispace);
__proBanner._$initialize=function(_parent,_options){
_options=_options||O;
this._$super(_parent,_options);
this._$addEvent('onok',_options.onok);
};
__proBanner.__getSpace=function(){
return __uispace;
};
__proBanner.__getXhtml=function(){
return'\
     <div>\
     <form class="0 t" enctype="multipart/form-data" method="post" target="uploadFrame" action="http://upload.photo.163.com/anony/web/upload/userdefinesize?sitefrom=test&responsetype=js&saveorigin=true&userdefinesize=980x300x0">\
      <input class="1 t h-t" type="text">\
      <input class="2 t h-b btn btn3" type="button" value="浏览..." name="ok" hidefocus="true">\
      <input class="3 t file" type="file" value="浏览..." name="Filedata" size="37">\
      <input class="4 t submit btn btn3 hide" type="button" value="开始上传" name="ok">\
     </form>\
     <p class="ht0">建议上传尺寸为980像素*300像素，或大于此尺寸同比例的图片。支持jpg\\jpeg\\gif\\png\\bmp格式。最大5M。</p>\
     <div class="5 t hint bgc5">\
      <div>\
       <p class="ht1">小屋顶部添加的图片显示效果为</p><p class="ht2">980像素 X 300像素</p>\
      </div>\
      <div style="display:none;">\
       <p class="ht3">正在上传...请稍后</p>\
      </div>\
      <div style="display:none;"><img class="6 t"></div>\
     </div>\
     <iframe class="7 t" src="http://photo.163.com/photo/html/crossdomain.html?t=20100205" name="uploadFrame" style="display:none;"></iframe>\
     <div class="act"><input type="button" class="8 t btn btn3" value="确定"><input type="button" class="9 t btn btn3" value="取消"></div>\
    </div>';
};
__proBanner._$resetOption=function(_options){
this.__reset();
__suproBanner._$resetOption.call(this,_options);
};
__proBanner.__intXnode=function(){
var _ntmp=E._$getElementsByClassName(this.__body,'t'),_i=0;
this.__eform=_ntmp[_i++];
this.__etext=_ntmp[_i++];
this.__ebrowse=_ntmp[_i++];
this.__efile=_ntmp[_i++];
this.__eupload=_ntmp[_i++];
this.__econ=_ntmp[_i++];
this.__eimg=_ntmp[_i++];
this.__eiframe=_ntmp[_i++];
this.__eok=_ntmp[_i++];
this.__ecancel=_ntmp[_i++];
g_UploaderWebJsCallback=this.__uploadCallBack._$bind(this);
V._$addEvent(this.__efile,'change',this.__onFileChange._$bind(this));
V._$addEvent(this.__eok,'click',this.__onOk._$bind(this));
V._$addEvent(this.__ecancel,'click',this.__onCancel._$bind(this));
this.__taber=new P.ut._$$VTaber(this.__econ);
this.__taber._$setIndex(0);
};
__proBanner.__reset=function(){
this.__eform.reset();
this.__taber._$setIndex(0);
this.__eimg.src=location.snf;
U.dom._$enableBtn(this.__eok,'btn3-disabled');
U.dom._$enableBtn(this.__ebrowse,'btn3-disabled');
};
__proBanner.__onFileChange=function(){
this.__disableBtn();
this.__etext.value=this.__efile.value;
this.__eform.submit();
this.__taber._$setIndex(1);
this.__eimg.src=location.snf;
};
__proBanner.__uploadCallBack=function(_data){
this.__reset();
if(!_data)
alert('网络错误，请稍后再试');
var _code=_data.resultcode;
if(_code===999){
this.__data=_data;
this.__eimg.src=_data.userDef1Url;
this.__taber._$setIndex(2);
}
else{
switch(_code){
case 0:
alert('图片过大');
break;
case 12:
alert('文件格式错误');
break;
default:
alert('网络错误，请稍候再试');
break;
}
this.__taber._$setIndex(0);
}
};
__proBanner.__onOk=function(){
this.__disableBtn();
if(!this.__data)
this._$hide();
else{
var _data={ourl:this.__data.ourl,userDef1Url:this.__data.userDef1Url,imgStorageType:this.__data.imgStorageType};
J._$loadDataByDWR(location.pdwr,'PhotoBean','copy',[_data],this.__cbCopy._$bind(this,_data));
}
};
__proBanner.__cbCopy=function(_data,_suc){
var _func=function(){
alert('网络错误，请稍候再试');
this.__reset();
}._$bind(this);
if(_suc)
J._$loadDataByDWR(location.sdwr,'PhotoBlogBean','addPhotoHeadPic',_data.userDef1Url,_data.imgStorageType,function(_suc){
if(_suc){
this.__reset();
this._$hide();
this._$dispatchEvent('onok',_data);
}
else
_func();
}._$bind(this));
else
_func();
};
__proBanner.__onCancel=function(){
this._$hide();
};
__proBanner.__disableBtn=function(){
U.dom._$disableBtn(this.__eok,'btn3-disabled');
U.dom._$disableBtn(this.__ebrowse,'btn3-disabled');
};
})();
(function(){
var p=P('np.w'),
__proModule;
p._$$BlogTopbarModule=C();
__proModule=p._$$BlogTopbarModule._$extend(P(N.ut)._$$Singleton,true);
__proModule.__initialize=function(){
this.__intXnode();
if(np.c._$UD.index)
return;
else
if(!np.c._$UD.photo2_0||np.c._$UD.blog2_0||np.c._$UD.imgout_blog)
this.__intModule();
};
__proModule.__intXnode=function(){
this.__etopbar=E._$getElement('np_album_topbar');
var _tmp=np.c._$UD.visitName,
_options={
visitor:{
userId:0,
userName:_tmp
},
mid:61,
themeId:-1,
targeturl:'',
noUserId:false,
targeturl:'javascript:void(0);',
fkurl:'http://fankui.163.com/ft/tutorial.fb?pid=3',
state:_tmp?1:0
};
this.__options=_options;
};
__proModule.__intModule=function(){
if(!this.__etopbar)
return;
new nb.w._$$TopBar(this.__etopbar,this.__options);
};
new p._$$BlogTopbarModule();
})();

(function($){
var p=P('np.m'),
__proModule,
__proHomeModule;
p._$$TopModule=C();
__proModule=p._$$TopModule._$extend(P(N.ut)._$$Singleton,true);
__proModule.__initialize=function(_options){
this._$super(_options);
this.__intXnode();
this.__intCache();
this.__intModule();
};
__proModule.__intXnode=function(){
this.__enav=E._$getElement('np_top_nav');
this.__emitm=E._$getElementsByClassName(this.__enav,'x');
};
__proModule.__intCache=function(){
this.__cache=new p._$$TopCache({
onmessagecheck:this.__cbCheckMessage._$bind(this)
});
};
__proModule.__intModule=function(){
if(np.c._$ISLOGIN)
this.__onCheckMessage();
};
__proModule.__logout=function(){
this.__cache._$logout();
};
__proModule.__onCheckMessage=function(){
setTimeout(this.__onCheckMessage._$bind(this),60000);
this.__cache._$checkMessage(np.c._$UD.visitName);
};
__proModule.__cbCheckMessage=function(_message){
if(!_message||(this.__emitm&&!this.__emitm.length))
return;
var _total=0,_tag=['inboxCount','commentCount','replyCount','guestbookCount','alertCount','noticeCount'];
for(var i=_tag.length-1,_count;i>=0;i--){
_count=_message[_tag[i]]||0;
this.__emitm[i+1].innerText=_count;
_total+=_count;
}
if(_total>0){
this.__emitm[0].innerText=_total;
E._$replaceClassName(this.__emitm[0],'off','on');
}
};
p._$$TopHomeModule=C();
__proHomeModule=p._$$TopHomeModule._$extend(p._$$TopModule,true);
__proHomeModule.__intXnode=function(){
__proModule.__intXnode.call(this);
this.__erank=$('np_home_host_rank');
U.dom._$setRankData(this.__erank,{grade:np.c._$UD.hostShareGrade,rank:np.c._$UD.hostShareRank});
};
__proHomeModule.__intModule=function(){
__proModule.__intModule.call(this);
if(np.c._$ISLOGIN){
if(!np.c._$ISEDIT){
this.__intXParam();
}
}
};
__proHomeModule.__intXParam=function(){
var _xparam=U.sys._$getAllXParam();
_xparam=U.obj._$toArray(_xparam)
if(_xparam&&_xparam.length){
switch(_xparam[0].op){
case'msg':
break;
case'rec':
break;
case'cmt':
break;
}
}
};
if(np.c._$UD.index||np.c._$UD.blog2_0)
return;
if(np.c._$UD.photo2_0_com)
new p._$$TopModule();
else
if(np.c._$UD.photo2_0)
new p._$$TopHomeModule();
new p._$$TopSearchModule();
})(E._$getElement);
