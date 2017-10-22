package kasania.physics;

import kasania.entity.Entity;

public class BoxColider extends Colider {

	public BoxColider(Entity entity) {
		super(entity);

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

}
