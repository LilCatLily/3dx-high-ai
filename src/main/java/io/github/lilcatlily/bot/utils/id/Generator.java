package io.github.lilcatlily.bot.utils.id;

import io.github.lilcatlily.bot.Resources;
import lombok.*;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.util.Date;

public abstract class Generator
{
    protected Font headerFont = new Font("Helvetica", Font.PLAIN, 20);
    protected Font inputFont = new Font("Noto Sans Display Condensed Black", Font.TRUETYPE_FONT, 44);
    protected Font digitFont = new Font("Noto Sans Disp Cond Blk", Font.PLAIN, 39);
    
    @Getter
    @Setter
    private File imageFile;
    
    private final BufferedImage background;
    @Getter(AccessLevel.PROTECTED)
    private final Graphics2D grapchics;
    
    Generator(String backgroundFileName)
    {
        this.background = getBackground(backgroundFileName);
        this.grapchics = (Graphics2D) this.background.getGraphics();
        this.drawInitialData();
    }
    
    private void drawInitialData()
    {
        grapchics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        grapchics.drawImage(background, 0, 0, null);
    }
    
    private BufferedImage getBackground(String filename)
    {
        try
        {
            return ImageIO.read(Resources.get(filename));
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    protected FontMetrics getFontMetrics(Font font) {
        Graphics graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
        if (graphics instanceof Graphics2D) {
            ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        return graphics.getFontMetrics(font);
    }
    
    protected void addImage(int x1, int y1, int x2, int y2, BufferedImage img)
    {
        grapchics.drawImage(img, x1, y1, x2, y2, 0, 0, img.getWidth(), img.getHeight(), null);
    }
    
    protected void addText(String text, Font font, int x, int y)
    {
        addText(text, font, x, y, Color.BLACK);
    }
    
    protected void addText(String text, Font font, int x, int y, Color color)
    {
        grapchics.setFont(font);
        grapchics.setColor(color);
        grapchics.drawString(text, x, y);
    }
    
    protected void addTextWithMaxWidth(String text, Font font, int x, int y, int maxWidth)
    {
        addTextWithMaxWidth(text, font, x, y, Color.BLACK, maxWidth);
    }
    
    protected void addTextWithMaxWidth(String text, Font font, int x, int y, Color color, int maxWidth)
    {
        grapchics.setColor(color);
        grapchics.setFont(getAdjustedFont(font.getName(), font.getSize(), text, maxWidth));
        grapchics.drawString(text, x, y);
    }

    protected Font getAdjustedFont(String fontName, int initialSize, String text, int width)
    {
        Font font = new Font(fontName, Font.TRUETYPE_FONT, initialSize);
        FontMetrics metrics;
        do {
            font = font.deriveFont(font.getSize() - 1f);
            metrics = getFontMetrics(font);
        } while (metrics.stringWidth(text) > width);

        return font;
    }
    
    // https://stackoverflow.com/a/27740330
    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    protected void drawCenteredString(String text, Font font, int y) {
        Font f = getAdjustedFont(font.getName(), font.getSize(), text, background.getWidth() - 20);
        grapchics.setFont(f);
        FontMetrics metrics = grapchics.getFontMetrics(f);
        Rectangle rect = new Rectangle(new Dimension(background.getWidth(), background.getHeight()));
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        grapchics.setColor(Color.BLACK);
        grapchics.drawString(text, x, y);
    }
    
    protected String getDate()
    {
        return DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
    }
    
    abstract void drawNameColumn();
    
    public abstract boolean create();

    protected boolean completeGeneration(String filename)
    {
        setImageFile(new File(Resources.getGeneratedDir(), "%s.png".formatted(filename)));
        try
        {
            return ImageIO.write(background, "png", getImageFile());
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
