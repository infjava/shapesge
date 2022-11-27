package fri.shapesge;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Text, s ktorým možno pohybovať a nakreslí sa na plátno.
 *
 * @author Ján Janech
 * @version 1.0  (25.11.2022)
 */

@SuppressWarnings("unused")
public class Text {
    private final TextDrawable drawable;
    private String text;
    private Font font;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;

    /**
     * Vytvor nový text preddefinovanej farby na preddefinovanej pozícii.
     */
    @SuppressWarnings("unused")
    public Text(String text, int x, int y) {
        this.text = text;

        this.font = new Font(Font.SERIF, Font.PLAIN, 12);

        this.xPosition = x;
        this.yPosition = y;
        this.color = Color.black;
        this.isVisible = false;

        this.drawable = new TextDrawable();
    }

    @SuppressWarnings("unused")
    public Text(String text) {
        this(text, 0, 20);
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
     * Posuň sa vodorovne o dĺžku danú parametrom.
     */
    @SuppressWarnings("unused")
    public void posunVodorovne(int vzdialenost) {
        this.xPosition += vzdialenost;
    }

    /**
     * Posuň sa zvisle o dĺžku danú parametrom.
     */
    @SuppressWarnings("unused")
    public void posunZvisle(int vzdialenost) {
        this.yPosition += vzdialenost;
    }

    /**
     * Zmeň font textu podľa požiadaviek.
     */
    @SuppressWarnings("unused")
    public void zmenFont(String font, FontStyle styl, int velkost) {
        var intStyle = Font.PLAIN;

        if (styl == FontStyle.BOLD) {
            intStyle |= Font.BOLD;
        }

        if (styl == FontStyle.ITALIC) {
            intStyle |= Font.ITALIC;
        }

        var newFont = new Font(font, intStyle, velkost);

        if (styl == FontStyle.UNDERLINE) {
            var underlineAttribute = new HashMap<TextAttribute, Integer>();
            underlineAttribute.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            this.font = newFont.deriveFont(underlineAttribute);
        } else {
            this.font = newFont;
        }
    }

    /**
     * Zmeň font textu podľa požiadaviek.
     */
    @SuppressWarnings("unused")
    public void zmenFont(String font, EnumSet<FontStyle> styl, int velkost) {
        var intStyle = Font.PLAIN;

        if (styl.contains(FontStyle.BOLD)) {
            intStyle |= Font.BOLD;
        }

        if (styl.contains(FontStyle.ITALIC)) {
            intStyle |= Font.ITALIC;
        }

        var newFont = new Font(font, intStyle, velkost);

        if (styl.contains(FontStyle.UNDERLINE)) {
            var underlineAttribute = new HashMap<TextAttribute, Integer>();
            underlineAttribute.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            this.font = newFont.deriveFont(underlineAttribute);
        } else {
            this.font = newFont;
        }
    }

    /**
     * Zmeň farbu na hodnotu danú parametrom.
     */
    @SuppressWarnings("unused")
    public void zmenFarbu(String farba) {
        this.color = Game.getGame().getParser().parseColor(farba);
    }

    /**
     * Zmeň text.
     */
    @SuppressWarnings("unused")
    public void changeText(String text) {
        this.text = text;
    }

    private class TextDrawable extends GameDrawable {
        @Override
        public void draw(Graphics2D canvas) {
            canvas.setColor(Text.this.color);

            canvas.setFont(Text.this.font);
            canvas.drawString(Text.this.text, Text.this.xPosition,Text.this.yPosition);
        }
    }
}
