CKEDITOR.plugins.add('Apage', {
     init: function (editor) {
         var pluginName = 'Apage';
         CKEDITOR.dialog.add(pluginName, this.path + 'dialogs/Apage.js');
         editor.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));
         editor.ui.addButton(pluginName,
         {
             label: 'Pages',
             command: pluginName
         });
     }
 });