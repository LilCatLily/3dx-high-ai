package io.github.lilcatlily.bot;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.*;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.SCHEDULED_EVENTS;

import gdn.rom.jda.command.*;
import gdn.rom.jda.common.*;
import gdn.rom.jda.discordbot.DiscordBot;
import io.github.lilcatlily.bot.command.RequestNewIdCommand;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;

public class BotContainer extends DiscordBot
{
    @Getter
    private JDA jda;
    
    public BotContainer()
    {
        this.jda = buildJDA();
    }

    @Override
    protected void addToBuilder(ClientBuilder builder)
    {
    }

    @Override
    protected String getOwnerId()
    {
        return "1208813569601183766";
    }

    @Override
    protected void addSlashCommands(ToolSet<SlashCommand> set)
    {
        set.add(new RequestNewIdCommand());
    }

    @Override
    protected void addContextMenus(ToolSet<ContextMenu<?>> set)
    {
    }

    @Override
    protected void addServerCommands(ToolSet<ServerCommands> set)
    {
    }

    @Override
    protected Activity getActivity()
    {
        return null;
    }

    @Override
    protected String getToken()
    {
        return "<BOT TOKEN>";
    }

    @Override
    protected void addEventListeners(ToolSet<Object> set)
    {
        set.add(new ContainerEventListener());
    }

    @Override
    protected EnumSet<GatewayIntent> getGatewayIntents()
    {
        return EnumSet.of(GUILD_MEMBERS, GUILD_MESSAGES, MESSAGE_CONTENT, DIRECT_MESSAGES);
    }

    @Override
    protected EnumSet<CacheFlag> getDisabledCacheFlags()
    {
        return EnumSet.of(EMOJI, STICKER, CLIENT_STATUS, VOICE_STATE, SCHEDULED_EVENTS, ACTIVITY, ONLINE_STATUS);
    }
    
    static class ContainerEventListener extends ListenerAdapter {

        @Override
        public void onReady(ReadyEvent event)
        {
            event.getJDA().getGuilds().forEach(g -> g.updateCommands().queue());
        }
    }

}
