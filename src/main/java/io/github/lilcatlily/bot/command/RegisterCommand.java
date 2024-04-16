package io.github.lilcatlily.bot.command;

import gdn.rom.jda.command.SlashCommand;
import gdn.rom.jda.command.event.SlashCommandEvent;
import gdn.rom.jda.command.lists.ChoiceList;
import gdn.rom.jda.command.option.*;
import io.github.lilcatlily.bot.Resources;
import io.github.lilcatlily.bot.data.BotData;
import io.github.lilcatlily.bot.data.models.Generated;
import io.github.lilcatlily.bot.roles.*;
import io.github.lilcatlily.bot.utils.NumberSystem;
import io.github.lilcatlily.bot.utils.id.*;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;
import java.time.Instant;

public class RegisterCommand extends SlashCommand
{
    private final String alreadyRegistered = "\n```\nYou are already registered, if your wanting to update your ID, please use the /id update <?> command\n```";

    public RegisterCommand()
    {
        super("register");
        setSubCommands(new RegisterCommand.Student(), new RegisterCommand.Staff());
    }

    public class Staff extends SlashCommand
    {
        private final OptionData roles = RequiredOption.text("role", "Your staff position / job", ChoiceList.toList(StaffRole.class));

        protected Staff()
        {
            super("staff", "Register as a staff member");
            options(RequiredOption.text("username", "Your 3DXChat username", 20), roles, new OptionData(OptionType.ATTACHMENT, "image", "Image of your 3DXChat avatar", false));
        }

        @Override
        protected void execute(SlashCommandEvent event)
        {
            event.deferReply().queue();
            var hook = event.getInteraction().getHook();
            if (BotData.generated().get().isRegistered(event.getAuthor()))
            {
                hook.sendMessage(event.getUser().getAsMention() + alreadyRegistered).setEphemeral(true).queue();
                return;
            }
            var username = event.getOption("username").getAsString();
            var role = StaffRole.fromString(event.getOption("role").getAsString());
            var img = getAttachmentOrDefaultFile(event);
            var generator = new StaffGenerator(event.getAuthor().getId(), username, role, img);
            if (generator.create())
            {
                var data = BotData.generated();
                var info = new Generated.UserData(Instant.now().getEpochSecond(), username, true);
                data.get().addUserData(event.getAuthor().getId(), info);
                data.save();
                NumberSystem.instance().incrementAndSave();
                var msgData = new MessageCreateBuilder().addContent("Here is your new Student ID").addFiles(FileUpload.fromData(generator.getImageFile())).build();
                hook.sendMessage(msgData).queue();
            } else
            {
                hook.sendMessage("Opps! looks like i hit a wall going 85....uh help?").queue();
            }
        }
    }

    public class Student extends SlashCommand
    {
        private final OptionData roles = Option.text("role", "Optional Student role", ChoiceList.toList(StudentRole.class));

        protected Student()
        {
            super("student", "Register as a Student");
            options(RequiredOption.text("username", "Your 3DXChat username", 20), new OptionData(OptionType.ATTACHMENT, "image", "Image of your 3DXChat avatar", false), roles);
        }

        @Override
        protected void execute(SlashCommandEvent event)
        {
            event.deferReply().queue();
            var hook = event.getInteraction().getHook();
            if (BotData.generated().get().isRegistered(event.getAuthor()))
            {
                hook.sendMessage(event.getUser().getAsMention() + alreadyRegistered).setEphemeral(true).queue();
                return;
            }
            var username = event.getOption("username").getAsString();
            var img = getAttachmentOrDefaultFile(event);
            Generator generator;
            if(event.hasOption("role"))
                generator = new StudentGenerator(event.getAuthor().getId(), username, StudentRole.fromString(event.getOption("role").getAsString()), img);
            else
                generator = new StudentGenerator(event.getAuthor().getId(), username, img);
            if (generator.create())
            {
                var data = BotData.generated();
                var info = new Generated.UserData(Instant.now().getEpochSecond(), username, false);
                data.get().addUserData(event.getAuthor().getId(), info);
                data.save();
                NumberSystem.instance().incrementAndSave();
                var msgData = new MessageCreateBuilder().addContent("Here is your new Student ID").addFiles(FileUpload.fromData(generator.getImageFile())).build();
                hook.sendMessage(msgData).queue();
            } else
            {
                hook.sendMessage("Opps! looks like i hit a wall going 85....uh help?").queue();
            }
        }
    }
    
    private BufferedImage getAttachmentOrDefaultFile(SlashCommandEvent event)
    {
        if(event.hasOption("image"))
        {
            try
            {
                URLConnection connection = new URI(event.getOption("image").getAsAttachment().getProxyUrl()).toURL().openConnection();
                connection.setRequestProperty("User-Agent", "3dx-high-bot");
                return ImageIO.read(connection.getInputStream());
            } catch (IOException | URISyntaxException e)
            {
                e.printStackTrace();
                return null;
            }
        } else {
            try
            {
                return getUserAvatar(event.getUser());
            } catch (IOException | URISyntaxException e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
    
    private BufferedImage getUserAvatar(User user) throws MalformedURLException, IOException, URISyntaxException
    {
        URLConnection connection = new URI(user.getAvatarUrl() != null ? user.getAvatarUrl() : user.getDefaultAvatarUrl()).toURL().openConnection();
        connection.setRequestProperty("User-Agent", "3dx-high-bot");
        BufferedImage profileImg;
        try {
            profileImg = ImageIO.read(connection.getInputStream());
        } catch (Exception ignored) {
            profileImg = ImageIO.read(Resources.get("default_profile.jpg"));
        }
        return profileImg;
    }

    @Override
    protected void execute(SlashCommandEvent event)
    {
    }
}
