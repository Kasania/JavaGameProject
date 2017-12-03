package kasania.update;

import java.awt.event.KeyEvent;

import kasania.entity.EntityID;
import kasania.entity.monster.TestMob;
import kasania.entity.player.Player;
import kasania.input.KeyManager;
import kasania.main.GameManager;
import kasania.main.Scene;
import kasania.physics.BoxColider;
import kasania.render.Renderer;
import kasania.resources.image.ImageName;
import kasania.time.TimeLine;
import kasania.ui.UIManager;

public class Updater implements Runnable{
	
	private static Player player;
	private static TestMob TM[];
	private static UIManager uimgr;

	private Scene currentScene;
	private long PTime;
	private long UTime;
	private long CTime;
	private int UPS;
	private long Updates;
	private static long CurrentUps = 360;
	private static int cursor;
	
	private KeyManager keymgr;
	
	private int CursorMax = 3;
	
	public Updater(int UPS){
		keymgr = GameManager.getKeymgr();

		currentScene = GameManager.getCurrentScene();
		setCursor(1);
		
		UTime = TimeLine.getNanoTime();
		PTime = UTime;
		CTime = UTime;
		setUpdates(0);
		this.UPS = UPS;
	}
	
	private void initEntity(){
		player = new Player(0,0,EntityID.PLAYER,0.75);
		player.setImage(ImageName.PLAYER_SPRITE);
		player.setColider(new BoxColider(player));
		TM = new TestMob[1];
		
		for(int i = 0; i<TM.length; i++){
			TM[i] = new TestMob(150*(i%10), 150*(i/10), EntityID.MONSTER);
			TM[i].setImage(ImageName.BOMB);
			TM[i].setColider(new BoxColider(TM[i]));
		}
	}
	
	public void run() {
		currentScene = GameManager.getCurrentScene();
		
		while(currentScene != Scene.DESTROY){
			UTime = TimeLine.getNanoTime();
			if(UTime - PTime >= TimeLine.getNanoToSec()/UPS){
				this.update();
				setUpdates(getUpdates() + 1);
				PTime = UTime;
			}
			if(UTime - CTime >= TimeLine.getNanoToSec()){
				CurrentUps = Updates;
				Renderer.drawText("U : " + CurrentUps, 0, 40);
				Updates = 0;
				CTime = UTime;
			}
			
			currentScene = GameManager.getCurrentScene();	
		}
		
		this.dispose();
	}
	
	private void update(){
		keymgr.update();
		if(currentScene == Scene.INTRO) updateIntroScr();
		else if(currentScene == Scene.TITLE) updateTitleScr();
		else if(currentScene == Scene.INGAME) updateInGameScr();
		else if(currentScene == Scene.PAUSE) updatePauseScr();
		
		
	}
	
	private void updateIntroScr(){
		
		
	}
	
	private void updateTitleScr(){
		
		CursorEvent();
		
		if(keymgr.isFirstPressed(KeyEvent.VK_ENTER)){
			switch (getCursor()) {
			case 1:
				uimgr = new UIManager();
				initEntity();
				setCursor(0);
				GameManager.setCurrentScene(Scene.INGAME);
				break;
			case 2 :
				break;
			case 3 :
				setCursor(0);
				GameManager.setCurrentScene(Scene.DESTROY);
				break;
			default:
				break;
			}
		}
	}
	
	private void updateInGameScr(){
		if(keymgr.isFirstPressed(KeyEvent.VK_ESCAPE)){
			setCursor(1);
			setCursorMax(2);
			GameManager.setCurrentScene(Scene.PAUSE);
		}
		player.Update();
		for(int i = 0; i<TM.length; i++){
			TM[i].Update();
			TM[i].ChaseTarget(player);
		}
		uimgr.update();
	}
	
	private void updatePauseScr(){
		if(keymgr.isFirstPressed(KeyEvent.VK_ESCAPE)) GameManager.setCurrentScene(Scene.INGAME);
		
		CursorEvent();
		
		if(keymgr.isFirstPressed(KeyEvent.VK_ENTER)){
			switch (getCursor()) {
			case 1:
				GameManager.setCurrentScene(Scene.INGAME);
				break;
			case 2 :
				setCursor(1);
				setCursorMax(3);
				GameManager.setCurrentScene(Scene.TITLE);
				break;
			default:
				break;
			}
		}
	}
	
	
	private void dispose(){
		
		
	}
	
	private void CursorEvent(){
		if(keymgr.isFirstPressed(KeyEvent.VK_UP)){
			setCursor(getCursor()-1);
		}
		
		if(keymgr.isFirstPressed(KeyEvent.VK_DOWN)){
			setCursor(getCursor()+1);
		}
		
		if(getCursor() > getCursorMax()){
			setCursor(1);
		}
		else if(getCursor() < 1){
			setCursor(getCursorMax());
		}
		
	}
	
	private int getCursorMax() {
		return CursorMax;
	}

	private void setCursorMax(int cursorMax) {
		CursorMax = cursorMax;
	}
	
	public long getUpdates() {
		return Updates;
	}

	public void setUpdates(long updates) {
		Updates = updates;
	}

	public static int getCursor() {
		return cursor;
	}

	public static void setCursor(int cursor) {
		Updater.cursor = cursor;
	}
	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		Updater.player = player;
	}

	public static TestMob[] getTM() {
		return TM;
	}

	public static void setTM(TestMob[] tM) {
		TM = tM;
	}
	public static long getCUps(){
		return CurrentUps;
	}

	public static UIManager getUimgr() {
		return uimgr;
	}

	public static void setUimgr(UIManager uimgr) {
		Updater.uimgr = uimgr;
	}

}
