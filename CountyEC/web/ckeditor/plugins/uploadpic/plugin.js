/**
 * Basic sample plugin inserting abbreviation elements into CKEditor editing area.
 *
 * Created out of the CKEditor Plugin SDK:
 * http://docs.ckeditor.com/#!/guide/plugin_sdk_sample_1
 */

// Register the plugin within the editor.
CKEDITOR.plugins.add( 'uploadpic', {

	// Register the icons.
	icons: 'uploadpic',

	// The plugin initialization logic goes inside this method.
	init: function( editor ) {

		// Define an editor command that opens our dialog.
		editor.addCommand( 'uploadpic', new CKEDITOR.dialogCommand( 'uploadpicDialog' ) );

		// Create a toolbar button that executes the above command.
		editor.ui.addButton( 'Uploadpic', {

			// The text part of the button (if available) and tooptip.
			label: 'Insert Abbreviation',

			// The command to execute on click.
			command: 'uploadpic',

			// The button placement in the toolbar (toolbar group name).
			toolbar: 'insert'
		});

		if ( editor.contextMenu ) {
			editor.addMenuGroup( 'uploadpicGroup' );
			editor.addMenuItem( 'uploadpicItem', {
				label: 'Edit Abbreviation',
				icon: this.path + 'icons/uploadpic.png',
				command: 'uploadpic',
				group: 'uploadpicGroup'
			});

			editor.contextMenu.addListener( function( element ) {
				if ( element.getAscendant( 'uploadpic', true ) ) {
					return { abbrItem: CKEDITOR.TRISTATE_OFF };
				}
			});
		}

		// Register our dialog file. this.path is the plugin folder path.
		CKEDITOR.dialog.add( 'uploadpicDialog', this.path + 'dialogs/uploadpic.js' );
	}
});

