<div class="top">
    <!--顶部导航栏-->
    <div class="top_auto">
         <div class="login-info" id="nologinview1">
                <em>欢迎来到长城云采</em>
                <a href="#" class="sn-login">请登录</a>
                <a href="#" class="sn-register">免费注册</a>
        </div>
        <div class="login-info" hidden="true" id="loginview10">
        	<em id="loginview4"></em>&nbsp;&nbsp;
        	<a id="loginview1" style="color: red;size: 5px"></a>&nbsp;
        	<a id="loginview5" onclick="loginOut()" class="pointer" style="color: black;size: 5px"></a>
        </div>
        <div class="top_quick_menu">
            <div id="jumptoecaiDiv" class="left">
                <a id="jumptoecai" onclick="junptoecai()" href="javascript:void(0);" class="menu-hd">我的业务平台</a>
            </div>
            <div class="left client-service">
                <a href="#" class="menu-hd">客服中心</a>
                <i class="arrow_down"><img src="../img/icon_arrow_down.png"/></i>
                <i class="arrow_up"><img src="../img/icon_arrow_up.png" alt=""/></i>
                <ul class="helpmessage">
                    <li><a href="javascript:;">新手入门</a></li>
                    <li><a href="javascript:;">联系客服</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--顶部 log和顶部搜索框-->
<div class="top-center">
    <div class="header-con">
        <div class="mall-login">
            <img src="../img/logo.png" class="mlogo" alt=""/>
        </div>
        <div class="search-box">
            <ul class="hot-query">
                <li class="query-list"><a class="chooseQuery" style="color: #ef7800;" href="#">商机信息</a></li>
                <li class="query-list"><a href="#">供应信息</a></li>
                <li class="query-list"><a href="#">供应商</a></li>
            </ul>
            <div id="mall-search" class="mall-search">
                    <input id="searchtext" type="text" placeholder="输入物资名称、供应商名称等 " class="search-m-input"/>
                    <div id="search-btn" class="search-btn pointer"><img src="../img/icon_search.png" alt=""/></div>

            </div>
        </div>
        <div class="header-tel">
            <img src="../img/tel2.png" alt=""/>
        </div>
        <div class="mobil-two-bar-codes">
            <img src="../img/downloadAPP.png" class="two-bar-codes" alt=""/>
        </div>
    </div>
</div>
<div class="nav">
    <div class="nav-auto">
        <div class="nav-lists">
            <a href="#" class="nav-list navselectflag nav-selected">首页</a>
            <a href="#" class="nav-list">采购商</a>
            <a id="materiamanager" href="javascript:void(0);" class="nav-list">物料管理</a>
            <a id="supplier" onclick="jumptosupp()" href="javascript:void(0);" class="nav-list">供应商</a>
            <a href="#" onclick="jumptosjh()" id="sjh-web" class="nav-list">商机信息</a>
            <a href="#" class="nav-list">关于我们</a>
        </div>
    </div>
</div>