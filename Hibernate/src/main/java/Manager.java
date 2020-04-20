import javax.persistence.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

@Entity
@Table
class Manager extends User {

    public Manager() {}

    @Id
    @SequenceGenerator(name = "managerGenerator", sequenceName = "MANAGER_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "managerGenerator")
    @Column
    private Integer id;

    @Column
    private String type;

    public Manager(String name, String email, String type, Integer KPI) {
        super(name, email, KPI);
        this.type = type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected String getType() { return this.type; }

    public Integer getId() {
        return id;
    }
}
