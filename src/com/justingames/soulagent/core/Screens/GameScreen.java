package com.justingames.soulagent.core.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.justingames.soulagent.core.Game;
import com.justingames.soulagent.core.GameObjects.Object.TiledMapObjects;
import com.justingames.soulagent.core.GameObjects.Player;
import com.justingames.soulagent.core.Scenes.Hud;
import com.justingames.soulagent.core.Utils.Assets.Background;
import com.justingames.soulagent.core.Utils.World.CollisionHandler;

public class GameScreen extends ScreenAdapter {
	private Game game;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private Box2DDebugRenderer debugRenderer;
	private World world;
	private CollisionHandler collisionHandlder;
	
	private Background background;
	
	private TiledMap map;
	private TiledMapObjects mapObjects;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	private Player player;
	
	private Hud hud;
	
	public GameScreen(Game game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(Game.WIDTH, Game.HEIGHT, camera);
		
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -9.8f), false);
		
		collisionHandlder = new CollisionHandler();
		world.setContactListener(collisionHandlder);
		
		background = new Background();
		
		map = new TmxMapLoader().load("maps/level-1.tmx");
		mapObjects = new TiledMapObjects(map, world);
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Game.PPM, Game.batch);
		
		player = new Player(world, mapObjects.getPlayerSpawnPoint());
		
		hud = new Hud(player);
	}
	
	@Override
	public void render(float deltaTime) {
		this.update(deltaTime);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Render the background
		background.render(camera, mapObjects);
		
		//Render the map
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		//Render the game objects
		Game.batch.setProjectionMatrix(camera.combined);
		Game.batch.begin();
		
		player.draw(Game.batch);
		
		Game.batch.end();
		
		//Render the world debug
		debugRenderer.render(world, camera.combined);
		
		//Rendering the HUD
		hud.render();
	}
	
	private void update(float deltaTime) {
		//Clamp and update the camera
		camera.position.x = MathUtils.clamp(player.getBody().getPosition().x, camera.viewportWidth / 2, (mapObjects.getMapWidth() / Game.PPM) - camera.viewportWidth / 2);
		camera.position.y = MathUtils.clamp(player.getBody().getPosition().y, camera.viewportHeight / 2, (mapObjects.getMapHeight() / Game.PPM) - camera.viewportHeight / 2);
		camera.update();
		
		//Update the world and the game objects
		world.step(1 / 60f, 6, 2);
		player.update(deltaTime);
		
		//Updating the HUD
		hud.update();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose() {
		debugRenderer.dispose();
		world.dispose();
		background.dispose();
		map.dispose();
		mapRenderer.dispose();
		hud.dispose();
	}
}
