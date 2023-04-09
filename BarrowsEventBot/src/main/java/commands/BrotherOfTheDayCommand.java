package commands;

import data.Competition;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class BrotherOfTheDayCommand extends Command  {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Competition.getInstance().setBrotherOfTheDay(event.getOption("brother").getAsString());
        event.reply("Brother of the Day has been set to: "+ event.getOption("brother").getAsString()).queue();
    }
}
