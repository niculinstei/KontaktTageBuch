package ch.niculin.contactdairy;

import ch.niculin.contactdairy.persitence.Persitence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Logic {
    private final Persitence persitence;

    public Logic(Persitence persitence) {
        this.persitence = persitence;
    }

    public Entries getContacts() {
        return persitence.showContacts();
    }

    public Entries insertContacts(List<Functions.NameDate> nameDateList) {
        List<Entry> entryList = new ArrayList<>();
        for (Functions.NameDate nameDate : nameDateList) {
            String[] split = nameDate.nameAndSurname.split("=");
            LocalDate date;
            if (nameDate.date == null) {
                date = LocalDate.now();
            } else {
                date = nameDate.date;
            }
            persitence.insertContacts(split[0], split[1], date);
            entryList.add(new Entry(date, split[0], split[1]));
        }
        return new Entries(entryList, Color.GREEN);
    }
}
