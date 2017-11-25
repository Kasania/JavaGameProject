package kasania.entity.player;

import java.awt.event.KeyEvent;
import java.util.spi.CurrencyNameProvider;

import kasania.entity.Entity;
import kasania.entity.EntityID;
import kasania.input.KeyManager;
import kasania.main.GameManager;
import kasania.render.Renderer;
import kasania.resources.sound.Sound;
import kasania.time.TimeElement;
import kasania.time.TimeLine;
import kasania.update.Updater;

public class Player extends Entity{

	private double mulSprint;
	private KeyManager keymgr;
	private static double XPos;
	private static double YPos;
	private Sound sound;
	
	private int spriteXSize = 64;
	private int spriteYSize = 64;
	private int currentFrame = 0;
	private int currentMaxFrame = 9;
	private PlayerStatus currentAction;
	private int frameCounter = 0;
	private int frameRate = 30;
	
	private int maxWalkFrame = 9;
	private int maxShootFrame = 13;
	private int maxSlashFrame = 6;
	private int maxFocusFrame = 7;
	private int maxPierceFrame = 8;
	private int maxDieFrame = 6;
	private int afterWalkFrame = 1;
	private int afterShootFrame = 4;
	
	private double attackRate = 0.05;
	
	public Player(double xPos, double yPos,EntityID ID,double movementSpeed) {
		super(xPos, yPos, ID);
		setPXPos(xPos);
		setPYPos(yPos);
		this.setMovementSpeed(movementSpeed);
		keymgr = GameManager.getKeymgr();
		currentAction = PlayerStatus.PLAYER_WALK_DOWN;
		
		mulSprint = 1.5;
		sound = new Sound("Effect\\PlayerFire.wav", "Effect");
		sound.init();
		TimeLine.addTimeValue(TimeElement.PLAYER_FIREDTIME, TimeLine.getNanoTime());
		TimeLine.addTimeValue(TimeElement.PLAYER_IDLE, TimeLine.getNanoTime());
		
	}
	public void Update(){
		UpdatePosition();
		UpdateCollision();
		UpdateAttack();
		UpdateFrames();
	}
	
	private void UpdateAttack() {
		if(keymgr.isPressed(KeyEvent.VK_C)){
			if(TimeLine.getTimeValueDiff(TimeElement.PLAYER_FIREDTIME, TimeLine.getNanoTime())>TimeLine.getNanoToSec()*attackRate){
				sound.init();
				sound.play();
				System.out.println(TimeLine.getTimeValueDiff(TimeElement.PLAYER_FIREDTIME, TimeLine.getNanoTime()));
				TimeLine.updateTimeValue(TimeElement.PLAYER_FIREDTIME, TimeLine.getNanoTime());
			}
			
		}
		
	}
	
	public void Render(){
		Renderer.drawFrames(this.getImage(), spriteXSize, spriteYSize, currentFrame, currentAction.ordinal(), 
				(int)Math.round(getPXPos()), (int)Math.round(getPYPos()));
	}
	
	private void UpdateFrames(){
		if (currentFrame>=currentMaxFrame) currentFrame = 1;
		
		if(frameCounter>frameRate) {
			++currentFrame;
			frameCounter = 0;
		}
		if(keymgr.isPressedAnykey()){
			currentFrame = 0;
		}
	}

	private void UpdatePosition(){
		double xCod = 0;
		double yCod = 0;
		boolean isSprint = false;
		int pressedkey = 0;
		if(keymgr.isPressed(KeyEvent.VK_W)){
			currentAction = PlayerStatus.PLAYER_WALK_UP;
			++frameCounter;
			yCod-= this.getMovementSpeed();
			++pressedkey;
		}
		if(keymgr.isPressed(KeyEvent.VK_S)){
			currentAction = PlayerStatus.PLAYER_WALK_DOWN;
			++frameCounter;
			yCod += this.getMovementSpeed();
			++pressedkey;
		}
		if(keymgr.isPressed(KeyEvent.VK_A)){
			currentAction = PlayerStatus.PLAYER_WALK_LEFT;
			++frameCounter;
			xCod -= this.getMovementSpeed();
			++pressedkey;
		}
		if(keymgr.isPressed(KeyEvent.VK_D)){
			currentAction = PlayerStatus.PLAYER_WALK_RIGHT;
			++frameCounter;
			xCod += this.getMovementSpeed();
			++pressedkey;
		}
		if(keymgr.isPressed(KeyEvent.VK_SHIFT)){
			isSprint = true;
		}
		
		if(xCod != 0 && yCod !=0){
			xCod = xCod / Root2;
			yCod = yCod / Root2;
		}
		if (pressedkey>1){
			frameCounter = frameCounter - pressedkey +1 ;
		}
		if(isSprint){
			xCod *= mulSprint;
			yCod *= mulSprint;
			++frameCounter;
		}
		
//		setPXPos(getPXPos() + xCod);
//		setPYPos(getPYPos() + yCod);
		
		setXPos(getPXPos());
		setYPos(getPYPos());
	}
	
	private void UpdateCollision(){
		this.colider.Update();
		if(this.getColider().isColide(Updater.getTM()[0])){
//			System.out.println("CD");
		}else{
//			System.out.println("NC");
			
		}
	}
	
	public static double getPXPos() {
		return XPos;
	}

	public static void setPXPos(double xPos) {
		XPos = xPos;
	}

	public static double getPYPos() {
		return YPos;
	}

	public static void setPYPos(double yPos) {
		YPos = yPos;
	}
	
}

