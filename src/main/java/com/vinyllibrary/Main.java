package com.vinyllibrary;

import com.vinyllibrary.model.VinylLibrary;
import com.vinyllibrary.patterns.session.SessionManager;
import com.vinyllibrary.util.UserSimulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vinyllibrary/view/main-view.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Vinyl Library");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.show();

        VinylLibrary library = new VinylLibrary();

        Thread simulationThread = new Thread(new UserSimulation(library), "UserSimulationThread");
        simulationThread.setDaemon(true);
        simulationThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
