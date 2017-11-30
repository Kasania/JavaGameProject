package kasania.ui;

import kasania.render.Renderer;
import kasania.resources.image.ImageName;

public class Inventory extends UI{
	
	@Override
	public void render() {
		super.render();
		Renderer.drawAtPos(ImageName.UI_INVENTORY, 0, 0);
		
	}

}
