package kasania.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener{
	
	private boolean keys[] ;
	{
		keys = new boolean[65535];
	}
	public boolean[] getKeys() {
		return keys;
	}

	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		keys[i] = true;
	}

	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		keys[i] = false;
	}

	public void keyTyped(KeyEvent e) {
	}
}
