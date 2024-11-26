package io.github.uoyteamsix;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A class representing the end screen
 * Displays the player's final score and a leaderboard of the top 5 scores
 * New addition
 */
public class EndScreen extends ScreenAdapter {
    private final UniSimGame game;
    private final int score;
    SpriteBatch batch;
    Viewport viewport;
    OrthographicCamera camera;
    BitmapFont fontBig;
    BitmapFont fontSmall;
    Texture endScreen;
    Preferences highScores;
    Stage stage;

    public EndScreen(UniSimGame game, float satisfaction) {
        this.game = game;
        this.score = (int) (satisfaction * 100);

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        batch = new SpriteBatch();
        stage = new Stage(viewport);

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

        // Get highscores
        highScores = Gdx.app.getPreferences("High Scores");

        // Check if and where player's score should be placed on the leaderboard
        for (int i = 0; i < 5; i++) {
            if (highScores.getInteger(String.valueOf(i), 0) < score) {
                insertScore(score, i);
                break;
            }
        }

        // Create display
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        //table.setDebug(true);
        table.add(createGameOverMessage()).top().padBottom(200);
        table.row();
        table.add(createLeaderboard()).bottom();
        table.row();
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        stage.act(delta);

        // Draw assets to screen
        batch.begin();
        batch.draw(endScreen, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        batch.dispose();
        stage.dispose();
    }

    /**
     * Inserts a player's score into the leaderboard
     * Only checks through top 5
     *
     * @param score the player's score
     * @param pos the place the score is being inserted to
     */
    public void insertScore(int score, int pos) {
        int prevScore;
        for (int i = pos; i < 5; i++) {
            String key = String.valueOf(i);
            prevScore = highScores.getInteger(key, 0);
            highScores.putInteger(key, score);
            highScores.flush();
            score = prevScore;
            if (score == 0) { break; }
        }
    }

    /**
     * @return a display with a "game over" message and the player's score
     */
    public Table createGameOverMessage() {
        Table gameOver = new Table();
        var labelStyleBig = new Label.LabelStyle(fontBig, Color.BLACK);
        var labelStyleSmall = new Label.LabelStyle(fontSmall, Color.BLACK);
        gameOver.add(new Label("Game Over", labelStyleBig));
        gameOver.row();
        gameOver.add(new Label("Score: " + score + "%", labelStyleSmall));
        gameOver.row();
        return gameOver;
    }

    /**
     * @return a display with the top 5 scores
     */
    public Table createLeaderboard() {
        Table leaderboard = new Table();
        var labelStyle = new Label.LabelStyle(fontSmall, Color.BLACK);
        for (int i = 0; i < 5; i++) {
            String key = String.valueOf(i);
            int score = highScores.getInteger(key, 0);
            String scoreLabelText = score == 0 ? "" : score + "%";
            Label numberLabel = new Label((i + 1) + ".", labelStyle);
            Label scoreLabel = new Label(scoreLabelText, labelStyle);
            leaderboard.add(numberLabel).left().padRight(500);
            leaderboard.add(scoreLabel).right();
            leaderboard.row();
        }
        return leaderboard;
    }

    /*
    TODO: Leaderboard functionality
    Display:
    - "Submit score to leaderboard" area
    - Make it look the same no matter the screen size

    Function:
    - Textbox to enter name
     */
}
