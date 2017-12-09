package kasania.render;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import kasania.entity.monster.TestMob;
import kasania.entity.player.Player;
import kasania.main.GameManager;
import kasania.main.Scene;
import kasania.resources.image.ImageManager;
import kasania.resources.image.ImageName;
import kasania.resources.image.SImage;
import kasania.time.TimeLine;
import kasania.ui.UIManager;
import kasania.update.Updater;

public class Renderer implements Runnable {

	private static Container DrawBoard;
	private JFrame frame;
	private static Graphics2D g;

	private Player player;
	private TestMob TM[];
	private UIManager uimgr;
	private boolean isInGame = false;

	private BufferStrategy bs;
	private static BufferedImage backBuffer;
	private static int[] backBufferRaster;
	private static int[] drawBuffer;
	
	private long PTime;
	private long RTime;
	private long CTime;
	private long renders;
	private int FPS;
	private Scene currentScene;
	private int cursor;

	public Renderer(int FPS) {
		frame = GameManager.getFrame();
		DrawBoard = frame.getContentPane();
		bs = frame.getBufferStrategy();
		backBuffer = new BufferedImage(GameManager.getWIDTH(), GameManager.getHEIGHT(), BufferedImage.TYPE_INT_ARGB);
		backBufferRaster = ((DataBufferInt) backBuffer.getRaster().getDataBuffer()).getData();
		drawBuffer = new int[GameManager.getWIDTH() * GameManager.getHEIGHT()];
		
		this.FPS = FPS;
		currentScene = GameManager.getCurrentScene();
		RTime = TimeLine.getNanoTime();
		PTime = RTime;
		CTime = RTime;
	}

	public void run() {
		currentScene = GameManager.getCurrentScene();
		while (currentScene != Scene.DESTROY) {
			RTime = TimeLine.getNanoTime();
			if (RTime - PTime >= TimeLine.getNanoToSec() / FPS) {
				this.render();
				setRenders(getRenders() + 1);
				PTime = RTime;
			}
			if (RTime - CTime >= TimeLine.getNanoToSec()) {
				drawText("R : " + renders, 0, 10);
				setRenders(0);
				CTime = RTime;
			}
			currentScene = GameManager.getCurrentScene();
		}
	}

	private void render() {
//		DrawBoard.paint(g);
		g = (Graphics2D) bs.getDrawGraphics();
		
		if (currentScene == Scene.INTRO) {
			renderIntroScr();
		} else if (currentScene == Scene.TITLE)
			renderTitleScr();
		else if (currentScene == Scene.INGAME) {
			if (!isInGame) {
				TM = Updater.getTM();
				player = Updater.getPlayer();
				isInGame = true;
				uimgr = Updater.getUimgr();
			}
			renderInGameScr();
		} else if (currentScene == Scene.PAUSE)
			renderPauseScr();
		else if (currentScene == Scene.DESTROY)
			dispose();
		for(int i = 0; i<backBufferRaster.length; i++){
			backBufferRaster[i] = drawBuffer[i];
		}
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GameManager.getWIDTH(), GameManager.getHEIGHT());
		g.drawImage(backBuffer, 0, 0, GameManager.getWIDTH(), GameManager.getHEIGHT(), DrawBoard);
		g.dispose();
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
			cursorX = startX - ImageManager.getImage(ImageName.MENU_CURSOR).getWidth();
			cursorY = startY;
		} else if (cursor == 2) {
			cursorX = settingX - ImageManager.getImage(ImageName.MENU_CURSOR).getWidth();
			cursorY = settingY;
		} else {
			cursorX = exitX - ImageManager.getImage(ImageName.MENU_CURSOR).getWidth();
			cursorY = exitY;
		}
		drawAbsPos(ImageName.MENU_GAMESTART, startX, startY);
		drawAbsPos(ImageName.MENU_SETTING, settingX, settingY);
		drawAbsPos(ImageName.MENU_GAMEEXIT, exitX, exitY);
		drawAbsPos(ImageName.MENU_CURSOR, cursorX, cursorY);

	}

	private void renderInGameScr() {
//		drawBackGround(ImageName.TESTBACKGROUND);
		player.Render();
		for (int i = 0; i < TM.length; i++)
			TM[i].Render();
		uimgr.render();
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
			cursorX = startX - ImageManager.getImage(ImageName.MENU_CURSOR).getWidth();
			cursorY = startY;
		} else {
			cursorX = exitX - ImageManager.getImage(ImageName.MENU_CURSOR).getWidth();
			cursorY = exitY;
		}
		drawAbsPos(ImageName.MENU_PAUSE, getCenterXPos(ImageName.MENU_PAUSE), 200);
		drawAbsPos(ImageName.MENU_RESUME, startX, startY);
		drawAbsPos(ImageName.MENU_EXIT, exitX, exitY);
		drawAbsPos(ImageName.MENU_CURSOR, cursorX, cursorY);

	}

	private void dispose() {
	}

	public static void drawAbsPos(ImageName name, int x, int y){
		SImage image =  ImageManager.getImage(name);
		for (int sy = 0; sy<image.getHeight();sy++){
			for (int sx = 0; sx<image.getWidth();sx++){
				if(sx+x>GameManager.getWIDTH() || sy+y>GameManager.getHEIGHT()) break;
				drawBuffer[(sx+x)+(sy+y)*GameManager.getWIDTH()] = image.getData()[sx+sy*image.getWidth()];
			}
		}
	}

//	public static void drawFrames(ImageName name, int xSize, int ySize, int currentFrame, int currentDir, int x, int y) {
//		BufferedImage targetImage = ImageManager.getImage(name);
//		g.drawImage(targetImage, x, y, x + xSize, y + ySize, xSize * currentFrame + 1, ySize * currentDir + 1,
//				xSize * (currentFrame + 1), ySize * (currentDir + 1), DrawBoard);
//	}

	public static void drawText(String text, int x, int y) {
//		g.drawString(text, x, y);
		System.out.println(text);
	}

	private int getCenterXPos(ImageName img) {
		return (GameManager.getWIDTH() - ImageManager.getImage(img).getWidth()) / 2;
	}

	private int getCenterYPos(ImageName img) {
		return (GameManager.getHEIGHT() - ImageManager.getImage(img).getHeight()) / 2;
	}

	public long getRenders() {
		return renders;
	}

	public void setRenders(long renders) {
		this.renders = renders;
	}

}
