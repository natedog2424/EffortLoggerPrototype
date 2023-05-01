// Authored by: Nathan Anderson, Noah McLelland, Adit Prabhu, Evan Severtson, and Annalise LaCourse
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TutorialViewController {

    @FXML
    public void createProject() {
        App.newProject();
    }

    @FXML
    public void importProject() {
        
    }
}
