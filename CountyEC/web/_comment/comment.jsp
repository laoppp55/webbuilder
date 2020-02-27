<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="http://njy01.coosite.com/css/comment.css" rel="stylesheet" type="text/css" />
<script src="http://www.gugulx.com/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" src="http://www.gugulx.com/js/zDrag.js"></script>
<script type="text/javascript" src="http://www.gugulx.com/js/zDialog.js"></script>
<script src="http://www.gugulx.com/js/validate/jquery.validate.js" type="text/javascript"></script>
<script src="http://www.gugulx.com/js/validate/messages_zh.js" type="text/javascript"></script>
<script src="http://www.gugulx.com/js/validate/additional-methods.js" type="text/javascript"></script>
<script charset="utf-8" language="javascript" type="text/javascript" src="http://www.gugulx.com/js/comment.js"></script>
<%
    String ip = request.getHeader("x-forwarded-for");
    if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
    }
    if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("X-Real-IP");
    }
    if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
    }


    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username=null;
    //如果SESSION失效，试试COOKIE是否可以读出来
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    for(Cookie c :cookies ){
        if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
            SecurityUtil securityUtil = new SecurityUtil();
            cookie_value = securityUtil.detrypt(c.getValue(),null);
            break;
        }
    }

    if (cookie_value != null) {
        int posi = cookie_value.indexOf("-");
        username = cookie_value.substring(0, posi);
    } else {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
        if (authToken != null) {
            SessionUtil.removeUserAuthorization(response, session);
        }
    }
    if(username == null ){
        username = ip;
    }
    System.out.println("1234="+username);
%>
<form id="formInsertComments" method="post" accept-charset="utf-8">
    <input id="siteid" type="hidden" name="siteid" value="">
    <input id="userid" type="hidden" name="userid" value="<%=username%>">
<div class="article_1225">
    <div class="title_1225">
       <!-- <span>我要点评</span><a href="#" onclick=javascript:divwinclose()>
        <img border="0" alt="" src="http://njy01.coosite.com/images/close_1225.jpg" /></a>-->
    </div>
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
                <div class="con_ztpj_2">
					<i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
					<i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
					<i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
					<i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
					<i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                </div>
                <div class="con_ztpj_3">(一般)</div>
                <div><input type="hidden" name="scores" value="3"></div>
            </div>
            <div class="con_ztpj">
                <div class="con_ztpj_1"><span> * </span>分项点评:</div>
            </div>
            <div class="con_ztpj11">
                <div class="con_ztpj_111">设备设施:</div>
                <div class="con_ztpj_2">
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                </div>
                <div class="con_ztpj_3">(一般)</div>
                <div><input type="hidden" name="support" value="3"></div>
                <div class="con_ztpj_111">环境卫生:</div>
                <div class="con_ztpj_2">
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                </div>
                <div class="con_ztpj_3">(好)</div>
                <div><input type="hidden" name="clean" value="3"></div>
            </div>
            <div class="con_ztpj11">
                <div class="con_ztpj_111">服务质量:</div>
                <div class="con_ztpj_2">
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                </div>
                <div class="con_ztpj_3">(非常好)</div>
                <div><input type="hidden" name="service" value="3"></div>
                <div class="con_ztpj_111">地理位置:</div>
                <div class="con_ztpj_2">
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                </div>
                <div class="con_ztpj_3">(差)</div>
                <div><input type="hidden" name="advantage" value="3"></div>
            </div>
            <div class="con_ztpj11">
                <div class="con_ztpj_111">餐饮服务:</div>
                <div class="con_ztpj_2">
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                </div>
                <div class="con_ztpj_3">(好)</div>
                <div><input type="hidden" name="food" value="3"></div>
                <div class="con_ztpj_111">性价比:</div>
                <div class="con_ztpj_2">
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_2.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                    <i  cjmark=""><img src="http://njy01.coosite.com/images/20160113_3.jpg" /></i>
                </div>
                <div class="con_ztpj_3">(一般)</div>
                <div><input type="hidden" name="appropriate" value="3"></div>
            </div>
            <div class="con_ztpj">
                <div class="con_ztpj_1"><span> * </span>入住感受:</div>
            </div>
            <div class="con_ztpj">
                <div class="con_ztpj_1">&nbsp;</div>
                <div class="con_lable">
                    <div class="con_lable_1">
                        <div class="lable_1_1">服务、客房、餐饮、交通……表扬或吐槽，畅所欲言</div>
                        <!--div class="lable_1_2"><input class="lable_btn" name="" type="button" /></div-->
                    </div>
                    <div class="con_lable_2"><textarea name="comments" cols="" rows="" class="lable_txt" id="comments" onblur="if ($('#comments').val()==''){$('#comments').val('最少20字，最多500字点评')}" onclick="if($('#comments').val('最少20字，最多500字点评'))$('#comments').val('')">最少20字，最多500字点评</textarea>
                    </div>
                </div>
            </div>
            <div class="lable_bot">已输入0字，还需要20字</div>
            <div class="lable_bot"><input type="submit" name="ok" value="提交" <%--src="http://njy01.coosite.com/images/20160113_5.jpg"--%>></div>
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
    </form>