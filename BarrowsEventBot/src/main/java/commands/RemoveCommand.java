package commands;

import data.Competition;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class RemoveCommand extends Command{
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Competition.getInstance().removeEntry(event.getOption("index").getAsInt());
        event.getHook().deleteOriginal().queue();
    }
}
