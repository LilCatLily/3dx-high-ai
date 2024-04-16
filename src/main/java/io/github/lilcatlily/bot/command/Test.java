package io.github.lilcatlily.bot.command;

import io.github.lilcatlily.bot.Resources;
import io.github.lilcatlily.bot.utils.id.StudentGenerator;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.IOException;

public class Test
{
    public static void main(String[] args) throws IOException {
        //new StaffGenerator("1208813569601183766", "SomeRandomNameThatsT", StaffRole.Counselor).create();
        
        new StudentGenerator("2208813569601183766", "LittleLilyCat", ImageIO.read(Resources.get("default_profile.jpg"))).create();
        //System.out.println(DateFormat.getDateInstance(DateFormat.LONG).format(new Date()));
        //IDGenerator.generateNewId("Lily_Cat", "Student", "1208813569601183766");
    }

    private static FontMetrics getFontMetrics(Font font) {
        Graphics graphics = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB).getGraphics();
        if (graphics instanceof Graphics2D) {
            ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        return graphics.getFontMetrics(font);
    }
}
