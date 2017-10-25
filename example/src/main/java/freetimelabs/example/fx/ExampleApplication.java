package freetimelabs.example.fx;

import freetimelabs.lifecycle.fx.SpringFXLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class ExampleApplication extends Application
{
    private static ClassPathResource FXML = new ClassPathResource("MainScreen.fxml");

    public static void main(String[] args)
    {
        launch(args);
    }

    private Scene mainScene;

    @Override
    public void init() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(FXML.getURL());
        mainScene = new Scene(loader.load());
//        Spring
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

    }
}
