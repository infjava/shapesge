package fri.shapesge;

import java.awt.Graphics2D;
import java.util.ArrayList;

class GameObjects {
    private final ArrayList<GameDrawable> drawables;

    public GameObjects() {
        this.drawables = new ArrayList<>();
    }

    public synchronized void registerDrawable(GameDrawable drawable) {
        this.drawables.add(drawable);
    }

    public synchronized void drawAll(Graphics2D canvas) {
        for (GameDrawable drawable : this.drawables) {
            drawable.draw(canvas);
        }
    }
}
