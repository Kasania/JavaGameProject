package kasania.resources.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageManager {
	
	private final String imgsrc = ".\\res\\img\\"; 
	
	private static HashMap<ImageName, SImage> images;
	{
		images = new HashMap<>();
		loadImage();
	}
	
	private void loadImage(){
		load(ImageName.PLAYER_SPRITE, "Player\\playerSprite.png");
		load(ImageName.BOMB, "Effect\\Bomb.png");
		load(ImageName.MENU_GAMESTART, "Menu\\GameStart.png");
		load(ImageName.MENU_SETTING, "Menu\\GameSetting.png");
		load(ImageName.MENU_GAMEEXIT, "Menu\\GameExit.png");
		load(ImageName.MENU_CURSOR, "Menu\\RightArrow.png");
		load(ImageName.MENU_PAUSE, "Menu\\Pause.png");
		load(ImageName.MENU_RESUME, "Menu\\Resume.png");
		load(ImageName.MENU_EXIT, "Menu\\Exit.png");
		load(ImageName.TESTBACKGROUND, "Stage\\st1.png");
		load(ImageName.UI_INVENTORY, "UI\\Inventory.png");
		load(ImageName.UI_STATUSBAR, "UI\\StatusBar.png");
	}
	
	private void load(ImageName name, String path){
		try {
			Image image = ImageIO.read(new File(imgsrc + path));
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			int[] data = new int[(width)*(height)];
			Graphics2D g2d = bi.createGraphics();
			g2d.drawImage(image, 0,0, null);
			data = ((DataBufferInt)bi.getRaster().getDataBuffer()).getData();
			g2d.dispose();
			images.put(name, new SImage(data, width, height));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SImage getImage(ImageName name){
		return images.get(name);
	}
	
	public HashMap<ImageName, SImage> getImages() {
		return images;
	}
	
}
