package fri.shapesge;

import fri.shapesge.drawables.TextDrawable;

import java.awt.Color;
import java.awt.Font;
import java.util.EnumSet;

/**
 * Text, s ktorým možno pohybovať a nakreslí sa na plátno.
 *
 * @author Ján Janech
 * @version 1.0  (25.11.2022)
 */

@SuppressWarnings("unused")
public class BlokTextu {
    private final TextDrawable drawable;

    /**
     * Vytvor nový text preddefinovanej farby na danej pozícii.
     * @param text text, ktorý sa má zobraziť
     * @param x x-ová súradnica textu
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica textu
     *          (vzdialenosť od horného okraja plátna)
     */
    @SuppressWarnings("unused")
    public BlokTextu(String text, int x, int y) {
        this.drawable = new TextDrawable(x, y, Color.black, text, new Font(Font.SERIF, Font.PLAIN, 12));
    }

    /**
     * Vytvor nový text preddefinovanej farby na preddefinovanej pozícii.
     * @param text text, ktorý sa má zobraziť
     */
    @SuppressWarnings("unused")
    public BlokTextu(String text) {
        this(text, 0, 20);
    }

    /**
     * Zobraz sa.
     */
    @SuppressWarnings("unused")
    public void zobraz() {
        this.drawable.makeVisible();
    }

    /**
     * Skry sa.
     */
    @SuppressWarnings("unused")
    public void skry() {
        this.drawable.makeInvisible();
    }

    /**
     * Posuň sa vpravo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVpravo() {
        this.drawable.moveBy(20, 0);
    }

    /**
     * Posuň sa vľavo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVlavo() {
        this.drawable.moveBy(-20, 0);
    }

    /**
     * Posuň sa hore o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunHore() {
        this.drawable.moveBy(0, -20);
    }

    /**
     * Posuň sa dole o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunDole() {
        this.drawable.moveBy(0, 20);
    }

    /**
     * Posuň sa vodorovne o dĺžku danú parametrom.
     * @param vzdialenost vzdialenosť v pixeloch
     */
    @SuppressWarnings("unused")
    public void posunVodorovne(int vzdialenost) {
        this.drawable.moveBy(vzdialenost, 0);
    }

    /**
     * Posuň sa zvisle o dĺžku danú parametrom.
     * @param vzdialenost vzdialenosť v pixeloch
     */
    @SuppressWarnings("unused")
    public void posunZvisle(int vzdialenost) {
        this.drawable.moveBy(0, vzdialenost);
    }

    /**
     * Zmeň font textu podľa požiadaviek.
     * @param font    názov fontu
     *                (napr. "Arial", "Times New Roman", "Courier New")
     * @param styl    štýl fontu
     *                (napr. {@link StylFontu#BOLD}, {@link StylFontu#ITALIC}, {@link StylFontu#UNDERLINE})
     * @param velkost veľkosť fontu v pixeloch
     */
    @SuppressWarnings("unused")
    public void zmenFont(String font, StylFontu styl, int velkost) {
        this.drawable.changeFont(font, styl == StylFontu.BOLD, styl == StylFontu.ITALIC, styl == StylFontu.UNDERLINE, velkost);
    }

    /**
     * Zmeň font textu podľa požiadaviek.
     * @param font    názov fontu
     *                (napr. "Arial", "Times New Roman", "Courier New")
     * @param styl    štýl fontu
     *                (napr. {@code EnumSet.of(StylFontu.BOLD, StylFontu.ITALIC)})
     * @param velkost veľkosť fontu v pixeloch
     */
    @SuppressWarnings("unused")
    public void zmenFont(String font, EnumSet<StylFontu> styl, int velkost) {
        this.drawable.changeFont(font, styl.contains(StylFontu.BOLD), styl.contains(StylFontu.ITALIC), styl.contains(StylFontu.UNDERLINE), velkost);
    }

    /**
     * Zmeň farbu na hodnotu danú parametrom.
     * @param farba nová farba z palety alebo v tvare #rrggbb
     */
    @SuppressWarnings("unused")
    public void zmenFarbu(String farba) {
        this.drawable.changeColor(farba);
    }

    /**
     * Zmeň text.
     * @param text nový text
     */
    @SuppressWarnings("unused")
    public void zmenText(String text) {
        this.drawable.changeText(text);
    }

    /**
     * Zmeň polohu textu na hodnoty dané parametrami.
     * @param x x-ová súradnica textu
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica textu
     *          (vzdialenosť od horného okraja plátna)
     */
    @SuppressWarnings("unused")
    public void zmenPolohu(int x, int y) {
        this.drawable.moveTo(x, y);
    }

    /**
     * @return x-ová súradnica (vzdialenosť od ľavého okraja) tvaru
     */
    public int getPoziciaX() {
        return this.drawable.getXPosition();
    }

    /**
     * @return y-ová súradnica (vzdialenosť od horného okraja) tvaru
     */
    public int getPoziciaY() {
        return this.drawable.getYPosition();
    }
}
