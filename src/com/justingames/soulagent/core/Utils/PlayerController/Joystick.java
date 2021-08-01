package com.justingames.soulagent.core.Utils.PlayerController;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Joystick extends Touchpad {
	
	public Joystick(int deadzone, Drawable background, Drawable knob) {
		super(deadzone, getStyle(background, knob));
	}

	private static Touchpad.TouchpadStyle getStyle(Drawable background, Drawable knob) {
		Touchpad.TouchpadStyle style = new TouchpadStyle();
		style.background = background;
		style.knob = knob;

		return style;
	}

	public boolean isLeft() {
		if (this.getKnobPercentX() < 0)
			return true;

		return false;
	}

	public boolean isRight() {
		if (this.getKnobPercentX() > 0)
			return true;

		return false;
	}

	public boolean isCenter() {
		if (this.getKnobPercentX() == 0 && this.getKnobPercentY() == 0)
			return true;

		return false;
	}
}
