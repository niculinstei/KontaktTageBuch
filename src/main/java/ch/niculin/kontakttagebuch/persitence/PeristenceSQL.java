package ch.niculin.kontakttagebuch.persitence;

import picocli.CommandLine;

import java.sql.*;
import java.time.LocalDate;
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
            statement.execute("CREATE TABLE IF NOT EXISTS Person(ID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, UNIQUE(name));");
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
    public void showKontakte() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT datum, name FROM Kontakt INNER JOIN Datum ON Datum.ID = Kontakt.datumID INNER JOIN Person ON Person.ID = Kontakt.personID order  by datum");
            while (result.next()) {
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,yellow " + result.getString("datum") + "|@") + "\t" + CommandLine.Help.Ansi.AUTO.string("@|bold,blue " + result.getString("name") + "|@"));
            }
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

    private int addPerson(String name) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Person values(?, '" + name + "');");

        } catch (SQLException e) {
            if (!e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
                throw new RuntimeException(e);
            }
        }
        try {
            return statement.executeQuery("SELECT ID FROM Person WHERE name = '" + name + "';").getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertKontakte(String name) {
        insertKontakte(name, LocalDate.now().toString());
    }

    @Override
    public void insertKontakte(String name, String datum) {
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(datum, "Date can't be null");
        int personID = addPerson(name);
        int datumID = addDatum(datum);
        addKontakt(datumID, personID);
    }

    private void addKontakt(int datumID, int personID) {
        String sql = "INSERT INTO  Kontakt(datumID, personID) VALUES(?,?)";
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1,datumID);
            pstm.setInt(2, personID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


