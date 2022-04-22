package ch.niculin.kontakttagebuch;

import ch.niculin.kontakttagebuch.persitence.PeristenceSQL;
import ch.niculin.kontakttagebuch.persitence.Persitence;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

public class KontaktTagebuch {

    public static void main(String[] args) {

        Persitence peristence = new PeristenceSQL("user.home", "datebase.db");
        Logic logic = new Logic(peristence);

        peristence.initalisieren();
        AnsiConsole.systemInstall();

        int exitCode = new CommandLine(new Functions(logic)).execute(args);
        AnsiConsole.systemUninstall();
        peristence.close();
        System.exit(exitCode);
    }


}




