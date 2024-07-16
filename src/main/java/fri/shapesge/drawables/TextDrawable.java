package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class TextDrawable extends FilledDrawable {
    private String[] text;
    private Font font;

    private int lineSpacing;
    private int maxWidth;

    public TextDrawable(int x, int y, Color color, String text, Font font) {
        super(x, y, color);

        this.text = text.split("\n");
        this.font = font;

        this.lineSpacing = 0;
        this.maxWidth = 0;
    }

    public void changeFont(String fontFamily, boolean bold, boolean italic, boolean underline, int size, int lineSpacing) {
        var intStyle = Font.PLAIN;

        if (bold) {
            intStyle |= Font.BOLD;
        }

        if (italic) {
            intStyle |= Font.ITALIC;
        }

        var newFont = new Font(fontFamily, intStyle, size);

        if (underline) {
            var underlineAttribute = new HashMap<TextAttribute, Integer>();
            underlineAttribute.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            this.font = newFont.deriveFont(underlineAttribute);
        } else {
            this.font = newFont;
        }

        this.lineSpacing = lineSpacing;

        Game.getGame().somethingHasChanged();
    }

    public void changeText(String text) {
        this.text = text.split("\n");

        Game.getGame().somethingHasChanged();
    }

    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(this.getColor());

        canvas.setFont(this.font);
        var metrics = canvas.getFontMetrics();
        var y = this.getYPosition() + metrics.getAscent();
        final var lineHeight = metrics.getHeight();

        for (String line : this.wrap(this.text)) {
            canvas.drawString(line, this.getXPosition(), y);
            y += lineHeight + this.lineSpacing;
        }
    }

    public int getLineSpacing() {
        return this.lineSpacing;
    }

    /**
     * @author trailblazercombi, with help from OpenAI ChatGPT
     */
    private FontMetrics generateFontMetrics() {
        Graphics2D g2d;
        g2d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        g2d.setFont(this.font);
        g2d.dispose();
        return g2d.getFontMetrics();
    }

    /**
     * @author trailblazercombi, with help from OpenAI ChatGPT
     */
    private int findBreakPoint(String line) {
        var breakPoint = -1;
        var lastSpaceIndex = -1;
        var metrics = this.generateFontMetrics();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                lastSpaceIndex = i;
            }
            if (metrics.stringWidth(line.substring(0, i + 1)) > this.maxWidth) {
                breakPoint = lastSpaceIndex;
                break;
            }
        }
        return breakPoint;
    }

    /**
     * @author trailblazercombi, with help from OpenAI ChatGPT
     */
    private String hyphenate(String word) {
        var metrics = this.generateFontMetrics();
        for (int i = 0; i < word.length(); i++) {
            if (metrics.stringWidth(word.substring(0, i + 1) + "-") > this.maxWidth) {
                return word.substring(0, i) + "-";
            }
        } return word;
    }

    /**
     * @author trailblazercombi, with help from OpenAI ChatGPT
     */
    private String[] wrap(String[] text) {
        if (this.maxWidth <= 0) {
            return text;
        }

        var wrapped = new ArrayList<String>();
        var metrics = this.generateFontMetrics();

        for (String line : text) {
            while (!line.isEmpty()) {
                int lineWidth = metrics.stringWidth(line);
                if (lineWidth <= this.maxWidth) {
                    wrapped.add(line);
                    break;
                } else {
                    var breakPoint = this.findBreakPoint(line);
                    if (breakPoint != -1) {
                        wrapped.add(line.substring(0, breakPoint));
                        line = line.substring(breakPoint).trim();
                    } else {
                        var halfWord = this.hyphenate(line);
                        wrapped.add(halfWord);
                        line = line.substring(halfWord.length() - 1).trim();
                    }
                }
            }
        }
        return wrapped.toArray(new String[0]);
    }

    public int getWidth() {
        var fontMetrics = this.generateFontMetrics();
        int maxWidth = 0;

        for (String line : this.wrap(this.text)) {
            maxWidth = Math.max(maxWidth, fontMetrics.stringWidth(line));
        }
        return maxWidth;
    }

    public int getHeight() {
        var fontMetrics = this.generateFontMetrics();
        return ((fontMetrics.getHeight() + this.lineSpacing) * this.text.length) - this.lineSpacing;
    }

    public void disableTextWrapping() {
        this.maxWidth = 0;
    }

    public void enableTextWrapping(int maxWidth) {
        this.maxWidth = maxWidth;
    }
}
