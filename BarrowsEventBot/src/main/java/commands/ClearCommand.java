package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.concurrent.TimeUnit;

public class ClearCommand extends Command {
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Are you sure you want to remove ALL the entries from the current competition?\n" +
                "This CANNOT be undone!")
                .addActionRow(
                        Button.primary("clearYes", "YES"),
                        Button.secondary("clearCancel", "CANCEL")
        ).setEphemeral(true).queue();

        event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
    }
}
