package com.justingames.soulagent.core.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.justingames.soulagent.core.GameObjects.Player;

public class Hud implements Disposable {
	private OrthographicCamera camera;
	private Viewport viewport;
	private Stage stage;
	
	public Hud(Player player) {
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(264, 132, camera);
		stage = new Stage(viewport);
		
		final Table table = new Table();
		table.setFillParent(true);
		table.pad(8);
		
		table.row().expand();
		table.add(player.getAttackButton()).size(24, 24).bottom().right().colspan(2);
		table.row().expandX();
		table.add(player.getJoystick()).size(42, 42).bottom().left();
		table.add(player.getJumpButton()).size(32, 32).bottom().right();
		
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}
	
	public void render() {
		stage.draw();
	}
	
	public void update() {
		stage.act();
	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}
