package commands;

import data.Competition;
import data.Entry;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class SummaryCommand extends Command {
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        ArrayList<Entry> entries = Competition.getInstance().getEntries();

        try {
            Path tempFile = Files.createTempFile("summary", ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()));

            int count = 0;
            for (Entry e : entries) {
                writer.append("Index: %s - Name: %s - Item: %s\n".formatted(count, event.getGuild().getMemberById(e.owner).getEffectiveName(), e.item.toString()));
                count++;
            }

            System.out.println(tempFile);

            writer.close();

            event.getHook().sendMessage("Here's the current summary:")
                    .addFiles(FileUpload.fromData(tempFile)).queue();
            
            deleteFile(tempFile);
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public void deleteFile(Path tempFile) throws IOException {
        if (Files.deleteIfExists(tempFile)){
            System.out.println("file was deleted");
        } else {
            System.out.println("file was not deleted");
        }
    }
}
