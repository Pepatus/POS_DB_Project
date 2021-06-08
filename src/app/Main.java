package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));*/
        FXMLLoader loader = null;
        loader = new FXMLLoader(getClass().getResource("../sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        primaryStage.setTitle("Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



/*
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
