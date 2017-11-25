package kasania.entity;

import kasania.physics.Colider;
import kasania.render.Renderer;
import kasania.resources.image.ImageName;

public abstract class Entity {
	
	private EntityID ID;
	
	private double XPos;
	private double YPos;
	protected final double Root2 = Math.sqrt(2);
	protected final double PI = Math.PI;
	private ImageName image;
	protected Colider colider;
	private double movementSpeed;
	
	protected boolean isVisible;
	
	protected final int UP = 0;
	protected final int LEFT = 1;
	protected final int DOWN = 2;
	protected final int RIGHT = 3;
	
	
	public Entity(double xPos, double yPos, EntityID ID){
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setID(ID);
		this.isVisible = true;
		this.colider = null;
	}

	public void Update(){
		if(colider != null){
			colider.Update();
		}
	}
	
	public void Render(){
		if (!isVisible) return;
		Renderer.drawAtPos(getImage(), (int)Math.round(getXPos()), (int)Math.round(getYPos()));
	}
	
	public EntityID getID() {
		return ID;
	}

	public void setID(EntityID iD) {
		ID = iD;
	}
	
	public double getXPos() {
		return XPos;
	}

	public void setXPos(double xPos) {
		XPos = xPos;
	}

	public double getYPos() {
		return YPos;
	}

	public void setYPos(double yPos) {
		YPos = yPos;
	}
	
	public ImageName getImage() {
		return image;
	}

	public void setImage(ImageName image) {
		this.image = image;
	}

	public Colider getColider() {
		return colider;
	}

	public void setColider(Colider colider) {
		this.colider = colider;
	}
	
	public void setVisible(boolean b){
		this.isVisible = b;
	}
	
	public double getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(double movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
}
