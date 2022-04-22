package ch.niculin.kontakttagebuch;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Help;

@Command(name = "Help", version = "2.0", mixinStandardHelpOptions = true, description = "@|fg(red) commands:|@.")
class Functions implements Callable<Integer> {

    private final Logic logic;

    @Option(names = {"-s", "--show"}, description = "shows Kontakte")
    private boolean isShow;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "1..*", validate = false)
    List<NameDate> group;

    public static class NameDate {
        @Option(names = {"-n", "--names"}, arity = "1", required = true, description = "insert person-name=surname")
        String nameAndSurname;

        @Option(names = {"-d", "--date"}, arity = "0..1", required = false, description = "insert date")
        String dates;
    }

    Functions(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Integer call() {
        List<Entry> entrieList = new ArrayList<>();
        if (isShow) {
            printEntries(logic.getContacts());
        } else {
            printEntries(logic.insertContacts(group));
        }
        printEntries(new Entries(entrieList, Color.GREEN));
        return 0;
    }

    public void printEntries(Entries entries) {
        switch (entries.getColor()) {
            case GREEN -> {
                for (Entry entry : entries.getEntries()) {
                    System.out.println(Help.Ansi.AUTO.string("@|bold,green,underline " + entry.getDate() + "\t" + entry.getName() + "\t" + entry.getSurname() + "|@"));
                }
            }
            case YELLOW -> {
                for (Entry entry : entries.getEntries()) {
                    System.out.println(Help.Ansi.AUTO.string("@|bold,yellow " + entry.getDate() + "|@") + "\t" + Help.Ansi.AUTO.string("@|bold,blue " + entry.getName() + "     \t" + entry.getSurname() + "|@"));
                }
            }
        }
    }
}


//-n nics=st2ws41 -d 2022-33-46 -n nsi=s2st42 -n niculin=stein2er2 -d 2002-02-01