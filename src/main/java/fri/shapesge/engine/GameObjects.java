package fri.shapesge.engine;

import fri.shapesge.drawables.GameDrawable;

import java.awt.Graphics2D;
import java.util.ArrayList;

class GameObjects {
    private final ArrayList<GameDrawable> drawables;

    GameObjects() {
        this.drawables = new ArrayList<>();
    }

    public synchronized void registerDrawable(GameDrawable drawable) {
        this.drawables.add(drawable);
    }

    public synchronized void unregisterDrawable(GameDrawable drawable) {
        this.drawables.remove(drawable);
    }

    public synchronized void drawAll(Graphics2D canvas) {
        for (GameDrawable drawable : this.drawables) {
            drawable.draw(canvas);
        }
    }

    public synchronized int getCount() {
        return this.drawables.size();
    }
}
