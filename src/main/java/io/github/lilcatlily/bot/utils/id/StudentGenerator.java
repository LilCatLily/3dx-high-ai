package io.github.lilcatlily.bot.utils.id;

import io.github.lilcatlily.bot.roles.*;
import io.github.lilcatlily.bot.utils.*;
import lombok.ToString;

import java.awt.*;
import java.awt.image.BufferedImage;

@ToString
public class StudentGenerator extends Generator
{
    private final String name;
    private final String role;
    private final String id;
    private final BufferedImage img;
    
    public StudentGenerator(String id, String name, StudentRole role, BufferedImage img)
    {
        super("student_bg.png");
        this.id = id;
        this.name = name;
        this.role = role.getText();
        this.img = img;
    }
    
    public StudentGenerator(String id, String name, BufferedImage img)
    {
        super("student_bg.png");
        this.id = id;
        this.name = name;
        this.role = "Student";
        this.img = img;
    }

    private final FontMetrics headerMetrics()
    {
        return getFontMetrics(headerFont);
    }
    
    private final FontMetrics inputMetrics()
    {
        return getFontMetrics(inputFont);
    }
    
    @Override
    void drawNameColumn()
    {
        var startingY = 500;
        var inputY = (startingY + inputMetrics().getAscent() - headerMetrics().getDescent());
        Font font = inputFont.deriveFont(inputFont.getSize() + 20f);
        drawCenteredString(name, font, startingY);
        this.drawPositionColumn(inputY - 20);
    }
    
    void drawPositionColumn(int startingY)
    {
        var inputY = (startingY + inputMetrics().getAscent() - headerMetrics().getDescent());
        Font font = inputFont.deriveFont(inputFont.getSize() + 10f);
        drawCenteredString(role, font, inputY);
        this.addIdNumber(inputY + 50);
    }
    
    void addIdNumber(int startingY)
    {
        drawCenteredString("Student ID:", headerMetrics().getFont(), startingY);
        var inputY = (startingY + inputMetrics().getAscent() - headerMetrics().getDescent());
        drawCenteredString(NumberSystem.instance().formatNumber(), digitFont, inputY);
        this.drawHireDateColumn(inputY + 50);
    }
    
    void drawHireDateColumn(int startingY)
    {
        drawCenteredString("Registered On:", headerMetrics().getFont(), startingY);
        var inputY = (startingY + inputMetrics().getAscent() - headerMetrics().getDescent());
        drawCenteredString(getDate(), inputFont, inputY);
    }
    
    // 179 - 416
    // 420 - 191
    void drawImage()
    {
        addImage(179, 191, 421, 417, img);
    }
    
    @Override
    public boolean create()
    {
        drawNameColumn();
        drawImage();
        return completeGeneration(id);
    }
}
