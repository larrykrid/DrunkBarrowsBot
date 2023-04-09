package commands;

import data.Competition;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SummaryCommand extends Command {
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Competition.getInstance().printEventSummary(event);
        event.getHook().deleteOriginal().queue();
    }
}
