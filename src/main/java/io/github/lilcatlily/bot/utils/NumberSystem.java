package io.github.lilcatlily.bot.utils;

import io.github.lilcatlily.bot.data.BotData;

public class NumberSystem
{
    private volatile static NumberSystem instance;

    public static NumberSystem instance()
    {
        if (instance == null)
        {
            synchronized (NumberSystem.class)
            {
                if (instance == null)
                {
                    instance = new NumberSystem();
                }
            }
        }
        return instance;
    }

    public int getCount()
    {
        return BotData.IdCount().get().getCount();
    }

    public String formatNumber()
    {
        return String.format("%04d", getCount());
    }
    
    public void incrementAndSave()
    {
        int newCount = getCount() + 1;
        BotData.IdCount().get().setCount(newCount);
        BotData.IdCount().save();
    }
}
