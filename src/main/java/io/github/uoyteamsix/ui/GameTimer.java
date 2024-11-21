package io.github.uoyteamsix.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.uoyteamsix.GameLogic;
import io.github.uoyteamsix.Timer;

/**
 * A class to represent the game timer UI element.
 */
public class GameTimer extends Table {
    // Multiplier for the background image size.
    // TODO: Make configurable in settings.
    private static final float IMAGE_SCALE = 3.0f;

    private final UiAssets uiAssets;
    private final GameLogic gameLogic;
    private Timer gameTimer;
    private Label timeLabel;
    private Image backgroundImage;

    public GameTimer(UiAssets uiAssets, GameLogic gameLogic, Timer gameTimer) {
        this.uiAssets = uiAssets;
        this.gameLogic = gameLogic;
        this.gameTimer = gameTimer;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Convert time in seconds to minutes and seconds.
        float remainingTime = gameTimer.getTimeLeft();
        int minutes = (int) (remainingTime / 60.0f);
        int seconds = ((int) remainingTime) % 60;
        if (timeLabel != null) {
            timeLabel.setText(String.format("%d:%02d", minutes, seconds));
        }

        // Create time label once fonts have been loaded.
        if (timeLabel == null && uiAssets.hasFontsLoaded()) {
            var labelStyle = new Label.LabelStyle(uiAssets.getLargeFont(), Color.BLACK);
            timeLabel = new Label("", labelStyle);
            timeLabel.setAlignment(Align.center);
        }

        // Create background image once spritesheet has been loaded.
        if (backgroundImage == null && uiAssets.hasSpritesheetLoaded()) {
            var textureRegion = new TextureRegion(uiAssets.getSpritesheet(), 288, 128, 64, 32);
            backgroundImage = new Image(textureRegion);
        }

        // Add image and label to the stack once they have both been created.
        if (getChildren().isEmpty() && backgroundImage != null && timeLabel != null) {
            var textureRegion = ((TextureRegionDrawable) backgroundImage.getDrawable()).getRegion();
            add(backgroundImage).size(textureRegion.getRegionWidth() * IMAGE_SCALE,
                    textureRegion.getRegionHeight() * IMAGE_SCALE);
            row();
            add(timeLabel).align(Align.top).padTop(-48.0f * 1.65f);
        }
    }
}
