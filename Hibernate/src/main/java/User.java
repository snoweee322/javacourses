import javax.persistence.*;

@Table
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class User implements Comparable<User> {

    @Column
    public String name;

    @Column
    public String email;

    @Column
    public Integer KPI;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email, Integer KPI) {
        this(name);
        this.email = email;
        this.KPI = KPI;
    }

    public User(String name, String email) {
    }

    protected String getName() {
        return this.name;
    }

    protected String getEmail() {
        return this.email;
    }

    protected Integer getKPI() {
        return this.KPI;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKPI(Integer KPI) {
        this.KPI = KPI;
    }

    @Override
    public int compareTo(User temp) {
            int result = (this.getName().compareTo(temp.getName()));
            if (result < 0)
                return -1; // lesser
            else if (result == 0)
                return 0; //equal
            return 1; //greater
    }
}
