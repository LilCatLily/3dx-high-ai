package io.github.lilcatlily.bot.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GfxUtil
{
    public static void addCenterText(String text, Font font, int x, int y, Graphics g, Color color)
    {
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, Math.max(0, x - ((int) g.getFontMetrics().getStringBounds(text, g).getWidth() / 2)), y);
    }

    public static void addRightText(String text, Font font, int x, int y, Graphics g, Color color)
    {
        g.setFont(font);
        g.setColor(color);
        int realX = Math.max(0, x - ((int) g.getFontMetrics().getStringBounds(text, g).getWidth()));
        g.drawString(text, realX, y);
    }

    public static void addShadow(String text, Font font, int x, int y, Graphics g, Color color)
    {
        addText(text, font, x + 1, y + 1, g, color);
        addText(text, font, x + 1, y - 1, g, color);
        addText(text, font, x - 1, y + 1, g, color);
        addText(text, font, x - 1, y - 1, g, color);
    }

    public static void addCenterShadow(String text, Font font, int x, int y, Graphics g, Color color)
    {
        addCenterText(text, font, x + 1, y + 1, g, color);
        addCenterText(text, font, x + 1, y - 1, g, color);
        addCenterText(text, font, x - 1, y + 1, g, color);
        addCenterText(text, font, x - 1, y - 1, g, color);
    }

    public static void addText(String text, Font font, int x, int y, Graphics g)
    {
        addText(text, font, x, y, g, Color.BLACK);
    }
    
    public static void addText(String text, Font font, int x, int y, Graphics g, Color color)
    {
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public static void addTextWithMaxWidth(String text, Font font, int x, int y, Graphics g, int maxWidth)
    {
        addTextWithMaxWidth(text, font, x, y, g, Color.BLACK, maxWidth);
    }
    
    public static void addTextWithMaxWidth(String text, Font font, int x, int y, Graphics g, Color color, int maxWidth)
    {
        g.setColor(color);
        g.setFont(getAdjustedFont(font.getName(), font.getSize(), text, maxWidth));
        g.drawString(text, x, y);
    }

    private static Font getAdjustedFont(String fontName, int initialSize, String text, int width)
    {
        Font font = new Font(fontName, Font.PLAIN, initialSize);
        FontMetrics metrics;
        do {
            font = font.deriveFont(font.getSize() - 1f);
            metrics = getFontMetrics(font);
        } while (metrics.stringWidth(text) > width);

        return font;
    }
    
    public static FontMetrics getFontMetrics(Font font) {
        Graphics graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
        if (graphics instanceof Graphics2D) {
            ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        return graphics.getFontMetrics(font);
    }
    
    /**
     * @param  percentage
     *                        0.0 - 1.0
     * 
     * @return            color from green to red
     */
    public static Color getThreatLevel(double percentage)
    {
        return Color.getHSBColor((float) (1f - percentage) * .35f, 1, 1);
    }
}
