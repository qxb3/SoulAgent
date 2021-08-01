package com.justingames.soulagent.core.GameObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.justingames.soulagent.core.Game;
import com.justingames.soulagent.core.Utils.Assets.Assets;
import com.justingames.soulagent.core.Utils.PlayerController.ImageButton;
import com.justingames.soulagent.core.Utils.PlayerController.Joystick;
import com.badlogic.gdx.physics.box2d.EdgeShape;

public class Player extends GameObject {
	private Joystick joystick;
	private ImageButton attackButton;
	private ImageButton jumpButton;
	
	private State currentState;
	private State previousState;
	
	public boolean canJump = false;
	private boolean runningRight = true;
	private boolean isDamaged = false;
	private boolean isDead = false;
	
	private float stateTimer = 0;
	
	public Player(World world, Vector2 position) {
		super(world, position);
		
		joystick = new Joystick(0, Assets.instance.skins.getDrawable("touchpad_background"), Assets.instance.skins.getDrawable("touchpad_knob"));
		attackButton = new ImageButton(Assets.instance.skins.getDrawable("button_attack"), Assets.instance.skins.getDrawable("button_attack_pressed"));
		attackButton.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				attackButton.isTouched = true;
				return true;
			}
		});
		jumpButton = new ImageButton(Assets.instance.skins.getDrawable("button_jump"), Assets.instance.skins.getDrawable("button_jump_pressed"));
		jumpButton.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				jumpButton.isTouched = true;
				return true;
			}
		});
		
		currentState = State.STANDING;
		previousState = State.STANDING;
		
		this.setBounds(0, 0, 0.32f, 0.32f);
		this.setRegion(Assets.instance.playerAssets.idleAnimation.getKeyFrame(stateTimer, true));
	}
	
	@Override
	public void define() {
		CircleShape shape = new CircleShape();
        shape.setRadius(0.16f);
		
		BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
		fixtureDef.density = 4f;
        fixtureDef.friction = 0.01f;
		fixtureDef.filter.categoryBits = Game.PlayerBit;
		fixtureDef.filter.maskBits = Game.GroundBit;
		
		body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(this);
		
		fixtureDef.density = 0f;
		fixtureDef.friction = 0f;
		
		EdgeShape edgeShape = new EdgeShape();
		
		edgeShape.set(new Vector2(-0.08f, -0.16f), new Vector2(0.08f, -0.16f));
		fixtureDef.shape = edgeShape;
		fixtureDef.filter.categoryBits = Game.PlayerFootBit;
		fixtureDef.filter.maskBits = Game.GroundBit;
		body.createFixture(fixtureDef).setUserData(this);
	}
	
	@Override
	public void update(float deltaTime) {
		this.position = body.getPosition();
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(deltaTime));
		handleInput();
	}
	
	public void handleInput() {
		float horizontalSpeed = body.getLinearVelocity().x;
		float verticalSpeed = body.getLinearVelocity().y;
		
		if (!isDamaged || isDead) {
			if (joystick.isLeft() && horizontalSpeed >= - 1.2f) {
				body.applyLinearImpulse(new Vector2(-0.06f, 0), body.getWorldCenter(), true);
				runningRight = false;
			} else if (joystick.isRight() && horizontalSpeed <= 1.2f) {
				body.applyLinearImpulse(new Vector2(0.06f, 0), body.getWorldCenter(), true);
				runningRight = true;
			} else if (joystick.isCenter()) {
				body.setLinearVelocity(0, verticalSpeed);
			}
			
			if (jumpButton.isTouched && canJump) {
				body.applyLinearImpulse(new Vector2(0, 1), body.getWorldCenter(), true);
				canJump = false;
			}
		}
		
		attackButton.isTouched = false;
		jumpButton.isTouched = false;
	}
	
	private TextureRegion getFrame(float deltaTime) {
		currentState = getState();
		TextureRegion region;
       
		switch (currentState) {
            case JUMPING:
                region = Assets.instance.playerAssets.jumpingAnimation.getKeyFrame(stateTimer, true);
                break;
            case RUNNING:
                region = Assets.instance.playerAssets.runningAnimation.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = Assets.instance.playerAssets.fallingAnimation.getKeyFrame(stateTimer, true);
                break;
            case DEAD:
                region = Assets.instance.playerAssets.deathAnimation.getKeyFrame(stateTimer, false);
                break;
            case STANDING:
				region = Assets.instance.playerAssets.idleAnimation.getKeyFrame(stateTimer, true);
				break;
            default:
                region = Assets.instance.playerAssets.idleAnimation.getKeyFrame(stateTimer, true);
                break;
        }

        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        if (currentState == previousState)
            stateTimer += deltaTime;
        else
            stateTimer = 0;

        previousState = currentState;

        return region;
	}
	
	public State getState() {
		if (isDead)
			return State.DEAD;
        if (body.getLinearVelocity().y < 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.FALLING;
        else if (body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if (body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else
            return State.STANDING;
	}
	
	public Joystick getJoystick() {
		return joystick;
	}
	
	public ImageButton getAttackButton() {
		return attackButton;
	}
	
	public ImageButton getJumpButton() {
		return jumpButton;
	}
	
	public static enum State {
		STANDING, RUNNING,
		JUMPING, FALLING,
		ROLLING, ATTACKING,
		DEAD
	}
}
