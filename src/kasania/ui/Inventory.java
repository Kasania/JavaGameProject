package kasania.ui;

import kasania.render.Renderer;
import kasania.resources.image.ImageName;

public class Inventory extends UI{
	
	{
		Image = ImageName.UI_INVENTORY;
	}
	
	public void render() {
		Renderer.drawAtPos(Image, 0, 100);
		
	}

}
