class User implements Comparable<User> {
    private String name;
    private String email;
    public Integer KPI;

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
