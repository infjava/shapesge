package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class RectangularDrawable extends FilledDrawable {
    private int width;
    private int height;

    public RectangularDrawable(int x, int y, int width, int height, Color color) {
        super(x, y, color);

        this.width = width;
        this.height = height;
    }

    public void changeSize(int width, int height) {
        this.width = width;
        this.height = height;

        Game.getGame().somethingHasChanged();
    }

    @Override
    public void draw(Graphics2D canvas) {
        if (!this.isVisible()) {
            return;
        }

        var shape = new Rectangle2D.Double(this.getXPosition(), this.getYPosition(), this.width, this.height);
        canvas.setColor(this.getColor());
        canvas.fill(shape);
    }
}
