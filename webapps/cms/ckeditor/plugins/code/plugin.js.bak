(function(){
    //Section 1 : 按下自定义按钮时执行的代码
    var a= {
            exec:function(editor){
                alert("这是自定义按钮");
            }
        },
        //Section 2 : 创建自定义按钮、绑定方法
        b='code';
    CKEDITOR.plugins.add(b,{
        init:function(editor){
            editor.addCommand(b,a);
            editor.ui.addButton('code',{
                label:'code',
                icon: this.path + 'format_wz.png',
                command:b
            });
        }
    });
})();

/*(function () {
    b = 'format_wz';
    CKEDITOR.plugins.add(b, {
        requires: ['styles', 'button'],
        init: function (a) {
            a.addCommand(b, CKEDITOR.plugins.autoformat.commands.autoformat);
            a.ui.addButton('format_wz', {
                label: "一键排版",
                command: 'format_wz',
                icon: this.path + "format_wz.png"
            });
        }
    });

    CKEDITOR.plugins.autoformat = {
        commands: {
            autoformat: {
                exec: function (editor) {
                    formatText(editor);
                }
            }
        }
    };

    //执行的方法
    function formatText(editor) {

        var myeditor = editor;

        //得到要编辑的源代码
        var editorhtml = myeditor.getData();
        //在这里执行你的操作。。。。。

        editorhtml= editorhtml.replace(/(<\/?(?!br|p|img|a|h1|h2|h3|h4|h5|h6)[^>\/]*)\/?>/gi,'');
        //在p标签处添加样式，使每个段落自动缩进两个字符
        editorhtml= editorhtml.replace(/\<[p|P]>/g,"<p style='text-indent: 2em'>");

        //再将内容放回到编辑器中
        editor.setData(html);

    }
})();*/
