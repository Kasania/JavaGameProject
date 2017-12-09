package kasania.resources.image;

public class SImage {
	
	private int[] data;
	private int width;
	private int height;
	
	public SImage(int[] data, int width, int height){
		this.data = data;
		this.width = width;
		this.height = height;
	}
	
	public int[] getData(){
		return data;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
