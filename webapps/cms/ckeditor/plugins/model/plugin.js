(function(){ 
    //Section 1 : 按下自定义按钮时执行的代码 
    var a= { 
        exec:function(editor){ 
            //alert("这是MODEL自定义按钮"); 
            var winStr = "/webbuilder/template/selectModel.jsp?column=" + createForm.column.value;
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open(winStr, "Select Model", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=yes,scrollbars=yes");
            //window.open( winStr,"", "dialogWidth=800px;dialogHeight=600px");
        } 
    }, 

    //Section 2 : 创建自定义按钮、绑定方法 
    b='model';
    CKEDITOR.plugins.add(b,{ 
        init:function(editor){ 
            editor.addCommand(b,a); 
            editor.ui.addButton('model',{ 
                label:'model', 
                icon: this.path + 'format_wz.png', 
                command:b 
            }); 
        } 
    }); 
})(); 
