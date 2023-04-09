import commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class mainClass extends ListenerAdapter {

    public static void main(String[] args){
        String token = "";

        if (args[0] != null){
            token = args[0];
        }

        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new mainClass())
                .setActivity(Activity.listening("/commands"))
                .build();


        OptionData barrowsItems = new OptionData(OptionType.STRING, "item", "The barrows item the person received.", true)
                .addChoice("Ahrim's hood", "Ahrim's hood").addChoice("Ahrim's top", "Ahrim's top").addChoice("Ahrim's bottoms", "Ahrim's bottoms"). addChoice("Ahrim's staff", "Ahrim's staff")
                .addChoice("Dharok's helm", "Dharok's helm"). addChoice("Dharok's platebody", "Dharok's platebody").addChoice("Dharok's platelegs", "Dharok's platelegs").addChoice("Dharok's greataxe", "Dharok's greataxe")
                .addChoice("Verac's helm", "Verac's helm").addChoice("Verac's brassard", "Verac's brassard").addChoice("Verac's plateskirt", "Verac's plateskirt").addChoice("Verac's flail", "Verac's flail")
                .addChoice("Torag's helm",  "Torag's helm").addChoice("Torag's platebody", "Torag's platebody").addChoice("Torag's platelegs", "Torag's platelegs").addChoice("Torag's hammers", "Torag's hammers")
                .addChoice("Karil's coif", "Karil's coif").addChoice("Karil's top", "Karil's top").addChoice("Karil's skirt", "Karil's skirt").addChoice("Karil's crossbow", "Karil's crossbow")
                .addChoice("Guthan's helm", "Guthan's helm").addChoice("Guthan's platebody", "Guthan's platebody").addChoice("Guthan's chainskirt", "Guthan's chainskirt").addChoice("Guthan's spear", "Guthan's spear");

        OptionData brothers = new OptionData(OptionType.STRING, "brother", "The Barrows Brother of which the items must be received.", true)
                .addChoice("Ahrim", "Ahrim") . addChoice("Dharok", "Dharok").addChoice("Verac", "Verac")
                .addChoice("Torag", "Torag").addChoice("Karil", "Karil").addChoice("Guthan", "Guthan")
                .addChoice("None", "None");

        jda.updateCommands().addCommands(
                Commands.slash("add", "Adds a new entry into this Barrows competition.")
                        .addOption(OptionType.USER, "name", "The person that got the item. Use the @ discord tag.", true)
                        .addOptions(barrowsItems),
                Commands.slash("brotheroftheday", "Set the brother of the day. Select \"None\" to clear.")
                        .addOptions(brothers),
                Commands.slash("leaderboard", "Shows the top 10 of the current running event."),
                Commands.slash("summary", "Print the current state of the event without finishing it."),
                Commands.slash("remove", "Removes 1 entry from the competition. Only use in case of accidental duplication.")
                        .addOption(OptionType.INTEGER, "index", "The number in front of the entry in the summary. Must be a number.", true)
        ).queue();

    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
            Command c = null;

            switch (event.getName()){
                case "add" -> c = new AddCommand();
                case "brotheroftheday" -> c = new BrotherOfTheDayCommand();
                case "leaderboard" -> c = new LeaderboardCommand();
                case "summary" -> c = new SummaryCommand();
                case "remove" -> c = new RemoveCommand();
                default -> event.reply("Something went wrong. Please try again.").queue();
            }

            if (c != null)
                c.execute(event);
    }
}
