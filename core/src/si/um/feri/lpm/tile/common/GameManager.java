package si.um.feri.lpm.tile.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.ashley.core.Entity;


import si.um.feri.lpm.tile.ecs.component.MovementComponent;
import si.um.feri.lpm.tile.util.Mappers;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private final Preferences PREFS;

    private static final String RESULT_BEST = "BEST_RESULT";

    private int result;
    private int health;
    public boolean isInWatter = false;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(GameManager.class.getSimpleName());
    }

    public int getHealth() {
        return health;
    }

    public void damage() {
        health--;

        if (health == 0) {
            if (result > getBestResult()) setBestResult(result);
        }
    }

    public void swim(Entity entity) {
        MovementComponent movementComponent = Mappers.MOVEMENT.get(entity);
        movementComponent.speed -= 0.01;
        if (movementComponent.speed <=0){
            movementComponent.speed = 0.1f;
        }

    }

    public int getResult() {
        return result;
    }

    public void incResult() {
        result++;
    }

    public void resetResult() {
        result = 0;
        health = 50;
    }

    public boolean isGameOver() {
        return getHealth() <= 0;
    }

    public int getBestResult() {
        return PREFS.getInteger(RESULT_BEST, 0);
    }

    public void setBestResult(int result) {
        PREFS.putInteger(RESULT_BEST, result);
        PREFS.flush();
    }
}
