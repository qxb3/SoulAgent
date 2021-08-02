package com.justingames.soulagent.core.Utils.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
	public AssetManager assetManager;
	public Skins skins;
	
	public PlayerAssets playerAssets;
	public GameObjectAssets gameObjectAssets;

	public Assets(AssetManager assetManager) {
		this.assetManager = assetManager;
		skins = new Skins();
		
		playerAssets = new PlayerAssets();
		gameObjectAssets = new GameObjectAssets();
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		skins.dispose();
		playerAssets.dispose();
		gameObjectAssets.dispose();
	}
}
