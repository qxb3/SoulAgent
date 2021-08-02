package com.justingames.soulagent.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.justingames.soulagent.core.Screens.GameScreen;
import com.justingames.soulagent.core.Utils.Assets.Assets;

public class Game extends Game {
	public static final float WIDTH = 2.56f;
	public static final float HEIGHT = 2.56f;
	public static final float PPM = 100;
	
	public static final short GroundBit = 1;
	public static final short PlayerBit = 2;
	public static final short PlayerFootBit = 4;
	public static final short StoneBit = 8;
	
	public static SpriteBatch batch;
	public static Assets assets;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		assets = new Assets(new AssetManager());
		
		setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
	}
}
