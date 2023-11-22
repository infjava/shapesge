package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.Color;

public abstract class FilledDrawable extends GameDrawable {
    public Color color;

    public FilledDrawable(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public void changeColor(String newColor) {
        this.color = Game.getGame().getParser().parseColor(newColor);
    }
}
