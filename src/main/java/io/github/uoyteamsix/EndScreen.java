package io.github.uoyteamsix;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A class representing the end screen
 */
public class EndScreen extends ScreenAdapter {
    private final UniSimGame game;
    private final float satisfaction;
    SpriteBatch batch;
    Viewport viewport;
    OrthographicCamera camera;
    BitmapFont fontBig;
    BitmapFont fontSmall;
    Texture endScreen;

    public EndScreen(UniSimGame game, float satisfaction) {
        this.game = game;
        this.satisfaction = satisfaction;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);

        batch = new SpriteBatch();

        // Create fonts
        var generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font.ttf"));
        var parametersBig = new FreeTypeFontGenerator.FreeTypeFontParameter();
        var parametersSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parametersBig.size = 150;
        fontBig = generator.generateFont(parametersBig);
        fontBig.setColor(Color.BLACK);
        parametersSmall.size = 75;
        fontSmall = generator.generateFont(parametersSmall);
        fontSmall.setColor(Color.BLACK);

        // Set background
        endScreen = new Texture("screens/map-blurred.jpg");
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Create game over message
        GlyphLayout glyphLayoutBig = new GlyphLayout(fontBig, "Game Over");
        float w1 = glyphLayoutBig.width;

        // Create score display
        GlyphLayout glyphLayoutSmall = new GlyphLayout(fontSmall, "Score: " + (int) (satisfaction * 100) + "%");
        float w2 = glyphLayoutSmall.width;

        // Draw assets to screen
        batch.begin();
        batch.draw(endScreen, 0, 0, viewport.getWorldWidth(),viewport.getWorldHeight());
        fontBig.draw(batch, glyphLayoutBig, (viewport.getWorldWidth() - w1)/2, viewport.getWorldHeight() * 0.8f);
        fontSmall.draw(batch, glyphLayoutSmall, (viewport.getWorldWidth() - w2)/2, viewport.getWorldHeight() * 0.6f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /*
    TODO: Leaderboard functionality
    Display:
    - "Submit score to leaderboard" area
    - Leaderboard

    Function:
    - Leaderboard save file
    - Textbox to enter name
    - Only get top 5 of leaderboard/leaderboard has 5 entries at any time
     */
}
