package io.github.uoyteamsix;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndScreen extends ScreenAdapter {
    private final UniSimGame game;
    private final float satisfaction;
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture endScreen;

    public EndScreen(UniSimGame game, float satisfaction) {
        this.game = game;
        this.satisfaction = satisfaction;

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        endScreen = new Texture("screens/map-blurred.jpg");
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        ScreenUtils.clear(0.5f, 0, 0, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(endScreen, 0, 0);
        batch.end();
    }

    /*
    TODO:
    Display:
    - "Game Over" message and score
    - "Submit score to leaderboard" area
    - Leaderboard

    Function:
    - Leaderboard save file
    - Textbox to enter name
    - Only get top 5 of leaderboard/leaderboard has 5 entries at any time
     */
}
