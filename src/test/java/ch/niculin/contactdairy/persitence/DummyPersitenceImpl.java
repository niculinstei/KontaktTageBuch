package ch.niculin.contactdairy.persitence;

import ch.niculin.contactdairy.Color;
import ch.niculin.contactdairy.Entries;
import ch.niculin.contactdairy.Entry;

import java.time.LocalDate;
import java.util.List;

public class DummyPersitenceImpl implements Persitence{
    @Override
    public Entries showContacts() {
        return new Entries(List.of(new Entry(LocalDate.now(), "niculin", "steiner")), Color.YELLOW);
    }

    @Override
    public void initialise() {

    }

    @Override
    public void close() {

    }

    @Override
    public void insertContacts(String name, String surname, LocalDate date) {

    }


}
