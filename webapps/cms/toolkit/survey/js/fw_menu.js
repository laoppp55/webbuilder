window.onresize = function (){
	if (mywidth <= 760) {
	ScreenOffset = 0;
	}else{
	ScreenOffset = (mywidth - 760)/2;
	}
}

function initScreen(){
var ScreenOffset;
if (mywidth <= 760) {
	ScreenOffset = 0;
}else{
	ScreenOffset = (mywidth - 760)/2;
}
return ScreenOffset;
}

function MM_displayStatusMsg(msgStr)  { //v3.0
	status=msgStr; document.MM_returnValue = true;
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}
function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
 var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
   var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
   if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function navChange(formName, popupName, target) {
	var popup = document[formName].elements[popupName];
	if (popup.options[popup.selectedIndex].value != "") {
		window.open(popup.options[popup.selectedIndex].value, target);
		popup.selectedIndex=0;
	}
}

function fwLoadMenus() {
		
  if (window.fw_menu_0) return;
//这里添加子菜单

   //东方爱婴二级菜单
    window.fw_menu_1_1 = new Menu("公司简介","location='./web/company/com001.htm'");
   fw_menu_1_1.addMenuItem("公司简介","location='./web/company/com001.htm'");
   fw_menu_1_1.addMenuItem("爱婴标志","location='./web/company/com003-b.htm'");
   fw_menu_1_1.addMenuItem("LOVE涵义","location='./web/company/com003-c.htm'");
   fw_menu_1_1.addMenuItem("爱心标志","location='./web/company/com003-d.htm'");
   
    window.fw_menu_1_2 = new Menu("发展历程","location='./web/company/com002.htm'");
   fw_menu_1_2.addMenuItem("公司年表","location='./web/company/com002.htm'");
   fw_menu_1_2.addMenuItem("获奖纪事","location='./web/company/com002-b.htm'");
   
    window.fw_menu_1_3 = new Menu("企业文化","location='./web/company/com003.htm'");
   fw_menu_1_3.addMenuItem("企业文化","location='./web/company/com003.htm'");
   fw_menu_1_3.addMenuItem("3C价值理念","location='./web/company/com003-a.htm'");
   fw_menu_1_3.addMenuItem("我们的使命","location='./web/company/com001-b.htm'");
   fw_menu_1_3.addMenuItem("我们的愿景","location='./web/company/com001-c.htm'");
   fw_menu_1_3.addMenuItem("为您提供的价值","location='./web/company/com001-d.htm'");
   
    window.fw_menu_1_5 = new Menu("媒体报道","location='./web/company/aycmall.php'");
   fw_menu_1_5.addMenuItem("电视报道","location='./web/company/aycmall.php'");
   fw_menu_1_5.addMenuItem("杂志摘录","location='./web/company/aycm2all.php'");
   fw_menu_1_5.addMenuItem("报刊选登","location='./web/company/aycm3all.php'");
   
   //早教课堂二级菜单
    window.fw_menu_2_2 = new Menu("课程总表","location='./web/classroom/class01.htm'");
   fw_menu_2_2.addMenuItem("孕期训练","location='./web/classroom/class02.htm#1'");
   fw_menu_2_2.addMenuItem("启蒙课程","location='./web/classroom/class-qm.htm'");
   fw_menu_2_2.addMenuItem("感官课程","location='./web/classroom/class-gg.htm'");
   fw_menu_2_2.addMenuItem("美劳课程","location='./web/classroom/class-ml.htm'");
   fw_menu_2_2.addMenuItem("数学课程","location='./web/classroom/class-sx.htm'");
   fw_menu_2_2.addMenuItem("音乐课程","location='./web/classroom/class-yy.htm'");
      
    window.fw_menu_2_3 = new Menu("孕期训练","location='./web/classroom/class02.htm'");
   fw_menu_2_3.addMenuItem("课程设置","location='./web/classroom/class02.htm'");
   fw_menu_2_3.addMenuItem("训练课堂","location='./web/classroom/class02-b.htm'");
   fw_menu_2_3.addMenuItem("全程指导","location='./web/classroom/class02-c.htm'");
   fw_menu_2_3.addMenuItem("拉玛泽简介","location='./web/classroom/class02-d.htm'");
   
    window.fw_menu_2_4 = new Menu("早教课程","location='./web/classroom/class03.htm'");
   fw_menu_2_4.addMenuItem("PAT发育模式","location='./web/classroom/class03.htm'");
   fw_menu_2_4.addMenuItem("课程与班次","location='./web/classroom/class03-b.htm'");
   fw_menu_2_4.addMenuItem("上课地点","location='./web/classroom/class04.php'");
  
  //宝宝乐园二级菜单    
    window.fw_menu_3_2 = new Menu("封面宝宝","location='./web/express/exp-fmbaby.php'");
   fw_menu_3_2.addMenuItem("宝宝档案","location='./web/express/exp-fmbaby.php'");
   fw_menu_3_2.addMenuItem("精彩瞬间","location='./web/express/fmbaby2.php'");
   fw_menu_3_2.addMenuItem("成长故事","location='./web/express/fmbaby3.php'");
  
	
	window.fw_menu_3_3= new Menu("育儿资讯","location='./web/express/yuer-mbaall.php'");
   fw_menu_3_3.addMenuItem("育儿MBA","location='./web/express/yuer-mbaall.php'");
   fw_menu_3_3.addMenuItem("妈妈锦囊","location='./web/express/yuer-mmjnall.php'");
   fw_menu_3_3.addMenuItem("新手父母","location='./web/express/yuer-xsfmall.php'");
   fw_menu_3_3.addMenuItem("童言无忌","location='./web/express/yuer-tywjall.php'");
   
	window.fw_menu_3_4= new Menu("爱婴丛书","location='./web/book/book2.php'");
   fw_menu_3_4.addMenuItem("0-1岁父母用书","location='./web/book/book1.php'");
   fw_menu_3_4.addMenuItem("1-2岁父母用书","location='./web/book/book2.php'");
   fw_menu_3_4.addMenuItem("2-3岁父母用书","location='./web/book/book3.php'");
   
   	window.fw_menu_3_5= new Menu("宝宝大赛","location='./web/ad/ad92.htm'");
   fw_menu_3_5.addMenuItem("报名程序","location='./web/ad/ad92a.htm'");
   fw_menu_3_5.addMenuItem("参赛流程","location='./web/ad/ad92-b.htm'");
   fw_menu_3_5.addMenuItem("比赛项目规则","location='./web/ad/ad92-c.htm'");
   fw_menu_3_5.addMenuItem("成绩公告","location='./web/ad/ad92-d.htm'");
   fw_menu_3_5.addMenuItem("赛场传真","location='./web/ad/ad92-e.php'");
   fw_menu_3_5.addMenuItem("精彩瞬间","location='./web/ad/ad92-g.htm'");
  
//人才中心二级菜单    
    window.fw_menu_5_1 = new Menu("爱婴大家庭","location='./web/jobcenter/101.htm'");
   fw_menu_5_1.addMenuItem("欢迎辞","location='./web/jobcenter/101.htm'");
   fw_menu_5_1.addMenuItem("企业文化","location='./web/jobcenter/105.htm'");
   fw_menu_5_1.addMenuItem("用人观","location='./web/jobcenter/106.htm'");
   fw_menu_5_1.addMenuItem("员工风采","location='./web/jobcenter/107.php'");
   fw_menu_5_1.addMenuItem("每月动态","location='./web/jobcenter/108.htm'");
	
	window.fw_menu_5_2= new Menu("职业发展","location='./web/jobcenter/201.htm'");
   fw_menu_5_2.addMenuItem("职业与发展","location='./web/jobcenter/201.htm'");
   fw_menu_5_2.addMenuItem("职业发展阶梯","location='./web/jobcenter/202.htm'");
   
	window.fw_menu_5_3= new Menu("应聘程序","location='./web/jobcenter/301.htm'");
   fw_menu_5_3.addMenuItem("应聘流程","location='./web/jobcenter/301.htm'");
   fw_menu_5_3.addMenuItem("下载应聘登记表","location='./web/jobcenter/job.zip'");
 
  //顾客服务二级菜单
  
  window.fw_menu_4_1 = new Menu("顾客服务","location='./web/new-ser/service-fwg.htm'");
   fw_menu_4_1.addMenuItem("东方爱婴服务观","location='./web/new-ser/service-fwg.htm'");
   fw_menu_4_1.addMenuItem("会员手册","location='./web/new-ser/service-sc.htm'");
   fw_menu_4_1.addMenuItem("服务热线","location='./web/new-ser/service-rx.htm'");
   fw_menu_4_1.addMenuItem("网上投诉","location='./web/new-ser/service-ts.htm'");
   fw_menu_4_1.addMenuItem("育儿调查表","location='./web/new-ser/service-dc.htm'");
   fw_menu_4_1.addMenuItem("顾客满意度调查","location='./web/new-ser/service-dc2.htm'");
   fw_menu_4_1.addMenuItem("课程及产品调查","location='./web/new-ser/service-dc3.htm'"); 
   
   window.fw_menu_4_2 = new Menu("早期教育专辑","location='./web/new-ser/service00.php'");
   fw_menu_4_2.addMenuItem("0-6个月","location='./web/new-ser/jjzjb.php?keyid=y1'");
   fw_menu_4_2.addMenuItem("7-12个月","location='./web/new-ser/jjzjb.php?keyid=y2'");
   fw_menu_4_2.addMenuItem("13-18个月","location='./web/new-ser/jjzjb.php?keyid=y3'");
   fw_menu_4_2.addMenuItem("19-24个月","location='./web/new-ser/jjzjb.php?keyid=y4'");
   fw_menu_4_2.addMenuItem("25-36个月","location='./web/new-ser/jjzjb.php?keyid=y5'");
   fw_menu_4_2.addMenuItem("36个月以上","location='./web/new-ser/jjzjb.php?keyid=y6'");
   
      
    window.fw_menu_4_3 = new Menu("孕期专家指导","location='./web/new-ser/service00.php'");
   fw_menu_4_3.addMenuItem("孕前准备","location='./web/new-ser/yqzd.php?keyid=r1'");
   fw_menu_4_3.addMenuItem("孕早期","location='./web/new-ser/yqzd.php?keyid=r2'");
   fw_menu_4_3.addMenuItem("孕中期","location='./web/new-ser/yqzd.php?keyid=r3'");
   fw_menu_4_3.addMenuItem("孕晚期","location='./web/new-ser/yqzd.php?keyid=r4'");
   fw_menu_4_3.addMenuItem("分娩","location='./web/new-ser/yqzd.php?keyid=r5'");
   fw_menu_4_3.addMenuItem("哺乳准备","location='./web/new-ser/yqzd.php?keyid=r6'");
   
   window.fw_menu_4_5 = new Menu("婴幼健康宝典","location='./web/new-ser/service00.php'");
   fw_menu_4_5.addMenuItem("新生儿保健","location='./web/new-ser/service00.php'");
   fw_menu_4_5.addMenuItem("喂养","location='./web/new-ser/service00.php'");
   fw_menu_4_5.addMenuItem("预防接种","location='./web/new-ser/service00.php'");
   fw_menu_4_5.addMenuItem("危险与急救","location='./web/new-ser/service00.php'");
   fw_menu_4_5.addMenuItem("疾病","location='./web/new-ser/service00.php'");
   fw_menu_4_5.addMenuItem("其他","location='./web/new-ser/service00.php'");
   

//特许经营二级菜单
    window.fw_menu_6_1 = new Menu("加盟指南","location='./web/license/license00.htm'");
   fw_menu_6_1.addMenuItem("关于东方爱婴","location='./web/license/jmzn01.htm'");
   fw_menu_6_1.addMenuItem("类似机构的区别","location='./web/license/jmzn02.htm'");
   fw_menu_6_1.addMenuItem("健康快乐自信","location='./web/license/jmzn03.htm'");
   fw_menu_6_1.addMenuItem("市场发展前景","location='./web/license/jmzn04.htm'");
   fw_menu_6_1.addMenuItem("发展战略模式","location='./web/license/jmzn05.htm'");
   fw_menu_6_1.addMenuItem("发展现状","location='./web/license/jmzn06.htm'");
   fw_menu_6_1.addMenuItem("特许加盟体系","location='./web/license/jmzn07.htm'");
   fw_menu_6_1.addMenuItem("保障支持体系","location='./web/license/jmzn08.htm'");
   fw_menu_6_1.addMenuItem("投资回报分析","location='./web/license/jmzn09.htm'");
   fw_menu_6_1.addMenuItem("计划项目综述","location='./web/license/jmzn10.htm'");
   fw_menu_6_1.addMenuItem("申请条件及程序","location='./web/license/jmzn11.htm'");

//东方爱婴
  window.fw_menu_1 = new Menu();
  fw_menu_1.addMenuItem(fw_menu_1_1,"location='./web/company/com001.htm'");
  fw_menu_1.addMenuItem(fw_menu_1_2,"location='./web/company/com002.htm'");
  fw_menu_1.addMenuItem(fw_menu_1_3,"location='./web/company/com003.htm'");
  fw_menu_1.addMenuItem("爱婴动态","location='./web/company/com004all.php'");
  fw_menu_1.addMenuItem(fw_menu_1_5,"location='./web/company/aycmall.php'");
  
 //早教课堂

  window.fw_menu_2 = new Menu();
  fw_menu_2.addMenuItem("东方爱婴教育观","location='./web/classroom/class00.htm'");
  fw_menu_2.addMenuItem(fw_menu_2_2,"location='./web/classroom/class01.htm'");
  fw_menu_2.addMenuItem(fw_menu_2_3,"location='./web/classroom/class02.htm'");
  fw_menu_2.addMenuItem(fw_menu_2_4,"location='./web/classroom/class03.htm'");
  fw_menu_2.addMenuItem("全国早教中心","location='./web/classroom/class04.php'");
  fw_menu_2.addMenuItem("东方爱婴专家团","location='./web/classroom/expert.htm'");

//宝宝乐园

  window.fw_menu_3 = new Menu();
  fw_menu_3.addMenuItem("活动看板","location='./web/express/exp-hdkb.htm'");
  fw_menu_3.addMenuItem(fw_menu_3_2,"location='./web/express/exp-fmbaby.php'");
  fw_menu_3.addMenuItem(fw_menu_3_3,"location='./web/express/yuer-mbaall.php'");
  fw_menu_3.addMenuItem(fw_menu_3_4,"location='./web/book/book2.php'");
  fw_menu_3.addMenuItem(fw_menu_3_5,"location='./web/ad/ad92.htm'");
  

  
//会员专区

  window.fw_menu_4 = new Menu();
  fw_menu_4.addMenuItem(fw_menu_4_1,"location='./web/new-ser/service-fwg.htm'");
  fw_menu_4.addMenuItem(fw_menu_4_2,"location='./web/new-ser/service00.php'");
  fw_menu_4.addMenuItem(fw_menu_4_3,"location='./web/new-ser/service00.php'");
  fw_menu_4.addMenuItem("PAT教育计划","location='./web/ad/may.htm'");
  fw_menu_4.addMenuItem(fw_menu_4_5,"location='./web/new-ser/service00.php'");
  fw_menu_4.addMenuItem("玩教具展台","location='./web/new-ser/serzq-wj.php'");
  fw_menu_4.addMenuItem("爱婴专递","location='./web/new-ser/express.php'");
  
//人才中心
  window.fw_menu_5 = new Menu();
  fw_menu_5.addMenuItem(fw_menu_5_1,"location='./web/jobcenter/101.htm'");
  fw_menu_5.addMenuItem(fw_menu_5_2,"location='./web/jobcenter/201.htm'");
  fw_menu_5.addMenuItem(fw_menu_5_3,"location='./web/jobcenter/301.htm'");
  fw_menu_5.addMenuItem("热点问题","location='./web/jobcenter/401.htm'");
  fw_menu_5.addMenuItem("职位空缺","location='./web/jobcenter/501.php'");
  
 //特许经营 
  window.fw_menu_6 = new Menu();
  fw_menu_6.addMenuItem(fw_menu_6_1,"location='./web/license/license00.htm'");
  fw_menu_6.addMenuItem("加盟流程","location='./web/license/license-lc.htm'");
  fw_menu_6.addMenuItem("加盟问答","location='./web/license/license-faq.htm'");
  fw_menu_6.addMenuItem("在线咨询","location='./web/license/license-zx.htm'");
  fw_menu_6.addMenuItem("加盟申请","location='./web/license/license-sq.htm'");
  fw_menu_6.addMenuItem("2004年五月份培训日历","location='./web/license/license-jh5.php'");

//盟商在线 
  window.fw_menu_7 = new Menu();
  fw_menu_7.addMenuItem("信息中心","location='./web/leaguer-ol/info.php'");
  fw_menu_7.addMenuItem("培训中心","location='./web/leaguer-ol/study.php'");
  fw_menu_7.addMenuItem("产品中心","location='./web/leaguer-ol/product.php'");
  fw_menu_7.addMenuItem("研发中心","location='./web/leaguer-ol/research.php'");
  fw_menu_7.addMenuItem("营销中心","location='./web/leaguer-ol/market.php'");
  fw_menu_7.addMenuItem("认证中心","location='./web/leaguer-ol/accredit.php'");
  fw_menu_7.addMenuItem("交流中心","location='http://www.babycare.com.cn/babybbs/index.php?s=f6858283893e332598d6d3ac4352c0f0&act=Login&CODE=00'");

//爱婴论坛
 /* window.fw_menu_8 = new Menu();
  fw_menu_8.addMenuItem("宝贝看招","location='http://www.babycare.com.cn/finalbbs'");*/

  
  //FAQ
  window.fw_menu_9 = new Menu();
  fw_menu_9.addMenuItem("关于东方爱婴","location='./web/faq/101.php'");
  fw_menu_9.addMenuItem("关于早期教育","location='./web/faq/201.php'");
  fw_menu_9.addMenuItem("关于课程","location='./web/faq/301.php'");
  fw_menu_9.addMenuItem("关于环境","location='./web/faq/401.php'");
  fw_menu_9.addMenuItem("关于教师","location='./web/faq/501.php'");
  fw_menu_9.addMenuItem("关于爱婴专递","location='./web/faq/601.php'");
  fw_menu_9.addMenuItem("关于教学效果","location='./web/faq/701.php'");
  fw_menu_9.addMenuItem("关于教材","location='./web/faq/801.php'");
  fw_menu_9.addMenuItem("关于分龄","location='./web/faq/901.php'");
  fw_menu_9.addMenuItem("英文水平要求","location='./web/faq/1001.php'");
  fw_menu_9.addMenuItem("关于课程区别","location='./web/faq/1101.php'");
  fw_menu_9.addMenuItem("英语教学区别","location='./web/faq/1201.php'");

  fw_menu_9.writeMenus();
}
// fwLoadMenus()



function Menu(label, mw, mh, fnt, fs, fclr, fhclr, bg, bgh) {
this.version = "990702 [Menu; menu.js]";
	this.type = "Menu";
	this.menuWidth = 100;
	this.menuItemHeight = 22;
	this.fontSize = fs||12;
	this.fontWeight = "plain";
	this.fontFamily = fnt||"MS Shell Dlg";
	this.fontColor = fclr||"#000000";
	this.fontColorHilite = fhclr||"#ffffFF";
	this.bgColor = "#FF0099";
	this.menuBorder = 1;
	this.menuItemBorder = 1;
	this.menuItemBgColor = bg||"#FFCCFF";
	this.menuLiteBgColor = "#FFE1FF";
	this.menuBorderBgColor = "#cc0099";
	this.menuHiliteBgColor = bgh||"#9999FF";
	this.menuContainerBgColor = "#ffccff";
	this.childMenuIcon = "images/arrows.gif";

	this.items = new Array();
	this.actions = new Array();
	this.childMenus = new Array();

	this.hideOnMouseOut = true;

	

	this.addMenuItem = addMenuItem;
	this.addMenuSeparator = addMenuSeparator;
	this.writeMenus = writeMenus;
	this.FW_showMenu = FW_showMenu;
	this.onMenuItemOver = onMenuItemOver;
	this.onMenuItemAction = onMenuItemAction;
	this.hideMenu = hideMenu;
	this.hideChildMenu = hideChildMenu;

	if (!window.menus) window.menus = new Array();
	this.label = label || "menuLabel" + window.menus.length;
	window.menus[this.label] = this;
	window.menus[window.menus.length] = this;
	if (!window.activeMenus) window.activeMenus = new Array();
}

function addMenuItem(label, action) {
	this.items[this.items.length] = label;
	this.actions[this.actions.length] = action;
}

function addMenuSeparator() {
	this.items[this.items.length] = "separator";
	this.actions[this.actions.length] = "";
	this.menuItemBorder = 0;
}

// For NS6. 

function FIND(item) {
	if (document.all) return(document.all[item]);
	if (document.getElementById) return(document.getElementById(item));
	return(false);
}

function writeMenus(container) {	
	if (window.triedToWriteMenus) return;

	if (!container && document.layers) {
		window.delayWriteMenus = this.writeMenus;
		var timer = setTimeout('delayWriteMenus()', 250);
		container = new Layer(100);
		clearTimeout(timer);
	} else if (document.all || document.hasChildNodes) {		
		document.writeln('<SPAN ID="menuContainer"></SPAN>');
		container = FIND("menuContainer");
	}

	window.fwHideMenuTimer = null;
	if (!container) return;	
	window.triedToWriteMenus = true; 
	container.isContainer = true;
	container.menus = new Array();
	for (var i=0; i<window.menus.length; i++) 
		container.menus[i] = window.menus[i];
	window.menus.length = 0;
	var countMenus = 0;
	var countItems = 0;
	var top = 0;
	var content = '';
	var lrs = false;
	var theStat = "";
	var tsc = 0;
	if (document.layers) lrs = true;
	for (var i=0; i<container.menus.length; i++, countMenus++) {
		var menu = container.menus[i];
		if (menu.bgImageUp) {
			menu.menuBorder = 0;
			menu.menuItemBorder = 0;
		}
		if (lrs) {
			var menuLayer = new Layer(100, container);
			var lite = new Layer(100, menuLayer);
			lite.top = menu.menuBorder-2;
			lite.left = menu.menuBorder-2;
			var body = new Layer(100, lite);
			body.top = menu.menuBorder;
			body.left = menu.menuBorder;
		} else {
			content += ''+
			'<DIV ID="menuLayer'+ countMenus +'" STYLE="position:absolute;z-index:1;left:10;top:'+ (i * 100) +';visibility:hidden;">\n'+
			'  <DIV ID="menuLite'+ countMenus +'" STYLE="position:absolute;z-index:1;left:'+ menu.menuBorder +';top:'+ menu.menuBorder +';visibility:hide;" onMouseOut="mouseoutMenu();">\n'+
			'	 <DIV ID="menuFg'+ countMenus +'" STYLE="position:absolute;left:'+ menu.menuBorder +';top:'+ menu.menuBorder +';visibility:hide;">\n'+
			'';
		}
		var x=i;
		for (var i=0; i<menu.items.length; i++) {
			var item = menu.items[i];
			var childMenu = false;
			var defaultHeight = menu.fontSize+6;
			//set01
			var defaultIndent = 6;
			if (item.label) {
				item = item.label;
				childMenu = true;
			}
			menu.menuItemHeight = menu.menuItemHeight || defaultHeight;
			menu.menuItemIndent = menu.menuItemIndent || defaultIndent;
			var itemProps = 'font-family:' + menu.fontFamily +';font-weight:' + menu.fontWeight + ';fontSize:' + menu.fontSize + ';';
			if (menu.fontStyle) itemProps += 'font-style:' + menu.fontStyle + ';';
			if (document.all) 
				itemProps += 'font-size:' + menu.fontSize + ';" onMouseOver="onMenuItemOver(null,this);" onClick="onMenuItemAction(null,this);';
			else if (!document.layers) {
				itemProps += 'font-size:' + menu.fontSize + 'px;'; // zilla wants 12px.
			}
			var l;
			if (lrs) {
				l = new Layer(800,body);
			}
			var dTag	= '<DIV ID="menuItem'+ countItems +'" STYLE="position:absolute;left:0;top:'+ (i * menu.menuItemHeight) +';'+ itemProps +'">';
			var dClose = '</DIV>'
			if (menu.bgImageUp) {
				menu.menuBorder = 0;
				menu.menuItemBorder = 0;
				dTag	= '<DIV ID="menuItem'+ countItems +'" STYLE="background:url('+menu.bgImageUp+');position:absolute;left:0;top:'+ (i * menu.menuItemHeight) +';'+ itemProps +'">';
				if (document.layers) {
					dTag = '<LAYER BACKGROUND="'+menu.bgImageUp+'" ID="menuItem'+ countItems +'" TOP="'+ (i * menu.menuItemHeight) +'" style="' + itemProps +'">';
					dClose = '</LAYER>';
				}
			}
			var textProps = 'position:absolute;left:' + menu.menuItemIndent + ';top:3;';
			if (lrs) {
				textProps +=itemProps;
				dTag = "";
				dClose = "";
			}

			var dText	= '<DIV ID="menuItemText'+ countItems +'" STYLE="' + textProps + 'color:'+ menu.fontColor +';">'+ item +'&nbsp</DIV>\n<DIV ID="menuItemHilite'+ countItems +'" STYLE="' + textProps + 'top:3;color:'+ menu.fontColorHilite +';visibility:hidden;">'+ item +'&nbsp</DIV>';
			if (item == "separator") {
				content += ( dTag + '<DIV ID="menuSeparator'+ countItems +'" STYLE="position:absolute;left:1;top:2;"></DIV>\n<DIV ID="menuSeparatorLite'+ countItems +'" STYLE="position:absolute;left:1;top:2;"></DIV>\n' + dClose);
			} else if (childMenu) {
				content += ( dTag + dText + '<DIV ID="childMenu'+ countItems +'" STYLE="position:absolute;left:0;top:3;"><IMG SRC="'+ menu.childMenuIcon +'"></DIV>\n' + dClose);
			} else {
				content += ( dTag + dText + dClose);
			}
			if (lrs) {
				l.document.open("text/html");
				l.document.writeln(content);
				l.document.close();	
				content = '';
				theStat += "-";
				tsc++;
				if (tsc > 50) {
					tsc = 0;
					theStat = "";
				}
				status = theStat;
			}
			countItems++;  
		}
		if (lrs) {
			// focus layer
			var focusItem = new Layer(100, body);
			focusItem.visiblity="hidden";
			focusItem.document.open("text/html");
			focusItem.document.writeln("&nbsp;");
			focusItem.document.close();	
		} else {
		  content += '	  <DIV ID="focusItem'+ countMenus +'" STYLE="position:absolute;left:0;top:0;visibility:hide;" onClick="onMenuItemAction(null,this);">&nbsp;</DIV>\n';
		  content += '   </DIV>\n  </DIV>\n</DIV>\n';
		}
		i=x;
	}
	if (document.layers) {		
		container.clip.width = window.innerWidth;
		container.clip.height = window.innerHeight;
		container.onmouseout = mouseoutMenu;
		container.menuContainerBgColor = this.menuContainerBgColor;
		for (var i=0; i<container.document.layers.length; i++) {
			proto = container.menus[i];
			var menu = container.document.layers[i];
			container.menus[i].menuLayer = menu;
			container.menus[i].menuLayer.Menu = container.menus[i];
			container.menus[i].menuLayer.Menu.container = container;
			var body = menu.document.layers[0].document.layers[0];
			body.clip.width = proto.menuWidth || body.clip.width;
			body.clip.height = proto.menuHeight || body.clip.height;
			for (var n=0; n<body.document.layers.length-1; n++) {
				var l = body.document.layers[n];
				l.Menu = container.menus[i];
				l.menuHiliteBgColor = proto.menuHiliteBgColor;
				l.document.bgColor = proto.menuItemBgColor;
				l.saveColor = proto.menuItemBgColor;
				l.onmouseover = proto.onMenuItemOver;
				l.onclick = proto.onMenuItemAction;
				l.action = container.menus[i].actions[n];
				l.focusItem = body.document.layers[body.document.layers.length-1];
				l.clip.width = proto.menuWidth || body.clip.width + proto.menuItemIndent;
				l.clip.height = proto.menuItemHeight || l.clip.height;
				if (n>0) l.top = body.document.layers[n-1].top + body.document.layers[n-1].clip.height + proto.menuItemBorder;
				l.hilite = l.document.layers[1];
				if (proto.bgImageUp) l.background.src = proto.bgImageUp;
				l.document.layers[1].isHilite = true;
				if (l.document.layers[0].id.indexOf("menuSeparator") != -1) {
					l.hilite = null;
					l.clip.height -= l.clip.height / 2;
					l.document.layers[0].document.bgColor = proto.bgColor;
					l.document.layers[0].clip.width = l.clip.width -2;
					l.document.layers[0].clip.height = 1;
					l.document.layers[1].document.bgColor = proto.menuLiteBgColor;
					l.document.layers[1].clip.width = l.clip.width -2;
					l.document.layers[1].clip.height = 1;
					l.document.layers[1].top = l.document.layers[0].top + 1;
				} else if (l.document.layers.length > 2) {
					l.childMenu = container.menus[i].items[n].menuLayer;
					l.document.layers[2].left = l.clip.width -13;
					l.document.layers[2].top = (l.clip.height / 2) -4;
					l.document.layers[2].clip.left += 3;
					l.Menu.childMenus[l.Menu.childMenus.length] = l.childMenu;
				}
			}
			body.document.bgColor = proto.bgColor;
			body.clip.width  = l.clip.width +proto.menuBorder;
			body.clip.height = l.top + l.clip.height +proto.menuBorder;
			var focusItem = body.document.layers[n];
			focusItem.clip.width = body.clip.width;
			focusItem.Menu = l.Menu;
			focusItem.top = -30;
            focusItem.captureEvents(Event.MOUSEDOWN);
            focusItem.onmousedown = onMenuItemDown;
			menu.document.bgColor = proto.menuBorderBgColor;
			var lite = menu.document.layers[0];
			lite.document.bgColor = proto.menuLiteBgColor;
			lite.clip.width = body.clip.width +1;
			lite.clip.height = body.clip.height +1;
			menu.clip.width = body.clip.width + (proto.menuBorder * 3) ;
			menu.clip.height = body.clip.height + (proto.menuBorder * 3);
		}
	} else {
		if ((!document.all) && (container.hasChildNodes)) {
			container.innerHTML=content;
		} else {
			container.document.open("text/html");
			container.document.writeln(content);
			container.document.close();	
		}
		if (!FIND("menuLayer0")) return;
		var menuCount = 0;
		for (var x=0; x<container.menus.length; x++) {
			var menuLayer = FIND("menuLayer" + x);
			container.menus[x].menuLayer = "menuLayer" + x;
			menuLayer.Menu = container.menus[x];
			menuLayer.Menu.container = "menuLayer" + x;
			menuLayer.style.zIndex = 1;
		    var s = menuLayer.style;
			s.top = s.pixelTop = -300;
			s.left = s.pixelLeft = -300;

			var menu = container.menus[x];
			menu.menuItemWidth = menu.menuWidth || menu.menuIEWidth || 140;
			menuLayer.style.backgroundColor = menu.menuBorderBgColor;
			var top = 0;
			for (var i=0; i<container.menus[x].items.length; i++) {
				var l = FIND("menuItem" + menuCount);
				l.Menu = container.menus[x];
				if (l.addEventListener) { // ns6
					l.style.width = menu.menuItemWidth;	
					l.style.height = menu.menuItemHeight;
					l.style.top = top;
					l.addEventListener("mouseover", onMenuItemOver, false);
					l.addEventListener("click", onMenuItemAction, false);
					l.addEventListener("mouseout", mouseoutMenu, false);
				} else { //ie
					l.style.pixelWidth = menu.menuItemWidth;	
					l.style.pixelHeight = menu.menuItemHeight;
					l.style.pixelTop = top;
				}
				top = top + menu.menuItemHeight+menu.menuItemBorder;
				l.style.fontSize = menu.fontSize;
				l.style.backgroundColor = menu.menuItemBgColor;
				l.style.visibility = "inherit";
				l.saveColor = menu.menuItemBgColor;
				l.menuHiliteBgColor = menu.menuHiliteBgColor;
				l.action = container.menus[x].actions[i];
				l.hilite = FIND("menuItemHilite" + menuCount);
				l.focusItem = FIND("focusItem" + x);
				l.focusItem.style.pixelTop = l.focusItem.style.top = -30;
				var childItem = FIND("childMenu" + menuCount);
				if (childItem) {
					l.childMenu = container.menus[x].items[i].menuLayer;
					childItem.style.pixelLeft = childItem.style.left = menu.menuItemWidth -16;
					childItem.style.pixelTop = childItem.style.top =(menu.menuItemHeight /2) -5;//arrows的位置
					//childItem.style.pixelWidth = 30 || 7;
					//childItem.style.clip = "rect(0 7 7 3)";
					l.Menu.childMenus[l.Menu.childMenus.length] = l.childMenu;
				}
				var sep = FIND("menuSeparator" + menuCount);
				if (sep) {
					sep.style.clip = "rect(0 " + (menu.menuItemWidth - 3) + " 1 0)";
					sep.style.width = sep.style.pixelWidth = menu.menuItemWidth;	
					sep.style.backgroundColor = menu.bgColor;
					sep = FIND("menuSeparatorLite" + menuCount);
					sep.style.clip = "rect(1 " + (menu.menuItemWidth - 3) + " 2 0)";
					sep.style.width = sep.style.pixelWidth = menu.menuItemWidth;	
					sep.style.backgroundColor = menu.menuLiteBgColor;
					l.style.height = l.style.pixelHeight = menu.menuItemHeight/2;
					l.isSeparator = true
					top -= (menu.menuItemHeight - l.style.pixelHeight)
				} else {
					l.style.cursor = "hand"
				}
				menuCount++;
			}
			menu.menuHeight = top-1;
			var lite = FIND("menuLite" + x);
			var s = lite.style;
			s.height = s.pixelHeight = menu.menuHeight +(menu.menuBorder * 2);
			s.width = s.pixelWidth = menu.menuItemWidth + (menu.menuBorder * 2);
			s.backgroundColor = menu.menuLiteBgColor;

			var body = FIND("menuFg" + x);
			s = body.style;
			s.height = s.pixelHeight = menu.menuHeight + menu.menuBorder;
			s.width = s.pixelWidth = menu.menuItemWidth + menu.menuBorder;
			s.backgroundColor = menu.bgColor;

			s = menuLayer.style;
			s.width = s.pixelWidth  = menu.menuItemWidth + (menu.menuBorder * 4);
			s.height = s.pixelHeight  = menu.menuHeight+(menu.menuBorder*4);
		}
	}
	if (document.captureEvents) {	
		document.captureEvents(Event.MOUSEUP);
	}
	if (document.addEventListener) {	
		document.addEventListener("mouseup", onMenuItemOver, false);
	}
	if (document.layers && window.innerWidth) {
		window.onresize = NS4resize;
		window.NS4sIW = window.innerWidth;
		window.NS4sIH = window.innerHeight;
	}
	document.onmouseup = mouseupMenu;
	window.fwWroteMenu = true;
	status = "";
}

function NS4resize() {
	if (NS4sIW < window.innerWidth || 
		NS4sIW > window.innerWidth || 
		NS4sIH > window.innerHeight || 
		NS4sIH < window.innerHeight ) 
	{
		window.location.reload();
	}	
}

function onMenuItemOver(e, l) {
	FW_clearTimeout();
	l = l || this;
	a = window.ActiveMenuItem;
	if (document.layers) {
		if (a) {
			a.document.bgColor = a.saveColor;
			if (a.hilite) a.hilite.visibility = "hidden";
			if (a.Menu.bgImageOver) {
				a.background.src = a.Menu.bgImageUp;
			}
			a.focusItem.top = -100;
			a.clicked = false;
		}
		if (l.hilite) {
			l.document.bgColor = l.menuHiliteBgColor;
			l.zIndex = 1;
			l.hilite.visibility = "inherit";
			l.hilite.zIndex = 2;
			l.document.layers[1].zIndex = 1;
			l.focusItem.zIndex = this.zIndex +2;
		}
		if (l.Menu.bgImageOver) {
			l.background.src = l.Menu.bgImageOver;
		}
		l.focusItem.top = this.top;
		l.Menu.hideChildMenu(l);
	} else if (l.style && l.Menu) {
		if (a) {
			a.style.backgroundColor = a.saveColor;
			if (a.hilite) a.hilite.style.visibility = "hidden";
			if (a.Menu.bgImageUp) {
				a.style.background = "url(" + a.Menu.bgImageUp +")";;
			}
		} 
		if (l.isSeparator) return;
		l.style.backgroundColor = l.menuHiliteBgColor;
		l.zIndex = 1;  // magic IE 4.5 mac happy doohicky.	jba
		if (l.Menu.bgImageOver) {
			l.style.background = "url(" + l.Menu.bgImageOver +")";
		}
		if (l.hilite) {
			l.style.backgroundColor = l.menuHiliteBgColor;
			l.hilite.style.visibility = "inherit";
		}
		l.focusItem.style.top = l.focusItem.style.pixelTop = l.style.pixelTop;
		l.focusItem.style.zIndex = l.zIndex +1;
		l.Menu.hideChildMenu(l);
	} else {
		return; // not a menu - magic IE 4.5 mac happy doohicky.  jba
	}
	window.ActiveMenuItem = l;
}

function onMenuItemAction(e, l) {
	l = window.ActiveMenuItem;
	if (!l) return;
	hideActiveMenus();
	if (l.action) {
		eval("" + l.action);
	}
	window.ActiveMenuItem = 0;
}

function FW_clearTimeout()
{
	if (fwHideMenuTimer) clearTimeout(fwHideMenuTimer);
	fwHideMenuTimer = null;
	fwDHFlag = false;
}
function FW_startTimeout()
{
	fwStart = new Date();
	fwDHFlag = true;
	fwHideMenuTimer = setTimeout("fwDoHide()", 1000);
}

function fwDoHide()
{
	if (!fwDHFlag) return;
	var elapsed = new Date() - fwStart;
	if (elapsed < 1000) {
		fwHideMenuTimer = setTimeout("fwDoHide()", 1100-elapsed);
		return;
	}
	fwDHFlag = false;
	hideActiveMenus();
	window.ActiveMenuItem = 0;
}

function FW_showMenu(menu, x, y, child) {
	if (!window.fwWroteMenu) return;
	FW_clearTimeout();
	if (document.layers) {
		if (menu) {
			var l = menu.menuLayer || menu;
			l.left = 1;
			l.top = 1;
			hideActiveMenus();
			if (this.visibility) l = this;
			window.ActiveMenu = l;
		} else {
			var l = child;
		}
		if (!l) return;
		for (var i=0; i<l.layers.length; i++) { 			   
			if (!l.layers[i].isHilite) 
				l.layers[i].visibility = "inherit";
			if (l.layers[i].document.layers.length > 0) 
				FW_showMenu(null, "relative", "relative", l.layers[i]);
		}
		if (l.parentLayer) {
			if (x != "relative") 
				l.parentLayer.left = x + ScreenOffset || window.pageX || 0;
			if (l.parentLayer.left + l.clip.width > window.innerWidth) 
				l.parentLayer.left -= (l.parentLayer.left + l.clip.width - window.innerWidth);
			if (y != "relative") 
				l.parentLayer.top = y || window.pageY || 0;
			if (l.parentLayer.isContainer) {
				l.Menu.xOffset = window.pageXOffset;
				l.Menu.yOffset = window.pageYOffset;
				l.parentLayer.clip.width = window.ActiveMenu.clip.width +2;
				l.parentLayer.clip.height = window.ActiveMenu.clip.height +2;
				if (l.parentLayer.menuContainerBgColor) l.parentLayer.document.bgColor = l.parentLayer.menuContainerBgColor;
			}
		}
		l.visibility = "inherit";
		if (l.Menu) l.Menu.container.visibility = "inherit";
	} else if (FIND("menuItem0")) {
		var l = menu.menuLayer || menu;	
		hideActiveMenus();
		if (typeof(l) == "string") {
			l = FIND(l);
		}
		window.ActiveMenu = l;
		var s = l.style;
		s.visibility = "inherit";
		if (x != "relative") 
			s.left = s.pixelLeft = x + ScreenOffset || (window.pageX + document.body.scrollLeft) || 0;
		if (y != "relative") 
			s.top = s.pixelTop = y || (window.pageY + document.body.scrollTop) || 0;
		l.Menu.xOffset = document.body.scrollLeft;
		l.Menu.yOffset = document.body.scrollTop;
	}
	if (menu) {
		window.activeMenus[window.activeMenus.length] = l;
	}
}

function onMenuItemDown(e, l) {
	var a = window.ActiveMenuItem;
	if (document.layers) {
		if (a) {
			a.eX = e.pageX;
			a.eY = e.pageY;
			a.clicked = true;
		}
    }
}

function mouseupMenu(e)
{
	hideMenu(true, e);
	hideActiveMenus();
	return true;
}

function mouseoutMenu()
{
	hideMenu(false, false);
	return true;
}


function hideMenu(mouseup, e) {
	var a = window.ActiveMenuItem;
	if (a && document.layers) {
		a.document.bgColor = a.saveColor;
		a.focusItem.top = -30;
		if (a.hilite) a.hilite.visibility = "hidden";
		if (mouseup && a.action && a.clicked && window.ActiveMenu) {
 			if (a.eX <= e.pageX+15 && a.eX >= e.pageX-15 && a.eY <= e.pageY+10 && a.eY >= e.pageY-10) {
				setTimeout('window.ActiveMenu.Menu.onMenuItemAction();', 2);
			}
		}
		a.clicked = false;
		if (a.Menu.bgImageOver) {
			a.background.src = a.Menu.bgImageUp;
		}
	} else if (window.ActiveMenu && FIND("menuItem0")) {
		if (a) {
			a.style.backgroundColor = a.saveColor;
			if (a.hilite) a.hilite.style.visibility = "hidden";
			if (a.Menu.bgImageUp) {
				a.style.background = "url(" + a.Menu.bgImageUp +")";;
			}
		}
	}
	if (!mouseup && window.ActiveMenu) {
		if (window.ActiveMenu.Menu) {
			if (window.ActiveMenu.Menu.hideOnMouseOut) {
				FW_startTimeout();
			}
			return(true);
		}
	}
	return(true);
}

function PxToNum(pxStr)
{ // pxStr == 27px, we want 27.
	if (pxStr.length > 2) {
		n = Number(pxStr.substr(0, pxStr.length-2));
		return(n);
	}
	return(0);
}

function hideChildMenu(hcmLayer) {
	FW_clearTimeout();
	var l = hcmLayer;
	for (var i=0; i < l.Menu.childMenus.length; i++) {
		var theLayer = l.Menu.childMenus[i];
		if (document.layers) {
			theLayer.visibility = "hidden";
		} else {
			theLayer = FIND(theLayer);
			theLayer.style.visibility = "hidden";
		}
		theLayer.Menu.hideChildMenu(theLayer);
	}

	if (l.childMenu) {
		var childMenu = l.childMenu;
		if (document.layers) {
			l.Menu.FW_showMenu(null,null,null,childMenu.layers[0]);
			childMenu.zIndex = l.parentLayer.zIndex +1;
			childMenu.top = l.top + l.parentLayer.top + l.Menu.menuLayer.top + l.Menu.menuItemHeight/3;
			if (childMenu.left + childMenu.clip.width > window.innerWidth) {
				childMenu.left = l.parentLayer.left - childMenu.clip.width + l.Menu.menuLayer.left + 15;
				l.Menu.container.clip.left -= childMenu.clip.width;
			} else {
				childMenu.left = l.parentLayer.left + l.parentLayer.clip.width  + l.Menu.menuLayer.left -5;
			}
			var w = childMenu.clip.width+childMenu.left-l.Menu.container.clip.left;
			if (w > l.Menu.container.clip.width)  
				l.Menu.container.clip.width = w;
			var h = childMenu.clip.height+childMenu.top-l.Menu.container.clip.top;
			if (h > l.Menu.container.clip.height) l.Menu.container.clip.height = h;
			l.document.layers[1].zIndex = 0;
			childMenu.visibility = "inherit";
		} else if (FIND("menuItem0")) {
			childMenu = FIND(l.childMenu);
			var menuLayer = FIND(l.Menu.menuLayer);
			var s = childMenu.style;
			s.zIndex = menuLayer.style.zIndex+1;
			if (document.all) { // ie case.
				s.pixelTop = l.style.pixelTop + menuLayer.style.pixelTop + l.Menu.menuItemHeight/3;
				s.left = s.pixelLeft = (menuLayer.style.pixelWidth) + menuLayer.style.pixelLeft -1;
			} else { // zilla case
				var top = PxToNum(l.style.top) + PxToNum(menuLayer.style.top) + l.Menu.menuItemHeight/3;
				var left = (PxToNum(menuLayer.style.width)) + PxToNum(menuLayer.style.left) -1;
				s.top = top;
				s.left = left;
			}
			childMenu.style.visibility = "inherit";
		} else {
			return;
		}
		window.activeMenus[window.activeMenus.length] = childMenu;
	}
}

function hideActiveMenus() {
	if (!window.activeMenus) return;
	for (var i=0; i < window.activeMenus.length; i++) {
		if (!activeMenus[i]) continue;
		if (activeMenus[i].visibility && activeMenus[i].Menu) {
			activeMenus[i].visibility = "hidden";
			activeMenus[i].Menu.container.visibility = "hidden";
			activeMenus[i].Menu.container.clip.left = 0;
		} else if (activeMenus[i].style) {
			var s = activeMenus[i].style;
			s.visibility = "hidden";
			s.left = -200;
			s.top = -200;
		}
	}
	if (window.ActiveMenuItem) {
		hideMenu(false, false);
	}
	window.activeMenus.length = 0;
}