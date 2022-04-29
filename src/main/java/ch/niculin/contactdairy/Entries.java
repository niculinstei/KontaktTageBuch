package ch.niculin.contactdairy;

import java.util.List;

public class Entries {
    private final List<Entry> entries;
    private final Color color;

    public Entries(List<Entry> entries, Color color){
        this.entries = entries;
        this.color = color;
    }


    public List<Entry> getEntries() {
        return entries;
    }

    public Color getColor() {
        return color;
    }
}
