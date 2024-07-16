package fri.shapesge.drawables;

import fri.shapesge.engine.Game;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
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

        for (String line : this.text) {
            canvas.drawString(line, this.getXPosition(), y);
            y += lineHeight + this.lineSpacing;
        }
    }
    
//        // In my humble opinion, this code looks too wordy and might be slow.
//        // Somebody more skilled than me, could you please take a look and fix it? -- trailblazercombi
//        if (this.maxWidth <= 0) {
//            // If the maximum width is not set, TextDrawable works as normal.
//            for (String line : this.text) {
//                // Draw the line.
//                canvas.drawString(line, this.getXPosition(), y);
//                y += lineHeight + this.lineSpacing;
//            }
//        } else {
//            // If the maximum width is set, TextDrawable splits all lines into shorter ones.
//            for (String line : this.text) {
//                String[] words = line.split(" ");
//                // Create a new line builder.
//                var newLine = new StringBuilder();
//                var lenLine = 0;
//                // Parse all words. If the entire line fits, it SHOULD yield the same result as if the limit did not exist.
//                for (String word : words) {
//                    int newLenLine = lenLine + metrics.stringWidth(word);
//                    if (newLenLine > this.maxWidth) {
//                        // Once the limit is reached...
//                        // Check if the word we're processing will fit into a line on its own.
//                        if (metrics.stringWidth(word) > this.maxWidth) {
//                            // If it doesn't, split it further.
//                            char[] chars = word.toCharArray();
//                            // And assemble characters up to the limit.
//                            var wordMaker = new StringBuilder();
//                            int lenWord = metrics.charWidth('-');
//                            for (char aChar : chars) {
//                                if (lenWord + metrics.charWidth(aChar) > this.maxWidth) {
//                                    // If the limit is reached, hyphenate the word, draw it and reset the builder.
//                                    wordMaker.append('-');
//                                    canvas.drawString(wordMaker.toString(), this.getXPosition(), y);
//                                    y += lineHeight + this.lineSpacing;
//                                    wordMaker = new StringBuilder();
//                                    lenWord = metrics.charWidth('-');
//                                } else {
//                                    wordMaker.append(aChar);
//                                    lenWord += metrics.charWidth(aChar);
//                                }
//                            }
//                        } else {
//                            // Draw the line and reset the line builder.
//                            canvas.drawString(newLine.toString(), this.getXPosition(), y);
//                            y += lineHeight + this.lineSpacing;
//                            newLine = new StringBuilder();
//                            lenLine = 0;
//                        }
//                    } else {
//                        newLine.append(word).append(" ");
//                        lenLine = newLenLine;
//                    }
//                }
//                // Once we're done processing all words in the line, draw the leftovers.
//                canvas.drawString(newLine.toString(), this.getXPosition(), y);
//                y += lineHeight + this.lineSpacing;
//            }
//            // The wordy code ends here. -- trailblazercombi

    public int getLineSpacing() {
        return this.lineSpacing;
    }

    private FontMetrics generateFontMetrics() {
        Graphics2D g2d;
        g2d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        g2d.setFont(this.font);

        return g2d.getFontMetrics();
    }

    public int getWidth() {
        var fontMetrics = this.generateFontMetrics();
        int maxWidth = 0;
        for (String line : this.text) {
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
