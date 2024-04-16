package io.github.lilcatlily.bot.roles;

import gdn.rom.jda.common.util.KeyValueSupplier;

import java.util.Arrays;

public enum StaffRole implements KeyValueSupplier, Role
{
    Teacher,
    Coach,
    Guidance_Counselor,
    Librarian,
    Therapist,
    Security,
    Nurse,
    Janitor,
    IT,
    Cafeterian,
    Accountant,
    Secretary,
    Lawyer;

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
    
    public static StaffRole fromString(String value)
    {
        return Arrays.asList(StaffRole.values()).stream().filter(r -> r.getText().equals(value)).findFirst().get();
    }
}
