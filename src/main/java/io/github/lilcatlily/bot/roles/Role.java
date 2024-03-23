package io.github.lilcatlily.bot.roles;

public interface Role
{
    default String getText()
    {
        return toString().replace("_", " ");
    }
}
