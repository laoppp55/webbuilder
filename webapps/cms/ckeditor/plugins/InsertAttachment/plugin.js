(function () {
 CKEDITOR.plugins.add('InsertAttachment',
{    init: function(editor)
    {
        var pluginName = 'InsertAttachment';
        CKEDITOR.dialog.add(pluginName, this.path + 'dialogs/InsertAttachment.js');
        editor.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));
        editor.ui.addButton('InsertAttachment',
            {
                label: '上传文件',
                command: pluginName,
				icon: CKEDITOR.plugins.getPath('InsertAttachment') + 'insertfile.gif'
            });
    }
}); 

 })();