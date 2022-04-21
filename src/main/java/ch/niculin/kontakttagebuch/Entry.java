package ch.niculin.kontakttagebuch;

public class Entry {
    private final String name;
    private final String surname;
    private final String date;

    public Entry(String name, String surname, String date){
        this.name = name;
        this.surname = surname;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }
}
