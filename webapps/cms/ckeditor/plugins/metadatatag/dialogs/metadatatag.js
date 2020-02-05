CKEDITOR.ui.button = function( definition )
   {
       // Copy all definition properties to this object.
       CKEDITOR.tools.extend( this, definition,
           // Set defaults.
           {
               title        : definition.label,
               className    : definition.className || ( definition.command && 'cke_button_' + definition.command ) || '',
               click        : definition.click || function( editor )
                   {
                       editor.execCommand( definition.command );
                   }
           });
   
       this._ = {};
   };