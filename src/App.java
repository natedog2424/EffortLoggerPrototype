// 

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class App extends Application {

    static User user;
    static Project project;
    static DatabaseManager dbManager;
    //Update this DBPath to whatever we want the path to be
    static String DBPath = "jdbc:sqlite:./src/";


    public static void main(String[] args) { 
        launch(args);
    }
    
    public void start(Stage stage) throws IOException, SQLException {
        //Initialize database manager
        dbManager = new DatabaseManager();

        // Handle taking login info and such here in the future. For now, just load the project pane and fill in a default user object.
        user = new User("John Doe", "password");

        project = new Project();
        project.DatabaseName = "test";

        dbManager.connect(project);
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        stage.setTitle("Effort Logger 2.0");
        stage.setScene(scene);
        stage.show();
    }
}