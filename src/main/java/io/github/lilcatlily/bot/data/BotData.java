package io.github.lilcatlily.bot.data;

import io.github.lilcatlily.bot.Resources;
import io.github.lilcatlily.bot.data.manager.JsonDataManager;
import io.github.lilcatlily.bot.data.models.*;

import java.io.File;

public class BotData
{
    private static JsonDataManager<Id> IdCount;
    private static JsonDataManager<CloudinaryData> cloudinary;
    private static JsonDataManager<Generated> generated;
    private static JsonDataManager<Options> options;
    
    public static JsonDataManager<Options> options() {
        if (options == null) {
            options = new JsonDataManager<>(Options.class, new File(Resources.getDataDir(), "options.json"), Options::new);
        }
        return options;
    }
    
    public static JsonDataManager<Generated> generated() {
        if (generated == null) {
            generated = new JsonDataManager<>(Generated.class, new File(Resources.getDataDir(), "generated.json"), Generated::new);
        }
        return generated;
    }
    
    public static JsonDataManager<CloudinaryData> cloudinary() {
        if (cloudinary == null) {
            cloudinary = new JsonDataManager<>(CloudinaryData.class, new File(Resources.getDataDir(), "cloudinary.json"), CloudinaryData::new);
        }
        return cloudinary;
    }
    
    public static JsonDataManager<Id> IdCount() {
        if (IdCount == null) {
            IdCount = new JsonDataManager<>(Id.class, new File(Resources.getDataDir(), "id.json"), Id::new);
        }
        return IdCount;
    }
    
    public static void createFiles()
    {
        IdCount();
        cloudinary();
        generated();
        options();
    }
}
