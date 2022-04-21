package ch.niculin.kontakttagebuch.persitence;
//TODO comments

import ch.niculin.kontakttagebuch.Entries;

/**
 *
 */
public interface Persitence {
    Entries showKontakte();

    void initalisieren();

    void close();

    /**
     *
     * @param name
     * @param surname
     */
    void insertKontakte(String name, String surname);

    void insertKontakte(String name, String surname, String datum);
}
