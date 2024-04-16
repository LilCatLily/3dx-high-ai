package io.github.lilcatlily.bot.roles;

import gdn.rom.jda.common.util.KeyValueSupplier;

import java.util.Arrays;

public enum StudentRole implements KeyValueSupplier, Role
{
    Tutor,
    Student_Teacher;

    @Override
    public String key()
    {
        return getText();
    }

    @Override
    public String value()
    {
        return getText();
    }
    
    public static StudentRole fromString(String value)
    {
        return Arrays.asList(StudentRole.values()).stream().filter(r -> r.getText().equals(value)).findFirst().get();
    }
}
