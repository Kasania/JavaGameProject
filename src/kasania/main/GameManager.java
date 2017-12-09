package kasania.main;

import kasania.input.KeyBoard;
import kasania.input.KeyManager;
import kasania.input.Mouse;
import kasania.render.Renderer;
import kasania.resources.image.ImageManager;
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

	private ImageManager images;

	private static int WIDTH = 1600;
	private static int HEIGHT = 900;
	private int UPS;
	private int FPS;

	{
		init();
		UpdaterThread.start();
		rendererThread.start();

		while (currentScene != Scene.DESTROY) {

		}
		this.dispose();

	}

	private void init() {
		load();
		initSystem();
	}

	public void load() {
		currentScene = Scene.TITLE;

		images = new ImageManager();

		keyboard = new KeyBoard();
		frame = new MainFrame(WIDTH, HEIGHT);
		setKeymgr(new KeyManager());
	}

	public void initSystem() {
		FPS = 180;
		UPS = 360;
		updater = new Updater(UPS);
		renderer = new Renderer(FPS);
		UpdaterThread = new Thread(updater,"Updater");
		rendererThread = new Thread(renderer,"Renderer");
	}

	public void dispose() {

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
