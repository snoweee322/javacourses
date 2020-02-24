import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

abstract class User {
    private String name;
    private String email;
    public User(String name) {
        this.name = name;
    }
    public User(String name, String email) {
        this(name);
        this.email = email;
    }
    protected String getName() {
        return this.name;
    }
    protected String getEmail() {
        return this.email;
    }
}

class Developer extends User {
    private String language;
    public Developer(String name, String email, String language) {
        super(name, email);
        this.language = language;
    }
    protected String getLanguage() {
        return this.language;
    }
}

class Manager extends User {
    private String type;
    public Manager(String name, String email, String type) {
        super(name, email);
        this.type = type;
    }
    protected String getType() {
        return this.type;
    }
}

interface CSV {
    void toCSV(String str) throws IOException;
    String fromCSV(Integer number) throws IOException;
}

class Dev implements CSV {
    private String outputString;
    private Integer counter;
    public void toCSV(String str) throws IOException {
        FileWriter fw = new FileWriter("devs.csv", true);
        fw.write(str);
        fw.close();
    }
    public String fromCSV(Integer number) throws IOException {
        FileReader fw = new FileReader("devs.csv");
        Scanner output = new Scanner(fw);
        counter = 0;
        while(output.hasNextLine()) {
            if (counter.equals(number)) {
                outputString = output.nextLine();
                break;
            }
            else {
                output.nextLine();
            }
            counter++;
        }
        fw.close();
        return outputString;
    }
}

class Man implements CSV {
    private String outputString;
    private Integer counter;
    public void toCSV(String str) throws IOException {
        FileWriter fw = new FileWriter("managers.csv", true);
        fw.write(str);
        fw.close();
    }
    public String fromCSV(Integer number) throws IOException {
        FileReader fw = new FileReader("managers.csv");
        Scanner output = new Scanner(fw);
        counter = 0;
        while(output.hasNextLine()) {
            if (counter.equals(number)) {
                outputString = output.nextLine();
                break;
            }
            else {
                output.nextLine();
            }
            counter++;
        }
        fw.close();
        return outputString;
    }
}

public class Main {
    private static void generate() throws IOException {
        String[] fieldName = {"Alex", "Joshua", "Paul", "Grisha", "Richard", "Emma", "Olivia", "Isabella",
                "Mia", "Matthew"};
        String[] fieldEmail = {"A@abc.com", "B@abc.com", "C@abc.com", "D@abc.com", "E@abc.com", "F@abc.com"};
        String[] fieldLang = {"C/C++", "Java", "JavaScript", "R", "Ruby", "Python", "C#", "Go", "Swift",
                "Prolog", "Erlang", "Lisp", "Fortran", "Delphi", "Rust", "Lua", "Perl", "Scala", "PHP", "Haskell"};
        String[] fieldType = {"First-line manager", "Middle manager", "Top manager"};

        Random random = new Random();
        Developer[] developers = new Developer[1000];
        Manager[] managers = new Manager[1000];

        for(Integer i = 0; i < 1000; i++) {
            developers[i] = new Developer(fieldName[random.nextInt(fieldName.length)],
                    fieldEmail[random.nextInt(fieldEmail.length)], fieldLang[random.nextInt(fieldLang.length)]);
            managers[i] = new Manager(fieldName[random.nextInt(fieldName.length)],
                    fieldEmail[random.nextInt(fieldEmail.length)], fieldType[random.nextInt(fieldType.length)]);
        }
        Dev wrDev = new Dev();
        Man wrMan = new Man();
        File file = new File("/home/snoweee/Desktop/java_courses/devsmanagers", "devs.csv");
        if (file.exists() && file.isFile())
        {
            file.delete();
        }
        file.createNewFile();
        for(Integer i = 0; i < 1000; i++) {
            wrDev.toCSV(String.join(";", developers[i].getName(), developers[i].getEmail(),
                    developers[i].getLanguage(), "\n"));
        }
        file = new File("/home/snoweee/Desktop/java_courses/devsmanagers", "managers.csv");
        if (file.exists() && file.isFile())
        {
            file.delete();
        }
        file.createNewFile();
        for(Integer i = 0; i < 1000; i++) {
            wrMan.toCSV(String.join(";", managers[i].getName(), managers[i].getEmail(),
                    managers[i].getType(), "\n"));
        }
    }
    public static void main(String[] args) throws IOException {
        generate(); // to create new CSVs files
        Integer number = 666;
        Dev devR = new Dev();
        Man manR = new Man();
        String string1 = devR.fromCSV(number);
        String string2 = manR.fromCSV(number);
        String str1[] = string1.split(";");
        String str2[] = string2.split(";");
        System.out.println("Number: " + number + " // Record: " + Arrays.toString(str1));
        System.out.println("Number: " + number + " // Record: " + Arrays.toString(str2));
    }
}


