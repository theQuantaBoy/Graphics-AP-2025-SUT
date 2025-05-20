package com.ap_graphics.model;

import com.ap_graphics.model.enums.Avatar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class CharacterSprite {
    private static final float BASE_SPEED = 200f;
    private final Avatar avatar;
    private final Vector2 position;
    private final Vector2 velocity;
    private final Texture spriteSheet;
    private final Animation<TextureRegion> walkDownAnimation;
    private final Animation<TextureRegion> walkUpAnimation;
    private final Animation<TextureRegion> walkLeftAnimation;
    private final Animation<TextureRegion> walkRightAnimation;
    private float stateTime;
    private boolean isMoving;
    private String currentDirection;

    public CharacterSprite(Avatar avatar) {
        this.avatar = avatar;
        this.position = new Vector2(400, 300);
        this.velocity = new Vector2();
        this.stateTime = 0f;
        this.isMoving = false;
        this.currentDirection = "down";

        // Load sprite sheet
        this.spriteSheet = new Texture(avatar.getSpritePath());

        // Create animations for each direction
        this.walkDownAnimation = createWalkAnimation(0); // Assuming first row is down
        this.walkLeftAnimation = createWalkAnimation(1); // Assuming second row is left
        this.walkRightAnimation = createWalkAnimation(2); // Assuming third row is right
        this.walkUpAnimation = createWalkAnimation(3); // Assuming fourth row is up
    }

    private Animation<TextureRegion> createWalkAnimation(int row) {
        Array<TextureRegion> frames = new Array<>();
        int frameWidth = spriteSheet.getWidth() / 4; // Assuming 4 frames per animation
        int frameHeight = spriteSheet.getHeight() / 4; // Assuming 4 directions

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(spriteSheet,
                    i * frameWidth,
                    row * frameHeight,
                    frameWidth,
                    frameHeight));
        }

        return new Animation<>(0.1f, frames);
    }

    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);

        if (isMoving) {
            stateTime += delta;
        } else {
            stateTime = 0;
        }
    }

    public void render(SpriteBatch batch) {
        Animation<TextureRegion> currentAnimation;
        switch (currentDirection) {
            case "up":
                currentAnimation = walkUpAnimation;
                break;
            case "down":
                currentAnimation = walkDownAnimation;
                break;
            case "left":
                currentAnimation = walkLeftAnimation;
                break;
            case "right":
                currentAnimation = walkRightAnimation;
                break;
            default:
                currentAnimation = walkDownAnimation;
        }

        TextureRegion currentFrame;
        if (isMoving) {
            currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = currentAnimation.getKeyFrames()[0];
        }

        batch.draw(currentFrame,
                position.x - currentFrame.getRegionWidth() / 2,
                position.y - currentFrame.getRegionHeight() / 2);
    }

    public boolean handleKeyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                velocity.y = BASE_SPEED;
                currentDirection = "up";
                isMoving = true;
                return true;
            case Input.Keys.S:
                velocity.y = -BASE_SPEED;
                currentDirection = "down";
                isMoving = true;
                return true;
            case Input.Keys.A:
                velocity.x = -BASE_SPEED;
                currentDirection = "left";
                isMoving = true;
                return true;
            case Input.Keys.D:
                velocity.x = BASE_SPEED;
                currentDirection = "right";
                isMoving = true;
                return true;
        }
        return false;
    }

    public boolean handleKeyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.S:
                velocity.y = 0;
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                velocity.x = 0;
                break;
        }
        isMoving = velocity.x != 0 || velocity.y != 0;
        return false;
    }

    public void dispose() {
        if (spriteSheet != null) {
            spriteSheet.dispose();
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Avatar getAvatar() {
        return avatar;
    }
}
