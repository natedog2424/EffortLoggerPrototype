// 

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class App extends Application {

    public static User user;
    public static Project project;
    public static DatabaseManager dbManager;
    public static MainViewController mainVC;

    public static ArrayList<String> savedProjects = new ArrayList<String>();
    //Update this DBPath to whatever we want the path to be
    static String DBDir = "./projects/";
    static String DBPath = "jdbc:sqlite:" + DBDir;

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
        mainVC = fxmlLoader.getController();
        //scene.getStylesheets().add(App.class.getResource("EffortStyleMK2.css").toExternalForm());
        stage.setTitle("Effort Logger 2.0");
        stage.setScene(scene);
        stage.show();
    }
    
    public void start(Stage stage) throws IOException, SQLException {
        // Initialize database manager
        dbManager = new DatabaseManager();

        //find all saved projects by finding all database files in DBPath sorted by last modified
        File folder = new File(DBDir);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".db")) {
                savedProjects.add(file.getName().replace(".db", ""));
            }
        }

        if(savedProjects.size() > 0){
            //if there are saved projects, load the most recent one
            project = Project.fromDatabase(savedProjects.get(0));
        }

    
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
    
        
        /* 
        URI audioFileURI = new File("resources/EffortLogger_ost2.mp3").toURI();
        Media audioMedia = new Media(audioFileURI.toString());
    
        // Initialize the mediaPlayer instance variable and set it to autoplay
        mediaPlayer = new MediaPlayer(audioMedia);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set the cycleCount to INDEFINITE
        */
    }    

    public static void createProject(String projectName) throws SQLException{
        //create a new project with the given name
        project = new Project(projectName);

        //add to project list
        savedProjects.add(project.DatabaseName);

        mainVC.setProject(project);
    }

    public static void newProject(){
        //create a popup window to get the project name
        Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        stage.setTitle("Create Project");

        //add a text field to the popup window
        TextField projectName = new TextField();
        projectName.setPromptText("Project Name");
        projectName.setPrefColumnCount(15);

        
        //add create button to the popup window
        Button createButton = new Button("Create");
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //create a new project with the given name
                try {
                    App.createProject(projectName.getText());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                stage.close();
            }
        });

        HBox container = new HBox(10, projectName, createButton);
        container.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        container.setAlignment(javafx.geometry.Pos.CENTER);

        ((Group) scene.getRoot()).getChildren().addAll(container);

        stage.setScene(scene);
        stage.show();
    }
}