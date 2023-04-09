package data;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;

public class Competition {

    private static Competition instance;

    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<BarrowsItem> itemsOfTheDay = null;

    public static Competition getInstance(){
        if (instance == null)
            instance = new Competition();
        return instance;
    }

    public void addEntry(Entry entry){
        entries.add(entry);
    }

    public void setBrotherOfTheDay(String brother) {
        switch (brother){

            case "Ahrim" -> itemsOfTheDay = new ArrayList<BarrowsItem>() {{ add(BarrowsItem.AHRIMS_HOOD); add(BarrowsItem.AHRIMS_TOP); add(BarrowsItem.AHRIMS_BOTTOMS); add(BarrowsItem.AHRIMS_STAFF);}} ;
            case "Dharok" -> itemsOfTheDay = new ArrayList<BarrowsItem>() {{ add(BarrowsItem.DHAROKS_AXE); add(BarrowsItem.DHAROKS_HELM); add(BarrowsItem.DHAROKS_PLATEBODY); add(BarrowsItem.DHAROKS_PLATELEGS);}};
            case "Verac" -> itemsOfTheDay = new ArrayList<BarrowsItem>() {{add(BarrowsItem.VERACS_FLAIL); add(BarrowsItem.VERACS_HELM); add(BarrowsItem.VERACS_TOP); add(BarrowsItem.VERACS_SKIRT);}};
            case "Torag" -> itemsOfTheDay = new ArrayList<BarrowsItem>() {{add(BarrowsItem.TORAGS_HELM); add(BarrowsItem.TORAGS_HAMMERS); add(BarrowsItem.TORAGS_PLATEBODY); add(BarrowsItem.TORAGS_PLATELEGS);}};
            case "Karil" -> itemsOfTheDay = new ArrayList<BarrowsItem>() {{ add(BarrowsItem.KARILS_COIF); add(BarrowsItem.KARILS_TOP); add(BarrowsItem.KARILS_CROSSBOW); add(BarrowsItem.KARILS_SKIRT); }};
            case "Guthan" -> itemsOfTheDay = new ArrayList<BarrowsItem>() {{ add(BarrowsItem.GUTHANS_HELM); add(BarrowsItem.GUTHANS_SKIRT); add(BarrowsItem.GUTHANS_SPEAR); add(BarrowsItem.GUTHANS_PLATEBODY); }};
            case "None" -> itemsOfTheDay = null;
            default -> itemsOfTheDay = null;
        }
    }

    public Winners getWinners(){
        return calculateWinner();
    }

    public ArrayList<Entry> getEntries() { return entries; }

    private Winners calculateWinner() {
        Winners winners = new Winners();

        for (Entry e : entries ){
            winners.mostItems.merge(e.owner, 1, Integer::sum);

            if (itemsOfTheDay != null && itemsOfTheDay.contains(e.item)){
                winners.mostItemOfTheDAy.merge(e.owner, 1, Integer::sum);
            }
        }
        return winners;
    }

    public void removeEntry(int index) {
        if (index < entries.size())
            entries.remove(index);
    }

    public boolean hasItemOfTheDay(){return itemsOfTheDay != null;}

    public void resetCompetition(){
        entries = new ArrayList<>();
        itemsOfTheDay = null;
    }


}
