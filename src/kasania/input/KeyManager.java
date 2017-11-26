package kasania.input;

import kasania.main.GameManager;

public class KeyManager {
	private KeyBoard keyboard;
	private boolean[] key;
	private int keyStatus[];
	private final int NONPRESSED = 0;
	private final int FIRSTPRESSED = 1;
	private final int PRESSEDCONTINUE = 2;
	private final int RELEASED = 3;

	{
		keyboard = GameManager.getKeyboard();
		key = keyboard.getKeys();
		keyStatus = new int[key.length];

	}

	public void update() {
		for (int i = 0; i < key.length; i++) {
			if (key[i]) {
				if (keyStatus[i] == NONPRESSED) {
					keyStatus[i] = FIRSTPRESSED;
				} else {
					keyStatus[i] = PRESSEDCONTINUE;
				}
			} else {
				if (keyStatus[i] == RELEASED) {
					keyStatus[i] = NONPRESSED;
				} else if (keyStatus[i] == NONPRESSED) {

				} else {
					keyStatus[i] = RELEASED;
				}

			}

		}

	}

	public boolean isFirstPressed(int keyCode) {

		if (keyStatus[keyCode] == FIRSTPRESSED)
			return true;
		else
			return false;

	}

	public boolean isPressedContinue(int keyCode) {
		if (keyStatus[keyCode] == PRESSEDCONTINUE)
			return true;
		else
			return false;
	}

	public boolean isPressed(int KeyCode) {
		if (keyStatus[KeyCode] == FIRSTPRESSED || keyStatus[KeyCode] == PRESSEDCONTINUE)
			return true;
		else
			return false;

	}

	public boolean isReleased(int keyCode) {

		if (keyStatus[keyCode] == RELEASED)
			return true;
		else
			return false;
	}

	public boolean isPressedAnykey() {
		for (int i = 0; i < key.length; i++) {
			if (keyStatus[i] != NONPRESSED)
				return true;
		}
		return false;
	}

}
