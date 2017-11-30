package kasania.ui;

import java.awt.event.KeyEvent;

import kasania.input.KeyManager;
import kasania.main.GameManager;

public class UIManager {
	
	private boolean isInventoryOpen;
	private KeyManager keymgr;
	
	private Inventory inventory;
	{
		keymgr = GameManager.getKeymgr();
		inventory = new Inventory();
		isInventoryOpen = false;
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
		
	}
	
	
}
