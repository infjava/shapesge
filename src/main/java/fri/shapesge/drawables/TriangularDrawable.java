package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class TriangularDrawable extends FilledDrawable {
    private int width;
    private int height;

    public TriangularDrawable(int x, int y, int width, int height, Color color) {
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

        var xPoints = new int[]{this.getXPosition(), this.getXPosition() + (this.width / 2), this.getXPosition() - this.width / 2};
        var yPoints = new int[]{this.getYPosition(), this.getYPosition() + this.height, this.getYPosition() + this.height};
        var shape = new Polygon(xPoints, yPoints, 3);
        canvas.setColor(this.getColor());
        canvas.fill(shape);
    }
}
