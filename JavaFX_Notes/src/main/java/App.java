import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //createAndFillJson();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Note> notesList = objectMapper.readValue(new File("src/main/resources/notes.json"),
                new TypeReference<List<Note>>(){});

        Parent parent = FXMLLoader.load(new File("src/main/resources/config.fxml").toURI().toURL());
        Scene scene = new Scene(parent);

        ListView list = (ListView)scene.lookup("#list");
        list.getItems().addAll(notesList
                .stream()
                .map(Note::getName)
                .collect(Collectors.toList())
        );

        stage.setScene(scene);
        stage.setTitle("Notes");
        stage.show();
    }

    public void createAndFillJson() throws IOException {

        List<Note> notes = new ArrayList<>();
        notes.add(new Note("First note", "content"));
        notes.add(new Note("Second note", "content"));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("src/main/resources/notes.json"), notes);
    }
}
