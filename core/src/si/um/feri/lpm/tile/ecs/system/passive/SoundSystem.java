package si.um.feri.lpm.tile.ecs.system.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import si.um.feri.lpm.tile.assets.AssetDescriptors;

public class SoundSystem extends EntitySystem {

    private final AssetManager assetManager;

    private Sound pickSound;
    private Sound obstacleSound;
    private Music obstacleRiverSound;
    private float obstacleTimePlaying;

    public SoundSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        // setProcessing(false);    // passive
        init();
    }

    private void init() {
        pickSound = assetManager.get(AssetDescriptors.APPLE_MUNCH);
        obstacleSound = assetManager.get(AssetDescriptors.BUSH_HIT);
        obstacleRiverSound = assetManager.get(AssetDescriptors.WAVES); // todo change the sound
    }

    public void pick() {
        pickSound.play();
    }

    public void obstacle() {
        if (obstacleTimePlaying < 0) {
            obstacleSound.play();
            obstacleTimePlaying = 1500; // 2 s
        }
    }

    public void obstacleRiver() {
        if (obstacleTimePlaying < 0) {
            obstacleRiverSound.play();
            obstacleRiverSound.setLooping(true);
            obstacleTimePlaying = 1500; // 2 s
        }
    }

    @Override
    public void update(float deltaTime) {
        obstacleTimePlaying -= deltaTime;
    }
}