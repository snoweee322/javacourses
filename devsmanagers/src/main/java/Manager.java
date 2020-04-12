import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Manager extends User implements CSV {
    private String type;
    private String outputString;
    private Integer counter;
    private String str;

    public Manager(String name, String email, String type, Integer KPI) {
        super(name, email, KPI);
        this.type = type;
    }

    protected String getType() {
        return this.type;
    }

    @Override
    public void toCSV() throws IOException {
        FileWriter fw = new FileWriter("managers.csv", true);
        str = (String.join(";", this.getName(), this.getEmail(), this.getKPI().toString(),
                this.getType(), "\n"));
        fw.write(str);
        fw.close();
    }

    @Override
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
