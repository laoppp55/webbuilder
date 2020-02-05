<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HTML>
<HEAD>
<TITLE>JavsScript高级编程</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<META NAME="Generator" CONTENT="EditPlus">
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="../js/TreeGrid.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/TreeGrid.css"/>
</HEAD>

<BODY>
	<input type="button" value="关闭所有节点" onclick="expandAll('N')">
	<input type="button" value="展开所有节点" onclick="expandAll('Y')">
	<input type="button" value="取得当前行的数据" onclick="selectedItem()"><br>
	当前选中的行：<input type="text" id="currentRow" size="110">

	<div id="div1"></div>

	<script language="javascript">
		var config = {
			id: "tg1",
			width: "800",
			renderTo: "div1",
			headerAlign: "left",
			headerHeight: "30",
			dataAlign: "left",
			indentation: "20",
			folderOpenIcon: "../images/button/folderOpen.gif",
			folderCloseIcon: "../images/button/folderClose.gif",
			defaultLeafIcon: "../images/button/defaultLeaf.gif",
			hoverRowBackground: "false",
			folderColumnIndex: "1",
			itemClick: "itemClickEvent",
			columns:[
				{headerText: "", headerAlign: "center", dataAlign: "center", width: "20", handler: "customCheckBox"},
				{headerText: "名称", dataField: "name", headerAlign: "center", handler: "customOrgName"},
				{headerText: "拼音码", dataField: "code", headerAlign: "center", dataAlign: "center", width: "100"},
				{headerText: "负责人", dataField: "assignee", headerAlign: "center", dataAlign: "center", width: "100"},
				{headerText: "查看", headerAlign: "center", dataAlign: "center", width: "50", handler: "customLook"}
			],
			data:[
				{name: "城区分公司", code: "CQ", assignee: "", children:[
					{name: "城区卡品分销中心"},
					{name: "先锋服务厅", children:[
						{name: "chlid1"},
						{name: "chlid2"},
						{name: "chlid3", children: [
							{name: "chlid3-1"},
							{name: "chlid3-2"},
							{name: "chlid3-3"},
							{name: "chlid3-4"}
						]}
					]},
					{name: "半环服务厅"}
				]},
				{name: "清新分公司", code: "QX", assignee: "", children:[]},
				{name: "英德分公司", code: "YD", assignee: "", children:[]},
				{name: "佛冈分公司", code: "FG", assignee: "", children:[]}
			]
		};

		/*
			单击数据行后触发该事件
			id：行的id
			index：行的索引。
			data：json格式的行数据对象。
		*/
		function itemClickEvent(id, index, data){
			jQuery("#currentRow").val(id + ", " + index + ", " + TreeGrid.json2str(data));
		}
		
		/*
			通过指定的方法来自定义栏数据
		*/
		function customCheckBox(row, col){
			return "<input type='checkbox'>";
		}

		function customOrgName(row, col){
			var name = row[col.dataField] || "";
			return name;
		}

		function customLook(row, col){
			return "<a href='' style='color:blue;'>查看</a>";
		}

		//创建一个组件对象
		var treeGrid = new TreeGrid(config);
		treeGrid.show();
		
		/*
			展开、关闭所有节点。
			isOpen=Y表示展开，isOpen=N表示关闭
		*/
		function expandAll(isOpen){
			treeGrid.expandAll(isOpen);
		}
		
		/*
			取得当前选中的行，方法返回TreeGridItem对象
		*/
		function selectedItem(){
			var treeGridItem = treeGrid.getSelectedItem();
			if(treeGridItem!=null){
				//获取数据行属性值
				//alert(treeGridItem.id + ", " + treeGridItem.index + ", " + treeGridItem.data.name);
				
				//获取父数据行
				var parent = treeGridItem.getParent();
				if(parent!=null){
					//jQuery("#currentRow").val(parent.data.name);
				}
				
				//获取子数据行集
				var children = treeGridItem.getChildren();
				if(children!=null && children.length>0){
					jQuery("#currentRow").val(children[0].data.name);
				}
			}
		}
	</script>

</BODY>
</HTML>