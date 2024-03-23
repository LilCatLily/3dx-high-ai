package io.github.lilcatlily.bot.command;

import gdn.rom.jda.command.SlashCommand;
import gdn.rom.jda.command.event.SlashCommandEvent;
import gdn.rom.jda.command.lists.ChoiceList;
import gdn.rom.jda.command.option.RequiredOption;
import io.github.lilcatlily.bot.IDGenerator;
import io.github.lilcatlily.bot.roles.MainRole;
import io.github.lilcatlily.bot.utils.NumberSystem;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class RequestNewIdCommand extends SlashCommand {

    private final OptionData nameOption = RequiredOption.text("username", "Your 3DXChat username"); 
    private final OptionData avatarImg = new OptionData(OptionType.ATTACHMENT, "image", "Image of your 3DXChat avatar", true);
    private final OptionData mainRole = RequiredOption.text("role", "The role you wish to have", ChoiceList.toList(MainRole.class));
    
	public RequestNewIdCommand() {
		super("request-id", "Request a new school ID");
		options(nameOption, avatarImg, mainRole);
	}

	@Override
	protected void execute(SlashCommandEvent event) {
	    event.getInteraction().deferReply().queue();
	    var hook = event.getInteraction().getHook();
	    var username = event.getOption("username").getAsString();
	    var role = event.getOption("role").getAsString();

	    var newId = IDGenerator.generateNewId(username, role, event.getUser().getId());
	    if(newId != null)
	    {
	        NumberSystem.instance().incrementAndSave();
	        var data = new MessageCreateBuilder().addContent("Here is your new Student ID").addFiles(FileUpload.fromData(newId)).build();
	        hook.sendMessage(data).queue();
	    } else {
	        hook.sendMessage("Opps! looks like i hit a wall going 85....uh help?").queue();
        }
	}
}
