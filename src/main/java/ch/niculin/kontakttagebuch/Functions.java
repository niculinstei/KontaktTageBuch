package ch.niculin.kontakttagebuch;

import ch.niculin.kontakttagebuch.persitence.Persitence;
import picocli.CommandLine;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "Help", version = "2.3.45.1.6.3", mixinStandardHelpOptions = true, description = "@|fg(red) commands:|@.")
class Functions implements Callable<Integer> {
    private final Persitence persitence;


    @CommandLine.Option(names = {"-s", "--show"}, description = "shows Kontakte")
    private boolean isShow;

    @CommandLine.Option(names = {"-i", "--insert"}, split = ",", arity = "1..*", description = "insert person-name and -date")
    private HashMap<String, String> namesAndDates;

    @CommandLine.Option(names = {"-is", "--insertSimple"}, split = ",", arity = "1..*", description = "insert person-name")
    private List<String> names;



    Functions(Persitence persitence) {
        this.persitence = persitence;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green,underline inCallMethode|@"));
        if (isShow) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green,underline isShow=true|@"));
            persitence.showKontakte();
        }
        if (names != null) {
            for (String name : names) {
                persitence.insertKontakte(name);
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green,underline " + LocalDate.now().toString() + "\t" + name + "|@"));
            }
        }
        if (namesAndDates != null) {
            for (Map.Entry<String, String> entry : namesAndDates.entrySet()) {
                String name = entry.getKey();
                String datum = entry.getValue();
                persitence.insertKontakte(name, datum);
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green,underline " + datum + "\t" + name + "|@"));
            }
        }
        return 0;
    }
}
