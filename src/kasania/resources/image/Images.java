package kasania.resources.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Images {
	
	private final String imgsrc = ".\\res\\img\\"; 
	
	private HashMap<ImageName, BufferedImage> images;
	{
		setImages(new HashMap<>());
		loadImage();
	}
	
	private void loadImage(){
		load(ImageName.PLAYER_F22, "Player\\F22.png");
		load(ImageName.PLAYER_T50, "Player\\T50PAK.png");
		load(ImageName.PLAYER_FEMALE, "Player\\FemaleSprite.png");
		load(ImageName.BOMB, "Effect\\Bomb.png");
		load(ImageName.MENU_GAMESTART, "Menu\\GameStart.png");
		load(ImageName.MENU_SETTING, "Menu\\GameSetting.png");
		load(ImageName.MENU_GAMEEXIT, "Menu\\GameExit.png");
		load(ImageName.MENU_CURSOR, "Menu\\RightArrow.png");
		load(ImageName.MENU_PAUSE, "Menu\\Pause.png");
		load(ImageName.MENU_RESUME, "Menu\\Resume.png");
		load(ImageName.MENU_EXIT, "Menu\\Exit.png");
		load(ImageName.TESTBACKGROUND, "Stage\\st1.png");
	}
	
	private void load(ImageName name, String path){
		try {
			Image image = ImageIO.read(new File(imgsrc + path));
			BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bi.createGraphics();
			g2d.drawImage(image, 0,0, null);
			g2d.dispose();
			images.put(name, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public HashMap<ImageName, BufferedImage> getImages() {
		return images;
	}

	public void setImages(HashMap<ImageName, BufferedImage> images) {
		this.images = images;
	}
}
