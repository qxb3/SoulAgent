package com.justingames.soulagent.core.Utils.Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Skins extends Skin {
	
	public Skins() {
		this.add("touchpad_background", new Texture("hud/touchpad_background.png"));
		this.add("touchpad_knob", new Texture("hud/touchpad_knob.png"));
		this.add("button_jump", new Texture("hud/button_jump.png"));
		this.add("button_jump_pressed", new Texture("hud/button_jump_pressed.png"));
		this.add("button_attack", new Texture("hud/button_attack.png"));
		this.add("button_attack_pressed", new Texture("hud/button_attack_pressed.png"));
	}
}
