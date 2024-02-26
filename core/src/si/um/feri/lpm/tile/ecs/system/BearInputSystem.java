package si.um.feri.lpm.tile.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;

import si.um.feri.lpm.tile.common.GameManager;
import si.um.feri.lpm.tile.config.GameConfig;
import si.um.feri.lpm.tile.ecs.component.MovementComponent;
import si.um.feri.lpm.tile.ecs.component.BearComponent;
import si.um.feri.lpm.tile.util.Mappers;

public class BearInputSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(BearComponent.class, MovementComponent.class).get();

    public BearInputSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = Mappers.MOVEMENT.get(entity);
        movement.rSpeed = 0;

        if (GameManager.INSTANCE.isGameOver()){
            movement.speed = 0;
            movement.rSpeed = 0;
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.rSpeed = MathUtils.clamp(movement.rSpeed + GameConfig.MAX_BEAR_R_SPEED * deltaTime, -GameConfig.MAX_BEAR_R_SPEED, GameConfig.MAX_BEAR_R_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.rSpeed = MathUtils.clamp(movement.rSpeed - GameConfig.MAX_BEAR_R_SPEED * deltaTime, -GameConfig.MAX_BEAR_R_SPEED, GameConfig.MAX_BEAR_R_SPEED);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movement.speed = MathUtils.clamp(movement.speed + GameConfig.MAX_BEAR_SPEED * deltaTime, 0, GameConfig.MAX_BEAR_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movement.speed = MathUtils.clamp(movement.speed - GameConfig.MAX_BEAR_SPEED * deltaTime, 0, GameConfig.MAX_BEAR_SPEED);
        } else { // friction slows down item
            movement.speed -= movement.speed * deltaTime;
        }

    }
}
