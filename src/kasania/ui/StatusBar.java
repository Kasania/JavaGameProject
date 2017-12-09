package kasania.ui;

import kasania.main.GameManager;
import kasania.render.Renderer;
import kasania.resources.image.ImageName;
import kasania.resources.image.ImageManager;

public class StatusBar extends UI{

	public StatusBar() {
		Image = ImageName.UI_STATUSBAR;
		
	}
	
	public void render() {
		Renderer.drawAbsPos(Image, 0, GameManager.getHEIGHT()-ImageManager.getImage(Image).getHeight());
		
		
	}
}

