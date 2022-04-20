package ch.niculin.kontakttagebuch.persitence;

public interface Persitence {
    void showKontakte();
    void initalisieren();
    void close();
    void insertKontakte(String name);
    void insertKontakte(String name, String datum);
}
