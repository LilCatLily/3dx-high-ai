package io.github.lilcatlily.bot.command;

import gdn.rom.jda.command.SlashCommand;
import gdn.rom.jda.command.event.SlashCommandEvent;

public abstract class ParentCommand extends SlashCommand
{
    public ParentCommand(String name)
    {
        super(name);
    }
    
    public ParentCommand(String name, String description)
    {
        super(name, description);
    }

    @Override
    protected void execute(SlashCommandEvent event)
    {
        //NO-OP
    }
}
