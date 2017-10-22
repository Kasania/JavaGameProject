package kasania.physics;

import kasania.entity.Entity;

public abstract class Colider {
	
	private Entity entity;
	
	protected double[] coliderange;
	protected static final int x1 = 0, x2 = 1, y1 = 2, y2 = 3;
	
	public Colider(Entity entity){
		this.setEntity(entity);
		coliderange = new double[4];
		coliderange[x1] = entity.getXPos();
//		coliderange[x2] = entity.getXPos() + entity.getImage().getWidth(Renderer.getIO());
		coliderange[y1] = entity.getYPos();
//		coliderange[y2] = entity.getYPos() + entity.getImage().getHeight(Renderer.getIO());
	}
	
	public boolean isColide(Entity entity){
		double[] target = entity.getColider().coliderange;
		
		if(coliderange[x1]<=target[x2]){
			if(coliderange[x2]>=target[x1]){
				if(coliderange[y1]<=target[y2]){
					if(coliderange[y2]>=target[y1]){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	public void Update(){
		coliderange[x1] = entity.getXPos();
//		coliderange[x2] = entity.getXPos() + entity.getImage().getWidth(Renderer.getIO());
		coliderange[y1] = entity.getYPos();
//		coliderange[y2] = entity.getYPos() + entity.getImage().getHeight(Renderer.getIO());
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
