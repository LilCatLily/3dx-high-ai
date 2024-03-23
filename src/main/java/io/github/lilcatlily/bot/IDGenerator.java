package io.github.lilcatlily.bot;

import io.github.lilcatlily.bot.utils.*;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class IDGenerator
{
    private static Font TEXT_FONT  = new Font("Noto Sans Disp Cond Blk", Font.PLAIN, 66);
    private static final Font DIGIT_FONT = new Font("Noto Sans Disp Cond Blk", Font.PLAIN, 39);

    public static File generateNewId(String username, String role, String id)
    {
        var result = new BufferedImage(900, 600, BufferedImage.TYPE_INT_ARGB);
        var graphic = (Graphics2D) result.getGraphics();
        graphic.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphic.drawImage(getIdBackground(), 0, 0, 900, 600, 0, 0, 900, 600, null);
        addUsername(username, graphic);
        addRole(role, graphic);
        addIdNumber(NumberSystem.instance().formatNumber(), graphic);
        File file = new File(Resources.getGeneratedDir(), id + ".png");
        try
        {
            ImageIO.write(result, "png", file);
            return file;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedImage getIdBackground()
    {
        try
        {
            return ImageIO.read(Resources.get("background.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static void addRole(String role, Graphics2D graphic)
    {
        GfxUtil.addText(role, TEXT_FONT, 370, 355, graphic, Color.black);
    }

    private static void addUsername(String username, Graphics2D graphic)
    {
        GfxUtil.addTextWithMaxWidth(username, TEXT_FONT, 370, 280, graphic, Color.black, 522);
    }

    private static void addIdNumber(String number, Graphics2D graphic)
    {
        GfxUtil.addText(number, DIGIT_FONT, 788, 100, graphic, Color.black);
    }
}
