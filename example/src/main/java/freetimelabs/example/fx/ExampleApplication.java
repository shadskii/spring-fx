package freetimelabs.example.fx;

import freetimelabs.lifecycle.fx.SpringFXLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
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
    ConfigurableApplicationContext ctx;
    @Override
    public void init() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(FXML.getURL());
        mainScene = new Scene(loader.load());
        SpringApplication application = new SpringApplication(Object.class);
//        application.addInitializers(SpringFXLoader.loadFX(mainScene));
        ctx = application.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Example application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
