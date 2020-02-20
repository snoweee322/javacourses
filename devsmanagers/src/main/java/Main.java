import java.util.InputMismatchException;
import java.util.Scanner;

abstract class User {
    private String name;
    private String email;
    private String jobTitle;
    public User(String name) {
        this.name = name;
    }
    public User(String name, String email, String jobTitle) {
        this(name);
        this.email = email;
        this.jobTitle = jobTitle;
    }
    protected String getName() {
        return this.name;
    }
}

class Developer extends User {
    private String language;
    public Developer(String name, String email, String jobTitle, String language) {
        super(name, email, jobTitle);
        this.language = language;
    }
}

class Manager extends User {
    private String type;
    public Manager(String name, String email, String jobTitle, String type) {
        super(name, email, jobTitle);
        this.type = type;
    }
}

public class Main {
    public static void main(String[] args) {
        Developer object = new Developer("John", "abc@mail.com", "junior developer", "Java");
        System.out.println(object.getName());
    }
}


