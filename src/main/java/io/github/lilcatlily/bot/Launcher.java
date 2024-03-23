package io.github.lilcatlily.bot;

import io.github.lilcatlily.bot.data.BotData;

public class Launcher
{
    private static BotContainer botContainer = null;
    
    public static void main(String[] args)
    {
        Resources.ensurePathsExist();
        BotData.createFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(Launcher::shutdownHook));
        botContainer = new BotContainer();
    }
    
    /**
     * shutdown hook, closing connections
     *
     */
    private static void shutdownHook() {
        if (botContainer != null) {
            botContainer.getJda().shutdown();
        }
    }
}
