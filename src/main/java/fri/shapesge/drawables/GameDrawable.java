package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.Graphics2D;

public abstract class GameDrawable {
    private int xPosition;
    private int yPosition;
    private boolean isVisible;

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

        Game.getGame().somethingHasChanged();
    }

    public void makeInvisible() {
        if (!this.isVisible) {
            return;
        }

        Game.getGame().unregisterDrawable(this);
        this.isVisible = false;

        Game.getGame().somethingHasChanged();
    }

    public void moveTo(int newX, int newY) {
        this.xPosition = newX;
        this.yPosition = newY;

        Game.getGame().somethingHasChanged();
    }

    public void moveBy(int dx, int dy) {
        this.xPosition = this.xPosition + dx;
        this.yPosition = this.yPosition + dy;

        Game.getGame().somethingHasChanged();
    }

    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean isVisible() {
        return this.isVisible;
    }

    public abstract void draw(Graphics2D canvas);
}
