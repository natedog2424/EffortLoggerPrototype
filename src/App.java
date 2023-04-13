import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {
        String name = "Nathan Anderson";
    	System.out.format("%s Hello World! \n", name);
    	System.out.println("It started!");
        primaryStage.setTitle("Hello World");
        Button btn = new Button();
        btn.setText(String.format("Display: '%s says: Hello World!'",name));
        btn.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                System.out.format("%s: Hello World!\n", name);
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}