(function(){
    //Section 1 : 按下自定义按钮时执行的代码 
    var a= {
            exec:function(editor){
                var modeltype = createForm.modetype.value;
                //alert(modeltype);
                var winStr = "";
                if (modeltype==="1")
                    winStr = "/webbuilder/template1/setMetadataTagForArticle.jsp";
                else
                    winStr = "/webbuilder/template1/setMetadataTagForModel.jsp";
                var iWidth=1200;                                                 //弹出窗口的宽度;
                var iHeight=600;                                                //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                //iTop = 100;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                window.open(winStr, "metadatatag", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
            }
        },

        //Section 2 : 创建自定义按钮、绑定方法
        b='metadatatag';
    CKEDITOR.plugins.add(b,{
        init:function(editor){
            editor.addCommand(b,a);
            editor.ui.addButton('metadatatag',{
                label:'选择历史图片',
                icon: this.path + 'metadata.png',
                command:b
            });
        }
    });
})(); 
