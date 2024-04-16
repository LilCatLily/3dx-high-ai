package io.github.lilcatlily.bot.utils.id;

import io.github.lilcatlily.bot.roles.*;
import io.github.lilcatlily.bot.utils.NumberSystem;
import lombok.ToString;

import java.awt.*;
import java.awt.image.BufferedImage;

@ToString
public class StaffGenerator extends Generator
{
    private final String name;
    private final Role role;
    private final String id;
    private final BufferedImage img;
    
    public StaffGenerator(String id, String name, StaffRole role, BufferedImage img)
    {
        super("background2.png");
        this.id = id;
        this.name = name;
        this.role = role;
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
        var startingY = 200;
        addText("NAME:", headerMetrics().getFont(), 370, startingY);
        var inputY = (startingY + inputMetrics().getAscent() - headerMetrics().getDescent());
        addText(name, inputFont, 370, inputY);
        this.drawPositionColumn(inputY + 40);
    }
    
    void drawPositionColumn(int startingY)
    {
        addText("POSITION:", headerMetrics().getFont(), 370, startingY);
        var inputY = (startingY + inputMetrics().getAscent() - headerMetrics().getDescent());
        addText(role.getText(), inputFont, 370, inputY);
        this.drawHireDateColumn(inputY + 40);
    }
    
    void drawHireDateColumn(int startingY)
    {
        addText("HIRE DATE:", headerMetrics().getFont(), 370, startingY);
        var inputY = (startingY + inputMetrics().getAscent() - headerMetrics().getDescent());
        addText(getDate(), inputFont, 370, inputY);
    }
    
    void drawRoleColumn()
    {
        var x = 107;
        var y = 450;
        var font = headerFont.deriveFont(Font.BOLD, 28f);
        addText("Staff / Faculty", font, x, y);
    }
    
    void addIdNumber()
    {
        addText(NumberSystem.instance().formatNumber(), digitFont, 788, 100, Color.WHITE);
    }
    
    @Override
    public boolean create()
    {
        drawNameColumn();
        drawRoleColumn();
        addIdNumber();
        return completeGeneration(id);
    }
}
