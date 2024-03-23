package io.github.lilcatlily.bot;

import java.io.File;
import java.net.URL;
import java.nio.file.*;

public class Resources
{
    private static Path generatedPath = Paths.get("./generated/");
    private static Path dataPath      = Paths.get("./data/");

    public static void ensurePathsExist()
    {
        var generatedFile = generatedPath.toFile();
        if (!generatedFile.exists())
        {
            generatedFile.mkdir();
        }
        var dataFile = dataPath.toFile();
        if (!dataFile.exists())
        {
            dataFile.mkdir();
        }
    }

    public static File getDataDir()
    {
        return dataPath.toFile();
    }
    
    public static File getGeneratedDir()
    {
        return generatedPath.toFile();
    }

    public static URL get(String name)
    {
        return Launcher.class.getClassLoader().getResource(name);
    }
}
