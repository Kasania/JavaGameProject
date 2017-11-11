package kasania.entity;

import java.awt.event.KeyEvent;

import kasania.input.KeyManager;
import kasania.main.GameManager;
import kasania.render.Renderer;
import kasania.resources.sound.Sound;
import kasania.update.Updater;

public class Player extends Entity{

	
	private double mulSprint;
	private KeyManager keymgr;
	private static double XPos;
	private static double YPos;
	private Sound sound;
	private int frames = 4;
	private int dir = 4;
	private int currentFrame = 0;
	private int currentDir = 0;
	private int frameCounter = 0;
	
	public Player(double xPos, double yPos,EntityID ID,double movementSpeed) {
		super(xPos, yPos, ID);
		setPXPos(xPos);
		setPYPos(yPos);
		this.setMovementSpeed(movementSpeed);
		keymgr = GameManager.getKeymgr();
		
		mulSprint = 1.5;
		sound = new Sound("Effect\\PlayerFire.wav", "Effect");
	}
	

	public void Update(){
		UpdatePosition();
		UpdateCollision();
		UpdateAttack();
	}
	
	private void UpdateAttack() {
		if(keymgr.isPressed(KeyEvent.VK_C)){
			sound.play();
		}
		
	}
	
	public void Render(){
		Renderer.drawFrames(this.getImage(), frames, dir, currentFrame, currentDir, 
				(int)Math.round(getPXPos()), (int)Math.round(getPYPos()));
		
	}

	private void UpdatePosition(){
		double xCod = 0;
		double yCod = 0;
		boolean isSprint = false;
		if (currentFrame>=frames) currentFrame = 0;
		
		if(keymgr.isPressed(KeyEvent.VK_W)){
			currentDir = UP;
			++frameCounter;
			yCod-= this.getMovementSpeed();
		}
		if(keymgr.isPressed(KeyEvent.VK_S)){
			currentDir = DOWN;
			++frameCounter;
			yCod += this.getMovementSpeed();
		}
		if(keymgr.isPressed(KeyEvent.VK_A)){
			currentDir = LEFT;
			++frameCounter;
			xCod -= this.getMovementSpeed();
		}
		if(keymgr.isPressed(KeyEvent.VK_D)){
			currentDir = RIGHT;
			++frameCounter;
			xCod += this.getMovementSpeed();
		}
		if(keymgr.isPressed(KeyEvent.VK_SHIFT)){
			isSprint = true;
		}
		
		if(xCod != 0 && yCod !=0){
			xCod = xCod / Root2;
			yCod = yCod / Root2;
			--frameCounter;
		}
		if(isSprint){
			xCod *= mulSprint;
			yCod *= mulSprint;
			++frameCounter;
		}
		
		if(frameCounter>50) {
			++currentFrame;
			frameCounter = 0;
		}
		if(xCod == 0 && yCod ==0){
			currentFrame = 0;
		}
		
		setPXPos(getPXPos() + xCod);
		setPYPos(getPYPos() + yCod);
		
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

