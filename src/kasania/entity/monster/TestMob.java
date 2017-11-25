package kasania.entity.monster;

import kasania.entity.EntityID;

public class TestMob extends Monster{

	public TestMob(double xPos, double yPos,EntityID ID) {
		super(xPos, yPos,ID);
		setXPos(xPos);
		setYPos(yPos);
		this.setMovementSpeed(1);
		this.setVisible(false);
	}
	


}