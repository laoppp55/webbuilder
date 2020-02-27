<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息点评-->更多</title>
<link href="http://njy01.coosite.com/css/comment.css" rel="stylesheet" type="text/css" />

<script language=JavaScript> 
nereidFadeObjects = new Object();
nereidFadeTimers = new Object();
function nereidFade(object, destOp, rate, delta){
if (!document.all)
return
    if (object != "[object]"){  //do this so I can take a string too
        setTimeout("nereidFade("+object+","+destOp+","+rate+","+delta+")",0);
        return;
    }
    clearTimeout(nereidFadeTimers[object.sourceIndex]);
    diff = destOp-object.filters.alpha.opacity;
    direction = 1;
    if (object.filters.alpha.opacity > destOp){
        direction = -1;
    }
    delta=Math.min(direction*diff,delta);
    object.filters.alpha.opacity+=direction*delta;
    if (object.filters.alpha.opacity != destOp){
        nereidFadeObjects[object.sourceIndex]=object;
        nereidFadeTimers[object.sourceIndex]=setTimeout("nereidFade(nereidFadeObjects["+object.sourceIndex+"],"+destOp+","+rate+","+delta+")",rate);
    }
}
</script>
</head>

<body>
    <div class="article_1225">
    <div class="title_1225"><span>更多点评</span><a href="#"><img border="0" alt="" src="http://njy01.coosite.com/images/close_1225.jpg" /></a></div>
    <div class="con_0113">
    	<div class="con_left">
    	<div class="con_1">
        	<div class="con_1_pic"><img src="http://njy01.coosite.com/images/20160113_1.jpg" /></div>
            <div class="con_1_wz">
            	<div class="con_1_tit">十渡山水情农家院</div>
                <div class="con_1_zy">地址：房山区十渡镇八渡村紧天禄山景区 电话： 13716498209 … <span><a href="#">更多>></a></span></div>
            </div>
            <div class="clear"></div>
        </div>	
        <div class="con_ztpj">
        	<div class="con_ztpj_1"><span> * </span>整体评价:</div>
            <div class="con_ztpj_2"><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></div>
            <div class="con_ztpj_3">(一般)</div>
        </div>
        <div class="con_ztpj">
        	<div class="con_ztpj_1"><span> * </span>分项点评:</div>
        </div>
        <div class="con_ztpj11">
        	<div class="con_ztpj_111">设备设施:</div>
            <div class="con_ztpj_2"><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></div>
            <div class="con_ztpj_3">(一般)</div>
            <div class="con_ztpj_111">环境卫生:</div>
            <div class="con_ztpj_2"><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></div>
            <div class="con_ztpj_3">(好)</div>
        </div>
        <div class="con_ztpj11">
        	<div class="con_ztpj_111">服务质量:</div>
            <div class="con_ztpj_2"><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></div>
            <div class="con_ztpj_3">(非常好)</div>
            <div class="con_ztpj_111">地理位置:</div>
            <div class="con_ztpj_2"><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></div>
            <div class="con_ztpj_3">(差)</div>
        </div>
        <div class="con_ztpj11">
        	<div class="con_ztpj_111">餐饮服务:</div>
            <div class="con_ztpj_2"><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></div>
            <div class="con_ztpj_3">(好)</div>
            <div class="con_ztpj_111">性价比:</div>
            <div class="con_ztpj_2"><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_2.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></div>
            <div class="con_ztpj_3">(一般)</div>
        </div>
        <div class="con_ztpj">
        	<div class="con_ztpj_1"><span> * </span>入住感受:</div>
        </div>
		<div class="pl_list">
			 <div class="zs_jlnr">
                	<div class="zs_jlnr_1"><img src="http://njy01.coosite.com/images/20150527_8.png" /><br />yunliu</div>
                    <div class="zs_jlnr_2">环境很好，十一入住的，一百元标准间，不贵。老板人特别热情，不管去哪个景点都车接车送。总之，石人峰是来十渡的首选。<br /><span>2014年12月</span></div>
					<div class="latestWeb">
						<ul>
							<li>
							     <div><a href="#"><img src='http://njy01.coosite.com/images/55e736d12f2eb938622bcb79d3628535e4dd6ff3.jpg' width="90" height="59" alt="" border="0" onMouseOver="toolTip('<img src=http://njy01.coosite.com/images/55e736d12f2eb938622bcb79d3628535e4dd6ff3.jpg>')" onMouseOut="toolTip()"></a></div>
							</li>
							<li>
							     <div><a href="#"><img src='http://njy01.coosite.com/images/203fb80e7bec54e7c0aaab95bf389b504ec26af3.jpg' width="90" height="59" alt="" border="0" onMouseOver="toolTip('<img src=http://njy01.coosite.com/images/203fb80e7bec54e7c0aaab95bf389b504ec26af3.jpg>')" onMouseOut="toolTip()"></a></div>
							</li>
							<li>
							     <div><a href="#"><img src='http://njy01.coosite.com/images/b8389b504fc2d562794e6166e11190ef77c66cf3.jpg' width="90" height="59" alt="" border="0" onMouseOver="toolTip('<img src=http://njy01.coosite.com/images/b8389b504fc2d562794e6166e11190ef77c66cf3.jpg>')" onMouseOut="toolTip()"></a></div>
							</li>
						
						</ul>
					</div>
                    <div class="clear"></div>
					
              </div>
              <div class="zs_jlnr">
                	<div class="zs_jlnr_1"><img src="http://njy01.coosite.com/images/20150527_8.png" /><br />yunliu</div>
                  <div class="zs_jlnr_2">环境很好，十一入住的，一百元标准间，不贵。老板人特别热情，不管去哪个景点都车接车送。总之，石人峰是来十渡的首选。<br /><span>2014年12月</span></div>
                    <div class="clear"></div>
              </div>
                <div class="zs_jlnr">
                	<div class="zs_jlnr_1"><img src="http://njy01.coosite.com/images/20150527_8.png" /><br />yunliu</div>
                  <div class="zs_jlnr_2">环境很好，十一入住的，一百元标准间，不贵。老板人特别热情，不管去哪个景点都车接车送。总之，石人峰是来十渡的首选。<br /><span>2014年12月</span></div>
                    <div class="clear"></div>
              </div>
			  <script language="javascript" src="http://njy01.coosite.com/js/ToolTip.js"></script>
                <div class="clear"></div>
				<div class="nj_label"><a href="#">上一页</a><a href="#" class="red">1</a><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">下一页</a></div>
			</div>
        <div class="clear"></div>
        </div>
        <div class="con_2">
        	<p><span>提示</span></p>
            <p>我们对全体网名保证内容真实性，虚假和违规点评将不能正常发表。</p>
            <p><span>详细说明</span></p>
            <p>点评超过500字、包含至少3张图片，就有机会被认证为砖家点评并免费入住十渡山水情农家院。</p>
        </div>
    </div>
    <div class="clear"></div>
    </div>
</body>

</html>
