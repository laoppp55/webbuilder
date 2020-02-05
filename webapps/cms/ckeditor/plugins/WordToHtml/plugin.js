(function(){ 
    //Section 1 : 按下自定义按钮时执行的代码 
    var a= { 
        exec:function(editor){ 
            //alert("这是MODEL自定义按钮"); 
			var winStr = "/webbuilder/upload/WordToHtml.jsp?column=" + createForm.column.value;
			var iWidth=500;                                                 //弹出窗口的宽度;
            var iHeight=300;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            //iTop = 100;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open(winStr, "WordToHtml", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        } 
    }, 

    //Section 2 : 创建自定义按钮、绑定方法 
    b='WordToHtml';
    CKEDITOR.plugins.add(b,{ 
        init:function(editor){ 
            editor.addCommand(b,a); 
            editor.ui.addButton('WordToHtml',{ 
                label:'WORD转HTML', 
                icon: this.path + 'wordhtml.gif', 
                command:b 
            }); 
        } 
    }); 
})(); 
