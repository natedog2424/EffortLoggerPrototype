import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterViewController implements Initializable {
    
    // This is the full name field
    @FXML
    private TextField FullNameField;

    // This is the password field
    @FXML
    private TextField PasswordField;

    //
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }

    // This method is called when the user clicks the "Create" button
    // It creates a new user, serializes it, and saves it to a file
    @FXML
    protected void onCreateButtonPressed(){
        String fullName = FullNameField.getText();
        String password = PasswordField.getText();

        User newUser = new User(fullName, password);

        byte[] serializedUser = newUser.serialize();

        try (FileOutputStream fos = new FileOutputStream("user" + ".ser")) {
            fos.write(serializedUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Load the main layout
            App.loadMainLayout((Stage) FullNameField.getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
