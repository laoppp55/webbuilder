'use strict';

( function() {
  CKEDITOR.dialog.add( 'HelloWorld', function( editor ) {
	  var plugin = CKEDITOR.plugins.HelloWorld;
	  //var HelloLang = editor.lang.HelloWorld,anchors;

      return {
        title:          'Test Dialog',
        resizable:      CKEDITOR.DIALOG_RESIZE_BOTH,
        minWidth:       500,
        minHeight:      400,
        contents: [
            {
                id:         'tab1',
                label:      'First Tab',
                title:      'First Tab Title',
                accessKey:  'Q',
                elements: [
					{
					   id: 'linkType',
					   type: 'select',
					   label: 'URL Type',
					   'default': 'url',
					   items: [
						   [ 'toUrl', 'url' ],
						   [ 'toAnchor', 'anchor' ],
						   [ 'toEmail', 'email' ]
					   ],
					   //onChange: linkTypeChanged,
					   setup: function( data ) {
						   this.setValue( data.type || 'url' );
					   },
					   commit: function( data ) {
						   data.linkType = this.getValue();
						   alert("link type==" + data.linkType);
					   }
				    },

				    {
                        type:           'text',
                        label:          'Test Text 1',
                        id:             'testText1',
                        'default':      'hello world!',
						commit: function( data ) {
						   data.testText1 = this.getValue();
						   alert("text====" + data.testText1);
					   }
					},

					{
                        type: 'checkbox',
                        id: 'agree',
                        label: 'I agree',
                        'default': 'checked',
                        onClick: function() {
                           alert( 'Checked: ' + this.getValue() );
                        },
						commit: function( data ) {
						   data.agree = this.getValue();
					   }
                    },

					{
                        type: 'checkbox',
                        id: 'agree',
                        label: 'I not agree',
						onClick: function() {
                           alert( 'Checked: ' + this.getValue() );
                        },
					    commit: function( data ) {
						   data.noagree = this.getValue();
					    }
                    },

					{
                        type: 'button',
                        id: 'buttonId',
                        label: 'Click me',
                        title: 'My title',
                        onClick: function() {
                           alert( 'Clicked: ' + this.id );
                        }
                     }
                ]
            }
        ],
		onOk: function() {
			var data = {};
			// Collect data from fields.
			this.commitContent(data);
            var str = JSON.stringify(data);
			alert(plugin);
			alert(str);
            alert(!this._.selectedElement);
			var selection = editor.getSelection();//attributes = plugin.getLinkAttributes( editor, data );
			if (editor.mode == 'wysiwyg') {
				if ( !this._.selectedElement ) { 
					var range = selection.getRanges()[0];
					editor.insertHtml(str);
				} else {
					var element = this._.selectedElement,href = element.data( 'cke-saved-href' ),textView = element.getHtml();
					editor.insertHtml(str);
				}
            }
		}
    };
} );
} )();
 