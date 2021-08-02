package com.justingames.soulagent.core.Utils.World;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.justingames.soulagent.core.Game;
import com.justingames.soulagent.core.GameObjects.Player;

public class CollisionHandler implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		final int fixtureBit = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;
		
		switch(fixtureBit) {
			case Game.PlayerFootBit | Game.GroundBit:
			case Game.PlayerFootBit | Game.StoneBit:
				if (fixtureA.getFilterData().categoryBits == Game.PlayerFootBit)
					((Player) fixtureA.getUserData()).canJump = true;
				if (fixtureB.getFilterData().categoryBits == Game.PlayerFootBit)
					((Player) fixtureB.getUserData()).canJump = true;
				break;
			default:
				break;
		}
	}
	
	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		final int fixtureBit = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;
		
		switch(fixtureBit) {
			case Game.PlayerFootBit | Game.GroundBit:
			case Game.PlayerFootBit | Game.StoneBit:
				if (fixtureA.getFilterData().categoryBits == Game.PlayerFootBit)
					((Player) fixtureA.getUserData()).canJump = false;
				if (fixtureB.getFilterData().categoryBits == Game.PlayerFootBit)
					((Player) fixtureB.getUserData()).canJump = false;
				break;
			default:
				break;
		}
	}
	
	@Override
	public void preSolve(Contact contact, Manifold manifold) {
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}
