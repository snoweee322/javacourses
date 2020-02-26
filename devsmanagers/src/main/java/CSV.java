import java.io.IOException;

interface CSV {
    void toCSV() throws IOException;
    String fromCSV(Integer number) throws IOException;
}
