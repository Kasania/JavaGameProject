package kasania.main;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import kasania.input.KeyBoard;
import kasania.input.KeyManager;
import kasania.input.Mouse;
import kasania.render.Renderer;
import kasania.resources.image.ImageName;
import kasania.resources.image.Images;
import kasania.update.Updater;

public class GameManager {
	
	
	private Renderer renderer;
	private Updater updater;
	private Thread rendererThread;
	private Thread UpdaterThread;
	
	private static KeyBoard keyboard;
	private static KeyManager keymgr;
	private static Mouse mouse;
	private static MainFrame frame;
	private static Scene currentScene;

	private Images images;
	private static HashMap<ImageName, BufferedImage> imageList;
	
	private static final int WIDTH = 1600;
	private static final int HEIGHT = 900;
	private int UPS;
	private int FPS;
	
	{
		init();
		UpdaterThread.start();
		rendererThread.start();
		
		while(currentScene != Scene.DESTROY){
			
		}
		this.dispose();

	}
	
	private void init(){
		load();
		initSystem();
	}
	
	public void load(){
		currentScene = Scene.TITLE;

		images = new Images();
		imageList = images.getImages();
		
		keyboard = new KeyBoard();
		frame = new MainFrame(WIDTH, HEIGHT);
		setKeymgr(new KeyManager());
	}
	
	public void initSystem(){
		FPS = 180;
		UPS = 360;
		updater = new Updater(UPS);
		renderer = new Renderer(FPS);
		UpdaterThread = new Thread(updater);
		rendererThread = new Thread(renderer);
	}
	
	
	public void dispose(){
		
		System.exit(0);
	}
	
	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}
	public static MainFrame getFrame() {
		return frame;
	}

	public static KeyBoard getKeyboard() {
		return keyboard;
	}

	public static Scene getCurrentScene() {
		return currentScene;
	}

	public static void setCurrentScene(Scene currentScene) {
		GameManager.currentScene = currentScene;
	}
	
	public static HashMap<ImageName, BufferedImage> getImageList() {
		return imageList;
	}

	public void setImageList(HashMap<ImageName, BufferedImage> imageList) {
		GameManager.imageList = imageList;
	}

	public static KeyManager getKeymgr() {
		return keymgr;
	}

	public static void setKeymgr(KeyManager keymgr) {
		GameManager.keymgr = keymgr;
	}

	public static Mouse getMouse() {
		return mouse;
	}

}
