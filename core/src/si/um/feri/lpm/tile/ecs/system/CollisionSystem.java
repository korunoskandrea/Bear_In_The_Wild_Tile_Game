package si.um.feri.lpm.tile.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;

import si.um.feri.lpm.tile.assets.AssetDescriptors;
import si.um.feri.lpm.tile.assets.RegionNames;
import si.um.feri.lpm.tile.common.GameManager;
import si.um.feri.lpm.tile.ecs.component.BearComponent;
import si.um.feri.lpm.tile.ecs.component.BoundsComponent;
import si.um.feri.lpm.tile.ecs.component.ObstacleComponent;
import si.um.feri.lpm.tile.ecs.component.ObstacleComponentRiver;
import si.um.feri.lpm.tile.ecs.component.TextureComponent;
import si.um.feri.lpm.tile.ecs.system.passive.SoundSystem;
import si.um.feri.lpm.tile.ecs.system.passive.TiledSystem;
import si.um.feri.lpm.tile.util.Mappers;

public class CollisionSystem extends EntitySystem {

    private static final Family FAMILY_BEAR = Family.all(BearComponent.class, BoundsComponent.class).get();
    private static final Family FAMILY_WATER_BEAR = Family.all(BearComponent.class, BoundsComponent.class).get();
    private static final Family FAMILY_OBSTACLE = Family.all(ObstacleComponent.class, BoundsComponent.class).get();
    private static final Family FAMILY_OBSTACLE_RIVER = Family.all(ObstacleComponentRiver.class, BoundsComponent.class).get();

    private SoundSystem soundSystem;
    private TiledSystem tiledSystem;
    private TextureAtlas gamePlayAtlas;

    public CollisionSystem(AssetManager assetManager) {
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
    }

    @Override
    public void addedToEngine(Engine engine) {
        soundSystem = engine.getSystem(SoundSystem.class);
        tiledSystem = engine.getSystem(TiledSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        if (GameManager.INSTANCE.isGameOver()) return;
        ImmutableArray<Entity> bears = getEngine().getEntitiesFor(FAMILY_BEAR);
        ImmutableArray<Entity> waterBears = getEngine().getEntitiesFor(FAMILY_WATER_BEAR);
        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor(FAMILY_OBSTACLE);
        ImmutableArray<Entity> river = getEngine().getEntitiesFor(FAMILY_OBSTACLE_RIVER);

        for (Entity bear : bears) { //pick collision by tile
            BoundsComponent firstBounds = Mappers.BOUNDS.get(bear);
            bear.getComponent(TextureComponent.class).region = gamePlayAtlas.findRegion(RegionNames.BEAR);

            if (tiledSystem.collideWith(firstBounds.rectangle)) {
                soundSystem.pick();
            }

            for (Entity obstacle : obstacles) {
                ObstacleComponent obstacleComponent = Mappers.OBSTACLE.get(obstacle);
                if (obstacleComponent.hit) {
                    continue;
                }

                BoundsComponent secondBounds = Mappers.BOUNDS.get(obstacle);
                if (Intersector.overlaps(firstBounds.rectangle, secondBounds.rectangle)) {
                    // obstacleComponent.hit = true;
                    GameManager.INSTANCE.damage();
                    soundSystem.obstacle();
                }
            }

            for (Entity obstacle : river) {
                ObstacleComponentRiver obstacleComponentRiver = Mappers.OBSTACLE_RIVER.get(obstacle);
                if (obstacleComponentRiver.hit) {
                    continue;
                }

                BoundsComponent secondBounds = Mappers.BOUNDS.get(obstacle);
                if (Intersector.overlaps(firstBounds.rectangle, secondBounds.rectangle)) {
                    // obstacleComponent.hit = true;
                    GameManager.INSTANCE.swim(bear);
                    bear.getComponent(TextureComponent.class).region = gamePlayAtlas.findRegion(RegionNames.WATER_BEAR);

                    soundSystem.obstacleRiver();
                }
            }
        }
    }

}