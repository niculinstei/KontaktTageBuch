package ch.niculin.kontakttagebuch.persitence;


import ch.niculin.kontakttagebuch.Color;
import ch.niculin.kontakttagebuch.Entries;
import ch.niculin.kontakttagebuch.Entry;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeristenceSQL implements Persitence {
    private final Connection connection;

    public PeristenceSQL(String path, String dbName) {
        String datenbankURL = System.getProperty(path) + "\\" + dbName;
        System.out.println(datenbankURL);
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + datenbankURL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initalisieren() {
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
    public Entries showKontakte() {
        List<Entry> entryList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT datum, name, surname FROM Kontakt INNER JOIN Datum ON Datum.ID = Kontakt.datumID INNER JOIN Person ON Person.ID = Kontakt.personID order  by datum");
            while (result.next()) {
                entryList.add(new Entry(result.getString("name"), result.getString("surname"), result.getString("datum")));
            }
            return new Entries(entryList, Color.YELLOW);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int addDatum(String datum2) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Datum values(?, '" + datum2 + "');");
        } catch (SQLException e) {
            if (!e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
                throw new RuntimeException(e);
            }
        }
        try {
            return statement.executeQuery("SELECT ID FROM Datum WHERE datum = '" + datum2 + "';").getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private int addPerson(String name, String surname) {
        String sql = "INSERT INTO  Person(name, surname) VALUES(?,?)";
        PreparedStatement pstm = null;
        try {

            pstm = connection.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, surname);
            pstm.executeUpdate();

        } catch (SQLException e) {
            if (!e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
                throw new RuntimeException(e);
            }
        }
        try {
            String sql2 = "SELECT ID FROM Person WHERE name = '" + name + "' AND surname = '" + surname + "'";
            PreparedStatement pstm2;
            pstm2 = connection.prepareStatement(sql2);
            int id = pstm2.executeQuery().getInt(1);
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertKontakte(String name, String surname) {
        insertKontakte(name, surname, LocalDate.now().toString());
    }

    @Override
    public void insertKontakte(String name, String surname, String datum) {
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(surname, "surname can't be null");
        Objects.requireNonNull(datum, "Date can't be null");
        int personID = addPerson(name, surname);
        int datumID = addDatum(datum);
        addKontakt(datumID, personID);
    }

    private void addKontakt(int datumID, int personID) {
        String sql = "INSERT INTO  Kontakt(datumID, personID) VALUES(?,?)";
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, datumID);
            pstm.setInt(2, personID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}