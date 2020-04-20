import javax.persistence.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

@Entity
@Table
class Developer extends User {


    @Id
    @SequenceGenerator(name = "developerGenerator", sequenceName = "DEVELOPER_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developerGenerator")
    @Column
    private Integer id;

    @Column
    private String language;

    public Developer() {}

    public Developer(String name, String email, String language, Integer KPI) {
        super(name, email, KPI);
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected String getLanguage() {
        return this.language;
    }
}