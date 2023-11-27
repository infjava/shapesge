package fri.shapesge;

import fri.shapesge.engine.Game;

import java.awt.image.BufferedImage;

/**
 * Trieda DataObrazku reprezentuje bitmapový obrázok, ktorý je možné vykresliť pomocou triedy {@link Obrazok}
 *
 * @author Ján Janech
 * @version 1.0
 */
public class DataObrazku {
    private final BufferedImage image;

    /**
     * Prečíta obrázok z daného súboru.
     * @param suborSObrazkom cesta k súboru s obrázkom
     */
    public DataObrazku(String suborSObrazkom) {
        this.image = Game.getGame().getParser().parseImage(suborSObrazkom);
    }

    /**
     * Vráti šírku obrázku.
     * @return šírka obrázku
     */
    public int getSirka() {
        return this.image.getWidth();
    }

    /**
     * Vráti výšku obrázku.
     * @return výška obrázku
     */
    public int getVyska() {
        return this.image.getHeight();
    }

    BufferedImage getImage() {
        return this.image;
    }
}
