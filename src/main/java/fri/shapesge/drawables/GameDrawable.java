package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.Graphics2D;

public abstract class GameDrawable {
    protected int xPosition;
    protected int yPosition;
    protected boolean isVisible;

    public GameDrawable(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.isVisible = false;
    }

    public void makeVisible() {
        if (this.isVisible) {
            return;
        }

        Game.getGame().registerDrawable(this);
        this.isVisible = true;
    }

    public void makeInvisible() {
        if (!this.isVisible) {
            return;
        }

        Game.getGame().unregisterDrawable(this);
        this.isVisible = false;
    }

    public void moveBy(int dx, int dy) {
        this.xPosition += dx;
        this.yPosition += dy;
    }

    public abstract void draw(Graphics2D canvas);
}
