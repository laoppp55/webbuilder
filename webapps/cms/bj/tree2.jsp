<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-6-6
  Time: 上午10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../design/css/layui.css"  media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
    <style>
        /*左侧导航*/
        .layui-tree-skin-sidebar li i{
            color: rgba(255,255,255,.7);
            display: none;
        }
        .layui-tree-skin-sidebar li a cite{
            color: rgba(255,255,255,.7)
        }
        .layui-tree-skin-sidebar li .layui-tree-spread{
            display: block;
            position: absolute;
            right: 30px;
        }
        .layui-tree-skin-sidebar li{
            line-height: 45px;
            position: relative;
        }
        .layui-tree-skin-sidebar li ul{
            margin-left: 0;
            background: rgba(0,0,0,.3);
        }
        .layui-tree-skin-sidebar li ul a{
            padding-left: 20px;
        }
        .layui-tree-skin-sidebar li a{
            height: 45px;
            border-left: 5px solid transparent;
            box-sizing: border-box;
            width: 100%;
        }
        .layui-tree-skin-sidebar li a:hover{
            background: #4E5465;
            color: #fff;
            border-left: 5px solid #009688;
        }
        .layui-tree-skin-sidebar li a.active{
            background: #009688;
        }
    </style>
</head>
<body>

<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll " >
        <div id="sidemenubar" lay-filter="test"></div>
    </div>
</div>

<script src="../design/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use(['element','layer','jquery','tree'], function(){
        var element = layui.element;
        var layer = layui.layer;
        var $ = layui.jquery;
        var menuData =  [ //节点
            {
                name: '常用文件夹'
                ,id: '1'
                ,children: [
                {
                    name: '所有未读'
                    ,id: '11'
                    ,url: 'http://www.layui.com/'
                }, {
                    name: '置顶邮件'
                    ,id: '12'
                }, {
                    name: '标签邮件'
                    ,id: '13'
                }
            ]
            }, {
                name: '我的邮箱'
                ,id: '2'
                ,children: [
                    {
                        name: 'QQ邮箱'
                        ,id: '21'
                        ,spread: true
                        ,children: [
                        {
                            name: '收件箱'
                            ,id: '211'
                            ,children: [
                            {
                                name: '所有未读'
                                ,id: '2111'
                            }, {
                                name: '置顶邮件'
                                ,id: '2112'
                            }, {
                                name: '标签邮件'
                                ,id: '2113'
                            }
                        ]
                        }, {
                            name: '已发出的邮件'
                            ,id: '212'
                        }, {
                            name: '垃圾邮件'
                            ,id: '213'
                        }
                    ]
                    }, {
                        name: '阿里云邮'
                        ,id: '22'
                        ,children: [
                            {
                                name: '收件箱'
                                ,id: '221'
                            }, {
                                name: '已发出的邮件'
                                ,id: '222'
                            }, {
                                name: '垃圾邮件'
                                ,id: '223'
                            }
                        ]
                    }
                ]
            }
        ]
        layui.tree({
            elem: '#sidemenubar' //传入元素选择器
            ,skin: 'sidebar'  //自定义tree样式的类名
            ,nodes:menuData  //节点数据
            ,click: function(node,item){
                //node即为当前点击的节点数据,item就是被点击的a标签对象了
                //导航按钮选中当前
                $('#sidemenubar a').removeClass('active');
                $(item).addClass('active');
                $(item).siblings('.layui-tree-spread').click();
                //添加新tab

                //alert(node.name + "==" + $(item).attr('href') + "==" + node.id);
                activeTab.init(node.name,"http://www.sohu.com",node.id);
            }
        });
        var activeTab = {
            tabTit : '', //tab titile标题
            tabUrl : '', //tab内容嵌套iframe的src
            tabId  : '', //tab 标签的lay-id
            tabCon : function(){
                var result;
                $.ajax({
                    type: 'get',
                    url: this.tabUrl,
                    dataType: 'html',
                    success: function(data){
                        result = data;
                    }
                })
                return result;
            },
            addTab : function(){ //新增tab项
                alert(this.tabTit + "==" + this.tabUrl);
                element.tabAdd('demo', {
                    title: this.tabTit
                    ,content: '<iframe src = '+this.tabUrl +' ></iframe>' //支持传入html
                    ,id: this.tabId
                })
            },
            changeTab: function(){ //选中tab项
                element.tabChange('demo', this.tabId);
            },
            ishasTab : function(){ //判断tab项中是否包含
                var _this = this;
                var dataId,isflag;
                var arrays = $('.layui-tab-title li');
                $.each(arrays,function(idx,ele){
                    dataId = $(ele).attr('lay-id');
                    if(dataId === _this.tabId){
                        isflag = true
                        return false
                    }else{
                        isflag = false
                    }
                })
                return isflag
            },
            init : function(tabtit,taburl,tabid){
                var _this = this;
                _this.tabUrl = taburl;
                _this.tabId = tabid;
                _this.tabTit = tabtit;
                if(taburl){
                    if(!_this.ishasTab()){
                        _this.addTab();
                    }
                    _this.changeTab();
                }else{
                    return false
                }
            }
        }
    });
</script>
</body>
</html>
