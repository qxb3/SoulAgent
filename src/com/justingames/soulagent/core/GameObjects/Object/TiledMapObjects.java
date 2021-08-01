package com.justingames.soulagent.core.GameObjects.Object;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.justingames.soulagent.core.Game;
import java.util.ArrayList;

public class TiledMapObjects {
	private TiledMap map;
	private World world;
	private int tileWidth, tileHeight;
	private int mapWidth, mapHeight;

	public TiledMapObjects(TiledMap map, World world) {
		this.map = map;
		this.world = world;

		MapProperties properties = map.getProperties();
		tileWidth = properties.get("tilewidth", Integer.class);
		tileHeight = properties.get("tileheight", Integer.class);
		mapWidth = tileWidth * properties.get("width", Integer.class);
		mapHeight = tileHeight * properties.get("height", Integer.class);

		getGround();
	}

	private void getGround() {
		for (MapObject object : map.getLayers().get("collidable").getObjects()) {
			if (object instanceof PolygonMapObject) {
				float[] vertices = ((PolygonMapObject) object).getPolygon().getTransformedVertices();
				float[] worldVertices = new float[vertices.length];
				for (int i = 0; i < vertices.length; i++)
					worldVertices[i] = vertices[i] / Game.PPM;
					
				PolygonShape shape = new PolygonShape();
				shape.set(worldVertices);
				
				BodyDef bodyDef = new BodyDef();
				bodyDef.type = BodyDef.BodyType.StaticBody;
				
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = shape;
				fixtureDef.density = 1000f;
				fixtureDef.friction = 1.16f;
				fixtureDef.filter.categoryBits = Game.GroundBit;
				fixtureDef.filter.maskBits = Game.PlayerBit | Game.PlayerFootBit;
				
				Body body = world.createBody(bodyDef);
				body.createFixture(fixtureDef);
			}
		}
	}

	public Vector2 getPlayerSpawnPoint() {
		for (MapObject object : map.getLayers().get("player_spawn").getObjects()) {
			Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
			return new Vector2(rectangle.x / Game.PPM, rectangle.y / Game.PPM);
		}
		return null;
	}

//	public ArrayList<Vector2> getEnemyPoints() {
//		ArrayList<Vector2> points = new ArrayList<>();
//		for (MapObject object : map.getLayers().get("enemy_points").getObjects()) {
//			Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
//			points.add(new Vector2(rectangle.x / Game.PPM, rectangle.y / Game.PPM));
//		}
//		return points;
//	}

//	public ArrayList<Stone> getStoneSpawnPoints() {
//		ArrayList<Stone> stones = new ArrayList<>();
//		for (MapObject object : map.getLayers().get("stone_points").getObjects()) {
//			Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
//			Stone stone = new Stone(world, new Vector2(rectangle.x / Game.PPM, rectangle.y / Game.PPM), 0.12f, 0.12f);
//			stones.add(stone);
//		}
//		return stones;
//	}

//	public ArrayList<Soul> getSoulSpawnPoints() {
//		ArrayList<Soul> souls = new ArrayList<>();
//		for (MapObject object : map.getLayers().get("soul_points").getObjects()) {
//			Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
//			Soul soul = new Soul(world, new Vector2(rectangle.x / Game.PPM, rectangle.y / Game.PPM), 0.08f);
//			souls.add(soul);
//		}
//		return souls;
//	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}
}
