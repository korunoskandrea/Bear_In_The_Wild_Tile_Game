package si.um.feri.lpm.tile.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT32 =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT32, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<Sound> BUSH_HIT =
            new AssetDescriptor<Sound>(AssetPaths.BUSH_HIT, Sound.class);

    public static final AssetDescriptor<Sound> APPLE_MUNCH =
            new AssetDescriptor<Sound>(AssetPaths.APPLE_MUNCH, Sound.class);

    public static final AssetDescriptor<Music> WALKING_SNOW =
            new AssetDescriptor<Music>(AssetPaths.WALKING_SNOW, Music.class);
    public static final AssetDescriptor<Music> WAVES =
            new AssetDescriptor<Music>(AssetPaths.WAVES, Music.class);

    public static final AssetDescriptor<Music> ROAR =
            new AssetDescriptor<Music>(AssetPaths.ROAR, Music.class);

    public static final AssetDescriptor<TiledMap> TILES =
            new AssetDescriptor<TiledMap>(AssetPaths.TILES, TiledMap.class);

    private AssetDescriptors() {
    }
}
