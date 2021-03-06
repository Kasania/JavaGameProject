package kasania.entity.monster;

import kasania.entity.Entity;
import kasania.entity.EntityID;

public abstract class Monster extends Entity {

	public Monster(double xPos, double yPos,EntityID ID) {
		super(xPos, yPos, ID);
	}
	
	public void RandomMove(int range){
		int x = (int) (Math.random()*range-range/2.0);
		int y = (int) (Math.random()*range-range/2.0);
		this.setXPos(this.getXPos() + x);
		this.setYPos(this.getYPos() + y);
	}
	
	public void ChaseTarget(Entity target){
		double targetX = target.getXPos();
		double targetY = target.getYPos();
		
		double dir = -(Math.atan2(targetY-this.getYPos(), targetX-this.getXPos())-0.25*PI*2);
		
		this.setXPos(this.getXPos() + this.getMovementSpeed()*Math.sin(dir));
		this.setYPos(this.getYPos() + this.getMovementSpeed()*Math.cos(dir));
	}

}
