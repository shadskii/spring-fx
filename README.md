# spring-fx
Spring-FX allows for better integration of the Spring Framework and JavaFX. By leveraging the dependency injection power of spring we coupling between .fxml files and their controllers. This allows for greater flexibility in moving code around and reduces the overall size of controllers.

JavaFX components are loaded as spring beans during application initialization.
```java
public class ExampleApplication extends Application
{
    private static ClassPathResource FXML = new ClassPathResource("MainScreen.fxml");

    public static void main(String[] args)
    {
        launch(args);
    }

    private Scene mainScene;
    private ConfigurableApplicationContext ctx;

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
```

Controllers now can inject JavaFX components via `@Autowired`
```java
    @Autowired 
    private Button button1;

    @Autowired
    private Label middleText;
```

`intialize` is now replaced with a `@PostConstruct`
```java
  @FXML
  public void intialize(){}
  
  // Is replaced with
  @PostConstruct
  void intialize(){}
```
