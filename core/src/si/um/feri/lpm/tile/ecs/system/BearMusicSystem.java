package si.um.feri.lpm.tile.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.assets.AssetManager;

import si.um.feri.lpm.tile.assets.AssetDescriptors;
import si.um.feri.lpm.tile.ecs.component.MovementComponent;
import si.um.feri.lpm.tile.ecs.component.BearComponent;
import si.um.feri.lpm.tile.util.Mappers;

public class BearMusicSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            BearComponent.class,
            MovementComponent.class
    ).get();

    private final AssetManager assetManager;
    private int state = 0;

    public BearMusicSystem(AssetManager assetManager) {
        super(FAMILY);
        this.assetManager = assetManager;
        state = 0;
        assetManager.get(AssetDescriptors.ROAR).setLooping(true);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = Mappers.MOVEMENT.get(entity);
        switch (state) {
            case 0:
                if (movement.speed > 0.02f) {
                    assetManager.get(AssetDescriptors.WALKING_SNOW).play();
                    state = 1;
                }
                break;
            case 1:
                if (!assetManager.get(AssetDescriptors.WALKING_SNOW).isPlaying()) {
                    assetManager.get(AssetDescriptors.ROAR).play();
                    state = 2;
                }
                break;
            case 2:
                if (movement.speed < 0.001f) {
                    assetManager.get(AssetDescriptors.ROAR).stop();
                    //state = 3;
                }
                break;
        }
    }
}
