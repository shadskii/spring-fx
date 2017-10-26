/*
 * MIT License
 *
 * Copyright (c) [2017] [Jake]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package freetimelabs.example.fx;

import freetimelabs.lifecycle.fx.SpringFXLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;


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
        SpringApplication application = new SpringApplication(ExampleApplicationConfig.class);
        application.addInitializers(SpringFXLoader.loadFX(mainScene));
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
