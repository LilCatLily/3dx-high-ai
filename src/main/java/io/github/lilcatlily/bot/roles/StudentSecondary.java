package io.github.lilcatlily.bot.roles;

import gdn.rom.jda.common.util.KeyValueSupplier;

public enum StudentSecondary implements KeyValueSupplier, Role
{
    Athlete,
    Tutor,
    Teachers_Assistant;

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
