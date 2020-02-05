(function($,$$){
var m=P('P.ui'),
__xhtml=" <li class='js-judge'>\
                       <a target='_blank' class='t' hidefocus='true'><img class='t' title='点击查看作品' onerror='this.src=\""+location.fa75+"\"' /></a>\
         <div class='info hide t'>\
        <a class='t l' target='_blank'><img class='t avt'/></a>\
        <span class='t icn1 lk fc2'></span>\
        <span class='fi0 arrow iblock'></span>\
      </div>\
     </li>";
m._$$pictureItem=C();
var __proItem=m._$$pictureItem._$extend(P.ui._$$UIBase);
U.cls._$augment(m._$$pictureItem,P.ut._$$Reuse,true);
__proItem._$reset=function(_data){
_data=_data||{};
var _tmp=_data.actUserName||'';
this.__eimg.src=_data.pic75url||location.fa75;
this.__ehref.href='http://photo.163.com/'+(_data.userName||'')+'/pp/slide/'+(_data.setId||0)+'.html#pid='+(_data.picId||0);
this.__euserLink.title=this.__euserAvt.alt=_tmp;
this.__euserAvt.src='http://os.blog.163.com/common/ava.s?passport='+U._$getFullName(_tmp);
this.__euserLink.href='/'+_tmp+'/home';
this.__etime.innerText=_data.liketime+'喜欢';
U.dom._$hoverElement(this.__body);
};
__proItem.__getXNode=function(){
var _nd=E._$parseElement(__xhtml),_i=0,_tmp=E._$getElementsByClassName(_nd,'t');
this.__ehref=_tmp[_i++];
this.__eimg=_tmp[_i++];
this.__eLikeUser=_tmp[_i++];
this.__euserLink=_tmp[_i++];
this.__euserAvt=_tmp[_i++];
this.__etime=_tmp[_i++];
return _nd;
};
})(E._$getElement);
