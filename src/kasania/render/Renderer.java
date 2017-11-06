package kasania.render;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JFrame;

import kasania.entity.Player;
import kasania.entity.monster.TestMob;
import kasania.main.GameManager;
import kasania.main.Scene;
import kasania.resources.ImageName;
import kasania.update.Updater;

public class Renderer implements Runnable {

	private static Container DrawBoard;
	private JFrame frame;
	private static Graphics2D g;

	private Player player;
	private TestMob TM[];

	private BufferStrategy bs;
	private static HashMap<ImageName, BufferedImage> imageList;

	private long PTime;
	private long RTime;
	private long CTime;
	private long renders;
	private final long SEC = 1000000000;
	private int FPS;
	private Scene currentScene;
	private int cursor;

	public Renderer(int FPS) {
		
		frame = GameManager.getFrame();
		DrawBoard = frame.getContentPane();
		bs = frame.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		this.FPS = FPS;
		currentScene = GameManager.getCurrentScene();
		RTime = System.nanoTime();
		PTime = RTime;
		CTime = RTime;
		imageList = GameManager.getImageList();

	}

	public void run() {
		currentScene = GameManager.getCurrentScene();
		while (currentScene != Scene.DESTROY) {
			RTime = System.nanoTime();
			if (RTime - PTime >= SEC / FPS) {
				this.render();
				setRenders(getRenders() + 1);
				PTime = RTime;
			}
			if (RTime - CTime >= SEC) {
				System.out.println("R : " + renders);
				renders = 0;
				CTime = RTime;
			}
			currentScene = GameManager.getCurrentScene();
		}
	}

	private void render() {
		DrawBoard.paint(g);
		if (currentScene == Scene.INTRO)
			renderIntroScr();
		else if (currentScene == Scene.TITLE)
			renderTitleScr();
		else if (currentScene == Scene.INGAME)
			renderInGameScr();
		else if (currentScene == Scene.PAUSE)
			renderPauseScr();
		else if (currentScene == Scene.DESTROY)
			dispose();

		bs.show();
	}

	private void renderIntroScr() {

	}

	private void renderTitleScr() {
		int startX = getCenterXPos(ImageName.MENU_GAMESTART);
		int startY = getCenterYPos(ImageName.MENU_GAMESTART) + 100;
		int settingX = getCenterXPos(ImageName.MENU_SETTING);
		int settingY = startY + 100;
		int exitX = getCenterXPos(ImageName.MENU_GAMEEXIT);
		int exitY = settingY + 100;
		int cursorX;
		int cursorY;
		cursor = Updater.getCursor();
		if (cursor == 1) {
			cursorX = startX - imageList.get(ImageName.MENU_CURSOR).getWidth(DrawBoard);
			cursorY = startY;
		} else if (cursor == 2) {
			cursorX = settingX - imageList.get(ImageName.MENU_CURSOR).getWidth(DrawBoard);
			cursorY = settingY;
		} else {
			cursorX = exitX - imageList.get(ImageName.MENU_CURSOR).getWidth(DrawBoard);
			cursorY = exitY;
		}

		drawAtPos(ImageName.MENU_GAMESTART, startX, startY);
		drawAtPos(ImageName.MENU_SETTING, settingX, settingY);
		drawAtPos(ImageName.MENU_GAMEEXIT, exitX, exitY);
		drawAtPos(ImageName.MENU_CURSOR, cursorX, cursorY);
		
	}

	private void renderInGameScr() {
		TM = Updater.getTM();
		player = Updater.getPlayer();
		player.Render();
		for (int i = 0; i < TM.length; i++)
			TM[i].Render();

	}

	private void renderPauseScr() {

		int startX = getCenterXPos(ImageName.MENU_RESUME);
		int startY = getCenterYPos(ImageName.MENU_RESUME) + 100;
		int exitX = getCenterXPos(ImageName.MENU_EXIT);
		int exitY = startY + 100;
		int cursorX;
		int cursorY;
		cursor = Updater.getCursor();
		if (cursor == 1) {
			cursorX = startX - imageList.get(ImageName.MENU_CURSOR).getWidth(DrawBoard);
			cursorY = startY;
		} else {
			cursorX = exitX - imageList.get(ImageName.MENU_CURSOR).getWidth(DrawBoard);
			cursorY = exitY;
		}
		drawAtPos(ImageName.MENU_PAUSE, getCenterXPos(ImageName.MENU_PAUSE), 200);
		drawAtPos(ImageName.MENU_RESUME, startX, startY);
		drawAtPos(ImageName.MENU_EXIT, exitX, exitY);
		drawAtPos(ImageName.MENU_CURSOR, cursorX, cursorY);

	}

	private void dispose() {
	}

	public static void drawAtPos(ImageName name, int x, int y) {
		g.drawImage(imageList.get(name), x, y, DrawBoard);
	}
	
	public static void drawFrames(ImageName name, int x, int y, int w, int h, int r, int c){
		
	}

	private int getCenterXPos(ImageName img) {
		return (GameManager.getWIDTH() - imageList.get(img).getWidth(DrawBoard)) / 2;
	}

	private int getCenterYPos(ImageName img) {
		return (GameManager.getHEIGHT() - imageList.get(img).getHeight(DrawBoard)) / 2;
	}

	public long getRenders() {
		return renders;
	}

	public void setRenders(long renders) {
		this.renders = renders;
	}

}
