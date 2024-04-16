package io.github.lilcatlily.bot;

import io.github.lilcatlily.bot.utils.*;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

// UNUSED - KEPT FOR REFERENCE
public class IDGenerator
{
    private static Font TEXT_FONT  = new Font("Noto Sans Disp", Font.PLAIN, 66);
    private static final Font DIGIT_FONT = new Font("Noto Sans Disp Cond Blk", Font.PLAIN, 39);

    public static File generateNewId(String username, String role, String id)
    {
        var result = getIdBackground();
        var graphic = (Graphics2D) result.getGraphics();
        graphic.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphic.drawImage(result, 0, 0, null);
        drawNameColumn(username, graphic);
        //addRole(role, graphic);
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
    
    // 100 - 450
    
    private static void drawNameColumn(String name, Graphics2D graphic)
    {
//        var startingY = 230;
//        var font = new Font("Helvetica", Font.PLAIN, 18);
//        var metrix = GfxUtil.getFontMetrics(font);
//        Rectangle2D h = metrix.getStringBounds("NAME:", graphic);
//        GfxUtil.addText("NAME:", font, 370, startingY, graphic);
//        GfxUtil.addText(name, f, 370, (int) (startingY + h.getHeight() + 20), graphic);
    }

    private static void addRoleWithSecondary(String mainRole, String subRole, Graphics2D graphic)
    {
        
    }
    
    private static void addRole(String role, Graphics2D graphic)
    {
        GfxUtil.addText(role, TEXT_FONT, 370, 335, graphic, Color.black);
    }

    private static void addSecondaryRole(String role, Graphics2D graphic)
    {
        GfxUtil.addText(role, TEXT_FONT, 370, 335, graphic, Color.black);
    }
    
    private static void addUsername(String username, Graphics2D graphic)
    {
        GfxUtil.addTextWithMaxWidth(username, TEXT_FONT, 370, 260, graphic, Color.black, 522);
    }

    private static void addIdNumber(String number, Graphics2D graphic)
    {
        GfxUtil.addText(number, DIGIT_FONT, 788, 100, graphic, Color.WHITE);
    }
}
