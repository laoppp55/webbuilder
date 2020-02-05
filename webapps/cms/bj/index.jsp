<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.service.ColumnService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.security.PermissionSet" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.bizwink.cms.security.Permission" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
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
    int currentID = 0;                                            //当前正在处理的节点
    int i = 0;                                                      //循环变量
    int[] ordernum = new int[colTree.getNodeNum()];               //当前节点所有子节点的顺序号
    int nodenum = 1;                                              //当前被处理节点的初始值
    int subnodenum = 1;                                              //判断当前节点是否有子节点
    int subnodenumOfParentNode = 0;

    pid.add(0);
    buf.append("nodes: [");
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

      //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
      //设置所有子节点的父菜单名称，设置所有子节点的序列号，把所有的子节点存入pid数组中
      subnodenum = 0;
      nodenum = nodenum - 1;
      for (i = colTree.getNodeNum() - 1; i >= 0; i--) {
        if (treeNodes[i].getLinkPointer() == currentID) {
          nodenum = nodenum + 1;
          pid.add(0, treeNodes[i].getId());
          ordernum[nodenum] = subnodenum;
          subnodenum = subnodenum + 1;
        }
      }

      //处理当前节点
      if (currentID != 0) {
        //当前节点的父节点的子节点数量减1
        subnodenumOfParentNode = treeNodes[currentNode_ParentNodeIndex].getSubnodes() - 1;
        //修改当前节点父节点未处理的子节点的数量
        treeNodes[currentNode_ParentNodeIndex].setSubnodes(subnodenumOfParentNode);
        //设置当前节点子节点的数量
        treeNodes[currentNodeIndex].setSubnodes(subnodenum);
        //处理当前节点没有子节点的情况
        if (subnodenum == 0) {
          //当前节点是父节点的最后一个节点,循环向树形结构的上层进行查找，一直找到子节点数尚不是零的节点，从不是零的节点向下写节点的结束部分
          if (subnodenumOfParentNode == 0) {
            buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + ",alias:'" + treeNodes[currentNodeIndex].getEnName() + "'}\r");
            //寻找当前节点向上的路径上子节点数不是零的节点
            int tnum = 0;
            int level = 0;
            while (tnum == 0 && treeNodes[currentNodeIndex].getLinkPointer() > 0) {
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
            for (i = 0; i < level-1; i++) buf.append("]}\r");

            buf.append("]},\r");
          } else {                         //当前节点不是父节点的最后一个节点
            buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + ",alias:'" + treeNodes[currentNodeIndex].getEnName() + "'},\r");
          }
        } else {
          buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + ",alias:'" + treeNodes[currentNodeIndex].getEnName() + "'," + "children:[\r");
        }
      }
    }
    //去掉字符串最后一个多余的“,”字符，增加json的关闭字符“];”
    int posi = buf.lastIndexOf(",");
    if (posi>-1) {
      buf.delete(posi,buf.length());
      buf.append("\r]");
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
  <title>网站内容管理系统</title>
  <link rel="stylesheet" href="../design/css/layui.css">
  <link rel="stylesheet" href="mycss.css">
  <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">网站内容管理系统</div>
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="">栏目管理</a></li>
      <li class="layui-nav-item"><a href="">模板管理</a></li>
      <li class="layui-nav-item"><a href="">文章管理</a></li>
      <li class="layui-nav-item"><a href="javascript:;">文章迁移</a></li>
      <li class="layui-nav-item"><a href="javascript:;">文章审核</a></li>
      <li class="layui-nav-item"><a href="javascript:;">文章发布</a></li>
      <li class="layui-nav-item"><a href="javascript:;">系统设置</a></li>
      <li class="layui-nav-item"><a href="javascript:;">工具箱</a></li>
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
    <div style="overflow-y:auto">
      <ul id="demo1"></ul>
    </div>
  </div>

  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div id="mytools" style="padding: 5px;background-color: #0b84d1">
      <span cass="icon">文章管理>>>>></span>
      <span cass="icon" style="float: right">新建 上传文件 批量上传图片 引用 退稿 在审 未用 归档 文件夹管理</span>
    </div>
    <div id="articlelist">
      文章列表区域
    </div>
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
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });

    //Demo
    //layui.use(['tree', 'layer'], function(){
    layui.use(['element', 'tree', 'layer'], function(){
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
            //,skin: 'sidebar'
            ,skin: 'dx'
            ,target: '_blank' //是否新选项卡打开（比如节点返回href才有效）
            ,click: function(item){ //点击节点回调
                htmlobj=$.ajax({
                    url:"articlesnew.jsp",
                    dataType:'json',
                    async:false,
                    data:{
                        column:item.id,
                        start:0
                    },
                    success:function(data){

                    }
                });
                layer.msg('当前节名称：'+ item.name + "==" + item.id + '<br>全部参数：'+ JSON.stringify(item));
                console.log(item);
            }
            ,nodes: [{name:'www.bucgdx.com',id:41,alias:'root',spread: true,children:[
                {name:'学校简介',id:42,alias:'introduction',children:[
                    {name:'学校概况',id:58,alias:'brief'},
                    {name:'组织机构',id:59,alias:'org'},
                    {name:'学校荣誉',id:60,alias:'honour'},
                    {name:'学校资质',id:61,alias:'xxzz'},
                    {name:'学校领导',id:62,alias:'leader'},
                    {name:'招聘信息',id:63,alias:'zhaopin'}
                ]},
                {name:'通知通告',id:43,alias:'annoucement',children:[
                    {name:'通知',id:64,alias:'notics'},
                    {name:'公告',id:65,alias:'annoucement'}
                ]},
                {name:'新闻中心',id:44,alias:'newscenter',children:[
                    {name:'学校新闻',id:45,alias:'internews'},
                    {name:'培训新闻',id:46,alias:'trainnews'},
                    {name:'行业新闻',id:66,alias:'industrynews'},
                    {name:'我的新闻',id:143,alias:'mynews'}
                ]},
                {name:'党校教育',id:47,alias:'dxedu',children:[
                    {name:'企业领导干部培训',id:48,alias:'trainmanager'},
                    {name:'党员发展对象培训',id:49,alias:'objcpm'},
                    {name:'入党积极分子培训',id:50,alias:'jjfz'}
                ]},
                {name:'城建商学院',id:51,alias:'busischool',children:[
                    {name:'企业紧缺人才培训',id:67,alias:'trainshort'},
                    {name:'继续教育培训',id:68,alias:'jxtrain'}
                ]},
                {name:'新技术推广',id:52,alias:'newtech',children:[
                    {name:'“四新”技术推广',id:69,alias:'newtechpub'},
                    {name:'BIM培训',id:70,alias:'bimtrain'}
                ]},
                {name:'成人学历教育',id:53,alias:'crxledu',children:[
                    {name:'中央党校研究生班',id:71,alias:'dxmaster'},
                    {name:'奥鹏学历教育',id:72,alias:'aopeng'},
                    {name:'其他学历教育',id:73,alias:'other'}
                ]},
                {name:'行业培训',id:54,alias:'intelligence',children:[
                    {name:'注册类培训',id:74,alias:'certificate',children:[
                        {name:'一级注册建造师',id:78,alias:'constructor',children:[
                            {name:'建筑工程',id:111,alias:'conwork'},
                            {name:'公路工程',id:112,alias:'highway'},
                            {name:'铁路工程',id:113,alias:'railway'},
                            {name:'民航机场工程',id:114,alias:'airport'},
                            {name:'港口与航道工程',id:115,alias:'port'},
                            {name:'水利水电工程',id:116,alias:'hydropower'},
                            {name:'矿业工程',id:117,alias:'mineral'},
                            {name:'市政公用工程',id:118,alias:'public'},
                            {name:'通信与广电工程',id:119,alias:'communication'},
                            {name:'机电工程',id:120,alias:'mechatronics'}
                        ]},
                        {name:'二级注册建造师',id:79,alias:'associateconstructor',children:[
                            {name:'建筑工程',id:121,alias:'constructor'},
                            {name:'公路工程',id:122,alias:'highway'}
                        ]},
                        {name:'一级注册造价师',id:80,alias:'prcoster'},
                        {name:'二级注册造价师',id:81,alias:'srcoster'},
                        {name:'其它',id:82,alias:'other'}
                    ]},
                    {name:'安全三类人员培训',id:75,alias:'sanleiren'},
                    {name:'现场管理人员培训',id:76,alias:'xcleader',children:[
                        {name:'施工员',id:83,alias:'builder'},
                        {name:'质检员',id:84,alias:'iqc'},
                        {name:'材料员',id:85,alias:'materialman'},
                        {name:'资料员',id:86,alias:'documentman'},
                        {name:'测量验线员',id:87,alias:'surveyor'},
                        {name:'劳动力管理员',id:88,alias:'labormanager'},
                        {name:'试验员',id:89,alias:'tester'},
                        {name:'机械员',id:90,alias:'machinist'},
                        {name:'监理员',id:91,alias:'supervisor'},
                        {name:'预算员',id:92,alias:'estimater'}
                    ]},
                    {name:'现场操作人员培训',id:77,alias:'xcoperation',children:[
                        {name:'技术工人',id:93,alias:'skilledworker',children:[
                            {name:'木工',id:95,alias:'woodworker'},
                            {name:'抹灰工',id:96,alias:'daubler'},
                            {name:'管道工',id:97,alias:'piper'},
                            {name:'工程电气设备安装调试工',id:98,alias:'eworker'},
                            {name:'建筑油漆工',id:99,alias:'painter'},
                            {name:'测量放线工',id:100,alias:'actuator'},
                            {name:'瓦工',id:101,alias:'tiling'},
                            {name:'钢筋工',id:102,alias:'steelfixer'}
                        ]},
                        {name:'特种作业操作人员',id:94,alias:'specialworker',children:[
                            {name:'塔式起重机司机',id:103,alias:'driveroftowercrane'},
                            {name:'施工外梯司机',id:104,alias:'DriverofConstructionOuterElevator'},
                            {name:'信号司索工',id:105,alias:'SignalSupervisor'},
                            {name:'建筑架子工P',id:106,alias:'shelfworker'},
                            {name:'建筑起重机械安装拆卸工',id:107,alias:'disassemblyworker'},
                            {name:'建筑电工',id:108,alias:'electrician'},
                            {name:'有限空间作业',id:109,alias:'limitedspace'},
                            {name:'电焊工',id:110,alias:'electricwelder'}
                        ]}
                    ]}
                ]},
                {name:'联系我们',id:55,alias:'contactus'},
                {name:'相关链接',id:56,alias:'links'},
                {name:'合作伙伴',id:57,alias:'patner'},
                {name:'测试修改栏目',id:123,alias:'test'},
                {name:'包含文件',id:161,alias:'include'}
            ]}
            ]

            /*nodes: [ //节点
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
            ]*/
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
        });*/

    });
</script>
</body>
</html>
