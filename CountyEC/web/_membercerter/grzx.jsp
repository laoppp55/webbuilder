<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.SiteinfoService" %>
<%@ page import="com.bizwink.po.Siteinfo" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-1-30
  Time: 下午7:27
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = null;

    //入股SESSION失效，试试COOKIE是否可以读出来
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
        if (authToken == null) {
            int posi = cookie_value.indexOf("-");
            username = cookie_value.substring(0,posi);
        } else {
            username =authToken.getUsername();
        }
    } else {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
        if (authToken != null) {
            SessionUtil.removeUserAuthorization(response, session);
        }
    }

    Siteinfo siteinfo = null;
    Users user = null;
    if (username != null) {
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            SiteinfoService siteinfoService = (SiteinfoService)appContext.getBean("SiteinfoService");
            siteinfo = siteinfoService.selectBySitename(username + ".gugulx.com");
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            user = usersService.getByUserId(username);
        }
    } else {
        response.sendRedirect("http://www.gugulx.com/_members/login.jsp");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>游客用户中心--个人信息</title>
    <link href="/css/grzx_css.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script src="/js/iepng.js" type="text/javascript"></script>
    <script type="text/javascript"  src="http://www.gugulx.com/js/index_png.js" ></script>
    <![endif]-->
    <script type="text/javascript" src="http://www.gugulx.com/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript"  src="http://www.gugulx.com/js/qiehuan.js" ></script>
    <script language="javascript">
        function logoff() {
            $.post("/_members/logoff.jsp",{},
                    function(data) {
                        if (data.indexOf("<logoutflag>") > -1) {
                            $("#welcomeid").html("您好，欢迎来到周边旅游行网！");
                            $("#funid").html("<a href=\"/_members/zhuce.jsp\">注册</a> | <a href=\"/_members/login.jsp\">登录</a>");
                            $("#exitid").css("display","none");
                        }
                    }
            )
        }

        function updateinfo(type) {
            var val = "";

            if (type=="mphone")
                val = $("input[ name='mphone']").val();
            else if (type=="email")
                val = $("input[ name='email']").val();
            else if (type=="njyname")
                val = $("input[ name='njyname']").val();
            else if (type=="contactor")
                val = $("input[ name='contactor']").val();
            else if (type=="address")
                val = $("input[ name='address']").val();
            else if (type=="pass")
                val = $("input[ name='pass']").val();

            $.ajax({
                url : 'updateBaseinfo.jsp',
                type : 'post',
                async: false,                        //使用同步的方式,true为异步方式
                data:{
                    type:type,
                    info:encodeURI(val)
                },
                success : function(data){
                    if (data.indexOf("true") > -1) {
                        if (type=="mphone")
                            alert("用户手机号码修改成功");
                        else if (type=="email")
                            alert("用户电子邮件地址修改成功");
                        else if (type=="njyname")
                            alert(myname + "名称修改成功");
                        else if (type=="contactor")
                            alert("联系人信息修改成功");
                        else if (type=="address")
                            alert(myname + "详细地址信息修改成功");
                        else if (type=="pass")
                            alert("用户口令修改成功");
                    } else {
                        alert("修改" + val + "信息失败，可能" + val + "已经被注册");
                    }
                },
                fail:function(){
                }
            });
        }
    </script>
</head>

<body>
<div class="box">
    <div class="tit_2016">
        <div class="tit_2016_1" id="welcomeid">用户中心</div>
        <div class="tit_2016_2"  id="funid">用户昵称：<%=(user.getNICKNAME()==null)?"":user.getNICKNAME()%></div>
        <div class="tit_2016_3" id="exitid">[<a href="javascript:logoff();">退出</a>]</div>
    </div>
    <div class="clear"></div>
</div>
<div class="box">
<div class="neirong">
<div class="ny_left">
    <div class="biaoti">账户设置</div>
    <div class="list">
        <ul id="_ul">
            <li class="ts"><a href="#">个人资料</a></li>
            <li><a href="#">我的订单</a></li>
            <li><a href="#">我的留言</a></li>
            <li><a href="#">我的点评</a></li>
        </ul>
    </div>
</div>
<div class="ny_right" id="_div">
<div style="display:block" id="_div0">
    <div class="grzx_1">
        <div class="grzx_1_bt">个人资料</div>
        <div class="grzx_1_sub">&nbsp;</div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="grzx_1_0">用户头像：</div>
        <div class="grzx_1_1" style="padding-top:30px;">我的头像：</div>
        <div class="grzx_1_2"><img src="/images/20150527_8.png" /></div>
        <div class="grzx_1_3" style="padding-top:30px;"><a href="#">修改头像</a></div>
        <div class="grzx_1_4" style="padding-top:30px;">(上传真实头像，增加信任感)</div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="grzx_1_0">联系方式：</div>
        <div class="grzx_1_1"><span>*</span>手机号：</div>
        <div class="grzx_1_2"><input name="mphone" type="text" class="grzx_btn3" value="<%=(user.getMPHONE()==null)?"":user.getMPHONE()%>" /></div>
        <div class="grzx_1_3"><a href="#" onclick="javascript:updateinfo('mphone');">修改</a></div>
        <div class="grzx_1_4">&nbsp;</div>
        <div class="clear"></div>
        <div class="grzx_1_1"><span>*</span>邮箱：</div>
        <div class="grzx_1_2"><input name="email" type="text" class="grzx_btn3" value="<%=(user.getEMAIL()==null)?"":user.getEMAIL()%>" /></div>
        <div class="grzx_1_3"><a href="#" onclick="javascript:updateinfo('email');">修改</a></div>
        <div class="grzx_1_4">&nbsp;</div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="grzx_1_0">个人信息：</div>
        <div class="grzx_1_1"><span>*</span>真实姓名：</div>
        <div class="grzx_1_2"><input name="" type="text" class="grzx_btn3" value="张三" /></div>
        <div class="grzx_1_3"><a href="#">修改</a></div>
        <div class="grzx_1_4">（自己的真实姓名）</div>
        <div class="clear"></div>
        <div class="grzx_1_1"><span>*</span>证件号码：</div>
        <div class="grzx_1_2"><input name="" type="text" class="grzx_btn3" value="135406198211301628" /></div>
        <div class="grzx_1_3"><a href="#">修改</a></div>
        <div class="grzx_1_4">（证件号码，下单更加快捷方便）</div>
        <div class="grzx_1_1"><span>*</span>性别：</div>
        <div class="grzx_1_2"><input name="" type="text" class="grzx_btn3" value="女" /></div>
        <div class="grzx_1_3"><a href="#">修改</a></div>
        <div class="grzx_1_4">（修改自己的性别）</div>
        <div class="clear"></div>
        <div class="grzx_1_1"><span>*</span>地址：</div>
        <div class="grzx_1_2"><input name="" type="text" class="grzx_btn3" value="马上填写" /></div>
        <div class="grzx_1_3"><a href="#">修改</a></div>
        <div class="grzx_1_4">（自己的地址，有机会获得更多惊喜）</div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="grzx_1_0">修改密码：</div>
        <div class="grzx_1_1"><span>*</span>密码：</div>
        <div class="grzx_1_2"><input name="" type="text" class="grzx_btn3" value="************" /></div>
        <div class="grzx_1_3"><a href="#">修改</a></div>
        <div class="grzx_1_4">&nbsp;</div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2"><input class="grzx_btn" name="" type="button" /><input class="grzx_btn111" name="" type="button" /></div>
</div>
<div style="display:none" id="_div1">
    <div class="grzx_1">
        <div class="grzx_1_bt">我的订单</div>
        <div class="grzx_1_sub">&nbsp;</div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="grzx_ly">
            <div class="grzx_ly_1">农家院信息</div>
            <div class="grzx_ly_2">出游时间</div>
            <div class="grzx_ly_3">房间类型</div>
            <div class="grzx_ly_4">订单金额</div>
        </div>
    </div>
    <div class="grzx_2">
        <div class="grzx_ly_con">
            <div class="grzx_ly_tit">
                <div class="grzx_ly_btn"><input name="" type="radio" value="" /></div>
                <div class="grzx_ly_wz">雾灵山老兵之家 </div>
                <div class="grzx_ly_wz1">预订时间：2016-01-20</div>
            </div>
            <div class="grzx_ly_zs">
                <div class="grzx_ly_zs_1"><img src="/images/20160120_3.jpg" /></div>
                <div class="grzx_ly_zs_2">密云县其他 新城子曹家路雾灵山北门大沟村  </div>
                <div class="grzx_ly_zs_3">2016-01-12</div>
                <div class="grzx_ly_zs_4">双人间</div>
                <div class="grzx_ly_zs_5">￥128</div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="grzx_2">
        <div class="grzx_ly_con">
            <div class="grzx_ly_tit">
                <div class="grzx_ly_btn"><input name="" type="radio" value="" /></div>
                <div class="grzx_ly_wz">雾灵山老兵之家 </div>
                <div class="grzx_ly_wz1">预订时间：2016-01-20</div>
            </div>
            <div class="grzx_ly_zs">
                <div class="grzx_ly_zs_1"><img src="/images/20160120_3.jpg" /></div>
                <div class="grzx_ly_zs_2">密云县其他 新城子曹家路雾灵山北门大沟村  </div>
                <div class="grzx_ly_zs_3">2016-01-12</div>
                <div class="grzx_ly_zs_4">双人间</div>
                <div class="grzx_ly_zs_5">￥128</div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="grzx_2">
        <div class="grzx_ly_con">
            <div class="grzx_ly_tit">
                <div class="grzx_ly_btn"><input name="" type="radio" value="" /></div>
                <div class="grzx_ly_wz">雾灵山老兵之家 </div>
                <div class="grzx_ly_wz1">预订时间：2016-01-20</div>
            </div>
            <div class="grzx_ly_zs">
                <div class="grzx_ly_zs_1"><img src="/images/20160120_3.jpg" /></div>
                <div class="grzx_ly_zs_2">密云县其他 新城子曹家路雾灵山北门大沟村  </div>
                <div class="grzx_ly_zs_3">2016-01-12</div>
                <div class="grzx_ly_zs_4">双人间</div>
                <div class="grzx_ly_zs_5">￥128</div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="grzx_2">
        <div class="grzx_ly_con">
            <div class="grzx_ly_tit">
                <div class="grzx_ly_btn"><input name="" type="radio" value="" /></div>
                <div class="grzx_ly_wz">雾灵山老兵之家 </div>
                <div class="grzx_ly_wz1">预订时间：2016-01-20</div>
            </div>
            <div class="grzx_ly_zs">
                <div class="grzx_ly_zs_1"><img src="/images/20160120_3.jpg" /></div>
                <div class="grzx_ly_zs_2">密云县其他 新城子曹家路雾灵山北门大沟村  </div>
                <div class="grzx_ly_zs_3">2016-01-12</div>
                <div class="grzx_ly_zs_4">双人间</div>
                <div class="grzx_ly_zs_5">￥128</div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="grzx_2">
        <div class="grzx_bottom_1"><input name="" type="checkbox" value="" /></div>
        <div class="grzx_bottom_2">全选</div>
        <div class="grzx_bottom_3"><input name="" class="plsc_btn" type="button" /></div>
        <div class="grzx_bottom_4">
            <div class="page_1"><a href="#"><img src="/images/20160120_6.jpg" /></a></div>
            <div class="page_2">
                <ul>
                    <li class="page_ts"><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">…</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="page_3"><a href="#"><img src="/images/20160120_7.jpg" /></a></div>
            <div class="page_4">共11页，</div>
            <div class="page_5">到第<input value="10" name="" type="text" class="grzx_btn1" />页</div>
            <div class="page_6"><input name="" type="button" class="grzx_btn2" /></div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div style="display:none" id="_div2">
    <div class="grzx_1">
        <div class="grzx_1_bt">我的留言</div>
        <div class="grzx_1_sub">&nbsp;</div>
        <div class="clear"></div>
    </div>
    <div class="grzx_222">
        <div class="grzx_ly1">
            <div class="grzx_pic"><img src="/images/20160120_3.jpg" /></div>
            <div class="grzx_pic_wz">
                <div class="grzx_pic_tit">司马台古北水镇龙祥人家农家院</div>
                <div class="grzx_pic_zy">农家院可提供30人住宿；客房有双人标间价格180元/间（平时），周末220元/间；节假日价格300元/间… <a href="#">更多详情>></a></div>
            </div>
        </div>
        <div class="grzx_dp">全部留言：</div>
        <div class="grzx_lylb">
            <div class="grzx_lylb_1"><img src="/images/20150527_9.png" /><br />34342323</div>
            <div class="grzx_lylb_2">
                <div class="grzx_lylb_nav">司马台古北水镇龙祥人家农家院周边有什么采摘园吗？是否有停车位？我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>
                <div class="grzx_lylb_date">发布日期：2016-01-20 上午 11:04:12</div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="grzx_lylb">
            <div class="grzx_lylb_1"><img src="/images/20150527_9.png" /><br />34342323</div>
            <div class="grzx_lylb_2">
                <div class="grzx_lylb_nav">是否有停车位？我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>
                <div class="grzx_lylb_date">发布日期：2016-01-20 上午 11:04:12</div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="grzx_lylb">
            <div class="grzx_lylb_1"><img src="/images/20150527_9.png" /><br />34342323</div>
            <div class="grzx_lylb_2">
                <div class="grzx_lylb_nav">我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>
                <div class="grzx_lylb_date">发布日期：2016-01-20 上午 11:04:12</div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="grzx_222">
        <div class="grzx_ly1">
            <div class="grzx_pic"><img src="/images/20160120_9.jpg" /></div>
            <div class="grzx_pic_wz">
                <div class="grzx_pic_tit">龙庆峡春园福满宁家园</div>
                <div class="grzx_pic_zy">农家院可提供30人住宿；客房有双人标间价格180元/间（平时），周末220元/间；节假日价格300元/间… <a href="#">更多详情>></a></div>
            </div>
        </div>
        <div class="grzx_dp">全部留言：</div>
        <div class="grzx_lylb">
            <div class="grzx_lylb_1"><img src="/images/20150527_9.png" /><br />34342323</div>
            <div class="grzx_lylb_2">
                <div class="grzx_lylb_nav">我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>
                <div class="grzx_lylb_date">发布日期：2016-01-20 上午 11:04:12</div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="grzx_lylb">
            <div class="grzx_lylb_1"><img src="/images/20150527_9.png" /><br />34342323</div>
            <div class="grzx_lylb_2">
                <div class="grzx_lylb_nav">司马台古北水镇龙祥人家农家院周边有什么采摘园吗？是否有停车位？我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>
                <div class="grzx_lylb_date">发布日期：2016-01-20 上午 11:04:12</div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="grzx_bottom_1"><input name="" type="checkbox" value="" /></div>
        <div class="grzx_bottom_2">全选</div>
        <div class="grzx_bottom_3"><input name="" class="plsc_btn" type="button" /></div>
        <div class="grzx_bottom_4">
            <div class="page_1"><a href="#"><img src="/images/20160120_6.jpg" /></a></div>
            <div class="page_2">
                <ul>
                    <li class="page_ts"><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">…</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="page_3"><a href="#"><img src="/images/20160120_7.jpg" /></a></div>
            <div class="page_4">共11页，</div>
            <div class="page_5">到第<input value="10" name="" type="text" class="grzx_btn1" />页</div>
            <div class="page_6"><input name="" type="button" class="grzx_btn2" /></div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div style="display:none" id="_div3">
    <div class="grzx_1">
        <div class="grzx_1_bt">我的点评</div>
        <div class="grzx_1_sub">&nbsp;</div>
        <div class="clear"></div>
    </div>
    <div class="grzx_222">
        <div class="grzx_ly1">
            <div class="grzx_pic"><img src="/images/20160120_3.jpg" /></div>
            <div class="grzx_pic_wz">
                <div class="grzx_pic_tit">司马台古北水镇龙祥人家农家院</div>
                <div class="grzx_pic_zy">农家院可提供30人住宿；客房有双人标间价格180元/间（平时），周末220元/间；节假日价格300元/间… <a href="#">更多详情>></a></div>
            </div>
        </div>
        <div class="grzx_dp">全部点评：</div>
        <div class="grzx_lylb">
            <div class="grzx_lylb_1"><img src="/images/20150527_9.png" /><br />34342323</div>
            <div class="grzx_lylb_2">
                <div class="grzx_lylb_nav">古北水镇真的很好玩，有着千年历史的古北水镇，古朴、典雅、风景如画。古北水镇还有许多家农家院，也非常不错，有机会大家可以去游玩一次。</div>
                <div class="grzx_lylb_date">发布日期：2016-01-20 上午 11:04:12</div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="grzx_222">
        <div class="grzx_ly1">
            <div class="grzx_pic"><img src="/images/20160120_3.jpg" /></div>
            <div class="grzx_pic_wz">
                <div class="grzx_pic_tit">司马台古北水镇龙祥人家农家院</div>
                <div class="grzx_pic_zy">农家院可提供30人住宿；客房有双人标间价格180元/间（平时），周末220元/间；节假日价格300元/间… <a href="#">更多详情>></a></div>
            </div>
        </div>
        <div class="grzx_dp">全部点评：</div>
        <div class="grzx_lylb">
            <div class="grzx_lylb_1"><img src="/images/20150527_9.png" /><br />34342323</div>
            <div class="grzx_lylb_2">
                <div class="grzx_lylb_nav">古北水镇真的很好玩，有着千年历史的古北水镇，古朴、典雅、风景如画。古北水镇还有许多家农家院，也非常不错，有机会大家可以去游玩一次。</div>
                <div class="grzx_lylb_date">发布日期：2016-01-20 上午 11:04:12</div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="grzx_bottom_1"><input name="" type="checkbox" value="" /></div>
        <div class="grzx_bottom_2">全选</div>
        <div class="grzx_bottom_3"><input name="" class="plsc_btn" type="button" /></div>
        <div class="grzx_bottom_4">
            <div class="page_1"><a href="#"><img src="/images/20160120_6.jpg" /></a></div>
            <div class="page_2">
                <ul>
                    <li class="page_ts"><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">…</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="page_3"><a href="#"><img src="/images/20160120_7.jpg" /></a></div>
            <div class="page_4">共11页，</div>
            <div class="page_5">到第<input value="10" name="" type="text" class="grzx_btn1" />页</div>
            <div class="page_6"><input name="" type="button" class="grzx_btn2" /></div>
        </div>
        <div class="clear"></div>
    </div>
</div>
</div>
<div class="clear"></div>
</div>
</div>
</body>
</html>
