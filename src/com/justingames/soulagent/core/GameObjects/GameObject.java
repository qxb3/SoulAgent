package com.justingames.soulagent.core.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObject extends Sprite {
	protected World world;
	protected Body body;
	protected Vector2 position;
	
	public GameObject(World world, Vector2 spritePosition) {
		this.world = world;
		this.position = spritePosition;
		define();
	}
	
	public abstract void define()
	public abstract void update(float deltaTime)
	
	public Body getBody() {
		return body;
	}
}
