package com.justingames.soulagent.core.Utils.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
	public static final Assets instance = new Assets();
	
	public AssetManager assetManager;
	public Skins skins;
	
	public PlayerAssets playerAssets;

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		skins = new Skins();
		
		playerAssets = new PlayerAssets();
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		skins.dispose();
		playerAssets.dispose();
	}
}
