/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package churl.spiro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        // System.setProperty("quantum.multithreaded", "false");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));

        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }
}