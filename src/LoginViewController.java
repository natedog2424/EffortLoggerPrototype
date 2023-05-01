// Authored by: Nathan Anderson, Noah McLelland, Adit Prabhu, Evan Severtson, and Annalise LaCourse
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController implements Initializable {

    // The password field
    @FXML
    private TextField LoginPasswordField;

    @FXML
    private Label welcomeLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Deserialize the User object from the file
        try (FileInputStream fis = new FileInputStream("user.ser")) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            App.user = (User) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        welcomeLabel.setText("Welcome, " + App.user.fullName + "!");
    }

    // This method is called when the user presses the "Enter" button
    // It checks if the entered password matches the stored password
    // If it does, it prints "Logged in" to the console
    // If it doesn't, it prints "ERROR" to the console
    @FXML
    void onEnterButtonPressed() {
        String password = LoginPasswordField.getText();
        if (App.user.password.equals(password)) {
            try {
                // If the password matches, load the main layout
                App.loadMainLayout((Stage) LoginPasswordField.getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
