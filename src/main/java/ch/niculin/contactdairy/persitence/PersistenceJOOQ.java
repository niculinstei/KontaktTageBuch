package ch.niculin.contactdairy.persitence;

import ch.niculin.contactdairy.Color;
import ch.niculin.contactdairy.Entries;
import ch.niculin.contactdairy.Entry;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ch.niculin.contactdairy.jooq.tables.Datum.DATUM;
import static ch.niculin.contactdairy.jooq.tables.Kontakt.KONTAKT;
import static ch.niculin.contactdairy.jooq.tables.Person.PERSON;
import static org.jooq.impl.DSL.foreignKey;
import static org.jooq.impl.SQLDataType.*;

public class PersistenceJOOQ implements Persitence {
    private final Connection connection;
    private final DSLContext context;

    public PersistenceJOOQ(String path, String dbName) {
        String datenbankURL = System.getProperty(path) + "\\" + dbName;
        System.out.println(datenbankURL);
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + datenbankURL);
            context = DSL.using(connection, SQLDialect.SQLITE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialise() {
      if (DSL.using(connection).meta().getTables().isEmpty()){
          createDatum();
          createPerson();
          createKontakte();
      }

    }

    private void createDatum() {
        context.createTable("Datum")
                .column("ID", INTEGER.identity(true))
                .column("datum", DATE)
                .unique("datum")
                .primaryKey("ID")
                .execute();
    }

    private void createKontakte() {
        context.createTable("Kontakt")
                .column("datumID", INTEGER)
                .column("personID", INTEGER)
                .constraints(
                        foreignKey("datumID").references("Datum", "ID"),
                        foreignKey("personID").references("Person", "ID")

                )
                .unique("datumID", "personID")
                .execute();
    }

    private void createPerson() {
        context.createTable("Person")
                .column("ID", INTEGER.identity(true))
                .column("name", VARCHAR)
                .column("surname", VARCHAR)
                .unique("name", "surname")
                .primaryKey("ID")
                .execute();
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Entries showContacts() {
        List<Entry> entryList = new ArrayList<>();
        Result<Record3<LocalDate, String, String>> result = context.select(DATUM.DATUM_, PERSON.NAME, PERSON.SURNAME).from(KONTAKT)
                .innerJoin(DATUM).on(DATUM.ID.eq(KONTAKT.DATUMID))
                .innerJoin(PERSON).on(PERSON.ID.eq(KONTAKT.PERSONID))
                .fetch();

        for (Record record : result) {
            entryList.add(new Entry( (LocalDate) record.getValue(0),  (String) record.getValue(1),  (String) record.getValue(2)));
        }
        return new Entries(entryList, Color.YELLOW);

    }

    private int addDate(LocalDate datum) {
        try {
            context.insertInto(DATUM, DATUM.DATUM_)
                    .values(datum)
                    .execute();
        } catch (Exception e) {
            if (!e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
                throw new RuntimeException(e);
            }
        }
        Result<Record1<Integer>> result = context.select(DATUM.ID).from(DATUM).where(DATUM.DATUM_.eq(datum)).fetch();
        return result.getValue(0, DATUM.ID);

    }

    private int addPerson(String name, String surname) {
        try {
            context.insertInto(PERSON, PERSON.NAME, PERSON.SURNAME)
                    .values(name, surname)
                    .execute();
        } catch (Exception e) {
            if (!e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
                throw new RuntimeException(e);
            }
        }
        Result<Record1<Integer>> result = context.select(PERSON.ID).from(PERSON).where(PERSON.NAME.eq(name)).and(PERSON.SURNAME.eq(surname)).fetch();
        return result.getValue(0, PERSON.ID);
    }

    @Override
    public void insertContacts(String name, String surname, LocalDate date) {
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(surname, "surname can't be null");
        Objects.requireNonNull(date, "Date can't be null");
        int personID = addPerson(name, surname);
        int datumID = addDate(date);
        addContact(datumID, personID);
    }

    private void addContact(int datumID, int personID) {
        try {
            context.insertInto(KONTAKT, KONTAKT.DATUMID, KONTAKT.PERSONID)
                    .values(datumID, personID)
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
