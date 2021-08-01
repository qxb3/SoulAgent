package com.justingames.soulagent.core.Utils.Assets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.justingames.soulagent.core.Game;
import com.justingames.soulagent.core.GameObjects.Object.TiledMapObjects;

public class Background implements Disposable {
	private Texture[] backgrounds;
	
	public Background() {
		backgrounds = new Texture[] {
			new Texture("maps/tileset/background-0.png"),
			new Texture("maps/tileset/background-1.png"),
			new Texture("maps/tileset/background-2.png"),
		};
	}

	public void render(OrthographicCamera camera, TiledMapObjects mapObjects) {
		Game.batch.begin();
		for (Texture background : backgrounds)
			Game.batch.draw(background, camera.position.x - Game.WIDTH, 0, Game.HEIGHT * 2, mapObjects.getMapHeight() / Game.PPM);
		Game.batch.end();
	}

	@Override
	public void dispose() {
		for (Texture background : backgrounds)
			background.dispose();
	}
}
