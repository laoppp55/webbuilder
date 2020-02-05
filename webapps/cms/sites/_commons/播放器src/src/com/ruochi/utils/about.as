package com.ruochi.utils{
	import flash.display.Sprite;
	import flash.net.navigateToURL;
	import flash.net.URLRequest;
	import flash.ui.ContextMenu;
    import flash.ui.ContextMenuItem;
    import flash.ui.ContextMenuBuiltInItems;
    import flash.events.ContextMenuEvent;
	public function about(sprite:Sprite,lable:String, link:String):void {
		var contextMenu:ContextMenu = new ContextMenu();
		contextMenu.hideBuiltInItems();
		var item:ContextMenuItem = new ContextMenuItem(lable);
		contextMenu.customItems.push(item);
		item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, onItemSelect);
		sprite.contextMenu  = contextMenu;
		function onItemSelect(e:ContextMenuEvent):void {
			navigateToURL(new URLRequest(link), "_blank")
		}
		//_contextMenu.addEventListener(ContextMenuEvent.MENU_SELECT, menuSelectHandler);
	}
}