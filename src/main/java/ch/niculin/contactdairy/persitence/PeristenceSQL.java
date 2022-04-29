package ch.niculin.contactdairy.persitence;


import ch.niculin.contactdairy.Color;
import ch.niculin.contactdairy.Entries;
import ch.niculin.contactdairy.Entry;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeristenceSQL implements Persitence {
    private final Connection connection;
    private final DSLContext context;

    public PeristenceSQL(String path, String dbName) {
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
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Datum(ID INTEGER PRIMARY KEY AUTOINCREMENT, datum DATE, UNIQUE(datum));");
            statement.execute("CREATE TABLE IF NOT EXISTS Person(ID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, surname VARCHAR, UNIQUE(name, surname));");
            statement.execute("CREATE TABLE IF NOT EXISTS Kontakt(datumID INTEGER, personID INTEGER, FOREIGN KEY (datumID) REFERENCES Datum (datumID) FOREIGN KEY (personID) REFERENCES Person (personID), UNIQUE(datumID, personID));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT datum, name, surname FROM Kontakt INNER JOIN Datum ON Datum.ID = Kontakt.datumID INNER JOIN Person ON Person.ID = Kontakt.personID order  by datum");
            while (result.next()) {
               // entryList.add(new Entry(result.getString("name"), result.getString("surname"), (LocalDate) result.getString("datum")));
            }
            return new Entries(entryList, Color.YELLOW);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int addDate(LocalDate datum) {
        String insertTemplate = "INSERT INTO Datum(datum) values(?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(insertTemplate);
            pstm.setString(1, datum.toString());
        } catch (SQLException e) {
            if (!e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
                throw new RuntimeException(e);
            }
        }
        try {
            String selectTemplate = "SELECT ID FROM Datum WHERE datum =?";
            PreparedStatement pstm = connection.prepareStatement(selectTemplate);
            pstm.setString(1, datum.toString());
            return pstm.executeQuery().getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    private int addPerson(String name, String surname) {
        String insertTemplate = "INSERT INTO  Person(name, surname) VALUES(?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(insertTemplate);
            pstm.setString(1, name);
            pstm.setString(2, surname);
            pstm.executeUpdate();
        } catch (SQLException e) {
            if (!e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
                throw new RuntimeException(e);
            }
        }
        try {

            String selectTemplate = "SELECT ID FROM Person WHERE name =? AND surname =?";
            PreparedStatement pstm = connection.prepareStatement(selectTemplate);
            pstm.setString(1, name);
            pstm.setString(2, surname);
            return pstm.executeQuery().getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        String sql = "INSERT INTO  Kontakt(datumID, personID) VALUES(?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, datumID);
            pstm.setInt(2, personID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}