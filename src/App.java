// 

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class App extends Application {

    public static User user;
    public static Project project;
    public static DatabaseManager dbManager;
    //Update this DBPath to whatever we want the path to be
    static String DBPath = "jdbc:sqlite:./src/";

    private MediaPlayer mediaPlayer;
    public static void main(String[] args) { 
        launch(args);
    }

    // This method loads the main layout from the FXML file
    // It also sets the title of the window and shows the window
    // It is called from both the LoginViewController and the RegisterViewController
    public static void loadMainLayout(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        //scene.getStylesheets().add(App.class.getResource("EffortStyleMK2.css").toExternalForm());
        stage.setTitle("Effort Logger 2.0");
        stage.setScene(scene);
        stage.show();
    }
    
    
    public void start(Stage stage) throws IOException, SQLException {
        // Initialize database manager
        dbManager = new DatabaseManager();
    
        // Create a File object for the user.ser file
        // This is used to check if the user has already registered
        File userFile = new File("user.ser");
        FXMLLoader loader;
        if (userFile.exists()) {
            // If the file exists, load the LoginViewController
            loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        } else {
            // If the file doesn't exist, load the RegisterViewController
            loader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
        }
    
        Scene scene = new Scene(loader.load(), 960, 540);
        //scene.getStylesheets().add(getClass().getResource("EffortStyleMK2.css").toExternalForm());
        stage.setTitle("Effort Logger 2.0");
        stage.setScene(scene);
        stage.show();
    
        project = new Project();
        project.DatabaseName = "test";
    
        dbManager.connect(project);
        
        /* 
        URI audioFileURI = new File("resources/EffortLogger_ost2.mp3").toURI();
        Media audioMedia = new Media(audioFileURI.toString());
    
        // Initialize the mediaPlayer instance variable and set it to autoplay
        mediaPlayer = new MediaPlayer(audioMedia);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set the cycleCount to INDEFINITE
        */
    }    
}