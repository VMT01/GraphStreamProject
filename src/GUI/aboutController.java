package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class aboutController {
    @FXML
    BorderPane aboutPane = new BorderPane();
    @FXML
    Button closeAboutButton = new Button();

    @FXML
    public void closeAbout() {
        Stage aboutPaneRoot = (Stage) aboutPane.getScene().getWindow();
        aboutPaneRoot.close();
    }
}
