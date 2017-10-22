package kasania.entity;

import java.awt.event.KeyEvent;

import kasania.input.KeyManager;
import kasania.main.GameManager;
import kasania.update.Updater;

public class Player extends Entity{

	
	private double mulSprint;
	private KeyManager keymgr;
	private static double XPos;
	private static double YPos;
	
	public Player(double xPos, double yPos,EntityID ID,double movementSpeed) {
		super(xPos, yPos, ID);
		setPXPos(xPos);
		setPYPos(yPos);
		this.setMovementSpeed(movementSpeed);
		keymgr = GameManager.getKeymgr();
		
		mulSprint = 1.5;
		
	}
	

	public void Update(){
		UpdatePosition();
		updateCollision();
	}
	
	private void UpdatePosition(){
		double xCod = 0;
		double yCod = 0;
		boolean isSprint = false;
		if(keymgr.isPressed(KeyEvent.VK_W)){
			yCod-= this.getMovementSpeed();
		}
		if(keymgr.isPressed(KeyEvent.VK_S)){
			yCod += this.getMovementSpeed();
		}
		if(keymgr.isPressed(KeyEvent.VK_A)){
			xCod -= this.getMovementSpeed();
		}
		if(keymgr.isPressed(KeyEvent.VK_D)){
			xCod += this.getMovementSpeed();
		}
		if(keymgr.isPressed(KeyEvent.VK_SHIFT)){
			isSprint = true;
		}
		
		if(xCod != 0 && yCod !=0){
			xCod = xCod / root2;
			yCod = yCod / root2;
		}
		if(isSprint){
			xCod *= mulSprint;
			yCod *= mulSprint;
			
		}
		
		setPXPos(getPXPos() + xCod);
		setPYPos(getPYPos() + yCod);
		
		setXPos(getPXPos());
		setYPos(getPYPos());
	}
	
	private void updateCollision(){
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

