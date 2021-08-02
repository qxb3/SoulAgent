package com.justingames.soulagent.core.GameObjects.Object;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.justingames.soulagent.core.Game;
import com.justingames.soulagent.core.GameObjects.GameObject;

public class Stone extends GameObject {
	
	public Stone(World world, Vector2 position) {
		super(world, position);
		
		this.setBounds(this.position.x, this.position.y, 0.24f, 0.24f);
		this.setRegion(Game.assets.gameObjectAssets.stoneTexture);
	}
	
	@Override
	public void define() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.12f, 0.12f);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(this.position);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 8;
		fixtureDef.filter.categoryBits = Game.StoneBit;
		fixtureDef.filter.maskBits = Game.GroundBit | Game.PlayerBit | Game.PlayerFootBit;
		
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef).setUserData(this);
	}
	
	@Override
	public void update(float deltaTime) {
		this.position = body.getPosition();
		this.setPosition(body.getPosition().x - this.getWidth() / 2, body.getPosition().y - this.getHeight() / 2);
		this.setRotation(body.getAngle());
	}
}
