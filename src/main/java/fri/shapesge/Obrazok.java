package fri.shapesge;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Trieda Obrazok, reprezentuje bitmapový obrázok, ktorý môže byť vykreslený na plátno.
 *
 * @author originál: Miroslav Kvaššay and Michal Varga
 * @author engine: Ján Janech
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Obrazok {
    private static final AffineTransform IDENTITY_TRANSFORM = new AffineTransform();

    private final ImageDrawable drawable;
    private int xPosition;
    private int yPosition;
    private int angle;
    private AffineTransform transform;
    private BufferedImage image;
    private boolean isVisible;

    /**
     * Vytvor nový obrázok s danou cestou na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Obrazok(String suborSObrazkom) {
        this(suborSObrazkom, 100, 100);
    }

    @SuppressWarnings("unused")
    public Obrazok(String imagePath, int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.angle = 0;

        this.computeTransformation();

        this.image = Game.getGame().getParser().parseImage(imagePath);
        this.isVisible = false;

        this.drawable = new ImageDrawable();
    }

    /**
     * Zobraz sa.
     */
    @SuppressWarnings("unused")
    public void zobraz() {
        if (this.isVisible) {
            return;
        }

        Game.getGame().registerDrawable(this.drawable);
        this.isVisible = true;
    }

    /**
     * Skry sa.
     */
    @SuppressWarnings("unused")
    public void skry() {
        if (!this.isVisible) {
            return;
        }

        Game.getGame().unregisterDrawable(this.drawable);
        this.isVisible = true;
    }

    /**
     * Posuň sa vpravo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVpravo() {
        this.posunVodorovne(20);
    }

    /**
     * Posuň sa vľavo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVlavo() {
        this.posunVodorovne(-20);
    }

    /**
     * Posuň sa hore o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunHore() {
        this.posunZvisle(-20);
    }

    /**
     * Posuň sa dole o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunDole() {
        this.posunZvisle(20);
    }

    /**
     * Posuň sa zvisle o dĺžku danú parametrom.
     */
    @SuppressWarnings("unused")
    public void posunVodorovne(int vzdialenost) {
        this.xPosition += vzdialenost;
        this.computeTransformation();
    }

    /**
     * Posuň sa pomaly vodorovne o dĺžku danú parametrom.
     */
    @SuppressWarnings("unused")
    public void posunZvisle(int vzdialenost) {
        this.yPosition += vzdialenost;
        this.computeTransformation();
    }

    /**
     * Zmeň obrázok.
     * Súbor s obrázkom musí existovať.
     */
    @SuppressWarnings("unused")
    public void zmenObrazok(String suborSObrazkom) {
        this.image = Game.getGame().getParser().parseImage(suborSObrazkom);
    }

    /**
     * Zmeň uhol natočenia obrázku podľa parametra. Sever = 0.
     */
    @SuppressWarnings("unused")
    public void zmenUhol(int uhol) {
        this.angle = uhol;
        this.computeTransformation();
    }

    private void computeTransformation() {
        if (this.angle == 0 && this.xPosition == 0 && this.yPosition == 0) {
            this.transform = IDENTITY_TRANSFORM;
        } else if (this.angle == 0) {
            this.transform = AffineTransform.getTranslateInstance(this.xPosition, this.yPosition);
        } else {
            var centerX = this.image.getWidth() / 2.0;
            var centerY = this.image.getHeight() / 2.0;
            var transformation = new AffineTransform();
            transformation.translate(this.xPosition + centerX, this.yPosition + centerY);
            transformation.rotate(Math.toRadians(this.angle));
            transformation.translate(-centerX, -centerY);
            this.transform = transformation;
        }
    }

    private class ImageDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            if (!Obrazok.this.isVisible) {
                return;
            }

            canvas.drawImage(Obrazok.this.image, Obrazok.this.transform, null);
        }
    }
}
