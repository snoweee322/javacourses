import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    public TextFlow textFlow;

    @FXML
    private Button deleteButton;

    private String jsonString;

    public List<Note> getList() throws IOException { // json file -> list

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(new File("src/main/resources/notes.json"),
                new TypeReference<List<Note>>(){});
    }

    void updateJsonFile(List<Note> updatedNotes) throws IOException { // list -> recreate json file

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("src/main/resources/notes.json"), updatedNotes);
    }

    @FXML
    public ListView list;

    @FXML
    private Button testBtn;

    @FXML
    private Text noteName;

    @FXML
    public void click(MouseEvent mouseEvent) throws IOException {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New note");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField from = new TextField();
        from.setPromptText("Name");
        TextField to = new TextField();
        to.resize(50, 50);
        to.setPromptText("Note");

        to.setPrefWidth(800);
        gridPane.add(from, 0, 0);
        gridPane.add(to, 2, 0);

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(() -> from.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(from.getText(), to.getText());
            }
            return null;
        });

        List<Note> notes = getList();

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            list.getItems().add(from.getText());
            Note temp = new Note(from.getText(), to.getText());
            notes.add(temp);
        });
        updateJsonFile(notes);
    }

    @FXML
    public void selectItem(MouseEvent mouseEvent) throws IOException {
        String name = list.getSelectionModel().getSelectedItem().toString();
        noteName.setText(name);
        List<Note> notes = getList();
        String content = notes.stream()
                .filter(note -> list.getSelectionModel().getSelectedItem().toString().equals(note.getName()))
                .findAny()
                .get()
                .getContent();
        Text text = new Text(content);
        textFlow.getChildren().clear();
        textFlow.getChildren().add(text);

    }

    public void deleteNote(MouseEvent mouseEvent) throws IOException {
        List<Note> notes = getList();
        Note toDel = notes.stream()
                .filter(note -> list.getSelectionModel().getSelectedItem().toString().equals(note.getName()))
                .findAny()
                .get();
        notes.remove(toDel);
        updateJsonFile(notes);
        list.getItems().removeAll();
        list.getItems().clear();
        list.refresh();
        list.getItems().addAll(notes
                .stream()
                .map(Note::getName)
                .collect(Collectors.toList())
        );
    }
}
