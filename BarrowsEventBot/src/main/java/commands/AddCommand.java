package commands;

import data.BarrowsItem;
import data.Competition;
import data.Entry;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.concurrent.TimeUnit;

public class AddCommand extends Command {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        Entry entry = new Entry();
        entry.owner = event.getOption("name").getAsString();
        entry.item = getItemFromEntry(event);

        Competition.getInstance().addEntry(entry);

        String username = event.getGuild().getMemberById(entry.owner).getEffectiveName();

        event.getHook().sendMessage("%s 's entry for %s has been recorded.".formatted(username, entry.item.toString())).queue();
        event.getHook().deleteOriginal().queueAfter(25, TimeUnit.SECONDS);
    }

    private BarrowsItem getItemFromEntry(SlashCommandInteractionEvent event){
        return switch (event.getOption("item").getAsString()){
            case "Ahrim's hood" -> BarrowsItem.AHRIMS_HOOD;
            case "Ahrim's top" ->  BarrowsItem.AHRIMS_TOP;
            case "Ahrim's bottoms" -> BarrowsItem.AHRIMS_BOTTOMS;
            case "Ahrim's staff" ->  BarrowsItem.AHRIMS_STAFF;
            case "Dharok's helm" ->  BarrowsItem.DHAROKS_HELM;
            case "Dharok's platebody" ->  BarrowsItem.DHAROKS_PLATEBODY;
            case "Dharok's platelegs" ->  BarrowsItem.DHAROKS_PLATELEGS;
            case "Dharok's greataxe" ->  BarrowsItem.DHAROKS_AXE;
            case "Verac's helm" ->  BarrowsItem.VERACS_HELM;
            case "Verac's brassard" ->  BarrowsItem.VERACS_TOP;
            case "Verac's plateskirt" ->  BarrowsItem.VERACS_SKIRT;
            case "Verac's flail" ->  BarrowsItem.VERACS_FLAIL;
            case "Torag's helm" ->  BarrowsItem.TORAGS_HELM;
            case "Torag's platebody" ->  BarrowsItem.TORAGS_PLATEBODY;
            case "Torag's platelegs" ->  BarrowsItem.TORAGS_PLATELEGS;
            case "Torag's hammers" ->  BarrowsItem.TORAGS_HAMMERS;
            case "Karil's coif" ->  BarrowsItem.KARILS_COIF;
            case "Karil's top" ->  BarrowsItem.KARILS_TOP;
            case "Karil's skirt" ->  BarrowsItem.KARILS_SKIRT;
            case "Karil's crossbow" ->  BarrowsItem.KARILS_CROSSBOW;
            case "Guthan's helm" ->  BarrowsItem.GUTHANS_HELM;
            case "Guthan's platebody" ->  BarrowsItem.GUTHANS_PLATEBODY;
            case "Guthan's chainskirt" ->  BarrowsItem.GUTHANS_SKIRT;
            case "Guthan's spear" ->  BarrowsItem.GUTHANS_SPEAR;
            default ->  null;
        };
    }
}
