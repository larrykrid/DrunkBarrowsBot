package util;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.BarrowsBotConstants;
import data.Entry;

public class JsonWriter {
    private static JsonWriter writer;
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    LocalDate dateNow = LocalDate.now();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Type entryType = new TypeToken<List<Entry>>(){}.getType();

    String currentDate = df.format(dateNow);
    String filePath = BarrowsBotConstants.FILE_PATH + "/" + currentDate + ".txt";
    int index = 0;

    JsonWriter(){
        int existingFiles = 0;
        try {
            Path newFilePath = Paths.get(filePath);
            //Case exists for if there is a crash, we need to know where it lost progress
            while(Files.exists(newFilePath)){
                existingFiles++;
                filePath = BarrowsBotConstants.FILE_PATH + "/" + currentDate + "_" + existingFiles + ".txt";
                newFilePath = Paths.get(filePath);
            }
            Files.createFile(newFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JsonWriter getWriter(){
        if (writer == null) {
            writer = new JsonWriter();
        }
        return writer;
    }

    //Currently using basic Entry, we could create a new class model that would include entry variables along with
        // - time of addition
        // - index
        // - action (add, clear, slash command if we want to get more detailed)
        // - who completed the action
    public void addJsonEntry(Entry entry) {
        try {
            FileReader fr = new FileReader(filePath);
            List<Entry> entJson = gson.fromJson(fr, entryType);

            if(entJson == null){
                System.out.println("empty list");
                entJson = new ArrayList<>();
            }

            entJson.add(entry);
            FileWriter fw = new FileWriter(filePath);
            gson.toJson(entJson, fw);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String retrieveTime() {
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm:ss");
        LocalTime timeNow = LocalTime.now();
        return tf.format(timeNow);
    }
}
