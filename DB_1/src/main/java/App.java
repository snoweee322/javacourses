import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws IOException, SQLException {

        Properties properties = new Properties();
        properties.load(new FileReader("src/main/resources/app.properties"));
        Connection connection = DriverManager.getConnection(
                properties.getProperty("db.host"),
                properties.getProperty("db.user"),
                properties.getProperty(("db.password")));
        System.out.println(properties.getProperty("db.host"));
        System.out.println(properties.getProperty("db.user"));


        Statement statement = connection.createStatement();
        //ResultSet resultSet;
        connection.setAutoCommit(false);

        Long startTime = System.nanoTime();
        statement.execute("CREATE TABLE user(id INT, fio VARCHAR(50), phone VARCHAR (12));");
        for (int i = 0; i < 1_000_000; i++)
            statement.execute("INSERT INTO user (id, fio, phone) VALUES ("+i+", 'Name', '100')");
        Long endTime = System.nanoTime();
        Double elapsedTime = ((double) endTime - startTime) / 1000000000;
        System.out.println("Time: " + elapsedTime);
        //resultSet = statement.executeQuery("SELECT * FROM user;");
/*        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String fio = resultSet.getString("fio");
            String phone = resultSet.getString("phone");
            System.out.println(id + " " + fio + " " + phone);
        }*/
        connection.commit();
        statement.execute("DROP TABLE user;");
        //resultSet.close();
        statement.close();
    }
}
