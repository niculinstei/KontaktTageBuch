package ch.niculin.contactdairy;

import ch.niculin.contactdairy.persitence.DummyPersitenceImpl;
import ch.niculin.contactdairy.persitence.Persitence;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    private final Persitence persitence = new DummyPersitenceImpl();
    private final Logic logic = new Logic(persitence);

    @Test
    public void testValidInsert() {
        Entries entries = logic.insertContacts(List.of(getNameDate()));

        Entry entry = entries.getEntries().get(0);

        assertEquals("niculin", entry.getName());
        assertEquals("steiner", entry.getSurname());
        assertEquals(LocalDate.now(), entry.getDate());
    }

    @Test
    public void testValidEntriesFromGetContacts() {
        Entries entries = logic.getContacts();

        Entry entry = entries.getEntries().get(0);

        assertEquals("niculin", entry.getName());
        assertEquals("steiner", entry.getSurname());
        assertEquals(LocalDate.now(), entry.getDate());
        assertEquals(Color.YELLOW, entries.getColor());

    }

    @Test
    public void testDateIsNull() {
        Functions.NameDate nameDate = new Functions.NameDate();
        nameDate.setNameAndSurname("Niculin=Steiner");

        Entries entries = logic.insertContacts(List.of(nameDate));

        boolean condition = entries.getEntries().get(0).getDate().equals(LocalDate.now());
        assertTrue(condition);
    }

    @Test
    public void testDateIsNotNull() {
        Entries entries = logic.insertContacts(List.of(getNameDate()));

        Entry entry = entries.getEntries().get(0);

        boolean condition = entries.getEntries().get(0).getDate().equals(entry.getDate());
        assertTrue(condition);
    }

    @Test
    public void testReturnColor() {
        Entries entries = logic.insertContacts(List.of(getNameDate()));
        boolean condition = entries.getColor().equals(Color.GREEN);
        assertTrue(condition);
    }

    private static Functions.NameDate getNameDate() {
        Functions.NameDate nameDate = new Functions.NameDate();
        nameDate.setDate(LocalDate.now());
        nameDate.setNameAndSurname("niculin=steiner");
        return nameDate;
    }
}