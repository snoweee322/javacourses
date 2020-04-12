import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class Main {
    static User search(TreeSet<User> tree, User key) { // treeSet search
        User ceil  = tree.ceiling(key); // least elt >= key
        User floor = tree.floor(key);   // highest elt <= key
        return ceil == floor ? ceil : null;
    }

    private static User generateObj() {
        String[] fieldName = {"Alex", "Joshua", "Paul", "Grisha", "Richard", "Emma", "Olivia", "Isabella",
                "Mia", "Matthew"};
        String[] fieldEmail = {"A@abc.com", "B@abc.com", "C@abc.com", "D@abc.com", "E@abc.com", "F@abc.com"};
        String[] fieldLang = {"C/C++", "Java", "JavaScript", "R", "Ruby", "Python", "C#", "Go", "Swift",
                "Prolog", "Erlang", "Lisp", "Fortran", "Delphi", "Rust", "Lua", "Perl", "Scala", "PHP", "Haskell"};
        Random random = new Random();
        return new Developer(fieldName[random.nextInt(fieldName.length)],
                fieldEmail[random.nextInt(fieldEmail.length)], fieldLang[random.nextInt(fieldLang.length)], random.nextInt(100)%100);
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
                    fieldEmail[random.nextInt(fieldEmail.length)], fieldLang[random.nextInt(fieldLang.length)], random.nextInt(100)%100);
            managers[i] = new Manager(fieldName[random.nextInt(fieldName.length)],
                    fieldEmail[random.nextInt(fieldEmail.length)], fieldType[random.nextInt(fieldType.length)], random.nextInt(100)%100);
        }

        //File file = new File("/home/snoweee/Desktop/javacourses/devsmanagers", "devs.csv");
        File file = new File("F:\\IntelliJProjects\\devsmanagers", "devs.csv");
        if (file.exists() && file.isFile())
            file.delete();
        file.createNewFile();
        for(Integer i = 0; i < 1000; i++)
            developers[i].toCSV();

        //file = new File("/home/snoweee/Desktop/javacourses/devsmanagers", "managers.csv");
        file = new File("F:\\IntelliJProjects\\devsmanagers", "managers.csv");
        if (file.exists() && file.isFile())
            file.delete();
        file.createNewFile();
        for(Integer i = 0; i < 1000; i++) {
            managers[i].toCSV();
        }
    }
    public static void main(String[] args) throws IOException {
        generate(); // to create new CSVs files
        Integer number = 111;
        Developer developer = new Developer("", "", "", 0);
        Manager manager = new Manager("", "", "", 0);
        String string1 = developer.fromCSV(number);
        String string2 = manager.fromCSV(number);
        String[] str1 = string1.split(";");
        String[] str2 = string2.split(";");
        System.out.println("Number: " + number + " // Record: " + Arrays.toString(str1));
        System.out.println("Number: " + number + " // Record: " + Arrays.toString(str2));

        // generic class

        Developer user1 = new Developer("1", "1", "1", 0); // трешак
        Developer user2 = new Developer("1", "1", "1", 100);
        Developer user3 = new Developer("1", "1", "1", 45);
        System.out.println("---GENERIC METHOD CALL---");
        Statistic<User> tmp1 = new Statistic<User>();//getMaxKPI(user1, user2, user3);
        User getMax = tmp1.getMaxKPI(user1, user2, user3);
        User getMin = tmp1.getMinKPI(user1, user2, user3);
        User getAverage = tmp1.getAvgKPI(user1, user2, user3);
        System.out.println("Max KPI: " + getMax.getKPI());
        System.out.println("Min KPI: " + getMin.getKPI());
        System.out.println("Average KPI: " + getAverage.getKPI());

        // arraylist vs linkedlist

        List<User> arrayList = new ArrayList<User>(); // сравнение скоростей добавления
        List<User> linkedList = new LinkedList<User>();

        Long startTime = System.nanoTime();
        for(int i = 0; i < 10000000; i++) { // 10 млн элементов (добавление + генерация)
            arrayList.add(generateObj());
        }
        Long endTime = System.nanoTime();
        Double elapsedTime = ((double)endTime - startTime)/1000000000;
        System.out.println("\nElapsed time for ArrayList: " + elapsedTime + " seconds.");

        startTime = System.nanoTime();
        for(int i = 0; i < 10000000; i++) { // 10 млн элементов (добавление + генерация)
            linkedList.add(generateObj());
        }
        endTime = System.nanoTime();
        elapsedTime = ((double)endTime - startTime)/1000000000;
        System.out.println("Elapsed time for LinkedList: " + elapsedTime + " seconds.");

        // compareTo/TreeSet test for 1mln

        TreeSet<User> treeSet = new TreeSet<>(); // Comparable
        for (Integer i = 0; i < 1_000_000; i++)
            treeSet.add(new Developer(i.toString(), "1", "1", 1));


        TreeSet<User> treeSet1 = new TreeSet<>(new UserComparator()); // UserComparator
        for (Integer i = 0; i < 1_000_000; i++)
                treeSet1.add(new Developer("1", "1", "1", i));

        // 0 - 100_000...900_000

        Integer toFind = 0;
        for (int p = 0; p < 10; p++) {
            Double accum = 0D;
            for (int i = 0; i < 100000; i++) {
                startTime = System.nanoTime();
                search(treeSet, new Developer(toFind.toString(), "1", "1", 1));
                endTime = System.nanoTime();
                elapsedTime = ((double) endTime - startTime) / 1000000000;
                accum += elapsedTime;
            }
            System.out.println("Time: " + accum / 100000 + "seconds");
            toFind += 100_000;
        }
    }
}


