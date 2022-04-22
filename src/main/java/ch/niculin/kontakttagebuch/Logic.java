package ch.niculin.kontakttagebuch;

import ch.niculin.kontakttagebuch.persitence.Persitence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Logic {
    private final Persitence persitence;


    public Logic(Persitence persitence) {
        this.persitence = persitence;
    }

    public Entries getContacts() {
        return persitence.showKontakte();
    }

    public Entries insertContacts(List<Functions.NameDate> nameDateList) {
        List<Entry> entryList = new ArrayList<>();
        for (Functions.NameDate nameDate : nameDateList) {
            String[] split = nameDate.nameAndSurname.split("=");
            String date;
            if (nameDate.dates == null) {
                date = LocalDate.now().toString();
            } else {
                date = nameDate.dates;
            }
            persitence.insertKontakte(split[0], split[1], date);
            entryList.add(new Entry(split[0], split[1], date));
        }
        return new Entries(entryList, Color.GREEN);
    }
}
