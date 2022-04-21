package ch.niculin.kontakttagebuch;

import ch.niculin.kontakttagebuch.persitence.Persitence;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(name = "Help", version = "2.0", mixinStandardHelpOptions = true, description = "@|fg(red) commands:|@.")
class Functions implements Callable<Integer> {

    private final Persitence persitence;

    @Option(names = {"-s", "--show"}, description = "shows Kontakte")
    private boolean isShow;

    @Option(names = {"-n", "--names"}, split = ",", arity = "1..*", description = "insert person-name and surname")
    private HashMap<String, String> nameAndSurname;

    @Option(names = {"-d", "--date"}, split = ",", arity = "1..*", description = "insert date")
    private List<String> dates;

    Functions(Persitence persitence) {
        this.persitence = persitence;
    }

    @Override
    public Integer call() {
        List<Entry> entrieList = new ArrayList<>();
        if (isShow) {
            colorEntries(persitence.showKontakte());
        }
        if (nameAndSurname != null && dates == null) {
            for (Map.Entry<String, String> entry : nameAndSurname.entrySet()) {
                String name = entry.getKey();
                String surname = entry.getValue();
                persitence.insertKontakte(name, surname);
                entrieList.add(new Entry(name, surname, LocalDate.now().toString()));
            }
        }

        if (nameAndSurname != null && dates != null) {
            int index = 0;
            String name;
            String surname;
            String date;
            for (Map.Entry<String, String> entry : nameAndSurname.entrySet()) {
                name = entry.getKey();
                surname = entry.getValue();
                date = dates.get(index);
                persitence.insertKontakte(name, surname, date);
                entrieList.add(new Entry(name, surname, date));
                index++;
            }
        }
        colorEntries( new Entries(entrieList, Color.GREEN));
        return 0;
    }

    public void colorEntries(Entries entries){
        switch (entries.getColor()){
            case GREEN -> {
                for (Entry entry: entries.getEntries()){
                    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green,underline " + entry.getDate() + "\t" + entry.getName() + "\t" + entry.getSurname() + "|@"));
                }
            }
            case YELLOW -> {
                for (Entry entry: entries.getEntries()){
                    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,yellow " + entry.getDate() + "|@") + "\t" + CommandLine.Help.Ansi.AUTO.string("@|bold,blue " + entry.getName() + "     \t" + entry.getSurname() + "|@"));                }
            }
        }
    }



}


