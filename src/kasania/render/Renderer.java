package kasania.render;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import kasania.entity.monster.TestMob;
import kasania.entity.player.Player;
import kasania.main.GameManager;
import kasania.main.Scene;
import kasania.resources.image.ImageName;
import kasania.resources.image.Images;
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
	private boolean ingameflag = false;

	private BufferStrategy bs;

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
		g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		this.FPS = FPS;
		currentScene = GameManager.getCurrentScene();
		RTime = TimeLine.getNanoTime();
		PTime = RTime;
		CTime = RTime;
		g.setFont(new Font("Consolas",Font.BOLD,20));
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
		DrawBoard.paint(g);
		if (currentScene == Scene.INTRO){
			renderIntroScr();
		}
		else if (currentScene == Scene.TITLE)
			renderTitleScr();
		else if (currentScene == Scene.INGAME){
			if (!ingameflag){
				TM = Updater.getTM();
				player = Updater.getPlayer();
				ingameflag = true;
				uimgr = Updater.getUimgr();
			}
			renderInGameScr();
		}
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
			cursorX = startX - Images.getImage(ImageName.MENU_CURSOR).getWidth(DrawBoard);
			cursorY = startY;
		} else if (cursor == 2) {
			cursorX = settingX - Images.getImage(ImageName.MENU_CURSOR).getWidth(DrawBoard);
			cursorY = settingY;
		} else {
			cursorX = exitX - Images.getImage(ImageName.MENU_CURSOR).getWidth(DrawBoard);
			cursorY = exitY;
		}
		drawAtPos(ImageName.MENU_GAMESTART, startX, startY);
		drawAtPos(ImageName.MENU_SETTING, settingX, settingY);
		drawAtPos(ImageName.MENU_GAMEEXIT, exitX, exitY);
		drawAtPos(ImageName.MENU_CURSOR, cursorX, cursorY);
		
	}

	private void renderInGameScr() {
		drawBackGround(ImageName.TESTBACKGROUND);
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
			cursorX = startX - Images.getImage(ImageName.MENU_CURSOR).getWidth(DrawBoard);
			cursorY = startY;
		} else {
			cursorX = exitX - Images.getImage(ImageName.MENU_CURSOR).getWidth(DrawBoard);
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
		g.drawImage(Images.getImage(name), x, y, DrawBoard);
	}
	
	public static void drawBackGround(ImageName name){
		g.drawImage(Images.getImage(name), 0, 0, GameManager.getWIDTH(), GameManager.getHEIGHT(), 
				0, 0, GameManager.getWIDTH(), GameManager.getHEIGHT(), DrawBoard);
	}
	
	public static void drawFrames(ImageName name,int xSize, int ySize, int currentFrame, int currentDir, int x, int y){
		BufferedImage targetImage = Images.getImage(name);
		g.drawImage(targetImage, x, y, x+xSize, y+ySize, 
				xSize*currentFrame+1, ySize*currentDir+1, xSize*(currentFrame+1), ySize*(currentDir+1), DrawBoard);
	}
	
	public static void drawText(String text, int x, int y){
		g.drawString(text, x, y);
		System.out.println(text);
	}

	private int getCenterXPos(ImageName img) {
		return (GameManager.getWIDTH() - Images.getImage(img).getWidth(DrawBoard)) / 2;
	}

	private int getCenterYPos(ImageName img) {
		return (GameManager.getHEIGHT() - Images.getImage(img).getHeight(DrawBoard)) / 2;
	}

	public long getRenders() {
		return renders;
	}

	public void setRenders(long renders) {
		this.renders = renders;
	}

}
