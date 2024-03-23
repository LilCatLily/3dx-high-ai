package io.github.lilcatlily.bot.utils;

import io.github.lilcatlily.bot.data.BotData;
import io.github.lilcatlily.bot.data.models.CloudinaryData;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.*;

public class CloudinaryAPI
{
    static CloudinaryData data       = BotData.cloudinary().get();
    static Cloudinary     cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", data.getName(), "api_key", data.getKey(), "api_secret", data.getSecret()));

    public void upload(File file)
    {
        try
        {
            cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", file.getName()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
