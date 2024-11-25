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

public class EndScreen extends ScreenAdapter {
    private final UniSimGame game;
    private final float satisfaction;
    SpriteBatch batch;
    Viewport viewport;
    OrthographicCamera camera;
    BitmapFont font;
    Texture endScreen;

    public EndScreen(UniSimGame game, float satisfaction) {
        this.game = game;
        this.satisfaction = satisfaction;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);

        batch = new SpriteBatch();

        var generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font.ttf"));
        var parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 150;
        font = generator.generateFont(parameters);
        font.setColor(Color.BLACK);

        endScreen = new Texture("screens/map-blurred.jpg");
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        GlyphLayout glyphLayout = new GlyphLayout();
        String item = "Game Over";
        glyphLayout.setText(font, item);
        float w = glyphLayout.width;

        batch.begin();
        batch.draw(endScreen, 0, 0, viewport.getWorldWidth(),viewport.getWorldHeight());
        font.draw(batch, glyphLayout, (viewport.getWorldWidth() - w)/2, viewport.getWorldHeight() * 0.8f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /*
    TODO:
    Display:
    - Score
        - adding second font seems to kill both of them, fix needed for that
    - "Submit score to leaderboard" area
    - Leaderboard

    Function:
    - Leaderboard save file
    - Textbox to enter name
    - Only get top 5 of leaderboard/leaderboard has 5 entries at any time
     */
}
