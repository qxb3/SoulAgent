package com.justingames.soulagent.core.Utils.Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class GameObjectAssets implements Disposable {
	public Texture stoneTexture;
	public Texture soulTexture;
	
	public Animation<TextureRegion> soulAnimation;
	
	public GameObjectAssets() {
		stoneTexture = new Texture("sprites/game-objects/stone.png");
		soulTexture = new Texture("sprites/game-objects/soul-spritesheet.png");
		
		TextureRegion[][] soulRegion = TextureRegion.split(soulTexture, soulTexture.getWidth() / 6, soulTexture.getHeight() / 1);
		soulAnimation = AnimationUtils.get(soulRegion, 6, 1, 0, 0, 0.1f);
	}
	
	@Override
	public void dispose() {
		stoneTexture.dispose();
		soulTexture.dispose();
	}
}
