package ch.niculin.contactdairy;

import ch.niculin.contactdairy.persitence.PersistenceJOOQ;
import ch.niculin.contactdairy.persitence.Persitence;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

public class ContactDiary {

    public static void main(String[] args) {

        Persitence peristence = new PersistenceJOOQ("user.home", "datebase.db");
        Logic logic = new Logic(peristence);
        peristence.initialise();
        int exitCode = new CommandLine(new Functions(logic)).execute(args);
        AnsiConsole.systemUninstall();
        peristence.close();
        System.exit(exitCode);
    }
}




