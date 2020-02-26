import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Developer extends User implements CSV {
    private String language;
    private String outputString;
    private Integer counter;
    private String str;

    public Developer(String name, String email, String language) {
        super(name, email);
        this.language = language;
    }
    protected String getLanguage() {
        return this.language;
    }

    @Override
    public void toCSV() throws IOException {
        FileWriter fw = new FileWriter("devs.csv", true);
        str = (String.join(";", this.getName(), this.getEmail(),
                this.getLanguage(), "\n"));
        fw.write(str);
        fw.close();
    }

    @Override
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