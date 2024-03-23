package io.github.lilcatlily.bot.roles;

import gdn.rom.jda.common.util.KeyValueSupplier;

public enum MainRole implements KeyValueSupplier, Role
{
    Student,
    Teacher,
    Gym_Coach,
    Guidance_Counselor,
    Librarian,
    Therapist,
    Security,
    Nurse,
    Janitor,
    IT,
    Cafeteria_Worker,
    Accountant,
    Secretary,
    Lawyer;

    @Override
    public String key()
    {
        return toString().replace("_", "-").toLowerCase();
    }

    @Override
    public String value()
    {
        return getText();
    }
}
