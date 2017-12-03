package kasania.ui;

import java.awt.event.KeyEvent;

import kasania.input.KeyManager;
import kasania.main.GameManager;

public class UIManager {
	
	private boolean isInventoryOpen;
	private KeyManager keymgr;
	
	private Inventory inventory;
	
	private StatusBar statusbar;
	{
		keymgr = GameManager.getKeymgr();
		inventory = new Inventory();
		isInventoryOpen = false;
		
		statusbar = new StatusBar();
	}
	public void update(){
		if(keymgr.isFirstPressed(KeyEvent.VK_I)){
			if(!isInventoryOpen){
				isInventoryOpen = true;
			}
			else{
				isInventoryOpen = false;
			}
		}
		
	}

	
	public void render(){
		if(isInventoryOpen){
			inventory.render();
		}
		statusbar.render();
		
		
	}
	
	
}
