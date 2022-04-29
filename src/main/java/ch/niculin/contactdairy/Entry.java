package ch.niculin.contactdairy;

import java.time.LocalDate;

public class Entry {
    private final String name;
    private final String surname;
    private final LocalDate date;

    public Entry(LocalDate date, String name, String surname){
        this.name = name;
        this.surname = surname;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }
}
