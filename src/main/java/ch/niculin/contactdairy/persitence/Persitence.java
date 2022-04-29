package ch.niculin.contactdairy.persitence;

import ch.niculin.contactdairy.Entries;

import java.time.LocalDate;

/**
 * Methods for save a contact into a persistence
 */
public interface Persitence {
    /**
     *
     * @return All Entries from persistence
     */
    Entries showContacts();

    /**
     * Initialises the persistence
     */
     void initialise();

    /**
     * Closes the persistence
     */
    void close();

    /**
     * insert data into persistence
     *
     * @param name    name of contact as String
     * @param surname surname of contact as String
     * @param date   date of contact as String
     */

    void insertContacts(String name, String surname, LocalDate date);
}
