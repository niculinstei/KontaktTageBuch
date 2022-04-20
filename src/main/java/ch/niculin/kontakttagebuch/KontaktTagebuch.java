package ch.niculin.kontakttagebuch;

import ch.niculin.kontakttagebuch.persitence.PeristenceSQL;
import ch.niculin.kontakttagebuch.persitence.Persitence;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

public class KontaktTagebuch {


    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        Persitence peristence = new PeristenceSQL("user.home", "datebase.db");

        try {
            peristence.initalisieren();

            int exitCode = new CommandLine(new Functions(peristence)).execute(args);
            System.exit(exitCode);
            AnsiConsole.systemUninstall();

        } finally {
            peristence.close();
        }
    }
}




