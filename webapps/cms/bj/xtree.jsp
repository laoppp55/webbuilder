<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.service.ColumnService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.security.PermissionSet" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.bizwink.cms.security.Permission" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.news.ColumnTree" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.tree.node" %>
<%@ page import="com.bizwink.cms.util.FileUtil" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-6-6
  Time: 上午10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int siteid = authToken.getSiteID();
  int samsiteid = authToken.getSamSiteid();
  String userid = authToken.getUserID();
  int sitetype = authToken.getSitetype();
  int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
  String tbuf = null;
  Tree colTree = null;

  Column rootColumn = null;
  ApplicationContext appContext = SpringInit.getApplicationContext();
  if (appContext!=null) {
    ColumnService columnService = (ColumnService)appContext.getBean("columnService");
    rootColumn = columnService.getRootColumnBySiteid(BigDecimal.valueOf(siteid));
  }
  int rootColumnID = rootColumn.getID().intValue();
  if (sitetype == 0 || sitetype == 2) {                             //0表示自己创建的网站，2表示完整拷贝模板网站
    if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
      //普通用户
      List clist = new ArrayList();
      PermissionSet p_set = authToken.getPermissionSet();
      Iterator iter1 = p_set.elements();
      while (iter1.hasNext()) {
        Permission permission = (Permission) iter1.next();
        if (rightid == permission.getRightID()) {
          clist = permission.getColumnListOnRight();
          break;
        }
      }
      colTree = TreeManager.getInstance().getUserTree(userid, siteid, clist,rightid);
    } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
      //站点管理员
      colTree = TreeManager.getInstance().getSiteTree(siteid);
    }
  } else {                                                           //1表示共享模板网站的栏目和模板
    if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
      //普通用户
      List clist = new ArrayList();
      PermissionSet p_set = authToken.getPermissionSet();
      Iterator iter1 = p_set.elements();
      while (iter1.hasNext()) {
        Permission permission = (Permission) iter1.next();
        if (rightid == permission.getRightID()) {
          clist = permission.getColumnListOnRight();
          break;
        }
      }
      colTree = TreeManager.getInstance().getUserTree(userid, siteid, clist,rightid);
    } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
      //站点管理员
      colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
    }
  }

  //构建栏目树的JSON数据
  StringBuffer buf = new StringBuffer();                        //存储生成的菜单树
  if (colTree.getNodeNum() > 1) {
    node[] treeNodes = colTree.getAllNodes();                     //获取该树的所有节点
    List<Integer> pid = new ArrayList<Integer>();
    //int pid[] = new int[colTree.getNodeNum()];                    //遍历树所需要的节点数组，存储当前未处理的节点
    int currentID = 0;                                            //当前正在处理的节点
    int i = 0;                                                      //循环变量
    int[] ordernum = new int[colTree.getNodeNum()];               //当前节点所有子节点的顺序号
    int nodenum = 1;                                              //当前被处理节点的初始值
    int subnodenum = 1;                                              //判断当前节点是否有子节点
    int subnodenumOfParentNode = 0;

    pid.add(0);
    buf.append("    var json = [");
    while(pid.size()>0) {
      //处理当前节点
      currentID = pid.get(0);
      pid.remove(0);
      int currentNodeIndex = 0;
      int currentNode_ParentNodeIndex = 0;
      //获取当前节点
      for (i = 0; i < colTree.getNodeNum(); i++) {
        if (treeNodes[i].getId() == currentID) {
          currentNodeIndex = i;
          break;
        }
      }

      //获取当前节点的父节点
      for (i = 0; i < colTree.getNodeNum(); i++) {
        if (treeNodes[i].getId() == treeNodes[currentNodeIndex].getLinkPointer()) {
          currentNode_ParentNodeIndex = i;
          break;
        }
      }

      if (currentID != 0) {
        subnodenumOfParentNode = treeNodes[currentNode_ParentNodeIndex].getSubnodes()-1;
        treeNodes[currentNode_ParentNodeIndex].setSubnodes(subnodenumOfParentNode);
        buf.append("{title:\"" + treeNodes[currentNodeIndex].getChName() + "\",value:\"" + currentID + "\",data:[");
      }

      //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
      //设置所有子节点的父菜单名称，设置所有子节点的序列号，把所有的子节点存入pid数组中
      subnodenum = 0;
      nodenum = nodenum - 1;
      for (i = colTree.getNodeNum()-1; i >= 0 ; i--) {
        if (treeNodes[i].getLinkPointer() == currentID) {
          nodenum = nodenum + 1;
          pid.add(0,treeNodes[i].getId());
          ordernum[nodenum] = subnodenum;
          subnodenum = subnodenum + 1;
        }
      }

      //设置当前节点的子节点数量
      treeNodes[currentNodeIndex].setSubnodes(subnodenum);
      if (subnodenum == 0) {
        //当前节点是父节点的最后一个节点,循环向树形结构的上层进行查找，一直找到子节点数尚不是零的节点，从不是零的节点向下写节点的结束部分
        if (subnodenumOfParentNode==0) {
          //寻找当前节点向上的路径上子节点数不是零的节点
          int tnum = 0;
          int level = 0;
          while(tnum == 0 && treeNodes[currentNodeIndex].getLinkPointer()>0) {
            for (i = 0; i < colTree.getNodeNum(); i++) {
              if (treeNodes[i].getId() == treeNodes[currentNodeIndex].getLinkPointer()) {
                tnum = treeNodes[i].getSubnodes();
                if (tnum == 0) {
                  level = level + 1;
                  currentNodeIndex = i;
                  break;
                }
              }
            }
          }

          //从当前节点到根节点的路径上查找到第一个未处理子节点数不为零的节点，输出相应深度个数的“]}”字符串
          for(i=0; i<level; i++) buf.append("]}\r");

          buf.append("]},\r");
        } else {                         //当前节点不是父节点的最后一个节点
          buf.append("]},\r");
        }
      } else {
        buf.append("\r");
      }
    }
    //去掉字符串最后一个多余的“,”字符，增加json的关闭字符“];”
    int posi = buf.lastIndexOf(",");
    if (posi>-1) {
      buf.delete(posi,buf.length());
      buf.append("\r];");
    }
  }

  FileUtil.writeFile(buf,"c:\\data\\11111.txt");
  System.out.println(buf.toString());
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>layout 后台大布局 - Layui</title>
  <link rel="stylesheet" href="../design/css/layui.css">
  <link rel="stylesheet" href="mycss.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">layui 后台布局</div>
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="">控制台</a></li>
      <li class="layui-nav-item"><a href="">商品管理</a></li>
      <li class="layui-nav-item"><a href="">用户</a></li>
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="">邮件管理</a></dd>
          <dd><a href="">消息管理</a></dd>
          <dd><a href="">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          贤心
        </a>
        <dl class="layui-nav-child">
          <dd><a href="">基本资料</a></dd>
          <dd><a href="">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="">退了</a></li>
    </ul>
  </div>

  <div class="layui-side layui-bg-black"  style="top: 50px">
    <form class="layui-form">
    <div class="layui-side-scroll">
        <div id="xtree1" class="xtree_contianer"></div>
    </div>
    </form>
  </div>

  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;">内容主体区域<span cass=”icon”>&#xeb80;</span></div>
  </div>

  <div class="layui-footer">
    <!-- 底部固定区域 -->
    © layui.com - 底部固定区域
  </div>
</div>
<script src="../design/layui.js"></script>
<!--引用xtree-->
<script src="../design/layui-xtree.js"></script>
<script>
    //一套json数据下面会使用，同时供你参考
    var json = [
        {title:"www.bucgdx.com",value:"41",data:[
            {title:"学校简介",value:"42",data:[
                {title:"学校概况",value:"58",data:[]},
                {title:"组织机构",value:"59",data:[]},
                {title:"学校荣誉",value:"60",data:[]},
                {title:"学校资质",value:"61",data:[]},
                {title:"学校领导",value:"62",data:[]},
                {title:"招聘信息",value:"63",data:[]}
            ]},
            {title:"通知通告",value:"43",data:[
                {title:"通知",value:"64",data:[]},
                {title:"公告",value:"65",data:[]}
            ]},
            {title:"新闻中心",value:"44",data:[
                {title:"学校新闻",value:"45",data:[]},
                {title:"培训新闻",value:"46",data:[]},
                {title:"行业新闻",value:"66",data:[]},
                {title:"我的新闻",value:"143",data:[]}
            ]},
            {title:"党校教育",value:"47",data:[
                {title:"企业领导干部培训",value:"48",data:[]},
                {title:"党员发展对象培训",value:"49",data:[]},
                {title:"入党积极分子培训",value:"50",data:[]}
            ]},
            {title:"城建商学院",value:"51",data:[
                {title:"企业紧缺人才培训",value:"67",data:[]},
                {title:"继续教育培训",value:"68",data:[]}
            ]},
            {title:"新技术推广",value:"52",data:[
                {title:"“四新”技术推广",value:"69",data:[]},
                {title:"BIM培训",value:"70",data:[]}
            ]},
            {title:"成人学历教育",value:"53",data:[
                {title:"中央党校研究生班",value:"71",data:[]},
                {title:"奥鹏学历教育",value:"72",data:[]},
                {title:"其他学历教育",value:"73",data:[]}
            ]},
            {title:"行业培训",value:"54",data:[
                {title:"注册类培训",value:"74",data:[
                    {title:"一级注册建造师",value:"78",data:[
                        {title:"建筑工程",value:"111",data:[]},
                        {title:"公路工程",value:"112",data:[]},
                        {title:"铁路工程",value:"113",data:[]},
                        {title:"民航机场工程",value:"114",data:[]},
                        {title:"港口与航道工程",value:"115",data:[]},
                        {title:"水利水电工程",value:"116",data:[]},
                        {title:"矿业工程",value:"117",data:[]},
                        {title:"市政公用工程",value:"118",data:[]},
                        {title:"通信与广电工程",value:"119",data:[]},
                        {title:"机电工程",value:"120",data:[]}
                    ]},
                    {title:"二级注册建造师",value:"79",data:[
                        {title:"建筑工程",value:"121",data:[]},
                        {title:"公路工程",value:"122",data:[]}
                    ]},
                    {title:"一级注册造价师",value:"80",data:[]},
                    {title:"二级注册造价师",value:"81",data:[]},
                    {title:"其它",value:"82",data:[]}
                ]},
                {title:"安全三类人员培训",value:"75",data:[]},
                {title:"现场管理人员培训",value:"76",data:[
                    {title:"施工员",value:"83",data:[]},
                    {title:"质检员",value:"84",data:[]},
                    {title:"材料员",value:"85",data:[]},
                    {title:"资料员",value:"86",data:[]},
                    {title:"测量验线员",value:"87",data:[]},
                    {title:"劳动力管理员",value:"88",data:[]},
                    {title:"试验员",value:"89",data:[]},
                    {title:"机械员",value:"90",data:[]},
                    {title:"监理员",value:"91",data:[]},
                    {title:"预算员",value:"92",data:[]}
                ]},
                {title:"现场操作人员培训",value:"77",data:[
                    {title:"技术工人",value:"93",data:[
                        {title:"木工",value:"95",data:[]},
                        {title:"抹灰工",value:"96",data:[]},
                        {title:"管道工",value:"97",data:[]},
                        {title:"工程电气设备安装调试工",value:"98",data:[]},
                        {title:"建筑油漆工",value:"99",data:[]},
                        {title:"测量放线工",value:"100",data:[]},
                        {title:"瓦工",value:"101",data:[]},
                        {title:"钢筋工",value:"102",data:[]}
                    ]},
                    {title:"特种作业操作人员",value:"94",data:[
                        {title:"塔式起重机司机",value:"103",data:[]},
                        {title:"施工外梯司机",value:"104",data:[]},
                        {title:"信号司索工",value:"105",data:[]},
                        {title:"建筑架子工P",value:"106",data:[]},
                        {title:"建筑起重机械安装拆卸工",value:"107",data:[]},
                        {title:"建筑电工",value:"108",data:[]},
                        {title:"有限空间作业",value:"109",data:[]},
                        {title:"电焊工",value:"110",data:[]}
                    ]}
                ]}
            ]},
            {title:"联系我们",value:"55",data:[]},
            {title:"相关链接",value:"56",data:[]},
            {title:"合作伙伴",value:"57",data:[]},
            {title:"测试修改栏目",value:"123",data:[]},
            {title:"包含文件",value:"161",data:[]}
        ]}
    ];

    //layui的form模块是必须的,用于支持xtree模块
    layui.use(['form'], function () {
        var form = layui.form;

        //1、最基础的用法 - 直接绑定json
        var xtree1 = new layuiXtree({
            elem: 'xtree1'   //(必填) 放置xtree的容器，样式参照 .xtree_contianer
            , form: form     //(必填) layui 的 from
            , data: json     //(必填) json数据
        });

        /*
        //2、最基础的用法 - 异步加载
        var xtree2 = new layuiXtree({
            elem: 'xtree2'                  //(必填) 放置xtree的容器，样式参照 .xtree_contianer
            , form: form                    //(必填) layui 的 from
            , data: 'server/xtreedata.ashx' //(必填) 数据接口，需要返回以上结构的json字符串
        });

        //3、最完整的参数用法
        var xtree3 = new layuiXtree({
            elem: 'xtree3'                  //必填三兄弟之老大
            , form: form                    //必填三兄弟之这才是真老大
            , data: 'server/xtreedata2.ashx' //必填三兄弟之这也算是老大
            , isopen: false  //加载完毕后的展开状态，默认值：true
            , ckall: true    //启用全选功能，默认值：false
            , ckallback: function () { } //全选框状态改变后执行的回调函数
            , icon: {        //三种图标样式，更改几个都可以，用的是layui的图标
                open: "&#xe7a0;"       //节点打开的图标
                , close: "&#xe622;"    //节点关闭的图标
                , end: "&#xe621;"      //末尾节点的图标
            }
            , color: {       //三种图标颜色，独立配色，更改几个都可以
                open: "#EE9A00"        //节点图标打开的颜色
                , close: "#EEC591"     //节点图标关闭的颜色
                , end: "#828282"       //末级节点图标的颜色
            }
            , click: function (data) {  //节点选中状态改变事件监听，全选框有自己的监听事件
                console.log(data.elem); //得到checkbox原始DOM对象
                console.log(data.elem.checked); //开关是否开启，true或者false
                console.log(data.value); //开关value值，也可以通过data.elem.value得到
                console.log(data.othis); //得到美化后的DOM对象
            }
        });*/

        //提供的方法们
        //获取全部[选中的][末级节点]原checkbox DOM对象，返回Array
        document.getElementById('btn1').onclick = function () {
            var oCks = xtree3.GetChecked(); //这是方法
            for (var i = 0; i < oCks.length; i++) {
                console.log(oCks[i].value);
            }
        }

        //获取全部原checkbox DOM对象，返回Array
        document.getElementById('btn2').onclick = function () {
            var oCks = xtree3.GetAllCheckBox(); //这是方法
            for (var i = 0; i < oCks.length; i++) {
                console.log(oCks[i].value);
            }
        }

        //更新数据，重新渲染
        document.getElementById('btn3').onclick = function () {
            xtree3.render();
        }

        //通过value值找父级DOM对象，顶级节点与错误值均返回null
        document.getElementById('btn4').onclick = function () {
            var oCks = xtree3.GetParent(document.getElementById('txt1').value);  //这是方法
            if (oCks != null) { //如果返回的对象不为null，则获取到父节点了，如果为null，说明这个值对应的节点是一级节点或是值错误
                console.log(oCks.value);
            }
            else {
                console.log('无父级节点或value值错误');
            }
        }
    });

    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });

    //Demo
    //layui.use(['tree', 'layer'], function(){
    /*layui.use(['element', 'tree', 'layer'], function(){
        var layer = layui.layer
            ,$ = layui.jquery;
        var element = layui.element;
        var jsonStr;
        var url = "r?wf_num=T_bhlayui_001";

        //添加选中颜色显示
        $("body").on("mousedown", ".layui-tree a", function() {
            if(!$(this).siblings('ul').length) {
                $(".layui-tree a").removeClass('active');
                $(this).find('cite').parent().addClass('active');
            }
        });

        layui.tree({
            elem: '#demo1' //指定元素
            ,skin: 'sidebar'
            //,skin: 'shihuang'
            ,target: '_blank' //是否新选项卡打开（比如节点返回href才有效）
            ,click: function(item){ //点击节点回调
                layer.msg('当前节名称：'+ item.name + '<br>全部参数：'+ JSON.stringify(item));
                console.log(item);
            }
            ,nodes: [ //节点
                { name: '常用文件夹',id: 1,alias: 'changyong',children: [
                    {name: '所有未读（设置跳转）',id: 11,href: 'http://www.layui.com/',alias: 'weidu'},
                    {name: '置顶邮件',id: 12},
                    {name: '标签邮件',id: 13}
                ]},
                {name: '我的邮箱',id: 2,spread: true,children: [
                    {name: 'QQ邮箱',id: 21,spread: true,children: [
                        {name: '收件箱',id: 211,children: [
                            {name: '所有未读',id: 2111},
                            {name: '置顶邮件',id: 2112},
                            {name: '标签邮件',id: 2113}
                        ]},
                        {name: '已发出的邮件',id: 212},
                        {name: '垃圾邮件',id: 213}
                    ]},
                    {name: '阿里云邮',id: 22,children: [
                        {name: '收件箱',id: 221},
                        {name: '已发出的邮件',id: 222},
                        {name: '垃圾邮件',id: 223}
                    ]}
                ]},
                {name: '收藏夹',id: 3,alias: 'changyong',children: [
                    {name: '爱情动作片',id: 31,alias: 'love'},
                    {name: '技术栈',id: 12,children: [
                        {name: '前端',id: 121},
                        {name: '全端',id: 122}
                    ]}
                ]}
            ]
        });


        //生成一个模拟树
        /*var createTree = function(node, start){
          node = node || function(){
            var arr = [];
            for(var i = 1; i < 10; i++){
              arr.push({
                name: i.toString().replace(/(\d)/, '$1$1$1$1$1$1$1$1$1')
              });
            }
            return arr;
          }();
          start = start || 1;
          layui.each(node, function(index, item){
            if(start < 10 && index < 9){
              var child = [
                {
                  name: (1 + index + start).toString().replace(/(\d)/, '$1$1$1$1$1$1$1$1$1')
                }
              ];
              node[index].children = child;
              createTree(child, index + start + 1);
            }
          });
          return node;
        };

        layui.tree({
          elem: '#demo2' //指定元素
          ,nodes: createTree()
        });

    });*/
</script>
</body>
</html>
