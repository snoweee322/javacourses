import java.io.*;
import java.util.*;

public class Main {
    private static User generateObj() {
        String[] fieldName = {"Alex", "Joshua", "Paul", "Grisha", "Richard", "Emma", "Olivia", "Isabella",
                "Mia", "Matthew"};
        String[] fieldEmail = {"A@abc.com", "B@abc.com", "C@abc.com", "D@abc.com", "E@abc.com", "F@abc.com"};
        String[] fieldLang = {"C/C++", "Java", "JavaScript", "R", "Ruby", "Python", "C#", "Go", "Swift",
                "Prolog", "Erlang", "Lisp", "Fortran", "Delphi", "Rust", "Lua", "Perl", "Scala", "PHP", "Haskell"};
        Random random = new Random();
        return new Developer(fieldName[random.nextInt(fieldName.length)],
                fieldEmail[random.nextInt(fieldEmail.length)], fieldLang[random.nextInt(fieldLang.length)]);
    }
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

        File file = new File("/home/snoweee/Desktop/javacourses/devsmanagers", "devs.csv");
        if (file.exists() && file.isFile())
            file.delete();
        file.createNewFile();
        for(Integer i = 0; i < 1000; i++)
            developers[i].toCSV();

        file = new File("/home/snoweee/Desktop/javacourses/devsmanagers", "managers.csv");
        if (file.exists() && file.isFile())
            file.delete();
        file.createNewFile();
        for(Integer i = 0; i < 1000; i++) {
            managers[i].toCSV();
        }
    }
    public static void main(String[] args) throws IOException {
        generate(); // to create new CSVs files
        Integer number = 666;
        Developer developer = new Developer("", "", "");
        Manager manager = new Manager("", "", "");
        String string1 = developer.fromCSV(number);
        String string2 = manager.fromCSV(number);
        String str1[] = string1.split(";");
        String str2[] = string2.split(";");
        //System.out.println("Number: " + number + " // Record: " + Arrays.toString(str1));
        //System.out.println("Number: " + number + " // Record: " + Arrays.toString(str2));


        List<User> arrayList = new ArrayList<User>(); // сравнение скоростей добавления
        List<User> linkedList = new LinkedList<User>();

        Long startTime = System.nanoTime();
        for(int i = 0; i < 10000000; i++) { // 10 млн элементов (добавление + генерация)
            arrayList.add(generateObj());
        }
        Long endTime = System.nanoTime();
        Double elapsedTime = ((double)endTime - startTime)/1000000000;
        System.out.println("Elapsed time for ArrayList: " + elapsedTime + " seconds.");

        startTime = System.nanoTime();
        for(int i = 0; i < 10000000; i++) { // 10 млн элементов (добавление + генерация)
            linkedList.add(generateObj());
        }
        endTime = System.nanoTime();
        elapsedTime = ((double)endTime - startTime)/1000000000;
        System.out.println("Elapsed time for LinkedList: " + elapsedTime + " seconds.");
    }
}


