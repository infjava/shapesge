package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class EllipticalDrawable extends FilledDrawable {
    private int diameterX;
    private int diameterY;

    public EllipticalDrawable(int x, int y, int diameterX, int diameterY, Color color) {
        super(x, y, color);

        this.diameterX = diameterX;
        this.diameterY = diameterY;
    }

    public void changeSize(int newDiameterX, int newDiameterY) {
        this.diameterX = newDiameterX;
        this.diameterY = newDiameterY;

        Game.getGame().somethingHasChanged();
    }

    @Override
    public void draw(Graphics2D canvas) {
        if (!this.isVisible()) {
            return;
        }

        var shape = new Ellipse2D.Double(this.getXPosition(), this.getYPosition(), this.diameterX, this.diameterY);
        canvas.setColor(this.getColor());
        canvas.fill(shape);
    }

    public int getDiameterX() {
        return this.diameterX;
    }

    public int getDiameterY() {
        return this.diameterY;
    }
}
