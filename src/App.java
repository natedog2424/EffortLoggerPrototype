// 

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class App extends Application {

    static User user;
    static Project project;
    static DatabaseManager dbManager;
    //Update this DBUrl to whatever we want the path to be
    static String DBUrl = "jdbc:sqlite:./src/effort.db";


    public static void main(String[] args) { 
        launch(args);
    }
    
    public void start(Stage stage) throws IOException {
        //Initialize database manager
        dbManager = new DatabaseManager();

        // Handle taking login info and such here in the future. For now, just load the project pane and fill in a default user object.
        user = new User("John Doe", "password");


        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ProjectPane.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 340);
        stage.setTitle("Effort Logger 2.0");
        stage.setScene(scene);
        stage.show();
    }
}