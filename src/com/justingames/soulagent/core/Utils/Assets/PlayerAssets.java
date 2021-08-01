package com.justingames.soulagent.core.Utils.Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class PlayerAssets implements Disposable {
	private Texture playerTexture;
	
	public Animation<TextureRegion> idleAnimation;
	public Animation<TextureRegion> runningAnimation;
	public Animation<TextureRegion> pushingAnimation;
	public Animation<TextureRegion> attackingAnimation;
	public Animation<TextureRegion> jumpingAnimation;
	public Animation<TextureRegion> fallingAnimation;
	public Animation<TextureRegion> rollingAnimation;
	public Animation<TextureRegion> damagedAnimation;
	public Animation<TextureRegion> deathAnimation;
	
	public PlayerAssets() {
		playerTexture = new Texture("sprites/player-spritesheet.png");
		TextureRegion[][] playerRegions = TextureRegion.split(playerTexture, playerTexture.getWidth() / 8, playerTexture.getHeight() / 15);
		
		idleAnimation = AnimationUtils.get(playerRegions, 4, 1, 0, 5, 0.1f);
		runningAnimation = AnimationUtils.get(playerRegions, 6, 1, 0, 1, 0.1f);
		pushingAnimation = AnimationUtils.get(playerRegions, 6, 1, 0, 2, 0.1f);
		attackingAnimation = AnimationUtils.get(playerRegions, 4, 1, 0, 3, 0.1f);
		jumpingAnimation = AnimationUtils.get(playerRegions, 3, 1, 0, 7, 0.1f);
		fallingAnimation = AnimationUtils.get(playerRegions, 3, 1, 0, 6, 0.1f);
		rollingAnimation = AnimationUtils.get(playerRegions, 3, 2, 0, 9, 0.1f);
		damagedAnimation = AnimationUtils.get(playerRegions, 3, 1, 0, 8, 0.1f);
		deathAnimation = AnimationUtils.get(playerRegions, 8, 1, 0, 0, 0.1f);
	}
	
	@Override
	public void dispose() {
		playerTexture.dispose();
	}
}
