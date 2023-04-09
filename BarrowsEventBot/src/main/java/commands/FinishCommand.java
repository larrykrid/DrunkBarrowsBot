package commands;

import data.Competition;
import data.Winners;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FinishCommand extends Command{

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        Winners winners = Competition.getInstance().getWinners();

        Map<String, Integer> topFive = winners.mostItems.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        String mostItemsList = "";

        for (String key : topFive.keySet()){
            mostItemsList += (event.getGuild().getMemberById(key).getAsMention() + " - " + topFive.get(key) + "\n");
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(0x0000ff);
        builder.setThumbnail("https://cdn.discordapp.com/attachments/585386371968139276/1090335893908377690/drinks.png");
        builder.setTitle("Drunk Barrows Scoreboard");
        builder.addField("__Most items received:__", mostItemsList, true);

        if (Competition.getInstance().hasItemOfTheDay()){
            Map<String, Integer> topFiveIOTD = winners.mostItemOfTheDAy.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(5)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            String IOTDscoreboard = "";

            for (String key : topFiveIOTD.keySet()){
                IOTDscoreboard += (event.getGuild().getMemberById(key).getAsMention() + " - " + topFiveIOTD.get(key) + "\n");
            }
            builder.addField("__Most Brother of the Day items received:__", IOTDscoreboard, true);
        }
        builder.setFooter("Thank you for participating! See you double next time!");

        Competition.getInstance().printEventSummary(event);
//        Competition.getInstance().resetCompetition();

        event.getHook().sendMessageEmbeds(builder.build()).queue();
    }
}
